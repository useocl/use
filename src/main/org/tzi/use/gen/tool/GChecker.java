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
import java.util.Arrays;
import java.util.List;

import org.tzi.use.gen.assl.dynamics.IGChecker;
import org.tzi.use.gen.model.GFlaggedInvariant;
import org.tzi.use.gen.model.GModel;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.NullWriter;


/**
 * Counts results (either valid or invalid).
 * @author  Joern Bohling
 */
class GStatistic implements Comparable<GStatistic> {
    protected long fCountValid;
    protected long fCountInvalid;

    public GStatistic() {
        fCountValid = 0;
        fCountInvalid = 0;
    }

    public long diff() {
        return fCountValid - fCountInvalid;
    }

    public void registerResult( boolean valid ) {
        if (valid)
            fCountValid++;
        else
            fCountInvalid++;
    }

    @Override
    public int compareTo(GStatistic o) {
        return (Long.valueOf(diff())).compareTo(Long.valueOf(o.diff()));
    }

    @Override
    public boolean equals(Object o) {
    	if (this == o) {
    		return true;
    	} else if (o == null) {
    		return false;
    	} else if (o instanceof GStatistic) {
    		GStatistic s = (GStatistic)o;
    		return this.compareTo(s) == 0;
    	} else {
    		return false;
    	}
    }
    
    @Override
    public int hashCode() {
    	return Long.valueOf(diff()).hashCode();
    }
    
    public String toStringForStatistics() {
        String valid = "" + fCountValid;
        String invalid = "" + fCountInvalid;
        String total = "" + (fCountInvalid+fCountValid);
        String str = "                                             ";
        if (valid.length() <= 9)
            valid = str.substring(valid.length(),9) + valid;
        if (invalid.length() <= 9)
            invalid = str.substring(invalid.length(),9) + invalid;  
        if (total.length() <= 9)
            total = str.substring(total.length(),9) + total;    
        return total + valid + invalid;
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
    private Object[] fInvariantStatistics;
    private int fSize;
    private GStatistic fStructureStatistic;
    private int sortCounter;

    public GChecker(GModel model, boolean check) {
        fCheckStructure = check;
        
        List<GInvariantStatistic> stats = new ArrayList<GInvariantStatistic>();
        
        for (GFlaggedInvariant inv : model.flaggedInvariants()) {
            inv = (GFlaggedInvariant)inv.clone();
            stats.add( new GInvariantStatistic(inv));
        }
        
        sortCounter = 0;
        fInvariantStatistics = stats.toArray();
        fSize = stats.size();
        fStructureStatistic = new GStatistic();
    }

    public boolean check(MSystemState state, PrintWriter pw) {
        // resort the invariants every 10th check.
        // invariants, which are often invalid, will be checked first.
        if (sortCounter == 10) {
            sortCounter = 0;
            Arrays.sort(fInvariantStatistics);
        } else
            sortCounter++;
        
        // evaluating invariants
        boolean result = true;
        for (int k=0; k<fSize && result; k++) {
            GInvariantStatistic stat 
                = (GInvariantStatistic) fInvariantStatistics[k];
            if (!stat.flaggedInvariant().disabled()) {
                boolean valid = stat.flaggedInvariant().eval(state);
                stat.registerResult(valid);
                if (!valid) {
                    pw.println(stat.flaggedInvariant().toString()+ " invalid.");
                    result = false;
                    break;
                }
            }
        }
        
        // checking structure
        if (result && fCheckStructure) {
            result=state.checkStructure(new PrintWriter(new NullWriter()), false);
            if (!result) {
                pw.println("invalid structure.");
            }
            fStructureStatistic.registerResult(result);
        }
        
        if (result)
            pw.println("valid state.");
        
        return result;
    }

    public void printStatistics(PrintWriter pw) {
        pw.println("Note: A disabled invariant has never been checked.");
        pw.println("An enabled and negated invariant is `valid'");
        pw.println("if it has been evaluated to false.");
        pw.println();
        pw.println("   checks    valid  invalid  Invariant");
        pw.println(fStructureStatistic.toStringForStatistics() +
                   "  model-inherent multiplicities" );
    
        Arrays.sort(fInvariantStatistics);
        for (int k=0; k<fSize; k++)
            pw.println(
                       ((GInvariantStatistic) fInvariantStatistics[k])
                       .toStringForStatistics());
    }
}
