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
package org.nabucco.framework.workflow.impl.service.definition.search;

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
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateListMsg;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowDefinitionSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowSignalSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowStateSearchRq;
import org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition;

/**
 * SearchWorkflowDefinitionImpl<p/>Workflow definition search service.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class SearchWorkflowDefinitionImpl extends ServiceSupport implements SearchWorkflowDefinition {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchWorkflowDefinition";

    private static Map<String, String[]> ASPECTS;

    private SearchWorkflowDefinitionServiceHandler searchWorkflowDefinitionServiceHandler;

    private SearchWorkflowStateServiceHandler searchWorkflowStateServiceHandler;

    private SearchWorkflowSignalServiceHandler searchWorkflowSignalServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchWorkflowDefinitionImpl instance. */
    public SearchWorkflowDefinitionImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchWorkflowDefinitionServiceHandler = injector.inject(SearchWorkflowDefinitionServiceHandler.getId());
        if ((this.searchWorkflowDefinitionServiceHandler != null)) {
            this.searchWorkflowDefinitionServiceHandler.setPersistenceManager(persistenceManager);
            this.searchWorkflowDefinitionServiceHandler.setLogger(super.getLogger());
        }
        this.searchWorkflowStateServiceHandler = injector.inject(SearchWorkflowStateServiceHandler.getId());
        if ((this.searchWorkflowStateServiceHandler != null)) {
            this.searchWorkflowStateServiceHandler.setPersistenceManager(persistenceManager);
            this.searchWorkflowStateServiceHandler.setLogger(super.getLogger());
        }
        this.searchWorkflowSignalServiceHandler = injector.inject(SearchWorkflowSignalServiceHandler.getId());
        if ((this.searchWorkflowSignalServiceHandler != null)) {
            this.searchWorkflowSignalServiceHandler.setPersistenceManager(persistenceManager);
            this.searchWorkflowSignalServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("searchWorkflowDefinition", new String[] { "org.nabucco.aspect.caching" });
            ASPECTS.put("searchWorkflowState", new String[] { "org.nabucco.aspect.caching" });
            ASPECTS.put("searchWorkflowSignal", new String[] { "org.nabucco.aspect.caching" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowDefinitionListMsg> searchWorkflowDefinition(
            ServiceRequest<WorkflowDefinitionSearchRq> rq) throws SearchException {
        if ((this.searchWorkflowDefinitionServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchWorkflowDefinition().");
            throw new InjectionException("No service implementation configured for searchWorkflowDefinition().");
        }
        ServiceResponse<WorkflowDefinitionListMsg> rs;
        this.searchWorkflowDefinitionServiceHandler.init();
        rs = this.searchWorkflowDefinitionServiceHandler.invoke(rq);
        this.searchWorkflowDefinitionServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowStateListMsg> searchWorkflowState(ServiceRequest<WorkflowStateSearchRq> rq)
            throws SearchException {
        if ((this.searchWorkflowStateServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchWorkflowState().");
            throw new InjectionException("No service implementation configured for searchWorkflowState().");
        }
        ServiceResponse<WorkflowStateListMsg> rs;
        this.searchWorkflowStateServiceHandler.init();
        rs = this.searchWorkflowStateServiceHandler.invoke(rq);
        this.searchWorkflowStateServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowSignalListMsg> searchWorkflowSignal(ServiceRequest<WorkflowSignalSearchRq> rq)
            throws SearchException {
        if ((this.searchWorkflowSignalServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchWorkflowSignal().");
            throw new InjectionException("No service implementation configured for searchWorkflowSignal().");
        }
        ServiceResponse<WorkflowSignalListMsg> rs;
        this.searchWorkflowSignalServiceHandler.init();
        rs = this.searchWorkflowSignalServiceHandler.invoke(rq);
        this.searchWorkflowSignalServiceHandler.finish();
        return rs;
    }
}
