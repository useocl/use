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

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.sys.MLink;
import org.w3c.dom.Element;

/**
 * Extends the {@link BinaryAssociationOrLinkEdge} to additionally  
 * draw the line between the rectangle of the association class/ objectlink 
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
     * The first way point the connection point is related to.
     * Without user defined way points this is the source way point.
     */
    WayPoint connectionPointRefWayPoint1;
    
    /**
     * The second way point the connection point is related to.
     * Without user defined way points this is the target way point.
     */
    WayPoint connectionPointRefWayPoint2;
    
    /**
     * The NodeBase of instance ClassNode or ObjectNode displaying 
     * the class part of the association class / object link.
     */
    private NodeBase fAssociationClassOrObjectNode; 
    
    PositionChangedListener<PlaceableNode> updater;
    
    /**
     * Use this constructor if it is a binary association class.
     */
    public BinaryAssociationClassOrObject( NodeBase source, NodeBase target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     NodeBase associationClassOrObjectNode,
                     DiagramView diagram, MAssociation assoc ) {
        super( source, target, sourceEnd, targetEnd, diagram, assoc );
        initNodeEdge( associationClassOrObjectNode );
    }
    
    /**
     * Use this constructor if it is a binary object link.
     */
    public BinaryAssociationClassOrObject( NodeBase source, NodeBase target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     NodeBase associationClassOrObjectNode,
                     DiagramView diagram, MLink link ) {
        super( source, target, sourceEnd, targetEnd, diagram, link );
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
                
        updater = new PositionChangedListener<PlaceableNode>() {
			@Override
			public void positionChanged(PlaceableNode source, Point2D newPosition,
					double deltaX, double deltaY) {
				updateConnectionPoint();
			}
		};
		
        if ( isReflexive() ) {
            this.connectionPointRefWayPoint1 = fRefNode2;
            this.connectionPointRefWayPoint2 = fRefNode3;
        } else {
        	this.connectionPointRefWayPoint1 = fSourceWayPoint;
            this.connectionPointRefWayPoint2 = fTargetWayPoint;
        }
        
        this.connectionPointRefWayPoint1.addPositionChangedListener(updater);
        this.connectionPointRefWayPoint2.addPositionChangedListener(updater);
        fAssociationClassOrObjectNode.addPositionChangedListener(updater);
        
        reIDNodes();
    }
    
    /**
     * Draws this nodeedge.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        drawBinaryEdge( g );
        drawNodeEdge( g );
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
     */
    private void updateConnectionPoint() {
    	Point2D firstPoint = connectionPointRefWayPoint1.getCenter();
    	Point2D secondPoint = connectionPointRefWayPoint2.getCenter();
    	
        Point2D conPoint = Util.calculateMidPoint( firstPoint, secondPoint );
        Point2D nep = fAssociationClassOrObjectNode.getIntersectionCoordinate( 
                                                 fAssociationClassOrObjectNode.getCenter(),
                                                 conPoint);
        
        fAssociationClassOrObjectWayPoint.setCenter( nep );
        associationConnectionPoint.setCenter( conPoint );
    }

    private void calculateConnectionReferencePoints() {
    	calculateConnectionReferencePoints(false);
    }
    
    private void calculateConnectionReferencePoints(boolean forceUpdate) {
    	int firstPointIndex = (this.fWayPoints.size() - 1) / 2;
    	int secondPointIndex = firstPointIndex + (this.fWayPoints.size() - 1)  % 2;
    			
    	WayPoint ref1 = this.fWayPoints.get(firstPointIndex);
    	WayPoint ref2 = this.fWayPoints.get(secondPointIndex);
    	boolean changed = false;
    	
    	if (!this.connectionPointRefWayPoint1.equals(ref1)) {
    		this.connectionPointRefWayPoint1.removePositionChangedListener(updater);
    		this.connectionPointRefWayPoint1 = ref1;
    		this.connectionPointRefWayPoint1.addPositionChangedListener(updater);
    		changed = true;
    	}
    	
    	if (!this.connectionPointRefWayPoint2.equals(ref2)) {
    		this.connectionPointRefWayPoint2.removePositionChangedListener(updater);
    		this.connectionPointRefWayPoint2 = ref2;
    		this.connectionPointRefWayPoint2.addPositionChangedListener(updater);
    		changed = true;
    	}
    	
    	if (changed || forceUpdate) {
    		updateConnectionPoint();
    	}
    }
    
    
    /* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.EdgeBase#occupiesThanAdd(int, int, int)
	 */
	@Override
	public WayPoint occupiesThanAdd(int x, int y, int clickCount) {
		WayPoint wp = super.occupiesThanAdd(x, y, clickCount);
		if (wp != null) {
			calculateConnectionReferencePoints();
		}
		return wp;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.EdgeBase#removeNodeOnEdge(org.tzi.use.gui.views.diagrams.waypoints.WayPoint)
	 */
	@Override
	public void removeNodeOnEdge(WayPoint node) {
		super.removeNodeOnEdge(node);
		calculateConnectionReferencePoints();
	}

	@Override
    protected String getStoreType() { return "NodeEdge"; }

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.BinaryAssociationOrLinkEdge#restoreAdditionalInfo(org.tzi.use.gui.util.PersistHelper, org.w3c.dom.Element, java.lang.String)
	 */
	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, Element element, int version) {
		super.restoreAdditionalInfo(helper, element, version);
		calculateConnectionReferencePoints(true);
	}
}