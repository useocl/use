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

package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

/**
 * Contains the properties for the object boxes. Some of the properties can be
 * changed by the user by choosing "properties..." on the context menu of the
 * sequence diagram.
 * 
 * @author Antje Werner
 */

class OBProperties {
	/**
	 * Specifies if all boxes should have the same width (->true) or if
	 * individual widths are allowed (->false).
	 */
	private boolean fSameWidth;

	/**
	 * Specifies if all boxes should have the same height (->true) or if
	 * individual heights are allowed (->false).
	 */
	private boolean fSameHeight;

	/**
	 * Specifies if labels of boxes should be broken
	 */
	private boolean lineBreakLabel;

	/**
	 * The maximum height of all object boxes created so far.
	 */
	private int fHeight;

	/**
	 * The maximum width of all object boxes created so far.
	 */
	private int fWidth;

	/**
	 * The maximum height of all object boxes created so far.
	 */
	private int fMaxHeight;

	/**
	 * The maximum width of all object boxes created so far.
	 */
	private int fMaxWidth;

	/**
	 * The specified Width for all Boxes (-1 if not specified by the user).
	 */
	private boolean fManWidth;

	/**
	 * The specified Height of all Boxes (-1 if not specified by the user).
	 */
	private boolean fManHeight;

	/**
	 * Creates a new OBProperties object and sets the standard values.
	 */
	public OBProperties() {
		fSameWidth = false;
		fSameHeight = true;
		lineBreakLabel = false;
		fHeight = 20;
		fWidth = 70;
		fMaxWidth = 0;
		fMaxHeight = 0;
		fManWidth = false;
		fManHeight = false;
	}

	// set-Methods
	/**
	 * Sets the value of the sameWidth-Variable.
	 * 
	 * @param value true, if all boxes should have the same width, false
	 *            otherwise
	 */
	public void setSameWidth(boolean value) {
		fSameWidth = value;
	}

	/**
	 * Sets the value of the sameHeight-Variable.
	 * 
	 * @param value true, if all boxes should have the same height, false
	 *            otherwise
	 */
	public void setSameHeight(boolean value) {
		fSameHeight = value;
	}

	/**
	 * Sets the maxHeight-Variable
	 * 
	 * @param value the maximum height of alle object boxes created so far
	 */
	public void setHeight(int value) {
		fHeight = value;
	}

	/**
	 * Sets the maxWidth-Variable
	 * 
	 * @param value the maximum width of alle object boxes created so far
	 */
	public void setWidth(int value) {
		fWidth = value;
	}

	/**
	 * Sets the maxHeight-Variable
	 * 
	 * @param value the maximum height of alle object boxes created so far
	 */
	public void setMaxHeight(int value) {
		fMaxHeight = value;
	}

	/**
	 * Sets the maxWidth-Variable
	 * 
	 * @param value the maximum width of alle object boxes created so far
	 */
	public void setMaxWidth(int value) {
		fMaxWidth = value;
	}

	/**
	 * Sets the manWidth-Variable
	 * 
	 * @param value the width witch is manual given by a user
	 */
	public void setManWidth(boolean value) {
		fManWidth = value;
	}

	/**
	 * Sets the manheight-Variable
	 * 
	 * @param value the height witch is manual given by a user
	 */
	public void setManHeight(boolean value) {
		fManHeight = value;
	}

	// getMethods
	/**
	 * Indicates wheter or not all object boxes have the same width.
	 * 
	 * @return true if all object boxes have the same width; false otherwise.
	 */
	boolean sameWidth() {
		return fSameWidth;
	}

	/**
	 * Indicates wheter or not all object boxes have the same height.
	 * 
	 * @return true if all object boxes have the same height; false otherwise.
	 */
	boolean sameHeight() {
		return fSameHeight;
	}

	/**
	 * Returns the width manual given by the user (or -1 if the user has not
	 * given a width).
	 * 
	 * @return the width given by the user
	 */
	int getWidth() {
		return fWidth;
	}

	/**
	 * Returns the height manual given by the user (or -1 if the user has not
	 * given a height).
	 * 
	 * @return the height given by the user
	 */
	int getHeight() {
		return fHeight;
	}

	/**
	 * Returns the width manual given by the user (or -1 if the user has not
	 * given a width).
	 * 
	 * @return the width given by the user
	 */
	int getMaxWidth() {
		return fMaxWidth;
	}

	/**
	 * Returns the height manual given by the user (or -1 if the user has not
	 * given a height).
	 * 
	 * @return the height given by the user
	 */
	int getMaxHeight() {
		return fMaxHeight;
	}

	/**
	 * Returns the maximum width of all object boxes created so far.
	 * 
	 * @return the maximum width of all object boxes.
	 */
	boolean manWidth() {
		return fManWidth;
	}

	/**
	 * Returns the maximum height of all object boxes created so far.
	 * 
	 * @return the maximum height of all object boxes
	 */
	boolean manHeight() {
		return fManHeight;
	}

	/**
	 * Checks if the given value is bigger then the maxWidth variable.
	 * 
	 * @param value the possibly new maximum width
	 * @return true, if value is the bigger then the maximum width; else
	 *         otherwise
	 */
	boolean maxWidth(int value) {
		return (value > fMaxWidth);
	}

	/**
	 * Checks if the given value is bigger then the maxHeight variable.
	 * 
	 * @param value the possibly new maximum height
	 * @return true, if value is the bigger then the maximum height; else
	 *         otherwise
	 */
	boolean maxHeight(int value) {
		return (value > fMaxHeight);
	}

	/**
	 * Check if label should be braked
	 * 
	 */
	boolean isLineBreakLabel() {
		return lineBreakLabel;
	}

	/**
	 * Set if label should be braked
	 */
	void setLineBreak(boolean lineBreak) {
		lineBreakLabel = lineBreak;
	}
}