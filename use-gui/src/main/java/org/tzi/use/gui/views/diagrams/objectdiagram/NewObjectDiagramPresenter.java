package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectDiagramModel;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.TransitionEvent;
import org.tzi.use.uml.sys.MObject;

/**
 * MVP presenter for the object diagram. Currently a skeleton with no logic; wiring will be added incrementally.
 */
public class NewObjectDiagramPresenter implements INewObjectDiagramPresenter {

    private final INewObjectDiagramView view;
    private final ObjectDiagramModel model;
    private final PlacementRepository placementRepository;
    private final ApplicationController applicationController;
    private final ContextMenuProvider contextMenuProvider;

    public NewObjectDiagramPresenter(INewObjectDiagramView view,
                                     ObjectDiagramModel model,
                                     PlacementRepository placementRepository,
                                     ApplicationController applicationController,
                                     ContextMenuProvider contextMenuProvider) {
        this.view = view;
        this.model = model;
        this.placementRepository = placementRepository;
        this.applicationController = applicationController;
        this.contextMenuProvider = contextMenuProvider;
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
        // will be implemented during migration
    }

    @Override
    public void onCreateObject(String clsName) {
        if (applicationController != null) {
            applicationController.createObject(clsName);
        }
    }

    @Override
    public void onTransition(TransitionEvent e) {
        // hook for future logic
    }

    @Override
    public void onObjectCreated(MObject obj) {
        // hook for future logic
    }

    @Override
    public void onObjectDestroyed(MObject obj) {
        // hook for future logic
    }

    @Override
    public void onAttributeAssigned(AttributeAssignedEvent e) {
        // hook for future logic
    }

    @Override
    public void onLinkInserted(LinkInsertedEvent e) {
        // hook for future logic
    }

    @Override
    public void onLinkDeleted(LinkDeletedEvent e) {
        // hook for future logic
    }

    public ObjectDiagramModel getModel() {
        return model;
    }
}
