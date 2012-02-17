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
package org.nabucco.framework.workflow.impl.service.crosscutting;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.workflow.ProcessWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;
import org.nabucco.framework.workflow.facade.component.WorkflowComponentLocator;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowResult;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService;
import org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance;
import org.nabucco.framework.workflow.impl.service.crosscutting.util.WorkflowContextSupport;

/**
 * ProcessWorkflowServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProcessWorkflowServiceHandlerImpl extends ProcessWorkflowServiceHandler {

    private static final long serialVersionUID = 1L;

    private WorkflowComponent component;

    @Override
    protected WorkflowResultRs processWorkflow(ProcessWorkflowRq rq) throws WorkflowException {

        Identifier instanceId = rq.getInstanceId();

        try {
            this.component = WorkflowComponentLocator.getInstance().getComponent();

            WorkflowResult result = this.processTransition(rq);
            WorkflowInstance instance = result.getInstance();

            org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs rs;
            rs = new org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs();
            rs.setWorkflowName(instance.getDefinition().getName());
            rs.setInstanceId(new Identifier(instance.getId()));
            rs.setInstance(instance);
            rs.setContext(instance.getCurrentEntry().getContext());
            rs.getNextTransitions().addAll(result.getNextTransitions());
            rs.setStateName(instance.getCurrentEntry().getState().getName());
            rs.getConstraintList().addAll(instance.getCurrentEntry().getState().getConstraintList());

            return rs;

        } catch (ConnectionException ce) {
            throw new WorkflowException("Error locating WorkflowComponent from JNDI.", ce);
        } catch (ServiceException se) {
            throw new WorkflowException("Error processing workflow with id " + instanceId + ".", se);
        } catch (Exception e) {
            throw new WorkflowException("Error processing workflow with id " + instanceId + ".", e);
        }

    }

    /**
     * Process an existing workflow instance with the given id to the next state.
     * 
     * @param instanceId
     *            id of the workflow instance to process
     * 
     * @return the workflow result
     * 
     * @throws ServiceException
     *             when the workflow cannot be processed
     */
    private WorkflowResult processTransition(ProcessWorkflowRq request) throws ServiceException {
        Long instanceId = request.getInstanceId().getValue();
        WorkflowInstance instance = this.findInstance(instanceId);

        WorkflowEngineService engineService = this.component.getWorkflowEngineService();
        ServiceRequest<ExecuteWorkflowRq> rq = new ServiceRequest<ExecuteWorkflowRq>(super.getContext());

        ExecuteWorkflowRq requestMessage = new ExecuteWorkflowRq();
        requestMessage.setInstance(instance);
        requestMessage.setSignal(request.getSignal());
        requestMessage.setComment(request.getComment());

        for (Instance subInstance : request.getSubInstances()) {
            if (subInstance.getId() != null) {
                if (subInstance instanceof WorkflowInstance) {
                    instance.getSubInstances().add((WorkflowInstance) subInstance);

                    if (instance.getDatatypeState() == DatatypeState.PERSISTENT) {
                        instance.setDatatypeState(DatatypeState.MODIFIED);
                    }
                }
            }
        }

        if (request.getContext() != null) {
            requestMessage.setContext(WorkflowContextSupport.createContext(request.getContext()));
        }

        rq.setRequestMessage(requestMessage);

        ServiceResponse<org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs> rs = engineService
                .processTransition(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new WorkflowException("Cannot process workflow with id " + instanceId + ".");
        }

        WorkflowResult result = rs.getResponseMessage().getResult();

        if (result == null || result.getInstance() == null) {
            throw new WorkflowException("Cannot process workflow with id " + instanceId + ".");
        }

        return result;
    }

    /**
     * Search the workflow instance with the given id.
     * 
     * @param instanceId
     *            id of the workflow instance to find
     * 
     * @return the loaded workflow instance
     * 
     * @throws ServiceException
     *             when the search did not end expected or no instance was found
     */
    private WorkflowInstance findInstance(Long instanceId) throws ServiceException {

        ServiceRequest<WorkflowInstanceMsg> rq = new ServiceRequest<WorkflowInstanceMsg>(super.getContext());

        WorkflowInstance instance = new WorkflowInstance();
        instance.setId(instanceId);

        WorkflowInstanceMsg msg = new WorkflowInstanceMsg();
        msg.setWorkflowInstance(instance);
        rq.setRequestMessage(msg);

        ResolveWorkflowInstance resolveService = this.component.getResolveWorkflowInstance();
        ServiceResponse<WorkflowInstanceMsg> rs = resolveService.resolveWorkflowInstance(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new WorkflowException("Cannot find workflow instance with id " + instance.getId() + ".");
        }

        instance = rs.getResponseMessage().getWorkflowInstance();

        if (instance == null) {
            throw new WorkflowException("Cannot find workflow instance with id [null].");
        }

        return instance;
    }

}
