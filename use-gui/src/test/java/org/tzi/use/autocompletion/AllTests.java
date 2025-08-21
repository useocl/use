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

package org.tzi.use.autocompletion;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all test in package <code>org.tzi.use.graph</code>.
 *
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class AllTests {

    private AllTests(){}

    public static Test suite() {
        final TestSuite test = new TestSuite( "All auto completion tests" );
        test.addTestSuite( AutoCompletionSuggestionTest.class );
        test.addTestSuite(AutoCompletionParserTest.class);
        return test;
    }
}
