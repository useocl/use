package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;

/**
 * Builds context menus and routes callbacks to the presenter.
 */
public interface ContextMenuProvider {
    JPopupMenu buildMenu(MouseEvent triggerEvent, NewObjectDiagramPresenter presenter);
}

