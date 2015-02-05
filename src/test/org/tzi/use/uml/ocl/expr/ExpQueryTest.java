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

package org.tzi.use.uml.ocl.expr;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MMultiplicity;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Test ExpQuery and subclasses.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */

public class ExpQueryTest extends TestCase {
    static List<Value> emptyQualifierValues = Collections.emptyList();
    
	private MSystemState fState;
    private Expression fSet123;
    private Expression fEGreater1;
    private Expression fE1NotEqualsE2;
    private Evaluator e;

    protected void setUp() throws Exception {
        fState = new MSystem(new ModelFactory().createModel("Test")).state();
        // create range
        Value[] args1 =
            new Value[] {
                IntegerValue.valueOf(1),
                IntegerValue.valueOf(2),
                IntegerValue.valueOf(3)};
        fSet123 = new ExpressionWithValue(new SetValue(TypeFactory.mkInteger(), args1));

        // create query expression
        Expression[] args2 =
            new Expression[] {
                new ExpVariable("e", TypeFactory.mkInteger()),
                new ExpConstInteger(1)};
        fEGreater1 = ExpStdOp.create(">", args2);

        Expression[] args3 =
            new Expression[] {
                new ExpVariable("e1", TypeFactory.mkInteger()),
                new ExpVariable("e2", TypeFactory.mkInteger())};
        fE1NotEqualsE2 = ExpStdOp.create("<>", args3);
        e = new Evaluator();
    }

    public void testSelect1() throws ExpInvalidException {
        Expression exp = new ExpSelect((VarDecl)null, fSet123, new ExpConstBoolean(true));
        Value[] values =
            new Value[] { IntegerValue.valueOf(1), IntegerValue.valueOf(2), IntegerValue.valueOf(3)};
        assertEquals(
                     exp.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(exp, fState));
    }

    public void testSelect2() throws ExpInvalidException {
        Expression exp =
            new ExpSelect(
                          new VarDecl("e", TypeFactory.mkInteger()),
                          fSet123,
                          fEGreater1);
        Value[] values = new Value[] { IntegerValue.valueOf(2), IntegerValue.valueOf(3)};
        assertEquals(
                     exp.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(exp, fState));
    }

    public void testReject() throws ExpInvalidException {
        Expression exp =
            new ExpReject(
                          new VarDecl("e", TypeFactory.mkInteger()),
                          fSet123,
                          fEGreater1);
        Value[] values = new Value[] { IntegerValue.valueOf(1)};
        assertEquals(
                     exp.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(exp, fState));
    }

    public void testExists1() throws ExpInvalidException {
        Expression exp =
            new ExpExists(
                          new VarDecl("e", TypeFactory.mkInteger()),
                          fSet123,
                          fEGreater1);
        assertEquals(exp.toString(), BooleanValue.TRUE, e.eval(exp, fState));
    }

    public void testExists2() throws ExpInvalidException {
        VarDeclList elemVars = new VarDeclList(true);
        elemVars.add(new VarDecl("e1", TypeFactory.mkInteger()));
        elemVars.add(new VarDecl("e2", TypeFactory.mkInteger()));
        Expression exp = new ExpExists(elemVars, fSet123, fE1NotEqualsE2);
        assertEquals(exp.toString(), BooleanValue.TRUE, e.eval(exp, fState));
    }

    public void testForAll1() throws ExpInvalidException {
        Expression exp =
            new ExpForAll(
                          new VarDecl("e", TypeFactory.mkInteger()),
                          fSet123,
                          fEGreater1);
        assertEquals(exp.toString(), BooleanValue.FALSE, e.eval(exp, fState));
    }

    public void testForAll2() throws ExpInvalidException {
        VarDeclList elemVars = new VarDeclList(true);
        elemVars.add(new VarDecl("e1", TypeFactory.mkInteger()));
        elemVars.add(new VarDecl("e2", TypeFactory.mkInteger()));
        Expression exp = new ExpForAll(elemVars, fSet123, fE1NotEqualsE2);
        assertEquals(exp.toString(), BooleanValue.FALSE, e.eval(exp, fState));
    }

    public void testCollect() throws ExpInvalidException {
        Expression[] args =
            new Expression[] {
                new ExpVariable("e", TypeFactory.mkInteger()),
                new ExpConstInteger(2)};
        Expression mult2Exp = ExpStdOp.create("*", args);
        Expression exp =
            new ExpCollectNested(new VarDecl("e", TypeFactory.mkInteger()), fSet123, mult2Exp);
        Value[] values =
            new Value[] { IntegerValue.valueOf(2), IntegerValue.valueOf(4), IntegerValue.valueOf(6)};
        assertEquals(
                     exp.toString(),
                     new BagValue(TypeFactory.mkInteger(), values),
                     e.eval(exp, fState));
    }

    public void testIterate1() throws ExpInvalidException {
        // Set { 1..100 }->iterate(e; acc : Integer= 0 | acc + e);
        Value[] args1 = new Value[100];
        for (int i = 0; i < 100; i++)
            args1[i] = IntegerValue.valueOf(i + 1);
        Expression set1To100 = new ExpressionWithValue(new SetValue(TypeFactory.mkInteger(), args1));

        // create query expression
        Expression[] args2 =
            new Expression[] {
                new ExpVariable("acc", TypeFactory.mkInteger()),
                new ExpVariable("e", TypeFactory.mkInteger())};
        Expression accPlusE = ExpStdOp.create("+", args2);

        Expression exp =
            new ExpIterate(
                           new VarDecl("e", TypeFactory.mkInteger()),
                           new VarInitializer(
                                              "acc",
                                              TypeFactory.mkInteger(),
                                              new ExpConstInteger(0)),
                           set1To100,
                           accPlusE);
        assertEquals(exp.toString(), IntegerValue.valueOf(5050), e.eval(exp, fState));
    }

