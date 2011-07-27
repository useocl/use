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

package org.tzi.use.uml.ocl.value;

import java.util.Arrays;

import com.gargoylesoftware.base.testing.EqualsTester;
import junit.framework.TestCase;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Test Value classes.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public class ValueTest extends TestCase {

    public void testEnum() {
        String[] literals = { "a", "b", "c" };
        EnumType enm = TypeFactory.mkEnum("E", Arrays.asList(literals));
        assertEquals("EnumValue.value", "b", new EnumValue(enm, "b").value());
        try {
            new EnumValue(enm, "d");
            fail("Illegal EnumValue");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    public void testInt() {
        assertEquals("IntegerValue.value", 42, IntegerValue.valueOf(42).value());
        assertEquals(
                     "IntegerValue.type",
                     TypeFactory.mkInteger(),
                     IntegerValue.valueOf(42).type());
        assertTrue(
                   "IntegerValue.equals",
                   IntegerValue.valueOf(42).equals(IntegerValue.valueOf(42)));
    }

    public void testReal() {
        assertEquals("RealValue.value", 1.2, new RealValue(1.2).value(), 0);
        assertEquals("RealValue.type", TypeFactory.mkReal(), new RealValue(1.2).type());
        assertTrue("RealValue.equals", new RealValue(1.2).equals(new RealValue(1.2)));
    }

    public void testBoolean() {
        assertTrue("BooleanValue.value", BooleanValue.TRUE.value());
        assertFalse("BooleanValue.value", BooleanValue.FALSE.value());
        assertEquals("BooleanValue.value", BooleanValue.TRUE, BooleanValue.get(true));
        assertEquals("BooleanValue.value", BooleanValue.FALSE, BooleanValue.get(false));
        assertEquals(
                     "BooleanValue.type",
                     TypeFactory.mkBoolean(),
                     BooleanValue.TRUE.type());
        assertEquals(
                     "BooleanValue.type",
                     TypeFactory.mkBoolean(),
                     BooleanValue.FALSE.type());
        assertTrue("BooleanValue.equals", BooleanValue.FALSE.equals(BooleanValue.FALSE));
        assertTrue("BooleanValue.equals", BooleanValue.FALSE.equals(BooleanValue.FALSE));
    }

    public void testString() {
        assertEquals("StringValue.value", "foo", new StringValue("foo").value());
        assertEquals(
                     "StringValue.type",
                     TypeFactory.mkString(),
                     new StringValue("foo").type());
        assertTrue(
                   "StringValue.equals",
                   new StringValue("bar").equals(new StringValue("bar")));
    }

    public void testSet() {
        SetValue intSet = new SetValue(TypeFactory.mkInteger());
        assertEquals("SetValue.size", 0, intSet.size());
        intSet.add(IntegerValue.valueOf(3));
        assertEquals("SetValue.insert", 1, intSet.size());
        intSet.add(IntegerValue.valueOf(3));
        assertEquals("SetValue/no duplicates", 1, intSet.size());
        try {
            intSet.add(new StringValue("foo"));
            // fail("SetValue/matching element type");
            // currently this is allowed..
        } catch (IllegalArgumentException e) {
            // expected
        }
        SetValue set1 = new SetValue(TypeFactory.mkInteger());
        set1.add(IntegerValue.valueOf(1));
        set1.add(IntegerValue.valueOf(2));
        set1.add(IntegerValue.valueOf(3));
        SetValue set2 = new SetValue(TypeFactory.mkInteger());
        set2.add(IntegerValue.valueOf(2));
        set2.add(IntegerValue.valueOf(3));
        assertFalse("SetValue.equals", set1.equals(set2));
        set2.add(IntegerValue.valueOf(1));
        assertTrue("SetValue.equals", set1.equals(set2));
        assertEquals("SetValue.toString", "Set{1,2,3}", set1.toString());
    }

    public void testBag() {
        BagValue intBag = new BagValue(TypeFactory.mkInteger());
        assertEquals("BagValue.size", 0, intBag.size());
        intBag.add(IntegerValue.valueOf(3));
        assertEquals("BagValue.insert", 1, intBag.size());
        intBag.add(IntegerValue.valueOf(3));
        assertEquals("BagValue/duplicates", 2, intBag.size());
        try {
            intBag.add(new StringValue("foo"));
            //            fail("BagValue/matching element type");
        } catch (IllegalArgumentException e) {
            // expected
        }
        BagValue bag1 = new BagValue(TypeFactory.mkInteger());
        bag1.add(IntegerValue.valueOf(1));
        bag1.add(IntegerValue.valueOf(2));
        bag1.add(IntegerValue.valueOf(3));
        BagValue bag2 = new BagValue(TypeFactory.mkInteger());
        bag2.add(IntegerValue.valueOf(2));
        bag2.add(IntegerValue.valueOf(3));
        assertFalse("BagValue.equals", bag1.equals(bag2));
        bag2.add(IntegerValue.valueOf(1));
        assertTrue("BagValue.equals", bag1.equals(bag2));
        bag2.add(IntegerValue.valueOf(1));
        assertEquals("BagValue.toString", "Bag{1,1,2,3}", bag2.toString());
    }

    public void testSequence() {
        SequenceValue intSeq = new SequenceValue(TypeFactory.mkInteger());
        assertEquals("SequenceValue.size", 0, intSeq.size());
        intSeq.add(IntegerValue.valueOf(3));
        assertEquals("SequenceValue.insert", 1, intSeq.size());
        intSeq.add(IntegerValue.valueOf(3));
        assertEquals("SequenceValue/duplicates", 2, intSeq.size());
        try {
            intSeq.add(new StringValue("foo"));
            //            fail("SequenceValue/matching element type");
        } catch (IllegalArgumentException e) {
            // expected
        }
        SequenceValue seq1 = new SequenceValue(TypeFactory.mkInteger());
        seq1.add(IntegerValue.valueOf(1));
        seq1.add(IntegerValue.valueOf(2));
        seq1.add(IntegerValue.valueOf(3));
        SequenceValue seq2 = new SequenceValue(TypeFactory.mkInteger());
        seq2.add(IntegerValue.valueOf(1));
        seq2.add(IntegerValue.valueOf(2));
        assertFalse("SequenceValue.equals", seq1.equals(seq2));
        seq2.add(IntegerValue.valueOf(3));
        assertTrue("SequenceValue.equals", seq1.equals(seq2));
        seq2.add(IntegerValue.valueOf(1));
        assertEquals("SequenceValue.toString", "Sequence{1,2,3,1}", seq2.toString());
    }

    public void testSetEquals() {
        SetValue intSet1 = new SetValue(TypeFactory.mkInteger());
        intSet1.add(IntegerValue.valueOf(1));
        SetValue intSet2 = new SetValue(TypeFactory.mkInteger());
        intSet2.add(IntegerValue.valueOf(1));
        SetValue intSet3 = new SetValue(TypeFactory.mkInteger());
        intSet3.add(IntegerValue.valueOf(2));
        SetValue intSet4 = new SetValue(TypeFactory.mkInteger()) { /*subclass*/ };
        intSet4.add(IntegerValue.valueOf(1));
        
        new EqualsTester(intSet1, intSet2, intSet3, intSet4);
    }
    
    public void testSequenceEquals() {
        SequenceValue intSequence1 = new SequenceValue(TypeFactory.mkInteger());
        intSequence1.add(IntegerValue.valueOf(1));
        SequenceValue intSequence2 = new SequenceValue(TypeFactory.mkInteger());
        intSequence2.add(IntegerValue.valueOf(1));
        SequenceValue intSequence3 = new SequenceValue(TypeFactory.mkInteger());
        intSequence3.add(IntegerValue.valueOf(2));
        SequenceValue intSequence4 = new SequenceValue(TypeFactory.mkInteger()) { /*subclass*/ };
        intSequence4.add(IntegerValue.valueOf(1));
        
        new EqualsTester(intSequence1, intSequence2, intSequence3, intSequence4);
    }


    public void testBagEquals() {
        BagValue intBag1 = new BagValue(TypeFactory.mkInteger());
        intBag1.add(IntegerValue.valueOf(1));
        BagValue intBag2 = new BagValue(TypeFactory.mkInteger());
        intBag2.add(IntegerValue.valueOf(1));
        BagValue intBag3 = new BagValue(TypeFactory.mkInteger());
        intBag3.add(IntegerValue.valueOf(2));
        BagValue intBag4 = new BagValue(TypeFactory.mkInteger()) { /*subclass*/ };
        intBag4.add(IntegerValue.valueOf(1));
        
        new EqualsTester(intBag1, intBag2, intBag3, intBag4);
    }
    
    
}
