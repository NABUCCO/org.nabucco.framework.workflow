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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.assignee;

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
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * AssigneeEffect<p/>Changes the Assignee of the current workflow instance<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class AssigneeEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.ASSIGNEE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;" };

    public static final String NEWUSER = "newUser";

    public static final String NEWGROUP = "newGroup";

    public static final String NEWROLE = "newRole";

    /** The new assigned user. */
    private Name newUser;

    /** The new assigned group. */
    private Name newGroup;

    /** The new assigned role. */
    private Name newRole;

    /** Constructs a new AssigneeEffect instance. */
    public AssigneeEffect() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        effectType = EFFECTTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the AssigneeEffect.
     */
    protected void cloneObject(AssigneeEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getNewUser() != null)) {
            clone.setNewUser(this.getNewUser().cloneObject());
        }
        if ((this.getNewGroup() != null)) {
            clone.setNewGroup(this.getNewGroup().cloneObject());
        }
        if ((this.getNewRole() != null)) {
            clone.setNewRole(this.getNewRole().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowEffect.class).getPropertyMap());
        propertyMap.put(NEWUSER,
                PropertyDescriptorSupport.createBasetype(NEWUSER, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NEWGROUP,
                PropertyDescriptorSupport.createBasetype(NEWGROUP, Name.class, 8, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NEWROLE,
                PropertyDescriptorSupport.createBasetype(NEWROLE, Name.class, 9, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(AssigneeEffect.getPropertyDescriptor(NEWUSER), this.newUser, null));
        properties.add(super.createProperty(AssigneeEffect.getPropertyDescriptor(NEWGROUP), this.newGroup, null));
        properties.add(super.createProperty(AssigneeEffect.getPropertyDescriptor(NEWROLE), this.newRole, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NEWUSER) && (property.getType() == Name.class))) {
            this.setNewUser(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEWGROUP) && (property.getType() == Name.class))) {
            this.setNewGroup(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEWROLE) && (property.getType() == Name.class))) {
            this.setNewRole(((Name) property.getInstance()));
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
        final AssigneeEffect other = ((AssigneeEffect) obj);
        if ((this.newUser == null)) {
            if ((other.newUser != null))
                return false;
        } else if ((!this.newUser.equals(other.newUser)))
            return false;
        if ((this.newGroup == null)) {
            if ((other.newGroup != null))
                return false;
        } else if ((!this.newGroup.equals(other.newGroup)))
            return false;
        if ((this.newRole == null)) {
            if ((other.newRole != null))
                return false;
        } else if ((!this.newRole.equals(other.newRole)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.newUser == null) ? 0 : this.newUser.hashCode()));
        result = ((PRIME * result) + ((this.newGroup == null) ? 0 : this.newGroup.hashCode()));
        result = ((PRIME * result) + ((this.newRole == null) ? 0 : this.newRole.hashCode()));
        return result;
    }

    @Override
    public AssigneeEffect cloneObject() {
        AssigneeEffect clone = new AssigneeEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The new assigned user.
     *
     * @return the Name.
     */
    public Name getNewUser() {
        return this.newUser;
    }

    /**
     * The new assigned user.
     *
     * @param newUser the Name.
     */
    public void setNewUser(Name newUser) {
        this.newUser = newUser;
    }

    /**
     * The new assigned user.
     *
     * @param newUser the String.
     */
    public void setNewUser(String newUser) {
        if ((this.newUser == null)) {
            if ((newUser == null)) {
                return;
            }
            this.newUser = new Name();
        }
        this.newUser.setValue(newUser);
    }

    /**
     * The new assigned group.
     *
     * @return the Name.
     */
    public Name getNewGroup() {
        return this.newGroup;
    }

    /**
     * The new assigned group.
     *
     * @param newGroup the Name.
     */
    public void setNewGroup(Name newGroup) {
        this.newGroup = newGroup;
    }

    /**
     * The new assigned group.
     *
     * @param newGroup the String.
     */
    public void setNewGroup(String newGroup) {
        if ((this.newGroup == null)) {
            if ((newGroup == null)) {
                return;
            }
            this.newGroup = new Name();
        }
        this.newGroup.setValue(newGroup);
    }

    /**
     * The new assigned role.
     *
     * @return the Name.
     */
    public Name getNewRole() {
        return this.newRole;
    }

    /**
     * The new assigned role.
     *
     * @param newRole the Name.
     */
    public void setNewRole(Name newRole) {
        this.newRole = newRole;
    }

    /**
     * The new assigned role.
     *
     * @param newRole the String.
     */
    public void setNewRole(String newRole) {
        if ((this.newRole == null)) {
            if ((newRole == null)) {
                return;
            }
            this.newRole = new Name();
        }
        this.newRole.setValue(newRole);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AssigneeEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AssigneeEffect.class).getAllProperties();
    }
}
