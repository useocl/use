package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import java.util.List;

/**
 * No-op controller used during incremental migration.
 */
public class NullApplicationController implements ApplicationController {
    @Override
    public void createObject(String className) {
        // no-op
    }

    @Override
    public void insertLink(MAssociation association, List<MObject> participants) {
        // no-op
    }

    @Override
    public void deleteLink(MLink link) {
        // no-op
    }
}

