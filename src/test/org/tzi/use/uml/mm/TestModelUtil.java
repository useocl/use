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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.mm;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * The class <code>TestModelUtil</code> offers methods for creating
 * different USE-models.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class TestModelUtil {
    private static TestModelUtil util = null;

    private TestModelUtil() {
    }

    /**
     * This method is for creating an instance of this class. It
     * guarantees that only one instance of this class exists.
     */
    public static TestModelUtil getInstance() {
        if ( util == null ) {
            util = new TestModelUtil();
        }
        return util;
    }

    /**
     * This method creates an empty model.
     */
    public MModel createEmptyModel() {
        ModelFactory mf = new ModelFactory();
        MModel model = mf.createModel( "PersonCompany" );
        return model;
    }

    /**
     * This method creates a model with an enumeration.
     */
    public MModel createModelWithEnum() {
        try{
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "Color" );
            
            List literals = new ArrayList();
            literals.add( "blau" );
            literals.add( "gelb" );
            literals.add( "rot" );
            literals.add( "gruen" );
            EnumType type = TypeFactory.mkEnum( "colors", literals );
            model.addEnumType( type );
            
            return model;
        } catch ( MInvalidModelException e ) {
            throw new Error( e );
        }
    }

    /**
     * This method creates a model with some classes, including
     * attributes with an ObjectType.
     */
    public MModel createModelWithObjectTypes() {
        try{
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "TestObjectType" );
            MClass a = mf.createClass( "A", false );
            MClass b = mf.createClass( "B", false );
            model.addClass (a);
            model.addClass (b);
            MAttribute a1 = mf.createAttribute ("name",TypeFactory.mkObjectType(b));
            a.addAttribute (a1);
            
            return model;
        } catch ( MInvalidModelException e ) {
            throw new Error( e );
        }
    }


    public MModel createModelWithCollectionTypes() {
        try{
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "TestObjectType" );
            MClass a = mf.createClass( "A", false ); 
            MClass b = mf.createClass( "B", false );
            model.addClass (a);
            model.addClass (b);
            MAttribute a1 = mf.createAttribute
                ("name",TypeFactory.mkSet(TypeFactory.mkObjectType(b)));
            a.addAttribute(a1);
            return model;
        } 
        catch ( MInvalidModelException e ) {
            throw new Error( e );
        }
        
    }

    /**
     * This method creates a model with two classes (Person and Company).
     */
    public MModel createModelWithClasses() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );
            return model;
        } catch ( MInvalidModelException e ) {
            throw new Error( e );
        }
    }

    /**
     * This method creates a model with two classes (Person and Company)
     * and two associations (Job and isBoss).
     */
    public MModel createModelWithClassAndAssocs() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );
            MAssociation job = mf.createAssociation( "Job" );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MAssociationEnd endPerson = mf.createAssociationEnd( person,
                                                                 "employee", m1,
                                                                 MAggregationKind.NONE,
                                                                 false );
            MAssociationEnd endCompany = mf.createAssociationEnd( company,
                                                                  "company", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );
            model.addAssociation( job );
            MAssociation isBoss = mf.createAssociation( "isBoss" );
            MAssociationEnd endPerson1 = mf.createAssociationEnd( person, "boss", m1,
                                                                  MAggregationKind.NONE,
                                                                  false );
            MAssociationEnd endPerson2 = mf.createAssociationEnd( person, "worker", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            isBoss.addAssociationEnd( endPerson1 );
            isBoss.addAssociationEnd( endPerson2 );
            model.addAssociation( isBoss );
            return model;
        } catch ( MInvalidModelException e ) {
            //e.printStackTrace();
            throw new Error( e );
        }
    }

    /**
     * This method creates a model with two classes (Person and Company)
     * and one association (Job). It contains higher multiplicities.
     */
    public MModel createModelWithClassAndAssocs2() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );

            MAttribute companyName = mf.createAttribute( "name", TypeFactory.mkString() );
            company.addAttribute( companyName );

            MAssociation job = mf.createAssociation( "Job" );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, MMultiplicity.MANY );
            MAssociationEnd endPerson = mf.createAssociationEnd( person,
                                                                 "employee", m1,
                                                                 MAggregationKind.NONE,
                                                                 false );
            MAssociationEnd endCompany = mf.createAssociationEnd( company,
                                                                  "company", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );
            model.addAssociation( job );

            return model;
        } catch ( MInvalidModelException e ) {
            //e.printStackTrace();
            throw new Error( e );
        }
    }

    /**
     * This method creates a model with two classes (Person and Company)
     * and an associationclass (Job).
     */
    public MModel createModelWithClassAndAssocClass() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );

            MAttribute companyName = mf.createAttribute( "name", TypeFactory.mkString() );
            company.addAttribute( companyName );

            MAssociationClass job = mf.createAssociationClass( "Job", false );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MAssociationEnd endPerson = mf.createAssociationEnd( person, "person", m1,
                                                                 MAggregationKind.NONE,
                                                                 false );
            MAssociationEnd endCompany = mf.createAssociationEnd( company, "company", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );
            model.addClass( job );
            model.addAssociation( job );

            MAttribute salary = mf.createAttribute( "salary", TypeFactory.mkInteger() );
            job.addAttribute( salary );

            return model;
        } catch ( MInvalidModelException e ) {
            //e.printStackTrace();
            throw new Error( e );
        }
    }

    /**
     * This method creates a model with one class (Person)
     * and one associationclass (Job).
     */
    public MModel createModelWithOneClassAndOneAssocClass() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            model.addClass( person );

            MAssociationClass job = mf.createAssociationClass( "Job", false );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MAssociationEnd endPerson1 = mf.createAssociationEnd( person, "boss", m1,
                                                                  MAggregationKind.NONE,
                                                                  false );
            MAssociationEnd endPerson2 = mf.createAssociationEnd( person, "worker", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            job.addAssociationEnd( endPerson1 );
            job.addAssociationEnd( endPerson2 );
            model.addClass( job );
            model.addAssociation( job );

            return model;
        } catch ( MInvalidModelException e ) {
            //e.printStackTrace();
            throw new Error( e );
        }
    }

    /**
     * This method creates a model with three classes (Person, Salary and Company)
     * and an associationclass (Job).
     */
    public MModel createModelWithClassAndTenaryAssocClass() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            MClass salary = mf.createClass( "Salary", false );
            model.addClass( person );
            model.addClass( company );
            model.addClass( salary );

            MAttribute companyName = mf.createAttribute( "name", TypeFactory.mkString() );
            company.addAttribute( companyName );

            MAssociationClass job = mf.createAssociationClass( "Job", false );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MMultiplicity m3 = mf.createMultiplicity();
            m3.addRange( 0, 1 );
            MAssociationEnd endPerson = mf.createAssociationEnd( person, "person", m1,
                                                                 MAggregationKind.NONE,
                                                                 false );
            MAssociationEnd endCompany = mf.createAssociationEnd( company, "company", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            MAssociationEnd endSalary = mf.createAssociationEnd( salary, "salary", m3,
                                                                 MAggregationKind.NONE,
                                                                 false );
            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );
            job.addAssociationEnd( endSalary );
            model.addClass( job );
            model.addAssociation( job );
            return model;
        } catch ( MInvalidModelException e ) {
            //e.printStackTrace();
            throw new Error( e );
        }
    }


    /**
     * This method creates a model with two classes (Person and Company),
     * an associationclass (Job) and an association (isBoss).
     */
    public MModel createComplexModel() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonCompany" );

            // adds two classes named Person and Company
            MClass person = mf.createClass( "Person", false );
            MClass company = mf.createClass( "Company", false );
            model.addClass( person );
            model.addClass( company );

            MAttribute companyName = mf.createAttribute( "name", TypeFactory.mkString() );
            company.addAttribute( companyName );

            // adds an associationclass between Person and Company named Job
            MAssociationClass job = mf.createAssociationClass( "Job", false );
            MMultiplicity m1 = mf.createMultiplicity();
            m1.addRange( 0, 1 );
            MMultiplicity m2 = mf.createMultiplicity();
            m2.addRange( 0, 1 );
            MAssociationEnd endPerson = mf.createAssociationEnd( person, "employee", m1,
                                                                 MAggregationKind.NONE,
                                                                 false );
            MAssociationEnd endCompany = mf.createAssociationEnd( company, "company", m2,
                                                                  MAggregationKind.NONE,
                                                                  false );
            job.addAssociationEnd( endPerson );
            job.addAssociationEnd( endCompany );
            model.addClass( job );
            model.addAssociation( job );

            // adds an association between Person itself named isBoss
            MAssociation isBoss = mf.createAssociation( "isBoss" );
            m1.addRange( 0, 1 );
            m2.addRange( 0, 1 );
            MAssociationEnd endWorker = mf.createAssociationEnd( person, "worker", m1,
                                                                 MAggregationKind.NONE,
                                                                 false );
            MAssociationEnd endBoss = mf.createAssociationEnd( person, "boss", m2,
                                                               MAggregationKind.NONE,
                                                               false );
            isBoss.addAssociationEnd( endWorker );
            isBoss.addAssociationEnd( endBoss );
            model.addAssociation( isBoss );

            return model;
        } catch ( MInvalidModelException e ) {
            //e.printStackTrace();
            throw new Error( e );
        }
    }


    /**
     * This method creates a model with two classes (Person 
     * and Employee) and a generalization structure between them.
     */
    public MModel createModelWithGen() {
        try {
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "PersonEmployee" );
            MClass person = mf.createClass( "Person", false );
            MClass employee = mf.createClass( "Employee", false );
            model.addClass( person );
            model.addClass( employee );
            MGeneralization gen = mf.createGeneralization( employee, person );
            model.addGeneralization( gen );
            return model;
        } catch ( MInvalidModelException e ) {
            throw new Error( e );
        }
    }

    public MModel createModelWithOperation() {
        try{
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "Person" );
            MClass person = mf.createClass( "Person", false );
            model.addClass (person);

            // adds an attribute
            MAttribute name = mf.createAttribute("fName", TypeFactory.mkString() );
            person.addAttribute (name);
            
            // adds an operation
            MOperation op = mf.createOperation( "equalsName", 
                                                new VarDeclList( 
                                                    new VarDecl( "name", TypeFactory.mkString() )), 
                                                TypeFactory.mkBoolean() );
            person.addOperation( op );
            
            return model;
        } catch ( MInvalidModelException e ) {
            throw new Error( e ) ;
        }
    }
        
    public MModel createModelWithInvariant() {
        try{
            ModelFactory mf = new ModelFactory();
            MModel model = mf.createModel( "Person" );
            MClass person = mf.createClass( "Person", false );
            model.addClass (person);

            // adds an attribute
            MAttribute name = mf.createAttribute("fName", TypeFactory.mkString() );
            person.addAttribute (name);
            
            Expression expr = new ExpVariable( "p1", TypeFactory.mkBoolean() );
            MClassInvariant inv = new MClassInvariant( null, null, person, expr );
            model.addClassInvariant( inv );
            
            return model;
        } catch ( MInvalidModelException e ) {
            throw new Error( e );
        } catch ( ExpInvalidException e ) {
            throw new Error( e );
        }
    }

}
