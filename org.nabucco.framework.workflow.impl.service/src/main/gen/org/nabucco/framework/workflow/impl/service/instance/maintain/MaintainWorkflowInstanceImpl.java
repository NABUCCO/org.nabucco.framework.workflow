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
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.instance.maintain.MaintainWorkflowInstance;

/**
 * MaintainWorkflowInstanceImpl<p/>Workflow instance maintain service<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-04-28
 */
public class MaintainWorkflowInstanceImpl extends ServiceSupport implements MaintainWorkflowInstance {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainWorkflowInstance";

    private static Map<String, String[]> ASPECTS;

    private MaintainWorkflowInstanceServiceHandler maintainWorkflowInstanceServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainWorkflowInstanceImpl instance. */
    public MaintainWorkflowInstanceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainWorkflowInstanceServiceHandler = injector.inject(MaintainWorkflowInstanceServiceHandler.getId());
        if ((this.maintainWorkflowInstanceServiceHandler != null)) {
            this.maintainWorkflowInstanceServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainWorkflowInstanceServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainWorkflowInstance", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowInstanceMsg> maintainWorkflowInstance(ServiceRequest<WorkflowInstanceMsg> rq)
            throws MaintainException {
        if ((this.maintainWorkflowInstanceServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainWorkflowInstance().");
            throw new InjectionException("No service implementation configured for maintainWorkflowInstance().");
        }
        ServiceResponse<WorkflowInstanceMsg> rs;
        this.maintainWorkflowInstanceServiceHandler.init();
        rs = this.maintainWorkflowInstanceServiceHandler.invoke(rq);
        this.maintainWorkflowInstanceServiceHandler.finish();
        return rs;
    }
}
