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

package org.tzi.use.util.input;

import java.io.IOException;

/**
 * Test Readline implementation.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
class Readline_test {

    public static void main(String[] args) {
        Readline rl = LineInput.getUserInputReadline("readline library not found");
        String line;
        try {
            do { 
                line = rl.readline("Enter line: ");
                System.out.println("Your input was: " + line);
            } while (! line.equals("q") );

            rl.writeHistory(".Readline_test_history");
            rl.writeHistory("/.Readline_test_history");
        } catch (IOException ex) {
            System.out.println("Can't write history: " + ex.getMessage());
        }
    }
}
