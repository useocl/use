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

$Id$

package org.tzi.use.uml.ocl.type;

import java.util.Set;

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
}

