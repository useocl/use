package org.tzi.use.runtime.gui.impl;

import org.tzi.use.gui.views.diagrams.DiagramView;
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
     * Constructs a handle to the requested diagram view.
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
    public abstract void doRun();


    protected final List<PlaceableNode> getGraphNodes() {
        return diagramViews.stream().flatMap(view -> view.getGraph().getNodes().stream()).toList();
    }

    protected final void addNode(final PlaceableNode node) {
        diagramViews.forEach(view -> view.getGraph().add(node));
    }

    protected final List<PlaceableNode> getNodes(final String name) {
        return diagramViews.stream().map(view -> view.getGraph().getNodes().stream().filter(node -> node.name().equals(name)).findFirst().orElse(null)).toList();
    }

    protected final void addAnnotation(final PlaceableNode node) {
//        diagramViews.forEach(view -> view.getGraph().getNodes().)
    }

    protected final List<PlaceableNode> getNodesByAnnotation(final String annotationName) {
//        return diagramViews.stream().map(view -> view.getGraph().getNodes().stream().filter((ClassNode node) -> node.cls().getAllAnnotations().).findFirst().orElse(null)).toList();
        return null;
    }


}
