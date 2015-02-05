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

/**
 * Abstract base class for basic types (Integer, Real, Boolean, and
 * String).
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public abstract class BasicType extends TypeImpl {

    private final String fTypename;

    protected BasicType(String t) {
        fTypename = t;
    }
    
    @Override
    public boolean isKindOfOclAny(VoidHandling h) {
    	return true;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append(fTypename);
    }

    /**
     * Returns true if the passed type is equal.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        // all instances of a basic type are equal
        return obj.getClass().equals(getClass());
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}
