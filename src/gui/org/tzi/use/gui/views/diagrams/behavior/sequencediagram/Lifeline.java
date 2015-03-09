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
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.statemachine.StateMachineDiagramOptions;
import org.tzi.use.gui.views.diagrams.statemachine.StateNode;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MState;

/**
 * Represents a lifeline of the sequence diagram. It's abstract, because there
 * are two kind of lifelines which must be differentiated: one for objects and
 * one for associations/links.
 * 
 * @author Marc Richters, Antje Werner
 */
public abstract class Lifeline implements Selectable {
	/**
	 * Indicates if this lifeline has state machine
	 */
	boolean hasStatesMachine = false;
	
	/**
	 * Indicates if this lifeline's states should be shown.
	 * Individual for each lifeline, differ from {@code showStates}
	 * in {@link SDProperties}
	 */
	private boolean isShowStates;
	
	/**
	 * All of state machines of object obtain on this lifeline
	 */
	List<MProtocolStateMachine> sortedPSMs;

	/**
	 * The initialization state 
	 */
	MState initState;

	/**
	 * All of state nodes which should be drawn on this lifeline
	 */
	List<StateNode> stateNodesList;
	
	/**
	 * Name of current state
	 */
	String currentStateName;

	/**
     * Indicates if the node is selected.
     */
    private boolean fIsSelected = false;

	/**
	 * The column of the lifeline.
	 */
	int columnNumber;

	/**
	 * The x-value on which the lifeline is positioned in the diagram.
	 */
	int xPos;
	
	int yEndPos;

	/**
	 * @return the yEndPos
	 */
	public int getEndPosY() {
		return yEndPos;
	}

	/**
	 * @param yEndPos the yEndPos to set
	 */
	public void setEndPosY(int yEndPos) {
		this.yEndPos = yEndPos;
	}

	/**
	 * The length of the maximum message sent from a lifeline which is
	 * positioned on the left size of this.
	 */
	int maxOfMessLength;

	/**
	 * The list of all activations sending a message to this lifeline.
	 */
	List<Activation> activationsList;

	/**
	 * The current activation nesting on this lifeline.
	 */
	int activationNesting;

	/**
	 * The object box of the lifeline.
	 */
	ObjectBox objectBox;

	/**
	 * The list of all frames on this lifeline.
	 */
	List<Frame> fFrames;

	/**
	 * The antecessor lifeline.
	 */
	Lifeline antecessor;

	/**
	 * The successor lifeline.
	 */
	private Lifeline successor;

	/**
	 * Indicates if this lifeline should be drawn in the diagram.
	 */
	boolean fDraw;

	/**
	 * Indicates if this lifeline has been choosed to be hidden.
	 */
	boolean isHidden;

	/**
	 * Describes if the association has been deleted. A Link between two Objects
	 * can be deleted and later inserted again.
	 */
	protected boolean fIsDeleted;

	/**
	 * Indicates whether or not the association of this lifeline has been
	 * deleted.
	 * 
	 * @return true if delete should be shown; false otherwise.
	 */
	public boolean isDeleted() {
		return fIsDeleted;
	}

	/**
	 * Sets the value of the fDeleted-Variable
	 * 
	 * @param deleted true, if tha association has been deleted; false otherwise
	 */
	public void setDeleted(boolean deleted) {
		fIsDeleted = deleted;
	}

	/**
	 * Contains several properties for this SequenceDiagram
	 */
	protected SDProperties fProperties;

	private OBProperties fObProperties;

	public SDProperties getSequenceDiagramProperties() {
		return fProperties;
	}

	public OBProperties getObjectBoxProperties() {
		return fObProperties;
	}

	protected SequenceDiagram sequenceDiagram;

	public SequenceDiagram getSequenceDiagram() {
		return sequenceDiagram;
	}

	public Lifeline(SequenceDiagram sequenceDiagram) {
		this.sequenceDiagram = sequenceDiagram;
		this.fProperties = sequenceDiagram.getProperties();
		this.fObProperties = sequenceDiagram.getObProperties();
		this.fIsDeleted = false;
		isShowStates = true; // by default all states of this lifeline should be shown
	}

	/**
	 * Marks the lifeline's states to be showed in the sequence diagram.
	 * 
	 * @param hidden true if the lifeline's states should be showed; false
	 *            otherwise
	 */
	void setShowStates(boolean showStates) {
		isShowStates = showStates;
	}

