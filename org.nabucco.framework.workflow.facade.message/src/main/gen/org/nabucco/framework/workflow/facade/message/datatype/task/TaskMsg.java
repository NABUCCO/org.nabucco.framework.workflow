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
package org.nabucco.framework.workflow.facade.message.datatype.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.task.TaskItem;

/**
 * TaskMsg<p/>Message containing a list of tasks<p/>
 *
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class TaskMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String TASKITEM = "taskItem";

    public static final String REFERENCINGDATATYPE = "referencingDatatype";

    /** The Task */
    private TaskItem taskItem;

    /** The referencing datatype (can be set in aspect) */
    private NabuccoDatatype referencingDatatype;

    /** Constructs a new TaskMsg instance. */
    public TaskMsg() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TASKITEM, PropertyDescriptorSupport.createDatatype(TASKITEM, TaskItem.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(REFERENCINGDATATYPE, PropertyDescriptorSupport.createDatatype(REFERENCINGDATATYPE,
                NabuccoDatatype.class, 1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TaskMsg.getPropertyDescriptor(TASKITEM), this.getTaskItem()));
        properties.add(super.createProperty(TaskMsg.getPropertyDescriptor(REFERENCINGDATATYPE),
                this.getReferencingDatatype()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TASKITEM) && (property.getType() == TaskItem.class))) {
            this.setTaskItem(((TaskItem) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REFERENCINGDATATYPE) && (property.getType() == NabuccoDatatype.class))) {
            this.setReferencingDatatype(((NabuccoDatatype) property.getInstance()));
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
        final TaskMsg other = ((TaskMsg) obj);
        if ((this.taskItem == null)) {
            if ((other.taskItem != null))
                return false;
        } else if ((!this.taskItem.equals(other.taskItem)))
            return false;
        if ((this.referencingDatatype == null)) {
            if ((other.referencingDatatype != null))
                return false;
        } else if ((!this.referencingDatatype.equals(other.referencingDatatype)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.taskItem == null) ? 0 : this.taskItem.hashCode()));
        result = ((PRIME * result) + ((this.referencingDatatype == null) ? 0 : this.referencingDatatype.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The Task
     *
     * @return the TaskItem.
     */
    public TaskItem getTaskItem() {
        return this.taskItem;
    }

    /**
     * The Task
     *
     * @param taskItem the TaskItem.
     */
    public void setTaskItem(TaskItem taskItem) {
        this.taskItem = taskItem;
    }

    /**
     * The referencing datatype (can be set in aspect)
     *
     * @return the NabuccoDatatype.
     */
    public NabuccoDatatype getReferencingDatatype() {
        return this.referencingDatatype;
    }

    /**
     * The referencing datatype (can be set in aspect)
     *
     * @param referencingDatatype the NabuccoDatatype.
     */
    public void setReferencingDatatype(NabuccoDatatype referencingDatatype) {
        this.referencingDatatype = referencingDatatype;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TaskMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TaskMsg.class).getAllProperties();
    }
}
