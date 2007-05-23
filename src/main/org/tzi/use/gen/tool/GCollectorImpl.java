/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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

package org.tzi.use.gen.tool;

import org.tzi.use.gen.assl.dynamics.IGCollector;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.util.NullWriter;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

/**
 * Collects information which can be printed using the -d and -b options
 * in the generator shell. If a valid state was found, it collects the
 * results (USE commands). It also knows whether the evaluation of an 
 * ASSL procedure can be aborted.
 * @author  Joern Bohling
 */
class GCollectorImpl implements IGCollector {
    private boolean fValidStateFound;
    private List fCommands;   // MCmd
    private long fLimit;
    private PrintWriter fBasicPrintWriter;
    private PrintWriter fDetailPrintWriter;
    private long fLeafCount;
    private boolean fExistsInvalidMessage;

    public GCollectorImpl() {
        fValidStateFound = false;
        fCommands = new ArrayList();
        fLimit = Long.MAX_VALUE;
        fLeafCount = 0;
        fBasicPrintWriter = new PrintWriter( new NullWriter() );
        fDetailPrintWriter = new PrintWriter( new NullWriter() );
        fExistsInvalidMessage = false;
    }
    
    public boolean canStop() {
        return fValidStateFound || fLeafCount >= fLimit;
    }

    public void setValidStateFound() {
        fValidStateFound = true;
    }
    
    public boolean expectSubsequentReporting() {
        return fValidStateFound;
    }
    
    public void subsequentlyPrependCmd( MCmd cmd ) {
        fCommands.add(0, cmd);
    }
   
    public List commands() {
        return fCommands;
    }

    public PrintWriter basicPrintWriter() {
        return fBasicPrintWriter;
    }
    
    public PrintWriter detailPrintWriter() {
        return fDetailPrintWriter;
    }

    public void setLimit(long limit) {
        fLimit = limit;
    }

    public long limit() {
        return fLimit;
    }
    
    public void setBasicPrintWriter( PrintWriter pw ) {
        fBasicPrintWriter = pw;
    }

    public void setDetailPrintWriter( PrintWriter pw ) {
        fDetailPrintWriter = pw;
    }

    public void leaf() {
        fLeafCount++;
        fBasicPrintWriter.print("check state (" + fLeafCount + "): ");
    }

    public long numberOfCheckedStates() {
        return fLeafCount;
    }

    public boolean validStateFound() {
        return fValidStateFound;
    }

    public void invalid( String str ) {
        fExistsInvalidMessage = true;
        basicPrintWriter().println("ERROR: " + str);
        basicPrintWriter().println("EXITING THIS CONFIGURATION NODE.");
    }
    
    public void invalid( Exception e ) {
        invalid(e.getMessage());
    }

    public boolean existsInvalidMessage() {
        return fExistsInvalidMessage;
    }
}

