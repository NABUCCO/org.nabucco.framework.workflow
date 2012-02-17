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
package org.nabucco.framework.workflow.facade.datatype.definition.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.search.WorkflowStateSearchParameter;

/**
 * WorkflowDefinitionSearchParameter<p/>The search parameter for searching workflow definitions.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-05-08
 */
public class WorkflowDefinitionSearchParameter extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m0,n;" };

    public static final String DEFINITION = "definition";

    public static final String STATEPARAMETERS = "stateParameters";

    /** Name of the workflow definition to search for. */
    private Name definition;

    /** The search parameter for states of the workflow definition. */
    private NabuccoList<WorkflowStateSearchParameter> stateParameters;

    /** Constructs a new WorkflowDefinitionSearchParameter instance. */
    public WorkflowDefinitionSearchParameter() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowDefinitionSearchParameter.
     */
    protected void cloneObject(WorkflowDefinitionSearchParameter clone) {
        super.cloneObject(clone);
        if ((this.getDefinition() != null)) {
            clone.setDefinition(this.getDefinition().cloneObject());
        }
        if ((this.stateParameters != null)) {
            clone.stateParameters = this.stateParameters.cloneCollection();
        }
    }

    /**
     * Getter for the StateParametersJPA.
     *
     * @return the List<WorkflowStateSearchParameter>.
     */
    List<WorkflowStateSearchParameter> getStateParametersJPA() {
        if ((this.stateParameters == null)) {
            this.stateParameters = new NabuccoListImpl<WorkflowStateSearchParameter>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowStateSearchParameter>) this.stateParameters).getDelegate();
    }

    /**
     * Setter for the StateParametersJPA.
     *
     * @param stateParameters the List<WorkflowStateSearchParameter>.
     */
    void setStateParametersJPA(List<WorkflowStateSearchParameter> stateParameters) {
        if ((this.stateParameters == null)) {
            this.stateParameters = new NabuccoListImpl<WorkflowStateSearchParameter>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowStateSearchParameter>) this.stateParameters).setDelegate(stateParameters);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(DEFINITION,
                PropertyDescriptorSupport.createBasetype(DEFINITION, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(STATEPARAMETERS, PropertyDescriptorSupport.createCollection(STATEPARAMETERS,
                WorkflowStateSearchParameter.class, 1, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowDefinitionSearchParameter.getPropertyDescriptor(DEFINITION),
                this.definition, null));
        properties.add(super.createProperty(WorkflowDefinitionSearchParameter.getPropertyDescriptor(STATEPARAMETERS),
                this.stateParameters, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DEFINITION) && (property.getType() == Name.class))) {
            this.setDefinition(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATEPARAMETERS) && (property.getType() == WorkflowStateSearchParameter.class))) {
            this.stateParameters = ((NabuccoList<WorkflowStateSearchParameter>) property.getInstance());
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
        final WorkflowDefinitionSearchParameter other = ((WorkflowDefinitionSearchParameter) obj);
        if ((this.definition == null)) {
            if ((other.definition != null))
                return false;
        } else if ((!this.definition.equals(other.definition)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.definition == null) ? 0 : this.definition.hashCode()));
        return result;
    }

    @Override
    public WorkflowDefinitionSearchParameter cloneObject() {
        WorkflowDefinitionSearchParameter clone = new WorkflowDefinitionSearchParameter();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the workflow definition to search for.
     *
     * @return the Name.
     */
    public Name getDefinition() {
        return this.definition;
    }

    /**
     * Name of the workflow definition to search for.
     *
     * @param definition the Name.
     */
    public void setDefinition(Name definition) {
        this.definition = definition;
    }

    /**
     * Name of the workflow definition to search for.
     *
     * @param definition the String.
     */
    public void setDefinition(String definition) {
        if ((this.definition == null)) {
            if ((definition == null)) {
                return;
            }
            this.definition = new Name();
        }
        this.definition.setValue(definition);
    }

    /**
     * The search parameter for states of the workflow definition.
     *
     * @return the NabuccoList<WorkflowStateSearchParameter>.
     */
    public NabuccoList<WorkflowStateSearchParameter> getStateParameters() {
        if ((this.stateParameters == null)) {
            this.stateParameters = new NabuccoListImpl<WorkflowStateSearchParameter>(NabuccoCollectionState.INITIALIZED);
        }
        return this.stateParameters;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowDefinitionSearchParameter.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowDefinitionSearchParameter.class).getAllProperties();
    }
}
