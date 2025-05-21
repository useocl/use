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

package org.tzi.use.gui.viewsFX.evalbrowser;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.tzi.use.gui.mainFX.MainWindow;
import org.tzi.use.main.gui.Main;
import org.tzi.use.uml.ocl.expr.*;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;
import org.tzi.use.uml.sys.MSystem;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A tree view showing evaluation results of an expression. Each node is adorned
 * with the result of evaluating the sub-expression.
 *
 * @author Akif Aydin
 */
public class ExprEvalBrowser extends BorderPane {

    private TreeView<EvalNode> treeView;
    private ListView<Entry> varAssList;
    private WebView substituteWebView;
    private WebView topHtmlView;
    private ComboBox<String> comboTreeDisplays;
    private SplitPane mainSplitPane;
    private SplitPane rightSplitPane;

    final CheckMenuItem fExtendedExists = new CheckMenuItem("exists");
    final CheckMenuItem fExtendedForAll = new CheckMenuItem("forAll");
    final CheckMenuItem fExtendedAnd = new CheckMenuItem("and");
    final CheckMenuItem fExtendedOr = new CheckMenuItem("or");
    final CheckMenuItem fExtendedImplies = new CheckMenuItem("implies");

    final CheckMenuItem cbSubstituteVariables = new CheckMenuItem("Variable Substitution");

    private final ToggleGroup highlightGroup = new ToggleGroup();
    private final RadioMenuItem highlightNone = new RadioMenuItem("No highlighting");
    final RadioMenuItem highlightTerm = new RadioMenuItem("Term highlighting");
    final RadioMenuItem highlightSubtree = new RadioMenuItem("Subtree highlighting");
    final RadioMenuItem highlightComplete = new RadioMenuItem("Complete subtree highlighting");
    private final ToggleGroup varAssignGroup = new ToggleGroup();
    private final RadioMenuItem rbLate = new RadioMenuItem("Late");
    private final RadioMenuItem rbEarly = new RadioMenuItem("Early");
    private final RadioMenuItem rbNever = new RadioMenuItem("Never");

    final CheckMenuItem fNoColorHighlightingChk = new CheckMenuItem("Black and white");

    final CheckMenuItem cbVarAssList = new CheckMenuItem("Variable assignment window");
    final CheckMenuItem cbSubstitutionView = new CheckMenuItem("Subexpression evaluation window");

    private final MenuItem miSetAll = new MenuItem("Set all");
    private final MenuItem miSetNone = new MenuItem("Set none");
    private final MenuItem miCapture = new MenuItem("Capture to file");

    private ShowVariableAssignment varAssignmentMode = ShowVariableAssignment.LATE;
    private EvalNode originalRoot;

    public enum ShowVariableAssignment {
        LATE,
        EARLY,
        NEVER
    }

    private ContextMenu configMenu;

    public ExprEvalBrowser(EvalNode root, MSystem system) {
        initLayout(root);
    }

