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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.ObjectCreation;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MSequenceStatement;


/**
 * ExprNavigationTest tests the navigation with an linkobject.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class ExprNavigationTest extends TestCase {

	static List<Value> emptyQualiferValues = Collections.emptyList();
	
    public void testModelWithObjects() {
        ObjectCreation.getInstance().createModelWithObjects();
        ObjectCreation.getInstance().createModelWithManyObjects();
        ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
    }

//-------------------------------------------------------------
// Tests for Navigation with normal links
//-------------------------------------------------------------

    public void testNavigationWithAssocs() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjects();
        List<MAssociationEnd> assocEnds = system.model().getAssociation( "Job" ).associationEnds();
        MAssociationEnd personEnd = null;
        MAssociationEnd companyEnd = null;
        for ( int i = 0; i < assocEnds.size(); i++ ) {
            MAssociationEnd ae = assocEnds.get( i );
            if ( ae.cls().name().equals( "Person" ) ) {
                personEnd = ae;
            }
            if ( ae.cls().name().equals( "Company" ) ) {
                companyEnd = ae;
            }
        }

        try {

            Expression srcExpr = new ExpVariable( "p1", system.model().getClass( "Person" ) );

            ExpNavigation nav = new ExpNavigation( srcExpr, personEnd, companyEnd, Collections.<Expression>emptyList() );
            Value val = nav.eval( new EvalContext( null, system.state(),
                                                   system.varBindings(), 
                                                   null, "") );

            assertTrue( val.isObject() );
            assertEquals( "c1", ( ( ObjectValue ) val ).value().name() );
        } catch ( ExpInvalidException e ) {
            throw new Error( e );
        }
    }

    public void testNavigationWithAssocs1_MANY() {
        MSystem system = ObjectCreation.getInstance().createModelWithManyObjects();
        List<MAssociationEnd> assocEnds = system.model().getAssociation( "Job" ).associationEnds();
        MAssociationEnd personEnd = null;
        MAssociationEnd companyEnd = null;
        for ( int i = 0; i < assocEnds.size(); i++ ) {
            MAssociationEnd ae = assocEnds.get( i );
            if ( ae.cls().name().equals( "Person" ) ) {
                personEnd = ae;
            }
            if ( ae.cls().name().equals( "Company" ) ) {
                companyEnd = ae;
            }
        }

        try {

            Expression srcExpr = new ExpVariable( "p1", system.model().getClass( "Person" ) );

            ExpNavigation nav = new ExpNavigation( srcExpr, personEnd, companyEnd, Collections.<Expression>emptyList() );
            Value val = nav.eval( new EvalContext( null, system.state(),
                                                   system.varBindings(),
                                                   null, "" ) );

            assertTrue( val.isSet() );
            assertEquals( 4, ( ( SetValue ) val ).size() );
            
            Iterator<Value> it = ( ( SetValue ) val ).getSortedElements().iterator();
            
            assertEquals( "c1", ( ( ObjectValue ) it.next() ).value().name() );
            assertEquals( "c2", ( ( ObjectValue ) it.next() ).value().name() );
            assertEquals( "c3", ( ( ObjectValue ) it.next() ).value().name() );
            assertEquals( "c4", ( ( ObjectValue ) it.next() ).value().name() );

        } catch ( ExpInvalidException e ) {
            throw new Error( e );
        }
    }

//-------------------------------------------------------------
// Tests for navigation with linkobjects
//-------------------------------------------------------------

    public void testNavigationWithAssocClass() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
        MAssociationClass job = system.model().getAssociationClass( "Job" );
        List<MAssociationEnd> assocEnds = job.associationEnds();
        MAssociationEnd personEnd = null;
        for ( int i = 0; i < assocEnds.size(); i++ ) {
            MAssociationEnd ae = assocEnds.get( i );
            if ( ae.cls().name().equals( "Person" ) ) {
                personEnd = ae;
            }
        }


        try {

            Expression srcExpr = new ExpVariable( "p1", system.model().getClass( "Person" ) );

            ExpNavigation nav = new ExpNavigation( srcExpr, personEnd, job, Collections.<Expression>emptyList() );
            Value val = nav.eval( new EvalContext( null, system.state(),
                                                   system.varBindings(), 
                                                   null, "" ) );

            assertTrue( val.isObject() );
            assertEquals( "j1", val.toString());
        } catch ( ExpInvalidException e ) {
            throw new Error( e );
        }
    }

//-------------------------------------------------------------
// Tests for the method getNavigableElements() in MSystemState
//-------------------------------------------------------------

    /**
     * Test the navigation from a specific object to the linkobject j1.
     */
    public void testNavigableElementsToLinkObject() {
        try {
            MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
            MModel model = system.model();

            List<String> names = new ArrayList<String>();
            MSequenceStatement seq = new MSequenceStatement();
            
            // creation of an object (c2) of the class Company
            
            names.add( "c2" );
            MClass companyClass = model.getClass("Company");
            
            for (String name : names) {
            	seq.appendStatement(
            			new MNewObjectStatement(
            					companyClass, 
            					name));
            }
            system.execute(seq);
            seq.clear();

            // creation of a link between p1 and c2 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c2" );
            MAssociationClass assoc = model.getAssociationClass( "Job" );
            
            List<MRValue> participants = new ArrayList<MRValue>();
            for (String name : names) {
            	participants.add(
            			new MRValueExpression(
            					system.state().objectByName(name)));
            }
            
            system.execute(
            		new MNewLinkObjectStatement(
            				assoc, 
            				participants,
            				Collections.<List<MRValue>>emptyList(),
            				"j2"), false);

            MAssociationClass job = system.model().getAssociationClass( "Job" );
            MAssociationEnd personEnd =
                    job.associationEndsAt( system.model().getClass( "Person" ) ).iterator().next();

            // navigation from p1 to linkobject j1
            MObject p1 = system.state().objectByName( "p1" );

            List<MObject> objects = system.state().getNavigableObjects( p1, personEnd, job, emptyQualiferValues );

            assertEquals( 2, objects.size() );
            
            assertTrue(objects.contains(system.state().objectByName("j2")));
            assertTrue(objects.contains(system.state().objectByName("j1")));
        } catch ( Exception e ) {
            fail( "Exception was thrown: " + e.getMessage() );
        }
    }

    /**
     * Test the navigation from linkobject j1 to a specific object.
     */
    public void testNavigableElementsFromLinkObject() {
        try {
            MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
            MAssociationClass job = system.model().getAssociationClass( "Job" );
            MAssociationEnd personEnd = job.associationEndsAt( system.model().getClass( "Person" ) ).iterator().next();

            // navigation from linkobject j1 to p1
            MObject j1 = system.state().objectByName( "j1" );

            List<MObject> objects = system.state().getNavigableObjects( j1, job, personEnd, emptyQualiferValues );

            assertEquals( 1, objects.size() );
            assertEquals( "p1", ( objects.get( 0 ) ).name() );
        } catch ( Exception e ) {
            fail( "Exception was thrown: " + e.getMessage() );
        }
    }

    /**
     * Test the navigation from an object to a specific object
     * respectively to the associationClass Job.
     */
    public void testNavigableElementsWithAssocClass() {
        try {
            MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
            MAssociationClass job = system.model().getAssociationClass( "Job" );
            MAssociationEnd personEnd =
                job.associationEndsAt( system.model().getClass( "Person" ) ).iterator().next();
            MAssociationEnd companyEnd =
                job.associationEndsAt( system.model().getClass( "Company" ) ).iterator().next();

            // navigation from linkobject j1 to p1
            MObject p1 = system.state().objectByName( "p1" );

            List<MObject> objects = system.state().getNavigableObjects( p1, personEnd, companyEnd, emptyQualiferValues );

            assertEquals( 1, objects.size() );
            assertEquals( "c1", ( objects.get( 0 ) ).name() );
        } catch ( Exception e ) {
            fail( "Exception was thrown: " + e.getMessage() );
        }
    }

    /**
     * Test the navigation from a specific object respectively to the
     * association Job.
     */
    public void testNavigableElementsWithNormalAssoc() {
        try {
            MSystem system = ObjectCreation.getInstance().createModelWithObjects();
            MModel model = system.model();

            List<String> names = new ArrayList<String>();
            MSequenceStatement seq = new MSequenceStatement();
            
            // creation of an object (c2) of the class Company
            names.add( "c2" );
            MClass companyClass = model.getClass("Company");
            
            for (String name : names) {
            	seq.appendStatement(
            			new MNewObjectStatement(
            					companyClass, 
            					name));
            }
            system.execute(seq);
            seq.clear();


            // creation of a link between p1 and c2 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c2" );
            List<MRValue> exprs = new ArrayList<MRValue>();
            List<List<MRValue>> qualifier = new ArrayList<List<MRValue>>();
            
            Iterator<String> it = names.iterator();
            
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( it.next() );
                exprs.add(new MRValueExpression(new ExpVariable( obj.name(), obj.cls() )));
            }
            MAssociation assoc = model.getAssociation( "Job" );
            
            system.execute(new MLinkInsertionStatement(assoc, exprs, qualifier), true);
            
            MAssociation job = system.model().getAssociation( "Job" );
            MAssociationEnd personEnd =
                job.associationEndsAt( system.model().getClass( "Person" ) ).iterator().next();
            MAssociationEnd companyEnd =
                job.associationEndsAt( system.model().getClass( "Company" ) ).iterator().next();

            // navigation from p1 to linkobject j1
            MObject p1 = system.state().objectByName( "p1" );

            List<MObject> objects = system.state().getNavigableObjects( p1, personEnd, companyEnd, emptyQualiferValues );

            names = Arrays.asList("c1", "c2");
            assertEquals( 2, objects.size() );
            assertTrue( names.contains( objects.get( 1 ).name() ) );
            assertTrue( names.contains( objects.get( 0 ).name() ) );
        } catch ( MSystemException e ) {
            fail( "Exception was thrown: " + e.getMessage() );
        }
    }

