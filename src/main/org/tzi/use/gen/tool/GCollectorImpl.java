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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gen.assl.dynamics.IGCollector;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.NullPrintWriter;

/**
 * Collects information which can be printed using the -d and -b options
 * in the generator shell. If a valid state was found, it collects the
 * results (USE commands). It also knows whether the evaluation of an 
 * ASSL procedure can be aborted.
 * @author  Joern Bohling
 */
public class GCollectorImpl implements IGCollector {
    private boolean fValidStateFound;
    private List<MStatement> fStatements;
    private long fLimit;
    private PrintWriter fBasicPrintWriter;
    private PrintWriter fDetailPrintWriter;
    private long fLeafCount;
    private long cutCount;
    /**
     * Number of states ignored by using reduced number of link combinations.
     */
    private long ignoredStates;
    
    private boolean fExistsInvalidMessage;
    private boolean fPrePostCondViolation;

    private boolean doBasicPrinting = false;
    private boolean doDetailPrinting = false;
    
    public GCollectorImpl() {
        fValidStateFound = false;
        fStatements = new ArrayList<MStatement>();
        fLimit = Long.MAX_VALUE;
        fLeafCount = 0;
        fBasicPrintWriter = NullPrintWriter.getInstance();
        fDetailPrintWriter = NullPrintWriter.getInstance();
        fExistsInvalidMessage = false;
        fPrePostCondViolation = false;
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
    
    public void subsequentlyPrependStatement(MStatement statement) {
    	fStatements.add(0, statement);
    }
    
    public List<MStatement> statements() {
    	return fStatements;
    }

    public PrintWriter basicPrintWriter() {
        return fBasicPrintWriter;
    }
    
    public boolean doBasicPrinting() {
    	return this.doBasicPrinting;
    }
    
    public PrintWriter detailPrintWriter() {
        return fDetailPrintWriter;
    }

    public boolean doDetailPrinting() {
    	return this.doDetailPrinting;
    }
    
    public void setLimit(long limit) {
        fLimit = limit;
    }

    public long limit() {
        return fLimit;
    }
    
    public void setBasicPrintWriter( PrintWriter pw ) {
        fBasicPrintWriter = pw;
        this.doBasicPrinting = true;
    }

    public void setDetailPrintWriter( PrintWriter pw ) {
        fDetailPrintWriter = pw;
        this.doDetailPrinting = true;
    }

    public void leaf() {
        fLeafCount++;
        fBasicPrintWriter.print("check state (" + fLeafCount + "): ");
    }

    public long numberOfCheckedStates() {
        return fLeafCount;
    }

    public long getCutCount() {
    	return this.cutCount;
    }
    
    public void addCut() {
    	++cutCount;
    }
    
    public boolean validStateFound() {
    	// if a valid state has been found and no pre or postconditions have been violated
    	return fValidStateFound && !fPrePostCondViolation;
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
    
    /**
     * storing pre or postcondition violations during the ASSL evaluation process
     */
    public void setPrePostViolation() {
    	fPrePostCondViolation = true;
    }
    
    /**
     * @return "true" if an pre or postcondition violation occured during the ASSL evaluation
     * called by GEvalProcedure
     */
    public boolean getPrePostViolation() {
    	return fPrePostCondViolation;
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#addIgnoredStates(long)
	 */
	@Override
	public void addIgnoredStates(long ignoredStates) {
		this.ignoredStates += ignoredStates;
	}
	
	public long getIgnoredStates() {
		return this.ignoredStates;
	}
}

