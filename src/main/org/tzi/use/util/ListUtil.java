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

package org.tzi.use.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class with useful class operations for modifying Lists.
 *
 * @author  Joern Bohling
 */
public final class ListUtil {

    // no instances
    private ListUtil() {}

    private static <T> void combine(Stack<T> elementStack,
                                    List<List<T>> listWithLists,
                                    List<List<T>> combinations) {
        if (elementStack.size() == listWithLists.size()) {
            combinations.add( new ArrayList<T>(elementStack));
        } else {
            List<T> currentList = listWithLists.get(elementStack.size());

            for (T item : currentList) {
                elementStack.push( item );
                combine( elementStack, listWithLists, combinations );
                elementStack.pop();
            }
        }
    }

    /**
     * Computes of a given List with Lists the combinations.
     * Example: Given  { { a, b } { 1, 2, 3} { p, q} }
     * Result is: { {a,1,p} {a,1,q} {a,2,p} {a,2,q} {a,3,p}
     *              ... {b,2,q} {b,3,p} {b,3,q} }
     */
    public static <T> List<List<T>> combinations( List<List<T>> listWithLists ) {
        // the elements of listWithLists must be Lists
        List<List<T>> combinations = new ArrayList<List<T>>();
        Stack<T> elementStack = new Stack<T>();
        
        combine( elementStack, listWithLists, combinations );
        return combinations;
    }
}