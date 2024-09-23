package org.tzi.use.gui.main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller for the Main.fxml file.
 *
 * @author Akif Aydin
 */
public class Controller {

    //@FXML
    private TextArea logTextArea, folderTreeTextDisplay; // Reference to the log text area in FXML
    //@FXML
    private ToolBar toolBar; // Reference to the ToolBar
    //@FXML
    private Menu fileMenuItems, editMenuItems, stateMenuItems, viewMenuItems, pluginsMenuItems, helpMenuItems;
    //@FXML
    private TreeView<String> folderTreeView;

    // Static variable to store the last selected directory path
    private static String specificationDir = System.getProperty("user.dir");
    private static List<String> specificationDirectorys = new ArrayList<>();


    public void initialize() {
        logTextArea.setText("Log output will be displayed here.");
        logTextArea.setEditable(false);

        folderTreeTextDisplay.setEditable(false);

        // initializing the modelBrowserTreeView/browser panel
        //ModelBrowserTest modelBrowserTest = new ModelBrowserTest(folderTreeView);
        //modelBrowserTest.updateFolderTree("No model available");

        // initializing the toolbar
        //initToolbarItems(toolBar, folderTreeView);

        // initialize the
        //initMenuBarItems(fileMenuItems, editMenuItems, stateMenuItems, viewMenuItems, pluginsMenuItems, helpMenuItems, folderTreeView);

    }

    /**
     * initializing the MenuBar
     */
/*    private static void initMenuBarItems(Menu fileMenuItems, Menu editMenuItems, Menu stateMenuItems, Menu viewMenuItems, Menu pluginsMenuItems, Menu helpMenuItems, TreeView<String> folderTreeView) {
        initFileMenuItems(fileMenuItems, folderTreeView);
        initEditMenuItems(editMenuItems);
        initStateMenuItems(stateMenuItems);
        initViewMenuItems(viewMenuItems);
        initPluginsMenuItems(pluginsMenuItems);
        initHelpMenuItems(helpMenuItems);
    }*/

/*    *//**
     * initializing the FileMenuItems
     *//*
    private static void initFileMenuItems(Menu fileMenuItems, TreeView<String> folderTreeView) {
        MenuItem openSpecification = new MenuItem("Open specification...");
        MenuItem openRecentSpecification = new MenuItem("Open recent specification");
        MenuItem saveScript = new MenuItem("Save script (.soil)...");
        MenuItem saveProtocol = new MenuItem("Save protocol...");
        MenuItem printerSetup = new MenuItem("Printer Setup...");
        MenuItem printDiagram = new MenuItem("Print diagram...");
        MenuItem printView = new MenuItem("Print View...");
        MenuItem exportAsPdf = new MenuItem("Export view as PDF...");
        MenuItem exit = new MenuItem("Exit");

        Image openImage = new Image("images/document-open.png");
        Image saveImage = new Image("images/save.png");
        Image printerImage = new Image("images/document-print.png");
        Image exportImage = new Image("images/export_pdf.png");

        openSpecification.setGraphic(new ImageView(openImage));
        openRecentSpecification.setGraphic(new ImageView(openImage));
        saveScript.setGraphic(new ImageView(saveImage));
        saveProtocol.setGraphic(new ImageView(saveImage));
        printerSetup.setGraphic(new ImageView(printerImage));
        printDiagram.setGraphic(new ImageView(printerImage));
        printView.setGraphic(new ImageView(printerImage));
        exportAsPdf.setGraphic(new ImageView(exportImage));

        openSpecification.setAccelerator(KeyCombination.valueOf("Ctrl+O"));
        exit.setAccelerator(KeyCombination.valueOf("Ctrl+Q"));
        openSpecification.setOnAction(e -> {
            System.out.println("Ctrl+O Succesfully pressed");
            openDirectoryChooser(folderTreeView);
        });
        exit.setOnAction(e -> {
            System.out.println("Ctrl+Q Succesfully pressed");
        });

        fileMenuItems.getItems().addAll(openSpecification, openRecentSpecification, saveScript, saveProtocol, printerSetup, printDiagram, printView, exportAsPdf, exit);
    }*/

