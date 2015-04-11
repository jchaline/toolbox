package tools.mapplugins;

import org.springframework.stereotype.Service;

import services.PropertiesService;
import tools.Tool;
import tools.mapplugins.bean.Repository;
import tools.mapplugins.commons.MappluginConstants;
import tools.mapplugins.service.RepositoryService;
import tools.mapplugins.service.SqlService;


@Service
public class MappluginTool implements Tool
{
	private RepositoryService _repositoryService = new RepositoryService();

    @Override
    public String getConf( )
    {
        StringBuilder builder = new StringBuilder( );
        builder.append( "SVN urls : " + PropertiesService.getProperty( MappluginConstants.MARK_SVN_URLS ) );
        builder.append( "SQL file : " + PropertiesService.getProperty( MappluginConstants.MARK_SQL_FILE ) );
        return builder.toString( );
    }

    @Override
    public int run( )
    {
    	boolean forceLoad = "true".equals(PropertiesService.getProperty(MappluginConstants.MARK_FORCE_LOAD));
    	boolean forceSave = "true".equals(PropertiesService.getProperty(MappluginConstants.MARK_FORCE_SAVE));
    	Repository repo = _repositoryService.loadRepo(forceLoad, forceSave);

        //derniere etape, generer le fichier SQL permettant de créer la bdd
        String sqlPath = PropertiesService.getProperty( MappluginConstants.MARK_SQL_FILE );
        return SqlService.generateSqlFile( repo, sqlPath );
    }

    @Override
    public String getId( )
    {
        return "mapplugin";
    }

    @Override
    public String getName( )
    {
        return "mapplugin";
    }
}
