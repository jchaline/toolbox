package services.svn;

import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNNodeKind;


public class SvnFilter
{
    private SVNNodeKind kind;

    /**
     * The black list for the dir. If not empty, don't go into dir present in
     * this list
     */
    private List<String> blackList = new ArrayList<String>( );

    /**
     * @return the kind
     */
    public SVNNodeKind getKind( )
    {
        return kind;
    }

    /**
     * @param kind the kind to set
     */
    public void setKind( SVNNodeKind kind )
    {
        this.kind = kind;
    }

    /**
     * @return the blackList
     */
    public List<String> getBlackList( )
    {
        return blackList;
    }

    /**
     * @param blackList the blackList to set
     */
    public void setBlackList( List<String> blackList )
    {
        this.blackList = blackList;
    }
}
