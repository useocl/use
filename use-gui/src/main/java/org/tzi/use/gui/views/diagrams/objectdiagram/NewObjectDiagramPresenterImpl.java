package org.tzi.use.gui.views.diagrams.objectdiagram;

import com.google.common.eventbus.Subscribe;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.*;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * MVP presenter for the object diagram. Currently a skeleton with no logic; wiring will be added incrementally.
 */

public class NewObjectDiagramPresenterImpl implements NewObjectDiagramPresenter {

    private final NewObjectDiagramUI view;

    private final ObjectDiagramInteractor diagram;

    private final NewObjectDiagramModel model;

    private final ApplicationController applicationController;

    private final ContextMenuProvider contextMenuProvider;

    private final PlacementRepository placementRepository;

    private final MSystem system;

    // Common log message fragments extracted to constants to avoid duplicated literals (Sonar S1192)
    private static final String PREFIX_EVENT = "event=";
    private static final String PREFIX_OBJECT = "object=";
    private static final String PREFIX_LINK = "link=";
    private static final String PREFIX_COUNT = "count=";
    private static final String PREFIX_ASSOCIATION = "association=";
    private static final String PREFIX_CLASS = "class=";
    private static final String MSG_REQUESTED = "requested";
    private static final String CONSTRUCTED_PREFIX = "constructed; view=";
    private static final String COMMA_DIAGRAM = ", diagram=";
    private static final String COMMA_MODEL = ", model=";

    public NewObjectDiagramPresenterImpl(NewObjectDiagramUI view,

                                         ObjectDiagramInteractor diagram,

                                         NewObjectDiagramModel model,

                                         ApplicationController applicationController,

                                         ContextMenuProvider contextMenuProvider,

                                         PlacementRepository placementRepository,

                                         MSystem system) {

        this.view = view;

        this.diagram = diagram;

        this.model = model;

        this.applicationController = applicationController;

        this.contextMenuProvider = contextMenuProvider;

        this.placementRepository = placementRepository;

        this.system = system;

        log("<init>", CONSTRUCTED_PREFIX + (view == null ? "null" : view.getClass().getSimpleName())
                + COMMA_DIAGRAM + (diagram == null ? "null" : diagram.getClass().getSimpleName())
                + COMMA_MODEL + (model == null ? "null" : model.getClass().getSimpleName()));

        if (this.system != null) {

            this.system.getEventBus().register(this);

        }

    }

    private static int sizeOf(Collection<?> values) {

        return values == null ? 0 : values.size();

    }

    private static int sizeOf(Set<?> values) {

        return values == null ? 0 : values.size();

    }

    private static int sizeOf(List<?> values) {

        return values == null ? 0 : values.size();

    }

    private void log(String method, String message) {

        PresenterFileLogger.log(getClass(), method, message);

    }

    @Override

    public void onRequestPopup() {

        log("onRequestPopup", "requested popup menu");

        // will be implemented when ContextMenuProvider is wired

    }

    @Override

    public void onAddObject(Value value) {

        log("onAddObject", "value=" + value);

        // will be implemented during migration

    }

    @Override

    public void onRefreshRequested() {

        log("onRefreshRequested", "refresh requested");

        refreshDiagram(true);

        onStatusMessage("Diagram refreshed");

    }

    @Override

    public void onCreateObject(String clsName) {

        log("onCreateObject", "clsName=" + clsName);

        if (applicationController != null) {

            applicationController.createObject(clsName);

            onStatusMessage("Create object: " + clsName);

        }

    }

    @Override

    public void onResetDiagram(ObjDiagramOptions options) {

        log("onResetDiagram", "options=" + options);

        if (applicationController != null) {

            applicationController.resetDiagram(options);

        }

        if (model != null) {

            model.reset();

        }

        refreshDiagram(true);

        onStatusMessage("Diagram reset");

    }

    @Override

