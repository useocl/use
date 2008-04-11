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

package org.tzi.use.util;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Test FilterIterator class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public class FilterIteratorTest extends TestCase {

    public FilterIteratorTest(String name) {
        super(name);
    }

    protected ArrayList fList;

    protected void setUp() {
        fList = new ArrayList();
        for (int i = 0; i < 6; i++)
            fList.add(new Integer(i));
    }

    protected void tearDown() {
        fList.clear();
    }

    public void testModulo2Filter() {
        Iterator iter = new FilterIterator(fList.iterator(), new UnaryPredicate() {
                public boolean isTrue(Object obj) {
                    return ((Integer) obj).intValue() % 2 == 0;
                }
            });

        int i = 0;
        while (iter.hasNext()) {
            assertEquals(new Integer(i), iter.next());
            i += 2;
        }
        assertEquals(6, i);
    }

    public void testFalseFilter() {
        Iterator iter = new FilterIterator(fList.iterator(), new UnaryPredicate() {
                public boolean isTrue(Object obj) {
                    return false;
                }
            });

        assertFalse("should not pass any elements", iter.hasNext());
    }

    public void testTrueFilter() {
        Iterator iter = new FilterIterator(fList.iterator(), new UnaryPredicate() {
                public boolean isTrue(Object obj) {
                    return true;
                }
            });

        int i = 0;
        while (iter.hasNext()) {
            assertEquals(new Integer(i), iter.next());
            i++;
        }
        assertEquals(6, i);
    }
}
