package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import java.awt.Graphics;

/**
 * Placeholder canvas implementing the view interface. Rendering will be migrated later.
 */
public class DiagramCanvas extends JComponent implements INewObjectDiagramView {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // actual rendering logic will be migrated from NewObjectDiagram
    }

    @Override
    public void showPopup(JPopupMenu popup) {
        popup.show(this, getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void refresh() {
        revalidate();
        repaint();
    }

    @Override
    public void setStatus(String status) {
        // status handling will be delegated by the higher-level view
    }
}
