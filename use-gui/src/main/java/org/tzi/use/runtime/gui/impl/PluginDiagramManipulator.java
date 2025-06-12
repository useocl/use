package org.tzi.use.runtime.gui.impl;

import lombok.Getter;
import org.tzi.use.gui.views.diagrams.DiagramView;
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

    @Getter
    private final Class<? extends DiagramView> targetClass;

    @Getter
    protected StyleInfoProvider styleInfoProvider;

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

    protected final void updateRegisteredDiagramViews() {
        diagramViews.clear();
        diagramViews.addAll(ALL_REGISTERED_DIAGRAM_VIEWS.stream().filter(targetClass::isInstance).toList());
    }

    /**
     * Gets called when the graphical panel diagram initializes.
     */
    public void onInitialisation() {
        // override to use
    }

    /**
     * Gets called when the graphical panel diagram closes.
     */
    public void onClosure() {
        // override to use
    }

    /**
     * Causes a repaint of all current diagram views as soon as possible.
     */
    protected final void markForRepainting() {
        diagramViews.forEach(DiagramView::repaint);
    }

    protected final void addNodeToGraph(final PlaceableNode node) {
        diagramViews.forEach(view -> view.getGraph().add(node));
    }
}
