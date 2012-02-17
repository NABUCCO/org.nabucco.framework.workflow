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
package org.nabucco.framework.workflow.facade.service.definition.maintain;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;

/**
 * MaintainWorkflowDefinition<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public interface MaintainWorkflowDefinition extends Service {

    /**
     * Missing description at method maintainWorkflowDefinition.
     *
     * @param rq the ServiceRequest<WorkflowDefinitionMsg>.
     * @return the ServiceResponse<WorkflowDefinitionMsg>.
     * @throws MaintainException
     */
    ServiceResponse<WorkflowDefinitionMsg> maintainWorkflowDefinition(ServiceRequest<WorkflowDefinitionMsg> rq)
            throws MaintainException;

    /**
     * Missing description at method maintainWorkflowSignal.
     *
     * @param rq the ServiceRequest<WorkflowSignalMsg>.
     * @return the ServiceResponse<WorkflowSignalMsg>.
     * @throws MaintainException
     */
    ServiceResponse<WorkflowSignalMsg> maintainWorkflowSignal(ServiceRequest<WorkflowSignalMsg> rq)
            throws MaintainException;
}
