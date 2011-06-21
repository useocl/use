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
import java.util.HashSet;
import java.util.Set;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;

/**
 * Hides the selected objects.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
  */
@SuppressWarnings("serial")
public final class ActionHideObjectDiagram extends ActionHide {
 
    public ActionHideObjectDiagram( String text, Set<?> nodesToHide,
                                    Selection<Selectable> nodeSelection,
                                    DirectedGraph<NodeBase, EdgeBase> graph, LayoutInfos layoutInfos ) {
        super(text);
        setNodes( nodesToHide );
        
        fLayoutInfos = layoutInfos;
        fNodeSelection = nodeSelection;
        fGraph = graph;
    }

    /**
     * Easier access to the object diagram
     * @return
     */
    protected NewObjectDiagram getDiagram() {
    	return (NewObjectDiagram)fLayoutInfos.getDiagram();
    }
    
    /**
     * Displays all hidden objects again. The objects have to be added
     * again, because they were deleted from the view before.
     */
    public void showAllHiddenElements() {
    	getDiagram().showAll();
        getDiagram().invalidateContent();
    }    

    /**
     * Hides all objects with there connecting links.
     */
    public void hideNodesAndEdges() {        
        fLayoutInfos.getDiagram().hideElementsInDiagram( fNodesToHide );
        fNodeSelection.clear();
        getDiagram().invalidateContent();
    }
    
    public void actionPerformed(ActionEvent e) {
        hideNodesAndEdges();
    }
    
    /*
     * Displays all hidden objects again. The objects have to be added
     * again, because they were deleted from the view before.
     */
    public void showHiddenElements(Set<?> hiddenElements) {
        
    	// Add all already hidden nodes
    	Set<Object> nodesToHide = new HashSet<Object>(fLayoutInfos.getHiddenNodes());

    	// Remove all supplied nodes (don't hide them anymore)
    	nodesToHide.removeAll(hiddenElements);

    	// Shows all Nodes and Edges
    	// (Copies hiddenObjects to objects...)
    	this.showAllHiddenElements();
    	
    	fNodesToHide.clear();
    	fNodesToHide.addAll(nodesToHide);

    	this.hideNodesAndEdges();
    }
}