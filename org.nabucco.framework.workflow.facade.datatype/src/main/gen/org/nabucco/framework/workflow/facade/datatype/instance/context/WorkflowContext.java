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
package org.nabucco.framework.workflow.facade.datatype.instance.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.xml.XmlDocument;

/**
 * WorkflowContext<p/>The workflow definition context.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-04
 */
public class WorkflowContext extends Context implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,2147483647;u0,n;m0,1;" };

    public static final String XML = "xml";

    /** The serialized datatype. */
    private XmlDocument xml;

    /** Constructs a new WorkflowContext instance. */
    public WorkflowContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowContext.
     */
    protected void cloneObject(WorkflowContext clone) {
        super.cloneObject(clone);
        if ((this.getXml() != null)) {
            clone.setXml(this.getXml().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(Context.class).getPropertyMap());
        propertyMap.put(XML,
                PropertyDescriptorSupport.createBasetype(XML, XmlDocument.class, 5, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowContext.getPropertyDescriptor(XML), this.xml, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(XML) && (property.getType() == XmlDocument.class))) {
            this.setXml(((XmlDocument) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final WorkflowContext other = ((WorkflowContext) obj);
        if ((this.xml == null)) {
            if ((other.xml != null))
                return false;
        } else if ((!this.xml.equals(other.xml)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.xml == null) ? 0 : this.xml.hashCode()));
        return result;
    }

    @Override
    public WorkflowContext cloneObject() {
        WorkflowContext clone = new WorkflowContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The serialized datatype.
     *
     * @return the XmlDocument.
     */
    public XmlDocument getXml() {
        return this.xml;
    }

    /**
     * The serialized datatype.
     *
     * @param xml the XmlDocument.
     */
    public void setXml(XmlDocument xml) {
        this.xml = xml;
    }

    /**
     * The serialized datatype.
     *
     * @param xml the String.
     */
    public void setXml(String xml) {
        if ((this.xml == null)) {
            if ((xml == null)) {
                return;
            }
            this.xml = new XmlDocument();
        }
        this.xml.setValue(xml);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowContext.class).getAllProperties();
    }
}
