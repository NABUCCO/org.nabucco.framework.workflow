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
package org.nabucco.framework.workflow.impl.service.aspect;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.transitioning.TransitioningAspect;
import org.nabucco.framework.base.impl.service.aspect.transitioning.TransitioningException;
import org.nabucco.framework.base.impl.service.aspect.transitioning.TransitioningSupport;

/**
 * ResumeWorkflowTransitioningAspect
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResumeWorkflowTransitioningAspect extends TransitioningSupport implements TransitioningAspect {

    @Override
    public void transitionBefore(ServiceMessage requestMessage) throws TransitioningException {
        // Nothing to do before!
    }

    @Override
    public void transitionAfter(ServiceMessage requestMessage, ServiceMessage responseMessage)
            throws TransitioningException {

        if (responseMessage == null) {
            return;
        }

        ResumeWorkflowTransitioningAspectVisitor visitor = new ResumeWorkflowTransitioningAspectVisitor(
                super.getContext());

        try {
            responseMessage.accept(visitor);
        } catch (VisitorException ve) {
            throw new TransitioningException("Error resuming workflow.", ve);
        }
    }

}
