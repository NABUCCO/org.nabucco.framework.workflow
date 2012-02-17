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
package org.nabucco.framework.workflow.facade.datatype.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;

/**
 * WorkflowResult<p/>The transient result returned to the clients<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public class WorkflowResult extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String INSTANCE = "instance";

    public static final String NEXTTRANSITIONS = "nextTransitions";

    /** The current workflow instance. */
    private WorkflowInstance instance;

    /** The next workflow transitions. */
    private NabuccoList<TransitionParameter> nextTransitions;

    private Long nextTransitionsRefId;

    /** Constructs a new WorkflowResult instance. */
    public WorkflowResult() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowResult.
     */
    protected void cloneObject(WorkflowResult clone) {
        super.cloneObject(clone);
        if ((this.getInstance() != null)) {
            clone.setInstance(this.getInstance().cloneObject());
        }
        if ((this.nextTransitions != null)) {
            clone.nextTransitions = this.nextTransitions.cloneCollection();
        }
    }

    /**
     * Getter for the NextTransitionsJPA.
     *
     * @return the List<TransitionParameter>.
     */
    List<TransitionParameter> getNextTransitionsJPA() {
        if ((this.nextTransitions == null)) {
            this.nextTransitions = new NabuccoListImpl<TransitionParameter>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TransitionParameter>) this.nextTransitions).getDelegate();
    }

    /**
     * Setter for the NextTransitionsJPA.
     *
     * @param nextTransitions the List<TransitionParameter>.
     */
    void setNextTransitionsJPA(List<TransitionParameter> nextTransitions) {
        if ((this.nextTransitions == null)) {
            this.nextTransitions = new NabuccoListImpl<TransitionParameter>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TransitionParameter>) this.nextTransitions).setDelegate(nextTransitions);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(INSTANCE, PropertyDescriptorSupport.createDatatype(INSTANCE, WorkflowInstance.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NEXTTRANSITIONS, PropertyDescriptorSupport.createCollection(NEXTTRANSITIONS,
                TransitionParameter.class, 1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowResult.getPropertyDescriptor(INSTANCE), this.getInstance(), null));
        properties.add(super.createProperty(WorkflowResult.getPropertyDescriptor(NEXTTRANSITIONS),
                this.nextTransitions, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INSTANCE) && (property.getType() == WorkflowInstance.class))) {
            this.setInstance(((WorkflowInstance) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEXTTRANSITIONS) && (property.getType() == TransitionParameter.class))) {
            this.nextTransitions = ((NabuccoList<TransitionParameter>) property.getInstance());
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
        final WorkflowResult other = ((WorkflowResult) obj);
        if ((this.instance == null)) {
            if ((other.instance != null))
                return false;
        } else if ((!this.instance.equals(other.instance)))
            return false;
        if ((this.nextTransitionsRefId == null)) {
            if ((other.nextTransitionsRefId != null))
                return false;
        } else if ((!this.nextTransitionsRefId.equals(other.nextTransitionsRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.instance == null) ? 0 : this.instance.hashCode()));
        result = ((PRIME * result) + ((this.nextTransitionsRefId == null) ? 0 : this.nextTransitionsRefId.hashCode()));
        return result;
    }

    @Override
    public WorkflowResult cloneObject() {
        WorkflowResult clone = new WorkflowResult();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The current workflow instance.
     *
     * @param instance the WorkflowInstance.
     */
    public void setInstance(WorkflowInstance instance) {
        this.instance = instance;
    }

    /**
     * The current workflow instance.
     *
     * @return the WorkflowInstance.
     */
    public WorkflowInstance getInstance() {
        return this.instance;
    }

    /**
     * The next workflow transitions.
     *
     * @return the NabuccoList<TransitionParameter>.
     */
    public NabuccoList<TransitionParameter> getNextTransitions() {
        if ((this.nextTransitions == null)) {
            this.nextTransitions = new NabuccoListImpl<TransitionParameter>(NabuccoCollectionState.INITIALIZED);
        }
        return this.nextTransitions;
    }

    /**
     * Getter for the NextTransitionsRefId.
     *
     * @return the Long.
     */
    public Long getNextTransitionsRefId() {
        return this.nextTransitionsRefId;
    }

    /**
     * Setter for the NextTransitionsRefId.
     *
     * @param nextTransitionsRefId the Long.
     */
    public void setNextTransitionsRefId(Long nextTransitionsRefId) {
        this.nextTransitionsRefId = nextTransitionsRefId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowResult.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowResult.class).getAllProperties();
    }
}
