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
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToAttached;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToAttached.Placement;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * Represents a Multiplicity node in a diagram. 
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public final class Multiplicity extends EdgeProperty {
    MAssociationEnd fAssocEnd;
    
    public Multiplicity( String id, MAssociationEnd assocEnd, AttachedWayPoint attached, DiagramOptions opt ) {
    	super(id, new PlaceableNode[] {attached}, false, opt, null);
    	
        fAssocEnd = assocEnd;
        fName = fAssocEnd.multiplicity().toString();
        
        setStrategy(new StrategyRelativeToAttached(this, attached, Placement.BOTTOM, 8, 8));
    }
    
    @Override
    public String getStoreType() {
    	return "multiplicity";
    }
    
    @Override
    public String toString() {
    	return "Multiplicity: " + fAssocEnd.multiplicity().toString();
    }
    
    //public MAssociationEnd get_fAssocEnd() {
    //	return fAssocEnd;
    //}
}
