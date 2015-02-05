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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.behavior.DrawingUtil;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;

/**
 * This class represents the actor as a node in communication diagrams.
 * 
 * @author Quang Dung Nguyen
 * 
 */
public class ActorNode extends PlaceableNode {
	// Margin from actor symbol to edges of this node
	private static final int MARGIN = 5;
	private final DiagramOptions fOpt;

	private Actor user;
	private int labelWidth;
	private boolean isUnmovable;

	public ActorNode(Actor user) {
		this.user = user;
		this.fOpt = new CommunicationDiagramOptions();
		isUnmovable = false;
	}

	public Actor getActorData() {
		return user;
	}

	public String ident() {
		return "User." + getActorData().getUserName();
	}

	@Override
	public String getId() {
		return getActorData().getUserName();
	}

	/**
	 * Drawing the actor node
	 */
	@Override
	protected void onDraw(Graphics2D g) {
		Color oldColor = g.getColor();

		Rectangle2D currentBounds = getBounds();
		FontMetrics fm = g.getFontMetrics();

		g.setColor(new Color(255, 255, 255, 1));
		g.fill(currentBounds);
		g.draw(currentBounds);

		int x = (int) currentBounds.getCenterX();
		int y = (int) currentBounds.getY();

		if (isSelected()) {
			g.setColor(fOpt.getNODE_SELECTED_COLOR());
		} else {
			g.setColor(fOpt.getEDGE_COLOR());
		}

		DrawingUtil.drawBigActor(x, y + MARGIN, g);
		g.drawString(user.getUserName(), (int) (currentBounds.getCenterX() - fm.stringWidth(user.getUserName()) / 2), (int) currentBounds.getMinY()
				+ DrawingUtil.TOTAL_HEIGHT_BIG + fm.getAscent() + 2 * MARGIN);
		g.setColor(oldColor);
	}

	/**
	 * Sets the correct size of the width and height of this object node. This
	 * method needs to be called before actually drawing the node. (Width and
	 * height are needed from other methods before the nodes are drawn.)
	 */
	@Override
	public void doCalculateSize(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();

		labelWidth = fm.stringWidth(user.getUserName());
		int nameHeight = fm.getAscent();
		int maxWidth;

		if (labelWidth < DrawingUtil.ARMS_LENGTH_BIG) {
			maxWidth = DrawingUtil.ARMS_LENGTH_BIG;
		} else {
			maxWidth = labelWidth;
		}

		setCalculatedBounds(maxWidth + 2 * MARGIN, nameHeight + DrawingUtil.TOTAL_HEIGHT_BIG + 3 * MARGIN);
	}

	@Override
	public String name() {
		return getActorData().getUserName();
	}

	/**
	 * Check if the actor node movable
	 * 
	 * @return true if actor node movable, otherwise false
	 */
	public boolean isUnmovable() {
		return isUnmovable;
	}

	/**
	 * Set the actor node unmovable.
	 * 
	 * @param unmovableActor
	 * 
	 */
	public void setUnmovable(boolean unmovableActor) {
		this.isUnmovable = unmovableActor;
	}

	@Override
	protected String getStoreType() {
		return "User Node";
	}

}
