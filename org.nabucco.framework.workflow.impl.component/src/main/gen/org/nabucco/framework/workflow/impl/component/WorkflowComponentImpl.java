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
package org.nabucco.framework.workflow.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.workflow.facade.component.WorkflowComponentLocal;
import org.nabucco.framework.workflow.facade.component.WorkflowComponentRemote;
import org.nabucco.framework.workflow.facade.service.definition.maintain.MaintainWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.definition.produce.ProduceWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.definition.resolve.ResolveWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService;
import org.nabucco.framework.workflow.facade.service.instance.maintain.MaintainWorkflowInstance;
import org.nabucco.framework.workflow.facade.service.instance.produce.ProduceWorkflowInstance;
import org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance;
import org.nabucco.framework.workflow.facade.service.instance.search.SearchWorkflowInstance;

/**
 * WorkflowComponentImpl<p/>Component for defining workflows.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-18
 */
public class WorkflowComponentImpl extends ComponentSupport implements WorkflowComponentLocal, WorkflowComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "WorkflowComponent";

    /** Constructs a new WorkflowComponentImpl instance. */
    public WorkflowComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE,
                ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super
                .lookup(WorkflowComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL, ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public MaintainWorkflowDefinition getMaintainWorkflowDefinitionLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.MAINTAIN_WORKFLOW_DEFINITION_LOCAL,
                MaintainWorkflowDefinition.class);
    }

    @Override
    public MaintainWorkflowDefinition getMaintainWorkflowDefinition() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.MAINTAIN_WORKFLOW_DEFINITION_REMOTE,
                MaintainWorkflowDefinition.class);
    }

    @Override
    public SearchWorkflowDefinition getSearchWorkflowDefinitionLocal() throws ServiceException {
        return super
                .lookup(WorkflowComponentJndiNames.SEARCH_WORKFLOW_DEFINITION_LOCAL, SearchWorkflowDefinition.class);
    }

    @Override
    public SearchWorkflowDefinition getSearchWorkflowDefinition() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.SEARCH_WORKFLOW_DEFINITION_REMOTE,
                SearchWorkflowDefinition.class);
    }

    @Override
    public ProduceWorkflowDefinition getProduceWorkflowDefinitionLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.PRODUCE_WORKFLOW_DEFINITION_LOCAL,
                ProduceWorkflowDefinition.class);
    }

    @Override
    public ProduceWorkflowDefinition getProduceWorkflowDefinition() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.PRODUCE_WORKFLOW_DEFINITION_REMOTE,
                ProduceWorkflowDefinition.class);
    }

    @Override
    public ResolveWorkflowDefinition getResolveWorkflowDefinitionLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.RESOLVE_WORKFLOW_DEFINITION_LOCAL,
                ResolveWorkflowDefinition.class);
    }

    @Override
    public ResolveWorkflowDefinition getResolveWorkflowDefinition() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.RESOLVE_WORKFLOW_DEFINITION_REMOTE,
                ResolveWorkflowDefinition.class);
    }

    @Override
    public MaintainWorkflowInstance getMaintainWorkflowInstanceLocal() throws ServiceException {
        return super
                .lookup(WorkflowComponentJndiNames.MAINTAIN_WORKFLOW_INSTANCE_LOCAL, MaintainWorkflowInstance.class);
    }

    @Override
    public MaintainWorkflowInstance getMaintainWorkflowInstance() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.MAINTAIN_WORKFLOW_INSTANCE_REMOTE,
                MaintainWorkflowInstance.class);
    }

    @Override
    public SearchWorkflowInstance getSearchWorkflowInstanceLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.SEARCH_WORKFLOW_INSTANCE_LOCAL, SearchWorkflowInstance.class);
    }

    @Override
    public SearchWorkflowInstance getSearchWorkflowInstance() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.SEARCH_WORKFLOW_INSTANCE_REMOTE, SearchWorkflowInstance.class);
    }

    @Override
    public ProduceWorkflowInstance getProduceWorkflowInstanceLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.PRODUCE_WORKFLOW_INSTANCE_LOCAL, ProduceWorkflowInstance.class);
    }

    @Override
    public ProduceWorkflowInstance getProduceWorkflowInstance() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.PRODUCE_WORKFLOW_INSTANCE_REMOTE, ProduceWorkflowInstance.class);
    }

    @Override
    public ResolveWorkflowInstance getResolveWorkflowInstanceLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.RESOLVE_WORKFLOW_INSTANCE_LOCAL, ResolveWorkflowInstance.class);
    }

    @Override
    public ResolveWorkflowInstance getResolveWorkflowInstance() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.RESOLVE_WORKFLOW_INSTANCE_REMOTE, ResolveWorkflowInstance.class);
    }

    @Override
    public WorkflowEngineService getWorkflowEngineServiceLocal() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.WORKFLOW_ENGINE_SERVICE_LOCAL, WorkflowEngineService.class);
    }

    @Override
    public WorkflowEngineService getWorkflowEngineService() throws ServiceException {
        return super.lookup(WorkflowComponentJndiNames.WORKFLOW_ENGINE_SERVICE_REMOTE, WorkflowEngineService.class);
    }
}
