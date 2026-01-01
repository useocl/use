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

package org.tzi.use.uml.ocl.type;

import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Collection is the abstract base class for set, sequence, and bag.
 *
 * @author  Mark Richters
 * @see     SetType
 * @see     SequenceType
 * @see     BagType
 */
public class CollectionType extends TypeImpl {

    private final Type fElemType;

    protected CollectionType(Type elemType) {
        fElemType = elemType;
    }

    public Type elemType() {
        return fElemType;
    }

    @Override
    public boolean isKindOfCollection(VoidHandling h) {
    	return true;
    }
    
    @Override
    public boolean isTypeOfCollection() {
    	return true;
    }
    
    @Override
	public boolean conformsTo(IType other) {
        if (other == null) {
            return false;
        }

        // If other is a low-level OCL Type, perform precise check
        if (other instanceof Type) {
            Type o = (Type) other;
            if (!o.isTypeOfCollection())
                return false;

            CollectionType t2 = (CollectionType) o;
            return fElemType.conformsTo(t2.elemType());
        }

        // other is only API-level IType: conservatively accept if it is a collection
        return other.isTypeOfCollection();
    }

    /**
     * Returns the set of all supertypes (including this type).  If
     * this collection has type Collection(T) the result is the set of
     * all types Collection(T') where T' &lt;= T.
     */
    @Override
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<>();
        for (org.tzi.use.uml.api.IType it : fElemType.allSupertypes()) {
            if (it instanceof Type) {
                Type t = (Type) it;
                res.add(TypeFactory.mkCollection(t));
            }
        }
        return res;
    }

    @Override
    public Type getLeastCommonSupertype(Type type)
    {
    	if (!type.isKindOfCollection(IType.VoidHandling.INCLUDE_VOID))
    		return null;
    	
    	if (type.isTypeOfVoidType())
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == getClass() )
            return ((CollectionType) obj).elemType().equals(elemType());
        return false;
    }

    @Override
    public int hashCode() {
        return fElemType.hashCode();
    }
    
    @Override
    public boolean isVoidOrElementTypeIsVoid() {
		return elemType().isVoidOrElementTypeIsVoid();
	}

	/**
	 * Creates a collection type with the same
	 * kind of collection and the given element type.
	 * <p>
	 * <code>
	 * Collection(OclAny).createCollectionType(String) => Collection(String)<br/>
	 * Set(OclAny).createCollectionType(String) => Set(String)
	 * </code>
	 * </p>
	 * @param t The new type of the elements
	 * @return
	 */
	public Type createCollectionType(Type t) {
		return TypeFactory.mkCollection(t);
	}

	/**
	 * Creates a new collection value of this type, if possible.
	 * Note: The type <code>Collection</code> cannot be instantiated. 
	 * @param values The values of the elements of the collection. 
	 * @return
	 * @throws UnsupportedOperationException If called on CollectionType.
	 */
	public CollectionValue createCollectionValue(List<Value> values) {
		throw new UnsupportedOperationException("The abstract type " + StringUtil.inQuotes("Collection") + " cannot be instantiated.");
	}
	
	public CollectionValue createCollectionValue(Value[] values) {
		throw new UnsupportedOperationException("The abstract type " + StringUtil.inQuotes("Collection") + " cannot be instantiated.");
	}
}
