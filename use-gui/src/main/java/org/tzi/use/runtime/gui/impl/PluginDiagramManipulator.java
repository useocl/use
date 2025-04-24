package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The plugin has to extend this class to get a handle to manipulate given parts of a diagram.
 */
public abstract class PluginDiagramManipulator {

    final static List<DiagramView> ALL_REGISTERED_DIAGRAM_VIEWS = new ArrayList<>();

    private final Set<DiagramView> diagramViews;

    private final Class<? extends DiagramView> targetClass;

    /**
     * Constructs a handle to the requested diagram view by setting the class variable.
     *
     * @param clazz type of the requested diagram view
     */
    protected PluginDiagramManipulator(Class<? extends DiagramView> clazz) {
        this.targetClass = clazz;
        diagramViews = new HashSet<>();
        diagramViews.addAll(ALL_REGISTERED_DIAGRAM_VIEWS.stream().filter(targetClass::isInstance).toList());
    }

    protected void updateRegisteredDiagramViews() {
        diagramViews.clear();
        diagramViews.addAll(ALL_REGISTERED_DIAGRAM_VIEWS.stream().filter(targetClass::isInstance).toList());
    }

    /**
     * Executes the plugins behaviour after initialisation of given diagram.
     */
    public void onInitialisation() {
        // override to use
    }

    /**
     * Executes the plugins behaviour when the diagram is being closed / detached.
     */
    public void onClosure() {
        // override to use
    }

    protected void addStyleInfoProvider(final StyleInfoProvider styleInfoProvider) {
        DiagramExtensionPoint.getInstance().addStyleInfoProvider(styleInfoProvider);
    }


    protected final void repaint() {
        diagramViews.forEach(DiagramView::repaint);
    }

    protected final void addNodeToGraph(final PlaceableNode node) {
        diagramViews.forEach(view -> view.getGraph().add(node));
    }

    @Deprecated(forRemoval = true)
    protected final List<DiagramView.DiagramData> getDiagramDataByVisibility(final boolean isVisible) {
        return diagramViews.stream().map(isVisible ? DiagramView::getVisibleData : DiagramView::getHiddenData).toList();
    }

    /**
     * TODO: ClassNode only for certain use case. Abstract method to return node type
     *  i can build my own mapper
     */
    protected final void resetClassNodesColor() {
        getDiagramDataByVisibility(true).stream().map(DiagramView.DiagramData::getNodes).forEach(placeableNode -> placeableNode.forEach(node -> {
            if (placeableNode instanceof ClassNode classNode) {
                // or generalize methods to use those of PlaceableNode
                classNode.setColor(null);
                classNode.resetAttributeColor();
                classNode.resetOperationColor();
            }
        }));
        getDiagramDataByVisibility(true).stream().map(DiagramView.DiagramData::getNodes).forEach(placeableNode -> placeableNode.forEach(node -> {
            if (placeableNode instanceof ClassNode classNode) {
                classNode.setColor(null);
                classNode.resetAttributeColor();
                classNode.resetOperationColor();
            }
        }));
    }
}
