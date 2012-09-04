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
package org.nabucco.framework.workflow.impl.service.instance.resolve;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance;

/**
 * ResolveWorkflowInstanceImpl<p/>Workflow instance resolution service<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-28
 */
public class ResolveWorkflowInstanceImpl extends ServiceSupport implements ResolveWorkflowInstance {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ResolveWorkflowInstance";

    private static Map<String, String[]> ASPECTS;

    private ResolveWorkflowInstanceServiceHandler resolveWorkflowInstanceServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ResolveWorkflowInstanceImpl instance. */
    public ResolveWorkflowInstanceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.resolveWorkflowInstanceServiceHandler = injector.inject(ResolveWorkflowInstanceServiceHandler.getId());
        if ((this.resolveWorkflowInstanceServiceHandler != null)) {
            this.resolveWorkflowInstanceServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveWorkflowInstanceServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("resolveWorkflowInstance", new String[] { "org.nabucco.aspect.relating",
                    "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<WorkflowInstanceMsg> resolveWorkflowInstance(ServiceRequest<WorkflowInstanceMsg> rq)
            throws ResolveException {
        if ((this.resolveWorkflowInstanceServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveWorkflowInstance().");
            throw new InjectionException("No service implementation configured for resolveWorkflowInstance().");
        }
        ServiceResponse<WorkflowInstanceMsg> rs;
        this.resolveWorkflowInstanceServiceHandler.init();
        rs = this.resolveWorkflowInstanceServiceHandler.invoke(rq);
        this.resolveWorkflowInstanceServiceHandler.finish();
        return rs;
    }
}
