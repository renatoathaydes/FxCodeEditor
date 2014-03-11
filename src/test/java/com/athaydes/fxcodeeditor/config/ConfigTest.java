package com.athaydes.fxcodeeditor.config;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Renato
 */
public class ConfigTest {

    @Test
    public void canLoadProperties() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put( "p1", "s1" );
        configMap.put( "p2", "s2" );
        Config config = new Config( configMap );
        assertThat( config.getStylesByPattern(), is( configMap ) );
    }

    @Test
    public void canLoadDelimiters() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put( "delimiter[',']", "s1" );
        configMap.put( "delimiter[<,>]", "s2" );
        Map<Delimiter, String> expected = new HashMap<>();
        expected.put( new Delimiter( "'", "'" ), "s1" );
        expected.put( new Delimiter( "<", ">" ), "s2" );
        Config config = new Config( configMap );
        assertThat( config.getDelimiters(), is( expected ) );
    }

}