    private void initLayout(EvalNode root) {
        this.originalRoot = root;

        miSetAll.setOnAction(e -> {
            fExtendedExists.setSelected(true);
            fExtendedForAll.setSelected(true);
            fExtendedAnd.setSelected(true);
            fExtendedOr.setSelected(true);
            fExtendedImplies.setSelected(true);
            rebuildTree();
        });

        miSetNone.setOnAction(e -> {
            fExtendedExists.setSelected(false);
            fExtendedForAll.setSelected(false);
            fExtendedAnd.setSelected(false);
            fExtendedOr.setSelected(false);
            fExtendedImplies.setSelected(false);
            rebuildTree();
        });

        miCapture.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Snapshot");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
            fileChooser.setInitialFileName("evalbrowser_capture.png");
            File file = fileChooser.showSaveDialog(this.getScene().getWindow());

            if (file != null) {
                WritableImage image = this.snapshot(new SnapshotParameters(), null);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    System.out.println("Capture saved: " + file.getAbsolutePath());
                } catch (IOException ex) {
                    new Alert(Alert.AlertType.ERROR, "Error saving image:\n" + ex.getMessage()).showAndWait();
                }
            }
        });

        // 🔼 Top Label
        topHtmlView = new WebView();
        topHtmlView.setPrefHeight(32); // adjust as needed
        topHtmlView.setContextMenuEnabled(false); // optional: disable context menu
        topHtmlView.getEngine().loadContent(
                "<html><head><style>body { font-family: sans-serif; font-size: 14px; }</style></head><body>" +
                        root.getExpressionString(false) +
                        "</body></html>"
        );
        setTop(topHtmlView);

        // 🌳 TreeView
        TreeItem<EvalNode> rootItem = createTreeItems(root);
        applyFolderIcons(rootItem);
        rootItem.setExpanded(true);
        treeView = new TreeView<>(rootItem);
        treeView.setShowRoot(true);
        treeView.setCellFactory(tv -> new EvalNodeTreeCell());
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                EvalNode selectedNode = newItem.getValue();

                // 🧾 1. Variablenbindung anzeigen
                List<Entry> bindings = selectedNode.getVarBindings();
                varAssList.getItems().setAll(bindings);

                // 🌐 2. HTML Substitution anzeigen
                String html = "<html><head><style>body { font-family: sans-serif; }</style></head><body>"
                        + selectedNode.substituteChildExpressions()
                        + "</body></html>";

                substituteWebView.getEngine().loadContent(html);
            }
        });
        configMenu = createConfigMenu();
        treeView.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                // Get the clicked position relative to tree view
                double x = event.getX();
                double y = event.getY();

                // Check if click was within content bounds
                if (y < treeView.getLayoutBounds().getHeight()) {
                    int row = (int)(y / treeView.getFixedCellSize());
                    TreeItem<EvalNode> clickedItem = treeView.getTreeItem(row);

                    if (clickedItem != null) {
                        treeView.getSelectionModel().select(clickedItem);
                        ContextMenu itemMenu = createContextMenu(clickedItem);
                        itemMenu.show(treeView, event.getScreenX(), event.getScreenY());
                        return;
                    }
                }

                // If we get here, it's an empty space click
                handleEmptySpaceClick(event);
            }
        });

        // Left SplitPane
        ScrollPane treeScrollPane = new ScrollPane(treeView);
        treeScrollPane.setFitToWidth(true);
        treeScrollPane.setFitToHeight(true);

        // 📋 Variable Assignment List
        varAssList = new ListView<>();

        // 🌐 Substitution View (HTML)
        substituteWebView = new WebView();

        // 📤 Right SplitPane (vertical)
        rightSplitPane = new SplitPane();
        rightSplitPane.setOrientation(Orientation.VERTICAL);
        rightSplitPane.getItems().addAll(varAssList, substituteWebView);
        rightSplitPane.setDividerPositions(0.5);

        // ↔ Main SplitPane
        mainSplitPane = new SplitPane(treeScrollPane, rightSplitPane);
        SplitPane.setResizableWithParent(treeScrollPane, true);
        SplitPane.setResizableWithParent(rightSplitPane, true);
        if (rightSplitPane.getItems().isEmpty()) {
            mainSplitPane.setDividerPositions(1.0); // Only TreeView visible
        } else {
            mainSplitPane.setDividerPositions(0.6); // Tree gets 60%, right pane 40%
        }
        setCenter(mainSplitPane);

        // ⬇ Bottom Controls
        comboTreeDisplays = new ComboBox<>();
        comboTreeDisplays.setPromptText("Display options");
        comboTreeDisplays.getItems().addAll("Expand all", "Expand all true", "Expand all false", "Collapse");
        comboTreeDisplays.setOnAction(e -> handleComboAction(comboTreeDisplays.getValue()));

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> closeWindow());

        HBox bottomBar = new HBox(5, comboTreeDisplays, closeBtn);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(5));
        setBottom(bottomBar);

        updateRightSplitPane();
        loadDefaultConfiguration();
        updateRightSplitPane();
    }

    public static Stage create(EvalNode root, MSystem sys) {
        ExprEvalBrowser view = new ExprEvalBrowser(root, sys);
        Stage stage = new Stage();
        stage.setTitle("Evaluation Browser");
        stage.setScene(new Scene(view));
        stage.setWidth(800);
        stage.setHeight(600);
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/useLogo.gif"))));
        stage.show();
        return stage;
    }

    private ContextMenu createConfigMenu() {
        // 🔧 Erweiterte Evaluation
        Menu extendedMenu = new Menu("Extended evaluation");
        extendedMenu.getItems().addAll(fExtendedExists, fExtendedForAll, fExtendedAnd, fExtendedOr, fExtendedImplies, new SeparatorMenuItem(), miSetAll, miSetNone);

        MenuItem miLoadDefault = new MenuItem("Default configuration");
        miLoadDefault.setOnAction(e -> loadDefaultConfiguration());

        MenuItem miSetDefault = new MenuItem("Set as default");
        miSetDefault.setOnAction(e -> new SetDefaultDialog(this));

        // 🧩 Substitution
        cbSubstituteVariables.setOnAction(e -> rebuildTree());
        // 2. Variable Assignment Mode Menu
        rbLate.setToggleGroup(varAssignGroup);
        rbEarly.setToggleGroup(varAssignGroup);
        rbNever.setToggleGroup(varAssignGroup);
        rbLate.setSelected(varAssignmentMode == ShowVariableAssignment.LATE);
        rbEarly.setSelected(varAssignmentMode == ShowVariableAssignment.EARLY);
        rbNever.setSelected(varAssignmentMode == ShowVariableAssignment.NEVER);
        rbLate.setSelected(true);

        rbLate.setOnAction(e -> switchVariableAssignmentMode(ShowVariableAssignment.LATE));
        rbEarly.setOnAction(e -> switchVariableAssignmentMode(ShowVariableAssignment.EARLY));
        rbNever.setOnAction(e -> switchVariableAssignmentMode(ShowVariableAssignment.NEVER));

        Menu variableAssignmentMenu = new Menu("Show variable assignments");
        variableAssignmentMenu.getItems().addAll(rbLate, rbEarly, rbNever, new SeparatorMenuItem(), cbSubstituteVariables);

        // 🧠 Highlighting
        highlightNone.setToggleGroup(highlightGroup);
        highlightNone.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) refreshTree();
        });
        highlightTerm.setToggleGroup(highlightGroup);
        highlightTerm.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) refreshTree();
        });
        highlightSubtree.setToggleGroup(highlightGroup);
        highlightSubtree.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) refreshTree();
        });
        highlightComplete.setToggleGroup(highlightGroup);
        highlightComplete.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) refreshTree();
        });
        // For black/white checkbox
        fNoColorHighlightingChk.selectedProperty().addListener((obs, old, newVal) -> {
            refreshTree();
        });
        highlightNone.setSelected(true);

        Menu highlightMenu = new Menu("True-false highlighting");
        highlightMenu.getItems().addAll(highlightNone, highlightTerm, highlightSubtree, highlightComplete, new SeparatorMenuItem(), fNoColorHighlightingChk);

        cbVarAssList.setOnAction(e -> updateRightSplitPane());
        cbSubstitutionView.setOnAction(e -> updateRightSplitPane());

        return new ContextMenu(
                extendedMenu,
                new SeparatorMenuItem(),
                cbVarAssList,
                cbSubstitutionView,
                variableAssignmentMenu,
                highlightMenu,
                new SeparatorMenuItem(),
                miLoadDefault,
                miSetDefault,
                new SeparatorMenuItem(),
                miCapture
        );
    }

    private void switchVariableAssignmentMode(ShowVariableAssignment newMode) {
        this.varAssignmentMode = newMode;
        rebuildTree();  // This already handles saving and restoring expanded state
    }


    private void loadDefaultConfiguration() {
        // Restore highlighting mode
        String hl = System.getProperty("use.gui.view.evalbrowser.highlighting", "no");
        switch (hl) {
            case "term" -> highlightTerm.setSelected(true);
            case "subtree" -> highlightSubtree.setSelected(true);
            case "complete" -> highlightComplete.setSelected(true);
            default -> highlightNone.setSelected(true);
        }

        cbVarAssList.setSelected(Boolean.parseBoolean(System.getProperty("use.gui.view.evalbrowser.VarAssignmentWindow", "false")));
        cbSubstitutionView.setSelected(Boolean.parseBoolean(System.getProperty("use.gui.view.evalbrowser.SubExprSubstitutionWindow", "false")));
        fExtendedExists.setSelected(getBoolean("use.gui.view.evalbrowser.exists", true));
        fExtendedForAll.setSelected(getBoolean("use.gui.view.evalbrowser.forall", true));
        fExtendedAnd.setSelected(getBoolean("use.gui.view.evalbrowser.and", true));
        fExtendedOr.setSelected(getBoolean("use.gui.view.evalbrowser.or", true));
        fExtendedImplies.setSelected(getBoolean("use.gui.view.evalbrowser.implies", true));
        cbSubstituteVariables.setSelected(getBoolean("use.gui.view.evalbrowser.substitution", false));
        fNoColorHighlightingChk.setSelected(Boolean.parseBoolean(System.getProperty("use.gui.view.evalbrowser.blackHighlighting", "false")));
        String mode = System.getProperty("use.gui.view.evalbrowser.treeview", "late");
        switch (mode) {
            case "early" -> switchVariableAssignmentMode(ShowVariableAssignment.EARLY);
            case "never" -> switchVariableAssignmentMode(ShowVariableAssignment.NEVER);
            default -> switchVariableAssignmentMode(ShowVariableAssignment.LATE);
        }
    }

    private boolean getBoolean(String key, boolean defaultVal) {
        return Boolean.parseBoolean(System.getProperty(key, Boolean.toString(defaultVal)));
    }

    private void updateRightSplitPane() {
        rightSplitPane.getItems().clear();

        boolean showVarList = cbVarAssList.isSelected();
        boolean showSubstitution = cbSubstitutionView.isSelected();

        if (!showVarList && !showSubstitution) {
            mainSplitPane.getItems().setAll(treeView); // or treeScrollPane if that's what you used
            return;
        }

        if (!mainSplitPane.getItems().contains(rightSplitPane)) {
            mainSplitPane.getItems().setAll(treeView, rightSplitPane);
            mainSplitPane.setDividerPositions(0.6);
        }

        if (showVarList && showSubstitution) {
            rightSplitPane.getItems().setAll(varAssList, substituteWebView);
            rightSplitPane.setDividerPositions(0.5);
        } else if (showVarList) {
            rightSplitPane.getItems().setAll(varAssList);
        } else if (showSubstitution) {
            rightSplitPane.getItems().setAll(substituteWebView);
        }
    }


    private void rebuildTree() {
        Set<String> expandedExprs = new HashSet<>();

        // Only try to collect if tree is already initialized
        if (treeView != null && treeView.getRoot() != null) {
            collectExpandedItems(treeView.getRoot(), expandedExprs);
        }

        TreeItem<EvalNode> newRoot = createTreeItems(originalRoot);
        treeView.setRoot(newRoot);

        restoreExpandedItems(newRoot, expandedExprs);
        newRoot.setExpanded(true);
    }

    private ContextMenu createContextMenu(TreeItem<EvalNode> treeItem) {
        ContextMenu menu = new ContextMenu();

        MenuItem expandItem = new MenuItem("Expand");
        expandItem.setOnAction(e -> treeItem.setExpanded(true));

        MenuItem collapseItem = new MenuItem("Collapse");
        collapseItem.setOnAction(e -> treeItem.setExpanded(false));

        MenuItem expandAllItem = new MenuItem("Expand all");
        expandAllItem.setOnAction(e -> expandAll(treeItem));

        MenuItem collapseAllItem = new MenuItem("Collapse all");
        collapseAllItem.setOnAction(e -> collapseAll(treeItem));

        MenuItem copyItem = new MenuItem("Copy Expression");
        copyItem.setOnAction(e -> copyToClipboard(treeItem.getValue()));
        menu.getItems().addAll(
                expandItem, collapseItem,
                new SeparatorMenuItem(),
                expandAllItem, collapseAllItem,
                new SeparatorMenuItem(),
                copyItem
        );
        return menu;
    }

    private void copyToClipboard(EvalNode node) {
        ClipboardContent content = new ClipboardContent();
        content.putString(node.getExpressionStringRaw(isSubstituteVars()));
        Clipboard.getSystemClipboard().setContent(content);
    }

    private void handleEmptySpaceClick(MouseEvent event) {
        // Clear selection
        treeView.getSelectionModel().clearSelection();

        // Clear detail views
        varAssList.getItems().clear();
        substituteWebView.getEngine().loadContent("");

        // Show configuration menu
        configMenu.show(treeView, event.getScreenX(), event.getScreenY());
    }


    private EvalNode.TreeValue getNodeTreeValue(EvalNode node) {
        Value value = node.getResult();
        Type type = node.getExpression().type();

        if (value.equals(BooleanValue.TRUE)) {
            return EvalNode.TreeValue.TRUE;
        } else if (value.equals(BooleanValue.FALSE)) {
            return EvalNode.TreeValue.FALSE;
        } else if (value.equals(UndefinedValue.instance) && type.isTypeOfBoolean()) {
            return EvalNode.TreeValue.UNDEFINED;
        } else {
            return EvalNode.TreeValue.INVALID;
        }
    }

    private class EvalNodeTreeCell extends TreeCell<EvalNode> {
        private final WebView webView = new WebView();
        private final StackPane pane = new StackPane(webView);

        public EvalNodeTreeCell() {
            // Configure WebView
            webView.setPrefHeight(20);
            webView.setMaxWidth(Double.MAX_VALUE);
            webView.setContextMenuEnabled(false);

            webView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    getTreeView().getSelectionModel().select(getTreeItem());

                    ContextMenu itemMenu = createContextMenu(getTreeItem());
                    itemMenu.show(webView, e.getScreenX(), e.getScreenY());

                    e.consume(); // Prevent further propagation
                }
            });

            webView.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                    TreeItem<EvalNode> treeItem = getTreeItem();
                    if (treeItem != null && !treeItem.isLeaf()) {
                        treeItem.setExpanded(!treeItem.isExpanded());
                    }
                    e.consume();
                }
            });

            // Make entire cell clickable
            setOnMouseClicked(e -> {
                if (!isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                    getTreeView().getSelectionModel().select(getTreeItem());
                }
            });
        }

        @Override
        protected void updateItem(EvalNode item, boolean empty) {
            super.updateItem(item, empty);
            boolean isSelected = getTreeView().getSelectionModel().getSelectedItem() == getTreeItem();

            if (empty || item == null) {
                setGraphic(null);
                setStyle("");
            } else {
                // Determine style
                boolean blackBackground = fNoColorHighlightingChk.isSelected()
                        && highlightTerm.isSelected()
                        && getHighlightValue(item) == EvalNode.TreeValue.FALSE;

                boolean pressedHighlight = isSelected;

                String textColor = getHighlightColor(item);
                boolean isBold = shouldBold(item);

                String backgroundStyle = "";
                if (blackBackground) {
                    backgroundStyle = "background-color:black;";
                } else if (pressedHighlight) {
                    backgroundStyle = "background-color:lightgray;";
                }

                String content = (item instanceof EvalNodeVarAssignment)
                        ? item.getExpressionString(false)
                        : item.getExpressionString(isSubstituteVars()) + " = " + item.getResult();

                // Build HTML
                String html = "<html><body style='margin:0;padding:0;font-family:sans-serif;" +
                        "font-size:12px;cursor:default;" +
                        "color:" + textColor + ";" +
                        "font-weight:" + (isBold ? "bold" : "normal") + ";" +
                        backgroundStyle + "'>" +
                        content +
                        "</body></html>";

                webView.getEngine().loadContent(html);

                // 👇 Combine icon and WebView into an HBox
                ImageView icon;
                if (getTreeItem().isLeaf()) {
                    icon = getIcon("TreeLeaf.gif");
                } else if (getTreeItem().isExpanded()) {
                    icon = getIcon("TreeOpen.gif");
                } else {
                    icon = getIcon("TreeClosed.gif");
                }

                // 📌 Make icon update when expanded/collapsed
                getTreeItem().expandedProperty().addListener((obs, oldVal, newVal) -> {
                    updateItem(getItem(), false);
                });

                HBox hbox = new HBox(4, icon, pane);
                setGraphic(hbox);
                setStyle(""); // Don't style the cell wrapper itself
            }
        }

        private String getHighlightColor(EvalNode node) {
            EvalNode.TreeValue value = getHighlightValue(node);

            if (highlightNone.isSelected() || value == EvalNode.TreeValue.INVALID) {
                return "black";
            }

            if (fNoColorHighlightingChk.isSelected()) {
                // Black and white mode
                if (value == EvalNode.TreeValue.TRUE) {
                    return "black";
                } else if (value == EvalNode.TreeValue.FALSE) {
                    return "white";
                }
                return "black";
            } else {
                // Color mode
                return switch (value) {
                    case TRUE -> "#008000";
                    case FALSE -> "#c00000";
                    case UNDEFINED -> "gray";
                    default -> "black";
                };
            }
        }

        private boolean shouldBold(EvalNode node) {
            if (highlightNone.isSelected()) return false;

            EvalNode.TreeValue value = getHighlightValue(node);
            return value == EvalNode.TreeValue.TRUE ||
                    value == EvalNode.TreeValue.FALSE;
        }

        private EvalNode.TreeValue getHighlightValue(EvalNode node) {
            if (highlightTerm.isSelected()) return getNodeTreeValue(node);
            if (highlightSubtree.isSelected()) return node.getSubTreeValue();
            if (highlightComplete.isSelected()) return node.getCompleteTreeValue();
            return EvalNode.TreeValue.INVALID;
        }
    }


    private TreeItem<EvalNode> createTreeItems(EvalNode node) {
        TreeItem<EvalNode> treeItem = new TreeItem<>(node);
        Expression parentExpr = node.getExpression();

        for (EvalNode child : node.children()) {
            child.setSubstituteVariables(isSubstituteVars());

            // Skip insignificant nodes (like "1=1")
            if (child.getExpression().toString().equals(child.getResult().toString())) {
                continue;
            }

            // Handle early variable assignments
            if (varAssignmentMode == ShowVariableAssignment.EARLY) {
                List<Entry> parentVars = node.getVarBindings();
                List<Entry> childVars = child.getVarBindings();
                List<Entry> newVars = new ArrayList<>(childVars);
                newVars.removeAll(parentVars);

                if (!newVars.isEmpty()) {
                    // Create variable assignment node
                    EvalNodeVarAssignment varNode = new EvalNodeVarAssignment(new Vector<>(childVars));
                    Entry firstVar = newVars.getFirst();
                    varNode.setExpression(new ExpVariable(firstVar.getVarName(), firstVar.getValue().type()));
                    varNode.setResult(firstVar.getValue());
                    varNode.setSubTreeValue(getNodeTreeValue(child));
                    varNode.setCompleteTreeValue(getNodeTreeValue(child));

                    // Add all variable assignments to this node
                    for (Entry var : newVars) {
                        varNode.addVarAssignment(var);
                    }

                    TreeItem<EvalNode> varItem = new TreeItem<>(varNode);
                    treeItem.getChildren().add(varItem);

                    // Add the actual child node under the variable assignment
                    if (shouldAddChildNode(parentExpr, child)) {
                        varItem.getChildren().add(createTreeItems(child));
                    }

                    continue;
                }
            }

            if (varAssignmentMode == ShowVariableAssignment.EARLY &&
                    child.getExpression() instanceof ExpVariable) {
                continue; // skip this child — it has already been shown via assignment
            }

            // Add regular child node (for LATE or NEVER modes)
            if (varAssignmentMode != ShowVariableAssignment.NEVER ||
                    !(child.getExpression() instanceof ExpVariable)) {
                if (shouldAddChildNode(parentExpr, child)) {
                    treeItem.getChildren().add(createTreeItems(child));
                }
            }

            // Check for early termination conditions
            if (shouldTerminateEarly(parentExpr, child)) {
                break;
            }
        }

        return treeItem;
    }

    private void collectExpandedItems(TreeItem<EvalNode> item, Set<String> exprs) {
        if (item == null) return;
        if (item.isExpanded()) {
            exprs.add(getStableId(item.getValue()));
        }
        for (TreeItem<EvalNode> child : item.getChildren()) {
            collectExpandedItems(child, exprs);
        }
    }

    private void restoreExpandedItems(TreeItem<EvalNode> item, Set<String> exprs) {
        if (item == null) return;
        if (exprs.contains(getStableId(item.getValue()))) {
            item.setExpanded(true);
        }
        for (TreeItem<EvalNode> child : item.getChildren()) {
            restoreExpandedItems(child, exprs);
        }
    }

    private boolean shouldAddChildNode(Expression parentExpr, EvalNode child) {
        // Don't add variable assignments if in NEVER mode
        if (varAssignmentMode == ShowVariableAssignment.NEVER &&
                child.getExpression() instanceof ExpVariable) {
            return false;
        }
        return true;
    }

    private boolean shouldTerminateEarly(Expression parentExpr, EvalNode child) {
        if (!fExtendedForAll.isSelected() && parentExpr instanceof ExpForAll &&
                (child.getResult().equals(BooleanValue.FALSE) ||
                        child.getResult().equals(UndefinedValue.instance))) {
            return true;
        }
        if (!fExtendedExists.isSelected() && parentExpr instanceof ExpExists &&
                child.getResult().equals(BooleanValue.TRUE)) {
            return true;
        }
        // Add other termination conditions...
        return false;
    }

    public void updateEvalBrowser(EvalNode root) {
        // 1. Update the top label
        topHtmlView.getEngine().loadContent(
                "<html><head><style>body { font-family: sans-serif; font-size: 14px; }</style></head><body>" +
                        root.getExpressionString(false) +
                        "</body></html>"
        );

        // 2. Build new TreeView content
        TreeItem<EvalNode> newRoot = createTreeItems(originalRoot);
        treeView.setRoot(newRoot);
        applyFolderIcons(newRoot);
        newRoot.setExpanded(true);

        // 3. Clear details
        varAssList.getItems().clear();
        substituteWebView.getEngine().loadContent("");

        // 4. Optional: set root highlighting info
        if (root.getResult().equals(BooleanValue.TRUE)) {
            root.setSubTreeValue(EvalNode.TreeValue.TRUE);
            root.setCompleteTreeValue(EvalNode.TreeValue.TRUE);
        } else if (root.getResult().equals(BooleanValue.FALSE)) {
            root.setSubTreeValue(EvalNode.TreeValue.FALSE);
            root.setCompleteTreeValue(EvalNode.TreeValue.FALSE);
        }

        // 5. Re-apply right pane layout (if needed)
        updateRightSplitPane();
    }


    private void handleComboAction(String selected) {
        switch (selected) {
            case "Expand all":
                expandAll(treeView.getRoot());
                break;
            case "Collapse":
                collapseAll(treeView.getRoot());
                treeView.getRoot().setExpanded(true); // Keep root expanded
                break;
            case "Expand all true":
                expandAllWithCondition(treeView.getRoot(), "true");
                break;
            case "Expand all false":
                expandAllWithCondition(treeView.getRoot(), "false");
                break;
        }
    }

    private void expandAllWithCondition(TreeItem<?> node, String condition) {
        if (node != null) {
            node.setExpanded(true);
            for (TreeItem<?> child : node.getChildren()) {
                if (child.getValue().toString().contains(condition)){
                    expandAllWithCondition(child, condition);
                }
            }
        }
    }

    private void expandAll(TreeItem<?> node) {
        if (node != null) {
            node.setExpanded(true);
            for (TreeItem<?> child : node.getChildren()) {
                expandAll(child);
            }
        }
    }

    public ShowVariableAssignment getVarAssignmentMode() {
        return varAssignmentMode;
    }

    private String getStableId(EvalNode node) {
        return node.getExpression().toString();  // this stays constant
    }

    private void collapseAll(TreeItem<?> node) {
        if (node != null) {
            node.setExpanded(false);
            for (TreeItem<?> child : node.getChildren()) {
                collapseAll(child);
            }
        }
    }

    private void applyFolderIcons(TreeItem<EvalNode> item) {
        ImageView closed = getIcon("TreeClosed.gif");
        ImageView open = getIcon("TreeOpen.gif");
        ImageView leaf = getIcon("TreeLeaf.gif"); // optional for non-folders

        if (item.isLeaf()) {
            item.setGraphic(leaf);
        } else {
            item.setGraphic(closed);
            item.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
                item.setGraphic(isNowExpanded ? open : closed);
            });
        }

        for (TreeItem<EvalNode> child : item.getChildren()) {
            applyFolderIcons(child);
        }
    }

    private static ImageView getIcon(String name) {
        // Load the image from the resource path
        Image image = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/images/" + name)));
        // Create an ImageView to display the image
        return new ImageView(image);
    }

    private void refreshTree() {
        treeView.refresh(); // This forces all cells to update
    }

    private void closeWindow() {
        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private boolean isSubstituteVars() {
        return cbSubstituteVariables.isSelected();
    }
}
