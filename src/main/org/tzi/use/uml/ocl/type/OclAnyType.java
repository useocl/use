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

import java.util.HashSet;
import java.util.Set;

/**
 * The OclAny type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class OclAnyType extends TypeImpl {

	@Override
    public boolean isTypeOfOclAny() {
    	return true;
    }
    
    @Override
	public boolean isKindOfOclAny(VoidHandling h) {
		return true;
	}

	/** 
     * Returns the set of all supertypes (including this type).
     */
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>(1);
        res.add(this);
        return res;
    }

    /**
     * Returns true if the passed type is equal.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass().equals(getClass())) return true;
        return false;
    }

    public int hashCode() {
        return getClass().hashCode();
    }
    
    
    /** 
     * Returns a complete printable type name.
     */
    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append("OclAny");
    }

	@Override
	public boolean conformsTo(Type other) {
		return other.isTypeOfOclAny();
	}
}
