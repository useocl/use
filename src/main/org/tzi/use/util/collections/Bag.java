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

package org.tzi.use.util.collections;

import java.util.Collection;
import java.util.Iterator;

/** 
 * A Bag extends the notion of a Set by allowing duplicates of its
 * elements. It does so by storing for each object the number of its
 * occurrences.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see         java.util.Set
 */


public interface Bag<T> extends Collection<T> {
    // Query Operations

    /**
     * Returns the number of all elements (including duplicates) in this Bag.
     */
    @Override
	int size();

    /**
     * Returns the number of different elements in this
     * Bag. Duplicates are not counted.  
     */
    int sizeUnique();

    /**
     * Returns true if this Bag contains no elements.
     */
    @Override
	boolean isEmpty();

    /**
     * Returns true if this Bag contains the specified object at least once.
     *
     * @exception NullPointerException obj is null.
     */
    @Override
	boolean contains(Object obj);

    /**
     * Returns the number of occurrences of the specified object in this Bag.
     *
     * @exception NullPointerException obj is null.
     */
    int occurrences(Object obj);

    /**
     * Returns an Iterator over the elements in this Bag. Different
     * elements are returned in no particular order (unless the Bag is
     * an instance of some class that provides a guarantee). However,
     * duplicate elements are guaranteed to be kept adjacent.
     */
    @Override
	Iterator<T> iterator();

    /**
     * Returns an Iterator over the unique elements in this
     * Bag. Elements are returned in no particular order (unless the
     * Bag is an instance of some class that provides a
     * guarantee). Duplicate elements are delivered just once.
     */
    Iterator<T> uniqueIterator();


    // Modification Operations

    /** 
     * Adds the specified element to this Bag (optional
     * operation). The Bag may already contain one or more equal
     * elements.
     *
     * @return true if o != null.
     */
    @Override
	boolean add(T o);

    /** 
     * Adds the specified element <code>count</code> times to this
     * Bag. The specified count must be greater than zero. The Bag may
     * already contain one or more equal elements.
     *
     * @exception IllegalArgumentException count less than 1.
     * @return true if o != null.  
     */
    boolean add(T o, int count);

    /** 
     * Removes one occurrence of the given element in this
     * Bag if it is present.
     *
     * @return true if the Bag contained the specified element.
     */
    @Override
	boolean remove(Object o);

    /** 
     * Removes all occurrences of the given element in this
     * Bag if it is present.
     *
     * @return true if the Bag contained the specified element.
     */
    boolean removeAll(T o);


    // Bulk Operations

    /**
     * Removes all elements from this Bag (optional operation).
     *
     * @exception UnsupportedOperationException clear is not supported
     *        by this Bag.
     */
    @Override
	void clear();


    // Views

    // Comparison and hashing

    /**
     * Compares the specified Object with this Bag for equality.
     *
     * @param o Object to be compared for equality with this Bag.
     * @return true if the specified Object is equal to this Bag.
     */
    @Override
	boolean equals(Object o);

    /**
     * Returns the hash code value for this Bag.
     */
    @Override
	int hashCode();
}
