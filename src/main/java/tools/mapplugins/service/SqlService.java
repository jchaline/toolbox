package tools.mapplugins.service;

import java.util.List;

import org.apache.log4j.Logger;

import services.FileService;
import services.PropertiesService;
import tools.mapplugins.bean.Repository;
import tools.mapplugins.commons.MappluginConstants;
import tools.mapplugins.xml.Project;


public class SqlService
{
    private static final Logger LOGGER = Logger.getLogger( SqlService.class );

    public static int generateSqlFile( Repository repo, String path )
    {
        String strArtifactFirstId = PropertiesService.getProperty( MappluginConstants.MARK_SQL_ARTIFACT_FIRST_ID );
        String strDependenciesFirstId = PropertiesService
                .getProperty( MappluginConstants.MARK_SQL_DEPENDENCIES_FIRST_ID );
        Integer artifactId = Integer.valueOf( strArtifactFirstId );
        Integer dependencyId = Integer.valueOf( strDependenciesFirstId );

        List<Project> listProject = repo.getProjectsList( );
        StringBuilder builder = new StringBuilder( );
        for ( Project project : listProject )
        {
            builder.append( getSqlForProject( artifactId, project ) );
            for ( String dependency : project.getRealDependencies( ) )
            {
                builder.append( getSqlForStrDependency( dependencyId, artifactId, dependency ) );
                dependencyId++;
            }
            artifactId++;
        }

        return FileService.write( path, builder.toString( ) );
    }

    /**
     * Get the line for the dependency
     * @param artifactId the artifact bean id
     * @param dependencyId the dependency bean id
     * @param dependency the str dependency
     * @return the SQL line
     */
    private static Object getSqlForStrDependency( Integer dependencyId, Integer artifactId, String dependency )
    {
        StringBuilder builder = new StringBuilder( );
        String[] coords = dependency.split( MappluginConstants.ARTIFACT_COORD_SEPARATOR );
        if ( coords.length == 3 )
        {
            builder.append( "INSERT INTO " );
            builder.append( PropertiesService.getProperty( MappluginConstants.MARK_SQL_DEPENDENCIES_TABLE ) );
            builder.append( " VALUES ('" + dependencyId + "', '" + artifactId + "', '" + coords[0] + "', '" + coords[1]
                    + "', '" + coords[2] + "');\n" );
        }
        else
        {
            LOGGER.error( "Wrong number of coords into dependency : " + dependency );
        }
        return builder.toString( );
    }

    /**
     * Get the line for the artifact
     * @param currentId
     * @param project the artifact
     * @return the SQL line
     */
    private static String getSqlForProject( int currentId, Project project )
    {
        StringBuilder builder = new StringBuilder( );
        builder.append( "INSERT INTO " );
        builder.append( PropertiesService.getProperty( MappluginConstants.MARK_SQL_ARTIFACT_TABLE ) );
        builder.append( " VALUES ('" + currentId + "', '" + project.getArtifactId( ) + "', '" + project.getGroupId( )
                + "', '" + project.getVersion( ) + "');\n" );
        return builder.toString( );
    }
}
