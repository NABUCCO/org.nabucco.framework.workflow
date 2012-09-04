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
package org.nabucco.framework.workflow.impl.service.datatype.resolve;

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
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskResolveRqMsg;
import org.nabucco.framework.workflow.facade.service.datatype.resolve.ResolveWorkflowDatatype;

/**
 * ResolveWorkflowDatatypeImpl<p/>Workflow datatype resolution service<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class ResolveWorkflowDatatypeImpl extends ServiceSupport implements ResolveWorkflowDatatype {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ResolveWorkflowDatatype";

    private static Map<String, String[]> ASPECTS;

    private ResolveTaskServiceHandler resolveTaskServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ResolveWorkflowDatatypeImpl instance. */
    public ResolveWorkflowDatatypeImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.resolveTaskServiceHandler = injector.inject(ResolveTaskServiceHandler.getId());
        if ((this.resolveTaskServiceHandler != null)) {
            this.resolveTaskServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveTaskServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("resolveTask", new String[] { "org.nabucco.aspect.relating", "org.nabucco.aspect.resolving",
                    "org.nabucco.aspect.transitioning", "org.nabucco.aspect.constraining" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TaskMsg> resolveTask(ServiceRequest<TaskResolveRqMsg> rq) throws ResolveException {
        if ((this.resolveTaskServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveTask().");
            throw new InjectionException("No service implementation configured for resolveTask().");
        }
        ServiceResponse<TaskMsg> rs;
        this.resolveTaskServiceHandler.init();
        rs = this.resolveTaskServiceHandler.invoke(rq);
        this.resolveTaskServiceHandler.finish();
        return rs;
    }
}
