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
package org.nabucco.framework.workflow.facade.message.definition.produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTriggerType;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTriggerType;

/**
 * ProduceWorkflowTriggerRq<p/>Message holding attributes for creating a new WorkflowTrigger.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-20
 */
public class ProduceWorkflowTriggerRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String TRIGGERTYPE = "triggerType";

    public static final String SIGNALTRIGGERTYPE = "signalTriggerType";

    /** Type of the trigger to create. */
    private WorkflowTriggerType triggerType;

    /** An optional signal trigger type when triggerType == SIGNAL */
    private SignalTriggerType signalTriggerType;

    /** Constructs a new ProduceWorkflowTriggerRq instance. */
    public ProduceWorkflowTriggerRq() {
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
        propertyMap.put(TRIGGERTYPE, PropertyDescriptorSupport.createEnumeration(TRIGGERTYPE,
                WorkflowTriggerType.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(SIGNALTRIGGERTYPE, PropertyDescriptorSupport.createEnumeration(SIGNALTRIGGERTYPE,
                SignalTriggerType.class, 1, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ProduceWorkflowTriggerRq.getPropertyDescriptor(TRIGGERTYPE),
                this.getTriggerType()));
        properties.add(super.createProperty(ProduceWorkflowTriggerRq.getPropertyDescriptor(SIGNALTRIGGERTYPE),
                this.getSignalTriggerType()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TRIGGERTYPE) && (property.getType() == WorkflowTriggerType.class))) {
            this.setTriggerType(((WorkflowTriggerType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNALTRIGGERTYPE) && (property.getType() == SignalTriggerType.class))) {
            this.setSignalTriggerType(((SignalTriggerType) property.getInstance()));
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
        final ProduceWorkflowTriggerRq other = ((ProduceWorkflowTriggerRq) obj);
        if ((this.triggerType == null)) {
            if ((other.triggerType != null))
                return false;
        } else if ((!this.triggerType.equals(other.triggerType)))
            return false;
        if ((this.signalTriggerType == null)) {
            if ((other.signalTriggerType != null))
                return false;
        } else if ((!this.signalTriggerType.equals(other.signalTriggerType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.triggerType == null) ? 0 : this.triggerType.hashCode()));
        result = ((PRIME * result) + ((this.signalTriggerType == null) ? 0 : this.signalTriggerType.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Type of the trigger to create.
     *
     * @return the WorkflowTriggerType.
     */
    public WorkflowTriggerType getTriggerType() {
        return this.triggerType;
    }

    /**
     * Type of the trigger to create.
     *
     * @param triggerType the WorkflowTriggerType.
     */
    public void setTriggerType(WorkflowTriggerType triggerType) {
        this.triggerType = triggerType;
    }

    /**
     * An optional signal trigger type when triggerType == SIGNAL
     *
     * @return the SignalTriggerType.
     */
    public SignalTriggerType getSignalTriggerType() {
        return this.signalTriggerType;
    }

    /**
     * An optional signal trigger type when triggerType == SIGNAL
     *
     * @param signalTriggerType the SignalTriggerType.
     */
    public void setSignalTriggerType(SignalTriggerType signalTriggerType) {
        this.signalTriggerType = signalTriggerType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProduceWorkflowTriggerRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProduceWorkflowTriggerRq.class).getAllProperties();
    }
}
