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
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.RestoreLayoutException;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.w3c.dom.Element;

import com.google.common.base.Supplier;

/**
 * Base relative strategy:
 * <code>relativeX</code> and <code>relativeY</code>
 * specify the distance to the specified corner of the relative node.
 * @since 3.1.0 
 * @author Lars Hamann
 *
 */
public class StrategyRelativeToCorner extends StatefullPositionStrategy {

	public enum DeltaBasis {
		RELATIVE,
		ABSOLUTE
	}
	
	protected Direction corner;
	
	protected PlaceableNode relativeNode;
	
	protected DeltaBasis basisDeltaX;
	protected double deltaX;
	protected double maxDeltaX = Double.MAX_VALUE;
	protected Supplier<Double> widthCalculation = null;
	
	protected DeltaBasis basisDeltaY;
	protected double deltaY;
	protected double maxDeltaY = Double.MAX_VALUE;
	protected Supplier<Double> heightCalculation = null;
	
	protected StrategyRelativeToCorner() {}
	
	public StrategyRelativeToCorner(PlaceableNode owner, PlaceableNode relativeNode, Direction corner, double deltaX, DeltaBasis deltaXbasis, double deltaY, DeltaBasis deltaYbasis) {
		super(owner);
		
		setRelativeNode(relativeNode);
		
		this.corner = corner;
		
		this.deltaX = deltaX;
		this.basisDeltaX= deltaXbasis;
		
		this.deltaY = deltaY;
		this.basisDeltaY= deltaYbasis;
	}
	
	
	@Override
	public boolean isCalculated() {
		return true;
	}
	
	/**
	 * Sets the relative node to the given one.
	 * @param relativeNode
	 */
	public void setRelativeNode(PlaceableNode relativeNode) {
		if (this.relativeNode != null)
			this.relativeNode.removePositionChangedListener(getOwnerUpdater());
		
		this.relativeNode = relativeNode;
		this.relativeNode.addPositionChangedListener(getOwnerUpdater());
	}
	
	public PlaceableNode getRelativeNode() {
		return this.relativeNode;
	}
	
	/**
	 * Sets the relative information for the vertical placement.
	 * @param deltaX The delta value
	 * @param basis Basis for calculation
	 */
	public void setX(double deltaX, DeltaBasis basis) {
		this.deltaX = deltaX;
		this.basisDeltaX = basis;
	}
	
