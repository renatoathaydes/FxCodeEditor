package com.athaydes.fxcodeeditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

/**
 * @author Renato
 */
public class CodeEditorTest {

    public static class TestApp extends Application {


        @Override
        public void start( Stage stage ) throws Exception {
            Scene scene = new Scene( new CodeEditor(), 800, 400 );
            stage.setScene( scene );
            stage.centerOnScreen();
            stage.show();
        }
    }

    @Test
    public void testApp() {
        Application.launch( TestApp.class );
    }


}
