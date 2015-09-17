package tools.mapplugin.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import services.PropertiesService;
import services.SerializableService;
import tools.mapplugins.bean.ArtifactComparator;
import tools.mapplugins.bean.Repository;
import tools.mapplugins.commons.MappluginConstants;
import tools.mapplugins.service.MavenService;
import tools.mapplugins.xml.Project;


@RunWith( MockitoJUnitRunner.class )
public class MavenServiceTest
{
    private String serializableFile;
    private Repository repo;

    @Before
    public void init( )
    {
        PropertiesService.init( );
        serializableFile = PropertiesService.getProperty( MappluginConstants.MARK_SERIALIZABLE_FILE );
        repo = SerializableService.deserialize( serializableFile );
    }

    @Test
    @Ignore( "Not yet implemented : suffix different of SNAPSHOT" )
    public void compareAlphaTest( )
    {
        assertTrue( ArtifactComparator.compareStatic( "2.0.0", "2.0.2" ) == -1 );
        assertTrue( ArtifactComparator.compareStatic( "2.0.1", "2.0.2" ) == -1 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1", "2.0.2" ) == 1 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1", "2.0.2-RC01" ) == 1 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1", "3.0.1-RC01" ) == 1 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1-RC02", "3.0.1-RC02" ) == 0 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1-RC03", "3.0.1-RC02" ) == 1 );
    }

    @Test
    @Ignore
    public void compareTest( )
    {
        assertTrue( ArtifactComparator.compareStatic( "2.0.0", "2.0.2" ) < 0 );
        assertTrue( ArtifactComparator.compareStatic( "2.0.1", "2.0.2" ) < 0 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1", "2.0.2" ) > 0 );
        assertTrue( ArtifactComparator.compareStatic( "3.0.1", "3.0.1-SNAPSHOT" ) > 0 );
    }

    @Test
    @Ignore
    public void associateDependenciesTest( )
    {
        Project project = MavenService.findDependency( repo, "fr.paris.lutece.plugins", "plugin-html",
                "[2.0.2-SNAPSHOT,2.1.3]" );

        project.setRealDependencies( new ArrayList<String>( ) );

        assertTrue( project.getRealDependencies( ).isEmpty( ) );

        MavenService.associateDependencies( repo );

        assertTrue( !project.getRealDependencies( ).isEmpty( ) );

        //asser that all dependency contains three part separate with ;
        for ( Project p : repo.getProjectsList( ) )
        {
            for ( String s : p.getRealDependencies( ) )
            {
                assertTrue( s.split( MappluginConstants.ARTIFACT_COORD_SEPARATOR ).length == 3 );
            }
        }
    }

    @Test
    @Ignore
    public void resolveVersionTest( )
    {
        Project dependencySimple = MavenService.findDependency( repo, "fr.paris.lutece.plugins", "plugin-html",
                "2.1.2-SNAPSHOT" );
        Project dependencyPlage = MavenService.findDependency( repo, "fr.paris.lutece.plugins", "plugin-html",
                "[2.0.2-SNAPSHOT,2.1.3]" );

        assertTrue( dependencySimple != null );
        assertTrue( dependencyPlage != null );
    }
}
