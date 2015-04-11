package tools.generator.service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import services.FileService;
import services.PropertiesService;
import tools.generator.bean.Attribut;
import tools.generator.bean.Bean;
import tools.generator.bean.Plugin;
import tools.generator.utils.GeneratorConstants;


public class TemplateService
{

    private static final String PLUGIN_NAME = "(plugin.name)";
    private static final String PLUGIN_NAME_UP_FIRST = "(plugin.Name)";
    private static final String BEAN_NAME_UP_FIRST = "(bean.Name)";
    private static final String BEAN_NAME = "(bean.name)";
    private static final String MARK_PACKAGE = "package";
    private static final String MARK_BEAN = "bean";
    private static final String MARK_SPECIFIC_IMPORT = "specific_import";
    private Factory _factoryService = new Factory( );
    private String pathFolderWithTemplate;

    /**
     * The import know by the application, like Date, List, ... which can be use
     * with the bean.att file
     */
    private Properties _imports;
    private List<String> _beansName;

    /**
     * Determinate the name of the template file
     * @param templatePath the path to the template file
     * @return the name of the template
     */
    public String getTemplateName( String templatePath )
    {
        String name = "";

        // the "\\" string corresponding to the \ caractere, but this caractere must be escape with itself
        String[] split = templatePath.split( GeneratorConstants.PATH_SEPARATOR + GeneratorConstants.PATH_SEPARATOR );
        name = split[split.length - 1];
        return name;
    }

    /**
     * Determinate the path to the folder which contain the file to generate
     * @param templatePath the path to the template file
     * @return the path of the template's directory
     */
    public String getFolderPath( String templatePath )
    {
        String ret = "";

        String fileToRemoveFromPath = getTemplateName( templatePath );
        int indexOf = templatePath.indexOf( fileToRemoveFromPath );
        ret = templatePath.substring( 0, indexOf );

        return ret;
    }

    /**
     * Determinate if the template is for a bean or abstract plugin file
     * @param templateName the name of the template file
     * @return true if the template is for bean, false otherwise
     */
    public boolean isBeanTemplate( String templateName )
    {
        boolean isBean = templateName.contains( BEAN_NAME ) || templateName.contains( BEAN_NAME_UP_FIRST );

        return isBean;
    }

    /**
     * Get path to all template file
     * @param sourceTemplatesPath the directory with the tree of folder and
     *            templates file
     * @param pattern the pattern to match
     * @return the list of path to templates
     */
    public List<String> getTemplatesFiles( String sourceTemplatesPath )
    {
        List<String> listFiles = new LinkedList<String>( );

        Method method = getFilterMethodS( );

        if ( method != null )
        {
            try
            {
                findFiles( listFiles, sourceTemplatesPath, method );
            }
            catch ( IllegalAccessException e )
            {
                e.printStackTrace( );
            }
            catch ( IllegalArgumentException e )
            {
                e.printStackTrace( );
            }
            catch ( InvocationTargetException e )
            {
                e.printStackTrace( );
            }
        }

        return listFiles;
    }

    /**
     * Get the filter method to filter files
     * @return the filter method
     */
    private Method getFilterMethodS( )
    {

        //the filter method just take the name of the template which must be filtered
        Method method = null;
        try
        {
            method = TemplateService.class.getMethod( GeneratorConstants.NAME_FILTER_METHOD, String.class );
        }
        catch ( NoSuchMethodException e )
        {
            e.printStackTrace( );
        }
        catch ( SecurityException e )
        {
            e.printStackTrace( );
        }
        return method;
    }

    /**
     * For each template file, apply the correct rule to generate the plugin
     * files
     * @param plugin the plugin to generate
     * @param templates list of the templates to use
     * @return the list of file created
     */
    public List<String> applyRulesForTemplates( Plugin plugin, List<String> templates )
    {
        List<String> filesCreated = new LinkedList<String>( );
        for ( String template : templates )
        {
            List<String> files = applyRules( plugin, template );
            filesCreated.addAll( files );
        }
        return filesCreated;
    }

