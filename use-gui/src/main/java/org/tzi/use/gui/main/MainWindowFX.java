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

// Notwendig für die Einbindung von Java Swing Content in einer JavaFX Anwendung

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;

import javax.swing.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.tzi.use.config.Options;
import org.tzi.use.config.RecentItems.RecentItemsObserver;

import org.tzi.use.config.RecentItems;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CommunicationDiagramView;

import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;

import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.main.gui.Main;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.runtime.gui.impl.PluginActionProxy;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.USEWriter;


import java.awt.*;
import java.awt.event.ActionEvent;
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

    @FXML
    private TextArea fLogTextArea;

    @FXML
    private VBox webViewPlaceholder; // Reference to the log text area in FXML

    @FXML
    private ToolBar fToolBar; // Reference to the ToolBar

    @FXML
    private Menu fileMenuItems, editMenuItems, stateMenuItems, viewMenuItems, pluginsMenuItems, helpMenuItems;

    @FXML
    private TreeView<String> folderTreeView;

    @FXML
    private AnchorPane fDesktopPane;

    @FXML
    private HBox fDesktopTaskbarPane;

    @FXML
    private Label fStatusBar;

    // Boolean Property's as Action Listeners to Update Visibility!!
    private final BooleanProperty fActionFileReload = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionSpecificationLoaded = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionSaveScript = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionPrintDiagram = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionPrintView = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionExportContentAsPDF = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionEditUndo = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionEditRedo = new SimpleBooleanProperty(false);

    // Keep track of the currently active ResizableInternalWindow
    private final ObjectProperty<ResizableInternalWindow> activeWindow = new SimpleObjectProperty<>(null);

    // The List containing all ResizableInternalWindows
    private final List<ResizableInternalWindow> internalWindows = new ArrayList<>();

    private static Session fSession;
    private static IRuntime fPluginRuntime;
    private Stage primaryStage;  // Reference to the primary stage
    private static MainWindowFX instance;
    private PageLayout fPageLayout;
    private SwingNode swingNode;
    private static PrintWriter fLogWriter;
    private LogPanel fLogPanel;
    private Button fBtnEditUndo;
    private Button fBtnEditRedo;
    private MenuItem fMenuItemEditUndo;
    private MenuItem fMenuItemEditRedo;
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



    private Map<Map<String, String>, PluginActionProxy> pluginActions =
            new HashMap<Map<String, String>, PluginActionProxy>();


    public MainWindowFX() {
        instance = this;
    }

    @FXML
    public void initialize() throws IOException {
        Options.getRecentFiles().getItems().clear();
        // update the status for the visibility of the menu- and tab-items
        updateFActionDiagramPrinter();
        updateFActionViewPrinter();
        updateFActionExportContentAsPDF();

        bindActionProperties();

        initWebViewPlaceholder();
        initFolderTreeView();

        initToolbarItems(fToolBar, folderTreeView); // initializing the toolbar
        initMenuBarItems(fileMenuItems, editMenuItems, stateMenuItems, viewMenuItems, pluginsMenuItems, helpMenuItems, folderTreeView); // initializing the menubar

        fSession = getSession();
        fPluginRuntime = getPluginRuntime();
        primaryStage = getPrimaryStage();

        if (fSession != null && fSession.hasSystem()) {
            fModelBrowser = new ModelBrowserFX(fSession.system().model(), fPluginRuntime);
            fModelBrowser.setModel(fSession.system().model());
        }

        //create the log panel
        fLogPanel = new LogPanel(fLogTextArea);
        fLogWriter = new PrintWriter(fLogPanel, true);


        // initialize application state to current system
        sessionChanged();

        // the session may be changed from the shell
        fSession.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Platform.runLater(() -> {
                    sessionChanged();
                });
            }
        });

        activeWindow.addListener((observable, oldWindow, newWindow)-> {
            if (newWindow != null) {
                String windowTitle = newWindow.getTitleText();
                if ("Class diagram".equals(windowTitle) || "Object diagram".equals(windowTitle) || "Communication diagram".equals(windowTitle)) {
                    // show diagram printer and exportAsPdf if above tabs selected
                    fActionPrintDiagram.setValue(true);
                    fActionExportContentAsPDF.setValue(true);
                } else {
                    // don't show diagram printer and exportAsPdf if not selected
                    fActionPrintDiagram.setValue(false);
                    fActionExportContentAsPDF.setValue(false);
                }
                if ("Sequence diagram".equals(windowTitle)) {
                    // show view printer if sequence diagram tab is selected
                    fActionPrintView.setValue(true);
                } else {
                    // don't show view printer if sequence diagram tab is not selected
                    fActionPrintView.setValue(false);
                }
            } else {
                // when no tabs open
                fActionPrintDiagram.setValue(false);
                fActionPrintView.setValue(false);
                fActionExportContentAsPDF.setValue(false);
            }
        });

        /**
         * for soil statements
         */
        fSession.addEvaluatedStatementListener(
                new Session.EvaluatedStatementListener(){
                    @Override
                    public void evaluatedStatement(Session.EvaluatedStatement event) {
                        Platform.runLater(() -> {
                            setUndoRedoButtons();
                        });
                    }});

    }

    private void bindActionProperties() {
        // **[MARKER: DYNAMIC BINDINGS FOR ACTIONS]**
        Options.getRecentFiles().addObserver(new RecentItemsObserver() {
            @Override
            public void onRecentItemChange(RecentItems src) {
                setRecentFiles();
                fActionFileReload.set(!Options.getRecentFiles().isEmpty());
                fActionSpecificationLoaded.set(!Options.getRecentFiles().isEmpty());
            }
        });

    }

    /**
     * Set application state for new system. The system parameter may be null.
     */
    void sessionChanged() {
        boolean on = fSession.hasSystem();

        if (Options.doPLUGIN) {
            for (PluginActionProxy currentAction : pluginActions.values()) {
                currentAction.calculateEnabled();
            }
        }

        //TODO
        setUndoRedoButtons();
//        closeAllViews();
//        statemachineMenu.removeAll();
//        createStateMachineMenuEntries(statemachineMenu);

        if (fModelBrowser != null && primaryStage != null) {
            if (on) {
                MSystem system = fSession.system();
                fModelBrowser.setModel(system.model());
                primaryStage.setTitle("USE: " + new File(system.model().filename()).getName());
            }
        }

    }

    private void setUndoRedoButtons() {
        if(!fSession.hasSystem()){
            disableUndo();
            disableRedo();
            return;
        }

        String nextToUndo = fSession.system().getUndoDescription();

        if (nextToUndo != null) {
            enableUndo(nextToUndo);
        } else {
            disableUndo();
        }

        String nextToRedo =
                fSession.system().getRedoDescription();

        if (nextToRedo != null) {
            enableRedo(nextToRedo);
        } else {
            disableRedo();
        }
    }

    /**
     * Enables the undo command.
     */
    void enableUndo(String name) {
        fActionEditUndo.set(true);
        // change text of menu item, leave toolbar button untouched
        String s = "Undo: " + name;
        fMenuItemEditUndo.setText(s);
        fBtnEditUndo.getTooltip().setText(s);
    }

    /**
     * Disables the undo command.
     */
    void disableUndo() {
        fActionEditUndo.set(false);
        // change text of menu item, leave toolbar button untouched
        fMenuItemEditUndo.setText("Undo");
        fBtnEditUndo.getTooltip().setText(DEFAULT_UNDO_TEXT);
    }

    /**
     * Enables the redo command.
     */
    void enableRedo(String name) {
        fActionEditRedo.set(true);
        // change text of menu item, leave toolbar button untouched
        String s = "Redo: " + name;
        fMenuItemEditRedo.setText(s);
        fBtnEditRedo.getTooltip().setText(s);
    }

    /**
     * Disables the undo command.
     */
    void disableRedo() {
        fActionEditRedo.set(false);
        // change text of menu item, leave toolbar button untouched
        fMenuItemEditRedo.setText("Redo");
        fBtnEditRedo.getTooltip().setText(DEFAULT_REDO_TEXT);
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
        openSpecification.setGraphic(getIcon("document-open.png"));
        openSpecification.setAccelerator(KeyCombination.valueOf("Ctrl+O"));
        openSpecification.setOnAction(e -> {
            instance.openDirectoryChooser(folderTreeView);
        });

        recentFilesMenu = new Menu("Open recent specification");
        recentFilesMenu.setGraphic(getIcon("document-open.png"));

        MenuItem reloadSpecification = new MenuItem("Reload specification");
        reloadSpecification.disableProperty().bind(fActionFileReload.not());
        reloadSpecification.setAccelerator(KeyCombination.valueOf("Ctrl+R"));
        reloadSpecification.setGraphic(getIcon("refresh.png"));
        reloadSpecification.setOnAction(e -> {
            // can not be Null bcs reloadSpecification is disabled if recentFiles is null ^^
            instance.compile(Options.getRecentFile("use"));
            initializeModelBrowserFX(); //reinitializing after compiling
        });

        MenuItem saveScript = new MenuItem("Save script (.soil)...");
        saveScript.setGraphic(getIcon("save.png"));
        saveScript.disableProperty().bind(fActionSaveScript.not());

        MenuItem saveProtocol = new MenuItem("Save protocol...");
        saveProtocol.setGraphic(getIcon("save.png"));

        MenuItem printerSetup = new MenuItem("Printer Setup...");
        printerSetup.setGraphic(getIcon("document-print.png"));
        printerSetup.setOnAction(e -> {

            PrinterJob job = PrinterJob.createPrinterJob();

            // initialize page format if necessary
            fPageLayout = pageLayout(job);

            // Set the job's current page layout before showing the dialog
            job.getJobSettings().setPageLayout(fPageLayout);

            // Show page setup dialog
            boolean proceed = job.showPageSetupDialog(null);
            if (proceed) {
                // Apply the configured layout to the job
                fPageLayout = job.getJobSettings().getPageLayout();
            }

        });

        MenuItem printDiagram = new MenuItem("Print diagram...");
        printDiagram.setGraphic(getIcon("document-print.png"));
        printDiagram.disableProperty().bind(fActionPrintDiagram.not());
        printDiagram.setOnAction(e -> {
            // TODO
        });

        MenuItem printView = new MenuItem("Print View...");
        printView.setGraphic(getIcon("document-print.png"));
        printView.disableProperty().bind(fActionPrintView.not());

        MenuItem exportAsPdf = new MenuItem("Export view as PDF...");
        exportAsPdf.setGraphic(getIcon("export_pdf.png"));
        exportAsPdf.disableProperty().bind(fActionExportContentAsPDF.not());


        MenuItem exit = new MenuItem("Exit");
        exit.setAccelerator(KeyCombination.valueOf("Ctrl+Q"));
        exit.setOnAction(e -> {
            // Platform.exit terminates the entire JavaFX application
            Platform.exit();
            Shell.getInstance().exit();
        });

        fileMenuItems.getItems().addAll(openSpecification, recentFilesMenu, reloadSpecification, saveScript, saveProtocol, new SeparatorMenuItem(), printerSetup, printDiagram, printView, exportAsPdf, new SeparatorMenuItem(), exit);
    }

    /**
     * initializing the EditMenuItems
     */
    private void initEditMenuItems(Menu editMenuItems) {
        fMenuItemEditUndo = new MenuItem("Undo");
        fMenuItemEditRedo = new MenuItem("Redo");

        fMenuItemEditUndo.setGraphic(getIcon("undo.png"));
        fMenuItemEditRedo.setGraphic(getIcon("redo.png"));

        fMenuItemEditUndo.disableProperty().bind(fActionEditUndo.not());
        fMenuItemEditUndo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        fMenuItemEditUndo.setOnAction(e -> {
            actionEditUndo();
        });
        fMenuItemEditRedo.disableProperty().bind(fActionEditRedo.not());
        fMenuItemEditRedo.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));
        fMenuItemEditRedo.setOnAction(e -> {
            actionEditRedo();
        });

        editMenuItems.getItems().addAll(fMenuItemEditUndo, fMenuItemEditRedo);
    }

    /**
     * initializing the StateMenuItems
     */
    private void initStateMenuItems(Menu stateMenuItems) {
        MenuItem createObject = new MenuItem("Create object...");
        createObject.setAccelerator(KeyCombination.keyCombination("F7"));
        createObject.disableProperty().bind(fActionSpecificationLoaded.not());
        createObject.setOnAction(e -> {
            CreateObjectDialog dlg = new CreateObjectDialog(fSession, instance);
            dlg.showAndWait();
            updateFActionSaveScript();
            setUndoRedoButtons();
        });

        MenuItem evaluateOCLexpr = new MenuItem("Evaluate OCL expression...");
        evaluateOCLexpr.setAccelerator(KeyCombination.keyCombination("F8"));
        evaluateOCLexpr.setGraphic(getIcon("OCL.gif"));
        evaluateOCLexpr.setOnAction(e -> {
            System.out.println("F8 Succesfully pressed");
        });

        MenuItem checkSN = new MenuItem("Check structure now");
        checkSN.setAccelerator(KeyCombination.keyCombination("F9"));
        checkSN.disableProperty().bind(fActionSpecificationLoaded.not());
        checkSN.setOnAction(e -> {
            System.out.println("F9 Succesfully pressed");
        });

        CheckMenuItem checkSAEC = new CheckMenuItem("Check structure after every change");
        CheckMenuItem checkSMT = new CheckMenuItem("Check state machine transitions");
        CheckMenuItem checkSIAEC = new CheckMenuItem("Check state invariants after every change");
        MenuItem determine_states = new MenuItem("Determine states");
        determine_states.disableProperty().bind(fActionSpecificationLoaded.not());
        MenuItem checkStateInvariants = new MenuItem("Check state invariants");
        checkStateInvariants.disableProperty().bind(fActionSpecificationLoaded.not());
        MenuItem reset = new MenuItem("Reset");
        reset.disableProperty().bind(fActionSpecificationLoaded.not());



        stateMenuItems.getItems().addAll(createObject, evaluateOCLexpr, checkSN, checkSAEC, checkSMT, checkSIAEC, determine_states, checkStateInvariants, reset);
    }

    /**
     * initializing the ViewMenuItems
     */
    private void initViewMenuItems(Menu viewMenuItems) {
        Menu createView = new Menu("Create View");
        MenuItem tile = new MenuItem("Tile");
        MenuItem closeAll = new MenuItem("Close all");

        MenuItem createClassDiagramViewItem = new MenuItem("Class diagram", getIcon("ClassDiagram.gif"));
        createClassDiagramViewItem.setOnAction(e -> {
            instance.createClassDiagram();
        });

        Menu createStateMachineDiagramViewItem = new Menu("State machine diagram", getIcon("Diagram.gif"));
        createStateMachineDiagramViewItem.setOnAction(e -> {});

        MenuItem createObjectDiagramViewItem = new MenuItem("Object diagram ", getIcon("ObjectDiagram.gif"));
        createObjectDiagramViewItem.setOnAction(e -> {
            instance.createObjectDiagram();
        });

        MenuItem createClassInvariantViewItem = new MenuItem("Class invariant", getIcon("invariant-view.png"));
        MenuItem createObjectCountViewItem = new MenuItem("Object count", getIcon("blue-chart-icon.png"));
        MenuItem createLinkCountViewItem = new MenuItem("Link count", getIcon("red-chart-icon.png"));
        MenuItem createStateEvolutionViewItem = new MenuItem("State evolution", getIcon("line-chart.png"));
        MenuItem createObjectPropertiesViewItem = new MenuItem("Object properties", getIcon("ObjectProperties.gif"));
        MenuItem createClassExtentViewItem = new MenuItem("Class extent", getIcon("ClassExtentView.gif"));
        MenuItem createSequenceDiagramViewItem = new MenuItem("Sequence diagram", getIcon("SequenceDiagram.gif"));
        MenuItem createCommunicationDiagramViewItem = new MenuItem("Communication diagram", getIcon("CommunicationDiagram.gif"));
        MenuItem createCallStackViewItem = new MenuItem("Call stack", getIcon("CallStack.gif"));
        MenuItem createCommandListViewItem = new MenuItem("Command list ", getIcon("CmdList.gif"));
        MenuItem associationEndsInformation = new MenuItem("Association ends informations ", getIcon("CompositeAggregation.gif"));


        // Add MenuItems objects to the SubMenu
        createView.getItems().addAll(createClassDiagramViewItem, createStateMachineDiagramViewItem, createObjectDiagramViewItem, createClassInvariantViewItem, createObjectCountViewItem,
                createLinkCountViewItem, createStateEvolutionViewItem, createObjectPropertiesViewItem, createClassExtentViewItem, createSequenceDiagramViewItem, createCommunicationDiagramViewItem,
                createCallStackViewItem, createCommandListViewItem, associationEndsInformation);

        createView.getItems().iterator().forEachRemaining(menuItem -> {
            menuItem.disableProperty().bind(fActionSpecificationLoaded.not());
        });

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
            AboutDialog dlg = new AboutDialog(primaryStage);
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
        toolbarItems.put("Reload current specification", getIcon("refresh.png"));
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
        toolbarItems.put("Create link count view", getIcon("red-chart-icon.png"));
        toolbarItems.put("Create state evolution view", getIcon("line-chart.png"));
        toolbarItems.put("Create object properties view", getIcon("ObjectProperties.gif"));
        toolbarItems.put("Create class extent view", getIcon("ClassExtentView.gif"));
        toolbarItems.put("Create sequence diagram view", getIcon("SequenceDiagram.gif"));
        toolbarItems.put("Create communication diagram view", getIcon("CommunicationDiagram.gif"));
        toolbarItems.put("Create call stack view", getIcon("CallStack.gif"));
        toolbarItems.put("Create command list view", getIcon("CmdList.gif"));

        int i = 0;
        // initializing each button of the toolbar
        for (Map.Entry<String, ImageView> entry : toolbarItems.entrySet()) {
            String tooltipText = entry.getKey();
            ImageView imageKey = entry.getValue();
            Button button = new Button();
            button.setMaxSize(30, 30);
            button.setGraphic(imageKey);
            Tooltip tooltip = new Tooltip(tooltipText);
            button.setTooltip(tooltip);


            switch (entry.getKey()) {
                case "Open specification":
                    button.setDisable(false);
                    button.setOnAction(e -> instance.openDirectoryChooser(folderTreeView));
                    toolBar.getItems().add(button);
                    break;
                case "Reload current specification":
                    button.disableProperty().bind(fActionFileReload.not());
                    button.setOnAction(e -> {
                        instance.compile(Options.getRecentFile("use"));
                        initializeModelBrowserFX(); //reinitializing after compiling
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Print diagram":
                    button.disableProperty().bind(fActionPrintDiagram.not());
                    //TODO "Print diagram"
                    toolBar.getItems().add(button);
                    break;
                case "Print view":
                    button.disableProperty().bind(fActionPrintView.not());
                    //TODO "Print view"
                    toolBar.getItems().add(button);
                    break;
                case "Export content of view as PDF":
                    button.disableProperty().bind(fActionExportContentAsPDF.not());
                    //TODO "Export content of view as PDF"
                    toolBar.getItems().add(button);
                    break;
                case "Undo last statement":
                    fBtnEditUndo = button;
                    fBtnEditUndo.disableProperty().bind(fActionEditUndo.not());
                    fBtnEditUndo.setTooltip(new Tooltip(DEFAULT_UNDO_TEXT));
                    fBtnEditUndo.setOnAction(e->{
                        actionEditUndo();
                    });
                    toolBar.getItems().add(fBtnEditUndo);
                    break;
                case "Redo last undone statement":
                    fBtnEditRedo = button;
                    fBtnEditRedo.disableProperty().bind(fActionEditRedo.not());
                    fBtnEditRedo.setTooltip(new Tooltip(DEFAULT_REDO_TEXT));
                    fBtnEditRedo.setOnAction(e->{
                        actionEditRedo();
                    });
                    toolBar.getItems().add(fBtnEditRedo);
                    break;
                case "Evaluate OCL expression":
                    //TODO "Print view"
                    toolBar.getItems().add(button);
                    break;
                case "Create class diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> instance.createClassDiagram());
                    toolBar.getItems().add(button);
                    break;
                case "Create statemachine diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create statemachine diagram view"
                    toolBar.getItems().add(button);
                    break;
                case "Create object diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createObjectDiagram();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create class invariant view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create class invariant view"
                    toolBar.getItems().add(button);
                    break;
                case "Create object count view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create object count view"
                    toolBar.getItems().add(button);
                    break;
                case "Create link count view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create link count view"
                    toolBar.getItems().add(button);
                    break;
                case "Create state evolution view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create state evolution view"
                    toolBar.getItems().add(button);
                    break;
                case "Create object properties view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create object properties view"
                    toolBar.getItems().add(button);
                    break;
                case "Create class extent view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create class extent view"
                    toolBar.getItems().add(button);
                    break;
                case "Create sequence diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create sequence diagram view"
                    toolBar.getItems().add(button);
                    break;
                case "Create communication diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create communication diagram view"
                    toolBar.getItems().add(button);
                    break;
                case "Create call stack view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create call stack view"
                    toolBar.getItems().add(button);
                    break;
                case "Create command list view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    //TODO "Create command list view"
                    toolBar.getItems().add(button);
                    break;
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
     * Creates a new ResizableInternalWindow and adds it to the desktop.
     */
    private void createNewWindow(String title, SwingNode swingNode) {
        ResizableInternalWindow window = new ResizableInternalWindow(title, fDesktopPane, fDesktopTaskbarPane, swingNode,  this);

        // Random or custom position in the desktop
        window.setLayoutX(Math.random() * Math.max(0, (fDesktopPane.getWidth() - 300)));
        window.setLayoutY(Math.random() * Math.max(0, (fDesktopPane.getHeight() - 200)));

        // add Window to DesktopPane
        fDesktopPane.getChildren().add(window);

        // Mark this window as active for the controller (MainWindow)
        window.toFront();
        setActiveWindow(window); // sets window active for this controller
        window.setActive(true);  // sets window active and marks the border

        // add Window to list of Windows
        internalWindows.add(window);

        // Listener for the selected Tab
        setupTabSelectionMessage(window, "Use left mouse button to move "
                + "classes, right button for popup menu.");

    }

    /**
     * Closes all windows, removing them from desktop and taskbar (if minimized).
     */
    private void closeAllWindows() {
        // For each window in the list, call its closeWindow() or remove from desktop etc.
        for (ResizableInternalWindow win : internalWindows) {
            win.closeWindow();
        }
        internalWindows.clear();
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

        if (wasUsed && Options.getRecentFile("use") != null) {
            File recentFile = Options.getRecentFile("use").toFile();
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

            initializeModelBrowserFX();

            wasUsed = true;

            setRecentFiles();

        }

    }

    /**
     * Undoes the last executed statement in the system.
     * Shows alerts if the system is busy or an error occurs.
     */
    public void actionEditUndo(){
        if (fSession.hasSystem() && fSession.system().isExecutingStatement()) {
            showAlert("The system is currently executing a statement.\nPlease end the execution before undoing.", "USE is executing");
        }

        try {
            fSession.system().undoLastStatement();
            setUndoRedoButtons();
        } catch (MSystemException ex) {
            showAlert(ex.getMessage(), "Error");
        }
        // keeping the BooleanProperty Updated
        updateFActionSaveScript();
    }

    public void actionEditRedo() {
        if (fSession.hasSystem() && fSession.system().isExecutingStatement()) {
            showAlert("The system is currently executing a statement.\nPlease end the execution before redoing.", "USE is executing");
        }

        MSystem system = fSession.system();

        MStatement nextToRedo = system.nextToRedo();

        if ((nextToRedo instanceof MEnterOperationStatement) || (nextToRedo instanceof MExitOperationStatement)) {
            showAlert("openter/opexit can only be redone in the shell", "Error");
        }

        try {
            system.redoStatement();
            setUndoRedoButtons();
        } catch (MSystemException ex) {
            showAlert(ex.getMessage(), "Error");
        }
        updateFActionSaveScript();
    }

    public void createObject(String clsName) {
        MClass objectClass = fSession.system().model().getClass(clsName);

        if (objectClass == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No class named `" + clsName + "' defined in model.");
            alert.showAndWait();

            return;
        }

        createObject(objectClass, null);
    }

    /**
     * Creates a new object. Keeps track of undo information and handles errors
     * on the GUI level.
     */
    public void createObject(MClass objectClass, String objectName) {

        try {
            MNewObjectStatement statement =
                    new MNewObjectStatement(objectClass, objectName);

            USEWriter.getInstance().protocol(
                    "[GUI] " + statement.getShellCommand().substring(1));

            fSession.system().execute(statement);

        } catch (MSystemException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif"))));
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            Platform.runLater(alert::showAndWait);
        }
    }

    /**
     * Creates a new class diagram view.
     */
    private void createClassDiagram() {

        // to create an Intance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // Don't load layout if shift key is pressed
        boolean loadLayout = (ActionEvent.SHIFT_MASK) == 0;

        //setting the visiility of the MainWindow (Swing Gui) to false because we dont want it to be shown
        MainWindow.setJavaFxCall(true);
        ClassDiagram.setJavaFxCall(true); // so that class diagrams dont save any state

        // Calling the Swing MainWindow to get the ClassDiagram out of it
        MainWindow mainwindow = MainWindow.create(fSession,fPluginRuntime);

        ClassDiagramView cdv = new ClassDiagramView(mainwindow, fSession.system(), loadLayout);
        ViewFrame f = new ViewFrame("Class diagram", cdv, "ClassDiagram.gif");

        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(cdv, BorderLayout.CENTER);
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Class diagram", swingNode);
    }

    /**
     * Creates a new object diagram view.
     */
    private void createObjectDiagram() {

        // to create an Intance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we doesn't want it to be shown
        MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        MainWindow mainwindow = MainWindow.create(fSession,fPluginRuntime);

        // Create the NewObjectDiagramView and the enclosing ViewFrame
        NewObjectDiagramView odv = new NewObjectDiagramView(mainwindow, fSession.system());
        ViewFrame f = new ViewFrame("Object diagram", odv, "ObjectDiagram.gif");

        // on changes of session the object view diagrams are being updated!
        fSession.addChangeListener(event -> {
            Platform.runLater(odv::updateUI);
        });

        // Check if the system has too many objects and prompt the user
        int OBJECTS_LARGE_SYSTEM = 1;
        if (fSession.system().state().allObjects().size() > OBJECTS_LARGE_SYSTEM) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Large system state");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif"))));
            alert.setContentText("The current system state contains more than " + OBJECTS_LARGE_SYSTEM + " instances. " +
                    "This can slow down the object diagram.\r\nDo you want to start with an empty object diagram?");

            // Show a confirmation dialog using Alert
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                odv.getDiagram().hideAll();
            } else {
                // Do Nothing (Show the Object Diagram View without Hiding)
            }
        }

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(odv, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Object diagram", swingNode);
    }

    // Actions

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
        // clearing the current state before compiling the new one
        fLogPanel.clear();

        // clearing all the windows inside the desktoppane
        closeAllWindows();

        fLogWriter.println("compiling specification " + f.toString() + "...");

        MModel model = null;
        try (InputStream iStream = Files.newInputStream(f)) {
            model = USECompiler.compileSpecification(iStream, f.toAbsolutePath().toString(),
                    fLogWriter, new ModelFactory());
            fLogWriter.println("done.");
            //logTextArea.appendText("done.\n");
        } catch (IOException ex) {
            fLogWriter.println("File `" + f.toAbsolutePath().toString() + "' not found.");
        }

        final MSystem system;
        if (model != null) {
            fLogWriter.println(model.getStats());
            // create system
            system = new MSystem(model);
        } else {
            system = null;
        }

        // set new system (might be null if compilation failed)
        Platform.runLater(() -> fSession.setSystem(system));

        if (system != null) {
            fSession.setSystem(system);  // System synchron setzen, da keine GUI-Operation erforderlich ist
            Options.getRecentFiles().push(f.toString());
            Options.setLastDirectory(f.getParent());
            return true;
        } else {
            return false;
        }
    }
    protected void initializeModelBrowserFX() {
        // check if specifications avaiable
        updateFActionSaveScript();
        updateFActionDiagramPrinter();
        updateFActionViewPrinter();
        updateFActionExportContentAsPDF();
        if (fSession.system() != null && fSession.system().model() != null) {
            new ModelBrowserFX(fSession.system().model(), fPluginRuntime);
        } else {
            new ModelBrowserFX(null, fPluginRuntime);
        }
    }

    public void updateTreeView(TreeItem<String> root) {
        folderTreeView.setRoot(root);
    }

    public void initWebViewPlaceholder() {
        Platform.runLater(() -> {
                    WebView webView = new WebView();
                    webView.setVisible(true);
                    webView.setContextMenuEnabled(false);
                    //webView.getEngine().loadContent("test");
                    // Füge das WebView in die VBox (webViewPlaceholder) ein
                    webViewPlaceholder.getChildren().add(webView);
                }
        );
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

    /**
     *  this Method creates menu-items for each recent specification and
     *  ads a seperator after each has been created, to include at last an
     *  menu-item (clearRecentSpecifications) to clear these menu-items
     *  (also the recent files in the Options)
     */
    public void setRecentFiles() {
        if (recentFilesMenu.getItems() != null){
            recentFilesMenu.getItems().clear();
        }

        for (Path recent : Options.getRecentFiles("use")) {
            MenuItem item = new MenuItem(recent.toString());
            item.setOnAction(e -> {
                compile(Objects.requireNonNull(recent));
                initializeModelBrowserFX();
            });
            recentFilesMenu.getItems().add(item);
        }

        MenuItem clearRecentSpecifications = new MenuItem("Clear recent specifications");
        clearRecentSpecifications.disableProperty().bind(fActionFileReload.not());
        clearRecentSpecifications.setOnAction(e -> {
            Options.getRecentFiles().getItems().clear();
            recentFilesMenu.getItems().clear();
            // after having all menu-items for the recent specifications deleted we reinitialize
            // the menuitem to contain only the divider and clearRecentSpecifications
            setRecentFiles();
        });

        recentFilesMenu.getItems().add(new SeparatorMenuItem());
        recentFilesMenu.getItems().add(clearRecentSpecifications);
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

    // setter für IRuntime
    public void setPluginRuntime(IRuntime pluginRuntime) {
        this.fPluginRuntime = pluginRuntime;
    }

    // setter for primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // getter for primaryStage
    public Stage getPrimaryStage() {
        return primaryStage;
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
     * This Method updates the Status of the BooleanProperty (fActionSaveScript), according to if scripts are available.
     */
    public void updateFActionSaveScript() {
        // TODO hier schauen wann angezeigt werden soll
        if (fSession.hasSystem()) {
            fActionSaveScript.set(fSession.system().numEvaluatedStatements() > 0);
        }
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionPrintDiagram), according to if Diagrams are available.
     */
    public void updateFActionDiagramPrinter() {
        // TODO hier schauen auf welchem Tab man ist und wenn man auf einem tab ist welches für Printer geeignet ist dann halt true.
        //fActionPrinter.set(fDesktopPane.getTabs().);
        fActionPrintDiagram.set(false);
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionPrintView), according to if Views are available.
     */
    public void updateFActionViewPrinter() {
        // TODO hier schauen auf welchem Tab man ist und wenn man auf einem tab ist welches für Printer geeignet ist dann halt true.
        //fActionPrinter.set(fDesktopPane.getTabs().);
        fActionPrintView.set(false);
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionExportContentAsPDF), according to if contents of View are available.
     */
    public void updateFActionExportContentAsPDF() {
        // TODO hier schauen auf welchem Tab man ist und wenn man auf einem tab ist welches für Printer geeignet ist dann halt true.
        //fActionPrinter.set(fDesktopPane.getTabs().);
        fActionExportContentAsPDF.set(false);
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
        Image image = new Image(Objects.requireNonNull(MainWindowFX.class.getResourceAsStream("/images/" + name)));
        // Create an ImageView to display the image
        return new ImageView(image);
    }

    /**
     * Returns the instance of MainWindow.
     */
    public static MainWindowFX getInstance() {
        return instance;
    }

    /**
     * Returns the page format for printing.
     */
    public PageLayout pageLayout(PrinterJob job) {
        if (fPageLayout == null) {
            // initialize with defaults
            Printer printer = job.getPrinter();
            Paper paper = job.getPrinter().getDefaultPageLayout().getPaper();

            // Default Portrait to initialize it
            PageOrientation pageOrientation = PageOrientation.PORTRAIT;

            if (Options.PRINT_PAGEFORMAT_ORIENTATION.equals("landscape"))
                pageOrientation = PageOrientation.LANDSCAPE;
            else if (Options.PRINT_PAGEFORMAT_ORIENTATION.equals("seascape"))
                pageOrientation = PageOrientation.REVERSE_LANDSCAPE;

            fPageLayout = printer.createPageLayout(paper, pageOrientation, Printer.MarginType.DEFAULT);
        }
        return fPageLayout;
    }

    /**
     * Sets up a tab to display a temporary message in the status bar when selected.
     *
     * @param window       the window to which the selection behavior will be applied
     * @param tmpMessage   the temporary message to display when the tab is selected
     */
    private void setupTabSelectionMessage(ResizableInternalWindow window, String tmpMessage){
        window.setOnMouseClicked(event -> {
            if (window.isActive()) {
                fStatusBar.setText(tmpMessage);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(8), e -> fStatusBar.setText("Ready.")));
                timeline.setCycleCount(1);
                timeline.play();
            } else {
                fStatusBar.setText("Ready.");
            }
        });
    }

    /**
     * returns the active Window on the DesktopPan
     */
    public ResizableInternalWindow getActiveWindow() {
        return activeWindow.get();
    }

    /**
     * adding a new Window To the window List of the desktopPane
     */
    public void addWindowToList(ResizableInternalWindow window){
        internalWindows.add(window);
    }

    /**
     * deleting a Window from the window List of the desktopPane
     */
    public void deleteWindowFromList(ResizableInternalWindow window){
        internalWindows.add(window);
    }

    /**
     * setting the active Window
     */
    public void setActiveWindow(ResizableInternalWindow window) {
        activeWindow.set(window);

        // Deactivate all other windows
        for (ResizableInternalWindow win : internalWindows) {
            win.setActive(win == window);
        }
    }

    /**
     * returns the activeWindowProperty
     */
    public ObjectProperty<ResizableInternalWindow> activeWindowProperty() {
        return activeWindow;
    }

    /**
     * Displays an error alert with the given title and message.
     *
     * @param content the message displayed in the alert dialog
     */
    private void showAlert(String content, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif"))));
        alert.showAndWait();
    }

}
