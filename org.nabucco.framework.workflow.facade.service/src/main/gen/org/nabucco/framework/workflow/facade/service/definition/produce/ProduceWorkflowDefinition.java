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
package org.nabucco.framework.workflow.facade.service.definition.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
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

/**
 * ProduceWorkflowDefinition<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public interface ProduceWorkflowDefinition extends Service {

    /**
     * Creates a new WorkflowDefinition.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<WorkflowDefinitionMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowDefinitionMsg> produceWorkflowDefinition(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException;

    /**
     * Creates a new WorkflowState.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<WorkflowStateMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowStateMsg> produceWorkflowState(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException;

    /**
     * Creates a new WorkflowTransition.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<WorkflowTransitionMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowTransitionMsg> produceWorkflowTransition(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException;

    /**
     * Creates a new WorkflowSignal.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<WorkflowSignalMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowSignalMsg> produceWorkflowSignal(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException;

    /**
     * Creates a new WorkflowTrigger for a given Trigger Type.
     *
     * @param rq the ServiceRequest<ProduceWorkflowTriggerRq>.
     * @return the ServiceResponse<WorkflowTriggerMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowTriggerMsg> produceWorkflowTrigger(ServiceRequest<ProduceWorkflowTriggerRq> rq)
            throws ProduceException;

    /**
     * Creates a new WorkflowCondition for a given Condition Type.
     *
     * @param rq the ServiceRequest<ProduceWorkflowConditionRq>.
     * @return the ServiceResponse<WorkflowConditionMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowConditionMsg> produceWorkflowCondition(ServiceRequest<ProduceWorkflowConditionRq> rq)
            throws ProduceException;

    /**
     * Creates a new WorkflowEffect for a given Effect Type.
     *
     * @param rq the ServiceRequest<ProduceWorkflowEffectRq>.
     * @return the ServiceResponse<WorkflowEffectMsg>.
     * @throws ProduceException
     */
    ServiceResponse<WorkflowEffectMsg> produceWorkflowEffect(ServiceRequest<ProduceWorkflowEffectRq> rq)
            throws ProduceException;
}
