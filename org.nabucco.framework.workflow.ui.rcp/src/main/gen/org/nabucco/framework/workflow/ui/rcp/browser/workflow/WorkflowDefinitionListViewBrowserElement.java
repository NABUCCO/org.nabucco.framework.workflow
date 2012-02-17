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

import java.util.List;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjectionReciever;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.model.browser.BrowserElement;
import org.nabucco.framework.plugin.base.model.browser.BrowserListElement;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.ui.rcp.list.workflow.model.WorkflowDefinitionListViewModel;

/**
 * WorkflowDefinitionListViewBrowserElement
 *
 * @author Undefined
 */
public class WorkflowDefinitionListViewBrowserElement extends BrowserListElement<WorkflowDefinitionListViewModel>
        implements NabuccoInjectionReciever {

    private WorkflowDefinitionListViewBrowserElementHandler listViewBrowserElementHandler;

    /**
     * Constructs a new WorkflowDefinitionListViewBrowserElement instance.
     *
     * @param datatypeList the List<WorkflowDefinition>.
     */
    public WorkflowDefinitionListViewBrowserElement(final List<WorkflowDefinition> datatypeList) {
        this(datatypeList.toArray(new WorkflowDefinition[datatypeList.size()]));
    }

    /**
     * Constructs a new WorkflowDefinitionListViewBrowserElement instance.
     *
     * @param datatypeArray the WorkflowDefinition[].
     */
    public WorkflowDefinitionListViewBrowserElement(final WorkflowDefinition[] datatypeArray) {
        super();
        NabuccoInjector instance = NabuccoInjector.getInstance(WorkflowDefinitionListViewBrowserElement.class);
        listViewBrowserElementHandler = instance.inject(WorkflowDefinitionListViewBrowserElementHandler.class);
        viewModel = new WorkflowDefinitionListViewModel();
        viewModel.setElements(datatypeArray);
    }

    @Override
    protected void createChildren() {
        this.clearChildren();
        listViewBrowserElementHandler.createChildren(viewModel, this);
    }

    @Override
    public void removeBrowserElement(final BrowserElement element) {
        super.removeBrowserElement(element);
        listViewBrowserElementHandler.removeChild(element, this);
    }
}
