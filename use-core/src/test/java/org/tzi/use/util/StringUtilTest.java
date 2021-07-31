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
import junit.framework.TestCase;

/**
 * Test StringUtil class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public class StringUtilTest extends TestCase {

    public StringUtilTest(String name) {
        super(name);
    }

    public void testNthIndexOf() {
        assertEquals(-1, StringUtil.nthIndexOf("abbbb", 0, "bb"));
        assertEquals(1, StringUtil.nthIndexOf("abbbb", 1, "bb"));
        assertEquals(3, StringUtil.nthIndexOf("abbbb", 2, "bb"));
        assertEquals(-1, StringUtil.nthIndexOf("abbbb", 3, "bb"));
        assertEquals(7, StringUtil.nthIndexOf("abbbbaabb", 3, "bb"));
        assertEquals(-1, StringUtil.nthIndexOf("abbbb", 0, 'b'));
        assertEquals(1, StringUtil.nthIndexOf("abbbb", 1, 'b'));
        assertEquals(3, StringUtil.nthIndexOf("abbbb", 3, 'b'));
    }

    public void testPad() {
        assertEquals("a", StringUtil.pad("a", 1));
        assertEquals("a ", StringUtil.pad("a", 2));
    }

    public void testCenter() {
        assertEquals(" a ", StringUtil.center("a", 3));
        assertEquals(" a ", StringUtil.center("a", 3));
    }

    public void testEscapeChar() {
        assertEquals("a", StringUtil.escapeChar('a', '"'));
        assertEquals("\\344", StringUtil.escapeChar('\344', '"'));
        assertEquals("\\u1234", StringUtil.escapeChar('\u1234', '"'));
        assertEquals("\\t", StringUtil.escapeChar('\t', '"'));
    }
}
