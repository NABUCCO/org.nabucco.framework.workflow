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
package org.nabucco.framework.workflow.facade.service.instance.search;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceListMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.message.instance.search.WorkflowInstanceSearchRq;

/**
 * SearchWorkflowInstance<p/>Workflow instance search service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-28
 */
public interface SearchWorkflowInstance extends Service {

    /**
     * Search for workflow instances.
     *
     * @param rq the ServiceRequest<WorkflowInstanceSearchRq>.
     * @return the ServiceResponse<WorkflowInstanceListMsg>.
     * @throws SearchException
     */
    ServiceResponse<WorkflowInstanceListMsg> searchWorkflowInstance(ServiceRequest<WorkflowInstanceSearchRq> rq)
            throws SearchException;

    /**
     * Search for the parent workflow instance.
     *
     * @param rq the ServiceRequest<WorkflowInstanceMsg>.
     * @return the ServiceResponse<WorkflowInstanceMsg>.
     * @throws SearchException
     */
    ServiceResponse<WorkflowInstanceMsg> searchParentWorkflowInstance(ServiceRequest<WorkflowInstanceMsg> rq)
            throws SearchException;
}
