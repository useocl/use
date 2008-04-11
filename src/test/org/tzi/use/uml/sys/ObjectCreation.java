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

$Id$

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * ObjectCreation is needed for creating instances of a specific
 * model. This class is only needed for the tests.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class ObjectCreation {
    private static ObjectCreation instance = null;

    private ObjectCreation() {
    }

    public static ObjectCreation getInstance() {
        if ( instance == null ) {
            instance = new ObjectCreation();
        }
        return instance;
    }

    /**
     * Creates a model with two classes and an associationclass. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjects() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocs();
            MSystem system = new MSystem( model );

            // creation of an object (p1) of the class Person
            List names = new ArrayList();
            names.add( "p1" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (c1) of the class Company
            names.clear();
            names.add( "c1" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link between p1 and c1 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MAssociation assoc = model.getAssociation( "Job" );
            cmd = new MCmdInsertLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an associationclass. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithManyObjects() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocs2();
            MSystem system = new MSystem( model );

            // creation of an object (p1) of the class Person
            List names = new ArrayList();
            names.add( "p1" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (c1) of the class Company
            names.clear();
            names.add( "c1" );
            names.add( "c2" );
            names.add( "c3" );
            names.add( "c4" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link between p1 and c1 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MAssociation assoc = model.getAssociation( "Job" );
            cmd = new MCmdInsertLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            // creation of a link between p1 and c2 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c2" );
            exprs = new Expression[names.size()];
            it = names.iterator();
            i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            assoc = model.getAssociation( "Job" );
            cmd = new MCmdInsertLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            // creation of a link between p1 and c3 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c3" );
            exprs = new Expression[names.size()];
            it = names.iterator();
            i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            assoc = model.getAssociation( "Job" );
            cmd = new MCmdInsertLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            // creation of a link between p1 and c4 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c4" );
            exprs = new Expression[names.size()];
            it = names.iterator();
            i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            assoc = model.getAssociation( "Job" );
            cmd = new MCmdInsertLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            // set an attribute value in c1
            ExpConstString expr = new ExpConstString( "IBM" );
            MObject obj = system.state().objectByName( "c1" );
            MAttribute attr = obj.cls().attribute( "name", false );
            ExpVariable exprVar = new ExpVariable( obj.name(), obj.type() );
            ExpAttrOp attrOpExp = new ExpAttrOp( attr, exprVar );
            cmd = new MCmdSetAttribute( system.state(), attrOpExp, expr );
            system.executeCmd( cmd );


            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an associationclass. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsAndLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocClass();
            MSystem system = new MSystem( model );

            // creation of an object (p1) of the class Person
            List names = new ArrayList();
            names.add( "p1" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (c1) of the class Company
            names.clear();
            names.add( "c1" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link between p1 and c1 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            MAssociationClass assoc = model.getAssociationClass( "Job" );
            cmd = new MCmdCreateInsertObjects( system.state(), "j1", assoc, names );
            system.executeCmd( cmd );

            // set an attribute value in c1
            ExpConstString expr = new ExpConstString( "IBM" );
            MObject obj = system.state().objectByName( "c1" );
            MAttribute attr = obj.cls().attribute( "name", false );
            ExpVariable exprVar = new ExpVariable( obj.name(), obj.type() );
            ExpAttrOp attrOpExp = new ExpAttrOp( attr, exprVar );
            cmd = new MCmdSetAttribute( system.state(), attrOpExp, expr );
            system.executeCmd( cmd );

            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates an instance of a model with one class and one associationclass. 
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsOfSameClassAndLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithOneClassAndOneAssocClass();
            MSystem system = new MSystem( model );

            // creation of an objects (p1,p2) of the class Person
            List names = new ArrayList();
            names.add( "p1" );
            names.add( "p2" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link between p1 and p2 of an associationclass
            names.clear();
            names.add( "p1" );
            names.add( "p2" );
            MAssociationClass assoc = model.getAssociationClass( "Job" );
            cmd = new MCmdCreateInsertObjects( system.state(), "j1", assoc, names );
            system.executeCmd( cmd );

            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an associationclass. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsAndTenaryLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndTenaryAssocClass();
            MSystem system = new MSystem( model );

            // creation of an object (p1) of the class Person
            List names = new ArrayList();
            names.add( "p1" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (c1) of the class Company
            names.clear();
            names.add( "c1" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (s1) of the class Company
            names.clear();
            names.add( "s1" );
            type = TypeFactory.mkObjectType( model.getClass( "Salary" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link between p1, s1 and c1 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            names.add( "s1" );
            MAssociationClass assoc = model.getAssociationClass( "Job" );
            cmd = new MCmdCreateInsertObjects( system.state(), "j1", assoc, names );
            system.executeCmd( cmd );

            // set an attribute value in c1
            ExpConstString expr = new ExpConstString( "IBM" );
            MObject obj = system.state().objectByName( "c1" );
            MAttribute attr = obj.cls().attribute( "name", false );
            ExpVariable exprVar = new ExpVariable( obj.name(), obj.type() );
            ExpAttrOp attrOpExp = new ExpAttrOp( attr, exprVar );
            cmd = new MCmdSetAttribute( system.state(), attrOpExp, expr );
            system.executeCmd( cmd );

            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }


    /**
     * Creates a model with two classes, an associationclass and an association.
     * It creates instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsAndLinkObject2() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createComplexModel();
            MSystem system = new MSystem( model );

            // creation of an object (p1) of the class Person
            List names = new ArrayList();
            names.add( "p1" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (p2) of the class Person
            names.clear();
            names.add( "p2" );
            type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link between p1 and p2 (p1 is boss of p2)
            names.clear();
            names.add( "p1" );
            names.add( "p2" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MAssociation ass = model.getAssociation( "isBoss" );
            cmd = new MCmdInsertLink( system.state(), exprs, ass );
            system.executeCmd( cmd );

            // creation of an object (c1) of the class Company
            names.clear();
            names.add( "c1" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of an object (c2) of the class Company
            names.clear();
            names.add( "c2" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            // creation of a link object between p1 and c1 of an association
            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            MAssociationClass assoc = model.getAssociationClass( "Job" );
            cmd = new MCmdCreateInsertObjects( system.state(), "j1", assoc, names );
            system.executeCmd( cmd );

            // creation of a link object between p2 and c1 of an association
            names.clear();
            names.add( "p2" );
            names.add( "c1" );
            assoc = model.getAssociationClass( "Job" );
            cmd = new MCmdCreateInsertObjects( system.state(), "j2", assoc, names );
            system.executeCmd( cmd );

            // set an attribute value in c1
            ExpConstString expr = new ExpConstString( "IBM" );
            MObject obj = system.state().objectByName( "c1" );
            MAttribute attr = obj.cls().attribute( "name", false );
            ExpVariable exprVar = new ExpVariable( obj.name(), obj.type() );
            ExpAttrOp attrOpExp = new ExpAttrOp( attr, exprVar );
            cmd = new MCmdSetAttribute( system.state(), attrOpExp, expr );
            system.executeCmd( cmd );

            // set an attribute value in c2
            expr = new ExpConstString( "SUN" );
            obj = system.state().objectByName( "c2" );
            attr = obj.cls().attribute( "name", false );
            exprVar = new ExpVariable( obj.name(), obj.type() );
            attrOpExp = new ExpAttrOp( attr, exprVar );
            cmd = new MCmdSetAttribute( system.state(), attrOpExp, expr );
            system.executeCmd( cmd );


            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }


    

}
