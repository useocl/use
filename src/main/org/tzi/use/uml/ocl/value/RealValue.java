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
import org.tzi.use.util.FloatUtil;

/**
 * Real values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     IntegerValue
 */
public final class RealValue extends Value {
    final double fValue;
    
    public RealValue(double d) {
        super(TypeFactory.mkReal());
        fValue = d;
    }

    public double value() {
        return fValue;
    }

    @Override
    public boolean isReal() {
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
        else if (obj instanceof RealValue )
            return FloatUtil.equals(((RealValue) obj).fValue, fValue);
        else if (obj instanceof IntegerValue )
            return ((IntegerValue) obj).fValue == fValue;
        return false;
    }

    @Override
    public int hashCode() {
        // Note: this must be the same hash code as for IntegerValue
        // if we want to treat, e.g., 1.0 equal to 1 in collections.
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
            return Double.valueOf(fValue).compareTo(Double.valueOf(val2));
        } else if (o instanceof UndefinedValue ) {
            return +1;
        } else
            return toString().compareTo(o.toString());
    }
}
