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

import junit.framework.TestCase;

import org.tzi.use.SystemManipulator;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;


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
    private SystemManipulator systemManipulator;
    
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
        systemManipulator = new SystemManipulator(system);
    }
    
    /**
     * Test destruction of a single object.
     */
    public void testDestroySingleObject() {   
        try{
        	// create one object of class a
        	systemManipulator.createObjects(a, "a1");
        	
            // expect one object of class a       
            assertEquals(1, system.state().objectsOfClass(a).size());
           
            // destroy all objects of class a
            systemManipulator.destroyObjects(new ExpAllInstances(a.type()));
           
            // expect no objects of class a
            assertEquals(0, system.state().objectsOfClass(a).size());

        } catch ( MSystemException ex ) {
            fail( ex.getMessage() );
        } catch ( ExpInvalidException ex ) {
            fail( ex.getMessage() );
        }
    }


    /**
     * Tests the destruction of six objects. Two objects are always 
     * from the same type.
     */
    public void testDestroyObjectsWithDifferentTypes() {
        try{
        	// create two objects of class `A'
        	systemManipulator.createObjects(a, "a1", "a2");
        	// create two objects of class `B'
        	systemManipulator.createObjects(b, "b1", "b2");
        	// create two objects of class `C'
        	systemManipulator.createObjects(c, "c1", "c2");
        	
            // expect two objects of class a
            assertEquals(2, system.state().objectsOfClass(a).size());
            // expect two objects of class b
            assertEquals(2, system.state().objectsOfClass(b).size());
            // expect two objects of class c
            assertEquals(2, system.state().objectsOfClass(c).size());
            
            // Destruction of all objects!
            systemManipulator.destroyObjects(
            		new ExpAllInstances(a.type()),
            		new ExpAllInstances(b.type()),
            		new ExpAllInstances(c.type()));
          
            // expect no objects of class a
            assertEquals(0, system.state().objectsOfClass(a).size());
            // expect no objects of class b
            assertEquals(0, system.state().objectsOfClass(b).size());
            // expect no objects of class c
            assertEquals(0, system.state().objectsOfClass(c).size());

        } catch ( MSystemException ex ) {
            fail( ex.getMessage() );
        } catch ( ExpInvalidException ex ) {
            fail( ex.getMessage() );
        }
    }
}
