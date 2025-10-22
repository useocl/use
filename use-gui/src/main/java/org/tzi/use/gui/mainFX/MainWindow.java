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

package org.tzi.use.gui.mainFX;

// Notwendig für die Einbindung von Java Swing Content in einer JavaFX Anwendung

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;

import javax.swing.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.tzi.use.config.Options;
import org.tzi.use.config.RecentItems.RecentItemsObserver;

import org.tzi.use.config.RecentItems;
import org.tzi.use.gui.main.runtime.IPluginActionExtensionPoint;
import org.tzi.use.gui.utilFX.StatusBar;
import org.tzi.use.gui.views.*;
import org.tzi.use.gui.views.diagrams.DiagramType;
import org.tzi.use.gui.main.ViewFrame;

import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CommunicationDiagramView;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.SDScrollPane;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.SequenceDiagramView;
import org.tzi.use.gui.views.diagrams.behavior.shared.VisibleDataManager;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;

import org.tzi.use.gui.views.diagrams.statemachine.StateMachineDiagramView;
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
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;
import org.tzi.use.uml.sys.events.tags.SystemStructureChangedEvent;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.USEWriter;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
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
public class MainWindow {

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
    private StatusBar fStatusBar;

    // Boolean Property's as Action Listeners to Update Visibility!!
    private final BooleanProperty fActionFileReload = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionSpecificationLoaded = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionSaveScript = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionPrintDiagram = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionPrintView = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionExportContentAsPDF = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionEditUndo = new SimpleBooleanProperty(false);
    private final BooleanProperty fActionEditRedo = new SimpleBooleanProperty(false);
    private final BooleanProperty autoCheckStructure = new SimpleBooleanProperty();
    private final BooleanProperty autoCheckStateInvariants = new SimpleBooleanProperty();

    // Keep track of the currently active ResizableInternalWindow
    private final ObjectProperty<ResizableInternalWindow> activeWindow = new SimpleObjectProperty<>(null);

    // The List containing all ResizableInternalWindows
    private final List<ResizableInternalWindow> allDesktopWindows = new ArrayList<>();

    private static Session fSession;
    private static IRuntime fPluginRuntime;
    private Stage primaryStage;  // Reference to the primary stage
    private static MainWindow instance;
    private org.tzi.use.gui.main.MainWindow swingMainWindow; // just for Loading Plugins
    private PageLayout fPageLayout;
    private PrinterJob fPrinterJob;
    private CheckMenuItem fCbMenuItemCheckStructure;
    private SwingNode swingNode;
    private static PrintWriter fLogWriter;
    private LogPanel fLogPanel;
    private Button fBtnEditUndo;
    private Button fBtnEditRedo;
    private MenuItem fMenuItemEditUndo;
    private MenuItem fMenuItemEditRedo;
    private static Menu recentFilesMenu;
    private ModelBrowser fModelBrowser;
    private Menu stateMachineDiagramMenu;

    // Static variable to store the last selected directory path
    private static String specificationDir = System.getProperty("user.dir");

    private static boolean wasUsed;

    private static final String DEFAULT_UNDO_TEXT = "Undo last statement";
    private static final String DEFAULT_REDO_TEXT = "Redo last undone statement";


    private Map<Map<String, String>, PluginActionProxy> pluginActions =
            new HashMap<Map<String, String>, PluginActionProxy>();


    public MainWindow() {
        instance = this;
    }

    @FXML
    public void initialize() throws IOException {
        // Only clear if we started with no specification
        if (Options.specFilename == null) {
            Options.getRecentFiles().getItems().clear();
        }

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
            fModelBrowser = new ModelBrowser(fSession.system().model(), fPluginRuntime);
            fModelBrowser.setModel(fSession.system().model());
        }

        //create the log panel
        fLogPanel = new LogPanel(fLogTextArea);
        fLogWriter = new PrintWriter(fLogPanel, true);

        // initialize application state to current system
        sessionChanged();

        // Build plug-in UI now – runtime is guaranteed non-null here
        if (Options.doPLUGIN) {
            buildPluginToolbarAndMenu();
        }

