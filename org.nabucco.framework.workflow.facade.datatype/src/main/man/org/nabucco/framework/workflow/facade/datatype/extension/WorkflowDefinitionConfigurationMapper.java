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
package org.nabucco.framework.workflow.facade.datatype.extension;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowConditionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowDefinitionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowSignalExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowStateExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowTransitionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowTriggerExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.InstantiationEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.LogEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.SubWorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionCommentType;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinitionType;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.AssigneeCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.BooleanCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.BooleanOperator;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.GroupCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.InstantiableCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.PermissionCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.RoleCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionComposite;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionType;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.instantiable.InstantiableEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.log.LogEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.subworkflow.SubWorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignalContainer;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTriggerType;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.TimeTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTriggerType;

/**
 * WorkflowDefinitionMapper
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowDefinitionConfigurationMapper {

    /** Singleton instance. */
    private static WorkflowDefinitionConfigurationMapper instance = new WorkflowDefinitionConfigurationMapper();

    /**
     * Private constructor.
     */
    private WorkflowDefinitionConfigurationMapper() {
    }

    /**
     * Singleton access.
     * 
     * @return the WorkflowDefinitionMapper instance.
     */
    public static WorkflowDefinitionConfigurationMapper getInstance() {
        return instance;
    }

    /**
     * Maps a workflow extension configuration to a workflow definition instance.
     * 
     * @param extension
     *            the extension to map
     * 
     * @return the definition
     */
    public WorkflowDefinition mapToDefinition(WorkflowDefinitionExtension extension) {
        WorkflowDefinition definition = new WorkflowDefinition();
        definition.setDatatypeState(DatatypeState.INITIALIZED);
        definition.setName(PropertyLoader.loadProperty(extension.getName()));
        definition.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        definition.setDescription(PropertyLoader.loadProperty(extension.getDescription()));
        definition.setWorkflowType(PropertyLoader.loadProperty(WorkflowDefinitionType.class, extension.getType()));

        for (WorkflowSignalExtension signalExtension : extension.getSignalList()) {
            definition.getSignalList().add(this.createSignal(signalExtension));
        }

        for (WorkflowStateExtension stateExtension : extension.getStateList()) {
            definition.getStateList().add(this.createState(stateExtension));
        }

        for (WorkflowTransitionExtension transitionExtension : extension.getTransitionList()) {
            definition.getTransitionList().add(this.createTransition(transitionExtension, definition));
        }

        return definition;
    }

    /**
     * Create the signal for the given extension.
     * 
     * @param extension
     *            the signal extension
     * 
     * @return the signal container holding the signal
     */
    private WorkflowSignalContainer createSignal(WorkflowSignalExtension extension) {
        WorkflowSignal signal = new WorkflowSignal();
        signal.setDatatypeState(DatatypeState.INITIALIZED);
        signal.setName(PropertyLoader.loadProperty(extension.getName()));
        signal.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        signal.setDescription(PropertyLoader.loadProperty(extension.getDescription()));

        WorkflowSignalContainer container = new WorkflowSignalContainer();
        container.setDatatypeState(DatatypeState.INITIALIZED);
        container.setSignal(signal);
        container.setSignalName(signal.getName());

        return container;
    }

    /**
     * Create the state for the given extension.
     * 
     * @param extension
     *            the state extension
     * 
     * @return the new state
     */
    private WorkflowState createState(WorkflowStateExtension extension) {
        WorkflowState state = new WorkflowState();
        state.setDatatypeState(DatatypeState.INITIALIZED);
        state.setName(PropertyLoader.loadProperty(extension.getName()));
        state.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        state.setDescription(PropertyLoader.loadProperty(extension.getDescription()));
        state.setType(PropertyLoader.loadProperty(WorkflowStateType.class, extension.getType()));

        return state;
    }

    /**
     * Create the transition for the given extension.
     * 
     * @param extension
     *            the transition extension
     * @param definition
     *            the workflow definition
     * 
     * @return the new transition
     */
    private WorkflowTransition createTransition(WorkflowTransitionExtension extension, WorkflowDefinition definition) {
        WorkflowTransition transition = new WorkflowTransition();
        transition.setDatatypeState(DatatypeState.INITIALIZED);
        transition.setName(PropertyLoader.loadProperty(extension.getName()));
        transition.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        transition.setDescription(PropertyLoader.loadProperty(extension.getDescription()));
        transition.setCommentCardinality(PropertyLoader.loadProperty(TransitionCommentType.class,
                extension.getCommentType()));

        transition.setSource(this.getState(definition, extension.getSource()));
        transition.setTarget(this.getState(definition, extension.getTarget()));

        transition.setTrigger(this.createTrigger(extension.getTrigger(), definition));
        transition.setCondition(this.createCondition(extension.getCondition()));

        for (WorkflowEffectExtension effectExtension : extension.getEffectList()) {
            WorkflowEffect effect = this.createEffect(effectExtension);

            if (effect != null) {
                transition.getEffectList().add(effect);
            }
        }

        return transition;
    }

    /**
     * Create the trigger for the given extension.
     * 
     * @param extension
     *            the trigger extension
     * @param definition
     *            the workflow definition
     * 
     * @return the new trigger
     */
    private WorkflowTrigger createTrigger(WorkflowTriggerExtension extension, WorkflowDefinition definition) {

        WorkflowTriggerType type = PropertyLoader.loadProperty(WorkflowTriggerType.class, extension.getType());

        if (type == null) {
            return null;
        }

        WorkflowTrigger trigger;

        switch (type) {

        case SIGNAL: {
            SignalTrigger signalTrigger = new SignalTrigger();
            signalTrigger.setSignal(this.getSignal(definition, extension.getSignal()));
            signalTrigger.setSignalType(SignalTriggerType.USER_INTERFACE);
            trigger = signalTrigger;
            break;
        }

        case TIME: {
            TimeTrigger timeTrigger = new TimeTrigger();
            timeTrigger.setTimer(PropertyLoader.loadProperty(extension.getTimer()));
            trigger = timeTrigger;
            break;
        }

        default: {
            return null;
        }

        }

        trigger.setDatatypeState(DatatypeState.INITIALIZED);
        trigger.setName(PropertyLoader.loadProperty(extension.getName()));
        trigger.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        trigger.setDescription(PropertyLoader.loadProperty(extension.getDescription()));

        return trigger;
    }

    /**
     * Create the condition for the given extension including all child conditions.
     * 
     * @param extension
     *            the condition extension
     * 
     * @return the new condition
     */
    private WorkflowCondition createCondition(WorkflowConditionExtension extension) {

        if (extension == null) {
            return null;
        }

        WorkflowConditionType type = PropertyLoader.loadProperty(WorkflowConditionType.class, extension.getType());

        if (type == null) {
            return null;
        }

        WorkflowCondition condition;

        switch (type) {

        case ASSIGNEE: {
            AssigneeCondition assigneeCondition = new AssigneeCondition();
            condition = assigneeCondition;
            break;
        }

        case GROUP: {
            GroupCondition groupCondition = new GroupCondition();
            groupCondition.setGroupName(PropertyLoader.loadProperty(extension.getValue()));
            condition = groupCondition;
            break;
        }

        case ROLE: {
            RoleCondition roleCondition = new RoleCondition();
            roleCondition.setRoleName(PropertyLoader.loadProperty(extension.getValue()));
            condition = roleCondition;
            break;
        }

        case PERMISSION: {
            PermissionCondition permissionCondition = new PermissionCondition();
            permissionCondition.setPermissionName(PropertyLoader.loadProperty(extension.getValue()));
            condition = permissionCondition;
            break;
        }

        case INSTANTIABLE: {
            InstantiableCondition instantiableCondition = new InstantiableCondition();
            instantiableCondition.setClassName(PropertyLoader.loadProperty(extension.getValue()));
            condition = instantiableCondition;
            break;
        }
        
        case BOOLEAN: {
            BooleanCondition booleanCondition = new BooleanCondition();
            booleanCondition.setOperator(PropertyLoader.loadProperty(BooleanOperator.class, extension.getOperator()));
            condition = booleanCondition;
            break;
        }

        default: {
            return null;
        }

        }

        if (condition instanceof WorkflowConditionComposite) {
            WorkflowConditionComposite composite = (WorkflowConditionComposite) condition;
            for (WorkflowConditionExtension child : extension.getChildren()) {
                composite.getConditionList().add(this.createCondition(child));
            }
        }

        condition.setDatatypeState(DatatypeState.INITIALIZED);
        condition.setName(PropertyLoader.loadProperty(extension.getName()));
        condition.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        condition.setDescription(PropertyLoader.loadProperty(extension.getDescription()));

        return condition;
    }

    /**
     * Create the effect for the given extension.
     * 
     * @param extension
     *            the effect extension
     * @param definition
     *            the workflow definition
     * 
     * @return the new effect
     */
    private WorkflowEffect createEffect(WorkflowEffectExtension extension) {

        WorkflowEffectType type = PropertyLoader.loadProperty(WorkflowEffectType.class, extension.getType());

        if (type == null) {
            return null;
        }

        WorkflowEffect effect;

        switch (type) {

        case LOG: {
            LogEffectExtension logExtension = (LogEffectExtension) extension;

            LogEffect logEffect = new LogEffect();
            logEffect.setMessage(PropertyLoader.loadProperty(logExtension.getMessage()));
            effect = logEffect;

            break;
        }

        case INSTANTIABLE: {
            InstantiationEffectExtension instanceExtension = (InstantiationEffectExtension) extension;

            InstantiableEffect instanceEffect = new InstantiableEffect();
            instanceEffect.setClassName(instanceExtension.getClassName().getValue().getValue());
            effect = instanceEffect;

            break;
        }

        case SUB_WORKFLOW: {
            SubWorkflowEffectExtension subWorkflowExtension = (SubWorkflowEffectExtension) extension;

            SubWorkflowEffect subWorkflowEffect = new SubWorkflowEffect();
            subWorkflowEffect.setSummary(PropertyLoader.loadProperty(subWorkflowExtension.getSummary()));
            subWorkflowEffect.setDefinitionName(PropertyLoader.loadProperty(subWorkflowExtension.getDefinitionName()));
            subWorkflowEffect.setAssignedGroup(PropertyLoader.loadProperty(subWorkflowExtension.getAssignedGroup()));
            subWorkflowEffect.setAssignedUser(PropertyLoader.loadProperty(subWorkflowExtension.getAssignedUser()));
            subWorkflowEffect.setAssignedRole(PropertyLoader.loadProperty(subWorkflowExtension.getAssignedRole()));
            effect = subWorkflowEffect;

            break;
        }

        default: {
            return null;
        }

        }

        effect.setDatatypeState(DatatypeState.INITIALIZED);
        effect.setName(PropertyLoader.loadProperty(extension.getName()));
        effect.setOwner(PropertyLoader.loadProperty(extension.getOwner()));
        effect.setDescription(PropertyLoader.loadProperty(extension.getDescription()));

        return effect;
    }

    /**
     * Retrieves the state for the given state extension-.
     * 
     * @param definition
     *            the workflow definition holding the states
     * @param extension
     *            the state extension to resolve
     * 
     * @return the resolved state
     */
    private WorkflowState getState(WorkflowDefinition definition, WorkflowStateExtension extension) {
        for (WorkflowState state : definition.getStateList()) {
            if (state.getName().getValue().equals(PropertyLoader.loadProperty(extension.getName()))) {
                return state;
            }
        }
        return null;
    }

    /**
     * Retrieves the signal for the given signal extension-.
     * 
     * @param definition
     *            the workflow definition holding the signals
     * @param extension
     *            the signal extension to resolve
     * 
     * @return the resolved signal
     */
    private WorkflowSignal getSignal(WorkflowDefinition definition, WorkflowSignalExtension extension) {

        for (WorkflowSignalContainer container : definition.getSignalList()) {
            WorkflowSignal signal = container.getSignal();
            String signalName = signal.getName().getValue();
            String extensionName = extension.getName().getValue().getValue();

            if (signalName.equals(extensionName)) {
                return signal;
            }
        }

        return null;
    }

}
