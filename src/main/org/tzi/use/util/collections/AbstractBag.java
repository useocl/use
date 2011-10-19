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

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * Skeleton for implementations of the Bag interface.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public abstract class AbstractBag<T> extends AbstractCollection<T> implements Bag<T> {
    // Comparison and hashing

    /**
     * Compares the specified Object with this Bag for equality.
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Bag<?>))
            return false;
        
        Bag<?> otherBag = (Bag<?>) o;
        if (otherBag.size() != size()) {
            return false;
        }

        // Both objects are bags, therefore they are equal
        // if they
        // (1) have the same size (already checked)
        // (2) contain the same elements (3) the same number of times
        for (T element : this) {
        	if (!(otherBag.contains(element) && 
        		  otherBag.occurrences(element) == this.occurrences(element))) {
        		return false;
        	}
        }

        return true;
    }

    /**
     * Returns the hash code value for this Bag.
     */
    public int hashCode() {
        int h = 0;
        Iterator<T> i = iterator();
        while (i.hasNext()) {
            Object obj = i.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }
}

