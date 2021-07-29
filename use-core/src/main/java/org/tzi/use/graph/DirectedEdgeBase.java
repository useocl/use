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

package org.tzi.use.graph;

/** 
 * Basic implementation of directed binary edges in a graph.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     DirectedGraph
 */


public class DirectedEdgeBase<N> implements DirectedEdge<N> {
    protected N fSource;
    protected N fTarget;

    /**
     * Constructs an empty edge.
     * Source and target must be set later.
     */
    public DirectedEdgeBase() {}
    
    /**
     * Construct a new edge.
     *
     * @exception NullPointerException source or target is null.
     */
    public DirectedEdgeBase(N source, N target) {
        if (source == null || target == null )
            throw new NullPointerException();
        fSource = source;
        fTarget = target;
    }

    /**
     * Sets the source node of this edge.
     * Should only be done while constructing a graph.
     */
    public void setSource(N source) {
        fSource = source;
    }
    
    /**
     * Returns the source node of this edge.
     */
    public N source() {
        return fSource;
    }

    /**
     * Sets the target node of this edge.
     * Should only be done while constructing a graph.
     */
    public void setTarget(N target) {
        fTarget = target;
    }
    
    /**
     * Returns the target node of this edge.
     */
    public N target() {
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