    public void onHighlightChange(HighlightChangeEvent e) {

        log("onHighlightChange", PREFIX_EVENT + e);

        if (diagram instanceof NewObjectDiagram diag && e != null) {

            diag.handleHighlightChange(e);

        }

    }

    @Override

    @Subscribe

    public void onTransition(TransitionEvent e) {

        log("onTransition", PREFIX_EVENT + e);

        if (diagram == null || e == null) {

            return;

        }

        diagram.updateObject(e.getSource());

        refreshDiagram(true);

    }

    private void refreshDiagram(boolean fullLayout) {

        if (diagram != null) {

            diagram.invalidateContent(fullLayout);

        }

        if (view != null) {

            view.refresh();

        }

    }

    @Subscribe

    public void onObjectCreated(ObjectCreatedEvent e) {

        if (e == null) {

            return;

        }

        onObjectCreated(e.getCreatedObject());

    }

    @Override

    public void onObjectCreated(MObject obj) {

        log("onObjectCreated", PREFIX_OBJECT + obj);

        if (diagram == null || obj == null || obj instanceof MLink) {

            return;

        }

        diagram.addObject(obj);

        refreshDiagram(true);

    }

    @Subscribe

    public void onObjectDestroyed(ObjectDestroyedEvent e) {

        if (e == null) {

            return;

        }

        onObjectDestroyed(e.getDestroyedObject());

    }

    @Override

    public void onObjectDestroyed(MObject obj) {

        log("onObjectDestroyed", PREFIX_OBJECT + obj);

        if (diagram == null || obj == null || obj instanceof MLink) {

            return;

        }

        diagram.deleteObject(obj);

        refreshDiagram(true);

    }

    @Override

    @Subscribe

    public void onAttributeAssigned(AttributeAssignedEvent e) {

        log("onAttributeAssigned", PREFIX_EVENT + e);

        if (diagram == null || e == null || e.getObject() == null) {

            return;

        }

        diagram.updateObject(e.getObject());

        refreshDiagram(true);

    }

    @Override

    @Subscribe

    public void onLinkInserted(LinkInsertedEvent e) {

        log("onLinkInserted", PREFIX_EVENT + e);

        if (diagram == null || e == null) {

            return;

        }

        MLink link = e.getLink();

        if (link == null) {

            return;

        }

        if (link instanceof MLinkObject linkObject) {

            diagram.addObject(linkObject);

        }

        diagram.addLink(link);

        refreshDiagram(true);

    }

    @Override

    @Subscribe

    public void onLinkDeleted(LinkDeletedEvent e) {

        log("onLinkDeleted", PREFIX_EVENT + e);

        if (diagram == null || e == null) {

            return;

        }

        MLink link = e.getLink();

        if (link == null) {

            return;

        }

        if (link instanceof MLinkObject linkObject) {

            diagram.deleteObject(linkObject);

        }

        diagram.deleteLink(link);

        refreshDiagram(true);

    }

    @Override

    public void onPopupMenuPrepared(JPopupMenu menu,

                                    Point popupPosition,

                                    Set<MObject> selectedObjects,

                                    Set<MLink> selectedLinks,

                                    Set<MObject> selectedAssocObjects) {

        log("onPopupMenuPrepared", "popupPosition=" + popupPosition + ", selectedObjects=" + sizeOf(selectedObjects)

                + ", selectedLinks=" + sizeOf(selectedLinks) + ", selectedAssocObjects=" + sizeOf(selectedAssocObjects));

        if (contextMenuProvider != null && menu != null) {

            contextMenuProvider.enhanceMenu(menu, diagram, this, popupPosition,

                    selectedObjects, selectedLinks, selectedAssocObjects);

        }

    }

    @Override

    public void onStatusMessage(String status) {

        log("onStatusMessage", status);

        if (view != null && status != null) {

            view.setStatus(status);

        }

    }

    @Override

