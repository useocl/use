package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.uml.sys.MSystem;
import org.w3c.dom.Element;
import java.util.Set;

/**
 * Persistence boundary for diagram layouts/placements. Concrete implementations
 * decide how and where to persist. Kept small to align with the MVP target
 * architecture.
 */
public interface PlacementRepository {
    void storeLayout(NewObjectDiagramModel model,
                     PersistHelper helper,
                     Element root);

    Set<org.tzi.use.uml.sys.MObject> restoreLayout(NewObjectDiagramModel model,
                                                   PersistHelper helper,
                                                   int version,
                                                   MSystem system);
}
