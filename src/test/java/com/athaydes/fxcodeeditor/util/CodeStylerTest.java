package com.athaydes.fxcodeeditor.util;

import com.athaydes.fxcodeeditor.config.Config;
import com.athaydes.fxcodeeditor.config.Delimiter;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Renato
 */
public class CodeStylerTest {

    CodeStyler styler = new CodeStyler();

    static final Map<Delimiter, String> noDelimiters = Collections.emptyMap();
    static final Map<String, String> noStyles = Collections.emptyMap();

    @Test
    public void canRemoveStyle() {
        String result = styler.removeStyle( "Hi <span style=\"color:green\">there</span>", "there" );

        assertThat( result, is( "Hi there" ) );
    }

    @Test
    public void replacesKeywordWithStyledKeyword() {
        Map<String, String> styles = Collections.singletonMap( "keyword", "color:red" );

        String result = styler.ensureStyledWith( styles, noDelimiters, "This is <div> a keyword</div>" );

        assertThat( result, is( "This is <div> a <span style=\"color:red\">keyword</span></div>" ) );
    }

    @Test
    public void replacesSeveralKeywordsWithStyledKeywords() {
        Map<String, String> styles = new HashMap<>();
        styles.put( "public|out", "color:red" );
        styles.put( "void|int", "font-weight:bold" );

        String result = styler.ensureStyledWith( styles, noDelimiters,
                "public class Hello {" +
                        "  public void sayHi(String name) {" +
                        "    System.out.println(\"Hello \" + name);" +
                        "  }" +
                        "}" );

        assertThat( result, is( "<span style=\"color:red\">public</span> class Hello {" +
                "  <span style=\"color:red\">public</span> <span style=\"font-weight:bold\">void</span> sayHi(String name) {" +
                "    System.<span style=\"color:red\">out</span>.println(\"Hello \" + name);" +
                "  }" +
                "}" ) );
    }

    @Test
    public void doesNotReStyleKeyword() {
        Map<String, String> styles = Collections.singletonMap( "keyword", "color:red" );

        String result = styler.ensureStyledWith( styles, noDelimiters, "This is <div> a <span style=\"color:red\">keyword</span></div>" );

        assertThat( result, is( "This is <div> a <span style=\"color:red\">keyword</span></div>" ) );
    }

    @Test
    public void reStylesKeywordIfPreviousStyleWasDifferent() {
        Map<String, String> styles = Collections.singletonMap( "keyword", "color:red" );

        String result = styler.ensureStyledWith( styles, noDelimiters, "This is <div> a <span style=\"color:blue\">keyword</span></div>" );

        assertThat( result, is( "This is <div> a <span style=\"color:red\">keyword</span></div>" ) );
    }

    @Test
    public void stylesDelimitedText() {
        Map<String, String> styles = Collections.singletonMap( "delimiter[',']", "color:red" );
        Config config = new Config( styles );

        String result = styler.ensureStyledWith( noStyles, config.getDelimiters(),
                "This is a 'delimited text'" );

        assertThat( result, is( "This is a <span style=\"color:red\">'delimited text'</span>" ) );
    }

    @Test
    public void stylesDelimitedText2() {
        Map<String, String> styles = Collections.singletonMap( "delimiter[\",\"]", "color:red" );
        Config config = new Config( styles );

        String result = styler.ensureStyledWith( noStyles, config.getDelimiters(),
                "This is a \"delimited text\" and this is not. \"\"" );

        assertThat( result, is( "This is a <span style=\"color:red\">\"delimited text\"</span> and this is not. <span style=\"color:red\">\"\"</span>" ) );
    }

    @Test
    public void doesNotReStyleDelimitedText() {
        Map<String, String> styles = Collections.singletonMap( "delimiter[',']", "color:red" );
        Config config = new Config( styles );

        String result = styler.ensureStyledWith( noStyles, config.getDelimiters(),
                "This is a <span style=\"color:red\">'delimited text'</span>" );

        assertThat( result, is( "This is a <span style=\"color:red\">'delimited text'</span>" ) );
    }

    @Test
    public void reStylesDelimitedTextIfPreviousStyleWasDifferent() {
        Map<String, String> styles = Collections.singletonMap( "delimiter[',']", "color:red" );
        Config config = new Config( styles );

        String result = styler.ensureStyledWith( noStyles, config.getDelimiters(),
                "This is a <span style=\"color:blue\">'delimited text'</span>" );

        assertThat( result, is( "This is a <span style=\"color:red\">'delimited text'</span>" ) );
    }

}
