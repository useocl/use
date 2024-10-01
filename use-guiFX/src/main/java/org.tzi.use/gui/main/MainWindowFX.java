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
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import org.kordamp.desktoppanefx.scene.layout.InternalWindow;
import org.kordamp.desktoppanefx.scene.layout.TaskBar;
import org.tzi.use.config.Options;

import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CommunicationDiagramView;

import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;

import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.runtime.gui.impl.PluginActionProxy;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;


import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.*;

/**
 * The main application window of USE.
 *
 * @author Akif Aydin
 */
@SuppressWarnings("serial")
public class MainWindowFX {
    private static Session fSession;
    private static IRuntime fPluginRuntime;
    private Stage primaryStage;  // Reference to the primary stage

    private static MainWindowFX instance;

    private static LogPanel fLogPanel;

    private static PrintWriter fLogWriter;

    @FXML
    private TextArea logTextArea;

    @FXML
    private VBox webViewPlaceholder; // Reference to the log text area in FXML

    @FXML
    private ToolBar toolBar; // Reference to the ToolBar

    @FXML
    private Menu fileMenuItems, editMenuItems, stateMenuItems, viewMenuItems, pluginsMenuItems, helpMenuItems;

    @FXML
    private TreeView<String> folderTreeView;

    @FXML
    private DesktopPane fDesktopPane;

    private static Menu recentFilesMenu;

    private ModelBrowserFX fModelBrowser;


    // Static variable to store the last selected directory path
    private static String specificationDir = System.getProperty("user.dir");

    private static boolean wasUsed;


    private final List<ClassDiagramView> classDiagrams = new ArrayList<ClassDiagramView>();
    private final List<NewObjectDiagramView> objectDiagrams = new ArrayList<NewObjectDiagramView>();
    private final List<CommunicationDiagramView> communicationDiagrams = new ArrayList<CommunicationDiagramView>();

    private static final String DEFAULT_UNDO_TEXT = "Undo last statement";
    private static final String DEFAULT_REDO_TEXT = "Redo last undone statement";

    private static final String STATE_EVAL_OCL = "Evaluate OCL expression";

    private static Path ffileName;


    private Map<Map<String, String>, PluginActionProxy> pluginActions =
            new HashMap<Map<String, String>, PluginActionProxy>();


    public MainWindowFX() {
        instance = this;
    }

    @FXML
    public void initialize() throws IOException {
        initWebViewPlaceholder();
        initDesktopPane();
        initLogTextArea(); // create the log panel
        initFolderTreeView();

        Options.getRecentFiles().getItems().clear();
        fSession = getSession();
        fPluginRuntime = getPluginRuntime();

        //fModelBrowser = new ModelBrowserFX(fSession.system().model(), folderTreeView, fPluginRuntime);

        if (fSession != null && fSession.hasSystem()) {
            fModelBrowser.setModel(fSession.system().model());
        }

        fLogPanel = new LogPanel();
        fLogWriter = new PrintWriter(new TextComponentWriter(fLogPanel.getTextComponent()), true);

        // initializing the toolbar
        initToolbarItems(toolBar, folderTreeView);

        // initializing the menubar
        initMenuBarItems(fileMenuItems, editMenuItems, stateMenuItems, viewMenuItems, pluginsMenuItems, helpMenuItems, folderTreeView);


        // initialize application state to current system

        sessionChanged();


    }

    /**
     * Set application state for new system. The system parameter may be null.
     */
    void sessionChanged() {

        boolean on = fSession.hasSystem();
        if (on) {
            MSystem system = fSession.system();
            fModelBrowser.setModel(system.model());
            primaryStage.setTitle("USE: " + new File(system.model().filename()).getName());
        }
    }

    /**
     * initializing the MenuBar
     */
    private void initMenuBarItems(Menu fileMenuItems, Menu editMenuItems, Menu stateMenuItems, Menu viewMenuItems, Menu pluginsMenuItems, Menu helpMenuItems, TreeView<String> folderTreeView) {
        initFileMenuItems(fileMenuItems, folderTreeView);
        initEditMenuItems(editMenuItems);
        initStateMenuItems(stateMenuItems);
        initViewMenuItems(viewMenuItems);
        initPluginsMenuItems(pluginsMenuItems);
        initHelpMenuItems(helpMenuItems);
    }

