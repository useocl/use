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

import java.util.List;

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

    @Override
    public boolean isCollection(boolean excludeVoid) {
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

    public Type getLeastCommonSupertype(Type type)
    {
    	if (!type.isCollection(false))
    		return null;
    	
    	if (type.isVoidType())
    		return this;
    	
    	CollectionType cType = (CollectionType)type;
    	Type commonElementType = this.fElemType.getLeastCommonSupertype(cType.fElemType);

    	if (commonElementType == null)
    		return null;
    	else
    		return TypeFactory.mkCollection(commonElementType);
    }
        
    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Collection(");
        elemType().toString(sb);
        return sb.append(")");
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

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.type.Type#initOrderedSuperTypes(java.util.List)
	 */
    /** 
     * If this collection has type Collection(T) all
     * types Collection(T') where T' <= T are added.
     */
	@Override
	protected void getOrderedSuperTypes(List<Type> allSupertypes) {
        for (Type t : fElemType.allSupertypesOrdered()) {
            allSupertypes.add(TypeFactory.mkCollection(t));
        }
	}
    
}
