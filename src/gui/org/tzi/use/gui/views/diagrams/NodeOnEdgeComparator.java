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

package org.tzi.use.gui.views.diagrams;

import java.util.Comparator;

import org.tzi.use.gui.main.sorting.CompareUtil;
import org.tzi.use.gui.main.sorting.CompareUtilImpl;

/**
 * @author gutsche
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NodeOnEdgeComparator implements Comparator {

    private CompareUtil fCompareUtil;
    
    public NodeOnEdgeComparator() {
        fCompareUtil = new CompareUtilImpl();
    }
    /**
     * Compares two NodeOnEdges by their ID.
     * @param obj1 first NodeOnEdge 
     * @param obj2 second NodeOnEdge 
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    public int compare( Object obj1, Object obj2) {
        int idOfNode1 = ((NodeOnEdge) obj1).getID();
        int idOfNode2 = ((NodeOnEdge) obj2).getID();
        return fCompareUtil.compareInt( idOfNode1, idOfNode2 );
    }
}
