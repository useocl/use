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

package org.tzi.use.gui.views.diagrams.util;

import com.gargoylesoftware.base.testing.EqualsTester;
import junit.framework.TestCase;

/**
 * Test DirectedLine class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  green
 * @see     DirectedLine
 */

public class DirectedLineTest extends TestCase {
    public void testEquals() {

        DirectedLine orig = 
            (DirectedLine)DirectedLineFactory.createSolidDirectedLine(10,20,30,40);
        DirectedLine equal = 
            (DirectedLine)DirectedLineFactory.createSolidDirectedLine(10,20,30,40);
        DirectedLine differentButSameClass = 
            (DirectedLine)DirectedLineFactory.createSolidDirectedLine(40,30,20,10);
        DashedDirectedLine sameValuesFromSubClass = 
            (DashedDirectedLine)DirectedLineFactory.createDashedDirectedLine(10,20,30,40);
        new EqualsTester(orig, equal, differentButSameClass, sameValuesFromSubClass);
    }
} 
