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
 * The OCL Set type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetType
 */
public final class SetType extends CollectionType {

    public SetType(Type elemType) {
        super(elemType);
    }

    public String shortName() {
        if (elemType().isCollection() )
            return "Set(...)";
        else 
            return "Set(" + elemType() + ")";
    }

    public boolean isTrueCollection() {
    	return false;
    }
    
    public boolean isSet() {
    	return true;
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
        if (! t.isTrueCollection() && ! t.isSet() )
            return false;

        CollectionType t2 = (CollectionType) t;
        if (elemType().isSubtypeOf(t2.elemType()) )
            return true;
        return false;
    }

    /** 
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Set(T) the result is the set of
     * all types Set(T') and Collection(T') where T' <= T.
     */
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>();
        res.addAll(super.allSupertypes());
        Set<Type> elemSuper = elemType().allSupertypes();
        Iterator<Type> typeIter = elemSuper.iterator();
        
        while (typeIter.hasNext() ) {
            Type t = typeIter.next();
            res.add(TypeFactory.mkSet(t));
        }
        return res;
    }

    public Type getLeastCommonSupertype(Type type)
    {
    	if (!type.isCollection())
    		return null;
    	
    	CollectionType cType = (CollectionType)type;
    	Type commonElementType = this.elemType().getLeastCommonSupertype(cType.elemType());
    	
    	if (commonElementType == null)
    		return null;
    	
    	if (type.isSet())
    		return TypeFactory.mkSet(commonElementType);
    	else
    		return TypeFactory.mkCollection(commonElementType);
    }
    
    public String toString() {
        return "Set(" + elemType() + ")";
    }
}
