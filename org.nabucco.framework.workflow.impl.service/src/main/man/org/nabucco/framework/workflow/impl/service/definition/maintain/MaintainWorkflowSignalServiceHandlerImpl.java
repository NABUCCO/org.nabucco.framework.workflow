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
package org.nabucco.framework.workflow.impl.service.definition.maintain;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;

/**
 * MaintainWorkflowSignalServiceHandlerImpl
 * 
 * @author Silas Schwarz PRODYNA AG
 */
public class MaintainWorkflowSignalServiceHandlerImpl extends MaintainWorkflowSignalServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected WorkflowSignalMsg maintainWorkflowSignal(WorkflowSignalMsg msg) throws MaintainException {

        try {
            super.getPersistenceManager().persist(msg.getSignal());
            return msg;

        } catch (PersistenceException e) {
            throw new MaintainException("Error persisting WorkflowSignal with id '" + msg.getSignal().getId() + "'", e);
        }

    }
}
