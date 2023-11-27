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

import org.tzi.use.gen.assl.dynamics.IGChecker;
import org.tzi.use.gen.assl.dynamics.IGCollector;
import org.tzi.use.gen.tool.statistics.GInvariantStatistic;
import org.tzi.use.gen.tool.statistics.GStatistic;
import org.tzi.use.output.UserOutput;
import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemState;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;


/**
 * Checks whether a system state is valid in a given <code>GModel</code>.
 * This checker evaluates the invariants, which mostly have been evaluated to
 * false, at first.
 * @author  Joern Bohling
 */
public class GChecker implements IGChecker {
    private final boolean fCheckStructure;
    
    private final GInvariantStatistic[] fInvariantStatistics;
    private final int fSize;
    private final GStatistic fStructureStatistic;
    
    private int sortCounter;
    
    private final int checksBeforeSort;
        
    private final Comparator<GStatistic> invariantComparator;
    
    private final Evaluator fEvaluator = new Evaluator();
    
    public GChecker(MModel model, GGeneratorArguments args) {
        fCheckStructure = args.checkStructure();
        Collection<MClassInvariant> invs = model.classInvariants();
        fInvariantStatistics = new GInvariantStatistic[invs.size()];
        
        int index = 0;
        for (MClassInvariant inv : invs) {
        	fInvariantStatistics[index] = new GInvariantStatistic(inv);
        	++index;
        }
        
        checksBeforeSort = args.getChecksBeforeSortInvariants();
        
        sortCounter = 0;
        fSize = fInvariantStatistics.length;
        fStructureStatistic = new GStatistic("model-inherent multiplicities");
        
        invariantComparator = GInvariantStatistic.getComparator(args.getInvariantSortOrder());
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
            	
        } else {
            ++sortCounter;
        }
        
        // evaluating invariants
        boolean result = true;
        long start;
        boolean valid;
        Value value;
        
        for (int k = 0; k < fSize; k++) {
            GInvariantStatistic stat = fInvariantStatistics[k];
            if (stat.getInvariant().isActive() && !stat.isCheckedByBarrier() ) {
            	
            	try {
            		start = System.nanoTime();
	            		                    
	                value = fEvaluator.eval( stat.getInvariant().expandedExpression(), state );
	                    
	                valid = value.isDefined() && ((BooleanValue) value).isFalse() == stat.getInvariant().isNegated();
	                	            	
	                stat.registerResult(valid, System.nanoTime() - start);
	                
	                if (!valid) {
	                	if (collector.doBasicPrinting()) {
                            collector.getUserOutput().println(stat.getInvariant().toString() + " invalid.");
                        }
	                	 
	                    result = false;
	                    break;
	                }
	            } catch (MultiplicityViolationException e) {
	            	if (collector.doDetailPrinting()) {
	            		collector.getUserOutput().printlnTrace("An error occurred while checking an invariant:");
	            		collector.getUserOutput().printlnTrace(e.getMessage());
	            	}
	            	stat.registerException();
	            	return false;
	            }
            }
        }
        
        // checking structure
        if (result && fCheckStructure) {
        	start = System.nanoTime();
            result = state.checkStructure(VoidUserOutput.getInstance(), false);
            if (!result && collector.doBasicPrinting()) {
                collector.getUserOutput().println("invalid structure.");
            }
            fStructureStatistic.registerResult(result, System.nanoTime() - start);
        }
        
        if (result && collector.doBasicPrinting())
        	collector.getUserOutput().println("valid state.");
        
        return result;
    }

    public void printStatistics(UserOutput output, long checkedStates) {
    	// Add the local data to the total 
        for (GInvariantStatistic fInvariantStatistic : fInvariantStatistics) {
            fInvariantStatistic.localReset();
        }

    	fStructureStatistic.localReset();
    	
        output.println("Note: A disabled invariant has never been checked.");
        output.println("An enabled and negated invariant is `valid'");
        output.println("if it has been evaluated to false.");
        output.println();
        output.println("        checks          valid        invalid     mul. viol.      time (ms)  Invariant");

        output.println(fStructureStatistic.toStringForStatistics());
            
        long totalChecks = fStructureStatistic.getTotalChecks();
        
        for (int k = 0; k < fSize; k++) {
        	GInvariantStatistic s = fInvariantStatistics[k];
        	if (!s.isCheckedByBarrier()) {
        		output.println(s.toStringForStatistics());
        		totalChecks += s.getTotalChecks();
        	}
        }
        
        output.println();
        output.print("Total checks: ");
        output.print(String.format("%,d", totalChecks));
        output.print(" Overhead (checks - states checked): ");
        output.println(String.format("%,d", totalChecks - checkedStates));
        output.println(String.format("Sorted %,d times.", sortCount));
    }
}
