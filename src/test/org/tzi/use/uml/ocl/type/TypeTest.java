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

package org.tzi.use.uml.ocl.type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.gargoylesoftware.base.testing.EqualsTester;
import junit.framework.TestCase;

/**
 * Test Type classes.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     Type
 */

public class TypeTest extends TestCase {
    private EnumType enum1;
    private EnumType enum2;

    protected void setUp() {
        final String[] LITERALS = { "a", "b", "c" };
        enum1 = TypeFactory.mkEnum("E", Arrays.asList(LITERALS));
        enum2 = TypeFactory.mkEnum("E", Arrays.asList(LITERALS));
    }

    public void testEquality() {
        assertTrue(TypeFactory.mkInteger().equals(TypeFactory.mkInteger()));

        assertTrue(
                   TypeFactory.mkSet(TypeFactory.mkInteger()).equals(
                                                                     TypeFactory.mkSet(TypeFactory.mkInteger())));
        assertTrue(enum1.equals(enum2));
    }

    public void testSubtype() {
        assertTrue(
                   "Integer < Integer",
                   TypeFactory.mkInteger().isSubtypeOf(TypeFactory.mkInteger()));
        assertTrue(
                   "Integer < Real",
                   TypeFactory.mkInteger().isSubtypeOf(TypeFactory.mkReal()));
        assertTrue(
                   "Set(Integer) < Set(Integer)",
                   TypeFactory.mkSet(TypeFactory.mkInteger())
                   .isSubtypeOf(TypeFactory.mkSet(TypeFactory.mkInteger())));
        assertTrue(
                   "Set(Integer) < Set(Real)",
                   TypeFactory.mkSet(TypeFactory.mkInteger())
                   .isSubtypeOf(TypeFactory.mkSet(TypeFactory.mkReal())));
        assertTrue(
                   "Set(Integer) < Collection(Integer)",
                   TypeFactory.mkSet(TypeFactory.mkInteger())
                   .isSubtypeOf(TypeFactory.mkCollection(TypeFactory.mkInteger())));
        assertFalse(
                    "Set(Integer) < Set(String)",
                    TypeFactory.mkSet(TypeFactory.mkInteger())
                    .isSubtypeOf(TypeFactory.mkSet(TypeFactory.mkString())));
        assertFalse(
                    "Collection(Integer) < Set(Integer)",
                    TypeFactory.mkCollection(TypeFactory.mkInteger())
                    .isSubtypeOf(TypeFactory.mkSet(TypeFactory.mkInteger())));
        assertFalse(
                    "Collection(Integer) < Bag(Integer)",
                    TypeFactory.mkCollection(TypeFactory.mkInteger())
                    .isSubtypeOf(TypeFactory.mkBag(TypeFactory.mkInteger())));
        assertFalse(
                    "Bag(Integer) < Set(Integer)",
                    TypeFactory.mkBag(TypeFactory.mkInteger())
                    .isSubtypeOf(TypeFactory.mkSet(TypeFactory.mkInteger())));
        assertTrue(
                   "Bag(Integer) < Bag(Integer)",
                   TypeFactory.mkBag(TypeFactory.mkInteger())
                   .isSubtypeOf(TypeFactory.mkBag(TypeFactory.mkInteger())));
        assertTrue(
                   "Bag(Integer) < Collection(Integer)",
                   TypeFactory.mkBag(TypeFactory.mkInteger())
                   .isSubtypeOf(TypeFactory.mkCollection(TypeFactory.mkInteger())));
        assertTrue(
                   "Set(Set(Integer)) < Collection(Collection(Integer))",
                   TypeFactory.mkSet(TypeFactory.mkSet(TypeFactory.mkInteger()))
                   .isSubtypeOf(TypeFactory.mkCollection(TypeFactory
                                                         .mkCollection(TypeFactory.mkInteger()))));
        assertTrue(
                   "Sequence(Integer) < Sequence(Integer)",
                   TypeFactory.mkSequence(TypeFactory.mkInteger())
                   .isSubtypeOf(TypeFactory.mkSequence(TypeFactory.mkInteger())));
    }

