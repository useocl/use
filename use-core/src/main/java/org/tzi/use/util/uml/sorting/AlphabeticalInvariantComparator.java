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

import org.tzi.use.uml.mm.MClassInvariant;

/**
 *
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 * @version $ProjectVersion: 0.393 $
 */
public class AlphabeticalInvariantComparator implements Comparator<MClassInvariant> {
    private final boolean classNameSortsFirst;

    /**
     * Constructor of AlphabeticalInvariantByClassComparator
     */
    public AlphabeticalInvariantComparator(boolean classNameSortsFirst) {
    	this.classNameSortsFirst = classNameSortsFirst;
    }

    /**
     * Compares two invariants by the name of their class
     * @param object1 first invariant (has to be of type MClassInvariant)
     * @param object2 second invariant (has to be of type MClassInvariant)
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    public int compare(final MClassInvariant object1, final MClassInvariant object2) {
    	if (classNameSortsFirst) {
    		int result = object1.cls().name().compareToIgnoreCase(object2.cls().name());
    		if (result != 0) {
    			return result;
    		}
    	}
    	
    	return object1.name().compareToIgnoreCase(object2.name());
    }
}
