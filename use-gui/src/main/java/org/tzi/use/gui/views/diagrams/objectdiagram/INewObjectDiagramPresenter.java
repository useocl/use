package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.events.*;
import org.tzi.use.uml.sys.MObject;

/** Minimal presenter contract for the object diagram MVP. */
public interface INewObjectDiagramPresenter {
    void onRequestPopup();
    void onAddObject(Value value);
    void onRefreshRequested();
    void onCreateObject(String clsName);

    // System event hooks (currently no-op implementations)
    default void onTransition(TransitionEvent e) {}
    default void onObjectCreated(MObject obj) {}
    default void onObjectDestroyed(MObject obj) {}
    default void onAttributeAssigned(AttributeAssignedEvent e) {}
    default void onLinkInserted(LinkInsertedEvent e) {}
    default void onLinkDeleted(LinkDeletedEvent e) {}
}
