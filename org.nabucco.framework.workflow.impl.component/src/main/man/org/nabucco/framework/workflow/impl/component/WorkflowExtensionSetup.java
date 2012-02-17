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
package org.nabucco.framework.workflow.impl.component;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowDefinitionExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowTriggerSignalVisitor;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignal;
import org.nabucco.framework.workflow.facade.datatype.definition.signal.WorkflowSignalContainer;
import org.nabucco.framework.workflow.facade.datatype.extension.WorkflowDefinitionConfigurationMapper;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalListMsg;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowSignalMsg;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowDefinitionSearchRq;
import org.nabucco.framework.workflow.facade.message.definition.search.WorkflowSignalSearchRq;
import org.nabucco.framework.workflow.facade.service.definition.maintain.MaintainWorkflowDefinition;
import org.nabucco.framework.workflow.facade.service.definition.search.SearchWorkflowDefinition;

/**
 * WorkflowExtensionSetup
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowExtensionSetup {

    private NabuccoLogger logger;

    private WorkflowComponent component;

    /**
     * Creates a new {@link WorkflowExtensionSetup} instance.
     * 
     * @param logger
     */
    public WorkflowExtensionSetup(WorkflowComponent component, NabuccoLogger logger) {
        this.component = component;
        this.logger = logger;
    }

    /**
     * Setup the workflow component by configured extensions.
     */
    public void setupWorkflow() {

        this.logger.info("Starting setup of configured WorkflowDefinitions.");

        try {
            ExtensionMap workflowExtensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_FRAMEWORK_WORKFLOW);

            for (String extensionName : workflowExtensions.getExtensionNames()) {

                NabuccoExtension extension = workflowExtensions.getExtension(extensionName);

                if (extension instanceof WorkflowConfigurationExtension) {

                    WorkflowDefinitionExtension workflowExtension = ((WorkflowConfigurationExtension) extension)
                            .getWorkflow();

                    WorkflowDefinition definition = this.mapToDefinition(workflowExtension);

                    this.maintainWorkflowSignals(definition.getSignalList());

                    try {
                        definition.accept(new WorkflowTriggerSignalVisitor(definition.getSignalList()));
                    } catch (VisitorException ve) {
                        this.logger.error(ve, "Unexpected error in visiting workflow definition.");
                    }

                    this.maintainWorkflowDefinition(definition);
                }
            }

        } catch (ExtensionException ee) {
            this.logger.error(ee, "Error loading workflow extensions.");
        } catch (Exception e) {
            this.logger.error(e, "Error loading workflow extensions.");
        } finally {
            this.logger.info("Finished setup of configured WorkflowDefinitions.");
        }

    }

    /**
     * Map a workflow definition extension to a workflow definition
     * 
     * @param workflowExtension
     *            the extension
     * 
     * @return the workflow definition
     */
    private WorkflowDefinition mapToDefinition(WorkflowDefinitionExtension workflowExtension) {
        return WorkflowDefinitionConfigurationMapper.getInstance().mapToDefinition(workflowExtension);
    }

    /**
     * Maintain the workflow definition.
     * 
     * @param workflow
     *            the workflow to maintain
     */
    private void maintainWorkflowSignals(List<WorkflowSignalContainer> signalList) {

        MaintainWorkflowDefinition maintainService;

        try {
            maintainService = this.component.getMaintainWorkflowDefinition();
        } catch (ServiceException se) {
            this.logger.error(se, "Error locating MaintainWorkflowDefinition.");
            return;
        }

        ServiceMessageContext context = ServiceContextFactory.getInstance().newServiceMessageContext();

        for (WorkflowSignalContainer container : signalList) {

            WorkflowSignal signal = container.getSignal();

            try {
                WorkflowSignal existingSignal = this.existingSignal(signal);

                if (existingSignal != null && existingSignal.getId() != null) {
                    this.logger.info("Skipping signal '", signal.getName().getValue(),
                            "' since a signal with the same name already exists.");

                    container.setSignal(existingSignal);

                    continue;
                }

                ServiceRequest<WorkflowSignalMsg> rq = new ServiceRequest<WorkflowSignalMsg>(context);

                WorkflowSignalMsg signalMsg = new WorkflowSignalMsg();
                signalMsg.setSignal(signal);
                rq.setRequestMessage(signalMsg);

                ServiceResponse<WorkflowSignalMsg> rs = maintainService.maintainWorkflowSignal(rq);

                container.setSignal(rs.getResponseMessage().getSignal());

            } catch (ServiceException se) {
                this.logger.error(se, "Error maintaining workflow signal [", signal.getName().getValue(), "].");
            }
        }
    }

    /**
     * Maintain the workflow definition.
     * 
     * @param workflow
     *            the workflow to maintain
     */
    private void maintainWorkflowDefinition(WorkflowDefinition workflow) {

        try {
            if (this.alreadyExists(workflow)) {
                this.logger.info("Skipping workflow '", workflow.getName().getValue(),
                        "' since a workflow with the same name already exists.");
                return;
            }

            MaintainWorkflowDefinition maintainService = this.component.getMaintainWorkflowDefinition();

            ServiceMessageContext context = ServiceContextFactory.getInstance().newServiceMessageContext();
            ServiceRequest<WorkflowDefinitionMsg> rq = new ServiceRequest<WorkflowDefinitionMsg>(context);

            WorkflowDefinitionMsg msg = new WorkflowDefinitionMsg();
            msg.setWorkflow(workflow);
            rq.setRequestMessage(msg);

            maintainService.maintainWorkflowDefinition(rq);

            this.logger.info("Successfully maintained workflow definition [", workflow.getName().getValue(), "].");

        } catch (ServiceException se) {
            this.logger.error(se, "Error maintaining workflow definition [", workflow.getName().getValue(), "].");
        }
    }

    /**
     * Checks whether a workflow definition does already exist.
     * 
     * @param workflow
     *            the workflow definition to search for
     * 
     * @return <b>true</b> if the workflow does already exist in the database, <b>false</b> if not
     * 
     * @throws ServiceException
     *             when the search did end normally
     */
    private boolean alreadyExists(WorkflowDefinition workflow) throws ServiceException {

        ServiceMessageContext context = ServiceContextFactory.getInstance().newServiceMessageContext();
        SearchWorkflowDefinition searchService = this.component.getSearchWorkflowDefinition();

        WorkflowDefinitionSearchRq msg = new WorkflowDefinitionSearchRq();
        msg.setName(workflow.getName());

        ServiceRequest<WorkflowDefinitionSearchRq> rq = new ServiceRequest<WorkflowDefinitionSearchRq>(context);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowDefinitionListMsg> rs = searchService.searchWorkflowDefinition(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            return true;
        }

        return !rs.getResponseMessage().getWorkflowDefinitionList().isEmpty();
    }

    /**
     * Checks whether a workflow signal does already exist.
     * 
     * @param signal
     *            the workflow signal to search for
     * 
     * @return the already existing signal, or null if none exists
     * 
     * @throws ServiceException
     *             when the search did end normally
     */
    private WorkflowSignal existingSignal(WorkflowSignal signal) throws ServiceException {

        ServiceMessageContext context = ServiceContextFactory.getInstance().newServiceMessageContext();
        SearchWorkflowDefinition searchService = this.component.getSearchWorkflowDefinition();

        WorkflowSignalSearchRq msg = new WorkflowSignalSearchRq();
        msg.setName(signal.getName());

        ServiceRequest<WorkflowSignalSearchRq> rq = new ServiceRequest<WorkflowSignalSearchRq>(context);
        rq.setRequestMessage(msg);

        ServiceResponse<WorkflowSignalListMsg> rs = searchService.searchWorkflowSignal(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            return null;
        }

        return rs.getResponseMessage().getSignalList().first();
    }

}
