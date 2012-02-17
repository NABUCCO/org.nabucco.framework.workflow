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
package org.nabucco.framework.workflow.ui.web.communication.instance.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceEntryMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.instance.produce.ProduceWorkflowInstance;

/**
 * ProduceWorkflowInstanceDelegate<p/>Workflow instance produce service<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public class ProduceWorkflowInstanceDelegate extends ServiceDelegateSupport {

    private ProduceWorkflowInstance service;

    /**
     * Constructs a new ProduceWorkflowInstanceDelegate instance.
     *
     * @param service the ProduceWorkflowInstance.
     */
    public ProduceWorkflowInstanceDelegate(ProduceWorkflowInstance service) {
        super();
        this.service = service;
    }

    /**
     * ProduceWorkflowInstance.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowDefinitionMsg.
     * @return the WorkflowInstanceMsg.
     * @throws ProduceException
     */
    public WorkflowInstanceMsg produceWorkflowInstance(WorkflowDefinitionMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws ProduceException {
        ServiceRequest<WorkflowDefinitionMsg> request = new ServiceRequest<WorkflowDefinitionMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowInstance(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowInstance.class, "produceWorkflowInstance", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ProduceException("Cannot execute service operation: ProduceWorkflowInstance.produceWorkflowInstance");
    }

    /**
     * ProduceWorkflowInstanceEntry.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowStateMsg.
     * @return the WorkflowInstanceEntryMsg.
     * @throws ProduceException
     */
    public WorkflowInstanceEntryMsg produceWorkflowInstanceEntry(WorkflowStateMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws ProduceException {
        ServiceRequest<WorkflowStateMsg> request = new ServiceRequest<WorkflowStateMsg>(super.createServiceContext(
                session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceEntryMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowInstanceEntry(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowInstance.class, "produceWorkflowInstanceEntry", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ProduceException(
                "Cannot execute service operation: ProduceWorkflowInstance.produceWorkflowInstanceEntry");
    }
}
