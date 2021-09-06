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
import org.tzi.use.gen.assl.statics.GInstrBarrier;
import org.tzi.use.gen.assl.statics.GInstrCalculatedBarrier;
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
    
    /**
     * Number of leafs, i. e., checked system states
     */
    private long fLeafCount;
    
    /**
     * Number of states ignored by using reduced number of link combinations.
     */
    private long ignoredStates = 0;
    
    /**
     * Number of cuts made
     */
    private long cutCount = 0;
    
    /**
     * Number of barriers hit
     */
    private long barrierHitCount = 0;
    
    /**
     * Number of automatically calculated barriers
     */
    private int calculatedBarriers = 0;
    
    private boolean fExistsInvalidMessage;
    private boolean fPrePostCondViolation;

    private final boolean doBasicPrinting;
    private final boolean doDetailPrinting;
    
    private boolean isBlocked = false;
    
    private List<GInstrBarrier> barriers = new ArrayList<GInstrBarrier>();
    
    public GCollectorImpl(boolean doBasicPrinting, boolean doDetailPrinting) {
        fValidStateFound = false;
        fStatements = new ArrayList<MStatement>();
        fLimit = Long.MAX_VALUE;
        fLeafCount = 0;
        fBasicPrintWriter = NullPrintWriter.getInstance();
        fDetailPrintWriter = NullPrintWriter.getInstance();
        fExistsInvalidMessage = false;
        fPrePostCondViolation = false;
        this.doBasicPrinting = doBasicPrinting;
        this.doDetailPrinting = doDetailPrinting;
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
    
    /**
     * Sets the {@link PrintWriter} to write basic information to.
     * This writer is only used when the constructor parameter <code>doBasicPrinting</code>
     * was set to <code>true</code>. 
     * @param pw
     */
    public void setBasicPrintWriter( PrintWriter pw ) {
        fBasicPrintWriter = pw;
    }

    /**
     * Sets the {@link PrintWriter} to write detailed information to.
     * This writer is only used when the constructor parameter <code>doDetailPrinting</code>
     * was set to <code>true</code>. 
     * @param pw
     */
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

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#setBlocked()
	 */
	@Override
	public void setBlocked(boolean newValue) {
		this.isBlocked = newValue;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#getBlocked()
	 */
	@Override
	public boolean getBlocked() {
		return isBlocked;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#addCut()
	 */
	@Override
	public void addCut() {
		++this.cutCount;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#getCuts()
	 */
	@Override
	public long getCuts() {
		return this.cutCount;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#addBarrierHit()
	 */
	@Override
	public void addBarrierBlock() {
		++this.barrierHitCount;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#getBarriersHit()
	 */
	@Override
	public long getBarriersHit() {
		return this.barrierHitCount;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#addBarrier(GInstrCalculatedBarrier)
	 */
	@Override
	public void addBarrier(GInstrCalculatedBarrier barrier) {
		++calculatedBarriers;
		barriers.add(barrier);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCollector#addBarrier(GInstrBarrier)
	 */
	@Override
	public void addBarrier(GInstrBarrier barrier) {
		barriers.add(barrier);
	}
	
	/**
	 * @return the calculatedBarriers
	 */
	public int getNumCalculatedBarriers() {
		return calculatedBarriers;
	}
	
	public List<GInstrBarrier> getBarriers() {
		return this.barriers;
	}
}

