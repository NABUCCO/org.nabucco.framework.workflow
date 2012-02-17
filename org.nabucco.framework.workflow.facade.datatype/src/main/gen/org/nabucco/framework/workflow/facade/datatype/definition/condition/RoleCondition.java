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
 * RoleCondition<p/>Condition for Authorization Role.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-10
 */
public class RoleCondition extends WorkflowConditionComponent implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowConditionType CONDITIONTYPE_DEFAULT = WorkflowConditionType.ROLE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;" };

    public static final String ROLENAME = "roleName";

    /** Name of the required role. */
    private Name roleName;

    /** Constructs a new RoleCondition instance. */
    public RoleCondition() {
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
     * @param clone the RoleCondition.
     */
    protected void cloneObject(RoleCondition clone) {
        super.cloneObject(clone);
        clone.setConditionType(this.getConditionType());
        if ((this.getRoleName() != null)) {
            clone.setRoleName(this.getRoleName().cloneObject());
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
        propertyMap.put(ROLENAME,
                PropertyDescriptorSupport.createBasetype(ROLENAME, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(RoleCondition.getPropertyDescriptor(ROLENAME), this.roleName, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ROLENAME) && (property.getType() == Name.class))) {
            this.setRoleName(((Name) property.getInstance()));
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
        final RoleCondition other = ((RoleCondition) obj);
        if ((this.roleName == null)) {
            if ((other.roleName != null))
                return false;
        } else if ((!this.roleName.equals(other.roleName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.roleName == null) ? 0 : this.roleName.hashCode()));
        return result;
    }

    @Override
    public RoleCondition cloneObject() {
        RoleCondition clone = new RoleCondition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the required role.
     *
     * @return the Name.
     */
    public Name getRoleName() {
        return this.roleName;
    }

    /**
     * Name of the required role.
     *
     * @param roleName the Name.
     */
    public void setRoleName(Name roleName) {
        this.roleName = roleName;
    }

    /**
     * Name of the required role.
     *
     * @param roleName the String.
     */
    public void setRoleName(String roleName) {
        if ((this.roleName == null)) {
            if ((roleName == null)) {
                return;
            }
            this.roleName = new Name();
        }
        this.roleName.setValue(roleName);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(RoleCondition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(RoleCondition.class).getAllProperties();
    }
}
