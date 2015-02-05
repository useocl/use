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

package org.tzi.use.uml.mm;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * The class <code>MAssociationClassTest</code> tests if an
 * AssociationClass is created correctly.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class MAssociationClassTest extends TestCase {

	static final List<VarDecl> emptyQualifiers = Collections.emptyList();
	
    /**
     * Creates a model with classes and an associationclass.
     */
    public void testCreateModel() {
        TestModelUtil.getInstance().createModelWithClassAndAssocClass();
    }

    /**
     * Creates a model with classes and an associationclass an
     * creates objects of these.
     */
    public void testCreateObjects() {
        try {
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocClass();
                        
            UseSystemApi api = UseSystemApi.create(model, false);

            api.createObjects("Person", "p1", "p2");
            api.createObjects("Company", "c1");
            api.createLinkObject("Job", "j1", new String[] {"p1", "c1"});
      
            assertEquals( api.getObject( "p1" ).name(), "p1" );
            assertEquals( api.getObject( "p2" ).name(), "p2" );
            assertEquals( api.getObject( "c1" ).name(), "c1" );
            assertEquals( api.getObject( "j1" ).name(), "j1" );
        } catch ( UseApiException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Tests that no Attribute of an AssociationClass can have the same name
     * as its rolename.
     */
    public void testOverlappingAttributeAndAssociationEndNames() {
        // Test for Well-Formedness Rule No. 1 of AssociationClass of OMG 1.4
        try {
            UseModelApi api = new UseModelApi("PersonCompany");
            api.createClass( "Person", false );
            api.createClass( "Company", false );
            api.createAssociationClass( "Job", false, 
                                        "Person", "employee", "0..1", MAggregationKind.NONE,
            		                    "Company", "company", "0..1", MAggregationKind.NONE);

            api.createAttribute( "Job", "employee", "Integer");

            fail( "UseApiException was not thrown." );
        } catch ( UseApiException e ) {
            // wanted.
        }
    }


    /**
     * Tests that no rolename of an AssociationClass can have the same name
     * as its attribute.
     */
    public void testOverlappingAssociationEndAndAttributeNames() {
        // Test for Well-Formedness Rule No. 1 of AssociationClass of OMG 1.4
    	
    	// Combined use of api and direct model manipulation. 
    	UseModelApi api = new UseModelApi("PersonCompany");
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = api.getModel();
            
            MClass person = api.createClass( "Person", false );
            api.createClass( "Company", false );
            
            MAssociationClass job = mf.createAssociationClass( "Job", false );
            model.addClass( job );

            // adds an attribute
            Type type = TypeFactory.mkInteger();
            api.createAttributeEx( job, "employee", type );

            // adds the associationends
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MAssociationEnd endPerson = mf.createAssociationEnd( person, "employee", m1,
                                                                 MAggregationKind.NONE,
                                                                 false,
                                                                 emptyQualifiers);
            job.addAssociationEnd( endPerson );

            fail( "MInvalidModelException was not thrown." );
        } catch ( MInvalidModelException e ) {
            // wanted.
        } catch (  UseApiException e ) {
        	throw new Error(e);
        }

    }

    /**
     * Tests that an AssociationClass cannot be defiened between itself
     * and something else.
     */
    public void testAssocClassBetweenItself() {
        // Test for Well-Formedness Rule No. 2 of AssociationClass of OMG 1.4
        MAssociationClass job = null;
        MAssociationEnd endPerson = null;
        
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );

            MClass person = mf.createClass( "Person", false );
            model.addClass( person );

            job = mf.createAssociationClass( "Job", false );
            model.addClass( job );

            // adds the associationends
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            endPerson = mf.createAssociationEnd( person, "employee", m1,
                                                 MAggregationKind.NONE,
                                                 false, emptyQualifiers );
            MAssociationEnd endJob = mf.createAssociationEnd( job, "employee", m1,
                                                              MAggregationKind.NONE,
                                                              false,
                                                              emptyQualifiers);


            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endJob );

            fail( "MInvalidModelException was not thrown." );
        } catch ( MInvalidModelException e ) {
            assertEquals( job.associationEnds().get( 0 ), endPerson );
        }

    }

    /**
     * Tests that an ternary AssociationClass cannot be used with
     * an aggregation.
     */
    public void testTernaryAssocClassWithAggregation() {
        
    	UseModelApi api = new UseModelApi("PersonCompanySalary");
    	
    	try {
            api.createClass( "Person", false );
            api.createClass( "Company", false );
            api.createClass( "Salary", false );

            api.createAssociationClass( "Job", false, 
            		                    new String[] {"Person",   "Company",  "Salary"},
            		                    new String[] {"employee", "employer", "salary"},
            		                    new String[] {"0..1",     "0..1",     "0..1"},
            		                    new int[]    {MAggregationKind.AGGREGATION, MAggregationKind.NONE, MAggregationKind.NONE} 
            		                    );
            
            fail( "UseApiException was not thrown." );
        } catch ( UseApiException e ) {
        	assertNull(api.getClass("Job"));
        	assertNull(api.getAssociation("Job"));
        }
    }

    /**
     * Entry point
     */
    public static void main( String[] args ) {
        junit.textui.TestRunner.run( new TestSuite( MAssociationClassTest.class ) );
    }

}
