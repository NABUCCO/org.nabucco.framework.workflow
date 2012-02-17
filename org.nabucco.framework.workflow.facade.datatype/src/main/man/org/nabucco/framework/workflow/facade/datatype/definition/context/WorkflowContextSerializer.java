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
package org.nabucco.framework.workflow.facade.datatype.definition.context;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.workflow.facade.datatype.instance.context.WorkflowContext;

/**
 * WorkflowContextSerializer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowContextSerializer {

    /**
     * Singleton instance.
     */
    private static WorkflowContextSerializer instance = new WorkflowContextSerializer();

    /**
     * Private constructor.
     */
    private WorkflowContextSerializer() {
    }

    /**
     * Singleton access.
     * 
     * @return the WorkflowContextSerializer instance.
     */
    public static WorkflowContextSerializer getInstance() {
        return instance;
    }

    /**
     * Serialize the workflow context to xml.
     * 
     * @param context
     *            the context to serialize
     * 
     * @return the serialized context
     * 
     * @throws SerializationException
     *             when the context serialization fails
     */
    public void serialize(WorkflowContext context) throws SerializationException {
        if (context == null || context.getDatatype() == null) {
            return;
        }

        XmlSerializer serializer = new XmlSerializer();
        SerializationResult result = serializer.serialize(context.getDatatype());
        context.setXml(result.getContent());
    }

    /**
     * Deserializes the workflow context from xml.
     * 
     * @param context
     *            the context to deserialize
     * 
     * @return the deserialized context
     * 
     * @throws SerializationException
     *             when the context deserialization fails
     */
    public void deserialize(WorkflowContext context) throws SerializationException {
        if (context == null || context.getXml() == null || context.getXml().getValue() == null) {
            return;
        }

        XmlSerializer serializer = new XmlSerializer();
        List<Datatype> datatypes = serializer.deserialize(new DeserializationData(context.getXml().getValue()));

        if (datatypes.isEmpty()) {
            return;
        }

        Datatype datatype = datatypes.get(0);

        if (!(datatype instanceof NabuccoDatatype)) {
            throw new SerializationException(
                    "Error deserializing WorkflowContext. Serialized datatype is not of type NabuccoDatatype.");
        }

        context.setDatatype((NabuccoDatatype) datatype);
    }

}
