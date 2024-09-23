package org.tzi.use.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.tzi.use.config.Options;
import org.tzi.use.util.USEWriter;


public class SampleTestFX extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load the main window FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();

        // Set up the scene
        Scene scene = new Scene(root, 900.0, 620.0);

        //scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        // Set the stage properties
        primaryStage.setTitle("USE");
        primaryStage.getIcons().add(new Image("images/use1.gif"));
        primaryStage.setScene(scene);

        // Center the stage on the screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth() - scene.getWidth()) / 2;
        double centerY = primaryScreenBounds.getMinY() + (primaryScreenBounds.getHeight() - scene.getHeight()) / 2;
        primaryStage.setX(centerX);
        primaryStage.setY(centerY);

        // Show the stage
        primaryStage.show();

    }


    public static void main(String[] args) {

        // set System.out to the OldUSEWriter to protocol the output.
        System.setOut(USEWriter.getInstance().getOut());
        // set System.err to the OldUSEWriter to protocol the output.
        System.setErr(USEWriter.getInstance().getErr());

        // read and set global options, setup application properties
        Options.processArgs(args);

        // Launch the JavaFX application
        launch(args);
    }
}
