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

package org.tzi.use.gui.views.diagrams.event;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiagramView.DiagramData;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

@SuppressWarnings("serial")
public class ActionSelectAll extends AbstractAction {
    private Selection<PlaceableNode> fSelection;
    private DiagramView fDiagram;
    
    public ActionSelectAll( Selection<PlaceableNode> selection, DiagramView diagram ) {
        super( "Select all" );
        fSelection = selection;
        fDiagram = diagram;
    }
    
    public void actionPerformed( ActionEvent e ) {
    	DiagramData data = fDiagram.getVisibleData(); 
    	// Not all diagrams support hide and show operations
    	if (data != null)
    		fSelection.addAll(data.getNodes());
    	else
    		fSelection.addAll(fDiagram.getGraph().getNodes());
    	
    	fDiagram.repaint();
    }
    
}
