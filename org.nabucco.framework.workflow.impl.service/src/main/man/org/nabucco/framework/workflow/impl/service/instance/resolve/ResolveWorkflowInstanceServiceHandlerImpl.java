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
package org.nabucco.framework.workflow.impl.service.instance.resolve;

import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.impl.service.instance.resolve.util.WorkflowInstanceResolver;

/**
 * ResolveWorkflowInstanceServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResolveWorkflowInstanceServiceHandlerImpl extends ResolveWorkflowInstanceServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected WorkflowInstanceMsg resolveWorkflowInstance(WorkflowInstanceMsg rq) throws ResolveException {

        WorkflowInstance instance = rq.getWorkflowInstance();

        WorkflowInstanceResolver resolver = new WorkflowInstanceResolver(super.getPersistenceManager());
        instance = resolver.resolveInstance(instance);

        WorkflowInstanceMsg rs = new WorkflowInstanceMsg();
        rs.setWorkflowInstance(instance);
        return rs;
    }

}
