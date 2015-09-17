package launch;

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

        List<Tool> tools = SpringService.getBean( Tool.class );
        logger.debug( "Tools size : " + tools.size( ) );
        tools.stream().forEach(e -> logger.info("Tool available : "+e.getId()));
        
        final String toolId;
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
        
        logger.debug( "Search for  "+toolId );
        tools.stream().filter(t -> t.getId().equals(toolId)).findFirst().ifPresent(ToolLauncher::run);;
    }

    /**
     * @param tool outil à executer
     */
    static void run(Tool tool){
        logger.info( "Use conf : " );
        logger.info( tool.getConf( ) );
        int status = tool.run( );
        logger.info( "Tool exit with status " + status );
    }
}
