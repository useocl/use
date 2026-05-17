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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.parser.soil.ast.ASTAttributeAssignmentStatement;
import org.tzi.use.parser.soil.ast.ASTConditionalExecutionStatement;
import org.tzi.use.parser.soil.ast.ASTIterationStatement;
import org.tzi.use.parser.soil.ast.ASTLinkDeletionStatement;
import org.tzi.use.parser.soil.ast.ASTLinkInsertionStatement;
import org.tzi.use.parser.soil.ast.ASTNewLinkObjectStatement;
import org.tzi.use.parser.soil.ast.ASTNewObjectStatement;
import org.tzi.use.parser.soil.ast.ASTObjectDestructionStatement;
import org.tzi.use.parser.soil.ast.ASTOperationCallStatement;
import org.tzi.use.parser.soil.ast.ASTRValueExpressionOrOpCall;
import org.tzi.use.parser.soil.ast.ASTRValueNewLinkObject;
import org.tzi.use.parser.soil.ast.ASTRValueNewObject;
import org.tzi.use.parser.soil.ast.ASTSequenceStatement;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.parser.soil.ast.ASTVariableAssignmentStatement;
import org.tzi.use.util.NullPrintWriter;


/**
 * Soil AST tests
 * @author Daniel Gent
 *
 */
public class ASTConstructionTest extends TestCase {

	private ASTStatement fResult;

	@Override
	@Before
	public void setUp() {
		fResult = null;
	}

	@Test
	public void testEmptyStatement() {
		// nothing
		fResult = constructAST("");
		assertNotNull(fResult);
		assertTrue(fResult.isEmptyStatement());
		
		// multiple nothings
		setUp();
		fResult = constructAST(";;;");
		assertNotNull(fResult);
		assertTrue(fResult.isEmptyStatement());
	}
	
	@Test
	public void testSequenceStatement() {
		// sequence of two statements
		fResult = constructAST("v := 42; w := 43");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTSequenceStatement);
		
