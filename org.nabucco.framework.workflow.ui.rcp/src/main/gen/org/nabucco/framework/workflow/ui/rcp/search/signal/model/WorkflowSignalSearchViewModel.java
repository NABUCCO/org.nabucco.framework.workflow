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
package org.nabucco.framework.workflow.ui.rcp.search.signal.model;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchParameter;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchViewModel;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;

/**
 * WorkflowSignalSearchViewModel<p/>Search View for WorkflowSignals<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class WorkflowSignalSearchViewModel extends NabuccoComponentSearchViewModel<WorkflowSignal> implements
        NabuccoComponentSearchParameter {

    public static final String ID = "org.nabucco.framework.workflow.ui.search.signal.WorkflowSignalSearchViewModel";

    private WorkflowSignal signal;

    public static final String PROPERTY_SIGNAL_NAME = "signalName";

    public static final String PROPERTY_SIGNAL_DESCRIPTION = "signalDescription";

    public static String TITLE = (ID + "Title");

    /**
     * Constructs a new WorkflowSignalSearchViewModel instance.
     *
     * @param viewId the String.
     */
    public WorkflowSignalSearchViewModel(String viewId) {
        super();
        correspondingListView = viewId;
        this.signal = new WorkflowSignal();
    }

    @Override
    public String getSearchModelId() {
        return searchModelId;
    }

    @Override
    public NabuccoComponentSearchParameter getSearchParameter() {
        return this;
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

    @Override
    public String getId() {
        return WorkflowSignalSearchViewModel.ID;
    }
}
