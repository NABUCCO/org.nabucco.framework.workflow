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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowConditionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowEffectMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowTransitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowTriggerMsg;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowConditionRq;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowEffectRq;
import org.nabucco.framework.workflow.facade.message.definition.produce.ProduceWorkflowTriggerRq;
import org.nabucco.framework.workflow.facade.service.definition.produce.ProduceWorkflowDefinition;

/**
 * ProduceWorkflowDefinitionImpl<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public class ProduceWorkflowDefinitionImpl extends ServiceSupport implements ProduceWorkflowDefinition {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceWorkflowDefinition";

    private static Map<String, String[]> ASPECTS;

    private ProduceWorkflowDefinitionServiceHandler produceWorkflowDefinitionServiceHandler;

    private ProduceWorkflowStateServiceHandler produceWorkflowStateServiceHandler;

    private ProduceWorkflowTransitionServiceHandler produceWorkflowTransitionServiceHandler;

    private ProduceWorkflowSignalServiceHandler produceWorkflowSignalServiceHandler;

    private ProduceWorkflowTriggerServiceHandler produceWorkflowTriggerServiceHandler;

    private ProduceWorkflowConditionServiceHandler produceWorkflowConditionServiceHandler;

    private ProduceWorkflowEffectServiceHandler produceWorkflowEffectServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ProduceWorkflowDefinitionImpl instance. */
    public ProduceWorkflowDefinitionImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.produceWorkflowDefinitionServiceHandler = injector.inject(ProduceWorkflowDefinitionServiceHandler.getId());
        if ((this.produceWorkflowDefinitionServiceHandler != null)) {
            this.produceWorkflowDefinitionServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowDefinitionServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowStateServiceHandler = injector.inject(ProduceWorkflowStateServiceHandler.getId());
        if ((this.produceWorkflowStateServiceHandler != null)) {
            this.produceWorkflowStateServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowStateServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowTransitionServiceHandler = injector.inject(ProduceWorkflowTransitionServiceHandler.getId());
        if ((this.produceWorkflowTransitionServiceHandler != null)) {
            this.produceWorkflowTransitionServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowTransitionServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowSignalServiceHandler = injector.inject(ProduceWorkflowSignalServiceHandler.getId());
        if ((this.produceWorkflowSignalServiceHandler != null)) {
            this.produceWorkflowSignalServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowSignalServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowTriggerServiceHandler = injector.inject(ProduceWorkflowTriggerServiceHandler.getId());
        if ((this.produceWorkflowTriggerServiceHandler != null)) {
            this.produceWorkflowTriggerServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowTriggerServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowConditionServiceHandler = injector.inject(ProduceWorkflowConditionServiceHandler.getId());
        if ((this.produceWorkflowConditionServiceHandler != null)) {
            this.produceWorkflowConditionServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowConditionServiceHandler.setLogger(super.getLogger());
        }
        this.produceWorkflowEffectServiceHandler = injector.inject(ProduceWorkflowEffectServiceHandler.getId());
        if ((this.produceWorkflowEffectServiceHandler != null)) {
            this.produceWorkflowEffectServiceHandler.setPersistenceManager(persistenceManager);
            this.produceWorkflowEffectServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("produceWorkflowDefinition", NO_ASPECTS);
            ASPECTS.put("produceWorkflowState", NO_ASPECTS);
            ASPECTS.put("produceWorkflowTransition", NO_ASPECTS);
            ASPECTS.put("produceWorkflowSignal", NO_ASPECTS);
            ASPECTS.put("produceWorkflowTrigger", NO_ASPECTS);
            ASPECTS.put("produceWorkflowCondition", NO_ASPECTS);
            ASPECTS.put("produceWorkflowEffect", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowDefinitionMsg> produceWorkflowDefinition(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException {
        if ((this.produceWorkflowDefinitionServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowDefinition().");
            throw new InjectionException("No service implementation configured for produceWorkflowDefinition().");
        }
        ServiceResponse<WorkflowDefinitionMsg> rs;
        this.produceWorkflowDefinitionServiceHandler.init();
        rs = this.produceWorkflowDefinitionServiceHandler.invoke(rq);
        this.produceWorkflowDefinitionServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowStateMsg> produceWorkflowState(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException {
        if ((this.produceWorkflowStateServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowState().");
            throw new InjectionException("No service implementation configured for produceWorkflowState().");
        }
        ServiceResponse<WorkflowStateMsg> rs;
        this.produceWorkflowStateServiceHandler.init();
        rs = this.produceWorkflowStateServiceHandler.invoke(rq);
        this.produceWorkflowStateServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowTransitionMsg> produceWorkflowTransition(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException {
        if ((this.produceWorkflowTransitionServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowTransition().");
            throw new InjectionException("No service implementation configured for produceWorkflowTransition().");
        }
        ServiceResponse<WorkflowTransitionMsg> rs;
        this.produceWorkflowTransitionServiceHandler.init();
        rs = this.produceWorkflowTransitionServiceHandler.invoke(rq);
        this.produceWorkflowTransitionServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowSignalMsg> produceWorkflowSignal(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException {
        if ((this.produceWorkflowSignalServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowSignal().");
            throw new InjectionException("No service implementation configured for produceWorkflowSignal().");
        }
        ServiceResponse<WorkflowSignalMsg> rs;
        this.produceWorkflowSignalServiceHandler.init();
        rs = this.produceWorkflowSignalServiceHandler.invoke(rq);
        this.produceWorkflowSignalServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowTriggerMsg> produceWorkflowTrigger(ServiceRequest<ProduceWorkflowTriggerRq> rq)
            throws ProduceException {
        if ((this.produceWorkflowTriggerServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowTrigger().");
            throw new InjectionException("No service implementation configured for produceWorkflowTrigger().");
        }
        ServiceResponse<WorkflowTriggerMsg> rs;
        this.produceWorkflowTriggerServiceHandler.init();
        rs = this.produceWorkflowTriggerServiceHandler.invoke(rq);
        this.produceWorkflowTriggerServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowConditionMsg> produceWorkflowCondition(ServiceRequest<ProduceWorkflowConditionRq> rq)
            throws ProduceException {
        if ((this.produceWorkflowConditionServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowCondition().");
            throw new InjectionException("No service implementation configured for produceWorkflowCondition().");
        }
        ServiceResponse<WorkflowConditionMsg> rs;
        this.produceWorkflowConditionServiceHandler.init();
        rs = this.produceWorkflowConditionServiceHandler.invoke(rq);
        this.produceWorkflowConditionServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowEffectMsg> produceWorkflowEffect(ServiceRequest<ProduceWorkflowEffectRq> rq)
            throws ProduceException {
        if ((this.produceWorkflowEffectServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceWorkflowEffect().");
            throw new InjectionException("No service implementation configured for produceWorkflowEffect().");
        }
        ServiceResponse<WorkflowEffectMsg> rs;
        this.produceWorkflowEffectServiceHandler.init();
        rs = this.produceWorkflowEffectServiceHandler.invoke(rq);
        this.produceWorkflowEffectServiceHandler.finish();
        return rs;
    }
}
