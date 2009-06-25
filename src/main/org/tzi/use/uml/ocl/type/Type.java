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

import org.tzi.use.uml.ocl.expr.ExpInvalidException;

/**
 * Abstract base class of all types. Types should be created only by
 * the TypeFactory class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public abstract class Type {

    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public abstract boolean isSubtypeOf(Type t);

    /** 
     * Overwrite this method to return a short printable name of
     * a type (e.g. 'Set(...)') rather than a full type name.
     */
    public String shortName() {
        return toString();
    }

    /** 
     * Returns a complete printable type name, e.g. 'Set(Bag(Integer))'. 
     */
    public abstract String toString();


    /** 
     * Overwrite to determine equality of types.
     */
    public abstract boolean equals(Object obj);

    /** 
     * Always define hashCode when defining equals().
     */
    public abstract int hashCode();

    /** 
     * Returns the set of all supertypes (including this type).
     */
    public abstract Set<Type> allSupertypes();

    // The following set of functions is a rather ugly solution, but
    // it avoids numerous instanceof tests in user code.
    // Corresponding Subtypes override these methods and return true
    public boolean isNumber() {
    	return false;
    }

    public boolean isInteger() {
    	return false;
    }

    public boolean isReal() {
    	return false;
    }

    public boolean isString() {
    	return false;
    }

    public boolean isBoolean() {
    	return false;
    }

    public boolean isEnum() {
    	return false;
    }

    public boolean isCollection() {
    	return false;
    }

    public boolean isTrueCollection() {
    	return false;
    }

    public boolean isSetBagOrSequence() {
        return (this.isSet())
            || (this.isBag())
            || (this.isSequence());
    }

    public boolean isSet() {
    	return false;
    }

    public boolean isSequence() {
    	return false;
    }

    public boolean isOrderedSet() {
		return false;
	}
    
    public boolean isBag() {
    	return false;
    }

    public boolean isObjectType() {
    	return false;
    }

    public boolean isOclAny() {
    	return false;
    }

    public boolean isTupleType() {
    	return false;
    }
    
    public boolean isVoidType() {
    	return false;
    }
    
    public boolean isDate() {
    	return false;
    }
    
    /**
     * Returns the least common supertype to the given Type
     * @param type
     * @return
     * @throws ExpInvalidException
     */
	public Type getLeastCommonSupertype(Type type) {
		if (type == null )
            return this;

        // easy case: equal type
        if (this.equals(type))
        	return type;

        // one of the types (this or type) is oclVoid
        if (this.isVoidType())
        	return type;
        
        if (type.isVoidType())
        	return this;
        
        // determine common supertypes = intersection of all
        // supertypes of all elements
        Set<Type> cs = new HashSet<Type>();
        cs.addAll(this.allSupertypes());
        cs.retainAll(type.allSupertypes());
        
        // return immediately if intersection is empty
        if (cs.isEmpty() )
        	return null;
        
        // determine the least common supertype
        // if there is only one common supertype return it
        if (cs.size() == 1 ) 
            return cs.iterator().next();

        // search for a type that is less than or equal to all other types
        Type cType = null;
        Iterator<Type> it1 = cs.iterator();
        outerLoop: 
        while (it1.hasNext() ) {
            Type t1 = it1.next();
            Iterator<Type> it2 = cs.iterator();
            
            while (it2.hasNext() ) {
                Type t2 = it2.next();
                if (! t1.isSubtypeOf(t2) )
                    continue outerLoop;
            }
            
            cType = t1;
            break;
        }
    
        return cType;
	}
}

