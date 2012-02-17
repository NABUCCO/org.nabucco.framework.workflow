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
package org.nabucco.framework.workflow.facade.datatype.definition.signal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;

/**
 * WorkflowSignalContainer<p/>Container for holding a single signal.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public class WorkflowSignalContainer extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m1,1;" };

    public static final String SIGNALNAME = "signalName";

    public static final String SIGNAL = "signal";

    /** The name of the workflow signal. */
    private Name signalName;

    /** Reference to the single signal. */
    private WorkflowSignal signal;

    /** Constructs a new WorkflowSignalContainer instance. */
    public WorkflowSignalContainer() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowSignalContainer.
     */
    protected void cloneObject(WorkflowSignalContainer clone) {
        super.cloneObject(clone);
        if ((this.getSignalName() != null)) {
            clone.setSignalName(this.getSignalName().cloneObject());
        }
        if ((this.getSignal() != null)) {
            clone.setSignal(this.getSignal().cloneObject());
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
        propertyMap.put(SIGNALNAME,
                PropertyDescriptorSupport.createBasetype(SIGNALNAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(SIGNAL, PropertyDescriptorSupport.createDatatype(SIGNAL, WorkflowSignal.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.AGGREGATION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowSignalContainer.getPropertyDescriptor(SIGNALNAME), this.signalName,
                null));
        properties.add(super.createProperty(WorkflowSignalContainer.getPropertyDescriptor(SIGNAL), this.getSignal(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SIGNALNAME) && (property.getType() == Name.class))) {
            this.setSignalName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNAL) && (property.getType() == WorkflowSignal.class))) {
            this.setSignal(((WorkflowSignal) property.getInstance()));
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
        final WorkflowSignalContainer other = ((WorkflowSignalContainer) obj);
        if ((this.signalName == null)) {
            if ((other.signalName != null))
                return false;
        } else if ((!this.signalName.equals(other.signalName)))
            return false;
        if ((this.signal == null)) {
            if ((other.signal != null))
                return false;
        } else if ((!this.signal.equals(other.signal)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.signalName == null) ? 0 : this.signalName.hashCode()));
        result = ((PRIME * result) + ((this.signal == null) ? 0 : this.signal.hashCode()));
        return result;
    }

    @Override
    public WorkflowSignalContainer cloneObject() {
        WorkflowSignalContainer clone = new WorkflowSignalContainer();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the workflow signal.
     *
     * @return the Name.
     */
    public Name getSignalName() {
        return this.signalName;
    }

    /**
     * The name of the workflow signal.
     *
     * @param signalName the Name.
     */
    public void setSignalName(Name signalName) {
        this.signalName = signalName;
    }

    /**
     * The name of the workflow signal.
     *
     * @param signalName the String.
     */
    public void setSignalName(String signalName) {
        if ((this.signalName == null)) {
            if ((signalName == null)) {
                return;
            }
            this.signalName = new Name();
        }
        this.signalName.setValue(signalName);
    }

    /**
     * Reference to the single signal.
     *
     * @param signal the WorkflowSignal.
     */
    public void setSignal(WorkflowSignal signal) {
        this.signal = signal;
    }

    /**
     * Reference to the single signal.
     *
     * @return the WorkflowSignal.
     */
    public WorkflowSignal getSignal() {
        return this.signal;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowSignalContainer.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowSignalContainer.class).getAllProperties();
    }
}
