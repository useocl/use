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

// $Id$

package org.tzi.use.uml.mm.statemachines;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.sys.MSystemException;

/**
 * A region is an orthogonal part of either a composite state or a state machine. 
 * It contains states and transitions.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * 
 * @author Lars Hamann
 *
 */
public class MRegion {
	
	/**
	 * The StateMachine that owns the Region. 
	 * If a Region is owned by a StateMachine, then it cannot also be owned by a State.
	 */
	protected MStateMachine stateMachine;
	
	/**
	 * The set of transitions owned by the region.
	 */
	protected Set<MTransition> transition = new HashSet<MTransition>();

	/**
	 * All outgoing transitions from the defined states.
	 */
	protected Map<MVertex, Set<MTransition>> outgoingTrasitions = new HashMap<MVertex, Set<MTransition>>();
	
	/**
	 * The vertices that are owned by this region.
	 * Saved by name.
	 */
	protected Map<String, MVertex> subvertex = new HashMap<String,MVertex>();

	protected MState initialState = null;
	
	public MRegion(MStateMachine owner) {
		this.stateMachine = owner;
	}
	
	/**
	 * @return the stateMachine
	 */
	public MStateMachine getStateMachine() {
		return stateMachine;
	}

	/**
	 * @return the transition
	 */
	public Set<MTransition> getTransitions() {
		return transition;
	}

	public void addTransition(MTransition t) throws MSystemException {
		
		if (t.getSource() instanceof MPseudoState
				&& ((MPseudoState) t.getSource()).getKind() == MPseudoStateKind.initial) {
			if (initialState != null)
				throw new MSystemException("An initial state of a state machine can only have one outgoing transition!");
			
			if (t.getGuard() != null)
				throw new MSystemException("An transition from the initial state cannot have a guard!");

			if (t.getTarget().equals(t.getSource())) {
				throw new MSystemException("A reflexive transition on an inital state is not allowed!");
			}
			
			initialState = (MState)t.getTarget();
			this.transition.add(t);
			return;
		} 
		
		if (t.getTarget() instanceof MPseudoState
				&& ((MPseudoState) t.getTarget()).getKind() == MPseudoStateKind.initial) {
			throw new MSystemException("An initial state of a state machine cannot have incoming transitions!");
		}
		
		if (t.getSource() instanceof MFinalState) {
			throw new MSystemException("An final state of a state machine cannot have outgoing transitions!");
		}
		
		this.transition.add(t);
		
		if (!this.outgoingTrasitions.containsKey(t.getSource())) {
			this.outgoingTrasitions.put(t.getSource(), new HashSet<MTransition>());
		}
				
		this.outgoingTrasitions.get(t.getSource()).add(t);
	}
	
	/**
	 * @return the subvertex
	 */
	public Collection<MVertex> getSubvertices() {
		return subvertex.values();
	}
	
	public void addSubvertex(MVertex v) throws MSystemException {
		if (this.subvertex.containsKey(v.name()))
			throw new MSystemException("The state names inside one region must be unique!");
		
		if (v instanceof MPseudoState &&
			((MPseudoState)v).getKind() == MPseudoStateKind.initial ) {
			for (MVertex ev : this.subvertex.values()) {
				if (ev instanceof MPseudoState &&
					((MPseudoState)ev).getKind() == MPseudoStateKind.initial ) {
						throw new MSystemException("A region can have only one initial pseudo state!");
				}
			}
		}
		
		this.subvertex.put(v.name(), v);
		v.setContainer(this);
	}
	
	/**
	 * Returns the vertex with the given name or <code>null</code> if
	 * no vertex with this name is defined.
	 * @param name
	 * @return
	 */
	public MVertex getSubvertex(String name) {
		return this.subvertex.get(name);
	}

	/**
	 * @return
	 */
	public MState getInitialState() {
		return initialState;
	}

	/**
	 * @param value
	 * @return
	 */
	public Set<MTransition> getOutgoingTransitions(MVertex from) {
		return this.outgoingTrasitions.get(from);
	}
}
