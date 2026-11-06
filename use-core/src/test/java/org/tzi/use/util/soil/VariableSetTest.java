/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.util.soil;

import java.util.Random;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * Test cases for the special set operations of {@code VariableSet}s
 * 
 * @author Daniel Gent
 * @see VariableSet
 */
public class VariableSetTest extends TestCase {
	/** {@code VariableSet} A */
	private VariableSet fA;
	/** {@code VariableSet} B */
	private VariableSet fB;
	
	
	/**
	 * constructs the fixtures
	 */
	@Override
	@Before
	public void setUp() {
		
		// fill A and B with some random data
		Type[] types = { 
				TypeFactory.mkInteger(),
				TypeFactory.mkReal(),
				TypeFactory.mkString(),
				TypeFactory.mkBoolean(),
				TypeFactory.mkOclAny(),
		};
		
		String[] names = {
				"v00", "v01", "v02", "v03", "v04", "v05", "v06", "v07", "v08",
				"v09", "v10", "v11", "v12", "v13", "v14", "v15", "v16", "v17",
				"v18", "v19"
		};
		
		int numElems = 10;
		Random random = new Random();
		
		fA = new VariableSet();
		for (int i = 0; i < numElems; ++i) {
			fA.add(
					// the last 5 names are exclusively in B
					names[random.nextInt(names.length - 5)], 
					types[random.nextInt(types.length)]);
		}

		fB = new VariableSet();
		for (int i = 0; i < numElems; ++i) {
			fB.add(
					// the first 5 names are exclusively in A
					names[5 + random.nextInt(names.length - 5)], 
					types[random.nextInt(types.length)]);
		}
		
		// make sure there is an element in one set, which is not in the other
		fA.add("aExclusive", types[random.nextInt(types.length)]);
		fB.add("bExclusive", types[random.nextInt(types.length)]);
		
		// make sure A and B have at least one element in common
	    Type commonType = types[random.nextInt(types.length)];
	    fA.add("common", commonType);
	    fB.add("common", commonType);
		
	    // something to do for polydiff2...
	    Type integerType = TypeFactory.mkInteger();
	    Type realType = TypeFactory.mkReal();
	    // just to make sure...
	    assertTrue(integerType.conformsTo(realType));
	    
	    fA.add("aInt_bReal", integerType);
	    fB.add("aInt_bReal", realType);
	    
	    fA.add("aReal_bInt", realType);
	    fB.add("aReal_bInt", integerType);
	}
	
	
	/**
	 * tests {@link VariableSet#add(VariableSet) Union} respectively
	 * {@link VariableSet#add(VariableSet) add}
	 */
	@Test
	public void testUnion() {
		VariableSet result = VariableSet.union(fA, fB);
		
		// everything in A must be in the result
		assertTrue(result.containsAll(fA));
		
		// everything in B must be in the result
		assertTrue(result.containsAll(fB));
		
		// everything in the result must be in either A or B
		for (String name : result.getNames()) {
			for (Type type : result.getTypes(name)) {
				assertTrue(fA.contains(name, type) || fB.contains(name, type));
			}
		}
	}
	
	
	/**
	 * tests {@link VariableSet#difference(VariableSet, VariableSet) Difference} 
	 * respectively {@link VariableSet#add(VariableSet) remove}
	 */
	@Test
	public void testDifference() {
		VariableSet diffAB = VariableSet.difference(fA, fB);
		
		// everything in the difference must be in A
		assertTrue(fA.containsAll(diffAB));
		
		// nothing in B might be in the difference
		for (String name : fB.getNames()) {
			for (Type type : fB.getTypes(name)) {
				assertFalse(diffAB.contains(name, type));
			}
		}
		
		VariableSet diffBA = VariableSet.difference(fB, fA);
		
		// everything in the difference must be in B
		assertTrue(fB.containsAll(diffBA));
		
		// nothing in A might be in the difference
		for (String name : fA.getNames()) {
			for (Type type : fA.getTypes(name)) {
				assertFalse(diffBA.contains(name, type));
			}
		}
	}
	
	
	/**
	 * tests 
	 * {@link VariableSet#polymorphicDifference1(VariableSet, VariableSet) 
	 * PolymorphicDifference1} 
	 * respectively 
	 * {@link VariableSet#removePolymorphic1(VariableSet) removePolymorphic1}
	 */
	@Test
	public void testPolymorphicDifference1() {
		VariableSet pDiff1 = VariableSet.polymorphicDifference1(fA, fB);
		
		// everything in pDiff1 must be in A
		assertTrue(fA.containsAll(pDiff1));
		
		// the normal difference is a subset of the first polymorphic difference
		// (it removes everything a normal difference would + possibly some more)
		// we want to take a look everything that gets only removed by the
		// first polymorphic difference
		
		VariableSet diff = VariableSet.difference(fA, fB);
		VariableSet pDiffExcl = VariableSet.difference(diff, pDiff1);
		
		// for each element in pDiffExcl the following must hold:
		// - element of vA
		// - not element of vB
		// - vB has a variable with that name
		for (String name : pDiffExcl.getNames()) {
			for (Type type : pDiffExcl.getTypes(name)) {
				assertTrue(fA.contains(name, type));
				assertFalse(fB.contains(name, type));
				assertTrue(fB.contains(name));
			}
		}
	}
	

	/**
	 * tests 
	 * {@link VariableSet#polymorphicDifference2(VariableSet, VariableSet) 
	 * PolymorphicDifference2} 
	 * respectively 
	 * {@link VariableSet#removePolymorphic2(VariableSet) removePolymorphic2}
	 */
	@Test
	public void testPolymorphicDifference2() {
		VariableSet pDiff2 = VariableSet.polymorphicDifference2(fA, fB);
		
		// everything in pDiff2 must be in A
		assertTrue(fA.containsAll(pDiff2));
		
		// the normal difference is a subset of the second polymorphic difference
		// (it removes everything a normal difference would + possibly some more)
		// we want to take a look everything that gets only removed by the
		// second polymorphic difference
		
		VariableSet diff = VariableSet.difference(fA, fB);
		VariableSet pDiffExcl = VariableSet.difference(diff, pDiff2);
		
		// for each element in pDiffExcl the following must hold:
		// - element of A
		// - not element of B
		// - B has a variable with that name
		// - one of its types must be a subtype of the current elements type
		for (String name : pDiffExcl.getNames()) {
			for (Type type : pDiffExcl.getTypes(name)) {
				assertTrue(fA.contains(name, type));
				assertFalse(fB.contains(name, type));
				assertTrue(fB.contains(name));
				boolean containsSubType = false;
				for (Type otherType : fB.getTypes(name)) {
					if (otherType.conformsTo(type)) {
						containsSubType = true;
						break;
					}
				}
				assertTrue(containsSubType);
			}
		}
	}
}
