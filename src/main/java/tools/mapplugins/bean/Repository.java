package tools.mapplugins.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tools.mapplugins.xml.Project;


/**
 * Handling artifacts
 * @author jchaline
 */
public class Repository implements Serializable
{
    private static final long serialVersionUID = 8115463804253023495L;

    /**
     * Reference all the artifact, getting by group id, id, and version
     */
    private Map<String, Map<String, Map<String, Project>>> artifacts = new HashMap<String, Map<String, Map<String, Project>>>( );

    /**
     * Get all the artifacts
     * @return the artifacts list
     */
    public List<Project> getProjectsList( )
    {
        List<Project> list = new ArrayList<Project>( );
        for ( Map<String, Map<String, Project>> groups : getArtifacts( ).values( ) )
        {
            for ( Map<String, Project> versions : groups.values( ) )
            {
                list.addAll( versions.values( ) );
            }
        }

        return list;
    }

    /**
     * The way to add artifact to repository
     * @param artifact the artifact to add
     */
    public void add( Project artifact )
    {
        if ( !getArtifacts( ).containsKey( artifact.getGroupId( ) ) )
        {
            getArtifacts( ).put( artifact.getGroupId( ), new HashMap<String, Map<String, Project>>( ) );
        }
        if ( !getArtifacts( ).get( artifact.getGroupId( ) ).containsKey( artifact.getArtifactId( ) ) )
        {
            getArtifacts( ).get( artifact.getGroupId( ) ).put( artifact.getArtifactId( ),
                    new HashMap<String, Project>( ) );
        }
        getArtifacts( ).get( artifact.getGroupId( ) ).get( artifact.getArtifactId( ) )
                .put( artifact.getVersion( ), artifact );
    }
    
    public void addAll(Collection<Project> artifacts){
    	for(Project p : artifacts){
    		add(p);
    	}
    }

    /**
     * Get exactly artifact with coordonates
     * @param groupId
     * @param artifactId
     * @param version
     * @return
     */
    public Project get( String groupId, String artifactId, String version )
    {
        Project project = null;
        if ( getArtifacts( ).containsKey( groupId ) && getArtifacts( ).get( groupId ).containsKey( artifactId ) )
        {
            project = getArtifacts( ).get( groupId ).get( artifactId ).get( version );
        }
        return project;
    }

    /**
     * Remove exactly artifact with coordonates, only one version or all.
     * @param groupId
     * @param artifactId
     * @param version
     * @return
     */
    public void remove( String groupId, String artifactId, String version )
    {
        if ( getArtifacts( ).containsKey( groupId ) && getArtifacts( ).get( groupId ).containsKey( artifactId ) )
        {
            if ( StringUtils.isNotBlank( version ) )
            {
                getArtifacts( ).get( groupId ).get( artifactId ).remove( version );
            }
            else
            {
                getArtifacts( ).get( groupId ).remove( artifactId );
            }
        }
    }

    /**
     * @return the artifacts
     */
    public Map<String, Map<String, Map<String, Project>>> getArtifacts( )
    {
        return artifacts;
    }

    /**
     * @param artifacts the artifacts to set
     */
    public void setArtifacts( Map<String, Map<String, Map<String, Project>>> artifacts )
    {
        this.artifacts = artifacts;
    }

    /**
     * Remove artifact
     * @param groupId
     * @param artifactId
     * @param version
     * @return
     */
    public void remove( Project p )
    {
        this.remove( p.getGroupId( ), p.getArtifactId( ), p.getVersion( ) );
    }
}
