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
package org.nabucco.framework.workflow.facade.component;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for WorkflowComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class WorkflowComponentLocator extends ComponentLocatorSupport<WorkflowComponent> implements
        ComponentLocator<WorkflowComponent> {

    private static WorkflowComponentLocator instance;

    /**
     * Constructs a new WorkflowComponentLocator instance.
     *
     * @param component the Class<WorkflowComponent>.
     * @param jndiName the String.
     */
    private WorkflowComponentLocator(String jndiName, Class<WorkflowComponent> component) {
        super(jndiName, component);
    }

    @Override
    public WorkflowComponent getComponent() throws ConnectionException {
        WorkflowComponent component = super.getComponent();
        if ((component instanceof WorkflowComponentLocal)) {
            return new WorkflowComponentLocalProxy(((WorkflowComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the WorkflowComponentLocator.
     */
    public static WorkflowComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new WorkflowComponentLocator(WorkflowComponent.JNDI_NAME, WorkflowComponent.class);
        }
        return instance;
    }
}
