/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.workflow.impl.service.engine.util.signal;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.security.Group;
import org.nabucco.framework.base.facade.datatype.security.Permission;
import org.nabucco.framework.base.facade.datatype.security.Role;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.authorization.UserRq;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.common.authorization.facade.component.AuthorizationComponent;
import org.nabucco.framework.common.authorization.facade.component.AuthorizationComponentLocator;
import org.nabucco.framework.common.authorization.facade.exception.AuthorizationException;
import org.nabucco.framework.common.authorization.facade.message.AuthorizationInformationRs;
import org.nabucco.framework.common.authorization.facade.service.AuthorizationService;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTriggerType;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.impl.service.engine.util.condition.ConditionEvaluationVisitor;

/**
 * TransitionFilter
 * <p/>
 * Utility for filitering workflow transitions and their signals.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TransitionFilter {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TransitionFilter.class);

    private WorkflowInstance instance;

    private WorkflowContext context;

    private ServiceMessageContext serviceContext;

    /**
     * Creates a new {@link TransitionFilter} instance.
     * 
     * @param instance
     *            the workflow instance
     * @param context
     *            the workflow context
     * @param serviceContext
     *            the service context
     */
    public TransitionFilter(WorkflowInstance instance, WorkflowContext context, ServiceMessageContext serviceContext) {
        this.instance = instance;
        this.serviceContext = serviceContext;
        this.context = context;
    }

    /**
     * Filter the next signals of a workflow instance.
     * 
     * @param instance
     *            the instance to resolve
     * 
     * @return the next signals
     * 
     * @throws WorkflowException
     *             when the signals cannot be filtered
     */
    public List<TransitionParameter> filterNextTransitions(WorkflowInstance instance) throws WorkflowException {

        WorkflowDefinition definition = instance.getDefinition();
        Long stateId = instance.getCurrentEntry().getState().getId();
        List<TransitionParameter> nextTransitions = new ArrayList<TransitionParameter>();

        for (WorkflowTransition transition : definition.getTransitionList()) {
            if (!transition.getSource().getId().equals(stateId)) {
                continue;
            }

            if (!this.checkConditions(transition)) {
                continue;
            }

            if (transition.getTrigger().getType() == WorkflowTriggerType.SIGNAL) {
                SignalTrigger trigger = (SignalTrigger) transition.getTrigger();

                TransitionParameter parameter = new TransitionParameter();
                parameter.setCommentCardinality(transition.getCommentCardinality());
                parameter.setSignal(trigger.getSignal());

                nextTransitions.add(parameter);
            }
        }

        return nextTransitions;
    }

    /**
     * Check the conditions of a given workflow transition.
     * 
     * @param transition
     *            the workflow transition to check the conditions for
     * 
     * @return <b>true</b> when the condition is valid, <b>false</b> when not
     * 
     * @throws WorkflowException
     *             when the condition cannot be evaluated
     */
    private boolean checkConditions(WorkflowTransition transition) throws WorkflowException {
        if (transition.getCondition() == null) {
            return true;
        }

        User user = null;

        if (this.serviceContext.getSubject() != null) {
            Subject subject = this.serviceContext.getSubject();
            if (subject.getUserId() != null && subject.getUserId().getValue() != null) {
                user = subject.getUser();
            }
        }

        List<Group> groupList = new ArrayList<Group>();
        List<Role> roleList = new ArrayList<Role>();
        List<Permission> permissionList = new ArrayList<Permission>();

        if (user != null && user.getUsername() != null) {

            Name userId = user.getUsername();

            try {
                AuthorizationComponent component = AuthorizationComponentLocator.getInstance().getComponent();
                AuthorizationService authorizationService = component.getAuthorizationService();

                UserRq userRq = new UserRq();
                userRq.setUserId(new UserId(userId.getValue()));
                ServiceRequest<UserRq> rq = new ServiceRequest<UserRq>(this.serviceContext);
                rq.setRequestMessage(userRq);

                ServiceResponse<AuthorizationInformationRs> rs = authorizationService.getInformation(rq);

                groupList.addAll(rs.getResponseMessage().getGroupList());
                roleList.addAll(rs.getResponseMessage().getRoleList());
                permissionList.addAll(rs.getResponseMessage().getPermissionList());

            } catch (ConnectionException ce) {
                logger.error(ce, "Error connecting to Authorization Component.");
                throw new WorkflowException("Error connecting to Authorization Component.", ce);
            } catch (AuthorizationException ae) {
                logger.error(ae, "Error resolving Authorization Information for user with id ", userId, ".");
                throw new WorkflowException("Error resolving Authorization Information.", ae);
            } catch (Exception e) {
                logger.error(e, "Error resolving Authorization Information for user with id ", userId, "");
                throw new WorkflowException("Error resolving to Authorization Information.", e);
            }
        }

        try {
            ConditionEvaluationVisitor visitor = new ConditionEvaluationVisitor(this.instance, this.context, user,
                    groupList, roleList, permissionList, this.serviceContext);
            transition.getCondition().accept(visitor);
            return visitor.isSuccessful();
        } catch (VisitorException ve) {
            logger.error(ve, "Error evaluating conditions for transition ", transition.getName(), ".");
        } catch (Exception e) {
            logger.error(e, "Error evaluating conditions for transition ", transition.getName(), ".");
        }

        return false;
    }
}
