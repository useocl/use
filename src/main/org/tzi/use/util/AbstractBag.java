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

import java.util.Collection;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Arrays;

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
        
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size()) {
            return false;
        }

        // Since the `contains' methods do not work in one way
        // (e.g. Bag{1,1}=Bag{1,0} `contains' returns true;
        // Bag{1,0}=Bag{1,1} `contains' returns false). Both Bags are
        // transformed into an array and are sorted. This way every
        // value can be compared seperatly.
        Object[] thisBagAsArray =  toArray();
        Object[] objBagAsArray = c.toArray();
        Arrays.sort( thisBagAsArray );
        Arrays.sort( objBagAsArray );
        
        for ( int i=0; i<thisBagAsArray.length; i++ ) {
            if ( !thisBagAsArray[i].equals( objBagAsArray[i] ) ) {
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

