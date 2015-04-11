package tools.marker.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

import services.FileService;
import services.PropertiesService;

public class MarkerService {
	
	private static final Logger logger = Logger.getLogger( MarkerService.class );
	
	/**
     * For each filepath given, add a mark to recognize it
     * @param filePath the path to the file
     */
    public static void appendMark( String filePath )
    {
        FileWriter writer = null;
        String texteBegin = "<!-- " + PropertiesService.getProperty( "marker.mark" ) + " BEGIN " + filePath + " -->";
        String texteEND = "<!-- " + PropertiesService.getProperty( "marker.mark" ) + " END " + filePath + " -->";
        RandomAccessFile randomAccesFile = null;
        try
        {
        	FileService.insert( filePath, 0, texteBegin.getBytes( ) );
            writer = new FileWriter( filePath, true );
            writer.write( texteEND, 0, texteEND.length( ) );
        }
        catch ( IOException e )
        {
            logger.error(e);
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close( );
                }
                catch ( IOException e )
                {
                	logger.error(e);
                }
            }
            if ( randomAccesFile != null )
            {
                try
                {
                    randomAccesFile.close( );
                }
                catch ( IOException e )
                {
                	logger.error(e);
                }
            }
        }
    }

	public static String getFilePattern() {
		return PropertiesService.getProperty( "marker.templates.pattern" );
	}

	public static String getPathFiles() {
		return PropertiesService.getProperty( "marker.templates.path" );
	}
}
