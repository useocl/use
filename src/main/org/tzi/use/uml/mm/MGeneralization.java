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

import org.tzi.use.graph.DirectedEdge;

/** 
 * A generalization connects two classes. We currently don't support
 * generalizations of associations.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MGeneralization extends MModelElementImpl implements DirectedEdge<MClass> {
    private final MClass fParent;
    private final MClass fChild;

    /** 
     * Creates a new generalization.
     */
    MGeneralization(MClass child, MClass parent) {
        super("GEN_" + child.name() + "_" + parent.name());
        fChild = child;
        fParent = parent;
    }

    /**
     * Returns the child class of this generalization.
     */
    public MClass child() {
        return fChild;
    }

    /**
     * Returns the parent class of this generalization.
     */
    public MClass parent() {
        return fParent;
    }

    // DirectedEdge interface

    /**
     * Returns the source node of this edge.
     */
    public MClass source() {
        return fChild;
    }

    /**
     * Returns the target node of this edge.
     */
    public MClass target() {
        return fParent;
    }

    /**
     * Returns true if source and target of this edge connect the same Node.
     */
    public boolean isReflexive() {
        return fChild.equals(fParent);
    }

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitGeneralization(this);
    }
}
