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

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;

/**
 * A <i>protocol state machine</i> is always defined in the context of a classifier. 
 * It specifies which operations of the classifier can be called in which state and under which condition, 
 * thus specifying the allowed call sequences on the classifier's operations. 
 * A protocol state machine presents the possible and permitted transitions on the instances of its context
 * classifier, together with the operations that carry the transitions. 
 * In this manner, an instance lifecycle can be created for a classifier, 
 * by specifying the order in which the operations can be activated and the states through which an instance
 * progresses during its existence.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * @author Lars Hamann
 *
 */
public class MProtocolStateMachine extends MStateMachine {

	/**
	 * @param name
	 * @param srcPos
	 */
	public MProtocolStateMachine(String name, SrcPos srcPos, MClass context) {
		super(name, srcPos, context);
	}
	
	/**
	 * @param mObjectState
	 * @return
	 */
	public MProtocolStateMachineInstance createInstance(MObject object) {
		return new MProtocolStateMachineInstance(this, object);
	}

	/**
	 * @param operation
	 * @return
	 */
	public boolean handlesOperation(MOperation operation) {
		//TODO: Caching?
		for (MRegion r : this.getRegions()) {
			for (MTransition t : r.getTransitions()) {
				// PSM only contains protocol transitions
				MProtocolTransition pt = (MProtocolTransition)t;
				// some transitions don't have an operation, e. g., create
				if (pt.getReferred() == null) continue;
				
				if (operation.equals(pt.getReferred()) || 
					operation.isValidOverrideOf(pt.getReferred()))
					return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.context.name() + "::" + this.name();
	}

	/**
	 * Searches for a state with the given name in all regions
	 * of the state machine.
	 * @param stateName
	 * @return
	 */
	public MState getState(String stateName) {
		for (MRegion r : this.getRegions()) {
			MVertex v =  r.getSubvertex(stateName);
			if (v != null && v instanceof MState) {
				return (MState)v;
			}
		}
		
		return null;
	}
}
