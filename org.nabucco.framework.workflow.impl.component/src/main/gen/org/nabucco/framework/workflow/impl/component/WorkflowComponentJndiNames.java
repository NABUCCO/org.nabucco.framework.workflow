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
package org.nabucco.framework.workflow.impl.component;

/**
 * WorkflowComponentJndiNames<p/>Component for defining workflows.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-18
 */
public interface WorkflowComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.component.QueryFilterService/remote";

    final String MAINTAIN_WORKFLOW_DEFINITION_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.maintain.MaintainWorkflowDefinition/local";

    final String MAINTAIN_WORKFLOW_DEFINITION_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.maintain.MaintainWorkflowDefinition/remote";

    final String SEARCH_WORKFLOW_DEFINITION_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition/local";

    final String SEARCH_WORKFLOW_DEFINITION_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition/remote";

    final String PRODUCE_WORKFLOW_DEFINITION_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.produce.ProduceWorkflowDefinition/local";

    final String PRODUCE_WORKFLOW_DEFINITION_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.produce.ProduceWorkflowDefinition/remote";

    final String RESOLVE_WORKFLOW_DEFINITION_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.resolve.ResolveWorkflowDefinition/local";

    final String RESOLVE_WORKFLOW_DEFINITION_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.definition.resolve.ResolveWorkflowDefinition/remote";

    final String MAINTAIN_WORKFLOW_INSTANCE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.maintain.MaintainWorkflowInstance/local";

    final String MAINTAIN_WORKFLOW_INSTANCE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.maintain.MaintainWorkflowInstance/remote";

    final String SEARCH_WORKFLOW_INSTANCE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.search.SearchWorkflowInstance/local";

    final String SEARCH_WORKFLOW_INSTANCE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.search.SearchWorkflowInstance/remote";

    final String PRODUCE_WORKFLOW_INSTANCE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.produce.ProduceWorkflowInstance/local";

    final String PRODUCE_WORKFLOW_INSTANCE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.produce.ProduceWorkflowInstance/remote";

    final String RESOLVE_WORKFLOW_INSTANCE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance/local";

    final String RESOLVE_WORKFLOW_INSTANCE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance/remote";

    final String WORKFLOW_ENGINE_SERVICE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService/local";

    final String WORKFLOW_ENGINE_SERVICE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService/remote";

    final String SEARCH_WORKFLOW_DATATYPE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.search.SearchWorkflowDatatype/local";

    final String SEARCH_WORKFLOW_DATATYPE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.search.SearchWorkflowDatatype/remote";

    final String RESOLVE_WORKFLOW_DATATYPE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.resolve.ResolveWorkflowDatatype/local";

    final String RESOLVE_WORKFLOW_DATATYPE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.resolve.ResolveWorkflowDatatype/remote";

    final String PRODUCE_WORKFLOW_DATATYPE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.produce.ProduceWorkflowDatatype/local";

    final String PRODUCE_WORKFLOW_DATATYPE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.produce.ProduceWorkflowDatatype/remote";

    final String MAINTAIN_WORKFLOW_DATATYPE_LOCAL = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.maintain.MaintainWorkflowDatatype/local";

    final String MAINTAIN_WORKFLOW_DATATYPE_REMOTE = "nabucco/org.nabucco.framework.workflow/org.nabucco.framework.workflow.facade.service.datatype.maintain.MaintainWorkflowDatatype/remote";
}
