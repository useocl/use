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

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElement;
import org.tzi.use.uml.mm.UseFileLocatable;

/**
 * State machines can be used to express the behavior of part of a system. Behavior is modeled as a traversal of a graph of
 * state nodes interconnected by one or more joined transition arcs that are triggered by the dispatching of series of (event)
 * occurrences. During this traversal, the state machine executes a series of activities associated with various elements of the
 * state machine. 
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * 
 * @author Lars Hamann
 */
public class MStateMachine implements MNamedElement, UseFileLocatable {
	
	/**
	 * The position of the state machine in the model,
	 * if read from a file.
	 */
	protected SrcPos srcPos;
	
	/**
	 * The name of the state machine.
	 */
	protected String name;
	
	/**
	 * Simplified: in UML 2.4 context is defined in Behavior
	 * which is a superclass of StateMachine.
	 * The classifier that is the context for the execution of the behavior.
	 */
	protected MClass context;
	
	/**
	 * The regions owned directly by the state machine.
	 */
	protected List<MRegion> region = new ArrayList<MRegion>();
	
	/**
	 * Constructs a new state machine with a default region.
	 * @param name The name of the state machine
	 * @param srcPos The source position of the state machine inside of a USE file.
	 */
	public MStateMachine(String name, SrcPos srcPos, MClass context) {
		this.srcPos = srcPos;
		this.name = name;
		this.context = context;
		
		// Default region, because we do not support multiple regions, yet.
		region.add(new MRegion(this));
	}

	@Override
	public String name() {
		return name;
	}

	/**
	 * @return the region
	 */
	public List<MRegion> getRegions() {
		return region;
	}
	
	public MRegion getDefaultRegion() {
		return this.region.iterator().next();
	}
	
	public MClass getContext() {
		return this.context;
	}

	@Override
	public int getPositionInModel() {
		return (srcPos == null ? 0 : srcPos.line());
	}
}
