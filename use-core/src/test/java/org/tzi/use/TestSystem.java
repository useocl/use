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

package org.tzi.use;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MMultiplicity;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MEmptyStatement;
import org.tzi.use.uml.sys.soil.MObjectOperationCallStatement;
import org.tzi.use.uml.sys.soil.MVariableAssignmentStatement;
import org.tzi.use.util.soil.VariableEnvironment;

/**
 * Helper to setup a test system.
 * @author Daniel Gent
 *
 */
public class TestSystem {

	private MSystem fSystem;

	public TestSystem() throws MSystemException, MInvalidModelException, ExpInvalidException {
		init();
	}
	
	
	/**
	 * Resets the state of the test system.
	 * @throws MSystemException 
	 */
	public void reset() throws MSystemException {
		fSystem.reset();
		initObjectsAndLinks();
	}
	
	public MSystem getSystem() {
		return fSystem;
	}
	
	public MModel getModel() {
		return fSystem.model();
	}

	public MSystemState getState() {
		return fSystem.state();
	}

	public VariableEnvironment getVarEnv() {
		return fSystem.getVariableEnvironment();
	}
	
	
	/**
	 * generates model and state for test cases.<br>
	 * model contains 2 classes (C1, C2), 1 association (A1 between (C1, C2))
	 * and 1 association class (AC1 between (C1, C2)).<br>
	 * state contains 4 objects (o1 : C1, o2 : C2, o3 : C1, o4 : C2) 
	 * and 1 link (A1 between (o3, o4)).<br>
	 * <p>
	 * Model:
	 * <p>
	 * <code>
	 * model testModel
	 * <p>
	 * class C1<br>
	 * attributes<br>
	 *   int : Integer
	 * <p>
	 * operations<br>
	 *   s1(p1 : Integer) : Integer --with soil body<br>
	 *   s2()  -- with soil body<br>
	 *   o1() : String = ''<br>
	 *   u1() : String<br>
	 *   u2()<br>
	 * <p>
	 * end
	 * <p>
	 * class C2<br>
	 * attributes<br>
	 *   str : String<br>
	 * end
	 * <p>
	 * association A1 between<br>
	 *   C1 [0..1] role E1<br>
	 *   C2 [0..1] role E2<br>
	 * end
	 * <p>
	 * associationClass AC1 between<br>
	 *   C1 [0..1] role role1<br>
	 *   C2 [0..1] role role2<br>
	 * end   
	 * </code>
	 * <p>
	 * State<br>
	 * <p>
	 * Objects<br>
	 * <p>
	 * <code>
	 * o1 : C1 @o1<br>
	 * o2 : C2 @o2<br>
	 * o3 : C1 @o3<br>
	 * o4 : C2 @o4<br>
	 * </code>
	 * <p>
	 * Links<br>
	 * <code>
	 * a1 between (o3, o4)
	 * </code>
	 * @throws ExpInvalidException 
	 * @throws Exception if something can't be generated
	 */
	private void init() throws MSystemException, MInvalidModelException, ExpInvalidException {
		ModelFactory factory = new ModelFactory();
		MModel model = factory.createModel("testModel");
		
		MClass c1 = factory.createClass("C1", false);
		model.addClass(c1);
		c1.addAttribute(factory.createAttribute("int", TypeFactory.mkInteger()));
		
		// soil defined operation with 1 parameter & result value
		MOperation op1 = new MOperation(
				"s1", 
				new VarDeclList(
						new VarDecl("p1", TypeFactory.mkInteger())), 
				TypeFactory.mkInteger());
		
		op1.setStatement(
				new MVariableAssignmentStatement(
						"result", IntegerValue.valueOf(42)));
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
		
		MOperation op6 = new MOperation(
				"failEnter",
				new VarDeclList(true),
				null);
		
		op6.setStatement(MEmptyStatement.getInstance());
		
		op6.preConditions().add(
				factory.createPrePostCondition(
						"alwaysFails", 
						op6, 
						true,
						new ExpConstBoolean(false)));
		
		c1.addOperation(op6);
		
		MOperation op7 = new MOperation(
				"failExit",
				new VarDeclList(true),
				null);
		
		op7.setStatement(MEmptyStatement.getInstance());
		
		op7.postConditions().add(
				factory.createPrePostCondition(
						"alwaysFails", 
						op7, 
						false, 
						new ExpConstBoolean(false)));
		
		c1.addOperation(op7);
		
		MOperation op8 = new MOperation(
				"proxyFail",
				new VarDeclList(true),
				null);
		
		op8.setStatement(new MObjectOperationCallStatement(
				new ExpVariable("self", c1), 
				op7, new Expression[0]));
		
	
		c1.addOperation(op8);
		
		//////////////
		// class C2 //
		//////////////
		
		MClass c2 = factory.createClass("C2", false);
		model.addClass(c2);
		c2.addAttribute(factory.createAttribute("str", TypeFactory.mkString()));	
		
		List<VarDecl> emptyQualifiers = Collections.emptyList();
		
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
		
		model.addAssociation(a1);
		
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
		
		model.addClass(ac1);
		model.addAssociation(ac1);
		
		fSystem = new MSystem(model);
		
		initObjectsAndLinks();
	}

	private void initObjectsAndLinks() throws MSystemException {
		
		MSystemState state = fSystem.state();
		VariableEnvironment varEnv = fSystem.getVariableEnvironment();
	
		MClass C1 = getModel().getClass("C1");
		MClass C2 = getModel().getClass("C2");
		
		varEnv.assign("o1", state.createObject(C1, "O1").value());
		varEnv.assign("O1", varEnv.lookUp("o1"));
		varEnv.assign("o2", state.createObject(C2, "O2").value());
		varEnv.assign("O2", varEnv.lookUp("o2"));
		varEnv.assign("o3", state.createObject(C1, "O3").value());
		varEnv.assign("O3", varEnv.lookUp("o3"));
		varEnv.assign("o4", state.createObject(C2, "O4").value());
		varEnv.assign("O4", varEnv.lookUp("o4"));
		varEnv.assign("o5", state.createObject(C1, "O5").value());
		varEnv.assign("O5", varEnv.lookUp("o5"));
		varEnv.assign("o6", state.createObject(C2, "O6").value());
		varEnv.assign("O6", varEnv.lookUp("o6"));
		
		varEnv.assign("v", IntegerValue.valueOf(42));
		
		state.createLink(
				getModel().getAssociation("A1"),
				Arrays.asList(state.objectByName("O3"),
						state.objectByName("O4")), null);
		
		MAssociationClass AC1 = getModel().getAssociationClass("AC1");
		
		varEnv.assign(
				"lo1", state.createLinkObject(
						AC1, 
						"LO1", 
						Arrays.asList(
							getState().objectByName("O5"),
							getState().objectByName("O6")),
						null
						).value());
		
		varEnv.assign("LO1", varEnv.lookUp("lo1"));
	}
}
