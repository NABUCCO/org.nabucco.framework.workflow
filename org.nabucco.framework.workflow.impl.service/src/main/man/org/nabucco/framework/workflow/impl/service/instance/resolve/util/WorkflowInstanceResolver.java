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
package org.nabucco.framework.workflow.impl.service.instance.resolve.util;

import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionComposite;
import org.nabucco.framework.workflow.facade.datatype.definition.context.WorkflowContextSerializer;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * WorkflowInstanceResolver
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowInstanceResolver {

    private PersistenceManager persistenceManager;

    /**
     * Creates a new {@link WorkflowInstanceResolver} instance.
     * 
     * @param persistenceManager
     *            the persistence manager
     */
    public WorkflowInstanceResolver(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    /**
     * Resolve the workflow instance.
     * 
     * @param instance
     *            the instance to resolve
     * 
     * @return the resolved instance
     * 
     * @throws ResolveException
     *             when the instance cannot be resolved
     */
    public WorkflowInstance resolveInstance(WorkflowInstance instance) throws ResolveException {
        try {
            instance = this.persistenceManager.find(instance);
        } catch (PersistenceException e) {
            throw new ResolveException("Cannot resolve WorkflowInstance with id " + instance.getId() + ".", e);
        }

        this.resolveWorkflow(instance.getDefinition());
        this.resolveEntries(instance);
        this.resolveContext(instance.getContext());
        this.resolveSubInstances(instance);

        return instance;
    }

    /**
     * Resolves the referenced WorkflowState of the given WorkflowInstance.
     * 
     * @param entry
     *            the entry to resolve
     * 
     * @throws ResolveException
     */
    private void resolveEntries(WorkflowInstance instance) throws ResolveException {

        WorkflowInstanceEntry currentEntry = instance.getCurrentEntry();
        this.resolveContext(currentEntry.getContext());

        for (WorkflowInstanceEntry entry : instance.getEntryList()) {
            this.resolveContext(entry.getContext());
        }
    }

    /**
     * Deserializes the serialized context.
     * 
     * @param context
     *            the context to resolve
     * 
     * @throws ResolveException
     *             when the deserialization fails
     */
    private void resolveContext(WorkflowContext context) throws ResolveException {
        if (context == null) {
            return;
        }

        try {
            WorkflowContextSerializer.getInstance().deserialize(context);
        } catch (SerializationException e) {
            throw new ResolveException("Error deserializing WorkflowInstanceContext.", e);
        }
    }

    /**
     * Resolve the sub instances
     * 
     * @param instance
     *            the parent instance to resolve
     */
    private void resolveSubInstances(WorkflowInstance instance) {
        instance.getSubInstances().size();
    }

    /**
     * Resolves a single workflow definition.
     * 
     * @param workflow
     *            the workflow to resolve
     * 
     * @return the resolved workflow definition
     * 
     * @throws ResolveException
     */
    private WorkflowDefinition resolveWorkflow(WorkflowDefinition workflow) throws ResolveException {

        try {
            workflow = this.persistenceManager.find(workflow);
        } catch (PersistenceException e) {
            throw new ResolveException("Cannot resolve WorkflowDefinition with id " + workflow.getId() + ".", e);
        }

        for (WorkflowState state : workflow.getStateList()) {
            state.getConstraintList().size();
        }

        for (WorkflowTransition transition : workflow.getTransitionList()) {
            transition.getEffectList().size();
            this.resolveCondition(transition.getCondition());
        }

        workflow.getSignalList().size();

        return workflow;
    }

    /**
     * Resolve the workflow condition tree.
     * 
     * @param condition
     *            the condition to resolve deep
     */
    private void resolveCondition(WorkflowCondition condition) {
        if (condition instanceof WorkflowConditionComposite) {
            WorkflowConditionComposite composite = (WorkflowConditionComposite) condition;
            for (WorkflowCondition child : composite.getConditionList()) {
                this.resolveCondition(child);
            }
        }
    }
}
