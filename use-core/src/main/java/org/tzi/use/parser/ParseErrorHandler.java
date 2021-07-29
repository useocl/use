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

/*
 * Created on Jun 24, 2005
 */
package org.tzi.use.parser;

import java.io.PrintWriter;

/**
 * Used by all parsers in this package. 
 * Collects error information during parsing.
 * 
 * @author green
 */
public class ParseErrorHandler {
    private int fErrorCount = 0;

    private String fFileName;
    private PrintWriter fErrWriter;
    
    /**
     * @param fileName The name of the parsed file - used for constructing
     * error strings.
     * @param errWriter Error messages are passed through to this 
     * writer.
     */
    public ParseErrorHandler(String fileName, PrintWriter errWriter)  {
        fFileName = fileName;
        fErrWriter = errWriter;
    }
    
    public String getFileName() {
        return fFileName;
    }
    
    public PrintWriter getErrorWriter() {
    	return fErrWriter;
    }
    
    public int errorCount() {
        return fErrorCount;
    }

    void incErrorCount() {
        fErrorCount++;
    }
    
    public void reportError(String error) {
        fErrWriter.println(fFileName + ":" + error);
        fErrWriter.flush();
        incErrorCount();
    }

}
