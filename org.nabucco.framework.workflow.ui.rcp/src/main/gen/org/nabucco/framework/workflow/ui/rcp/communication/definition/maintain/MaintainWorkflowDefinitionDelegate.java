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
package org.nabucco.framework.workflow.ui.rcp.communication.definition.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;
import org.nabucco.framework.workflow.facade.service.definition.maintain.MaintainWorkflowDefinition;

/**
 * MaintainWorkflowDefinitionDelegate<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class MaintainWorkflowDefinitionDelegate extends ServiceDelegateSupport {

    private MaintainWorkflowDefinition service;

    /**
     * Constructs a new MaintainWorkflowDefinitionDelegate instance.
     *
     * @param service the MaintainWorkflowDefinition.
     */
    public MaintainWorkflowDefinitionDelegate(MaintainWorkflowDefinition service) {
        super();
        this.service = service;
    }

    /**
     * MaintainWorkflowDefinition.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the WorkflowDefinitionMsg.
     * @return the WorkflowDefinitionMsg.
     * @throws ClientException
     */
    public WorkflowDefinitionMsg maintainWorkflowDefinition(WorkflowDefinitionMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<WorkflowDefinitionMsg> request = new ServiceRequest<WorkflowDefinitionMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowDefinitionMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainWorkflowDefinition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainWorkflowDefinition.class, "maintainWorkflowDefinition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException(
                "Cannot execute service operation: MaintainWorkflowDefinition.maintainWorkflowDefinition");
    }

    /**
     * MaintainWorkflowSignal.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the WorkflowSignalMsg.
     * @return the WorkflowSignalMsg.
     * @throws ClientException
     */
    public WorkflowSignalMsg maintainWorkflowSignal(WorkflowSignalMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<WorkflowSignalMsg> request = new ServiceRequest<WorkflowSignalMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowSignalMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainWorkflowSignal(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainWorkflowDefinition.class, "maintainWorkflowSignal", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainWorkflowDefinition.maintainWorkflowSignal");
    }
}