		// sequence of three statements
		setUp();
		fResult = constructAST("v := 42; w := 43; x := 44");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTSequenceStatement);
		
		// sequence with leading, trailing and intermediate empty statements
		setUp();
		fResult = constructAST(" ; v := 42; ; w := 43; x := 44; ");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTSequenceStatement);
		// empty statements should have been stripped
		assertTrue(((ASTSequenceStatement)fResult).getStatements().size() == 3);
		
		// test combinations of statements in a sequence
		
		List<String> stats = new ArrayList<String>();
		// empty
		stats.add("");
		// variable assignment (expression)
		stats.add("v := 42");
		// variable assignment (new object)
		stats.add("v := new C");
		// variable assignment (new object with mandatory name)
		stats.add("v := new C('o1')");
		// variable assignment (new link object)
		stats.add("v := new AC between (o1, o2)");
		// variable assignment (new link object with mandatory name)
		stats.add("v := new AC('lo1') between (o1, o2)");
		// attribute assignment
		stats.add("o.att := 42");
		// link object creation
		stats.add("new AC between (o1, o2)");
		// link object creation with mandatory name
		stats.add("new AC('lo1') between (o1, o2)");
		// object creation
		stats.add("new C");
		// object creation with mandatory name
		stats.add("new C('o1')");
		// object destruction
		stats.add("destroy o");
		// multiple object destruction
		stats.add("destroy o1, o2");
		// link insertion
		stats.add("insert (o1, o2) into A");
		// link deletion
		stats.add("delete (o1, o2) from A");
		// conditional execution
		stats.add("if expr then v := 42 else v := 24 end");
		// iteration
		stats.add("for iter in setExpr do v := v + iter end");
		// operation call
		stats.add("o1.opName(argExpr1, argExpr2)");
				
		for (String stat1 : stats) {
			for (String stat2 : stats) {
				setUp();
				String combinedStat = stat1 + "; " + stat2;
				fResult = constructAST(combinedStat);
				assertNotNull(fResult);
				// while we're at it: sequence of sequences
				combinedStat = combinedStat + "; " + combinedStat;
				fResult = constructAST(combinedStat);
				assertNotNull(fResult);
			}
		}
	}

	@Test
	public void testVariableAssignment() {
		// assignment of an expression
		fResult = constructAST("v := 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue() 
				instanceof ASTRValueExpressionOrOpCall);
		
		// assignment of an operation call
		setUp();
		fResult = constructAST("v := obj.op()");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue() 
				instanceof ASTRValueExpressionOrOpCall);
		
		// assignment of a new object
		setUp();
		fResult = constructAST("v := new C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue() 
				instanceof ASTRValueNewObject);
		
		// assignment of a new object with mandatory name
		setUp();
		fResult = constructAST("v := new C('x')");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue() 
				instanceof ASTRValueNewObject);
		
		// assignment of a new link object
		setUp();
		fResult = constructAST("v := new AC between (a, b)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue() 
				instanceof ASTRValueNewLinkObject);
		
		// assignment of a new link object with mandatory name
		setUp();
		fResult = constructAST("v := new AC('x') between (a, b)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue() 
				instanceof ASTRValueNewLinkObject);
	}
		
	@Test
	public void testAttributeAssignment() {
		// assign expression to attribute
		fResult = constructAST("o.a := 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
		
		// assign return value to attribute
		setUp();
		fResult = constructAST("o.a := x.f()");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
		
		// assign new object to attribute
		setUp();
		fResult = constructAST("o.a := new C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
		
		// assign new object with mandatory object name to attribute
		setUp();
		fResult = constructAST("o.a := new C('x')");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
		
		// assign new link object to attribute
		setUp();
		fResult = constructAST("o.a := new AC between (x, y)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
		
		// assign new link object with mandatory object name to attribute
		setUp();
		fResult = constructAST("o.a := new AC('x') between (x, y)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
	}
	
	@Test
	public void testObjectCreation() {
		// object creation without variable assignment
		fResult = constructAST("new C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewObjectStatement);
		
		// object creation with mandatory object name
		setUp();
		fResult = constructAST("new C('a')");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewObjectStatement);
	}

	@Test
	public void testLinkObjectCreation() {
		// simple link object creation
		fResult = constructAST("new AC between (o1, o2)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewLinkObjectStatement);
		
		// link object creation with mandatory link object name
		setUp();
		fResult = constructAST("new AC('n') between (o1, o2)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewLinkObjectStatement);
		
		// link object creation with rValues
		setUp();
		fResult = constructAST("new AC between (new C, new D)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewLinkObjectStatement);
		
		// variant of above
		setUp();
		fResult = constructAST("new AC between (new C ('x'), new D ('y'))");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewLinkObjectStatement);
	}

	@Test
	public void testObjectDestruction() {
		// single destruction statement
		fResult = constructAST("destroy o");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTObjectDestructionStatement);
		
		// syntactic sugar, generates sequence of destruction statements
		setUp();
		fResult = constructAST("destroy o1, o2");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTSequenceStatement);
		for (ASTStatement statement : ((ASTSequenceStatement)fResult).getStatements()) {
			assertTrue(statement instanceof ASTObjectDestructionStatement);
		}
				
		// static collection = single destruction statement
		setUp();
		fResult = constructAST("destroy Set{o1, o2}");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTObjectDestructionStatement);
		
		// runtime collection = single destruction statement
		setUp();
		fResult = constructAST("destroy C.allInstances()");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTObjectDestructionStatement);
	}

	@Test
	public void testLinkInsertion() {
		// simple link insertion
		fResult = constructAST("insert (o1, o2) into ass");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTLinkInsertionStatement);
		
		// link insertion with new objects
		setUp();
		fResult = constructAST("insert (new C, newC) into ass");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTLinkInsertionStatement);
		
		// link insertion with new objects and mandatory names
		setUp();
		fResult = constructAST("insert (new C('x'), new C('y')) into ass");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTLinkInsertionStatement);
		
		// link insertion with new link objects
		setUp();
		fResult = constructAST("insert (new C between (a, b), new C between (c, d)) into ass");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTLinkInsertionStatement);
		
		// link insertion with new link objects with mandatory names
		setUp();
		fResult = constructAST("insert (new C('x') between (a, b), new C('y') between (c, d)) into ass");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTLinkInsertionStatement);
	}
		
	@Test
	public void testLinkDeletion() {
		// simple link deletion
		fResult = constructAST("delete (o1, o2) from a");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTLinkDeletionStatement);
	}

	@Test
	public void testConditionalExecution() {
		// conditional execution without else statement
		fResult = constructAST("if c then v := 42 end");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTConditionalExecutionStatement);
		
		// conditional execution with else statement
		setUp();
		fResult = constructAST("if c then v := 42 else v := 43 end");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTConditionalExecutionStatement);
	}

	@Test
	public void testIteration() {
		// simple iteration
		fResult = constructAST("for iv in S do v := 42 end");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTIterationStatement);
	}
	
	
	@Test
	public void testOperationCall() {
		// simple operation call
		fResult = constructAST("obj.op(p1, p2)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTOperationCallStatement);
	}	
	
	
	/**
	 * Helper method
	 * @param input
	 * @return
	 */
	private ASTStatement constructAST(String input) {
		return SoilCompiler.constructAST(
				new ByteArrayInputStream(input.getBytes()), 
				"<input>", 
				NullPrintWriter.getInstance(),
				false);
	}
}
