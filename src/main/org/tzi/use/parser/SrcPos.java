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

package org.tzi.use.parser;

import org.antlr.runtime.Token;

/**
 * A ScrPos object keeps information about a position in a source file.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class SrcPos {
    private String fSrcName;
    private int fLine;
    private int fColumn;

    public SrcPos(String srcName, int line, int column) {
        fSrcName = srcName;
        fLine = line;
        fColumn = column;
    }

    public SrcPos(Token token) {
        fSrcName = token.getInputStream().getSourceName();
        fLine = token.getLine();
        fColumn = token.getCharPositionInLine();
    }

    /** 
     * Returns the source's name.
     */
    public String srcName() {
        return fSrcName;
    }
    
    /** 
     * Returns the line number. First line has number 1.
     */
    public int line() {
        return fLine;
    }
    
    /** 
     * Returns the column number. First column has number 1.
     */
    public int column() {
        return fColumn;
    }
    
    /** 
     * Returns a printable representation of the source position which
     * is suitable for usage as a message prefix.  
     */
    public String toString() {
        return toString(false);
    }
    
    /** 
     * Returns a printable representation of the source position which
     * is suitable for usage as a message prefix.
     * @param simple If <code>false</code> a common used suffix <code>: </code> is added to the string.
     */
    public String toString(boolean simple) {
        return fSrcName + ":" + fLine + ":" + fColumn + (simple ? "" : ": ");
    }
}
