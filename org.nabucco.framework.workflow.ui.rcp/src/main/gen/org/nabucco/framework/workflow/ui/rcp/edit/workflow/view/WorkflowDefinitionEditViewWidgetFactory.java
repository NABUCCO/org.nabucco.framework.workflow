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
package org.nabucco.framework.workflow.ui.rcp.edit.workflow.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.nabucco.framework.plugin.base.component.picker.combo.ElementPickerCombo;
import org.nabucco.framework.plugin.base.component.picker.combo.ElementPickerComboParameter;
import org.nabucco.framework.plugin.base.layout.WidgetFactory;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.workflow.ui.rcp.edit.workflow.model.WorkflowDefinitionEditViewModel;

/**
 * WorkflowDefinitionEditViewWidgetFactory<p/>Edit view for datatype WorkflowDefintion<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-25
 */
public class WorkflowDefinitionEditViewWidgetFactory extends WidgetFactory {

    private WorkflowDefinitionEditViewModel model;

    public static final String SECTION = "SectionName";

    public static final String LABEL_NAME = "workflow.name";

    public static final String OBSERVE_VALUE_NAME = WorkflowDefinitionEditViewModel.PROPERTY_WORKFLOW_NAME;

    public static final String LABEL_DESCRIPTION = "workflow.description";

    public static final String OBSERVE_VALUE_DESCRIPTION = WorkflowDefinitionEditViewModel.PROPERTY_WORKFLOW_DESCRIPTION;

    public static final String LABEL_TYPE = "workflow.workflowType";

    public static final String OBSERVE_VALUE_TYPE = WorkflowDefinitionEditViewModel.PROPERTY_WORKFLOW_WORKFLOWTYPE;

    /**
     * Constructs a new WorkflowDefinitionEditViewWidgetFactory instance.
     *
     * @param model the WorkflowDefinitionEditViewModel.
     * @param nabuccoFormToolKit the NabuccoFormToolkit.
     */
    public WorkflowDefinitionEditViewWidgetFactory(NabuccoFormToolkit nabuccoFormToolKit,
            WorkflowDefinitionEditViewModel model) {
        super(nabuccoFormToolKit);
        this.model = model;
    }

    /**
     * CreateSectionHeading.
     *
     * @param parent the Composite.
     * @return the Section.
     */
    public Section createSectionHeading(Composite parent) {
        return nabuccoFormToolKit.createSection(parent, SECTION, new GridLayout());
    }

    /**
     * CreateLabelName.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelName(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_NAME);
    }

    /**
     * CreateInputFieldName.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldName(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model, OBSERVE_VALUE_NAME);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }

    /**
     * CreateLabelDescription.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelDescription(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_DESCRIPTION);
    }

    /**
     * CreateInputFieldDescription.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldDescription(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model, OBSERVE_VALUE_DESCRIPTION);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }

    /**
     * CreateLabelType.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelType(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_TYPE);
    }

    /**
     * CreateElementComboType.
     *
     * @param params the ElementPickerComboParameter.
     * @param parent the Composite.
     * @return the ElementPickerCombo.
     */
    public ElementPickerCombo createElementComboType(Composite parent, ElementPickerComboParameter params) {
        ElementPickerCombo elementCombo = new ElementPickerCombo(parent, SWT.NONE, params.getContentProvider(),
                params.getTableLabelProvider(), "", false);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement;
        IObservableValue modelElement;
        uiElement = SWTObservables.observeSelection(elementCombo.getCombo());
        modelElement = BeansObservables.observeValue(model, OBSERVE_VALUE_TYPE);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        elementCombo.addSelectionListener(new WorkflowDefinitionEditViewTypeComboBoxHandler(model));
        return elementCombo;
    }
}
