package com.athaydes.fxcodeeditor.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Renato
 */
public class Config {

    private final Map<String, String> stylesByPattern;
    private final Map<Delimiter, String> delimiters;
    private String editorStyle;
    static final Pattern delimiterPattern = Pattern.compile( "^delimiter\\[(.+,.+)\\]$" );

    public Config( Map<String, String> propertiesMap ) {
        Map<String, String> stylesByPattern = new LinkedHashMap<>();
        Map<Delimiter, String> delimiters = new LinkedHashMap<>();
        for ( String property : propertiesMap.keySet() ) {
            Matcher matcher = delimiterPattern.matcher( property );
            if ( matcher.matches() ) {
                delimiters.put( delimiterFor( matcher ), propertiesMap.get( property ) );
            } else if ( property.equals( "editor.style" ) ) {
                editorStyle = propertiesMap.get( property );
            } else {
                stylesByPattern.put( property, propertiesMap.get( property ) );
            }
        }
        this.stylesByPattern = Collections.unmodifiableMap( stylesByPattern );
        this.delimiters = Collections.unmodifiableMap( delimiters );
    }

    private Delimiter delimiterFor( Matcher matcher ) {
        String property = matcher.group( 1 );
        String[] parts = property.split( "," );
        return new Delimiter( parts[ 0 ], parts[ 1 ] );
    }

    public Map<String, String> getStylesByPattern() {
        return stylesByPattern;
    }

    public Map<Delimiter, String> getDelimiters() {
        return delimiters;
    }

    public String getEditorStyle() {
        return editorStyle;
    }

}
