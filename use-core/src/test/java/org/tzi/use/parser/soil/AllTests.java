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

package org.tzi.use.parser.soil;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * All soil parser tests
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class AllTests {
	
	private AllTests() {}
	
	
	/**
	 * Returns a new <code>TestSuite</code> containing all
	 * soil parser tests.
	 * @return
	 */
	public static Test suite() {
		final TestSuite testSuite = new TestSuite("All soil parser tests");
        testSuite.addTestSuite(ASTConstructionTest.class);
        testSuite.addTestSuite(StatementGenerationTest.class);
        return testSuite;
    }
}
