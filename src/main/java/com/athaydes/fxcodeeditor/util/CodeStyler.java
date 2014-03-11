package com.athaydes.fxcodeeditor.util;

import com.athaydes.fxcodeeditor.config.Delimiter;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Renato
 */
public class CodeStyler {

    public String ensureStyledWith( Map<String, String> styles,
                                    Map<Delimiter, String> delimiters,
                                    String text ) {
        for ( Map.Entry<Delimiter, String> entry : delimiters.entrySet() ) {
            Delimiter delimiter = entry.getKey();
            String style = entry.getValue();
            String pattern = delimiter.getOpening() + ".*?" + delimiter.getClosing();
            text = removeStyle( text, pattern );
            text = addStyle( text, pattern, style );
        }
        for ( Map.Entry<String, String> entry : styles.entrySet() ) {
            String pattern = entry.getKey();
            String style = entry.getValue();
            text = removeStyle( text, pattern );
            text = addStyle( text, "\\b(" + pattern + ")\\b", style );
        }
        return text;
    }

    private String addStyle( String text, String pattern, String style ) {
        Matcher matcher = Pattern.compile( pattern ).matcher( text );
        text = replaceMatches( matcher, text, match ->
                "<span style=\"" + style + "\">" + match + "</span>" );
        return text;
    }

    String removeStyle( String text, String pattern ) {
        pattern = "<span style.+>(" + pattern + ")</span>";
        Matcher matcher = Pattern.compile( pattern ).matcher( text );
        try {
            return replaceMatches( matcher, text, match ->
                    match.substring( match.indexOf( ">" ) + 1, match.lastIndexOf( "<" ) ) );
        } catch ( StringIndexOutOfBoundsException e ) {
            throw new RuntimeException( "Error matching pattern '" + pattern + "' on: " + text );
        }
    }

    private String replaceMatches( Matcher matcher, String text, Function<String, String> provideReplacement ) {
        StringBuffer buffer = new StringBuffer();
        while ( matcher.find() ) {
            String match = text.substring( matcher.start(), matcher.end() );
            matcher.appendReplacement( buffer, provideReplacement.apply( match ) );
        }
        matcher.appendTail( buffer );
        return buffer.toString();
    }

}
