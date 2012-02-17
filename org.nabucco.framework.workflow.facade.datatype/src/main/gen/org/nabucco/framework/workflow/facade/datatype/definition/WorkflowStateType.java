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
package org.nabucco.framework.workflow.facade.datatype.definition;

import java.util.Collections;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * WorkflowStateType<p/>The workflow effect types.<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-03-29
 */
public enum WorkflowStateType implements Enumeration {
    /** The workflow state is a start state. */
    START("S"),
    /** The workflow state is a end state. */
    END("E"),
    /** The workflow state is a normal workflow state. */
    NORMAL("N"), ;

    private String id;

    /**
     * Constructs a new WorkflowStateType instance.
     *
     * @param id the String.
     */
    WorkflowStateType(String id) {
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
     * @return the WorkflowStateType.
     */
    public static WorkflowStateType valueOfId(String literalId) {
        for (WorkflowStateType enumeration : WorkflowStateType.values()) {
            if (enumeration.getId().equalsIgnoreCase(literalId)) {
                return enumeration;
            }
        }
        return null;
    }
}
