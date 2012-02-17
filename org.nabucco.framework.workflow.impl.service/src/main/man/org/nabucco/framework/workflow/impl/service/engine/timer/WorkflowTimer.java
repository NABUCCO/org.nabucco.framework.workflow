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
package org.nabucco.framework.workflow.impl.service.engine.timer;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.timer.Timer;
import org.nabucco.framework.base.impl.service.timer.TimerExecutionException;
import org.nabucco.framework.base.impl.service.timer.TimerPriority;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.impl.service.engine.util.WorkflowFacade;

/**
 * SignalQueue
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowTimer extends Timer {

    private static final long serialVersionUID = 1L;

    private WorkflowFacade facade;

    /**
     * Creates a new {@link WorkflowTimer} instance.
     */
    public WorkflowTimer(String name, TimerPriority priority, ServiceMessageContext context) {
        super(name, priority);

        this.facade = WorkflowFacade.newInstance(context);
    }

    @Override
    public void execute() throws TimerExecutionException {

        List<WorkflowInstance> instances = new ArrayList<WorkflowInstance>();

        try {
            instances.addAll(this.facade.searchInstance(WorkflowStateType.NORMAL));
        } catch (ServiceException se) {
            this.getLogger().error(se, "Error finding workflow instances for timer execution.");
        }

        for (WorkflowInstance instance : instances) {
            this.processTransition(instance);
        }

    }

    /**
     * Execute the timer service
     * 
     * @param instance
     *            the instance to trigger
     * 
     * @throws TimerExecutionException
     */
    private void processTransition(WorkflowInstance instance) throws TimerExecutionException {

        try {
            instance = this.facade.resolveInstance(instance);
            this.facade.processTransition(instance, super.getName());
        } catch (ServiceException se) {
            this.getLogger().error("Error traversing workflow instance by timer.");
        }
    }
}
