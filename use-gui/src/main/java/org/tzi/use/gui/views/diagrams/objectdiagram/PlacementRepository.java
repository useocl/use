package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectDiagramModel;

/**
 * Persistence boundary for diagram layouts/placements.
 * Implementation will handle IO; kept minimal for MVP wiring.
 */
public interface PlacementRepository {
    void storeLayout(ObjectDiagramModel model);
    void restoreLayout(ObjectDiagramModel model);
}
