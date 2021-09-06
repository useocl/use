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

import java.io.*;

/** 
 * A filter which counts the characters as they are read.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class CountReader extends FilterReader {
    private long fCount = 0;
    
    /**
     * Creates a new count reader.
     */
    public CountReader(Reader in) {
        super(in);
    }

    /**
     * Returns the number of characters read so far by the underlying
     * reader.
     */
    public long count() {
        return fCount;
    }

    /**
     * Reads a single character.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        int c = super.read();
        if (c != -1 )
            fCount++;
        return c;
    }

    /**
     * Reads characters into a portion of an array.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char cbuf[], int off, int len) throws IOException {
        int c = super.read(cbuf, off, len);
        if (c != -1 )
            fCount += c;
        return c;
    }

    /**
     * Skips characters.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
        long c = super.skip(n);
        fCount += c;
        return c;
    }

    /**
     * Tells whether this stream supports the mark() operation.
     */
    public boolean markSupported() {
        return false;
    }
}
