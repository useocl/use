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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.w3c.dom.Element;

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
    WayPoint fNENode; 
    
    /**
     * Point which connects the dashed line of an association class / object link
     * to the solid line.
     */
    WayPoint associationConnectionPoint;
     
    private double fX_old;
    private double fY_old;
    private double fX_con_old;
    private double fY_con_old;
    private boolean fFirstCall = true;
        
    /**
     * The NodeBase of instance ClassNode or ObjectNode displaying 
     * the class part of the association class / object link.
     */
    private NodeBase fAssociationOrLinkNode; 
    
    /**
     * Use this constructor if it is a binary associationclass/objectlink.
     */
    public BinaryAssociationClassOrObject( NodeBase source, NodeBase target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     NodeBase nodeEdgeNode,
                     DiagramView diagram, MAssociation assoc, boolean isLink ) {
        super( source, target, sourceEnd, targetEnd, diagram, assoc, isLink );
        initNodeEdge( nodeEdgeNode );
    }

    /**
     * Initializes this NodeEdge.
     */
    private void initNodeEdge( NodeBase nodeEdgeNode ) {
        fAssociationOrLinkNode = nodeEdgeNode;
        
        fNENode = new WayPoint( fSource, fTarget, this,
                                fNodesOnEdgeCounter++,
                                WayPointType.ASSOC_CLASS, 
                                getName(), fOpt );
        
        associationConnectionPoint = new WayPoint( fSource, fTarget, this,
                                 fNodesOnEdgeCounter++,
                                 WayPointType.ASSOC_CLASS_CON,
                                 getName(), fOpt );
        
        associationConnectionPoint.setWasMoved( false );
        
        if ( isReflexive() ) {
            fWayPoints.add( fWayPoints.indexOf( fRefNode3 ), associationConnectionPoint );
        } else {
            fWayPoints.add( 1, associationConnectionPoint );
        }
        
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

    /**
     * Draws the dashed line starting at the mid point of the solid line.
     * @param g The graphics object the dashed line is drawn into.
     */
    private void drawNodeEdge( Graphics g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        update();
        
        try {
            DirectedEdgeFactory.drawDashedEdge( g, 
                                                (int) associationConnectionPoint.getCenter().getX(), 
                                                (int) associationConnectionPoint.getCenter().getY(), 
                                                (int) fAssociationOrLinkNode.getCenter().getX(), 
                                                (int) fAssociationOrLinkNode.getCenter().getY() );
        } catch ( Exception ex ) {
            //ignore
        }
    }

    /**
     * Updates the connection point.
     */
    public void update() {
        if ( fAssociationOrLinkNode.isDragged() ) {
            fFirstCall = false;
            fX_old = fAssociationOrLinkNode.getX();
            fY_old = fAssociationOrLinkNode.getY();
            fX_con_old = associationConnectionPoint.getX();
            fY_con_old = associationConnectionPoint.getY();
        } else if ( !fAssociationOrLinkNode.isDragged() && ! fAssociationOrLinkNode.isSelected()) {
            double x = fX_old - fX_con_old;
            double y = fY_old - fY_con_old;
            double newX = x + associationConnectionPoint.getX();
            double newY = y + associationConnectionPoint.getY();
            fNENode.setPosition( newX, newY );
            fAssociationOrLinkNode.setPosition( newX, newY );
        }
        
        if ( fFirstCall && !fAssociationOrLinkNode.isSelected() ) {
            final int maxSpace = 30;
            double newX = 0.0;
            double newY = 0.0;
            
            if ( fAssociationOrLinkNode.getCenter().getX() >= associationConnectionPoint.getCenter().getX() ) {
                newX = associationConnectionPoint.getCenter().getX() + maxSpace + fAssociationOrLinkNode.getWidth()/2;
                newY = associationConnectionPoint.getCenter().getY();
            } else {
                newX = associationConnectionPoint.getCenter().getX() - maxSpace - fAssociationOrLinkNode.getWidth()/2;
                newY = associationConnectionPoint.getCenter().getY();
            }
            fNENode.setCenter( newX, newY );
            fAssociationOrLinkNode.setCenter( newX, newY );
        } 
        
        
        if ( isReflexive() ) {
            updateConnectionPoint( (int) fRefNode2.getCenter().getX(), (int) fRefNode2.getCenter().getY(), 
                                   (int) fRefNode3.getCenter().getX(), (int) fRefNode3.getCenter().getY() );
        } else {
            updateConnectionPoint( fSourceWayPoint.getCenter().getX(), fSourceWayPoint.getCenter().getY(), 
            					   fTargetWayPoint.getCenter().getX(), fTargetWayPoint.getCenter().getY() );
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
    private void updateConnectionPoint( double x1, double y1, double x2, double y2 ) {
        if ( ( fWayPoints.size() <= 3 && !associationConnectionPoint.isSelected() 
               && !associationConnectionPoint.wasMoved() && !isReflexive() )
              || ( fWayPoints.size() <= 6 && !associationConnectionPoint.isSelected() 
                   && !associationConnectionPoint.wasMoved() && isReflexive() ) ) {
            Point2D conPoint = Util.calculateMidPoint( x1, y1, x2, y2 );
            Point2D nep = fAssociationOrLinkNode.getIntersectionCoordinate( 
                                                     fAssociationOrLinkNode.getCenter(),
                                                     conPoint);
            fNENode.setCenter( nep );
            associationConnectionPoint.setCenter( conPoint );
        }
    }

    @Override
    protected String getStoreType() { return "NodeEdge"; }
    
    @Override
    protected void restoreAdditionalInfo(PersistHelper helper, Element element, String version) {
    	fFirstCall = false;
    }
}