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
 * String values.
 *
 * @author  Mark Richters
 */
public final class StringValue extends Value {
    private final String fValue;
    
    public StringValue(String s) {
        super(TypeFactory.mkString());
        fValue = s;
    }

    public String value() {
        return fValue;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append("'").append(fValue).append("'");
    }

    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof StringValue )
            return ((StringValue) obj).fValue.equals(fValue);
        return false;
    }

    public int hashCode() {
        return fValue.hashCode();
    }

    public int compareTo(Value o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue )
            return +1;
        if (! (o instanceof StringValue) )
            return toString().compareTo(o.toString());
        return fValue.compareTo(((StringValue) o).fValue);
    }
}