    /**
     * Create the files with the template given
     * @param plugin the plugin to generate
     * @param templatePath the template to use
     * @return the files created
     */
    public List<String> applyRules( Plugin plugin, String templatePath )
    {
        List<String> filesCreated = new LinkedList<String>( );

        // the model to use with the template for process generation
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( "plugin", plugin );

        // the path to the folder which contain the file to generate
        String pathToFileToGenerate = getPathToFileGenerate( templatePath );

        // the folder which contain the template
        String folderPathOfTheTemplate = getFolderPath( templatePath );

        // the template name
        String templateName = getTemplateName( templatePath );

        // check the real case : a bean file (one for each bean) or a plugin file (only one for the plugin)
        if ( isBeanTemplate( templateName ) )
        {
            for ( Bean bean : plugin.getBeanList( ) )
            {

                String packagePath = getPackagePath( pathToFileToGenerate );
                packagePath = correctBeanPath( plugin, bean, packagePath );
                model.put( MARK_PACKAGE, packagePath );
                // the path corrected (ex: plugin.name replace by the plugin's name, ...)
                String pathCorrect = correctBeanPath( plugin, bean, pathToFileToGenerate );

                //generate the folder with the file to generate
                FileService.makeFolder( pathCorrect );
                List<String> specificImport = getImport( bean );
                model.put( MARK_SPECIFIC_IMPORT, specificImport );

                model.put( MARK_BEAN, bean );
                String fileName = getFileName( templateName, bean.getName( ) );
                String genSrc = _factoryService.genSrc( model, folderPathOfTheTemplate, templateName );

                FileService.write( pathCorrect + fileName, genSrc );
                filesCreated.add( pathCorrect + fileName );
            }
        }
        else
        {
            String packagePath = getPackagePath( pathToFileToGenerate );
            packagePath = correctPluginPath( plugin, packagePath );
            model.put( MARK_PACKAGE, packagePath );

            // the path corrected (ex: plugin.name replace by the plugin's name, ...)
            pathToFileToGenerate = correctPluginPath( plugin, pathToFileToGenerate );

            //generate the folder with the file to generate
            FileService.makeFolder( pathToFileToGenerate );

            String fileName = getFileName( templateName, plugin.getName( ) );
            String genSrc = _factoryService.genSrc( model, folderPathOfTheTemplate, templateName );

            FileService.write( pathToFileToGenerate + fileName, genSrc );
            filesCreated.add( pathToFileToGenerate + GeneratorConstants.PATH_SEPARATOR + fileName );
        }

        //createFileInFolder(genSrc);

        return filesCreated;
    }

    public void makeFolderForPackage( Plugin plugin, String generatePath )
    {
        for ( String pack : plugin.getPackages( ) )
        {
        	FileService.makeFolder( generatePath + GeneratorConstants.PATH_SEPARATOR + pack );
        }
    }

    /**
     * Try to determinate the specific import of the bean like Date or other
     * bean
     * @param bean the bean to find import
     * @return the imports
     */
    private List<String> getImport( Bean bean )
    {
        List<String> imports = new ArrayList<String>( );
        String type = null;
        for ( Attribut a : bean.getAttributs( ).values( ) )
        {
            type = a.getType( );
            if ( PropertiesService.containsKey( type ) )
            {
                imports.add( PropertiesService.getProperty( type ) );
            }
        }
        return imports;
    }

    /**
     * Modify the path, replace char sequence with the real value
     * @param plugin the plugin to generate
     * @param bean the bean actually generated
     * @param pathToFileToGenerate the path to correct
     * @return the correct path
     */
    private String correctBeanPath( Plugin plugin, Bean bean, String pathToFileToGenerate )
    {
        String correctPath = correctPluginPath( plugin, pathToFileToGenerate );

        return correctPath.replace( BEAN_NAME, bean.getName( ) );
    }

    /**
     * Get the path of the package declaration for classes templates
     * @param pathToFileToFolder the path to the folder which contains the file
     *            created
     * @return the package declaration
     */
    private String getPackagePath( String pathToFileToFolder )
    {
        String srcFolder = PropertiesService.getProperty( GeneratorConstants.KEY_FOLDER_SRC );

        int indexSrc = pathToFileToFolder.indexOf( srcFolder );

        String result = pathToFileToFolder.substring( indexSrc + srcFolder.length( ) + 1,
                pathToFileToFolder.length( ) - 1 );
        result = result.replace( GeneratorConstants.PATH_SEPARATOR.charAt( 0 ), '.' );

        return result;
    }

