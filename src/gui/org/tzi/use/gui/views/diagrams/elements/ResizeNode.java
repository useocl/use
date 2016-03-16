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
package org.tzi.use.gui.views.diagrams.elements;

import java.awt.Cursor;
import java.awt.Graphics2D;

import org.tzi.use.gui.views.diagrams.util.Direction;

/**
 * A node which represents the rectangles to
 * resize a node. 
 * @author Lars Hamann
 *
 */
public class ResizeNode extends PlaceableNode {

	private final Direction direction;
	
	private final PlaceableNode nodeToResize;
	
	public ResizeNode(PlaceableNode nodeToResize, Direction direction) {
		super();
		this.direction = direction;
		this.nodeToResize = nodeToResize;
		setExactWidth(5);
		setExactHeight(5);
	}
	
	@Override
	public String getId() {
		return "";
	}

	public Cursor getCursor() {
		switch (this.direction) {
		case NORTH:
			return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
		case NORTH_EAST:
			return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
		case EAST:
			return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
		case SOUTH_EAST:
			return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
		case SOUTH:
			return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
		case SOUTH_WEST:
			return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
		case WEST:
			return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
		case NORTH_WEST:
			return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
		default:
			return Cursor.getDefaultCursor();
		}
	}
	
	@Override
	protected void onDraw(Graphics2D g) {
		g.fillRect(getXInteger(), getYInteger(), 5, 5);
	}

	@Override
	protected void doCalculateSize(Graphics2D g) {
		
	}

	/**
     * Sets the position of this node to the new position while dragging.
     */
    public void setDraggedPosition( double deltaX, double deltaY ) {
    	double widthDelta = deltaX;
    	double heightDelta = deltaY;
    	
    	if (direction.isLocatedNorth())
    		heightDelta *= -1;
    	
    	if (direction.isLocatedWest())
    		widthDelta *= -1;
    	
    	if (!(direction.isLocatedWest() || direction.isLocatedEast()))
    		widthDelta = 0;
    	
    	if (!(direction.isLocatedNorth() || direction.isLocatedSouth()))
    		heightDelta = 0;

    	double expectedWidth = this.nodeToResize.getWidth() + widthDelta;
    	double oldWidth = this.nodeToResize.getWidth();
    	this.nodeToResize.setExactWidth(expectedWidth);
    	
    	double expectedHeight = this.nodeToResize.getHeight() + heightDelta;
    	double oldHeight = this.nodeToResize.getHeight();
    	this.nodeToResize.setExactHeight(expectedHeight);
    	
    	// The real movement with the correct sign
    	widthDelta = Math.copySign(oldWidth - this.nodeToResize.getWidth(), widthDelta); 
    	heightDelta = Math.copySign(oldHeight - this.nodeToResize.getHeight(), heightDelta);

    	double positionX = this.nodeToResize.getX() + (direction.isLocatedWest() ? -widthDelta : 0); 
    	double positionY = this.nodeToResize.getY() + (direction.isLocatedNorth() ? -heightDelta : 0);
    	
    	this.nodeToResize.setPosition(positionX, positionY);
    }

	@Override
	public String name() {
		return "Resize Node (" + direction.toString() + ")";
	}

	@Override
	protected String getStoreType() {
		return "";
	}
	
	public PlaceableNode getNodeToResize() {
		return nodeToResize;
	}

	public void setToCalculatedSize() {
		this.nodeToResize.setSizeIsCalculated(true);
	}
}
