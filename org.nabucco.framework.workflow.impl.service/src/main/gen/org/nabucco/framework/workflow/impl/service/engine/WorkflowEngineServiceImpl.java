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
package org.nabucco.framework.workflow.impl.service.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.InitWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.ResumeWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowTimerRq;
import org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService;

/**
 * WorkflowEngineServiceImpl<p/>Workflow Engine service that executes the workflow transitions<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-05-04
 */
public class WorkflowEngineServiceImpl extends ServiceSupport implements WorkflowEngineService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "WorkflowEngineService";

    private static Map<String, String[]> ASPECTS;

    private StartWorkflowServiceHandler startWorkflowServiceHandler;

    private ProcessTransitionServiceHandler processTransitionServiceHandler;

    private ResumeWorkflowServiceHandler resumeWorkflowServiceHandler;

    private StartTimerServiceHandler startTimerServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new WorkflowEngineServiceImpl instance. */
    public WorkflowEngineServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.startWorkflowServiceHandler = injector.inject(StartWorkflowServiceHandler.getId());
        if ((this.startWorkflowServiceHandler != null)) {
            this.startWorkflowServiceHandler.setPersistenceManager(persistenceManager);
            this.startWorkflowServiceHandler.setLogger(super.getLogger());
        }
        this.processTransitionServiceHandler = injector.inject(ProcessTransitionServiceHandler.getId());
        if ((this.processTransitionServiceHandler != null)) {
            this.processTransitionServiceHandler.setPersistenceManager(persistenceManager);
            this.processTransitionServiceHandler.setLogger(super.getLogger());
        }
        this.resumeWorkflowServiceHandler = injector.inject(ResumeWorkflowServiceHandler.getId());
        if ((this.resumeWorkflowServiceHandler != null)) {
            this.resumeWorkflowServiceHandler.setPersistenceManager(persistenceManager);
            this.resumeWorkflowServiceHandler.setLogger(super.getLogger());
        }
        this.startTimerServiceHandler = injector.inject(StartTimerServiceHandler.getId());
        if ((this.startTimerServiceHandler != null)) {
            this.startTimerServiceHandler.setPersistenceManager(persistenceManager);
            this.startTimerServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("startWorkflow", NO_ASPECTS);
            ASPECTS.put("processTransition", NO_ASPECTS);
            ASPECTS.put("resumeWorkflow", NO_ASPECTS);
            ASPECTS.put("startTimer", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowResultRs> startWorkflow(ServiceRequest<InitWorkflowRq> rq) throws WorkflowException {
        if ((this.startWorkflowServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for startWorkflow().");
            throw new InjectionException("No service implementation configured for startWorkflow().");
        }
        ServiceResponse<WorkflowResultRs> rs;
        this.startWorkflowServiceHandler.init();
        rs = this.startWorkflowServiceHandler.invoke(rq);
        this.startWorkflowServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowResultRs> processTransition(ServiceRequest<ExecuteWorkflowRq> rq)
            throws WorkflowException {
        if ((this.processTransitionServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for processTransition().");
            throw new InjectionException("No service implementation configured for processTransition().");
        }
        ServiceResponse<WorkflowResultRs> rs;
        this.processTransitionServiceHandler.init();
        rs = this.processTransitionServiceHandler.invoke(rq);
        this.processTransitionServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowResultRs> resumeWorkflow(ServiceRequest<ResumeWorkflowRq> rq)
            throws WorkflowException {
        if ((this.resumeWorkflowServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resumeWorkflow().");
            throw new InjectionException("No service implementation configured for resumeWorkflow().");
        }
        ServiceResponse<WorkflowResultRs> rs;
        this.resumeWorkflowServiceHandler.init();
        rs = this.resumeWorkflowServiceHandler.invoke(rq);
        this.resumeWorkflowServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> startTimer(ServiceRequest<WorkflowTimerRq> rq) throws WorkflowException {
        if ((this.startTimerServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for startTimer().");
            throw new InjectionException("No service implementation configured for startTimer().");
        }
        ServiceResponse<EmptyServiceMessage> rs;
        this.startTimerServiceHandler.init();
        rs = this.startTimerServiceHandler.invoke(rq);
        this.startTimerServiceHandler.finish();
        return rs;
    }
}
