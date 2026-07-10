package org.tzi.use.gui.views.diagrams.objectdiagram;

import javax.swing.JPopupMenu;
import java.awt.Point;
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
                     Point popupPosition,
                     Set<MObject> selectedObjects,
                     Set<MLink> selectedLinks,
                     Set<MObject> selectedAssocObjects);

    /**
     * Helper interface to contribute show/hide/crop submenu entries without
     * exposing Swing types to the presenter. This logically belongs to the
     * context menu provider since it is used to build parts of the context
     * menu; keeping it nested helps grouping related APIs.
     */
    interface ShowHideCropMenuBuilder {
        void addSelectedObjectPathView(Set<MObject> selectedObjects);
        void addSelectionWithOCLViewAction();
        void addSelectionObjectView();
    }
}
