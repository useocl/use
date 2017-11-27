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
import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

/**
 * Test Report class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public class ReportTest extends TestCase {

    public void test1() {
        Report r = new Report(4, "[ $c = $r, $r, $l ]");
        r.addRuler('-');
        r.addRow();
        r.addCell("foo");
        r.addCell(Integer.valueOf(3));
        r.addCell(Double.valueOf(1.2));
        r.addCell(Boolean.FALSE);

        r.addRow();
        r.addCell("foobar");
        r.addCell(Integer.valueOf(453453));
        r.addCell(Double.valueOf(-1.245345345));
        r.addCell(Boolean.TRUE);

        r.addRow();
        r.addCell("line");
        r.addCell(Integer.valueOf(555));
        r.addCell(Double.valueOf(999.0));
        r.addCell(Boolean.TRUE);
        r.addRuler('=');

        StringWriter sw1 = new StringWriter();
        PrintWriter p1 = new PrintWriter(sw1);
        r.printOn(p1);
        p1.flush();
        StringWriter sw2 = new StringWriter();
        PrintWriter p2 = new PrintWriter(sw2);
        p2.println("----------------------------------------");
        p2.println("[  foo   =      3,          1.2, false ]");
        p2.println("[ foobar = 453453, -1.245345345, true  ]");
        p2.println("[  line  =    555,        999.0, true  ]");
        p2.println("========================================");
        p2.flush();
        assertEquals(sw1.toString(), sw2.toString());
    }
}
