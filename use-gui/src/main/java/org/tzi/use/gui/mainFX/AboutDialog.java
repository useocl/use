package org.tzi.use.gui.mainFX;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tzi.use.config.Options;
import org.tzi.use.main.gui.Main;

import java.util.Objects;

/**
 * About dialog.
 *
 * @author  Akif Aydin
 */
public class AboutDialog extends Stage {

    public AboutDialog(Stage owner) {
        super(StageStyle.UTILITY); // Remove decorations for a simpler dialog

        setTitle("About");

        HBox logoBox = new HBox();
        logoBox.setPadding(new javafx.geometry.Insets(0, 5, 0, 5));
        logoBox.getChildren().add(new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif")))));

        VBox infoBox = new VBox();
        infoBox.setPadding(new javafx.geometry.Insets(0, 5, 0, 5));
        Label titleLabel = new Label("USE - UML Based Specification Environment");
        titleLabel.setFont(Font.font(titleLabel.getFont().getName(), FontWeight.BOLD, titleLabel.getFont().getSize()));
        infoBox.getChildren().addAll(titleLabel,
                new Label(Options.COPYRIGHT),
                new Label("Version " + Options.RELEASE_VERSION),
                new Label("For more information see:"),
                new Label("http://www.db.informatik.uni-bremen.de/projects/USE/"),
                new Label(" "), // Spacer
                new Label("This program is free software; you can redistribute it and/or"),
                new Label("modify it under the terms of the GNU General Public License as"),
                new Label("published by the Free Software Foundation; either version 2 of the"),
                new Label("License, or (at your option) any later version."),
                new Label(" "), // Spacer
                new Label("This program is distributed in the hope that it will be useful, but"),
                new Label("WITHOUT ANY WARRANTY; without even the implied warranty of"),
                new Label("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE."),
                new Label("See the GNU General Public License for more details."));

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> close());
        //closeBtn.setAlignment(Pos.CENTER);

        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
        btnBox.setHgrow(closeBtn, Priority.ALWAYS);
        btnBox.getChildren().add(closeBtn);

        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
        contentPane.setLeft(logoBox);
        contentPane.setCenter(infoBox);
        contentPane.setBottom(btnBox);

        setScene(new Scene(contentPane));

        initOwner(owner); // Set owner for positioning relative to parent stage
        sizeToScene(); // Ensure size fits content
        centerOnScreen(); // Center the dialog on the screen
    }
    public static void main(String[] args){
        Application.launch(args);
    }

}



