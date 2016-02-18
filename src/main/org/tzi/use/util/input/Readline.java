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
 * The general readline interface.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public interface Readline extends AutoCloseable {

    /**
     * Reads a line of text.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached.
     *
     * @exception  IOException  If an I/O error occurs.
     */
    String readline(String prompt) throws IOException;

    /**
     * Informs the Readline implementation that a history of input
     * should be preserved.
     */
    void usingHistory();

    /**
     * Reads a history file. The Readline implementation can use the
     * information in the file to provide input from previous
     * sessions.
     *
     * @exception IOException If an I/O error occurs.  
     */
    void readHistory(String filename) throws IOException;

    /**
     * Writes a history file with information from the current
     * session.
     *
     * @exception IOException If an I/O error occurs.  
     */
    void writeHistory(String filename) throws IOException;

    /**
     * Closes the Readline stream.
     */
    void close() throws IOException;
    

    /*
     * Determines if the input from this readline needs to be echoed on
     * the console
     */
    boolean doEcho();
}

