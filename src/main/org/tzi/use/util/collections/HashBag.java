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

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A Bag stores objects and for each object the number of its occurrences.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class HashBag<T> extends AbstractBag<T> {

    // We don't want to allocate a new Integer object each time we
    // have to increment the value in a map.
    class MutableInteger {
        int fInt;
        MutableInteger(int n) {
            fInt = n;
        }
    }

    private transient HashMap<T, MutableInteger> fMap;
    private transient int fSizeAll;

    /**
     * Constructs a new, empty HashBag; the backing HashMap has default
     * capacity and load factor.
     */
    public HashBag() {
        fMap = new HashMap<T, MutableInteger>();
    }

    /**
     * Returns the number of all elements (including duplicates) in this Bag.
     */
    public int size() {
        return fSizeAll;
    }

    /**
     * Returns the number of different elements in this
     * Bag. Duplicates are not counted.  
     */
    public int sizeUnique() {
        return fMap.size();
    }   

    /**
     * Returns true if this Bag contains no elements.
     */
    public boolean isEmpty() {
        return fMap.isEmpty();
    }

    /**
     * Returns true if this Bag contains the specified object at least once.
     */
    public boolean contains(Object obj) {
        return fMap.containsKey(obj);
    }
    

    /**
     * Returns the number of occurrences of the specified object in this Bag.
     */
    public int occurrences(Object obj) {
        MutableInteger count = fMap.get(obj);
        if (count == null )
            return 0;
        else 
            return count.fInt;
    }

    /**
     * Returns an Iterator over the elements in this Bag. Different
     * elements are returned in no particular order (unless the Bag is
     * an instance of some class that provides a guarantee). However,
     * duplicate elements are guaranteed to be kept adjacent.
     */
    public Iterator<T> iterator() {
        return new AllElementsIterator(this);
    }

    /**
     * Returns an Iterator over the unique elements in this
     * Bag. Elements are returned in no particular order (unless the
     * Bag is an instance of some class that provides a
     * guarantee). Duplicate elements are delivered just once.
     */
    public Iterator<T> uniqueIterator() {
        return fMap.keySet().iterator();
    }


    // Modification Operations
    
    public boolean add(T obj) {
        MutableInteger count = fMap.get(obj);
        if (count == null )
            fMap.put(obj, new MutableInteger(1));
        else
            count.fInt++;
        
        fSizeAll++;
        return obj != null;
    }

    public boolean add(T obj, int c) {
        if (c < 1 ) 
            throw new IllegalArgumentException("count: " + c);
        MutableInteger count = fMap.get(obj);
        if (count == null )
            fMap.put(obj, new MutableInteger(c));
        else
            count.fInt += c;
        fSizeAll += c;
        return obj != null;
    }

    public boolean remove(Object obj) {
        MutableInteger count = fMap.get(obj);
        
        if (count == null )
            return false;
        else {
            if (count.fInt > 1 )
                count.fInt--;
            else
                fMap.remove(obj);
            fSizeAll--;
            return true;
        }
    }

    public boolean removeAll(Object obj) {
        MutableInteger count = fMap.get(obj);
        if (count == null )
            return false;
        else {
            fMap.remove(obj);
            fSizeAll -= count.fInt;
            return true;
        }
    }

    // Bulk Operations

    /**
     * Removes all elements from this Bag (optional operation).
     */
    public void clear() {
        fMap.clear();
        fSizeAll = 0;
    }


    /**
     * HashBag Iterator.
     */
    private class AllElementsIterator implements Iterator<T> {
        private Bag<T> fBag;
        private Iterator<T> fKeyIterator;
        private int fElemsLeft;
        private T fElem;

        AllElementsIterator(Bag<T> b) {
            fBag = b;
            fKeyIterator = fBag.uniqueIterator();
            fElemsLeft = 0;
            fElem = null;
        }

        public boolean hasNext() {
            return fElemsLeft > 0 || fKeyIterator.hasNext();
        }

        public T next() throws NoSuchElementException {
            if (fElemsLeft == 0 ) {
                // need to get next element
                if (! fKeyIterator.hasNext() )
                    throw new NoSuchElementException();
                
                fElem = fKeyIterator.next();
                fElemsLeft = fBag.occurrences(fElem) - 1;
                return fElem;
            } else {
                // there are more of this current element
                fElemsLeft--;
                return fElem;
            }
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("remove");
        }
    }
}
