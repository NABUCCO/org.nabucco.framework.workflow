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
package org.nabucco.framework.workflow.ui.rcp.browser.workflow;

import java.io.Serializable;
import java.util.Map;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjectionReciever;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.model.browser.DatatypeBrowserElement;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.ui.rcp.edit.workflow.model.WorkflowDefinitionEditViewModel;

/**
 * WorkflowDefinitionEditViewBrowserElement
 *
 * @author Undefined
 */
public class WorkflowDefinitionEditViewBrowserElement extends DatatypeBrowserElement implements
        NabuccoInjectionReciever {

    private WorkflowDefinitionEditViewModel viewModel;

    private WorkflowDefinitionEditViewBrowserElementHandler browserHandler;

    /**
     * Constructs a new WorkflowDefinitionEditViewBrowserElement instance.
     *
     * @param datatype the WorkflowDefinition.
     */
    public WorkflowDefinitionEditViewBrowserElement(final WorkflowDefinition datatype) {
        super();
        NabuccoInjector instance = NabuccoInjector.getInstance(WorkflowDefinitionEditViewBrowserElement.class);
        browserHandler = instance.inject(WorkflowDefinitionEditViewBrowserElementHandler.class);
        viewModel = new WorkflowDefinitionEditViewModel();
        viewModel.setWorkflow(datatype);
    }

    @Override
    protected void fillDatatype() {
        viewModel = browserHandler.loadFull(viewModel);
    }

    @Override
    protected void createChildren() {
        this.clearChildren();
        browserHandler.createChildren(viewModel, this);
    }

    @Override
    public Map<String, Serializable> getValues() {
        return this.viewModel.getValues();
    }

    /**
     * Getter for the ViewModel.
     *
     * @return the WorkflowDefinitionEditViewModel.
     */
    public WorkflowDefinitionEditViewModel getViewModel() {
        return this.viewModel;
    }

    /**
     * Setter for the ViewModel.
     *
     * @param viewModel the WorkflowDefinitionEditViewModel.
     */
    public void setViewModel(WorkflowDefinitionEditViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
