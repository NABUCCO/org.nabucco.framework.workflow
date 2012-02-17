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
package org.nabucco.framework.workflow.impl.service.aspect;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContext;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContextResponse;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.visitor.ServiceMessageVisitor;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;
import org.nabucco.framework.workflow.facade.component.WorkflowComponentLocator;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowResult;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.facade.message.engine.ResumeWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;

/**
 * ResumeWorkflowTransitioningAspectVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class ResumeWorkflowTransitioningAspectVisitor extends ServiceMessageVisitor {

    private ServiceMessageContext context;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ResumeWorkflowTransitioningAspectVisitor.class);

    /**
     * Creates a new {@link ResumeWorkflowTransitioningAspectVisitor} instance.
     * 
     * @param context
     *            the service context
     */
    public ResumeWorkflowTransitioningAspectVisitor(ServiceMessageContext context) {
        this.context = context;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        if (!(datatype instanceof NabuccoDatatype)) {
            return;
        }

        ComponentRelationContainer container = DatatypeAccessor.getInstance().getComponentRelation(datatype);

        if (!container.isEmpty()) {
            boolean resumed = this.resumeWorkflowInstances((NabuccoDatatype) datatype, container);
            if (resumed) {
                return;
            }
        }

        super.visit(datatype);
    }

    /**
     * Resume all workflow instances for the given datatype.
     * 
     * @param datatype
     *            the datatype to resume the workflow instances for
     * @param container
     *            the component relation container holding workflow instance component relations
     * 
     * @return <b>true</b> if the workflow has been resumed, <b>false</b> if not
     * 
     * @throws VisitorException
     *             when the resuming fails
     */
    private boolean resumeWorkflowInstances(NabuccoDatatype datatype, ComponentRelationContainer container)
            throws VisitorException {

        for (ComponentRelation<?> relation : container.getAllComponentRelations()) {
            if (relation == null || !(relation.getTarget() instanceof WorkflowInstance)) {
                continue;
            }

            TransitionContext transitionContext = this
                    .resumeWorkflow(datatype, (WorkflowInstance) relation.getTarget());

            if (transitionContext != null) {

                WorkflowTransitionContext workflowContext = (WorkflowTransitionContext) this.context
                        .get(ServiceSubContextType.WORKFLOW_TRANSITION);

                if (workflowContext == null) {
                    workflowContext = new WorkflowTransitionContext();
                    this.context.put(ServiceSubContextType.WORKFLOW_TRANSITION, workflowContext);
                }

                WorkflowTransitionContextResponse responseTransitionContext = null;
                if (workflowContext.getResponseTransitionContext() == null) {
                    responseTransitionContext = new WorkflowTransitionContextResponse();
                    workflowContext.setResponseTransitionContext(responseTransitionContext);
                } else {
                    responseTransitionContext = workflowContext.getResponseTransitionContext();
                }
                String instanceId = String.valueOf(transitionContext.getInstanceId());
                responseTransitionContext.getTransitionContextMap().put(instanceId, transitionContext);

                return true;
            }
        }

        return false;
    }

    /**
     * Resume the workflow for the given workflow instance.
     * 
     * @param datatype
     *            the datatype holding the workflow instance
     * @param instance
     *            the workflow instance to resume
     * 
     * @return the transition context holding the workflow transition information
     * 
     * @throws VisitorException
     *             when the resuming fails
     */
    private TransitionContext resumeWorkflow(NabuccoDatatype datatype, WorkflowInstance instance)
            throws VisitorException {

        try {
            WorkflowComponent component = WorkflowComponentLocator.getInstance().getComponent();

            ServiceRequest<ResumeWorkflowRq> rq = new ServiceRequest<ResumeWorkflowRq>(this.context);

            ResumeWorkflowRq msg = new ResumeWorkflowRq();
            msg.setWorkflowInstance(instance);

            WorkflowContext workflowContext = new WorkflowContext();
            workflowContext.setDatatype(datatype);
            msg.setWorkflowContext(workflowContext);

            rq.setRequestMessage(msg);

            ServiceResponse<WorkflowResultRs> rs = component.getWorkflowEngineService().resumeWorkflow(rq);

            WorkflowResult result = rs.getResponseMessage().getResult();

            TransitionContext context = new TransitionContext();
            context.setInstanceId(result.getInstance().getId());
            context.setName(result.getInstance().getDefinition().getName());
            context.setWorkflowContext(result.getInstance().getContext());
            context.setStateName(result.getInstance().getCurrentEntry().getState().getName());
            context.getNextTransitions().addAll(result.getNextTransitions());

            return context;

        } catch (ConnectionException ce) {
            logger.error(ce, "Error connection to Workflow Component.");
            throw new VisitorException("Error connecting to Workflow Component.", ce);
        } catch (WorkflowException we) {
            logger.error("Cannot resume Workflow '", instance.getName(), "'.", we);
        } catch (Exception e) {
            logger.error("Error resuming Workflow '", instance.getName(), "'.", e);
            throw new VisitorException("Error resuming Workflow ", e);
        }

        return null;
    }
}
