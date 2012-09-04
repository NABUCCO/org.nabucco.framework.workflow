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
package org.nabucco.framework.workflow.ui.rcp.communication.datatype.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;
import org.nabucco.framework.workflow.facade.service.datatype.produce.ProduceWorkflowDatatype;

/**
 * ProduceWorkflowDatatypeDelegate<p/>Workflow datatype produce service<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class ProduceWorkflowDatatypeDelegate extends ServiceDelegateSupport {

    private ProduceWorkflowDatatype service;

    /**
     * Constructs a new ProduceWorkflowDatatypeDelegate instance.
     *
     * @param service the ProduceWorkflowDatatype.
     */
    public ProduceWorkflowDatatypeDelegate(ProduceWorkflowDatatype service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTask.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TaskMsg.
     * @return the TaskMsg.
     * @throws ClientException
     */
    public TaskMsg produceTask(TaskMsg message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TaskMsg> request = new ServiceRequest<TaskMsg>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TaskMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTask(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDatatype.class, "produceTask", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceWorkflowDatatype.produceTask");
    }
}
