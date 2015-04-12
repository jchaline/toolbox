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
        String toolIdRequired = toolId;

        List<Tool> tools = SpringService.getBean( Tool.class );
        logger.debug( "Tools number : " + tools.size( ) );
        tools.stream().map(t -> t.getId()).forEach(e -> logger.info("Tool available : "+e));

        logger.debug( "Search for  "+toolIdRequired );
        tools.stream().filter(t -> t.getId().equals(toolIdRequired)).forEach(tool -> lambdaRun(tool) );
    }

    /**
     * Function devant être remplacé par une lambda
     * @param tool outil à executer
     */
    static void lambdaRun(Tool tool){
        logger.info( "Use conf : " );
        logger.info( tool.getConf( ) );
        int status = tool.run( );
        logger.info( "Tool exit with status " + status );
    }
}
