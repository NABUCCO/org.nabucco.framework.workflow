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
 * TaskMaintainRqMsg<p/>Message containing a resolved tasks and datatype that it references<p/>
 *
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class TaskMaintainRqMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String TASKITEM = "taskItem";

    public static final String SOURCEDATATYPE = "sourceDatatype";

    /** The Task */
    private TaskItem taskItem;

    /** Datatype that relates to the maintaining task */
    private NabuccoDatatype sourceDatatype;

    /** Constructs a new TaskMaintainRqMsg instance. */
    public TaskMaintainRqMsg() {
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
        propertyMap.put(SOURCEDATATYPE, PropertyDescriptorSupport.createDatatype(SOURCEDATATYPE, NabuccoDatatype.class,
                1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TaskMaintainRqMsg.getPropertyDescriptor(TASKITEM), this.getTaskItem()));
        properties.add(super.createProperty(TaskMaintainRqMsg.getPropertyDescriptor(SOURCEDATATYPE),
                this.getSourceDatatype()));
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
        } else if ((property.getName().equals(SOURCEDATATYPE) && (property.getType() == NabuccoDatatype.class))) {
            this.setSourceDatatype(((NabuccoDatatype) property.getInstance()));
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
        final TaskMaintainRqMsg other = ((TaskMaintainRqMsg) obj);
        if ((this.taskItem == null)) {
            if ((other.taskItem != null))
                return false;
        } else if ((!this.taskItem.equals(other.taskItem)))
            return false;
        if ((this.sourceDatatype == null)) {
            if ((other.sourceDatatype != null))
                return false;
        } else if ((!this.sourceDatatype.equals(other.sourceDatatype)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.taskItem == null) ? 0 : this.taskItem.hashCode()));
        result = ((PRIME * result) + ((this.sourceDatatype == null) ? 0 : this.sourceDatatype.hashCode()));
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
     * Datatype that relates to the maintaining task
     *
     * @return the NabuccoDatatype.
     */
    public NabuccoDatatype getSourceDatatype() {
        return this.sourceDatatype;
    }

    /**
     * Datatype that relates to the maintaining task
     *
     * @param sourceDatatype the NabuccoDatatype.
     */
    public void setSourceDatatype(NabuccoDatatype sourceDatatype) {
        this.sourceDatatype = sourceDatatype;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TaskMaintainRqMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TaskMaintainRqMsg.class).getAllProperties();
    }
}
