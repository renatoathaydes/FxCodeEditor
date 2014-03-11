package com.athaydes.fxcodeeditor.util;

/**
 * @author Renato
 */
public class HtmlText {

    private String prefix;
    private String suffix;

    public static WithPrefix withPrefix( String prefix ) {
        return new WithPrefix( prefix );
    }

    private HtmlText( String prefix, String suffix ) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String withContent( String content ) {
        StringBuilder sb = new StringBuilder( prefix.length() + content.length() + suffix.length() );
        return sb.append( prefix ).append( content ).append( suffix ).toString();
    }

    public String extractContent( String wholeText ) {
        if ( wholeText.startsWith( prefix ) && wholeText.endsWith( suffix ) ) {
            return wholeText.substring( prefix.length(), wholeText.length() - suffix.length() );
        } else {
            throw new IllegalStateException( "Prefix and suffix do not match wholeText" );
        }
    }

    public static class WithPrefix {

        final String prefix;

        public WithPrefix( String prefix ) {
            this.prefix = prefix;
        }

        public HtmlText withSuffix( String suffix ) {
            return new HtmlText( prefix, suffix );
        }

    }

}
