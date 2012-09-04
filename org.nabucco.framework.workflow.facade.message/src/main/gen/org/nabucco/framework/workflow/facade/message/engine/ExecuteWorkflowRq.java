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
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.Signal;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * ExecuteWorkflowRq<p/>Message containing the trigger for workflow transitions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public class ExecuteWorkflowRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;", "m0,1;", "l0,255;u0,n;m0,1;",
            "l0,100000;u0,n;m0,1;" };

    public static final String INSTANCE = "instance";

    public static final String CONTEXT = "context";

    public static final String SIGNAL = "signal";

    public static final String TIMER = "timer";

    public static final String COMMENT = "comment";

    /** The current workflow instance. */
    private WorkflowInstance instance;

    /** Context information for the new state. */
    private WorkflowContext context;

    /** The triggered signal (XOR with timer)! */
    private Signal signal;

    /** The triggered timer (XOR with signal)! */
    private Name timer;

    /** An optional workflow execution comment. */
    private Comment comment;

    /** Constructs a new ExecuteWorkflowRq instance. */
    public ExecuteWorkflowRq() {
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
        propertyMap.put(INSTANCE, PropertyDescriptorSupport.createDatatype(INSTANCE, WorkflowInstance.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, WorkflowContext.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SIGNAL, PropertyDescriptorSupport.createDatatype(SIGNAL, Signal.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(TIMER,
                PropertyDescriptorSupport.createBasetype(TIMER, Name.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(COMMENT,
                PropertyDescriptorSupport.createBasetype(COMMENT, Comment.class, 4, PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ExecuteWorkflowRq.getPropertyDescriptor(INSTANCE), this.getInstance()));
        properties.add(super.createProperty(ExecuteWorkflowRq.getPropertyDescriptor(CONTEXT), this.getContext()));
        properties.add(super.createProperty(ExecuteWorkflowRq.getPropertyDescriptor(SIGNAL), this.getSignal()));
        properties.add(super.createProperty(ExecuteWorkflowRq.getPropertyDescriptor(TIMER), this.timer));
        properties.add(super.createProperty(ExecuteWorkflowRq.getPropertyDescriptor(COMMENT), this.comment));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INSTANCE) && (property.getType() == WorkflowInstance.class))) {
            this.setInstance(((WorkflowInstance) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTEXT) && (property.getType() == WorkflowContext.class))) {
            this.setContext(((WorkflowContext) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNAL) && (property.getType() == Signal.class))) {
            this.setSignal(((Signal) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TIMER) && (property.getType() == Name.class))) {
            this.setTimer(((Name) property.getInstance()));
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
        final ExecuteWorkflowRq other = ((ExecuteWorkflowRq) obj);
        if ((this.instance == null)) {
            if ((other.instance != null))
                return false;
        } else if ((!this.instance.equals(other.instance)))
            return false;
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
            return false;
        if ((this.signal == null)) {
            if ((other.signal != null))
                return false;
        } else if ((!this.signal.equals(other.signal)))
            return false;
        if ((this.timer == null)) {
            if ((other.timer != null))
                return false;
        } else if ((!this.timer.equals(other.timer)))
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
        result = ((PRIME * result) + ((this.instance == null) ? 0 : this.instance.hashCode()));
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        result = ((PRIME * result) + ((this.signal == null) ? 0 : this.signal.hashCode()));
        result = ((PRIME * result) + ((this.timer == null) ? 0 : this.timer.hashCode()));
        result = ((PRIME * result) + ((this.comment == null) ? 0 : this.comment.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The current workflow instance.
     *
     * @return the WorkflowInstance.
     */
    public WorkflowInstance getInstance() {
        return this.instance;
    }

    /**
     * The current workflow instance.
     *
     * @param instance the WorkflowInstance.
     */
    public void setInstance(WorkflowInstance instance) {
        this.instance = instance;
    }

    /**
     * Context information for the new state.
     *
     * @return the WorkflowContext.
     */
    public WorkflowContext getContext() {
        return this.context;
    }

    /**
     * Context information for the new state.
     *
     * @param context the WorkflowContext.
     */
    public void setContext(WorkflowContext context) {
        this.context = context;
    }

    /**
     * The triggered signal (XOR with timer)!
     *
     * @return the Signal.
     */
    public Signal getSignal() {
        return this.signal;
    }

    /**
     * The triggered signal (XOR with timer)!
     *
     * @param signal the Signal.
     */
    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    /**
     * The triggered timer (XOR with signal)!
     *
     * @return the Name.
     */
    public Name getTimer() {
        return this.timer;
    }

    /**
     * The triggered timer (XOR with signal)!
     *
     * @param timer the Name.
     */
    public void setTimer(Name timer) {
        this.timer = timer;
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
        return PropertyCache.getInstance().retrieve(ExecuteWorkflowRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ExecuteWorkflowRq.class).getAllProperties();
    }
}
