package tools.formengine.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "field" )
public class Field
{
    @XmlAttribute
    private String type;

    @XmlAttribute
    private String name;

    @XmlElement
    private String label;
    
    @XmlElementWrapper(name="choiceList")
    @XmlElement(name="choice")
    private List<Choice> choices;

    @XmlElementWrapper(name="checkFieldRules")
    @XmlElement(name="checkRule")
    private List<CheckRule> rules;

    public Integer id_field;
    public Integer id_entry;
    public Integer height;
    public Integer width;
    public Integer is_default_value;
    public Integer max_size_enter;
    public Integer field_position;
    public String workgroup_key;
    public Integer is_shown_in_result_list;
    public Integer is_shown_in_result_record;
    public String title;
    public String default_value = "''";

    /**
     * @return the type
     */
    public String getType( )
    {
        return type;
    }

    /**
     * @return the name
     */
    public String getName( )
    {
        return name;
    }

    /**
     * @return the label
     */
    public String getLabel( )
    {
        return label;
    }

    /**
     * @return the choices
     */
    public List<Choice> getChoices( )
    {
        return choices;
    }

    /**
     * @param choices the choices to set
     */
    public void setChoices( List<Choice> choices )
    {
        this.choices = choices;
    }

    /**
     * @return the rules
     */
    public List<CheckRule> getRules( )
    {
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules( List<CheckRule> rules )
    {
        this.rules = rules;
    }

}
