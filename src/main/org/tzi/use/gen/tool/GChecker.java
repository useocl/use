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
import java.util.Arrays;
import java.util.Comparator;

import org.tzi.use.gen.assl.dynamics.IGChecker;
import org.tzi.use.gen.assl.dynamics.IGCollector;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.gen.model.GModel;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.NullPrintWriter;


/**
 * Counts results (either valid or invalid).
 * @author  Joern Bohling
 */
class GStatistic {
    protected long fCountValid = 0;
    protected long fCountInvalid = 0;
    protected long fCountException = 0;
    
    protected long fCountValid_Local = 0;
    protected long fCountInvalid_Local = 0;
    
    protected long totalTime;
    protected long totalTime_Local;
    
    public GStatistic() {}

    public long getTotalChecks() {
    	return fCountInvalid + fCountValid + fCountException;
    }
    
    public void registerResult( boolean valid, long time ) {
        if (valid) {
            fCountValid_Local++;
        } else {
            fCountInvalid_Local++;
        }
        
        totalTime_Local += time;
        totalTime += time;
    }

    public void registerException() {
    	++fCountException;
    }
    
    public void localReset() {
    	fCountValid += fCountValid_Local;
    	fCountInvalid += fCountInvalid_Local;
    	    		
    	fCountValid_Local = 0;
    	fCountInvalid_Local = 0;
    	totalTime_Local = 0;
    }

    public String toStringForStatistics() {
		return String.format("%,14d %,14d %,14d %,14d %,14d", getTotalChecks(),
				fCountValid, fCountInvalid, fCountException, totalTime / 1000000);
    }
}


/**
 * Counts results of the evaluation of an invariant.
 * @author  Joern Bohling
 */
class GInvariantStatistic extends GStatistic {
    private GFlaggedInvariant fFlaggedInvariant;

    public GFlaggedInvariant flaggedInvariant() {
        return fFlaggedInvariant;
    }

    public GInvariantStatistic(GFlaggedInvariant inv) {
        fFlaggedInvariant = inv;
    }

    public String toStringForStatistics() {
        return super.toStringForStatistics() + "  " + fFlaggedInvariant;
    }
}


/**
 * Checks whether a system state is valid in a given <code>GModel</code>.
 * This checker evaluates the invariants, which mostly have been evaluated to
 * false, at first.
 * @author  Joern Bohling
 */
public class GChecker implements IGChecker {
    private boolean fCheckStructure;
    
    private final GInvariantStatistic[] fInvariantStatistics;
    private final int fSize;
    private final GStatistic fStructureStatistic;
    
    private int sortCounter;
    
    private final int checksBeforeSort;
        
    private final Comparator<GInvariantStatistic> invariantComparator;
    
    public GChecker(GModel model, GGeneratorArguments args) {
        fCheckStructure = args.checkStructure();
                
        fInvariantStatistics = new GInvariantStatistic[model.flaggedInvariants().size()];
        int index = 0;
        for (GFlaggedInvariant inv : model.flaggedInvariants()) {
        	inv = (GFlaggedInvariant)inv.clone();
        	fInvariantStatistics[index] = new GInvariantStatistic(inv);
        	++index;
        }
        
        checksBeforeSort = args.getChecksBeforeSortInvariants();
        
        sortCounter = 0;
        fSize = fInvariantStatistics.length;
        fStructureStatistic = new GStatistic();
        
        switch (args.getInvariantSortOrder()) {
        	case MOST_FAILED:
        		invariantComparator = new Comparator<GInvariantStatistic>() {
					@Override
					public int compare(GInvariantStatistic o1, GInvariantStatistic o2) {
						long diff1 = o1.fCountValid_Local - o1.fCountInvalid_Local;
						long diff2 = o2.fCountValid_Local - o2.fCountInvalid_Local;
						
						return (int)(diff1 - diff2);
					}
				};
				break;
        	case FASTEST:
        		invariantComparator = new Comparator<GInvariantStatistic>() {
					@Override
					public int compare(GInvariantStatistic o1,
							GInvariantStatistic o2) {
						double diff1 = (double) o1.totalTime_Local
							/ (double) (o1.fCountValid_Local - o1.fCountInvalid_Local);
						double diff2 = (double) o2.totalTime_Local
							/ (double) (o2.fCountValid_Local - o2.fCountInvalid_Local);
						
						return (int)Math.round(diff1 - diff2);
					}
				};
				break;
        	case COMBINED:
        	default:
        		invariantComparator = new Comparator<GInvariantStatistic>() {
					@Override
					public int compare(GInvariantStatistic o1,
							GInvariantStatistic o2) {
						
						double total1 = o1.fCountValid_Local + o1.fCountInvalid_Local;
						double total2 = o2.fCountValid_Local + o2.fCountInvalid_Local;
						
						// We want invalid invariants to be checked first
						double validFactor1 =  o1.fCountValid_Local / total1;
						double validFactor2 =  o2.fCountValid_Local / total2;
						
						// time per evaluation
						double timeFactor1 = (double)o1.totalTime_Local / total1;
						double timeFactor2 = (double)o2.totalTime_Local / total2;
						
						// ratio of valid invariants multiplied by ratio of evaluation time
						double f1 = validFactor1 * (timeFactor1 / timeFactor2);
						double f2 = validFactor2 * (timeFactor2 / timeFactor1);
						
						return Double.compare(f1, f2);
					}
				};
        }
    }