	/**
	 * Indicates if this lifeline's states should be showed.
	 * 
	 * @return True is the lifeline's states has marked to be showed; false
	 *         otherwise
	 */
	boolean isShowStates() {
		return isShowStates;
	}
	
	/**
	 * Check if this the object obtain to this lifeline has a state machine
	 * 
	 * @return the hasStatesMachine
	 */
	boolean hasStatesMachine() {
		return hasStatesMachine;
	}

	/**
	 * Draw all of appeared states of this lifeline 
	 * @param g
	 */
	protected void drawStateNodes(Graphics2D g) {
		for (int i = 0; i < stateNodesList.size(); i++) {
			StateNode sn = stateNodesList.get(i);
			sn.setX(objectBox.xPosOfBoxCenter - sn.getWidth() / 2);
			sn.draw(g);
		}
	}

	/**
	 * Returns the x-value of the lifeline.
	 * 
	 * @return the x-value of the lifeline
	 */
	int xValue() {
		return xPos;
	}

	/**
	 * Resets, if necessary, the x-Value, the Antecessor, the Successor of the
	 * lifeline and the x-Value of the object box, if the x-Value of the
	 * lifeline has been changed (for example the lifeline has been moved).
	 * 
	 * @param x the new x-value
	 */
	void setNewValues(int x) {
		// lifeline has been moved in right direction
		if (x > xPos) {
			// set new x-Value for the corresponding object box
			objectBox.setX(x);
			// set new x-values for the lifeline
			xPos = x;

			/* calculate the new successor */
			// if old successor is now on the left side of this lifeline
			if (successor != null && successor.xValue() < x) {
				// swap columns
				int saveColumn = successor.column();
				successor.setColumn(columnNumber);
				columnNumber = saveColumn;

				// update the successors and antecessors
				Lifeline saveSuccessor = successor.getSuccessor();
				if (successor.getSuccessor() != null)
					successor.getSuccessor().setAntecessor(this);
				if (antecessor != null)
					antecessor.setSuccessor(successor);
				successor.setAntecessor(antecessor);
				successor.setSuccessor(this);
				setAntecessor(successor);
				setSuccessor(saveSuccessor);
			}
			// lifeline has been moved in left direction
		} else if (x < xPos) {
			// set new x-Value for the corresponding object box
			objectBox.setX(x);
			// set new x-values for the lifeline
			xPos = x;
			// if there was no antecessor
			if (antecessor == null) {
				if (x - objectBox.getWidth() / 2 < fProperties.getLeftMargin() + 10 + fProperties.frWidth() / 2) {
					xPos = fProperties.getLeftMargin() + 10 + fProperties.frWidth() / 2 + objectBox.getWidth() / 2 + 1;
					objectBox.setX(xPos);
				}
				// otherwise if old antecessor is now on the right side of
				// this lifeline
			} else if (antecessor.xValue() > x) {
				// swap columns
				int saveColumn = antecessor.column();
				antecessor.setColumn(columnNumber);
				columnNumber = saveColumn;
				// update the successors and antecessors
				Lifeline saveAntecessor = antecessor.getAntecessor();
				if (antecessor.getAntecessor() != null)
					antecessor.getAntecessor().setSuccessor(this);
				if (successor != null)
					successor.setAntecessor(antecessor);
				antecessor.setAntecessor(this);
				antecessor.setSuccessor(successor);
				setSuccessor(antecessor);
				setAntecessor(saveAntecessor);
			}
		}

	}

	/**
	 * Returns the object box.
	 * 
	 * @return the object box of the lifeline
	 */
	ObjectBox getObjectBox() {
		return objectBox;
	}

	/**
	 * Return the antecessor lifeline.
	 * 
	 * @return the antecessor lifeline
	 */
	Lifeline getAntecessor() {
		return antecessor;
	}

	/**
	 * Returns the successor lifeline.
	 * 
	 * @return the successor lifeline
	 */
	Lifeline getSuccessor() {
		return successor;
	}

	/**
	 * Returns the maximum message length sent from a lifline which ist
	 * positioned on the left size of this.
	 * 
	 * @return the maximum message length
	 */
	private int getMaxMessLength() {
		return maxOfMessLength;
	}

