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

import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.component.dialog.DialogType;
import org.nabucco.framework.base.ui.web.component.work.WorkItemWorkflow;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowEntry;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * ProcessWorkflowActionHandler
 * 
 * This action process the in the workflow configured action
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ProcessWorkflowActionHandler extends WebActionHandlerSupport {

    /** Action ID */
    public final static String ID = "Workflow.ProcessWorkflowAction";

    private static final String DIALOG_CONTROL_COMMENT = "comment";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        if (editorId == null || editorId.isEmpty()) {
            throw new ClientException("Cannot process workflow operation. The editor id is 'null'.");
        }

        EditorItem editor = (EditorItem) NabuccoServletUtil.getWorkItem(editorId);
        if (editor == null) {
            throw new ClientException("Cannot process workflow operation. The editor with id "
                    + editorId + " was not found.");
        }

        WorkItemWorkflow workflow = editor.getWorkflow();
        String actionId = workflow.getActionId();

        if (actionId == null || actionId.isEmpty()) {
            throw new ClientException("Cannot process workflow operation. There is no action configured.");
        }

        this.addComment(parameter, workflow);

        WebActionResult result = new WebActionResult();
        WebActionResult subResult = super.executeAction(actionId, parameter);
        result.addResult(subResult);

        result.addItem(new RefreshItem(WebElementType.EDITOR, editorId));
        return result;
    }

    /**
     * Add the dialog comment to the workflow transition context.
     * 
     * @param parameter
     *            the action parameter
     * @param workflow
     *            the workitem workflow
     * 
     * @throws ClientException
     *             when the comment cannot be added to the transition context
     */
    private void addComment(WebActionParameter parameter, WorkItemWorkflow workflow) throws ClientException {
        String id = parameter.get(NabuccoServletPathType.DIALOG);
        if (id == null) {
            return;
        }

        Dialog dialog = NabuccoServletUtil.getDialog(id);

        if (dialog.getDialogType() != DialogType.GRIDDIALOG) {
            return;
        }

        GridDialogModel dialogModel = (GridDialogModel) dialog.getModel();
        DialogGridModelElement element = dialogModel.getControl(DIALOG_CONTROL_COMMENT);

        if (element == null || element.getType() != DialogControlType.TEXTAREA) {
            return;
        }

        String comment = ((DialogControlModel) element).getValue();

        WorkflowModel model = workflow.getModel();
        String workflowId = parameter.get(NabuccoServletPathType.WORKFLOW);
        if (workflowId == null || workflowId.isEmpty()) {
            throw new ClientException("The workflow operation cannot process. Workflow ID is not defined.");
        }

        WorkflowEntry workflowEntry = model.getEntry(workflowId);
        if (workflowEntry == null) {
            throw new ClientException("The workflow operation cannot process. Workflow is not found with ID + '"
                    + workflowId + "'.");
        }

        TransitionContext transitionContext = workflowEntry.getTransitionContext();
        transitionContext.setComment(comment);
    }

}
