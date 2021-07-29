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

package org.tzi.use.gui.views.diagrams.elements.positioning;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.RestoreLayoutException;
import org.tzi.use.gui.views.diagrams.PositionChangedListener;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.w3c.dom.Element;

/**
 * Base class for strategies which require state information.
 * @since 3.1.0
 * @author Lars Hamann
 *
 */
public abstract class StatefullPositionStrategy implements PositionStrategy, PositionChangedListener {

	protected PlaceableNode owner = null;
	/**
	 * The x-offset defined by a drag operation for this strategy.
	 */
	private double userOffsetX = 0;
	/**
	 * The y-offset defined by a drag operation for this strategy.
	 */
	private double userOffsetY = 0;
	
	/**
	 * Used for deserializing
	 */
	protected StatefullPositionStrategy() {}
	
	public StatefullPositionStrategy(PlaceableNode owner) {
		setOwner(owner);
	}
	
	/**
	 * The owning node of the strategy.
	 * @return the owner
	 */
	public PlaceableNode getOwner() {
		return owner;
	}
	
	/**
	 * Sets the owning node of the strategy
	 * @return the owner
	 */
	public void setOwner(PlaceableNode owner) {
		this.owner = owner;
	}
	
	protected PositionChangedListener getOwnerUpdater() {
		return this;
	}
	
	@Override
	public final Point2D calculatePosition(PlaceableNode owner) {
		Point2D position = doCalculatePosition(owner);
		
		if (userOffsetX != 0 || userOffsetY != 0) {
			position = new Point2D.Double(
					position.getX() + userOffsetX,
					position.getY() + userOffsetY);
		}
		
		return position;
	}
	
	protected abstract Point2D doCalculatePosition(PlaceableNode owner);
	
	@Override
	public Point2D calculateDraggedPosition(PlaceableNode owner, double movedX, double movedY) {
		userOffsetX += movedX;
		userOffsetY += movedY;
		return new Point2D.Double(owner.getCenter().getX() + movedX, owner.getCenter().getY() + movedY);
	}
	
	@Override
	public void moveTo(PlaceableNode owner, double x, double y) { 
		// Center of node calculated by the strategy
		Point2D strategyPosition = this.doCalculatePosition(owner);
		// distance to the x position the node should be moved to (left border)
		this.userOffsetX = x + owner.getWidth() / 2 - strategyPosition.getX();
		this.userOffsetY = y + owner.getHeight() / 2 - strategyPosition.getY();
		getOwner().updatePosition();
	}
	
	@Override
	public void positionChanged(Object source, Point2D newPosition,
			double deltaX, double deltaY) {
		getOwner().updatePosition();
	}
	
	@Override
	public void boundsChanged(Object source, Rectangle2D oldBounds,
			Rectangle2D newBounds) {
		getOwner().updatePosition();
	}

	@Override
	public void reset() {
		userOffsetX = 0;
		userOffsetY = 0;
	}
	
	@Override
	public void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper, Element parent, boolean hidden) {
		parent.setAttribute("type", this.getClass().getName());
		helper.appendChild(parent, "userOffsetX", String.valueOf(userOffsetX));
		helper.appendChild(parent, "userOffsetY", String.valueOf(userOffsetY));
	}

	@Override
	public void restoreAdditionalInfo(PlaceableNode owner, PersistHelper helper, int version) throws RestoreLayoutException {
		setOwner(owner);
		userOffsetX = helper.getElementDoubleValue("userOffsetX");
		userOffsetY = helper.getElementDoubleValue("userOffsetY");
	}

	@Override
	public void recoverNonSerializedInformation(PositionStrategy strategy) {
		// Do nothing.
	}
}
