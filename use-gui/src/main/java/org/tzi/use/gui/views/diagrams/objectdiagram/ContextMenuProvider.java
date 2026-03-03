package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JPopupMenu;
import java.util.Set;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MLink;

/**
 * Builds context menus and routes callbacks to the presenter.
 */
public interface ContextMenuProvider {
    void enhanceMenu(JPopupMenu popupMenu,
                     ObjectDiagramInteractor diagram,
                     NewObjectDiagramPresenter presenter,
                     Set<MObject> selectedObjects,
                     Set<MLink> selectedLinks,
                     Set<MObject> selectedAssocObjects);
}
