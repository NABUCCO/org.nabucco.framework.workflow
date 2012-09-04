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
package org.nabucco.framework.workflow.facade.message.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.text.TextContent;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * InitWorkflowRq<p/>Request message for starting a new workflow.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-12
 */
public class InitWorkflowRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l1,255;u0,n;m1,1;", "l3,12;u0,n;m0,1;",
            "l0,2147483647;u0,n;m0,1;", "m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;",
            "m1,1;", "m0,1;", "l0,n;u0,n;m0,1;", "l0,100000;u0,n;m0,1;" };

    public static final String WORKFLOW = "workflow";

    public static final String SUMMARY = "summary";

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String CONTEXT = "context";

    public static final String ASSIGNEDUSER = "assignedUser";

    public static final String ASSIGNEDGROUP = "assignedGroup";

    public static final String ASSIGNEDROLE = "assignedRole";

    public static final String FUNCTIONALTYPE = "functionalType";

    public static final String PRIORITY = "priority";

    public static final String DUEDATE = "dueDate";

    public static final String COMMENT = "comment";

    /** The workflow definition to start a new instance for. */
    private WorkflowDefinition workflow;

    /** The summary of the new workflow instance. */
    private Description summary;

    /** Owner of the new created workflow instance. */
    private Owner owner;

    /** The description of the new workflow instance. */
    private TextContent description;

    /** Context information for the initial state. */
    private WorkflowContext context;

    /** The assigned user. */
    private Name assignedUser;

    /** The assigned group. */
    private Name assignedGroup;

    /** The assigned role. */
    private Name assignedRole;

    /** Type of the task scenario. */
    private Code functionalType;

    protected static final String FUNCTIONALTYPE_CODEPATH = "nabucco.workflow.functionaltype";

    /** Priority of the task scenario. */
    private Code priority;

    protected static final String PRIORITY_CODEPATH = "nabucco.workflow.priority";

    /** Due date of the instance. */
    private Date dueDate;

    /** An optional workflow execution comment. */
    private Comment comment;

    /** Constructs a new InitWorkflowRq instance. */
    public InitWorkflowRq() {
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
        propertyMap.put(WORKFLOW, PropertyDescriptorSupport.createDatatype(WORKFLOW, WorkflowDefinition.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap
                .put(SUMMARY, PropertyDescriptorSupport.createBasetype(SUMMARY, Description.class, 1,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, TextContent.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, WorkflowContext.class, 4,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ASSIGNEDUSER,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDUSER, Name.class, 5, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(ASSIGNEDGROUP,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDGROUP, Name.class, 6, PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(ASSIGNEDROLE,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDROLE, Name.class, 7, PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(FUNCTIONALTYPE, PropertyDescriptorSupport.createDatatype(FUNCTIONALTYPE, Code.class, 8,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPONENT, FUNCTIONALTYPE_CODEPATH));
        propertyMap.put(PRIORITY, PropertyDescriptorSupport.createDatatype(PRIORITY, Code.class, 9,
                PROPERTY_CONSTRAINTS[9], false, PropertyAssociationType.COMPONENT, PRIORITY_CODEPATH));
        propertyMap.put(DUEDATE,
                PropertyDescriptorSupport.createBasetype(DUEDATE, Date.class, 10, PROPERTY_CONSTRAINTS[10], false));
        propertyMap.put(COMMENT,
                PropertyDescriptorSupport.createBasetype(COMMENT, Comment.class, 11, PROPERTY_CONSTRAINTS[11], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(WORKFLOW), this.getWorkflow()));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(SUMMARY), this.summary));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(OWNER), this.owner));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(DESCRIPTION), this.description));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(CONTEXT), this.getContext()));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(ASSIGNEDUSER), this.assignedUser));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(ASSIGNEDGROUP), this.assignedGroup));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(ASSIGNEDROLE), this.assignedRole));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(FUNCTIONALTYPE),
                this.getFunctionalType()));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(PRIORITY), this.getPriority()));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(DUEDATE), this.dueDate));
        properties.add(super.createProperty(InitWorkflowRq.getPropertyDescriptor(COMMENT), this.comment));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WORKFLOW) && (property.getType() == WorkflowDefinition.class))) {
            this.setWorkflow(((WorkflowDefinition) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUMMARY) && (property.getType() == Description.class))) {
            this.setSummary(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == TextContent.class))) {
            this.setDescription(((TextContent) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTEXT) && (property.getType() == WorkflowContext.class))) {
            this.setContext(((WorkflowContext) property.getInstance()));
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
        } else if ((property.getName().equals(FUNCTIONALTYPE) && (property.getType() == Code.class))) {
            this.setFunctionalType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PRIORITY) && (property.getType() == Code.class))) {
            this.setPriority(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DUEDATE) && (property.getType() == Date.class))) {
            this.setDueDate(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMMENT) && (property.getType() == Comment.class))) {
            this.setComment(((Comment) property.getInstance()));
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
        final InitWorkflowRq other = ((InitWorkflowRq) obj);
        if ((this.workflow == null)) {
            if ((other.workflow != null))
                return false;
        } else if ((!this.workflow.equals(other.workflow)))
            return false;
        if ((this.summary == null)) {
            if ((other.summary != null))
                return false;
        } else if ((!this.summary.equals(other.summary)))
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
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
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
        if ((this.functionalType == null)) {
            if ((other.functionalType != null))
                return false;
        } else if ((!this.functionalType.equals(other.functionalType)))
            return false;
        if ((this.priority == null)) {
            if ((other.priority != null))
                return false;
        } else if ((!this.priority.equals(other.priority)))
            return false;
        if ((this.dueDate == null)) {
            if ((other.dueDate != null))
                return false;
        } else if ((!this.dueDate.equals(other.dueDate)))
            return false;
        if ((this.comment == null)) {
            if ((other.comment != null))
                return false;
        } else if ((!this.comment.equals(other.comment)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.workflow == null) ? 0 : this.workflow.hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        result = ((PRIME * result) + ((this.assignedUser == null) ? 0 : this.assignedUser.hashCode()));
        result = ((PRIME * result) + ((this.assignedGroup == null) ? 0 : this.assignedGroup.hashCode()));
        result = ((PRIME * result) + ((this.assignedRole == null) ? 0 : this.assignedRole.hashCode()));
        result = ((PRIME * result) + ((this.functionalType == null) ? 0 : this.functionalType.hashCode()));
        result = ((PRIME * result) + ((this.priority == null) ? 0 : this.priority.hashCode()));
        result = ((PRIME * result) + ((this.dueDate == null) ? 0 : this.dueDate.hashCode()));
        result = ((PRIME * result) + ((this.comment == null) ? 0 : this.comment.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The workflow definition to start a new instance for.
     *
     * @return the WorkflowDefinition.
     */
    public WorkflowDefinition getWorkflow() {
        return this.workflow;
    }

    /**
     * The workflow definition to start a new instance for.
     *
     * @param workflow the WorkflowDefinition.
     */
    public void setWorkflow(WorkflowDefinition workflow) {
        this.workflow = workflow;
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
     * Owner of the new created workflow instance.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the new created workflow instance.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The description of the new workflow instance.
     *
     * @return the TextContent.
     */
    public TextContent getDescription() {
        return this.description;
    }

    /**
     * The description of the new workflow instance.
     *
     * @param description the TextContent.
     */
    public void setDescription(TextContent description) {
        this.description = description;
    }

    /**
     * Context information for the initial state.
     *
     * @return the WorkflowContext.
     */
    public WorkflowContext getContext() {
        return this.context;
    }

    /**
     * Context information for the initial state.
     *
     * @param context the WorkflowContext.
     */
    public void setContext(WorkflowContext context) {
        this.context = context;
    }

    /**
     * The assigned user.
     *
     * @return the Name.
     */
    public Name getAssignedUser() {
        return this.assignedUser;
    }

    /**
     * The assigned user.
     *
     * @param assignedUser the Name.
     */
    public void setAssignedUser(Name assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * The assigned group.
     *
     * @return the Name.
     */
    public Name getAssignedGroup() {
        return this.assignedGroup;
    }

    /**
     * The assigned group.
     *
     * @param assignedGroup the Name.
     */
    public void setAssignedGroup(Name assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    /**
     * The assigned role.
     *
     * @return the Name.
     */
    public Name getAssignedRole() {
        return this.assignedRole;
    }

    /**
     * The assigned role.
     *
     * @param assignedRole the Name.
     */
    public void setAssignedRole(Name assignedRole) {
        this.assignedRole = assignedRole;
    }

    /**
     * Type of the task scenario.
     *
     * @return the Code.
     */
    public Code getFunctionalType() {
        return this.functionalType;
    }

    /**
     * Type of the task scenario.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        this.functionalType = functionalType;
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
     * Priority of the task scenario.
     *
     * @param priority the Code.
     */
    public void setPriority(Code priority) {
        this.priority = priority;
    }

    /**
     * Due date of the instance.
     *
     * @return the Date.
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    /**
     * Due date of the instance.
     *
     * @param dueDate the Date.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * An optional workflow execution comment.
     *
     * @return the Comment.
     */
    public Comment getComment() {
        return this.comment;
    }

    /**
     * An optional workflow execution comment.
     *
     * @param comment the Comment.
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(InitWorkflowRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InitWorkflowRq.class).getAllProperties();
    }

    /**
     * Getter for the FunctionalTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getFunctionalTypeCodePath() {
        return new CodePath(FUNCTIONALTYPE_CODEPATH);
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
