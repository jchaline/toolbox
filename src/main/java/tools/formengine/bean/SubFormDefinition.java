package tools.formengine.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subFormDefinition")
public class SubFormDefinition
{
    @XmlAttribute
    private String name;
    @XmlElement
    private String title;
    
    @XmlElementWrapper(name="formElements")
    @XmlElement(name="fields")
    private List<Fields> fields;
    /**
     * @return the name
     */
    public String getName( )
    {
        return name;
    }

    /**
     * @return the title
     */
    public String getTitle( )
    {
        return title;
    }

    /**
     * @return the formElements
     */
    public List<Fields> getFields( )
    {
        return fields;
    }

   
}
