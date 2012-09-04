/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.workflow.ui.rcp.communication.instance.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
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
     * @param message the WorkflowDefinitionMsg.
     * @return the WorkflowInstanceMsg.
     * @throws ClientException
     */
    public WorkflowInstanceMsg produceWorkflowInstance(WorkflowDefinitionMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<WorkflowDefinitionMsg> request = new ServiceRequest<WorkflowDefinitionMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
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
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceWorkflowInstance.produceWorkflowInstance");
    }

    /**
     * ProduceWorkflowInstanceEntry.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the WorkflowStateMsg.
     * @return the WorkflowInstanceEntryMsg.
     * @throws ClientException
     */
    public WorkflowInstanceEntryMsg produceWorkflowInstanceEntry(WorkflowStateMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<WorkflowStateMsg> request = new ServiceRequest<WorkflowStateMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceEntryMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
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
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceWorkflowInstance.produceWorkflowInstanceEntry");
    }
}
