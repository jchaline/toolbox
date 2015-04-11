package tools.mapplugins.service;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.tmatesoft.svn.core.SVNNodeKind;

import services.PropertiesService;
import services.svn.SvnFilter;
import tools.mapplugins.commons.MappluginConstants;

/**
 * @author jchaline
 */
public class SvnService
{
    /**
     * Get the project content from url
     * @param url the project pom.xml url
     * @return the string content
     * @throws IOException exception while getting the pom from url
     */
    public static String getProjectContent( String url ) throws IOException
    {
        Document pom = Jsoup.connect( url ).get( );
        
        String pomContent = pom.body( ).children( ).removeAttr( "xmlns" ).removeAttr( "xmlns:xsi" )
                .removeAttr( "xsi:schemalocation" ).toString( );

        return pomContent;
    }

    /**
     * Init the svn filter
     * @return the filter
     */
    public static SvnFilter getSvnFilter( )
    {
        SvnFilter filter = new SvnFilter( );
        String blackFolders = PropertiesService.getProperty( MappluginConstants.MARK_FOLDER_REFUSED );
        if ( StringUtils.isNotBlank( blackFolders ) )
        {
            for ( String folderName : blackFolders.split( MappluginConstants.PROPERTIES_SEPARATOR ) )
            {
                filter.getBlackList( ).add( folderName );
            }
        }
        filter.setKind( SVNNodeKind.FILE );
        return filter;
    }
}
