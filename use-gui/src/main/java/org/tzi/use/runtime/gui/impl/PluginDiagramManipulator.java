package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * The plugin has to extend this class to get a handle to manipulate given parts of a diagram.
 */
public abstract class PluginDiagramManipulator {

    final static List<DiagramView> ALL_REGISTERED_DIAGRAM_VIEWS = new ArrayList<>();

    private final List<DiagramView> diagramViews;

    /**
     * Constructs a handle to the requested diagram view by setting the class variable.
     *
     * @param clazz type of the requested diagram view
     */
    protected PluginDiagramManipulator(Class<? extends DiagramView> clazz) {
        diagramViews = ALL_REGISTERED_DIAGRAM_VIEWS.stream().filter(view -> view.getClass().equals(clazz)).toList();
    }

    /**
     * This has to be implemented by the Plugin!
     * Executes the plugins behaviour after initialisation of given diagram.
     */
    public abstract void onInitialisation();

    protected DiagramOptions getDiagramOptions(){
        return diagramViews.get(0).getOptions();
    }

    protected final void repaint() {
        diagramViews.forEach(DiagramView::repaint);
    }

    protected final List<PlaceableNode> getGraphNodes() {
        return diagramViews.stream().flatMap(view -> view.getGraph().getNodes().stream()).toList();
    }

    protected final void addNodeToGraph(final PlaceableNode node) {
        diagramViews.forEach(view -> view.getGraph().add(node));
    }

    protected final List<PlaceableNode> findNodesByName(final String name) {
        return diagramViews.stream().map(view -> view.getGraph().getNodes().stream().filter(node -> node.name().equals(name)).findFirst().orElse(null)).toList();
    }


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
