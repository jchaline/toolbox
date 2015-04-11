package tools.mapplugins.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.tmatesoft.svn.core.SVNException;

import services.FileService;
import services.PropertiesService;
import services.SerializableService;
import services.svn.Svn;
import services.svn.SvnEntry;
import services.svn.SvnFilter;
import tools.mapplugins.bean.Repository;
import tools.mapplugins.commons.MappluginConstants;
import tools.mapplugins.xml.Project;

public class RepositoryService {
	
    private static final String PATTERN_POM = ".*/pom\\.xml$";
	private static final String PREFIX_HTTP = "http";
	private static final Logger logger = Logger.getLogger( RepositoryService.class );
	
	public Repository parseUrl(String urls){
		Repository repo = new Repository();
		repo.addAll(updateRepository(urls));
		return repo;
	}
	
	public Repository loadRepo(boolean forceLoad, boolean save){
		Repository repo = null;
		
		String serializableFile = PropertiesService.getProperty( MappluginConstants.MARK_SERIALIZABLE_FILE );

        repo = SerializableService.deserialize( serializableFile );
        if ( repo == null || forceLoad)
        {
            String urlsConfig = PropertiesService.getProperty( MappluginConstants.MARK_SVN_URLS );
            repo = parseUrl(urlsConfig);
            MavenService.associateDependencies( repo );
            if(save){
            	SerializableService.serialize( repo, serializableFile );
            }
        }
        
		return repo;
	}

	/**
     * For all path configure, update repo with url or local folder/file
     * @param repo the repository to update
     * @param pathConfig the path configured
     */
	public List<Project> updateRepository(String pathConfig) {
		List<Project> projects = new ArrayList<Project>();
		String[] urls = pathConfig.split(";");
		for (String url : urls) {
			if (isUrl(url)) {
				projects.addAll(updateRepoWithSVNUrl(url));
			} else {
				projects.addAll(updateRepoWithLocalPath(url));
			}
		}
		return projects;
	}

    private boolean isUrl(String url) {
		return StringUtils.isNotBlank(url) && url.startsWith(PREFIX_HTTP);
	}

    /**
     * Check path (distant url or local path)
     * @param repo the repo to update
     * @param repoPath the path
     */
    private List<Project> updateRepoWithLocalPath(String repoPath) {
    	List<Project> projects = new ArrayList<Project>();
    	
    	List<String> listPaths = FileService.findFiles(-1, repoPath, PATTERN_POM);
    	logger.debug("Find "+listPaths.size()+" poms");
    	for(String pomPath : listPaths){
    		List<String> lines = FileService.read(pomPath);
    		String pomContent = StringUtils.join(lines, "\n");
    		pomContent = Jsoup.parse(pomContent).body( ).children( ).removeAttr( "xmlns" ).removeAttr( "xmlns:xsi" )
            .removeAttr( "xsi:schemalocation" ).toString( );
    		try {
    			projects.add(MavenService.getProject(pomContent));
			} catch (JAXBException e) {
				logger.error(e);
			}
    		logger.debug(pomContent.length());
    	}
    	return projects;
    }
	/**
     * Update repository with the given svn url
     * @param repo the repo to update
     * @param url the svn url
     */
	private List<Project> updateRepoWithSVNUrl(String url) {
		List<Project> projects = new ArrayList<Project>();
		
		Svn svn = new Svn( );
		svn.setUrl( url );
		if ( svn.connect( ) )
		{
		    try
		    {
		        SvnFilter filter = SvnService.getSvnFilter( );

		        //get all the pom.xml
		        List<SvnEntry> svnEntries = svn.list( "", -1, filter );
		        logger.info( svnEntries.size( ) + " elements founds at " + url );

		        for ( SvnEntry entry : svnEntries )
		        {
		            //pour chaque element, obtention et parsing du pom
		            Project unmarshal = null;
		            try
		            {
		            	String content = SvnService.getProjectContent( entry.getUrl( ) );
		                unmarshal = MavenService.getProject(content);
		                MavenService.correctProject( unmarshal );

		                //referencer l'artifact courant
		                projects.add( unmarshal );
		            }
		            catch ( IOException | JAXBException e )
		            {
		            	logger.error("Error with "+entry.getUrl());
		                logger.error( e );
		            }
		        }
		    }
		    catch ( SVNException e )
		    {
		        logger.error( e );
		    }
		}
		else
		{
		    logger.error( "Error with connection, can't connect to "+url );
		}
		return projects;
	}
}
