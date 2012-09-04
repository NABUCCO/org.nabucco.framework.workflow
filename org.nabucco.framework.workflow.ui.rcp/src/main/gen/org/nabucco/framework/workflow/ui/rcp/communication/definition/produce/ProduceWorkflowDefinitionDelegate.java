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
package org.nabucco.framework.workflow.ui.rcp.communication.definition.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowConditionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowEffectMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowTransitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowTriggerMsg;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowConditionRq;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowEffectRq;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowTriggerRq;
import org.nabucco.framework.workflow.facade.service.definition.produce.ProduceWorkflowDefinition;

/**
 * ProduceWorkflowDefinitionDelegate<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public class ProduceWorkflowDefinitionDelegate extends ServiceDelegateSupport {

    private ProduceWorkflowDefinition service;

    /**
     * Constructs a new ProduceWorkflowDefinitionDelegate instance.
     *
     * @param service the ProduceWorkflowDefinition.
     */
    public ProduceWorkflowDefinitionDelegate(ProduceWorkflowDefinition service) {
        super();
        this.service = service;
    }

    /**
     * ProduceWorkflowDefinition.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the WorkflowDefinitionMsg.
     * @throws ClientException
     */
    public WorkflowDefinitionMsg produceWorkflowDefinition(EmptyServiceMessage message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowDefinitionMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowDefinition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowDefinition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowDefinition");
    }

    /**
     * ProduceWorkflowState.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the WorkflowStateMsg.
     * @throws ClientException
     */
    public WorkflowStateMsg produceWorkflowState(EmptyServiceMessage message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowStateMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowState(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowState", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowState");
    }

    /**
     * ProduceWorkflowTransition.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the WorkflowTransitionMsg.
     * @throws ClientException
     */
    public WorkflowTransitionMsg produceWorkflowTransition(EmptyServiceMessage message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowTransitionMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowTransition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowTransition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowTransition");
    }

    /**
     * ProduceWorkflowSignal.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the WorkflowSignalMsg.
     * @throws ClientException
     */
    public WorkflowSignalMsg produceWorkflowSignal(EmptyServiceMessage message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowSignalMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowSignal(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowSignal", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowSignal");
    }

    /**
     * ProduceWorkflowTrigger.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceWorkflowTriggerRq.
     * @return the WorkflowTriggerMsg.
     * @throws ClientException
     */
    public WorkflowTriggerMsg produceWorkflowTrigger(ProduceWorkflowTriggerRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ProduceWorkflowTriggerRq> request = new ServiceRequest<ProduceWorkflowTriggerRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowTriggerMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowTrigger(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowTrigger", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowTrigger");
    }

    /**
     * ProduceWorkflowCondition.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceWorkflowConditionRq.
     * @return the WorkflowConditionMsg.
     * @throws ClientException
     */
    public WorkflowConditionMsg produceWorkflowCondition(ProduceWorkflowConditionRq message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ProduceWorkflowConditionRq> request = new ServiceRequest<ProduceWorkflowConditionRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowConditionMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowCondition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowCondition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowCondition");
    }

    /**
     * ProduceWorkflowEffect.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceWorkflowEffectRq.
     * @return the WorkflowEffectMsg.
     * @throws ClientException
     */
    public WorkflowEffectMsg produceWorkflowEffect(ProduceWorkflowEffectRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ProduceWorkflowEffectRq> request = new ServiceRequest<ProduceWorkflowEffectRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowEffectMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceWorkflowEffect(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDefinition.class, "produceWorkflowEffect", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceWorkflowDefinition.produceWorkflowEffect");
    }
}
