/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.uml.sys.soil;


import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class AllTests {
	
	/**
	 * TODO
	 */
	private AllTests() {
		
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public static Test suite() {
		final TestSuite testSuite = new TestSuite("All system soil tests");
        testSuite.addTestSuite(StatementEffectTest.class);
        return testSuite;
    }
}
