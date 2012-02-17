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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowTransitionMsg;

/**
 * ProduceWorkflowTransitionServiceHandlerImpl
 * 
 * @author Jens Wurm, PRODYNA AG
 */
public class ProduceWorkflowTransitionServiceHandlerImpl extends
        ProduceWorkflowTransitionServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public WorkflowTransitionMsg produceWorkflowTransition(EmptyServiceMessage request)
            throws ProduceException {

        WorkflowTransition transition = new WorkflowTransition();
        transition.setOwner(new Owner());
        transition.setName(new Name());
        transition.setDescription(new Description());
        transition.setDatatypeState(DatatypeState.INITIALIZED);

        WorkflowTransitionMsg response = new WorkflowTransitionMsg();
        response.setTransition(transition);
        return response;
    }

}
