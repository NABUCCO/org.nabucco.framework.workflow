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
package org.nabucco.framework.workflow.ui.web.communication.instance.resolve;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance;

/**
 * ResolveWorkflowInstanceDelegate<p/>Workflow instance resolution service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-28
 */
public class ResolveWorkflowInstanceDelegate extends ServiceDelegateSupport {

    private ResolveWorkflowInstance service;

    /**
     * Constructs a new ResolveWorkflowInstanceDelegate instance.
     *
     * @param service the ResolveWorkflowInstance.
     */
    public ResolveWorkflowInstanceDelegate(ResolveWorkflowInstance service) {
        super();
        this.service = service;
    }

    /**
     * ResolveWorkflowInstance.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowInstanceMsg.
     * @return the WorkflowInstanceMsg.
     * @throws ResolveException
     */
    public WorkflowInstanceMsg resolveWorkflowInstance(WorkflowInstanceMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws ResolveException {
        ServiceRequest<WorkflowInstanceMsg> request = new ServiceRequest<WorkflowInstanceMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.resolveWorkflowInstance(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ResolveWorkflowInstance.class, "resolveWorkflowInstance", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ResolveException("Cannot execute service operation: ResolveWorkflowInstance.resolveWorkflowInstance");
    }
}
