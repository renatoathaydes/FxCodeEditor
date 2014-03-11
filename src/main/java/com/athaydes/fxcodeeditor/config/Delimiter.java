package com.athaydes.fxcodeeditor.config;

/**
 * @author Renato
 */
public class Delimiter {

    private final String opening;
    private final String closing;

    public Delimiter( String opening, String closing ) {
        this.opening = opening;
        this.closing = closing;
    }

    public String getOpening() {
        return opening;
    }

    public String getClosing() {
        return closing;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Delimiter delimiter = ( Delimiter ) o;

        if ( !closing.equals( delimiter.closing ) ) return false;
        if ( !opening.equals( delimiter.opening ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = opening.hashCode();
        result = 31 * result + closing.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return opening + closing;
    }
}
