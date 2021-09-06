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

package org.tzi.use.gui.views.diagrams.elements;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyInBetween;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;

/**
 * Represents a association name node in a diagram. 
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public final class AssociationName extends EdgeProperty {

	private MLink link = null;
    
    public AssociationName( String id, String name, AttachedWayPoint source, AttachedWayPoint target, 
                     DiagramOptions opt, EdgeBase edge, MAssociation assoc, MLink link ) {
    	super(id, new PlaceableNode[] {source, target}, link != null, opt, null);
    	
        fName = name;
        fAssoc = assoc;
        fEdge = edge;
        this.link = link;
        this.setStrategy(new StrategyInBetween(this, new PlaceableNode[] {source, target}, 0, -10));
    }
    
    /**
     * Used by n-ary associations / links
     * @param name
     * @param connectedNodes
     * @param opt
     * @param source
     * @param assoc
     */
    AssociationName( String id, String name, DiagramOptions opt,
                     DiamondNode source, MAssociation assoc, MLink link ) {
    	super(id, new PlaceableNode[] {source}, link != null, opt, null);
        fName = name;
        fAssoc = assoc;
        this.link = link;
        this.setStrategy(new StrategyRelativeToCorner(this, source, Direction.NORTH, 0, DeltaBasis.ABSOLUTE, 10, DeltaBasis.ABSOLUTE));
    }
        
    @Override
    public String name() {
        return fName;
    }
    
    @Override
    public String getStoreType() {
    	return "associationName";
    }
    
    /**
     * Returns the link related to this association name or <code>null</code>
     * if not related to a link.
     * @return
     */
    public MLink getLink() {
    	return link;
    }

	@Override
	public String toString() {
		return "AssociationName: " + name();
	}
}
