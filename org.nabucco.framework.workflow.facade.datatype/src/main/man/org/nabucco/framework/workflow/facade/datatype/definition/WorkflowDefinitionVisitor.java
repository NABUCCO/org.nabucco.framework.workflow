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
package org.nabucco.framework.workflow.facade.datatype.definition;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;

/**
 * WorkflowDefinitionVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowDefinitionVisitor extends DatatypeVisitor {

    @Override
    public final void visit(Datatype datatype) throws VisitorException {

        super.visit(datatype);

        if (datatype instanceof WorkflowDefinition) {
            this.visit((WorkflowDefinition) datatype);
        }
        if (datatype instanceof WorkflowState) {
            this.visit((WorkflowState) datatype);
        }
        if (datatype instanceof WorkflowTransition) {
            this.visit((WorkflowTransition) datatype);
        }
        if (datatype instanceof WorkflowTrigger) {
            this.visit((WorkflowTrigger) datatype);
        }
        if (datatype instanceof WorkflowCondition) {
            this.visit((WorkflowCondition) datatype);
        }
        if (datatype instanceof WorkflowEffect) {
            this.visit((WorkflowEffect) datatype);
        }
    }

    @Override
    public final void visit(Basetype basetype) throws VisitorException {
    }

    /**
     * Visit a workflow definition.
     * 
     * @param workflow
     *            the workflow definition to visit
     */
    public void visit(WorkflowDefinition workflow) {
    }

    /**
     * Visit a workflow transition.
     * 
     * @param workflow
     *            the workflow transition to visit
     */
    public void visit(WorkflowTransition transition) {
    }

    /**
     * Visit a workflow state.
     * 
     * @param workflow
     *            the workflow state to visit
     */
    public void visit(WorkflowState state) {
    }

    /**
     * Visit a workflow trigger.
     * 
     * @param workflow
     *            the workflow trigger to visit
     */
    public void visit(WorkflowTrigger trigger) {
    }

    /**
     * Visit a workflow condition.
     * 
     * @param workflow
     *            the workflow condition to visit
     */
    public void visit(WorkflowCondition condition) {
    }

    /**
     * Visit a workflow effect.
     * 
     * @param workflow
     *            the workflow effect to visit
     */
    public void visit(WorkflowEffect effect) {
    }

}
