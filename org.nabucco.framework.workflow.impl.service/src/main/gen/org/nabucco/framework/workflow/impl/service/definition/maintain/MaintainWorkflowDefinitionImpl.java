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
package org.nabucco.framework.workflow.impl.service.definition.maintain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;
import org.nabucco.framework.workflow.facade.service.definition.maintain.MaintainWorkflowDefinition;

/**
 * MaintainWorkflowDefinitionImpl<p/>Workflow definition produce service.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class MaintainWorkflowDefinitionImpl extends ServiceSupport implements MaintainWorkflowDefinition {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainWorkflowDefinition";

    private static Map<String, String[]> ASPECTS;

    private MaintainWorkflowDefinitionServiceHandler maintainWorkflowDefinitionServiceHandler;

    private MaintainWorkflowSignalServiceHandler maintainWorkflowSignalServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainWorkflowDefinitionImpl instance. */
    public MaintainWorkflowDefinitionImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainWorkflowDefinitionServiceHandler = injector.inject(MaintainWorkflowDefinitionServiceHandler
                .getId());
        if ((this.maintainWorkflowDefinitionServiceHandler != null)) {
            this.maintainWorkflowDefinitionServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainWorkflowDefinitionServiceHandler.setLogger(super.getLogger());
        }
        this.maintainWorkflowSignalServiceHandler = injector.inject(MaintainWorkflowSignalServiceHandler.getId());
        if ((this.maintainWorkflowSignalServiceHandler != null)) {
            this.maintainWorkflowSignalServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainWorkflowSignalServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainWorkflowDefinition", new String[] { "org.nabucco.aspect.caching" });
            ASPECTS.put("maintainWorkflowSignal", new String[] { "org.nabucco.aspect.caching" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowDefinitionMsg> maintainWorkflowDefinition(ServiceRequest<WorkflowDefinitionMsg> rq)
            throws MaintainException {
        if ((this.maintainWorkflowDefinitionServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainWorkflowDefinition().");
            throw new InjectionException("No service implementation configured for maintainWorkflowDefinition().");
        }
        ServiceResponse<WorkflowDefinitionMsg> rs;
        this.maintainWorkflowDefinitionServiceHandler.init();
        rs = this.maintainWorkflowDefinitionServiceHandler.invoke(rq);
        this.maintainWorkflowDefinitionServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<WorkflowSignalMsg> maintainWorkflowSignal(ServiceRequest<WorkflowSignalMsg> rq)
            throws MaintainException {
        if ((this.maintainWorkflowSignalServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainWorkflowSignal().");
            throw new InjectionException("No service implementation configured for maintainWorkflowSignal().");
        }
        ServiceResponse<WorkflowSignalMsg> rs;
        this.maintainWorkflowSignalServiceHandler.init();
        rs = this.maintainWorkflowSignalServiceHandler.invoke(rq);
        this.maintainWorkflowSignalServiceHandler.finish();
        return rs;
    }
}
