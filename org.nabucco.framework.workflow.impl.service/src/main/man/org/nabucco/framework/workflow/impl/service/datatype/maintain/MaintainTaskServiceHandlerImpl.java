/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.workflow.impl.service.datatype.maintain;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.workflow.facade.datatype.task.TaskItem;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMaintainRqMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;

/**
 * MaintainTaskServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MaintainTaskServiceHandlerImpl extends MaintainTaskServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TaskMsg maintainTask(TaskMaintainRqMsg msg) throws MaintainException {

        TaskItem taskItem = msg.getTaskItem();

        try {
            if (taskItem.getDatatypeState() == DatatypeState.INITIALIZED) {
                taskItem.setCreator(super.getContext().getUserId());
                taskItem.setOwner(super.getContext().getOwner());
                taskItem.setCreationTime(NabuccoSystem.getCurrentTimestamp());
            }

            taskItem = super.getPersistenceManager().persist(taskItem);

        } catch (PersistenceException pe) {
            throw new MaintainException("Error maintaining Task with id '" + taskItem.getId() + "'.", pe);
        }
        TaskMsg rs = new TaskMsg();
        rs.setTaskItem(taskItem);
        return rs;
    }

}
