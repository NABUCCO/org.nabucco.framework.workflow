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
package org.nabucco.framework.workflow.facade.datatype.condition.instantiable;

import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.InstantiableCondition;
import org.nabucco.framework.workflow.facade.datatype.effect.instantiable.InstantiationContext;

/**
 * InstantiableEvaluation
 * <p/>
 * Interface for classes that are instantiated by the {@link InstantiableCondition} and evaluated.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface InstantiableEvaluation {

    /**
     * Called from the workflow engine for a configured {@link InstantiableCondition}.
     * 
     * @param context
     *            the instantiation context holding workflow information
     * 
     * @return <b>true</b> if the condition is met, <b>false</b> if not
     * 
     * @throws WorkflowException
     *             when the evaluation fails
     */
    boolean evaluate(InstantiationContext context) throws WorkflowException;
}
