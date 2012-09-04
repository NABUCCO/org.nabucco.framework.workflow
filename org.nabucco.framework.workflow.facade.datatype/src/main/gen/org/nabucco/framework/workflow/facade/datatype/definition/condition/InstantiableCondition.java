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
package org.nabucco.framework.workflow.facade.datatype.definition.condition;

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
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionType;

/**
 * InstantiableCondition<p/>Condition for validating the current user against the assignee.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-10
 */
public class InstantiableCondition extends WorkflowConditionComponent implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowConditionType CONDITIONTYPE_DEFAULT = WorkflowConditionType.INSTANTIABLE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,1000;u0,n;m1,1;" };

    public static final String CLASSNAME = "className";

    /** The class to instanciate and evaluate. Must derive from InstantiableEvaluation interface. */
    private FullQualifiedClassName className;

    /** Constructs a new InstantiableCondition instance. */
    public InstantiableCondition() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        conditionType = CONDITIONTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the InstantiableCondition.
     */
    protected void cloneObject(InstantiableCondition clone) {
        super.cloneObject(clone);
        clone.setConditionType(this.getConditionType());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowConditionComponent.class).getPropertyMap());
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
        properties.add(super.createProperty(InstantiableCondition.getPropertyDescriptor(CLASSNAME), this.className,
                null));
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
        final InstantiableCondition other = ((InstantiableCondition) obj);
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
    public InstantiableCondition cloneObject() {
        InstantiableCondition clone = new InstantiableCondition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The class to instanciate and evaluate. Must derive from InstantiableEvaluation interface.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getClassName() {
        return this.className;
    }

    /**
     * The class to instanciate and evaluate. Must derive from InstantiableEvaluation interface.
     *
     * @param className the FullQualifiedClassName.
     */
    public void setClassName(FullQualifiedClassName className) {
        this.className = className;
    }

    /**
     * The class to instanciate and evaluate. Must derive from InstantiableEvaluation interface.
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
        return PropertyCache.getInstance().retrieve(InstantiableCondition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InstantiableCondition.class).getAllProperties();
    }
}
