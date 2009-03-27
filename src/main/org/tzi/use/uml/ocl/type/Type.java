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
    public abstract Set allSupertypes();

    // The following set of functions is a rather ugly solution, but
    // it avoids numerous instanceof tests in user code.

    public boolean isNumber() {
        return (this instanceof IntegerType)
            || (this instanceof RealType);
    }

    public boolean isInteger() {
        return (this instanceof IntegerType);
    }

    public boolean isReal() {
        return (this instanceof RealType);
    }

    public boolean isString() {
        return (this instanceof StringType);
    }

    public boolean isBoolean() {
        return (this instanceof BooleanType);
    }

    public boolean isEnum() {
        return (this instanceof EnumType);
    }

    public boolean isCollection() {
        return (this instanceof CollectionType);
    }

    public boolean isTrueCollection() {
        return (this.getClass() == CollectionType.class);
    }

    public boolean isSetBagOrSequence() {
        return (this instanceof SetType)
            || (this instanceof BagType)
            || (this instanceof SequenceType);
    }

    public boolean isSet() {
        return (this instanceof SetType);
    }

    public boolean isSequence() {
        return (this instanceof SequenceType);
    }

    public boolean isBag() {
        return (this instanceof BagType);
    }

    public boolean isObjectType() {
        return (this instanceof ObjectType);
    }

    public boolean isOclAny() {
        return (this instanceof OclAnyType);
    }

    public boolean isTupleType() {
        return (this instanceof TupleType);
    }
    
	public static Type leastCommonSupertype(Type[] types) throws ExpInvalidException {
		if (types.length == 0 )
            return new VoidType();

        // easy case: one or more elements of equal type
        boolean sameTypes = true;
        for (int i = 1; i < types.length; i++)
            if (! types[0].equals(types[i]) ) {
                sameTypes = false;
                break;
            }
        if (sameTypes )
            return types[0];

        // determine common supertypes = intersection of all
        // supertypes of all elements
        Set cs = new HashSet();
        cs.addAll(types[0].allSupertypes());
        for (int i = 1; i < types.length; i++) {
            cs.retainAll(types[i].allSupertypes());
            // return immediately if intersection is empty
            if (cs.isEmpty() )
                throw new ExpInvalidException("Type mismatch, element " + 
                                              (i + 1) +
                                              " does not have a common supertype " + 
                                              "with previous elements.");
        }
        // System.err.println("*** common supertypes: " + cs);

        // determine the least common supertype

        // if there is only one common supertype return it
        if (cs.size() == 1 ) 
            return (Type) cs.iterator().next();

        // search for a type that is less than or equal to all other types
        types[0] = null;
        Iterator it1 = cs.iterator();
        outerLoop: 
        while (it1.hasNext() ) {
            Type t1 = (Type) it1.next();
            Iterator it2 = cs.iterator();
            while (it2.hasNext() ) {
                Type t2 = (Type) it2.next();
                if (! t1.isSubtypeOf(t2) )
                    continue outerLoop;
            }
            types[0] = t1;
            break;
        }
        // System.err.println("*** least common supertype: " + t0);
        if (types[0] != null )
            return types[0];
        
        return null;
	}

}

