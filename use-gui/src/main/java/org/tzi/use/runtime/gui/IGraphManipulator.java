package org.tzi.use.runtime.gui;

import org.tzi.use.gui.views.diagrams.DiagramGraph;

public interface IGraphManipulator {
    /**
     * Manipulate a given graph object on ClassDiagramView initialization.
     *
     * @param graph - the graph object to manipulate
     */
    void manipulateOnInitialization(DiagramGraph graph);
}
