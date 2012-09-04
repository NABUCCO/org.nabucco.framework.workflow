/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.workflow.impl.service.engine;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;

/**
 * ProcessTransitionServiceHandler<p/>Workflow Engine service that executes the workflow transitions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public abstract class ProcessTransitionServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.workflow.impl.service.engine.ProcessTransitionServiceHandler";

    /** Constructs a new ProcessTransitionServiceHandler instance. */
    public ProcessTransitionServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ExecuteWorkflowRq>.
     * @return the ServiceResponse<WorkflowResultRs>.
     * @throws WorkflowException
     */
    protected ServiceResponse<WorkflowResultRs> invoke(ServiceRequest<ExecuteWorkflowRq> rq) throws WorkflowException {
        ServiceResponse<WorkflowResultRs> rs;
        WorkflowResultRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.processTransition(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<WorkflowResultRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (WorkflowException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            WorkflowException wrappedException = new WorkflowException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new WorkflowException("Error during service invocation.", e);
        }
    }

    /**
     * Performs a transition between two workflow states.
     *
     * @param msg the ExecuteWorkflowRq.
     * @return the WorkflowResultRs.
     * @throws WorkflowException
     */
    protected abstract WorkflowResultRs processTransition(ExecuteWorkflowRq msg) throws WorkflowException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
