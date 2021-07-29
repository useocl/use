/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.gen.tool;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

import org.tzi.use.util.Log;

/**
 * Holds information about the use specified configuration, e. g., 
 * RandomNr and other options, of a generator run.
 * 
 * @author Lars Hamann
 *
 */
public class GGeneratorArguments {
	
	public enum InvariantSortOrder {
		MOST_FAILED,
		FASTEST,
		COMBINED
	}
	
	private String fFilename;
	
	private String callString;
	
	private Long fLimit;
    
	private String fPrintFilename = null;
    
    private boolean fPrintBasics = false;
    
    private boolean fPrintDetails = false;
    
    private Long fRandomNr;
    
    private boolean fCheckStructure = true;
    
    private boolean useRandomTry = false;
    
    /**
     * If true, the generator checks directly after
     * a try call if multiplicity is violated.  
     */
    private boolean useTryCuts = true;
    
    /**
     * If true, a try on a 1 : * association uses
     * only useful links.
     */
    private boolean useMinCombinations = true;
    
    /**
     * If true, time related information (duration, snapshots/s)
     * is printed in the result.
     * Otherwise it is suppressed. This is usefull for tests.
     */
    private boolean printTimeRelatedData = true;

    /***
     * After this number of checks the invariants get sorted,
     * to be faster (failing invs first, etc.)
     */
    private int fChecksBeforeSortInvariants = 10000;
    
    private InvariantSortOrder invariantSortOrder = InvariantSortOrder.COMBINED;
    
    /**
     * If true, the generator automatically calculates barriers for
     * the ASSL-Procedure.
     */
    private boolean calculateBarriers = true;
    
    public GGeneratorArguments() {
    	fRandomNr = Long.valueOf((new Random()).nextInt(10000));
    	fLimit = Long.valueOf(Long.MAX_VALUE);
    }
    
	/**
	 * @return the fFilename
	 */
	public String getFilename() {
		return fFilename;
	}

	/**
	 * @param fFilename the fFilename to set
	 */
	public void setFilename(String filename) {
		this.fFilename = filename;
	}

	/**
	 * @return the fLimit
	 */
	public Long getLimit() {
		return fLimit;
	}

	/**
	 * @param fLimit the fLimit to set
	 */
	public void setLimit(Long limit) {
		this.fLimit = limit;
	}

	/**
	 * @return the fPrintFilename
	 */
	public String getPrintFilename() {
		return fPrintFilename;
	}

	/**
	 * @param fPrintFilename the fPrintFilename to set
	 */
	public void setPrintFilename(String printFilename) {
		this.fPrintFilename = printFilename;
	}

	/**
	 * @return the fPrintBasics
	 */
	public boolean doPrintBasics() {
		return fPrintBasics;
	}

	/**
	 * @param fPrintBasics the fPrintBasics to set
	 */
	public void setDoPrintBasics(boolean printBasics) {
		this.fPrintBasics = printBasics;
	}

	/**
	 * @return the fPrintDetails
	 */
	public boolean doPrintDetails() {
		return fPrintDetails;
	}

	/**
	 * @param fPrintDetails the fPrintDetails to set
	 */
	public void setDoPrintDetails(boolean printDetails) {
		this.fPrintDetails = printDetails;
	}

	/**
	 * @return the fRandomNr
	 */
	public Long getRandomNr() {
		return fRandomNr;
	}

	/**
	 * @param fRandomNr the fRandomNr to set
	 */
	public void setRandomNr(Long randomNr) {
		this.fRandomNr = randomNr;
	}

	/**
	 * @return the fCheckStructure
	 */
	public boolean checkStructure() {
		return fCheckStructure;
	}

	/**
	 * @param checkStructure the fCheckStructure to set
	 */
	public void setCheckStructure(boolean checkStructure) {
		this.fCheckStructure = checkStructure;
	}

	/**
	 * @return the useTryCuts
	 */
	public boolean useTryCuts() {
		return useTryCuts;
	}

