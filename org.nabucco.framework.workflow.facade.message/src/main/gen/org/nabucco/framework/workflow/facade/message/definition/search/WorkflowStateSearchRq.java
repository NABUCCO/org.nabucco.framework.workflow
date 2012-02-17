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
package org.nabucco.framework.workflow.facade.message.definition.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;

/**
 * WorkflowStateSearchRq<p/>Message containing search criteria for a workflow state.<p/>
 *
 * @author Silas Schwarz, PRODYNA AG, 2010-05-24
 */
public class WorkflowStateSearchRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;",
            "l3,12;u0,n;m0,1;", "m0,1;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String OWNER = "owner";

    public static final String TYPE = "type";

    /** The name of the WorkflowState. */
    private Name name;

    /** The description of the WorkflowState. */
    private Description description;

    /** The owner of the WorkflowState. */
    private Owner owner;

    /** The type of the WorkflowState. */
    private WorkflowStateType type;

    /** Constructs a new WorkflowStateSearchRq instance. */
    public WorkflowStateSearchRq() {
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
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, WorkflowStateType.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowStateSearchRq.getPropertyDescriptor(NAME), this.name));
        properties
                .add(super.createProperty(WorkflowStateSearchRq.getPropertyDescriptor(DESCRIPTION), this.description));
        properties.add(super.createProperty(WorkflowStateSearchRq.getPropertyDescriptor(OWNER), this.owner));
        properties.add(super.createProperty(WorkflowStateSearchRq.getPropertyDescriptor(TYPE), this.getType()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == WorkflowStateType.class))) {
            this.setType(((WorkflowStateType) property.getInstance()));
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
        final WorkflowStateSearchRq other = ((WorkflowStateSearchRq) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The name of the WorkflowState.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the WorkflowState.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The description of the WorkflowState.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * The description of the WorkflowState.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * The owner of the WorkflowState.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the WorkflowState.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The type of the WorkflowState.
     *
     * @return the WorkflowStateType.
     */
    public WorkflowStateType getType() {
        return this.type;
    }

    /**
     * The type of the WorkflowState.
     *
     * @param type the WorkflowStateType.
     */
    public void setType(WorkflowStateType type) {
        this.type = type;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowStateSearchRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowStateSearchRq.class).getAllProperties();
    }
}
