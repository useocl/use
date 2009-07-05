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

package org.tzi.use.gui.main.sorting;

import org.tzi.use.uml.mm.MPrePostCondition;

/**
 *
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 * @version $ProjectVersion: 0.393 $
 */
public class AlphabeticalConditionByOperationComparator implements SortingComparator<MPrePostCondition> {
    private CompareUtil compareUtil;

    /**
     * Constructor of AlphabeticalConditionByOperationComparator
     */
    public AlphabeticalConditionByOperationComparator() {
        this.compareUtil = new CompareUtilImpl();
    }

    /**
     * Sets the compareUtil, which supplies the basic comparison operations, to the given one
     * @param compareUtil the new compareUtil
     */
    public void setCompareUtil(final CompareUtil compareUtil) {
        this.compareUtil = compareUtil;
    }

    /**
     * Compares two Pre-/Postconditions by the name of their operation
     * @param object1 first Pre-/Postcondition (has to be of type MPrePostCondition)
     * @param object2 second Pre-/Postcondition (has to be of type MPrePostCondition)
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    public int compare(final MPrePostCondition firstCondition, final MPrePostCondition secondCondition) {
        final String firstString = createComparisonString(firstCondition);
        final String secondString = createComparisonString(secondCondition);
        return compareUtil.compareString(firstString, secondString);
    }

    private String createComparisonString(final MPrePostCondition condition) {
        final String operationName = condition.operation().name();
        final String name = condition.name();
        return operationName + "::" + name;
    }
}
