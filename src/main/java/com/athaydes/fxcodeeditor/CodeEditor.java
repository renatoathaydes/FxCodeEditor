package com.athaydes.fxcodeeditor;

import com.athaydes.fxcodeeditor.config.Config;
import com.athaydes.fxcodeeditor.config.Delimiter;
import com.athaydes.fxcodeeditor.config.ResourceLoader;
import com.athaydes.fxcodeeditor.util.CodeStyler;
import com.athaydes.fxcodeeditor.util.HtmlText;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Renato
 */
public class CodeEditor extends StackPane {

    private final WebView view = new WebView();
    private final WebView editor = new WebView();
    private CodeStyler styler = new CodeStyler();
    private ResourceLoader resourceLoader = new ResourceLoader();

    private final Map<String, String> stylesByPattern = new LinkedHashMap<>();
    private final Map<Delimiter, String> stylesByDelimiter = new LinkedHashMap<>();

    private HtmlText editorText;
    private HtmlText viewText;

    public CodeEditor() {
        super();
        updateConfig( "dark-java" );

        TextField field = new TextField();
        StackPane.setAlignment( field, Pos.BOTTOM_CENTER );
        field.setOnAction( ( event ) -> addStyle( field.getText() ) );

        getChildren().addAll( view, editor, field );
        editor.getEngine().loadContent( editorText.withContent( "" ) );
        view.getEngine().loadContent( viewText.withContent( "" ) );
        editor.setOpacity( 0.5 );
        editor.setOnKeyPressed( this::onKeyPressed );
        Platform.runLater( editor::requestFocus );
    }

    private void updateConfig( String theme ) {
        Config config = resourceLoader.getConfigWith( theme );
        stylesByPattern.putAll( config.getStylesByPattern() );
        stylesByDelimiter.putAll( config.getDelimiters() );
        editorText = HtmlText
                .withPrefix( "<head></head><body style=\"" + config.getEditorStyle() + "\" contenteditable=\"true\">" )
                .withSuffix( "</body>" );
        viewText = HtmlText
                .withPrefix( "<head></head><body style=\"" + config.getEditorStyle() + "\">" )
                .withSuffix( "</body>" );
    }

    private void addStyle( String text ) {
        String[] parts = text.split( "=" );
        if ( parts.length == 2 ) {
            System.out.println( "Adding style! " + Arrays.toString( parts ) );
            stylesByPattern.put( parts[ 0 ].trim(), parts[ 1 ].trim() );
            onKeyPressed( null );
        }
    }

    private void onKeyPressed( KeyEvent event ) {
        Platform.runLater( () -> view.getEngine().loadContent(
                viewText.withContent( styleHtmlText( getEditorHtmlText() ) ) ) );
    }

    private String styleHtmlText( String htmlText ) {
        return styler.ensureStyledWith( stylesByPattern, stylesByDelimiter, htmlText );
    }

    private String getEditorHtmlText() {
        return editorText.extractContent( editor.getEngine()
                .executeScript( "document.documentElement.innerHTML" )
                .toString() );
    }

}