    /**
     * initializing the FileMenuItems
     */
    private void initFileMenuItems(Menu fileMenuItems, TreeView<String> folderTreeView) {
        MenuItem openSpecification = new MenuItem("Open specification...");
        recentFilesMenu = new Menu("Open recent specification");
        MenuItem saveScript = new MenuItem("Save script (.soil)...");
        MenuItem saveProtocol = new MenuItem("Save protocol...");
        MenuItem printerSetup = new MenuItem("Printer Setup...");
        MenuItem printDiagram = new MenuItem("Print diagram...");
        MenuItem printView = new MenuItem("Print View...");
        MenuItem exportAsPdf = new MenuItem("Export view as PDF...");
        MenuItem exit = new MenuItem("Exit");

        openSpecification.setGraphic(getIcon("document-open.png"));
        recentFilesMenu.setGraphic(getIcon("document-open.png"));
        saveScript.setGraphic(getIcon("save.png"));
        saveProtocol.setGraphic(getIcon("save.png"));
        printerSetup.setGraphic(getIcon("document-print.png"));
        printDiagram.setGraphic(getIcon("document-print.png"));
        printView.setGraphic(getIcon("document-print.png"));
        exportAsPdf.setGraphic(getIcon("export_pdf.png"));

        openSpecification.setAccelerator(KeyCombination.valueOf("Ctrl+O"));
        exit.setAccelerator(KeyCombination.valueOf("Ctrl+Q"));

        openSpecification.setOnAction(e -> {
            instance.openDirectoryChooser(folderTreeView);
        });

        exit.setOnAction(e -> {
            System.out.println("Ctrl+Q Succesfully pressed");
        });

        fileMenuItems.getItems().addAll(openSpecification, recentFilesMenu, saveScript, saveProtocol, printerSetup, printDiagram, printView, exportAsPdf, exit);
    }

