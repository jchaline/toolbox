package tools.formengine;

import org.springframework.stereotype.Service;

import services.PropertiesService;
import tools.Tool;
import tools.formengine.bean.FormDefinition;
import tools.formengine.process.FormParser;
import tools.formengine.process.FormengineGenerator;
import tools.formengine.utils.DirectoryConstants;
import utils.ToolsConstants;

@Service
public class FormEngineTool implements Tool
{
    private static final String TOOL_ID = "formengine";
    private static final String TOOL_NAME = "Generateur de config formengine";

    @Override
    public int run( )
    {
        String fileXml = PropertiesService.getProperty( DirectoryConstants.DIRECTORY_XML_FILE );

        FormDefinition form = FormParser.parse( fileXml );
        FormengineGenerator.generate( form );
        return ToolsConstants.STATUS_OK;
    }

    @Override
    public String getConf( )
    {
        // TODO Auto-generated method stub
        return null;
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
