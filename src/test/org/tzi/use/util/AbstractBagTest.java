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

import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.sys.MCmdCreateObjects;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MClass;
import java.util.List;
import org.tzi.use.util.cmd.CommandFailedException;
import org.tzi.use.uml.ocl.value.ObjectValue;
import java.util.ArrayList;

/**
 * Test comparing Bags with each other.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Fabian Gutsche
 */
public class AbstractBagTest extends TestCase {
    private ModelFactory mf;
    private MModel model;
    private MSystem system;
    private MClass a;
    private MClass b;
    private MClass c;
        
    /**
     * Tests if the equals method returns false if the bags are not the
     * same size. (values are primitiv)
     */
    public void testBagWithoutSameSize() {
        Value[] valuesForBag1 = { new IntegerValue(1) };
        
        Value[] valuesForBag2 = { new IntegerValue(0), 
                                  new IntegerValue(1) };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        Value[] valuesForBag3 = { new IntegerValue(0), 
                                  new IntegerValue(1) };
        Value[] valuesForBag4 = { new IntegerValue(1) };
        
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
        Value[] valuesForBag1 = { new IntegerValue(1), 
                                  new IntegerValue(1) };
        
        Value[] valuesForBag2 = { new IntegerValue(0), 
                                  new IntegerValue(1) };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        Value[] valuesForBag3 = { new IntegerValue(0), 
                                  new IntegerValue(1) };
        Value[] valuesForBag4 = { new IntegerValue(1), 
                                  new IntegerValue(1) };
        
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
        Value[] valuesForBag1 = { new IntegerValue(1), 
                                  new IntegerValue(1) };
        
        Value[] valuesForBag2 = { new IntegerValue(1), 
                                  new IntegerValue(1) };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkInteger(),
                                           valuesForBag2 );
        
        assertTrue( bagValue1.equals( bagValue2 ) );
        
        Value[] valuesForBag3 = { new IntegerValue(3), 
                                  new IntegerValue(0), 
                                  new IntegerValue(1) };
        Value[] valuesForBag4 = { new IntegerValue(0),
                                  new IntegerValue(1), 
                                  new IntegerValue(3) };
        
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
        ObjectValue o1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("a1") );        
        Value[] valuesForBag1 = { o1 };
        
        ObjectValue ov1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                           system.state().objectByName("a1") );
        ObjectValue ov2 = new ObjectValue( TypeFactory.mkObjectType(b), 
                                           system.state().objectByName("b1") );
        Value[] valuesForBag2 = { ov1, ov2 };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        o1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("a1") );        
        ObjectValue o2 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("b1") );
        Value[] valuesForBag3 = { o1, o2 };
        
        ov1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                           system.state().objectByName("a1") );
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
        ObjectValue o1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("a1") );        
        ObjectValue o2 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("a1") );
        Value[] valuesForBag1 = { o1, o2 };
        
        ObjectValue ov1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                           system.state().objectByName("a1") );
        ObjectValue ov2 = new ObjectValue( TypeFactory.mkObjectType(b), 
                                           system.state().objectByName("b1") );
        Value[] valuesForBag2 = { ov1, ov2 };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag2 );
        
        assertFalse( bagValue1.equals( bagValue2 ) );
        
        o1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                              system.state().objectByName("a1") );        
        o2 = new ObjectValue( TypeFactory.mkObjectType(b), 
                              system.state().objectByName("b1") );
        Value[] valuesForBag3 = { o1, o2 };
        
        ov1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                               system.state().objectByName("a1") );
        ov2 = new ObjectValue( TypeFactory.mkObjectType(a), 
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
        ObjectValue o1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("a1") );        
        ObjectValue o2 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                          system.state().objectByName("a1") );
        Value[] valuesForBag1 = { o1, o2 };
        
        ObjectValue ov1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                                           system.state().objectByName("a1") );
        ObjectValue ov2 = new ObjectValue( TypeFactory.mkObjectType(b), 
                                           system.state().objectByName("a1") );
        Value[] valuesForBag2 = { ov1, ov2 };
        
        BagValue bagValue1 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag1 );
        BagValue bagValue2 = new BagValue( TypeFactory.mkOclAny(),
                                           valuesForBag2 );
        
        assertTrue( bagValue1.equals( bagValue2 ) );
        
        o1 = new ObjectValue( TypeFactory.mkObjectType(a), 
                              system.state().objectByName("a1") );        
        o2 = new ObjectValue( TypeFactory.mkObjectType(b), 
                              system.state().objectByName("b1") );
        ObjectValue o3 = new ObjectValue( TypeFactory.mkObjectType(c), 
                                          system.state().objectByName("c1") );
        Value[] valuesForBag3 = { o1, o2, o3 };
        
        ov1 = new ObjectValue( TypeFactory.mkObjectType(c), 
                               system.state().objectByName("c1") );
        ov2 = new ObjectValue( TypeFactory.mkObjectType(a), 
                               system.state().objectByName("a1") );
        ObjectValue ov3 = new ObjectValue( TypeFactory.mkObjectType(b), 
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

            List<String> names = new ArrayList<String>();
            names.add( "a1" );        
            MCmd createObjects = new MCmdCreateObjects( system.state(), names, 
                                                        TypeFactory.mkObjectType( a ) );
            createObjects.execute();
            names.clear();
            names.add( "b1" );        
            createObjects = new MCmdCreateObjects( system.state(), names, 
                                                   TypeFactory.mkObjectType( b ) );
            createObjects.execute();
            names.clear();
            names.add( "c1" );        
            createObjects = new MCmdCreateObjects( system.state(), names, 
                                                   TypeFactory.mkObjectType( c ) );
            createObjects.execute();
        } catch ( MInvalidModelException ex ) {
            fail( ex.getMessage() );
        } catch ( CommandFailedException ex ) {
            fail( ex.getMessage() );
        }
    }
}
