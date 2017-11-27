/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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
 * Unlimited natural values
 * @author Lars Hamann
 * @since 3.1
 *
 */
public class UnlimitedNaturalValue extends Value {
	// -1 is unlimited!
	int fValue;

	private UnlimitedNaturalValue(int n) {
		super(TypeFactory.mkUnlimitedNatural());
        fValue = n;
    }

    public int value() {
        return (fValue == -1 ? Integer.MAX_VALUE : fValue);
    }

    @Override
    public boolean isUnlimitedNatural() {
    	return true;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        return (fValue == -1 ? sb.append("*") : sb.append(fValue));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof UnlimitedNaturalValue )
        	return ((UnlimitedNaturalValue) obj).fValue == fValue;
        
        if (fValue == -1) return false;
        
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
        if (o instanceof UnlimitedNaturalValue) {
            int val2 = ((UnlimitedNaturalValue) o).fValue;
            return ( fValue < val2 ? -1 : ( fValue == val2 ? 0 : +1) );
        }
        
        if (fValue == -1) return +1;
        
        if (o instanceof IntegerValue) {
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
    
    private static final UnlimitedNaturalValue[] predefinedValues;
    
    /**
     * Single value for *
     */
    public static final UnlimitedNaturalValue UNLIMITED = new UnlimitedNaturalValue(-1);
    
    static {
    	predefinedValues = new UnlimitedNaturalValue[20];
    	for (int i = 0; i < predefinedValues.length; ++i) {
    		predefinedValues[i] = new UnlimitedNaturalValue(i);
    	}
    }
    
    public static UnlimitedNaturalValue valueOf(int integer) {
    	if (integer < 0) 
			throw new IllegalArgumentException("UnlimitedNaturals must be greater or equal than 0");
    	
    	if (integer >= 0 && integer < predefinedValues.length) {
    		return predefinedValues[integer];
    	}
    	return new UnlimitedNaturalValue(integer);
    }
}
