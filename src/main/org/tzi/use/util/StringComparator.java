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

$Id$

package org.tzi.use.util;

import java.util.Comparator;

/** 
 * A StringComparator compares two strings according to the Comparator
 * interface. This is only necessary for the jdk1.1 collection
 * classes. In jdk1.2 the String class already implements the
 * Comparable interface. This StringComparator can be passed to
 * e.g. TreeMaps holding strings as keys.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class StringComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        return ((String) o1).compareTo((String) o2);
    }
}
