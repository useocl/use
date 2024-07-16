package test;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import org.apache.xerces.xs.StringList;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controller {

    @FXML
    private TextArea logTextArea; // Reference to the log text area in FXML
    @FXML
    private BorderPane borderPaneID; // Reference to the BorderPane
    @FXML
    private TextArea folderTreeTextDisplay; // Reference to the BorderPane Left side VBox Textarea: folderTreeTextDisplay

    @FXML
    private ToolBar toolBar; // Reference to the ToolBar

    public void initialize() {
        // Initialize and handle any controller-specific logic here
        logTextArea.setText("Log output will be displayed here.");
        logTextArea.setEditable(false);
        //borderPaneID.setPrefSize(842.0, 595.0); //borderPane Size
        folderTreeTextDisplay.setEditable(false);
        //folderTreeTextDisplay.setStyle("-fx-control-inner-background: #f0f0f0;"); // Optional: adjust background color to match the look

        Map<String, String> toolbarItems = new LinkedHashMap<>();
        toolbarItems.put("Open specification", "images/document-open.png");
        toolbarItems.put("Reload specification", "images/refresh.png");
        toolbarItems.put("Print diagram", "images/document-print.png");
        toolbarItems.put("Print view", "images/document-print.png");
        toolbarItems.put("Export content of view as PDF", "images/export_pdf.png");
        toolbarItems.put("Undo last statement", "images/undo.png");
        toolbarItems.put("Redo last undone statement", "images/redo.png");
        toolbarItems.put("Evaluate OCL expression", "images/OCL.gif");
        toolbarItems.put("Create class diagram view", "images/ClassDiagram.gif");
        toolbarItems.put("Create statemachine diagram view", "images/Diagram.gif");
        toolbarItems.put("Create object diagram view", "images/ObjectDiagram.gif");
        toolbarItems.put("Create class invariant view", "images/invariant-view.png");
        toolbarItems.put("Create object count view", "images/blue-chart-icon.png");
        toolbarItems.put("Create link count view", "images/blue-chart-icon.png");
        toolbarItems.put("Create state evolution view", "images/line-chart.png");
        toolbarItems.put("Create object properties view", "images/ObjectProperties.gif");
        toolbarItems.put("Create class extent view", "images/ClassExtentView.gif");
        toolbarItems.put("Create sequence diagram view", "images/SequenceDiagram.gif");
        toolbarItems.put("Create communication diagram view", "images/CommunicationDiagram.gif");
        toolbarItems.put("Create call stack view", "images/CallStack.gif");
        toolbarItems.put("Create command list view", "images/CmdList.gif");



        Integer i = 0;
        // Add buttons with images
        for (Map.Entry<String, String> entry : toolbarItems.entrySet()) {
            String tooltipText = entry.getKey();
            String imageKey = entry.getValue();
            Button button = new Button();
            button.setMaxSize(30,30);
            button.setGraphic(new ImageView(new Image(imageKey)));
            Tooltip tooltip = new Tooltip(tooltipText);
            button.setTooltip(tooltip);
            toolBar.getItems().add(button);

            // Add spacing between specific buttons
            if (i == 4 || i == 6 || i == 7) {
                Region spacer = new Region();
                spacer.setPrefWidth(10); // Adjust as needed
                toolBar.getItems().add(spacer);
            }
            i++;
        }
    }

}
