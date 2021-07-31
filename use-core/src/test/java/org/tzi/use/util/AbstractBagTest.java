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

package org.tzi.use.util;

import junit.framework.TestCase;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;


/**
 * Test comparing Bags with each other.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Fabian Gutsche
 */
public class AbstractBagTest extends TestCase {
    
    private MSystem system;
    private MClass a;
    private MClass b;
    private MClass c;
        
    /**
     * Tests if the equals method returns false if the bags are not the
     * same size. (values are primitiv)
     */
    public void testBagWithoutSameSize() {
        Value[] valuesForBag1 = { IntegerValue.valueOf(1) };
        
        Value[] valuesForBag2 = { IntegerValue.valueOf(0), 
                                  IntegerValue.valueOf(1) };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        Value[] valuesForBag3 = { IntegerValue.valueOf(0), 
                                  IntegerValue.valueOf(1) };
        Value[] valuesForBag4 = { IntegerValue.valueOf(1) };
        
        bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                  valuesForBag3 );
        bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                  valuesForBag4 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
    }

    /**
     * Tests if the equals method returns false if the values of the
     * two bags are not the same. (values are primitiv)
     */
    public void testSameBagSize() {
        Value[] valuesForBag1 = { IntegerValue.valueOf(1), 
                                  IntegerValue.valueOf(1) };
        
        Value[] valuesForBag2 = { IntegerValue.valueOf(0), 
                                  IntegerValue.valueOf(1) };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        Value[] valuesForBag3 = { IntegerValue.valueOf(0), 
                                  IntegerValue.valueOf(1) };
        Value[] valuesForBag4 = { IntegerValue.valueOf(1), 
                                  IntegerValue.valueOf(1) };
        
        bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                  valuesForBag3 );
        bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                  valuesForBag4 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );    
    }
    
    
    /**
     * Tests if the equals method returns true if the values of the
     * two bags are the same. (values are primitiv)
     */
    public void testSameBagSizeWithSameValues() {
        Value[] valuesForBag1 = { IntegerValue.valueOf(1), 
                                  IntegerValue.valueOf(1) };
        
        Value[] valuesForBag2 = { IntegerValue.valueOf(1), 
                                  IntegerValue.valueOf(1) };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag2 );
        
        assertTrue( bagValue1.equals( bagValue2 ) );
        
        Value[] valuesForBag3 = { IntegerValue.valueOf(3), 
                                  IntegerValue.valueOf(0), 
                                  IntegerValue.valueOf(1) };
        Value[] valuesForBag4 = { IntegerValue.valueOf(0),
                                  IntegerValue.valueOf(1), 
                                  IntegerValue.valueOf(3) };
        
        bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                  valuesForBag3 );
        bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                  valuesForBag4 );
        
        assertTrue( bagValue1.equals( bagValue2 ) );
    }







    /**
     * Tests if the equals method returns false if the bags are not the
     * same size. (values are objects)
     */
    public void testBagWithoutSameSizeWithObjects() {
        createModel();
        ObjectValue o1 = new ObjectValue( a, 
                                          system.state().objectByName("a1") );        
        Value[] valuesForBag1 = { o1 };
        
        ObjectValue ov1 = new ObjectValue( a, 
                                           system.state().objectByName("a1") );
        ObjectValue ov2 = new ObjectValue( b, 
                                           system.state().objectByName("b1") );
        Value[] valuesForBag2 = { ov1, ov2 };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        o1 = new ObjectValue( a, system.state().objectByName("a1") );
        
        ObjectValue o2 = new ObjectValue( a, system.state().objectByName("b1") );
        Value[] valuesForBag3 = { o1, o2 };
        
        ov1 = new ObjectValue( a, system.state().objectByName("a1") );
        Value[] valuesForBag4 = { ov1 };
        
        bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag3 );
        bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag4 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
    }


    /**
     * Tests if the equals method returns false if the values of the
     * two bags are not the same. (values are objects)
     */
    public void testSameBagSizeObjects() {
        createModel();
        ObjectValue o1 = new ObjectValue( a, 
                                          system.state().objectByName("a1") );        
        ObjectValue o2 = new ObjectValue( a, 
                                          system.state().objectByName("a1") );
        Value[] valuesForBag1 = { o1, o2 };
        
        ObjectValue ov1 = new ObjectValue( a, 
                                           system.state().objectByName("a1") );
        ObjectValue ov2 = new ObjectValue( b, 
                                           system.state().objectByName("b1") );
        Value[] valuesForBag2 = { ov1, ov2 };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        o1 = new ObjectValue( a, 
                              system.state().objectByName("a1") );        
        o2 = new ObjectValue( b, 
                              system.state().objectByName("b1") );
        Value[] valuesForBag3 = { o1, o2 };
        
        ov1 = new ObjectValue( a, 
                               system.state().objectByName("a1") );
        ov2 = new ObjectValue( a, 
                               system.state().objectByName("a1") );
        Value[] valuesForBag4 = { ov1, ov2 };
        
        bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag3 );
        bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag4 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
    }


    /**
     * Tests if the equals method returns true if the values of the
     * two bags are the same. (values are objects)
     */
    public void testSameBagSizeWithObjectsValuesAreTheSame() {
        createModel();
        ObjectValue o1 = new ObjectValue( a, 
                                          system.state().objectByName("a1") );        
        ObjectValue o2 = new ObjectValue( a, 
                                          system.state().objectByName("a1") );
        Value[] valuesForBag1 = { o1, o2 };
        
        ObjectValue ov1 = new ObjectValue( a, 
                                           system.state().objectByName("a1") );
        ObjectValue ov2 = new ObjectValue( b, 
                                           system.state().objectByName("a1") );
        Value[] valuesForBag2 = { ov1, ov2 };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag2 );
        
        assertTrue( bagValue1.equals( bagValue2 ) );
        
        o1 = new ObjectValue( a, 
                              system.state().objectByName("a1") );        
        o2 = new ObjectValue( b, 
                              system.state().objectByName("b1") );
        ObjectValue o3 = new ObjectValue( c, 
                                          system.state().objectByName("c1") );
        Value[] valuesForBag3 = { o1, o2, o3 };
        
        ov1 = new ObjectValue( c, 
                               system.state().objectByName("c1") );
        ov2 = new ObjectValue( a, 
                               system.state().objectByName("a1") );
        ObjectValue ov3 = new ObjectValue( b, 
                                           system.state().objectByName("b1") );
        Value[] valuesForBag4 = { ov1, ov2, ov3 };
        
        bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag3 );
        bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag4 );
        
        assertTrue( bagValue1.equals( bagValue2 ) );
    }



    /**
     * Creates the model and system every test is working with.
     */
    private void createModel() {
        UseModelApi mApi = new UseModelApi("Test");
        
        try {
            a = mApi.createClass("A", false);
            b = mApi.createClass("B", false);
            c = mApi.createClass("C", false);
            
            UseSystemApi sApi = UseSystemApi.create(mApi.getModel(), false);
            
            sApi.createObjectsEx(a, "a1");
            sApi.createObjectsEx(b, "b1");
            sApi.createObjectsEx(c, "c1");
            
            system = sApi.getSystem();
        } catch ( UseApiException ex ) {
            fail( ex.getMessage() );
        }
    }
}
