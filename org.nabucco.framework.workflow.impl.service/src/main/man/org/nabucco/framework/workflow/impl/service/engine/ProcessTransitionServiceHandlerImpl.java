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
package org.nabucco.framework.workflow.impl.service.engine;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.security.Group;
import org.nabucco.framework.base.facade.datatype.security.Permission;
import org.nabucco.framework.base.facade.datatype.security.Role;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
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
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.TimeTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTriggerType;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowResult;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.impl.service.engine.util.WorkflowFacade;
import org.nabucco.framework.workflow.impl.service.engine.util.condition.ConditionEvaluationVisitor;
import org.nabucco.framework.workflow.impl.service.engine.util.effect.EffectExecutor;
import org.nabucco.framework.workflow.impl.service.engine.util.signal.TransitionFilter;

/**
 * ProcessTransitionServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProcessTransitionServiceHandlerImpl extends ProcessTransitionServiceHandler {

    private static final long serialVersionUID = 1L;

    private WorkflowFacade facade;

    @Override
    protected WorkflowResultRs processTransition(ExecuteWorkflowRq rq) throws WorkflowException {
        this.facade = WorkflowFacade.newInstance(super.getContext());

        WorkflowInstance instance = rq.getInstance();
        WorkflowContext context = rq.getContext();

        WorkflowTransition transition = this.findTransition(rq);

        if (transition != null) {
            instance = this.executeTransition(instance, transition, rq.getContext(), rq.getComment());
        } else {
            try {
                if (instance.getDatatypeState() != DatatypeState.PERSISTENT) {
                    instance = this.facade.maintainInstance(instance);
                }
            } catch (ServiceException se) {
                throw new WorkflowException("Error maintaining workflow instance.", se);
            }
        }

        WorkflowResult result = new WorkflowResult();
        result.setInstance(instance);

        TransitionFilter transitionFilter = new TransitionFilter(instance, context, super.getContext());
        List<TransitionParameter> nextTransitions = transitionFilter.filterNextTransitions(instance);
        result.getNextTransitions().addAll(nextTransitions);

        WorkflowResultRs rs = new WorkflowResultRs();
        rs.setResult(result);

        return rs;
    }

    /**
     * Find the appropriate transition to execute.
     * 
     * @param rq
     *            the execution request
     * 
     * @return the transition to execute
     * 
     * @throws WorkflowException
     *             when the appropriate transition cannot be found
     */
    private WorkflowTransition findTransition(ExecuteWorkflowRq rq) throws WorkflowException {

        WorkflowInstance instance = rq.getInstance();
        WorkflowContext context = rq.getContext();

        WorkflowTransition transition = null;

        if (rq.getSignal() != null) {
            transition = this.findTransition(instance, context, WorkflowTriggerType.SIGNAL, rq.getSignal().getName());
        } else if (rq.getTimer() != null) {
            transition = this.findTransition(instance, context, WorkflowTriggerType.TIME, rq.getTimer());
        }

        return transition;
    }

    /**
     * Filter the selected transition depending on the sent signal and available conditions.
     * 
     * @param instance
     *            the instance holding the current state
     * @param context
     *            the workflow context
     * @param signal
     *            the signal to activate the transition (XOR with timer)
     * @param triggerName
     *            the timer to activate the transition (XOR with signal)
     * 
     * @return the filtered transition
     * 
     * @throws WorkflowException
     *             when no or more than one transitions was found
     */
    private WorkflowTransition findTransition(WorkflowInstance instance, WorkflowContext context,
            WorkflowTriggerType triggerType, Name triggerName) throws WorkflowException {

        if (triggerName == null || triggerName.getValue() == null) {
            throw new WorkflowException(
                    "Cannot process transition without a trigger. Either signal or timer must be specified.");
        }

        List<WorkflowTransition> allTransitions = this.findAllTransitions(instance);
        List<WorkflowTransition> filteredTransitions = new ArrayList<WorkflowTransition>();

        for (WorkflowTransition transition : allTransitions) {

            if (transition.getTrigger().getType() != triggerType) {
                continue;
            }

            switch (transition.getTrigger().getType()) {

            case SIGNAL: {
                SignalTrigger trigger = (SignalTrigger) transition.getTrigger();
                if (!trigger.getSignal().getName().equals(triggerName)) {
                    continue;
                }

                if (this.checkConditions(instance, context, transition)) {
                    filteredTransitions.add(transition);
                }

                break;
            }

            case TIME: {
                TimeTrigger trigger = (TimeTrigger) transition.getTrigger();
                if (!trigger.getTimer().equals(triggerName)) {
                    continue;
                }

                if (this.checkConditions(instance, context, transition)) {
                    filteredTransitions.add(transition);
                }

                break;
            }

            }

        }

        String name = String.valueOf(triggerName);

        if (filteredTransitions.size() == 0) {
            super.getLogger().warning("No transition for trigger '", name, "' found.");
            return null;
        }

        if (filteredTransitions.size() > 1) {
            super.getLogger().warning("More than one transition for trigger '", name, "' found.");
        }

        return filteredTransitions.get(0);
    }

    /**
     * Check the conditions of a given workflow transition.
     * 
     * @param instance
     *            the current workflow instance
     * @param context
     *            the workflow context
     * @param transition
     *            the workflow transition to check the conditions for
     * 
     * @return <b>true</b> when the condition is valid, <b>false</b> when not
     * 
     * @throws WorkflowException
     *             when the condition cannot be evaluated
     */
    private boolean checkConditions(WorkflowInstance instance, WorkflowContext context, WorkflowTransition transition)
            throws WorkflowException {
        if (transition.getCondition() == null) {
            return true;
        }

        User user = null;

        if (super.getContext().getSubject() != null) {
            Subject subject = super.getContext().getSubject();
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
                ServiceRequest<UserRq> rq = new ServiceRequest<UserRq>(super.getContext());
                rq.setRequestMessage(userRq);

                ServiceResponse<AuthorizationInformationRs> rs = authorizationService.getInformation(rq);

                groupList.addAll(rs.getResponseMessage().getGroupList());
                roleList.addAll(rs.getResponseMessage().getRoleList());
                permissionList.addAll(rs.getResponseMessage().getPermissionList());

            } catch (ConnectionException ce) {
                super.getLogger().error(ce, "Error connecting to Authorization Component.");
                throw new WorkflowException("Error connecting to Authorization Component.", ce);
            } catch (AuthorizationException ae) {
                super.getLogger().error(ae, "Error resolving Authorization Information for user with id ", userId, ".");
                throw new WorkflowException("Error resolving Authorization Information.", ae);
            } catch (Exception e) {
                super.getLogger().error(e, "Error resolving Authorization Information for user with id ", userId, "");
                throw new WorkflowException("Error resolving to Authorization Information.", e);
            }
        }

        try {
            ConditionEvaluationVisitor visitor = new ConditionEvaluationVisitor(instance, context, user, groupList,
                    roleList, permissionList, super.getContext());

            transition.getCondition().accept(visitor);
            return visitor.isSuccessful();
        } catch (VisitorException ve) {
            super.getLogger().error(ve, "Error evaluating conditions for transition ", transition.getName(), ".");
        } catch (Exception e) {
            super.getLogger().error(e, "Error evaluating conditions for transition ", transition.getName(), ".");
        }

        return false;
    }

    /**
     * Find all transitions available for the current workflow instance state.
     * 
     * @param instance
     *            the current workflow instance
     * 
     * @return all available transitions from the current instance state
     * 
     * @throws WorkflowException
     *             when no transition was found.
     */
    private List<WorkflowTransition> findAllTransitions(WorkflowInstance instance) throws WorkflowException {
        WorkflowDefinition definition = instance.getDefinition();

        WorkflowInstanceEntry currentEntry = instance.getCurrentEntry();
        WorkflowState currentState = currentEntry.getState();

        List<WorkflowTransition> transitions = new ArrayList<WorkflowTransition>();
        for (WorkflowTransition transition : definition.getTransitionList()) {
            if (transition.getSource().getId().equals(currentState.getId())) {
                transitions.add(transition);
            }
        }

        if (transitions.size() == 0) {
            throw new WorkflowException("No transition from state '" + currentState.getName() + "' found.");
        }

        return transitions;
    }

    /**
     * Execute the workflow transition with its effects.
     * 
     * @param instance
     *            the instance holding the current state
     * @param transition
     *            the transition to process
     * @param context
     *            the current transition context
     * @param comment
     *            the execution comment
     * 
     * @return the instance in its new state
     * 
     * @throws WorkflowException
     *             when the transition failed.
     */
    private WorkflowInstance executeTransition(WorkflowInstance instance, WorkflowTransition transition,
            Context context, Comment comment) throws WorkflowException {

        this.createNewEntry(instance, transition.getTarget(), context, comment);

        ServiceMessageContext serviceContext = super.getContext();

        EffectExecutor executor = new EffectExecutor(instance, transition, context, serviceContext);

        for (WorkflowEffect effect : transition.getEffectList()) {
            executor.execute(effect);
        }

        return this.persist(instance);
    }

    /**
     * Persists the new state of a workflow instance.
     * 
     * @param instance
     *            the current instance
     * @param context
     *            the current transition context
     * 
     * @return the persistet instance
     * 
     * @throws WorkflowException
     *             when the instance cannot be persistet
     */
    private WorkflowInstance persist(WorkflowInstance instance) throws WorkflowException {

        try {
            instance.setDatatypeState(DatatypeState.MODIFIED);
            instance = this.facade.maintainInstance(instance);
        } catch (ServiceException e) {
            throw new WorkflowException("Error persisting workflow instance '"
                    + String.valueOf(instance.getName()) + "' with id '" + instance.getId() + "'.", e);
        }
        return instance;
    }

    /**
     * Create a new workflow instance entry for the given target state.
     * 
     * @param instance
     *            the workflow instance
     * @param targetState
     *            the transition target state
     * @param comment
     *            the entry comment
     * 
     * @throws WorkflowException
     *             when the entry cannot be created
     */
    private void createNewEntry(WorkflowInstance instance, WorkflowState targetState, Context context, Comment comment)
            throws WorkflowException {

        try {
            WorkflowInstanceEntry entry = this.facade.produceEntry(targetState);

            if (context != null) {
                WorkflowContext workflowContext = new WorkflowContext();
                workflowContext.setDatatypeState(DatatypeState.INITIALIZED);
                workflowContext.setDatatype(context.getDatatype());
                workflowContext.setPropertyName(context.getPropertyName());
                entry.setContext(workflowContext);
            }

            entry.setComment(comment);

            instance.getEntryList().add(entry);
            instance.setCurrentEntry(entry);

        } catch (ServiceException e) {
            throw new WorkflowException("Error producing new WorkflowInstanceEntry.", e);
        }
    }

}
