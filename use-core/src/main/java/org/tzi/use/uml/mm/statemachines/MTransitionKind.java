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

/**
 * @author Lars Hamann
 *
 */
public enum MTransitionKind {
	/**
	 * kind=external implies that the transition, if triggered, will exit the source vertex.
	 */
	external,
	/**
	 * kind=internal implies that the transition, if triggered, occurs without exiting or entering the source state. 
	 * Thus, it does not cause a state change. This means that the entry or exit condition of the source state 
	 * will not be invoked. An internal transition can be taken even if the state machine is in 
	 * one or more regions nested within this state.
	 */
	internal,
	/**
	 * kind=local implies that the transition, if triggered, will not exit the composite (source) state, 
	 * but it will apply to any state within the composite state, and these will be exited and entered.
	 */
	local
}
