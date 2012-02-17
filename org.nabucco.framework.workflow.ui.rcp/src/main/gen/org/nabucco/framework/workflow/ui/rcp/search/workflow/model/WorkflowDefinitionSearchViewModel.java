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
package org.nabucco.framework.workflow.ui.rcp.search.workflow.model;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchParameter;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchViewModel;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;

/**
 * WorkflowDefinitionSearchViewModel<p/>Search View for WorkflowDefinitions.<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-02-27
 */
public class WorkflowDefinitionSearchViewModel extends NabuccoComponentSearchViewModel<WorkflowDefinition> implements
        NabuccoComponentSearchParameter {

    public static final String ID = "org.nabucco.framework.workflow.ui.search.workflow.WorkflowDefinitionSearchViewModel";

    private WorkflowDefinition workflow;

    public static final String PROPERTY_WORKFLOW_DESCRIPTION = "workflowDescription";

    public static final String PROPERTY_WORKFLOW_NAME = "workflowName";

    public static String TITLE = (ID + "Title");

    /**
     * Constructs a new WorkflowDefinitionSearchViewModel instance.
     *
     * @param viewId the String.
     */
    public WorkflowDefinitionSearchViewModel(String viewId) {
        super();
        correspondingListView = viewId;
        this.workflow = new WorkflowDefinition();
    }

    @Override
    public String getSearchModelId() {
        return searchModelId;
    }

    @Override
    public NabuccoComponentSearchParameter getSearchParameter() {
        return this;
    }

    /**
     * Getter for the Workflow.
     *
     * @return the WorkflowDefinition.
     */
    public WorkflowDefinition getWorkflow() {
        return this.workflow;
    }

    /**
     * Setter for the WorkflowDescription.
     *
     * @param newDescription the String.
     */
    public void setWorkflowDescription(String newDescription) {
        if (((workflow != null) && (workflow.getDescription() == null))) {
            Description description = new Description();
            workflow.setDescription(description);
        }
        String oldVal = workflow.getDescription().getValue();
        workflow.getDescription().setValue(newDescription);
        this.updateProperty(PROPERTY_WORKFLOW_DESCRIPTION, oldVal, newDescription);
        if (((!oldVal.equals(newDescription)) && workflow.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            workflow.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the WorkflowDescription.
     *
     * @return the String.
     */
    public String getWorkflowDescription() {
        if ((((workflow == null) || (workflow.getDescription() == null)) || (workflow.getDescription().getValue() == null))) {
            return "";
        }
        return workflow.getDescription().getValue();
    }

    /**
     * Setter for the WorkflowName.
     *
     * @param newName the String.
     */
    public void setWorkflowName(String newName) {
        if (((workflow != null) && (workflow.getName() == null))) {
            Name name = new Name();
            workflow.setName(name);
        }
        String oldVal = workflow.getName().getValue();
        workflow.getName().setValue(newName);
        this.updateProperty(PROPERTY_WORKFLOW_NAME, oldVal, newName);
        if (((!oldVal.equals(newName)) && workflow.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            workflow.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the WorkflowName.
     *
     * @return the String.
     */
    public String getWorkflowName() {
        if ((((workflow == null) || (workflow.getName() == null)) || (workflow.getName().getValue() == null))) {
            return "";
        }
        return workflow.getName().getValue();
    }

    @Override
    public String getId() {
        return WorkflowDefinitionSearchViewModel.ID;
    }
}
