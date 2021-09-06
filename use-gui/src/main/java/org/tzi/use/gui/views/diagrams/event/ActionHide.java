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

import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;

/**
 * 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public abstract class ActionHide<NodeType> extends AbstractAction {
    /**
     * All nodes which are suppose to be hidden in a diagram.
     */
    Set<NodeType> fNodesToHide;
    
    /**
     * Actual selected nodes in the diagram.
     */
    Selection<? extends Selectable> fNodeSelection;
    
    /**
     * This graph contains all nodes and edges of a diagram.  
     */
    DirectedGraph<PlaceableNode, EdgeBase> fGraph;
    
    DiagramView diagram;
    
    ActionHide( String text, DiagramView diagram, Selection<? extends Selectable> nodeSelection ) {
        super( text );
        this.diagram = diagram;
        this.fNodeSelection = nodeSelection;
    }

    void setNodes( Set<? extends NodeType> set ) {
        if ( set != null ) {
            fNodesToHide = new HashSet<NodeType>(set);
        }
    }
    
    /**
     * Displays all hidden elements again. The hidden elements need to be 
     * added to the diagram again, because they were deleted from the 
     * view before.
     */
    public abstract void showAllHiddenElements();
            
    /**
     * Hides all nodes with there connecting edges.
     */
    public abstract void hideNodesAndEdges();
    
}