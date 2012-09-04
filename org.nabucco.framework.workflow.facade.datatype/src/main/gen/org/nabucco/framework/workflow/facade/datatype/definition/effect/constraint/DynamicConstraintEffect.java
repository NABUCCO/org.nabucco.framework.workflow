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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.constraint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * DynamicConstraintEffect<p/>Add Dynamic Constraints to the referenced datatype.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-03-18
 */
public class DynamicConstraintEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.CONSTRAINT;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String PROPERTYNAME = "propertyName";

    public static final String EDITABLE = "editable";

    public static final String VISIBLE = "visible";

    public static final String MINLENGTH = "minLength";

    public static final String MAXLENGTH = "maxLength";

    public static final String MINMULTIPLICITY = "minMultiplicity";

    public static final String MAXMULTIPLICITY = "maxMultiplicity";

    /** Name of the property to add the constraint to. */
    private Name propertyName;

    /** Whether the property should be editable or not. */
    private Flag editable;

    /** Whether the property should be visible or not. */
    private Flag visible;

    /** The new property min length. */
    private Number minLength;

    /** The new property max length. */
    private Number maxLength;

    /** The new property max multiplicity. */
    private Number minMultiplicity;

    /** The new property max multiplicity. */
    private Number maxMultiplicity;

    /** Constructs a new DynamicConstraintEffect instance. */
    public DynamicConstraintEffect() {
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
     * @param clone the DynamicConstraintEffect.
     */
    protected void cloneObject(DynamicConstraintEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getPropertyName() != null)) {
            clone.setPropertyName(this.getPropertyName().cloneObject());
        }
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getVisible() != null)) {
            clone.setVisible(this.getVisible().cloneObject());
        }
        if ((this.getMinLength() != null)) {
            clone.setMinLength(this.getMinLength().cloneObject());
        }
        if ((this.getMaxLength() != null)) {
            clone.setMaxLength(this.getMaxLength().cloneObject());
        }
        if ((this.getMinMultiplicity() != null)) {
            clone.setMinMultiplicity(this.getMinMultiplicity().cloneObject());
        }
        if ((this.getMaxMultiplicity() != null)) {
            clone.setMaxMultiplicity(this.getMaxMultiplicity().cloneObject());
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
        propertyMap.put(PROPERTYNAME,
                PropertyDescriptorSupport.createBasetype(PROPERTYNAME, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(EDITABLE,
                PropertyDescriptorSupport.createBasetype(EDITABLE, Flag.class, 8, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(VISIBLE,
                PropertyDescriptorSupport.createBasetype(VISIBLE, Flag.class, 9, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(MINLENGTH,
                PropertyDescriptorSupport.createBasetype(MINLENGTH, Number.class, 10, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(MAXLENGTH,
                PropertyDescriptorSupport.createBasetype(MAXLENGTH, Number.class, 11, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(MINMULTIPLICITY, PropertyDescriptorSupport.createBasetype(MINMULTIPLICITY, Number.class, 12,
                PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(MAXMULTIPLICITY, PropertyDescriptorSupport.createBasetype(MAXMULTIPLICITY, Number.class, 13,
                PROPERTY_CONSTRAINTS[6], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(PROPERTYNAME),
                this.propertyName, null));
        properties.add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(EDITABLE), this.editable,
                null));
        properties
                .add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(VISIBLE), this.visible, null));
        properties.add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(MINLENGTH), this.minLength,
                null));
        properties.add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(MAXLENGTH), this.maxLength,
                null));
        properties.add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(MINMULTIPLICITY),
                this.minMultiplicity, null));
        properties.add(super.createProperty(DynamicConstraintEffect.getPropertyDescriptor(MAXMULTIPLICITY),
                this.maxMultiplicity, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROPERTYNAME) && (property.getType() == Name.class))) {
            this.setPropertyName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EDITABLE) && (property.getType() == Flag.class))) {
            this.setEditable(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VISIBLE) && (property.getType() == Flag.class))) {
            this.setVisible(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINLENGTH) && (property.getType() == Number.class))) {
            this.setMinLength(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXLENGTH) && (property.getType() == Number.class))) {
            this.setMaxLength(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINMULTIPLICITY) && (property.getType() == Number.class))) {
            this.setMinMultiplicity(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXMULTIPLICITY) && (property.getType() == Number.class))) {
            this.setMaxMultiplicity(((Number) property.getInstance()));
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
        final DynamicConstraintEffect other = ((DynamicConstraintEffect) obj);
        if ((this.propertyName == null)) {
            if ((other.propertyName != null))
                return false;
        } else if ((!this.propertyName.equals(other.propertyName)))
            return false;
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.visible == null)) {
            if ((other.visible != null))
                return false;
        } else if ((!this.visible.equals(other.visible)))
            return false;
        if ((this.minLength == null)) {
            if ((other.minLength != null))
                return false;
        } else if ((!this.minLength.equals(other.minLength)))
            return false;
        if ((this.maxLength == null)) {
            if ((other.maxLength != null))
                return false;
        } else if ((!this.maxLength.equals(other.maxLength)))
            return false;
        if ((this.minMultiplicity == null)) {
            if ((other.minMultiplicity != null))
                return false;
        } else if ((!this.minMultiplicity.equals(other.minMultiplicity)))
            return false;
        if ((this.maxMultiplicity == null)) {
            if ((other.maxMultiplicity != null))
                return false;
        } else if ((!this.maxMultiplicity.equals(other.maxMultiplicity)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.propertyName == null) ? 0 : this.propertyName.hashCode()));
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.visible == null) ? 0 : this.visible.hashCode()));
        result = ((PRIME * result) + ((this.minLength == null) ? 0 : this.minLength.hashCode()));
        result = ((PRIME * result) + ((this.maxLength == null) ? 0 : this.maxLength.hashCode()));
        result = ((PRIME * result) + ((this.minMultiplicity == null) ? 0 : this.minMultiplicity.hashCode()));
        result = ((PRIME * result) + ((this.maxMultiplicity == null) ? 0 : this.maxMultiplicity.hashCode()));
        return result;
    }

    @Override
    public DynamicConstraintEffect cloneObject() {
        DynamicConstraintEffect clone = new DynamicConstraintEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the property to add the constraint to.
     *
     * @return the Name.
     */
    public Name getPropertyName() {
        return this.propertyName;
    }

    /**
     * Name of the property to add the constraint to.
     *
     * @param propertyName the Name.
     */
    public void setPropertyName(Name propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Name of the property to add the constraint to.
     *
     * @param propertyName the String.
     */
    public void setPropertyName(String propertyName) {
        if ((this.propertyName == null)) {
            if ((propertyName == null)) {
                return;
            }
            this.propertyName = new Name();
        }
        this.propertyName.setValue(propertyName);
    }

    /**
     * Whether the property should be editable or not.
     *
     * @return the Flag.
     */
    public Flag getEditable() {
        return this.editable;
    }

    /**
     * Whether the property should be editable or not.
     *
     * @param editable the Flag.
     */
    public void setEditable(Flag editable) {
        this.editable = editable;
    }

    /**
     * Whether the property should be editable or not.
     *
     * @param editable the Boolean.
     */
    public void setEditable(Boolean editable) {
        if ((this.editable == null)) {
            if ((editable == null)) {
                return;
            }
            this.editable = new Flag();
        }
        this.editable.setValue(editable);
    }

    /**
     * Whether the property should be visible or not.
     *
     * @return the Flag.
     */
    public Flag getVisible() {
        return this.visible;
    }

    /**
     * Whether the property should be visible or not.
     *
     * @param visible the Flag.
     */
    public void setVisible(Flag visible) {
        this.visible = visible;
    }

    /**
     * Whether the property should be visible or not.
     *
     * @param visible the Boolean.
     */
    public void setVisible(Boolean visible) {
        if ((this.visible == null)) {
            if ((visible == null)) {
                return;
            }
            this.visible = new Flag();
        }
        this.visible.setValue(visible);
    }

    /**
     * The new property min length.
     *
     * @return the Number.
     */
    public Number getMinLength() {
        return this.minLength;
    }

    /**
     * The new property min length.
     *
     * @param minLength the Number.
     */
    public void setMinLength(Number minLength) {
        this.minLength = minLength;
    }

    /**
     * The new property min length.
     *
     * @param minLength the Integer.
     */
    public void setMinLength(Integer minLength) {
        if ((this.minLength == null)) {
            if ((minLength == null)) {
                return;
            }
            this.minLength = new Number();
        }
        this.minLength.setValue(minLength);
    }

    /**
     * The new property max length.
     *
     * @return the Number.
     */
    public Number getMaxLength() {
        return this.maxLength;
    }

    /**
     * The new property max length.
     *
     * @param maxLength the Number.
     */
    public void setMaxLength(Number maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * The new property max length.
     *
     * @param maxLength the Integer.
     */
    public void setMaxLength(Integer maxLength) {
        if ((this.maxLength == null)) {
            if ((maxLength == null)) {
                return;
            }
            this.maxLength = new Number();
        }
        this.maxLength.setValue(maxLength);
    }

    /**
     * The new property max multiplicity.
     *
     * @return the Number.
     */
    public Number getMinMultiplicity() {
        return this.minMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @param minMultiplicity the Number.
     */
    public void setMinMultiplicity(Number minMultiplicity) {
        this.minMultiplicity = minMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @param minMultiplicity the Integer.
     */
    public void setMinMultiplicity(Integer minMultiplicity) {
        if ((this.minMultiplicity == null)) {
            if ((minMultiplicity == null)) {
                return;
            }
            this.minMultiplicity = new Number();
        }
        this.minMultiplicity.setValue(minMultiplicity);
    }

    /**
     * The new property max multiplicity.
     *
     * @return the Number.
     */
    public Number getMaxMultiplicity() {
        return this.maxMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @param maxMultiplicity the Number.
     */
    public void setMaxMultiplicity(Number maxMultiplicity) {
        this.maxMultiplicity = maxMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @param maxMultiplicity the Integer.
     */
    public void setMaxMultiplicity(Integer maxMultiplicity) {
        if ((this.maxMultiplicity == null)) {
            if ((maxMultiplicity == null)) {
                return;
            }
            this.maxMultiplicity = new Number();
        }
        this.maxMultiplicity.setValue(maxMultiplicity);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DynamicConstraintEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DynamicConstraintEffect.class).getAllProperties();
    }
}
