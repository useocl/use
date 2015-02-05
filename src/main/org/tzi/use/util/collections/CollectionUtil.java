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

package org.tzi.use.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.tzi.use.util.Pair;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

/**
 * Class with useful class operations for modifying collections.
 *
 * @author  Joern Bohling
 * @author  Lars Hamann
 */
public final class CollectionUtil {

	public enum UniqueList {
		FIRST_IS_UNIQUE,
		SECOND_IS_UNIQUE
	}
	
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
        List<List<T>> combinations = new ArrayList<List<T>>();
        Stack<T> elementStack = new Stack<T>();
        
        combine( elementStack, listWithLists, combinations );
        
        return combinations;
    }
    
    
    /**
     * Computes of a given List with Lists the combinations.
     * Example: Given  { { a, b } { 1, 2, 3} { p, q} }
     * Result is: { {a,1,p} {a,1,q} {a,2,p} {a,2,q} {a,3,p}
     *              ... {b,2,q} {b,3,p} {b,3,q} }
     */
    public static <T> List<List<Pair<T>>> combinationsOne( List<T> firstList, List<T> secondList, UniqueList unique ) {
    	List<T> l1 = (unique == UniqueList.FIRST_IS_UNIQUE ? secondList : firstList);
    	List<T> l2 = (unique == UniqueList.FIRST_IS_UNIQUE ? firstList : secondList);
    	
    	// Each partition contains only combinations from one element of the unique list
    	// e.g.: {a,b} {c,d} results in:
    	// 0 = {(a,c), (a,d)}, 1 = {(b,c), (b,d)}
    	List<List<Pair<T>>> partitionedCombinations = new ArrayList<List<Pair<T>>>();
    	
    	for (int index = 0; index < l1.size(); index++) {
    		List<Pair<T>> partition = new ArrayList<Pair<T>>();
    		
    		for (int index2 = 0; index2 < l2.size(); index2++) {
    			Pair<T> entry = new Pair<T>();
    			if (unique == UniqueList.FIRST_IS_UNIQUE) {
    				entry.first  = l2.get(index2);
    				entry.second = l1.get(index);
    			} else {
    				entry.first  = l1.get(index);
    				entry.second = l2.get(index2);
    			}
    			partition.add(entry);
    		}
    		
    		partitionedCombinations.add(partition);
    	}
    	
    	
    	List<List<Pair<T>>> result = new ArrayList<List<Pair<T>>>();
    	result.add(Collections.<Pair<T>>emptyList());
    	// Loops over all partitions of the combinations and calls combine.
    	// The tails is reduced by one after every call, because the previous call includes already the combinations
    	for (int index = 0; index < partitionedCombinations.size(); ++index) {
    		combinationsOneAux(result, Collections.<Pair<T>>emptyList(), partitionedCombinations.subList(index, partitionedCombinations.size()));
    	}
    	
    	return result;
    }
    
    private static <T> void combinationsOneAux(List<List<Pair<T>>> result, List<Pair<T>> head, List<List<Pair<T>>> tail) {
    
    	if (tail.size() == 0) return;
    	
    	List<Pair<T>> myCombinations = tail.get(0);
    	
    	// All combination for a single element
    	// cmb = m * (1 + (|tail|  
    	for(int index = 0; index < myCombinations.size(); ++index) {
    		List<Pair<T>> newHead = new LinkedList<Pair<T>>(head);
    		newHead.add(myCombinations.get(index));
    		result.add(newHead);
    		for (int index2 = 1; index2 < tail.size(); ++index2) {
	    		combinationsOneAux(result, newHead, tail.subList(index2, tail.size()));
    		}
    	}
    }
    
    /**
     * Returns <code>theList</code> if it is not <code>null</code> or
     * an unmodifiable singleton empty list (see {@link Collections#emptyList()}).
     * @param <T>
     * @param theList
     * @return
     */
    public static <T> List<T> emptyListIfNull(final List<T> theList) {
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
    public static <TK, TV> Map<TK, TV> emptyMapIfNull(final Map<TK, TV> theMap) {
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
    public static <T> Set<T> emptySetIfNull(final Set<T> theSet) {
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
    public static <T> List<T> initAsArrayList(final List<T> theList) {
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
    public static <TK, TV> Map<TK,TV> initAsHashMap(final Map<TK,TV> theMap) {
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
    public static <T> Set<T> initAsHashSet(final Set<T> theSet) {
    	if (theSet.size() == 0)
    		return new HashSet<T>();
    	else
    		return theSet;
    }
    
    /**
     * This operation needs to be used with care!
     * It allows an unsafe downcast from a generic collection to another,
     * which in general is not valid!
     * @param set
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T, TCast extends T> Set<TCast> downCastUnsafe(final Set<? extends T> set) {
		return (Set)set;
    }

	/**
	 * Returns the single element from <code>collection<code>.
	 * If the collection has more than one element or is empty,
	 * an exception is thrown.
	 * @param collection The collection to retrieve the single element from.
	 * @return The single element.
	 * @throws IllegalArgumentException If the collection is empty or has more than one element.
	 */
	public static <T> T exactlyOne( Collection<T> collection) throws IllegalArgumentException {
		Iterator<T> i = collection.iterator();
		if (!i.hasNext())
			throw new IllegalArgumentException("Collection is empty");
		
		T element = i.next();
		
		if (i.hasNext())
			throw new IllegalArgumentException("Collection has more than one element");
		
		return element;
	}
	
	public static <T> boolean exists(Iterable<T> source, Predicate<T> predicate) {
		Iterator<T> iter = source.iterator();
		return exists(iter, predicate);
	}
	
	public static <T> boolean exists(Iterator<T> source, Predicate<T> predicate) {
		T item;
		
		while (source.hasNext()) {
			item = source.next(); 
			if (predicate.apply(item)) {
				 return true;
			 }
		}
		
		return false;
	}

	public static <T,TCast> Set<TCast> filterByType(final Set<T> source, final Class<TCast> cls) {
		return Sets.newHashSet(Iterators.filter(source.iterator(), cls)); 
	}
}