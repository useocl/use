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

package org.tzi.use.uml.ocl.type;

import java.util.HashSet;
import java.util.Set;

/**
 * The OCL type UnlimitedNatural
 * @author Lars Hamann
 * @since 3.1
 */
public class UnlimitedNaturalType extends BasicType {
	UnlimitedNaturalType() {
        super("UnlimitedNatural");
    }
    
    @Override
	public boolean isKindOfNumber(VoidHandling h) {
    	return true;
    }
    
    @Override
	public boolean isKindOfInteger(VoidHandling h) {
    	return true;
    }
    
    @Override
	public boolean isKindOfUnlimitedNatural(VoidHandling h) {
    	return true;
    }
    
    @Override
	public boolean isTypeOfUnlimitedNatural() {
    	return true;
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    @Override
	public boolean conformsTo(Type t) {
        return !t.isTypeOfVoidType() && (t.isKindOfNumber(VoidHandling.EXCLUDE_VOID) || t.isTypeOfOclAny());
    }

    /** 
     * Returns the set of all supertypes (including this type).
     */
    @Override
	public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<Type>(4);
        res.add(TypeFactory.mkOclAny());
        res.add(TypeFactory.mkReal());
        res.add(TypeFactory.mkInteger());
        res.add(this);
        return res;
    }
}
