package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectDiagramModel;

/**
 * No-op placement repository used during incremental migration.
 */
public class NullPlacementRepository implements PlacementRepository {
    @Override
    public void storeLayout(ObjectDiagramModel model) {
        // no-op
    }

    @Override
    public void restoreLayout(ObjectDiagramModel model) {
        // no-op
    }
}
