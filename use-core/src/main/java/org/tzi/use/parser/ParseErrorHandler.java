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

/*
 * Created on Jun 24, 2005
 */
package org.tzi.use.parser;

import org.tzi.use.output.UserOutput;

/**
 * Used by all parsers in this package. 
 * Collects error information during parsing.
 * 
 * @author Fabian Buettner
 */
public class ParseErrorHandler {
    private int fErrorCount = 0;

    private final String fFileName;
    private final UserOutput output;
    
    /**
     * @param fileName The name of the parsed file - used for constructing
     * error strings.
     * @param userOutput Error messages are passed through to the user output.
     */
    public ParseErrorHandler(String fileName, UserOutput userOutput)  {
        fFileName = fileName;
        this.output = userOutput;
    }
    
    public String getFileName() {
        return fFileName;
    }
    
    public UserOutput getOutput() {
    	return this.output;
    }
    
    public int errorCount() {
        return fErrorCount;
    }

    void incErrorCount() {
        fErrorCount++;
    }
    
    public void reportError(String error) {
        this.output.printlnError(fFileName + ":" + error);
        incErrorCount();
    }

}
