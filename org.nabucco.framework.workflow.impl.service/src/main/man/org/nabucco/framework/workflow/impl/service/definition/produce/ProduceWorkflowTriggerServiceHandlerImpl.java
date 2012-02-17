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
package org.nabucco.framework.workflow.impl.service.definition.produce;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTriggerType;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.TimeTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowTriggerMsg;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowTriggerRq;

/**
 * ProduceWorkflowTriggerServiceHandlerImpl
 * 
 * @author Silas Schwarz PRODYNA AG
 */
public class ProduceWorkflowTriggerServiceHandlerImpl extends ProduceWorkflowTriggerServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected WorkflowTriggerMsg produceWorkflowTrigger(ProduceWorkflowTriggerRq request)
            throws ProduceException {

        WorkflowTriggerMsg response = new WorkflowTriggerMsg();

        switch (request.getTriggerType()) {

        case SIGNAL:
            response.setTrigger(this.createSignalTrigger(request.getSignalTriggerType()));
            break;

        case TIME:
            response.setTrigger(this.createTimeTrigger());
            break;

        default:
            throw new ProduceException("Cannot produce WorkflowTrigger for TriggerType ["
                    + request.getTriggerType() + "].");
        }

        response.getTrigger().setDatatypeState(DatatypeState.INITIALIZED);
        response.getTrigger().setName(new Name());
        response.getTrigger().setOwner(new Owner());
        response.getTrigger().setDescription(new Description());
        return response;
    }

    /**
     * Create a new {@link SignalTrigger} instance.
     * 
     * @return the new trigger
     */
    private WorkflowTrigger createSignalTrigger(SignalTriggerType type) {
        SignalTrigger trigger = new SignalTrigger();
        trigger.setSignalType(type);
        return trigger;
    }

    /**
     * Create a new {@link TimeTrigger} instance.
     * 
     * @return the new trigger
     */
    private WorkflowTrigger createTimeTrigger() {
        TimeTrigger trigger = new TimeTrigger();
        trigger.setTimer(new Name());
        return trigger;
    }

}
