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
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceListMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.message.instance.search.WorkflowInstanceSearchRq;
import org.nabucco.framework.workflow.facade.service.instance.search.SearchWorkflowInstance;

/**
 * SearchWorkflowInstanceImpl<p/>Workflow instance search service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-28
 */
public class SearchWorkflowInstanceImpl extends ServiceSupport implements SearchWorkflowInstance {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchWorkflowInstance";

    private static Map<String, String[]> ASPECTS;

    private SearchWorkflowInstanceServiceHandler searchWorkflowInstanceServiceHandler;

    private SearchParentWorkflowInstanceServiceHandler searchParentWorkflowInstanceServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchWorkflowInstanceImpl instance. */
    public SearchWorkflowInstanceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchWorkflowInstanceServiceHandler = injector.inject(SearchWorkflowInstanceServiceHandler.getId());
        if ((this.searchWorkflowInstanceServiceHandler != null)) {
            this.searchWorkflowInstanceServiceHandler.setPersistenceManager(persistenceManager);
            this.searchWorkflowInstanceServiceHandler.setLogger(super.getLogger());
        }
        this.searchParentWorkflowInstanceServiceHandler = injector.inject(SearchParentWorkflowInstanceServiceHandler
                .getId());
        if ((this.searchParentWorkflowInstanceServiceHandler != null)) {
            this.searchParentWorkflowInstanceServiceHandler.setPersistenceManager(persistenceManager);
            this.searchParentWorkflowInstanceServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("searchWorkflowInstance", NO_ASPECTS);
            ASPECTS.put("searchParentWorkflowInstance", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowInstanceListMsg> searchWorkflowInstance(ServiceRequest<WorkflowInstanceSearchRq> rq)
            throws SearchException {
        if ((this.searchWorkflowInstanceServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchWorkflowInstance().");
            throw new InjectionException("No service implementation configured for searchWorkflowInstance().");
        }
        ServiceResponse<WorkflowInstanceListMsg> rs;
        this.searchWorkflowInstanceServiceHandler.init();
        rs = this.searchWorkflowInstanceServiceHandler.invoke(rq);
        this.searchWorkflowInstanceServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowInstanceMsg> searchParentWorkflowInstance(ServiceRequest<WorkflowInstanceMsg> rq)
            throws SearchException {
        if ((this.searchParentWorkflowInstanceServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchParentWorkflowInstance().");
            throw new InjectionException("No service implementation configured for searchParentWorkflowInstance().");
        }
        ServiceResponse<WorkflowInstanceMsg> rs;
        this.searchParentWorkflowInstanceServiceHandler.init();
        rs = this.searchParentWorkflowInstanceServiceHandler.invoke(rq);
        this.searchParentWorkflowInstanceServiceHandler.finish();
        return rs;
    }
}
