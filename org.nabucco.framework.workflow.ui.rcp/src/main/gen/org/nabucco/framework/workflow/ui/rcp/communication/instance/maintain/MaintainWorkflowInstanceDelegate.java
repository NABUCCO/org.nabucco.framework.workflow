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
package org.nabucco.framework.workflow.ui.rcp.communication.instance.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.instance.maintain.MaintainWorkflowInstance;

/**
 * MaintainWorkflowInstanceDelegate<p/>Workflow instance maintain service<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public class MaintainWorkflowInstanceDelegate extends ServiceDelegateSupport {

    private MaintainWorkflowInstance service;

    /**
     * Constructs a new MaintainWorkflowInstanceDelegate instance.
     *
     * @param service the MaintainWorkflowInstance.
     */
    public MaintainWorkflowInstanceDelegate(MaintainWorkflowInstance service) {
        super();
        this.service = service;
    }

    /**
     * MaintainWorkflowInstance.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the WorkflowInstanceMsg.
     * @return the WorkflowInstanceMsg.
     * @throws ClientException
     */
    public WorkflowInstanceMsg maintainWorkflowInstance(WorkflowInstanceMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<WorkflowInstanceMsg> request = new ServiceRequest<WorkflowInstanceMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainWorkflowInstance(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainWorkflowInstance.class, "maintainWorkflowInstance", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainWorkflowInstance.maintainWorkflowInstance");
    }
}
