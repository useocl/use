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
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;

/**
 * Hides selected nodes and edges from a given diagram.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
  */
@SuppressWarnings("serial")
public final class ActionHideClassDiagram extends ActionHide {
    public ActionHideClassDiagram( String text, Set<?> nodesToHide,
                                   Selection<Selectable> nodeSelection, DirectedGraph<NodeBase, EdgeBase> graph,
                                   LayoutInfos layoutInfos ) {
        super( text );
        setNodes( nodesToHide );
        fLayoutInfos = layoutInfos;
        fNodeSelection = nodeSelection;
        fGraph = graph;
    }

    protected ClassDiagram getDiagram() {
    	return (ClassDiagram)fLayoutInfos.getDiagram();
    }
    
    public void showAllHiddenElements() {
        // add all hidden nodes
    	getDiagram().showAll();
        getDiagram().invalidateContent();
    }

    /**
     * Hides all nodes with there connecting edges.
     */
    public void hideNodesAndEdges() {
        getDiagram().hideElementsInDiagram( fNodesToHide );
        
        fNodeSelection.clear();
        
        getDiagram().invalidateContent();
    }
    
    public void actionPerformed(ActionEvent e) {
        hideNodesAndEdges();
    }

    public void showHiddenElements(Set<?> hiddenElements) {
    	
    	//FIXME: Only show hidden elements. Not show all and hide the rest
    	
    	// New set with currently hidden nodes
    	Set<Object> objectsToHide = new HashSet<Object>(fLayoutInfos.getHiddenNodes());
    	
    	// Remove elements that should be shown
    	objectsToHide.removeAll(hiddenElements);
    	
    	// Resets the view
    	this.showAllHiddenElements();

    	// Set objects to hide
    	fNodesToHide.clear();
    	fNodesToHide.addAll(objectsToHide);

    	this.hideNodesAndEdges();
    }
}