    private long sortCount = 0;
	
	public boolean check(final MSystemState state, final IGCollector collector) {
        // resort the invariants starting every 10.000th check.
        // invariants, which are often invalid, will be checked first.
        if (sortCounter == checksBeforeSort) {
            sortCounter = 0;
            
            Arrays.sort(fInvariantStatistics, invariantComparator);
            ++sortCount;
            
            for (int i = 0; i < fInvariantStatistics.length; ++i)
            	fInvariantStatistics[i].localReset();
            	
        } else
            sortCounter++;
        
        // evaluating invariants
        boolean result = true;
        long start;
                
        for (int k = 0; k < fSize && result; k++) {
            GInvariantStatistic stat = fInvariantStatistics[k];
            if (!stat.flaggedInvariant().disabled()) {
            	
            	try {
            		start = System.nanoTime();
	            	boolean valid = stat.flaggedInvariant().eval(collector, state);
	                stat.registerResult(valid, System.nanoTime() - start);
	                
	                if (!valid) {
	                	if (collector.doBasicPrinting())
	                		collector.basicPrintWriter().println(stat.flaggedInvariant().toString() + " invalid.");
	                	 
	                    result = false;
	                    break;
	                }
	            } catch (MultiplicityViolationException e) {
	            	if (collector.doDetailPrinting()) {
	            		collector.detailPrintWriter().println("An error occured while checking an invariant:");
	            		collector.detailPrintWriter().println(e.getMessage());
	            	}
	            	stat.registerException();
	            	return false;
	            }
            }
        }
        
        // checking structure
        if (result && fCheckStructure) {
        	start = System.nanoTime();
            result = state.checkStructure(NullPrintWriter.getInstance(), false);
            if (!result && collector.doBasicPrinting()) {
                collector.basicPrintWriter().println("invalid structure.");
            }
            fStructureStatistic.registerResult(result, System.nanoTime() - start);
        }
        
        if (result && collector.doBasicPrinting())
        	collector.basicPrintWriter().println("valid state.");
        
        return result;
    }

    public void printStatistics(PrintWriter pw, long checkedStates) {
    	fStructureStatistic.localReset();
    	
        pw.println("Note: A disabled invariant has never been checked.");
        pw.println("An enabled and negated invariant is `valid'");
        pw.println("if it has been evaluated to false.");
        pw.println();
        pw.println("        checks          valid        invalid     mul. viol.      time (ms)  Invariant");

        pw.println(fStructureStatistic.toStringForStatistics() +
                   "  model-inherent multiplicities" );
    
        for (int i = 0; i < fInvariantStatistics.length; ++i)
        	fInvariantStatistics[i].localReset();
        Arrays.sort(fInvariantStatistics, invariantComparator);
        
        long totalChecks = fStructureStatistic.getTotalChecks();
        
        for (int k=0; k < fSize; k++) {
        	GInvariantStatistic s = fInvariantStatistics[k];
            pw.println(s.toStringForStatistics());
            totalChecks += s.getTotalChecks();
        }
        pw.println();
        pw.print("Total checks: ");
        pw.print(String.format("%,d", totalChecks));
        pw.print(" Overhead (checks - states checked): ");
        pw.println(String.format("%,d", totalChecks - checkedStates));
        pw.println(String.format("Sorted %,d times.", sortCount));
    }
}
