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

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowTimerRq;

/**
 * StartTimerServiceHandler<p/>Workflow Engine service that executes the workflow transitions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public abstract class StartTimerServiceHandler extends PersistenceServiceHandlerSupport implements ServiceHandler,
        PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.workflow.impl.service.engine.StartTimerServiceHandler";

    /** Constructs a new StartTimerServiceHandler instance. */
    public StartTimerServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<WorkflowTimerRq>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws WorkflowException
     */
    protected ServiceResponse<EmptyServiceMessage> invoke(ServiceRequest<WorkflowTimerRq> rq) throws WorkflowException {
        ServiceResponse<EmptyServiceMessage> rs;
        EmptyServiceMessage msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.startTimer(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<EmptyServiceMessage>(rq.getContext());
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
     * Start the workflow timer service.
     *
     * @param msg the WorkflowTimerRq.
     * @return the EmptyServiceMessage.
     * @throws WorkflowException
     */
    protected abstract EmptyServiceMessage startTimer(WorkflowTimerRq msg) throws WorkflowException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
