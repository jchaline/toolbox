package tools.mapplugins.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import tools.mapplugins.bean.ArtifactComparator;
import tools.mapplugins.bean.Repository;
import tools.mapplugins.xml.Dependency;
import tools.mapplugins.xml.Project;

/**
 * the maven service
 */
public class MavenService
{
    private static final String DEFAULT_GROUPID = "fr.paris.lutece.plugins";

    private static final Logger logger = Logger.getLogger( MavenService.class );

    public static final char MARK_PARENTHESE_OPEN = '(';
    public static final char MARK_PARENTHESE_CLOSE = ')';
    public static final char MARK_HOOK_OPEN = '[';
    public static final char MARK_HOOK_CLOSE = ']';
    public static final String MARK_COMMA = ",";
    
    /**
     * Get the project object from file content content
     * @param content the project pom.xml content
     * @return the project
     * @throws JAXBException exception while parsing the pom
     */
    public static Project getProject( String content ) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance( Project.class );
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller( );

        Project unmarshal = (Project) jaxbUnmarshaller
                .unmarshal( new StringReader( content.replaceAll( "[\n ]", "" ) ) );
        //TODO : replace maven properties
        return unmarshal;
    }

    /**
     * Set all dependencies with dependency solver
     * Log error if dependency cannot be found
     * @param repo the repository to associate
     */
    public static void associateDependencies( Repository repo )
    {
        //log si pas de dependence trouvé
        for ( Project p : repo.getProjectsList( ) )
        {
            p.setRealDependencies( new ArrayList<String>( ) );
            if ( p.getDependencies( ) != null && !p.getDependencies( ).isEmpty( ) )
            {

                for ( Dependency d : p.getDependencies( ) )
                {
                    logger.debug( "Dependency #" + d + "# search for " + p );
                    Project realDependency = findDependency( repo, d.getGroupId( ), d.getArtifactId( ), d.getVersion( ) );
                    if ( realDependency != null )
                    {
                        p.getRealDependencies( ).add( realDependency.toString( ) );
                    }
                    else
                    {
                        p.getRealDependencies( ).add( d.toString( ) );
                        logger.error( "Dependency #" + d + "# not found for " + p );
                    }
                }
            }
        }
    }

    /**
     * Get the list ordered of the available versions for any artifact
     * @param groupId
     * @param artifactId
     * @return the ordered list of the artifacts
     */
    public static List<String> getListVersionsAvailable( Repository repo, String groupId, String artifactId )
    {
        Map<String, Map<String, Map<String, Project>>> artifacts = repo.getArtifacts( );
        List<String> listVersion = new ArrayList<String>( );
        if ( artifacts.containsKey( groupId ) )
        {
            Map<String, Map<String, Project>> group = artifacts.get( groupId );
            if ( group.containsKey( artifactId ) )
            {
                Map<String, Project> versions = group.get( artifactId );
                Set<String> keySet = versions.keySet( );
                listVersion.addAll( keySet );
                Collections.sort( listVersion, new ArtifactComparator( ) );
            }
        }
        return listVersion;
    }

    /**
     * Get the dependency corresponding to the coordonate
     * @param groupId
     * @param artifactId
     * @param version
     * @return the dependency
     */
    public static Project findDependency( Repository repo, String groupId, String artifactId, String version )
    {
        Project dependency = null;

        //traitement d'une plage de version
        if ( MARK_PARENTHESE_OPEN == ( version.charAt( 0 ) ) || MARK_HOOK_OPEN == ( version.charAt( 0 ) ) )
        {
            dependency = findDependencyWithRange( repo, groupId, artifactId, version );
        }
        else
        {
            dependency = repo.get( groupId, artifactId, version );
        }

        return dependency;
    }

    /**
     * Get the dependency corresponding to the coordonate
     * @param groupId the group id
     * @param artifactId the artifact id
     * @param version the range version
     * @return the dependency
     */
    private static Project findDependencyWithRange( Repository repo, String groupId, String artifactId, String version )
    {
        Project p = null;
        List<String> listVersions = getListVersionsAvailable( repo, groupId, artifactId );
        if ( !listVersions.isEmpty( ) )
        {
            String[] plages = version.split( MARK_COMMA );

            char firstChar = plages[0].charAt( 0 );
            char lastChar = plages[1].charAt( plages[1].length( ) - 1 );

            //get the using min and max version
            String minVersion = plages[0].length( ) > 1 ? plages[0].substring( 1 ) : listVersions.get( 0 );
            String maxVersion = plages[1].length( ) > 1 ? plages[1].substring( 0, plages[1].length( ) - 1 )
                    : listVersions.get( listVersions.size( ) - 1 );

            String v = searchVersion( listVersions, minVersion, maxVersion, firstChar, lastChar );
            if ( StringUtils.isNotBlank( v ) )
            {
                p = repo.get( groupId, artifactId, v );
            }
        }

        return p;
    }

    /**
     * Search the greatest version
     * @param listVersions the list to search
     * @param min the mininum version accepted
     * @param max the maximum version accepted
     * @param bornMin the min born
     * @param bornMax the max born
     * @return the version
     */
    private static String searchVersion( List<String> listVersions, String minVersion, String maxVersion,
            char firstChar, char lastChar )
    {
        Collections.sort( listVersions, new ArtifactComparator( ) );
        Collections.reverse( listVersions );
        boolean find = false;
        String version = null;
        Iterator<String> itr = listVersions.iterator( );
        while ( itr.hasNext( ) && !find )
        {
            String v = itr.next( );
            int compare = ArtifactComparator.compareStatic( v, maxVersion );
            if ( compare <= 0 )
            {
                if ( compare < 0 || lastChar == MARK_HOOK_CLOSE )
                {
                    version = v;
                    find = true;
                }
            }
        }

        return version;
    }

    /**
     * Correct project data
     * @param the project to correct
     */
    public static void correctProject( Project p )
    {
        if ( StringUtils.isBlank( p.getGroupId( ) ) )
        {
            p.setGroupId( DEFAULT_GROUPID );
        }
    }
}
