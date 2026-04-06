package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.TransitionEvent;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.uml.sys.MSystem;
import org.w3c.dom.Element;
import javax.swing.JPopupMenu;
import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Presenter boundary for the object diagram (MVP, passive view).
 * Responsibilities: translate UI/input into model/system mutations, listen to system events,
 * and push UI updates via the view; keep UI components free of system logic.
 */
public interface NewObjectDiagramPresenter {
    void onRequestPopup();
    void onAddObject(Value value);
    void onRefreshRequested();
    void onCreateObject(String clsName);
    void onResetDiagram(ObjDiagramOptions options);
    void onHighlightChange(HighlightChangeEvent e);

    default void onTransition(TransitionEvent e) {}
    default void onObjectCreated(MObject obj) {}
    default void onObjectDestroyed(MObject obj) {}
    default void onAttributeAssigned(AttributeAssignedEvent e) {}
    default void onLinkInserted(LinkInsertedEvent e) {}
    default void onLinkDeleted(LinkDeletedEvent e) {}

    default void onPopupMenuPrepared(JPopupMenu menu,
                                     Point popupPosition,
                                     Set<MObject> selectedObjects,
                                     Set<MLink> selectedLinks,
                                     Set<MObject> selectedAssocObjects) {}
    default void onStatusMessage(String status) {}

    default void onBuildShowHideCropMenu(ShowHideCropMenuBuilder builder, Set<MObject> selectedObjects) {}

    default void onInsertLink(MAssociation association, List<MObject> participants) {}
    default void onDeleteLink(MLink link) {}
    default void onDeleteObjects(Set<MObject> objects) {}

    /** Notify presenter that selection changed in the diagram. */
    default void onSelectionChanged(Set<MObject> selectedObjects, Set<MLink> selectedLinks) {}

    /** Notify presenter that the current selection was dragged by the given delta. */
    default void onDragSelectionMoved(int dx, int dy) {}

    /** Show properties for the given object. */
    default void onShowObjectProperties(MObject object) {}

    /** Open the protocol state machine view for the given instance. */
    default void onShowProtocolStateMachine(MProtocolStateMachine stateMachine, MObject instance) {}

    /** Fetch links of an association from the model/state. */
    default Set<MLink> fetchLinksOfAssociation(MAssociation association) { return Set.of(); }

    /** Fetch protocol state machines available for the given object. */
    default Set<MProtocolStateMachine> fetchProtocolStateMachines(MObject object) { return Set.of(); }

    /** Fetch all objects of the given class from the state. */
    default Set<MObject> fetchObjectsOfClass(MClass cls) { return Set.of(); }

    /** Fetch all associations of the current model. */
    default Collection<MAssociation> fetchAllAssociations() { return Set.of(); }

    default void onHideLinks(Collection<MLink> links) {}
    default void onShowLinks(Collection<MLink> links) {}
    default void onHideObjects(Collection<MObject> objects) {}
    default void onHideAllLinks() {}
    default void onShowAllLinks() {}
    default void onShowHiddenElements() {}
    default void onCropSelection(Collection<MObject> objectsToHide) {}
    default void onGrayOut(Collection<MObject> objects) {}
    default void onGrayIn(Collection<MObject> objects) {}
    default void onToggleShowStates(boolean show) {}

    default void onStoreLayout(PersistHelper helper,
                               Element root,
                               NewObjectDiagramModel model) {}
    default Set<MObject> onRestoreLayout(PersistHelper helper,
                                         int version,
                                         NewObjectDiagramModel model,
                                         MSystem system) { return Set.of(); }

    /** Lifecycle hook to release resources (e.g., event bus). */
    default void detach() {}
}
