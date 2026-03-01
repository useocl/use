package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import java.util.List;

/**
 * Boundary for executing application/system commands.
 * Methods are minimal and will be expanded during migration.
 */
public interface ApplicationController {
    void createObject(String className);
    void insertLink(MAssociation association, List<MObject> participants);
    void deleteLink(MLink link);
}

