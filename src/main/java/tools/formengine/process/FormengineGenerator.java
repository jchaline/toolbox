package tools.formengine.process;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import services.FileService;
import services.PropertiesService;
import tools.formengine.bean.Choice;
import tools.formengine.bean.Field;
import tools.formengine.bean.Fields;
import tools.formengine.bean.FormDefinition;
import tools.formengine.bean.SubFormDefinition;
import tools.formengine.utils.DirectoryConstants;


/**
 * Transform Form object into SQL requests and properties files
 * TODO : gerer le cas des chexbox, select (input avec plusieurs valeurs
 * possibles) si le fichier de contenu est configurer (ie : génération des
 * fields correspondants)
 * @author jchaline
 * 
 */
public class FormengineGenerator
{

    /**
     * Mapping entre un type définit dans le XML et l'id représentatif pour
     * plugin-directory
     */
    private static final Map<String, Integer> entryId;

    static
    {
        Map<String, Integer> aMap = new HashMap<String, Integer>( );
        aMap.put( DirectoryConstants.TYPE_RADIO, 1 );
        aMap.put( DirectoryConstants.TYPE_CHECKBOX, 2 );
        aMap.put( DirectoryConstants.TYPE_COMBO, 5 );
        aMap.put( DirectoryConstants.TYPE_TEXT, 6 );
        aMap.put( DirectoryConstants.TYPE_TEXTAREA, 7 );
        aMap.put( DirectoryConstants.TYPE_UPLOAD, 13 );
        entryId = Collections.unmodifiableMap( aMap );
    }

    /**
     * Private constructor for utility class
     */
    private FormengineGenerator( )
    {

    }

    /**
     * Generate the files with SQL requets and properties components
     * @param form the form to generate
     * @param propertiesPath the properties file path
     * @param sqlPath the sql file path
     */
    public static void generate( FormDefinition form )
    {
        Integer entryPosition = 0;
        String idDirectory = PropertiesService.getProperty( DirectoryConstants.DIRECTORY_ID );
        //id de l'attribut mappé
        String strFirstIdEntry = PropertiesService.getProperty( DirectoryConstants.FIRST_ENTRY_ID );

        //id du champs representant le type d'attribut mappé
        String strFirstIdField = PropertiesService.getProperty( DirectoryConstants.FIRST_FIELD_ID );

        Integer currentIdField = Integer.valueOf( strFirstIdField );
        Integer currentIdEntry = Integer.valueOf( strFirstIdEntry );

        Integer currentIdParent = null;

        StringBuilder properties = new StringBuilder( );
        StringBuilder sql = new StringBuilder( );
        int indexFieldPos = 0;

        for ( SubFormDefinition subform : form.getSubforms( ) )
        {
            sql.append( getEntryParentSqllINE( idDirectory, subform.getTitle( ), currentIdEntry, entryPosition ) );
            entryPosition++;
            currentIdParent = currentIdEntry;
            currentIdEntry++;

            properties.append( "#" + subform.getName( ) + "\n" );

            if ( subform.getFields( ) != null )
            {
                for ( Fields fields : subform.getFields( ) )
                {
                    if ( fields.getFields( ) != null )
                    {
                        for ( Field field : fields.getFields( ) )
                        {
                            //avoid case without mandatory field
                            if ( mustCreateField( field ) )
                            {
                                setFieldValues( field, currentIdEntry, currentIdField, indexFieldPos++ );
                                String fieldSqllINE = getFieldSqllINE( currentIdField, currentIdEntry, field );
                                sql.append( fieldSqllINE );
                                currentIdField++;
                            }
                            // add attached field for radio or combo
                            else if ( field.getChoices( ) != null )
                            {
                                for ( Choice choice : field.getChoices( ) )
                                {
                                    ;
                                    Field fieldChoice = new Field( );
                                    fieldChoice.title = "'" + choice.getLabel( ) + "'";
                                    fieldChoice.default_value = "'" + choice.getValue( ) + "'";

                                    setFieldValues( fieldChoice, currentIdEntry, currentIdField, indexFieldPos++ );
                                    String fieldSqllINE = getFieldSqllINE( currentIdField, currentIdEntry, fieldChoice );
                                    sql.append( fieldSqllINE );
                                    currentIdField++;
                                }
                            }

                            properties
                                    .append( getEntryPropertyLine( form.getName( ), currentIdEntry, field.getName( ) ) );
                            sql.append( getEntrySqllINE( idDirectory, currentIdEntry, currentIdParent, field,
                                    entryPosition ) );

                            currentIdEntry++;
                            entryPosition++;
                        }
                    }
                }
            }
        }

        properties.append( getEntryPropertyLine( form.getName( ), currentIdEntry++, "id_demand" ) );
        properties.append( getEntryPropertyLine( form.getName( ), currentIdEntry++, "user_guid" ) );

        String pathProperties = PropertiesService.getProperty( DirectoryConstants.FILE_PROPERTIES );
        String pathSQL = PropertiesService.getProperty( DirectoryConstants.FILE_SQL );
        generateFile( pathProperties, properties );
        generateFile( pathSQL, sql );

    }

