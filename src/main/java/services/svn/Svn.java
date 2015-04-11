package services.svn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


/**
 * Svn : Classe de gestion du SVN a l\'aide de la librairie SVNKit
 * http://www.svnkit.com/
 * @author Valentin CARRUESCO (valentincarruesco@yahoo.fr)
 * @author Jeremy CHALINE (fork de la version de Valentin CARRUESCO)
 * @date 08/11/2011 11:48:24
 * @version 2.0
 */

public class Svn
{
    private static final Logger logger = Logger.getLogger( Svn.class );

    /**
     * Déclaration des attributs de la classe Svn
     */

    /**
     * Integer "id" de l'objet Svn courant
     */
    Integer id;
    /**
     * Chaine longue "url" de l'objet Svn courant
     */
    private String url;

    /**
     * Chaine courte "login" de l'objet Svn courant
     */
    private String login;

    /**
     * Chaine courte "password" de l'objet Svn courant
     */
    private String password;

    private SVNRepository repository;

    /**
     * Constructeur de la classe Svn pour une instanciation vide
     * @author Valentin CARRUESCO
     * @version 1
     */
    public Svn( )
    {
        this.repository = null;
    }

    /**
     * Constructeur de la classe avec initialisation des attributs
     * <b>url</b>,<b>login</b>,<b>password</b>
     * @author Valentin CARRUESCO
     * @version 1
     * @param url
     * @param login
     * @param password
     */
    public Svn( String url, String login, String password )
    {
        super( );
        this.url = url;
        this.login = login;
        this.password = password;
        this.repository = null;
    }

    /**
     * Se connecte et s'authentifie a un depot svn
     * @return
     */
    public boolean connect( )
    {
        boolean success = false;

        try
        {
            //Connexion
            repository = SVNRepositoryFactory.create( SVNURL.parseURIDecoded( this.url ) );
            //Authentification
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager( this.login,
                    this.password );
            this.repository.setAuthenticationManager( authManager );
            success = true;
        }
        catch ( SVNException e )
        {
            logger.error( "Impossible de se connecter/autentifier au SVN : " + e );
        }
        return success;
    }

    /************************/
    /** METHODES REECRITES **/
    /************************/

    /**
     * Cette méthode retourne un clone de l'objet courant
     * @author Valentin CARRUESCO
     * @version 1
     * @see java.lang.Object#clone()
     * @return<Svn> objet de type Svn cloné a partir de l'objet courant
     **/
    @Override
    protected Object clone( ) throws CloneNotSupportedException
    {
        return super.clone( );
    }

    /**
     * Cette méthode compare l'objet courant a l'objet passé en parametre et
     * retourne un bouléen
     * @author Valentin CARRUESCO
     * @version 1
     * @see java.lang.Object#equals(java.lang.Object)
     * @return true si les deux objets sont semblables, false dans les autres
     *         cas.
     **/
    @Override
    public boolean equals( Object arg0 )
    {
        return super.equals( arg0 );
    }

    /**
     * Retourne le toString de l'objet (chaine affaichant le contenu des
     * attributs de l'objet)
     * @author Valentin CARRUESCO
     * @version 1
     * @see java.lang.Object#toString()
     * @return<String> chaine representant le contenu de l'objet Svn courant
     **/
    @Override
    public String toString( )
    {
        String sortie = "==> Instance de la classe Svn : \n";
        sortie += "String url = " + this.getUrl( ) + " \n";
        sortie += "String login = " + this.getLogin( ) + " \n";
        sortie += "String password = " + this.getPassword( ) + " \n";

        return sortie;
    }

    /**
     * Vérifie un chemin et definis si c'ets un dossier, un fichier ou rien(si
     * il n'existe pas)
     * @param path
     * @return
     */
    public String check( String path )
    {
        String type = "UNKNOWN";
        SVNNodeKind nodeKind;
        try
        {
            nodeKind = this.repository.checkPath( path, -1 );
            if ( nodeKind == SVNNodeKind.NONE )
            {
                type = "EMPTY";
            }
            else if ( nodeKind == SVNNodeKind.FILE )
            {
                type = "FILE";
            }
            else if ( nodeKind == SVNNodeKind.DIR )
            {
                type = "FOLDER";
            }
            else if ( nodeKind == SVNNodeKind.UNKNOWN )
            {
                type = "UNKNOWN";
            }
        }
        catch ( SVNException e )
        {
            logger.error( "Impossible de verifier le chemin " + path + " : " + e );
        }

        return type;
    }

