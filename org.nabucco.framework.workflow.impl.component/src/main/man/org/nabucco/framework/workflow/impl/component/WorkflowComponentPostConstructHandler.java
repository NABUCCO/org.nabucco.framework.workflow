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

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.workflow.facade.component.WorkflowComponent;

/**
 * WorkflowComponentPostConstructHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowComponentPostConstructHandler extends PostConstructHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public void invoke() {

        NabuccoLogger logger = super.getLogger();

        if (super.getLocatable() == null) {
            logger.warning("Component [null] is not of type WorkflowComponent.");
            return;
        }

        if (!(super.getLocatable() instanceof WorkflowComponent)) {
            logger.warning("Component [", super.getLocatable().getClass().getSimpleName(),
                    "] is not of type WorkflowComponent.");
            return;
        }

        logger.debug("Initializing Workflow Component.");

        WorkflowComponent component = (WorkflowComponent) super.getLocatable();
        WorkflowExtensionSetup setup = new WorkflowExtensionSetup(component, logger);
        setup.setupWorkflow();

    }

}
