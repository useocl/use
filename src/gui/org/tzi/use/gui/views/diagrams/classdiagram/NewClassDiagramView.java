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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.BorderLayout;
import java.awt.print.PageFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;

/**
 * A graph showing an class diagram with all elements in the
 * current instance of MModel
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 * */
public class NewClassDiagramView extends JPanel 
                                 implements View, 
                                            PrintableView {

    private MainWindow fMainWindow;
    private MSystem fSystem;
    private NewClassDiagram fClassDiagram;

    public NewClassDiagramView( MainWindow mainWindow, MSystem system ) { 
        fMainWindow = mainWindow;
        fSystem = system;
        fSystem.addChangeListener( this );
        setLayout( new BorderLayout() );
        fClassDiagram = new NewClassDiagram( this, mainWindow.logWriter() );
        add( "Center", fClassDiagram );
        initState();
    }

    public MSystem system() {
        return fSystem;
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
     * Read  all instances of MModel and maps
     * the specified element to a graphic
     * instance.
     */
    private void initState() {
        // read Classes
        Collection allClasses = fSystem.model().classes();
        Iterator itClass = allClasses.iterator();
        while ( itClass.hasNext() ) {
            MClass cls = ( MClass ) itClass.next();
            fClassDiagram.addClass( cls );
        }

        // read Enumerations
        Collection allEnums = fSystem.model().enumTypes();
        Iterator itEnums = allEnums.iterator();
        while ( itEnums.hasNext() ) {
            EnumType enumeration = ( EnumType ) itEnums.next();
            fClassDiagram.addEnum( enumeration );
        }

        // read generalizations
        DirectedGraph genGraph = fSystem.model().generalizationGraph();
        Iterator edgeIter = genGraph.edgeIterator();
        while ( edgeIter.hasNext() ) {
            MGeneralization gen = ( MGeneralization ) edgeIter.next();
            fClassDiagram.addGeneralization( gen );
        }
 
        // read Associations
        Collection allAssociations = fSystem.model().associations();
        Iterator itAssoc = allAssociations.iterator();
        while ( itAssoc.hasNext() ) {
            MAssociation assoc = ( MAssociation ) itAssoc.next();
            fClassDiagram.addAssociation( assoc );
        }  
    }
    
    public void printView( PageFormat pf ) {
        fClassDiagram.printDiagram( pf, "Class diagram" );
    }

    public void stateChanged( StateChangeEvent e ) {}

    public void detachModel () {}

}