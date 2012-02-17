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
package org.nabucco.framework.workflow.ui.web.action;

import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.handler.OpenEditorActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;

/**
 * OpenWorkflowInstanceEntryEditor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class OpenWorkflowInstanceEntryEditor extends OpenEditorActionHandler<WorkflowInstanceEntry> {

    private static final String EDITOR_ID = "WorkflowEntryEditor";

    @Override
    protected String getEditorId(WebActionParameter arg0) throws ClientException {
        return EDITOR_ID;
    }

    @Override
    protected WorkflowInstanceEntry loadDatatype(Long id, WebActionParameter parameter) throws ClientException {

        if (id == null) {
            return null;
        }

        WorkflowInstanceEntry retVal = null;

        WorkItem selectedWorkItem = NabuccoServletUtil.getSelectedWorkItem();
        if (selectedWorkItem.getType() != WebElementType.EDITOR) {
            throw new ClientException("Cannot open workflow entry editor. The selectied working item is not editor.");
        }

        EditorItem editor = (EditorItem) selectedWorkItem;

        Datatype datatype = editor.getModel().getDatatype();

        Set<NabuccoProperty> properties = datatype.getProperties();

        for (NabuccoProperty property : properties) {
            if (property.getPropertyType() == NabuccoPropertyType.COMPONENT_RELATION) {

                ComponentRelationProperty componentRelationProperty = (ComponentRelationProperty) property;

                if (componentRelationProperty.getComponentRelationType().getTarget() == WorkflowInstance.class) {
                    ComponentRelation<?> relation = componentRelationProperty.getInstance().first();
                    if (relation != null) {
                        WorkflowInstance workflowInstance = (WorkflowInstance) relation.getTarget();
                        NabuccoList<WorkflowInstanceEntry> entries = workflowInstance.getEntryList();

                        for (WorkflowInstanceEntry entry : entries) {
                            if (entry.getId().equals(id)) {
                                retVal = entry;
                                break;
                            }
                        }
                    }

                }

                if (retVal != null) {
                    break;
                }
            }
        }

        if (retVal == null) {
            throw new ClientException("Cannot resolve workflow instance entry. Not found");
        }

        return retVal;
    }
}