//-------------------------------------------------------------
// Test the navigation by compiling an expression
//-------------------------------------------------------------

    /**
     * Test the navigation from p1 to company (a normal association).
     */
    public void testNavigationWithNormalAssoc() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
        String expr = "p1.company->size";
        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isInteger() );
        IntegerValue val = ( IntegerValue ) value;
        assertEquals( 1, val.value() );
    }

    /**
     * Test the navigation from j1 to company (from an linkObject to an
     * object).
     */
    public void testNavigationFromLinkObjectToObject() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
        String expr = "j1.company->size";
        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isInteger() );
        IntegerValue val = ( IntegerValue ) value;
        assertEquals( 1, val.value() );
    }

    /**
     * Test the navigation from c1 to job (from an object to an
     * linkObject).
     */
    public void testNavigationFromObjectToLinkObject() {
        MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
        String expr = "c1.job->size";
        PrintWriter pw = new PrintWriter( System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isInteger() );
        IntegerValue val = ( IntegerValue ) value;
        assertEquals( 1, val.value() );
    }

    /**
     * Test the navigation from c1 to job with an explicit rolename is given for
     * navigation (from an object to an linkObject).
     */
    public void testNavigationFromObjectToLinkObjectWithExplicitRolename() {
        MSystem system = ObjectCreation.getInstance()
            .createModelWithObjectsOfSameClassAndLinkObject();

        // test 1: navigation with explicit rolename
        String expr = "p1.job[boss]";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter( sw ); //System.err );

        Expression navExpr = OCLCompiler.compileExpression( system.model(),
                                                            expr,
                                                            "<input>", pw,
                                                            system.varBindings() );

        Evaluator eval = new Evaluator();
        Value value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isObject() );
        assertEquals( "j1", value.toString() );

        // test 2: ambigious navigation
        expr = "p1.job";
        
        navExpr = OCLCompiler.compileExpression( system.model(),
                                                 expr,
                                                 "<input>", pw,
                                                 system.varBindings() );
        assertNull( navExpr );
		String expected = "<input>:1:3: The navigation path from `Person' to `job' is ambiguous," + 
							" but no qualification of the source association was given.";
        assertEquals(expected, sw.toString().trim());

        // test 3: rolname is not `worker' is not avaliable
        expr = "p1.job[worker]";
        
        navExpr = OCLCompiler.compileExpression( system.model(),
                                                 expr,
                                                 "<input>", pw,
                                                 system.varBindings() );

        eval = new Evaluator();
        value = eval.eval( navExpr, system.state(), system.varBindings() );

        assertTrue( value.isUndefined() );
    }

    /**
     * Entry point
     */
    public static void main( String[] args ) {
        junit.textui.TestRunner.run( new TestSuite( ExprNavigationTest.class ) );

    }
}
