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
package org.nabucco.framework.workflow.facade.datatype.instance.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.instance.search.WorkflowInstanceAssignee;

/**
 * TaskSearchParameter<p/>The search parameter for searching tasks.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class TaskSearchParameter extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m0,1;", "l0,255;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "m0,1;", "m0,n;", "m0,n;", "l0,255;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "m0,1;" };

    public static final String OWNER = "owner";

    public static final String NAME = "name";

    public static final String CREATOR = "creator";

    public static final String MODIFIER = "modifier";

    public static final String ASSIGNEDUSER = "assignedUser";

    public static final String ASSIGNEDGROUPS = "assignedGroups";

    public static final String ASSIGNEDROLES = "assignedRoles";

    public static final String SUMMARY = "summary";

    public static final String CREATIONTIMEFROM = "creationTimeFrom";

    public static final String CREATIONTIMETO = "creationTimeTo";

    public static final String MODIFICATIONTIMEFROM = "modificationTimeFrom";

    public static final String MODIFICATIONTIMETO = "modificationTimeTo";

    public static final String DUEDATEFROM = "dueDateFrom";

    public static final String DUEDATETO = "dueDateTo";

    public static final String PRIORITY = "priority";

    /** Owner of the task */
    private Owner owner;

    /** Name of the task. */
    private Name name;

    /** Name of the task creator. */
    private Name creator;

    /** Name of the task modifier. */
    private Name modifier;

    /** The current task assignee. */
    private WorkflowInstanceAssignee assignedUser;

    /** The current task assigned groups (OR). */
    private NabuccoList<WorkflowInstanceAssignee> assignedGroups;

    /** The current task assigned roles (OR). */
    private NabuccoList<WorkflowInstanceAssignee> assignedRoles;

    /** Summary of the task. */
    private Description summary;

    /** Earliest creation time. */
    private Timestamp creationTimeFrom;

    /** Latest creation time. */
    private Timestamp creationTimeTo;

    /** Earliest modification time. */
    private Timestamp modificationTimeFrom;

    /** Latest modification time. */
    private Timestamp modificationTimeTo;

    /** Earliest due date. */
    private Date dueDateFrom;

    /** Latest due date. */
    private Date dueDateTo;

    /** Priority of the task scenario. */
    private Code priority;

    private Long priorityRefId;

    protected static final String PRIORITY_CODEPATH = "nabucco.workflow.priority";

    /** Constructs a new TaskSearchParameter instance. */
    public TaskSearchParameter() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TaskSearchParameter.
     */
    protected void cloneObject(TaskSearchParameter clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getCreator() != null)) {
            clone.setCreator(this.getCreator().cloneObject());
        }
        if ((this.getModifier() != null)) {
            clone.setModifier(this.getModifier().cloneObject());
        }
        if ((this.getAssignedUser() != null)) {
            clone.setAssignedUser(this.getAssignedUser().cloneObject());
        }
        if ((this.assignedGroups != null)) {
            clone.assignedGroups = this.assignedGroups.cloneCollection();
        }
        if ((this.assignedRoles != null)) {
            clone.assignedRoles = this.assignedRoles.cloneCollection();
        }
        if ((this.getSummary() != null)) {
            clone.setSummary(this.getSummary().cloneObject());
        }
        if ((this.getCreationTimeFrom() != null)) {
            clone.setCreationTimeFrom(this.getCreationTimeFrom().cloneObject());
        }
        if ((this.getCreationTimeTo() != null)) {
            clone.setCreationTimeTo(this.getCreationTimeTo().cloneObject());
        }
        if ((this.getModificationTimeFrom() != null)) {
            clone.setModificationTimeFrom(this.getModificationTimeFrom().cloneObject());
        }
        if ((this.getModificationTimeTo() != null)) {
            clone.setModificationTimeTo(this.getModificationTimeTo().cloneObject());
        }
        if ((this.getDueDateFrom() != null)) {
            clone.setDueDateFrom(this.getDueDateFrom().cloneObject());
        }
        if ((this.getDueDateTo() != null)) {
            clone.setDueDateTo(this.getDueDateTo().cloneObject());
        }
        if ((this.getPriority() != null)) {
            clone.setPriority(this.getPriority().cloneObject());
        }
        if ((this.getPriorityRefId() != null)) {
            clone.setPriorityRefId(this.getPriorityRefId());
        }
    }

    /**
     * Getter for the AssignedGroupsJPA.
     *
     * @return the List<WorkflowInstanceAssignee>.
     */
    List<WorkflowInstanceAssignee> getAssignedGroupsJPA() {
        if ((this.assignedGroups == null)) {
            this.assignedGroups = new NabuccoListImpl<WorkflowInstanceAssignee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowInstanceAssignee>) this.assignedGroups).getDelegate();
    }

    /**
     * Setter for the AssignedGroupsJPA.
     *
     * @param assignedGroups the List<WorkflowInstanceAssignee>.
     */
    void setAssignedGroupsJPA(List<WorkflowInstanceAssignee> assignedGroups) {
        if ((this.assignedGroups == null)) {
            this.assignedGroups = new NabuccoListImpl<WorkflowInstanceAssignee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowInstanceAssignee>) this.assignedGroups).setDelegate(assignedGroups);
    }

    /**
     * Getter for the AssignedRolesJPA.
     *
     * @return the List<WorkflowInstanceAssignee>.
     */
    List<WorkflowInstanceAssignee> getAssignedRolesJPA() {
        if ((this.assignedRoles == null)) {
            this.assignedRoles = new NabuccoListImpl<WorkflowInstanceAssignee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowInstanceAssignee>) this.assignedRoles).getDelegate();
    }

    /**
     * Setter for the AssignedRolesJPA.
     *
     * @param assignedRoles the List<WorkflowInstanceAssignee>.
     */
    void setAssignedRolesJPA(List<WorkflowInstanceAssignee> assignedRoles) {
        if ((this.assignedRoles == null)) {
            this.assignedRoles = new NabuccoListImpl<WorkflowInstanceAssignee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowInstanceAssignee>) this.assignedRoles).setDelegate(assignedRoles);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(CREATOR,
                PropertyDescriptorSupport.createBasetype(CREATOR, Name.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(MODIFIER,
                PropertyDescriptorSupport.createBasetype(MODIFIER, Name.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap
                .put(ASSIGNEDUSER, PropertyDescriptorSupport.createDatatype(ASSIGNEDUSER,
                        WorkflowInstanceAssignee.class, 4, PROPERTY_CONSTRAINTS[4], false,
                        PropertyAssociationType.COMPOSITION));
        propertyMap
                .put(ASSIGNEDGROUPS, PropertyDescriptorSupport.createCollection(ASSIGNEDGROUPS,
                        WorkflowInstanceAssignee.class, 5, PROPERTY_CONSTRAINTS[5], false,
                        PropertyAssociationType.COMPOSITION));
        propertyMap
                .put(ASSIGNEDROLES, PropertyDescriptorSupport.createCollection(ASSIGNEDROLES,
                        WorkflowInstanceAssignee.class, 6, PROPERTY_CONSTRAINTS[6], false,
                        PropertyAssociationType.COMPOSITION));
        propertyMap
                .put(SUMMARY, PropertyDescriptorSupport.createBasetype(SUMMARY, Description.class, 7,
                        PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(CREATIONTIMEFROM, PropertyDescriptorSupport.createBasetype(CREATIONTIMEFROM, Timestamp.class,
                8, PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(CREATIONTIMETO, PropertyDescriptorSupport.createBasetype(CREATIONTIMETO, Timestamp.class, 9,
                PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(MODIFICATIONTIMEFROM, PropertyDescriptorSupport.createBasetype(MODIFICATIONTIMEFROM,
                Timestamp.class, 10, PROPERTY_CONSTRAINTS[10], false));
        propertyMap.put(MODIFICATIONTIMETO, PropertyDescriptorSupport.createBasetype(MODIFICATIONTIMETO,
                Timestamp.class, 11, PROPERTY_CONSTRAINTS[11], false));
        propertyMap.put(DUEDATEFROM,
                PropertyDescriptorSupport.createBasetype(DUEDATEFROM, Date.class, 12, PROPERTY_CONSTRAINTS[12], false));
        propertyMap.put(DUEDATETO,
                PropertyDescriptorSupport.createBasetype(DUEDATETO, Date.class, 13, PROPERTY_CONSTRAINTS[13], false));
        propertyMap.put(PRIORITY, PropertyDescriptorSupport.createDatatype(PRIORITY, Code.class, 14,
                PROPERTY_CONSTRAINTS[14], false, PropertyAssociationType.COMPONENT, PRIORITY_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(CREATOR), this.creator, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(MODIFIER), this.modifier, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(ASSIGNEDUSER),
                this.getAssignedUser(), null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(ASSIGNEDGROUPS),
                this.assignedGroups, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(ASSIGNEDROLES),
                this.assignedRoles, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(SUMMARY), this.summary, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(CREATIONTIMEFROM),
                this.creationTimeFrom, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(CREATIONTIMETO),
                this.creationTimeTo, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(MODIFICATIONTIMEFROM),
                this.modificationTimeFrom, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(MODIFICATIONTIMETO),
                this.modificationTimeTo, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(DUEDATEFROM), this.dueDateFrom,
                null));
        properties
                .add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(DUEDATETO), this.dueDateTo, null));
        properties.add(super.createProperty(TaskSearchParameter.getPropertyDescriptor(PRIORITY), this.getPriority(),
                this.priorityRefId));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATOR) && (property.getType() == Name.class))) {
            this.setCreator(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFIER) && (property.getType() == Name.class))) {
            this.setModifier(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDUSER) && (property.getType() == WorkflowInstanceAssignee.class))) {
            this.setAssignedUser(((WorkflowInstanceAssignee) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDGROUPS) && (property.getType() == WorkflowInstanceAssignee.class))) {
            this.assignedGroups = ((NabuccoList<WorkflowInstanceAssignee>) property.getInstance());
            return true;
        } else if ((property.getName().equals(ASSIGNEDROLES) && (property.getType() == WorkflowInstanceAssignee.class))) {
            this.assignedRoles = ((NabuccoList<WorkflowInstanceAssignee>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SUMMARY) && (property.getType() == Description.class))) {
            this.setSummary(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATIONTIMEFROM) && (property.getType() == Timestamp.class))) {
            this.setCreationTimeFrom(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATIONTIMETO) && (property.getType() == Timestamp.class))) {
            this.setCreationTimeTo(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFICATIONTIMEFROM) && (property.getType() == Timestamp.class))) {
            this.setModificationTimeFrom(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFICATIONTIMETO) && (property.getType() == Timestamp.class))) {
            this.setModificationTimeTo(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DUEDATEFROM) && (property.getType() == Date.class))) {
            this.setDueDateFrom(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DUEDATETO) && (property.getType() == Date.class))) {
            this.setDueDateTo(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PRIORITY) && (property.getType() == Code.class))) {
            this.setPriority(((Code) property.getInstance()));
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
        final TaskSearchParameter other = ((TaskSearchParameter) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.creator == null)) {
            if ((other.creator != null))
                return false;
        } else if ((!this.creator.equals(other.creator)))
            return false;
        if ((this.modifier == null)) {
            if ((other.modifier != null))
                return false;
        } else if ((!this.modifier.equals(other.modifier)))
            return false;
        if ((this.assignedUser == null)) {
            if ((other.assignedUser != null))
                return false;
        } else if ((!this.assignedUser.equals(other.assignedUser)))
            return false;
        if ((this.summary == null)) {
            if ((other.summary != null))
                return false;
        } else if ((!this.summary.equals(other.summary)))
            return false;
        if ((this.creationTimeFrom == null)) {
            if ((other.creationTimeFrom != null))
                return false;
        } else if ((!this.creationTimeFrom.equals(other.creationTimeFrom)))
            return false;
        if ((this.creationTimeTo == null)) {
            if ((other.creationTimeTo != null))
                return false;
        } else if ((!this.creationTimeTo.equals(other.creationTimeTo)))
            return false;
        if ((this.modificationTimeFrom == null)) {
            if ((other.modificationTimeFrom != null))
                return false;
        } else if ((!this.modificationTimeFrom.equals(other.modificationTimeFrom)))
            return false;
        if ((this.modificationTimeTo == null)) {
            if ((other.modificationTimeTo != null))
                return false;
        } else if ((!this.modificationTimeTo.equals(other.modificationTimeTo)))
            return false;
        if ((this.dueDateFrom == null)) {
            if ((other.dueDateFrom != null))
                return false;
        } else if ((!this.dueDateFrom.equals(other.dueDateFrom)))
            return false;
        if ((this.dueDateTo == null)) {
            if ((other.dueDateTo != null))
                return false;
        } else if ((!this.dueDateTo.equals(other.dueDateTo)))
            return false;
        if ((this.priority == null)) {
            if ((other.priority != null))
                return false;
        } else if ((!this.priority.equals(other.priority)))
            return false;
        if ((this.priorityRefId == null)) {
            if ((other.priorityRefId != null))
                return false;
        } else if ((!this.priorityRefId.equals(other.priorityRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.creator == null) ? 0 : this.creator.hashCode()));
        result = ((PRIME * result) + ((this.modifier == null) ? 0 : this.modifier.hashCode()));
        result = ((PRIME * result) + ((this.assignedUser == null) ? 0 : this.assignedUser.hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((PRIME * result) + ((this.creationTimeFrom == null) ? 0 : this.creationTimeFrom.hashCode()));
        result = ((PRIME * result) + ((this.creationTimeTo == null) ? 0 : this.creationTimeTo.hashCode()));
        result = ((PRIME * result) + ((this.modificationTimeFrom == null) ? 0 : this.modificationTimeFrom.hashCode()));
        result = ((PRIME * result) + ((this.modificationTimeTo == null) ? 0 : this.modificationTimeTo.hashCode()));
        result = ((PRIME * result) + ((this.dueDateFrom == null) ? 0 : this.dueDateFrom.hashCode()));
        result = ((PRIME * result) + ((this.dueDateTo == null) ? 0 : this.dueDateTo.hashCode()));
        result = ((PRIME * result) + ((this.priority == null) ? 0 : this.priority.hashCode()));
        result = ((PRIME * result) + ((this.priorityRefId == null) ? 0 : this.priorityRefId.hashCode()));
        return result;
    }

    @Override
    public TaskSearchParameter cloneObject() {
        TaskSearchParameter clone = new TaskSearchParameter();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Owner of the task
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the task
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the task
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
     * Name of the task.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Name of the task.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Name of the task.
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
     * Name of the task creator.
     *
     * @return the Name.
     */
    public Name getCreator() {
        return this.creator;
    }

    /**
     * Name of the task creator.
     *
     * @param creator the Name.
     */
    public void setCreator(Name creator) {
        this.creator = creator;
    }

    /**
     * Name of the task creator.
     *
     * @param creator the String.
     */
    public void setCreator(String creator) {
        if ((this.creator == null)) {
            if ((creator == null)) {
                return;
            }
            this.creator = new Name();
        }
        this.creator.setValue(creator);
    }

    /**
     * Name of the task modifier.
     *
     * @return the Name.
     */
    public Name getModifier() {
        return this.modifier;
    }

    /**
     * Name of the task modifier.
     *
     * @param modifier the Name.
     */
    public void setModifier(Name modifier) {
        this.modifier = modifier;
    }

    /**
     * Name of the task modifier.
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
     * The current task assignee.
     *
     * @param assignedUser the WorkflowInstanceAssignee.
     */
    public void setAssignedUser(WorkflowInstanceAssignee assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * The current task assignee.
     *
     * @return the WorkflowInstanceAssignee.
     */
    public WorkflowInstanceAssignee getAssignedUser() {
        return this.assignedUser;
    }

    /**
     * The current task assigned groups (OR).
     *
     * @return the NabuccoList<WorkflowInstanceAssignee>.
     */
    public NabuccoList<WorkflowInstanceAssignee> getAssignedGroups() {
        if ((this.assignedGroups == null)) {
            this.assignedGroups = new NabuccoListImpl<WorkflowInstanceAssignee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.assignedGroups;
    }

    /**
     * The current task assigned roles (OR).
     *
     * @return the NabuccoList<WorkflowInstanceAssignee>.
     */
    public NabuccoList<WorkflowInstanceAssignee> getAssignedRoles() {
        if ((this.assignedRoles == null)) {
            this.assignedRoles = new NabuccoListImpl<WorkflowInstanceAssignee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.assignedRoles;
    }

    /**
     * Summary of the task.
     *
     * @return the Description.
     */
    public Description getSummary() {
        return this.summary;
    }

    /**
     * Summary of the task.
     *
     * @param summary the Description.
     */
    public void setSummary(Description summary) {
        this.summary = summary;
    }

    /**
     * Summary of the task.
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
     * Earliest creation time.
     *
     * @return the Timestamp.
     */
    public Timestamp getCreationTimeFrom() {
        return this.creationTimeFrom;
    }

    /**
     * Earliest creation time.
     *
     * @param creationTimeFrom the Timestamp.
     */
    public void setCreationTimeFrom(Timestamp creationTimeFrom) {
        this.creationTimeFrom = creationTimeFrom;
    }

    /**
     * Earliest creation time.
     *
     * @param creationTimeFrom the Long.
     */
    public void setCreationTimeFrom(Long creationTimeFrom) {
        if ((this.creationTimeFrom == null)) {
            if ((creationTimeFrom == null)) {
                return;
            }
            this.creationTimeFrom = new Timestamp();
        }
        this.creationTimeFrom.setValue(creationTimeFrom);
    }

    /**
     * Latest creation time.
     *
     * @return the Timestamp.
     */
    public Timestamp getCreationTimeTo() {
        return this.creationTimeTo;
    }

    /**
     * Latest creation time.
     *
     * @param creationTimeTo the Timestamp.
     */
    public void setCreationTimeTo(Timestamp creationTimeTo) {
        this.creationTimeTo = creationTimeTo;
    }

    /**
     * Latest creation time.
     *
     * @param creationTimeTo the Long.
     */
    public void setCreationTimeTo(Long creationTimeTo) {
        if ((this.creationTimeTo == null)) {
            if ((creationTimeTo == null)) {
                return;
            }
            this.creationTimeTo = new Timestamp();
        }
        this.creationTimeTo.setValue(creationTimeTo);
    }

    /**
     * Earliest modification time.
     *
     * @return the Timestamp.
     */
    public Timestamp getModificationTimeFrom() {
        return this.modificationTimeFrom;
    }

    /**
     * Earliest modification time.
     *
     * @param modificationTimeFrom the Timestamp.
     */
    public void setModificationTimeFrom(Timestamp modificationTimeFrom) {
        this.modificationTimeFrom = modificationTimeFrom;
    }

    /**
     * Earliest modification time.
     *
     * @param modificationTimeFrom the Long.
     */
    public void setModificationTimeFrom(Long modificationTimeFrom) {
        if ((this.modificationTimeFrom == null)) {
            if ((modificationTimeFrom == null)) {
                return;
            }
            this.modificationTimeFrom = new Timestamp();
        }
        this.modificationTimeFrom.setValue(modificationTimeFrom);
    }

    /**
     * Latest modification time.
     *
     * @return the Timestamp.
     */
    public Timestamp getModificationTimeTo() {
        return this.modificationTimeTo;
    }

    /**
     * Latest modification time.
     *
     * @param modificationTimeTo the Timestamp.
     */
    public void setModificationTimeTo(Timestamp modificationTimeTo) {
        this.modificationTimeTo = modificationTimeTo;
    }

    /**
     * Latest modification time.
     *
     * @param modificationTimeTo the Long.
     */
    public void setModificationTimeTo(Long modificationTimeTo) {
        if ((this.modificationTimeTo == null)) {
            if ((modificationTimeTo == null)) {
                return;
            }
            this.modificationTimeTo = new Timestamp();
        }
        this.modificationTimeTo.setValue(modificationTimeTo);
    }

    /**
     * Earliest due date.
     *
     * @return the Date.
     */
    public Date getDueDateFrom() {
        return this.dueDateFrom;
    }

    /**
     * Earliest due date.
     *
     * @param dueDateFrom the Date.
     */
    public void setDueDateFrom(Date dueDateFrom) {
        this.dueDateFrom = dueDateFrom;
    }

    /**
     * Earliest due date.
     *
     * @param dueDateFrom the java.util.Date.
     */
    public void setDueDateFrom(java.util.Date dueDateFrom) {
        if ((this.dueDateFrom == null)) {
            if ((dueDateFrom == null)) {
                return;
            }
            this.dueDateFrom = new Date();
        }
        this.dueDateFrom.setValue(dueDateFrom);
    }

    /**
     * Latest due date.
     *
     * @return the Date.
     */
    public Date getDueDateTo() {
        return this.dueDateTo;
    }

    /**
     * Latest due date.
     *
     * @param dueDateTo the Date.
     */
    public void setDueDateTo(Date dueDateTo) {
        this.dueDateTo = dueDateTo;
    }

    /**
     * Latest due date.
     *
     * @param dueDateTo the java.util.Date.
     */
    public void setDueDateTo(java.util.Date dueDateTo) {
        if ((this.dueDateTo == null)) {
            if ((dueDateTo == null)) {
                return;
            }
            this.dueDateTo = new Date();
        }
        this.dueDateTo.setValue(dueDateTo);
    }

    /**
     * Priority of the task scenario.
     *
     * @param priority the Code.
     */
    public void setPriority(Code priority) {
        this.priority = priority;
        if ((priority != null)) {
            this.setPriorityRefId(priority.getId());
        } else {
            this.setPriorityRefId(null);
        }
    }

    /**
     * Priority of the task scenario.
     *
     * @return the Code.
     */
    public Code getPriority() {
        return this.priority;
    }

    /**
     * Getter for the PriorityRefId.
     *
     * @return the Long.
     */
    public Long getPriorityRefId() {
        return this.priorityRefId;
    }

    /**
     * Setter for the PriorityRefId.
     *
     * @param priorityRefId the Long.
     */
    public void setPriorityRefId(Long priorityRefId) {
        this.priorityRefId = priorityRefId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TaskSearchParameter.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TaskSearchParameter.class).getAllProperties();
    }

    /**
     * Getter for the PriorityCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getPriorityCodePath() {
        return new CodePath(PRIORITY_CODEPATH);
    }
}
