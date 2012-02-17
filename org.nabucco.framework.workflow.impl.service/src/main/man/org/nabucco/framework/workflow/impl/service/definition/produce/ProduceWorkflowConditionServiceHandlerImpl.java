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
package org.nabucco.framework.workflow.impl.service.definition.produce;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.BooleanCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.BooleanOperator;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.Expression;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.GroupCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.PermissionCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.PropertyCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.RoleCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowConditionMsg;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowConditionRq;

/**
 * ProduceWorkflowConditionServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProduceWorkflowConditionServiceHandlerImpl extends
        ProduceWorkflowConditionServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected WorkflowConditionMsg produceWorkflowCondition(ProduceWorkflowConditionRq request)
            throws ProduceException {

        WorkflowConditionMsg response = new WorkflowConditionMsg();

        switch (request.getConditionType()) {

        case BOOLEAN:
            response.setCondition(this.createBooleanCondition());
            break;
            
        case GROUP:
            response.setCondition(this.createGroupCondition());
            break;
            
        case ROLE:
            response.setCondition(this.createRoleCondition());
            break;

        case PERMISSION:
            response.setCondition(this.createPermissionCondition());
            break;

        case PROPERTY:
            response.setCondition(this.createPropertyCondition());
            break;

        default:
            throw new ProduceException("Cannot produce WorkflowCondition for ConditionType ["
                    + request.getConditionType() + "].");
        }

        response.getCondition().setName(new Name());
        response.getCondition().setDescription(new Description());
        response.getCondition().setOwner(new Owner());
        response.getCondition().setDatatypeState(DatatypeState.INITIALIZED);

        return response;
    }

    /**
     * Create a new {@link BooleanCondition} instance.
     * 
     * @return the new instance
     */
    private WorkflowCondition createBooleanCondition() {
        BooleanCondition condition = new BooleanCondition();
        condition.setOperator(BooleanOperator.NOT);
        return condition;
    }
    
    /**
     * Create a new {@link GroupCondition} instance.
     * 
     * @return the new instance
     */
    private WorkflowCondition createGroupCondition() {
        GroupCondition condition = new GroupCondition();
        return condition;
    }
    
    /**
     * Create a new {@link RoleCondition} instance.
     * 
     * @return the new instance
     */
    private WorkflowCondition createRoleCondition() {
        RoleCondition condition = new RoleCondition();
        return condition;
    }

    /**
     * Create a new {@link PermissionCondition} instance.
     * 
     * @return the new instance
     */
    private WorkflowCondition createPermissionCondition() {
        PermissionCondition condition = new PermissionCondition();
        return condition;
    }

    /**
     * Create a new {@link PropertyCondition} instance.
     * 
     * @return the new instance
     */
    private WorkflowCondition createPropertyCondition() {
        PropertyCondition condition = new PropertyCondition();
        condition.setExpression(new Expression());
        return condition;
    }
}
