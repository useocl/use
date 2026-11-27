package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import java.util.List;
import java.util.Set;


/**
 * Boundary for executing application/system commands.
 * Methods are minimal and will be expanded during migration.
 */
public interface ApplicationController {
    void createObject(String className);
    void insertLink(MAssociation association, List<MObject> participants);
    void deleteLink(MLink link);
    void deleteObjects(Set<MObject> objects);
    void resetDiagram(ObjDiagramOptions options);
    /** Open object properties UI for the given object. */
    void showObjectProperties(String objectName);
}
