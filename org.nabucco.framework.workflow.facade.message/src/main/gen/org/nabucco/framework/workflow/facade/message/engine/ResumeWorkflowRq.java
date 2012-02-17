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
package org.nabucco.framework.workflow.facade.message.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * ResumeWorkflowRq<p/>Message containing the WorkflowInstance to resume.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2012-01-27
 */
public class ResumeWorkflowRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String WORKFLOWINSTANCE = "workflowInstance";

    public static final String WORKFLOWCONTEXT = "workflowContext";

    /** The Workflow Instance to resume. */
    private WorkflowInstance workflowInstance;

    /** The Workflow Context. */
    private WorkflowContext workflowContext;

    /** Constructs a new ResumeWorkflowRq instance. */
    public ResumeWorkflowRq() {
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
        propertyMap.put(WORKFLOWINSTANCE, PropertyDescriptorSupport.createDatatype(WORKFLOWINSTANCE,
                WorkflowInstance.class, 0, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WORKFLOWCONTEXT, PropertyDescriptorSupport.createDatatype(WORKFLOWCONTEXT,
                WorkflowContext.class, 1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ResumeWorkflowRq.getPropertyDescriptor(WORKFLOWINSTANCE),
                this.getWorkflowInstance()));
        properties.add(super.createProperty(ResumeWorkflowRq.getPropertyDescriptor(WORKFLOWCONTEXT),
                this.getWorkflowContext()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WORKFLOWINSTANCE) && (property.getType() == WorkflowInstance.class))) {
            this.setWorkflowInstance(((WorkflowInstance) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WORKFLOWCONTEXT) && (property.getType() == WorkflowContext.class))) {
            this.setWorkflowContext(((WorkflowContext) property.getInstance()));
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
        final ResumeWorkflowRq other = ((ResumeWorkflowRq) obj);
        if ((this.workflowInstance == null)) {
            if ((other.workflowInstance != null))
                return false;
        } else if ((!this.workflowInstance.equals(other.workflowInstance)))
            return false;
        if ((this.workflowContext == null)) {
            if ((other.workflowContext != null))
                return false;
        } else if ((!this.workflowContext.equals(other.workflowContext)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.workflowInstance == null) ? 0 : this.workflowInstance.hashCode()));
        result = ((PRIME * result) + ((this.workflowContext == null) ? 0 : this.workflowContext.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The Workflow Instance to resume.
     *
     * @return the WorkflowInstance.
     */
    public WorkflowInstance getWorkflowInstance() {
        return this.workflowInstance;
    }

    /**
     * The Workflow Instance to resume.
     *
     * @param workflowInstance the WorkflowInstance.
     */
    public void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    /**
     * The Workflow Context.
     *
     * @return the WorkflowContext.
     */
    public WorkflowContext getWorkflowContext() {
        return this.workflowContext;
    }

    /**
     * The Workflow Context.
     *
     * @param workflowContext the WorkflowContext.
     */
    public void setWorkflowContext(WorkflowContext workflowContext) {
        this.workflowContext = workflowContext;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ResumeWorkflowRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ResumeWorkflowRq.class).getAllProperties();
    }
}
