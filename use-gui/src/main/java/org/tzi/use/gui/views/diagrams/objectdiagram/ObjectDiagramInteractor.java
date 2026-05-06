package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.sys.MInstance;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;

/**
 * Facade for diagram operations that the presenter can trigger without reaching
 * into the view implementation.
 */
public interface ObjectDiagramInteractor {
    void addObject(MObject obj);
    void deleteObject(MObject obj);
    void addLink(MLink link);
    void deleteLink(MLink link);
    void updateObject(MInstance obj);
    void invalidateContent(boolean fullLayout);
    void clearSelection();
    ObjectSelection getObjectSelection();
    void hideLink(MLink link);
    void hideObject(MObject obj);
    void hideAllLinks();
    void showAll();
    void showAllLinks();
    void setShowStates(boolean show);
    boolean isShowStates();
    void setGreyed(MObject obj, boolean greyed);
    boolean isGreyed(MObject obj);
}
