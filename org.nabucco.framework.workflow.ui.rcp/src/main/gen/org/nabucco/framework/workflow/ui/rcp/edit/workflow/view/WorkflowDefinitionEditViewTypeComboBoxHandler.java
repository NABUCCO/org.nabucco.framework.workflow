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

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.nabucco.framework.workflow.ui.rcp.edit.workflow.model.WorkflowDefinitionEditViewModel;

/**
 * WorkflowDefinitionEditViewTypeComboBoxHandler<p/>Edit view for datatype WorkflowDefintion<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-25
 */
public class WorkflowDefinitionEditViewTypeComboBoxHandler implements SelectionListener {

    private WorkflowDefinitionEditViewModel model;

    /**
     * Constructs a new WorkflowDefinitionEditViewTypeComboBoxHandler instance.
     *
     * @param model the WorkflowDefinitionEditViewModel.
     */
    public WorkflowDefinitionEditViewTypeComboBoxHandler(final WorkflowDefinitionEditViewModel model) {
        super();
        this.model = model;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent selectionEvent) {
        if ((selectionEvent.widget instanceof Combo)) {
            Combo combo = ((Combo) selectionEvent.widget);
            model.setWorkflowWorkflowType(combo.getText());
        }
    }

    @Override
    public void widgetSelected(SelectionEvent selectionEvent) {
        if ((selectionEvent.widget instanceof Combo)) {
            Combo combo = ((Combo) selectionEvent.widget);
            model.setWorkflowWorkflowType(combo.getText());
        }
    }
}
