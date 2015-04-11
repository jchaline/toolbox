package tools.formengine.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "formElements")
public class FormElements
{
    @XmlElement
    private List<Fields> fields;

    /**
     * @return the fields
     */
    public List<Fields> getFields( )
    {
        return fields;
    }
}
