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

import org.tzi.use.uml.mm.MNamedElement;

/**
 *
 * @author Lars Hamann
 * 
 */
public class AlphabeticalNamedElementComparator implements Comparator<MNamedElement> {

    /**
     * Compares two associations by their name
     * @param object1 first association (has to be of type MAssociation)
     * @param object2 second association (has to be of type
     * MAssociation)
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    public int compare(final MNamedElement object1, final MNamedElement object2) {
        final String nameOfObject1 = object1.name();
        final String nameOfObject2 = object2.name();
        return nameOfObject1.compareToIgnoreCase(nameOfObject2);
    }
}
