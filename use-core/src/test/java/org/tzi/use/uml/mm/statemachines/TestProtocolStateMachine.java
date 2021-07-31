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

import junit.framework.TestCase;

import org.junit.Test;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.sys.MSystemException;

/**
 * @author Lars Hamann
 *
 */
public class TestProtocolStateMachine extends TestCase {

	@Test
	public void testUniqueStateNames() {
		MModel model = TestModelUtil.getInstance().createModelWithClasses();
		
		MProtocolStateMachine psm = new MProtocolStateMachine("psm_test", null, model.getClass("Person"));
		
		try {
			psm.getDefaultRegion().addSubvertex(new MState("state1"));
			psm.getDefaultRegion().addSubvertex(new MState("state2"));
		} catch (MSystemException e) {
			fail(e.getMessage());
		}
		
		try {
			psm.getDefaultRegion().addSubvertex(new MState("state1"));
			fail("Duplicate state name!");
		} catch (MSystemException e) {
			
		}
	}
	
	@Test
	public void testInitialState() {
		MModel model = TestModelUtil.getInstance().createModelWithClasses();
		
		MProtocolStateMachine psm = new MProtocolStateMachine("psm_test", null, model.getClass("Person"));
		MRegion defaultRegion = psm.getDefaultRegion();
		
		MPseudoState initial = new MPseudoState("init", MPseudoStateKind.initial);
		MState state1 = new MState("state1");
		MState state2 = new MState("state2");
		
		// Base states (all valid)
		try {
			defaultRegion.addSubvertex(initial);
			defaultRegion.addSubvertex(state1);
			defaultRegion.addSubvertex(state2);
		} catch (MSystemException e) {
			fail(e.getMessage());
		}
		
		// Only one initial pseudo state
		try {
			defaultRegion.addSubvertex(new MPseudoState("init2", MPseudoStateKind.initial));
			fail("Duplicate initial state!");
		} catch (MSystemException e) {
			
		}
		
		int numTransitions = defaultRegion.getTransitions().size();
		// transition from initial state to target cannot have a guard
		try {
			MTransition t = new MTransition(defaultRegion, initial, state1);
			t.setGuard(new ExpressionWithValue(BooleanValue.TRUE));
			defaultRegion.addTransition(t);
			fail("Accepted guard for initial state transition");
		} catch (MSystemException e) {
			
		}
		assertEquals("Added invalid transition!", numTransitions, defaultRegion
				.getTransitions().size());
		
		// valid transition
		try {
			MTransition t = new MTransition(defaultRegion, initial, state1);
			defaultRegion.addTransition(t);
		} catch (MSystemException e) {
			fail(e.getMessage());
		}
		assertEquals("Valid transition was not added!", ++numTransitions,
				defaultRegion.getTransitions().size());
		
		// only one transition is allowed
		try {
			MTransition t = new MTransition(defaultRegion, initial, state2);
			defaultRegion.addTransition(t);
			fail("More than one transition from an initial state");
		} catch (MSystemException e) {
			
		}
		assertEquals("Added invalid transition!", numTransitions, defaultRegion
				.getTransitions().size());
		
		// No incoming transitions
		try {
			MTransition t = new MTransition(defaultRegion, state2, initial);
			defaultRegion.addTransition(t);
			fail("Incoming transition to initial state!");
		} catch (MSystemException e) {
			
		}
		assertEquals("Added invalid transition!", numTransitions, defaultRegion
				.getTransitions().size());
	}
	
	@Test
	public void testFinalState() {
		MModel model = TestModelUtil.getInstance().createModelWithClasses();
		
		MProtocolStateMachine psm = new MProtocolStateMachine("psm_test", null, model.getClass("Person"));
		MRegion defaultRegion = psm.getDefaultRegion();
		
		MPseudoState initial = new MPseudoState("init", MPseudoStateKind.initial);
		MState state1 = new MState("state1");
		MState state2 = new MState("state2");
		MState state3 = new MState("state3");
		
		MFinalState final1 = new MFinalState("final1");
		MFinalState final2 = new MFinalState("final2");
		MFinalState final2_dup = new MFinalState("final2");
		
		// Base states (all valid)
		try {
			defaultRegion.addSubvertex(initial);
			defaultRegion.addSubvertex(state1);
			defaultRegion.addSubvertex(state2);
			defaultRegion.addSubvertex(state3);
			defaultRegion.addSubvertex(final1);
			defaultRegion.addSubvertex(final2);
			
			MTransition t = new MTransition(defaultRegion, state1, final1);
			defaultRegion.addTransition(t);
			
			t = new MTransition(defaultRegion, state2, final2);
			defaultRegion.addTransition(t);
			
			t = new MTransition(defaultRegion, state3, final1);
			defaultRegion.addTransition(t);
		} catch (MSystemException e) {
			fail(e.getMessage());
		}
		
		assertEquals("Lost subvertex!", defaultRegion.getSubvertices().size(), 6);
		
		// duplicate name for final state 
		try {
			defaultRegion.addSubvertex(final2_dup);
			fail("Accepted final state with duplicate name");
		} catch (MSystemException e) {
			
		}
		assertEquals("Added invalid subvertex!", defaultRegion.getSubvertices().size(), 6);
				
		int numTransitions = defaultRegion.getTransitions().size();
		// No outgoing transitions from final state
		try {
			MTransition t = new MTransition(defaultRegion, final1, state1);
			defaultRegion.addTransition(t);
			fail("Outgoing transition from final state!");
		} catch (MSystemException e) {
			
		}
		assertEquals("Added invalid transition!", numTransitions, defaultRegion.getTransitions().size());
	}
	
	@Test
	public void testTransitionCreation() {
		
	}
}