    /**
     * Check if we should create field for the entry
     * @param field the component to check
     * @return true to create field, false otherwise
     */
    private static boolean mustCreateField( Field field )
    {
        return !field.getType( ).equals( DirectoryConstants.TYPE_COMBO )
                && !field.getType( ).equals( DirectoryConstants.TYPE_RADIO );
    }

    /**
     * Affect defaults values for field element
     * @param field the field to create
     * @param currentIdEntry the parent entry id
     * @param currentIdField the field id
     * @param indexFieldPos the position for the field
     */
    private static void setFieldValues( Field field, Integer currentIdEntry, Integer currentIdField, int indexFieldPos )
    {
        //id_field, id_entry, title, DEFAULT_value, height, width, is_DEFAULT_value, max_size_enter, field_position, value_type_date, role_key, workgroup_key, is_shown_in_result_list, is_shown_in_result_record
        field.id_field = currentIdField;
        field.id_entry = currentIdEntry;
        field.field_position = indexFieldPos;

        field.height = 50;
        field.width = 50;
        field.is_default_value = 0;
        field.max_size_enter = 0;
        field.field_position = 1;
        field.is_shown_in_result_list = 0;
        field.is_shown_in_result_record = 0;
    }

    /**
     * Write into file the content of properties
     * @param pathFile
     * @param StringBuilder
     */
    private static void generateFile( String pathFile, StringBuilder content )
    {
    	FileService.write( pathFile, content.toString( ) );
    }

    /**
     * Generate the SQL line for the field
     * @param currentIdField
     * @param currentIdEntry
     * @param field
     * @return
     */
    private static String getFieldSqllINE( Integer currentIdField, Integer currentIdEntry, Field field )
    {
        //id_field, id_entry, title, DEFAULT_value, height, width, is_DEFAULT_value, max_size_enter, field_position, value_type_date, role_key, workgroup_key, is_shown_in_result_list, is_shown_in_result_record
        String sqlRequest = "INSERT INTO directory_field VALUES(" + field.id_field + "," + field.id_entry + ", "
                + ( field.title != null ? field.title : "NULL" ) + " ," + field.default_value + ", " + field.height
                + ", " + field.width + "," + field.is_default_value + "," + field.max_size_enter + ","
                + field.field_position + ",NULL,NULL,NULL," + field.is_shown_in_result_list + ","
                + field.is_shown_in_result_record + ");\n";
        return sqlRequest;
    }

    /**
     * Generate the SQL line for the field
     * @param currentIdField
     * @param currentIdEntry
     * @param position
     * @param field
     * @return
     */
    private static String getEntryParentSqllINE( String idDirectory, String subFormTitle, Integer currentIdEntry,
            Integer position )
    {
        String sqlRequest = "INSERT INTO directory_entry VALUES(" + currentIdEntry + ", NULL, " + idDirectory
                + ", 9, '" + StringEscapeUtils.escapeSql( subFormTitle ) + "', '', '', '', 0, 0, 0, 0, 0, 0, 1, 0, "
                + position + ", 0, 0, 0, 0, 0, 1, 0, NULL, 0, NULL, '', 0, 1, 0, NULL); \n";
        return sqlRequest;
    }

    /**
     * Generate the SQL line for the field
     * @param idDirectory
     * @param currentIdField the associate field
     * @param currentIdEntry the current entry id
     * @param currentIdParent the group parent id
     * @param fieldName
     * @param position
     * @return
     */
    private static String getEntrySqllINE( String idDirectory, Integer currentIdEntry, Integer currentIdParent,
            Field field, Integer position )
    {
        String sqlRequest = "INSERT INTO directory_entry VALUES(" + currentIdEntry + ", " + currentIdParent + ", "
                + idDirectory + ", " + getEntryType( field.getType( ) ) + ", '"
                + StringEscapeUtils.escapeSql( field.getLabel( ) ) + "', '', '', '', 0, 0, 0, 0, 0, 0, 1, 0, "
                + position + ", 0, 0, 0, 0, 0, 1, 0, NULL, 0, NULL, '', 0, 1, 0, NULL); \n";
        return sqlRequest;
    }

    /**
     * Get the entry ligne for the REST ws output file
     * @param formName the form name
     * @param currentId the current id
     * @param entryName the entry name
     * @return
     */
    private static String getEntryPropertyLine( String formName, Integer currentId, String entryName )
    {
        String line = "formengine-outputws.form." + formName + ".param.attribute." + entryName + "=" + currentId + "\n";
        return line;
    }

    private static int getEntryType( String strType )
    {
        int typeId = 0;
        if ( entryId.containsKey( strType ) )
        {
            typeId = entryId.get( strType );
        }
        else
        {
            System.err.println( "/!\\ Type inconnu : " + strType + " /!\\ " );
        }
        return typeId;
    }
}