    /**
     * Liste un dossier du dépot
     * @param path dossier du dépot
     * @param deep the deep to search, stop at 0, boundless with infinite
     * @param filter the content filter, can be null
     * @throws SVNException
     */
    @SuppressWarnings( "rawtypes" )
    public List<SvnEntry> list( String path, int deep, SvnFilter filter ) throws SVNException
    {
        List<SvnEntry> tree = new ArrayList<SvnEntry>( );
        if ( "FOLDER".equalsIgnoreCase( this.check( path ) ) )
        {
            Collection entries = this.repository.getDir( path, -1, null, (Collection) null );
            logger.debug( "At " + url+path + ", found " + entries.size( ) + " entries" );
            Iterator iterator = entries.iterator( );
            while ( iterator.hasNext( ) )
            {
                SVNDirEntry entry = (SVNDirEntry) iterator.next( );

                //first filter, the entry type
                if ( checkName( filter, entry ) )
                {
                    SvnEntry svnEntry = new SvnEntry( );
                    svnEntry.setAuthor( entry.getName( ) );
                    svnEntry.setDate( entry.getDate( ) );
                    svnEntry.setLock( entry.getLock( ) );
                    svnEntry.setPath( ( path.equals( StringUtils.EMPTY ) ? StringUtils.EMPTY : path + "/" ) );
                    svnEntry.setRevision( entry.getRevision( ) );
                    svnEntry.setSize( entry.getSize( ) );

                    if ( entry.getKind( ) == SVNNodeKind.NONE )
                    {
                        svnEntry.setType( "EMPTY" );
                    }
                    else if ( entry.getKind( ) == SVNNodeKind.FILE )
                    {
                        svnEntry.setType( "FILE" );
                    }
                    else if ( entry.getKind( ) == SVNNodeKind.DIR )
                    {
                        svnEntry.setType( "FOLDER" );
                    }
                    else if ( entry.getKind( ) == SVNNodeKind.UNKNOWN )
                    {
                        svnEntry.setType( "UNKNOWN" );
                    }
                    svnEntry.setUrl( entry.getURL( ).toString( ) );
                    if ( checkType( filter, entry ) )
                    {
                        tree.add( svnEntry );
                    }

                    if ( ( deep > 0 || deep < 0 ) && entry.getKind( ) == SVNNodeKind.DIR )
                    {
                        List<SvnEntry> subEntries = this.list( ( path.equals( StringUtils.EMPTY ) ) ? entry.getName( )
                                : path + "/" + entry.getName( ), deep - 1, filter );
                        tree.addAll( subEntries );
                    }
                }
            }
        }
        else
        {
            logger.info( "Impossible de lister " + path + " car ce n'est pas un dossier" );
        }
        return tree;
    }

    /**
     * Check the name of the entry
     * @param filter the entry filter
     * @param entry the entry to check
     * @return true if the entry must be accept, false otherwise
     */
    private boolean checkName( SvnFilter filter, SVNDirEntry entry )
    {
        boolean accept = true;
        if ( filter != null )
        {
        	Iterator<String> itr = filter.getBlackList( ).iterator();
            while(accept && itr.hasNext()){
            	String next = itr.next();
				if(entry.getName().matches(next)){
            		accept = false;
            	}
            }
        }
        return accept;
    }

    /**
     * Check the type of the entry
     * @param filter the entry filter
     * @param entry the entry to check
     * @return true if the entry must be accept, false otherwise
     */
    private boolean checkType( SvnFilter filter, SVNDirEntry entry )
    {
        return filter == null || filter.getKind( ) == null || filter.getKind( ) == entry.getKind( );
    }

    /****************/
    /** ACCESSEURS **/
    /****************/

    /**
     * Retourne l'attribut id de l'objet Svn courant sous forme de Integer
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Getter)
     * @return<Integer> id de l'objet Svn courant
     */
    public Integer getId( )
    {
        return this.id;
    }

    /**
     * Definis l'attribut id de l'objet Svn courant
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Setter)
     * @param<Integer> id de l'objet Svn courant
     */
    public void setId( Integer id )
    {
        this.id = id;
    }

    /**
     * Retourne l'attribut url de l'objet Svn courant sous forme de String
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Getter)
     * @return<String> url de l'Svn courant
     */
    public String getUrl( )
    {
        if ( url == null )
            url = StringUtils.EMPTY;
        return url;
    }

    /**
     * Definis l'attribut url de l'objet Svn courant
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Setter)
     * @param<String> url
     */
    public void setUrl( String url )
    {
        this.url = url;
    }

    /**
     * Retourne l'attribut login de l'objet Svn courant sous forme de String
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Getter)
     * @return<String> login de l'Svn courant
     */
    public String getLogin( )
    {
        if ( login == null )
            login = StringUtils.EMPTY;
        return login;
    }

    /**
     * Definis l'attribut login de l'objet Svn courant
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Setter)
     * @param<String> login
     */
    public void setLogin( String login )
    {
        this.login = login;
    }

    /**
     * Retourne l'attribut password de l'objet Svn courant sous forme de String
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Getter)
     * @return<String> password de l'Svn courant
     */
    public String getPassword( )
    {
        if ( password == null )
            password = StringUtils.EMPTY;
        return password;
    }

    /**
     * Definis l'attribut password de l'objet Svn courant
     * @author Valentin CARRUESCO
     * @version 1
     * @category Accesseur(Setter)
     * @param<String> password
     */
    public void setPassword( String password )
    {
        this.password = password;
    }

    /**
     * Récupère la racine du dépot
     * @return racine du dépot
     */
    public String getRoot( )
    {
        String root = "Pas de racine";
        try
        {
            root = this.repository.getRepositoryRoot( true ).toString( );
        }
        catch ( SVNException e )
        {
            System.out.println( "Impossible de r�cuperer la racine:" + e );
        }
        return root;
    }

    /**
     * Récupère l'UID du dépot
     * @return UID du dépot
     */
    public String getUID( )
    {
        String uid = "Pas d'UID";
        try
        {
            uid = this.repository.getRepositoryUUID( true ).toString( );
        }
        catch ( SVNException e )
        {
            System.out.println( "Impossible de r�cuperer l'UID:" + e );
        }
        return uid;
    }

}
