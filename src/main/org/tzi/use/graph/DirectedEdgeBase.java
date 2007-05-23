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

package org.tzi.use.graph;

/** 
 * Basic implementation of directed binary edges in a graph.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     DirectedGraph
 */


public class DirectedEdgeBase implements DirectedEdge {
    private Object fSource;
    private Object fTarget;

    /**
     * Construct a new edge.
     *
     * @exception NullPointerException source or target is null.
     */
    public DirectedEdgeBase(Object source, Object target) {
        if (source == null || target == null )
            throw new NullPointerException();
        fSource = source;
        fTarget = target;
    }

    // Query Operations

    /**
     * Returns the source node of this edge.
     */
    public Object source() {
        return fSource;
    }

    /**
     * Returns the target node of this edge.
     */
    public Object target() {
        return fTarget;
    }

    /**
     * Returns true if source and target of this edge connect the same node.
     */
    public boolean isReflexive() {
        return fSource.equals(fTarget);
    }

    public String toString() {
        return "(" + fSource + ", " + fTarget + ")";
    }
}
