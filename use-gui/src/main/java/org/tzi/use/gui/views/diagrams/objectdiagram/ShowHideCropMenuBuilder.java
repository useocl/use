package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.sys.MObject;

import java.util.Set;

/**
 * Package-local helper used by the presenter hook to contribute the
 * Show/hide/crop submenu entries without exposing Swing types to the interface.
 */
public interface ShowHideCropMenuBuilder {
    void addSelectedObjectPathView(Set<MObject> selectedObjects);
    void addSelectionWithOCLViewAction();
    void addSelectionObjectView();
}

