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
package org.nabucco.framework.workflow.impl.service.engine;

import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.impl.service.timer.TimerLookupException;
import org.nabucco.framework.base.impl.service.timer.TimerPriority;
import org.nabucco.framework.base.impl.service.timer.TimerService;
import org.nabucco.framework.workflow.facade.datatype.engine.WorkflowTimerInterval;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowTimerRq;
import org.nabucco.framework.workflow.impl.service.engine.timer.WorkflowTimer;

/**
 * StartWorkflowTimerServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class StartTimerServiceHandlerImpl extends StartTimerServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected EmptyServiceMessage startTimer(WorkflowTimerRq msg) throws WorkflowException {

        String name = String.valueOf(msg.getName());
        WorkflowTimerInterval interval = msg.getInterval();
        TimerPriority priority = mapToTimerPriority(interval);

        WorkflowTimer job = new WorkflowTimer(name, priority, super.getContext());

        try {
            TimerService.getInstance().schedule(job);

            super.getLogger().debug("Successfully started workflow timer with name '", name, "' and priority '",
                    priority.name(), "'.");

        } catch (TimerLookupException te) {
            super.getLogger().warning(te, "Cannot start workflow timer with name '", name, "'.");
        }

        return new EmptyServiceMessage();
    }

    /**
     * Maps an interval to the appropriate timer type.
     * 
     * @param interval
     *            the interval
     * 
     * @return the timer priority type
     */
    private TimerPriority mapToTimerPriority(WorkflowTimerInterval interval) {

        switch (interval) {

        case SHORT:
            return TimerPriority.SHORT;

        case MEDIUM:
            return TimerPriority.MEDIUM;

        case LONG:
            return TimerPriority.LONG;
        }

        throw new IllegalStateException("The interval '" + interval + "' is not supported yet.");
    }
}
