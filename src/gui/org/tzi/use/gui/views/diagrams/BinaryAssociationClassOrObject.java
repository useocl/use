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

import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * Draws the line between the rectangle of the associationclass/objectlink 
 * and the corresponding classes/objects.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class BinaryAssociationClassOrObject extends BinaryAssociationOrLinkEdge {
	/**
     * Association class / object link node.
     */
    WayPoint fAssociationClassOrObjectWayPoint; 
    
    /**
     * Point which connects the dashed line of an association class / object link
     * to the solid line.
     */
    WayPoint associationConnectionPoint;
        
    /**
     * The NodeBase of instance ClassNode or ObjectNode displaying 
     * the class part of the association class / object link.
     */
    private NodeBase fAssociationClassOrObjectNode; 
    
    PositionChangedListener<PlaceableNode> updater;
    
    /**
     * Use this constructor if it is a binary associationclass/objectlink.
     */
    public BinaryAssociationClassOrObject( NodeBase source, NodeBase target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     NodeBase associationClassOrObjectNode,
                     DiagramView diagram, MAssociation assoc, boolean isLink ) {
        super( source, target, sourceEnd, targetEnd, diagram, assoc, isLink );
        initNodeEdge( associationClassOrObjectNode );
    }

    /**
     * Initializes this NodeEdge.
     */
    private void initNodeEdge( NodeBase associationClassOrObjectNode ) {
        fAssociationClassOrObjectNode = associationClassOrObjectNode;
        
        fAssociationClassOrObjectWayPoint = new WayPoint( fSource, fTarget, this,
                                fNodesOnEdgeCounter++,
                                WayPointType.ASSOC_CLASS, 
                                getName(), fOpt );
        
        associationConnectionPoint = new WayPoint( fSource, fTarget, this,
                                 fNodesOnEdgeCounter++,
                                 WayPointType.ASSOC_CLASS_CON,
                                 getName(), fOpt );
        
        associationConnectionPoint.setWasMoved( false );
                
        if ( isReflexive() ) {
        
            updater = new PositionChangedListener<PlaceableNode>() {
				@Override
				public void positionChanged(PlaceableNode source, Point2D newPosition,
						double deltaX, double deltaY) {
					updateConnectionPoint( fRefNode2.getCenter(), 
                            			   fRefNode3.getCenter() );		
				}
			};
			
            fRefNode2.addPositionChangedListener(updater);
            fRefNode3.addPositionChangedListener(updater);
        } else {
            
            updater = new PositionChangedListener<PlaceableNode>() {
				@Override
				public void positionChanged(PlaceableNode source, Point2D newPosition,
						double deltaX, double deltaY) {
					updateConnectionPoint( fSourceWayPoint.getCenter(), 
										   fTargetWayPoint.getCenter());		
				}
			};
			
            fSourceWayPoint.addPositionChangedListener(updater);
            fTargetWayPoint.addPositionChangedListener(updater);
        }
        
        fAssociationClassOrObjectNode.addPositionChangedListener(updater);
        
        reIDNodes();
    }
    
    /**
     * Draws this nodeedge.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        drawNodeEdge( g );
        drawBinaryEdge( g );
    }

    public void update() {
    	updater.positionChanged(null, null, 0, 0);
    }
    
    /**
     * Draws the dashed line starting at the mid point of the solid line.
     * @param g The graphics object the dashed line is drawn into.
     */
    private void drawNodeEdge( Graphics2D g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }

        try {
            DirectedEdgeFactory.drawDashedEdge( g, 
                                                (int) associationConnectionPoint.getCenter().getX(), 
                                                (int) associationConnectionPoint.getCenter().getY(), 
                                                (int) fAssociationClassOrObjectWayPoint.getCenter().getX(), 
                                                (int) fAssociationClassOrObjectWayPoint.getCenter().getY() );
        } catch ( Exception ex ) {
            //ignore
        }
    }

    /**
     * Calculates the new position of the connection point where the 
     * dashed line connects to the solid line.
     * @param x1 x-coordinate of the first point.
     * @param y1 y-coordinate of the first point.
     * @param x2 x-coordinate of the second point.
     * @param y2 y-coordinate of the second point.
     */
    private void updateConnectionPoint( Point2D firstPoint, Point2D secondPoint ) {
        Point2D conPoint = Util.calculateMidPoint( firstPoint, secondPoint );
        Point2D nep = fAssociationClassOrObjectNode.getIntersectionCoordinate( 
                                                 fAssociationClassOrObjectNode.getCenter(),
                                                 conPoint);
        fAssociationClassOrObjectWayPoint.setCenter( nep );
        associationConnectionPoint.setCenter( conPoint );
    }

    @Override
    protected String getStoreType() { return "NodeEdge"; }
}