    public void onBuildShowHideCropMenu(ShowHideCropMenuBuilder builder, Set<MObject> selectedObjects) {

        log("onBuildShowHideCropMenu", "selectedObjects=" + sizeOf(selectedObjects));

        if (builder == null) {

            return;

        }

        if (selectedObjects != null && !selectedObjects.isEmpty()) {

            builder.addSelectedObjectPathView(selectedObjects);

        }

        builder.addSelectionWithOCLViewAction();

        builder.addSelectionObjectView();

    }

    @Override

    public void onInsertLink(MAssociation association, List<MObject> participants) {

        log("onInsertLink", PREFIX_ASSOCIATION + association + ", participants=" + sizeOf(participants));

        if (applicationController != null) {

            applicationController.insertLink(association, participants);

        } else if (view instanceof NewObjectDiagramView v) {

            v.insertLink(association, participants.toArray(new MObject[0]));

        }

    }

    @Override

    public void onDeleteLink(MLink link) {

        log("onDeleteLink", PREFIX_LINK + link);

        if (applicationController != null) {

            applicationController.deleteLink(link);

        } else if (view instanceof NewObjectDiagramView v) {

            v.deleteLink(link);

        }

        if (diagram != null) {

            diagram.clearSelection();

        }

    }

    @Override

    public void onDeleteObjects(Set<MObject> objects) {

        log("onDeleteObjects", PREFIX_COUNT + sizeOf(objects));

        if (applicationController != null) {

            applicationController.deleteObjects(objects);

        } else if (view instanceof NewObjectDiagramView v) {

            v.deleteObjects(objects);

        }

        if (diagram != null) {

            diagram.clearSelection();

        }

    }

    @Override

    public void onShowObjectProperties(MObject object) {

        log("onShowObjectProperties", PREFIX_OBJECT + object);

        if (applicationController != null && object != null) {

            applicationController.showObjectProperties(object.name());

        }

    }

    @Override

    public Set<MLink> fetchLinksOfAssociation(MAssociation association) {

        log("fetchLinksOfAssociation", PREFIX_ASSOCIATION + association);

        if (association == null || system == null) {

            return Set.of();

        }

        return system.state().linksOfAssociation(association).links();

    }

    @Override

    public Set<MObject> fetchObjectsOfClass(MClass cls) {

        log("fetchObjectsOfClass", PREFIX_CLASS + cls);

        if (cls == null || system == null) {

            return Set.of();

        }

        return system.state().objectsOfClass(cls);

    }

    @Override

    public Collection<MAssociation> fetchAllAssociations() {

        log("fetchAllAssociations", MSG_REQUESTED);

        if (system == null) {

            return Set.of();

        }

        return system.model().associations();

    }

    @Override

    public void onHideLinks(Collection<MLink> links) {

        log("onHideLinks", PREFIX_COUNT + sizeOf(links));

        if (diagram == null || links == null) {

            return;

        }

        links.forEach(diagram::hideLink);

        refreshDiagram(true);

    }

    @Override

    public void onShowLinks(Collection<MLink> links) {

        log("onShowLinks", PREFIX_COUNT + sizeOf(links));

        if (diagram == null || links == null) {

            return;

        }

        if (diagram instanceof NewObjectDiagram newDiagram) {

            newDiagram.showLink(new java.util.ArrayList<>(links));

            refreshDiagram(true);

        }

    }

    @Override

    public void onHideObjects(Collection<MObject> objects) {

        log("onHideObjects", PREFIX_COUNT + sizeOf(objects));

        if (diagram == null || objects == null) {

            return;

        }

        objects.forEach(diagram::hideObject);

        refreshDiagram(true);

    }

    @Override

    public void onHideAllLinks() {

        log("onHideAllLinks", MSG_REQUESTED);

        if (diagram == null) {

            return;

        }

        diagram.hideAllLinks();

        refreshDiagram(true);

    }

    @Override