	/**
	 * @param useTryCuts the useTryCuts to set
	 */
	public void setUseTryCuts(boolean useTryCuts) {
		this.useTryCuts = useTryCuts;
	}

	/**
	 * @return the useMinCombinations
	 */
	public boolean useMinCombinations() {
		return useMinCombinations;
	}

	/**
	 * @param useMinCombinations the useMinCombinations to set
	 */
	public void setUseMinCombinations(boolean useMinCombinations) {
		this.useMinCombinations = useMinCombinations;
	}

	/**
	 * @return the printTimeRelatedData
	 */
	public boolean printTimeRelatedData() {
		return printTimeRelatedData;
	}

	/**
	 * @param printTimeRelatedData the printTimeRelatedData to set
	 */
	public void setPrintTimeRelatedData(boolean printTimeRelatedData) {
		this.printTimeRelatedData = printTimeRelatedData;
	}
	/**
	 * @return
	 */
	public boolean doBasicPrinting() {
		return fPrintBasics || fPrintDetails;
	}
	/**
	 * @return
	 */
	public int getChecksBeforeSortInvariants() {
		return fChecksBeforeSortInvariants;
	}
	
	public void setChecksBeforeSortInvariants(int checks) {
		fChecksBeforeSortInvariants = checks;
	}

	/**
	 * @return the invariantSortOrder
	 */
	public InvariantSortOrder getInvariantSortOrder() {
		return invariantSortOrder;
	}

	/**
	 * @param invariantSortOrder the invariantSortOrder to set
	 */
	public void setInvariantSortOrder(InvariantSortOrder invariantSortOrder) {
		this.invariantSortOrder = invariantSortOrder;
	}

	/**
	 * @return the callString
	 */
	public String getCallString() {
		return callString;
	}

	/**
	 * @param callString the callString to set
	 */
	public void setCallString(String callString) {
		this.callString = callString;
	}

	/**
	 * @return the calculateBarriers
	 */
	public boolean isCalculateBarriers() {
		return calculateBarriers;
	}

	/**
	 * @param calculateBarriers the calculateBarriers to set
	 */
	public void setCalculateBarriers(boolean calculateBarriers) {
		this.calculateBarriers = calculateBarriers;
	}

