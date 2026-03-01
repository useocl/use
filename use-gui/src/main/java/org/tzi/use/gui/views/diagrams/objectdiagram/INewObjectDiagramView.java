package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JPopupMenu;

/** Minimal view contract for the object diagram MVP. */
public interface INewObjectDiagramView {
    void showPopup(JPopupMenu popup);
    void refresh();
    void setStatus(String status);
}

