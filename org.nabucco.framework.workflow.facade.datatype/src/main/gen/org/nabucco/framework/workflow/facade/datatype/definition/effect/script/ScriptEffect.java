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
package org.nabucco.framework.workflow.facade.datatype.definition.effect.script;

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
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffectType;

/**
 * ScriptEffect<p/>Executes a script.<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2010-05-25
 */
public class ScriptEffect extends WorkflowEffect implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowEffectType EFFECTTYPE_DEFAULT = WorkflowEffectType.SCRIPT;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;" };

    public static final String SCRIPTNAME = "scriptName";

    /** The name of the script to call. */
    private Name scriptName;

    /** Constructs a new ScriptEffect instance. */
    public ScriptEffect() {
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
     * @param clone the ScriptEffect.
     */
    protected void cloneObject(ScriptEffect clone) {
        super.cloneObject(clone);
        clone.setEffectType(this.getEffectType());
        if ((this.getScriptName() != null)) {
            clone.setScriptName(this.getScriptName().cloneObject());
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
        propertyMap.put(SCRIPTNAME,
                PropertyDescriptorSupport.createBasetype(SCRIPTNAME, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ScriptEffect.getPropertyDescriptor(SCRIPTNAME), this.scriptName, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SCRIPTNAME) && (property.getType() == Name.class))) {
            this.setScriptName(((Name) property.getInstance()));
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
        final ScriptEffect other = ((ScriptEffect) obj);
        if ((this.scriptName == null)) {
            if ((other.scriptName != null))
                return false;
        } else if ((!this.scriptName.equals(other.scriptName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.scriptName == null) ? 0 : this.scriptName.hashCode()));
        return result;
    }

    @Override
    public ScriptEffect cloneObject() {
        ScriptEffect clone = new ScriptEffect();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the script to call.
     *
     * @return the Name.
     */
    public Name getScriptName() {
        return this.scriptName;
    }

    /**
     * The name of the script to call.
     *
     * @param scriptName the Name.
     */
    public void setScriptName(Name scriptName) {
        this.scriptName = scriptName;
    }

    /**
     * The name of the script to call.
     *
     * @param scriptName the String.
     */
    public void setScriptName(String scriptName) {
        if ((this.scriptName == null)) {
            if ((scriptName == null)) {
                return;
            }
            this.scriptName = new Name();
        }
        this.scriptName.setValue(scriptName);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ScriptEffect.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ScriptEffect.class).getAllProperties();
    }
}
