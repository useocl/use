package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;

import java.awt.event.KeyListener;
import java.awt.Font;
import java.util.Set;

/**
 * Minimal host context for the object diagram to reduce direct coupling to the view.
 */
public interface DiagramContext {
    MSystem system();
    ModelBrowser getModelBrowser();
    boolean isSelectedView();
    void addKeyListener(KeyListener listener);
    Font getFont();
    void setFont(Font font);
    void createObject(String className);
    void insertLink(MAssociation association, MObject[] objects);
    void deleteLink(MLink link);
    void deleteObjects(Set<MObject> objects);
    void resetDiagram(ObjDiagramOptions options);
}
