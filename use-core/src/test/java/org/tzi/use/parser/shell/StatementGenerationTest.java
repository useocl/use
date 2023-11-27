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

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.tzi.use.output.DefaultUserOutput;
import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * test the generation of statements
 * @author Daniel Gent
 */
public class StatementGenerationTest extends TestCase {
	
	private MStatement fStatement;
	
	private MModel fModel;
	
	private MSystemState fState;

	@Before
	@Override
	public void setUp() throws Exception {
		fStatement = null;
		generateModelAndState();	
	}

	@Test
	public void testLegacyCommandList() {
		
		fStatement = generateStatement("destroy o1 destroy o2");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MSequenceStatement);
		assertEquals(((MSequenceStatement)fStatement).getNumStatements(), 2);
		for (MStatement statement : ((MSequenceStatement)fStatement).getStatements()) {
			assertTrue(statement instanceof MObjectDestructionStatement);
		}
	}

	@Test
	public void testOpEnter() {
		// correct way to call an undefined operation
		fStatement = generateStatement("openter o1 u1()");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MEnterOperationStatement);
		
		// another one
		fStatement = generateStatement("openter o1 u2(42)");
		assertNotNull(fStatement);
		assertTrue(fStatement instanceof MEnterOperationStatement);
		
		// wrong number of arguments
		fStatement = generateStatement("openter o1 u2(43, 44)");
		assertNull(fStatement);
		
		// wrong argument type
		fStatement = generateStatement("openter o1 u2('42')");
		assertNull(fStatement);
		
		// cannot enter soil defined operations
		fStatement = generateStatement("openter o1 s1(42)");
		assertNull(fStatement);
		
		// cannot enter ocl defined operations
		fStatement = generateStatement("openter o1 o1('42')");
		assertNull(fStatement);
	}

	private void generateModelAndState() throws Exception {
		List<VarDecl> emptyQualifiers = Collections.emptyList();
		
		ModelFactory factory = new ModelFactory();
		fModel = factory.createModel("testModel");
		
		MClass c1 = factory.createClass("C1", false);
		fModel.addClass(c1);
		c1.addAttribute(factory.createAttribute("int", TypeFactory.mkInteger()));
		
		// soil defined operation with 1 parameter & result value
		MOperation op1 = new MOperation(
				"s1", 
				new VarDeclList(
						new VarDecl("p1", TypeFactory.mkInteger())), 
				TypeFactory.mkInteger());
		
		op1.setStatement(MEmptyStatement.getInstance());
		c1.addOperation(op1);
		
		// soil defined operation without parameters & without result value
		MOperation op2 = new MOperation(
				"s2", 
				new VarDeclList(true), 
				null);
		
		op2.setStatement(MEmptyStatement.getInstance());
		c1.addOperation(op2);
		
		// ocl defined operation without parameters & with result value
		MOperation op3 = new MOperation(
				"o1", 
				new VarDeclList(true), 
				TypeFactory.mkString());
		
		op3.setExpression(new ExpConstString(""));
		c1.addOperation(op3);
		
		// undefined operation with result value
		MOperation op4 = new MOperation(
				"u1", 
				new VarDeclList(true), 
				TypeFactory.mkString());
		
		c1.addOperation(op4);
		
		// undefined operation without result value
		MOperation op5 = new MOperation(
				"u2", 
				new VarDeclList(
						new VarDecl("p1", TypeFactory.mkInteger())), 
				null);
		
		c1.addOperation(op5);
		
		MClass c2 = factory.createClass("C2", false);
		fModel.addClass(c2);
		c2.addAttribute(factory.createAttribute("str", TypeFactory.mkString()));	
		
		MAssociation a1 = factory.createAssociation("A1");
		a1.addAssociationEnd(
			factory.createAssociationEnd(
					c1, 
					"E1", 
					new MMultiplicity(0, 1), 
					MAggregationKind.NONE, 
					false, emptyQualifiers));
		
		a1.addAssociationEnd( 
			factory.createAssociationEnd(
					c2, 
					"E2", 
					new MMultiplicity(0, 1), 
					MAggregationKind.NONE, 
					false, emptyQualifiers));
		
		fModel.addAssociation(a1);
		
		MAssociationClass ac1 = factory.createAssociationClass("AC1", false);
		ac1.addAssociationEnd(
				factory.createAssociationEnd(
						c1, 
						"role1", 
						new MMultiplicity(0, 1), 
						MAggregationKind.NONE, 
						false, emptyQualifiers));
		
		ac1.addAssociationEnd( 
				factory.createAssociationEnd(
						c2, 
						"role2", 
						new MMultiplicity(0, 1), 
						MAggregationKind.NONE, 
						false, emptyQualifiers));
		
		fModel.addClass(ac1);
		fModel.addAssociation(ac1);
		
		MSystem system = new MSystem(fModel);
		fState = system.state();

		MObject fO1 = fState.createObject(c1, "O1");
		system.getVariableEnvironment().assign("o1", fO1.value());

		MObject fO2 = fState.createObject(c2, "O2");
		system.getVariableEnvironment().assign("o2", fO2.value());

		MObject fO3 = fState.createObject(c1, "O3");
		system.getVariableEnvironment().assign("o3", fO3.value());

		MObject fO4 = fState.createObject(c2, "O4");
		system.getVariableEnvironment().assign("o4", fO4.value());
		
		fState.createLink(DefaultUserOutput.createSystemOutOutput(), a1, Arrays.asList(fO3, fO4), null);
	}

	private MStatement generateStatement(String input) {
		
		return ShellCommandCompiler.compileShellCommand(
				fModel, 
				fState, 
				fState.system().getVariableEnvironment(), 
				input, 
				"<input>",
				VoidUserOutput.getInstance(),
				false);
	}
}