	/**
	 * Parses the command line arguments
	 * @param str
	 * @return
	 */
	public static GGeneratorArguments parseCallstring(String str) {
		GGeneratorArguments args = new GGeneratorArguments();
		StringTokenizer st = new StringTokenizer(str);
        
        boolean allOptionsFound = false;
        boolean error = false;
        boolean limitOptionFound = false;
        boolean outputOptionFound = false;
        boolean randomOptionFound = false;
        
        String message = null;
        
        try {
            while (!allOptionsFound && !error) {
                String optionOrFilename = st.nextToken();
                
                if (optionOrFilename.equals("-s")) {
                    if (!args.checkStructure())
                        error = true;
                    else
                        args.setCheckStructure(false);
                } else if (optionOrFilename.equals("-r")) {
                    if (randomOptionFound)
                        error = true;
                    else {
                        try {
                            args.setRandomNr(Long.valueOf(st.nextToken()));
                        } catch (NumberFormatException e) {
                            error = true;
                        }
                        error = error || (args.getRandomNr().longValue() <= 0);
                        if (error)
                            message = "the parameter of the -r"
                                    + " option must be a positive number"
                                    + " (< 2^63).";
                    }
                    randomOptionFound = true;
                } else if (optionOrFilename.equals("-l")) {
                    if (limitOptionFound)
                        error = true;
                    else {
                        try {
                            args.setLimit(Long.valueOf(st.nextToken()));
                        } catch (NumberFormatException e) {
                            error = true;
                        }
                        error = error || (args.getLimit().longValue() <= 0);
                        if (error)
                            message = "the parameter of the -l"
                                    + " option must be a positive number"
                                    + " (< 2^63).";
                    }
                    limitOptionFound = true;
                } else if (optionOrFilename.equals("-si")) {
                    try {
                        args.setChecksBeforeSortInvariants(Integer.valueOf(st.nextToken()));
                    } catch (NumberFormatException e) {
                        error = true;
                    }
                    
                    if (error)
                        message = "the parameter of the -si"
                                + " option must be a positive number.";
                } else if (optionOrFilename.equals("-so")) {
                	String sortOrder = st.nextToken();
                	
                	if (sortOrder.equals("c") || sortOrder.equals("combined"))
                		args.setInvariantSortOrder(InvariantSortOrder.COMBINED);
                	else if (sortOrder.equals("f") || sortOrder.equals("fastest"))
                		args.setInvariantSortOrder(InvariantSortOrder.FASTEST);
                	else if (sortOrder.equals("m") || sortOrder.equals("mostFailed"))
                		args.setInvariantSortOrder(InvariantSortOrder.MOST_FAILED);
                	else
                		error = true;
                	
                    if (error)
                        message = "Invalid sort order " + sortOrder;
                    
                } else if (optionOrFilename.equals("-b")
                        || optionOrFilename.equals("-d")
                        || optionOrFilename.equals("-bf")
                        || optionOrFilename.equals("-df")
                        || optionOrFilename.equals("-t")
                        || optionOrFilename.equals("-dc")
                        || optionOrFilename.equals("-ac")
                        || optionOrFilename.equals("-nb")) {
                    // an output option
                    if (outputOptionFound)
                        error = true;
                    else if (optionOrFilename.equals("-b"))
                        args.setDoPrintBasics(true);
                    else if (optionOrFilename.equals("-d"))
                        args.setDoPrintDetails(true);
                    else if (optionOrFilename.equals("-bf")) {
                        args.setDoPrintBasics(true);
                        args.setPrintFilename(st.nextToken());
                    } else if (optionOrFilename.equals("-df")) {
                        args.setDoPrintDetails(true);
                        args.setPrintFilename(st.nextToken());
                    } else if (optionOrFilename.equals("-t")) {
                    	args.setPrintTimeRelatedData(false);
                    } else if (optionOrFilename.equals("-dc")) {
                    	args.setUseTryCuts(false);
                    } else if (optionOrFilename.equals("-ac")) {
                    	args.setUseMinCombinations(false);
                    } else if (optionOrFilename.equals("-nb")) {
                    	args.setCalculateBarriers(false);
                    } else 
                    	outputOptionFound = true;
                } else {
                    // optionOrFilename must be a filename
                    if (optionOrFilename.startsWith("-"))
                        error = true;
                    else {
                        allOptionsFound = true;
                        if (optionOrFilename.startsWith("\"")) {
                        	// Quoted filename
                        	while (st.hasMoreElements()) {
                        		optionOrFilename += " " + st.nextToken();
                        		if (optionOrFilename.endsWith("\""))
                        			break;
                        	}
                        }
                        
                        args.setFilename(optionOrFilename);
                        args.setCallString(st.nextToken(""));
                    }
                }
            }
        } catch (NoSuchElementException e) {
            error = true;
        }

        if (error) {
            if (message != null)
                Log.error(message);
            else {
                Log.error("syntax is `start [-l <num>][-r <num>][-si <num>][-so [c|f|m]][-sif<num>]"
                        + "[-b|-d|-bf <FILE>|-df <FILE>|-t|-c|-ac|-dc] "
                        + "FILE PROCNAME([paramlist])'");
            }
            return null;
        }

		return args;
	}

	/**
	 * If <code>true</code>, the source collections
	 * of a try are permuted before the evaluation.
	 * @return
	 */
	public boolean useRandomTry() {
		return useRandomTry;
	}
	
	public void setRandomTry(boolean useRandom) {
		this.useRandomTry = useRandom;
	}
}
