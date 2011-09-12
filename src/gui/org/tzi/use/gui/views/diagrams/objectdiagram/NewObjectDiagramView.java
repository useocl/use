/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/* $ProjectHeader: use 2-3-1-release.3 Wed, 02 Aug 2006 17:53:29 +0200 green $ */

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.BorderLayout;
import java.awt.print.PageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MSequenceStatement;

/** 
 * A graph showing an object diagram with objects and links in the
 * current system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class NewObjectDiagramView extends JPanel 
  implements View, PrintableView, SortChangeListener {

    private MSystem fSystem;
    private MainWindow fMainWindow;

    protected NewObjectDiagram fObjectDiagram;
    public static int viewcount = 0; // jj

    public NewObjectDiagramView(MainWindow mainWindow, MSystem system) {
        fMainWindow = mainWindow;
        fSystem = system;
        fSystem.addChangeListener(this);
        ModelBrowserSorting.getInstance().addSortChangeListener( this );

        setLayout(new BorderLayout());

        fObjectDiagram = new NewObjectDiagram(this, mainWindow.logWriter());
        add( new JScrollPane(fObjectDiagram) );

        this.setFocusable(true);
        
        initState();
    }

    /**
     * Returns the model browser.
     */
    public ModelBrowser getModelBrowser() {
        return fMainWindow.getModelBrowser();
    }
    
    /**
     * The managed diagram
     * @return
     */
    public NewObjectDiagram getDiagram() {
    	return this.fObjectDiagram;
    }
    
    /**
     * Determinds if this is the selected view.
     * @return <code>true</code> if it is the selected view, otherwise
     * <code>false</false>
     */
    public boolean isSelectedView() {
        if ( fMainWindow.getSelectedView() != null ) {
            return fMainWindow.getSelectedView().equals( this );
        } 
        return false;
    }
    
    
    /**
     * Does a full update of the view.
     */
    private void initState() {        
        for (MObject obj : fSystem.state().allObjects()) {
            fObjectDiagram.addObject(obj);
        }

        for (MLink link : fSystem.state().allLinks()) {
            fObjectDiagram.addLink(link);
        }
        
        fObjectDiagram.invalidateContent();
        viewcount++; // jj
    }

    /**
     * Does an incremental update of the view.
     */
    public void stateChanged(StateChangeEvent e) {

    	for (MObject obj : e.getNewObjects())
            fObjectDiagram.addObject(obj);

        for (MLink link : e.getNewLinks())
            fObjectDiagram.addLink(link);

        for (MLink link : e.getDeletedLinks())
            fObjectDiagram.deleteLink(link, false);
        
        for (MObject obj : e.getDeletedObjects())
            fObjectDiagram.deleteObject(obj);

        // attribute value changes are always recognized since a
        // repaint forces object nodes to ask for the current
        // attribute values.
        fObjectDiagram.invalidateContent();
    }
    
    /**
     * After the occurence of an event the view is updated.
     */
    public void stateChanged( SortChangeEvent e ) {
        fObjectDiagram.invalidateContent();
    }

    /**
     * Returns the set of all associations that exist between the
     * specified classes. 
     *
     * @return Set(MAssociation) 
     */
    Set<MAssociation> getAllAssociationsBetweenClasses(Set<MClass> classes) {
        return fSystem.model().getAllAssociationsBetweenClasses(classes);
    }

    /**
     * Returns true if there is a link of the specified association
     * connecting the given set of objects.
     */
    boolean hasLinkBetweenObjects(MAssociation assoc, MObject[] objects) {
        return fSystem.state().hasLinkBetweenObjects(assoc, objects);
    }

    /**
     * Executes a command for deleting a link between selected 
     * objects.
     */
    void deleteLink(MAssociation association, MObject[] objects) {
    	try {
			fSystem.evaluateStatement(
					new MLinkDeletionStatement(association, objects, Collections.<List<MRValue>>emptyList()));
		} catch (MSystemException e) {
			JOptionPane.showMessageDialog(
					fMainWindow, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
    }
    
    /**
     * Executes a command for inserting a new link.
     */
    void insertLink(MAssociation association, MObject[] objects) {
    	try {
			fSystem.evaluateStatement(
					new MLinkInsertionStatement(association, objects, Collections.<List<Value>>emptyList()));
		} catch (MSystemException e) {
			JOptionPane.showMessageDialog(
					fMainWindow, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
    }

    
    /**
     * Executes a command for deleting selected objects.
     */
    void deleteObjects(Set<MObject> objects) {
        MSequenceStatement sequence = new MSequenceStatement();
        
        for (MObject obj : objects) {
        	sequence.appendStatement(
        			new MObjectDestructionStatement(obj.value()));
        }
        
        if (sequence.isEmpty()) {
        	return;
        }
        try {
			fSystem.evaluateStatement(sequence.simplify());
		} catch (MSystemException e) {
			JOptionPane.showMessageDialog(
					fMainWindow, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
    }

    public MSystem system() { // jj add public
        return fSystem;
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
        viewcount--; // jj
    }

    void createObject(String clsName) {
        fMainWindow.createObject(clsName);
    }

    public void printView(PageFormat pf) {
        fObjectDiagram.printDiagram(pf, "Object diagram" );
    }

}