    /**
     * Find the name of the generate file : keep the format orginal (the
     * upper/lowercase)
     * @param templateName the name of the template
     * @param replacement the string given for replacement
     * @return the correct name of the file
     */
    private String getFileName( String templateName, String replacement )
    {
        String fileName = "";
        String replacementCapFirst = capitalizeFirstLetter( replacement );

        if ( isBeanTemplate( templateName ) )
        {
            fileName = templateName.replace( BEAN_NAME_UP_FIRST, replacementCapFirst );
            fileName = fileName.replace( BEAN_NAME, replacement );
        }
        else
        {
            fileName = templateName.replace( PLUGIN_NAME_UP_FIRST, replacementCapFirst );
            fileName = fileName.replace( PLUGIN_NAME, replacement );
        }
        fileName = fileName.replace( ".ftl", "" );
        return fileName;
    }

    /**
     * Set the first letter of a string to upper case
     * @param str the string to modify
     * @return the string with first letter upper case
     */
    private String capitalizeFirstLetter( String str )
    {
        String replacementCorrect = "";
        if ( str.length( ) > 1 )
        {
            replacementCorrect = str.substring( 0, 1 ).toUpperCase( ) + str.substring( 1, str.length( ) );
        }
        else if ( str.length( ) == 1 )
        {
            replacementCorrect = str.substring( 0, 1 ).toUpperCase( );
        }
        return replacementCorrect;
    }

    /**
     * Modify the path, replace char sequence with the real value
     * @param plugin the plugin to generate
     * @param pathToFileToGenerate the path to correct
     * @return the correct path
     */
    private String correctPluginPath( Plugin plugin, String pathToFileToGenerate )
    {
        return pathToFileToGenerate.replace( PLUGIN_NAME, plugin.getName( ) );
    }

    /**
     * Get the path for the generated file with the template
     * @param templatePath the template path
     * @return the new path
     */
    private String getPathToFileGenerate( String templatePath )
    {
        String ret = "";

        String folderSource = PropertiesService.getProperty( GeneratorConstants.KEY_FOLDER_SOURCE );
        String folderGenerated = PropertiesService.getProperty( GeneratorConstants.KEY_FOLDER_TO_GENERATE );
        ret = templatePath.replace( folderSource, folderGenerated );

        ret = getFolderPath( ret );

        return ret;
    }

    /**
     * load all files which match pattern given
     * @param fileList the list to complete
     * @param directoryPath the path
     * @param filterMethod the method the filter the file
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void findFiles( List<String> fileList, String directoryPath, Method filterMethod )
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        if ( fileList != null )
        {
            File directory = new File( directoryPath );

            //si ce fichier n'existe pas
            if ( !directory.exists( ) )
            {
            }
            //si ce fichier n'est pas un repertoire
            else if ( !directory.isDirectory( ) )
            {
                String templateName = getTemplateName( directoryPath );
                if ( (Boolean) filterMethod.invoke( this, templateName ) )
                {
                    fileList.add( directoryPath );
                }
            }
            //si c'est un repertoire, appel recursif sur son contenu
            else
            {
                File[] subfiles = directory.listFiles( );
                for ( int i = 0; i < subfiles.length; i++ )
                {
                    String name = subfiles[i].getName( );
                    String path = directoryPath + GeneratorConstants.PATH_SEPARATOR + name;
                    findFiles( fileList, path, filterMethod );
                }
            }
        }
    }

    /**
     * Test the name given if it match the pattern
     * @param name the name to test
     * @param pattern the pattern to test
     * @return true if the pattern match, false otherwise
     */
    public boolean nameMatchPattern( String name )
    {
        boolean match = false;
        String pattern = PropertiesService.getProperty( GeneratorConstants.KEY_TEMPLATES_PATTERN );
        match = Pattern.matches( pattern, name );
        return match;
    }

    public String getPathFolderWithTemplate( )
    {
        return pathFolderWithTemplate;
    }

    public void setPathFolderWithTemplate( String pathFolderWithTemplate )
    {
        this.pathFolderWithTemplate = pathFolderWithTemplate;
    }

    /**
     * @return the beansName
     */
    public List<String> getBeansName( )
    {
        return _beansName;
    }

    /**
     * @param beansName the beansName to set
     */
    public void setBeansName( List<String> beansName )
    {
        this._beansName = beansName;
    }

    /**
     * @return the imports
     */
    public Properties getImports( )
    {
        return _imports;
    }

    /**
     * @param imports the imports to set
     */
    public void setImports( Properties imports )
    {
        this._imports = imports;
    }

}
