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
package org.nabucco.framework.workflow.facade.service.definition.search;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateListMsg;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowDefinitionSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowSignalSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowStateSearchRq;

/**
 * SearchWorkflowDefinition<p/>Workflow definition search service.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public interface SearchWorkflowDefinition extends Service {

    /**
     * Missing description at method searchWorkflowDefinition.
     *
     * @param rq the ServiceRequest<WorkflowDefinitionSearchRq>.
     * @return the ServiceResponse<WorkflowDefinitionListMsg>.
     * @throws SearchException
     */
    ServiceResponse<WorkflowDefinitionListMsg> searchWorkflowDefinition(ServiceRequest<WorkflowDefinitionSearchRq> rq)
            throws SearchException;

    /**
     * Missing description at method searchWorkflowState.
     *
     * @param rq the ServiceRequest<WorkflowStateSearchRq>.
     * @return the ServiceResponse<WorkflowStateListMsg>.
     * @throws SearchException
     */
    ServiceResponse<WorkflowStateListMsg> searchWorkflowState(ServiceRequest<WorkflowStateSearchRq> rq)
            throws SearchException;

    /**
     * Missing description at method searchWorkflowSignal.
     *
     * @param rq the ServiceRequest<WorkflowSignalSearchRq>.
     * @return the ServiceResponse<WorkflowSignalListMsg>.
     * @throws SearchException
     */
    ServiceResponse<WorkflowSignalListMsg> searchWorkflowSignal(ServiceRequest<WorkflowSignalSearchRq> rq)
            throws SearchException;
}
