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
package org.nabucco.framework.workflow.facade.datatype.definition.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;

/**
 * WorkflowStateSearchParameter<p/>The search parameter for searching workflow states.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-05-08
 */
public class WorkflowStateSearchParameter extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowStateType STATETYPE_DEFAULT = WorkflowStateType.NORMAL;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;", "m0,1;" };

    public static final String STATENAME = "stateName";

    public static final String STATETYPE = "stateType";

    /** Name of the workflow state to search for. */
    private Name stateName;

    /** Type of the workflow state to search for. */
    private WorkflowStateType stateType;

    /** Constructs a new WorkflowStateSearchParameter instance. */
    public WorkflowStateSearchParameter() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        stateType = STATETYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowStateSearchParameter.
     */
    protected void cloneObject(WorkflowStateSearchParameter clone) {
        super.cloneObject(clone);
        if ((this.getStateName() != null)) {
            clone.setStateName(this.getStateName().cloneObject());
        }
        clone.setStateType(this.getStateType());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(STATENAME,
                PropertyDescriptorSupport.createBasetype(STATENAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(STATETYPE, PropertyDescriptorSupport.createEnumeration(STATETYPE, WorkflowStateType.class, 1,
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
        properties.add(super.createProperty(WorkflowStateSearchParameter.getPropertyDescriptor(STATENAME),
                this.stateName, null));
        properties.add(super.createProperty(WorkflowStateSearchParameter.getPropertyDescriptor(STATETYPE),
                this.getStateType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(STATENAME) && (property.getType() == Name.class))) {
            this.setStateName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATETYPE) && (property.getType() == WorkflowStateType.class))) {
            this.setStateType(((WorkflowStateType) property.getInstance()));
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
        final WorkflowStateSearchParameter other = ((WorkflowStateSearchParameter) obj);
        if ((this.stateName == null)) {
            if ((other.stateName != null))
                return false;
        } else if ((!this.stateName.equals(other.stateName)))
            return false;
        if ((this.stateType == null)) {
            if ((other.stateType != null))
                return false;
        } else if ((!this.stateType.equals(other.stateType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.stateName == null) ? 0 : this.stateName.hashCode()));
        result = ((PRIME * result) + ((this.stateType == null) ? 0 : this.stateType.hashCode()));
        return result;
    }

    @Override
    public WorkflowStateSearchParameter cloneObject() {
        WorkflowStateSearchParameter clone = new WorkflowStateSearchParameter();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the workflow state to search for.
     *
     * @return the Name.
     */
    public Name getStateName() {
        return this.stateName;
    }

    /**
     * Name of the workflow state to search for.
     *
     * @param stateName the Name.
     */
    public void setStateName(Name stateName) {
        this.stateName = stateName;
    }

    /**
     * Name of the workflow state to search for.
     *
     * @param stateName the String.
     */
    public void setStateName(String stateName) {
        if ((this.stateName == null)) {
            if ((stateName == null)) {
                return;
            }
            this.stateName = new Name();
        }
        this.stateName.setValue(stateName);
    }

    /**
     * Type of the workflow state to search for.
     *
     * @return the WorkflowStateType.
     */
    public WorkflowStateType getStateType() {
        return this.stateType;
    }

    /**
     * Type of the workflow state to search for.
     *
     * @param stateType the WorkflowStateType.
     */
    public void setStateType(WorkflowStateType stateType) {
        this.stateType = stateType;
    }

    /**
     * Type of the workflow state to search for.
     *
     * @param stateType the String.
     */
    public void setStateType(String stateType) {
        if ((stateType == null)) {
            this.stateType = null;
        } else {
            this.stateType = WorkflowStateType.valueOf(stateType);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowStateSearchParameter.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowStateSearchParameter.class).getAllProperties();
    }
}
