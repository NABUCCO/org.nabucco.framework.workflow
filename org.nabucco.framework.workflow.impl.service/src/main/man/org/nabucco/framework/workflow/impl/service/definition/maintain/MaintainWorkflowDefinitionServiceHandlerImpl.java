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
package org.nabucco.framework.workflow.impl.service.definition.maintain;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateConstraint;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionComposite;
import org.nabucco.framework.workflow.facade.datatype.definition.context.WorkflowContextSerializer;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignalContainer;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.impl.service.definition.maintain.util.WorkflowDefinitionStateVisitor;

/**
 * MaintainWorkflowDefinitionServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MaintainWorkflowDefinitionServiceHandlerImpl extends MaintainWorkflowDefinitionServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public WorkflowDefinitionMsg maintainWorkflowDefinition(WorkflowDefinitionMsg request) throws MaintainException {

        WorkflowDefinition workflow = request.getWorkflow();

        try {

            workflow = this.maintainWorkflow(workflow);

            // Mark all lazy lists as eager!
            workflow.accept(new DatatypeVisitor());

        } catch (PersistenceException pe) {
            throw new MaintainException("Error maintaining WorkflowDefinition with id '" + workflow.getId() + "'.", pe);
        } catch (SerializationException se) {
            throw new MaintainException("Error maintaining context for WorkflowDefinition with id '"
                    + workflow.getId() + "'.", se);
        } catch (VisitorException ve) {
            throw new MaintainException(
                    "Error setting state of WorkflowDefinition with id '" + workflow.getId() + "'.", ve);
        }

        WorkflowDefinitionMsg response = new WorkflowDefinitionMsg();
        response.setWorkflow(workflow);
        return response;
    }

    /**
     * Maintains a workflow definition.
     * 
     * @param workflow
     *            the workflow to maintain
     * 
     * @return the maintained workflow
     * 
     * @throws PersistenceException
     * @throws VisitorException
     * @throws SerializationException
     */
    private WorkflowDefinition maintainWorkflow(WorkflowDefinition workflow) throws PersistenceException,
            VisitorException, SerializationException {

        // Recursively set state DELETED to all child elements.
        if (workflow.getDatatypeState() == DatatypeState.DELETED) {
            workflow.accept(new WorkflowDefinitionStateVisitor(DatatypeState.DELETED));
        }

        this.maintainChildren(workflow);

        workflow = super.getPersistenceManager().persist(workflow);

        return workflow;
    }

    /**
     * Maintain the child elements of a WorkflowDefinition.
     * 
     * @param workflow
     *            the parent workflow
     * 
     * @throws PersistenceException
     * @throws VisitorException
     * @throws SerializationException
     */
    private void maintainChildren(WorkflowDefinition workflow) throws PersistenceException, VisitorException,
            SerializationException {
        for (WorkflowState state : workflow.getStateList()) {
            this.maintainState(state, workflow);
        }

        for (WorkflowTransition transition : workflow.getTransitionList()) {
            this.maintainTransition(transition);
        }

        for (WorkflowSignalContainer container : workflow.getSignalList()) {
            container = super.getPersistenceManager().persist(container);
        }

    }

    /**
     * Maintain a single workflow state.
     * 
     * @param state
     *            the state to maintain
     * @param workflow
     *            the parent workflow
     * 
     * @return the maintained state
     * 
     * @throws PersistenceException
     * @throws VisitorException
     */
    private WorkflowState maintainState(WorkflowState state, WorkflowDefinition workflow) throws PersistenceException,
            VisitorException {

        if (state.getDatatypeState() == DatatypeState.DELETED) {

            Long id = state.getId();

            // Remove transitions referencing on the deleted state!
            for (WorkflowTransition transition : workflow.getTransitionList()) {
                if (transition.getSource().getId() == id || transition.getTarget().getId() == id) {
                    if (transition.getDatatypeState() != DatatypeState.DESTROYED) {
                        transition.setDatatypeState(DatatypeState.DELETED);
                        this.maintainTransition(transition);
                    }
                }
            }

            state = super.getPersistenceManager().persist(state);

            for (WorkflowStateConstraint constraint : state.getConstraintList()) {
                constraint.setDatatypeState(DatatypeState.DELETED);
                super.getPersistenceManager().persist(constraint);
            }

            return state;
        }

        for (WorkflowStateConstraint constraint : state.getConstraintList()) {
            super.getPersistenceManager().persist(constraint);
        }

        return super.getPersistenceManager().persist(state);
    }

    /**
     * Maintains a workflow context.
     * 
     * @param context
     *            the context to maintain
     * 
     * @return the maintained context
     * 
     * @throws PersistenceException
     * @throws SerializationException
     */
    @SuppressWarnings("unused")
    private WorkflowContext maintainContext(WorkflowContext context) throws PersistenceException,
            SerializationException {
        if (context != null) {
            WorkflowContextSerializer.getInstance().serialize(context);
            return super.getPersistenceManager().persist(context);
        }
        return null;
    }

    /**
     * Maintain a single workflow transition.
     * 
     * @param transition
     *            the transition to maintain
     * 
     * @return the maintained transition
     * 
     * @throws PersistenceException
     * @throws VisitorException
     */
    private WorkflowTransition maintainTransition(WorkflowTransition transition) throws PersistenceException,
            VisitorException {

        // Recursively set state DELETED to all child elements.
        if (transition.getDatatypeState() == DatatypeState.DELETED) {
            transition = super.getPersistenceManager().persist(transition);

            transition.accept(new WorkflowDefinitionStateVisitor(DatatypeState.DELETED));
            this.maintainChildren(transition);
            return transition;
        }

        this.maintainChildren(transition);

        transition = super.getPersistenceManager().persist(transition);

        return transition;
    }

    /**
     * Maintain the child elements of a WorkflowTransition.
     * 
     * @param transition
     *            the transition
     * 
     * @throws PersistenceException
     * @throws VisitorException
     */
    private void maintainChildren(WorkflowTransition transition) throws PersistenceException, VisitorException {
        WorkflowTrigger trigger = transition.getTrigger();
        this.maintainTrigger(trigger);

        WorkflowCondition condition = transition.getCondition();
        this.maintainCondition(condition);

        for (WorkflowEffect effect : transition.getEffectList()) {
            this.maintainEffect(effect);
        }
    }

    /**
     * Maintain the workflow trigger.
     * 
     * @param trigger
     *            the trigger to maintain
     * 
     * @return the maintained trigger
     * 
     * @throws PersistenceException
     */
    private WorkflowTrigger maintainTrigger(WorkflowTrigger trigger) throws PersistenceException {

        switch (trigger.getType()) {

        case SIGNAL: {
            SignalTrigger signalTrigger = (SignalTrigger) trigger;
            signalTrigger.setSignal(super.getPersistenceManager().find(signalTrigger.getSignal()));
            break;
        }
        }

        return super.getPersistenceManager().persist(trigger);
    }

    /**
     * Maintains the worklow condition
     * 
     * @param condition
     *            the condition to maintain
     * 
     * @return the maintained condition
     * 
     * @throws PersistenceException
     * @throws VisitorException
     */
    private WorkflowCondition maintainCondition(WorkflowCondition condition) throws PersistenceException,
            VisitorException {

        if (condition == null) {
            return null;
        }

        if (condition instanceof WorkflowConditionComposite) {

            WorkflowConditionComposite composite = (WorkflowConditionComposite) condition;

            if (composite.getDatatypeState() == DatatypeState.DELETED) {

                // Recursively set state DELETED to all child elements.
                composite.accept(new WorkflowDefinitionStateVisitor(DatatypeState.DELETED));
                composite = super.getPersistenceManager().persist(composite);

                for (WorkflowCondition childCondition : composite.getConditionList()) {
                    this.maintainCondition(childCondition);
                }

                return composite;
            }

            for (WorkflowCondition childCondition : composite.getConditionList()) {
                this.maintainCondition(childCondition);
            }
        }

        return super.getPersistenceManager().persist(condition);
    }

    /**
     * Maintain a single workflow effect.
     * 
     * @param effect
     *            the effect to maintain
     * 
     * @return the maintained effect
     * 
     * @throws PersistenceException
     */
    private WorkflowEffect maintainEffect(WorkflowEffect effect) throws PersistenceException {
        return super.getPersistenceManager().persist(effect);
    }
}
