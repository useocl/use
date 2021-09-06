/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams.statemachine;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.uml.mm.statemachines.MTransition;

/**
 * @author Lars Hamann
 *
 */
public class ProtocolTransitionEdge extends TransitionEdge {

	/**
	 * @param source
	 * @param target
	 * @param edgeName
	 * @param diagram
	 */
	ProtocolTransitionEdge(MTransition t, VertexNode source, VertexNode target,
			String edgeName, DiagramOptions diagramOptions, Direction reflexivePosition) {
		super(t, source, target, edgeName, diagramOptions, reflexivePosition);
	}
		
	public static ProtocolTransitionEdge create(MTransition t, VertexNode source, VertexNode target,
			String edgeName, DiagramOptions diagramOptions, Direction reflexivePosition) {
		ProtocolTransitionEdge edge = new ProtocolTransitionEdge(t, source, target, edgeName, diagramOptions, reflexivePosition);
		return edge;
	}
}
