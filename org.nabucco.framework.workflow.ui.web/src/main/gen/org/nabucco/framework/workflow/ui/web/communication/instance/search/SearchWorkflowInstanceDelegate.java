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
package org.nabucco.framework.workflow.ui.web.communication.instance.search;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceListMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.message.instance.search.WorkflowInstanceSearchRq;
import org.nabucco.framework.workflow.facade.service.instance.search.SearchWorkflowInstance;

/**
 * SearchWorkflowInstanceDelegate<p/>Workflow instance search service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-28
 */
public class SearchWorkflowInstanceDelegate extends ServiceDelegateSupport {

    private SearchWorkflowInstance service;

    /**
     * Constructs a new SearchWorkflowInstanceDelegate instance.
     *
     * @param service the SearchWorkflowInstance.
     */
    public SearchWorkflowInstanceDelegate(SearchWorkflowInstance service) {
        super();
        this.service = service;
    }

    /**
     * SearchWorkflowInstance.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowInstanceSearchRq.
     * @return the WorkflowInstanceListMsg.
     * @throws SearchException
     */
    public WorkflowInstanceListMsg searchWorkflowInstance(WorkflowInstanceSearchRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<WorkflowInstanceSearchRq> request = new ServiceRequest<WorkflowInstanceSearchRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchWorkflowInstance(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchWorkflowInstance.class, "searchWorkflowInstance", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: SearchWorkflowInstance.searchWorkflowInstance");
    }

    /**
     * SearchParentWorkflowInstance.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the WorkflowInstanceMsg.
     * @return the WorkflowInstanceMsg.
     * @throws SearchException
     */
    public WorkflowInstanceMsg searchParentWorkflowInstance(WorkflowInstanceMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<WorkflowInstanceMsg> request = new ServiceRequest<WorkflowInstanceMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<WorkflowInstanceMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchParentWorkflowInstance(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchWorkflowInstance.class, "searchParentWorkflowInstance", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException(
                "Cannot execute service operation: SearchWorkflowInstance.searchParentWorkflowInstance");
    }
}
