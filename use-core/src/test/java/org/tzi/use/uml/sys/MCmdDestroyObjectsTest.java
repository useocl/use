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

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;


/**
 * Test the destruction of objects.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Fabian Gutsche
 * @see     MCmdDestroyObjects
 */

public class MCmdDestroyObjectsTest extends TestCase {
    private UseModelApi modelApi;
    private UseSystemApi systemApi;

    private MClass a;
    private MClass b;
    private MClass c;
        
    /**
     * Creates the model and system every test is working with.
     */
    protected void setUp() {
        modelApi = new UseModelApi("Test");
                
        try {
            a = modelApi.createClass("A", false);
            b = modelApi.createClass("B", false);
            c = modelApi.createClass("C", false);
        } catch ( UseApiException ex ) {
            fail( ex.getMessage() );
        }
        
        systemApi = UseSystemApi.create(modelApi.getModel(), false);
    }
    
    /**
     * Test destruction of a single object.
     */
    public void testDestroySingleObject() {   
        try{
        	// create one object of class a
        	systemApi.createObjectEx(a, "a1");
        	
            // expect one object of class a       
            assertEquals(1, systemApi.getSystem().state().objectsOfClass(a).size());
           
            // destroy all objects of class a
            systemApi.deleteObject("a1");
           
            // expect no objects of class a
            assertEquals(0, systemApi.getSystem().state().objectsOfClass(a).size());

        } catch ( UseApiException ex ) {
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
        	systemApi.createObjectsEx(a, "a1", "a2");
        	// create two objects of class `B'
        	systemApi.createObjectsEx(b, "b1", "b2");
        	// create two objects of class `C'
        	systemApi.createObjectsEx(c, "c1", "c2");
        	
            // expect two objects of class a
            assertEquals(2, systemApi.getSystem().state().objectsOfClass(a).size());
            // expect two objects of class b
            assertEquals(2, systemApi.getSystem().state().objectsOfClass(b).size());
            // expect two objects of class c
            assertEquals(2, systemApi.getSystem().state().objectsOfClass(c).size());
            
            // Destruction of all objects!
            Value objectCollection = systemApi.evaluate("A.allInstances()->union(B.allInstances())->union(C.allInstances())");
            
            assertTrue(objectCollection.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID));
            CollectionValue colVal = (CollectionValue)objectCollection;
            MObject[] objects = new MObject[colVal.size()];
            int i = 0;
            for (Value v : colVal) {
            	objects[i] = ((ObjectValue)v).value();
            	++i;
            }
            
            systemApi.deleteObjectsEx(objects);
          
            // expect no objects of class a
            assertEquals(0, systemApi.getSystem().state().objectsOfClass(a).size());
            // expect no objects of class b
            assertEquals(0, systemApi.getSystem().state().objectsOfClass(b).size());
            // expect no objects of class c
            assertEquals(0, systemApi.getSystem().state().objectsOfClass(c).size());

        } catch ( UseApiException ex ) {
            fail( ex.getMessage() );
        }
    }
}
