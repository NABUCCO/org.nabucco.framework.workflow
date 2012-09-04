/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.workflow.impl.service.datatype.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.search.QuerySupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;
import org.nabucco.framework.workflow.facade.datatype.definition.search.WorkflowDefinitionSearchParameter;
import org.nabucco.framework.workflow.facade.datatype.definition.search.WorkflowStateSearchParameter;
import org.nabucco.framework.workflow.facade.datatype.instance.search.WorkflowInstanceAssignee;
import org.nabucco.framework.workflow.facade.datatype.instance.search.WorkflowInstanceSearchParameter;
import org.nabucco.framework.workflow.facade.datatype.task.TaskItem;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskListMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.search.TaskSearchRq;

/**
 * MaintainTaskServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SearchTasksServiceHandlerImpl extends SearchTasksServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TaskListMsg searchTasks(TaskSearchRq rq) throws SearchException {

        TaskListMsg rs = new TaskListMsg();

        WorkflowInstanceSearchParameter param = rq.getSearchParameter();

        try {
            List<TaskItem> resultList = this.executeQuery(param);
            rs.getTaskList().addAll(resultList);

        } catch (Exception e) {
            throw new SearchException("Error searching for Tasks.", e);
        }

        return rs;
    }

    /**
     * Execute the complex query with workflow definition parameters.
     * 
     * @param wisp
     *            the workflow instance search parameter
     * 
     * @throws PersistenceException
     *             when the query execution raised an exception
     */
    private List<TaskItem> executeQuery(WorkflowInstanceSearchParameter wisp) throws PersistenceException {

        List<TaskItem> instances = new ArrayList<TaskItem>();

        if (wisp.getDefinitionParameters().isEmpty()) {
            NabuccoQuery<TaskItem> query = this.createQuery(wisp, null);
            this.addInstanceParameters(query, wisp);

            instances.addAll(query.getResultList());
        }

        for (WorkflowDefinitionSearchParameter wdsp : wisp.getDefinitionParameters()) {

            NabuccoQuery<TaskItem> query = this.createQuery(wisp, wdsp);
            this.addInstanceParameters(query, wisp);
            this.addDefinitionParameters(query, wdsp);

            instances.addAll(query.getResultList());
        }

        return instances;
    }

    /**
     * Create the query for single execution.
     * 
     * @param wisp
     *            workflow instance search parameter
     * @param wdsp
     *            workflow definition search parameter
     * 
     * @return the complex query
     * 
     * @throws PersistenceException
     *             when the query is not valid
     */
    private NabuccoQuery<TaskItem> createQuery(WorkflowInstanceSearchParameter wisp,
            WorkflowDefinitionSearchParameter wdsp) throws PersistenceException {

        StringBuilder queryString = new StringBuilder();

        queryString.append("select i from TaskItem i");
        queryString.append(" left outer join fetch i.workflow w");
        queryString.append(" left outer join w.currentEntry e");
        queryString.append(" left outer join w.definition d");
        queryString.append(" left outer join e.state s");

        // Instance

        queryString.append(" where (i.owner = :owner or :owner is null)");
        queryString.append(" and (i.name = :name or :name is null)");
        queryString.append(" and (w.summary like :summary or :summary is null)");

        queryString.append(" and (i.creator = :creator or :creator is null)");
        queryString.append(" and (i.creationTime >= :creationTimeFrom or :creationTimeFrom is null)");
        queryString.append(" and (i.creationTime <= :creationTimeTo or :creationTimeTo is null)");

        queryString.append(" and (i.functionalTypeRefId = :functionaltype or :functionaltype is null)");
        queryString.append(" and (i.priorityRefId = :priority or :priority is null)");
        queryString.append(" and (i.dueDate >= :dueDateFrom or :dueDateFrom is null)");
        queryString.append(" and (i.dueDate <= :dueDateTo or :dueDateTo is null)");

        // Assignee

        queryString.append(" and (i.assignedUser = :assignedUser or :assignedUser is null");
        if (!wisp.getAssignedGroups().isEmpty()) {
            queryString.append(" or i.assignedGroup in ( :assignedGroups )");
        }
        if (!wisp.getAssignedRoles().isEmpty()) {
            queryString.append(" or i.assignedRole in ( :assignedRoles )");
        }
        queryString.append(")");

        // Entry

        queryString.append(" and (e.modifier = :modifier or :modifier is null)");
        queryString.append(" and (e.modificationTime >= :modificationTimeFrom or :modificationTimeFrom is null)");
        queryString.append(" and (e.modificationTime <= :modificationTimeTo or :modificationTimeTo is null)");

        // Definition

        if (wdsp != null) {
            boolean containsStateName = false;
            boolean containsStateType = false;

            for (WorkflowStateSearchParameter stateParam : wdsp.getStateParameters()) {
                if (stateParam.getStateName() != null) {
                    containsStateName = true;
                }
                if (stateParam.getStateType() != null) {
                    containsStateType = true;
                }
            }

            queryString.append(" and (d.name = :definition or :definition is null)");

            if (containsStateName) {
                queryString.append(" and (s.name IN ( :stateName ))");
            }
            if (containsStateType) {
                queryString.append(" and (s.type IN ( :stateType ))");
            }
        }

        return super.getPersistenceManager().createQuery(queryString.toString());
    }

    /**
     * Add the workflow instance search parameters to the query.
     * 
     * @param query
     *            the query to add the parameters
     * @param param
     *            the workflow instance search parameter
     * 
     * @throws PersistenceException
     *             when the parameters cannot be set appropriately
     */
    private void addInstanceParameters(NabuccoQuery<TaskItem> query, WorkflowInstanceSearchParameter param)
            throws PersistenceException {

        Long priority = (param.getPriority() != null) ? param.getPriority().getId() : null;
        Long functionalType = (param.getFunctionaltype() != null) ? param.getFunctionaltype().getId() : null;

        Date dueDate = (param.getDueDateFrom() != null) ? param.getDueDateFrom().getValue() : null;
        Date dueDateTo = (param.getDueDateTo() != null) ? param.getDueDateTo().getValue() : null;

        Name assignedUser = (param.getAssignedUser() != null) ? param.getAssignedUser().getName() : null;

        List<Name> assignedGroups = new ArrayList<Name>();
        for (WorkflowInstanceAssignee group : param.getAssignedGroups()) {
            assignedGroups.add(group.getName());
        }
        List<Name> assignedRoles = new ArrayList<Name>();
        for (WorkflowInstanceAssignee role : param.getAssignedRoles()) {
            assignedRoles.add(role.getName());
        }

        query.setParameter(WorkflowInstanceSearchParameter.OWNER, param.getOwner());
        query.setParameter(WorkflowInstanceSearchParameter.NAME, param.getName());
        query.setParameter(WorkflowInstanceSearchParameter.CREATOR, param.getCreator());
        query.setParameter(WorkflowInstanceSearchParameter.MODIFIER, param.getModifier());
        query.setParameter(WorkflowInstanceSearchParameter.ASSIGNEDUSER, assignedUser);

        if (!assignedGroups.isEmpty()) {
            query.setParameter(WorkflowInstanceSearchParameter.ASSIGNEDGROUPS, assignedGroups);
        }
        if (!assignedRoles.isEmpty()) {
            query.setParameter(WorkflowInstanceSearchParameter.ASSIGNEDROLES, assignedRoles);
        }

        query.setParameter(WorkflowInstanceSearchParameter.SUMMARY, QuerySupport.searchParameter(param.getSummary()));

        query.setParameter(WorkflowInstanceSearchParameter.CREATIONTIMEFROM, param.getCreationTimeFrom());
        query.setParameter(WorkflowInstanceSearchParameter.CREATIONTIMETO, param.getCreationTimeTo());
        query.setParameter(WorkflowInstanceSearchParameter.MODIFICATIONTIMEFROM, param.getModificationTimeFrom());
        query.setParameter(WorkflowInstanceSearchParameter.MODIFICATIONTIMETO, param.getModificationTimeTo());

        query.setParameter(WorkflowInstanceSearchParameter.DUEDATEFROM, dueDate, TemporalType.DATE);
        query.setParameter(WorkflowInstanceSearchParameter.DUEDATETO, dueDateTo, TemporalType.DATE);

        query.setParameter(WorkflowInstanceSearchParameter.PRIORITY, priority);
        query.setParameter(WorkflowInstanceSearchParameter.FUNCTIONALTYPE, functionalType);
    }

    /**
     * Add the workflow definition search parameters to the query.
     * 
     * @param query
     *            the query to add the parameters
     * @param param
     *            the workflow definition search parameter
     * 
     * @throws PersistenceException
     *             when the parameters cannot be set appropriately
     */
    private void addDefinitionParameters(NabuccoQuery<TaskItem> query, WorkflowDefinitionSearchParameter wdsp)
            throws PersistenceException {

        query.setParameter(WorkflowDefinitionSearchParameter.DEFINITION, wdsp.getDefinition());

        this.addStateParameter(query, wdsp);
    }

    /**
     * Add the workflow state search parameters to the query.
     * 
     * @param query
     *            the query to add the parameters
     * @param param
     *            the workflow state search parameter
     * 
     * @throws PersistenceException
     *             when the parameters cannot be set appropriately
     */
    private void addStateParameter(NabuccoQuery<TaskItem> query, WorkflowDefinitionSearchParameter wdsp)
            throws PersistenceException {

        List<Name> stateNames = new ArrayList<Name>();
        List<WorkflowStateType> stateTypes = new ArrayList<WorkflowStateType>();

        for (WorkflowStateSearchParameter wssp : wdsp.getStateParameters()) {
            if (wssp.getStateName() != null) {
                stateNames.add(wssp.getStateName());
            }
            if (wssp.getStateType() != null) {
                stateTypes.add(wssp.getStateType());
            }
        }

        if (!stateNames.isEmpty()) {
            query.setParameter(WorkflowStateSearchParameter.STATENAME, stateNames);
        }
        if (!stateTypes.isEmpty()) {
            query.setParameter(WorkflowStateSearchParameter.STATETYPE, stateTypes);
        }

    }

}
