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
package org.nabucco.framework.workflow.impl.service.definition.resolve;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;

/**
 * ResolveWorkflowDefinitionServiceHandler<p/>Workflow definition resolution service.<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2010-04-28
 */
public abstract class ResolveWorkflowDefinitionServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.workflow.impl.service.definition.resolve.ResolveWorkflowDefinitionServiceHandler";

    /** Constructs a new ResolveWorkflowDefinitionServiceHandler instance. */
    public ResolveWorkflowDefinitionServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<WorkflowDefinitionListMsg>.
     * @return the ServiceResponse<WorkflowDefinitionListMsg>.
     * @throws ResolveException
     */
    protected ServiceResponse<WorkflowDefinitionListMsg> invoke(ServiceRequest<WorkflowDefinitionListMsg> rq)
            throws ResolveException {
        ServiceResponse<WorkflowDefinitionListMsg> rs;
        WorkflowDefinitionListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.resolveWorkflowDefinition(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<WorkflowDefinitionListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ResolveException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ResolveException wrappedException = new ResolveException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ResolveException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method resolveWorkflowDefinition.
     *
     * @param msg the WorkflowDefinitionListMsg.
     * @return the WorkflowDefinitionListMsg.
     * @throws ResolveException
     */
    protected abstract WorkflowDefinitionListMsg resolveWorkflowDefinition(WorkflowDefinitionListMsg msg)
            throws ResolveException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
