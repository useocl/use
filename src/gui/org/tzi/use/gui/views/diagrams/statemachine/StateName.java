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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.util.Direction;

/**
 * Draws the name of a state outside of the state
 * representation like an association name or rolename.
 * Used for initial and final states.
 * @author Lars Hamann
 *
 */
public class StateName extends PlaceableNode {

	/**
	 * The overall vertical (top and bottom) margin between the text of the EdgeProperty
	 * and the surrounding rectangle.
	 */
	static final int MARGIN_VERTICAL = 8;
	/**
	 * The overall (left and right) horizontal margin between the text of the EdgeProperty
	 * and the surrounding rectangle.
	 */
	static final int MARGIN_HORIZONTAL = 8;
	
	/**
	 * The displayed name.
	 */
	private final VertexNode vertexNode;
	
	/**
	 * Color used for normal drawing
	 */
	private Color color = Color.black;
	
	/**
	 * Color used for drawing when the state node or
	 * this node is selected.
	 */
	private Color selectedColor;
	
	/**
	 * Constructs a new StateName node for
	 * the given state.
	 * @param state
	 */
	public StateName(VertexNode state) {
		this.vertexNode = state;
		this.strategy = new StrategyRelativeToCorner(this, state, Direction.NORTH, 0, DeltaBasis.ABSOLUTE, 10, DeltaBasis.ABSOLUTE);
	}
	
	@Override
    public String getId() {
    	return new StringBuilder(vertexNode.getId()).append("::").append("StateName").toString(); 
    }
	
	@Override
	protected void onDraw(Graphics2D g) {
        if ( isSelected() ) {
        	drawSelected(g);
        }
        
        Graphics2D gClone = (Graphics2D) g.create();
        setGraphicsColor(gClone);
        drawTextCentered(this.vertexNode.getVertex().name(), getTextLayout(gClone), gClone);
	}

	/**
	 * Draws a stroked line around the bounds of the state name
	 * and lines to the attached state node.
	 * @param g
	 */
	protected void drawSelected(Graphics2D g) {
		Graphics2D gClone = (Graphics2D)g.create();
		
		BasicStroke newStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10.0F, new float[] { 5.0F, 5.0F }, 0.0F);

		gClone.setStroke(newStroke);
		gClone.setColor(Color.GRAY);
		gClone.draw(getBounds());
		
		Point2D attachedPosition = vertexNode.getCenter();
		
		Point2D sourcePoint = this.getIntersectionCoordinate(attachedPosition);
		gClone.drawLine(
				(int)Math.round(attachedPosition.getX()), (int)Math.round(attachedPosition.getY()), 
				(int)Math.round(sourcePoint.getX()), (int)Math.round(sourcePoint.getY()));
	}

	@Override
	public void doCalculateSize(Graphics2D g) {
		Rectangle2D nameBounds = getTextLayout(g).getBounds();
		setCalculatedWidth(nameBounds.getWidth() + MARGIN_HORIZONTAL);
		setCalculatedHeight(nameBounds.getHeight() + MARGIN_VERTICAL);
	}

	/**
	 * Cached instance of the text layout the name.
	 * Is lazy initialized at first call to {@link #getTextLayout(Graphics2D)} 
	 */
	protected TextLayout textLayout = null;
	
	/**
	 * Gets the {@link TextLayout} used to draw the state name.
	 * @param g The GraphicContext to draw with
	 * @return
	 */
	protected TextLayout getTextLayout(Graphics2D g) {
		if (textLayout != null) return textLayout;
		
		if (g == null)
			throw new NullPointerException("Textlayout was not initialized.");
		
		FontRenderContext frc = g.getFontRenderContext();
		Font font;

        font = g.getFont();
        
		textLayout = new TextLayout(this.vertexNode.getVertex().name(), font, frc);
		return textLayout;
	}

	@Override
	public String name() {
		return this.vertexNode.getVertex().name();
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the selectedColor
	 */
	public Color getSelectedColor() {
		return selectedColor;
	}

	/**
	 * @param selectedColor the selectedColor to set
	 */
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	protected void setGraphicsColor(Graphics2D g) {
		if (isSelected() || this.vertexNode.isSelected()) {
			g.setColor(getSelectedColor());
		} else {
			g.setColor(getColor());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#getStoreType()
	 */
	@Override
	protected String getStoreType() {
		return "StateName";
	}

}
