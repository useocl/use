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

package org.tzi.use.parser.soil;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.TestSystem;
import org.tzi.use.config.Options;
import org.tzi.use.config.Options.WarningType;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MConditionalExecutionStatement;
import org.tzi.use.uml.sys.soil.MIterationStatement;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MOperationCallStatement;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MRValueNewLinkObject;
import org.tzi.use.uml.sys.soil.MRValueNewObject;
import org.tzi.use.uml.sys.soil.MRValueOperationCall;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.soil.MVariableAssignmentStatement;
import org.tzi.use.util.NullPrintWriter;


/**
 * Test for different statements
 * @author Daniel Gent
 *
 */
public class StatementGenerationTest extends TestCase {
	
	private MStatement fStatement;
	
	private TestSystem fTestSystem;

	@Before
	@Override
	public void setUp() throws Exception {
		fStatement = null;
		fTestSystem = new TestSystem();
  	    Options.explicitVariableDeclarations = false;
	}
	
	@Test
	public void testEmptyStatement() {
		// basic empty statement
		fStatement = generateStatement("");
		assertNotNull(fStatement);
		assertTrue(fStatement.isEmptyStatement());
		
		// multiple white spaces shouldn't be a problem
		fStatement = null;
		fStatement = generateStatement("      ");
		assertNotNull(fStatement);
		assertTrue(fStatement.isEmptyStatement());
		
		// sequence of empty statements is reduced to a single empty statement
		fStatement = null;
		fStatement = generateStatement(";;;");
		assertNotNull(fStatement);
		assertTrue(fStatement.isEmptyStatement());
	}
	
	@Test
	public void testSequenceStatement() {
		// type of a certain
		fStatement = generateStatement("a := 12; a := '12'");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MSequenceStatement);
		
		// type of a is uncertain
		fStatement = null;
		fStatement = generateStatement("a := 12; if 2 = 1 then a := '12' end");
		assertNull(fStatement);
		
