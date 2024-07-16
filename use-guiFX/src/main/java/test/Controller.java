package test;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class Controller {

    @FXML
    private TextArea logTextArea; // Reference to the log text area in FXML
    @FXML
    private BorderPane borderPaneID; // Reference to the BorderPane
    @FXML
    private TextArea folderTreeTextDisplay; // Reference to the BorderPane Left side VBox Textarea: folderTreeTextDisplay

    public void initialize() {
        // Initialize and handle any controller-specific logic here
        logTextArea.setText("Log output will be displayed here.");
        logTextArea.setEditable(false);
        //borderPaneID.setPrefSize(842.0, 595.0); //borderPane Size
        folderTreeTextDisplay.setEditable(false);
        //folderTreeTextDisplay.setStyle("-fx-control-inner-background: #f0f0f0;"); // Optional: adjust background color to match the look
    }

}
