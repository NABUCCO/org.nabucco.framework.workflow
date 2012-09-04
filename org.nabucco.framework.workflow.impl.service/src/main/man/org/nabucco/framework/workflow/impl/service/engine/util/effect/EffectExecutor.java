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
package org.nabucco.framework.workflow.impl.service.engine.util.effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.text.TextContent;
import org.nabucco.framework.base.facade.datatype.utils.MessageFormatter;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintFactory;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.VisibilityType;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.workflow.InitWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs;
import org.nabucco.framework.base.facade.service.workflow.WorkflowService;
import org.nabucco.framework.base.facade.service.workflow.WorkflowServiceLocator;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTransition;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.WorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.assignee.AssigneeEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.constraint.DynamicConstraintEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.instantiable.InstantiableEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.log.LogEffect;
import org.nabucco.framework.workflow.facade.datatype.definition.effect.subworkflow.SubWorkflowEffect;
import org.nabucco.framework.workflow.facade.datatype.effect.instantiable.Instantiable;
import org.nabucco.framework.workflow.facade.datatype.effect.instantiable.InstantiationContext;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.exception.EffectExecutionException;

/**
 * EffectExecutor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class EffectExecutor {

    private static final String PARAM_WORKFLOW = "workflow";

    private static final String PARAM_INSTANCE = "instance";

    private static final String PARAM_INSTANCEID = "instanceId";

    private static final String PARAM_TRANSITION = "transition";

    private static final String PARAM_SOURCE = "source";

    private static final String PARAM_TARGET = "target";

    private static final String PARAM_TRIGGER = "trigger";

    private static final String PARAM_CREATOR = "creator";

    private static final String PARAM_CREATIONDATE = "creationTime";

    private static final String PARAM_ASSIGNEDUSER = "assignee";

    private static final String PARAM_ASSIGNEDGROUP = "assignedGroup";

    private static final String PARAM_ASSIGNEDROLE = "assignedRole";

    private static final String PARAM_MODIFIER = "modifier";

    private static final String PARAM_MODIFICATIONDATE = "modificationTime";

    private static final String PARAM_DUEDATE = "dueDate";

    private WorkflowInstance instance;

    private WorkflowTransition transition;

    private Context context;

    private ServiceMessageContext serviceContext;

    private Map<String, Serializable> logParameters;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EffectExecutor.class);

    /**
     * Creates a new {@link EffectExecutor} instance.
     * 
     * @param instance
     *            the current instance
     * @param transition
     *            the current transition
     * @param context
     *            the service context
     */
    public EffectExecutor(WorkflowInstance instance, WorkflowTransition transition, Context context,
            ServiceMessageContext serviceContext) {
        this.instance = instance;
        this.transition = transition;
        this.context = context;
        this.serviceContext = serviceContext;
    }

    /**
     * Executes the given effect.
     * 
     * @param effect
     *            the effect to execute
     */
    public void execute(WorkflowEffect effect) throws WorkflowException {
        if (effect == null) {
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Start executing effect ", String.valueOf(effect.getEffectType()), ".");
        }

        switch (effect.getEffectType()) {
        case ASSIGNEE:
            this.execute((AssigneeEffect) effect);
            break;

        case CONSTRAINT:
            this.execute((DynamicConstraintEffect) effect);
            break;

        case INSTANTIABLE:
            this.execute((InstantiableEffect) effect);
            break;

        case MODIFICATION:
            break;

        case LOG:
            this.execute((LogEffect) effect);
            break;

        case SUB_WORKFLOW:
            this.execute((SubWorkflowEffect) effect);
            break;

        default:
            logger.warning("Effect of type '", effect.getEffectType(), "' is not supported yet!");
            break;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished executing effect ", String.valueOf(effect.getEffectType()), ".");
        }
    }

    /**
     * Changes the assignee of the current instance.
     * 
     * @param effect
     *            the assignee effect
     * 
     * @throws EffectExecutionException
     *             when the assignee cannot be set successfully
     */
    private void execute(AssigneeEffect effect) throws EffectExecutionException {
        try {

            if (effect.getNewGroup() != null) {
                instance.setAssignedGroup(effect.getNewGroup());
            }
            if (effect.getNewUser() != null) {
                instance.setAssignedUser(effect.getNewUser());
            }
            if (effect.getNewRole() != null) {
                instance.setAssignedRole(effect.getNewRole());
            }
        } catch (Exception e) {
            logger.error(e, "Error setting workflow instance assignee.");
            throw new EffectExecutionException("Error setting workflow instance assignee.", e);
        }
    }

    /**
     * Changes the dynamic constraints of the given datatype.
     * 
     * @param effect
     *            the constraint effect
     * 
     * @throws EffectExecutionException
     *             when the dynamic constraints cannot be set successfully
     */
    private void execute(DynamicConstraintEffect effect) throws EffectExecutionException {
        if (context == null || context.getDatatype() == null) {
            logger.warning("Cannot set dynamic constraints on datatype [null].");
            return;
        }

        NabuccoDatatype datatype = context.getDatatype();
        Name propertyName = effect.getPropertyName();

        ConstraintFactory factory = ConstraintFactory.getInstance();

        List<Constraint> constraintList = new ArrayList<Constraint>();

        Constraint constraint = null;

        if (effect.getEditable() != null && effect.getEditable().getValue() != null) {
            constraint = factory.createEditableConstraint(effect.getEditable().getValue());

            constraintList.add(constraint);
        }

        if (effect.getVisible() != null && effect.getVisible().getValue() != null) {
            if (effect.getVisible().getValue()) {
                constraint = factory.createVisibilityConstraint(VisibilityType.VISIBLE);
            } else {
                constraint = factory.createVisibilityConstraint(VisibilityType.INVISIBLE);
            }
            constraintList.add(constraint);
        }

        if (effect.getMinLength() != null && effect.getMaxLength() != null) {
            int min = effect.getMinLength().getValue();
            int max = effect.getMaxLength().getValue();

            constraint = factory.createLengthConstraint(min, max);
            constraintList.add(constraint);
        }

        if (effect.getMinMultiplicity() != null && effect.getMaxMultiplicity() != null) {
            int min = effect.getMinMultiplicity().getValue();
            int max = effect.getMaxMultiplicity().getValue();

            constraint = factory.createMultiplicityConstraint(min, max);
            constraintList.add(constraint);
        }

        if (constraintList.isEmpty()) {
            logger.debug("No dynamic constraints defined for dynamic constraint effect with id '",
                    String.valueOf(effect.getId()), "'.");
            return;
        }

        try {

            for (Constraint newconst : constraintList) {

                if (propertyName == null || propertyName.getValue() == null) {
                    datatype.addConstraint(newconst, true);
                } else {
                    datatype.addConstraint(datatype.getProperty(propertyName.getValue()), newconst);
                }

            }

        } catch (VisitorException ve) {
            throw new EffectExecutionException("Error recursively setting dynamic constraints on datatype '"
                    + datatype.getClass().getCanonicalName() + "' with id '" + datatype.getId() + "'.", ve);
        } catch (Exception ve) {
            throw new EffectExecutionException("Error setting dynamic constraints on datatype '"
                    + datatype.getClass().getCanonicalName() + "' with id '" + datatype.getId() + "'.", ve);
        }
    }

    /**
     * Instanciates the class
     * 
     * @param effect
     *            the instantiable effect
     * 
     * @throws EffectExecutionException
     *             when the instance class raises an exception
     */
    private void execute(InstantiableEffect effect) throws EffectExecutionException {
        if (effect.getClassName() == null || effect.getClassName().getValue() == null) {
            logger.warning("Cannot instantiate class [null] of InstantiableEffect with id '", effect.getId(), "'.");
            return;
        }

        String className = effect.getClassName().getValue();

        try {
            Class<?> instantiable = Class.forName(className);

            if (!Instantiable.class.isAssignableFrom(instantiable)) {
                logger.error("Cannot instantiate class [", className, "] of InstantiableEffect with id '",
                        effect.getId(), "'. ",
                        "Class is not of type org.nabucco.framework.workflow.facade.datatype.definition.effect.instantiable.Instantiable.");
                return;
            }

            Instantiable instance = (Instantiable) instantiable.newInstance();

            InstantiationContext context = new InstantiationContext(this.instance, this.context, serviceContext);
            instance.execute(context);

        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe, "Cannot find class [", className, "] of InstantiableEffect with id '", effect.getId(),
                    "'.");
            throw new EffectExecutionException("Cannot find class [" + className + "].", cnfe);
        } catch (InstantiationException ie) {
            logger.error(ie, "Cannot instantiate class [", className, "] of InstantiableEffect with id '",
                    effect.getId(), "'.");
            throw new EffectExecutionException("Cannot instantiate class [" + className + "].", ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae, "Cannot access class [", className, "] of InstantiableEffect with id '", effect.getId(),
                    "'.");
            throw new EffectExecutionException("Cannot access class [" + className + "].", iae);
        } catch (Exception e) {
            logger.error(e, "Error during execution of [", className, "].");
            throw new EffectExecutionException("Error during execution of instantiable [" + className + "].", e);
        }
    }

    /**
     * Prints the log effect message to the logger.
     * 
     * @param effect
     *            the log effect
     */
    private void execute(LogEffect effect) {
        if (effect.getMessage() == null || effect.getMessage().getValue() == null) {
            return;
        }

        Map<String, Serializable> parameters = this.getLogParameters();

        String plainMessage = effect.getMessage().getValue();
        logger.info(MessageFormatter.format(plainMessage, parameters));
    }

    /**
     * Creates a new workflow instance for the given sub workflow effect parameters.
     * 
     * @param effect
     *            the sub workflow effect
     * 
     * @throws EffectExecutionException
     *             when the instance class raises an exception
     */
    private void execute(SubWorkflowEffect effect) throws EffectExecutionException {

        Name definitionName = effect.getDefinitionName();

        try {
            ServiceRequest<InitWorkflowRq> initRq = new ServiceRequest<InitWorkflowRq>(serviceContext);

            InitWorkflowRq msg = new InitWorkflowRq();
            msg.setWorkflowName(definitionName);
            msg.setSummary(effect.getSummary());
            msg.setAssignedGroup(effect.getAssignedGroup());
            msg.setAssignedUser(effect.getAssignedUser());
            msg.setAssignedRole(effect.getAssignedRole());
            msg.setComment(new Comment("Sub Workflow of " + instance.getName()));

            if (effect.getDescription() != null && effect.getDescription().getValue() != null) {
                msg.setDescription(new TextContent(effect.getDescription().getValue()));
            }

            initRq.setRequestMessage(msg);

            WorkflowServiceLocator locator = new WorkflowServiceLocator();
            WorkflowService workflowService = locator.getService(null);
            ServiceResponse<WorkflowResultRs> rs = workflowService.initWorkflow(initRq);

            if (rs == null || rs.getResponseMessage() == null) {
                logger.error("Cannot create sub workflow instance for '", definitionName,
                        "'. No valid new workflow instance returned.");
                throw new EffectExecutionException("No valid sub workflow instance created for '"
                        + definitionName + "'.");
            }

            Instance subInstance = rs.getResponseMessage().getInstance();
            if (subInstance instanceof WorkflowInstance) {
                instance.getSubInstances().add((WorkflowInstance) subInstance);
            }

        } catch (WorkflowException we) {
            logger.error(we, "Cannot create sub workflow instance for '", definitionName,
                    "'. Error starting new workflow instance.");
            throw new EffectExecutionException("Cannot create sub workflow instance for '"
                    + definitionName + "'. Error starting new workflow instance.", we);
        } catch (Exception e) {
            logger.error(e, "Cannot create sub workflow instance for '", definitionName, "'.");
            throw new EffectExecutionException("Cannot create sub workflow instance for '" + definitionName + "'.", e);
        }

    }

    /**
     * Create the log parameters.
     * 
     * @return the logger parameters available in the log statement
     */
    private Map<String, Serializable> getLogParameters() {

        synchronized (this) {
            if (logParameters == null) {
                logParameters = new HashMap<String, Serializable>();

                logParameters.put(PARAM_INSTANCE, instance.getName());
                logParameters.put(PARAM_INSTANCEID, instance.getId());

                logParameters.put(PARAM_CREATOR, instance.getCreator());
                logParameters.put(PARAM_CREATIONDATE, instance.getCreationTime());
                logParameters.put(PARAM_MODIFIER, instance.getCurrentEntry().getModifier());
                logParameters.put(PARAM_MODIFICATIONDATE, instance.getCurrentEntry().getModificationTime());

                logParameters.put(PARAM_ASSIGNEDUSER, instance.getAssignedUser());
                logParameters.put(PARAM_ASSIGNEDGROUP, instance.getAssignedGroup());
                logParameters.put(PARAM_ASSIGNEDROLE, instance.getAssignedRole());

                logParameters.put(PARAM_DUEDATE, instance.getDueDate());

                logParameters.put(PARAM_WORKFLOW, instance.getDefinition().getName());

                logParameters.put(PARAM_TRANSITION, transition.getName());
                logParameters.put(PARAM_TRIGGER, transition.getTrigger().getName());
                logParameters.put(PARAM_SOURCE, transition.getSource().getName());
                logParameters.put(PARAM_TARGET, transition.getTarget().getName());
            }
        }

        return logParameters;
    }
}
