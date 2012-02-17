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
package org.nabucco.framework.workflow.ui.rcp.edit.workflow.model;

import java.io.Serializable;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.plugin.base.component.edit.model.EditViewModel;
import org.nabucco.framework.plugin.base.logging.Loggable;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinitionType;

/**
 * WorkflowDefinitionEditViewModel<p/>Edit view for datatype WorkflowDefintion<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-25
 */
public class WorkflowDefinitionEditViewModel extends EditViewModel implements Loggable {

    private WorkflowDefinition workflow;

    public static final String PROPERTY_WORKFLOW_NAME = "workflowName";

    public static final String PROPERTY_WORKFLOW_DESCRIPTION = "workflowDescription";

    public static final String PROPERTY_WORKFLOW_WORKFLOWTYPE = "workflowWorkflowType";

    /** Constructs a new WorkflowDefinitionEditViewModel instance. */
    public WorkflowDefinitionEditViewModel() {
        super();
    }

    /**
     * Getter for the ID.
     *
     * @return the String.
     */
    public String getID() {
        return "org.nabucco.framework.workflow.ui.rcp.edit.workflow.model.WorkflowDefinitionEditViewModel";
    }

    /**
     * Getter for the Values.
     *
     * @return the Map<String, Serializable>.
     */
    public Map<String, Serializable> getValues() {
        Map<String, Serializable> result = super.getValues();
        result.put(PROPERTY_WORKFLOW_WORKFLOWTYPE, this.getWorkflowWorkflowType());
        result.put(PROPERTY_WORKFLOW_NAME, this.getWorkflowName());
        result.put(PROPERTY_WORKFLOW_DESCRIPTION, this.getWorkflowDescription());
        return result;
    }

    /**
     * Setter for the Workflow.
     *
     * @param newValue the WorkflowDefinition.
     */
    public void setWorkflow(WorkflowDefinition newValue) {
        WorkflowDefinition oldValue = this.workflow;
        this.workflow = newValue;
        this.updateProperty(PROPERTY_WORKFLOW_DESCRIPTION, ((oldValue != null) ? oldValue.getDescription() : ""),
                ((newValue != null) ? newValue.getDescription() : ""));
        this.updateProperty(PROPERTY_WORKFLOW_NAME, ((oldValue != null) ? oldValue.getName() : ""),
                ((newValue != null) ? newValue.getName() : ""));
        this.updateProperty(PROPERTY_WORKFLOW_WORKFLOWTYPE, ((oldValue != null) ? oldValue.getWorkflowType() : ""),
                ((newValue != null) ? newValue.getWorkflowType() : ""));
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
     * Getter for the WorkflowWorkflowType.
     *
     * @return the String.
     */
    public String getWorkflowWorkflowType() {
        if (((workflow == null) || (workflow.getWorkflowType() == null))) {
            return "";
        }
        return workflow.getWorkflowType().name();
    }

    /**
     * Setter for the WorkflowWorkflowType.
     *
     * @param newWorkflowType the String.
     */
    public void setWorkflowWorkflowType(final String newWorkflowType) {
        String oldVal = "";
        if ((this.workflow.getWorkflowType() != null)) {
            oldVal = this.workflow.getWorkflowType().name();
        }
        this.workflow.setWorkflowType(WorkflowDefinitionType.valueOf(newWorkflowType));
        this.updateProperty(PROPERTY_WORKFLOW_WORKFLOWTYPE, oldVal, newWorkflowType);
        if (((!oldVal.equals(newWorkflowType)) && workflow.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            workflow.setDatatypeState(DatatypeState.MODIFIED);
        }
    }
}
