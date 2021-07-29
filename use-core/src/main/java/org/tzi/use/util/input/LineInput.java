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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.tzi.use.config.Options;

/**
 * Interface for getting a suitable platform-dependent readline
 * implementation. The GNU readline library is preferably used if
 * installed.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class LineInput {

    // utility class
    private LineInput() {}
    
    /**
     * Returns a readline implementation. If the native GNU readline
     * library is available, return that. Otherwise, a stream readline
     * implementation with System.in as source is returned.  
     *
     * @param errorMessage if not null print a message when the native
     *                     GNU readline library is not available, otherwise
     *                     fail silently.
     */
    public static Readline getUserInputReadline(String errorMessage) {
        Readline rl = null;
        try {
            System.loadLibrary("natGNUReadline");
            rl = new GNUReadline();
        } catch (UnsatisfiedLinkError ex) {
            if (errorMessage != null ) {
                System.out.println(ex.toString());
                System.out.println(errorMessage);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // no echo, do protocol
            rl = new StreamReadline(reader, false);
        }
        if (Options.readlineTest) {
            return new ReadlineTestReadlineDecorator(rl);
        }
        return rl;
    }

    public static Readline getStreamReadline(BufferedReader reader, boolean doEcho, String string) {
        Readline rl = new StreamReadline(reader, doEcho, string);
        if (Options.readlineTest) {
            return new ReadlineTestReadlineDecorator(rl);
        }
        return rl;
    }
}
