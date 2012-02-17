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
package org.nabucco.framework.workflow.impl.service.crosscutting;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.workflow.InitWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;
import org.nabucco.framework.workflow.facade.component.WorkflowComponentLocator;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowResult;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowDefinitionSearchRq;
import org.nabucco.framework.workflow.facade.service.definition.resolve.ResolveWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService;
import org.nabucco.framework.workflow.impl.service.crosscutting.util.WorkflowContextSupport;

/**
 * InitWorkflowServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InitWorkflowServiceHandlerImpl extends InitWorkflowServiceHandler {

    private static final long serialVersionUID = 1L;

    private WorkflowComponent component;

    @Override
    protected WorkflowResultRs initWorkflow(InitWorkflowRq rq) throws WorkflowException {

        Name name = rq.getWorkflowName();

        try {
            this.component = WorkflowComponentLocator.getInstance().getComponent();

            WorkflowResult result = this.startWorkflow(rq);

            WorkflowResultRs rs;
            rs = new org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs();
            rs.setWorkflowName(name);
            rs.setInstance(result.getInstance());
            rs.setInstanceId(new Identifier(result.getInstance().getId()));
            rs.getNextTransitions().addAll(result.getNextTransitions());
            rs.setStateName(result.getInstance().getCurrentEntry().getState().getName());
            rs.getConstraintList().addAll(result.getInstance().getCurrentEntry().getState().getConstraintList());

            return rs;

        } catch (ConnectionException ce) {
            throw new WorkflowException("Error locating WorkflowComponent from JNDI.", ce);
        } catch (ServiceException se) {
            throw new WorkflowException("Error initializing new workflow with name " + name + ".", se);
        } catch (Exception e) {
            throw new WorkflowException("Error initializing new workflow with name " + name + ".", e);
        }

    }

    /**
     * Start a new workflow instance for a workflow with the given name.
     * 
     * @param name
     *            name of the workflow to start
     * @param context
     *            the workflow context
     * @param dueDate
     *            the instance due date
     * @param functionalType
     *            the instance functional type
     * @param priority
     *            the instance priority
     * 
     * @return the workflow result
     * 
     * @throws ServiceException
     *             when the workflow cannot be started
     */
    private WorkflowResult startWorkflow(InitWorkflowRq request) throws ServiceException {

        Name name = request.getWorkflowName();

        WorkflowDefinition workflow = this.findWorkflow(name);

        WorkflowEngineService engineService = this.component.getWorkflowEngineService();
        ServiceRequest<org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq> rq = new ServiceRequest<org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq>(
                super.getContext());

        org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq requestMessage = new org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq();

        requestMessage.setWorkflow(workflow);

        if (request.getContext() != null) {
            requestMessage.setContext(WorkflowContextSupport.createContext(request.getContext()));
        }

        requestMessage.setSummary(request.getSummary());
        requestMessage.setComment(request.getComment());
        requestMessage.setDescription(request.getDescription());

        requestMessage.setAssignedGroup(request.getAssignedGroup());
        requestMessage.setAssignedUser(request.getAssignedUser());
        requestMessage.setAssignedRole(request.getAssignedRole());

        requestMessage.setDueDate(request.getDueDate());
        requestMessage.setPriority(request.getPriority());
        requestMessage.setFunctionalType(request.getFunctionalType());

        rq.setRequestMessage(requestMessage);

        ServiceResponse<org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs> rs = engineService
                .startWorkflow(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new WorkflowException("Cannot start workflow with name " + name + ".");
        }

        WorkflowResult result = rs.getResponseMessage().getResult();

        if (result == null || result.getInstance() == null) {
            throw new WorkflowException("Cannot start workflow with name " + name + ".");
        }

        return result;
    }

    /**
     * Search the workflow definition with the given name.
     * 
     * @param name
     *            name of the workflow to find
     * 
     * @return the loaded workflow definition
     * 
     * @throws ServiceException
     *             when the search did not end expected or no workflow was found
     */
    private WorkflowDefinition findWorkflow(Name name) throws ServiceException {

        ServiceRequest<WorkflowDefinitionSearchRq> rq = new ServiceRequest<WorkflowDefinitionSearchRq>(
                super.getContext());

        WorkflowDefinitionSearchRq msg = new WorkflowDefinitionSearchRq();
        msg.setName(name);
        rq.setRequestMessage(msg);

        SearchWorkflowDefinition searchService = this.component.getSearchWorkflowDefinition();
        ServiceResponse<WorkflowDefinitionListMsg> rs = searchService.searchWorkflowDefinition(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new WorkflowException("Cannot find workflow definition with name " + name + ".");
        }

        WorkflowDefinition workflow = rs.getResponseMessage().getWorkflowDefinitionList().first();

        if (workflow == null) {
            throw new WorkflowException("Cannot find workflow definition with name " + name + ".");
        }

        return this.resolveWorkflow(workflow);
    }

    /**
     * Search the workflow definition with the given name.
     * 
     * @param name
     *            name of the workflow to find
     * 
     * @return the loaded workflow definition
     * 
     * @throws ServiceException
     *             when the search did not end expected or no workflow was found
     */
    private WorkflowDefinition resolveWorkflow(WorkflowDefinition workflow) throws ServiceException {

        ServiceRequest<WorkflowDefinitionListMsg> rq = new ServiceRequest<WorkflowDefinitionListMsg>(super.getContext());

        WorkflowDefinitionListMsg msg = new WorkflowDefinitionListMsg();
        msg.getWorkflowDefinitionList().add(workflow);
        rq.setRequestMessage(msg);

        ResolveWorkflowDefinition resolveService = this.component.getResolveWorkflowDefinition();
        ServiceResponse<WorkflowDefinitionListMsg> rs = resolveService.resolveWorkflowDefinition(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new WorkflowException("Cannot resolve workflow definition with name " + workflow.getName() + ".");
        }

        WorkflowDefinition definition = rs.getResponseMessage().getWorkflowDefinitionList().first();

        if (definition == null) {
            throw new WorkflowException("Cannot resolve workflow definition with name " + workflow.getName() + ".");
        }

        return definition;
    }

}
