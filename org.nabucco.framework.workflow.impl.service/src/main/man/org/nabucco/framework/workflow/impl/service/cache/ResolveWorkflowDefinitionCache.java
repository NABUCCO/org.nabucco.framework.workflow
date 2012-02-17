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
package org.nabucco.framework.workflow.impl.service.cache;

import java.util.Iterator;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.caching.CachingAspect;
import org.nabucco.framework.base.impl.service.aspect.caching.CachingException;
import org.nabucco.framework.base.impl.service.aspect.caching.CachingSupport;
import org.nabucco.framework.workflow.facade.datatype.definition.WorkflowDefinition;
import org.nabucco.framework.workflow.facade.message.definition.WorkflowDefinitionListMsg;

/**
 * ResolveWorkflowDefinitionCache
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResolveWorkflowDefinitionCache extends CachingSupport implements CachingAspect {

    private static final String KEY_PREFIX = "ResolveWorkflowDefinition";

    private ServiceMessage response;

    @Override
    public void before(ServiceMessage requestMessage) throws CachingException {
        if (!(requestMessage instanceof WorkflowDefinitionListMsg)) {
            return;
        }

        String key = this.createKey((WorkflowDefinitionListMsg) requestMessage);

        this.response = super.retrieveFromCache(key);
    }

    @Override
    public void after(ServiceMessage requestMessage, ServiceMessage responseMessage) throws CachingException {
        if (!(requestMessage instanceof WorkflowDefinitionListMsg)) {
            return;
        }

        String key = this.createKey((WorkflowDefinitionListMsg) requestMessage);

        super.storeInCache(key, responseMessage);
    }

    /**
     * Create the cache key.
     * 
     * @param rq
     *            the request message
     * 
     * @return the key string
     */
    private String createKey(WorkflowDefinitionListMsg rq) {
        StringBuilder key = new StringBuilder();
        key.append(KEY_PREFIX);
        key.append(':');
        key.append(' ');

        for (Iterator<WorkflowDefinition> iterator = rq.getWorkflowDefinitionList().iterator(); iterator.hasNext();) {
            WorkflowDefinition definition = iterator.next();
            key.append(definition.getId());
            if (iterator.hasNext()) {
                key.append(',');
            }
        }
        return key.toString();
    }

    @Override
    public final boolean isCached() {
        return this.response != null;
    }

    @Override
    public final ServiceMessage getResponseMessage() {
        return this.response;
    }

}
