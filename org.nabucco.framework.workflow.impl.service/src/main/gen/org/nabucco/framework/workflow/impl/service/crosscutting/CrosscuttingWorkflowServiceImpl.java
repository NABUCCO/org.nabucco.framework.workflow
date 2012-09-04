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
package org.nabucco.framework.workflow.impl.service.crosscutting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.workflow.InitWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.ProcessWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.service.crosscutting.CrosscuttingWorkflowService;

/**
 * CrosscuttingWorkflowServiceImpl<p/>Workflow instance resolution service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-28
 */
public class CrosscuttingWorkflowServiceImpl extends ServiceSupport implements CrosscuttingWorkflowService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "CrosscuttingWorkflowService";

    private static Map<String, String[]> ASPECTS;

    private EntityManager entityManager;

    private InitWorkflowServiceHandler initWorkflowServiceHandler;

    private ProcessWorkflowServiceHandler processWorkflowServiceHandler;

    /** Constructs a new CrosscuttingWorkflowServiceImpl instance. */
    public CrosscuttingWorkflowServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.initWorkflowServiceHandler = injector.inject(InitWorkflowServiceHandler.getId());
        if ((this.initWorkflowServiceHandler != null)) {
            this.initWorkflowServiceHandler.setPersistenceManager(persistenceManager);
            this.initWorkflowServiceHandler.setLogger(super.getLogger());
        }
        this.processWorkflowServiceHandler = injector.inject(ProcessWorkflowServiceHandler.getId());
        if ((this.processWorkflowServiceHandler != null)) {
            this.processWorkflowServiceHandler.setPersistenceManager(persistenceManager);
            this.processWorkflowServiceHandler.setLogger(super.getLogger());
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
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowResultRs> initWorkflow(ServiceRequest<InitWorkflowRq> rq) throws WorkflowException {
        if ((this.initWorkflowServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for initWorkflow().");
            throw new InjectionException("No service implementation configured for initWorkflow().");
        }
        ServiceResponse<WorkflowResultRs> rs;
        this.initWorkflowServiceHandler.init();
        rs = this.initWorkflowServiceHandler.invoke(rq);
        this.initWorkflowServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowResultRs> processWorkflow(ServiceRequest<ProcessWorkflowRq> rq)
            throws WorkflowException {
        if ((this.processWorkflowServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for processWorkflow().");
            throw new InjectionException("No service implementation configured for processWorkflow().");
        }
        ServiceResponse<WorkflowResultRs> rs;
        this.processWorkflowServiceHandler.init();
        rs = this.processWorkflowServiceHandler.invoke(rq);
        this.processWorkflowServiceHandler.finish();
        return rs;
    }
}
