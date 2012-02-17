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
package org.nabucco.framework.workflow.facade.datatype.definition.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionType;

/**
 * BooleanCondition<p/>Condition composite to join other conditions by boolean expressions.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class BooleanCondition extends WorkflowConditionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowConditionType CONDITIONTYPE_DEFAULT = WorkflowConditionType.BOOLEAN;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String OPERATOR = "operator";

    /** The operator (AND, OR, etc.) of the boolean condition. Children are connected by this boolean operation. */
    private BooleanOperator operator;

    /** Constructs a new BooleanCondition instance. */
    public BooleanCondition() {
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
     * @param clone the BooleanCondition.
     */
    protected void cloneObject(BooleanCondition clone) {
        super.cloneObject(clone);
        clone.setConditionType(this.getConditionType());
        clone.setOperator(this.getOperator());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowConditionComposite.class).getPropertyMap());
        propertyMap.put(OPERATOR, PropertyDescriptorSupport.createEnumeration(OPERATOR, BooleanOperator.class, 8,
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
        properties
                .add(super.createProperty(BooleanCondition.getPropertyDescriptor(OPERATOR), this.getOperator(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OPERATOR) && (property.getType() == BooleanOperator.class))) {
            this.setOperator(((BooleanOperator) property.getInstance()));
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
        final BooleanCondition other = ((BooleanCondition) obj);
        if ((this.operator == null)) {
            if ((other.operator != null))
                return false;
        } else if ((!this.operator.equals(other.operator)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.operator == null) ? 0 : this.operator.hashCode()));
        return result;
    }

    @Override
    public BooleanCondition cloneObject() {
        BooleanCondition clone = new BooleanCondition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The operator (AND, OR, etc.) of the boolean condition. Children are connected by this boolean operation.
     *
     * @return the BooleanOperator.
     */
    public BooleanOperator getOperator() {
        return this.operator;
    }

    /**
     * The operator (AND, OR, etc.) of the boolean condition. Children are connected by this boolean operation.
     *
     * @param operator the BooleanOperator.
     */
    public void setOperator(BooleanOperator operator) {
        this.operator = operator;
    }

    /**
     * The operator (AND, OR, etc.) of the boolean condition. Children are connected by this boolean operation.
     *
     * @param operator the String.
     */
    public void setOperator(String operator) {
        if ((operator == null)) {
            this.operator = null;
        } else {
            this.operator = BooleanOperator.valueOf(operator);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BooleanCondition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BooleanCondition.class).getAllProperties();
    }
}
