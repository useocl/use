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

package org.tzi.use.util.soil;


import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.TestSystem;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;


/**
 * Test cases for various methods of {@code VariableEnvironment}s
 * 
 * @author Daniel Gent
 * @see VariableEnvironment
 */
public class VariableEnvironmentTest extends TestCase {
	private VariableEnvironment ve;
	private String n1;
	private String n2;
	private String n3;
	private Value v1;
	private Value v2;
	private Value v3;
	private Value vUnassigned;
		
	
	/**
	 * constructs fixture
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		TestSystem testSystem = new TestSystem();
		ve = new VariableEnvironment(testSystem.getState());
		n1 = "n1";
		n2 = "n2";
		n3 = "n3";
		v1 = IntegerValue.valueOf(1);
		v2 = IntegerValue.valueOf(2);
		v3 = IntegerValue.valueOf(3);
		vUnassigned = null;
	}
	
	@Test
	public void testIdentity() {
		assertEquals(1,1);
	}
	
	/**
	 * tests clear, isEmpty
	 * <p>
	 * - empty means one empty frame<br>
	 * - calling clear results in the variable environment being empty<br>
	 */
	@Test
	public void testClearIsEmpty() {
		// [ ]
		assertTrue(ve.isEmpty());
		// [n1 -> v1]
		ve.assign(n1, v1);
		assertFalse(ve.isEmpty());
		// [ ]
		ve.clear();
		assertTrue(ve.isEmpty());
		// [ ][ ]
		ve.pushFrame(false);
		assertFalse(ve.isEmpty());
		// [ ]
		ve.popFrame();
		assertTrue(ve.isEmpty());
	}
		
	
	/**
	 * tests pushFrame, popFrame
	 * <p>
	 * - after pushing a new frame, new assignments happen there<br>
	 * - mappings in a popped frame are gone<br>
	 */
	@Test
	public void testPushPopFrame() {
		// [ ][ ]
		ve.pushFrame(false);
		// [ ][n1 -> v1]
		ve.assign(n1, v1);
		assertEquals(ve.lookUp(n1), v1);
		// [ ]
		ve.popFrame();
		assertNull(ve.lookUp(n1));
		// [n1 -> v1]
		ve.assign(n1, v1);
		assertEquals(ve.lookUp(n1), v1);
	}
	
	
	/**
	 * tests assign and lookup methods
	 * <p>
	 * - assignments should only occur on the most recent level<br>
	 * - previous assignments on that level should be updated in the most
	 *   recent frame<br>
	 */
	@Test
	public void testAssignLookUp() {
		// [n1 -> v1]
		ve.assign(n1, v1);
		assertEquals(ve.lookUp(n1), v1);
		// [n1 -> v2]
		ve.assign(n1, v2);
		assertEquals(ve.lookUp(n1), v2);
		// [n1 -> v2, n2 -> v2]
		ve.assign(n2, v2);
		assertEquals(ve.lookUp(n1), v2);
		assertEquals(ve.lookUp(n2), v2);
		// [n1 -> v2, n2 -> v2][n1 -> v3, n2 -> v3, n3 -> v1]
		ve.pushFrame(false);
		ve.assign(n1, v3);
		ve.assign(n2, v3);
		ve.assign(n3, v1);
		assertEquals(ve.lookUp(n1), v3);
		assertEquals(ve.lookUp(n2), v3);
		assertEquals(ve.lookUp(n3), v1);
		// [n1 -> v2, n2 -> v2]
		ve.popFrame();
		assertEquals(ve.lookUp(n1), v2);
		assertEquals(ve.lookUp(n2), v2);
		assertEquals(ve.lookUp(n3), vUnassigned);
	}
	
	
	/**
	 * tests undefineReferencesTo, getTopLevelReferencesTo
	 * <p>
	 * - all variables - disregarding the containing the level or frame -
	 *   reference to the undefined value after undefineReferencesTo<br>
	 * - getTopLevelReferencesTo returns the names of all variables in the
	 *   first frame of the first level which refer to the specified object<br>
	 */
	@Test
	public void testObjectReferences() {
		MObject object = null;
		ModelFactory mf = new ModelFactory();
		MModel model = mf.createModel("m");
		MClass cls = mf.createClass("c", false);
		try {
			model.addClass(cls);
		} catch (MInvalidModelException e) {
			fail(e.getMessage());
		}
		MSystem system = new MSystem(model);
		try {
			object = system.state().createObject(cls, "o");
		} catch (MSystemException e) {
			fail(e.getMessage());
		}
		
		Value vO = object.value();
		Value vU = UndefinedValue.instance;
		
		// [n1 -> vO1]
		ve.assign(n1, vO);
		assertTrue(ve.getTopLevelReferencesTo(object).contains(n1));
		// [n1 -> vU]
		ve.undefineReferencesTo(object);
		assertEquals(ve.lookUp(n1), vU);
		// [n1 -> vO1, n2 -> vO1]
		ve.assign(n1, vO);
		ve.assign(n2, vO);
		assertTrue(ve.getTopLevelReferencesTo(object).contains(n1));
		assertTrue(ve.getTopLevelReferencesTo(object).contains(n2));
		// [n1 -> vU, n2 -> vU]
		ve.undefineReferencesTo(object);
		assertEquals(ve.lookUp(n1), vU);
		assertEquals(ve.lookUp(n2), vU);
		// [n1 -> vO1][n2 -> vO1]
		// [n1 -> vO1, n2 -> vO1]
		ve.assign(n1, vO);
		ve.assign(n2, vO);
		ve.pushFrame(false);
		ve.assign(n1, vO);
		ve.pushFrame(false);
		ve.assign(n2, vO);
		// [n1 -> vU][n2 -> vU]
		// [n1 -> vU, n2 -> vU]
		ve.undefineReferencesTo(object);
		assertEquals(ve.lookUp(n2), vU);
		// [n1 -> vU]
		// [n1 -> vU, n2 -> vU]
		ve.popFrame();
		assertEquals(ve.lookUp(n1), vU);
		// [n1 -> vU, n2 -> vU]
		ve.popFrame();
		assertEquals(ve.lookUp(n1), vU);
		assertEquals(ve.lookUp(n2), vU);		
	}


