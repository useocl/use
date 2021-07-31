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
import junit.framework.TestCase;


/**
 * Test MMultiplicity.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     MMultiplicity
 */

public class MMultiplicityTest extends TestCase {

    public void test1() {
        // env.printHeader("testing MMultiplicity...");

        MMultiplicity mult = MMultiplicity.ZERO_MANY;
        assertEquals(true, mult.contains(0));
        assertEquals(true, mult.contains(2000));

        mult = new MMultiplicity(3, 8);
        assertEquals(false, mult.contains(0));
        assertEquals(true, mult.contains(3));
        assertEquals(true, mult.contains(4));
        assertEquals(true, mult.contains(8));
        assertEquals(false, mult.contains(9));

        mult.addRange(11, 14);
        assertEquals(true, mult.contains(3));
        assertEquals(true, mult.contains(12));
    }
}