    public void testIterate2() throws ExpInvalidException {
        // Set { 1..3 }->iterate(e1, e2; acc : Integer= 0 | acc + e1 * e2));
        Value[] args1 = new Value[3];
        for (int i = 0; i < 3; i++)
            args1[i] = IntegerValue.valueOf(i + 1);
        Expression set1To3 = new ExpressionWithValue(new SetValue(TypeFactory.mkInteger(), args1));

        // create query expression
        Expression[] args2 =
            new Expression[] {
                new ExpVariable("e1", TypeFactory.mkInteger()),
                new ExpVariable("e2", TypeFactory.mkInteger())};
        Expression e1Multe2 = ExpStdOp.create("*", args2);
        args2 =
            new Expression[] {
                new ExpVariable("acc", TypeFactory.mkInteger()),
                e1Multe2 };
        Expression add = ExpStdOp.create("+", args2);

        VarDeclList elemVars = new VarDeclList(true);
        elemVars.add(new VarDecl("e1", TypeFactory.mkInteger()));
        elemVars.add(new VarDecl("e2", TypeFactory.mkInteger()));
        Expression exp =
            new ExpIterate(
                           elemVars,
                           new VarInitializer(
                                              "acc",
                                              TypeFactory.mkInteger(),
                                              new ExpConstInteger(0)),
                           set1To3,
                           add);
        assertEquals(exp.toString(), IntegerValue.valueOf(36), e.eval(exp, fState));
    }

    /**
     * Tests that navigating under invalid multiplicities does throw
     * the proper exception (and no RuntimeException).
     */
    public void testNavigationWithMultiplicityFailure() {
    	List<VarDecl> emptyQualifiers = Collections.emptyList();
    	
        try {
            ModelFactory f = new ModelFactory();
            MModel model =  f.createModel("Test");
            
            MClass a = f.createClass("A",false);
            model.addClass(a);
            
            MClass b = f.createClass("B",false);
            MAttribute x = f.createAttribute("x",TypeFactory.mkInteger());
            model.addClass(b);
            b.addAttribute(x);
            
            MAssociation r = f.createAssociation("R");
            MAssociationEnd ra = f.createAssociationEnd(a, "a", MMultiplicity.ZERO_MANY,
                                                        MAggregationKind.NONE, 
                                                        false, emptyQualifiers);
            MAssociationEnd rb = f.createAssociationEnd(b, "b", MMultiplicity.ONE,  
                                                        MAggregationKind.NONE, 
                                                        false, emptyQualifiers);
            r.addAssociationEnd(ra);
            r.addAssociationEnd(rb);
            model.addAssociation(r);

            MSystemState state = new MSystem(model).state();
            MObject a1 = state.createObject(a,"A1");
            MObject b1 = state.createObject(b,"B1");
            MObject b2 = state.createObject(b,"B2");

            state.createLink( r, Arrays.asList( new MObject[] { a1, b1 } ), null);
            state.createLink( r, Arrays.asList( new MObject[] { a1, b2 } ), null);

            VarBindings bindings = new VarBindings();
            bindings.push( "A1", new ObjectValue(a1.cls(), a1));
            bindings.push( "B1", new ObjectValue(a1.cls(), b1));
            bindings.push( "B2", new ObjectValue(a1.cls(), b2));
            
           
            ExpVariable expVar = new ExpVariable( "A1", a );
            ExpNavigation nav = new ExpNavigation( expVar, ra, rb, Collections.<Expression>emptyList() );
            
            try {
                e.eval(nav, state, bindings);
            } catch (MultiplicityViolationException ex) {
                // expected
                return;
            }
            fail("Unrecognized multiplicity violation.");
        } catch (MInvalidModelException ex) {
            fail("Serious error. Could not create model.");
        } catch (MSystemException ex) {
            fail("Serious error. Could not create system.");
        } catch (ExpInvalidException ex) {
            fail("Serious error. Could not create expression.");
        } 

    }

    /**
     * Test for bug0020.
     */
    public void testSetOfSetSequenceAndBag() {
        ModelFactory f = new ModelFactory();
        MModel model =  f.createModel("Test");
        String expText = "Set{Set{42},Sequence{42},Bag{42}}";
        
        PrintWriter dummyWriter = new PrintWriter(new StringWriter());
        VarBindings bindings = new VarBindings();
                        
        Expression exp = OCLCompiler.compileExpression(model, expText, "<junit test>", 
                                                       dummyWriter, bindings);
        
        assertNotNull(exp);
        
        MSystemState state = new MSystem(model).state();
        e.eval(exp, state, bindings);
    }

    /**
     * Test for bug0020.
     */
    public void testBagOfSetSequenceAndBag() {
        ModelFactory f = new ModelFactory();
        MModel model =  f.createModel("Test");
        String expText = "Bag{Set{42},Sequence{42},Bag{42}}";
        
        PrintWriter dummyWriter = new PrintWriter(new StringWriter());
        VarBindings bindings = new VarBindings();
                        
        Expression exp = OCLCompiler.compileExpression(model, expText, "<junit test>", 
                                                       dummyWriter, bindings);
        
        assertNotNull(exp);
        
        MSystemState state = new MSystem(model).state();
        e.eval(exp, state, bindings);
    }
}
