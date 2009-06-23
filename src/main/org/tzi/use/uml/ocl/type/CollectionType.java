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
 * Collection is the abstract base class for set, sequence, and bag.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetType
 * @see     SequenceType
 * @see     BagType
 */
public class CollectionType extends Type {

    private Type fElemType;

    protected CollectionType(Type elemType) {
        fElemType = elemType;
    }

    public Type elemType() {
        return fElemType;
    }

    public boolean isCollection() {
    	return true;
    }
    
    public boolean isTrueCollection() {
    	return true;
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
        if (! t.isTrueCollection() )
            return false;

        CollectionType t2 = (CollectionType) t;
        if (fElemType.isSubtypeOf(t2.elemType()) )
            return true;
        return false;
    }

    /** 
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Collection(T) the result is the set of
     * all types Collection(T') where T' <= T.
     */
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>();
        Set<Type> elemSuper = fElemType.allSupertypes();
        Iterator<Type> typeIter = elemSuper.iterator();
        
        while (typeIter.hasNext() ) {
            Type t = typeIter.next();
            res.add(TypeFactory.mkCollection(t));
        }
        
        return res;
    }

    public Type getLeastCommonSupertype(Type type)
    {
    	if (!type.isCollection())
    		return null;
    	
    	CollectionType cType = (CollectionType)type;
    	Type commonElementType = this.fElemType.getLeastCommonSupertype(cType.fElemType);
    	
    	return TypeFactory.mkCollection(commonElementType);
    }
        
    public String toString() {
        return "Collection(" + elemType() + ")";
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == getClass() )
            return ((CollectionType) obj).elemType().equals(elemType());
        return false;
    }

    public int hashCode() {
        return fElemType.hashCode();
    }
    
}
