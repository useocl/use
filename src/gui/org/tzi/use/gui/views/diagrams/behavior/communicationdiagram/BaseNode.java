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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import org.tzi.use.gui.views.diagrams.DiagramOptionChangedListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.Util;

/**
 * This abstract class represents a base node for {@link ObjectBoxNode} and
 * {@link LinkBoxNode} in communication diagram.
 * 
 * @author Quang Dung Nguyen
 * 
 */

public abstract class BaseNode extends PlaceableNode {
	private static final int HORIZONTAL_OFFSET = 10;
	private static final int VERTICAL_OFFSET = 4;

	private final DiagramOptions fOpt;
	private final CommunicationDiagramView comDiaView;
	private DiagramOptionChangedListener fOptChaneListener;

	protected String label;

	// Underlined Label
	private AttributedString formattedLabel;
	private ObjectState lifeState = ObjectState.NEW;

	private Rectangle2D.Double nameRect = new Rectangle2D.Double();

	public BaseNode(CommunicationDiagramView parent, DiagramOptions opt) {
		comDiaView = parent;
		fOpt = opt;
		fOptChaneListener = new DiagramOptionChangedListener() {
			@Override
			public void optionChanged(String optionname) {
				if (optionname.equals("lifestate"))
					calculateBounds();
			}
		};
		this.fOpt.addOptionChangedListener(fOptChaneListener);
	}

	/**
	 * Format the label with colon and under line
	 * 
	 * @param label
	 *            the given label
	 */
	void initLabel(String label) {
		formattedLabel = new AttributedString(label);
		formattedLabel.addAttribute(TextAttribute.FONT, comDiaView.getFont());
		formattedLabel.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	}

	/**
	 * Change the current life state
	 * 
	 * @param stateString
	 *            new life state as string
	 */
	public void setState(ObjectState state) {
		this.lifeState = state;
	}

	public ObjectState getState() {
		return lifeState;
	}
	
	@Override
	public String getId() {
		return name();
	}

	/**
	 * Sets the correct size of the width and height of this object node. This
	 * method needs to be called before actually drawing the node. (Width and
	 * height are needed from other methods before the nodes are drawn.)
	 */
	@Override
	public void doCalculateSize(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		if (((CommunicationDiagramOptions) fOpt).isShowLifeStates()) {
			nameRect.width = fm.stringWidth(label + getLifeStatesString());
			nameRect.height = fm.getHeight();
		} else {
			nameRect.width = fm.stringWidth(label);
			nameRect.height = fm.getHeight();
		}
		calculateBounds();
	}

	protected void calculateBounds() {
		double width = nameRect.width;
		double height = nameRect.height;

		height += VERTICAL_OFFSET;
		width += HORIZONTAL_OFFSET;

		height = Math.max(height, getMinHeight());
		width = Math.max(width, getMinWidth());

		setCalculatedBounds(width, height);
	}

	/**
	 * Get all of life states of one node
	 * 
	 * @return all of life states as string
	 */
	public String getLifeStatesString() {
		return lifeState.toString();
	}

	/**
	 * Draws a box with an underlined label.
	 */
	@Override
	protected void onDraw(Graphics2D g) {
		Rectangle2D.Double currentBounds = getRoundedBounds();

		if (!Util.enlargeRectangle(currentBounds, 10).intersects(g.getClipBounds())) {
			return;
		}

		double x = currentBounds.getX();
		int y = (int) currentBounds.getY();

		int labelWidth;
		if (((CommunicationDiagramOptions) fOpt).isShowLifeStates()) {
			labelWidth = g.getFontMetrics().stringWidth(label + getLifeStatesString());
		} else {
			labelWidth = g.getFontMetrics().stringWidth(label);
		}

		if (isSelected()) {
			g.setColor(fOpt.getNODE_SELECTED_COLOR());
		} else {
			if (this instanceof LinkBoxNode) {
				g.setColor(((CommunicationDiagramOptions) fOpt).getLinkNodeColor());
			} else {
				g.setColor(fOpt.getNODE_COLOR());
			}
		}
		g.fill(currentBounds);

		g.setColor(fOpt.getNODE_FRAME_COLOR());
		g.draw(currentBounds);

		x = (currentBounds.getCenterX() - labelWidth / 2);
		y = (int) currentBounds.getY() + g.getFontMetrics().getAscent() + VERTICAL_OFFSET / 2;

		g.drawString(formattedLabel.getIterator(), Math.round(x), y);
		if (((CommunicationDiagramOptions) fOpt).isShowLifeStates()) {
			g.drawString(getLifeStatesString(),
					Math.round(currentBounds.getMaxX() - g.getFontMetrics().stringWidth(getLifeStatesString()) - HORIZONTAL_OFFSET / 2), y);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		this.fOpt.removeOptionChangedListener(fOptChaneListener);
	}

}
