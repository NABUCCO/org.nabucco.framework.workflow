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
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.assignee.AssigneeEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.constraint.DynamicConstraintEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.context.ContextEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.modification.ModificationEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.script.ScriptEffect;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowEffectMsg;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowEffectRq;

/**
 * ProduceWorkflowEffectServiceHandlerImpl
 * 
 * @author Silas Schwarz PRODYNA AG
 */
public class ProduceWorkflowEffectServiceHandlerImpl extends ProduceWorkflowEffectServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected WorkflowEffectMsg produceWorkflowEffect(ProduceWorkflowEffectRq request)
            throws ProduceException {
        WorkflowEffectMsg result = new WorkflowEffectMsg();

        switch (request.getEffectType()) {

        case ASSIGNEE:
            result.setEffect(this.createAssigneeEffect());
            break;

        case CONSTRAINT:
            result.setEffect(this.createConstraintEffect());
            break;

        case CONTEXT:
            result.setEffect(this.createContextEffect());
            break;

        case MODIFICATION:
            result.setEffect(this.createModificationEffect());
            break;

        case SCRIPT:
            result.setEffect(this.createScriptEffect());
            break;

        default:
            throw new ProduceException("Cannot produce WorkflowEffect for EffectType ["
                    + request.getEffectType() + "].");
        }

        result.getEffect().setDatatypeState(DatatypeState.INITIALIZED);
        result.getEffect().setDescription(new Description());
        result.getEffect().setName(new Name());
        result.getEffect().setOwner(new Owner());

        return result;
    }

    /**
     * Create a new {@link AssigneeEffect} instance.
     * 
     * @return the new effect
     */
    private WorkflowEffect createAssigneeEffect() {
        AssigneeEffect effect = new AssigneeEffect();
        effect.setNewUser(new Name());
        effect.setNewGroup(new Name());
        effect.setNewRole(new Name());
        return effect;
    }

    /**
     * Create a new {@link DynamicConstraintEffect} instance.
     * 
     * @return the new effect
     */
    private WorkflowEffect createConstraintEffect() {
        DynamicConstraintEffect effect = new DynamicConstraintEffect();
        return effect;
    }

    /**
     * Create a new {@link ContextEffect} instance.
     * 
     * @return the new effect
     */
    private WorkflowEffect createContextEffect() {
        ContextEffect effect = new ContextEffect();
        return effect;
    }

    /**
     * Create a new {@link ModificationEffect} instance.
     * 
     * @return the new effect
     */
    private WorkflowEffect createModificationEffect() {
        ModificationEffect effect = new ModificationEffect();
        effect.setPath(new Path());
        return effect;
    }

    /**
     * Create a new {@link ScriptEffect} instance.
     * 
     * @return the new effect
     */
    private WorkflowEffect createScriptEffect() {
        ScriptEffect effect = new ScriptEffect();
        effect.setScriptName(new Name());
        return effect;
    }

}
