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
package org.nabucco.framework.workflow.facade.datatype.definition.trigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.SignalTriggerType;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTriggerType;

/**
 * SignalTrigger<p/>A trigger of a workflow transition activated by a signal.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class SignalTrigger extends WorkflowTrigger implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowTriggerType TYPE_DEFAULT = WorkflowTriggerType.SIGNAL;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String SIGNAL = "signal";

    public static final String SIGNALTYPE = "signalType";

    /** The triggered signal to wait for. */
    private WorkflowSignal signal;

    /** Type of the signal trigger. */
    private SignalTriggerType signalType;

    /** Constructs a new SignalTrigger instance. */
    public SignalTrigger() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        type = TYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the SignalTrigger.
     */
    protected void cloneObject(SignalTrigger clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getSignal() != null)) {
            clone.setSignal(this.getSignal().cloneObject());
        }
        clone.setSignalType(this.getSignalType());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowTrigger.class).getPropertyMap());
        propertyMap.put(SIGNAL, PropertyDescriptorSupport.createDatatype(SIGNAL, WorkflowSignal.class, 7,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(SIGNALTYPE, PropertyDescriptorSupport.createEnumeration(SIGNALTYPE, SignalTriggerType.class, 8,
                PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SignalTrigger.getPropertyDescriptor(SIGNAL), this.getSignal(), null));
        properties
                .add(super.createProperty(SignalTrigger.getPropertyDescriptor(SIGNALTYPE), this.getSignalType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SIGNAL) && (property.getType() == WorkflowSignal.class))) {
            this.setSignal(((WorkflowSignal) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNALTYPE) && (property.getType() == SignalTriggerType.class))) {
            this.setSignalType(((SignalTriggerType) property.getInstance()));
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
        final SignalTrigger other = ((SignalTrigger) obj);
        if ((this.signal == null)) {
            if ((other.signal != null))
                return false;
        } else if ((!this.signal.equals(other.signal)))
            return false;
        if ((this.signalType == null)) {
            if ((other.signalType != null))
                return false;
        } else if ((!this.signalType.equals(other.signalType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.signal == null) ? 0 : this.signal.hashCode()));
        result = ((PRIME * result) + ((this.signalType == null) ? 0 : this.signalType.hashCode()));
        return result;
    }

    @Override
    public SignalTrigger cloneObject() {
        SignalTrigger clone = new SignalTrigger();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The triggered signal to wait for.
     *
     * @param signal the WorkflowSignal.
     */
    public void setSignal(WorkflowSignal signal) {
        this.signal = signal;
    }

    /**
     * The triggered signal to wait for.
     *
     * @return the WorkflowSignal.
     */
    public WorkflowSignal getSignal() {
        return this.signal;
    }

    /**
     * Type of the signal trigger.
     *
     * @return the SignalTriggerType.
     */
    public SignalTriggerType getSignalType() {
        return this.signalType;
    }

    /**
     * Type of the signal trigger.
     *
     * @param signalType the SignalTriggerType.
     */
    public void setSignalType(SignalTriggerType signalType) {
        this.signalType = signalType;
    }

    /**
     * Type of the signal trigger.
     *
     * @param signalType the String.
     */
    public void setSignalType(String signalType) {
        if ((signalType == null)) {
            this.signalType = null;
        } else {
            this.signalType = SignalTriggerType.valueOf(signalType);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SignalTrigger.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SignalTrigger.class).getAllProperties();
    }
}
