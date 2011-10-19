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

// $Id: AbstractBagTest.java 2409 2011-07-27 09:45:00Z lhamann $

package org.tzi.use.util;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.util.collections.CollectionUtil;
import org.tzi.use.util.collections.MinCombinationsIterator;
import org.tzi.use.util.collections.CollectionUtil.UniqueList;

import junit.framework.TestCase;


/**
 * Test comparing Bags with each other.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Fabian Gutsche
 */
public class CombinationTest extends TestCase {
           
	private List<String> getList(int offSet, int numElements) {
		List<String> result = new ArrayList<String>(numElements);
		for (int index = 1; index <= numElements; ++index) {
			result.add(new String(new char[]{(char)(index + offSet + 64)}));
		}
		return result;
	}
	
    public void testCombination() {
        List<String> l1 = getList(0, 3);
        List<String> l2 = getList(3, 3);
        
        List<List<Pair<String>>> result = CollectionUtil.combinationsOne(l1, l2, UniqueList.SECOND_IS_UNIQUE);
        assertEquals(64, result.size());
    }
    
    
    public void testCombinationIterator() {
    	List<String> l1 = getList(0,3);
        List<String> l2 = getList(3,3);
        
        List<List<Pair<String>>> result = CollectionUtil.combinationsOne(l1, l2, UniqueList.SECOND_IS_UNIQUE);
        MinCombinationsIterator<String> iter = new MinCombinationsIterator<String>(l1, l2, UniqueList.SECOND_IS_UNIQUE);
        
        int num = 0;
        while(iter.hasNext()) {
        	List<Pair<String>> elem = iter.next();
        	assertTrue(result.contains(elem));
        	++num;
        }
        
        assertEquals(result.size(), num);
    }
}
