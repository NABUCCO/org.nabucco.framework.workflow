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
package org.nabucco.framework.workflow.impl.service.datatype.maintain;

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
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMaintainRqMsg;
import org.nabucco.framework.workflow.facade.message.datatype.task.TaskMsg;
import org.nabucco.framework.workflow.facade.service.datatype.maintain.MaintainWorkflowDatatype;

/**
 * MaintainWorkflowDatatypeImpl<p/>Workflow datatype maintain service<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-16
 */
public class MaintainWorkflowDatatypeImpl extends ServiceSupport implements MaintainWorkflowDatatype {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainWorkflowDatatype";

    private static Map<String, String[]> ASPECTS;

    private MaintainTaskServiceHandler maintainTaskServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainWorkflowDatatypeImpl instance. */
    public MaintainWorkflowDatatypeImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainTaskServiceHandler = injector.inject(MaintainTaskServiceHandler.getId());
        if ((this.maintainTaskServiceHandler != null)) {
            this.maintainTaskServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainTaskServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainTask", new String[] { "org.nabucco.aspect.transitioning",
                    "org.nabucco.aspect.relating", "org.nabucco.aspect.resolving", "org.nabucco.aspect.indexing",
                    "org.nabucco.aspect.constraining" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TaskMsg> maintainTask(ServiceRequest<TaskMaintainRqMsg> rq) throws MaintainException {
        if ((this.maintainTaskServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainTask().");
            throw new InjectionException("No service implementation configured for maintainTask().");
        }
        ServiceResponse<TaskMsg> rs;
        this.maintainTaskServiceHandler.init();
        rs = this.maintainTaskServiceHandler.invoke(rq);
        this.maintainTaskServiceHandler.finish();
        return rs;
    }
}
