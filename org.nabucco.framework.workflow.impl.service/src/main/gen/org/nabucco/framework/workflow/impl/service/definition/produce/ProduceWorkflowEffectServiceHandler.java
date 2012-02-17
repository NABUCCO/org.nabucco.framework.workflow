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
package org.nabucco.framework.workflow.impl.service.definition.produce;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowEffectMsg;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowEffectRq;

/**
 * ProduceWorkflowEffectServiceHandler<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public abstract class ProduceWorkflowEffectServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.workflow.impl.service.definition.produce.ProduceWorkflowEffectServiceHandler";

    /** Constructs a new ProduceWorkflowEffectServiceHandler instance. */
    public ProduceWorkflowEffectServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ProduceWorkflowEffectRq>.
     * @return the ServiceResponse<WorkflowEffectMsg>.
     * @throws ProduceException
     */
    protected ServiceResponse<WorkflowEffectMsg> invoke(ServiceRequest<ProduceWorkflowEffectRq> rq)
            throws ProduceException {
        ServiceResponse<WorkflowEffectMsg> rs;
        WorkflowEffectMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.produceWorkflowEffect(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<WorkflowEffectMsg>(rq.getContext());
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
     * Creates a new WorkflowEffect for a given Effect Type.
     *
     * @param msg the ProduceWorkflowEffectRq.
     * @return the WorkflowEffectMsg.
     * @throws ProduceException
     */
    protected abstract WorkflowEffectMsg produceWorkflowEffect(ProduceWorkflowEffectRq msg) throws ProduceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
