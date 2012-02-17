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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.security.Group;
import org.nabucco.framework.base.facade.datatype.security.Permission;
import org.nabucco.framework.base.facade.datatype.security.Role;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.workflow.facade.datatype.condition.instantiable.InstantiableEvaluation;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.AssigneeCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.BooleanCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.GroupCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.InstantiableCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.PermissionCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.RoleCondition;
import org.nabucco.framework.workflow.facade.datatype.definition.condition.WorkflowCondition;
import org.nabucco.framework.workflow.facade.datatype.effect.instantiable.InstantiationContext;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * ConditionEvaluationVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConditionEvaluationVisitor extends DatatypeVisitor {

    private Boolean result;

    private WorkflowInstance instance;

    private User user;

    private List<Group> groups;

    private List<Role> roles;

    private List<Permission> permissions;

    private Deque<ConditionStackEntry> stack;

    private WorkflowContext context;

    private ServiceMessageContext serviceContext;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ConditionEvaluationVisitor.class);

    /**
     * Creates a new instance of {@link ConditionEvaluationVisitor} with a given workflow signal and
     * a list of the current permissions
     * 
     * @param instance
     *            the workflow instance
     * @param context
     *            the workflow context
     * @param user
     *            the current user
     * @param groupList
     *            list of user groups
     * @param roleList
     *            list of user roles
     * @param permissionList
     *            list of user permissions
     * @param serviceContext
     *            the service context
     */
    public ConditionEvaluationVisitor(WorkflowInstance instance, WorkflowContext context, User user,
            List<Group> groupList, List<Role> roleList, List<Permission> permissionList,
            ServiceMessageContext serviceContext) {
        if (groupList == null || groupList.isEmpty()) {
            this.groups = Collections.emptyList();
        } else {
            this.groups = new ArrayList<Group>(groupList);
        }

        if (roleList == null || roleList.isEmpty()) {
            this.roles = Collections.emptyList();
        } else {
            this.roles = new ArrayList<Role>(roleList);
        }

        if (permissionList == null || permissionList.isEmpty()) {
            this.permissions = Collections.emptyList();
        } else {
            this.permissions = new ArrayList<Permission>(permissionList);
        }

        this.instance = instance;
        this.user = user;
        this.stack = new ArrayDeque<ConditionStackEntry>();
        this.context = context;
        this.serviceContext = serviceContext;
    }

    /**
     * Check whether the condition was evaluated successfully or not.
     * 
     * @return <b>true</b> if the condition is valid, <b>false</b> if not.
     */
    public boolean isSuccessful() {
        if (this.result == null) {
            return false;
        }

        return this.result;
    }

    /**
     * Reset the condition evaluation visitor result.
     */
    public void reset() {
        this.result = null;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        if (!(datatype instanceof WorkflowCondition)) {
            return;
        }

        WorkflowCondition condition = (WorkflowCondition) datatype;

        switch (condition.getConditionType()) {

        case BOOLEAN:
            this.visit((BooleanCondition) condition);
            break;

        case ASSIGNEE:
            this.visit((AssigneeCondition) condition);
            break;

        case GROUP:
            this.visit((GroupCondition) condition);
            break;

        case ROLE:
            this.visit((RoleCondition) condition);
            break;

        case PERMISSION:
            this.visit((PermissionCondition) condition);
            break;

        case INSTANTIABLE:
            this.visit((InstantiableCondition) condition);
            break;

        default:
            logger.warning("Cannot evaluate condition of type '"
                    + condition.getConditionType() + "'. Type is not supported yet.");
        }

    }

    /**
     * Visit the boolean condition and link its children by the boolean operator.
     * 
     * @param booleanCondition
     *            the boolean condition
     * 
     * @throws VisitorException
     *             when the condition cannot be evaluated
     */
    private void visit(BooleanCondition booleanCondition) throws VisitorException {
        ConditionStackEntry entry = new ConditionStackEntry(booleanCondition.getOperator());

        this.stack.push(entry);
        super.visit(booleanCondition);
        entry = this.stack.poll();

        this.modifyResult(entry.getResult());
    }

    /**
     * Visit the assignee condition and check it against it against the current user.
     * 
     * @param assigneeCondition
     *            the assignee condition to visit
     * 
     * @throws VisitorException
     *             when the condition cannot be evaluated
     */
    private void visit(AssigneeCondition assigneeCondition) throws VisitorException {
        if (this.instance.getAssignedUser() == null || this.instance.getAssignedUser().getValue() == null) {
            this.modifyResult(true);
            return;
        }

        if (this.user == null || this.user.getUsername() == null || this.user.getUsername().getValue() == null) {
            this.modifyResult(false);
        }

        String assignee = this.instance.getAssignedUser().getValue();
        String username = this.user.getUsername().getValue();

        if (assignee.equalsIgnoreCase(username)) {
            this.modifyResult(true);
        } else {
            this.modifyResult(false);
        }
    }

    /**
     * Visit the group condition and check whether the group is given.
     * 
     * @param groupCondition
     *            the group condition to visit
     * 
     * @throws VisitorException
     *             when the condition cannot be evaluated
     */
    private void visit(GroupCondition groupCondition) throws VisitorException {
        boolean result = false;
        for (Group current : this.groups) {
            if (current.getGroupname().equals(groupCondition.getGroupName())) {
                result = true;
            }
        }

        this.modifyResult(result);
    }

    /**
     * Visit the role condition and check whether the role is given.
     * 
     * @param roleCondition
     *            the role condition to visit
     * 
     * @throws VisitorException
     *             when the condition cannot be evaluated
     */
    private void visit(RoleCondition roleCondition) throws VisitorException {
        boolean result = false;
        for (Role current : this.roles) {
            if (current.getRolename().equals(roleCondition.getRoleName())) {
                result = true;
            }
        }

        this.modifyResult(result);
    }

    /**
     * Visit the permission condition and check whether the permission is given.
     * 
     * @param permissionCondition
     *            the permission condition to visit
     */
    private void visit(PermissionCondition permissionCondition) throws VisitorException {
        boolean result = false;
        for (Permission current : this.permissions) {
            if (current.getPermissionname().equals(permissionCondition.getPermissionName())) {
                result = true;
            }
        }

        this.modifyResult(result);
    }

    private void visit(InstantiableCondition condition) throws VisitorException {
        boolean result = false;

        if (condition.getClassName() == null || condition.getClassName().getValue() == null) {
            logger.warning("Cannot instantiate class [null] of InstantiableCondition with id '", condition.getId(),
                    "'.");
            this.modifyResult(false);
            return;
        }

        String className = condition.getClassName().getValue();

        try {
            Class<?> instantiableEvaluation = Class.forName(className);
            if (!InstantiableEvaluation.class.isAssignableFrom(instantiableEvaluation)) {
                logger.error("Cannot instantiate class [", className, "] of InstantiableCondition with id '",
                        condition.getId(), "'. ",
                        "Class is not of type '" + InstantiableEvaluation.class.getCanonicalName() + "'.");
                this.modifyResult(false);
                return;
            }

            InstantiableEvaluation instance = (InstantiableEvaluation) instantiableEvaluation.newInstance();

            InstantiationContext context = new InstantiationContext(this.instance, this.context, this.serviceContext);
            result = instance.evaluate(context);

        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe, "Cannot find class [", className, "] of InstantiableCondition with id '",
                    condition.getId(), "'.");
            throw new VisitorException("Cannot find class [" + className + "].", cnfe);
        } catch (InstantiationException ie) {
            logger.error(ie, "Cannot instantiate class [", className, "] of InstantiableCondition with id '",
                    condition.getId(), "'.");
            throw new VisitorException("Cannot instantiate class [" + className + "].", ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae, "Cannot access class [", className, "] of InstantiableCondition with id '",
                    condition.getId(), "'.");
            throw new VisitorException("Cannot access class [" + className + "].", iae);
        } catch (Exception e) {
            logger.error(e, "Error during execution of [", className, "].");
        }

        this.modifyResult(result);
    }

    /**
     * Modifies the result of the of the condition evaluator.
     * 
     * @param current
     *            the current result
     */
    private void modifyResult(boolean current) {
        ConditionStackEntry entry = this.stack.peek();

        if (entry == null) {
            this.result = current;
            return;
        }

        entry.modifyResult(current);
    }
}
