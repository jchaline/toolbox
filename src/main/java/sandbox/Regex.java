package sandbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Regex
{
    public static void main( String[] args )
    {
        String artifact = "\n plugin-chat \n";
        String strTest = "abcxxxxxxabcxxxxabc ";

        Pattern p = Pattern.compile( "abc" );
        Matcher m = p.matcher( strTest );

        System.out.println( "MATCHING : " + m.groupCount( ) );
        if ( m.groupCount( ) > 0 )
        {
            for ( int i = 0; i <= m.groupCount( ); i++ )
            {
                System.out.println( m.group( i ) );
            }
        }

        String result = artifact.replaceAll( "[\n ]", "" );
        System.out.println( artifact + " =>" + result );

    }
}
