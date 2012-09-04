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
package org.nabucco.framework.workflow.facade.datatype.definition.condition;

import java.util.Collections;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * WorkflowConditionType<p/>Desriminator for Conditions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public enum WorkflowConditionType implements Enumeration {
    /** Current User must match Workflow Instance Assignee. */
    ASSIGNEE("A"),
    /** Current User must be in the given Authorization Group. */
    GROUP("G"),
    /** Current User must have the given Authorization Role. */
    ROLE("R"),
    /** Current User must have the given Authorization Permission. */
    PERMISSION("P"),
    /** The Context Property must hold the given Value. */
    PROPERTY("Y"),
    /** Custom Condition that returns custom code. */
    INSTANTIABLE("I"),
    /** Boolean Condition joins multiple other conditions by a boolean operator. */
    BOOLEAN("B"), ;

    private String id;

    /**
     * Constructs a new WorkflowConditionType instance.
     *
     * @param id the String.
     */
    WorkflowConditionType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

    /**
     * ValueOfId.
     *
     * @param literalId the String.
     * @return the WorkflowConditionType.
     */
    public static WorkflowConditionType valueOfId(String literalId) {
        for (WorkflowConditionType enumeration : WorkflowConditionType.values()) {
            if (enumeration.getId().equalsIgnoreCase(literalId)) {
                return enumeration;
            }
        }
        return null;
    }
}