	/**
	 * Returns the column of the lifeline which depends on the visibility of the
	 * antecessor lifelines.
	 * 
	 * @return the column
	 */
	int column() {
		int column = columnNumber;
		// regard all antecessor lifelines
		Lifeline ll = antecessor;
		while (ll != null) {
			// if one antecessor is not drawn in the diagram
			if (!ll.isDraw() || ll.isHidden()) {
				// decrease the column of this lifeline
				column--;
			}
			ll = ll.getAntecessor();
		}
		// returns the column on which this lifeline is drawn
		return column;
	}

	/**
	 * Sets the nesting of the lifeline.
	 * 
	 * @param n the new nesting
	 */
	void setNesting(int n) {
		activationNesting = n;
	}

	/**
	 * Sets the maximum message length.
	 * 
	 * @param value the maximum message length
	 */
	private void setMaxMessLength(int value) {
		maxOfMessLength = value;
	}

	/**
	 * Sets the successor lifeline.
	 * 
	 * @param successor the new successor lifeline
	 */
	void setSuccessor(Lifeline successor) {
		this.successor = successor;
	}

	/**
	 * Sets the antecessor lifeline.
	 * 
	 * @param antecessor the new antecessor lifeline
	 */
	private void setAntecessor(Lifeline antecessor) {
		this.antecessor = antecessor;
	}

	/**
	 * Sets the column of the lifeline.
	 * 
	 * @param column the new column
	 */
	private void setColumn(int column) {
		columnNumber = column;
	}

	/**
	 * Adds an activation to the fActivations list and increases the nesting of
	 * the Activation and this lifeline.
	 * 
	 * @param a the activation to add
	 */
	synchronized void enterActivation(Activation a) {
		a.setNesting(++activationNesting);
		activationsList.add(a);
	}

	/**
	 * Adds an activation to the fActivations list and sets the nesting of the
	 * Activation. In contrast to enterActivation(Activation) the nesting not
	 * increases.
	 * 
	 * @param a the activation to add
	 */
	synchronized void addActivation(Activation a) {
		a.setNesting(activationNesting);
		activationsList.add(a);
	}

	/**
	 * Decreases the activation nesting, when the last Activation has been
	 * executes.
	 * 
	 */
	void exitActivation() {
		activationNesting--;
	}

	/**
	 * Marks the lifeline to draw in the sequence diagram.
	 * 
	 * @param draw true if the lifeline should be drawn; false otherwise
	 */
	void setDraw(boolean draw) {
		fDraw = draw;
	}

	/**
	 * Indicates if this lifeline should be drawn.
	 * 
	 * @return True is the lifeline has marked to draw; else otherwise
	 */
	boolean isDraw() {
		return fDraw;
	}

	/**
	 * Marks the lifeline to be hidden in the sequence diagram.
	 * 
	 * @param hidden true if the lifeline should be hidden; false otherwise
	 */
	void setHidden(boolean hidden) {
		isHidden = hidden;
	}

	/**
	 * Indicates if this lifeline should be hidden.
	 * 
	 * @return True is the lifeline has marked to be hidden; false otherwise
	 */
	boolean isHidden() {
		return isHidden;
	}

	/**
	 * Draws the lifeline as a dashed line.
	 * 
	 * @param x the x-value of the line
	 * @param y1 the start-y-value of the line
	 * @param y2 the end-y-value of the line
	 * @param g the graphics on which the dashed line should be drawn
	 */
	void drawDashedLine(int x, int y1, int y2, Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		g.setStroke(fProperties.getDASHEDSTROKE());
		g.drawLine(x, y1, x, y2);
		g.setStroke(oldStroke);
	}

