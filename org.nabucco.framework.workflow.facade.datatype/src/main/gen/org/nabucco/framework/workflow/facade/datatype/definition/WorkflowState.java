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
package org.nabucco.framework.workflow.facade.datatype.definition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateConstraint;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;

/**
 * WorkflowState<p/>Definition of a Workflow State<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-03-29
 */
public class WorkflowState extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l3,12;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "m1,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String TYPE = "type";

    public static final String CONSTRAINTLIST = "constraintList";

    /** The name of the workflow state. */
    private Name name;

    /** The owner of the workflow state. */
    private Owner owner;

    /** A brief description of the state. */
    private Description description;

    /** The type of the workflow (START, END, NORMAL) */
    private WorkflowStateType type;

    /** Constraints for the datatype or datatype property in the state. */
    private NabuccoList<WorkflowStateConstraint> constraintList;

    /** Constructs a new WorkflowState instance. */
    public WorkflowState() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowState.
     */
    protected void cloneObject(WorkflowState clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        clone.setType(this.getType());
        if ((this.constraintList != null)) {
            clone.constraintList = this.constraintList.cloneCollection();
        }
    }

    /**
     * Getter for the ConstraintListJPA.
     *
     * @return the List<WorkflowStateConstraint>.
     */
    List<WorkflowStateConstraint> getConstraintListJPA() {
        if ((this.constraintList == null)) {
            this.constraintList = new NabuccoListImpl<WorkflowStateConstraint>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowStateConstraint>) this.constraintList).getDelegate();
    }

    /**
     * Setter for the ConstraintListJPA.
     *
     * @param constraintList the List<WorkflowStateConstraint>.
     */
    void setConstraintListJPA(List<WorkflowStateConstraint> constraintList) {
        if ((this.constraintList == null)) {
            this.constraintList = new NabuccoListImpl<WorkflowStateConstraint>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowStateConstraint>) this.constraintList).setDelegate(constraintList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, WorkflowStateType.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(CONSTRAINTLIST, PropertyDescriptorSupport.createCollection(CONSTRAINTLIST,
                WorkflowStateConstraint.class, 7, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowState.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(WorkflowState.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(WorkflowState.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(WorkflowState.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(WorkflowState.getPropertyDescriptor(CONSTRAINTLIST), this.constraintList,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == WorkflowStateType.class))) {
            this.setType(((WorkflowStateType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONSTRAINTLIST) && (property.getType() == WorkflowStateConstraint.class))) {
            this.constraintList = ((NabuccoList<WorkflowStateConstraint>) property.getInstance());
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
        final WorkflowState other = ((WorkflowState) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
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
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public WorkflowState cloneObject() {
        WorkflowState clone = new WorkflowState();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the workflow state.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the workflow state.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the workflow state.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * The owner of the workflow state.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the workflow state.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the workflow state.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * A brief description of the state.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * A brief description of the state.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * A brief description of the state.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * The type of the workflow (START, END, NORMAL)
     *
     * @return the WorkflowStateType.
     */
    public WorkflowStateType getType() {
        return this.type;
    }

    /**
     * The type of the workflow (START, END, NORMAL)
     *
     * @param type the WorkflowStateType.
     */
    public void setType(WorkflowStateType type) {
        this.type = type;
    }

    /**
     * The type of the workflow (START, END, NORMAL)
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = WorkflowStateType.valueOf(type);
        }
    }

    /**
     * Constraints for the datatype or datatype property in the state.
     *
     * @return the NabuccoList<WorkflowStateConstraint>.
     */
    public NabuccoList<WorkflowStateConstraint> getConstraintList() {
        if ((this.constraintList == null)) {
            this.constraintList = new NabuccoListImpl<WorkflowStateConstraint>(NabuccoCollectionState.INITIALIZED);
        }
        return this.constraintList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowState.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowState.class).getAllProperties();
    }
}
