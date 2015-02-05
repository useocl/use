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
package org.tzi.use.uml.mm.statemachines;

import org.tzi.use.uml.ocl.expr.Expression;

/**
 * A state models a situation during which some (usually implicit) invariant condition holds.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * 
 * @author Lars Hamann
 *
 */
public class MState extends MVertex {
	
	/**
	 * @param name The name of the state
	 */
	public MState(String name) {
		super(name);
	}

	/**
	 * Simplified to Expression.
	 * Specifies conditions that are always true when this state is the current state. 
	 * In protocol state machines, state invariants are additional conditions to the preconditions 
	 * of the outgoing transitions, and to the postcondition of the incoming transitions.
	 */
	protected Expression stateInvariant;

	/**
	 * @return the stateInvariant
	 */
	public Expression getStateInvariant() {
		return stateInvariant;
	}

	/**
	 * @param stateInvariant the stateInvariant to set
	 */
	public void setStateInvariant(Expression stateInvariant) {
		this.stateInvariant = stateInvariant;
	}
}
