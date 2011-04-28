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

package org.tzi.use.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Class with useful class operations for modifying collections.
 *
 * @author  Joern Bohling
 * @author  Lars Hamann
 */
public final class CollectionUtil {

    // no instances
    private CollectionUtil() {}

    private static <T> void combine(Stack<T> elementStack,
                                    List<List<T>> listWithLists,
                                    List<List<T>> combinations) {
        if (elementStack.size() == listWithLists.size()) {
            combinations.add( new ArrayList<T>(elementStack));
        } else {
            List<T> currentList = listWithLists.get(elementStack.size());

            for (T item : currentList) {
                elementStack.push( item );
                combine( elementStack, listWithLists, combinations );
                elementStack.pop();
            }
        }
    }

    /**
     * Computes of a given List with Lists the combinations.
     * Example: Given  { { a, b } { 1, 2, 3} { p, q} }
     * Result is: { {a,1,p} {a,1,q} {a,2,p} {a,2,q} {a,3,p}
     *              ... {b,2,q} {b,3,p} {b,3,q} }
     */
    public static <T> List<List<T>> combinations( List<List<T>> listWithLists ) {
        // the elements of listWithLists must be Lists
        List<List<T>> combinations = new ArrayList<List<T>>();
        Stack<T> elementStack = new Stack<T>();
        
        combine( elementStack, listWithLists, combinations );
        return combinations;
    }
    
    /**
     * Returns <code>theList</code> if it is not <code>null</code> or
     * an unmodifiable singleton empty list (see {@link Collections#emptyList()}).
     * @param <T>
     * @param theList
     * @return
     */
    public static <T> List<T> emptyListIfNull(List<T> theList) {
    	if (theList == null)
    		return Collections.emptyList();
    	else
    		return theList;
    }
    
    /**
     * Returns <code>theMap</code> if it is not <code>null</code> or
     * an unmodifiable singleton empty map (see {@link Collections#emptyMap()}).
     * @param <TK>
     * @param <TV>
     * @param theMap
     * @return
     */
    public static <TK, TV> Map<TK, TV> emptyMapIfNull(Map<TK, TV> theMap) {
    	if (theMap == null)
    		return Collections.emptyMap();
    	else
    		return theMap;
    }
    
    /**
     * Returns <code>theSet</code> if it is not <code>null</code> or
     * an unmodifiable singleton empty set (see {@link Collections#emptySet()}).
     * @param <T>
     * @param theSet
     * @return
     */
    public static <T> Set<T> emptySetIfNull(Set<T> theSet) {
    	if (theSet == null)
    		return Collections.emptySet();
    	else
    		return theSet;
    }
    
    /**
     * Returns the same list <code>theList</code> if it has elements,
     * otherwise it returns a new <code>ArrayList</code>.
     * Can be used in combination with {@link CollectionUtil#emptyListIfNull(List)} to
     * use a singleton empty list as initial value.
     * <p>
     * <strong>Pre:</strong> <code>theList != null</code>
     * </p>
     * <p>
     * <strong>Post:</strong> <code>result</code> is writable. 
     * @param <T> Element type of the List
     * @param theList A <code>List</code>
     * @return The same list if <code>theList.size() > 0</code> or a new <code>ArrayList</code>  
     */
    public static <T> List<T> initAsArrayList(List<T> theList) {
    	if (theList.size() == 0)
    		return new ArrayList<T>();
    	else
    		return theList;
    }
    
    /**
     * Returns the same map <code>theMap</code> if it has elements,
     * otherwise it returns a new <code>HashMap</code>.
     * Can be used in combination with {@link CollectionUtil#emptyMapIfNull(List)} to
     * use a singleton empty map as initial value.
     * <p>
     * <strong>Pre:</strong> <code>theMap != null</code>
     * </p>
     * <p>
     * <strong>Post:</strong> <code>result</code> is writable. 
     * @param <TK> Element type of the keys
     * @param <TV> Element type of the values
     * @param theList A <code>List</code>
     * @return The same <code>Map</code> if <code>theMap.size() > 0</code> or a new <code>HashMap</code>  
     */
    public static <TK, TV> Map<TK,TV> initAsHashMap(Map<TK,TV> theMap) {
    	if (theMap.size() == 0)
    		return new HashMap<TK,TV>();
    	else
    		return theMap;
    }
    
    /**
     * Returns the same set <code>theSet</code> if it has elements,
     * otherwise it returns a new <code>HashSet</code>.
     * Can be used in combination with {@link CollectionUtil#emptySetIfNull(Set)} to
     * use a singleton empty set as initial value.
     * <p>
     * <strong>Pre:</strong> <code>theSet != null</code>
     * </p>
     * <p>
     * <strong>Post:</strong> <code>result</code> is writable. 
     * @param <T> Element type of the Set
     * @param theSet A <code>Set</code>
     * @return The same set if <code>theSet.size() > 0</code> or a new <code>HashSet</code>  
     */
    public static <T> Set<T> initAsHashSet(Set<T> theSet) {
    	if (theSet.size() == 0)
    		return new HashSet<T>();
    	else
    		return theSet;
    }
}