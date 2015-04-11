package tools.formengine.process;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import tools.formengine.bean.FormDefinition;


public final class FormParser
{
    /**
     * Private constructor for utility class
     */
    private FormParser( )
    {

    }

    /**
     * Parse any xml file into Form object
     * @param file
     * @return
     */
    public static FormDefinition parse( String path )
    {
        FormDefinition form = null;
        try
        {
            File file = new File( path );
            JAXBContext jaxbContext = JAXBContext.newInstance( FormDefinition.class );

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller( );
            form = (FormDefinition) jaxbUnmarshaller.unmarshal( file );
        }
        catch ( JAXBException e )
        {
            e.printStackTrace( );
        }

        return form;
    }
}
