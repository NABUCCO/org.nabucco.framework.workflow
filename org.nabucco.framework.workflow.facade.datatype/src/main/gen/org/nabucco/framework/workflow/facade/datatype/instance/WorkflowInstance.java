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
package org.nabucco.framework.workflow.facade.datatype.instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * WorkflowInstance<p/>An instance of a workflow (represents a complete workflow iteration).<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-08
 */
public class WorkflowInstance extends Instance implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;", "m0,n;", "m0,1;" };

    public static final String DEFINITION = "definition";

    public static final String CURRENTENTRY = "currentEntry";

    public static final String ENTRYLIST = "entryList";

    public static final String SUBINSTANCES = "subInstances";

    public static final String CONTEXT = "context";

    /** The workflow definition of the WorkflowInstance. */
    private WorkflowDefinition definition;

    /** The current entry */
    private WorkflowInstanceEntry currentEntry;

    /** The workflow entries that the instance passed through */
    private NabuccoList<WorkflowInstanceEntry> entryList;

    /** The workflow instances of the sub workflows. */
    private NabuccoList<WorkflowInstance> subInstances;

    /** The instance context. */
    private WorkflowContext context;

    private Long functionalTypeRefId;

    private Long priorityRefId;

    /** Constructs a new WorkflowInstance instance. */
    public WorkflowInstance() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowInstance.
     */
    protected void cloneObject(WorkflowInstance clone) {
        super.cloneObject(clone);
        if ((this.getDefinition() != null)) {
            clone.setDefinition(this.getDefinition().cloneObject());
        }
        if ((this.getCurrentEntry() != null)) {
            clone.setCurrentEntry(this.getCurrentEntry().cloneObject());
        }
        if ((this.entryList != null)) {
            clone.entryList = this.entryList.cloneCollection();
        }
        if ((this.subInstances != null)) {
            clone.subInstances = this.subInstances.cloneCollection();
        }
        if ((this.getContext() != null)) {
            clone.setContext(this.getContext().cloneObject());
        }
    }

    /**
     * Getter for the EntryListJPA.
     *
     * @return the List<WorkflowInstanceEntry>.
     */
    List<WorkflowInstanceEntry> getEntryListJPA() {
        if ((this.entryList == null)) {
            this.entryList = new NabuccoListImpl<WorkflowInstanceEntry>(NabuccoCollectionState.EAGER);
        }
        return ((NabuccoListImpl<WorkflowInstanceEntry>) this.entryList).getDelegate();
    }

    /**
     * Setter for the EntryListJPA.
     *
     * @param entryList the List<WorkflowInstanceEntry>.
     */
    void setEntryListJPA(List<WorkflowInstanceEntry> entryList) {
        if ((this.entryList == null)) {
            this.entryList = new NabuccoListImpl<WorkflowInstanceEntry>(NabuccoCollectionState.EAGER);
        }
        ((NabuccoListImpl<WorkflowInstanceEntry>) this.entryList).setDelegate(entryList);
    }

    /**
     * Getter for the SubInstancesJPA.
     *
     * @return the List<WorkflowInstance>.
     */
    List<WorkflowInstance> getSubInstancesJPA() {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<WorkflowInstance>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowInstance>) this.subInstances).getDelegate();
    }

    /**
     * Setter for the SubInstancesJPA.
     *
     * @param subInstances the List<WorkflowInstance>.
     */
    void setSubInstancesJPA(List<WorkflowInstance> subInstances) {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<WorkflowInstance>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowInstance>) this.subInstances).setDelegate(subInstances);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(Instance.class).getPropertyMap());
        propertyMap.put(DEFINITION, PropertyDescriptorSupport.createDatatype(DEFINITION, WorkflowDefinition.class, 16,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CURRENTENTRY, PropertyDescriptorSupport.createDatatype(CURRENTENTRY,
                WorkflowInstanceEntry.class, 17, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(ENTRYLIST, PropertyDescriptorSupport.createCollection(ENTRYLIST, WorkflowInstanceEntry.class,
                18, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SUBINSTANCES, PropertyDescriptorSupport.createCollection(SUBINSTANCES, WorkflowInstance.class,
                19, PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, WorkflowContext.class, 20,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(DEFINITION), this.getDefinition(),
                null));
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(CURRENTENTRY),
                this.getCurrentEntry(), null));
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(ENTRYLIST), this.entryList, null));
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(SUBINSTANCES), this.subInstances,
                null));
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(CONTEXT), this.getContext(), null));
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(FUNCTIONALTYPE),
                this.getFunctionalType(), this.functionalTypeRefId));
        properties.add(super.createProperty(WorkflowInstance.getPropertyDescriptor(PRIORITY), this.getPriority(),
                this.priorityRefId));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DEFINITION) && (property.getType() == WorkflowDefinition.class))) {
            this.setDefinition(((WorkflowDefinition) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CURRENTENTRY) && (property.getType() == WorkflowInstanceEntry.class))) {
            this.setCurrentEntry(((WorkflowInstanceEntry) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRYLIST) && (property.getType() == WorkflowInstanceEntry.class))) {
            this.entryList = ((NabuccoList<WorkflowInstanceEntry>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SUBINSTANCES) && (property.getType() == WorkflowInstance.class))) {
            this.subInstances = ((NabuccoList<WorkflowInstance>) property.getInstance());
            return true;
        } else if ((property.getName().equals(CONTEXT) && (property.getType() == WorkflowContext.class))) {
            this.setContext(((WorkflowContext) property.getInstance()));
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
        final WorkflowInstance other = ((WorkflowInstance) obj);
        if ((this.definition == null)) {
            if ((other.definition != null))
                return false;
        } else if ((!this.definition.equals(other.definition)))
            return false;
        if ((this.currentEntry == null)) {
            if ((other.currentEntry != null))
                return false;
        } else if ((!this.currentEntry.equals(other.currentEntry)))
            return false;
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
            return false;
        if ((this.functionalTypeRefId == null)) {
            if ((other.functionalTypeRefId != null))
                return false;
        } else if ((!this.functionalTypeRefId.equals(other.functionalTypeRefId)))
            return false;
        if ((this.priorityRefId == null)) {
            if ((other.priorityRefId != null))
                return false;
        } else if ((!this.priorityRefId.equals(other.priorityRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.definition == null) ? 0 : this.definition.hashCode()));
        result = ((PRIME * result) + ((this.currentEntry == null) ? 0 : this.currentEntry.hashCode()));
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        result = ((PRIME * result) + ((this.functionalTypeRefId == null) ? 0 : this.functionalTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.priorityRefId == null) ? 0 : this.priorityRefId.hashCode()));
        return result;
    }

    @Override
    public WorkflowInstance cloneObject() {
        WorkflowInstance clone = new WorkflowInstance();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The workflow definition of the WorkflowInstance.
     *
     * @param definition the WorkflowDefinition.
     */
    public void setDefinition(WorkflowDefinition definition) {
        this.definition = definition;
    }

    /**
     * The workflow definition of the WorkflowInstance.
     *
     * @return the WorkflowDefinition.
     */
    public WorkflowDefinition getDefinition() {
        return this.definition;
    }

    /**
     * The current entry
     *
     * @param currentEntry the WorkflowInstanceEntry.
     */
    public void setCurrentEntry(WorkflowInstanceEntry currentEntry) {
        this.currentEntry = currentEntry;
    }

    /**
     * The current entry
     *
     * @return the WorkflowInstanceEntry.
     */
    public WorkflowInstanceEntry getCurrentEntry() {
        return this.currentEntry;
    }

    /**
     * The workflow entries that the instance passed through
     *
     * @return the NabuccoList<WorkflowInstanceEntry>.
     */
    public NabuccoList<WorkflowInstanceEntry> getEntryList() {
        if ((this.entryList == null)) {
            this.entryList = new NabuccoListImpl<WorkflowInstanceEntry>(NabuccoCollectionState.INITIALIZED);
        }
        return this.entryList;
    }

    /**
     * The workflow instances of the sub workflows.
     *
     * @return the NabuccoList<WorkflowInstance>.
     */
    public NabuccoList<WorkflowInstance> getSubInstances() {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<WorkflowInstance>(NabuccoCollectionState.INITIALIZED);
        }
        return this.subInstances;
    }

    /**
     * The instance context.
     *
     * @param context the WorkflowContext.
     */
    public void setContext(WorkflowContext context) {
        this.context = context;
    }

    /**
     * The instance context.
     *
     * @return the WorkflowContext.
     */
    public WorkflowContext getContext() {
        return this.context;
    }

    /**
     * Getter for the FunctionalTypeRefId.
     *
     * @return the Long.
     */
    public Long getFunctionalTypeRefId() {
        return this.functionalTypeRefId;
    }

    /**
     * Setter for the FunctionalTypeRefId.
     *
     * @param functionalTypeRefId the Long.
     */
    public void setFunctionalTypeRefId(Long functionalTypeRefId) {
        this.functionalTypeRefId = functionalTypeRefId;
    }

    /**
     * Setter for the FunctionalType.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        super.setFunctionalType(functionalType);
        if ((functionalType != null)) {
            this.setFunctionalTypeRefId(functionalType.getId());
        } else {
            this.setFunctionalTypeRefId(null);
        }
    }

    /**
     * Getter for the PriorityRefId.
     *
     * @return the Long.
     */
    public Long getPriorityRefId() {
        return this.priorityRefId;
    }

    /**
     * Setter for the PriorityRefId.
     *
     * @param priorityRefId the Long.
     */
    public void setPriorityRefId(Long priorityRefId) {
        this.priorityRefId = priorityRefId;
    }

    /**
     * Setter for the Priority.
     *
     * @param priority the Code.
     */
    public void setPriority(Code priority) {
        super.setPriority(priority);
        if ((priority != null)) {
            this.setPriorityRefId(priority.getId());
        } else {
            this.setPriorityRefId(null);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowInstance.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowInstance.class).getAllProperties();
    }
}