    /**
     * initializing the EditMenuItems
     */
    private void initEditMenuItems(Menu editMenuItems) {
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

    /**
     * initializing the StateMenuItems
     */
    private void initStateMenuItems(Menu stateMenuItems) {
        MenuItem createObject = new MenuItem("Create object...");
        MenuItem evaluateOCLexpr = new MenuItem("Evaluate OCL expression...");
        MenuItem checkSN = new MenuItem("Check structure now");
        CheckMenuItem checkSAEC = new CheckMenuItem("Check structure after every change");
        CheckMenuItem checkSMT = new CheckMenuItem("Check state machine transitions");
        CheckMenuItem checkSIAEC = new CheckMenuItem("Check state invariants after every change");
        MenuItem determine_states = new MenuItem("Determine states");
        MenuItem checkStateInvariants = new MenuItem("Check state invariants");
        MenuItem reset = new MenuItem("Reset");

        Image oclImage = new Image("images/OCL.gif");
        evaluateOCLexpr.setGraphic(new ImageView(oclImage));

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

    /**
     * initializing the ViewMenuItems
     */
    private void initViewMenuItems(Menu viewMenuItems) {
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

    /**
     * initializing the PluginMenuItems
     * TODO
     */
    private void initPluginsMenuItems(Menu pluginMenuItems) {
        //Currently Empty
    }

    /**
     * initializing the HelpMenuItems
     */
    private void initHelpMenuItems(Menu helpMenuItems) {
        MenuItem about = new MenuItem("About...");

        about.setOnAction(e -> {
            Stage primaryStage = new Stage();
            AboutDialogFX dlg = new AboutDialogFX(primaryStage);
            dlg.showAndWait();
        });

        helpMenuItems.getItems().addAll(about);
    }

    /**
     * initializing the ToolbarItems
     */
    private void initToolbarItems(ToolBar toolBar, TreeView<String> folderTreeView) {
        Map<String, ImageView> toolbarItems = new LinkedHashMap<>();
        toolbarItems.put("Open specification", getIcon("document-open.png"));
        toolbarItems.put("Reload specification", getIcon("refresh.png"));
        toolbarItems.put("Print diagram", getIcon("document-print.png"));
        toolbarItems.put("Print view", getIcon("document-print.png"));
        toolbarItems.put("Export content of view as PDF", getIcon("export_pdf.png"));
        toolbarItems.put("Undo last statement", getIcon("undo.png"));
        toolbarItems.put("Redo last undone statement", getIcon("redo.png"));
        toolbarItems.put("Evaluate OCL expression", getIcon("OCL.gif"));
        toolbarItems.put("Create class diagram view", getIcon("ClassDiagram.gif"));
        toolbarItems.put("Create statemachine diagram view", getIcon("Diagram.gif"));
        toolbarItems.put("Create object diagram view", getIcon("ObjectDiagram.gif"));
        toolbarItems.put("Create class invariant view", getIcon("invariant-view.png"));
        toolbarItems.put("Create object count view", getIcon("blue-chart-icon.png"));
        toolbarItems.put("Create link count view", getIcon("blue-chart-icon.png"));
        toolbarItems.put("Create state evolution view", getIcon("line-chart.png"));
        toolbarItems.put("Create object properties view", getIcon("ObjectProperties.gif"));
        toolbarItems.put("Create class extent view", getIcon("ClassExtentView.gif"));
        toolbarItems.put("Create sequence diagram view", getIcon("SequenceDiagram.gif"));
        toolbarItems.put("Create communication diagram view", getIcon("CommunicationDiagram.gif"));
        toolbarItems.put("Create call stack view", getIcon("CallStack.gif"));
        toolbarItems.put("Create command list view", getIcon("CmdList.gif"));

        int i = 0;
        // Add buttons with images
        for (Map.Entry<String, ImageView> entry : toolbarItems.entrySet()) {
            String tooltipText = entry.getKey();
            ImageView imageKey = entry.getValue();
            Button button = new Button();
            button.setMaxSize(30, 30);
            button.setGraphic(imageKey);
            Tooltip tooltip = new Tooltip(tooltipText);
            button.setTooltip(tooltip);
            toolBar.getItems().add(button);

            switch (entry.getKey()) {
                case "Open specification" -> button.setOnAction(e -> {
                    instance.openDirectoryChooser(folderTreeView);
                });
//                case "Reload specification" -> button.setOnAction(e -> {
//                    openDirectoryChooser(folderTreeView);
//                });

                //TODO
            }

            if (entry.getKey().equals("Open specification")) {
                button.setOnAction(e -> {
                    instance.openDirectoryChooser(folderTreeView);
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

    }

    /**
     * This Methode has the Logic for the Filechooser of the folderTreeView
     */
    private void openDirectoryChooser(TreeView<String> folderTreeView) {

        if (!validateOpenPossible()) return;

        // Create a new FileChooser
        FileChooser fChooser = new FileChooser();
        // Set the title for the FileChooser dialog
        fChooser.setTitle("Open .use File");

        // Set the initial directory to a specific folder within your project
        if (specificationDir != null) {
            File initialDirectory = new File(specificationDir);
            if (initialDirectory.exists() && initialDirectory.isDirectory()) {
                fChooser.setInitialDirectory(initialDirectory);
            }
        }

        // Add a file filter to show only .use files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("USE Files (*.use)", "*.use");
        fChooser.getExtensionFilters().add(extFilter);
        //fChooser.setTitle("Open specification");

        if (wasUsed) {
            File recentFile = Objects.requireNonNull(Options.getRecentFile("use")).toFile();
            fChooser.setInitialFileName(recentFile.getName());
        }

        //This code shows an "Open" dialog, anchored to the first window of the JavaFX application, and captures the file the user selects.
        File selectedDirectory = fChooser.showOpenDialog(Stage.getWindows().get(0));

        if (selectedDirectory != null) {
            specificationDir = selectedDirectory.getParent();

            Path path = Path.of(selectedDirectory.getPath());
            Options.setLastDirectory(path.getParent());

            instance.compile(path);

            Options.getRecentFiles().push(path.toAbsolutePath().toString());

            if (fSession.system() != null && fSession.system().model() != null) {
                ModelBrowserFX modelBrowserFX = new ModelBrowserFX(fSession.system().model(), fPluginRuntime);
                //Platform.runLater(() -> logTextArea.clear());
            } else {
                ModelBrowserFX modelBrowserFX = new ModelBrowserFX(null, fPluginRuntime);
            }

            wasUsed = true;

            setRecentfiles();

            // Updating The browser panel //TODO DELETE AFTER TEST
            //modelBrowserFolderTree.updateFolderTree(selectedDirectory, selectedDirectory.getName().replace(".use", ""));
            //ModelBrowserFX.fillTreeView();

        }
    }

    // Actions

    public MenuItem ActionFileOpenSpecRecent(Path filename) {
        MenuItem recentSpec = new MenuItem();
        recentSpec.setText("");
        //ffileName = filename;
        if (!validateOpenPossible()) {
            return recentSpec;
        } else {
            //instance.compile(filename);
            recentSpec.setText(filename.toString());
            return recentSpec;
        }
    }


    private boolean validateOpenPossible() {
        if (fSession != null) {
            if (fSession.hasSystem() && fSession.system().isExecutingStatement()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "The system is currently executing a statement.\nPlease end the execution before opening a new model.",
                        ButtonType.OK);
                alert.setTitle("USE is executing");
                alert.showAndWait();
                return false;
            } else {
                return true;
            }
        }
        return true;

    }

    protected boolean compile(final Path f) {
        //fLogPanel.clear();
        logTextArea.clear();


        MModel model = null;
        try (InputStream iStream = Files.newInputStream(f)) {
            model = USECompiler.compileSpecification(iStream, f.toAbsolutePath().toString(),
                    fLogWriter, new ModelFactory());
            logTextArea.appendText("compiling specification " + f + "...\n");
            logTextArea.appendText("done.\n");
        } catch (IOException ex) {
            logTextArea.appendText("File `" + f.toAbsolutePath().toString() + "' not found.\n");
        }

        final MSystem system;
        if (model != null) {
            MModel finalModel = model;
            logTextArea.appendText(finalModel.getStats() + "\n");
            // create system
            system = new MSystem(model);
        } else {
            system = null;
        }

//        // set new system (may be null if compilation failed)
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                fSession.setSystem(system);
//            }
//        });

        if (system != null) {
            fSession.setSystem(system);  // System synchron setzen, da keine GUI-Operation erforderlich ist
            Options.getRecentFiles().push(f.toString());
            Options.setLastDirectory(f.getParent());
            return true;
        } else {
            return false;
        }
    }

    // Methode zur Überwachung der Logdatei auf Änderungen
    private void watchLogFile(Path logFilePath) {
        new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                logFilePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY &&
                                event.context().toString().equals(logFilePath.getFileName().toString())) {

                            // Lesen des geänderten Inhalts
                            String updatedText = new String(Files.readAllBytes(logFilePath));
                            // Aktualisieren der TextArea im JavaFX-Thread
                            Platform.runLater(() -> logTextArea.appendText(updatedText + "\n"));
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Simuliert einen LogWriter, der regelmäßig neue Log-Einträge schreibt
    private void simulateLogWriter(File logFile) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            for (int i = 1; i <= 10; i++) {
                writer.write("Log-Eintrag " + i + "\n");
                writer.flush();
                Thread.sleep(2000); // 2 Sekunden warten, bevor ein neuer Eintrag kommt
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateTreeView(TreeItem<String> root) {
        folderTreeView.setRoot(root);
    }

    public void initWebViewPlaceholder() {
        WebView webView = new WebView();
        webView.setVisible(true);
        webView.setContextMenuEnabled(false);
        //webView.getEngine().loadContent("test");
        // Füge das WebView in die VBox (webViewPlaceholder) ein
        webViewPlaceholder.getChildren().add(webView);
    }

    public void initDesktopPane(){
        //fDesktopPane = new DesktopPane();
        // Create a custom internal window (for demonstration)
        // Create InternalWindows with proper constructor
        fDesktopPane.getTaskBar().setPosition(TaskBar.Position.TOP);
        InternalWindow internalWindow1 = createInternalWindow("Window1", "Window 1", 100, 100);
        InternalWindow internalWindow2 = createInternalWindow("Window2", "Window 2", 300, 150);
        // Manage window behavior, such as positioning
        fDesktopPane.addInternalWindow(internalWindow1); // Position at (100, 100)
        fDesktopPane.addInternalWindow(internalWindow2); // Position at (300, 150)


        //fDesktopPane.s
    }

    public void initLogTextArea(){

        // Erstelle das Kontextmenü
        ContextMenu contextMenu = new ContextMenu();

        // Füge die "Clear" Option hinzu
        MenuItem clearItem = new MenuItem("Clear");
        clearItem.setOnAction(event -> logTextArea.clear());

        // Kontextmenü zur TextArea hinzufügen
        contextMenu.getItems().addAll(clearItem);

        // Setze das Kontextmenü in der TextArea
        logTextArea.setContextMenu(contextMenu);

        logTextArea.setEditable(false);

    }

    /**
     * Initializes the FolderTreeView (WebViews Right Click Context Menu)
     */
    public void initFolderTreeView(){

        TreeItem<String> rootItem = new TreeItem<>("No model available");
        folderTreeView.setRoot(rootItem);

        // Verwende die neue Klasse, um das Kontextmenü zu erstellen
        FolderTreeContextMenuFX contextMenuHandler = new FolderTreeContextMenuFX(fSession, fPluginRuntime);
        ContextMenu contextMenu = contextMenuHandler.createContextMenu();

        folderTreeView.setContextMenu(contextMenu);

    }

    // Create a method that creates an InternalWindow using the correct constructor
    private InternalWindow createInternalWindow(String id, String title, double x, double y) {
        // Create content for the InternalWindow (can be anything like a VBox, etc.)
        VBox content = new VBox(new Label(title));
        content.setStyle("-fx-background-color: lightgray; -fx-padding: 10;");

        // Create an optional icon for the window (can use an ImageView or any other node)
        ImageView icon = getIcon("arrow.png"); // Optional, can be null

        // Create the InternalWindow using the correct constructor
        InternalWindow internalWindow = new InternalWindow(id, icon, title, content);
        internalWindow.setPrefSize(300, 200);

        // Set the position of the window
        internalWindow.setLayoutX(x);
        internalWindow.setLayoutY(y);

        return internalWindow;
    }


//
//    public static MainWindowFX create(Session session, IRuntime pluginRuntime) {
//        final MainWindowFX win = new MainWindowFX(session, pluginRuntime);
//
//        //win.pack();
//        //win.setLocationRelativeTo(null);
//        //win.setVisible(true);
//        return win;
//    }

    private void setRecentfiles() {
        recentFilesMenu.getItems().clear();

        for (Path recent : Options.getRecentFiles("use")) {
            recentFilesMenu.getItems().add(ActionFileOpenSpecRecent(recent));
        }
    }


    // Setter für Session
    public void setSession(Session session) {
        this.fSession = session;
        // Now that session is set, initialize session-dependent components
        if (fSession != null && fSession.hasSystem()) {
            initializeSessionDependentComponents();
        }
    }

    private void initializeSessionDependentComponents() {
        // Initialize ModelBrowser and other session-related components
//        fModelBrowser = new ModelBrowserFX(fSession.system().model(), folderTreeView, fPluginRuntime);
//        fModelBrowser.fillTreeView();
        //System.out.println("ModelBrowserFX has been initialized with system.");
    }

    // Setter für IRuntime
    public void setPluginRuntime(IRuntime pluginRuntime) {
        this.fPluginRuntime = pluginRuntime;
    }

    // Setter für IRuntime
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public TreeView<String> getFolderTreeView() {
        return folderTreeView;
    }

    public void setStyleSheet() {
        folderTreeView.getStylesheets().add(getClass().getResource("/styles/treeview-style.css").toExternalForm());
    }

    public void setMode() {
        getFolderTreeView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public Session getSession() {
        return fSession;
    }

    // Methode, um das WebView aus der VBox zu holen
    public WebView getWebViewFromPlaceholder() {
        for (Node node : webViewPlaceholder.getChildren()) {
            if (node instanceof WebView) {
                return (WebView) node;  // WebView gefunden
            }
        }
        return null;  // Kein WebView gefunden
    }

    public IRuntime getPluginRuntime() {
        return fPluginRuntime;
    }

    /**
     * Retrieves an icon by name and returns it as an ImageView. This method loads the image from the specified resource path
     * constructed using the icon name provided. It ensures the resource is not null before loading to prevent runtime exceptions.
     *
     * @param name the name of the icon to retrieve, which is used to construct the resource path.
     * @return an ImageView containing the loaded image.
     */
    private static ImageView getIcon(String name) {
        // Load the image from the resource path
        Image image = new Image(Objects.requireNonNull(MainWindowFX.class.getResourceAsStream(Options.getIconPath(name))));
        // Create an ImageView to display the image
        return new ImageView(image);
    }

    public static MainWindowFX getInstance() {
        return instance;
    }

}
