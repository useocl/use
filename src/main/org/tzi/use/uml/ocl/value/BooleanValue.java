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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.ocl.value;

import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Boolean values.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class BooleanValue extends Value implements Comparable {
    /** 
     * Constant value representing true.
     */
    public static final BooleanValue TRUE = new BooleanValue(true);

    /** 
     * Constant value representing false.
     */
    public static final BooleanValue FALSE = new BooleanValue(false);

    private boolean fValue;
    
    /**
     * Don't let anyone outside this class instantiate new Boolean
     * objects. Use the static constants instead. 
     */
    private BooleanValue(boolean b) {
        super(TypeFactory.mkBoolean());
        fValue = b;
    }

    /**
     * Returns the appropriate static constant for a boolean
     * value. This may just simplify calling code a bit.
     */
    public static BooleanValue get(boolean b) {
        return ( b ) ? TRUE : FALSE;
    }

    public boolean value() {
        return fValue;
    }

    public boolean isFalse() {
        return ! fValue;
    }

    public boolean isTrue() {
        return fValue;
    }

    public String toString() {
        return ( fValue )? "true" : "false";
    }

    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof BooleanValue )
            return ((BooleanValue) obj).fValue == fValue;
        return false;
    }

    public int hashCode() {
        return fValue ? 1231 : 1237;
    }

    public int compareTo(Object o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue )
            return +1;
        if (! (o instanceof BooleanValue) )
            return toString().compareTo(o.toString());
        boolean b = ((BooleanValue) o).fValue;
        if (fValue == b )
            return 0;
        else if (fValue )
            return 1;
        else 
            return -1;
    }
}

