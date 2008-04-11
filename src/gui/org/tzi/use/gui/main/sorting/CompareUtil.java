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

package org.tzi.use.gui.main.sorting;

/**
 *
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 * @version $ProjectVersion: 0.393 $
 */
public interface CompareUtil {
    /**
     * Basic comparison operation to compare two integers
     * @param firstInt first integer
     * @param secondInt second integer
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    int compareInt(int firstInt, int secondInt);

    /**
     * Basic comparison operation to compare two strings
     * @param firstString first string
     * @param secondString second string
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    int compareString(String firstString, String secondString);
}
