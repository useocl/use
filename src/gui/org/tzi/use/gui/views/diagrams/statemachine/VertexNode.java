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

import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.uml.mm.statemachines.MVertex;

/**
 * @author Lars Hamann
 *
 */
public abstract class VertexNode extends PlaceableNode {

	protected MVertex vertex;

	protected boolean isActive = false;
	
	public VertexNode(MVertex vertex) {
		this.vertex = vertex;
	}
	
	public MVertex getVertex() {
		return this.vertex;
	}

	@Override
	public String name() {
		return vertex.name();
	}

	/**
	 * @param active
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}	
}
