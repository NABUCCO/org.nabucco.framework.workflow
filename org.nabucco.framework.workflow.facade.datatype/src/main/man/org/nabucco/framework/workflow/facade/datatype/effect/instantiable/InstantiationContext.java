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
package org.nabucco.framework.workflow.facade.datatype.effect.instantiable;

import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;

/**
 * InstantiationContext
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InstantiationContext {

    /** The current workflow instance. */
    private WorkflowInstance instance;

    /** The context of the current service. */
    private ServiceMessageContext serviceContext;

    /** The workflow context. */
    private Context workflowContext;

    /**
     * Creates a new {@link InstantiationContext} instance.
     * 
     * @param instance
     *            the workflow instance
     * @param context
     *            the workflow context of the current transition
     * @param serviceContext
     *            the service context
     */
    public InstantiationContext(WorkflowInstance instance, Context context, ServiceMessageContext serviceContext) {
        this.instance = instance;
        this.serviceContext = serviceContext;
        this.workflowContext = context;
    }

    /**
     * Getter for the instance.
     * 
     * @return Returns the instance.
     */
    public WorkflowInstance getInstance() {
        return this.instance;
    }

    /**
     * Getter for the workflowContext.
     * 
     * @return Returns the workflowContext.
     */
    public Context getWorkflowContext() {
        return this.workflowContext;
    }

    /**
     * Getter for the serviceContext.
     * 
     * @return Returns the serviceContext.
     */
    public ServiceMessageContext getServiceContext() {
        return this.serviceContext;
    }

}
