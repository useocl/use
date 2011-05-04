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
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.uml.mm.MAssociation;

/**
 * Represents a association name node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class AssociationName extends EdgeProperty {
	
    private List<String> fConnectedNodes;
    
    AssociationName( String name, NodeBase source, NodeBase target,
                     WayPoint sourceNode, WayPoint targetNode, 
                     DiagramOptions opt, EdgeBase edge, MAssociation assoc ) {
    	super(source, sourceNode, target, targetNode);
        fName = name;
        fAssoc = assoc;
        fOpt = opt;
        fEdge = edge;
    }
    
    AssociationName( String name, List<String> connectedNodes, DiagramOptions opt,
                     NodeBase source, MAssociation assoc ) {
        fName = name;
        fSource = source;
        fAssoc = assoc;
        fConnectedNodes = connectedNodes;
        fOpt = opt;
    }
    
    public String name() {
        return fName;
    }
    
    @Override
    protected void onDraw( Graphics2D g ) {
        if ( isSelected() ) {
        	drawSelected( g );
        }
        
        setColor( g );
        drawTextCentered(g);
        resetColor( g );
    }

	/**
	 * Calculates the default label position that is
	 * the center of the given points.
	 * @return
	 */
    @Override
	public Point2D.Double getDefaultPosition() {
		Point2D.Double result = new Point2D.Double();
		result.x = sourceWayPoint.getX() + ( targetWayPoint.getX() - sourceWayPoint.getX() ) / 2 - getBounds().getWidth() / 2 ;
		result.y = sourceWayPoint.getY() + ( targetWayPoint.getY() - sourceWayPoint.getY() ) / 2 - 4 ;
		return result;
	}
 
    public String ident() {
        String connectedNodes = "";
        if ( fConnectedNodes != null && !fConnectedNodes.isEmpty() ) {
            Iterator<String> it = fConnectedNodes.iterator();
            while ( it.hasNext() ) {
                connectedNodes += it.next() + "_";
            }
        } else {
            connectedNodes = fSource.name() + "_" + fTarget.name();
        }
        return "AssociationName." + fAssoc.name() + "_"
               + connectedNodes + "." + fName;
    }

}
