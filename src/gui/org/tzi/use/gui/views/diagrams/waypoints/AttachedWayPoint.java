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

package org.tzi.use.gui.views.diagrams.waypoints;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.NodeBase;

/**
 * A way point attached to a node
 * @author lhamann
 *
 */
public abstract class AttachedWayPoint extends WayPoint {
	/**
	 * The delta to the x coordinate of the attached node.
	 * Used when a source point is positioned by the user.
	 */
	protected double deltaX;
	
	/**
	 * The delta to the y coordinate of the attached node.
	 * Used when a source point is positioned by the user.
	 */
	protected double deltaY;
	
	/**
	 * True when the position of this way point
	 * is forced by the user, e.g., it is placed by her. 
	 */
	protected boolean forcedPosition;
	
	public AttachedWayPoint(NodeBase source, NodeBase target, EdgeBase edge,
			int id, WayPointType type, String edgeName, DiagramOptions opt) {
		super(source, target, edge, id, type, edgeName, opt);
	}
	
	protected abstract NodeBase getAttachedNode();
	
	@Override
	public Point2D getCalculationPoint() {
		if (isUserDefined()) {
			return new Point2D.Double(
					getAttachedNode().getX() + deltaX + getWidth() / 2, 
					getAttachedNode().getY() + deltaY + getHeight() / 2);
		} else {
			return getAttachedNode().getCenter();
		}
	}
	
	@Override
	public void setCenter(Point2D p) {
		super.setCenter(p);
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	
	@Override
	public boolean isUserDefined() {
		return this.forcedPosition;
	}
	
	/**
	 * The position of a source way point is handled different when dragged, e.g.,
	 * it cannot be placed outside of the source node. 
	 */
	@Override
    public synchronized void setDraggedPosition( double movedX, double movedY ) {
		// Don't leave the bounds
		Point2D attachedCenter = getAttachedNode().getCenter();
		
		Point2D.Double newPosition = new Point2D.Double(
				getCenter().getX() + movedX,
				getCenter().getY() + movedY); 
				
		Point2D intersectionPoint = getAttachedNode().getIntersectionCoordinate(newPosition);

		while(intersectionPoint.equals(attachedCenter)) {
			// Enlarge the line until it intersects the attached node
			newPosition.x += newPosition.getX() - attachedCenter.getX();
			newPosition.y += newPosition.getY() - attachedCenter.getY();
			
			intersectionPoint = getAttachedNode().getIntersectionCoordinate(newPosition);
		}
		
		setCenter(intersectionPoint);

		Rectangle2D attachedBounds = getAttachedNode().getBounds();
		this.deltaX = bounds.x - attachedBounds.getMinX();
		this.deltaY = bounds.y - attachedBounds.getMinY();
		
		forcedPosition = true;
    }
	
	/**
	 * Updates the position of this way point wrt. user defined position or
	 * the attached classifier 
	 * @param nextWayPoint The next way point on the edge (used to calculate automatic position)
	 */
	public void updatePosition(WayPoint nextWayPoint) {
		updatePosition(0, 0, nextWayPoint);
	}
	
	/**
	 * Updates the position of this way point wrt. user defined position or
	 * the attached classifier with the given deltas
	 * @param calcualtionSource
	 * @param nextWayPoint
	 */
	public void updatePosition(double calcDeltaX, double calcDeltaY, WayPoint nextWayPoint) {
		if (!isUserDefined()) {
			this.setCenter(getAttachedNode().getIntersectionCoordinate(
					new Point2D.Double(
							getCalculationPoint().getX() + calcDeltaX,
							getCalculationPoint().getY() + calcDeltaY),
					nextWayPoint.getCalculationPoint()));
    	} else {
    		this.setX(getAttachedNode().getX() + deltaX);
    		this.setY(getAttachedNode().getY() + deltaY);
    	}
	}
}
