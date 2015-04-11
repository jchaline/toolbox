package services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * Provide way to (un)serialize objects
 */
public class SerializableService
{

    private static final Logger logger = Logger.getLogger( SerializableService.class );

    /**
     * Serialize and save object to path
     * @param object the object to serialize
     * @param path the path
     */
    public static <T> void serialize( T object, String path )
    {
        try
        {
            FileOutputStream outputFile = new FileOutputStream( path );
            ObjectOutputStream oos = new ObjectOutputStream( outputFile );
            oos.writeObject( object );
            oos.flush( );
            oos.close( );
        }
        catch ( IOException e )
        {
            logger.error( "Error while create serializable file in " + path );
        }
    }

    /**
     * Load object from file path
     * @param path the path
     * @return the object
     */
    public static <T> T deserialize( String path )
    {
        T result = null;
        FileInputStream inputFile = null;
        try
        {
            inputFile = new FileInputStream( path );
            ObjectInputStream ois = new ObjectInputStream( inputFile );
            result = (T) ois.readObject( );
            ois.close( );
            inputFile.close( );
        }
        catch ( Exception e )
        {
            logger.error( e );
        }
        return result;
    }
}
