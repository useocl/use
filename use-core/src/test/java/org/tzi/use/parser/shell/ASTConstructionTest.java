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

package org.tzi.use.parser.shell;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.parser.soil.ast.ASTAttributeAssignmentStatement;
import org.tzi.use.parser.soil.ast.ASTEmptyStatement;
import org.tzi.use.parser.soil.ast.ASTEnterOperationStatement;
import org.tzi.use.parser.soil.ast.ASTExitOperationStatement;
import org.tzi.use.parser.soil.ast.ASTNewObjectStatement;
import org.tzi.use.parser.soil.ast.ASTRValueNewLinkObject;
import org.tzi.use.parser.soil.ast.ASTRValueNewObject;
import org.tzi.use.parser.soil.ast.ASTSequenceStatement;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.parser.soil.ast.ASTVariableAssignmentStatement;
import org.tzi.use.util.NullPrintWriter;


/**
 * Tests several AST constructions
 * @author Daniel
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
	public void testLegacyCreate() {
		fResult = constructAST("create v : C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTNewObjectStatement);
		
		setUp();
		fResult = constructAST("create v1, v2 : C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTSequenceStatement);
		for (ASTStatement statement : ((ASTSequenceStatement)fResult).getStatements()) {
			assertTrue(statement instanceof ASTNewObjectStatement);
		}
	}

	@Test
	public void testLegacyCreateAssign() {
		fResult = constructAST("assign v := create C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue()
				instanceof ASTRValueNewObject);
		
		setUp();
		fResult = constructAST("assign v1, v2 := create C");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTSequenceStatement);
		assertEquals(((ASTSequenceStatement)fResult).getNumStatements(), 2);
		for (ASTStatement statement : ((ASTSequenceStatement)fResult).getStatements()) {
			assertTrue(statement instanceof ASTVariableAssignmentStatement);
			assertTrue(((ASTVariableAssignmentStatement)statement).getRValue()
					instanceof ASTRValueNewObject);
		}
	}
	
	@Test
	public void testLegacyCreateInsert() {
		fResult = constructAST("create v : AC between (o1, o2)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
		assertTrue(((ASTVariableAssignmentStatement)fResult).getRValue()
				instanceof ASTRValueNewLinkObject);
	}
	
	@Test
	public void testLegacySet() {
		fResult = constructAST("set o1.att1 := 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTAttributeAssignmentStatement);
	}
	
	@Test
	public void testOpEnter() {
		// enter operation with arguments
		fResult = constructAST("openter o op(arg1, arg2)");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTEnterOperationStatement);
		
		// enter operation without arguments
		setUp();
		fResult = constructAST("openter o op()");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTEnterOperationStatement);
		
		// parentheses must always be used
		setUp();
		fResult = constructAST("openter o op");
		assertNull(fResult);
		
		// there must not be a dot between object and operation name
		setUp();
		fResult = constructAST("openter o.op()");
		assertNull(fResult);	
	}
	
	@Test
	public void testOpExit() {
		// exit operation without result value
		fResult = constructAST("opexit");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTExitOperationStatement);
		
		// exit operation with result value
		setUp();
		fResult = constructAST("opexit 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTExitOperationStatement);
	}

	@Test
	public void testLegacyLet() {
		fResult = constructAST("let v = 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
				
		setUp();
		fResult = constructAST("let v : Integer = 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTVariableAssignmentStatement);
	}

	@Test
	public void testExecute() {
		
		fResult = constructAST("execute 42");
		assertNotNull(fResult);
		assertTrue(fResult instanceof ASTEmptyStatement);
	}

	@Test
	public void testLegacyCommandList() {
		
		// command lists are consecutive commands separated by white spaces
		// or semicolon
		
		List<String> cmds = new ArrayList<String>();
		//  1. create
		cmds.add("create v : C1");
		//  2. create assign
		cmds.add("assign v := create C");
		//  3. create insert
		cmds.add("create v : AC between (o1, o2)");
		//  4. destroy
		cmds.add("destroy o1");
		//  5. insert
		cmds.add("insert (o1, o2) into A");
		//  6. delete
		cmds.add("delete (o1, o2) from A");
		//  7. set
		cmds.add("set o1.att1 := 42");
		/*//  8. openter
		cmds.add("openter o op()");
		//  9. opexit
		cmds.add("opexit 12");*/
		// 10. let
		cmds.add("let x = 2");
		// 11. execute
		cmds.add("execute 12");
		
		List<String> separators = new ArrayList<String>(2);
		separators.add(" ");
		separators.add(";");
				
		for (String cmd1 : cmds) {
			for (String cmd2 : cmds) {
				for (String separator : separators) {
					setUp();
					fResult = constructAST(cmd1 + separator + cmd2);
					assertNotNull(fResult);
				}
			}
		}
	}

	private ASTStatement constructAST(String input) {
		return ShellCommandCompiler.constructAST(
				new ByteArrayInputStream(input.getBytes()), 
				"<input>", 
				NullPrintWriter.getInstance(),
				false);
	}
}
