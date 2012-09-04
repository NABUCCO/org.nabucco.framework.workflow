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
package org.nabucco.framework.workflow.facade.datatype.definition.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionType;

/**
 * PermissionCondition<p/>Condition for Authorization Permission<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class PermissionCondition extends WorkflowConditionComponent implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowConditionType CONDITIONTYPE_DEFAULT = WorkflowConditionType.PERMISSION;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;" };

    public static final String PERMISSIONNAME = "permissionName";

    /** Name of the required permission. */
    private Name permissionName;

    /** Constructs a new PermissionCondition instance. */
    public PermissionCondition() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        conditionType = CONDITIONTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the PermissionCondition.
     */
    protected void cloneObject(PermissionCondition clone) {
        super.cloneObject(clone);
        clone.setConditionType(this.getConditionType());
        if ((this.getPermissionName() != null)) {
            clone.setPermissionName(this.getPermissionName().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowConditionComponent.class).getPropertyMap());
        propertyMap
                .put(PERMISSIONNAME, PropertyDescriptorSupport.createBasetype(PERMISSIONNAME, Name.class, 7,
                        PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PermissionCondition.getPropertyDescriptor(PERMISSIONNAME),
                this.permissionName, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PERMISSIONNAME) && (property.getType() == Name.class))) {
            this.setPermissionName(((Name) property.getInstance()));
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
        final PermissionCondition other = ((PermissionCondition) obj);
        if ((this.permissionName == null)) {
            if ((other.permissionName != null))
                return false;
        } else if ((!this.permissionName.equals(other.permissionName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.permissionName == null) ? 0 : this.permissionName.hashCode()));
        return result;
    }

    @Override
    public PermissionCondition cloneObject() {
        PermissionCondition clone = new PermissionCondition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the required permission.
     *
     * @return the Name.
     */
    public Name getPermissionName() {
        return this.permissionName;
    }

    /**
     * Name of the required permission.
     *
     * @param permissionName the Name.
     */
    public void setPermissionName(Name permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Name of the required permission.
     *
     * @param permissionName the String.
     */
    public void setPermissionName(String permissionName) {
        if ((this.permissionName == null)) {
            if ((permissionName == null)) {
                return;
            }
            this.permissionName = new Name();
        }
        this.permissionName.setValue(permissionName);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PermissionCondition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PermissionCondition.class).getAllProperties();
    }
}
