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
package org.nabucco.framework.workflow.ui.rcp.communication.datatype.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMaintainRqMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;
import org.nabucco.framework.workflow.facade.service.datatype.maintain.MaintainWorkflowDatatype;

/**
 * MaintainWorkflowDatatypeDelegate<p/>Workflow datatype maintain service<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class MaintainWorkflowDatatypeDelegate extends ServiceDelegateSupport {

    private MaintainWorkflowDatatype service;

    /**
     * Constructs a new MaintainWorkflowDatatypeDelegate instance.
     *
     * @param service the MaintainWorkflowDatatype.
     */
    public MaintainWorkflowDatatypeDelegate(MaintainWorkflowDatatype service) {
        super();
        this.service = service;
    }

    /**
     * MaintainTask.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TaskMaintainRqMsg.
     * @return the TaskMsg.
     * @throws ClientException
     */
    public TaskMsg maintainTask(TaskMaintainRqMsg message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TaskMaintainRqMsg> request = new ServiceRequest<TaskMaintainRqMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TaskMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainTask(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainWorkflowDatatype.class, "maintainTask", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainWorkflowDatatype.maintainTask");
    }
}