	void drawSelectedBound(Graphics2D g, int yStart, int yEnd) {
		g.fillRect(xPos - objectBox.getWidth() / 2 - 4, yStart - 4, 4, 4);
		g.fillRect(xPos + (objectBox.getWidth() + 1) / 2, yStart - 4, 4, 4);
		g.fillRect(xPos - objectBox.getWidth() / 2 - 4, yEnd, 4, 4);
		g.fillRect(xPos + (objectBox.getWidth() + 1) / 2, yEnd, 4, 4);
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(0.3f));
		g.drawLine(xPos - objectBox.getWidth() / 2 - 2, yStart - 2, xPos + (objectBox.getWidth() + 1) / 2 + 2, yStart - 2);
		g.drawLine(xPos - objectBox.getWidth() / 2 - 2, yStart - 2, xPos - objectBox.getWidth() / 2 - 2, yEnd + 2);
		g.drawLine(xPos + (objectBox.getWidth() + 1) / 2 + 2, yStart - 2, xPos + (objectBox.getWidth() + 1) / 2 + 2, yEnd + 2);
		g.drawLine(xPos - objectBox.getWidth() / 2 - 2, yEnd + 2, xPos + (objectBox.getWidth() + 1) / 2 + 2, yEnd + 2);
		g.setStroke(oldStroke);
	}

	/**
	 * @param g
	 */
	protected void drawZigZag(Graphics2D g) {
		// draw the zigzag pattern depending on counter
		int boxPos = objectBox.getYPosOfBoxStart() + objectBox.getHeight();
		if (sequenceDiagram.getScrollCounter() > 0 && boxPos < ((int) sequenceDiagram.getView().getY() + fProperties.yScroll())) {
			g.drawLine(xPos + 2, (int) sequenceDiagram.getView().getY() + fProperties.yScroll(), xPos - 2,
					(int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 4);
		}
		if (sequenceDiagram.getScrollCounter() > 1 && boxPos < ((int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 4)) {// scrolled
			g.drawLine(xPos - 2, (int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 4, xPos + 2, (int) sequenceDiagram.getView().getY()
					+ fProperties.yScroll() + 8);
		}
		if (sequenceDiagram.getScrollCounter() > 2 && boxPos < ((int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 8)) {
			g.drawLine(xPos + 2, (int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 8, xPos - 2, (int) sequenceDiagram.getView().getY()
					+ fProperties.yScroll() + 12);
		}
		if (sequenceDiagram.getScrollCounter() > 3 && boxPos < ((int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 12)) {
			g.drawLine(xPos - 2, (int) sequenceDiagram.getView().getY() + fProperties.yScroll() + 12, xPos + 2, (int) sequenceDiagram.getView().getY()
					+ fProperties.yScroll() + 16);
		}
	}

	void setupState(StateNode sn, int yPos, Font font) {
		sn.setY(yPos);
		sn.setRequiredWidth("STATENODE", 0);
		sn.setRequiredHeight("STATENODE", 0);
		if (fProperties.isStateNodeManualSize()) {
			sn.setExactWidth(fProperties.getStateNodeWidth());
			sn.setExactHeight(fProperties.getStateNodeHeight());
		} else {
			String nodeName = sn.name();
			FontMetrics fm = getSequenceDiagram().getFontMetrics(font);
			int nodeNameWidth = fm.stringWidth(nodeName) + SDProperties.STATE_NODE_HORIZON_MARGIN;
			sn.setExactWidth(nodeNameWidth);
			sn.setExactHeight(fm.getHeight() + SDProperties.STATE_NODE_VERTICAL_MARGIN);
		}
		sn.setShowInvariant(false);
		sn.setShowSuppressedInvText(false);
		StateMachineDiagramOptions smdo = new StateMachineDiagramOptions();
		sn.setBackColor(smdo.getNODE_COLOR());
		sn.setBackColorSelected(smdo.getNODE_SELECTED_COLOR());
		sn.setTextColor(smdo.getNODE_LABEL_COLOR());
		sn.setFrameColor(smdo.getNODE_FRAME_COLOR());
		sn.setFont(font);
		sn.setInvFont(font);
	}

	/**
	 * @param g
	 */
	protected void drawFrames(Graphics2D g) {
		for (int i = 0; i < fFrames.size(); i++) {
			Frame frame = fFrames.get(i);
			frame.drawFrame(g);
		}
	}

	/**
	 * @param g
	 * @param y
	 */
	protected void drawDestroySaltire(Graphics2D g, int y) {
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(2.0f));
		g.drawLine(xPos - 10, y - 10, xPos + 10, y + 10);
		g.drawLine(xPos + 10, y - 10, xPos - 10, y + 10);
		g.setStroke(oldStroke);
	}

	/**
	 * Searches the longest Activation-Message for this Lifeline.
	 * 
	 * @param fm the FontMetrics object of the diagram
	 */
	private synchronized int getLongestMessage(FontMetrics fm) {
		int returnValue = 0;
		// regard each activation of this lifeline
		for (int i = 0; i < activationsList.size(); i++) {
			Activation a = activationsList.get(i);
			// length of the longest message of the regarded activation
			int messLength = a.getMessLength();
			// if bigger than fMaxMessLength -> new fMaxMessLength
			if (messLength > 0 && messLength > maxOfMessLength)
				maxOfMessLength = messLength;
			// if messLength < 0 and longest message of successor is smaller
			// then -messLength
			// -> set fMaxMessLangth of successor
			else if (successor != null && messLength < 0 && -messLength > successor.getMaxMessLength())
				successor.setMaxMessLength(-messLength);
			// if negative messLength is smaller than returnValue
			// -> set returnValue = messLength
			if (messLength < 0 && returnValue > messLength)
				returnValue = messLength;
		}
		return returnValue;
	}

	/**
	 * Calculates the difference between this and another lifeline.
	 * 
	 * @param src the lifeline to which the distance should be calculated
	 * @return the difference between the column of the two lifelines
	 */
	public int calculateDistance(Lifeline src) {
		// src is actors lifeline -> return column of this lifeline
		if (src == null)
			return column();
		// src is this lifeline -> return 0
		if (src == this)
			return 0;
		// return differende between column of src and this
		return (column() - src.column());
	}

	/**
	 * Calculates the x position of this lifeline, subject to the values in the
	 * fProperties-Object of the SequenceDiagram-Object.
	 * 
	 * @param fm the FontMetrics object of the diagram
	 */
	void calculatePosition(FontMetrics fm) {
		// column of this lifeline in the diagram
		int column = column();
		// antecessor of this lifeline -> must not be drawn in the diagram
		Lifeline ll = antecessor;
		// calculate drawn antecessor lifeline
		while (ll != null && (!ll.isDraw() || ll.isHidden())) {
			ll = ll.getAntecessor();
		}
		// calculate longest message for this lifeline
		int returnValue = getLongestMessage(fm);
		// beginning of the diagram
		int xValue = fProperties.getLeftMargin() + fProperties.frWidth();
		// if there is no lifeline chosen by the user
		if (sequenceDiagram.getChoosedLinelines().isEmpty()) {
			// calculate xValue and in case of fDraw=true fXValue
			// subject to the user settings for the distance
			// of two lifelines
			if (!fProperties.llLikeLongMess()) {
				if (!fProperties.individualLl()) {
					xValue = fProperties.getLeftMargin() + fProperties.frWidth() + column * fProperties.llStep();
				} else {
					xValue = xPos;
				}
				if (fDraw) {
					xPos = xValue;
				}
			} else {
				if (fProperties.individualLl()) {
					if (ll != null) {
						xValue = ll.xValue() + maxOfMessLength;
					} else {
						xValue += maxOfMessLength;
						if (maxOfMessLength == 0) {
							xValue += fProperties.frWidth() / 2 + objectBox.getWidth() / 2;
						}
					}
				} else {
					xValue += fProperties.maxActMess() * column;
				}
				if (fDraw) {
					xPos = xValue;
				}
				if (fProperties.individualLl() && ll != null && (ll.xValue() + ll.getObjectBox().getWidth() / 2 > xValue - objectBox.getWidth() / 2 - 5)) {
					xPos = ll.xValue() + ll.getObjectBox().getWidth() / 2 + objectBox.getWidth() / 2 + 5;
				}
			}
		}
		// set x-value of the object box
		objectBox.setX(xPos);
		// calculate start- and end-x-value of the object box
		objectBox.calculateStart(fm);
		objectBox.calculateEnd(fm);

		// calculate last x-value of this lifeline
		int lastX = xPos + objectBox.getWidth() / 2;
		if (returnValue < 0 && xPos + objectBox.getWidth() / 2 - returnValue > sequenceDiagram.getLastXValue())
			lastX = xPos + objectBox.getWidth() / 2 - returnValue;
		if (sequenceDiagram.getLastXValue() < lastX && fDraw) {
			sequenceDiagram.setLastXValue(lastX);
		}

		// calculate width of object box
		int labelWidth = fm.stringWidth(objectBox.fName);
		// if there was no wider box so far -> set maximum width in
		// fObProperties
		if (fObProperties.maxWidth(labelWidth))
			fObProperties.setMaxWidth(labelWidth);
		// calculate height of object box
		int labelHeight = fProperties.getFont().getSize();
		// if there was no higher box so far -> set maximum height in
		// fObProperties
		if (fObProperties.maxHeight(labelHeight))
			fObProperties.setMaxHeight(labelHeight);

	}

	/**
	 * Abstract method for restoring some values.
	 * 
	 */
	public abstract void restoreValues();

	/**
	 * Draws the lifeline in the sequence diagram. Draws the object box, all
	 * activations from the fActivations list, the dashed line and the frames.
	 * 
	 * @param g the graphics on which the lifeline should be drawn
	 * @param counter the level of scrolling which was calculated in the
	 *            drawView-Method of the SequenceDiagram class.
	 */
	protected void draw(Graphics2D g) {
		FontMetrics fm = sequenceDiagram.getFontMetrics(fProperties.getFont());
		g.setColor(Color.black);
		
		if (hasStatesMachine) {
			currentStateName = initState.name();
		}
		
		// if only the visible view should be drawn
		if (sequenceDiagram.isOnlyView()) {
			// set drawing area on the counts of the visible view
			g.clipRect((int) sequenceDiagram.getView().getX(), (int) sequenceDiagram.getView().getY(), (int) sequenceDiagram.getView().getWidth(),
					(int) sequenceDiagram.getView().getHeight());
		}

		drawLifeline(g, fm);

		// reset drawing area to the whole visible view
		if (sequenceDiagram.isOnlyView()) {
			g.setClip((int) sequenceDiagram.getView().getX(), (int) sequenceDiagram.getView().getY(), (int) sequenceDiagram.getView().getWidth(),
					(int) sequenceDiagram.getView().getHeight());
		}
	}
	
	@Override
	public void setSelected(boolean on) {
		fIsSelected = on;
	}

	@Override
	public boolean isSelected() {
		return fIsSelected;
	}

	@Override
	public void setResizeAllowed(boolean allowed) {
		
	}

	@Override
	public boolean getResizeAllowed() {
		return false;
	}

	/**
	 * @param g
	 * @param fm
	 */
	protected abstract void drawLifeline(Graphics2D g, FontMetrics fm);

	/**
	 * Represents an object box of a lifeline.
	 * 
	 * @author Antje Werner
	 */
	class ObjectBox {
		/**
		 * Padding between object box's label and underline
		 */
		private static final int LABEL_UNDERLINE_PADDING = 2;

		/**
		 * Margin from label to the top line of object box
		 */
		private static final int TOP_MARGIN = 2;

		/**
		 * Sum of left margin and right margin from label
		 */
		private static final int LEFT_RIGHT_MARGIN = 10;

		/**
		 * Sum of top margin and bottom margin from label
		 */
		private static final int TOP_BOTTOM_MARGIN = 8;

		/**
		 * The x-value of the corresponding lifeline -> the center of the object
		 * box.
		 */
		private int xPosOfBoxCenter;

		/**
		 * The x value where the object box starts.
		 */
		private int xPosOfBoxStart;

		/**
		 * The x-value where the objct box ends.
		 */
		private int xPosOfBoxEnd;

		/**
		 * The y-value where the object box starts.
		 */
		private int yPosOfBoxStart;

		/**
		 * The name of the object which belongs to the object box.
		 */
		private String fName;

		/**
		 * Constructs a new object box.
		 * 
		 * @param xValue the center x-value of the object box
		 * @param yValue the y-value on which the object box starts
		 * @param lifeline the corresponding lifeline
		 * @param name the name of the corresponding object
		 */
		ObjectBox(int xValue, int yValue, String name) {
			xPosOfBoxCenter = xValue;
			yPosOfBoxStart = yValue;
			xPosOfBoxStart = 0;
			xPosOfBoxEnd = 0;
			fName = name;
		}

		/**
		 * Returns the start x-value of the box.
		 * 
		 * @return the start x-value
		 */
		int getStart() {
			return xPosOfBoxStart;
		}

		/**
		 * Returns the end x-value of the box.
		 * 
		 * @return the end x-value
		 */
		int getEnd() {
			return xPosOfBoxEnd;
		}

		/**
		 * Returns the start y-value of the box.
		 * 
		 * @return the start y-value
		 */
		int getYPosOfBoxStart() {
			return yPosOfBoxStart;
		}

		/**
		 * Calculates the height of the box for the given font.
		 * 
		 */
		int getHeight() {
			// manuel height chosen by the user
			if (fObProperties.manHeight()) {
				return fObProperties.getHeight();
				// same height for all boxes
			}

			// Font used for the diagram
			Font font = fProperties.getFont();
			FontMetrics fm = getSequenceDiagram().getFontMetrics(fProperties.getFont());

			String[] labelSpliter = fName.split(":");
			String objName = labelSpliter[0];

			if (!fObProperties.isLineBreakLabel() || objName.equals("")) {
				if (fObProperties.sameHeight()) {
					// calculate height of the box label
					int labelHeight = font.getSize();
					// if labelheight is bigger than the maximum height ind
					// fObProperties
					// -> labelheight is new maximum height
					if (fObProperties.maxHeight(labelHeight))
						fObProperties.setMaxHeight(labelHeight);
					// otherwise height of this box is the maximum height from
					// fObProperties
					else
						labelHeight = fObProperties.getMaxHeight();
					// return the calculated height + 8 as distance between
					// label
					// and border
					return (labelHeight + 8);
					// individual heights
				} else {
					// see above
					int labelHeight = font.getSize();
					if (fObProperties.maxHeight(labelHeight))
						fObProperties.setMaxHeight(labelHeight);
					return (labelHeight + 8);
				}
			} else {
				if (fObProperties.sameHeight()) {
					// calculate height of the box label
					int labelHeight = font.getSize() * 2 + fm.getLeading();
					// if labelheight is bigger than the maximum height ind
					// fObProperties
					// -> labelheight is new maximum height
					if (fObProperties.maxHeight(labelHeight))
						fObProperties.setMaxHeight(labelHeight);
					// otherwise height of this box is the maximum height from
					// fObProperties
					else
						labelHeight = fObProperties.getMaxHeight();
					// return the calculated height + 8 as distance between
					// label
					// and border
					return (labelHeight + TOP_BOTTOM_MARGIN);
					// individual heights
				} else {
					// see above
					int labelHeight = font.getSize() * 2 + fm.getLeading();
					if (fObProperties.maxHeight(labelHeight))
						fObProperties.setMaxHeight(labelHeight);
					return (labelHeight + TOP_BOTTOM_MARGIN);
				}
			}
		}

		/**
		 * Calculates the width of the box for the given FontMetrics.
		 * 
		 */

		int getWidth() {
			// see getHeight
			if (fObProperties.manWidth()) {
				return fObProperties.getWidth();
			}

			// the FontMetrics used for the diagram
			FontMetrics fm = getSequenceDiagram().getFontMetrics(fProperties.getFont());

			if (!fObProperties.isLineBreakLabel()) {
				if (fObProperties.sameWidth()) {
					int labelWidth = fm.stringWidth(fName);
					if (fObProperties.maxWidth(labelWidth))
						fObProperties.setMaxWidth(labelWidth);
					else
						labelWidth = fObProperties.getMaxWidth();
					return (labelWidth + LEFT_RIGHT_MARGIN);
				} else {
					int labelWidth = fm.stringWidth(fName);
					if (fObProperties.maxWidth(labelWidth))
						fObProperties.setMaxWidth(labelWidth);
					return (labelWidth + LEFT_RIGHT_MARGIN);
				}
			} else {
				String[] labelSpliter = fName.split(":");
				String objName = labelSpliter[0];
				String clsName = labelSpliter[1];
				String longerName = objName.length() < clsName.length() ? clsName : objName;

				if (fObProperties.sameWidth()) {
					int labelWidth = fm.stringWidth(longerName);
					if (fObProperties.maxWidth(labelWidth))
						fObProperties.setMaxWidth(labelWidth);
					else
						labelWidth = fObProperties.getMaxWidth();
					return (labelWidth + LEFT_RIGHT_MARGIN);
				} else {
					int labelWidth = fm.stringWidth(longerName);
					if (fObProperties.maxWidth(labelWidth))
						fObProperties.setMaxWidth(labelWidth);
					return (labelWidth + LEFT_RIGHT_MARGIN);
				}
			}

		}

		/**
		 * Sets the center x-value of the box.
		 * 
		 * @param x the new center x-value
		 */
		void setX(int x) {
			xPosOfBoxCenter = x;
		}

		/**
		 * Sets the start y-value of the box.
		 * 
		 * @param y the new start y-value
		 */
		void setY(int y) {
			yPosOfBoxStart = y;
		}

		/**
		 * Caculates and sets the start x-value of the object box in dependency
		 * on the FontMetrics of the diagram.
		 * 
		 * @param fm the FontMetrics of the sequence diagram
		 */
		void calculateStart(FontMetrics fm) {
			xPosOfBoxStart = xPosOfBoxCenter - getWidth() / 2;
		}

		/**
		 * Caculates and sets the end x-value of the object box in dependency on
		 * the FontMetrics of the diagram.
		 * 
		 * @param fm the FontMetrics of the sequence diagram
		 */
		void calculateEnd(FontMetrics fm) {
			xPosOfBoxEnd = xPosOfBoxCenter + getWidth() / 2;
		}

		/**
		 * Draws the box in the diagram.
		 * 
		 * @param graphic the graphic where the object box should be drawn in.
		 * @param fm the FontMetrics of the sequence diagram
		 * @param y the y-Value where the box begins.
		 */
		void drawBox(Graphics2D graphic, FontMetrics fm, int y, boolean background) {
			// calculate width of the object box
			int boxWidth = getWidth();
			// calculate width of the box label
			int labelWidth = fm.stringWidth(fName);
			// calculate height of the box label
			int labelHeight = (fm.getFont()).getSize();
			// the y-value where the label should be drawn
			int yValue = y + labelHeight + TOP_MARGIN;
			// calculate height of box
			int boxHeight = getHeight();

			if (background) {
				graphic.setColor(Color.orange);
				graphic.fillRect(xPosOfBoxCenter - boxWidth / 2, y, boxWidth, boxHeight);
				graphic.setColor(Color.black);
			}

			if (!fObProperties.isLineBreakLabel()) {
				// draw label
				graphic.drawString(fName, xPosOfBoxCenter - labelWidth / 2, yValue);
				// draw underline
				graphic.drawLine(xPosOfBoxCenter - labelWidth / 2, yValue + LABEL_UNDERLINE_PADDING, xPosOfBoxCenter + labelWidth / 2, yValue
						+ LABEL_UNDERLINE_PADDING);
			} else {
				String[] labelSpliter = fName.split(":");
				String objName = labelSpliter[0];
				String clsName = labelSpliter[1];
				clsName = ":" + clsName;

				// draw object name
				graphic.drawString(objName, xPosOfBoxCenter - fm.stringWidth(objName) / 2, yValue);
				// draw underline
				graphic.drawLine(xPosOfBoxCenter - fm.stringWidth(objName) / 2, yValue + 2, xPosOfBoxCenter + fm.stringWidth(objName) / 2, yValue + 2);

				// draw class name
				graphic.drawString(clsName, xPosOfBoxCenter - fm.stringWidth(clsName) / 2, yValue + labelHeight + fm.getLeading() + 2);
				// draw underline
				graphic.drawLine(xPosOfBoxCenter - fm.stringWidth(clsName) / 2, yValue + labelHeight + fm.getLeading() + 4,
						xPosOfBoxCenter + fm.stringWidth(clsName) / 2, yValue + labelHeight + fm.getLeading() + 4);

			}
			// draw box
			graphic.drawRect(xPosOfBoxCenter - boxWidth / 2, y, boxWidth, boxHeight);
		}
	}

	// ************************************************************************************************************

	/**
	 * 
	 * Represent an activation frame, which is used to show the activity from a
	 * lifeline.
	 * 
	 * @author Mark Richters, Antje Werner
	 */

	class Frame {
		/**
		 * The x-value of the lifeline on which this frame is positioned
		 */
		private int fXValue;

		/**
		 * The y-value on which the frame begins.
		 */
		private int fYValue;

		/**
		 * The height of the frame.
		 */
		private int fHeight;

		/**
		 * Constructs a new Frame.
		 * 
		 * @param x the x-value of the frame
		 * @param y the y-value of the frame
		 * @param height the height of the frame
		 * @param nesting the nesting of this frame
		 */
		Frame(int x, int y, int height, int nesting) {
			fXValue = x;
			fYValue = y;
			fHeight = height;
			if (nesting > 0) {
				fXValue += (nesting - 1) * fProperties.frOffset();
			}
		}

		/**
		 * Draws the frame in the diagram.
		 * 
		 * @param graphic the Graphic on which is has to be drawn
		 */
		private void drawFrame(Graphics2D graphic) {
			// draw background of the frame
			graphic.setColor(Color.white);
			graphic.fillRect(fXValue - fProperties.frWidth() / 2, fYValue, fProperties.frWidth(), fHeight);
			// draw border of the frame
			graphic.setColor(Color.black);
			graphic.drawRect(fXValue - fProperties.frWidth() / 2, fYValue, fProperties.frWidth(), fHeight);
		}

	}

}
