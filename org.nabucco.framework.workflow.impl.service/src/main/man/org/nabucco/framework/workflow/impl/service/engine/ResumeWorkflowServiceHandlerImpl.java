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
package org.nabucco.framework.workflow.impl.service.engine;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowResult;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.facade.message.engine.ResumeWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.impl.service.engine.util.WorkflowFacade;
import org.nabucco.framework.workflow.impl.service.engine.util.signal.TransitionFilter;

/**
 * ResumeWorkflowServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResumeWorkflowServiceHandlerImpl extends ResumeWorkflowServiceHandler {

    private static final long serialVersionUID = 1L;

    private WorkflowFacade facade;

    @Override
    protected WorkflowResultRs resumeWorkflow(ResumeWorkflowRq rq) throws WorkflowException {
        this.facade = WorkflowFacade.newInstance(super.getContext());

        WorkflowResult result = new WorkflowResult();
        WorkflowInstance instance = rq.getWorkflowInstance();
        WorkflowContext context = rq.getWorkflowContext();

        try {
            instance = this.facade.resolveInstance(instance);
            result.setInstance(instance);

            TransitionFilter transitionFilter = new TransitionFilter(instance, context, super.getContext());
            List<TransitionParameter> nextTransitions = transitionFilter.filterNextTransitions(instance);
            result.getNextTransitions().addAll(nextTransitions);

        } catch (ServiceException e) {
            throw new WorkflowException("Error resuming workflow with id " + instance.getId() + ".", e);
        }

        WorkflowResultRs rs = new WorkflowResultRs();
        rs.setResult(result);

        return rs;
    }

}
