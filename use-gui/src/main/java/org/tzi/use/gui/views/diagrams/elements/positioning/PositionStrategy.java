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

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.RestoreLayoutException;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.w3c.dom.Element;

/**
 * <p>A position strategy handles the placement of a node.
 * These strategies can be completely manual like the identity strategy
 * ({@link StrategyIdentity} or a combination of automatic values and
 * user information from dragging elements from their automatic position 
 * to a user defined one.</p>
 * <p>This dragging information can be used if the calculated position of
 * a node changes. The most simple case is to move the node by the previously
 * dragged deltas, like it is done in the strategy {@link StrategyInBetween}.</p>
 * @author Lars Hamann
 *
 */
public interface PositionStrategy {
	
	/**
	 * If <code>true</code>, the position of a strategy is calculated.
	 * This means, a call to {@link PlaceableNode#setPosition(double, double)}
	 * from outside does not change the position.
	 * Instead it forces an update by calling the strategy.
	 * @return
	 */
	boolean isCalculated();
	
	/**
	 * Where should the center of the node be places?
	 * @param owner
	 * @return
	 */
	Point2D calculatePosition(PlaceableNode owner);
		
	/**
	 * Handles a user movement of the owned node.
	 * Returns the new center position after the move.
	 * @param movedX
	 * @param movedY
	 * @return The new position after a move.
	 */
	Point2D calculateDraggedPosition(PlaceableNode owner, double movedX, double movedY);

	/**
	 * Notifies the strategy to align its values
	 * in a way such that the next time {@link #calculatePosition(PlaceableNode)}
	 * is called, the position is equals to the provided <code>x</code> and
	 * <code>y</code> values.
	 * @param x
	 * @param y
	 */
	void moveTo(PlaceableNode owner, double x, double y);
	
	/**
	 * Stores information about the settings of a strategy.
	 * @param owner The owner of the strategy. required for stateless strategies.
	 * @param helper
	 * @param nodeElement
	 * @param hidden
	 */
	void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper, Element nodeElement, boolean hidden);
		
	void restoreAdditionalInfo(PlaceableNode owner, PersistHelper helper, int version) throws RestoreLayoutException;
	
	void debugDraw(PlaceableNode owner, Graphics2D g);

	/**
	 * Free up any resources and remove listeners from related nodes.
	 */
	void dispose();

	/**
	 * Resets the strategy to the initial state, i. w., no user changes. 
	 */
	void reset();

	/**
	 * Allows a strategy to recover information from the current one,
	 * before it replaces the given <code>strategy</code>.
	 * @param strategy
	 */
	void recoverNonSerializedInformation(PositionStrategy strategy);
}
