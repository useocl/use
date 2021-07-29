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
package org.tzi.use.gui.views.diagrams.elements.positioning;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;

/**
 * This attach strategy calculates the way point position
 * using the center of the attached node and the center
 * of the opposite node if required.
 * @author Lars Hamann
 *
 */
public class StrategyAttachedIntersection extends StatefullPositionStrategy {

	protected StrategyAttachedIntersection() {}
	
	public StrategyAttachedIntersection(AttachedWayPoint owner) {
		super(owner);
	}

	@Override
	public boolean isCalculated() {
		return true;
	}
	
	@Override
	public AttachedWayPoint getOwner() {
		return (AttachedWayPoint)super.getOwner();
	}

	@Override
	public void setOwner(PlaceableNode owner) {
		if (!(owner instanceof AttachedWayPoint))
			throw new IllegalArgumentException("The owner of a StrategyAttachedIntersection needs to be an AttachedWayPoint.");
		
		// Cleanup if needed
		if (getOwner() != null)
			getOwner().getAttachedNode().removePositionChangedListener(getOwnerUpdater());
		
		super.setOwner(owner);
		
		// Start listening
		((AttachedWayPoint)owner).getAttachedNode().addPositionChangedListener(getOwnerUpdater());
	}
	
	@Override
	public Point2D doCalculatePosition(PlaceableNode sender) {
		// This overrides super.owner
		AttachedWayPoint owner = getOwner();

		// Initialization phase
		if (owner.getAutopositionWayPoint() == null)
			return new Point2D.Double();
		
		// Calculate the position using the offset specified in the edge
		Point2D newCenter;
		
		PlaceableNode ourNode =  owner.getCalculationNode();
		// Target can be an arbitrary way point, like a user defined one  
		PlaceableNode otherNode = owner.getAutopositionWayPoint().getCalculationNode();
		
		double alpha    = Util.calculateAngleBetween(ourNode.getCenter(), otherNode.getCenter());
		double alphaRad = Math.toRadians(alpha);
		
		// This calculates the possible space to use by using a lot on the line to the target
		double ourLength   = ourNode.getCenter().distance(ourNode.getIntersectionCoordinate(alpha - 90)) * 2;
		double otherLength = otherNode.getCenter().distance(otherNode.getIntersectionCoordinate(alpha - 90)) * 2;
		// Source and target way points align the offset of the edge,
		// to be able to calculate the values from both points
		double offset = Math.min(ourLength, otherLength) * owner.getMyOffset();
		
		// Multiply by -1 to keep edges aligned clockwise, i. e., one
		// edge is always "later" than the other.
		double offsetX = Math.sin(alphaRad) * offset * -1;
		double offsetY = Math.cos(alphaRad) * offset;
		
		Point2D sourceWithOffset = new Point2D.Double(
				ourNode.getCenter().getX() + offsetX,
				ourNode.getCenter().getY() + offsetY);
		    
		Point2D targetWithOffset = new Point2D.Double(
				otherNode.getCenter().getX() + offsetX,
				otherNode.getCenter().getY() + offsetY);
		
        newCenter = owner.getAttachedNode().getIntersectionCoordinate(sourceWithOffset, targetWithOffset);
        
        return newCenter;
	}

	@Override
	public Point2D calculateDraggedPosition(PlaceableNode owner, double movedX, double movedY) {
		AttachedWayPoint attachedOwner = (AttachedWayPoint)owner;
		
		Point2D newPos = new Point2D.Double(owner.getCenter().getX() + movedX, owner.getCenter().getY() + movedY);
		Point2D intersectionPoint = attachedOwner.getAttachedNode().getIntersectionCoordinate(newPos);
		intersectionPoint.setLocation(intersectionPoint.getX(), intersectionPoint.getY());
		return intersectionPoint;
	}

	@Override
	public void debugDraw(PlaceableNode owner, Graphics2D g) {
		
	}

	@Override
	public void dispose() {
		getOwner().getAttachedNode().removePositionChangedListener(getOwnerUpdater());
	}
}
