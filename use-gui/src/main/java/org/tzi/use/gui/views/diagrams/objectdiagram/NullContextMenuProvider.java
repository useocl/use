package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;

/**
 * No-op context menu provider used during incremental migration.
 */
public class NullContextMenuProvider implements ContextMenuProvider {
    @Override
    public JPopupMenu buildMenu(MouseEvent triggerEvent, NewObjectDiagramPresenter presenter) {
        return new JPopupMenu();
    }
}

