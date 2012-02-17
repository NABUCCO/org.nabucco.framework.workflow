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
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceEntryMsg;

/**
 * ProduceWorkflowInstanceEntryServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProduceWorkflowInstanceEntryServiceHandlerImpl extends ProduceWorkflowInstanceEntryServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public WorkflowInstanceEntryMsg produceWorkflowInstanceEntry(WorkflowStateMsg rq) throws ProduceException {

        WorkflowInstanceEntryMsg rs = new WorkflowInstanceEntryMsg();

        WorkflowInstanceEntry entry = new WorkflowInstanceEntry();
        entry.setDatatypeState(DatatypeState.INITIALIZED);
        entry.setOwner(NabuccoInstance.getInstance().getOwner());

        Subject subject = super.getContext().getSubject();
        entry.setModifier(subject.getUserId().getValue());
        entry.setModificationTime(NabuccoSystem.getCurrentTimestamp());
        entry.setState(rq.getState());

        rs.setWorkflowEntry(entry);

        return rs;
    }

}
