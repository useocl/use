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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;


/**
 * The class <code>DeletionTest</code> tests the deletion of a link
 * object.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class DeletionTest extends TestCase {

    /**
     * This test only tests the model which is used in this class.
     */
    public void testModel() {
        ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
    }

    /**
     * Tests if a linkobject is deleted with the command 'delete', which
     * is normaly for deleting links.
     */
    public void testDeleteLinkObject() {
        try {
            MSystem system = ObjectCreation.getInstance()
                    .createModelWithObjectsAndLinkObject();
            List<String> names = new ArrayList<String>();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            
            int i = 0;
            for (String name : names) {
                MObject obj =  system.state().objectByName( name ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MAssociation assoc = system.model().getAssociation( "Job" );
            MCmd cmd = new MCmdDeleteLink( system.state(), exprs, assoc );

            system.executeCmd( cmd );

            assertNull( system.state().objectByName( "j1" ) );
            assertEquals( "p1", system.state().objectByName( "p1" ).name() );
            assertEquals( "c1", system.state().objectByName( "c1" ).name() );
        } catch ( MSystemException e ) {
            fail( e.getMessage() );
        }
    }

    /**
     * Tests if a linkobject is deleted with the command 'destroy',
     * which is normally used for deleting links.
     */
    public void testDestroyLinkObject() {
        try {
            MSystem system = ObjectCreation.getInstance().createModelWithObjectsAndLinkObject();
            List<String> names = new ArrayList<String>();
            names.add( "j1" );
            Expression[] exprs = new Expression[names.size()];
            
            int i = 0;
            for(String name : names) {
                MObject obj =  system.state().objectByName( name ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MCmd cmd = new MCmdDestroyObjects( system.state(), exprs );

            system.executeCmd( cmd );


            assertNull( system.state().objectByName( "j1" ) );

            MAssociation assoc = system.model().getAssociation( "Job" );
            MObject[] objects = new MObject[2];
            objects[0]=system.state().objectByName( "p1" );
            objects[1]=system.state().objectByName( "c1" );
            assertFalse( system.state().hasLinkBetweenObjects( assoc, objects ) );

            assertEquals( "p1", system.state().objectByName( "p1" ).name() );
            assertEquals( "c1", system.state().objectByName( "c1" ).name() );
        } catch ( MSystemException e ) {
            fail( e.getMessage() );
        }
    }

    /**
     * Tests if an object, which is connected to a linkobject,
     * is deleted with the command 'destroy'.
     */
    public void testDestroyConnectedObject() {
        try {
            MSystem system = ObjectCreation.getInstance()
                    .createModelWithObjectsAndLinkObject();
            List<String> names = new ArrayList<String>();
            names.add( "p1" );
            Expression[] exprs = new Expression[names.size()];
            
            int i = 0;
            for (String name : names) {
                MObject obj =  system.state().objectByName( name ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            MCmd cmd = new MCmdDestroyObjects( system.state(), exprs );

            system.executeCmd( cmd );

            assertNull( system.state().objectByName( "j1" ) );
            assertNull( system.state().objectByName( "p1" ) );
            assertEquals( "c1", system.state().objectByName( "c1" ).name() );
        } catch ( MSystemException e ) {
            fail( e.getMessage() );
        }
    }

    /**
     * Entry point
     */
    public static void main( String[] args ) {
        junit.textui.TestRunner.run( new TestSuite( DeletionTest.class ) );
    }


}
