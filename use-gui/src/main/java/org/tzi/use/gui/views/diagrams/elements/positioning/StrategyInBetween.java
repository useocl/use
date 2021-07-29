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
 * This strategy places the owning node in
 * the mass center of n related nodes with a given offset. 
 * 
 * @since 3.1.0
 * @author Lars Hamann
 *
 */
public class StrategyInBetween extends StatefullPositionStrategy {

	/**
	 * These nodes define the position of the owner of this strategy.
	 * The owning node is placed at the mass center of these nodes. 
	 */
	PlaceableNode[] related;
	
	/**
	 * The x-offset defined for this strategy.
	 */
	double offsetX;
	/**
	 * The y-offset defined for this strategy.
	 */
	double offsetY;
	
	protected StrategyInBetween() {}
	
	public StrategyInBetween(PlaceableNode owner, PlaceableNode[] related, double offsetX, double offsetY) {
		super(owner);
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		setRelatedNodes(related);
	}

	@Override
	public boolean isCalculated() {
		return true;
	}
	
	@Override
	public Point2D doCalculatePosition(PlaceableNode owner) {
		Point2D.Double middle = new Point2D.Double();

		double massCenterX = 0;
		double massCenterY = 0;
		Point2D center;
		for (int i = 0; i < related.length; ++i) {
			center = related[i].getCenter();
			massCenterX += center.getX();
			massCenterY += center.getY();
		}
		massCenterX = massCenterX / related.length;
		massCenterY = massCenterY / related.length;
		
		middle.x = massCenterX + offsetX;
		middle.y = massCenterY + offsetY;

		return middle;
	}

	@Override
	public void debugDraw(PlaceableNode owner, Graphics2D g) { }
		
	@Override
	public void dispose() {
		for (int i = 0; i < related.length; ++i) {
			related[i].removePositionChangedListener(getOwnerUpdater());
		}
	}

	
	/**
	 * @return the offsetX
	 */
	public double getOffsetX() {
		return offsetX;
	}

	/**
	 * @param offsetX the offsetX to set
	 */
	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @return the offsetY
	 */
	public double getOffsetY() {
		return offsetY;
	}

	/**
	 * @param offsetY the offsetY to set
	 */
	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * @param relatedNodes
	 */
	public void setRelatedNodes(PlaceableNode[] relatedNodes) {
		if (this.related != null) {
			for (int i = 0; i < related.length; ++i) {
				related[i].removePositionChangedListener(getOwnerUpdater());
			}
		}
		
		this.related = relatedNodes;
		
		for (int i = 0; i < related.length; ++i) {
			related[i].addPositionChangedListener(getOwnerUpdater());
		}
	}

	@Override
	public void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper,
			Element parent, boolean hidden) {
		super.storeAdditionalInfo(owner, helper, parent, hidden);
		helper.appendChild(parent, "offsetX", String.valueOf(offsetX));
		helper.appendChild(parent, "offsetY", String.valueOf(offsetY));
		
		Element relatedContainer = helper.createChild(parent, "related");
		relatedContainer.setAttribute("length", String.valueOf(related.length));
		
		for (int i = 0; i < related.length; ++i) {
			helper.appendChild(relatedContainer, "related" + i, related[i].getId());
		}
	}

	@Override
	public void restoreAdditionalInfo(PlaceableNode owner,
			PersistHelper helper, int version) throws RestoreLayoutException {
		super.restoreAdditionalInfo(owner, helper, version);
		offsetX = helper.getElementDoubleValue("offsetX");
		offsetY = helper.getElementDoubleValue("offsetY");
		
		helper.toFirstChild("related");
		int numRelated = Integer.valueOf(helper.getAttributeValue("length"));
		PlaceableNode[] restoredRelated = new PlaceableNode[numRelated];
		
		for (int i = 0; i < numRelated; ++i) {
			String id = helper.getElementStringValue("related" + i);
			PlaceableNode n = helper.getAllNodes().get(id);
			
			if (n == null) {
				helper.toParent();
				throw new RestoreLayoutException("Unknown related node.");
			}
			
			restoredRelated[i] = n;
		}
		setRelatedNodes(restoredRelated);
		helper.toParent();
	}
}
