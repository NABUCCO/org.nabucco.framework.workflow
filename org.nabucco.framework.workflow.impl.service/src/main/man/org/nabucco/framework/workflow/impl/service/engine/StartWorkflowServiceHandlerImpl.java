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
package org.nabucco.framework.workflow.impl.service.engine;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowResult;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.impl.service.engine.util.WorkflowFacade;
import org.nabucco.framework.workflow.impl.service.engine.util.signal.TransitionFilter;

/**
 * StartWorkflowServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class StartWorkflowServiceHandlerImpl extends StartWorkflowServiceHandler {

    private static final long serialVersionUID = 1L;

    private WorkflowFacade facade;

    @Override
    protected WorkflowResultRs startWorkflow(InitWorkflowRq rq) throws WorkflowException {
        this.facade = WorkflowFacade.newInstance(super.getContext());

        WorkflowContext context = rq.getContext();

        WorkflowResult result = new WorkflowResult();

        WorkflowInstance instance = this.createInstance(rq);
        result.setInstance(instance);

        TransitionFilter transitionFilter = new TransitionFilter(instance, context, super.getContext());
        List<TransitionParameter> nextTransitions = transitionFilter.filterNextTransitions(instance);
        result.getNextTransitions().addAll(nextTransitions);

        WorkflowResultRs rs = new WorkflowResultRs();
        rs.setResult(result);

        return rs;
    }

    /**
     * Produce a new WorkflowInstance and maintain it to the DB.
     * 
     * @param request
     *            the request message holding the necessary instance parameters
     * 
     * @return the new created workflow instance
     * 
     * @throws WorkflowException
     *             when the instance cannot be created
     */
    private WorkflowInstance createInstance(InitWorkflowRq request) throws WorkflowException {

        WorkflowDefinition definition = request.getWorkflow();
        WorkflowContext context = request.getContext();

        if (definition == null || definition.getId() == null) {
            throw new WorkflowException("Cannot start workflow execution for non-persistent WorkflowDefinition.");
        }

        try {
            WorkflowInstance instance = this.facade.produceInstance(definition);
            instance.setContext(context);

            WorkflowState state = this.findInitialState(definition);
            WorkflowInstanceEntry entry = this.createEntry(state);
            entry.setComment(request.getComment());

            if (request.getOwner() != null && request.getOwner().getValue() != null) {
                instance.setOwner(request.getOwner());
            }

            instance.setCurrentEntry(entry);
            instance.getEntryList().add(entry);

            instance.setSummary(request.getSummary());
            instance.setDescription(request.getDescription());

            instance.setAssignedUser(request.getAssignedUser());
            instance.setAssignedRole(request.getAssignedRole());
            instance.setAssignedGroup(request.getAssignedGroup());

            instance.setFunctionalType(request.getFunctionalType());
            instance.setPriority(request.getPriority());
            instance.setDueDate(request.getDueDate());

            instance = this.facade.maintainInstance(instance);

            return instance;
        } catch (ServiceException se) {
            throw new WorkflowException("Error creating new instance for workflow "
                    + String.valueOf(definition.getName()) + " with id '" + definition.getId() + "'.");
        }
    }

    /**
     * Find the initial state of the workflow.
     * 
     * @param definition
     *            the workflow definition
     * 
     * @return the initial state
     * 
     * @throws WorkflowException
     *             when no initial state is found
     */
    private WorkflowState findInitialState(WorkflowDefinition definition) throws WorkflowException {
        for (WorkflowState state : definition.getStateList()) {
            if (state.getType() == WorkflowStateType.START) {
                return state;
            }
        }

        throw new WorkflowException("No Start-State defined for WorkflowDefinition "
                + String.valueOf(definition.getName()) + " with id '" + definition.getId() + "'.");
    }

    /**
     * Create the initial WorkflowInstanceEntry.
     * 
     * @param state
     *            the current state
     * 
     * @return the initial entry
     * 
     * @throws WorkflowException
     *             when the entry cannot be produced
     */
    private WorkflowInstanceEntry createEntry(WorkflowState state) throws WorkflowException {
        if (state == null || state.getId() == null) {
            throw new WorkflowException("Cannot start workflow execution for non-persistent WorkflowState.");
        }

        try {
            WorkflowInstanceEntry entry = this.facade.produceEntry(state);

            return entry;
        } catch (ServiceException se) {
            throw new WorkflowException("Error creating new instance entry for state "
                    + String.valueOf(state.getName()) + " with id '" + state.getId() + "'.");
        }
    }

}
