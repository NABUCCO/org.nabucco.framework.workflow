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
package org.nabucco.framework.workflow.ui.rcp.communication.datatype.search;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskListMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.search.TaskSearchRq;
import org.nabucco.framework.workflow.facade.service.datatype.search.SearchWorkflowDatatype;

/**
 * SearchWorkflowDatatypeDelegate<p/>Search service for workflow Datatypes<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class SearchWorkflowDatatypeDelegate extends ServiceDelegateSupport {

    private SearchWorkflowDatatype service;

    /**
     * Constructs a new SearchWorkflowDatatypeDelegate instance.
     *
     * @param service the SearchWorkflowDatatype.
     */
    public SearchWorkflowDatatypeDelegate(SearchWorkflowDatatype service) {
        super();
        this.service = service;
    }

    /**
     * SearchTasks.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TaskSearchRq.
     * @return the TaskListMsg.
     * @throws ClientException
     */
    public TaskListMsg searchTasks(TaskSearchRq message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TaskSearchRq> request = new ServiceRequest<TaskSearchRq>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TaskListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchTasks(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchWorkflowDatatype.class, "searchTasks", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SearchWorkflowDatatype.searchTasks");
    }
}
