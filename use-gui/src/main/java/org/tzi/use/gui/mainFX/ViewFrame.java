package org.tzi.use.gui.mainFX;

import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.SnapshotResult;
import javafx.scene.transform.Scale;
import org.tzi.use.config.Options;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.viewsFX.PrintableView;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * A JavaFX container holding a view of a system state.
 * Equivalent to Swing's ViewFrame using BorderPane.
 */
public class ViewFrame extends BorderPane {

    private final View fView;

    public ViewFrame(String title, View view, String iconFilename) {
        this.fView = view;
        setPadding(new Insets(4));
        // Title and icon could be passed to window wrapper (e.g., ResizableInternalWindow)
    }

    /**
     * Detaches the view from the system event bus.
     */
    public void close() {
        fView.detachModel();
    }

    /**
     * Checks whether the view supports printing.
     *
     * @return true if the view implements PrintableView, false otherwise
     */
    public boolean isPrintable() {
        return fView instanceof PrintableView;
    }

    /**
     * Prints the view using the provided JavaFX PageLayout.
     *
     * @param layout the page layout to use for printing
     */
    public void print(PageLayout layout, PrinterJob job) {
        if (fView instanceof PrintableView) {
            ((PrintableView) fView).printView(layout, job);
        }
    }

    /**
     * Renders the view into a Graphics2D context, suitable for export (e.g. PDF, image).
     *
     * @param g2         the Graphics2D context
     * @param exportAll  whether to export the entire window or just the view
     */
    public void export(Graphics2D g2, boolean exportAll) {
        Node exportNode;

        if (!exportAll && fView instanceof Node) {
            exportNode = (Node) fView;
        } else {
            exportNode = this;
        }

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        WritableImage snapshot = exportNode.snapshot(params, null);

        // Convert WritableImage to BufferedImage and draw
        BufferedImage bImg = javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot, null);
        g2.drawImage(bImg, 0, 0, null);
    }

    /**
     * Returns the view of this ViewFrameFX.
     */
    public View getView() {
        return fView;
    }
}
