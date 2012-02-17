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
package org.nabucco.framework.workflow.impl.service.instance.search;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;

/**
 * SearchParentWorkflowInstanceServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SearchParentWorkflowInstanceServiceHandlerImpl extends SearchParentWorkflowInstanceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY = "select p from WorkflowInstance p inner join p.subInstancesJPA c where c.id = :id";

    @Override
    protected WorkflowInstanceMsg searchParentWorkflowInstance(WorkflowInstanceMsg rq) throws SearchException {

        WorkflowInstance instance = rq.getWorkflowInstance();

        try {
            NabuccoQuery<WorkflowInstance> query = super.getPersistenceManager().createQuery(QUERY);
            query.setParameter("id", instance.getId());

            WorkflowInstance parentInstance = query.getSingleResult();

            WorkflowInstanceMsg rs = new WorkflowInstanceMsg();
            rs.setWorkflowInstance(parentInstance);
            return rs;

        } catch (PersistenceException e) {
            throw new SearchException("Cannot find parent workflow instance for child '" + instance.getSummary() + "'.", e);
        }
    }
}
