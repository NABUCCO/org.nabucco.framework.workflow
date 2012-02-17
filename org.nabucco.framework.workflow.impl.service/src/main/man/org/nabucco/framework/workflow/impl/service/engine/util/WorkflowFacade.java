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
package org.nabucco.framework.workflow.impl.service.engine.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;
import org.nabucco.framework.workflow.facade.component.WorkflowComponentLocator;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowState;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowStateType;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstance;
import org.nabucco.framework.workflow.facade.datatype.instance.WorkflowInstanceEntry;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowStateMsg;
import org.nabucco.framework.workflow.facade.message.engine.ExecuteWorkflowRq;
import org.nabucco.framework.workflow.facade.message.engine.WorkflowResultRs;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceEntryMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceListMsg;
import org.nabucco.framework.workflow.facade.message.instance.WorkflowInstanceMsg;
import org.nabucco.framework.workflow.facade.message.instance.search.WorkflowInstanceSearchRq;
import org.nabucco.framework.workflow.facade.service.engine.WorkflowEngineService;
import org.nabucco.framework.workflow.facade.service.instance.maintain.MaintainWorkflowInstance;
import org.nabucco.framework.workflow.facade.service.instance.produce.ProduceWorkflowInstance;
import org.nabucco.framework.workflow.facade.service.instance.resolve.ResolveWorkflowInstance;
import org.nabucco.framework.workflow.facade.service.instance.search.SearchWorkflowInstance;

