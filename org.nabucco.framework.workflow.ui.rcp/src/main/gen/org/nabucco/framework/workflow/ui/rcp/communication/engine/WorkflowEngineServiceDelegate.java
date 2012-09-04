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
package org.nabucco.framework.workflow.ui.rcp.communication.engine;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.ResumeWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowTimerRq;
import org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService;

/**
 * WorkflowEngineServiceDelegate<p/>Workflow Engine service that executes the workflow transitions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public class WorkflowEngineServiceDelegate extends ServiceDelegateSupport {

    private WorkflowEngineService service;

    /**
     * Constructs a new WorkflowEngineServiceDelegate instance.
     *
     * @param service the WorkflowEngineService.
     */
    public WorkflowEngineServiceDelegate(WorkflowEngineService service) {
        super();
        this.service = service;
    }

    /**
     * StartWorkflow.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the InitWorkflowRq.
     * @return the WorkflowResultRs.
     * @throws ClientException
     */
    public WorkflowResultRs startWorkflow(InitWorkflowRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<InitWorkflowRq> request = new ServiceRequest<InitWorkflowRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowResultRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.startWorkflow(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(WorkflowEngineService.class, "startWorkflow", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: WorkflowEngineService.startWorkflow");
    }

    /**
     * ProcessTransition.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ExecuteWorkflowRq.
     * @return the WorkflowResultRs.
     * @throws ClientException
     */
    public WorkflowResultRs processTransition(ExecuteWorkflowRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ExecuteWorkflowRq> request = new ServiceRequest<ExecuteWorkflowRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowResultRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.processTransition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(WorkflowEngineService.class, "processTransition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: WorkflowEngineService.processTransition");
    }

    /**
     * ResumeWorkflow.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ResumeWorkflowRq.
     * @return the WorkflowResultRs.
     * @throws ClientException
     */
    public WorkflowResultRs resumeWorkflow(ResumeWorkflowRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ResumeWorkflowRq> request = new ServiceRequest<ResumeWorkflowRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowResultRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.resumeWorkflow(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(WorkflowEngineService.class, "resumeWorkflow", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: WorkflowEngineService.resumeWorkflow");
    }

    /**
     * StartTimer.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the WorkflowTimerRq.
     * @return the EmptyServiceMessage.
     * @throws ClientException
     */
    public EmptyServiceMessage startTimer(WorkflowTimerRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<WorkflowTimerRq> request = new ServiceRequest<WorkflowTimerRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<EmptyServiceMessage> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.startTimer(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(WorkflowEngineService.class, "startTimer", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: WorkflowEngineService.startTimer");
    }
}
