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

import java.util.Set;

import javax.swing.Action;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;

/**
 * Coordinates the hiding of nodes and edges in diagrams.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
 public final class HideAdministration {
    private ActionHide fHideAction;

    /**
     * All actuall hidden nodes in a diagram.
     */
    private Set<Object> fHiddenNodes;
    /**
     * Actual selected nodes in the diagram.
     */
    private Selection fNodeSelection;
    /**
     * This graph contains all nodes and edges of a diagram.  
     */
    private DirectedGraph<NodeBase, EdgeBase> fGraph;
    /**
     * The diagram to which the graph, nodes and edges belong to.
     */
    private DiagramView fDiagram;
    /**
     * Layout information about the given diagram. 
     */    
    private LayoutInfos fLayoutInfos;
    
    public HideAdministration( Selection nodeSelection, DirectedGraph<NodeBase, EdgeBase> graph, 
                               LayoutInfos layoutInfos ) {
        fNodeSelection = nodeSelection;
        fGraph = graph;
        fLayoutInfos = layoutInfos;
        fDiagram = layoutInfos.getDiagram();
        
        if ( fDiagram instanceof NewObjectDiagram ) {
            fHideAction = new ActionHideObjectDiagram( "Hide", fHiddenNodes,
                                                       fNodeSelection, fGraph,
                                                       fLayoutInfos );
        } else if ( fDiagram instanceof ClassDiagram ) {
            fHideAction = new ActionHideClassDiagram( "Hide", fHiddenNodes,
                                                      fNodeSelection, fGraph,
                                                      fLayoutInfos );
        }
    }

    public Action getAction( String text, Set<?> selectedNodes ) {
        if ( fDiagram instanceof NewObjectDiagram ) {
            fHideAction = new ActionHideObjectDiagram( text, selectedNodes,
                                                       fNodeSelection, fGraph,
                                                       fLayoutInfos );
        } else if ( fDiagram instanceof ClassDiagram ) {
            fHideAction = new ActionHideClassDiagram( text, selectedNodes,
                                                      fNodeSelection, fGraph,
                                                      fLayoutInfos );
        }
        return fHideAction;
    }

    /**
     * Displays all hidden elements again. The hidden elements need to be 
     * added to the diagram again, because they were deleted from the 
     * view before.
     */
    public void showAllHiddenElements() {
        fHideAction.showAllHiddenElements();
    }

    public void showHiddenElements(Set<?> hiddenNodes) {
    	fHideAction.showHiddenElements(hiddenNodes);
    }

}