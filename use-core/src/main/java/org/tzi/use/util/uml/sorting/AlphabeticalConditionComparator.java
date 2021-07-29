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

package org.tzi.use.util.uml.sorting;

import java.util.Comparator;

import org.tzi.use.uml.mm.MPrePostCondition;

/**
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author Lars Hamann
 * @author Frank Hilken
 */
public class AlphabeticalConditionComparator implements Comparator<MPrePostCondition> {
	
	private final boolean includeConditionType;
	
    /**
	 * @param includeConditionType
	 */
	public AlphabeticalConditionComparator(boolean includeConditionType) {
		super();
		this.includeConditionType = includeConditionType;
	}

	/**
     * Compares two Pre-/Postconditions first by their condition type
     * (pre or post)then by their operation name and afterwards by their name.
     * 
     * @param object1 first Pre-/Postcondition
     * @param object2 second Pre-/Postcondition
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    public int compare( final MPrePostCondition firstCondition, final MPrePostCondition secondCondition ) {        
        
    	if (includeConditionType) {
	    	if (firstCondition.isPre() && !secondCondition.isPre()) {
	        	return -1;
	        } else if (!firstCondition.isPre() && secondCondition.isPre()) {
	        	return 1;
	        }
    	}
        
        int result = firstCondition.operation().name().compareTo(secondCondition.operation().name());
        if (result != 0) {
        	return result;
        }
        
        return firstCondition.name().compareTo(secondCondition.name());
    }
}
