package tools.formengine.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "checkRule" )
public class CheckRule
{
    @XmlAttribute
    private String type;
    
    @XmlAttribute
    private String parameter;
    /**
     * @return the type
     */
    public String getType( )
    {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType( String type )
    {
        this.type = type;
    }
    /**
     * @return the parameter
     */
    public String getParameter( )
    {
        return parameter;
    }
    /**
     * @param parameter the parameter to set
     */
    public void setParameter( String parameter )
    {
        this.parameter = parameter;
    }
}