    public void onShowAllLinks() {

        log("onShowAllLinks", MSG_REQUESTED);

        if (diagram == null) {

            return;

        }

        diagram.showAllLinks();

        refreshDiagram(true);

    }

    @Override

    public void onShowHiddenElements() {

        log("onShowHiddenElements", "requested; hiddenObjects=" + hiddenObjectCount() + ", hiddenLinks=" + hiddenLinkCount());

        if (diagram == null) {

            return;

        }

        diagram.showAll();

        diagram.showAllLinks();

        refreshDiagram(true);

    }

    @Override

    public void onCropSelection(Collection<MObject> objectsToHide) {

        log("onCropSelection", "keepCount=" + sizeOf(objectsToHide));

        if (diagram == null || objectsToHide == null || objectsToHide.isEmpty()) {

            return;

        }

        if (diagram instanceof NewObjectDiagram newDiagram) {

            Set<MObject> keepObjects = new java.util.LinkedHashSet<>(objectsToHide);

            Set<MObject> visibleObjects = new java.util.LinkedHashSet<>(newDiagram.getVisibleData().getObjectToNodeMap().keySet());

            visibleObjects.removeAll(keepObjects);

            visibleObjects.forEach(diagram::hideObject);

        } else {

            objectsToHide.forEach(diagram::hideObject);

        }

        diagram.clearSelection();

        refreshDiagram(true);

    }

    @Override

    public void onGrayOut(Collection<MObject> objects) {

        log("onGrayOut", PREFIX_COUNT + sizeOf(objects));

        if (diagram == null || objects == null) {

            return;

        }

        objects.forEach(obj -> diagram.setGreyed(obj, true));

        refreshDiagram(false);

    }

    @Override

    public void onGrayIn(Collection<MObject> objects) {

        log("onGrayIn", PREFIX_COUNT + sizeOf(objects));

        if (diagram == null || objects == null) {

            return;

        }

        objects.forEach(obj -> diagram.setGreyed(obj, false));

        refreshDiagram(false);

    }

    @Override

    public void onToggleShowStates(boolean show) {

        log("onToggleShowStates", "show=" + show);

        if (diagram == null) {

            return;

        }

        diagram.setShowStates(show);

        refreshDiagram(true);

    }

    @Override

    public void onStoreLayout(PersistHelper helper,

                              Element root,

                              NewObjectDiagramModel model) {

        log("onStoreLayout", "helper=" + helper + ", root=" + root + COMMA_MODEL + model);

        if (placementRepository != null && helper != null && root != null) {

            placementRepository.storeLayout(model, helper, root);

        }

    }

    @Override

    public Set<MObject> onRestoreLayout(PersistHelper helper,

                                        int version,

                                        NewObjectDiagramModel model,

                                        MSystem system) {

        log("onRestoreLayout", "version=" + version + ", helper=" + helper + COMMA_MODEL + model);

        if (placementRepository != null && helper != null) {

            return placementRepository.restoreLayout(model, helper, version, system);

        }

        return Set.of();

    }

    public NewObjectDiagramModel getModel() {

        return model;

    }

    MSystem getSystem() {

        return system;

    }

    @Override

    public void detach() {

        log("detach", MSG_REQUESTED);

        if (system != null) {

            try {

                system.getEventBus().unregister(this);

            } catch (Exception ignored) {

                // ignore if already unregistered

            }

        }

        // ensure diagram refresh stops referencing this presenter

        refreshDiagram(false);

    }

    private int hiddenObjectCount() {

        if (!(diagram instanceof NewObjectDiagram newDiagram) || newDiagram.getHiddenNodes() == null) {

            return 0;

        }

        return newDiagram.getHiddenNodes().size();

    }

    private int hiddenLinkCount() {

        if (!(diagram instanceof NewObjectDiagram newDiagram)

                || newDiagram.getHiddenData() == null

                || newDiagram.getHiddenData().getEdges() == null) {

            return 0;

        }

        return newDiagram.getHiddenData().getEdges().size();

    }

}