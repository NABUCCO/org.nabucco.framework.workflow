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
package org.nabucco.framework.workflow.facade.component;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.workflow.facade.service.datatype.maintain.MaintainWorkflowDatatype;
import org.nabucco.framework.workflow.facade.service.datatype.produce.ProduceWorkflowDatatype;
import org.nabucco.framework.workflow.facade.service.datatype.resolve.ResolveWorkflowDatatype;
import org.nabucco.framework.workflow.facade.service.datatype.search.SearchWorkflowDatatype;
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
 * WorkflowComponent<p/>Component for defining workflows.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-18
 */
public interface WorkflowComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.framework.workflow";

    final String COMPONENT_PREFIX = "work";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.framework.workflow.facade.component.WorkflowComponent");

    /**
     * Getter for the MaintainWorkflowDefinition.
     *
     * @return the MaintainWorkflowDefinition.
     * @throws ServiceException
     */
    MaintainWorkflowDefinition getMaintainWorkflowDefinition() throws ServiceException;

    /**
     * Getter for the SearchWorkflowDefinition.
     *
     * @return the SearchWorkflowDefinition.
     * @throws ServiceException
     */
    SearchWorkflowDefinition getSearchWorkflowDefinition() throws ServiceException;

    /**
     * Getter for the ProduceWorkflowDefinition.
     *
     * @return the ProduceWorkflowDefinition.
     * @throws ServiceException
     */
    ProduceWorkflowDefinition getProduceWorkflowDefinition() throws ServiceException;

    /**
     * Getter for the ResolveWorkflowDefinition.
     *
     * @return the ResolveWorkflowDefinition.
     * @throws ServiceException
     */
    ResolveWorkflowDefinition getResolveWorkflowDefinition() throws ServiceException;

    /**
     * Getter for the MaintainWorkflowInstance.
     *
     * @return the MaintainWorkflowInstance.
     * @throws ServiceException
     */
    MaintainWorkflowInstance getMaintainWorkflowInstance() throws ServiceException;

    /**
     * Getter for the SearchWorkflowInstance.
     *
     * @return the SearchWorkflowInstance.
     * @throws ServiceException
     */
    SearchWorkflowInstance getSearchWorkflowInstance() throws ServiceException;

    /**
     * Getter for the ProduceWorkflowInstance.
     *
     * @return the ProduceWorkflowInstance.
     * @throws ServiceException
     */
    ProduceWorkflowInstance getProduceWorkflowInstance() throws ServiceException;

    /**
     * Getter for the ResolveWorkflowInstance.
     *
     * @return the ResolveWorkflowInstance.
     * @throws ServiceException
     */
    ResolveWorkflowInstance getResolveWorkflowInstance() throws ServiceException;

    /**
     * Getter for the WorkflowEngineService.
     *
     * @return the WorkflowEngineService.
     * @throws ServiceException
     */
    WorkflowEngineService getWorkflowEngineService() throws ServiceException;

    /**
     * Getter for the SearchWorkflowDatatype.
     *
     * @return the SearchWorkflowDatatype.
     * @throws ServiceException
     */
    SearchWorkflowDatatype getSearchWorkflowDatatype() throws ServiceException;

    /**
     * Getter for the ResolveWorkflowDatatype.
     *
     * @return the ResolveWorkflowDatatype.
     * @throws ServiceException
     */
    ResolveWorkflowDatatype getResolveWorkflowDatatype() throws ServiceException;

    /**
     * Getter for the ProduceWorkflowDatatype.
     *
     * @return the ProduceWorkflowDatatype.
     * @throws ServiceException
     */
    ProduceWorkflowDatatype getProduceWorkflowDatatype() throws ServiceException;

    /**
     * Getter for the MaintainWorkflowDatatype.
     *
     * @return the MaintainWorkflowDatatype.
     * @throws ServiceException
     */
    MaintainWorkflowDatatype getMaintainWorkflowDatatype() throws ServiceException;
}
