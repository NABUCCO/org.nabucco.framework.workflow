/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.workflow.impl.service.datatype.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskListMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.search.TaskSearchRq;
import org.nabucco.framework.workflow.facade.service.datatype.search.SearchWorkflowDatatype;

/**
 * SearchWorkflowDatatypeImpl<p/>Search service for workflow Datatypes<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class SearchWorkflowDatatypeImpl extends ServiceSupport implements SearchWorkflowDatatype {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchWorkflowDatatype";

    private static Map<String, String[]> ASPECTS;

    private SearchTasksServiceHandler searchTasksServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchWorkflowDatatypeImpl instance. */
    public SearchWorkflowDatatypeImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchTasksServiceHandler = injector.inject(SearchTasksServiceHandler.getId());
        if ((this.searchTasksServiceHandler != null)) {
            this.searchTasksServiceHandler.setPersistenceManager(persistenceManager);
            this.searchTasksServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("searchTasks", new String[] { "org.nabucco.aspect.caching" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TaskListMsg> searchTasks(ServiceRequest<TaskSearchRq> rq) throws SearchException {
        if ((this.searchTasksServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchTasks().");
            throw new InjectionException("No service implementation configured for searchTasks().");
        }
        ServiceResponse<TaskListMsg> rs;
        this.searchTasksServiceHandler.init();
        rs = this.searchTasksServiceHandler.invoke(rq);
        this.searchTasksServiceHandler.finish();
        return rs;
    }
}
