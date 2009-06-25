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

package org.tzi.use.uml.ocl.value;

import org.tzi.use.uml.ocl.type.Type;

/**
 * Abstract base class of all values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     Type
 */
public abstract class Value implements Comparable<Value> {

    /* All values must implement the Comparable interface. This is
       mainly for having a well-defined order for printing values,
       e.g. sets. It does not impose an order with respect to the
       abstract semantics of values, however. Note that all values
       must be able of being compared with an UndefinedValue. */

    private Type fType; // every value has a type
    
    protected Value(Type t) {
        fType = t;
    }

    public Type type() {
        return fType;
    }

    public void setType( Type t) {
        fType = t;
    }
    

    public boolean isInteger() {
        return (this instanceof IntegerValue);
    }

    public boolean isReal() {
        return (this instanceof RealValue);
    }

    public boolean isBoolean() {
        return (this instanceof BooleanValue);
    }

    public boolean isDefined() {
        return ! isUndefined();
    }

    public boolean isUndefined() {
        return (this instanceof UndefinedValue);
    }

    public boolean isCollection() {
        return (this instanceof CollectionValue);
    }

    public boolean isBag() {
        return (this instanceof BagValue);
    }

    public boolean isSet() {
        return (this instanceof SetValue);
    }

    public boolean isSequence() {
        return (this instanceof SequenceValue);
    }

    public boolean isObject() {
        return (this instanceof ObjectValue);
    }

    public boolean isOrderedSet() {
		return (this instanceof OrderedSetValue);
	}
    
    public abstract String toString();

    public String toStringWithType() {
        return toString() + " : " + fType;
    }

    public abstract int hashCode();

    /**
     * Returns true if two values are equal. Note that this method
     * implements our OCL semantics of equality. It correctly deals
     * with undefined values.
     */
    public abstract boolean equals(Object obj);
}

