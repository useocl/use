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

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * The class <code>LinkTest</code> creates an instance of an
 * associationclass.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class LinkTest extends TestCase {

    /**
     * Creates instances of associations and associationclass.
     */
    public void testLinkCreation() {
        MSystem system = createModelWithObject();

        // tests weather or not the objects exists
        assertEquals( "p1", system.state().objectByName( "p1" ).name() );
        assertEquals( "c1", system.state().objectByName( "c1" ).name() );
        assertEquals( "j1", system.state().objectByName( "j1" ).name() );

        // tests if the link between p1 (Person) and c1 (Company) exists
        MObject[] objects = new MObject[2];
        objects[0] = system.state().objectByName( "p1" );
        objects[1] = system.state().objectByName( "c1" );
        assertTrue( system.state().hasLinkBetweenObjects( system.model()
                                                          .getAssociation( "Job" ),
                                                          objects ) );
        // tests if the link between p1 (Person) and j1 (Job) doesn't exist
        objects[0] = system.state().objectByName( "p1" );
        objects[1] = system.state().objectByName( "j1" );
        assertFalse( system.state().hasLinkBetweenObjects( system.model()
                                                           .getAssociation( "Job" ),
                                                           objects ) );

    }

    /**
     * Deletes Linkobject between Objects with command
     * <code>MCmdDestroyObjects</code>.
     */
    public void testObjectDestroy() {
        try {
            MSystem system = createModelWithObject();

            assertEquals( "j1", system.state().objectByName( "j1" ).name() );

            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertTrue( system.state().hasLinkBetweenObjects( system.model().getAssociation( "Job" ),
                                                              objects ) );


            List names = new ArrayList();
            names.add( "j1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MCmd cmd = new MCmdDestroyObjects( system.state(), exprs );
            system.executeCmd( cmd );

            assertEquals( null, system.state().objectByName( "j1" ) );

            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( system.model().getAssociation( "Job" ),
                                                               objects ) );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }


    /**
     * Deletes Linkobject between Objects with command
     * <code>MCmdDeleteLink</code>.
     */
    public void testLinkDeletion() {
        try {
            MSystem system = createModelWithObject();
            List names = new ArrayList();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MAssociation assoc = system.model().getAssociation( "Job" );
            MCmd cmd = new MCmdDeleteLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            assertEquals( null, system.state().objectByName( "j1" ) );

            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( assoc, objects ) );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }

    public void testLinkCreationAndUndo() {
        try {
            MSystem system = createModelWithObject();

            // tests weather or not the objects exists
            assertEquals( "j1", system.state().objectByName( "j1" ).name() );

            // tests if the link between p1 (Person) and c1 (Company) exists
            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertTrue( system.state().hasLinkBetweenObjects( system.model()
                                                              .getAssociation( "Job" ),
                                                              objects ) );

            // Undo the last command (insertion of j1)
            system.undoCmd();

            // tests if the object j1 does not exists
            assertEquals( null, system.state().objectByName( "j1" ) );

            // tests if the link between p1 (Person) and c1 (Company) exists
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( system.model()
                                                               .getAssociation( "Job" ),
                                                               objects ) );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }


    /**
     * Creates Linkobject between Objects with command
     * <code>MCmdInsertLink</code>.
     */
    public void testCreationLinkObjectWithCmdInsertLink() {
        try {
            MSystem system = createModelWithoutLinkObject();
            List names = new ArrayList();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MAssociation assoc = system.model().getAssociation( "Job" );
            MCmd cmd = new MCmdInsertLink( system.state(), exprs, assoc );
            system.executeCmd( cmd );

            // Look here first if the test fails. Hint: Check the names. (UniqueNameGenerator)
            assertEquals( "Job1", system.state().objectByName( "Job1" ).name() );

            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertTrue( system.state().hasLinkBetweenObjects( system.model().getAssociation( "Job" ),
                                                              objects ) );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Tries to create Linkobject with command
     * <code>MCmdCreateObjects</code>.
     */
    public void testCreationLinkObjectWithCmdCreateObjects() {
        MSystem system = null;
        try {
            system = createModelWithoutLinkObject();
            List names = new ArrayList();
            names.add( "j1" );
            ObjectType type = TypeFactory.mkObjectType( system.model().getClass( "Job" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );


        } catch ( MSystemException e ) {
            // tests if the object j1 does not exists
            assertEquals( null, system.state().objectByName( "j1" ) );

            // tests if the link between p1 (Person) and c1 (Company) exists
            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( system.model()
                                                               .getAssociation( "Job" ),
                                                               objects ) );
        }
    }

    /**
     * Tests that two AssociationClasses cannot be defiened between
     * two other objects with the command create-between .
     */
    public void testTwoLinkObjectsBetweenTwoOtherObjects() {
        MSystem system = null;
        try {
            system = createModelWithoutLinkObject();
            List names = new ArrayList();
            names.add( "p1" );
            names.add( "c1" );
            MAssociationClass assocClass = system.model().getAssociationClass( "Job" );
            // Insert the first LinkObject
            MCmd cmd = new MCmdCreateInsertObjects( system.state(), "j1", assocClass, names );
            system.executeCmd( cmd );

            // Insert the second LinkObject
            cmd = new MCmdCreateInsertObjects( system.state(), "j2", assocClass, names );
            system.executeCmd( cmd );
        } catch ( MSystemException e ) {
            // wanted.
        } finally {
            // tests if the object j2 does not exists
            assertEquals( null, system.state().objectByName( "j2" ) );
        }
    }

    /**
     * Tests that two AssociationClasses cannot be defiened between
     * two other objects with the command insert.
     */
    public void testTwoLinkObjectsBetweenTwoOtherObjectsWithInsert() {
        MSystem system = null;
        MAssociationClass assocClass = null;
        try {
            system = createModelWithoutLinkObject();
            List names = new ArrayList();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            assocClass = system.model().getAssociationClass( "Job" );
            // Insert the first LinkObject
            MCmd cmd = new MCmdInsertLink( system.state(), exprs, assocClass );
            system.executeCmd( cmd );

            // Insert the second LinkObject
            cmd = new MCmdInsertLink( system.state(), exprs, assocClass );
            system.executeCmd( cmd );
        } catch ( MSystemException e ) {
            // wanted.
        } finally {
            // for counting the number of Instances between names
            int cnt = 0;

            Iterator linkSet = system.state().linksOfAssociation( assocClass ).links().iterator();
            while ( linkSet.hasNext() ) {
                MLink link = ( MLink ) linkSet.next();
                Iterator it = link.linkedObjects().iterator();
                MObject first = ( MObject ) it.next();
                MObject second = ( MObject ) it.next();

                if ( ( first.name().equals( "p1" ) && second.name().equals( "c1" ) )
                        || ( first.name().equals( "c1" ) && second.name().equals( "p1" ) ) ) {
                    cnt++;
                }
            }

            // tests if the object j2 does not exists
            assertEquals( 1, cnt );
        }
    }


    /**
     * Creates a model with two classes and an associationclass. It
     * creates instances of those as well.
     *
     * @return returns the actual System.
     */
    private MSystem createModelWithObject() {
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

            // creation of an linkobject (j1) of the associationclass Job
            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            MAssociationClass assocClass = model.getAssociationClass( "Job" );
            cmd = new MCmdCreateInsertObjects( system.state(), "j1", assocClass, names );
            system.executeCmd( cmd );

            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an associationclass. It
     * creates instances of those as well.
     *
     * @return returns the actual System.
     */
    private MSystem createModelWithoutLinkObject() {
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

            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }


    /**
     * Entry point
     */
    public static void main( String[] args ) {
        junit.textui.TestRunner.run( new TestSuite( LinkTest.class ) );
    }

}

