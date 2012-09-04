/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.workflow.facade.datatype.task;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;

/**
 * TaskItemComponentRelation<p/>The task that can be<p/>
 *
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-02-08
 */
public class TaskItemComponentRelation extends ComponentRelation<TaskItem> {

    private static final long serialVersionUID = 1L;

    /** Constructs a new TaskItemComponentRelation instance. */
    protected TaskItemComponentRelation() {
        super();
    }

    /**
     * Constructs a new TaskItemComponentRelation instance.
     *
     * @param relationType the ComponentRelationType.
     */
    public TaskItemComponentRelation(ComponentRelationType relationType) {
        super(relationType);
    }

    /**
     * Getter for the Tarthe .
     *
     * @return the TaskItem.
     */
    public TaskItem getTarget() {
        return super.getTarget();
    }

    /**
     * Setter for the Target.
     *
     * @param target the TaskItem.
     */
    public void setTarget(TaskItem target) {
        super.setTarget(target);
    }

    @Override
    public TaskItemComponentRelation cloneObject() {
        TaskItemComponentRelation clone = new TaskItemComponentRelation();
        super.cloneObject(clone);
        return clone;
    }
}
