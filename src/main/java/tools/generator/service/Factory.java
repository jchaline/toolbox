package tools.generator.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class Factory
{

    private Configuration cfg;

    public Factory( )
    {
        cfg = new Configuration( );

    }

    public String genSrc( Map<String, Object> model, String folderPath, String templateName )
    {
        Template template = null;
        try
        {
            cfg.setDirectoryForTemplateLoading( new File( folderPath ) );
            template = cfg.getTemplate( templateName );
        }
        catch ( IOException e )
        {
            e.printStackTrace( );
        }
        Writer stringBuffer = new StringWriter( );
        try
        {
            template.process( model, stringBuffer );
        }
        catch ( TemplateException e )
        {
            e.printStackTrace( );
        }
        catch ( IOException e )
        {
            e.printStackTrace( );
        }

        return stringBuffer.toString( );
    }

}
