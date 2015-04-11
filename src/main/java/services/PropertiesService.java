package services;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;


public class PropertiesService
{

    public static final String PATH_PROJECT = System.getProperty( "user.dir" );
    public final static String FOLDER_CONFIG_NAME = "\\conf";
    public final static String FOLDER_OVERRIDE_NAME = "\\override";
    public static final String PROPERTIES_PATTERN = "([^\\s]+(\\.(?i)(properties))$)";

    private static Properties _properties = new Properties( );

    private PropertiesService( )
    {

    }

    /**
     * Get the key present in propertie map
     * @return the key list
     */
    public static List<String> getKeys( )
    {
        List<String> keyList = new ArrayList<String>( );
        keyList.addAll( (Set) _properties.keySet( ) );
        return keyList;
    }

    /**
     * The initialization class, ensure the load of the properties files
     */
    public static void init( )
    {
        List<String> findPropertiesFiles = findPropertiesFiles( );
        for ( String filePath : findPropertiesFiles )
        {
            initFileProperties( filePath );
        }
        List<String> findOverrideFiles = findOverrideFiles( );
        for ( String filePath : findOverrideFiles )
        {
            initFileProperties( filePath );
        }
    }

    /**
     * filter line, say if we should keep a line
     * @param line the line to test
     * @return the line to keep
     */
    public static List<String> filterLine( List<String> lines )
    {
        List<String> toKeep = new LinkedList<String>( );
        for ( String line : lines )
        {
            if ( line.length( ) > 0 && line.charAt( 0 ) != '#' )
            {
                toKeep.add( line );
            }
        }
        return toKeep;
    }

    /**
     * Load the properties file
     * @return the properties object
     */
    public static void initFileProperties( String filePath )
    {
        try
        {
            _properties.load( new FileInputStream( filePath ) );
        }
        catch ( Exception e )
        {
            e.printStackTrace( );
        }
    }

    /**
     * 
     * @param keyPathProject
     * @return
     */
    public static String getProperty( String keyPathProject )
    {
        return _properties.getProperty( keyPathProject );
    }

    /**
     * Find all the properties file to use
     * @return
     */
    private static List<String> findPropertiesFiles( )
    {
        return FileService.findFiles( -1, PATH_PROJECT + FOLDER_CONFIG_NAME, PROPERTIES_PATTERN );
    }

    /**
     * Find all the OVERRIDE properties file to use
     * @return
     */
    private static List<String> findOverrideFiles( )
    {
        return FileService.findFiles( 1, PATH_PROJECT + FOLDER_CONFIG_NAME + FOLDER_OVERRIDE_NAME, PROPERTIES_PATTERN );
    }

    /**
     * Call the containsKey method of the Properties
     * @param key the property key
     * @return true if the property exist, false otherwise
     */
    public static boolean containsKey( String key )
    {
        return _properties.containsKey( key );
    }
}
