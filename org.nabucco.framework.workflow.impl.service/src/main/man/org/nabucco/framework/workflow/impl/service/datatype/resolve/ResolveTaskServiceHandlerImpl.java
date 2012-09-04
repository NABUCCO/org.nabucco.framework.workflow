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
package org.nabucco.framework.workflow.impl.service.datatype.resolve;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.workflow.facade.datatype.task.TaskItem;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskResolveRqMsg;

/**
 * MaintainTaskServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResolveTaskServiceHandlerImpl extends ResolveTaskServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TaskMsg resolveTask(TaskResolveRqMsg rq) throws ResolveException {
        try {
            TaskItem taskItem = rq.getTaskItem();

            taskItem = super.getPersistenceManager().find(taskItem);

            TaskMsg rs = new TaskMsg();
            rs.setTaskItem(taskItem);
            return rs;
        } catch (PersistenceException e) {
            throw new ResolveException("Cannot resolve a task", e);
        }
    }

}
