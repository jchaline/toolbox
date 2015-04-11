package tools.formengine.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "choice" )
public class Choice
{
    @XmlAttribute
    private String label;
    
    @XmlAttribute
    private String value;

    /**
     * @return the label
     */
    public String getLabel( )
    {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel( String label )
    {
        this.label = label;
    }

    /**
     * @return the value
     */
    public String getValue( )
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue( String value )
    {
        this.value = value;
    }
}
