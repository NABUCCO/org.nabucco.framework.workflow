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
package org.nabucco.framework.workflow.impl.service.instance.maintain;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinitionVisitor;
import org.nabucco.framework.workflow.facade.datatype.definition.context.WorkflowContextSerializer;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;

/**
 * MaintainWorkflowInstanceServiceHandlerImpl
 * <p/>
 * Workflow instance maintain service
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MaintainWorkflowInstanceServiceHandlerImpl extends MaintainWorkflowInstanceServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public WorkflowInstanceMsg maintainWorkflowInstance(WorkflowInstanceMsg rq) throws MaintainException {

        WorkflowInstance instance = rq.getWorkflowInstance();

        try {
            instance = this.maintainWorkflowInstance(instance);
        } catch (PersistenceException pe) {
            throw new MaintainException("Error maintaining WorkflowInstance with id '" + instance.getId() + "'.", pe);
        }

        WorkflowInstanceMsg rs = new WorkflowInstanceMsg();
        rs.setWorkflowInstance(instance);
        return rs;
    }

    /**
     * Maintains a workflow instance.
     * 
     * @param instance
     *            the workflow instance to maintain
     * 
     * @return the maintained instance
     * 
     * @throws PersistenceException
     *             when the workflow instance cannot be persisted
     * @throws MaintainException
     *             when the workflow context cannot be maintained
     */
    private WorkflowInstance maintainWorkflowInstance(WorkflowInstance instance) throws PersistenceException,
            MaintainException {

        if (instance.getDatatypeState() == DatatypeState.DELETED) {

            instance = super.getPersistenceManager().persist(instance);

            for (WorkflowInstanceEntry entry : instance.getEntryList()) {
                if (entry.getContext() != null) {
                    entry.getContext().setDatatypeState(DatatypeState.DELETED);
                    this.maintainContext(entry.getContext());
                }
                entry.setDatatypeState(DatatypeState.DELETED);
                super.getPersistenceManager().persist(entry);
            }

            if (instance.getContext() != null) {
                instance.getContext().setDatatypeState(DatatypeState.DELETED);
                this.maintainContext(instance.getContext());
            }

            instance.getEntryList().size();
            instance.getSubInstances().size();

            return instance;
        }

        this.maintainChildren(instance);

        instance = super.getPersistenceManager().persist(instance);

        instance.getEntryList().size();
        instance.getSubInstances().size();

        try {
            instance.getDefinition().accept(new WorkflowDefinitionVisitor());
        } catch (VisitorException e) {
            super.getLogger().info(
                    "Error resolving Workflow Definition with id [" + instance.getDefinition().getId() + "].");
        }

        return instance;
    }

    /**
     * Maintain the child datatypes of a workflow instance.
     * 
     * @param instance
     *            the workflow instance
     * 
     * @throws PersistenceException
     *             when the workflow instance children cannot be persisted
     * @throws MaintainException
     *             when the workflow context cannot be maintained
     */
    private void maintainChildren(WorkflowInstance instance) throws MaintainException, PersistenceException {
        for (WorkflowInstanceEntry entry : instance.getEntryList()) {
            this.maintainContext(entry.getContext());
            super.getPersistenceManager().persist(entry);
        }

        this.maintainContext(instance.getContext());
    }

    /**
     * Maintain the workflow instance context.
     * 
     * @param context
     *            the context to maintain
     * 
     * @throws PersistenceException
     *             when the context cannot be persisted
     * @throws MaintainException
     *             when the context datatype cannot be serialized
     */
    private void maintainContext(WorkflowContext context) throws MaintainException, PersistenceException {
        try {
            if (context != null) {
                NabuccoDatatype transientDatatype = context.getDatatype();
                
                WorkflowContextSerializer.getInstance().serialize(context);
                super.getPersistenceManager().persist(context);
                
                context.setDatatype(transientDatatype);
            }
        } catch (SerializationException e) {
            throw new MaintainException("Error serializing WorkflowInstance context.", e);
        }
    }

}
