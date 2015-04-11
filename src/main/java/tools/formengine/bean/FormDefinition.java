package tools.formengine.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "formDefinition")
public class FormDefinition
{
    @XmlAttribute
    private String name;
    
    @XmlElement
    private String title;
    
    @XmlElementWrapper(name="subForms")
    @XmlElement(name="subFormDefinition")
    private List<SubFormDefinition> subforms;
    
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
     * @return the subforms
     */
    public List<SubFormDefinition> getSubforms( )
    {
        return subforms;
    }
}
