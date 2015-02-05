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

import java.awt.BasicStroke;
import java.awt.Font;

/**
 * Contains the properties for the sequence diagram. Some of the properties can
 * be changed by the user by choosing "properties..." on the context menu of the
 * sequence diagram.
 * 
 * @author Antje Werner
 */
class SDProperties {
	public static final int STATE_NODE_VERTICAL_MARGIN = 5;

	public static final int STATE_NODE_HORIZON_MARGIN = 10;
	
	/**
	 * Needed for the Frames.
	 */
	private static final float DASH1[] = { 5.0f };

	public static float[] getDASH1() {
		return DASH1;
	}

	/**
	 * The dashed Stroke, used for example for the lifelines or for the answers
	 * of the activations.
	 */
	private static final BasicStroke DASHEDSTROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, DASH1, 0.0f);

	public BasicStroke getDASHEDSTROKE() {
		return DASHEDSTROKE;
	}

	/**
	 * Specifies if Antialiasing should be used by drawing the diagram.
	 */
	private boolean fDoAntiAliasing;

	/**
	 * Specifies if args and return values should be shown within the activation
	 * messages and object boxes.
	 */
	private boolean fShowValues;

	/**
	 * Specifies if steps not related to should be omitted by drawing.
	 */
	private boolean fCompactDisplay;

	/**
	 * Specifies if create commands should be shown in the sequence diagram.
	 */
	private boolean fShowCreate;

	/**
	 * Specifies if set commands should be shown in the sequence diagram.
	 */
	private boolean fShowSet;

	/**
	 * Specifies if destroy commands should be shown in the sequence diagram.
	 */
	private boolean fShowDestroy;

	/**
	 * Specifies if insert commands should be shown in the sequence diagram.
	 */
	private boolean fShowInsert;

	/**
	 * Specifies if delete commands should be shown in the sequence diagram.
	 */
	private boolean fShowDelete;

	/**
	 * Specifies if each lifeline should have an individual distance (due to the
	 * longest message length) to the antecessor lifeline or if the distance
	 * should be the same between all lifelines.
	 */
	private boolean fIndividualLl;

	/**
	 * Specifies if the distance between two lifelines depends on the longest
	 * message or not.
	 */
	private boolean fLikeLongMess;

	/**
	 * The y_value on which the lifelines start.
	 */
	private int fYStart;

	/**
	 * The step of the time axis, that is the distance between activations.
	 */
	private int fYStep;

	/**
	 * The step of the lifelines, that is the distance between them.
	 */
	private int fXStep;

	/**
	 * The offset for nested activations.
	 */
	private int fFrameOffset;

	/**
	 * The y-value on which the scrolling-zigzag begins.
	 */
	private static int fYScroll;

	/**
	 * The longest activation message / distance of involved lifelines
	 */
	private int fMaxActMess;

	/**
	 * The distance between activations specified by the user.
	 */
	private int fActManDist;

	/**
	 * The margin-values for the sequence diagram
	 */
	private int fLeftMargin, fRightMargin, fTopMargin, fBottomMargin;

	/**
	 * The Font used for drawing messages and so on.
	 */
	private Font fFont;

	/**
	 * The size of the Font used for drawing messages and so on.
	 */
	private int fFontSize;

	/**
	 * The width of activation frames (also of the actor frame).
	 */
	private int fFrameWidth;
	
	/**
	 * Specifies if states of objects should be shown on sequence diagrams
	 */
	private boolean showStates;

	/**
	 * The width of state nodes
	 */
	private int stateNodeWidth;
	
	/**
	 * The height of state nodes
	 */
	private int stateNodeHeight;

	/**
	 * Specifies if size of state nodes were defined by user
	 */
	private boolean isStateNodeManualSize;
	
	private SequenceDiagram sequenceDiagram;

	/**
	 * Creates a new SDProperties-Object and sets the standard values.
	 */
	public SDProperties(SequenceDiagram sd) {
		this.sequenceDiagram = sd;
		fDoAntiAliasing = true;
		fShowValues = true;
		showStates = false;
		fCompactDisplay = true;
		fShowCreate = false;
		fShowSet = false;
		fShowDestroy = false;
		fShowInsert = false;
		fShowDelete = false;
		fIndividualLl = false;
		fLikeLongMess = false;
		isStateNodeManualSize = false;

		fYStart = 70;
		fYStep = 25;
		fXStep = 140;
		fFrameOffset = 4;
		fYScroll = 62;
		fLeftMargin = 20;
		fRightMargin = 20;
		fTopMargin = 20;
		fBottomMargin = 20;
		fActManDist = -1;
		fFont = Font.getFont("use.gui.view.sequencediagram", getFont());
		fFontSize = fFont.getSize();
		fFrameWidth = 10;
		stateNodeWidth = 80;
		stateNodeHeight = 20;
	}

	/**
	 * Returns the width of state nodes
	 * 
	 * @return the stateNodeWidth
	 */
	public int getStateNodeWidth() {
		return stateNodeWidth;
	}

	/**
	 * Set the width of state nodes
	 * 
	 * @param stateNodeWidth the width of state nodes to set
	 */
	public void setStateNodeWidth(int stateNodeWidth) {
		this.stateNodeWidth = stateNodeWidth;
	}
	
	/**
	 * Returns the height of state nodes
	 * 
	 * @return the height of state nodes
	 */
	public int getStateNodeHeight() {
		return stateNodeHeight;
	}

	/**
	 * Set the height of state nodes
	 * 
	 * @param stateNodeHeight the height of state nodes to set
	 */
	public void setStateNodeHeight(int stateNodeHeight) {
		this.stateNodeHeight = stateNodeHeight;
	}

	/**
	 * Check if the size of state nodes was defined by user
	 * 
	 * @return true, if size of state nodes was defined by user, false otherwise
	 */
	public boolean isStateNodeManualSize() {
		return isStateNodeManualSize;
	}

	/**
	 * @param isManual
	 */
	public void setStateNodeManualSize(boolean isManual) {
		this.isStateNodeManualSize = isManual;
	}

	// Set-Methods
	/**
	 * Should the states of objects be shown on sequence diagram
	 * 
	 * @param isShown true, if states should be shown, false otherwise
	 */
	public void setLifelineStates(boolean isShown) {
		showStates = isShown;
	}

	/**
	 * Sets the value of the fAntiAliasing-Variable.
	 * 
	 * @param value true, if antialiasing should be used, false otherwise
	 */
	public void setAntiAliasing(boolean value) {
		fDoAntiAliasing = value;
	}

	/**
	 * Sets the value of the fShowValues-Variable.
	 * 
	 * @param value true, if values should be shown, false otherwise
	 */
	public void showValues(boolean value) {
		fShowValues = value;
	}

	/**
	 * Sets the value of the fCompactDisplay-Variable.
	 * 
	 * @param value true, if steps not related to should be omitted by drawing,
	 *            false otherwise
	 */
	public void compactDisplay(boolean value) {
		fCompactDisplay = value;
	}

	/**
	 * Sets the value of the fShowCreate-Variable.
	 * 
	 * @param value true, if create should be drawn in the diagram, false
	 *            otherwise.
	 */
	public void showCreate(boolean value) {
		fShowCreate = value;
	}

	/**
	 * Sets the value of the fShowSet-Variable.
	 * 
	 * @param value true, if set should be drawn in the diagram, false
	 *            otherwise.
	 */
	public void showSet(boolean value) {
		fShowSet = value;
	}

	/**
	 * Sets the value of the fShowDestroy-Variable.
	 * 
	 * @param value true, if destroy should be drawn in the diagram, false
	 *            otherwise.
	 */
	public void showDestroy(boolean value) {
		fShowDestroy = value;
	}

	/**
	 * Sets the value of the fShowinsert-Variable.
	 * 
	 * @param value true, if insert should be drawn in the diagram, false
	 *            otherwise.
	 */
	public void showInsert(boolean value) {
		fShowInsert = value;
	}

	/**
	 * Sets the value of the fShowDeslete-Variable.
	 * 
	 * @param value true, if delete should be drawn in the diagram, false
	 *            otherwise.
	 */
	public void showDelete(boolean value) {
		fShowDelete = value;
	}

	/**
	 * Sets the activation step value.
	 * 
	 * @param value the new distance between activations
	 */
	public void setActStep(int value) {
		fYStep = value;
	}

	/**
	 * Sets the lifeline step value.
	 * 
	 * @param value the new distance between lifelines
	 */
	public void setLlStep(int value) {
		fXStep = value;
	}

	/**
	 * Sets the maximum activation message.
	 * 
	 * @param value the new maximum activation message
	 */
	public void setMaxActMess(int value) {
		fMaxActMess = value;
	}

	/**
	 * Sets the frame offset value
	 * 
	 * @param value the new value for the frame offset
	 */
	public void setFrOffset(int value) {
		fFrameOffset = value;
	}

	/**
	 * Sets the width of a frame.
	 * 
	 * @param value the new width
	 */
	public void setFrameWidth(int value) {
		fFrameWidth = value;
		fFrameOffset = value / 2 - 2;
	}

	/**
	 * Sets the left margin value.
	 * 
	 * @param value the new left margin
	 */
	public void setLeftMargin(int value) {
		fLeftMargin = value;
	}

	/**
	 * Sets the right margin value.
	 * 
	 * @param value the new right margin
	 */
	public void setRightMargin(int value) {
		fRightMargin = value;
	}

	/**
	 * Sets the top margin value.
	 * 
	 * @param value the new top margin
	 */
	public void setTopMargin(int value) {
		fTopMargin = value;
		// top Margin + height of actor (->40) + 10 (as distance)
		fYStart = fTopMargin + 40 + 10;
		// Y_START - distance (->5)
		fYScroll = fYStart - 5;
	}

	/**
	 * Sets the bottom margin value.
	 * 
	 * @param value the new bootom margin
	 */
	public void setBottomMargin(int value) {
		fBottomMargin = value;
	}

	/**
	 * Sets the distance between activations.
	 * 
	 * @param value the new distance
	 */
	public void setActDist(int value) {
		fActManDist = value;
	}

	/**
	 * Sets the font used for the sequence diagram.
	 * 
	 * @param value name of the Font
	 */
	public void setFont(String value) {
		fFont = new Font(value, Font.PLAIN, fFontSize);
	}

	/**
	 * Sets the size of the font used for the sequence diagram.
	 * 
	 * @param value the size for the font
	 */
	public void setFontSize(int value) {
		fFontSize = value;
		fFont = fFont.deriveFont(fFont.getStyle(), value);
	}

	/**
	 * Sets the value of the fLikeLongMess-Variable.
	 * 
	 * @param value true, if the distance between two lifelines depends on the
	 *            the longest message; false otherwise
	 */
	public void llLikeLongMess(boolean value) {
		fLikeLongMess = value;
	}

	/**
	 * Sets the distance between activations manual given by a user..
	 * 
	 * @param value manual given distance
	 */
	public void setAct_manDist(int value) {
		fActManDist = value;
	}

	/**
	 * Sets the value of the fIndividualLl-Variable
	 * 
	 * @param value true, if each lifeline should have an individual distance to
	 *            his antecessor, false otherwise
	 */
	public void setIndividualLl(boolean value) {
		fIndividualLl = value;
	}

	// get-Methods
	/**
	 * Indicates whether or not to show Lifeline-States, is used by drawing.
	 * 
	 * @return true if lifeline states should be showed; false otherwise.
	 */
	public boolean isStatesShown() {
		return showStates;
	}

	/**
	 * Indicates whether or not antialiasing is used by drawing.
	 * 
	 * @return true if antialiasing is used by drawing; false otherwise.
	 */
	public boolean getAntiAliasing() {
		return fDoAntiAliasing;
	}

	/**
	 * Indicates whether or not all values is shown in the diagram.
	 * 
	 * @return true if all values is shown in the diagram; false otherwise.
	 */
	public boolean showValues() {
		return fShowValues;
	}

	/**
	 * Indicates whether or not steps not related to should be omitted by
	 * drawing.
	 * 
	 * @return true if steps not related to should be omitted by drawing; false
	 *         otherwise.
	 */
	public boolean compactDisplay() {
		return fCompactDisplay;
	}

	/**
	 * Indicates whether or not create commands should be shown in the diagram.
	 * 
	 * @return true if create should be shown; false otherwise.
	 */
	public boolean showCreate() {
		return fShowCreate;
	}

	/**
	 * Indicates whether or not set commands should be shown in the diagram.
	 * 
	 * @return true if set should be shown; false otherwise.
	 */
	public boolean showSet() {
		return fShowSet;
	}

	/**
	 * Indicates whether or not destroy commands should be shown in the diagram.
	 * 
	 * @return true if destroy should be shown; false otherwise.
	 */
	public boolean showDestroy() {
		return fShowDestroy;
	}

	/**
	 * Indicates whether or not insert commands should be shown in the diagram.
	 * 
	 * @return true if insert should be shown; false otherwise.
	 */
	public boolean showInsert() {
		return fShowInsert;
	}

	/**
	 * Indicates whether or not delete commands should be shown in the diagram.
	 * 
	 * @return true if delete should be shown; false otherwise.
	 */
	public boolean showDelete() {
		return fShowDelete;
	}

	/**
	 * Returns the activation step, that is the distance between activations.
	 * 
	 * @return the activation step
	 */
	public int actStep() {
		return fYStep;
	}

	/**
	 * Returns the extended distance between two activations
	 * if states should be shown.
	 * 
	 * @return the activation step
	 */
	public int getStateNodeExtension() {
		if (fActManDist == -1) {
			if (isStateNodeManualSize) {
				return stateNodeHeight + fYStep;
			} else {
				return sequenceDiagram.getFontMetrics(fFont).getHeight() + STATE_NODE_VERTICAL_MARGIN + fYStep;
			}
		} else {
			if (isStateNodeManualSize) {
				return stateNodeHeight + fActManDist;
			} else {
				return sequenceDiagram.getFontMetrics(fFont).getHeight() + STATE_NODE_VERTICAL_MARGIN + fActManDist;
			}
		}
	}
	
	/**
	 * Returns the height of state node on sequence diagrams
	 * 
	 * @return height of state node
	 */
	public int getNodeHeight() {
		if (isStateNodeManualSize) {
			return stateNodeHeight;
		} else {
			return sequenceDiagram.getFontMetrics(fFont).getHeight() + STATE_NODE_VERTICAL_MARGIN;
		}
	}

	/**
	 * Returns the y-value on which the lifelines start.
	 * 
	 * @return the start value of lifelines
	 */
	public int yStart() {
		return fYStart;
	}

	/**
	 * Returns the y-value on which the scrolling-zigzag begins.
	 * 
	 * @return the y-value on which the scrolling-zigzag begins
	 */
	public int yScroll() {
		return fYScroll;
	}

	/**
	 * Returns the lifeline step, that is the distance between lifelines.
	 * 
	 * @return the lifeline step
	 */
	public int llStep() {
		return fXStep;
	}

	/**
	 * Returns the maximum activation message
	 * 
	 * @return the new maximum activation message
	 */
	public int maxActMess() {
		return fMaxActMess;
	}

	/**
	 * Returns the frame offset.
	 * 
	 * @return the frame offset
	 */
	public int frOffset() {
		return fFrameOffset;
	}

	/**
	 * Returns the frame width.
	 * 
	 * @return the frame width
	 */
	public int frWidth() {
		return fFrameWidth;
	}

	/**
	 * Returns the name of the used font.
	 * 
	 * @return the name of the font
	 */
	public String getFontName() {
		return fFont.getFontName();
	}

	/**
	 * Returns the size of the font used in the diagram
	 * 
	 * @return the font size
	 */
	public int getFontSize() {
		return fFontSize;
	}

	/**
	 * Returns the left margin in the diagram.
	 * 
	 * @return the left margin
	 */
	public int getLeftMargin() {
		return fLeftMargin;
	}

	/**
	 * Returns the right margin in the diagram.
	 * 
	 * @return the right margin
	 */
	public int getRightMargin() {
		return fRightMargin;
	}

	/**
	 * Returns the top margin in the diagram.
	 * 
	 * @return the top margin
	 */
	public int getTopMargin() {
		return fTopMargin;
	}

	/**
	 * Returns the bottom margin in the diagram.
	 * 
	 * @return the bottom margin
	 */
	public int getBottomMargin() {
		return fBottomMargin;
	}

	/**
	 * Returns the by the user manual given distance between activations
	 * 
	 * @return the manual given distance between activations
	 */
	public int getActManDist() {
		return fActManDist;
	}

	/**
	 * Indicates whether or not each lifeline should have an individual distance
	 * to his antecessor.
	 * 
	 * @return true the distances should be individual; false otherwise.
	 */
	public boolean individualLl() {
		return fIndividualLl;

	}

	/**
	 * Indicates whether or not the distance between two lifelines depends on
	 * the maximum message length
	 * 
	 * @return true the distances should be individual; false otherwise.
	 */
	public boolean llLikeLongMess() {
		return fLikeLongMess;

	}

	/**
	 * Returns the used font.
	 * 
	 * @return the font used in the diagram
	 */
	public Font getFont() {
		return fFont;
	}

}
