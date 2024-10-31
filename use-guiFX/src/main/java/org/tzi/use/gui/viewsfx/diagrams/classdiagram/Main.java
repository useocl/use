package org.tzi.use.gui.viewsfx.diagrams.classdiagram;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public final class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Example");

        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create a label
        Label userLabel = new Label("User:");
        grid.add(userLabel, 0, 0);

        // Create a text field
        TextField userText = new TextField();
        grid.add(userText, 1, 0);

        // Create a button
        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 1);

        // Set the scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
