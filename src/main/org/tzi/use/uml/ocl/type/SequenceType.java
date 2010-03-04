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
 * The OCL Sequence type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetType
 * @see     BagType
 */
public final class SequenceType extends CollectionType {

    public SequenceType(Type elemType) {
        super(elemType);
    }
    
    public boolean isInstantiableCollection() {
    	return true;
    }

    public String shortName() {
        if (elemType().isCollection() )
            return "Sequence(...)";
        else 
            return "Sequence(" + elemType() + ")";
    }

    public boolean isTrueCollection() {
    	return false;
    }
    
    public boolean isSequence() {
    	return true;
    }
    
    public Type getLeastCommonSupertype(Type type)
    {
    	if (!type.isCollection())
    		return null;
    	
    	if (type.isVoidType())
    		return this;
    	
    	CollectionType cType = (CollectionType)type;
    	Type commonElementType = this.elemType().getLeastCommonSupertype(cType.elemType());
    	
    	if (commonElementType == null) 
    		return null;
    	
    	if (type.isSequence())
    		return TypeFactory.mkSequence(commonElementType);
    	else
    		return TypeFactory.mkCollection(commonElementType);
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
        if (! t.isTrueCollection() && ! t.isSequence() )
            return false;

        CollectionType t2 = (CollectionType) t;
        if (elemType().isSubtypeOf(t2.elemType()) )
            return true;
        return false;
    }

    /** 
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Sequence(T) the result is the set of
     * all types Sequence(T') and Collection(T') where T' <= T.
     */
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>();
        res.addAll(super.allSupertypes());
        Set<Type> elemSuper = elemType().allSupertypes();
        Iterator<Type> typeIter = elemSuper.iterator();
        
        while (typeIter.hasNext() ) {
            Type t = typeIter.next();
            res.add(TypeFactory.mkSequence(t));
        }
        return res;
    }

    public String toString() {
        return "Sequence(" + elemType() + ")";
    }
}
