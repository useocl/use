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
import org.tzi.use.util.BufferedToString;

/**
 * <p>Abstract base class of all values.</p>
 * <p>All values must implement the <code>Comparable</code> interface.
 * This is mainly for having a well-defined order for printing values,
 * e.g. sets. It does not impose an order with respect to the
 * abstract semantics of values, however. Note that all values
 * must be able of being compared with an <code>UndefinedValue</code>.</p>
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     Type
 */
public abstract class Value implements Comparable<Value>, BufferedToString {

    private Type fType; // every value has a type
    
    protected Value(Type t) {
        fType = t;
    }

    public Type type() {
        return fType;
    }

    public Type getRuntimeType() {
    	return fType;
    }
    
    /**
     * True if value is an <code>{@link IntegerValue}</code> 
     * @return True if value is an instance of <code>{@link IntegerValue}</code>
     */
    public boolean isInteger() {
        return false;
    }

    /**
     * True if value is an <code>{@link UnlimitedNaturalValue}</code> 
     * @return True if value is an instance of <code>{@link UnlimitedNaturalValue}</code>
     */
    public boolean isUnlimitedNatural() {
    	return false;
    }
    
    /**
     * True if value is a <code>{@link RealValue}</code> 
     * @return True if value is an instance of <code>{@link RealValue}</code>
     */
    public boolean isReal() {
        return false;
    }

    /**
     * True if value is a <code>{@link BooleanValue}</code> 
     * @return True if value is an instance of <code>{@link BooleanValue}</code>
     */
    public boolean isBoolean() {
        return false;
    }

    public boolean isDefined() {
        return ! isUndefined();
    }

    /**
     * True if value is an <code>{@link UndefinedValue}</code> 
     * @return True if value is an instance of <code>{@link UndefinedValue}</code>
     */
    public boolean isUndefined() {
        return false;
    }

    /**
     * True if value is an instance of <code>{@link CollectionValue}</code> 
     * @return True if value is an instance of <code>{@link CollectionValue}</code>
     */
    public boolean isCollection() {
        return false;
    }

    /**
     * True if value is an instance of <code>{@link BagValue}</code> 
     * @return True if value is an instance of <code>{@link BagValue}</code>
     */
    public boolean isBag() {
        return false;
    }

    /**
     * True if value is an instance of <code>{@link SetValue}</code> 
     * @return True if value is an instance of <code>{@link SetValue}</code>
     */
    public boolean isSet() {
        return false;
    }

    /**
     * True if value is an instance of <code>{@link SequenceValue}</code> 
     * @return True if value is an instance of <code>{@link SequenceValue}</code>
     */
    public boolean isSequence() {
        return false;
    }

    /**
     * True if value is an instance of <code>{@link OrderedSetValue}</code> 
     * @return True if value is an instance of <code>{@link OrderedSetValue}</code>
     */
    public boolean isOrderedSet() {
		return false;
	}
    
    /**
     * True if value is an instance of <code>{@link ObjectValue}</code> 
     * @return True if value is an instance of <code>{@link ObjectValue}</code>
     */
    public boolean isObject() {
        return false;
    }

    /**
     * True if value is an instance of <code>{@link LinkValue}</code> 
     * @return True if value is an instance of <code>{@link LinkValue}</code>
     */
	public boolean isLink() {
		return false;
	}
	
    @Override
	public final String toString() {
    	StringBuilder sb = new StringBuilder();
    	this.toString(sb);
    	return sb.toString();
    }

    @Override
    public abstract StringBuilder toString(StringBuilder sb);
    
    public String toStringWithType() {
    	StringBuilder sb = new StringBuilder();
    	this.toStringWithType(sb);
    	return sb.toString();
    }

    public void toStringWithType(StringBuilder sb) {
        this.toString(sb);
        sb.append(" : ");
        getRuntimeType().toString(sb);
    }
    
    @Override
	public abstract int hashCode();

    /**
     * Returns true if two values are equal. Note that this method
     * implements our OCL semantics of equality. It correctly deals
     * with undefined values.
     */
    @Override
	public abstract boolean equals(Object obj);
    
    /**
     * This operation is needed by some external tools
     * that require a more loose interpretation of
     * static typed.
     */
    public void setTypeToRuntimeType() {
    	this.fType = getRuntimeType();
    }
}

