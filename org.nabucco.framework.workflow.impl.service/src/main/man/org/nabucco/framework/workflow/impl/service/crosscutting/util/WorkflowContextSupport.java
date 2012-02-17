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
package org.nabucco.framework.workflow.impl.service.crosscutting.util;

import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * WorkflowContextSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowContextSupport {

    /**
     * Private constructor must not be invoked.
     */
    private WorkflowContextSupport() {
    }

    /**
     * Create a {@link WorkflowContext} clone from a given {@link Context}.
     * 
     * @param context
     *            the original context
     * 
     * @return the workflow context clone
     */
    public static WorkflowContext createContext(Context context) {
        if (context == null) {
            return null;
        }

        WorkflowContext workflowContext = new WorkflowContext();
        workflowContext.setDatatypeState(context.getDatatypeState());
        workflowContext.setDatatype(context.getDatatype());
        workflowContext.setPropertyName(context.getPropertyName());
        return workflowContext;
    }

}
