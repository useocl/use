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

package org.tzi.use.gui.views.diagrams;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * Represents a Multiplicity node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class Multiplicity extends EdgeProperty {
    MAssociationEnd fAssocEnd;
    
    Multiplicity( MAssociationEnd assocEnd, NodeBase source, NodeBase target,
                  EdgeBase edge, WayPoint sourceNode, WayPoint targetNode, 
                  DiagramOptions opt, int side ) {
    	super(source, sourceNode, target, targetNode);
    	
        fAssocEnd = assocEnd;
        fName = fAssocEnd.multiplicity().toString();
        
        fAssoc = fAssocEnd.association();
        fEdge = edge;
        fOpt = opt;
        fSide = side;
        
    }
    
    @Override
    protected void onFirstDraw( Graphics2D g ) {
    	setRectangleSize( g );
    }
    
    @Override
    protected Point2D getDefaultPosition() {
		Direction targetLocation = Direction.getDirection(
				sourceWayPoint.getCenter(),
				targetWayPoint.getCenter());
		Point2D.Double result = new Point2D.Double(); 
		
		// simple approximation of multiplicity placement
        double fn1H = fSource.getHeight() / 2;
        
        if ( targetLocation.isLocatedSouth() ) {
            result.y = sourceWayPoint.getY() + fn1H + 15;
        } else {
            result.y = sourceWayPoint.getY() - fn1H - 10;
        }
        
        if ( targetLocation.isLocatedEast() ) {
            result.x = sourceWayPoint.getX() - getBounds().getWidth() - 2;
        } else {
            result.x = sourceWayPoint.getX() + 2;
        }
        
        return result;
    }
    
    /**
     * Draws a multiplicity on a binary edge.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        setColor( g );
        
        if ( isSelected() ) {
        	drawSelected(g);
        }
        
        drawTextCentered(g);
        
        resetColor( g );
    }

}
