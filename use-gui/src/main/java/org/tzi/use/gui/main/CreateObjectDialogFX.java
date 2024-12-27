/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.gui.main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import org.tzi.use.main.Session;
import org.tzi.use.main.gui.Main;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 
 * A dialog for creating objects.
 *  
 * @author  Mark Richters
 * @author  Lars Hamann
 */
@SuppressWarnings("serial")
class CreateObjectDialogFX extends Stage{
    private MSystem fSystem;
    private MainWindowFX fParent;
    private ObservableList<MClass> fClasses;
    private ListView<MClass> fListClasses;
    private TextField fTextObjectName;

    CreateObjectDialogFX(Session session, MainWindowFX parent) {
        fSystem = session.system();
        fParent = parent;

        session.addChangeListener(event -> Platform.runLater(this::close));

        setTitle("Create Object");
        initStyle(StageStyle.DECORATED); // only exit button visible on top right side
        setResizable(false);             // deactivate minimize/maximize feature
        initModality(Modality.APPLICATION_MODAL); // Make it a modal dialog (You cant interact with anything else until this window is closed)
        getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif")))); // same icon as the MainWindow

        // closes the stage
        setOnCloseRequest(windowEvent -> close());


        // TODO
        // filter classes (exclude MAssociationClass)
        List<MClass> classList = new ArrayList<>();
        for (MClass cls : fSystem.model().classes()) {
            if (!(cls instanceof MAssociationClass))
                classList.add(cls);
        }
        fClasses = FXCollections.observableArrayList(classList);

        // Class ListView
        fListClasses = new ListView<>(fClasses); // list view
        fListClasses.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // show one by one
        fListClasses.setPrefHeight(150);

        Label labelClasses = new Label("Select class:");
        labelClasses.setLabelFor(fListClasses);

        // create object name field and label
        fTextObjectName = new TextField();
        fTextObjectName.setPromptText("Enter object name");
        Label labelObjectName = new Label("Object name:");
        labelObjectName.setLabelFor(fTextObjectName);


        // create buttons
        Button btnCreate = new Button("Create");
        btnCreate.setOnAction(e -> {
            createObject();
        });

        Button btnCancel = new Button("Close");
        btnCancel.setOnAction(e -> {
            close();
        });

        HBox buttonbox = new HBox(10, btnCreate, btnCancel);
        buttonbox.setPadding(new Insets(10,0,0,0));

        //GridPane Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(labelClasses, 0, 0);
        grid.add(fListClasses, 0,1,2,1);

        grid.add(labelObjectName, 0, 2);
        grid.add(fTextObjectName, 1, 2);

        grid.add(buttonbox, 1, 3);
        GridPane.setHgrow(fTextObjectName, Priority.ALWAYS);

        // Set Scene
        Scene scene = new Scene(grid, 300, 300);
        setScene(scene);

        // Close on Escape Key
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                close();
            }
        });

    }

    /**
     * Creates a new object based on user input.
     * <p>
     * Validates the object name and class selection. If valid, it delegates the
     * creation of the object to the parent window and closes the dialog.
     * </p>
     */
    private void createObject() {
        // get object name
        String name = fTextObjectName.getText().trim();
        if (name.isEmpty()) {
            showAlert("You need to specify a name for the new object.");
            return;
        }

        // get class
        MClass cls = fListClasses.getSelectionModel().getSelectedItem();
        if ( cls == null ) {
            showAlert("You need to specify a class for the new object.");
            return;
        }

        // Call parent method to create the object
        fParent.createObject(cls, name);
    }

    /**
     * Displays an error alert with the given title and message.
     *
     * @param content the message displayed in the alert dialog
     */
    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif"))));
        alert.showAndWait();
    }
}

