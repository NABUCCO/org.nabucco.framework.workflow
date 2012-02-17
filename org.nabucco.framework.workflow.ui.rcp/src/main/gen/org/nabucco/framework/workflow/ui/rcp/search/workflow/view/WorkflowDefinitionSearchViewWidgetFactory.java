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
package org.nabucco.framework.workflow.ui.rcp.search.workflow.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.plugin.base.layout.WidgetFactory;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.workflow.ui.rcp.search.workflow.model.WorkflowDefinitionSearchViewModel;

/**
 * WorkflowDefinitionSearchViewWidgetFactory<p/>Search View for WorkflowDefinitions.<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-02-27
 */
public class WorkflowDefinitionSearchViewWidgetFactory extends WidgetFactory {

    private WorkflowDefinitionSearchViewModel model;

    public static final String LABEL_WORKFLOWDESCRIPTION = "workflow.description";

    public static final String OBSERVE_VALUE_WORKFLOWDESCRIPTION = WorkflowDefinitionSearchViewModel.PROPERTY_WORKFLOW_DESCRIPTION;

    public static final String LABEL_WORKFLOWNAME = "workflow.name";

    public static final String OBSERVE_VALUE_WORKFLOWNAME = WorkflowDefinitionSearchViewModel.PROPERTY_WORKFLOW_NAME;

    /**
     * Constructs a new WorkflowDefinitionSearchViewWidgetFactory instance.
     *
     * @param aModel the WorkflowDefinitionSearchViewModel.
     * @param nabuccoFormToolKit the NabuccoFormToolkit.
     */
    public WorkflowDefinitionSearchViewWidgetFactory(final NabuccoFormToolkit nabuccoFormToolKit,
            final WorkflowDefinitionSearchViewModel aModel) {
        super(nabuccoFormToolKit);
        model = aModel;
    }

    /**
     * CreateLabelWorkflowDescription.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelWorkflowDescription(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_WORKFLOWDESCRIPTION);
    }

    /**
     * CreateInputFieldWorkflowDescription.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldWorkflowDescription(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model, OBSERVE_VALUE_WORKFLOWDESCRIPTION);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }

    /**
     * CreateLabelWorkflowName.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelWorkflowName(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_WORKFLOWNAME);
    }

    /**
     * CreateInputFieldWorkflowName.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldWorkflowName(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model, OBSERVE_VALUE_WORKFLOWNAME);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }
}
