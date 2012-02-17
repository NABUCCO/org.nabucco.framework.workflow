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

import org.eclipse.swt.widgets.Composite;
import org.nabucco.framework.plugin.base.view.AbstractNabuccoSearchView;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.framework.plugin.base.view.NabuccoSearchView;
import org.nabucco.framework.workflow.ui.rcp.search.workflow.model.WorkflowDefinitionSearchViewModel;

/**
 * WorkflowDefinitionSearchView<p/>Search View for WorkflowDefinitions.<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-02-27
 */
public class WorkflowDefinitionSearchView extends AbstractNabuccoSearchView<WorkflowDefinitionSearchViewModel>
        implements NabuccoSearchView {

    private WorkflowDefinitionSearchViewModel model;

    public static final String ID = "org.nabucco.framework.workflow.ui.search.workflow.WorkflowDefinitionSearchView";

    /** Constructs a new WorkflowDefinitionSearchView instance. */
    public WorkflowDefinitionSearchView() {
        super();
        model = new WorkflowDefinitionSearchViewModel(this.getCorrespondingListView());
    }

    @Override
    public void createPartControl(final Composite parent, final NabuccoMessageManager aMessageManager) {
        this.getLayouter().layout(parent, aMessageManager, model);
    }

    @Override
    public WorkflowDefinitionSearchViewModel getModel() {
        return model;
    }

    @Override
    public String getId() {
        return WorkflowDefinitionSearchView.ID;
    }
}
