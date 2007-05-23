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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.mm;

import java.util.ArrayList;
import java.util.Iterator;

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
    
    private final class Range {
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
                (lower > upper || lower < 0 ||  upper < 1 ) )
                throw new IllegalArgumentException(
                                                   "Invalid multiplicity range `" + 
                                                   lower + ".." + upper + "'.");
            if (lower == MANY )
                lower = 0;
            fLower = lower;
            fUpper = upper;
        }

        /**
         * Test if range contains a specified value.
         */
        public boolean contains(int n) {
            return fLower <= n && ( fUpper == MANY || n <= fUpper );
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
    
    private ArrayList mRanges;  // (Range)

    /**
     * Creates a new multiplicity. You need to add ranges before the
     * multiplicity is actually valid.
     */
    public MMultiplicity() {
        mRanges = new ArrayList();
    }

    /**
     * Creates a multiplicity with given range.
     */
    public MMultiplicity(int lower, int upper) {
        this();
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
        Iterator it = mRanges.iterator();
        while (it.hasNext() ) {
            Range r = (Range) it.next();
            if (r.contains(n) )
                return true;
        }
        return false;
    }

    /**
     * Returns true if this multiplicity denotes a collection of
     * objects, i.e., the maximal upper range value is greater than
     * one.
     */
    public boolean isCollection() {
        Iterator it = mRanges.iterator();
        while (it.hasNext() ) {
            Range r = (Range) it.next();
            if (r.isGreaterOne() )
                return true;
        }
        return false;
    }


    public String toString() {
        return StringUtil.fmtSeq(mRanges.iterator(), ",");
    }
}

