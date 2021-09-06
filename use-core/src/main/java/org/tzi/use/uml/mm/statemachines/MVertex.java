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

package org.tzi.use.uml.mm.statemachines;

import java.util.Collections;
import java.util.Set;

import org.tzi.use.uml.mm.MNamedElement;

/**
 * A vertex is an abstraction of a node in a state machine graph. 
 * In general, it can be the source or destination of any number
 * of transitions.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * 
 * @author Lars Hamann
 *
 */
public abstract class MVertex implements MNamedElement {

	/**
	 * The name of the vertex
	 */
	protected String name;
	
	/**
	 * The region that contains this vertex.
	 */
	protected MRegion container;
	
	/**
	 * Specifies the transitions entering this vertex.
	 */
	//FIXME: Derived!
	protected Set<MTransition> incoming;
	
	public MVertex(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNamedElement#name()
	 */
	@Override
	public String name() {
		return name;
	}

	/**
	 * @return the container
	 */
	public MRegion getContainer() {
		return container;
	}

	/**
	 * @param container the container to set
	 */
	public void setContainer(MRegion container) {
		this.container = container;
	}

	protected Set<MTransition> outgoing;
	
	/**
	 * Specifies the transitions departing from this vertex.
	 */
	public Set<MTransition> getOutgoing() {
		
		if (this.outgoing == null) {
			Set<MTransition> out = this.getContainer().getOutgoingTransitions(this);
		
			if (out == null)
				this.outgoing = Collections.emptySet();
			else
				this.outgoing = out;
		}
		
		return this.outgoing;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name();
	}
	
}
