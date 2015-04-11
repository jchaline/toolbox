package tools.generator.service;

import java.util.LinkedList;
import java.util.List;

import services.FileService;
import services.PropertiesService;
import tools.generator.bean.Attribut;
import tools.generator.bean.Bean;
import tools.generator.bean.Plugin;
import tools.generator.utils.GeneratorConstants;


public class BeanService
{
    public static final String PATH_PROJECT = System.getProperty( "user.dir" );
    public final static String FOLDER_CONFIG_NAME = "\\conf";
    public final static String FOLDER_OVERRIDE_NAME = "\\override";
    public static final String PROPERTIES_PATTERN = "([^\\s]+(\\.(?i)(properties))$)";

    private BeanService( )
    {

    }

    /**
     * Generate Plugin object
     * @param properties the properties with beans
     * @return the plugin object
     */
    public static Plugin getPlugin( ) 
    {
        Plugin plugin = new Plugin( PropertiesService.getProperty( GeneratorConstants.KEY_PLUGIN_NAME ) );
        plugin.setName( PropertiesService.getProperty( GeneratorConstants.KEY_PLUGIN_NAME ) );
        plugin.setAuthorName( PropertiesService.getProperty( GeneratorConstants.KEY_PLUGIN_AUTHOR ) );

        List<Bean> beanList = getBeanList( );

        plugin.setBeanList( beanList );

        return plugin;
    }

    /**
     * Get bean list from the config file
     * @return the list of bean for the plugin
     */
    private static List<Bean> getBeanList( )
    {
        List<Bean> beanList = new LinkedList<Bean>( );

        String pathBeansFile = PropertiesService.getProperty( GeneratorConstants.KEY_BEANS_FILE_NAME );
        List<String> linesOfBeanFile = FileService.read( pathBeansFile );
        linesOfBeanFile = filterLine( linesOfBeanFile );

        for ( String line : linesOfBeanFile )
        {
            Bean createBeanFromLine = createBeanFromLine( line );
            beanList.add( createBeanFromLine );
        }
        return beanList;
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
     * Create a bean with the information in the line given
     * @param line the line with the bean information
     * @return the bean which correspond
     */
    public static Bean createBeanFromLine( String line )
    {
        String[] lineSplit = line.split( ";" );

        String[] beanInfo = lineSplit[0].split( "," );
        String beanName = beanInfo[0];

        Bean bean = new Bean( beanName );

        for ( int i = 1; i < lineSplit.length; i++ )
        {
            Attribut a = ( createAttribut( lineSplit[i] ) );
            bean.add( a.getName( ), a );
        }

        return bean;
    }

    /**
     * Create the object attribut from a substring take in the bean file
     * @param strAttribut the substring with the bean's attributs
     * @return the object Attribut
     */
    public static Attribut createAttribut( String strAttribut )
    {
        String[] arrayAttribut = strAttribut.split( "," );
        String typeAttribut = arrayAttribut[0];
        String nameAttribut = arrayAttribut[1];
        boolean isFiltered = arrayAttribut.length == 3 && arrayAttribut[2].endsWith( "true" );
        Attribut attribut = new Attribut( typeAttribut, nameAttribut, isFiltered );
        return attribut;
    }
}
