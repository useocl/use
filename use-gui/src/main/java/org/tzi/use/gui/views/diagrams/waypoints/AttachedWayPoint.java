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

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyAttachedIntersection;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyFixed;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.util.Direction;

/**
 * A way point attached to a node.
 * This kind of way point always stays on the bounds of the attached node.
 * The way point returns the bounds of the attached node for position strategies.
 * 
 * @author Lars Hamann
 *
 */
public class AttachedWayPoint extends WayPoint {

	public interface ResetStrategy {
		PositionStrategy reset();
	}
	
	/**
	 * The node this way point is attached to.
	 */
	protected PlaceableNode attachedNode;
	
	/**
	 * If the way point should be reseted,
	 * this "code block" is used to accomplish this.
	 * Using such a code block allows an owner of a way point
	 * to specify a new default behavior, as it is done
	 * by reflexive edges.
	 */
	protected ResetStrategy resetStrategy = null;
	
	/**
	 * Constructs a new attached way point with the strategy {@link StrategyAttachedIntersection}.
	 * @param attachedNode The node this way point is attached to. Used to register listeners. 
	 * @param source
	 * @param target
	 * @param edge
	 * @param id
	 * @param type
	 * @param edgeName
	 * @param opt
	 */
	public AttachedWayPoint(PlaceableNode attachedNode, EdgeBase edge, WayPointType type, String edgeName, DiagramOptions opt) {
		super(edge, type, edgeName, opt);
		
		this.attachedNode = attachedNode;
	}

	@Override
	public void onInitialize() {
		super.onInitialize();
		// Use a default strategy if none was set
		if (this.strategy == StrategyFixed.instance)
			this.strategy = new StrategyAttachedIntersection(this);
	}

	public PlaceableNode getAttachedNode() {
		return this.attachedNode;
	}
	
	public WayPoint getAutopositionWayPoint() { 
		if (this.getNextWayPoint() != null)
			return this.getNextWayPoint();
		else
			return this.getPreviousWayPoint();
	}
	
	@Override
	public PlaceableNode getCalculationNode() {
		if (isUserDefined()) {
			return this;
		} else {
			return getAttachedNode();
		}
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}
	
	/**
	 * The position of a source way point is handled different when dragged, e.g.,
	 * it cannot be placed outside of the source node. 
	 */
	@Override
    public synchronized void setDraggedPosition( double movedX, double movedY ) {
		// Dragging switches strategy to fixed
		if (getStrategy() instanceof StrategyAttachedIntersection) {
			// Calculate the current offset
			movedX += getCenter().getX() - getAttachedNode().getX();
			movedY += getCenter().getY() - getAttachedNode().getY();
			setStrategy(new StrategyRelativeToCorner(this, getAttachedNode(), Direction.NORTH_WEST, 0, DeltaBasis.ABSOLUTE, 0, DeltaBasis.ABSOLUTE));
		}
			
		Point2D draggedPosition = getStrategy().calculateDraggedPosition(this, movedX, movedY);
		
		verifyUpdatePosition(draggedPosition);
				
		setIsUserDefined(true);
		setPosition(draggedPosition);
    }

	/**
	 * If the way point should be reseted,
	 * this "code block" is used to accomplish this.
	 * Using such a code block allows an owner of a way point
	 * to specify a new default behavior, as it is done
	 * by reflexive edges.
	 *
	 * @return the resetStrategy
	 */
	public ResetStrategy getResetStrategy() {
		return resetStrategy;
	}

	/**
	 * If the way point should be reseted,
	 * this "code block" is used to accomplish this.
	 * Using such a code block allows an owner of a way point
	 * to specify a new default behavior, as it is done
	 * by reflexive edges.
	 * 
	 * @param resetStrategy the resetStrategy to set
	 */
	public void setResetStrategy(ResetStrategy resetStrategy) {
		this.resetStrategy = resetStrategy;
	}
	
	/**
     * Sets the position of the edge property to its automatically computed one.
     */
	@Override
	public void setToAutoPosition() {
		setIsUserDefined(false);
		PositionStrategy s;
		if (this.resetStrategy != null)
			s = this.resetStrategy.reset();
		else
			s = new StrategyAttachedIntersection(this);
		setStrategy(s);
		updatePosition();
	}
	
	/**
	 * This method needs to modify the center position calculated by the attach strategy <code>autoPosition</code>
	 * to the new position of this way point given as left top coordinates.
	 * @param autoPosition The calculated center position.
	 */
	@Override
	public void verifyUpdatePosition(Point2D autoPosition) {
		// Position must be in bounds
		Point2D verifiedPosition = attachedNode.getIntersectionCoordinate(autoPosition); 
		autoPosition.setLocation(verifiedPosition);
		super.verifyUpdatePosition(autoPosition);
	}
	
	
	/**
	 * Depending of the attached side, we need to invert the offset
	 */
	@Override
	public double getMyOffset() { 
		if (this.getNextWayPoint() == null)
			return fEdge.getOffset();
		else if (this.getPreviousWayPoint() == null)
			return fEdge.getOffset() * -1;
		else
			return 0.0;
	}

	@Override
	public AttachedWayPoint getStrategyWayPoint() {
		return this;
	}

	/**
	 * Returns a height hint for the attached node.
	 * Allows attached way points to let the attached node "grow",
	 * if they require a lot of space. 
	 * @return
	 */
	public double getHeightHint() {
		return 0;
	}
}
