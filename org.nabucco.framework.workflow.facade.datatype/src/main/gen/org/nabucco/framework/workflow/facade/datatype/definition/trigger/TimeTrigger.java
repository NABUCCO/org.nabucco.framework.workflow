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
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTrigger;
import org.nabucco.framework.workflow.facade.datatype.definition.trigger.WorkflowTriggerType;

/**
 * TimeTrigger<p/>A trigger of a workflow transition activated by a timer.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class TimeTrigger extends WorkflowTrigger implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowTriggerType TYPE_DEFAULT = WorkflowTriggerType.TIME;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;" };

    public static final String TIMER = "timer";

    /** The name of the timer. */
    private Name timer;

    /** Constructs a new TimeTrigger instance. */
    public TimeTrigger() {
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
     * @param clone the TimeTrigger.
     */
    protected void cloneObject(TimeTrigger clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getTimer() != null)) {
            clone.setTimer(this.getTimer().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowTrigger.class).getPropertyMap());
        propertyMap.put(TIMER,
                PropertyDescriptorSupport.createBasetype(TIMER, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TimeTrigger.getPropertyDescriptor(TIMER), this.timer, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TIMER) && (property.getType() == Name.class))) {
            this.setTimer(((Name) property.getInstance()));
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
        final TimeTrigger other = ((TimeTrigger) obj);
        if ((this.timer == null)) {
            if ((other.timer != null))
                return false;
        } else if ((!this.timer.equals(other.timer)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.timer == null) ? 0 : this.timer.hashCode()));
        return result;
    }

    @Override
    public TimeTrigger cloneObject() {
        TimeTrigger clone = new TimeTrigger();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the timer.
     *
     * @return the Name.
     */
    public Name getTimer() {
        return this.timer;
    }

    /**
     * The name of the timer.
     *
     * @param timer the Name.
     */
    public void setTimer(Name timer) {
        this.timer = timer;
    }

    /**
     * The name of the timer.
     *
     * @param timer the String.
     */
    public void setTimer(String timer) {
        if ((this.timer == null)) {
            if ((timer == null)) {
                return;
            }
            this.timer = new Name();
        }
        this.timer.setValue(timer);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TimeTrigger.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TimeTrigger.class).getAllProperties();
    }
}
