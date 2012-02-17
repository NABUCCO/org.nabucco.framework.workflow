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
package org.nabucco.framework.workflow.impl.service.definition.resolve;

import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionComposite;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;

/**
 * ResolveWorkflowDefinitionServiceHandlerImpl
 * 
 * @author Jens Wurm, PRODYNA AG
 */
public class ResolveWorkflowDefinitionServiceHandlerImpl extends ResolveWorkflowDefinitionServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected WorkflowDefinitionListMsg resolveWorkflowDefinition(WorkflowDefinitionListMsg rq) throws ResolveException {

        WorkflowDefinitionListMsg rs = new WorkflowDefinitionListMsg();

        for (WorkflowDefinition workflow : rq.getWorkflowDefinitionList()) {
            try {
                workflow = this.resolveWorkflow(workflow);

                rs.getWorkflowDefinitionList().add(workflow);

            } catch (PersistenceException e) {
                throw new ResolveException("Error resolving WorkflowDefinition with id " + workflow.getId(), e);
            } catch (SerializationException e) {
                throw new ResolveException("Error resolving context of WorkflowDefinition with id " + workflow.getId(),
                        e);
            }
        }

        return rs;
    }

    /**
     * Resolves a single workflow definition.
     * 
     * @param workflow
     *            the workflow to resolve
     * 
     * @return the resolved workflow definition
     * 
     * @throws PersistenceException
     * @throws SerializationException
     */
    private WorkflowDefinition resolveWorkflow(WorkflowDefinition workflow) throws PersistenceException,
            SerializationException {

        workflow = super.getPersistenceManager().find(workflow);

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
