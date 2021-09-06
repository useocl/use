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
 * This strategy places the owner on the same place as the identity source.
 * 
 * @since 3.1.0
 * @author Lars Hamann
 */
public class StrategyIdentity extends StatefullPositionStrategy {

	private PlaceableNode identitySource;
	
	protected StrategyIdentity() {}
	
	public StrategyIdentity(PlaceableNode owner) {
		super(owner);
	}
	
	/**
	 * @param connectionWayPoint
	 * @param wayPoint
	 */
	public StrategyIdentity(PlaceableNode owner, PlaceableNode identitySource) {
		super(owner);
		setIdentitySource(identitySource);
	}

	public void setIdentitySource(PlaceableNode node) {
		if (this.identitySource != null)
			this.identitySource.removePositionChangedListener(getOwnerUpdater());
		
		this.identitySource = node;
		this.identitySource.addPositionChangedListener(getOwnerUpdater());
	}
	
	public PlaceableNode getIdentitySource() {
		return this.identitySource;
	}
	
	@Override
	public boolean isCalculated() {
		return true;
	}

	@Override
	public Point2D doCalculatePosition(PlaceableNode owner) {		
		return identitySource.getCenter();
	}

	@Override
	public Point2D calculateDraggedPosition(PlaceableNode owner, double movedX, double movedY) {
		identitySource.setDraggedPosition(movedX, movedY);
		return identitySource.getCenter();
	}

	@Override
	public void debugDraw(PlaceableNode owner, Graphics2D g) {

	}

	@Override
	public void dispose() {
		this.identitySource.removePositionChangedListener(getOwnerUpdater());
	}

	@Override
	public void storeAdditionalInfo(PlaceableNode owner, PersistHelper helper,
			Element parent, boolean hidden) {
		super.storeAdditionalInfo(owner, helper, parent, hidden);
		helper.appendChild(parent, "identitySource", identitySource.getId());
	}

	@Override
	public void restoreAdditionalInfo(PlaceableNode owner,
			PersistHelper helper, int version) throws RestoreLayoutException {
		super.restoreAdditionalInfo(owner, helper, version);
		String identityId = helper.getElementStringValue("identitySource");
		setIdentitySource(helper.getAllNodes().get(identityId));
			
		if (getIdentitySource() == null)
			throw new RestoreLayoutException("Unknown identity source.");
	}
}
