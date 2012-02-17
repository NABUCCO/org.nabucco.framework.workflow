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

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;

/**
 * 
 * ProduceWorkflowInstanceServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProduceWorkflowInstanceServiceHandlerImpl extends ProduceWorkflowInstanceServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public WorkflowInstanceMsg produceWorkflowInstance(WorkflowDefinitionMsg rq) throws ProduceException {

        WorkflowDefinition workflow = rq.getWorkflow();

        String name = workflow.getName() + "-" + NabuccoSystem.getCurrentTimeMillis();

        WorkflowInstance instance = new WorkflowInstance();
        instance.setDatatypeState(DatatypeState.INITIALIZED);
        instance.setOwner(NabuccoInstance.getInstance().getOwner());
        instance.setName(name);

        Subject subject = super.getContext().getSubject();
        instance.setCreator(subject.getUserId().getValue());
        instance.setCreationTime(NabuccoSystem.getCurrentTimestamp());
        instance.setDefinition(workflow);

        instance.setAssignedUser(instance.getCreator());

        WorkflowInstanceMsg rs = new WorkflowInstanceMsg();
        rs.setWorkflowInstance(instance);

        return rs;
    }

}
