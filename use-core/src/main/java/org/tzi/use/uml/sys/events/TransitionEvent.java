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

package org.tzi.use.uml.sys.events;

import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.events.tags.EventContext;
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;

/**
 * Event for notifying about
 * state transitions.
 * @author Lars Hamann
 *
 */
public class TransitionEvent extends Event implements SystemStateChangedEvent {

	private final MObject source;
	
	private final MTransition transition;
	
	private final MStateMachine stateMachine;
	
	/**
	 * Constructs a new event
	 * @param context
	 */
	public TransitionEvent(EventContext context, MObject source, MStateMachine stateMachine, MTransition transition) {
		super(context);
		this.source = source;
		this.transition = transition;
		this.stateMachine = stateMachine;
	}

	/**
	 * The object that changed the state
	 * @return the source
	 */
	public MObject getSource() {
		return source;
	}

	/**
	 * The transition that was taken.
	 * May be <code>null</code>.
	 * @return the transition
	 */
	public MTransition getTransition() {
		return transition;
	}
	
	public MStateMachine getStateMachine() {
		return this.stateMachine;
	}
}
