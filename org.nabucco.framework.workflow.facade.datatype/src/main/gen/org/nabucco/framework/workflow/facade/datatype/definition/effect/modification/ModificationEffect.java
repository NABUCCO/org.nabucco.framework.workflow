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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.modification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.Value;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * ModificationEffect<p/>Modifies a property of the related datatype.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class ModificationEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.MODIFICATION;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String PATH = "path";

    public static final String VALUE = "value";

    /** The path of the property to set. */
    private Path path;

    /** The value of the property to set. */
    private Value value;

    /** Constructs a new ModificationEffect instance. */
    public ModificationEffect() {
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
     * @param clone the ModificationEffect.
     */
    protected void cloneObject(ModificationEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getPath() != null)) {
            clone.setPath(this.getPath().cloneObject());
        }
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
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
        propertyMap.put(PATH,
                PropertyDescriptorSupport.createBasetype(PATH, Path.class, 7, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(VALUE,
                PropertyDescriptorSupport.createBasetype(VALUE, Value.class, 8, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ModificationEffect.getPropertyDescriptor(PATH), this.path, null));
        properties.add(super.createProperty(ModificationEffect.getPropertyDescriptor(VALUE), this.value, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PATH) && (property.getType() == Path.class))) {
            this.setPath(((Path) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == Value.class))) {
            this.setValue(((Value) property.getInstance()));
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
        final ModificationEffect other = ((ModificationEffect) obj);
        if ((this.path == null)) {
            if ((other.path != null))
                return false;
        } else if ((!this.path.equals(other.path)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.path == null) ? 0 : this.path.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public ModificationEffect cloneObject() {
        ModificationEffect clone = new ModificationEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The path of the property to set.
     *
     * @return the Path.
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * The path of the property to set.
     *
     * @param path the Path.
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * The path of the property to set.
     *
     * @param path the String.
     */
    public void setPath(String path) {
        if ((this.path == null)) {
            if ((path == null)) {
                return;
            }
            this.path = new Path();
        }
        this.path.setValue(path);
    }

    /**
     * The value of the property to set.
     *
     * @return the Value.
     */
    public Value getValue() {
        return this.value;
    }

    /**
     * The value of the property to set.
     *
     * @param value the Value.
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * The value of the property to set.
     *
     * @param value the String.
     */
    public void setValue(String value) {
        if ((this.value == null)) {
            if ((value == null)) {
                return;
            }
            this.value = new Value();
        }
        this.value.setValue(value);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ModificationEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ModificationEffect.class).getAllProperties();
    }
}
