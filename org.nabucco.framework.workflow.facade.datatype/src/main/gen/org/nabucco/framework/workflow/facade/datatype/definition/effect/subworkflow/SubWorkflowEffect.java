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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.subworkflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * SubWorkflowEffect<p/>Executes a script.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-11-10
 */
public class SubWorkflowEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.SUB_WORKFLOW;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m1,1;" };

    public static final String DEFINITIONNAME = "definitionName";

    public static final String ASSIGNEDUSER = "assignedUser";

    public static final String ASSIGNEDGROUP = "assignedGroup";

    public static final String ASSIGNEDROLE = "assignedRole";

    public static final String SUMMARY = "summary";

    /** The name of the script to call. */
    private Name definitionName;

    /** The assigned user of the new instance. */
    private Name assignedUser;

    /** The assigned group of the new instance. */
    private Name assignedGroup;

    /** The assigned role of the new instance. */
    private Name assignedRole;

    /** The summary of the new workflow instance. */
    private Description summary;

    /** Constructs a new SubWorkflowEffect instance. */
    public SubWorkflowEffect() {
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
     * @param clone the SubWorkflowEffect.
     */
    protected void cloneObject(SubWorkflowEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getDefinitionName() != null)) {
            clone.setDefinitionName(this.getDefinitionName().cloneObject());
        }
        if ((this.getAssignedUser() != null)) {
            clone.setAssignedUser(this.getAssignedUser().cloneObject());
        }
        if ((this.getAssignedGroup() != null)) {
            clone.setAssignedGroup(this.getAssignedGroup().cloneObject());
        }
        if ((this.getAssignedRole() != null)) {
            clone.setAssignedRole(this.getAssignedRole().cloneObject());
        }
        if ((this.getSummary() != null)) {
            clone.setSummary(this.getSummary().cloneObject());
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
        propertyMap
                .put(DEFINITIONNAME, PropertyDescriptorSupport.createBasetype(DEFINITIONNAME, Name.class, 7,
                        PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ASSIGNEDUSER,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDUSER, Name.class, 8, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(ASSIGNEDGROUP,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDGROUP, Name.class, 9, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(ASSIGNEDROLE,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDROLE, Name.class, 10, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(SUMMARY, PropertyDescriptorSupport.createBasetype(SUMMARY, Description.class, 11,
                PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SubWorkflowEffect.getPropertyDescriptor(DEFINITIONNAME),
                this.definitionName, null));
        properties.add(super.createProperty(SubWorkflowEffect.getPropertyDescriptor(ASSIGNEDUSER), this.assignedUser,
                null));
        properties.add(super.createProperty(SubWorkflowEffect.getPropertyDescriptor(ASSIGNEDGROUP), this.assignedGroup,
                null));
        properties.add(super.createProperty(SubWorkflowEffect.getPropertyDescriptor(ASSIGNEDROLE), this.assignedRole,
                null));
        properties.add(super.createProperty(SubWorkflowEffect.getPropertyDescriptor(SUMMARY), this.summary, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DEFINITIONNAME) && (property.getType() == Name.class))) {
            this.setDefinitionName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDUSER) && (property.getType() == Name.class))) {
            this.setAssignedUser(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDGROUP) && (property.getType() == Name.class))) {
            this.setAssignedGroup(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDROLE) && (property.getType() == Name.class))) {
            this.setAssignedRole(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUMMARY) && (property.getType() == Description.class))) {
            this.setSummary(((Description) property.getInstance()));
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
        final SubWorkflowEffect other = ((SubWorkflowEffect) obj);
        if ((this.definitionName == null)) {
            if ((other.definitionName != null))
                return false;
        } else if ((!this.definitionName.equals(other.definitionName)))
            return false;
        if ((this.assignedUser == null)) {
            if ((other.assignedUser != null))
                return false;
        } else if ((!this.assignedUser.equals(other.assignedUser)))
            return false;
        if ((this.assignedGroup == null)) {
            if ((other.assignedGroup != null))
                return false;
        } else if ((!this.assignedGroup.equals(other.assignedGroup)))
            return false;
        if ((this.assignedRole == null)) {
            if ((other.assignedRole != null))
                return false;
        } else if ((!this.assignedRole.equals(other.assignedRole)))
            return false;
        if ((this.summary == null)) {
            if ((other.summary != null))
                return false;
        } else if ((!this.summary.equals(other.summary)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.definitionName == null) ? 0 : this.definitionName.hashCode()));
        result = ((PRIME * result) + ((this.assignedUser == null) ? 0 : this.assignedUser.hashCode()));
        result = ((PRIME * result) + ((this.assignedGroup == null) ? 0 : this.assignedGroup.hashCode()));
        result = ((PRIME * result) + ((this.assignedRole == null) ? 0 : this.assignedRole.hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        return result;
    }

    @Override
    public SubWorkflowEffect cloneObject() {
        SubWorkflowEffect clone = new SubWorkflowEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the script to call.
     *
     * @return the Name.
     */
    public Name getDefinitionName() {
        return this.definitionName;
    }

    /**
     * The name of the script to call.
     *
     * @param definitionName the Name.
     */
    public void setDefinitionName(Name definitionName) {
        this.definitionName = definitionName;
    }

    /**
     * The name of the script to call.
     *
     * @param definitionName the String.
     */
    public void setDefinitionName(String definitionName) {
        if ((this.definitionName == null)) {
            if ((definitionName == null)) {
                return;
            }
            this.definitionName = new Name();
        }
        this.definitionName.setValue(definitionName);
    }

    /**
     * The assigned user of the new instance.
     *
     * @return the Name.
     */
    public Name getAssignedUser() {
        return this.assignedUser;
    }

    /**
     * The assigned user of the new instance.
     *
     * @param assignedUser the Name.
     */
    public void setAssignedUser(Name assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * The assigned user of the new instance.
     *
     * @param assignedUser the String.
     */
    public void setAssignedUser(String assignedUser) {
        if ((this.assignedUser == null)) {
            if ((assignedUser == null)) {
                return;
            }
            this.assignedUser = new Name();
        }
        this.assignedUser.setValue(assignedUser);
    }

    /**
     * The assigned group of the new instance.
     *
     * @return the Name.
     */
    public Name getAssignedGroup() {
        return this.assignedGroup;
    }

    /**
     * The assigned group of the new instance.
     *
     * @param assignedGroup the Name.
     */
    public void setAssignedGroup(Name assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    /**
     * The assigned group of the new instance.
     *
     * @param assignedGroup the String.
     */
    public void setAssignedGroup(String assignedGroup) {
        if ((this.assignedGroup == null)) {
            if ((assignedGroup == null)) {
                return;
            }
            this.assignedGroup = new Name();
        }
        this.assignedGroup.setValue(assignedGroup);
    }

    /**
     * The assigned role of the new instance.
     *
     * @return the Name.
     */
    public Name getAssignedRole() {
        return this.assignedRole;
    }

    /**
     * The assigned role of the new instance.
     *
     * @param assignedRole the Name.
     */
    public void setAssignedRole(Name assignedRole) {
        this.assignedRole = assignedRole;
    }

    /**
     * The assigned role of the new instance.
     *
     * @param assignedRole the String.
     */
    public void setAssignedRole(String assignedRole) {
        if ((this.assignedRole == null)) {
            if ((assignedRole == null)) {
                return;
            }
            this.assignedRole = new Name();
        }
        this.assignedRole.setValue(assignedRole);
    }

    /**
     * The summary of the new workflow instance.
     *
     * @return the Description.
     */
    public Description getSummary() {
        return this.summary;
    }

    /**
     * The summary of the new workflow instance.
     *
     * @param summary the Description.
     */
    public void setSummary(Description summary) {
        this.summary = summary;
    }

    /**
     * The summary of the new workflow instance.
     *
     * @param summary the String.
     */
    public void setSummary(String summary) {
        if ((this.summary == null)) {
            if ((summary == null)) {
                return;
            }
            this.summary = new Description();
        }
        this.summary.setValue(summary);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SubWorkflowEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SubWorkflowEffect.class).getAllProperties();
    }
}
