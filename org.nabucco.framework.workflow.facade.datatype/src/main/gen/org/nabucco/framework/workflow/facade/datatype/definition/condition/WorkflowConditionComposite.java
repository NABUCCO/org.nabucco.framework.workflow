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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkflowConditionComposite<p/>Superclass for multiple workflow conditions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public abstract class WorkflowConditionComposite extends WorkflowCondition implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String CONDITIONLIST = "conditionList";

    /** The child conditions. */
    private NabuccoList<WorkflowCondition> conditionList;

    /** Constructs a new WorkflowConditionComposite instance. */
    public WorkflowConditionComposite() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowConditionComposite.
     */
    protected void cloneObject(WorkflowConditionComposite clone) {
        super.cloneObject(clone);
        if ((this.conditionList != null)) {
            clone.conditionList = this.conditionList.cloneCollection();
        }
    }

    /**
     * Getter for the ConditionListJPA.
     *
     * @return the List<WorkflowCondition>.
     */
    List<WorkflowCondition> getConditionListJPA() {
        if ((this.conditionList == null)) {
            this.conditionList = new NabuccoListImpl<WorkflowCondition>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowCondition>) this.conditionList).getDelegate();
    }

    /**
     * Setter for the ConditionListJPA.
     *
     * @param conditionList the List<WorkflowCondition>.
     */
    void setConditionListJPA(List<WorkflowCondition> conditionList) {
        if ((this.conditionList == null)) {
            this.conditionList = new NabuccoListImpl<WorkflowCondition>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowCondition>) this.conditionList).setDelegate(conditionList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowCondition.class).getPropertyMap());
        propertyMap.put(CONDITIONLIST, PropertyDescriptorSupport.createCollection(CONDITIONLIST,
                WorkflowCondition.class, 7, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowConditionComposite.getPropertyDescriptor(CONDITIONLIST),
                this.conditionList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONDITIONLIST) && (property.getType() == WorkflowCondition.class))) {
            this.conditionList = ((NabuccoList<WorkflowCondition>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public abstract WorkflowConditionComposite cloneObject();

    /**
     * The child conditions.
     *
     * @return the NabuccoList<WorkflowCondition>.
     */
    public NabuccoList<WorkflowCondition> getConditionList() {
        if ((this.conditionList == null)) {
            this.conditionList = new NabuccoListImpl<WorkflowCondition>(NabuccoCollectionState.INITIALIZED);
        }
        return this.conditionList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowConditionComposite.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowConditionComposite.class).getAllProperties();
    }
}
