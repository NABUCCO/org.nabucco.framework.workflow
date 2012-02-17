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
 * PropertyCondition<p/>Condition for property expressions.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-20
 */
public class PropertyCondition extends WorkflowConditionComponent implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowConditionType CONDITIONTYPE_DEFAULT = WorkflowConditionType.PROPERTY;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;" };

    public static final String EXPRESSION = "expression";

    private Expression expression;

    /** Constructs a new PropertyCondition instance. */
    public PropertyCondition() {
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
     * @param clone the PropertyCondition.
     */
    protected void cloneObject(PropertyCondition clone) {
        super.cloneObject(clone);
        clone.setConditionType(this.getConditionType());
        if ((this.getExpression() != null)) {
            clone.setExpression(this.getExpression().cloneObject());
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
        propertyMap.put(EXPRESSION, PropertyDescriptorSupport.createBasetype(EXPRESSION, Expression.class, 7,
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
                .add(super.createProperty(PropertyCondition.getPropertyDescriptor(EXPRESSION), this.expression, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXPRESSION) && (property.getType() == Expression.class))) {
            this.setExpression(((Expression) property.getInstance()));
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
        final PropertyCondition other = ((PropertyCondition) obj);
        if ((this.expression == null)) {
            if ((other.expression != null))
                return false;
        } else if ((!this.expression.equals(other.expression)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.expression == null) ? 0 : this.expression.hashCode()));
        return result;
    }

    @Override
    public PropertyCondition cloneObject() {
        PropertyCondition clone = new PropertyCondition();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getExpression.
     *
     * @return the Expression.
     */
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * Missing description at method setExpression.
     *
     * @param expression the Expression.
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * Missing description at method setExpression.
     *
     * @param expression the String.
     */
    public void setExpression(String expression) {
        if ((this.expression == null)) {
            if ((expression == null)) {
                return;
            }
            this.expression = new Expression();
        }
        this.expression.setValue(expression);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PropertyCondition.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PropertyCondition.class).getAllProperties();
    }
}
