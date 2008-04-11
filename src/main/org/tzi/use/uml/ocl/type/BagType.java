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

package org.tzi.use.uml.ocl.type;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The OCL Bag type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetType
 */
public final class BagType extends CollectionType {

    public BagType(Type elemType) {
        super(elemType);
    }

    public String shortName() {
        if (elemType().isCollection() )
            return "Bag(...)";
        else 
            return "Bag(" + elemType() + ")";
    }

    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
        if (! t.isTrueCollection() && ! t.isBag() )
            return false;

        CollectionType t2 = (CollectionType) t;
        if (elemType().isSubtypeOf(t2.elemType()) )
            return true;
        return false;
    }

    /** 
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Bag(T) the result is the set of
     * all types Bag(T') and Collection(T') where T' <= T.
     */
    public Set allSupertypes() {
        Set res = new HashSet();
        res.addAll(super.allSupertypes());
        Set elemSuper = elemType().allSupertypes();
        Iterator typeIter = elemSuper.iterator();
        while (typeIter.hasNext() ) {
            Type t = (Type) typeIter.next();
            res.add(TypeFactory.mkBag(t));
        }
        return res;
    }

    public String toString() {
        return "Bag(" + elemType() + ")";
    }
}
