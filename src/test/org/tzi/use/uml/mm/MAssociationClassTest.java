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

import org.tzi.use.SystemManipulator;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;


/**
 * The class <code>MAssociationClassTest</code> tests if an
 * AssociationClass is created correctly.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class MAssociationClassTest extends TestCase {

	static List<VarDecl> emptyQualifiers = Collections.emptyList();
	
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
            MSystem system = new MSystem( model );
            
            SystemManipulator manipulator = new SystemManipulator(system);

            manipulator.createObjects("Person", "p1", "p2");
            
            manipulator.createObjects("Company", "c1");
            
            manipulator.createLinkObject("Job", "j1", "p1", "c1");
      
            assertEquals( system.state().objectByName( "p1" ).name(), "p1" );
            assertEquals( system.state().objectByName( "p2" ).name(), "p2" );
            assertEquals( system.state().objectByName( "c1" ).name(), "c1" );
            assertEquals( system.state().objectByName( "j1" ).name(), "j1" );
        } catch ( MSystemException e ) {
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
            ModelFactory mf = new ModelFactory();

            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );
            MAssociationClass job = mf.createAssociationClass( "Job", false );
            model.addClass( job );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MAssociationEnd endPerson = mf.createAssociationEnd( person, "employee", m1,
                                                                 MAggregationKind.NONE,
                                                                 false, emptyQualifiers );
            MAssociationEnd endCompany = mf.createAssociationEnd( company, "company", m2,
                                                                  MAggregationKind.NONE,
                                                                  false, emptyQualifiers );
            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );

            Type type = TypeFactory.mkInteger();
            MAttribute attr = mf.createAttribute( "employee", type );
            job.addAttribute( attr );
            fail( "MInvalidModelException was not thrown." );
        } catch ( MInvalidModelException e ) {
            // wanted.
        }

    }


    /**
     * Tests that no rolename of an AssociationClass can have the same name
     * as its attribute.
     */
    public void testOverlappingAssociationEndAndAttributeNames() {
        // Test for Well-Formedness Rule No. 1 of AssociationClass of OMG 1.4
        try {
            ModelFactory mf = new ModelFactory();

            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );
            MAssociationClass job = mf.createAssociationClass( "Job", false );
            model.addClass( job );

            // adds an attribute
            Type type = TypeFactory.mkInteger();
            MAttribute attr = mf.createAttribute( "employee", type );
            job.addAttribute( attr );

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
        MAssociationClass job = null;
        MAssociationEnd endPerson = null;
        MAssociationEnd endCompany = null;
        MAssociationEnd endSalary = null;
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompanySalary" );

            MClass person = mf.createClass( "Person", false );
            model.addClass( person );

            MClass company = mf.createClass( "Company", false );
            model.addClass( company );

            MClass salary = mf.createClass( "Salary", false );
            model.addClass( salary );

            job = mf.createAssociationClass( "Job", false );
            model.addClass( job );

            // adds the associationends
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MMultiplicity m3 = mf.createMultiplicity();
            m3.addRange( 0, 1 );

            endPerson = mf.createAssociationEnd( person, "employee", m1,
                                                 MAggregationKind.AGGREGATION,
                                                 false, emptyQualifiers );
            endCompany = mf.createAssociationEnd( company, "employer", m2,
                                                  MAggregationKind.NONE,
                                                  false, emptyQualifiers );
            endSalary = mf.createAssociationEnd( salary, "salary", m3,
                                                 MAggregationKind.NONE,
                                                 false, emptyQualifiers );

            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );
            job.addAssociationEnd( endSalary );

            fail( "MInvalidModelException was not thrown." );
        } catch ( MInvalidModelException e ) {
            assertEquals( endPerson, job.associationEnds().get( 0 ) );
            assertEquals( endCompany, job.associationEnds().get( 1 ) );
            assertEquals( 2, job.associationEnds().size() );
        }

    }

    /**
     * Entry point
     */
    public static void main( String[] args ) {
        junit.textui.TestRunner.run( new TestSuite( MAssociationClassTest.class ) );
    }

}