        // the session may be changed from the shell
        fSession.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Platform.runLater(() -> {
                    sessionChanged();
                });
            }
        });

        activeWindow.addListener((observable, oldWindow, newWindow) -> {
            if (newWindow != null) {
                DiagramType type = newWindow.getDiagramType();
                fActionPrintDiagram.setValue(type.isDiagram());
                fActionPrintView.setValue(type.isView());
                fActionExportContentAsPDF.setValue(type.isExportable());
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
                new Session.EvaluatedStatementListener() {
                    @Override
                    public void evaluatedStatement(Session.EvaluatedStatement event) {
                        Platform.runLater(() -> {
                            setUndoRedoButtons();
                        });
                    }
                });

    }

    /**
     * Builds toolbar-buttons and menu entries for all plug-in actions.
     */
    private void buildPluginToolbarAndMenu() {
        // Nothing to do if plugins were switched off or runtime failed to start
        if (!Options.doPLUGIN) {
            return;
        }

        // 1. Fetch the “action” extension-point from the runtime
        IPluginActionExtensionPoint actionEP = (IPluginActionExtensionPoint) fPluginRuntime.getExtensionPoint("action");

        // 1.2 If we don’t yet have a Swing MainWindow, create a hidden one
        if (swingMainWindow == null) {
            org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);   // suppress UI
            swingMainWindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);
        }

        /*  actionEP.createPluginActions(..) returns exactly the same structure the
         *  Swing implementation expects:  a Map<descriptor-map, PluginActionProxy>.
         *  We hold on to that Map so that sessionChanged() can still call
         *  calculateEnabled() later on.                                             */
        pluginActions = actionEP.createPluginActions(fSession, swingMainWindow); //currently using the swing Main

        /* ------------------------------------------------------------
         * 2.  Make sure there is a “Plugins” menu and a separator at
         *     the end of the existing toolbar – identical to Swing.
         * ------------------------------------------------------------ */
        if (!fToolBar.getItems().isEmpty()) {
            Region spacer = new Region();
            spacer.setPrefWidth(10);
            fToolBar.getItems().add(spacer);
        }
        //  pluginsMenuItems is already defined in FXML -> make sure it is empty
        pluginsMenuItems.getItems().clear();

        /* ------------------------------------------------------------
         * 3.  Iterate over all plug-in actions and materialise them
         *     as (a) toolbar buttons, (b) menu items / sub-menus.
         * ------------------------------------------------------------ */
        for (Map.Entry<Map<String, String>, PluginActionProxy> entry : pluginActions.entrySet()) {
            Map<String, String> desc = entry.getKey();     // {menu, menuitem, tooltip, …}
            PluginActionProxy act = entry.getValue();   // Glue object that fires the action

            /* ---- 3a  Toolbar button ------------------------------------------ */
            Button btn = new Button();                     // 30×30 like the rest
            btn.setMaxSize(30, 30);
            btn.setTooltip(new Tooltip(desc.get("tooltip")));

            // Grab the Swing ImageIcon from the Action’s SMALL_ICON
            Object iconObj = act.getValue(Action.SMALL_ICON);
            if (iconObj instanceof ImageIcon swingIcon) {
                java.awt.Image awtImg = swingIcon.getImage();

                // Convert AWT Image ⇒ BufferedImage
                BufferedImage buff = new BufferedImage(
                        awtImg.getWidth(null),
                        awtImg.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2 = buff.createGraphics();
                g2.drawImage(awtImg, 0, 0, null);
                g2.dispose();

                // Convert BufferedImage ⇒ JavaFX Image
                Image fxImg = SwingFXUtils.toFXImage(buff, null);
                ImageView iv = new ImageView(fxImg);
                iv.setFitWidth(20);
                iv.setFitHeight(20);
                btn.setGraphic(iv);
            }

            btn.setOnAction(evt -> act.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, desc.getOrDefault("menuitem", "")))); // delegate
            fToolBar.getItems().add(btn);

            /* ---- 3b  Menu entry  --------------------------------------------- */
            if (desc.get("menu") == null || desc.get("menu").isBlank()) {
                /*  No sub-menu requested → add directly to the top-level
                 *  “Plugins” menu (this mirrors the Swing branch where
                 *  currentActionDescMap.get("menu") == null).                    */
                MenuItem mi = new MenuItem(desc.get("menuitem"));

                // Convert the Swing ImageIcon → JavaFX ImageView
                if (iconObj instanceof ImageIcon swingIcon2) {
                    java.awt.Image awtImg2 = swingIcon2.getImage();
                    BufferedImage buff2 = new BufferedImage(
                            awtImg2.getWidth(null),
                            awtImg2.getHeight(null),
                            BufferedImage.TYPE_INT_ARGB
                    );
                    Graphics2D g2b = buff2.createGraphics();
                    g2b.drawImage(awtImg2, 0, 0, null);
                    g2b.dispose();

                    Image fxImg2 = SwingFXUtils.toFXImage(buff2, null);
                    ImageView iv2 = new ImageView(fxImg2);
                    iv2.setFitWidth(16);
                    iv2.setFitHeight(16);
                    mi.setGraphic(iv2);
                }

                mi.setOnAction(evt -> act.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, desc.getOrDefault("menuitem", ""))));
                pluginsMenuItems.getItems().add(mi);

            } else {
                /*  Sub-menu requested.  Re-use it if it already exists, otherwise
                 *  create a fresh one—just like the Swing loop.                 */
                String desiredSub = desc.get("menu");
                Menu sub = null;
                for (MenuItem child : pluginsMenuItems.getItems()) {
                    if (child instanceof Menu && desiredSub.equals(child.getText())) {
                        sub = (Menu) child;
                        break;
                    }
                }
                if (sub == null) {
                    sub = new Menu(desiredSub);
                    pluginsMenuItems.getItems().add(sub);
                }
                MenuItem mi = new MenuItem(desc.get("menuitem"));
                // Convert the Swing ImageIcon → JavaFX ImageView
                if (iconObj instanceof ImageIcon swingIcon2) {
                    java.awt.Image awtImg2 = swingIcon2.getImage();
                    BufferedImage buff2 = new BufferedImage(
                            awtImg2.getWidth(null),
                            awtImg2.getHeight(null),
                            BufferedImage.TYPE_INT_ARGB
                    );
                    Graphics2D g2b = buff2.createGraphics();
                    g2b.drawImage(awtImg2, 0, 0, null);
                    g2b.dispose();

                    Image fxImg2 = SwingFXUtils.toFXImage(buff2, null);
                    ImageView iv2 = new ImageView(fxImg2);
                    iv2.setFitWidth(16);
                    iv2.setFitHeight(16);
                    mi.setGraphic(iv2);
                }
                mi.setOnAction(evt -> act.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, desc.getOrDefault("menuitem", ""))));
                sub.getItems().add(mi);
            }
        }
    }

    /**
     * Dynamic bindings for actions
     */
    private void bindActionProperties() {
        Options.getRecentFiles().addObserver(new RecentItemsObserver() {
            @Override
            public void onRecentItemChange(RecentItems src) {
                setRecentFiles();
                fActionFileReload.set(fSession.hasSystem());
                fActionSpecificationLoaded.set(fSession.hasSystem());
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

        fActionSpecificationLoaded.set(on);
        fActionFileReload.set(on);

        setUndoRedoButtons();
        closeAllWindows();


        if (fModelBrowser != null && primaryStage != null) {
            if (on) {
                fSession.system().getEventBus().unregister(this); // unregister old System before creating new one
                MSystem system = fSession.system();
                fModelBrowser.setModel(system.model());
                system.getEventBus().register(this);
                primaryStage.setTitle("USE: " + new File(system.model().filename()).getName());
            }
        }
        // for resetting the Application
        stateMachineDiagramMenu.getItems().clear();
        createStateMachineMenuEntries(stateMachineDiagramMenu.getItems());
        fStatusBar.clearMessage();
        repaintFolderTreeView();

        if (MainWindow.getInstance().getWebViewFromPlaceholder() != null) {
            MainWindow.getInstance().getWebViewFromPlaceholder().getEngine().loadContent("");
        }

    }

    public void setUndoRedoButtons() {
        if (!fSession.hasSystem()) {
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
        saveScript.setOnAction(e -> {
            saveScript();
        });

        MenuItem saveProtocol = new MenuItem("Save protocol...");
        saveProtocol.setGraphic(getIcon("save.png"));
        saveProtocol.setOnAction(e -> {
            saveProtocol();
        });

        MenuItem printerSetup = new MenuItem("Printer Setup...");
        printerSetup.setGraphic(getIcon("document-print.png"));
        printerSetup.setOnAction(e -> {
            // check if Printer exists, if not than create one
            if (fPrinterJob == null) {
                fPrinterJob = PrinterJob.createPrinterJob();
            }

            // if printer exists set it up
            if (fPrinterJob != null) {
                pageLayout();
            }
        });

        MenuItem printDiagram = new MenuItem("Print diagram...");
        printDiagram.setGraphic(getIcon("document-print.png"));
        printDiagram.disableProperty().bind(fActionPrintDiagram.not());
        printDiagram.setOnAction(e -> {
            ResizableInternalWindow activeWindow = getActiveWindow();
            if (activeWindow != null && activeWindow.getDiagramType().isDiagram()) {
                if (fPageLayout == null) {
                    fPageLayout = Printer.getDefaultPrinter().getDefaultPageLayout();
                }
                activeWindow.print(fPageLayout);
            }
        });

        MenuItem printView = new MenuItem("Print View...");
        printView.setGraphic(getIcon("document-print.png"));
        printView.disableProperty().bind(fActionPrintView.not());
        printView.setOnAction(e -> {
            ResizableInternalWindow activeWindow = getActiveWindow();
            if (activeWindow != null && activeWindow.getDiagramType().isView()) {
                if (fPageLayout == null) {
                    fPageLayout = Printer.getDefaultPrinter().getDefaultPageLayout();
                }
                activeWindow.print(fPageLayout);
            }
        });

        MenuItem exportAsPdf = new MenuItem("Export view as PDF...");
        exportAsPdf.setGraphic(getIcon("export_pdf.png"));
        exportAsPdf.disableProperty().bind(fActionExportContentAsPDF.not());
        exportAsPdf.setOnAction(e -> {
            ResizableInternalWindow activeWindow = getActiveWindow();
            if (activeWindow != null && activeWindow.getDiagramType().isExportable()) {
                if (fPageLayout == null) {
                    fPageLayout = Printer.getDefaultPrinter().getDefaultPageLayout();
                }
                activeWindow.exportAsPdf(false);
            }
        });


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
            EvalOCLDialog dlg = new EvalOCLDialog(fSession, primaryStage);
            dlg.showAndWait();
        });

        MenuItem checkSN = new MenuItem("Check structure now");
        checkSN.setAccelerator(KeyCombination.keyCombination("F9"));
        checkSN.disableProperty().bind(fActionSpecificationLoaded.not());
        checkSN.setOnAction(e -> {
            checkStructure();
        });

        fCbMenuItemCheckStructure = new CheckMenuItem("Check structure after every change");
        autoCheckStructure.bind(fCbMenuItemCheckStructure.selectedProperty());

        CheckMenuItem checkSMT = new CheckMenuItem("Check state machine transitions");
        checkSMT.setSelected(Options.getCheckTransitions());
        // Whenever the user toggles the item, update the preference
        checkSMT.selectedProperty().addListener(
                (obs, wasSelected, isSelected) ->
                        Options.setCheckTransitions(isSelected));

        CheckMenuItem checkSIAEC = new CheckMenuItem("Check state invariants after every change");
        checkSIAEC.setSelected(Options.getCheckStateInvariants());
        autoCheckStateInvariants.bind(checkSIAEC.selectedProperty());
        checkSIAEC.selectedProperty().addListener((obs, oldVal, newVal) -> Options.setCheckStateInvariants(newVal));

        MenuItem determine_states = new MenuItem("Determine states");
        determine_states.disableProperty().bind(fActionSpecificationLoaded.not());
        determine_states.setOnAction(e -> {
            fSession.system().determineStates(fLogWriter);
        });

        MenuItem checkStateInvariants = new MenuItem("Check state invariants");
        checkStateInvariants.disableProperty().bind(fActionSpecificationLoaded.not());
        checkStateInvariants.setOnAction(e -> {
            fSession.system().state().checkStateInvariants(fLogWriter);
        });

        MenuItem reset = new MenuItem("Reset");
        reset.disableProperty().bind(fActionSpecificationLoaded.not());
        reset.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Reset system to its initial state and delete all objects and links?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Please confirm");
            alert.getDialogPane().setHeaderText(null); // drop default header
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/use_icon.png"))));
            // --- show & handle -------------------------------------------------------
            alert.showAndWait().filter(btn -> btn == ButtonType.YES).ifPresent(btn -> fSession.reset());
        });

        stateMenuItems.getItems().addAll(createObject, evaluateOCLexpr, checkSN, fCbMenuItemCheckStructure, checkSMT, checkSIAEC, determine_states, checkStateInvariants, reset);
    }

    /**
     * initializing the ViewMenuItems
     */
    private void initViewMenuItems(Menu viewMenuItems) {
        Menu createView = new Menu("Create View");
        MenuItem tile = new MenuItem("Tile");
        MenuItem closeAll = new MenuItem("Close all");

        tile.setOnAction(e -> {
            tileAllWindows();
        });

        closeAll.setOnAction(e -> {
            closeAllWindows();
        });

        MenuItem createClassDiagramViewItem = new MenuItem("Class diagram", getIcon("ClassDiagram.gif"));
        createClassDiagramViewItem.setOnAction(e -> {
            instance.createClassDiagram();
        });

        stateMachineDiagramMenu = new Menu("State machine diagram", getIcon("Diagram.gif"));
        createStateMachineMenuEntries(stateMachineDiagramMenu.getItems());
        stateMachineDiagramMenu.disableProperty().bind(fActionSpecificationLoaded.not());

        MenuItem createObjectDiagramViewItem = new MenuItem("Object diagram ", getIcon("ObjectDiagram.gif"));
        createObjectDiagramViewItem.setOnAction(e -> {
            instance.createObjectDiagram();
        });

        MenuItem createClassInvariantViewItem = new MenuItem("Class invariants", getIcon("invariant-view.png"));
        createClassInvariantViewItem.setOnAction(e -> {
            instance.createClassInvariantView();
        });

        MenuItem createObjectPropertiesViewItem = new MenuItem("Object properties", getIcon("ObjectProperties.gif"));
        createObjectPropertiesViewItem.setOnAction(e -> {
            instance.createObjectPropertiesView();
        });

        MenuItem createClassExtentViewItem = new MenuItem("Class extent", getIcon("ClassExtentView.gif"));
        createClassExtentViewItem.setOnAction(e -> {
            instance.createClassExtentView();
        });
        MenuItem createSequenceDiagramViewItem = new MenuItem("Sequence diagram", getIcon("SequenceDiagram.gif"));
        createSequenceDiagramViewItem.setOnAction(e -> {
            instance.createSequenceDiagramView();
        });
        MenuItem createCommunicationDiagramViewItem = new MenuItem("Communication diagram", getIcon("CommunicationDiagram.gif"));
        createCommunicationDiagramViewItem.setOnAction(e -> {
            instance.createCommunicationDiagramView();
        });
        MenuItem createCallStackViewItem = new MenuItem("Call stack", getIcon("CallStack.gif"));
        createCallStackViewItem.setOnAction(e -> {
            instance.createCallStackView();
        });
        MenuItem createCommandListViewItem = new MenuItem("Command list ", getIcon("CmdList.gif"));
        createCommandListViewItem.setOnAction(e -> {
            instance.createCommandListView();
        });
        MenuItem associationEndsInformation = new MenuItem("Association ends informations ", getIcon("CompositeAggregation.gif"));
        associationEndsInformation.setOnAction(e -> {
            instance.createAssociationEndsInformation();
        });


        // Add MenuItems objects to the SubMenu
        createView.getItems().addAll(createClassDiagramViewItem, stateMachineDiagramMenu, createObjectDiagramViewItem, createClassInvariantViewItem,
                createObjectPropertiesViewItem, createClassExtentViewItem, createSequenceDiagramViewItem, createCommunicationDiagramViewItem,
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
        //Currently Emptyb

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
                        instance.compile(Objects.requireNonNull(Options.getRecentFile("use")));
                        initializeModelBrowserFX(); //reinitializing after compiling
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Print diagram":
                    button.disableProperty().bind(fActionPrintDiagram.not());
                    button.setOnAction(e -> {
                        ResizableInternalWindow activeWindow = getActiveWindow();
                        if (activeWindow != null && activeWindow.getDiagramType().isDiagram()) {
                            if (fPageLayout == null) {
                                fPageLayout = Printer.getDefaultPrinter().getDefaultPageLayout();
                            }
                            activeWindow.print(fPageLayout);
                        }
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Print view":
                    button.disableProperty().bind(fActionPrintView.not());
                    button.setOnAction(e -> {
                        ResizableInternalWindow activeWindow = getActiveWindow();
                        if (activeWindow != null && activeWindow.getDiagramType().isView()) {
                            if (fPageLayout == null) {
                                fPageLayout = Printer.getDefaultPrinter().getDefaultPageLayout();
                            }
                            activeWindow.print(fPageLayout);
                        }
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Export content of view as PDF":
                    button.disableProperty().bind(fActionExportContentAsPDF.not());
                    button.setOnAction(e -> {
                        ResizableInternalWindow activeWindow = getActiveWindow();
                        if (activeWindow != null && activeWindow.getDiagramType().isExportable()) {
                            if (fPageLayout == null) {
                                fPageLayout = Printer.getDefaultPrinter().getDefaultPageLayout();
                            }
                            activeWindow.exportAsPdf(true);
                        }
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Undo last statement":
                    fBtnEditUndo = button;
                    fBtnEditUndo.disableProperty().bind(fActionEditUndo.not());
                    fBtnEditUndo.setTooltip(new Tooltip(DEFAULT_UNDO_TEXT));
                    fBtnEditUndo.setOnAction(e -> {
                        actionEditUndo();
                    });
                    toolBar.getItems().add(fBtnEditUndo);
                    break;
                case "Redo last undone statement":
                    fBtnEditRedo = button;
                    fBtnEditRedo.disableProperty().bind(fActionEditRedo.not());
                    fBtnEditRedo.setTooltip(new Tooltip(DEFAULT_REDO_TEXT));
                    fBtnEditRedo.setOnAction(e -> {
                        actionEditRedo();
                    });
                    toolBar.getItems().add(fBtnEditRedo);
                    break;
                case "Evaluate OCL expression":
                    button.setOnAction(e -> {
                        EvalOCLDialog dlg = new EvalOCLDialog(fSession, primaryStage);
                        dlg.showAndWait();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create class diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> instance.createClassDiagram());
                    toolBar.getItems().add(button);
                    break;
                case "Create statemachine diagram view":

                    // JavaFX has MenuButton / SplitMenuButton – we use MenuButton.
                    MenuButton smBtn = new MenuButton();
                    smBtn.setGraphic(imageKey);
                    smBtn.setTooltip(new Tooltip(tooltipText));
                    smBtn.disableProperty().bind(fActionSpecificationLoaded.not());

                    // Re-populate each time the user opens the drop-down
                    smBtn.setOnShowing(ev -> createStateMachineMenuEntries(smBtn.getItems()));

                    toolBar.getItems().add(smBtn);
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
                    button.setOnAction(e -> {
                        instance.createClassInvariantView();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create object properties view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createObjectPropertiesView();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create class extent view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createClassExtentView();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create sequence diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createSequenceDiagramView();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create communication diagram view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createCommunicationDiagramView();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create call stack view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createCallStackView();
                    });
                    toolBar.getItems().add(button);
                    break;
                case "Create command list view":
                    button.disableProperty().bind(fActionSpecificationLoaded.not());
                    button.setOnAction(e -> {
                        instance.createCommandListView();
                    });
                    toolBar.getItems().add(button);
                    break;
            }

            // Add spacing between specific buttons
            if (i == 4 || i == 6 || i == 7) {
                Region spacer = new Region();
                spacer.setPrefWidth(10);
                toolBar.getItems().add(spacer);
            }
            i++;
        }

    }

    /**
     * Creates a new ResizableInternalWindow and adds it to the desktop.
     */
    public void createNewWindow(String title, SwingNode swingNode, DiagramType diagramType) {
        ResizableInternalWindow window = new ResizableInternalWindow(title, fDesktopPane, fDesktopTaskbarPane, swingNode, this, diagramType);

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
        allDesktopWindows.add(window);

        // Listener for the selected Tab
        setupTabSelectionMessage(window, diagramType);

    }

    /**
     * Closes all windows, removing them from desktop and taskbar (if minimized).
     */
    private void closeAllWindows() {
        // For each window in the list, call its closeWindow() or remove from desktop etc.
        for (ResizableInternalWindow win : allDesktopWindows) {
            win.closeWindow();
        }
        allDesktopWindows.clear();
        setActiveWindow(null);
    }

    /**
     * Tile all windows, inside the custom desktopPane and taskbar (if minimized).
     */
    private void tileAllWindows() {
        List<ResizableInternalWindow> windowsToTile = new ArrayList<>();

        // 1) Re-add minimized windows to desktop and collect all windows
        for (ResizableInternalWindow win : allDesktopWindows) {
            if (win.isMinimized()) {
                win.restoreFromMinimized();
            }
            // Make sure the window is on the desktop
            if (!fDesktopPane.getChildren().contains(win)) {
                fDesktopPane.getChildren().add(win);
            }
            windowsToTile.add(win);
        }


        // 2) Performs the tile arrangement
        // sorting by name of the title
        windowsToTile.sort(Comparator.comparing(ResizableInternalWindow::getTitleText));

        // Grid-Positioning
        int count = windowsToTile.size();
        if (count == 0) return;

        int cols = (int) Math.ceil(Math.sqrt(count));
        int rows = (int) Math.ceil((double) count / cols);

        double paneWidth = fDesktopPane.getWidth();
        double paneHeight = fDesktopPane.getHeight();

        double tileWidth = paneWidth / cols;
        double tileHeight = paneHeight / rows;

        for (int i = 0; i < count; i++) {
            ResizableInternalWindow win = windowsToTile.get(i);
            int col = i % cols;
            int row = i / cols;

            win.setLayoutX(col * tileWidth);
            win.setLayoutY(row * tileHeight);
            win.setPrefWidth(tileWidth);
            win.setPrefHeight(tileHeight);
            win.toFront();
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
     * Opens a “Save script” dialog, confirms overwriting if required,
     * and writes the current USE session to a *.soil* file.
     */
    private void saveScript() {
        String path;

        /* ------------------------------------------------------------------
         * 1. Build a fresh FileChooser configured for *.soil files
         * ------------------------------------------------------------------ */
        FileChooser fChooser = new FileChooser();
        // Start in the directory the user visited last time
        fChooser.setInitialDirectory(Options.getLastDirectory().toFile());
        // Filter so the user sees only “soil scripts (*.soil)”
        fChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("soil scripts (*.soil)", "*.soil"));
        fChooser.setTitle("Save script");

        /* ------------------------------------------------------------------
         * 2. Show the “Save” dialog
         * ------------------------------------------------------------------ */
        File chosen = fChooser.showSaveDialog(primaryStage);
        // null == user pressed Cancel or closed the dialog
        if (chosen == null) {
            return;
        }

        /* ------------------------------------------------------------------
         * 3. Normalise path/filename and log for diagnostics
         * ------------------------------------------------------------------ */
        path = chosen.getParent();
        String filename = chosen.getName();
        if (!filename.endsWith(".soil")) {
            filename += ".soil";    // force the extension
        }
        File f = new File(path, filename);
        Log.verbose("File " + f);

        /* ------------------------------------------------------------------
         * 4. Confirm overwrite if the file already exists
         * ------------------------------------------------------------------ */
        if (f.exists()) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Overwrite existing file" + f + "?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            confirm.initOwner(primaryStage);
            confirm.setHeaderText(null);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.YES) {
                return;
            }
        }

        /* ------------------------------------------------------------------
         * 5. Write the script file
         * ------------------------------------------------------------------ */
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)))) {
            // Header comment + blank line
            out.println("-- Script generated by USE " + Options.RELEASE_VERSION);
            out.println();

            // Dump the session’s SOIL statements
            fSession.system().writeSoilStatements(out);

            // Append a line to the GUI log area
            fLogTextArea.appendText("Wrote script " + f + "\n");

        } catch (IOException ex) {
            /* --------------------------------------------------------------
             * 6. Show any I/O error to the user
             * -------------------------------------------------------------- */
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.initOwner(primaryStage);
            alert.setHeaderText("Error");
            alert.showAndWait();
        }
    }

    /**
     * Opens a “Save protocol” dialog, confirms overwriting, and writes the
     * protocol text file produced by {@code USEWriter}.
     */
    private void saveProtocol() {
        String path;

        FileChooser fChooser = new FileChooser();
        fChooser.setInitialDirectory(Options.getLastDirectory().toFile());
        fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Textfiles (*.txt)", "*.txt"));
        fChooser.setTitle("Save protocol");


        // Show the Save dialog
        File chosen = fChooser.showSaveDialog(primaryStage);
        if (chosen == null) {
            return; // user cancelled
        }

        // normalise filename & build target File
        path = chosen.getParent();
        String filename = chosen.getName();
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        File f = new File(path, filename);

        // check if file exists and ask before overwriting it
        if (f.exists()) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Overwrite existing file" + f + "?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            confirm.initOwner(primaryStage);
            confirm.setHeaderText(null);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.YES) {
                return;
            }
        }

        // Write the protocol file
        try (FileOutputStream fOut = new FileOutputStream(f)) {
            USEWriter.getInstance().writeProtocolFile(fOut);
            // auto-flush + auto-close | Because try-catch handles it automatically
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.initOwner(primaryStage);
            alert.setHeaderText("Error");
            alert.showAndWait();
        }
    }

    /**
     * Undoes the last executed statement in the system.
     * Shows alerts if the system is busy or an error occurs.
     */
    public void actionEditUndo() {
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
            alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/use_icon.png"))));
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

        //setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        ClassDiagram.setJavaFxCall(true); // so that class diagrams don't save any state

        // Calling the Swing MainWindow to get the ClassDiagram out of it
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        ClassDiagramView cdv = new ClassDiagramView(mainwindow, fSession.system(), loadLayout);
        ViewFrame f = new ViewFrame("Class diagram", cdv, "ClassDiagram.gif");

        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(cdv, BorderLayout.CENTER);
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Class diagram", swingNode, DiagramType.CLASS_DIAGRAM);
    }

    /**
     * Rebuilds the list of state-machine menu-items for the given JavaFX Menu.
     */
    private void createStateMachineMenuEntries(ObservableList<MenuItem> destination) {
        destination.clear();

        int elems = 0;
        if (fSession != null && fSession.hasSystem()) {
            for (MClass cls : fSession.system().model().classes()) {
                for (var sm : cls.getOwnedProtocolStateMachines()) {
                    MenuItem item = new MenuItem(cls.name() + "::" + sm.name());
                    item.setOnAction(ev -> showStateMachineView(sm));
                    destination.add(item);
                    ++elems;
                }
            }
        }
        if (elems == 0) {
            MenuItem none = new MenuItem("No statemachines available");
            none.setDisable(true);
            destination.add(none);
        }
    }

    /**
     * Opens a State-Machine diagram for the given machine inside a ResizableInternalWindow.
     */
    private void showStateMachineView(org.tzi.use.uml.mm.statemachines.MStateMachine sm) {
        SwingNode node = new SwingNode();

        // Make Swing think we run head-less
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);

        org.tzi.use.gui.main.MainWindow swingMW =
                org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        StateMachineDiagramView dv = new StateMachineDiagramView(swingMW, fSession.system(), sm);

        ViewFrame vf = new ViewFrame("State machine " + StringUtil.inQuotes(sm.name()), dv, "Diagram.gif");
        JComponent content = (JComponent) vf.getContentPane();
        content.setLayout(new java.awt.BorderLayout());
        content.add(dv, java.awt.BorderLayout.CENTER);

        node.setContent(content);
        node.setCache(false);

        createNewWindow("State machine " + StringUtil.inQuotes(sm.name()), node, DiagramType.STATE_MACHINE_DIAGRAM);
    }

    /**
     * Creates a new object diagram view.
     */
    private void createObjectDiagram() {

        // to create an Intance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the NewObjectDiagramView and the enclosing ViewFrame
        NewObjectDiagramView odv = new NewObjectDiagramView(mainwindow, fSession.system());
        ViewFrame f = new ViewFrame("Object diagram", odv, "ObjectDiagram.gif");


        // on changes of session the object view diagrams are being updated!
        fSession.addChangeListener(event -> {
            Platform.runLater(odv::updateUI);
        });

        // Check if the system has too many objects and prompt the user
        int OBJECTS_LARGE_SYSTEM = 100;
        if (fSession.system().state().allObjects().size() > OBJECTS_LARGE_SYSTEM) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Large system state");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/use_icon.png"))));
            alert.getDialogPane().setHeaderText(null);
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
        c.requestFocusInWindow();
        c.add(odv, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Object diagram", swingNode, DiagramType.OBJECT_DIAGRAM);
    }

    /**
     * Creates a new class invariant view.
     */
    private void createClassInvariantView() {
        // to create an Intance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the ClassInvariantView and the enclosing ViewFrame
        ClassInvariantView civ = new ClassInvariantView(mainwindow, fSession.system());
        ViewFrame f = new ViewFrame("Class invariants", civ, "InvariantView.gif");
        civ.setViewFrame(f);

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(civ, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Class invariants", swingNode, DiagramType.CLASS_INVARIANT_VIEW);
    }

    /**
     * Creates a new Object Properties view.
     */
    private void createObjectPropertiesView() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the ClassInvariantView and the enclosing ViewFrame
        ObjectPropertiesView opv = new ObjectPropertiesView(mainwindow, fSession.system());
        ViewFrame f = new ViewFrame("Object properties", opv, "ObjectProperties.gif");

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Object properties", swingNode, DiagramType.OBJECT_PROPERTIES_VIEW);
    }

    /**
     * Creates a new Class Extent view.
     */
    private void createClassExtentView() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the ClassExtentView and the enclosing ViewFrame
        ClassExtentView cev = new ClassExtentView(mainwindow, fSession.system());
        ViewFrame f = new ViewFrame("Class extent", cev, "ClassExtentView.gif");

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(cev, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Class extent", swingNode, DiagramType.CLASS_EXTENT_VIEW);
    }

    /**
     * Creates a new Sequence Diagram view.
     */
    private void createSequenceDiagramView() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the ClassExtentView and the enclosing ViewFrame
        SequenceDiagramView sdv = SequenceDiagramView.createSequenceDiagramView(fSession.system(), mainwindow, null);
        ViewFrame f = new ViewFrame("Sequence diagram", sdv, "SequenceDiagram.gif");

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new SDScrollPane(sdv), BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Sequence diagram", swingNode, DiagramType.SEQUENCE_DIAGRAM);
    }

    /**
     * Creates a new Communication Diagram view.
     */
    private void createCommunicationDiagramView() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the ClassExtentView and the enclosing ViewFrame
        CommunicationDiagramView cdv = CommunicationDiagramView.createCommunicationDiagramm(mainwindow, fSession.system(), VisibleDataManager.createVisibleDataManager(fSession.system()));
        ViewFrame f = new ViewFrame("Communication diagram", cdv, "CommunicationDiagram.gif");

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(cdv, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Communication diagram", swingNode, DiagramType.COMMUNICATION_DIAGRAM);
    }

    /**
     * Creates a new Call Stack view.
     */
    private void createCallStackView() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the CallStackView and the enclosing ViewFrame
        CallStackView csv = new CallStackView(fSession.system());
        ViewFrame f = new ViewFrame("Call stack", csv, "CallStack.gif");

        // Set up the SwingNode content
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(csv);
        jsp.getVerticalScrollBar().setUnitIncrement(16);
        c.add(jsp, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Call stack", swingNode, DiagramType.CALL_STACK_VIEW);
    }

    /**
     * Creates a new Command List view.
     */
    private void createCommandListView() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the ClassExtentView and the enclosing ViewFrame
        CommandView clv = new CommandView(fSession.system());
        ViewFrame f = new ViewFrame("Command list", clv, "CmdList.gif");
        JComponent c = (JComponent) f.getContentPane();
        JScrollPane jsp = new JScrollPane(clv);
        jsp.getVerticalScrollBar().setUnitIncrement(16);
        c.setLayout(new BorderLayout());
        c.add(clv, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Command list", swingNode, DiagramType.COMMAND_LIST_VIEW);
    }

    /**
     * Creates a new Association Ends Information view.
     */
    private void createAssociationEndsInformation() {
        // to create an instance of a SwingNode, which is used to hold the Swing-Components
        swingNode = new SwingNode();

        // setting the visibility of the MainWindow (Swing Gui) to false because we don't want it to be shown
        org.tzi.use.gui.main.MainWindow.setJavaFxCall(true);
        NewObjectDiagram.setJavaFxCall(true); // so that NewObjectDiagram doesn't save any state

        // Create the Swing MainWindow instance
        org.tzi.use.gui.main.MainWindow mainwindow = org.tzi.use.gui.main.MainWindow.create(fSession, fPluginRuntime);

        // Create the ClassExtentView and the enclosing ViewFrame
        AssociationEndsInfo clv = new AssociationEndsInfo(mainwindow, fSession.system());
        ViewFrame f = new ViewFrame("Association ends information", clv, "Association.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(clv);
        jsp.getVerticalScrollBar().setUnitIncrement(16);
        c.add(clv, BorderLayout.CENTER);

        // Add the Swing component to the SwingNode
        swingNode.setContent(c);
        swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

        // creating the new Window with the swingNode
        createNewWindow("Association ends information", swingNode, DiagramType.ASSOCIATION_INFO_VIEW);
    }

    // Actions //

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
            system.getEventBus().register(this);
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
        fStatusBar.clearMessage();
        if (fSession.system() != null && fSession.system().model() != null) {
            new ModelBrowser(fSession.system().model(), fPluginRuntime);
        } else {
            new ModelBrowser(null, fPluginRuntime);
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
    public void initFolderTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("No model available");
        folderTreeView.setRoot(rootItem);

        // Verwende die neue Klasse, um das Kontextmenü zu erstellen
        FolderTreeContextMenu contextMenuHandler = new FolderTreeContextMenu(fSession, fPluginRuntime);
        ContextMenu contextMenu = contextMenuHandler.createContextMenu();

        folderTreeView.setContextMenu(contextMenu);
    }

    /**
     * closes all childnodes of the FolderTreeView recursively
     */
    public void repaintFolderTreeView() {
        TreeItem<?> root = folderTreeView.getRoot();
        if (root == null) return;

        // closing all childnodes recursively
        collapseRecursively(root);

        // clearing selection
        folderTreeView.getSelectionModel().clearSelection();

        // refresh the tree
        folderTreeView.refresh();
    }

    private void collapseRecursively(TreeItem<?> item) {
        // starts with child first
        for (TreeItem<?> child : item.getChildren()) {
            collapseRecursively(child);
        }
        // all collapsed except root
        if (item != folderTreeView.getRoot()) {
            item.setExpanded(false);
        }
    }

    /**
     * this Method creates menu-items for each recent specification and
     * ads a seperator after each has been created, to include at last an
     * menu-item (clearRecentSpecifications) to clear these menu-items
     * (also the recent files in the Options)
     */
    public void setRecentFiles() {
        if (recentFilesMenu.getItems() != null) {
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
        fSession = session;
    }

    // setter für IRuntime
    public void setPluginRuntime(IRuntime pluginRuntime) {
        fPluginRuntime = pluginRuntime;
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

    @Subscribe
    public void onStructureChanged(SystemStructureChangedEvent e) {
        if (!autoCheckStructure.get()) return;
        Platform.runLater(this::checkStructure);
    }

    @Subscribe
    public void onSystemChanged(SystemStateChangedEvent ev) {
        if (!autoCheckStateInvariants.get()) return;
        Platform.runLater(() -> {
            fLogWriter.println("Checking state invariants.");
            fSession.system().state().checkStateInvariants(fLogWriter);
        });
    }


    private void checkStructure() {
        boolean ok = fSession.system().state().checkStructure(fLogWriter);
        fLogWriter.println("checking structure, "
                + ((ok) ? "ok." : "found errors."));
        fLogWriter.flush();
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionSaveScript), according to if scripts are available.
     */
    public void updateFActionSaveScript() {
        if (fSession.hasSystem()) {
            fActionSaveScript.set(fSession.system().numEvaluatedStatements() > 0);
        }
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionPrintDiagram), according to if Diagrams are available.
     */
    public void updateFActionDiagramPrinter() {
        fActionPrintDiagram.set(false);
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionPrintView), according to if Views are available.
     */
    public void updateFActionViewPrinter() {
        fActionPrintView.set(false);
    }

    /**
     * This Method updates the Status of the BooleanProperty (fActionExportContentAsPDF), according to if contents of View are available.
     */
    public void updateFActionExportContentAsPDF() {
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
        Image image = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/images/" + name)));
        // Create an ImageView to display the image
        return new ImageView(image);
    }

    /**
     * Returns the instance of MainWindow.
     */
    public static MainWindow getInstance() {
        return instance;
    }

    /**
     * Updates the page format for printing.
     */
    private void pageLayout() {
        boolean proceed = fPrinterJob.showPageSetupDialog(null); // Show printer setup dialog
        if (proceed) {
            fPageLayout = fPrinterJob.getJobSettings().getPageLayout();

            // Update options with selected settings
            Options.PRINT_PAGEFORMAT_WIDTH = fPageLayout.getPaper().getWidth();
            Options.PRINT_PAGEFORMAT_HEIGHT = fPageLayout.getPaper().getHeight();

            switch (fPageLayout.getPageOrientation()) {
                case PORTRAIT:
                    Options.PRINT_PAGEFORMAT_ORIENTATION = "portrait";
                    break;
                case LANDSCAPE:
                    Options.PRINT_PAGEFORMAT_ORIENTATION = "landscape";
                    break;
                case REVERSE_LANDSCAPE:
                    Options.PRINT_PAGEFORMAT_ORIENTATION = "seascape";
                    break;
            }
        }
    }

    /**
     * Sets up a tab to display a temporary message in the status bar when selected.
     *
     * @param window      the window to which the selection behavior will be applied
     * @param diagramType the temporary message to display filtered by DiagramType of the selected tab
     */
    public void setupTabSelectionMessage(ResizableInternalWindow window, DiagramType diagramType) {
        String tmpMessage;
        if (window.isActive()) {
            if (diagramType == DiagramType.CLASS_DIAGRAM) {
                tmpMessage = "Use left mouse button to move classes, right button for popup menu.";
            } else if (diagramType == DiagramType.OBJECT_DIAGRAM) {
                tmpMessage = "Use left mouse button to move objects, right button for popup menu.";
            } else if (diagramType == DiagramType.COMMUNICATION_DIAGRAM) {
                tmpMessage = "Use left mouse button to move actor, object and link boxes, right button for popup menu.";
            } else if (diagramType == DiagramType.CLASS_INVARIANT_VIEW) {
                tmpMessage = "Use right mouse button for popup menu.";
            } else if (diagramType == DiagramType.CLASS_EXTENT_VIEW) {
                tmpMessage = "Use right mouse button for popup menu.";
            } else if (diagramType == DiagramType.STATE_MACHINE_DIAGRAM) {
                tmpMessage = "Use left mouse button to move objects, right button for popup menu.";
            } else {
                tmpMessage = "Ready.";
            }
            fStatusBar.showTmpMessage(tmpMessage);
        } else {
            fStatusBar.clearMessage();
        }
    }

    /**
     * returns the active Window on the DesktopPane
     */
    public ResizableInternalWindow getActiveWindow() {
        return activeWindow.get();
    }

    /**
     * returns the active Statusbar
     */
    public StatusBar getfStatusBar() {
        return fStatusBar;
    }

    /**
     * setting the active Window
     */
    public void setActiveWindow(ResizableInternalWindow window) {
        activeWindow.set(window);

        // Deactivate all other windows
        for (ResizableInternalWindow win : allDesktopWindows) {
            win.setActive(win == window);
        }

        if (window != null) {
            // Updating the StatusBar
            setupTabSelectionMessage(window, window.getDiagramType());
        }
    }

    /**
     * removing closed window out of the desktopWindowsList
     */
    public void removeClosedWindow(ResizableInternalWindow window) {
        allDesktopWindows.remove(window);
        if (allDesktopWindows.isEmpty()) {
            setActiveWindow(null);
        }
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
        alertStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/use_icon.png"))));
        alert.showAndWait();
    }

}
