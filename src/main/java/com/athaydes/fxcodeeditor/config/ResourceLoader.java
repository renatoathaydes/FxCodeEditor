package com.athaydes.fxcodeeditor.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Renato
 */
public class ResourceLoader {

    public Config getConfigWith( String identifier ) {
        getResourceIdentifiers();
        Properties properties = new Properties();
        try {
            properties.load( getClass().getResourceAsStream(
                    "/code-editor/" + identifier + ".properties" ) );
        } catch ( IOException e ) {
            System.err.println( "CodeEditor ResourceLoader could not read resource: " + e );
        }
        Map<String, String> result = new LinkedHashMap<>();
        for ( String key : properties.stringPropertyNames() ) {
            result.put( key, properties.getProperty( key ) );
        }
        return new Config( result );
    }

    public String[] getResourceIdentifiers() {
        List<String> identifiers = new ArrayList<>( 5 );
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources( "code-editor" );
            while ( resources.hasMoreElements() ) {
                URL resource = resources.nextElement();
                if ( "file".equals( resource.getProtocol() ) ) {
                    File dir = new File( resource.getFile() );
                    for ( File f : Optional.ofNullable( dir.listFiles() ).orElse( new File[ 0 ] ) ) {
                        if ( f.getName().endsWith( ".properties" ) )
                            identifiers.add( f.getName().substring( 0, f.getName().length() - ".properties".length() ) );
                    }
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        System.out.println( "Code editor themes: " + identifiers );
        return identifiers.toArray( new String[ identifiers.size() ] );
    }

}
