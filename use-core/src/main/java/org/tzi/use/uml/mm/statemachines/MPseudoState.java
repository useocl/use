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


/**
 * A pseudostate is an abstraction that encompasses different 
 * types of transient vertices in the state machine graph.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 *  
 * @author Lars Hamann
 */
public class MPseudoState extends MVertex {
	
	
	/**
	 * @param name The name of the pseudo state
	 * @param kind
	 */
	public MPseudoState(String name, MPseudoStateKind kind) {
		super(name);
		this.kind = kind;
	}

	/**
	 * Determines the precise type of the Pseudostate. Default value is initial.
	 */
	protected MPseudoStateKind kind = MPseudoStateKind.initial;
	
	public MPseudoStateKind getKind() {
		return kind;
	}
}
