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
package org.nabucco.framework.workflow.facade.datatype.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.task.Task;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.task.TaskItemStatus;

/**
 * TaskItem<p/>The task that can be<p/>
 *
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-02-08
 */
public class TaskItem extends Task implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final TaskItemStatus STATUS_DEFAULT = TaskItemStatus.CREATED;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m1,1;" };

    public static final String WORKFLOW = "workflow";

    public static final String STATUS = "status";

    /** The workflow instance to for the task */
    private WorkflowInstance workflow;

    /** The status of the task item */
    private TaskItemStatus status;

    private Long functionalTypeRefId;

    private Long priorityRefId;

    /** Constructs a new TaskItem instance. */
    public TaskItem() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        status = STATUS_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the TaskItem.
     */
    protected void cloneObject(TaskItem clone) {
        super.cloneObject(clone);
        if ((this.getWorkflow() != null)) {
            clone.setWorkflow(this.getWorkflow().cloneObject());
        }
        clone.setStatus(this.getStatus());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(Task.class).getPropertyMap());
        propertyMap.put(WORKFLOW, PropertyDescriptorSupport.createDatatype(WORKFLOW, WorkflowInstance.class, 14,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(STATUS, PropertyDescriptorSupport.createEnumeration(STATUS, TaskItemStatus.class, 15,
                PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TaskItem.getPropertyDescriptor(WORKFLOW), this.getWorkflow(), null));
        properties.add(super.createProperty(TaskItem.getPropertyDescriptor(STATUS), this.getStatus(), null));
        properties.add(super.createProperty(TaskItem.getPropertyDescriptor(FUNCTIONALTYPE), this.getFunctionalType(),
                this.functionalTypeRefId));
        properties.add(super.createProperty(TaskItem.getPropertyDescriptor(PRIORITY), this.getPriority(),
                this.priorityRefId));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WORKFLOW) && (property.getType() == WorkflowInstance.class))) {
            this.setWorkflow(((WorkflowInstance) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATUS) && (property.getType() == TaskItemStatus.class))) {
            this.setStatus(((TaskItemStatus) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final TaskItem other = ((TaskItem) obj);
        if ((this.workflow == null)) {
            if ((other.workflow != null))
                return false;
        } else if ((!this.workflow.equals(other.workflow)))
            return false;
        if ((this.status == null)) {
            if ((other.status != null))
                return false;
        } else if ((!this.status.equals(other.status)))
            return false;
        if ((this.functionalTypeRefId == null)) {
            if ((other.functionalTypeRefId != null))
                return false;
        } else if ((!this.functionalTypeRefId.equals(other.functionalTypeRefId)))
            return false;
        if ((this.priorityRefId == null)) {
            if ((other.priorityRefId != null))
                return false;
        } else if ((!this.priorityRefId.equals(other.priorityRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.workflow == null) ? 0 : this.workflow.hashCode()));
        result = ((PRIME * result) + ((this.status == null) ? 0 : this.status.hashCode()));
        result = ((PRIME * result) + ((this.functionalTypeRefId == null) ? 0 : this.functionalTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.priorityRefId == null) ? 0 : this.priorityRefId.hashCode()));
        return result;
    }

    @Override
    public TaskItem cloneObject() {
        TaskItem clone = new TaskItem();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The workflow instance to for the task
     *
     * @param workflow the WorkflowInstance.
     */
    public void setWorkflow(WorkflowInstance workflow) {
        this.workflow = workflow;
    }

    /**
     * The workflow instance to for the task
     *
     * @return the WorkflowInstance.
     */
    public WorkflowInstance getWorkflow() {
        return this.workflow;
    }

    /**
     * The status of the task item
     *
     * @return the TaskItemStatus.
     */
    public TaskItemStatus getStatus() {
        return this.status;
    }

    /**
     * The status of the task item
     *
     * @param status the TaskItemStatus.
     */
    public void setStatus(TaskItemStatus status) {
        this.status = status;
    }

    /**
     * The status of the task item
     *
     * @param status the String.
     */
    public void setStatus(String status) {
        if ((status == null)) {
            this.status = null;
        } else {
            this.status = TaskItemStatus.valueOf(status);
        }
    }

    /**
     * Getter for the FunctionalTypeRefId.
     *
     * @return the Long.
     */
    public Long getFunctionalTypeRefId() {
        return this.functionalTypeRefId;
    }

    /**
     * Setter for the FunctionalTypeRefId.
     *
     * @param functionalTypeRefId the Long.
     */
    public void setFunctionalTypeRefId(Long functionalTypeRefId) {
        this.functionalTypeRefId = functionalTypeRefId;
    }

    /**
     * Setter for the FunctionalType.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        super.setFunctionalType(functionalType);
        if ((functionalType != null)) {
            this.setFunctionalTypeRefId(functionalType.getId());
        } else {
            this.setFunctionalTypeRefId(null);
        }
    }

    /**
     * Getter for the PriorityRefId.
     *
     * @return the Long.
     */
    public Long getPriorityRefId() {
        return this.priorityRefId;
    }

    /**
     * Setter for the PriorityRefId.
     *
     * @param priorityRefId the Long.
     */
    public void setPriorityRefId(Long priorityRefId) {
        this.priorityRefId = priorityRefId;
    }

    /**
     * Setter for the Priority.
     *
     * @param priority the Code.
     */
    public void setPriority(Code priority) {
        super.setPriority(priority);
        if ((priority != null)) {
            this.setPriorityRefId(priority.getId());
        } else {
            this.setPriorityRefId(null);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TaskItem.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TaskItem.class).getAllProperties();
    }
}
