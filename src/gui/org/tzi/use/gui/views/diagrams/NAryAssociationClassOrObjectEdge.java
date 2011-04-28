/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;

/**
 * TODO
 * @author lhamann
 *
 */
public class NAryAssociationClassOrObjectEdge extends EdgeBase {
	/**
     * Association class / object link node.
     */
    WayPoint fNENode; 
    
    /**
     * Point which connects the dashed line of an association class / object link
     * to the solid line.
     */
    WayPoint fConNode;
    
    DiamondNode fNode;
    
    private boolean isLink;
    
    private double fX_old;
    private double fY_old;
    private double fX_con_old;
    private double fY_con_old;
    
    private boolean fFirstCall = true;
    
    /**
     * The NodeBase of instance ClassNode or ObjectNode displaying 
     * the class part of the associationclass/objectlink.
     */
    private NodeBase fNodeEdgeNode; 
    
	/**
     * Use this constructor if it is an t-nary associationclass/objectlink.
     */
    public NAryAssociationClassOrObjectEdge( NodeBase source, NodeBase target, DiamondNode node, NodeBase nodeEdgeNode, DiagramView diagram, MAssociation assoc, boolean isLink ) {
        super( source, target, assoc.name(), diagram );
        this.isLink = isLink;
        initNodeEdge( node, nodeEdgeNode);
    }
    
    /**
     * Initializes this NodeEdge.
     */
    private void initNodeEdge( DiamondNode node, NodeBase nodeEdgeNode ) {
        fNode = node;
        fNodeEdgeNode = nodeEdgeNode;
                
        fNENode = new WayPoint( fSource, fTarget, this,
                                fNodesOnEdgeCounter++,
                                WayPointType.ASSOC_CLASS, 
                                getName(), fOpt );
        fConNode = new WayPoint( fSource, fTarget, this,
                                 fNodesOnEdgeCounter++,
                                 WayPointType.ASSOC_CLASS_CON,
                                 getName(), fOpt );
        
        fConNode.setWasMoved( false );

        fWayPoints.add( 1, fConNode );
                
        reIDNodes();
    }
    
	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.EdgeBase#onDraw(java.awt.Graphics2D)
	 */
	@Override
    public void onDraw( Graphics2D g ) {
        drawNodeEdge( g );
        fNode.draw( g );
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
            DirectedEdgeFactory.drawDashedEdge( g, (int) fNode.getCenter().getX(), 
                                                (int) fNode.getCenter().getY(), 
                                                (int) fNodeEdgeNode.getCenter().getX(), 
                                                (int) fNodeEdgeNode.getCenter().getY() );
        } catch ( Exception ex ) {
            //ignore
        }
    }

    /**
     * Calculates the mid point of two given points which form a straight line.
     * This calculated mid point marks the connection point, where the 
     * dashed line connectes to the solid line.
     * @param x1 x-coordinate of the first point.
     * @param y1 y-coordinate of the first point.
     * @param x2 x-coordinate of the second point.
     * @param y2 y-coordinate of the second point.
     */
    private Point2D calcConnectionPoint( double x1, double y1, double x2, double y2 ) {
        // calculating midpoint for x on the edge between obj1
        // and obj2
        if ( x1 > x2 ) {
            x1 = ( ( x1 - x2 ) / 2 ) + x2;
        } else if ( x2 > x1 ) {
            x1 = ( ( x2 - x1 ) / 2 ) + x1;
        }

        // calculating midpoint for y on the edge between obj1
        // and obj2
        if ( y1 > y2 ) {
            y1 = ( ( y1 - y2 ) / 2 ) + y2;
        } else if ( y2 > y1 ) {
            y1 = ( ( y2 - y1 ) / 2 ) + y1;
        }
        
        return new Point2D.Double( x1, y1 );
    }
    
    /**
     * Updates the connection point.
     */
    public void update() {
        if ( fNodeEdgeNode.isDragged() ) { // && fDiagram.isDoAutoLayout() ) {
            fFirstCall = false;
            fX_old = fNodeEdgeNode.getX();
            fY_old = fNodeEdgeNode.getY();
            fX_con_old = fConNode.getX();
            fY_con_old = fConNode.getY();
        } else if ( !fNodeEdgeNode.isDragged() ) { //&& fDiagram.isDoAutoLayout() ) {
            double x = fX_old - fX_con_old;
            double y = fY_old - fY_con_old;
            double newX = x + fConNode.getX();
            double newY = y + fConNode.getY();
            fNENode.setPosition( newX, newY );
            fNodeEdgeNode.setPosition( newX, newY );
        }
        
        if ( fFirstCall && !fNodeEdgeNode.isSelected() ) {
            final int maxSpace = 30;
            double newX = 0.0;
            double newY = 0.0;
            
            if ( fNodeEdgeNode.getCenter().getX() >= fConNode.getCenter().getX() ) {
                newX = fConNode.getCenter().getX() + maxSpace + fNodeEdgeNode.getWidth()/2;
                newY = fConNode.getCenter().getY();
            } else {
                newX = fConNode.getCenter().getX() - maxSpace - fNodeEdgeNode.getWidth()/2;
                newY = fConNode.getCenter().getY();
            }
            fNENode.setPosition( newX, newY );
            fNodeEdgeNode.setPosition( newX, newY );
        } 
        
        updateConnectionPoint( fSourceWayPoint.getX(), fSourceWayPoint.getY(), fTargetWayPoint.getX(), fTargetWayPoint.getY() );
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
        if ( ( fWayPoints.size() <= 3 && !fConNode.isSelected() 
               && !fConNode.wasMoved() && !isReflexive() )
              || ( fWayPoints.size() <= 6 && !fConNode.isSelected() 
                   && !fConNode.wasMoved() && isReflexive() ) ) {
            Point2D conPoint = calcConnectionPoint( x1, y1, x2, y2 );
            Point2D nep = fNodeEdgeNode.getIntersectionCoordinate( 
                                                     fNodeEdgeNode.getCenter(),
                                                     conPoint);
            fNENode.setX( nep.getX() );
            fNENode.setY( nep.getY() );
            fConNode.setX( conPoint.getX() );
            fConNode.setY( conPoint.getY() );
        }
    }
    
	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.EdgeBase#isLink()
	 */
	@Override
	public boolean isLink() {
		return isLink;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.EdgeBase#storeGetType()
	 */
	@Override
	protected String storeGetType() {
		return "NodeEdge";
	}

	@Override    
    public String storePlacementInfo( boolean hidden ) {
        return "";
    }
	
	/**
     * Saves placement information about this edge iff this NodeEdge
     * participates in a tnary edge.
     * @param hidden If this edge should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public String storeInfo( boolean hidden ) {
        String xml = "";
        
        xml = LayoutTags.EDGE_O;
        
        if ( isLink() ) {
            xml += " type=\"NodeEdge\" kind=\"link\">" + LayoutTags.NL;
        } else {
            xml += " type=\"NodeEdge\" kind=\"association\">" + LayoutTags.NL;
        }
        
        xml += LayoutTags.INDENT + LayoutTags.NAME_O + fEdgeName 
               + LayoutTags.NAME_C + LayoutTags.NL;

        if ( !fWayPoints.isEmpty() ) {
            for (WayPoint n : fWayPoints) {
                xml += n.storePlacementInfo( hidden ) + LayoutTags.NL;
            }
        }
        
        xml += LayoutTags.INDENT + LayoutTags.HIDDEN_O + hidden 
               + LayoutTags.HIDDEN_C + LayoutTags.NL;
        
        xml += LayoutTags.EDGE_C + LayoutTags.NL;
        return xml;
    }
}
