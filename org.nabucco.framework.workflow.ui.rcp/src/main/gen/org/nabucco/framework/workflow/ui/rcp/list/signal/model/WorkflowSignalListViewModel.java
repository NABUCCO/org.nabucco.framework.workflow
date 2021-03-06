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
package org.nabucco.framework.workflow.ui.rcp.list.signal.model;

import org.nabucco.framework.plugin.base.model.ListViewModel;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;

/**
 * WorkflowSignalListViewModel<p/>ListView for WorkflowSignals<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-18
 */
public class WorkflowSignalListViewModel extends ListViewModel<WorkflowSignal> {

    /** Constructs a new WorkflowSignalListViewModel instance. */
    public WorkflowSignalListViewModel() {
        super();
    }
}
