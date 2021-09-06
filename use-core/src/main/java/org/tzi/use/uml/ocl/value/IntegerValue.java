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

import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Integer values.
 *
 * @author  Mark Richters
 * @see     RealValue
 */
public final class IntegerValue extends Value {
    final int fValue;
    
    /**
     * Constructs a new IntegerValue with the value of <code>n</code>
     * @deprecated Use {@link IntegerValue#valueOf(int)} instead, 
     *             because it uses caching for some commonly used values to reduce
     *             the overall amount of objects.
     * @param n
     */
    @Deprecated
	public IntegerValue(int n) {
        super(TypeFactory.mkInteger());
        fValue = n;
    }

    public int value() {
        return fValue;
    }

    @Override
    public boolean isInteger() {
    	return true;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append(fValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof IntegerValue )
            return ((IntegerValue) obj).fValue == fValue;
        else if (obj instanceof RealValue )
            return ((RealValue) obj).fValue == fValue;
        return false;
    }

    @Override
    public int hashCode() {
        // Note: this must be the same hash code as for RealValue if
        // we want to treat, e.g., 1.0 equal to 1 in collections.
        return Double.valueOf(fValue).hashCode();
    }

    @Override
    public int compareTo(Value o) {
        if (o == this )
            return 0;
        if (o instanceof IntegerValue ) {
            int val2 = ((IntegerValue) o).fValue;
            return ( fValue < val2 ? -1 : ( fValue == val2 ? 0 : +1) );
        } else if (o instanceof RealValue ) {
            double val2 = ((RealValue) o).fValue;
            return ( fValue < val2 ? -1 : ( fValue == val2 ? 0 : +1) );
        } else if (o instanceof UndefinedValue ) {
            return +1;
        } else
            return toString().compareTo(o.toString());
    }
    
    private static final IntegerValue[] predefinedValues;
    static {
    	predefinedValues = new IntegerValue[20];
    	for (int i = 0; i < predefinedValues.length; ++i) {
    		predefinedValues[i] = new IntegerValue(i);
    	}
    }
    
    public static IntegerValue valueOf(int integer) {
    	if (integer >= 0 && integer < predefinedValues.length) {
    		return predefinedValues[integer];
    	}
    	return new IntegerValue(integer);
    }
}
