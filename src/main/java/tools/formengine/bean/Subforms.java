package tools.formengine.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subForms")
public class Subforms
{
    @XmlElement
    private List<SubFormDefinition> subFormDefinitions;

    /**
     * @return the subFormDefinition
     */
    public List<SubFormDefinition> getSubFormDefinition( )
    {
        return subFormDefinitions;
    }

}
