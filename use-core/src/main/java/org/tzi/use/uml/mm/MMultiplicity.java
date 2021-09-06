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

package org.tzi.use.uml.mm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tzi.use.util.StringUtil;

/** 
 * A Multiplicity specifies possible cardinality values for instances
 * participating in associations.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class MMultiplicity {
    /**
     * A value representing an umlimited upper bound (that is `*').
     */
    public static final int MANY = -1; // implementation value for `*'

    /**
     * Convenience object for the multiplicity `0..*'.
     */
    public static final MMultiplicity ZERO_MANY = new MMultiplicity(0, MANY);

    /**
     * Convenience object for the multiplicity `1..*'.
     */
    public static final MMultiplicity ONE_MANY = new MMultiplicity(1, MANY);
    
    /**
     * Convenience object for the multiplicity `1'.
     */
    public static final MMultiplicity ONE = new MMultiplicity(1, 1);
    
    /**
     * Convenience object for the multiplicity `0..1'.
     */
    public static final MMultiplicity ZERO_ONE = new MMultiplicity(0, 1);
    
    public final class Range {
        int fLower;
        int fUpper;

        Range(int lower, int upper) {
            // possible argument combinations:
            // lower upper
            //   *     *    ok
            //   *     u    error
            //   l     *    ok 
            //   l     u    l <= u, l >= 0, u > 0
            if (upper != MANY && 
                (lower > upper || lower < 0 ||  upper < 0 ) )
                throw new IllegalArgumentException(
                                                   "Invalid multiplicity range `" + 
                                                   lower + ".." + upper + "'.");
            if (lower == MANY )
                lower = 0;
            fLower = lower;
            fUpper = upper;
        }
        
        /**
		 * @return the fLower
		 */
		public int getLower() {
			return fLower;
		}
		
		/**
		 * @return the fUpper
		 */
		public int getUpper() {
			return fUpper;
		}

        /**
         * Test if range contains a specified value.
         */
        public boolean contains(int n) {
            return n == MANY && fUpper == MANY || fLower <= n && ( fUpper == MANY || n <= fUpper );
        }

        /**
         * Test if range contains values greater one.
         */
        public boolean isGreaterOne() {
            return fUpper > 1 || fUpper == MANY;
        }

        public String toString() {
            if (fUpper == MANY )
                if (fLower == 0 )
                    return "*";
                else 
                    return fLower + "..*";
            else
                if (fLower == fUpper )
                    return Integer.toString(fUpper);
                else 
                    return fLower + ".." + fUpper;
        }
    }
    
    private List<Range> mRanges = new ArrayList<MMultiplicity.Range>();
    
    /**
	 * @return the mRanges
	 */
	public List<Range> getRanges() {
		return Collections.unmodifiableList(mRanges);
	}
    
    /**
     * Creates a new multiplicity. You need to add ranges before the
     * multiplicity is actually valid.
     */
    public MMultiplicity() {
    }

    /**
     * Creates a multiplicity with given range.
     */
    public MMultiplicity(int lower, int upper) {
        addRange(lower, upper);
    }

    /**
     * Adds a range to this multiplicity.
     *
     * @exception IllegalArgumentException if the interval is illegal.
     */
    public void addRange(int lower, int upper) {
        mRanges.add(new Range(lower, upper));
    }

    /**
     * Tests whether this multiplicity contains a specified value.
     */
    public boolean contains(int n) {
        for (Range r : mRanges) {
            if (r.contains(n) )
                return true;
        }
        return false;
    }

    /**
     * UML 2.4.1 p. 97:
     * The query includesMultiplicity() checks whether this multiplicity includes all 
     * the cardinalities allowed by the specified multiplicity.
     *   MultiplicityElement::includesMultiplicity(M : MultiplicityElement) : Boolean;
     *     pre: self.upperBound()->notEmpty() and self.lowerBound()->notEmpty()
     *          and M.upperBound()->notEmpty() and M.lowerBound()->notEmpty()
     *     includesMultiplicity = (self.lowerBound() <= M.lowerBound()) and (self.upperBound() >= M.upperBound())
     *     
     * Because USE supports ranges of multiplicities, this query differs from the original specification.
     **/
    public boolean includesMultiplicity(MMultiplicity m, boolean includeLowerBounds) {
    	//TODO improve support of multiple ranges
    	if (mRanges.size() != m.mRanges.size())
    		return false;
    	
    	for (int i = 0; i < mRanges.size(); ++i) {
    		Range ourRange = mRanges.get(i);
    		Range otherRange = m.mRanges.get(i);
    		if (!((!includeLowerBounds || ourRange.contains(otherRange.fLower)) &&
    			  ourRange.contains(otherRange.fUpper)))
    			  return false;
    	}
    	
    	return true;
    }
    
    /**
     * Returns true if this multiplicity denotes a collection of
     * objects, i.e., the maximal upper range value is greater than
     * one.
     */
    public boolean isCollection() {
        for (Range r : mRanges) {
            if (r.isGreaterOne() )
                return true;
        }
        return false;
    }


    public String toString() {
        return StringUtil.fmtSeq(mRanges.iterator(), ",");
    }
}

