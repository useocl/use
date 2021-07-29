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

package org.tzi.use.uml.mm;

public class MAggregationKind {

    // utility class
    private MAggregationKind() {}
    

    /**
     * The different possible kinds of associations.
     */
    public static final int NONE        = 0;
    public static final int AGGREGATION = 1;
    public static final int COMPOSITION = 2;

    private static final String[] fNames 
        = { "association", "aggregation", "composition" };
    /**
     * Returns a string representation of the enumeration values.
     */
    public static final String name(int k) { 
        return fNames[k];
    }
    
    public static final boolean isValid(int k) {
    	return k >= 0 && k <= 2;
    }
}
