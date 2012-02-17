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
package org.nabucco.framework.workflow.facade.message.definition.produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowConditionType;

/**
 * ProduceWorkflowConditionRq<p/>Message holding attributes for creating a new WorkflowCondition.<p/>
 *
 * @author Silas Schwarz, PRODYNA AG, 2010-04-20
 */
public class ProduceWorkflowConditionRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String CONDITIONTYPE = "conditionType";

    /** The condition type to create a condition for. */
    private WorkflowConditionType conditionType;

    /** Constructs a new ProduceWorkflowConditionRq instance. */
    public ProduceWorkflowConditionRq() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(CONDITIONTYPE, PropertyDescriptorSupport.createEnumeration(CONDITIONTYPE,
                WorkflowConditionType.class, 0, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ProduceWorkflowConditionRq.getPropertyDescriptor(CONDITIONTYPE),
                this.getConditionType()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONDITIONTYPE) && (property.getType() == WorkflowConditionType.class))) {
            this.setConditionType(((WorkflowConditionType) property.getInstance()));
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
        final ProduceWorkflowConditionRq other = ((ProduceWorkflowConditionRq) obj);
        if ((this.conditionType == null)) {
            if ((other.conditionType != null))
                return false;
        } else if ((!this.conditionType.equals(other.conditionType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.conditionType == null) ? 0 : this.conditionType.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The condition type to create a condition for.
     *
     * @return the WorkflowConditionType.
     */
    public WorkflowConditionType getConditionType() {
        return this.conditionType;
    }

    /**
     * The condition type to create a condition for.
     *
     * @param conditionType the WorkflowConditionType.
     */
    public void setConditionType(WorkflowConditionType conditionType) {
        this.conditionType = conditionType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProduceWorkflowConditionRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProduceWorkflowConditionRq.class).getAllProperties();
    }
}
