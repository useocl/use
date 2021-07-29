/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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
import java.util.Set;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CommunicationDiagram;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;

/**
 * TODO
 * @author Quang Dung Nguyen
 *
 */
@SuppressWarnings("serial")
public final class ActionHideCommunicationDiagram extends ActionHide<PlaceableNode> {
	
	public ActionHideCommunicationDiagram(String text, Set<PlaceableNode> nodesToHide,
			Selection<? extends PlaceableNode> nodeSelection,
			DirectedGraph<PlaceableNode, EdgeBase> graph,
			DiagramViewWithObjectNode diagramViewWithObjectNode) {
		super(text, diagramViewWithObjectNode, nodeSelection);
		setNodes(nodesToHide);
		fGraph = graph;
	}
	
	/**
     * Easier access to the object diagram
     * @return
     */
    protected CommunicationDiagram getDiagram() {
    	return (CommunicationDiagram) diagram;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		hideNodesAndEdges();
	}

	@Override
	public void showAllHiddenElements() {
		getDiagram().showAll();
        getDiagram().repaint();
	}

	@Override
	public void hideNodesAndEdges() {
		getDiagram().hideElementsInCommunicationDiagram( fNodesToHide );
        fNodeSelection.clear();
        getDiagram().repaint();
	}
}