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

import org.tzi.use.uml.mm.MNamedElement;
import org.tzi.use.uml.mm.commonbehavior.communications.MTrigger;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * A transition is a directed relationship between a source vertex and a target vertex. 
 * It may be part of a compound transition, which takes the state machine 
 * from one state configuration to another, representing the complete response of
 * the state machine to an occurrence of an event of a particular type.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * @author Lars Hamann
 *
 */
public class MTransition implements MNamedElement {

	/**
	 * See definition of TransitionKind. Default value is external.
	 */
	protected MTransitionKind kind = MTransitionKind.external;
	
	/**
	 * Designates the region that owns this transition.
	 */
	protected MRegion container;
	
	/**
	 * Designates the originating vertex (state or pseudostate) of the transition.
	 */
	protected MVertex source;
	
	/**
	 * Designates the target vertex that is reached when the transition is taken.
	 */
	protected MVertex target;
	
	/**
	 * Specifies the triggers that may fire the transition.
	 */
	protected MTrigger trigger;
	
	/**
	 * Simplified to Expression.
	 * 
	 * A guard is a constraint that provides a fine-grained control over the firing of the transition. 
	 * The guard is evaluated when an event occurrence is dispatched by the state machine. 
	 * If the guard is true at that time, the transition may be enabled; otherwise, it is disabled. 
	 * Guards should be pure expressions without side effects. Guard expressions with side effects are ill-formed.
	 */
	protected Expression guard;
	
	
	/**
	 * @param sourceV
	 * @param targetV
	 */
	public MTransition(MRegion container, MVertex sourceV, MVertex targetV) {
		this.source = sourceV;
		this.target = targetV;
		this.container = container;
	}

	@Override
	public String name() {
		StringBuilder b = new StringBuilder();
		
		if (this.getGuard() != null) {
			b.append('[');
			b.append(this.getGuard().toString());
			b.append(']');
		}
		
		if (this.getTrigger() != null) {
			if (b.length() > 0) b.append(" ");
			b.append(this.getTrigger().toString());
		}
		
		b.append('/');
		
		//TODO: Action!
		
		return b.toString();
	}


	/**
	 * @return the kind
	 */
	public MTransitionKind getKind() {
		return kind;
	}


	/**
	 * @return the container
	 */
	public MRegion getContainer() {
		return container;
	}


	/**
	 * @return the source
	 */
	public MVertex getSource() {
		return source;
	}


	/**
	 * @return the target
	 */
	public MVertex getTarget() {
		return target;
	}


	/**
	 * @return the trigger
	 */
	public MTrigger getTrigger() {
		return trigger;
	}

	/**
	 * 
	 */
	public void setTrigger(MTrigger trigger) {
		this.trigger = trigger;
	}
	
	/**
	 * @return the guard
	 */
	public Expression getGuard() {
		return guard;
	}


	/**
	 * @param guard the guard to set
	 */
	public void setGuard(Expression guard) {
		this.guard = guard;
	}

	@Override
	public String toString() {
		return name();
	}


	/**
	 * Returns <code>true</code> if this transition hast the same source and target state.
	 * @return
	 */
	public boolean isReflexive() {
		return source.equals(target);
	}
}
