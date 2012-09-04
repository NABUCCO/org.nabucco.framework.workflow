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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.instantiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * InstantiableEffect<p/>Writes a log messages to the logger.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-03-09
 */
public class InstantiableEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.INSTANTIABLE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,1000;u0,n;m1,1;" };

    public static final String CLASSNAME = "className";

    /** The class to instanciate and execute. Must derive from Instantiable interface. */
    private FullQualifiedClassName className;

    /** Constructs a new InstantiableEffect instance. */
    public InstantiableEffect() {
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
     * @param clone the InstantiableEffect.
     */
    protected void cloneObject(InstantiableEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getClassName() != null)) {
            clone.setClassName(this.getClassName().cloneObject());
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
        propertyMap.put(CLASSNAME, PropertyDescriptorSupport.createBasetype(CLASSNAME, FullQualifiedClassName.class, 7,
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
        properties.add(super.createProperty(InstantiableEffect.getPropertyDescriptor(CLASSNAME), this.className, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CLASSNAME) && (property.getType() == FullQualifiedClassName.class))) {
            this.setClassName(((FullQualifiedClassName) property.getInstance()));
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
        final InstantiableEffect other = ((InstantiableEffect) obj);
        if ((this.className == null)) {
            if ((other.className != null))
                return false;
        } else if ((!this.className.equals(other.className)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.className == null) ? 0 : this.className.hashCode()));
        return result;
    }

    @Override
    public InstantiableEffect cloneObject() {
        InstantiableEffect clone = new InstantiableEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The class to instanciate and execute. Must derive from Instantiable interface.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getClassName() {
        return this.className;
    }

    /**
     * The class to instanciate and execute. Must derive from Instantiable interface.
     *
     * @param className the FullQualifiedClassName.
     */
    public void setClassName(FullQualifiedClassName className) {
        this.className = className;
    }

    /**
     * The class to instanciate and execute. Must derive from Instantiable interface.
     *
     * @param className the String.
     */
    public void setClassName(String className) {
        if ((this.className == null)) {
            if ((className == null)) {
                return;
            }
            this.className = new FullQualifiedClassName();
        }
        this.className.setValue(className);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(InstantiableEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InstantiableEffect.class).getAllProperties();
    }
}
