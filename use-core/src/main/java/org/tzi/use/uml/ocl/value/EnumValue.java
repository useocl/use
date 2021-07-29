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

import org.tzi.use.uml.ocl.type.EnumType;

/**
 * An enumeration value.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     org.tzi.use.uml.ocl.type.EnumType
 */
public final class EnumValue extends Value {
    private String fLiteral;

    public EnumValue(EnumType t, String literal) {
        super(t);
        fLiteral = literal;
        if (! t.contains(literal) )
            throw new IllegalArgumentException("literal `" + literal + 
                                               "' not part of enumeration type `" + t + "'");
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        return type().toString(sb).append("::").append(fLiteral);
    }

    public String value() {
        return fLiteral;
    }

    public boolean equals(Object obj) {
        if (obj == this )
            return true;

        if (obj instanceof EnumValue ) {
            EnumValue v = (EnumValue) obj;
            return type().equals(v.type()) && fLiteral.equals(v.fLiteral);
        }
        return false;
    }

    public int hashCode() {
        return fLiteral.hashCode();
    }

    public int compareTo(Value o) {
        if (o == this )
            return 0;
        if (o instanceof UndefinedValue )
            return +1;
        if (! (o instanceof EnumValue) )
            return toString().compareTo(o.toString());
        return fLiteral.compareTo(((EnumValue) o).fLiteral);
    }
}
