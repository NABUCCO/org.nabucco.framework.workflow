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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * LogEffect<p/>Writes a log messages to the logger.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-03-09
 */
public class LogEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.LOG;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;" };

    public static final String MESSAGE = "message";

    /** The log message to write. */
    private Description message;

    /** Constructs a new LogEffect instance. */
    public LogEffect() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        effectType = EFFECTTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the LogEffect.
     */
    protected void cloneObject(LogEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getMessage() != null)) {
            clone.setMessage(this.getMessage().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowEffect.class).getPropertyMap());
        propertyMap
                .put(MESSAGE, PropertyDescriptorSupport.createBasetype(MESSAGE, Description.class, 7,
                        PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(LogEffect.getPropertyDescriptor(MESSAGE), this.message, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MESSAGE) && (property.getType() == Description.class))) {
            this.setMessage(((Description) property.getInstance()));
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
        final LogEffect other = ((LogEffect) obj);
        if ((this.message == null)) {
            if ((other.message != null))
                return false;
        } else if ((!this.message.equals(other.message)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.message == null) ? 0 : this.message.hashCode()));
        return result;
    }

    @Override
    public LogEffect cloneObject() {
        LogEffect clone = new LogEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The log message to write.
     *
     * @return the Description.
     */
    public Description getMessage() {
        return this.message;
    }

    /**
     * The log message to write.
     *
     * @param message the Description.
     */
    public void setMessage(Description message) {
        this.message = message;
    }

    /**
     * The log message to write.
     *
     * @param message the String.
     */
    public void setMessage(String message) {
        if ((this.message == null)) {
            if ((message == null)) {
                return;
            }
            this.message = new Description();
        }
        this.message.setValue(message);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(LogEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LogEffect.class).getAllProperties();
    }
}
