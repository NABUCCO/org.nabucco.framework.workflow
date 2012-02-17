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
package org.nabucco.framework.workflow.facade.service.engine;

import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.ResumeWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowTimerRq;

/**
 * WorkflowEngineService<p/>Workflow Engine service that executes the workflow transitions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public interface WorkflowEngineService extends Service {

    /**
     * Starts a new workflow execution.
     *
     * @param rq the ServiceRequest<InitWorkflowRq>.
     * @return the ServiceResponse<WorkflowResultRs>.
     * @throws WorkflowException
     */
    ServiceResponse<WorkflowResultRs> startWorkflow(ServiceRequest<InitWorkflowRq> rq) throws WorkflowException;

    /**
     * Performs a transition between two workflow states.
     *
     * @param rq the ServiceRequest<ExecuteWorkflowRq>.
     * @return the ServiceResponse<WorkflowResultRs>.
     * @throws WorkflowException
     */
    ServiceResponse<WorkflowResultRs> processTransition(ServiceRequest<ExecuteWorkflowRq> rq) throws WorkflowException;

    /**
     * Evaluates the next steps for a workflow instance.
     *
     * @param rq the ServiceRequest<ResumeWorkflowRq>.
     * @return the ServiceResponse<WorkflowResultRs>.
     * @throws WorkflowException
     */
    ServiceResponse<WorkflowResultRs> resumeWorkflow(ServiceRequest<ResumeWorkflowRq> rq) throws WorkflowException;

    /**
     * Start the workflow timer service.
     *
     * @param rq the ServiceRequest<WorkflowTimerRq>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws WorkflowException
     */
    ServiceResponse<EmptyServiceMessage> startTimer(ServiceRequest<WorkflowTimerRq> rq) throws WorkflowException;
}
