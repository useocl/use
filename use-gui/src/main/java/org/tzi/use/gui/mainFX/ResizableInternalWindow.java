package org.tzi.use.gui.mainFX;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.tzi.use.gui.views.diagrams.DiagramType;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CommunicationDiagramView;
import org.tzi.use.gui.views.diagrams.behavior.sequencediagram.SequenceDiagramView;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.statemachine.StateMachineDiagramView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ResizableInternalWindow extends Pane {

    // track if Window is active at front
    private final BooleanProperty isActive = new SimpleBooleanProperty(false);

    // Minimum dimensions for resizing
    public static final double MIN_WIDTH_WINDOW = 120;
    public static final double MIN_HEIGHT_WINDOW = 60;

    // Margin for edges, so that SwingNode doesn't cover edges and allows resizing the window
    private static final double EDGE_MARGIN = 6;

    // Margin width to detect resizing zones
    private static final double RESIZE_MARGIN = 5;

    // --- Mouse drag tracking for moving the window by the title bar ---
    private double dragOffsetX;  // offset of mouse X from window's layoutX
    private double dragOffsetY;  // offset of mouse Y from window's layoutY
    private boolean draggingWindow = false;

    // --- Mouse press geometry for resizing ---
    private double startMouseSceneX;
    private double startMouseSceneY;
    private double startX;
    private double startY;
    private double startW;
    private double startH;

    // --- Which edges are we resizing? ---
    private boolean resizeTop = false;
    private boolean resizeBottom = false;
    private boolean resizeLeft = false;
    private boolean resizeRight = false;

    // Parent container references
    private final SwingNode content;
    private final Pane desktopPane; // Main area where the window is displayed
    private final HBox taskbarPane; // Taskbar area for minimized windows

    // UI elements for the title bar
    private final HBox titleBar = new HBox(); // Title bar container
    private final Label lblTitle = new Label(); // Title text
    private final Region spacer = new Region(); // Spacer for flexible layout

    // Control buttons
    private final Button btnMinimize = new Button("\u23AF");  //2212
    private final Button btnMaximize = new Button("\u29E0");  //25A1
    private final Button btnClose = new Button("\uFF38");  //00D7  uFF58

    // State tracking variables
    private double oldX, oldY, oldWidth, oldHeight; // for restore from minimized/maximized
    private boolean minimized = false;              // whether the window is minimized
    private boolean maximized = false;              // whether the window is maximized
    private Button taskbarButton;                   // taskbar representation when minimized

    // DiagramType to know which toolbarItems and menuItems need to be visible
    private final DiagramType diagramType;

    // controller
    private final MainWindow controller;

    /**
     * Constructor for the resizable internal window.
     *
     * @param title       The title of the window.
     * @param desktopPane The main container for this window.
     * @param taskbarPane The taskbar to hold minimized windows.
     * @param content     The Swing content for this window
     */
    public ResizableInternalWindow(String title, Pane desktopPane, HBox taskbarPane, SwingNode content, MainWindow controller, DiagramType diagramType) {
        this.desktopPane = desktopPane;
        this.taskbarPane = taskbarPane;
        this.controller = controller;
        this.content = content;
        this.diagramType = diagramType;
        lblTitle.setText(title);

        initWindow();
        initHandlers();

        setupContent();
    }

    /**
     * Initializes the window layout, including title bar and buttons.
     */
    private void initWindow() {
        // Style and layout for the title bar
        titleBar.setStyle("-fx-background-color: #DCDCDC; -fx-border-color: black; -fx-border-width: 2;");
        titleBar.setPrefHeight(30);
        titleBar.setAlignment(Pos.CENTER_LEFT);

        // Configure title label
        lblTitle.setTextOverrun(OverrunStyle.ELLIPSIS);
        lblTitle.setStyle("-fx-padding: 0 0 0 10; -fx-font-weight: bold;");
        HBox.setHgrow(lblTitle, Priority.NEVER);

        // Spacer for dynamic alignment
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Configure control buttons
        btnMinimize.setPrefWidth(40);
        btnMaximize.setPrefWidth(40);
        btnClose.setPrefWidth(40);
        styleTitleBarButtons();

        // Add title and buttons to the title bar
        titleBar.getChildren().addAll(lblTitle, spacer, btnMinimize, btnMaximize, btnClose);

        // Add the title bar to the window
        getChildren().add(titleBar);

        // Style and set default size for the window
        setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

        // Different Size on different Windows
        if (diagramType == DiagramType.SELECTED_CLASS_PATH_VIEW || diagramType == DiagramType.SELECTED_OBJECT_PATH_VIEW) {
            setPrefSize(450, 200);
        } else if (diagramType == DiagramType.SELECTED_CLASS_VIEW) {
            setPrefSize(580, 230);
        } else if (diagramType == DiagramType.SELECTED_OBJECT_VIEW) {
            setPrefSize(530, 230);
        } else if (diagramType == DiagramType.SELECTED_OCL_VIEW) {
            setPrefSize(370, 250);
        } else {
            setPrefSize(300, 200);
        }

        // Bind the title bar width to match the window
        titleBar.prefWidthProperty().bind(widthProperty());

        // Adjust button visibility based on window size
        addSizeListenerForButtonVisibility();

        // integration of the drag/drop future for Object Diagram Windows
        dragRecognition();
    }

    /**
     * Adjusts button visibility (Minimize, Maximize, Title) based on window size.
     */
    private void addSizeListenerForButtonVisibility() {
        ChangeListener<Number> widthListener = (obs, oldVal, newVal) -> {
            double w = newVal.doubleValue();

            // Hide Minimize and Maximize if width is too small
            btnMinimize.setVisible(w > 140);
            btnMaximize.setVisible(w > 120);

            // Hide title text if width is extremely small
            lblTitle.setVisible(w >= 60);
        };
        widthProperty().addListener(widthListener);
    }

    /**
     * Initializes handlers for window dragging, resizing, and button actions.
     */
    private void initHandlers() {
        // --- Close button action ---
        btnClose.setOnAction(e -> {
            controller.removeClosedWindow(this);
            closeWindow();
        });

        // --- Minimize button action ---
        btnMinimize.setOnAction(e -> {
            if (!minimized) {
                minimizeWindow();
            } else {
                restoreFromMinimized();
            }
        });

        // --- Maximize button action ---
        btnMaximize.setOnAction(e -> {
            if (!maximized) {
                maximizeWindow();
            } else {
                restoreFromMaximized();
            }
        });

        // --- Dragging the window via titleBar (when NOT maximized) ---
        titleBar.setOnMousePressed(e -> {
            setWindowToFront();


            // --- Double-click detection right away ---
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()) {
                if (!maximized) {
                    maximizeWindow();
                } else {
                    restoreFromMaximized();
                }
                e.consume();
                return; // --- Skip the drag logic entirely on double-click ---
            }

            // --- Otherwise handle normal press for dragging ---
            if (!maximized) {
                draggingWindow = true;
                dragOffsetX = e.getSceneX() - getLayoutX();
                dragOffsetY = e.getSceneY() - getLayoutY();
            }
        });

        titleBar.setOnMouseDragged(e -> {
            if (draggingWindow && !maximized) {
                double newX = e.getSceneX() - dragOffsetX;
                double newY = e.getSceneY() - dragOffsetY;

                // Clamp position so window doesn't move fully off-screen
                double maxX = desktopPane.getWidth() - getWidth();
                double maxY = desktopPane.getHeight() - getHeight();
                if (newX < 0) newX = 0;
                if (newY < 0) newY = 0;
                if (newX > maxX) newX = maxX;
                if (newY > maxY) newY = maxY;

                setLayoutX(newX);
                setLayoutY(newY);
            }
        });

        titleBar.setOnMouseReleased(e -> {
            draggingWindow = false;
        });

        // detect Mouse Movement and update Cursor Appearance
        mouseMovementRecognition();

        content.setOnMousePressed(e -> {
            setWindowToFront();
        });

        // when leaving the window, clear the statusbar to default.
        setOnMouseExited(e -> {
            controller.getfStatusBar().clearMessage();
        });

        // --- Record initial geometry on mouse press if we might resize ---
        setOnMousePressed(e -> {
            setWindowToFront();

            // If we are near an edge (resizing), record the window's geometry
            if ((resizeLeft || resizeRight || resizeTop || resizeBottom) && !minimized && !maximized) {
                startMouseSceneX = e.getSceneX();
                startMouseSceneY = e.getSceneY();

                startX = getLayoutX();
                startY = getLayoutY();
                startW = getWidth();
                startH = getHeight();
            }

        });

        // --- Resize the window on mouse drag if an edge is selected ---
        setOnMouseDragged(e -> {
            // If the window is minimized or maximized, we do not resize
            if (minimized || maximized) {
                return;
            }

            // If not actually in resize mode, return
            if (!(resizeLeft || resizeRight || resizeTop || resizeBottom)) {
                return;
            }

            double mouseDeltaX = e.getSceneX() - startMouseSceneX;
            double mouseDeltaY = e.getSceneY() - startMouseSceneY;

            double newX = startX;
            double newY = startY;
            double newW = startW;
            double newH = startH;

            // Horizontal edges
            if (resizeLeft) {
                newX = startX + mouseDeltaX;
                newW = startW - mouseDeltaX;
            } else if (resizeRight) {
                newW = startW + mouseDeltaX;
            }

            // Vertical edges
            if (resizeTop) {
                newY = startY + mouseDeltaY;
                newH = startH - mouseDeltaY;
            } else if (resizeBottom) {
                newH = startH + mouseDeltaY;
            }

            // Enforce minimum size
            if (newW < MIN_WIDTH_WINDOW) {
                newW = MIN_WIDTH_WINDOW;
                // If dragging from the left, shift X accordingly
                if (resizeLeft) {
                    newX = startX + (startW - MIN_WIDTH_WINDOW);
                }
            }
            if (newH < MIN_HEIGHT_WINDOW) {
                newH = MIN_HEIGHT_WINDOW;
                // If dragging from the top, shift Y accordingly
                if (resizeTop) {
                    newY = startY + (startH - MIN_HEIGHT_WINDOW);
                }
            }

            // Also clamp so the window cannot move entirely off the right/bottom
            double maxX = desktopPane.getWidth() - newW;
            double maxY = desktopPane.getHeight() - newH;
            if (newX < 0) newX = 0;
            if (newY < 0) newY = 0;
            if (newX > maxX) newX = maxX;
            if (newY > maxY) newY = maxY;

            setLayoutX(newX);
            setLayoutY(newY);
            setPrefWidth(newW);
            setPrefHeight(newH);
        });

        // Add a listener for `isActive` to change the style
        isActive.addListener((obs, wasActive, isNowActive) -> {
            if (isNowActive) {
                setStyle("-fx-border-color: #0078D7; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, #0078D7, 10, 0.5, 0, 0);");
            } else {
                setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-effect: none;");
            }
        });
    }

    // --- Detect edges on mouse move (for changing cursor) ---
    private void mouseMovementRecognition() {
        // No resizing in minimized or maximized state
        if (minimized || maximized) {
            setCursor(Cursor.DEFAULT);
            return;
        }

        // If the user is dragging the window by the title bar, skip
        if (draggingWindow) {
            return;
        }

        //no resizing cursor inside the content
        content.setOnMouseMoved(e -> {
            setCursor(Cursor.DEFAULT);

            updateMousePositionHandler(e);
        });

        content.setOnMouseDragged(this::updateMousePositionHandler);

        // show resizing Cursor and update the statusBars mouse location on Window
        setOnMouseMoved(e -> {
            double x = e.getX();
            double y = e.getY();

            double width = getWidth();
            double height = getHeight();

            boolean nearLeft = (x < RESIZE_MARGIN);
            boolean nearRight = (x > width - RESIZE_MARGIN);
            boolean nearTop = (y < RESIZE_MARGIN);
            boolean nearBottom = (y > height - RESIZE_MARGIN);

            resizeLeft = nearLeft;
            resizeRight = nearRight;
            resizeTop = nearTop;
            resizeBottom = nearBottom;

            // Update cursor based on which edges
            if (nearLeft && nearTop) {
                setCursor(Cursor.NW_RESIZE);
            } else if (nearRight && nearTop) {
                setCursor(Cursor.NE_RESIZE);
            } else if (nearLeft && nearBottom) {
                setCursor(Cursor.SW_RESIZE);
            } else if (nearRight && nearBottom) {
                setCursor(Cursor.SE_RESIZE);
            } else if (nearLeft) {
                setCursor(Cursor.W_RESIZE);
            } else if (nearRight) {
                setCursor(Cursor.E_RESIZE);
            } else if (nearTop) {
                setCursor(Cursor.N_RESIZE);
            } else if (nearBottom) {
                setCursor(Cursor.S_RESIZE);
            } else {
                setCursor(Cursor.DEFAULT);
            }
        });


    }

    private void setupContent() {
        Pane contentPane = new Pane();

        // Position the content pane at x = 5, y = titleBar height
        contentPane.setLayoutX(EDGE_MARGIN);
        contentPane.setLayoutY(titleBar.getPrefHeight() + EDGE_MARGIN);

        // Make it narrower by 10px (5px on left, 5px on right)
        contentPane.prefWidthProperty().bind(
                widthProperty().subtract(EDGE_MARGIN * 2)
        );

        // And reduce the height by 5px at the bottom
        contentPane.prefHeightProperty().bind(
                heightProperty().subtract(titleBar.getPrefHeight() + EDGE_MARGIN * 2)
        );

        getChildren().add(contentPane);

        // putting the SwingNode inside a Region to make it resizable
        SwingNodeRegion region = new SwingNodeRegion(content);
        contentPane.getChildren().add(region);

        region.prefWidthProperty().bind(contentPane.widthProperty());
        region.prefHeightProperty().bind(contentPane.heightProperty());

        // Make sure there's no visible background or border
        contentPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
    }

    // ==========================================================
    // ================ Minimize / Maximize Logic ===============
    // ==========================================================

    private void minimizeWindow() {
        saveCurrentGeometry();
        minimized = true;
        desktopPane.getChildren().remove(this);

        taskbarButton = new Button(lblTitle.getText());
        taskbarButton.setOnAction(e -> restoreFromMinimized());
        taskbarPane.getChildren().add(taskbarButton);

        // if minimized and other windows open, focus last window in list
        if (!desktopPane.getChildren().isEmpty()) {
            controller.setActiveWindow((ResizableInternalWindow) desktopPane.getChildren().getLast());
            ResizableInternalWindow lastWindowInList = (ResizableInternalWindow) desktopPane.getChildren().getLast();
            lastWindowInList.setActive(true);
        } else {
            controller.setActiveWindow(null);
        }
    }

    public void restoreFromMinimized() {
        if (taskbarButton != null) {
            taskbarPane.getChildren().remove(taskbarButton);
            taskbarButton = null;
        }
        if (!desktopPane.getChildren().contains(this)) {
            desktopPane.getChildren().add(this);
        }
        restorePreviousGeometry();
        minimized = false;

        // focus on new active window
        ResizableInternalWindow currentActive = controller.getActiveWindow();
        if (currentActive != null && currentActive != this) {
            currentActive.setActive(false);
        }
        this.setActive(true);
        controller.setActiveWindow(this);
    }

    private void maximizeWindow() {
        saveCurrentGeometry();
        setLayoutX(0);
        setLayoutY(0);
        setPrefWidth(desktopPane.getWidth());
        setPrefHeight(desktopPane.getHeight());
        maximized = true;
        btnMaximize.setText("\u2750"); // "Restore" icon (❒) or something else
        toFront();
    }

    private void restoreFromMaximized() {
        restorePreviousGeometry();
        maximized = false;
        btnMaximize.setText("\u29E0"); // back to square icon
        toFront();
    }

    private void saveCurrentGeometry() {
        oldX = getLayoutX();
        oldY = getLayoutY();
        oldWidth = getWidth();
        oldHeight = getHeight();
    }

    private void restorePreviousGeometry() {
        setLayoutX(oldX);
        setLayoutY(oldY);
        setPrefWidth(oldWidth);
        setPrefHeight(oldHeight);
    }

    public void closeWindow() {

        // remove from taskbar if minimized
        if (taskbarButton != null) {
            taskbarPane.getChildren().remove(taskbarButton);
        }

        // remove from desktop
        desktopPane.getChildren().remove(this);

        if (desktopPane.getChildren().isEmpty() && taskbarPane.getChildren().isEmpty()) {
            // if no desktopPanes open
            // controller.setActiveWindow(null); Not Needed I think
        } else if (!desktopPane.getChildren().isEmpty()) {
            controller.setActiveWindow((ResizableInternalWindow) desktopPane.getChildren().getLast());
            ResizableInternalWindow lastWindowInList = (ResizableInternalWindow) desktopPane.getChildren().getLast();
            lastWindowInList.setActive(true);
        }

    }


    // --- Styling the buttons to have a modern look ---
    private void styleTitleBarButtons() {
        // Base style (transparent background, some padding, hand cursor, etc.)
        String baseStyle = ""
                + "-fx-background-color: transparent;"
                + "-fx-border-width: 0;"
                + "-fx-background-radius: 0;"
                + "-fx-cursor: hand;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 14px;";

        // Hover style for minimize/maximize (light gray)
        String hoverStyleGeneric = ""
                + "-fx-background-color: rgba(0,0,0,0.1);"
                + "-fx-text-fill: #333;"
                + "-fx-border-width: 0;"
                + "-fx-background-radius: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 14px;"
                + "-fx-alignment: CENTER;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 0 8;";

        // Hover style for close (red)
        String hoverStyleClose = ""
                + "-fx-background-color: #ff5c5c;"
                + "-fx-text-fill: white;"
                + "-fx-border-width: 0;"
                + "-fx-background-radius: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 14px;"
                + "-fx-alignment: CENTER;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 0 8;";

        // Minimize button
        btnMinimize.setStyle(baseStyle);
        btnMinimize.setOnMouseEntered(e -> btnMinimize.setStyle(hoverStyleGeneric));
        btnMinimize.setOnMouseExited(e -> btnMinimize.setStyle(baseStyle));

        // Maximize button
        btnMaximize.setStyle(baseStyle);
        btnMaximize.setOnMouseEntered(e -> btnMaximize.setStyle(hoverStyleGeneric));
        btnMaximize.setOnMouseExited(e -> btnMaximize.setStyle(baseStyle));
        btnMaximize.setTextOverrun(OverrunStyle.CLIP);

        // Close button (special red hover)
        btnClose.setStyle(baseStyle);
        btnClose.setOnMouseEntered(e -> btnClose.setStyle(hoverStyleClose));
        btnClose.setOnMouseExited(e -> btnClose.setStyle(baseStyle));
        btnClose.setTextOverrun(OverrunStyle.CLIP);
    }

    public void dragRecognition() {
        content.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString() && db.getString().startsWith("CLASS-")) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        if (diagramType == DiagramType.OBJECT_DIAGRAM) {
            content.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasString() && db.getString().startsWith("CLASS-")) {
                    String className = db.getString().substring("CLASS-".length());

                    // mouse position to have the object created to that position in the object diagram window TODO
                    double mouseX = event.getX();
                    double mouseY = event.getY();

                    // creating the dragged object with its name and updating undo, redo and save script menu-/taskbar-item
                    controller.createObject(className);
                    controller.setUndoRedoButtons();
                    controller.updateFActionSaveScript();
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            });
        }
    }

    private void updateMousePositionHandler(MouseEvent e) {
        boolean isValidDiagramtype = diagramType == DiagramType.CLASS_DIAGRAM || diagramType == DiagramType.OBJECT_DIAGRAM || diagramType == DiagramType.COMMUNICATION_DIAGRAM;
        if (isValidDiagramtype) {
            double x = e.getX();
            double y = e.getY();

            // update statusbars right side mouse position (x + y)
            String posMsg = String.format("[x=%.1f, y=%.1f]", x, y);
            controller.getfStatusBar().showMessage(posMsg, Pos.BASELINE_RIGHT);
        }
    }

    public void setActive(Boolean active) {
        isActive.set(active);
    }

    public Boolean isActive() {
        return isActive.get();
    }

    // --- Setting Window to Front ---
    public void setWindowToFront() {
        // Mark this window as active for the controller (MainWindow)
        toFront();
        setActive(true);
        controller.setActiveWindow(this);
    }

    // --- returning the Title of the Window ---
    public String getTitleText() {
        return lblTitle.getText();
    }

    // --- returning if the window is minimized ---
    public boolean isMinimized() {
        return minimized;
    }

    // --- returning the DiagramType of the Window ---
    public DiagramType getDiagramType() {
        return diagramType;
    }

    /**
     * Prints the window’s content
     */
    public void print(PageLayout pageLayout) {
        // 1. Get the Swing content
        JComponent root = content.getContent();

        // 3. Use preferred size to render entire content
        // TODO might want to get the preferredSizes of not the root but the actual content found inside renderingSwingContent
        Dimension preferredSize = root.getPreferredSize();
        double scale = 3.0;
        int width = (int) (preferredSize.width * scale);
        int height = (int) (preferredSize.height * scale);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // 4. Render the Swing content at full size
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.scale(scale, scale); // Paint with 3x detail
        renderingSwingContent(root, g2d);
        g2d.dispose();

        // 5. Convert to JavaFX image and scale for display/printing
        WritableImage fxImage = SwingFXUtils.toFXImage(image, null);
        ImageView imageView = new ImageView(fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(pageLayout.getPrintableWidth());
        imageView.setFitHeight(pageLayout.getPrintableHeight());

        // 6. Print with JavaFX PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(this.getScene().getWindow())) {
            job.getJobSettings().setPageLayout(pageLayout);
            job.getJobSettings().setJobName(diagramType.toString());
            boolean success = job.printPage(imageView);
            if (success) {
                job.endJob();
            }
        }
    }


    /**
     * Renders the Swing content of the specified component by painting it onto the provided graphics context.
     */
    private void renderingSwingContent(JComponent root, Graphics2D g2d) {
        Component[] components = root.getComponents();

        for (Component comp : components) {
            if (comp instanceof ClassDiagramView cdv) {
                cdv.getClassDiagram().paint(g2d);
                return;
            } else if (comp instanceof StateMachineDiagramView smdv) {
                smdv.getStateMachineDiagram().drawDiagram(g2d);
                return;
            } else if (comp instanceof CommunicationDiagramView commdv) {
                commdv.getCommunicationDiagram().paint(g2d);
                return;
            } else if (comp instanceof NewObjectDiagramView odv) {
                odv.getDiagram().paint(g2d);
                return;
            } else if (comp instanceof JScrollPane scrollPane) {
                Component inner = scrollPane.getViewport().getView();
                if (inner instanceof SequenceDiagramView sdv) {
                    sdv.getSequenceDiagram().paint(g2d);
                    return;
                }
            }
        }
    }

    public void exportAsPdf(boolean exportAll) {
        Window ownerWindow = this.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File selectedFile = fileChooser.showSaveDialog(ownerWindow);
        if (selectedFile == null) return;

        if (!selectedFile.getName().toLowerCase().endsWith(".pdf")) {
            selectedFile = new File(selectedFile.getAbsolutePath() + ".pdf");
        }

        if (selectedFile.exists()) {
            Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Overwrite existing file?\n" + selectedFile.getName());
            overwriteAlert.setHeaderText("Please confirm");
            overwriteAlert.initOwner(ownerWindow);
            overwriteAlert.showAndWait().ifPresent(result -> {
                if (!result.getText().equalsIgnoreCase("OK") && !result.getText().equalsIgnoreCase("Yes")) {
                    return;
                }
            });
        }

        try {
            // Step 1: Determine the size
            JComponent root = content.getContent();
            Dimension preferredSize = exportAll ? root.getPreferredSize() : root.getVisibleRect().getSize();

            // high-resolution scaling
            double scale = 3.0;
            int width = (int) (preferredSize.width * scale);
            int height = (int) (preferredSize.height * scale);

            Rectangle pdfSize = new com.itextpdf.text.Rectangle(width, height);
            Document document = new Document(pdfSize);

            // Step 2: Create writer
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
            document.open();

            // Step 3: Render content
            PdfContentByte canvas = writer.getDirectContent();
            Graphics2D g2 = new PdfGraphics2D(canvas, width, height);
            renderingSwingContent(root, g2);

            g2.scale(scale, scale);
            renderingSwingContent(root, g2);
            g2.dispose();

            // Step 4: Finish
            document.close();

            Alert done = new Alert(Alert.AlertType.INFORMATION, "Export finished.");
            done.setHeaderText("USE - Export Complete");
            done.initOwner(ownerWindow);
            done.showAndWait();

        } catch (IOException | DocumentException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Error exporting PDF: " + ex.getMessage());
            error.setHeaderText("Export Failed");
            error.initOwner(ownerWindow);
            error.showAndWait();
            ex.printStackTrace();
        }
    }
}