    public void testSupertype() {
        assertEquals(
                     "OclAny.allSupertypes()",
                     mkSet(new Object[] { TypeFactory.mkOclAny()}),
                     TypeFactory.mkOclAny().allSupertypes());
        assertEquals(
                     "Boolean.allSupertypes()",
                     mkSet(new Object[] { TypeFactory.mkBoolean(), TypeFactory.mkOclAny()}),
                     TypeFactory.mkBoolean().allSupertypes());
        assertEquals(
                     "Integer.allSupertypes()",
                     mkSet(
                           new Object[] {
                               TypeFactory.mkInteger(),
                               TypeFactory.mkReal(),
                               TypeFactory.mkOclAny()}),
                     TypeFactory.mkInteger().allSupertypes());
        assertEquals(
                     "Real.allSupertypes()",
                     mkSet(new Object[] { TypeFactory.mkReal(), TypeFactory.mkOclAny()}),
                     TypeFactory.mkReal().allSupertypes());
        assertEquals(
                     "String.allSupertypes()",
                     mkSet(new Object[] { TypeFactory.mkString(), TypeFactory.mkOclAny()}),
                     TypeFactory.mkString().allSupertypes());
        assertEquals(
                     "Enum.allSupertypes()",
                     mkSet(new Object[] { enum1, TypeFactory.mkOclAny()}),
                     enum1.allSupertypes());
        assertEquals(
                     "Collection(Boolean).allSupertypes()",
                     mkSet(
                           new Object[] {
                               TypeFactory.mkCollection(TypeFactory.mkBoolean()),
                               TypeFactory.mkCollection(TypeFactory.mkOclAny()),
                           }),
                     TypeFactory.mkCollection(TypeFactory.mkBoolean()).allSupertypes());
        assertEquals(
                     "Collection(Integer).allSupertypes()",
                     mkSet(
                           new Object[] {
                               TypeFactory.mkCollection(TypeFactory.mkInteger()),
                               TypeFactory.mkCollection(TypeFactory.mkReal()),
                               TypeFactory.mkCollection(TypeFactory.mkOclAny()),
                           }),
                     TypeFactory.mkCollection(TypeFactory.mkInteger()).allSupertypes());
        assertEquals(
                     "Collection(Collection(Real))).allSupertypes()",
                     mkSet(new Object[] {
                               TypeFactory.mkCollection(
                                   TypeFactory.mkCollection(TypeFactory.mkReal())),
                               TypeFactory.mkCollection(
                                   TypeFactory.mkCollection(TypeFactory.mkOclAny())),
                           }), 
                     TypeFactory.mkCollection(TypeFactory.mkCollection(
                         TypeFactory.mkReal())).allSupertypes());

        assertEquals(
                     "Set(Integer).allSupertypes()",
                     mkSet(
                           new Object[] {
                               TypeFactory.mkCollection(TypeFactory.mkInteger()),
                               TypeFactory.mkCollection(TypeFactory.mkReal()),
                               TypeFactory.mkCollection(TypeFactory.mkOclAny()),
                               TypeFactory.mkSet(TypeFactory.mkInteger()),
                               TypeFactory.mkSet(TypeFactory.mkReal()),
                               TypeFactory.mkSet(TypeFactory.mkOclAny()),
                           }),
                     TypeFactory.mkSet(TypeFactory.mkInteger()).allSupertypes());
        assertEquals(
                     "Sequence(Integer).allSupertypes()",
                     mkSet(
                           new Object[] {
                               TypeFactory.mkCollection(TypeFactory.mkInteger()),
                               TypeFactory.mkCollection(TypeFactory.mkReal()),
                               TypeFactory.mkCollection(TypeFactory.mkOclAny()),
                               TypeFactory.mkSequence(TypeFactory.mkInteger()),
                               TypeFactory.mkSequence(TypeFactory.mkReal()),
                               TypeFactory.mkSequence(TypeFactory.mkOclAny()),
                           }),
                     TypeFactory.mkSequence(TypeFactory.mkInteger()).allSupertypes());
        assertEquals(
                     "Bag(Integer).allSupertypes()",
                     mkSet(
                           new Object[] {
                               TypeFactory.mkCollection(TypeFactory.mkInteger()),
                               TypeFactory.mkCollection(TypeFactory.mkReal()),
                               TypeFactory.mkCollection(TypeFactory.mkOclAny()),
                               TypeFactory.mkBag(TypeFactory.mkInteger()),
                               TypeFactory.mkBag(TypeFactory.mkReal()),
                               TypeFactory.mkBag(TypeFactory.mkOclAny()),
                           }),
                     TypeFactory.mkBag(TypeFactory.mkInteger()).allSupertypes());
    }

    
    // more elaborated test for equals
    public void testEquals() {
        // boolean 
        BooleanType bt1 = new BooleanType();
        BooleanType bt2 = new BooleanType();
        new EqualsTester(bt1, bt2, null, null);
        assertEquals( bt1.hashCode(), bt2.hashCode() );
        
        
        // integer
        IntegerType it1 = new IntegerType();
        IntegerType it2 = new IntegerType();
        new EqualsTester(it1, it2, null, null);
        assertEquals( it1.hashCode(), it2.hashCode() );

        // real
        RealType rt1 = new RealType();
        RealType rt2 = new RealType();
        new EqualsTester(rt1, rt2, null, null);
        assertEquals( rt1.hashCode(), rt2.hashCode() );
        
        
        SetType sett1 = new SetType(bt1);
        SetType sett2 = new SetType(bt1);
        SetType sett3 = new SetType(it1);
        new EqualsTester(sett1, sett2, sett3, null);
        
        BagType bagt1 = new BagType(bt1);
        BagType bagt2 = new BagType(bt1);
        BagType bagt3 = new BagType(it1);
        new EqualsTester(bagt1, bagt2, bagt3, null);

        SequenceType sequencet1 = new SequenceType(bt1);
        SequenceType sequencet2 = new SequenceType(bt1);
        SequenceType sequencet3 = new SequenceType(it1);
        new EqualsTester(sequencet1, sequencet2, sequencet3, null);
        
        CollectionType collectiont1 = new CollectionType(bt1);
        CollectionType collectiont2 = new CollectionType(bt1);
        CollectionType collectiont3 = new CollectionType(it1);
        new EqualsTester(collectiont1, collectiont2, collectiont3, sett1);

        EnumType enumt1 = new EnumType( "e1", new LinkedList() );
        EnumType enumt2 = new EnumType( "e1", new LinkedList() );
        EnumType enumt3 = new EnumType( "e2", new LinkedList() );
        new EqualsTester(enumt1, enumt2, enumt3, null);

        TupleType.Part part1 = new TupleType.Part("p1", sett1);
        TupleType.Part part2 = new TupleType.Part("p1", sett2);
        TupleType.Part part3 = new TupleType.Part("p2", sett2);
        TupleType tuplet1 = new TupleType( new TupleType.Part[]{part1});
        TupleType tuplet2 = new TupleType( new TupleType.Part[]{part2});
        TupleType tuplet3 = new TupleType( new TupleType.Part[]{part3});
        new EqualsTester(tuplet1, tuplet2, tuplet3, null);
                
    }
  
    private Set mkSet(Object[] elems) {
        Set res = new HashSet(elems.length);
        res.addAll(Arrays.asList(elems));
        return res;
    }
    
    
}
