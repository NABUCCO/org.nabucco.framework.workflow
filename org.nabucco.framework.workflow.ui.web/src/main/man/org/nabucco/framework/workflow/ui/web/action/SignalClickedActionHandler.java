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

import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionCommentType;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowEntry;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * SignalClickedActionHandler
 * 
 * This action checks the type of the clicked transition and opens a dialog if necessary
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SignalClickedActionHandler extends WebActionHandlerSupport {

    public final static String ID = "Workflow.SignalClickedAction";

    private final String MANDATORY_DIALOG_ID = "WorkflowMandatoryConfirmDialog";

    private final String OPTIONAL_DIALOG_ID = "WorkflowOptionalConfirmDialog";

    @SuppressWarnings("unused")
    private final String CONFIRMATION_DIALOG_ID = "WorkflowConfirmDialog";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult retVal = new WebActionResult();

        EditorItem editor = (EditorItem) parameter.getElement();

        if (editor == null) {
            throw new ClientException("The workflow operation cannot process. Editor is 'null'");
        }

        WorkflowModel model = editor.getWorkflow().getModel();

        String workflowId = parameter.get(NabuccoServletPathType.WORKFLOW);
        String workflowSignalName = parameter.get(NabuccoServletPathType.SIGNAL);

        if (workflowId == null || workflowId.isEmpty()) {
            throw new ClientException("The workflow operation cannot process. Workflow ID is not defined.");
        }

        if (workflowSignalName == null || workflowSignalName.isEmpty()) {
            throw new ClientException("The workflow operation cannot process. Workflow Signal is not defined.");
        }

        WorkflowEntry workflowEntry = model.getEntry(workflowId);
        if (workflowEntry == null) {
            throw new ClientException("The workflow operation cannot process. Workflow is not found with ID + '"
                    + workflowId + "'.");
        }

        TransitionContext transitionContext = workflowEntry.getTransitionContext();

        TransitionParameter workflowTransition = null;

        for (TransitionParameter transitionParamter : transitionContext.getNextTransitions()) {
            Name signalName = transitionParamter.getSignal().getName();
            if (signalName.getValueAsString().equals(workflowSignalName)) {
                workflowTransition = transitionParamter;
                break;
            }
        }

        if (workflowTransition == null) {
            // Cannot find
            return retVal;
        }

        TransitionCommentType cardinality = workflowTransition.getCommentCardinality();

        String dialogId = null;

        switch (cardinality) {
        case MANDATORY: {
            dialogId = this.MANDATORY_DIALOG_ID;
            break;
        }
        case OPTIONAL: {
            dialogId = this.OPTIONAL_DIALOG_ID;
            break;
        }
        case NONE: {
            WebActionResult subActionResult = super.executeAction(ProcessWorkflowActionHandler.ID, parameter);
            retVal.addResult(subActionResult);
            break;
        }
        }

        if (dialogId != null) {
            Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(dialogId);
            dialog.getModel().addParameter(NabuccoServletPathType.EDITOR, editor.getInstanceId());
            dialog.getModel().addParameter(NabuccoServletPathType.WORKFLOW, workflowId);
            dialog.getModel().addParameter(NabuccoServletPathType.SIGNAL, workflowSignalName);
            retVal.addItem(new OpenItem(WebElementType.DIALOG, dialog.getInstanceId()));
        }

        return retVal;
    }

}