	/**
	 * @param deltaX
	 */
	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}
	
	/**
	 * @param basisX
	 */
	public void setBasisX(DeltaBasis basisX) {
		this.basisDeltaX = basisX;
	}
	
	public void setY(double deltaY, DeltaBasis basis) {
		this.deltaY = deltaY;
		this.basisDeltaY = basis;
	}

	/**
	 * @param deltaY
	 */
	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}
	
	/**
	 * @param basisY
	 */
	public void setBasisY(DeltaBasis basisY) {
		this.basisDeltaY = basisY;
	}
	
	public void setCorner(Direction corner) {
		this.corner = corner;
	}
	
	/**
	 * @return the maxDeltaX
	 */
	public double getMaxDeltaX() {
		return maxDeltaX;
	}

	/**
	 * @param maxDeltaX the maxDeltaX to set
	 */
	public void setMaxDeltaX(double maxDeltaX) {
		this.maxDeltaX = maxDeltaX;
	}

	/**
	 * @return the maxDeltaY
	 */
	public double getMaxDeltaY() {
		return maxDeltaY;
	}

	/**
	 * @param maxDeltaY the maxDeltaY to set
	 */
	public void setMaxDeltaY(double maxDeltaY) {
		this.maxDeltaY = maxDeltaY;
	}

	@Override
	public Point2D doCalculatePosition(PlaceableNode owner) {
		
		Point2D.Double rel = new Point2D.Double();
		Rectangle2D bounds = relativeNode.getBounds();
		
		if (corner == Direction.WEST || corner == Direction.EAST) {
			rel.y = bounds.getCenterY();
		} else {
			rel.y = (corner.isLocatedNorth() ? bounds.getMinY() : bounds.getMaxY());
		}
		
		if (corner == Direction.NORTH || corner == Direction.SOUTH) {
			rel.x = bounds.getCenterX();
		} else {
			rel.x = (corner.isLocatedWest() ? bounds.getMinX() : bounds.getMaxX());
		}

		double deltaXSign = (corner.isLocatedWest()  ? -1 : 1);
		double deltaYSign = (corner.isLocatedNorth() ? -1 : 1);
		double absDeltaX = 0;
		double absDeltaY = 0;
		
		if (basisDeltaX == DeltaBasis.ABSOLUTE) {
			absDeltaX = deltaX + ((corner.isLocatedEast() || corner.isLocatedWest()) ? owner.getWidth() / 2 : 0);
		} else if (basisDeltaX == DeltaBasis.RELATIVE) {
			double width = (widthCalculation == null ? bounds.getWidth() : widthCalculation.get());
			absDeltaX = width * deltaX;
		}
		rel.x += Math.min(absDeltaX, maxDeltaX) * deltaXSign;
		
		if (basisDeltaY == DeltaBasis.ABSOLUTE) {
			absDeltaY = deltaY + ((corner.isLocatedNorth() || corner.isLocatedSouth()) ? owner.getHeight() / 2 : 0);
		} else if (basisDeltaY == DeltaBasis.RELATIVE) {
			double height = (heightCalculation == null ? bounds.getHeight() : heightCalculation.get());
			absDeltaY = height * deltaY;
		}
		rel.y += Math.min(absDeltaY, maxDeltaY) * deltaYSign;

		return rel;
	}

	@Override
	public void debugDraw(PlaceableNode owner, Graphics2D g) {
		
	}
	
	@Override
	public void dispose() {
		this.relativeNode.removePositionChangedListener(getOwnerUpdater());
	}

	@Override
	public void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper, Element parent, boolean hidden) {
		super.storeAdditionalInfo(owner, helper, parent, hidden);
		helper.appendChild(parent, "basisDeltaX", basisDeltaX.toString());
		helper.appendChild(parent, "deltaX", String.valueOf(deltaX));
		helper.appendChild(parent, "maxDeltaX", String.valueOf(maxDeltaX));
		
		helper.appendChild(parent, "basisDeltaY", basisDeltaY.toString());
		helper.appendChild(parent, "deltaY", String.valueOf(deltaY));
		helper.appendChild(parent, "maxDeltaY", String.valueOf(maxDeltaY));
		
		helper.appendChild(parent, "corner", corner.toString());
		helper.appendChild(parent, "relativeNodeId", relativeNode.getId());
	}

	@Override
	public void restoreAdditionalInfo(PlaceableNode owner, PersistHelper helper, int version) throws RestoreLayoutException {
		super.restoreAdditionalInfo(owner, helper, version);
		
		this.basisDeltaX = DeltaBasis.valueOf(helper.getElementStringValue("basisDeltaX"));
		this.deltaX = helper.getElementDoubleValue("deltaX");
		this.maxDeltaX = helper.getElementDoubleValue("maxDeltaX");
		
		this.basisDeltaY = DeltaBasis.valueOf(helper.getElementStringValue("basisDeltaY"));
		this.deltaY = helper.getElementDoubleValue("deltaY");
		this.maxDeltaY = helper.getElementDoubleValue("maxDeltaY");
		
		this.corner = Direction.valueOf(helper.getElementStringValue("corner"));
		
		String relativeId = helper.getElementStringValue("relativeNodeId");
		setRelativeNode(helper.getAllNodes().get(relativeId));
		
		if (this.getRelativeNode() == null) {
			throw new RestoreLayoutException("Unknown relative node");
		}
	}

	@Override
	public void recoverNonSerializedInformation(PositionStrategy strategy) {
		if (strategy instanceof StrategyRelativeToCorner) {
			StrategyRelativeToCorner s = (StrategyRelativeToCorner)strategy;
			this.widthCalculation = s.widthCalculation;
			this.heightCalculation = s.heightCalculation;
		}
	}
	
	/**
	 * @param function
	 */
	public void setWidthCalculation(final Supplier<Double> calculationSupplier) {
		this.widthCalculation = calculationSupplier;
	}
	
	public void setHeightCalculation(final Supplier<Double> calculationSupplier) {
		this.heightCalculation = calculationSupplier;
	}
	
	@Override
	public String toString() {
		return "Rel. to cor.:" + getRelativeNode().toString() + ", x=" + deltaX  + " " + basisDeltaX + " (max=" + maxDeltaX + "), y=" + deltaY + " " + basisDeltaY + "(max=" + maxDeltaY + "), c=" + corner; 
	}
}
