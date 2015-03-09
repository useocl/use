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

package org.tzi.use.uml.sys;

import java.util.Iterator;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;


/**
 * The class <code>LinkTest</code> creates an instance of an
 * association class.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class LinkTest extends TestCase {

    /**
     * Creates instances of associations and association class.
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
     * Deletes Link object between Objects with command
     * <code>MCmdDestroyObjects</code>.
     */
    public void testObjectDestroy() {
        try {
            MSystem system = createModelWithObject();
            
            UseSystemApi api = UseSystemApi.create(system, false);

            assertEquals( "j1", system.state().objectByName( "j1" ).name() );

            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertTrue( system.state().hasLinkBetweenObjects( system.model().getAssociation( "Job" ),
                                                              objects ) );

            api.deleteObject("j1");
            assertNull(system.state().objectByName("j1"));

            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( system.model().getAssociation( "Job" ),
                                                               objects ) );
        } catch ( UseApiException e ) {
            throw ( new Error( e ) );
        }
    }


    /**
     * Deletes link object between Objects with command
     * <code>MCmdDeleteLink</code>.
     */
    public void testLinkDeletion() {
        try {
            MSystem system = createModelWithObject();
            
            UseSystemApi api = UseSystemApi.create(system, false);
            
            MAssociation assoc = system.model().getAssociation("Job");
                   
            api.deleteLink("Job", new String[] {"p1", "c1"});
            
            assertNull(system.state().objectByName("j1"));

            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( assoc, objects ) );
        } catch ( UseApiException e ) {
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
            system.undoLastStatement();
            
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
     * Creates link object between Objects with command
     * <code>MCmdInsertLink</code>.
     */
    public void testCreationLinkObjectWithCmdInsertLink() {
        try {
            MSystem system = createModelWithoutLinkObject();
            
            UseSystemApi api = UseSystemApi.create(system, false);
             
            api.createLink("Job", "p1", "c1");
            
            // Look here first if the test fails. Hint: Check the names. (UniqueNameGenerator)
            assertEquals( "Job1", system.state().objectByName( "Job1" ).name() );

            MObject[] objects = new MObject[2];
            objects[0] = system.state().objectByName( "p1" );
            objects[1] = system.state().objectByName( "c1" );
            assertTrue( system.state().hasLinkBetweenObjects( system.model().getAssociation( "Job" ),
                                                              objects ) );
        } catch ( UseApiException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Tries to create link object with command
     * <code>MCmdCreateObjects</code>.
     */
    public void testCreationLinkObjectWithCmdCreateObjects() {
        MSystem system = null;
        try {
            system = createModelWithoutLinkObject();
            
            UseSystemApi api = UseSystemApi.create(system, false);
            api.createObjects("Job", "j1");

        } catch ( UseApiException e ) {
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
     * Tests that two AssociationClasses cannot be defined between
     * two other objects with the command create-between .
     */
    public void testTwoLinkObjectsBetweenTwoOtherObjects() {
        MSystem system = null;
        try {
            system = createModelWithoutLinkObject();
           
            UseSystemApi api = UseSystemApi.create(system, false);
             
            // Insert the first LinkObject
            api.createLinkObject("Job", "j1", new String[]{"p1", "c1"});
            
            // Insert the second LinkObject
            api.createLinkObject("Job", "j2", new String[]{"p1", "c1"});  
            
        } catch ( UseApiException e ) {
            // wanted.
        } finally {
            // tests if the object j2 does not exists
            assertNull(system.state().objectByName("j2"));
        }
    }

    /**
     * Tests that two AssociationClasses cannot be defined between
     * two other objects with the command insert.
     */
    public void testTwoLinkObjectsBetweenTwoOtherObjectsWithInsert() {
        MSystem system = null;
        MAssociationClass assocClass = null;
        try {
            system = createModelWithoutLinkObject();
            assocClass = system.model().getAssociationClass("Job");
            
            UseSystemApi api = UseSystemApi.create(system, false);
               
            // Insert the first LinkObject
            api.createLink("Job", "p1", "c1");
            
            // Insert the second LinkObject
            api.createLink("Job", "p1", "c1");
            
        } catch ( UseApiException e ) {
            // wanted.
        } finally {
            // for counting the number of Instances between names
            int cnt = 0;

            Iterator<MLink> linkSet = system.state().linksOfAssociation( assocClass ).links().iterator();
            while ( linkSet.hasNext() ) {
                MLink link = linkSet.next();
                Iterator<MObject> it = link.linkedObjects().iterator();
                MObject first = it.next();
                MObject second = it.next();

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
     * Creates a model with two classes and an association class. It
     * creates instances of those as well.
     *
     * @return returns the actual System.
     */
    private MSystem createModelWithObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocClass();
            
            UseSystemApi api = UseSystemApi.create(model, true);
            
            // creation of an object (p1) of the class Person
            api.createObjects("Person", "p1");
            
            // creation of an object (c1) of the class Company
            api.createObjects("Company", "c1");
        

            // creation of an link object (j1) of the association class Job
            api.createLinkObject(
            		"Job", 
            		"j1", 
            		new String[] {"p1", "c1"});
            
            return api.getSystem();
        } catch ( UseApiException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an association class. It
     * creates instances of those as well.
     *
     * @return returns the actual System.
     */
    private MSystem createModelWithoutLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocClass();
            
            UseSystemApi api = UseSystemApi.create(model, false);
            
            // creation of an object (p1) of the class Person
            api.createObjects("Person", "p1");
            
            // creation of an object (c1) of the class Company
            api.createObjects("Company", "c1");
            
            return api.getSystem();
        } catch ( UseApiException e ) {
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

