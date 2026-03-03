package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MSequenceStatement;

import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjDiagramOptions;

/**
 * Default bridge to execute domain commands against the USE system.
 * Keeps UI concerns (dialogs) minimal and delegates heavy UI (qualifier input) to QualifierInputView when needed.
 */
class DefaultApplicationController implements ApplicationController {
    private final MainWindow mainWindow;
    private final MSystem system;
    private final DiagramContext diagramContext;

    DefaultApplicationController(MainWindow mainWindow, MSystem system, DiagramContext diagramContext) {
        this.mainWindow = mainWindow;
        this.system = system;
        this.diagramContext = diagramContext;
    }

    @Override
    public void createObject(String className) {
        MainWindow.instance().createObject(className);
    }

    @Override
    public void insertLink(MAssociation association, List<MObject> participants) {
        MObject[] objects = participants.toArray(new MObject[0]);
        if (association.hasQualifiedEnds()) {
            QualifierInputView input = new QualifierInputView(mainWindow, system, association, objects);
            input.setLocationRelativeTo(mainWindow);
            input.setVisible(true);
            return;
        }

        try {
            system.execute(new MLinkInsertionStatement(association, objects, Collections.<List<Value>>emptyList()));
        } catch (MSystemException e) {
            JOptionPane.showMessageDialog(mainWindow, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteLink(MLink link) {
        try {
            system.execute(new MLinkDeletionStatement(link));
        } catch (MSystemException e) {
            JOptionPane.showMessageDialog(mainWindow, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteObjects(Set<MObject> objects) {
        MSequenceStatement sequence = new MSequenceStatement();
        for (MObject obj : objects) {
            sequence.appendStatement(new MObjectDestructionStatement(obj.value()));
        }
        if (sequence.isEmpty()) {
            return;
        }
        try {
            system.execute(sequence.simplify());
        } catch (MSystemException e) {
            JOptionPane.showMessageDialog(mainWindow, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void resetDiagram(ObjDiagramOptions options) {
        if (diagramContext != null) {
            diagramContext.resetDiagram(options);
        }
    }

    @Override
    public void showObjectProperties(String objectName) {
        ObjectPropertiesView v = MainWindow.instance().showObjectPropertiesView();
        v.selectObject(objectName);
    }
}
