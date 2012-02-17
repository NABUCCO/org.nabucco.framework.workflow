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
 * WorkflowComponentLocal<p/>Component for defining workflows.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-18
 */
public interface WorkflowComponentLocal extends WorkflowComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the MaintainWorkflowDefinitionLocal.
     *
     * @return the MaintainWorkflowDefinition.
     * @throws ServiceException
     */
    MaintainWorkflowDefinition getMaintainWorkflowDefinitionLocal() throws ServiceException;

    /**
     * Getter for the SearchWorkflowDefinitionLocal.
     *
     * @return the SearchWorkflowDefinition.
     * @throws ServiceException
     */
    SearchWorkflowDefinition getSearchWorkflowDefinitionLocal() throws ServiceException;

    /**
     * Getter for the ProduceWorkflowDefinitionLocal.
     *
     * @return the ProduceWorkflowDefinition.
     * @throws ServiceException
     */
    ProduceWorkflowDefinition getProduceWorkflowDefinitionLocal() throws ServiceException;

    /**
     * Getter for the ResolveWorkflowDefinitionLocal.
     *
     * @return the ResolveWorkflowDefinition.
     * @throws ServiceException
     */
    ResolveWorkflowDefinition getResolveWorkflowDefinitionLocal() throws ServiceException;

    /**
     * Getter for the MaintainWorkflowInstanceLocal.
     *
     * @return the MaintainWorkflowInstance.
     * @throws ServiceException
     */
    MaintainWorkflowInstance getMaintainWorkflowInstanceLocal() throws ServiceException;

    /**
     * Getter for the SearchWorkflowInstanceLocal.
     *
     * @return the SearchWorkflowInstance.
     * @throws ServiceException
     */
    SearchWorkflowInstance getSearchWorkflowInstanceLocal() throws ServiceException;

    /**
     * Getter for the ProduceWorkflowInstanceLocal.
     *
     * @return the ProduceWorkflowInstance.
     * @throws ServiceException
     */
    ProduceWorkflowInstance getProduceWorkflowInstanceLocal() throws ServiceException;

    /**
     * Getter for the ResolveWorkflowInstanceLocal.
     *
     * @return the ResolveWorkflowInstance.
     * @throws ServiceException
     */
    ResolveWorkflowInstance getResolveWorkflowInstanceLocal() throws ServiceException;

    /**
     * Getter for the WorkflowEngineServiceLocal.
     *
     * @return the WorkflowEngineService.
     * @throws ServiceException
     */
    WorkflowEngineService getWorkflowEngineServiceLocal() throws ServiceException;
}
