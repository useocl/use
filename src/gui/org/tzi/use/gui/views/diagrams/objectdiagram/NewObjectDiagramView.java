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

// $Id$

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.BorderLayout;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StateChangeEvent;

/** 
 * A graph showing an object diagram with objects and links in the
 * current system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class NewObjectDiagramView extends JPanel 
  implements View, PrintableView, SortChangeListener {

    private MSystem fSystem;
    private MainWindow fMainWindow;

    NewObjectDiagram fObjectDiagram;

    public NewObjectDiagramView(MainWindow mainWindow, MSystem system) {
        fMainWindow = mainWindow;
        fSystem = system;
        fSystem.addChangeListener(this);
        ModelBrowserSorting.getInstance().addSortChangeListener( this );

        setLayout(new BorderLayout());

        fObjectDiagram = new NewObjectDiagram(this, mainWindow.logWriter());
        add("Center", fObjectDiagram);

        initState();
    }

    /**
     * Returns the model browser.
     */
    public ModelBrowser getModelBrowser() {
        return fMainWindow.getModelBrowser();
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
        MSystemState systemState = fSystem.state();
        Set objects = systemState.allObjects();
        Iterator it = objects.iterator();
        while (it.hasNext() ) {
            MObject obj = (MObject) it.next();
            fObjectDiagram.addObject(obj);
        }
        Set links = systemState.allLinks();
        it = links.iterator();
        while (it.hasNext() ) {
            MLink link = (MLink) it.next();
            fObjectDiagram.addLink(link);
        }
        fObjectDiagram.repaint();
    }

    /**
     * Does an incremental update of the view.
     */
    public void stateChanged(StateChangeEvent e) {
        Iterator it;

        it = e.getNewObjects().iterator();
        while (it.hasNext() )
            fObjectDiagram.addObject((MObject) it.next());

        it = e.getNewLinks().iterator();
        while (it.hasNext() )
            fObjectDiagram.addLink((MLink) it.next());

        it = e.getDeletedLinks().iterator();
        while (it.hasNext() )
            fObjectDiagram.deleteLink((MLink) it.next(), false);
        
        it = e.getDeletedObjects().iterator();
        while (it.hasNext() )
            fObjectDiagram.deleteObject((MObject) it.next());

        // attribute value changes are always recognized since a
        // repaint forces object nodes to ask for the current
        // attribute values.
        fObjectDiagram.repaint();
    }
    
    /**
     * After the occurence of an event the view is updated.
     */
    public void stateChanged( SortChangeEvent e ) {
        fObjectDiagram.repaint();
    }

    /**
     * Returns the set of all associations that exist between the
     * specified classes. 
     *
     * @return Set(MAssociation) 
     */
    Set getAllAssociationsBetweenClasses(Set classes) {
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
    void deleteLink( String assocName, MObject[] objects ) {
        String objNameList = objectNameList( objects );
        String cmd = "delete (" + objNameList + ") from " + assocName;
        fMainWindow.execCmd( cmd );
    }
    
    /**
     * Executes a command for inserting a new link.
     */
    void insertLink(String assocName, MObject[] objects) {
        String objNameList = objectNameList( objects );
    
        String cmd = "insert (" + objNameList + ") into " + assocName;
        fMainWindow.execCmd(cmd);
    }

    /**
     * Writes all objects participating in an association into a 
     * String seperated by a comma. 
     * @param assocName Name of the association.
     * @param objects Selected objects. 
     * @return The list of object names separated by a comma.
     */
    private String objectNameList(MObject[] objects ) {
        String txt ="";
        for (int i=0;i<objects.length;++i) {
            if (i>0) txt = txt + ",";
            MObject o = objects[i];
            txt = txt + o.name();
        }
        return txt;
    }

    /**
     * Executes a command for deleting selected objects.
     */
    void deleteObjects(Set objects) {
        String cmd = null;
        Iterator it = objects.iterator();
        while ( it.hasNext() ) {
            MObject obj = ( MObject ) it.next();
            if ( cmd == null ) {
                cmd = "destroy " + obj.name();
            } else {
                cmd += "," + obj.name();
            }
        }
        fMainWindow.execCmd( cmd );
    }

    MSystem system() {
        return fSystem;
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
    }

    void createObject(String clsName) {
        ArrayList names = new ArrayList(1);
        names.add(fSystem.uniqueObjectNameForClass(clsName));
        fMainWindow.createObject(clsName, names);
    }

    public void printView(PageFormat pf) {
        fObjectDiagram.printDiagram(pf, "Object diagram" );
    }

}
