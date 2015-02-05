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

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.RestoreLayoutException;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;
import org.w3c.dom.Element;

/**
 * A node placed relative to an attached way point, like for example, a role name 
 * @author Lars Hamann
 *
 */
public class StrategyRelativeToAttached extends StatefullPositionStrategy {

	public enum Placement {
		TOP,
		BOTTOM
	}
	
	Placement placement;
	
	/**
	 * The attached way point the strategy uses for
	 * calculating the position relative to.
	 */
	AttachedWayPoint attached;
	
	int offset_x = 4;
	
	int offset_y = 4;
	
	protected StrategyRelativeToAttached() {}
	
	public StrategyRelativeToAttached(PlaceableNode owner, AttachedWayPoint attached, Placement placement, int offset_x, int offset_y) {
		super(owner);
		this.placement = placement;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		setAttached(attached);
	}

	/**
	 * @return the attached
	 */
	public AttachedWayPoint getAttached() {
		return attached;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(AttachedWayPoint attached) {
		if (this.attached != null)
			this.attached.removePositionChangedListener(getOwnerUpdater());
		
		this.attached = attached.getStrategyWayPoint();
		this.attached.addPositionChangedListener(getOwnerUpdater());
	}
	
	@Override
	public boolean isCalculated() {
		return true;
	}
	
	@Override
	public Point2D doCalculatePosition(PlaceableNode owner) {
		if (Double.isNaN(attached.getX()) || Double.isNaN(attached.getY()))
			return new Point2D.Double();
		
		Point2D nextCenter = attached.getAutopositionWayPoint().getCenter();
		
		if (Double.isNaN(nextCenter.getX()) || Double.isNaN(nextCenter.getY()))
			nextCenter = attached.getCenter();
	
		PlaceableNode attachedBounds = attached.getCalculationNode(); 
		// First build a rectangle were the center of the owner is allowed to stay
		Rectangle2D ownerMovement = new Rectangle2D.Double(
				attachedBounds.getX() - owner.getWidth() / 2 - offset_x, 
				attachedBounds.getY() - owner.getHeight() / 2 - offset_y, 
				attachedBounds.getWidth() + owner.getWidth() + offset_x, 
				attachedBounds.getHeight() + owner.getHeight() + offset_y);
		
		int y = (placement == Placement.TOP ? -1 : 1);
		Point2D from = new Point2D.Double(attached.getCenter().getX() - owner.getWidth() / 2 - offset_x, attached.getCenter().getY() + y *  (owner.getHeight() / 2 - offset_y));
		Point2D to =   new Point2D.Double(nextCenter.getX() - owner.getWidth() / 2 - offset_x, nextCenter.getY() + y * (owner.getHeight() / 2 - offset_y));
		
		Point2D intP = Util.intersectionPoint(ownerMovement, from, to, true);

		return intP;
	}

	@Override
	public void debugDraw(PlaceableNode owner, Graphics2D g) {
		// First build a rectangle were the center of the owner is allowed to stay
		Rectangle2D ownerMovement = new Rectangle2D.Double(
				attached.getCalculationNode().getX() - owner.getWidth() / 2 - 2, 
				attached.getCalculationNode().getY() - owner.getHeight() / 2 - 2, 
				attached.getCalculationNode().getWidth() + owner.getWidth() + 4, 
				attached.getCalculationNode().getHeight() + owner.getHeight() + 4);
		
		int y = (placement == Placement.TOP ? -1 : 1);
		Point2D from = new Point2D.Double(attached.getCenter().getX() - owner.getWidth() / 2 - 2, attached.getCenter().getY() + y *  (owner.getHeight() / 2 - 4));
		Point2D to =   new Point2D.Double(attached.getAutopositionWayPoint().getCenter().getX() - owner.getWidth() / 2 - 2, attached.getAutopositionWayPoint().getCenter().getY() + y * (owner.getHeight() / 2 - 4));
		
		BasicStroke newStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10.0F, new float[] { 5.0F, 5.0F }, 0.0F);

		g.setStroke(newStroke);
		g.draw(ownerMovement);
		g.draw(new Line2D.Double(from, to));
	}

	@Override
	public void dispose() {
		attached.removePositionChangedListener(getOwnerUpdater());
	}
	
	
	@Override
	public void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper,
			Element parent, boolean hidden) {
		super.storeAdditionalInfo(owner, helper, parent, hidden);
		helper.appendChild(parent, "attached", attached.getId());
		helper.appendChild(parent, "offset_x", String.valueOf(offset_x));
		helper.appendChild(parent, "offset_y", String.valueOf(offset_y));
		helper.appendChild(parent, "placement", String.valueOf(placement));
	}

	@Override
	public void restoreAdditionalInfo(PlaceableNode owner,
			PersistHelper helper, int version) throws RestoreLayoutException {
		super.restoreAdditionalInfo(owner, helper, version);
		
		this.offset_x = helper.getElementIntegerValue("offset_x");
		this.offset_y = helper.getElementIntegerValue("offset_y");
		this.placement = Placement.valueOf(helper.getElementStringValue("placement"));
		
		String attachedId = helper.getElementStringValue("attached");
		
		PlaceableNode n = helper.getAllNodes().get(attachedId);
			
		if (n == null || !(n instanceof AttachedWayPoint))
			throw new RestoreLayoutException("Unknown attached way point source.");
		
		this.setAttached((AttachedWayPoint)n);
	}
}
