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
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionCommentType;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;

/**
 * WorkflowTransition<p/>A Transition in a workflow.<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-03-29
 */
public class WorkflowTransition extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final TransitionCommentType COMMENTCARDINALITY_DEFAULT = TransitionCommentType.OPTIONAL;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l3,12;u0,n;m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String OWNER = "owner";

    public static final String COMMENTCARDINALITY = "commentCardinality";

    public static final String SOURCE = "source";

    public static final String TARGET = "target";

    public static final String TRIGGER = "trigger";

    public static final String CONDITION = "condition";

    public static final String EFFECTLIST = "effectList";

    /** The name of the transition. */
    private Name name;

    /** A brief description of the transition. */
    private Description description;

    /** The owner of the transition. */
    private Owner owner;

    /** The cardinality of the comment of this transition. */
    private TransitionCommentType commentCardinality;

    /** The source state of the transition. */
    private WorkflowState source;

    /** The target state of the transition. */
    private WorkflowState target;

    /** The trigger of the transition. */
    private WorkflowTrigger trigger;

    /** The guarding condition of the transition. */
    private WorkflowCondition condition;

    /** The list of effects of the transition. */
    private NabuccoList<WorkflowEffect> effectList;

    /** Constructs a new WorkflowTransition instance. */
    public WorkflowTransition() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        commentCardinality = COMMENTCARDINALITY_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowTransition.
     */
    protected void cloneObject(WorkflowTransition clone) {
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
        clone.setCommentCardinality(this.getCommentCardinality());
        if ((this.getSource() != null)) {
            clone.setSource(this.getSource().cloneObject());
        }
        if ((this.getTarget() != null)) {
            clone.setTarget(this.getTarget().cloneObject());
        }
        if ((this.getTrigger() != null)) {
            clone.setTrigger(this.getTrigger().cloneObject());
        }
        if ((this.getCondition() != null)) {
            clone.setCondition(this.getCondition().cloneObject());
        }
        if ((this.effectList != null)) {
            clone.effectList = this.effectList.cloneCollection();
        }
    }

    /**
     * Getter for the EffectListJPA.
     *
     * @return the List<WorkflowEffect>.
     */
    List<WorkflowEffect> getEffectListJPA() {
        if ((this.effectList == null)) {
            this.effectList = new NabuccoListImpl<WorkflowEffect>(NabuccoCollectionState.EAGER);
        }
        return ((NabuccoListImpl<WorkflowEffect>) this.effectList).getDelegate();
    }

    /**
     * Setter for the EffectListJPA.
     *
     * @param effectList the List<WorkflowEffect>.
     */
    void setEffectListJPA(List<WorkflowEffect> effectList) {
        if ((this.effectList == null)) {
            this.effectList = new NabuccoListImpl<WorkflowEffect>(NabuccoCollectionState.EAGER);
        }
        ((NabuccoListImpl<WorkflowEffect>) this.effectList).setDelegate(effectList);
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
        propertyMap.put(COMMENTCARDINALITY, PropertyDescriptorSupport.createEnumeration(COMMENTCARDINALITY,
                TransitionCommentType.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(SOURCE, PropertyDescriptorSupport.createDatatype(SOURCE, WorkflowState.class, 7,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(TARGET, PropertyDescriptorSupport.createDatatype(TARGET, WorkflowState.class, 8,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(TRIGGER, PropertyDescriptorSupport.createDatatype(TRIGGER, WorkflowTrigger.class, 9,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(CONDITION, PropertyDescriptorSupport.createDatatype(CONDITION, WorkflowCondition.class, 10,
                PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EFFECTLIST, PropertyDescriptorSupport.createCollection(EFFECTLIST, WorkflowEffect.class, 11,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(COMMENTCARDINALITY),
                this.getCommentCardinality(), null));
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(SOURCE), this.getSource(), null));
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(TARGET), this.getTarget(), null));
        properties
                .add(super.createProperty(WorkflowTransition.getPropertyDescriptor(TRIGGER), this.getTrigger(), null));
        properties.add(super.createProperty(WorkflowTransition.getPropertyDescriptor(CONDITION), this.getCondition(),
                null));
        properties
                .add(super.createProperty(WorkflowTransition.getPropertyDescriptor(EFFECTLIST), this.effectList, null));
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
        } else if ((property.getName().equals(COMMENTCARDINALITY) && (property.getType() == TransitionCommentType.class))) {
            this.setCommentCardinality(((TransitionCommentType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SOURCE) && (property.getType() == WorkflowState.class))) {
            this.setSource(((WorkflowState) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGET) && (property.getType() == WorkflowState.class))) {
            this.setTarget(((WorkflowState) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TRIGGER) && (property.getType() == WorkflowTrigger.class))) {
            this.setTrigger(((WorkflowTrigger) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONDITION) && (property.getType() == WorkflowCondition.class))) {
            this.setCondition(((WorkflowCondition) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EFFECTLIST) && (property.getType() == WorkflowEffect.class))) {
            this.effectList = ((NabuccoList<WorkflowEffect>) property.getInstance());
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
        final WorkflowTransition other = ((WorkflowTransition) obj);
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
        if ((this.commentCardinality == null)) {
            if ((other.commentCardinality != null))
                return false;
        } else if ((!this.commentCardinality.equals(other.commentCardinality)))
            return false;
        if ((this.source == null)) {
            if ((other.source != null))
                return false;
        } else if ((!this.source.equals(other.source)))
            return false;
        if ((this.target == null)) {
            if ((other.target != null))
                return false;
        } else if ((!this.target.equals(other.target)))
            return false;
        if ((this.trigger == null)) {
            if ((other.trigger != null))
                return false;
        } else if ((!this.trigger.equals(other.trigger)))
            return false;
        if ((this.condition == null)) {
            if ((other.condition != null))
                return false;
        } else if ((!this.condition.equals(other.condition)))
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
        result = ((PRIME * result) + ((this.commentCardinality == null) ? 0 : this.commentCardinality.hashCode()));
        result = ((PRIME * result) + ((this.source == null) ? 0 : this.source.hashCode()));
        result = ((PRIME * result) + ((this.target == null) ? 0 : this.target.hashCode()));
        result = ((PRIME * result) + ((this.trigger == null) ? 0 : this.trigger.hashCode()));
        result = ((PRIME * result) + ((this.condition == null) ? 0 : this.condition.hashCode()));
        return result;
    }

    @Override
    public WorkflowTransition cloneObject() {
        WorkflowTransition clone = new WorkflowTransition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the transition.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the transition.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the transition.
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
     * A brief description of the transition.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * A brief description of the transition.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * A brief description of the transition.
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
     * The owner of the transition.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the transition.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the transition.
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
     * The cardinality of the comment of this transition.
     *
     * @return the TransitionCommentType.
     */
    public TransitionCommentType getCommentCardinality() {
        return this.commentCardinality;
    }

    /**
     * The cardinality of the comment of this transition.
     *
     * @param commentCardinality the TransitionCommentType.
     */
    public void setCommentCardinality(TransitionCommentType commentCardinality) {
        this.commentCardinality = commentCardinality;
    }

    /**
     * The cardinality of the comment of this transition.
     *
     * @param commentCardinality the String.
     */
    public void setCommentCardinality(String commentCardinality) {
        if ((commentCardinality == null)) {
            this.commentCardinality = null;
        } else {
            this.commentCardinality = TransitionCommentType.valueOf(commentCardinality);
        }
    }

    /**
     * The source state of the transition.
     *
     * @param source the WorkflowState.
     */
    public void setSource(WorkflowState source) {
        this.source = source;
    }

    /**
     * The source state of the transition.
     *
     * @return the WorkflowState.
     */
    public WorkflowState getSource() {
        return this.source;
    }

    /**
     * The target state of the transition.
     *
     * @param target the WorkflowState.
     */
    public void setTarget(WorkflowState target) {
        this.target = target;
    }

    /**
     * The target state of the transition.
     *
     * @return the WorkflowState.
     */
    public WorkflowState getTarget() {
        return this.target;
    }

    /**
     * The trigger of the transition.
     *
     * @param trigger the WorkflowTrigger.
     */
    public void setTrigger(WorkflowTrigger trigger) {
        this.trigger = trigger;
    }

    /**
     * The trigger of the transition.
     *
     * @return the WorkflowTrigger.
     */
    public WorkflowTrigger getTrigger() {
        return this.trigger;
    }

    /**
     * The guarding condition of the transition.
     *
     * @param condition the WorkflowCondition.
     */
    public void setCondition(WorkflowCondition condition) {
        this.condition = condition;
    }

    /**
     * The guarding condition of the transition.
     *
     * @return the WorkflowCondition.
     */
    public WorkflowCondition getCondition() {
        return this.condition;
    }

    /**
     * The list of effects of the transition.
     *
     * @return the NabuccoList<WorkflowEffect>.
     */
    public NabuccoList<WorkflowEffect> getEffectList() {
        if ((this.effectList == null)) {
            this.effectList = new NabuccoListImpl<WorkflowEffect>(NabuccoCollectionState.INITIALIZED);
        }
        return this.effectList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowTransition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowTransition.class).getAllProperties();
    }
}
