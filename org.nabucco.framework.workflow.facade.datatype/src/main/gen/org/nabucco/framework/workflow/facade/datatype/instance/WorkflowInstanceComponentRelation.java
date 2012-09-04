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
package org.nabucco.framework.workflow.facade.datatype.instance;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;

/**
 * WorkflowInstanceComponentRelation<p/>An instance of a workflow (represents a complete workflow iteration).<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2011-02-08
 */
public class WorkflowInstanceComponentRelation extends ComponentRelation<WorkflowInstance> {

    private static final long serialVersionUID = 1L;

    /** Constructs a new WorkflowInstanceComponentRelation instance. */
    protected WorkflowInstanceComponentRelation() {
        super();
    }

    /**
     * Constructs a new WorkflowInstanceComponentRelation instance.
     *
     * @param relationType the ComponentRelationType.
     */
    public WorkflowInstanceComponentRelation(ComponentRelationType relationType) {
        super(relationType);
    }

    /**
     * Getter for the Tarthe .
     *
     * @return the WorkflowInstance.
     */
    public WorkflowInstance getTarget() {
        return super.getTarget();
    }

    /**
     * Setter for the Target.
     *
     * @param target the WorkflowInstance.
     */
    public void setTarget(WorkflowInstance target) {
        super.setTarget(target);
    }

    @Override
    public WorkflowInstanceComponentRelation cloneObject() {
        WorkflowInstanceComponentRelation clone = new WorkflowInstanceComponentRelation();
        super.cloneObject(clone);
        return clone;
    }
}