		// type of o1 & o2 certain
		fStatement = null;
		fStatement = generateStatement("o1 := 'overwritten'; o2 := 'also'");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MSequenceStatement);
		
		// type of o1 and o2 uncertain
		fStatement = null;
		fStatement = generateStatement("if 1 = 2 then o1 := 'overwritten' end; o2 := o1");
		assertNull(fStatement);		
	}
	
	@Test
	public void testVariableAssignment() {
		// simple variable assignment
		fStatement = generateStatement("v := 42");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueExpression);
		
		// assign variable to variable
		fStatement = null;
		fStatement = generateStatement("v := o1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		
		// assign new object to variable
		fStatement = null;
		fStatement = generateStatement("v := new C1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueNewObject);
		
		// assign new object with mandatory name to variable
		fStatement = null;
		fStatement = generateStatement("v := new C1('x')");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueNewObject);
		
		// assign new link object to variable
		fStatement = null;
		fStatement = generateStatement("v := new AC1 between (o1, o2)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueNewLinkObject);
		
		// assign new link object with mandatory name to variable
		fStatement = null;
		fStatement = generateStatement("v := new AC1('x') between (o1, o2)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueNewLinkObject);
		
		// assign result of soil operation call to variable
		fStatement = null;
		fStatement = generateStatement("v := o1.s1(0)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueOperationCall);
		
		// assign result of ocl defined operation call to variable
		fStatement = null;
		fStatement = generateStatement("v := o1.o1()");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MVariableAssignmentStatement);
		assertTrue(((MVariableAssignmentStatement)fStatement).getValue() 
				instanceof MRValueExpression);
		
		// error: operation without result value
		fStatement = null;
		fStatement = generateStatement("v := o1.s2()");
		assertNull(fStatement);
		
		// error: operation without body
		fStatement = null;
		fStatement = generateStatement("v := o1.u1()");
		assertNull(fStatement);
	}
	
	@Test
	public void testAttributeAssignment() {
		// valid attribute + valid value
		fStatement = generateStatement("o1.int := 42");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MAttributeAssignmentStatement);
		
		// valid attribute + invalid value
		fStatement = null;
		fStatement = generateStatement("o1.int := '42'");
		assertNull(fStatement);
		
		// invalid attribute
		fStatement = null;
		fStatement = generateStatement("o1.notAnAttribute := 42");
		assertNull(fStatement);
	}
	
	@Test
	public void testObjectCreation() {
		// simple object creation
		fStatement = generateStatement("new C1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MNewObjectStatement);
		
		// object creation with mandatory object name
		fStatement = null;
		fStatement = generateStatement("new C1('x')");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MNewObjectStatement);
		
		// mandatory object name not of type String
		fStatement = null;
		fStatement = generateStatement("new C1(12)");
		assertNull(fStatement);
				
		// invalid class
		fStatement = null;
		fStatement = generateStatement("new NotAClass");
		assertNull(fStatement);
		
		// can't instantiate association class this way
		fStatement = null;
		fStatement = generateStatement("new AC1");
		assertNull(fStatement);
	}
	
	@Test
	public void testLinkObjectCreation() {
		// simple link object creation
		fStatement = generateStatement("new AC1 between (o1, o2)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MNewLinkObjectStatement);
		
		// link object creation with mandatory link object name
		fStatement = null;
		fStatement = generateStatement("new AC1('x') between (o1, o2)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MNewLinkObjectStatement);
		
		// link object creation between new objects
		fStatement = null;
		fStatement = generateStatement("new AC1('x') between (new C1, new C2)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MNewLinkObjectStatement);
		
		// variation of above
		fStatement = null;
		fStatement = generateStatement("new AC1('x') between (new C1('y'), new C2('z'))");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MNewLinkObjectStatement);
		
		// wrong number of participants
		fStatement = null;
		fStatement = generateStatement("new AC1 between (o1, o2, o3)");
		assertNull(fStatement);
		
		// wrong type of participants
		fStatement = null;
		fStatement = generateStatement("new AC1 between (o2, o1)");
		assertNull(fStatement);
		
		// unknown class
		fStatement = null;
		fStatement = generateStatement("new NotAnAssociationClass between (o1, o2)");
		assertNull(fStatement);
		
		// not an association class
		fStatement = null;
		fStatement = generateStatement("new C1 between (o1, o2)");
		assertNull(fStatement);
	}
	
	@Test
	public void testObjectDestruction() {
		// simple object destruction
		fStatement = generateStatement("destroy o1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MObjectDestructionStatement);
		
		// object destruction with invalid expression
		fStatement = null;
		fStatement = generateStatement("destroy 42");
		assertNull(fStatement);
		
		// "syntactic sugar" - multiple object destructions with one statement
		fStatement = null;
		fStatement = generateStatement("destroy o1, o2, o3");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MSequenceStatement);
		assertEquals(((MSequenceStatement)fStatement).getNumStatements(), 3);
		for (MStatement s : ((MSequenceStatement)fStatement).getStatements()) {
			assertTrue(s instanceof MObjectDestructionStatement);
		}
		
		// static collections
		fStatement = null;
		fStatement = generateStatement("destroy Set{o1, o2}");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MSequenceStatement);
		assertEquals(((MSequenceStatement)fStatement).getNumStatements(), 2);
		for (MStatement s : ((MSequenceStatement)fStatement).getStatements()) {
			assertTrue(s instanceof MObjectDestructionStatement);
		}
		
		// dynamic collections - a single object destruction statement after
		// compiling, will be expanded during evaluation
		fStatement = null;
		fStatement = generateStatement("destroy C1.allInstances()");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MObjectDestructionStatement);
	}
	
	@Test
	public void testLinkInsertion() {
		// simple link insertion
		fStatement = generateStatement("insert (o1, o2) into A1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MLinkInsertionStatement);
		
		// some rValue variations
		fStatement = null;
		fStatement = generateStatement("insert (new C1, new C2) into A1");
		assertTrue(fStatement instanceof MLinkInsertionStatement);
		
		// as above
		fStatement = null;
		fStatement = generateStatement("insert (new C1('x'), new C2('y')) into A1");
		assertTrue(fStatement instanceof MLinkInsertionStatement);
		
		// link object creation as link insertion
		fStatement = null;
		fStatement = generateStatement("insert (o1, o2) into AC1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MLinkInsertionStatement);
		
		// wrong participant types
		fStatement = null;
		fStatement = generateStatement("insert (o2, o1) into A1");
		assertNull(fStatement);
		
		// wrong number of participants
		fStatement = null;
		fStatement = generateStatement("insert (o2, o1, o2) into A1");
		assertNull(fStatement);
		
		// not existing association
		fStatement = null;
		fStatement = generateStatement("insert (o1, o2) into NotAnAssoc");
		assertNull(fStatement);
	}
	
	@Test
	public void testLinkDeletion() {
		// simple link deletion
		fStatement = generateStatement("delete (o1, o2) from A1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MLinkDeletionStatement);
		
		// link object deletion
		fStatement = null;
		fStatement = generateStatement("delete (o1, o2) from AC1");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MLinkDeletionStatement);
		
		// wrong participant types
		fStatement = null;
		fStatement = generateStatement("delete (o2, o1) from A1");
		assertNull(fStatement);
		
		// wrong number of participants
		fStatement = null;
		fStatement = generateStatement("delete (o2, o1, o2) from A1");
		assertNull(fStatement);
		
		// not existing association
		fStatement = null;
		fStatement = generateStatement("delete (o1, o2) from NotAnAssoc");
		assertNull(fStatement);
	}
	
	@Test
	public void testConditionalExecution() {
		// conditional execution without else statement
		Options.setCheckWarningsUnrelatedTypes(WarningType.IGNORE);
		fStatement = generateStatement("if o1 = o2 then v := 42 end");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MConditionalExecutionStatement);
		
		// conditional execution with else statement
		fStatement = null;
		fStatement = generateStatement("if o1 = o2 then v := 42 else v := 43 end");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MConditionalExecutionStatement);
		
		// conditional execution with undefined condition
		fStatement = null;
		fStatement = generateStatement("if oclUndefined(Boolean) then v := 42 end");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MConditionalExecutionStatement);
		
		// condition with wrong type
		fStatement = null;
		fStatement = generateStatement("if o1 then v := 42 end");
		assertNull(fStatement);
		Options.setCheckWarningsUnrelatedTypes(WarningType.ERROR);
	}
	
	@Test
	public void testIteration() {
		// simple iteration
		fStatement = generateStatement("for x in Set{1,2,3} do v := x end");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MIterationStatement);
		
		// iteration range is not a collection
		fStatement = null;
		fStatement = generateStatement("for x in 5 do v := x end");
		assertNull(fStatement);
		
		// assignment to iteration variable
		fStatement = null;
		fStatement = generateStatement("for x in Set{1,2,3} do x := 42 end");
		assertNull(fStatement);
	}
	
	@Test
	public void testOperationCall() {
		// simple operation call
		fStatement = generateStatement("o1.s1(5)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MOperationCallStatement);	
		
		// another one
		fStatement = null;
		fStatement = generateStatement("o1.s2()");
		assertTrue(fStatement instanceof MOperationCallStatement);
		
		// no source object
		fStatement = null;
		fStatement = generateStatement("s1(5, 6)");
		assertNull(fStatement);
		
		// no parentheses
		fStatement = null;
		fStatement = generateStatement("o1.s2");
		assertNull(fStatement);
		
		// wrong number of parameters
		fStatement = null;
		fStatement = generateStatement("o1.s1(5, 6)");
		assertNull(fStatement);
		
		// wrong parameter type
		fStatement = null;
		fStatement = generateStatement("o1.s1('5')");
		assertNull(fStatement);
		
		// unknown operation
		fStatement = null;
		fStatement = generateStatement("o1.notAnOperation()");
		assertNull(fStatement);
		
		
		// ocl defined operation
		fStatement = null;
		fStatement = generateStatement("o1.o1()");
		assertNull(fStatement);
		
		
		// undefined operation
		fStatement = null;
		fStatement = generateStatement("call o1.u1()");
		assertNull(fStatement);
	}
	
	
	/**
	 * generates a MStatement from a String input (i.e. something you would
	 * enter in a shell)
	 * @param input the input
	 * @return the generated statement or null if the generation failed
	 */
	private MStatement generateStatement(String input) {
		
		return SoilCompiler.compileStatement(
				fTestSystem.getModel(), 
				fTestSystem.getState(), 
				fTestSystem.getVarEnv(), 
				input, 
				"<input>", 
				NullPrintWriter.getInstance(), 
				false);
	}
}
