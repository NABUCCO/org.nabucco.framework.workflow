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
package org.nabucco.framework.workflow.impl.service.definition.search;

import java.util.List;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateListMsg;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowStateSearchRq;

/**
 * SearchWorkflowStateServiceHandlerImpl
 * 
 * @author Stefanie Feld, PRODYNA AG
 */
public class SearchWorkflowStateServiceHandlerImpl extends SearchWorkflowStateServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "name";

    private static final String PROPERTY_DESCRIPTION = "description";

    private static final String PROPERTY_OWNER = "owner";

    private static final String PROPERTY_TYPE = "type";

    private static final String QUERY = createQuery();

    @Override
    protected WorkflowStateListMsg searchWorkflowState(WorkflowStateSearchRq request) throws SearchException {

        try {
            WorkflowStateListMsg response = new WorkflowStateListMsg();

            NabuccoQuery<WorkflowState> query = super.getPersistenceManager().createQuery(QUERY);
            query.setParameter(PROPERTY_NAME, request.getName());
            query.setParameter(PROPERTY_OWNER, request.getOwner());
            query.setParameter(PROPERTY_TYPE, request.getType());
            query.setParameter(PROPERTY_DESCRIPTION, request.getDescription());

            List<WorkflowState> resultList = query.getResultList();

            for (WorkflowState wState : resultList) {
                response.getWorkflowStateList().add(wState);
            }

            super.getPersistenceManager().clear();
            return response;

        } catch (Exception e) {
            throw new SearchException("Error searching for WorkflowState.", e);
        }
    }

    /**
     * Create the query string.
     * 
     * @return the query as string
     */
    private static String createQuery() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select s from WorkflowState s");
        queryString.append(" where (s.name = :name or :name is null)");
        queryString.append(" and (s.owner = :owner or :owner is null)");
        queryString.append(" and (s.type = :type or :type is null)");
        queryString.append(" and (s.description = :description or :description is null)");
        return queryString.toString();
    }

}
