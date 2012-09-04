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
package org.nabucco.framework.workflow.impl.service.instance.produce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceEntryMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.instance.produce.ProduceWorkflowInstance;

/**
 * ProduceWorkflowInstanceImpl<p/>Workflow instance produce service<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public class ProduceWorkflowInstanceImpl extends ServiceSupport implements ProduceWorkflowInstance {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceWorkflowInstance";

    private static Map<String, String[]> ASPECTS;

    private ProduceWorkflowInstanceServiceHandler produceWorkflowInstanceServiceHandler;

    private ProduceWorkflowInstanceEntryServiceHandler produceWorkflowInstanceEntryServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ProduceWorkflowInstanceImpl instance. */
    public ProduceWorkflowInstanceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.produceWorkflowInstanceServiceHandler = injector.inject(ProduceWorkflowInstanceServiceHandler.getId());
        if ((this.produceWorkflowInstanceServiceHandler != null)) {
            this.produceWorkflowInstanceServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowInstanceServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowInstanceEntryServiceHandler = injector.inject(ProduceWorkflowInstanceEntryServiceHandler
                .getId());
        if ((this.produceWorkflowInstanceEntryServiceHandler != null)) {
            this.produceWorkflowInstanceEntryServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowInstanceEntryServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("produceWorkflowInstance", NO_ASPECTS);
            ASPECTS.put("produceWorkflowInstanceEntry", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowInstanceMsg> produceWorkflowInstance(ServiceRequest<WorkflowDefinitionMsg> rq)
            throws ProduceException {
        if ((this.produceWorkflowInstanceServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowInstance().");
            throw new InjectionException("No service implementation configured for produceWorkflowInstance().");
        }
        ServiceResponse<WorkflowInstanceMsg> rs;
        this.produceWorkflowInstanceServiceHandler.init();
        rs = this.produceWorkflowInstanceServiceHandler.invoke(rq);
        this.produceWorkflowInstanceServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowInstanceEntryMsg> produceWorkflowInstanceEntry(ServiceRequest<WorkflowStateMsg> rq)
            throws ProduceException {
        if ((this.produceWorkflowInstanceEntryServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowInstanceEntry().");
            throw new InjectionException("No service implementation configured for produceWorkflowInstanceEntry().");
        }
        ServiceResponse<WorkflowInstanceEntryMsg> rs;
        this.produceWorkflowInstanceEntryServiceHandler.init();
        rs = this.produceWorkflowInstanceEntryServiceHandler.invoke(rq);
        this.produceWorkflowInstanceEntryServiceHandler.finish();
        return rs;
    }
}
