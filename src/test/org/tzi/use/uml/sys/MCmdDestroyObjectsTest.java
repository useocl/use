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
import java.util.List;

import junit.framework.TestCase;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateObjects;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.cmd.CommandFailedException;


/**
 * Test the destruction of objects.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Fabian Gutsche
 * @see     MCmdDestroyObjects
 */

public class MCmdDestroyObjectsTest extends TestCase {
    private ModelFactory mf;
    private MModel model;
    private MSystem system;
    private MClass a;
    private MClass b;
    private MClass c;
    
    /**
     * Creates the model and system every test is working with.
     */
    protected void setUp() {
        mf = new ModelFactory();
        model = mf.createModel("Test");
        system = new MSystem( model );
        try {
            a = mf.createClass("A", false);
            b = mf.createClass("B", false);
            c = mf.createClass("C", false);
            
            model.addClass(a);
            model.addClass(b);
            model.addClass(c);
        } catch ( MInvalidModelException ex ) {
            fail( ex.getMessage() );
        }
    }
    
    /**
     * Test destruction of a single object.
     */
    public void testDestroySingleObject() {   
        try{
            List names = new ArrayList();
            names.add( "a1" );        
            MCmd createObjects = new MCmdCreateObjects( system.state(), names, 
                                                       TypeFactory.mkObjectType( a ) );
            createObjects.execute();
            
            // aspect one object of class a
            assertEquals( 1, system.state().objectsOfClass( a ).size() );
            
            Expression[] exprs = { new ExpAllInstances( TypeFactory.mkObjectType( a ) ) };
            
            MCmd destroyObjects = new MCmdDestroyObjects( system.state(), exprs );
            destroyObjects.execute();

            // aspect no objects of class a
            assertEquals( 0, system.state().objectsOfClass( a ).size() );

        } catch ( CommandFailedException ex ) {
            fail( ex.getMessage() );
        } catch ( ExpInvalidException ex ) {
            fail( ex.getMessage() );
        }
    }


    /**
     * Tests the destruction of six objects. Two objects are allways 
     * from the same type.
     */
    public void testDestroyObjectsWithDifferentTypes() {
        try{
            List names = new ArrayList();
            // create two objects of class `A'
            names.add( "a1" );        
            names.add( "a2" );        
            MCmd createObjects = new MCmdCreateObjects( system.state(), names, 
                                                       TypeFactory.mkObjectType( a ) );
            createObjects.execute();

            // create two objects of class `B'
            names = new ArrayList();
            names.add( "b1" );        
            names.add( "b2" );        
            createObjects = new MCmdCreateObjects( system.state(), names, 
                                                   TypeFactory.mkObjectType( b ) );
            createObjects.execute();
            
            // create two objects of class `C'
            names = new ArrayList();
            names.add( "c1" );        
            names.add( "c2" );        
            createObjects = new MCmdCreateObjects( system.state(), names, 
                                                   TypeFactory.mkObjectType( c ) );
            createObjects.execute();

            // aspect two objects of class a
            assertEquals( 2, system.state().objectsOfClass( a ).size() );
            // aspect two objects of class b
            assertEquals( 2, system.state().objectsOfClass( b ).size() );
            // aspect two objects of class c
            assertEquals( 2, system.state().objectsOfClass( c ).size() );
            

            // Destruction of all objects!
            Expression[] exprs = { new ExpAllInstances( TypeFactory.mkObjectType( a ) ),
                                   new ExpAllInstances( TypeFactory.mkObjectType( b ) ),
                                   new ExpAllInstances( TypeFactory.mkObjectType( c ) ) };
            
            MCmd destroyObjects = new MCmdDestroyObjects( system.state(), exprs );
            destroyObjects.execute();

            // aspect no objects of class a
            assertEquals( 0, system.state().objectsOfClass( a ).size() );
            // aspect no objects of class b
            assertEquals( 0, system.state().objectsOfClass( b ).size() );
            // aspect no objects of class c
            assertEquals( 0, system.state().objectsOfClass( c ).size() );

        } catch ( CommandFailedException ex ) {
            fail( ex.getMessage() );
        } catch ( ExpInvalidException ex ) {
            fail( ex.getMessage() );
        }
    }
}
