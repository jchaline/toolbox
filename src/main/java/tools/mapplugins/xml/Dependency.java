package tools.mapplugins.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import tools.mapplugins.commons.MappluginConstants;


@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "dependency" )
public class Dependency implements Serializable
{
    private static final long serialVersionUID = -3474385308647789992L;

    @XmlElement( name = "artifactid" )
    private String artifactId;

    @XmlElement( name = "version" )
    private String version;

    @XmlElement( name = "groupid" )
    private String groupId;

    @XmlElement( name = "type" )
    private String type;

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
     * @return the groupId
     */
    public String getGroupId( )
    {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    /**
     * @return the version
     */
    public String getVersion( )
    {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion( String version )
    {
        this.version = version;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId( )
    {
        return artifactId;
    }

    /**
     * @param artifactId the artifactId to set
     */
    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    @Override
    public String toString( )
    {
        return groupId + MappluginConstants.ARTIFACT_COORD_SEPARATOR + artifactId
                + MappluginConstants.ARTIFACT_COORD_SEPARATOR + version;
    }
}
