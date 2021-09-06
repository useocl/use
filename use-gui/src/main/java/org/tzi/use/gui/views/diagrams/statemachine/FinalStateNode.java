/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams.statemachine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.uml.mm.statemachines.MState;
import org.w3c.dom.Element;

/**
 * @author Lars Hamann
 *
 */
public class FinalStateNode extends VertexNode {

	private static final int SIZE_DOT = 10;
	private static final int SIZE_CIRCEL = 14;
	
	protected StateName nameNode;
	
	/**
	 * @param v
	 */
	public FinalStateNode(MState v) {
		super(v);
		this.setBackColor(Color.BLACK);
		this.setExactHeight(SIZE_CIRCEL + 2);
		this.setExactWidth(SIZE_CIRCEL + 2);
		
		nameNode = new StateName(this);
		//TODO: Make setable
		nameNode.setSelectedColor(Color.orange);
		nameNode.setColor(Color.black);
	}

	@Override
    public String getId() {
    	return this.getVertex().name(); 
    }
	
	@Override
	protected void onDraw(Graphics2D g) {
		if (isSelected())
			g.setColor(getBackColorSelected());
		else if (this.isActive)
			g.setColor(Color.GREEN);
		else
			g.setColor(getBackColor());
		
		Rectangle2D currentBounds = getBounds();
		
		g.fillArc(
			(int)Math.round(currentBounds.getX() + (SIZE_CIRCEL - SIZE_DOT) / 2), 
			(int)Math.round(currentBounds.getY() + (SIZE_CIRCEL - SIZE_DOT) / 2),
			SIZE_DOT,
			SIZE_DOT,
			0, 360);
		
		g.drawArc(
				(int)Math.round(currentBounds.getX()), 
				(int)Math.round(currentBounds.getY()),
				SIZE_CIRCEL,
				SIZE_CIRCEL,
				0, 360);
		
		nameNode.draw(g);
	}

	@Override
	public void doCalculateSize(Graphics2D g) {
		nameNode.doCalculateSize(g);
	}

	@Override
	public PlaceableNode getRelatedNode(double x, double y) {
		if (nameNode.occupies(x, y)) {
			return nameNode;
		}
		
		return super.getRelatedNode(x, y);
	}

	@Override
	protected String getStoreType() {
		return "FinalState";
	}

	@Override
	protected void storeAdditionalInfo(PersistHelper helper,
			Element nodeElement, boolean hidden) {
		super.storeAdditionalInfo(helper, nodeElement, hidden);
		nameNode.storePlacementInfo(helper, nodeElement, hidden);
	}

	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, int version) {
		super.restoreAdditionalInfo(helper, version);
		
		if (helper.toFirstChild("node"))
			nameNode.restorePlacementInfo(helper, version);
		
		helper.toParent();
	}
}
