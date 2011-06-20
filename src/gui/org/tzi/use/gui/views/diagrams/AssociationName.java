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
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.views.diagrams.util.Direction;
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
                     DiagramOptions opt, EdgeBase edge, MAssociation assoc, boolean isLink ) {
    	super(source, sourceNode, target, targetNode, isLink);
        fName = name;
        fAssoc = assoc;
        fOpt = opt;
        fEdge = edge;
        this.sourceWayPoint.addPositionChangedListener(new PositionChangedListener<PlaceableNode>() {
			@Override
			public void positionChanged(PlaceableNode source, Point2D newPosition, double deltaX, double deltaY) {
				AssociationName.this.calculatePosition();				
			}
		});
        
        this.targetWayPoint.addPositionChangedListener(new PositionChangedListener<PlaceableNode>() {
			@Override
			public void positionChanged(PlaceableNode source, Point2D newPosition, double deltaX, double deltaY) {
				AssociationName.this.calculatePosition(); 				
			}
		});
    }
    
    /**
     * Used by n-ary associations / links
     * @param name
     * @param connectedNodes
     * @param opt
     * @param source
     * @param assoc
     */
    AssociationName( String name, List<String> connectedNodes, DiagramOptions opt,
                     DiamondNode source, MAssociation assoc, boolean isLink ) {
        fName = name;
        fSource = source;
        fAssoc = assoc;
        fConnectedNodes = connectedNodes;
        fOpt = opt;
        this.isLink = isLink;
        
        this.fSource.addPositionChangedListener(new PositionChangedListener<PlaceableNode>() {
			@Override
			public void positionChanged(PlaceableNode source, Point2D newPosition, double deltaX, double deltaY) {
				AssociationName.this.calculatePosition();
			}
		});
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
        drawTextCentered( g );
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
		
		if (this.fEdge == null) {
			// n-Ary association
			Rectangle2D diamondNodeBounds = this.fSource.getBounds();
			result.x = diamondNodeBounds.getCenterX() - (getBounds().getWidth() / 2);
			result.y = diamondNodeBounds.getY() - 30;
		} else if (this.fEdge.isReflexive()) {
			BinaryAssociationOrLinkEdge binaryEdge = (BinaryAssociationOrLinkEdge)this.fEdge;
			
			if (binaryEdge.getReflexivePosition().isLocatedNorth()) {
				result.y = binaryEdge.getWayPointMostTo(Direction.NORTH).getCenter().getY() - getBounds().getHeight();
			} else {
				result.y = binaryEdge.getWayPointMostTo(Direction.SOUTH).getCenter().getY() + 4;
			}
			
			double westX = binaryEdge.getWayPointMostTo(Direction.WEST).getCenter().getX();
			double eastX = binaryEdge.getWayPointMostTo(Direction.EAST).getCenter().getX();
			
			result.x = westX + (eastX - westX) / 2 - getBounds().getWidth() / 2;
			
		} else {
			Point2D sourceCenter = sourceWayPoint.getCenter();
			Point2D targetCenter = targetWayPoint.getCenter();
			result.x = sourceCenter.getX() + ( targetCenter.getX() - sourceCenter.getX() ) / 2 - getBounds().getWidth() / 2 ;
			result.y = sourceCenter.getY() + ( targetCenter.getY() - sourceCenter.getY() ) / 2 - getBounds().getHeight();
		}
		
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
