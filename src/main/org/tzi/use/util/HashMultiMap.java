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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/** 
 * A MultiMap is a Map allowing multiple occurrences of keys.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see         java.util.Map
 */


public class HashMultiMap<K, V> implements MultiMap<K, V> {
    private Map<K, List<V>> fMap;
    private transient int fSizeAll;

    public HashMultiMap() {
        fMap = new HashMap<K, List<V>>();
    }

    // Query Operations

    /**
     * Returns the number of values in this multimap.
     */
    public int size() {
        return fSizeAll;
    }

    /**
     * Returns <tt>true</tt> if this multimap contains no mappings.  
     */
    public boolean isEmpty() {
        return fSizeAll == 0;
    }

    /**
     * Returns <tt>true</tt> if this multimap contains a mapping for
     * the specified key.  
     */
    public boolean containsKey(K key) {
        return fMap.containsKey(key);
    }

    /**
     * Returns <tt>true</tt> if this multimap maps one or more keys to
     * the specified value.  
     */
    public boolean containsValue(V value) {
        for (List<V> l : fMap.values()) {
            if (l.contains(value) )
                return true;
        }
        return false;
    }

    /**
     * Returns a list of values to which this multimap maps the specified
     * key.  
     *
     * @return the list of values to which this map maps the specified
     *         key, the list may be empty if the multimap contains no
     *         mapping for this key. 
     */
    public List<V> get(K key) {
        List<V> l = fMap.get(key);
        if (l == null )
            l = new ArrayList<V>();
        return l;
    }


    // Modification Operations

    /**
     * Adds the specified value with the specified key to this multimap.
     */
    public void put(K key, V value) {
        List<V> l = fMap.get(key);
        if (l == null ) {
            l = new ArrayList<V>();
            fMap.put(key, l);
        }
        l.add(value);
        fSizeAll++;
    }   

    /**
     * Copies all entries from the specified multimap to this
     * multimap.  
     */
    public void putAll(MultiMap<K, V> t) {
        for (K key : t.keySet()) {
            List<V> tl = t.get(key);
            List<V> l = fMap.get(key);
            
            if (l == null ) {
                l = new ArrayList<V>();
                fMap.put(key, l);
            }
            l.addAll(tl);
            fSizeAll += tl.size();
        }
    }


    /**
     * Removes all mappings for this key from this multimap if present.
     */
    public void remove(K key) {
        List<V> l = fMap.get(key);
        if (l != null )
            fSizeAll -= l.size();
        fMap.remove(key);
    }

    /**
     * Removes the specified key/value mapping from this multimap if present.
     */
    public void remove(K key, V value) {
        List<V> l = fMap.get(key);
        if (l != null )
            if (l.remove(value) )
                fSizeAll--;
    }

    // Bulk Operations

    /**
     * Removes all mappings from this map (optional operation).
     */
    public void clear() {
        fMap.clear();
        fSizeAll = 0;
    }


    // Views

    /**
     * Returns a set view of the keys contained in this multimap.
     */
    public Set<K> keySet() {
        return fMap.keySet();
    }

    public Map<K, List<V>> getMap() {
    	return new HashMap<K, List<V>>(this.fMap);
    }
    
    // Comparison and hashing
    /**
     * Compares the specified object with this multimap for equality.
     */
    public boolean equals(Object o) {
        if (o == this ) return true;
        if (o == null) return false;
        
        MultiMap<?, ?> other = (MultiMap<?, ?>)o;
        
        if (other.size() != this.size() )
            return false;

        return fMap.equals(other.getMap());
    }

    /**
     * Returns the hash code value for this multimap.
     */
    public int hashCode() {
        int h = 0;
        for (Entry<K, List<V>> entry : fMap.entrySet()) {
            h += entry.hashCode();
        }
        return h;
    }
}
