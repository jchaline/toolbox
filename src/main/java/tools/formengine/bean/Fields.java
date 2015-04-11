package tools.formengine.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fields")
public class Fields
{
    @XmlElement(name="field")
    private List<Field> fields;

    /**
     * @return the fields
     */
    public List<Field> getFields( )
    {
        return fields;
    }
}
