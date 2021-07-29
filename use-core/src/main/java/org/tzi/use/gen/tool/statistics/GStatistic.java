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

package org.tzi.use.gen.tool.statistics;

import java.util.Comparator;

import org.tzi.use.gen.tool.GGeneratorArguments.InvariantSortOrder;

/**
 * Counts results (either valid or invalid).
 * @author  Joern Bohling
 */
public class GStatistic {
    protected long fCountValid = 0;
    protected long fCountInvalid = 0;
    protected long fCountException = 0;
    
    protected long fCountValid_Local = 0;
    protected long fCountInvalid_Local = 0;
    
    protected long totalTime;
    protected long totalTime_Local;
    // Object to retrieve the name from by calling toString().
    protected final Object name;
    
    public GStatistic(Object name) {
    	this.name = name;
    }

    public long getTotalChecks() {
    	return fCountInvalid + fCountValid + fCountException;
    }
    
    public void registerResult( boolean valid, long time ) {
        if (valid) {
            ++fCountValid_Local;
            ++fCountValid;
        } else {
            ++fCountInvalid_Local;
            ++fCountInvalid;
        }
        
        totalTime_Local += time;
        totalTime += time;
    }

    public void registerException() {
    	++fCountException;
    }
    
    public void localReset() {
    	fCountValid_Local = 0;
    	fCountInvalid_Local = 0;
    	totalTime_Local = 0;
    }

    public String toStringForStatistics() {
		return String.format("%,14d %,14d %,14d %,14d %,14d", getTotalChecks(),
				fCountValid, fCountInvalid, fCountException, totalTime / 1000000) + "  " + name.toString();
    }
    
    public static Comparator<GStatistic> getComparator(InvariantSortOrder sortOrder) {
    	switch (sortOrder) {
    	case MOST_FAILED:
    		return new Comparator<GStatistic>() {
				@Override
				public int compare(GStatistic o1, GStatistic o2) {
					long diff1 = o1.fCountValid_Local - o1.fCountInvalid_Local;
					long diff2 = o2.fCountValid_Local - o2.fCountInvalid_Local;
					
					return (int)(diff1 - diff2);
				}
			};
    	case FASTEST:
    		return new Comparator<GStatistic>() {
				@Override
				public int compare(GStatistic o1,
						GStatistic o2) {
					double diff1 = (double) o1.totalTime_Local
						/ (double) (o1.fCountValid_Local - o1.fCountInvalid_Local);
					double diff2 = (double) o2.totalTime_Local
						/ (double) (o2.fCountValid_Local - o2.fCountInvalid_Local);
					
					return (int)Math.round(diff1 - diff2);
				}
			};
    	case COMBINED:
    	default:
    		return new Comparator<GStatistic>() {
				@Override
				public int compare(GStatistic o1,
						GStatistic o2) {
					
					double total1 = o1.fCountValid_Local + o1.fCountInvalid_Local;
					double total2 = o2.fCountValid_Local + o2.fCountInvalid_Local;
					
					// We want invalid invariants to be checked first
					double validFactor1 =  o1.fCountValid_Local / total1;
					double validFactor2 =  o2.fCountValid_Local / total2;
					
					// time per evaluation
					double timeFactor1 = o1.totalTime_Local / total1;
					double timeFactor2 = o2.totalTime_Local / total2;
					
					// ratio of valid invariants multiplied by ratio of evaluation time
					double f1 = validFactor1 * (timeFactor1 / timeFactor2);
					double f2 = validFactor2 * (timeFactor2 / timeFactor1);
					
					return Double.compare(f1, f2);
				}
			};
    	}
    }
}