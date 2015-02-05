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
 * Determines the unique least common super-type for a set of types.
 * @author Fabian
 *
 */
public class UniqueLeastCommonSupertypeDeterminator {

	public Type calculateFor(Set<Type> types) {
		if (types.isEmpty()) 
			return TypeFactory.mkVoidType();
		
		if (types.size() == 1) {
			return types.iterator().next();
		}
		
		//TODO:  The first two steps can be optimized
		
		// First step: Determine the set of common super-types of all elements
    	Set<Type> allSuperTypes = new HashSet<Type>();
    	for(Type t : types) {
			if (t.isVoidOrElementTypeIsVoid()) 
				allSuperTypes.add(t); 
			else 
				allSuperTypes.addAll(t.allSupertypes());
		}
    	    	
    	// Second step: Select those that are common to all others 
    	Set<Type> allCommonSuperTypes = new HashSet<Type>();
    	for (Type t : allSuperTypes) {
    		if (typeIsSupertypeOfAll(t,types)) 
    			allCommonSuperTypes.add(t);
    	}
    	
		// Third step: Find the most specific-one that is comparable to all others
    	Type result = null;
    	for (Type t : allCommonSuperTypes) {
			if (typeIsComparableToAll(t,allCommonSuperTypes)) {
				if (result == null) { 
					result = t;
				}
				else if (t.conformsTo(result)) {
					result = t;
				}
			}
		}
		return result;
	}
	
	/**
     * Determines whether t is either sub-type or super-type of each element in allSuperTypes.
	 * @param t
	 * @param allSuperTypes
	 * @return
	 */
	private boolean typeIsComparableToAll(Type t, Set<Type> allSuperTypes) {
		for (Type t1 : allSuperTypes) {
			if (! (t1.conformsTo(t) || t.conformsTo(t1)))  
				return false;
		}
		return true;
	}

	/**
	 * Determines whether t is a super-type of each element in types.
	 * @param t
	 * @param types
	 * @return
	 */
	private boolean typeIsSupertypeOfAll(Type t, Set<Type> types) {
		for (Type t1 : types) {
			if (! t1.conformsTo(t)) {
				return false;
			}
		}
		return true;
	}
}
