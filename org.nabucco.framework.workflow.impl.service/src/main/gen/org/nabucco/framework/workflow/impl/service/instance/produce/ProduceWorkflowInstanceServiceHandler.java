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
package org.nabucco.framework.workflow.impl.service.instance.produce;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;

/**
 * ProduceWorkflowInstanceServiceHandler<p/>Workflow instance produce service<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public abstract class ProduceWorkflowInstanceServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.workflow.impl.service.instance.produce.ProduceWorkflowInstanceServiceHandler";

    /** Constructs a new ProduceWorkflowInstanceServiceHandler instance. */
    public ProduceWorkflowInstanceServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<WorkflowDefinitionMsg>.
     * @return the ServiceResponse<WorkflowInstanceMsg>.
     * @throws ProduceException
     */
    protected ServiceResponse<WorkflowInstanceMsg> invoke(ServiceRequest<WorkflowDefinitionMsg> rq)
            throws ProduceException {
        ServiceResponse<WorkflowInstanceMsg> rs;
        WorkflowInstanceMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.produceWorkflowInstance(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<WorkflowInstanceMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ProduceException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ProduceException wrappedException = new ProduceException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ProduceException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method produceWorkflowInstance.
     *
     * @param msg the WorkflowDefinitionMsg.
     * @return the WorkflowInstanceMsg.
     * @throws ProduceException
     */
    protected abstract WorkflowInstanceMsg produceWorkflowInstance(WorkflowDefinitionMsg msg) throws ProduceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
