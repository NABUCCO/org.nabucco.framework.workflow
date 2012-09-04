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
package org.nabucco.framework.workflow.ui.web.communication.datatype.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;
import org.nabucco.framework.workflow.facade.service.datatype.produce.ProduceWorkflowDatatype;

/**
 * ProduceWorkflowDatatypeDelegate<p/>Workflow datatype produce service<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class ProduceWorkflowDatatypeDelegate extends ServiceDelegateSupport {

    private ProduceWorkflowDatatype service;

    /**
     * Constructs a new ProduceWorkflowDatatypeDelegate instance.
     *
     * @param service the ProduceWorkflowDatatype.
     */
    public ProduceWorkflowDatatypeDelegate(ProduceWorkflowDatatype service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTask.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TaskMsg.
     * @return the TaskMsg.
     * @throws ProduceException
     */
    public TaskMsg produceTask(TaskMsg message, NabuccoSession session, ServiceSubContext... subContexts)
            throws ProduceException {
        ServiceRequest<TaskMsg> request = new ServiceRequest<TaskMsg>(super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TaskMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTask(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceWorkflowDatatype.class, "produceTask", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ProduceException("Cannot execute service operation: ProduceWorkflowDatatype.produceTask");
    }
}
