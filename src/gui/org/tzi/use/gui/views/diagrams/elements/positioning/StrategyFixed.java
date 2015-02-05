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
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.w3c.dom.Element;

/**
 * This strategy is used for placing elements
 * as is. This means, no automatically computations 
 * are done.
 *
 * @author Lars Hamann
 */
public class StrategyFixed implements PositionStrategy {

	public static final StrategyFixed instance = new StrategyFixed();
	
	private StrategyFixed() { }
	
	@Override
	public Point2D calculatePosition(PlaceableNode owner) {
		return owner.getCenter();
	}

	@Override
	public Point2D calculateDraggedPosition(PlaceableNode owner, double movedX, double movedY) {
		return new Point2D.Double(owner.getCenter().getX() + movedX, owner.getCenter().getY() + movedY);
	}

	@Override
	public void moveTo(PlaceableNode owner, double x, double y) { 
		owner.setPosition(x, y);
	}
	
	@Override
	public void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper, Element nodeElement, boolean hidden) {
		nodeElement.setAttribute("type", getClass().getName());
		helper.appendChild(nodeElement, "x", String.valueOf(owner.getX()));
        helper.appendChild(nodeElement, "y", String.valueOf(owner.getY()));
	}

	@Override
	public void restoreAdditionalInfo(PlaceableNode owner, PersistHelper helper, int version) {
		double x = helper.getElementDoubleValue("x");
		double y = helper.getElementDoubleValue("y");
		owner.setX(x);
		owner.setY(y);
	}
	
	@Override
	public void debugDraw(PlaceableNode owner, Graphics2D g) { }

	@Override
	public void dispose() {	}

	@Override
	public boolean isCalculated() {
		return false;
	}
	
	@Override
	public void reset() { }

	@Override
	public void recoverNonSerializedInformation(PositionStrategy strategy) {
		
	}
}
