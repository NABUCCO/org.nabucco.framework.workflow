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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.Viewer;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerContentProvider;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinitionType;

/**
 * WorkflowDefinitionEditViewTypeContentProvider<p/>Edit view for datatype WorkflowDefintion<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-25
 */
public class WorkflowDefinitionEditViewTypeContentProvider implements ElementPickerContentProvider {

    /** Constructs a new WorkflowDefinitionEditViewTypeContentProvider instance. */
    public WorkflowDefinitionEditViewTypeContentProvider() {
        super();
    }

    @Override
    public String[] getPaths() {
        return null;
    }

    @Override
    public Object[] getElements(Object arg0) {
        List<WorkflowDefinitionType> list = new ArrayList<WorkflowDefinitionType>();
        for (WorkflowDefinitionType ct : WorkflowDefinitionType.values()) {
            list.add(ct);
        }
        return list.toArray();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
    }
}
