package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.ocl.value.Value;

/** Minimal presenter contract for the object diagram MVP. */
public interface IObjectDiagramPresenter {
    void onRequestPopup();
    void onAddObject(Value value);
    void onRefreshRequested();
    void onCreateObject(String clsName);
}

