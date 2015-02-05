/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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
package org.tzi.use.uml.sys;

import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

import junit.framework.TestCase;

import org.junit.Test;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.sys.soil.MIterationStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MVariableAssignmentStatement;

/**
 * @author green
 */
public class MSystemStateTest extends TestCase {

    public void testGetCrossProductOfInstanceSets() throws UseApiException {
    	
        UseModelApi api = new UseModelApi("test");
        MClass c1 = api.createClass("C1", false);
        MClass c2 = api.createClass("C2", false);
        MClass c3 = api.createClass("C3", false);
        
        UseSystemApi sysApi = UseSystemApi.create(api.getModel(), false);

    	sysApi.createObjectEx(c1,"o1_1");
    	sysApi.createObjectEx(c2,"o2_1");
    	sysApi.createObjectEx(c2,"o2_2");
    	sysApi.createObjectEx(c3,"o3_1");
    	sysApi.createObjectEx(c3,"o3_2");
    	sysApi.createObjectEx(c3,"o3_3");
        
    	MSystemState state = sysApi.getSystem().state();
        assertEquals(2, state.getCrossProductOfInstanceSets(
                Arrays.asList(new MClass[]{c1,c2})).size());
        assertEquals(3, state.getCrossProductOfInstanceSets(
                Arrays.asList(new MClass[]{c1,c3})).size());
        assertEquals(6, state.getCrossProductOfInstanceSets(
                Arrays.asList(new MClass[]{c2,c3})).size());
    }

    /**
     * "Stresstest" for the name generator
     * @throws UseApiException 
     * @throws MSystemException 
     */
    @Test
    public void testMemoryUsage() throws UseApiException, MSystemException {
    	final int numObjects    = 20000;
    	final int numStatements = 20000;
    	
    	UseModelApi api = new UseModelApi("test");
    	MClass test = api.createClass("Test", false);
    	MClass test1 = api.createClass("Test1", false);
    	
    	UseSystemApi sys = UseSystemApi.create(api.getModel(), true);

    	try {
	    	createObjects(sys, test, numObjects);
	    	createObjects(sys, test1, numObjects);
    	} catch (OutOfMemoryError e) {
    		sys = null;
    		fail("Memory consumption to high!");
    	}
    	
    	try {
    		// Test a single loop statement generating objects (one undo)
    		
    		// !new Object
    		MNewObjectStatement stmt = new MNewObjectStatement(test, (String)null);
    		// Sequence{1..numObjects}
    		Expression range = new ExpressionWithValue(new SequenceValue(TypeFactory.mkInteger(), new int[]{1,numObjects}));
    		// for (Sequence{1..numObjects}) do new Object; end
    		MIterationStatement iter = new MIterationStatement("i", range, stmt);
    		sys.getSystem().execute(iter);
    	} catch (OutOfMemoryError e) {
    		sys = null;
    		fail("Memory consumption to high!");
    	}
    	
    	
    	try {
    		// Execute a statement which does not generate a new name
    		MVariableAssignmentStatement stmt = new MVariableAssignmentStatement("myVar", UndefinedValue.instance);
	    	for (int i = 0; i < numStatements; ++i) {
	    		sys.getSystem().execute(stmt);
	    	}
    	} catch (OutOfMemoryError e) {
    		sys = null;
    		fail("Memory consumption to high!");
    	}
    	
    }
    
    @Test
    public void testNamesGeneration() throws UseApiException, MSystemException, OperationNotSupportedException {
    	
    	UseModelApi api = new UseModelApi("test");
    	MClass test = api.createClass("Test", false);
    	MClass test1 = api.createClass("Test1", false);
    	
    	UseSystemApi sys = UseSystemApi.create(api.getModel(), true);
    	MObject obj;
    	
		obj = sys.createObjectEx(test, null);
		assertEquals("Test1", obj.name());
		
		obj = sys.createObjectEx(test, null);
		assertEquals("Test2", obj.name());
		
		obj = sys.createObjectEx(test, null);
		assertEquals("Test3", obj.name());
		
		sys.undo();
		
		obj = sys.createObjectEx(test, null);
		assertEquals("Test3", obj.name());
		
		sys.undo();
		sys.undo();
		
		obj = sys.createObjectEx(test, null);
		assertEquals("Test2", obj.name());
		
		// Execute a statement which does not generate a new name
		MVariableAssignmentStatement stmt = new MVariableAssignmentStatement("myVar", UndefinedValue.instance);
		sys.getSystem().execute(stmt);
		
		obj = sys.createObjectEx(test, null);
		assertEquals("Test3", obj.name());
		
		sys.undo();
		sys.undo();
		
		obj = sys.createObjectEx(test, null);
		assertEquals("Test3", obj.name());
		
	
		obj = createObjects(sys, test1, 4);
		assertEquals("Test14", obj.name());
		
		stmt = new MVariableAssignmentStatement("myVar", UndefinedValue.instance);
		sys.getSystem().execute(stmt);
		
		obj = createObjects(sys, test1, 4);
		assertEquals("Test18", obj.name());
		
		undoN(sys, 9);
		redoN(sys, 9);
				
		obj = sys.createObjectEx(test1, null);
		assertEquals("Test19", obj.name());
		
		// test reset
		sys.getSystem().reset();
		
		obj = createObjects(sys, test1, 4);
		assertEquals("Test14", obj.name());
		
		// test name conflicts with class test and test1
		sys.getSystem().reset();
		
		obj = createObjects(sys, test, 11);
		assertEquals("Test11", obj.name());
		
		obj = createObjects(sys, test1, 1);
		assertEquals("Test12", obj.name());
		
		obj = createObjects(sys, test, 1);
		assertEquals("Test13", obj.name());
	}
    
    /**
     * Creates <code>num</code> unnamed objects and returns the
     * last created one.
     * @param sys
     * @param cls
     * @param num
     * @throws UseApiException 
     */
    private MObject createObjects(UseSystemApi sys, MClass cls, int num) throws UseApiException {
    	MObject obj = null;
    	for (int i = 0; i < num; ++i) {
    		obj = sys.createObjectEx(cls, null);
    	}
    	
    	return obj;
    }
    
    private void undoN(UseSystemApi sys, int undos) throws UseApiException, OperationNotSupportedException {
    	for (int i = 0; i < undos; ++i) {
    		sys.undo();
    	}
    }
    
    private void redoN(UseSystemApi sys, int redos) throws UseApiException, OperationNotSupportedException {
    	for (int i = 0; i < redos; ++i) {
    		sys.redo();
    	}
    }
}