/**
 * WorkflowFacade
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowFacade {

    private ServiceMessageContext context;

    /** Logger */
    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WorkflowFacade.class);

    /** Components */
    private static final Map<Class<? extends Component>, SoftReference<? extends Component>> componentMap = new HashMap<Class<? extends Component>, SoftReference<? extends Component>>();

    /**
     * Creates a new {@link WorkflowFacade} instance.
     * 
     * @param context
     *            the service context
     */
    private WorkflowFacade(ServiceMessageContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Cannot create WorkflowFacade for context [null].");
        }

        this.context = context;
    }

    /**
     * Returns a new {@link WorkflowFacade} instance for a given service context.
     * 
     * @param context
     *            the service context
     * 
     * @return the new instance
     */
    public static WorkflowFacade newInstance(ServiceMessageContext context) {
        return new WorkflowFacade(context);
    }

    /**
     * Maintain a {@link WorkflowInstance} in the {@link WorkflowInstanceComponent}.
     * 
     * @param instance
     *            the workflow instance
     * 
     * @return the maintained workflow instance
     * 
     * @throws ServiceException
     *             when the maintain failed
     */
    public WorkflowInstance maintainInstance(WorkflowInstance instance) throws ServiceException {
        MaintainWorkflowInstance maintain = this.getWorkflowComponent().getMaintainWorkflowInstance();
        ServiceRequest<WorkflowInstanceMsg> rq = new ServiceRequest<WorkflowInstanceMsg>(this.context);

        WorkflowInstanceMsg msg = new WorkflowInstanceMsg();
        msg.setWorkflowInstance(instance);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowInstanceMsg> rs = maintain.maintainWorkflowInstance(rq);
        this.validateResponse(rs, "Service 'maintainWorkflowInstance' did not finish successful.");

        return rs.getResponseMessage().getWorkflowInstance();
    }

    /**
     * Resolve a {@link WorkflowInstance} from the {@link WorkflowInstanceComponent}.
     * 
     * @param instance
     *            the workflow instance
     * 
     * @return the resolved workflow instance
     * 
     * @throws ServiceException
     *             when the resolve failed
     */
    public WorkflowInstance resolveInstance(WorkflowInstance instance) throws ServiceException {
        ResolveWorkflowInstance resolve = this.getWorkflowComponent().getResolveWorkflowInstance();
        ServiceRequest<WorkflowInstanceMsg> rq = new ServiceRequest<WorkflowInstanceMsg>(this.context);

        WorkflowInstanceMsg msg = new WorkflowInstanceMsg();
        msg.setWorkflowInstance(instance);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowInstanceMsg> rs = resolve.resolveWorkflowInstance(rq);
        this.validateResponse(rs, "Service 'resolveWorkflowInstance' did not finish successful.");

        return rs.getResponseMessage().getWorkflowInstance();
    }

    /**
     * Search all {@link WorkflowInstance} of the {@link WorkflowInstanceComponent}.
     * 
     * @param stateType
     *            the state type to filter for
     * 
     * @param instance
     *            the workflow instance
     * 
     * @return the searched workflow instances
     * 
     * @throws ServiceException
     *             when the search failed
     */
    public List<WorkflowInstance> searchInstance(WorkflowStateType stateType) throws ServiceException {
        SearchWorkflowInstance searchService = this.getWorkflowComponent().getSearchWorkflowInstance();
        ServiceRequest<WorkflowInstanceSearchRq> rq = new ServiceRequest<WorkflowInstanceSearchRq>(this.context);

        WorkflowInstanceSearchRq msg = new WorkflowInstanceSearchRq();
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowInstanceListMsg> rs = searchService.searchWorkflowInstance(rq);

        return rs.getResponseMessage().getWorkflowInstanceList();
    }

    /**
     * Produce a new {@link WorkflowInstance} for the given workflow definition.
     * 
     * @param definition
     *            the workflow definition
     * 
     * @return the new created workflow instance
     * 
     * @throws ServiceException
     *             when the produce failed
     */
    public WorkflowInstance produceInstance(WorkflowDefinition definition) throws ServiceException {
        ProduceWorkflowInstance produce = this.getWorkflowComponent().getProduceWorkflowInstance();
        ServiceRequest<WorkflowDefinitionMsg> rq = new ServiceRequest<WorkflowDefinitionMsg>(this.context);

        WorkflowDefinitionMsg msg = new WorkflowDefinitionMsg();
        msg.setWorkflow(definition);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowInstanceMsg> rs = produce.produceWorkflowInstance(rq);
        this.validateResponse(rs, "Service 'produceWorkflowInstance' did not finish successful.");

        return rs.getResponseMessage().getWorkflowInstance();
    }

    /**
     * Produce a new {@link WorkflowInstanceEntry} for the given workflow state.
     * 
     * @param state
     *            the workflow state
     * 
     * @return the new created workflow instance entry
     * 
     * @throws ServiceException
     *             when the produce failed
     */
    public WorkflowInstanceEntry produceEntry(WorkflowState state) throws ServiceException {
        ProduceWorkflowInstance produce = this.getWorkflowComponent().getProduceWorkflowInstance();
        ServiceRequest<WorkflowStateMsg> rq = new ServiceRequest<WorkflowStateMsg>(this.context);

        WorkflowStateMsg msg = new WorkflowStateMsg();
        msg.setState(state);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowInstanceEntryMsg> rs = produce.produceWorkflowInstanceEntry(rq);
        this.validateResponse(rs, "Service 'produceWorkflowInstanceEntry' did not finish successful.");

        return rs.getResponseMessage().getWorkflowEntry();
    }

    /**
     * Process a workflow transition for a given timer name.
     * 
     * @param instance
     *            the instance to process
     * @param timer
     *            the triggered timer name
     * 
     * @return the instance in its new state
     * 
     * @throws ServiceException
     *             when the process failed
     */
    public WorkflowResultRs processTransition(WorkflowInstance instance, String timer) throws ServiceException {
        WorkflowEngineService engineService = this.getWorkflowComponent().getWorkflowEngineService();
        ServiceRequest<ExecuteWorkflowRq> rq = new ServiceRequest<ExecuteWorkflowRq>(this.context);

        ExecuteWorkflowRq msg = new ExecuteWorkflowRq();
        msg.setInstance(instance);
        msg.setTimer(new Name(timer));
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowResultRs> rs = engineService.processTransition(rq);
        this.validateResponse(rs, "Service 'processTransition' did not finish successful.");

        return rs.getResponseMessage();
    }

    /**
     * Process a workflow transition for a given timer name.
     * 
     * @param instance
     *            the instance to process
     * @param signal
     *            the triggered signal
     * 
     * @return the instance in its new state
     * 
     * @throws ServiceException
     *             when the process failed
     */
    public WorkflowResultRs processTransition(WorkflowInstance instance, WorkflowSignal signal) throws ServiceException {
        WorkflowEngineService engineService = this.getWorkflowComponent().getWorkflowEngineService();
        ServiceRequest<ExecuteWorkflowRq> rq = new ServiceRequest<ExecuteWorkflowRq>(this.context);

        ExecuteWorkflowRq msg = new ExecuteWorkflowRq();
        msg.setInstance(instance);
        msg.setSignal(signal);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowResultRs> rs = engineService.processTransition(rq);
        this.validateResponse(rs, "Service 'processTransition' did not finish successful.");

        return rs.getResponseMessage();
    }

    /**
     * Getter for the {@link WorkflowDefinitionComponent}.
     * 
     * @return the component instance
     */
    private WorkflowComponent getWorkflowComponent() {
        return this.getComponent(WorkflowComponent.class, WorkflowComponentLocator.getInstance());
    }

    /**
     * Validate the service response.
     * 
     * @param rs
     *            the service response to validate
     * 
     * @throws ServiceException
     *             when the response is invalid
     */
    private void validateResponse(ServiceResponse<?> rs, String errorMessage) throws ServiceException {
        if (rs == null || rs.getResponseMessage() == null) {
            throw new ServiceException(errorMessage);
        }
    }

    /**
     * Lookup a component from JNDI or cache.
     * 
     * @param <C>
     *            the component to locate
     * @param componentClass
     *            the component class to locate
     * @param locator
     *            the component locator
     * 
     * @return the lookuped component
     */
    private <C extends Component> C getComponent(Class<C> componentClass, ComponentLocator<C> locator) {

        @SuppressWarnings("unchecked")
        SoftReference<C> reference = (SoftReference<C>) componentMap.get(componentClass);

        if (reference == null || reference.get() == null) {

            try {
                C component = locator.getComponent();
                reference = new SoftReference<C>(component);
                componentMap.put(componentClass, reference);
            } catch (ConnectionException ce) {
                logger.error(ce, "Error connecting to current JBoss instance.");
                throw new IllegalStateException("Error connecting to current JBoss instance.", ce);
            }

        }

        return reference.get();
    }
}