    /**
     * This Methode has the Logic for the Filechooser of the folderTreeView
     */
    private static void openDirectoryChooser(TreeView<String> folderTreeView) {
        // Create a new FileChooser
        FileChooser fileChooser = new FileChooser();
        // Set the title for the FileChooser dialog
        fileChooser.setTitle("Open .use File");

        // Set the initial directory to a specific folder within your project
        if (specificationDir != null) {
            File initialDirectory = new File(specificationDir);
            if (initialDirectory.exists() && initialDirectory.isDirectory()) {
                fileChooser.setInitialDirectory(initialDirectory);
            }
        }

        // Add a file filter to show only .use files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("USE Files (*.use)", "*.use");
        fileChooser.getExtensionFilters().add(extFilter);
        //ModelBrowserTest modelBrowserFolderTree = new ModelBrowserTest(folderTreeView);

        File selectedDirectory = fileChooser.showOpenDialog(Stage.getWindows().get(0));

        if (selectedDirectory != null){
            specificationDir = selectedDirectory.getParent();
            specificationDirectorys.add(selectedDirectory.getPath());

            // Updating The browser panel
            //modelBrowserFolderTree.updateFolderTree(selectedDirectory, selectedDirectory.getName().replace(".use", ""));
        }
    }

    /**
     * initializing the EditMenuItems
     *//*
    private static void initEditMenuItems(Menu editMenuItems) {
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");

        Image undoImage = new Image("images/undo.png");
        Image redoImage = new Image("images/redo.png");

        undo.setGraphic(new ImageView(undoImage));
        redo.setGraphic(new ImageView(redoImage));

        undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undo.setOnAction(e -> {
            System.out.println("Ctrl+Z Succesfully pressed");
        });
        redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));
        redo.setOnAction(e -> {
            System.out.println("Ctrl+Shift+Z Succesfully pressed");
        });

        editMenuItems.getItems().addAll(undo, redo);
    }

    *//**
     * initializing the StateMenuItems
     *//*
    private static void initStateMenuItems(Menu stateMenuItems) {
        MenuItem createObject = new MenuItem("Create object...");
        MenuItem evaluateOCLexpr = new MenuItem("Evaluate OCL expression...");
        MenuItem checkSN = new MenuItem("Check structure now");
        MenuItem checkSAEC = new MenuItem("Check structure after every change");
        MenuItem checkSMT = new MenuItem("Check state machine transitions");
        MenuItem checkSIAEC = new MenuItem("Check state invariants after every change");
        MenuItem determine_states = new MenuItem("Determine states");
        MenuItem checkStateInvariants = new MenuItem("Check state invariants");
        MenuItem reset = new MenuItem("Reset");

        Image oclImage = new Image("images/OCL.gif");
        evaluateOCLexpr.setGraphic(new ImageView(oclImage));

        Image uncheckedImage = new Image("images/unchecked.png");
        ImageView imgViewUncheckedSAEC = new ImageView(uncheckedImage);
        imgViewUncheckedSAEC.setFitWidth(15);
        imgViewUncheckedSAEC.setFitHeight(15);
        checkSAEC.setGraphic(imgViewUncheckedSAEC);

        ImageView imgViewUncheckedSMT = new ImageView(uncheckedImage);
        imgViewUncheckedSMT.setFitWidth(15);
        imgViewUncheckedSMT.setFitHeight(15);
        checkSMT.setGraphic(imgViewUncheckedSMT);

        ImageView imgViewUncheckedSIAEC = new ImageView(uncheckedImage);
        imgViewUncheckedSIAEC.setFitWidth(15);
        imgViewUncheckedSIAEC.setFitHeight(15);
        checkSIAEC.setGraphic(imgViewUncheckedSIAEC);

        checkSAEC.setOnAction(e -> {
            checkAndUncheck(checkSAEC);
        });
        checkSMT.setOnAction(e -> {
            checkAndUncheck(checkSMT);
        });
        checkSIAEC.setOnAction(e -> {
            checkAndUncheck(checkSIAEC);
        });

        createObject.setAccelerator(KeyCombination.keyCombination("F7"));
        evaluateOCLexpr.setAccelerator(KeyCombination.keyCombination("F8"));
        checkSN.setAccelerator(KeyCombination.keyCombination("F9"));
        createObject.setOnAction(e -> {
            System.out.println("F7 Succesfully pressed");
        });
        evaluateOCLexpr.setOnAction(e -> {
            System.out.println("F8 Succesfully pressed");
        });
        checkSN.setOnAction(e -> {
            System.out.println("F9 Succesfully pressed");
        });

        stateMenuItems.getItems().addAll(createObject, evaluateOCLexpr, checkSN, checkSAEC, checkSMT, checkSIAEC, determine_states, checkStateInvariants, reset);
    }

    *//**
     * simulating a checkbox
     *//*
    private static void checkAndUncheck(MenuItem menuItem) {
        Image checkedImage = new Image("images/check.png");
        Image uncheckedImage = new Image("images/unchecked.png");
        ImageView imgViewUnchecked = new ImageView(uncheckedImage);
        ImageView imgViewChecked = new ImageView(checkedImage);
        imgViewUnchecked.setFitWidth(15);
        imgViewUnchecked.setFitHeight(15);
        imgViewChecked.setFitWidth(15);
        imgViewChecked.setFitHeight(15);
        ImageView currentGraphic = (ImageView) menuItem.getGraphic();

        if (currentGraphic.getImage().getUrl().equals(checkedImage.getUrl())) {
            menuItem.setGraphic(imgViewUnchecked); // Set to unchecked
        } else {
            menuItem.setGraphic(imgViewChecked); // Set to checked
        }
    }

    *//**
     * initializing the ViewMenuItems
     *//*
    private static void initViewMenuItems(Menu viewMenuItems) {
        Menu createView = new Menu("Create View");
        MenuItem tile = new MenuItem("Tile");
        MenuItem closeAll = new MenuItem("Close all");

        MenuItem createClassDiagramViewItem = new MenuItem("Class diagram", new ImageView("images/ClassDiagram.gif"));
        Menu createStateMachineDiagramViewItem = new Menu("State machine diagram", new ImageView("images/Diagram.gif"));
        MenuItem createObjectDiagramViewItem = new MenuItem("Object diagram ", new ImageView("images/ObjectDiagram.gif"));
        MenuItem createClassInvariantViewItem = new MenuItem("Class invariant", new ImageView("images/invariant-view.png"));
        MenuItem createObjectCountViewItem = new MenuItem("Object count", new ImageView("images/blue-chart-icon.png"));
        MenuItem createLinkCountViewItem = new MenuItem("Link count", new ImageView("images/red-chart-icon.png"));
        MenuItem createStateEvolutionViewItem = new MenuItem("State evolution", new ImageView("images/line-chart.png"));
        MenuItem createObjectPropertiesViewItem = new MenuItem("Object properties", new ImageView("images/ObjectProperties.gif"));
        MenuItem createClassExtentViewItem = new MenuItem("Class extent", new ImageView("images/ClassExtentView.gif"));
        MenuItem createSequenceDiagramViewItem = new MenuItem("Sequence diagram", new ImageView("images/SequenceDiagram.gif"));
        MenuItem createCommunicationDiagramViewItem = new MenuItem("Communication diagram", new ImageView("images/CommunicationDiagram.gif"));
        MenuItem createCallStackViewItem = new MenuItem("Call stack", new ImageView("images/CallStack.gif"));
        MenuItem createCommandListViewItem = new MenuItem("Command list ", new ImageView("images/CmdList.gif"));
        MenuItem associationEndsInformation = new MenuItem("Association ends informations ", new ImageView("images/CompositeAggregation.gif"));


        // Add MenuItems objects to the SubMenu
        createView.getItems().addAll(createClassDiagramViewItem, createStateMachineDiagramViewItem, createObjectDiagramViewItem, createClassInvariantViewItem, createObjectCountViewItem,
                createLinkCountViewItem, createStateEvolutionViewItem, createObjectPropertiesViewItem, createClassExtentViewItem, createSequenceDiagramViewItem, createCommunicationDiagramViewItem,
                createCallStackViewItem, createCommandListViewItem, associationEndsInformation);

        viewMenuItems.getItems().addAll(createView, tile, closeAll);
    }

    *//**
     * initializing the PluginMenuItems
     * TODO
     *//*
    private static void initPluginsMenuItems(Menu pluginMenuItems) {
        //Currently Empty
    }

    *//**
     * initializing the HelpMenuItems
     *//*
    private static void initHelpMenuItems(Menu helpMenuItems) {
        MenuItem about = new MenuItem("About...");

        about.setOnAction(e ->{
            Stage primaryStage = new Stage();
            AboutDialogFX dlg = new AboutDialogFX(primaryStage);
            dlg.showAndWait();
        });

        helpMenuItems.getItems().addAll(about);
    }*/

    /**
     * initializing the ToolbarItems
     *//*
    private static void initToolbarItems(ToolBar toolBar, TreeView<String> folderTreeView) {
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

        int i = 0;
        // Add buttons with images
        for (Map.Entry<String, String> entry : toolbarItems.entrySet()) {
            String tooltipText = entry.getKey();
            String imageKey = entry.getValue();
            Button button = new Button();
            button.setMaxSize(30, 30);
            button.setGraphic(new ImageView(new Image(imageKey)));
            Tooltip tooltip = new Tooltip(tooltipText);
            button.setTooltip(tooltip);
            toolBar.getItems().add(button);

            if (entry.getKey().equals("Open specification")) {
                button.setOnAction(e -> {
                    openDirectoryChooser(folderTreeView);

                });
            }

            // Add spacing between specific buttons
            if (i == 4 || i == 6 || i == 7) {
                Region spacer = new Region();
                spacer.setPrefWidth(10); // Adjust as needed
                toolBar.getItems().add(spacer);
            }
            i++;
        }

    }*/

}
