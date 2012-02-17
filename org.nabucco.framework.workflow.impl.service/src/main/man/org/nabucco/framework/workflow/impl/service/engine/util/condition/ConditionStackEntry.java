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
package org.nabucco.framework.workflow.impl.service.engine.util.condition;

import org.nabucco.framework.workflow.facade.datatype.definition.condition.BooleanOperator;

/**
 * ConditionStackEntry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class ConditionStackEntry {

    private Boolean result;

    private BooleanOperator operator;

    /**
     * Creates a new {@link ConditionStackEntry} instance.
     * 
     * @param operator
     *            the operator
     */
    ConditionStackEntry(BooleanOperator operator) {
        if (operator == null) {
            throw new IllegalArgumentException("Cannot create condition stack entry for operator [null].");
        }
        this.operator = operator;
    }

    /**
     * Getter for the operator.
     * 
     * @return Returns the operator.
     */
    public BooleanOperator getOperator() {
        return this.operator;
    }

    /**
     * Getter for the result.
     * 
     * @return Returns the result.
     */
    public Boolean getResult() {
        return this.result;
    }

    /**
     * Modifies the temporary result of the of the condition evaluator.
     * 
     * @param current
     *            the current result
     */
    public void modifyResult(boolean current) {

        if (this.operator == null) {
            this.result = current;
            return;
        }

        switch (operator) {

        case AND: {
            if (this.result == null) {
                this.result = current;
            } else {
                this.result = this.result && current;
            }
            break;
        }

        case OR: {
            if (this.result == null) {
                this.result = current;
            } else {
                this.result = this.result || current;
            }
            break;
        }

        case XOR: {
            if (this.result == null) {
                this.result = current;
            } else {
                this.result = this.result ^ current;
            }
            break;
        }

        case NOT: {
            this.result = !current;
            break;
        }

        default: {
            throw new IllegalStateException("The boolean operator [" + operator + "] is not supported.");
        }

        }
    }

    @Override
    public String toString() {
        return this.operator + " : " + this.result;
    }

}
