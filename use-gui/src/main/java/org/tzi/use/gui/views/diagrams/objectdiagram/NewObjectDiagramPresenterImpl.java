package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.TransitionEvent;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;
import com.google.common.eventbus.Subscribe;

import javax.swing.JPopupMenu;
import java.awt.Point;
import java.util.Collection;
import java.util.Set;
import java.util.List;
import org.tzi.use.gui.util.PersistHelper;
import org.w3c.dom.Element;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;

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
        if (this.system != null) {
            this.system.getEventBus().register(this);
        }
    }

    @Override
    public void onRequestPopup() {
        // will be implemented when ContextMenuProvider is wired
    }

    @Override
    public void onAddObject(Value value) {
        // will be implemented during migration
    }

    @Override
    public void onRefreshRequested() {
        refreshDiagram(true);
        onStatusMessage("Diagram refreshed");
    }

    @Override
    public void onCreateObject(String clsName) {
        if (applicationController != null) {
            applicationController.createObject(clsName);
            onStatusMessage("Create object: " + clsName);
        }
    }

    @Override
    public void onResetDiagram(ObjDiagramOptions options) {
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
        if (diagram instanceof NewObjectDiagram diag && e != null) {
            diag.handleHighlightChange(e);
        }
    }

    @Override
    @Subscribe
    public void onTransition(TransitionEvent e) {
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
        if (diagram == null || obj == null || obj instanceof MLink) {
            return;
        }
        diagram.deleteObject(obj);
        refreshDiagram(true);
    }

    @Override
    @Subscribe
    public void onAttributeAssigned(AttributeAssignedEvent e) {
        if (diagram == null || e == null || e.getObject() == null) {
            return;
        }
        diagram.updateObject(e.getObject());
        refreshDiagram(true);
    }

    @Override
    @Subscribe
    public void onLinkInserted(LinkInsertedEvent e) {
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
        if (contextMenuProvider != null && menu != null) {
            contextMenuProvider.enhanceMenu(menu, diagram, this, popupPosition,
                    selectedObjects, selectedLinks, selectedAssocObjects);
        }
    }

    @Override
    public void onStatusMessage(String status) {
        if (view != null && status != null) {
            view.setStatus(status);
        }
    }

    @Override
    public void onBuildShowHideCropMenu(ShowHideCropMenuBuilder builder, Set<MObject> selectedObjects) {
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
        if (applicationController != null) {
            applicationController.insertLink(association, participants);
        } else if (view instanceof NewObjectDiagramView v) {
            v.insertLink(association, participants.toArray(new MObject[0]));
        }
    }

    @Override
    public void onDeleteLink(MLink link) {
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
        if (applicationController != null && object != null) {
            applicationController.showObjectProperties(object.name());
        }
    }

    @Override
    public Set<MLink> fetchLinksOfAssociation(MAssociation association) {
        if (association == null || system == null) {
            return Set.of();
        }
        return system.state().linksOfAssociation(association).links();
    }

    @Override
    public Set<MObject> fetchObjectsOfClass(MClass cls) {
        if (cls == null || system == null) {
            return Set.of();
        }
        return system.state().objectsOfClass(cls);
    }

    @Override
    public Collection<MAssociation> fetchAllAssociations() {
        if (system == null) {
            return Set.of();
        }
        return system.model().associations();
    }

    @Override
    public void onHideLinks(Collection<MLink> links) {
        if (diagram == null || links == null) {
            return;
        }
        links.forEach(diagram::hideLink);
        refreshDiagram(true);
    }

    @Override
    public void onShowLinks(Collection<MLink> links) {
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
        if (diagram == null || objects == null) {
            return;
        }
        objects.forEach(diagram::hideObject);
        refreshDiagram(true);
    }

    @Override
    public void onHideAllLinks() {
        if (diagram == null) {
            return;
        }
        diagram.hideAllLinks();
        refreshDiagram(true);
    }

    @Override
    public void onShowAllLinks() {
        if (diagram == null) {
            return;
        }
        diagram.showAllLinks();
        refreshDiagram(true);
    }

    @Override
    public void onShowHiddenElements() {
        if (diagram == null) {
            return;
        }
        diagram.showAll();
        diagram.showAllLinks();
        refreshDiagram(true);
    }

    @Override
    public void onCropSelection(Collection<MObject> objectsToHide) {
        if (diagram == null || objectsToHide == null) {
            return;
        }
        objectsToHide.forEach(diagram::hideObject);
        refreshDiagram(true);
    }

    @Override
    public void onGrayOut(Collection<MObject> objects) {
        if (diagram == null || objects == null) {
            return;
        }
        objects.forEach(obj -> diagram.setGreyed(obj, true));
        refreshDiagram(false);
    }

    @Override
    public void onGrayIn(Collection<MObject> objects) {
        if (diagram == null || objects == null) {
            return;
        }
        objects.forEach(obj -> diagram.setGreyed(obj, false));
        refreshDiagram(false);
    }

    @Override
    public void onToggleShowStates(boolean show) {
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
        if (placementRepository != null && helper != null && root != null) {
            placementRepository.storeLayout(model, helper, root);
        }
    }

    @Override
    public Set<MObject> onRestoreLayout(PersistHelper helper,
                                          int version,
                                          NewObjectDiagramModel model,
                                          MSystem system) {
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
}
