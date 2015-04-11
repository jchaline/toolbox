/*
 * Copyright (c) 2002-2010, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package tools.mapplugins.bean;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.log4j.Logger;

/**
 * 
 * @author jchaline
 * 
 */
public class ArtifactComparator implements Comparator<String>, Serializable
{
    private static final Logger LOGGER = Logger.getLogger( ArtifactComparator.class );

    private static final String REGEX_DOT = "\\.";
    private static final String REGEX_DASH = "-";
    private static final long serialVersionUID = 6330488276398145992L;

    /**
     * The Artifact compare method
     * @param version1 the first artifact
     * @param version2 the second artifact
     * @return -1, 0 ou 1
     */
    public int compare( String version1, String version2 )
    {
        return compareStatic( version1, version2 );
    }

    /**
     * The Artifact compare method
     * @param version1 the first artifact
     * @param version2 the second artifact
     * @return -1, 0 ou 1
     */
    public static int compareStatic( String version1, String version2 )
    {
        int compare = 0;
        String[] arrayVersion1 = version1.split( REGEX_DOT );
        String[] arrayVersion2 = version2.split( REGEX_DOT );

        if ( arrayVersion1.length == 3 && arrayVersion2.length == 3 )
        {
            if ( arrayVersion1[0].equals( arrayVersion2[0] ) )
            {
                if ( arrayVersion1[1].equals( arrayVersion2[1] ) )
                {
                    if ( !arrayVersion1[2].equals( arrayVersion2[2] ) )
                    {
                        //TODO : correction for -RCx version or FINAL, RELEASE, ... Actually, only work with SNAPSHOT key
                        String minor1 = arrayVersion1[2];
                        String minor2 = arrayVersion2[2];
                        String[] split1 = minor1.split( REGEX_DASH );
                        String[] split2 = minor2.split( REGEX_DASH );
                        compare = split1[0].compareTo( split2[0] );
                        if ( split1.length != split2.length && compare == 0 )
                        {
                            compare = split1.length > split2.length ? -1 : 1;
                        }
                    }
                }
                else
                {
                    compare = arrayVersion1[1].compareTo( arrayVersion2[1] );
                }
            }
            else
            {
                compare = ( arrayVersion1[0] ).compareTo( arrayVersion2[0] );
            }
        }
        else if ( arrayVersion1.length != 3 && arrayVersion2.length == 3 )
        {
            compare = -1;
        }
        else if ( arrayVersion1.length == 3 && arrayVersion2.length != 3 )
        {
            compare = 1;
        }
        
        LOGGER.debug("compare "+ version1 + " and " + version2 + " : " + compare);

		return compare;
	}
}
