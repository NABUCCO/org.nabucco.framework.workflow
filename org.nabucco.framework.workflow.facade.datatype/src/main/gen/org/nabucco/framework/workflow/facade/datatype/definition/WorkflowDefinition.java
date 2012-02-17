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
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinitionType;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignalContainer;

/**
 * WorkflowDefinition<p/>Template of a Workflow defines states and transitions<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2010-03-29
 */
public class WorkflowDefinition extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l3,12;u0,n;m1,1;", "m1,1;", "m0,n;", "m0,n;", "m0,n;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String OWNER = "owner";

    public static final String WORKFLOWTYPE = "workflowType";

    public static final String STATELIST = "stateList";

    public static final String TRANSITIONLIST = "transitionList";

    public static final String SIGNALLIST = "signalList";

    /** The name of the workflow definition. */
    private Name name;

    /** A brief description of the workflow. */
    private Description description;

    /** The owner of the workflow definition. */
    private Owner owner;

    /** The type of the workflow. */
    private WorkflowDefinitionType workflowType;

    /** The list of states contained by the workflow. */
    private NabuccoList<WorkflowState> stateList;

    /** The list of all transitions contained by the workflow. */
    private NabuccoList<WorkflowTransition> transitionList;

    /** The list of available signals of this workflow. */
    private NabuccoList<WorkflowSignalContainer> signalList;

    /** Constructs a new WorkflowDefinition instance. */
    public WorkflowDefinition() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowDefinition.
     */
    protected void cloneObject(WorkflowDefinition clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        clone.setWorkflowType(this.getWorkflowType());
        if ((this.stateList != null)) {
            clone.stateList = this.stateList.cloneCollection();
        }
        if ((this.transitionList != null)) {
            clone.transitionList = this.transitionList.cloneCollection();
        }
        if ((this.signalList != null)) {
            clone.signalList = this.signalList.cloneCollection();
        }
    }

    /**
     * Getter for the StateListJPA.
     *
     * @return the List<WorkflowState>.
     */
    List<WorkflowState> getStateListJPA() {
        if ((this.stateList == null)) {
            this.stateList = new NabuccoListImpl<WorkflowState>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowState>) this.stateList).getDelegate();
    }

    /**
     * Setter for the StateListJPA.
     *
     * @param stateList the List<WorkflowState>.
     */
    void setStateListJPA(List<WorkflowState> stateList) {
        if ((this.stateList == null)) {
            this.stateList = new NabuccoListImpl<WorkflowState>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowState>) this.stateList).setDelegate(stateList);
    }

    /**
     * Getter for the TransitionListJPA.
     *
     * @return the List<WorkflowTransition>.
     */
    List<WorkflowTransition> getTransitionListJPA() {
        if ((this.transitionList == null)) {
            this.transitionList = new NabuccoListImpl<WorkflowTransition>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowTransition>) this.transitionList).getDelegate();
    }

    /**
     * Setter for the TransitionListJPA.
     *
     * @param transitionList the List<WorkflowTransition>.
     */
    void setTransitionListJPA(List<WorkflowTransition> transitionList) {
        if ((this.transitionList == null)) {
            this.transitionList = new NabuccoListImpl<WorkflowTransition>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowTransition>) this.transitionList).setDelegate(transitionList);
    }

    /**
     * Getter for the SignalListJPA.
     *
     * @return the List<WorkflowSignalContainer>.
     */
    List<WorkflowSignalContainer> getSignalListJPA() {
        if ((this.signalList == null)) {
            this.signalList = new NabuccoListImpl<WorkflowSignalContainer>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowSignalContainer>) this.signalList).getDelegate();
    }

    /**
     * Setter for the SignalListJPA.
     *
     * @param signalList the List<WorkflowSignalContainer>.
     */
    void setSignalListJPA(List<WorkflowSignalContainer> signalList) {
        if ((this.signalList == null)) {
            this.signalList = new NabuccoListImpl<WorkflowSignalContainer>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowSignalContainer>) this.signalList).setDelegate(signalList);
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
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(WORKFLOWTYPE, PropertyDescriptorSupport.createEnumeration(WORKFLOWTYPE,
                WorkflowDefinitionType.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(STATELIST, PropertyDescriptorSupport.createCollection(STATELIST, WorkflowState.class, 7,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TRANSITIONLIST, PropertyDescriptorSupport.createCollection(TRANSITIONLIST,
                WorkflowTransition.class, 8, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SIGNALLIST, PropertyDescriptorSupport.createCollection(SIGNALLIST,
                WorkflowSignalContainer.class, 9, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        properties.add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(WORKFLOWTYPE),
                this.getWorkflowType(), null));
        properties.add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(STATELIST), this.stateList, null));
        properties.add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(TRANSITIONLIST),
                this.transitionList, null));
        properties
                .add(super.createProperty(WorkflowDefinition.getPropertyDescriptor(SIGNALLIST), this.signalList, null));
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
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WORKFLOWTYPE) && (property.getType() == WorkflowDefinitionType.class))) {
            this.setWorkflowType(((WorkflowDefinitionType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATELIST) && (property.getType() == WorkflowState.class))) {
            this.stateList = ((NabuccoList<WorkflowState>) property.getInstance());
            return true;
        } else if ((property.getName().equals(TRANSITIONLIST) && (property.getType() == WorkflowTransition.class))) {
            this.transitionList = ((NabuccoList<WorkflowTransition>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SIGNALLIST) && (property.getType() == WorkflowSignalContainer.class))) {
            this.signalList = ((NabuccoList<WorkflowSignalContainer>) property.getInstance());
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
        final WorkflowDefinition other = ((WorkflowDefinition) obj);
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
        if ((this.workflowType == null)) {
            if ((other.workflowType != null))
                return false;
        } else if ((!this.workflowType.equals(other.workflowType)))
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
        result = ((PRIME * result) + ((this.workflowType == null) ? 0 : this.workflowType.hashCode()));
        return result;
    }

    @Override
    public WorkflowDefinition cloneObject() {
        WorkflowDefinition clone = new WorkflowDefinition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the workflow definition.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the workflow definition.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the workflow definition.
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
     * A brief description of the workflow.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * A brief description of the workflow.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * A brief description of the workflow.
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
     * The owner of the workflow definition.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the workflow definition.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the workflow definition.
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
     * The type of the workflow.
     *
     * @return the WorkflowDefinitionType.
     */
    public WorkflowDefinitionType getWorkflowType() {
        return this.workflowType;
    }

    /**
     * The type of the workflow.
     *
     * @param workflowType the WorkflowDefinitionType.
     */
    public void setWorkflowType(WorkflowDefinitionType workflowType) {
        this.workflowType = workflowType;
    }

    /**
     * The type of the workflow.
     *
     * @param workflowType the String.
     */
    public void setWorkflowType(String workflowType) {
        if ((workflowType == null)) {
            this.workflowType = null;
        } else {
            this.workflowType = WorkflowDefinitionType.valueOf(workflowType);
        }
    }

    /**
     * The list of states contained by the workflow.
     *
     * @return the NabuccoList<WorkflowState>.
     */
    public NabuccoList<WorkflowState> getStateList() {
        if ((this.stateList == null)) {
            this.stateList = new NabuccoListImpl<WorkflowState>(NabuccoCollectionState.INITIALIZED);
        }
        return this.stateList;
    }

    /**
     * The list of all transitions contained by the workflow.
     *
     * @return the NabuccoList<WorkflowTransition>.
     */
    public NabuccoList<WorkflowTransition> getTransitionList() {
        if ((this.transitionList == null)) {
            this.transitionList = new NabuccoListImpl<WorkflowTransition>(NabuccoCollectionState.INITIALIZED);
        }
        return this.transitionList;
    }

    /**
     * The list of available signals of this workflow.
     *
     * @return the NabuccoList<WorkflowSignalContainer>.
     */
    public NabuccoList<WorkflowSignalContainer> getSignalList() {
        if ((this.signalList == null)) {
            this.signalList = new NabuccoListImpl<WorkflowSignalContainer>(NabuccoCollectionState.INITIALIZED);
        }
        return this.signalList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowDefinition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowDefinition.class).getAllProperties();
    }
}
