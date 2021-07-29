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

import java.util.Comparator;
import java.util.Collection;
import java.util.Iterator;

/** 
 * A CollectionComparator compares two collections. The collections
 * must be ordered and their elements must be comparable. 
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class CollectionComparator<I extends Comparable<I>> implements Comparator<Collection<I>> {


    /** 
     * Compare two collections.
     *
     * @pre o1, o2 must be Collections, their iterators must have a
     * defined order, and the collection elements must implement the
     * Comparable interface.  
     */
    public int compare(Collection<I> c1, Collection<I> c2) {
        
        if (c1.size() != c2.size() )
            return c1.size() - c2.size();

        Iterator<I> it1 = c1.iterator();
        Iterator<I> it2 = c2.iterator();
        
        while (it1.hasNext() && it2.hasNext() ) {
            int comp = it1.next().compareTo(it2.next());
            if (comp != 0 )
                return comp;
        }
        return 0;
    }
}
