package launch;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import services.PropertiesService;
import services.SpringService;
import tools.Tool;


/**
 * Classe principal de la toolbox, execute une sous appplication en fonction de
 * l'argument fournit
 * @author jchaline
 * 
 */
public class ToolLauncher
{
    private static final Logger logger = Logger.getLogger( ToolLauncher.class );

    public static void main( String[] args )
    {
    	SpringService.init();
        PropertiesService.init( );

        String toolId = null;
        if ( args.length != 1 )
        {
            Scanner sc = new Scanner( System.in );
            System.out.println( "Which tool use ?" );
            toolId = sc.nextLine( );
            sc.close( );
        }
        else
        {
            toolId = args[0];
        }

        List<Tool> tools = SpringService.getBean( Tool.class );
        logger.debug( "Tools number : " + tools.size( ) );

        Iterator<Tool> toolsItr = tools.iterator( );
        while ( toolsItr.hasNext( ) )
        {
            Tool tool = toolsItr.next( );
            if ( tool.getId( ).equals( toolId ) )
            {
                logger.info( "Tool running ! " );
                logger.info( "Use conf : " );
                logger.info( tool.getConf( ) );
                int status = tool.run( );
                logger.info( "Tool exit with status " + status );
            }
        }
    }
}
