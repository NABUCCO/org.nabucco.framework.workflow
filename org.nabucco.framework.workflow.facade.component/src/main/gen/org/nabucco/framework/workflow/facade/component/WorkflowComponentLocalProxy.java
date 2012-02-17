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
package org.nabucco.framework.workflow.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;
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
 * WorkflowComponentLocalProxy<p/>Component for defining workflows.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-18
 */
public class WorkflowComponentLocalProxy implements WorkflowComponent {

    private static final long serialVersionUID = 1L;

    private final WorkflowComponentLocal delegate;

    /**
     * Constructs a new WorkflowComponentLocalProxy instance.
     *
     * @param delegate the WorkflowComponentLocal.
     */
    public WorkflowComponentLocalProxy(WorkflowComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public MaintainWorkflowDefinition getMaintainWorkflowDefinition() throws ServiceException {
        return this.delegate.getMaintainWorkflowDefinitionLocal();
    }

    @Override
    public SearchWorkflowDefinition getSearchWorkflowDefinition() throws ServiceException {
        return this.delegate.getSearchWorkflowDefinitionLocal();
    }

    @Override
    public ProduceWorkflowDefinition getProduceWorkflowDefinition() throws ServiceException {
        return this.delegate.getProduceWorkflowDefinitionLocal();
    }

    @Override
    public ResolveWorkflowDefinition getResolveWorkflowDefinition() throws ServiceException {
        return this.delegate.getResolveWorkflowDefinitionLocal();
    }

    @Override
    public MaintainWorkflowInstance getMaintainWorkflowInstance() throws ServiceException {
        return this.delegate.getMaintainWorkflowInstanceLocal();
    }

    @Override
    public SearchWorkflowInstance getSearchWorkflowInstance() throws ServiceException {
        return this.delegate.getSearchWorkflowInstanceLocal();
    }

    @Override
    public ProduceWorkflowInstance getProduceWorkflowInstance() throws ServiceException {
        return this.delegate.getProduceWorkflowInstanceLocal();
    }

    @Override
    public ResolveWorkflowInstance getResolveWorkflowInstance() throws ServiceException {
        return this.delegate.getResolveWorkflowInstanceLocal();
    }

    @Override
    public WorkflowEngineService getWorkflowEngineService() throws ServiceException {
        return this.delegate.getWorkflowEngineServiceLocal();
    }
}
