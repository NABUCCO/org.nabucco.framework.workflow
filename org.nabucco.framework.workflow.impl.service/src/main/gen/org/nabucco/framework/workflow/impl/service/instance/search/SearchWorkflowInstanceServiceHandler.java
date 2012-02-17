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
package org.nabucco.framework.workflow.impl.service.instance.search;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceListMsg;
import org.nabucco.framework.workflow.facade.message.instance.search.WorkflowInstanceSearchRq;

/**
 * SearchWorkflowInstanceServiceHandler<p/>Workflow instance search service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-28
 */
public abstract class SearchWorkflowInstanceServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.workflow.impl.service.instance.search.SearchWorkflowInstanceServiceHandler";

    /** Constructs a new SearchWorkflowInstanceServiceHandler instance. */
    public SearchWorkflowInstanceServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<WorkflowInstanceSearchRq>.
     * @return the ServiceResponse<WorkflowInstanceListMsg>.
     * @throws SearchException
     */
    protected ServiceResponse<WorkflowInstanceListMsg> invoke(ServiceRequest<WorkflowInstanceSearchRq> rq)
            throws SearchException {
        ServiceResponse<WorkflowInstanceListMsg> rs;
        WorkflowInstanceListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.searchWorkflowInstance(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<WorkflowInstanceListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (SearchException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SearchException wrappedException = new SearchException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SearchException("Error during service invocation.", e);
        }
    }

    /**
     * Search for workflow instances.
     *
     * @param msg the WorkflowInstanceSearchRq.
     * @return the WorkflowInstanceListMsg.
     * @throws SearchException
     */
    protected abstract WorkflowInstanceListMsg searchWorkflowInstance(WorkflowInstanceSearchRq msg)
            throws SearchException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
