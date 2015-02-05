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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

/**
 * A special {@link CommunicationDiagramEdge} to indicate a significant difference between edge and link 
 * @author Thomas Schaefer
 *
 */
public class LinkCommunicationDiagramEdge extends CommunicationDiagramEdge{

	/**
	 * @param source
	 * @param target
	 * @param diagram
	 * @param completeEdgeMoveMovesUserWayPoints
	 */
	public LinkCommunicationDiagramEdge(PlaceableNode source,
			PlaceableNode target, DiagramView diagram,
			boolean completeEdgeMoveMovesUserWayPoints) {
		super(source, target, diagram, completeEdgeMoveMovesUserWayPoints);
	}
	
	/**
	 * creates a new {@link LinkCommunicationDiagramEdge}
	 * @param source
	 * @param target
	 * @param diagram
	 * @param completeEdgeMoveMovesUserWayPoints
	 * @return
	 */
	
	static LinkCommunicationDiagramEdge create(PlaceableNode source, PlaceableNode target, DiagramView diagram, boolean completeEdgeMoveMovesUserWayPoints) {
		LinkCommunicationDiagramEdge edge = new LinkCommunicationDiagramEdge(source, target, diagram, completeEdgeMoveMovesUserWayPoints);
		edge.initialize();
		return edge;
	}

}
