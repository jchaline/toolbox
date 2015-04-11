package tools.marker;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import services.FileService;
import services.PropertiesService;
import tools.Tool;
import tools.marker.service.MarkerService;
import utils.ToolsConstants;

@Service
public final class MarkerTool implements Tool
{
    private static final Logger logger = Logger.getLogger( MarkerTool.class );
    private static final String TOOL_ID = "marker";
    private static final String TOOL_NAME = "Injecteur de marque";

    @Override
    public int run( )
    {
        logger.info( "Run tool : " + getName( ) );
        PropertiesService.init( );
        String filePatternFilter = MarkerService.getFilePattern();
        String pathFiles = MarkerService.getPathFiles();

        //configuration test
        if ( StringUtils.isBlank( filePatternFilter ) || StringUtils.isBlank( pathFiles ) )
        {
            logger.error( "You must configure the pattern and the path for the file, into the marker.properties file" );
            return ToolsConstants.STATUS_ERROR;
        }

        //environnement test
        if ( !(new File( pathFiles ).exists( )) )
        {
            logger.error( pathFiles
                    + " isn't correct, you must set a valide path for the files, into marker.properties : marker.templates.path" );
            return ToolsConstants.STATUS_ERROR;
        }

        List<String> listFiles = FileService.findFiles( -1, pathFiles, filePatternFilter );

        logger.debug( "# files found : " + listFiles.size( ) );
        for ( String file : listFiles )
        {
            MarkerService.appendMark( file );
        }
        logger.debug( " Files appends. " );

        return ToolsConstants.STATUS_OK;
    }

    @Override
    public String getConf( )
    {
    	String filePatternFilter = MarkerService.getFilePattern();
        String pathFiles = MarkerService.getPathFiles();
        return "Use parttern : "+filePatternFilter+" and path : "+pathFiles;
    }

    @Override
    public String getId( )
    {
        return TOOL_ID;
    }

    @Override
    public String getName( )
    {
        return TOOL_NAME;
    }
}
