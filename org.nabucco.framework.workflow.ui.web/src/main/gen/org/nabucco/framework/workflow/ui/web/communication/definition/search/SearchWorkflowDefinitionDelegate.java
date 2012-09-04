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
package org.nabucco.framework.workflow.ui.web.communication.definition.search;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateListMsg;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowDefinitionSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowSignalSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowStateSearchRq;
import org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition;

/**
 * SearchWorkflowDefinitionDelegate<p/>Workflow definition search service.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class SearchWorkflowDefinitionDelegate extends ServiceDelegateSupport {

    private SearchWorkflowDefinition service;

    /**
     * Constructs a new SearchWorkflowDefinitionDelegate instance.
     *
     * @param service the SearchWorkflowDefinition.
     */
    public SearchWorkflowDefinitionDelegate(SearchWorkflowDefinition service) {
        super();
        this.service = service;
    }

    /**
     * SearchWorkflowDefinition.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowDefinitionSearchRq.
     * @return the WorkflowDefinitionListMsg.
     * @throws SearchException
     */
    public WorkflowDefinitionListMsg searchWorkflowDefinition(WorkflowDefinitionSearchRq message,
            NabuccoSession session, ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<WorkflowDefinitionSearchRq> request = new ServiceRequest<WorkflowDefinitionSearchRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowDefinitionListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchWorkflowDefinition(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchWorkflowDefinition.class, "searchWorkflowDefinition", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: SearchWorkflowDefinition.searchWorkflowDefinition");
    }

    /**
     * SearchWorkflowState.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowStateSearchRq.
     * @return the WorkflowStateListMsg.
     * @throws SearchException
     */
    public WorkflowStateListMsg searchWorkflowState(WorkflowStateSearchRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<WorkflowStateSearchRq> request = new ServiceRequest<WorkflowStateSearchRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowStateListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchWorkflowState(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchWorkflowDefinition.class, "searchWorkflowState", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: SearchWorkflowDefinition.searchWorkflowState");
    }

    /**
     * SearchWorkflowSignal.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowSignalSearchRq.
     * @return the WorkflowSignalListMsg.
     * @throws SearchException
     */
    public WorkflowSignalListMsg searchWorkflowSignal(WorkflowSignalSearchRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<WorkflowSignalSearchRq> request = new ServiceRequest<WorkflowSignalSearchRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowSignalListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchWorkflowSignal(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchWorkflowDefinition.class, "searchWorkflowSignal", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: SearchWorkflowDefinition.searchWorkflowSignal");
    }
}
