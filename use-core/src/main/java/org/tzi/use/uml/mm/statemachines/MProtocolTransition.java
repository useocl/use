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

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.commonbehavior.communications.MCallEvent;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * A protocol transition (transition as specialized in the ProtocolStateMachines package) 
 * specifies a legal transition for an operation. Transitions of protocol state machines have the following information: 
 * a pre-condition (guard), on trigger, and a post-condition. 
 * Every protocol transition is associated to zero or one operation (referred BehavioralFeature) that belongs
 * to the context classifier of the protocol state machine.
 * <p>
 * The protocol transition specifies that the associated (referred) operation can be 
 * called for an instance in the origin state under the initial condition (guard), 
 * and that at the end of the transition, the destination state will be reached under the final condition (post).
 * </p>
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * 
 * @author Lars Hamann
 *
 */
public class MProtocolTransition extends MTransition {
	
	/**
	 * @param source
	 * @param target
	 * @param preConditionExp
	 */
	public MProtocolTransition(MRegion container, MVertex source, MVertex target) {
		super(container, source, target);
	}

	/**
	 * Specifies the post-condition of the transition, which is the condition that should be 
	 * obtained once the transition is triggered. 
	 * This post-condition is part of the post-condition of the operation connected to the transition.
	 */
	protected Expression postCondition;
	
	/**
	 * Specifies the precondition of the transition. It specifies the condition that should be verified before triggering the
	 * transition. This guard condition added to the source state will be evaluated as part 
	 * of the precondition of the operation referred by the transition if any.
	 * Same as {@link #getGuard()}.
	 */
	public Expression getPreCondition() {
		return this.getGuard();
	}
	
	public void setPreCondition(Expression condition) {
		this.setGuard(condition);
	}
	
	public Expression getPostCondition() {
		return this.postCondition;
	}
	
	/**
	 * @param prePostConditionExp
	 */
	public void setPostCondition(Expression condition) {
		this.postCondition = condition;
	}
	
	/**
	 * This association refers to the associated operation. 
	 * It is derived from the operation of the call trigger when applicable.
	 */
	public MOperation getReferred() {
		if (this.getTrigger() == null)
			return null;
		
		if (this.getTrigger().getEvent() instanceof MCallEvent) {
			return ((MCallEvent)this.getTrigger().getEvent()).getOperation();
		}
		
		return null;
	}

	@Override
	public String name() {
		String n = super.name();
		StringBuilder s = new StringBuilder(n);
		
		if (this.postCondition != null) {
			s.append(" [");
			this.postCondition.toString(s);
			s.append(']');
		}
		
		return s.toString();
	}

	/**
	 * @return
	 */
	public boolean hasGuard() {
		return this.guard != null;
	}

	/**
	 * @return
	 */
	public boolean hasPostCondition() {
		return this.postCondition != null;
	}
}