	/**
	 * tests remove
	 * <p>
	 * - removes a mapping in the most recent frame<br>
	 */
	@Test
	public void testRemove() {
		// [n1 -> v1]
		ve.assign(n1, v1);
		assertEquals(ve.lookUp(n1), v1);
		// [ ]
		ve.remove(n1);
		assertEquals(ve.lookUp(n1), vUnassigned);
		// [n1 -> v1][n1 -> v2]
		ve.assign(n1, v1);
		ve.pushFrame(false);
		ve.assign(n1, v2);
		assertEquals(ve.lookUp(n1), v2);
		// [n -> v1][ ]
		ve.remove(n1);
		assertEquals(ve.lookUp(n1), vUnassigned);
		// [n -> v1]
		ve.popFrame();
		assertEquals(ve.lookUp(n1), v1);
	}


	/**
	 * test constructSymbolTable
	 * <p>
	 * - name -> value mappings are transformed to corresponding 
	 *   name -> TypeOf(value) mappings<br>
	 * - the symbol table in constructed only by mappings in the most recent 
	 *   frame<br>
	 */
	@Test
	public void testConstructSymbolTable() {
		// VE
		// [n1 -> v1, n2 -> v2, n3 -> v3]
		ve.assign(n1, v1);
		ve.assign(n2, v2);
		ve.assign(n3, v3);
		// ST
		// [n1 -> Type(v1), n2 -> Type(v2), n3 -> Type(v3)]
		SymbolTable st = ve.constructSymbolTable();
		assertEquals(st.getType(n1), v1.type());
		assertEquals(st.getType(n2), v2.type());
		assertEquals(st.getType(n3), v3.type());
		// VE
		// [n1 -> v1, n2 -> v2, n3 -> v3][n1 -> v2, n2 -> v3, n3 -> v1]
		ve.pushFrame(false);
		ve.assign(n1, v2);
		ve.assign(n2, v3);
		ve.assign(n3, v1);
		// ST
		// [n1 -> Type(v2), n2 -> Type(v3), n3 -> Type(v1)]
		st = ve.constructSymbolTable();
		assertEquals(st.getType(n1), v2.type());
		assertEquals(st.getType(n2), v3.type());
		assertEquals(st.getType(n3), v1.type());
	}

	
	/**
	 * tests constructVarBindings
	 * <p>
	 * - all mappings in the current frame get copied<br>
	 */
	@Test
	public void testConstructVarBindings() {
		VarBindings vb;
		// VE
		// [n1 -> v1, n2 -> v2, n3 -> v3]
		ve.assign(n1, v1);
		ve.assign(n2, v2);
		ve.assign(n3, v3);
		// VB
		// [n1 -> v1, n2 -> v2, n3 -> v3]
		vb = ve.constructVarBindings();
		assertEquals(vb.getValue(n1), v1);
		assertEquals(vb.getValue(n2), v2);
		assertEquals(vb.getValue(n3), v3);
		// VE
		// [n1 -> v1, n2 -> v2, n3 -> v3][n1 -> v2, n2 -> v3, n3 -> v1]
		ve.pushFrame(false);
		ve.assign(n1, v2);
		ve.assign(n2, v3);
		ve.assign(n3, v1);
		// VB
		// [n1 -> v2, n2 -> v3, n3 -> v1]
		vb = ve.constructVarBindings();
		assertEquals(vb.getValue(n1), v2);
		assertEquals(vb.getValue(n2), v3);
		assertEquals(vb.getValue(n3), v1);
	}
	
	// TODO lookup, constructVBetc with visible objects
}
