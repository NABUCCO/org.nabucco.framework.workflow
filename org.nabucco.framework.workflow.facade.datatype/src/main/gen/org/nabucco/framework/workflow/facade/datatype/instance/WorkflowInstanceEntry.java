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
package org.nabucco.framework.workflow.facade.datatype.instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * WorkflowInstanceEntry<p/>A workflow entry (the current state of a workflow instance).<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-02-08
 */
public class WorkflowInstanceEntry extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,100000;u0,n;m0,1;", "m1,1;", "m0,1;" };

    public static final String OWNER = "owner";

    public static final String MODIFIER = "modifier";

    public static final String MODIFICATIONTIME = "modificationTime";

    public static final String COMMENT = "comment";

    public static final String STATE = "state";

    public static final String CONTEXT = "context";

    /** The owner of the WorkflowEntry. */
    private Owner owner;

    /** The name of the last maintainer. */
    private Name modifier;

    /** The Date and Time of last modification. */
    private Timestamp modificationTime;

    /** The last transition comment. */
    private Comment comment;

    /** The workflow state that the entry historicizes */
    private WorkflowState state;

    /** The instance context. */
    private WorkflowContext context;

    /** Constructs a new WorkflowInstanceEntry instance. */
    public WorkflowInstanceEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowInstanceEntry.
     */
    protected void cloneObject(WorkflowInstanceEntry clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getModifier() != null)) {
            clone.setModifier(this.getModifier().cloneObject());
        }
        if ((this.getModificationTime() != null)) {
            clone.setModificationTime(this.getModificationTime().cloneObject());
        }
        if ((this.getComment() != null)) {
            clone.setComment(this.getComment().cloneObject());
        }
        if ((this.getState() != null)) {
            clone.setState(this.getState().cloneObject());
        }
        if ((this.getContext() != null)) {
            clone.setContext(this.getContext().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(MODIFIER,
                PropertyDescriptorSupport.createBasetype(MODIFIER, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(MODIFICATIONTIME, PropertyDescriptorSupport.createBasetype(MODIFICATIONTIME, Timestamp.class,
                5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(COMMENT,
                PropertyDescriptorSupport.createBasetype(COMMENT, Comment.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(STATE, PropertyDescriptorSupport.createDatatype(STATE, WorkflowState.class, 7,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, WorkflowContext.class, 8,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowInstanceEntry.getPropertyDescriptor(OWNER), this.owner, null));
        properties
                .add(super.createProperty(WorkflowInstanceEntry.getPropertyDescriptor(MODIFIER), this.modifier, null));
        properties.add(super.createProperty(WorkflowInstanceEntry.getPropertyDescriptor(MODIFICATIONTIME),
                this.modificationTime, null));
        properties.add(super.createProperty(WorkflowInstanceEntry.getPropertyDescriptor(COMMENT), this.comment, null));
        properties.add(super.createProperty(WorkflowInstanceEntry.getPropertyDescriptor(STATE), this.getState(), null));
        properties.add(super.createProperty(WorkflowInstanceEntry.getPropertyDescriptor(CONTEXT), this.getContext(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFIER) && (property.getType() == Name.class))) {
            this.setModifier(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFICATIONTIME) && (property.getType() == Timestamp.class))) {
            this.setModificationTime(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMMENT) && (property.getType() == Comment.class))) {
            this.setComment(((Comment) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATE) && (property.getType() == WorkflowState.class))) {
            this.setState(((WorkflowState) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTEXT) && (property.getType() == WorkflowContext.class))) {
            this.setContext(((WorkflowContext) property.getInstance()));
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
        final WorkflowInstanceEntry other = ((WorkflowInstanceEntry) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.modifier == null)) {
            if ((other.modifier != null))
                return false;
        } else if ((!this.modifier.equals(other.modifier)))
            return false;
        if ((this.modificationTime == null)) {
            if ((other.modificationTime != null))
                return false;
        } else if ((!this.modificationTime.equals(other.modificationTime)))
            return false;
        if ((this.comment == null)) {
            if ((other.comment != null))
                return false;
        } else if ((!this.comment.equals(other.comment)))
            return false;
        if ((this.state == null)) {
            if ((other.state != null))
                return false;
        } else if ((!this.state.equals(other.state)))
            return false;
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.modifier == null) ? 0 : this.modifier.hashCode()));
        result = ((PRIME * result) + ((this.modificationTime == null) ? 0 : this.modificationTime.hashCode()));
        result = ((PRIME * result) + ((this.comment == null) ? 0 : this.comment.hashCode()));
        result = ((PRIME * result) + ((this.state == null) ? 0 : this.state.hashCode()));
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        return result;
    }

    @Override
    public WorkflowInstanceEntry cloneObject() {
        WorkflowInstanceEntry clone = new WorkflowInstanceEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The owner of the WorkflowEntry.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the WorkflowEntry.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the WorkflowEntry.
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
     * The name of the last maintainer.
     *
     * @return the Name.
     */
    public Name getModifier() {
        return this.modifier;
    }

    /**
     * The name of the last maintainer.
     *
     * @param modifier the Name.
     */
    public void setModifier(Name modifier) {
        this.modifier = modifier;
    }

    /**
     * The name of the last maintainer.
     *
     * @param modifier the String.
     */
    public void setModifier(String modifier) {
        if ((this.modifier == null)) {
            if ((modifier == null)) {
                return;
            }
            this.modifier = new Name();
        }
        this.modifier.setValue(modifier);
    }

    /**
     * The Date and Time of last modification.
     *
     * @return the Timestamp.
     */
    public Timestamp getModificationTime() {
        return this.modificationTime;
    }

    /**
     * The Date and Time of last modification.
     *
     * @param modificationTime the Timestamp.
     */
    public void setModificationTime(Timestamp modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * The Date and Time of last modification.
     *
     * @param modificationTime the Long.
     */
    public void setModificationTime(Long modificationTime) {
        if ((this.modificationTime == null)) {
            if ((modificationTime == null)) {
                return;
            }
            this.modificationTime = new Timestamp();
        }
        this.modificationTime.setValue(modificationTime);
    }

    /**
     * The last transition comment.
     *
     * @return the Comment.
     */
    public Comment getComment() {
        return this.comment;
    }

    /**
     * The last transition comment.
     *
     * @param comment the Comment.
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * The last transition comment.
     *
     * @param comment the String.
     */
    public void setComment(String comment) {
        if ((this.comment == null)) {
            if ((comment == null)) {
                return;
            }
            this.comment = new Comment();
        }
        this.comment.setValue(comment);
    }

    /**
     * The workflow state that the entry historicizes
     *
     * @param state the WorkflowState.
     */
    public void setState(WorkflowState state) {
        this.state = state;
    }

    /**
     * The workflow state that the entry historicizes
     *
     * @return the WorkflowState.
     */
    public WorkflowState getState() {
        return this.state;
    }

    /**
     * The instance context.
     *
     * @param context the WorkflowContext.
     */
    public void setContext(WorkflowContext context) {
        this.context = context;
    }

    /**
     * The instance context.
     *
     * @return the WorkflowContext.
     */
    public WorkflowContext getContext() {
        return this.context;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowInstanceEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowInstanceEntry.class).getAllProperties();
    }
}
