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
package org.nabucco.framework.workflow.ui.rcp.edit.signal.model;

import java.io.Serializable;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.plugin.base.component.edit.model.EditViewModel;
import org.nabucco.framework.plugin.base.logging.Loggable;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;

/**
 * WorkflowSignalEditViewModel<p/>Edit view for datatype WorkflowSignal<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class WorkflowSignalEditViewModel extends EditViewModel implements Loggable {

    private WorkflowSignal signal;

    public static final String PROPERTY_SIGNAL_NAME = "signalName";

    public static final String PROPERTY_SIGNAL_DESCRIPTION = "signalDescription";

    public static final String PROPERTY_SIGNAL_OWNER = "signalOwner";

    /** Constructs a new WorkflowSignalEditViewModel instance. */
    public WorkflowSignalEditViewModel() {
        super();
    }

    /**
     * Getter for the ID.
     *
     * @return the String.
     */
    public String getID() {
        return "org.nabucco.framework.workflow.ui.rcp.edit.signal.model.WorkflowSignalEditViewModel";
    }

    /**
     * Getter for the Values.
     *
     * @return the Map<String, Serializable>.
     */
    public Map<String, Serializable> getValues() {
        Map<String, Serializable> result = super.getValues();
        result.put(PROPERTY_SIGNAL_DESCRIPTION, this.getSignalDescription());
        result.put(PROPERTY_SIGNAL_OWNER, this.getSignalOwner());
        result.put(PROPERTY_SIGNAL_NAME, this.getSignalName());
        return result;
    }

    /**
     * Setter for the Signal.
     *
     * @param newValue the WorkflowSignal.
     */
    public void setSignal(WorkflowSignal newValue) {
        WorkflowSignal oldValue = this.signal;
        this.signal = newValue;
        this.updateProperty(PROPERTY_SIGNAL_DESCRIPTION, ((oldValue != null) ? oldValue.getDescription() : ""),
                ((newValue != null) ? newValue.getDescription() : ""));
        this.updateProperty(PROPERTY_SIGNAL_NAME, ((oldValue != null) ? oldValue.getName() : ""),
                ((newValue != null) ? newValue.getName() : ""));
        this.updateProperty(PROPERTY_SIGNAL_OWNER, ((oldValue != null) ? oldValue.getOwner() : ""),
                ((newValue != null) ? newValue.getOwner() : ""));
    }

    /**
     * Getter for the Signal.
     *
     * @return the WorkflowSignal.
     */
    public WorkflowSignal getSignal() {
        return this.signal;
    }

    /**
     * Setter for the SignalName.
     *
     * @param newName the String.
     */
    public void setSignalName(String newName) {
        if (((signal != null) && (signal.getName() == null))) {
            Name name = new Name();
            signal.setName(name);
        }
        String oldVal = signal.getName().getValue();
        signal.getName().setValue(newName);
        this.updateProperty(PROPERTY_SIGNAL_NAME, oldVal, newName);
        if (((!oldVal.equals(newName)) && signal.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            signal.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the SignalName.
     *
     * @return the String.
     */
    public String getSignalName() {
        if ((((signal == null) || (signal.getName() == null)) || (signal.getName().getValue() == null))) {
            return "";
        }
        return signal.getName().getValue();
    }

    /**
     * Setter for the SignalDescription.
     *
     * @param newDescription the String.
     */
    public void setSignalDescription(String newDescription) {
        if (((signal != null) && (signal.getDescription() == null))) {
            Description description = new Description();
            signal.setDescription(description);
        }
        String oldVal = signal.getDescription().getValue();
        signal.getDescription().setValue(newDescription);
        this.updateProperty(PROPERTY_SIGNAL_DESCRIPTION, oldVal, newDescription);
        if (((!oldVal.equals(newDescription)) && signal.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            signal.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the SignalDescription.
     *
     * @return the String.
     */
    public String getSignalDescription() {
        if ((((signal == null) || (signal.getDescription() == null)) || (signal.getDescription().getValue() == null))) {
            return "";
        }
        return signal.getDescription().getValue();
    }

    /**
     * Setter for the SignalOwner.
     *
     * @param newOwner the String.
     */
    public void setSignalOwner(String newOwner) {
        if (((signal != null) && (signal.getOwner() == null))) {
            Owner owner = new Owner();
            signal.setOwner(owner);
        }
        String oldVal = signal.getOwner().getValue();
        signal.getOwner().setValue(newOwner);
        this.updateProperty(PROPERTY_SIGNAL_OWNER, oldVal, newOwner);
        if (((!oldVal.equals(newOwner)) && signal.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            signal.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the SignalOwner.
     *
     * @return the String.
     */
    public String getSignalOwner() {
        if ((((signal == null) || (signal.getOwner() == null)) || (signal.getOwner().getValue() == null))) {
            return "";
        }
        return signal.getOwner().getValue();
    }
}
