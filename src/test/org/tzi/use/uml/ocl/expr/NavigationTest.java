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

import junit.framework.*;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.ObjectCreation;

/**
 * NavigationTest tests the navigation with an linkobject.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class NavigationTest extends TestCase {

    public void testModelWithObjects() {
        ObjectCreation.getInstance().createModelWithObjects();
        ObjectCreation.getInstance().createModelWithManyObjects();
        ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
    }

//-------------------------------------------------------------
// Test the navigation by compiling an expression
//-------------------------------------------------------------

    /**
     * Test the navigation from p1 to company (a normal association).
     */
    public void testNavigationWithNormalAssoc() {
        MSystem system = ObjectCreation.getInstance().createModelWithManyObjects();
        String expr = "p1.company->size";
        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings());

        assertTrue( value.isInteger() );
        IntegerValue val = ( IntegerValue ) value;
        assertEquals( 4, val.value() );
    }


    /**
     * Test the navigation from p1 to a attribute of company
     * (a normal association).
     */
    public void testNavigationToAnAttributeWithNormalAssoc() {
        MSystem system = ObjectCreation.getInstance().createModelWithManyObjects();
        String expr = "p1.company->exists(c|c.name='IBM')";
        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );
        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isBoolean() );
        assertTrue( ( ( BooleanValue ) value ).value() );
    }

    /**
     * Test the navigation from p1 to an attribute of company
     * (with associationclass).
     */
    public void testNavigationToAnAttributeWithAssocClass1() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
        String expr = "c1.name = 'IBM'";
        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isBoolean() );
        assertTrue( ( ( BooleanValue ) value ).value() );
    }

    /**
     * Test the navigation from p1 over p2 to the attribute of company
     * (with associationclass).
     */
    public void testNavigationToAnAttributeWithAssocClass2() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject2();
        String expr = "p1.boss.company.name = p1.company.name";

        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings());

        assertTrue( value.isBoolean() );
        assertTrue( ( ( BooleanValue ) value ).value() );
    }

    /**
     * Test the navigation from p1 to the attribute of company
     * (with associationclass).
     */
    public void testNavigationToAnAttributeWithTernaryAssocClass() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndTenaryLinkObject();
        String expr = "c1.job = s1.job and p1.job = s1.job and c1.job = p1.job";

        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isBoolean() );
        assertTrue( ( ( BooleanValue ) value ).value() );
    }


    /**
     * Entry point
     */
    public static void main( String[] args ) {
        junit.textui.TestRunner.run( new TestSuite( ExprNavigationTest.class ) );

    }
}
