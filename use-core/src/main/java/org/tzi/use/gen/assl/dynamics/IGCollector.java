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

/**
 * March 22th 2001 
 * @author  Joern Bohling
 */

package org.tzi.use.gen.assl.dynamics;

import java.io.PrintWriter;

import org.tzi.use.gen.assl.statics.GInstrBarrier;
import org.tzi.use.gen.assl.statics.GInstrCalculatedBarrier;
import org.tzi.use.uml.sys.soil.MStatement;

public interface IGCollector {
    /**
     * True, if a valid state was found.
     * No further execution is needed.
     * @return
     */
    boolean canStop();
    
    void setValidStateFound();
    
    void subsequentlyPrependStatement(MStatement statement);
    
    boolean expectSubsequentReporting();
    
    /**
     * Called if a leaf is hit.
     * Normally used for statistical reasons (number of states checked).
     */
    void leaf();
    
    long numberOfCheckedStates();
    
    void setPrePostViolation();
    
    boolean getPrePostViolation();
    
    PrintWriter basicPrintWriter();
    boolean doBasicPrinting();
    
    PrintWriter detailPrintWriter();
    boolean doDetailPrinting();

    void invalid(String str);
    void invalid(Exception e);

	/**
	 * Adds <code>ignoredStates</code> to the overall ignored states
	 * @param ignoredStates
	 */
    void addIgnoredStates(long ignoredStates);

	/**
	 * Informs the previous callers, that a barrier cannot be passed.
	 */
    void setBlocked(boolean newValue);
    
	/**
	 * Returns the state of the last checked barrier.
	 */
    boolean getBlocked();
	
	/**
	 * Adds 1 to the cut counter
	 */
    void addCut();
	
	/**
	 * Returns the number of cuts
	 * @return Number of cuts made
	 */
    long getCuts();
	
	/**
	 * Adds one to the number of "hit" barriers.
	 */
    void addBarrierBlock();
	
	/**
	 * Returns the number of barriers hit.
	 * @return Number of hit barriers.
	 */
    long getBarriersHit();

	/**
	 * Add the barrier statement to the list of barriers.
	 * @param bInstr 
	 */
    void addBarrier(GInstrBarrier bInstr);
	
	/**
	 * Add the calculated barrier statement to the list of barriers and
	 * increments the counter for calculated barriers by one.
	 * @param bInstr 
	 */
    void addBarrier(GInstrCalculatedBarrier bInstr);
}
