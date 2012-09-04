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
package org.nabucco.framework.workflow.ui.web.communication.definition.resolve;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;
import org.nabucco.framework.workflow.facade.service.definition.resolve.ResolveWorkflowDefinition;

/**
 * ResolveWorkflowDefinitionDelegate<p/>Workflow definition resolution service.<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2010-04-28
 */
public class ResolveWorkflowDefinitionDelegate extends ServiceDelegateSupport {

    private ResolveWorkflowDefinition service;

    /**
     * Constructs a new ResolveWorkflowDefinitionDelegate instance.
     *
     * @param service the ResolveWorkflowDefinition.
     */
    public ResolveWorkflowDefinitionDelegate(ResolveWorkflowDefinition service) {
        super();
        this.service = service;
    }

    /**
     * ResolveWorkflowDefinition.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowDefinitionListMsg.
     * @return the WorkflowDefinitionListMsg.
     * @throws ResolveException
     */
    public WorkflowDefinitionListMsg resolveWorkflowDefinition(WorkflowDefinitionListMsg message,
            NabuccoSession session, ServiceSubContext... subContexts) throws ResolveException {
        ServiceRequest<WorkflowDefinitionListMsg> request = new ServiceRequest<WorkflowDefinitionListMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowDefinitionListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.resolveWorkflowDefinition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ResolveWorkflowDefinition.class, "resolveWorkflowDefinition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ResolveException(
                "Cannot execute service operation: ResolveWorkflowDefinition.resolveWorkflowDefinition");
    }
}
