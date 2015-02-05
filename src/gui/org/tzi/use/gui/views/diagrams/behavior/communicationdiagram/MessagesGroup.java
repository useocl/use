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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.behavior.DrawingUtil;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyInBetween;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;

/**
 * This class represents a group of messages, which belongs to a communication
 * edge.
 * 
 * @author Quang Dung Nguyen
 * 
 */
public final class MessagesGroup extends EdgeProperty {

	/**
	 * The overall vertical (top and bottom) margin between the text of the
	 * EdgeProperty and the surrounding rectangle.
	 */
	static final int MARGIN_VERTICAL = 6;
	/**
	 * The overall (left and right) horizontal margin between the text of the
	 * EdgeProperty and the surrounding rectangle.
	 */
	static final int MARGIN_HORIZONTAL = 6;

	/**
	 * The Length of arrow of communication messages
	 */
	static final int MESSAGE_ARROW_LENGTH = 10;

	/**
	 * The offset between arrow and messages label of communication messages
	 */
	static final int MESSAGE_ARROW_OFFSET = 2;

	/**
	 * Set the filter on or off
	 */
	private boolean isFiltered = false;

	/**
	 * Delete some Messages
	 */
	private boolean deleteSomeMessages = false;

	/**
	 * Contains all Messages should be shown
	 */

	private Set<MMessage> visibleMessages = null;

	/**
	 * The message which should be colored on navigation of communication
	 * messages
	 */
	private MMessage coloredMessage;

	/**
	 * Resets the message enumeration
	 */

	private boolean isEnumReseted = false;
	
	private Color activatedMessageColor = CommunicationDiagram.ACTIVATED_MESSAGE_COLOR;

	public MessagesGroup(DiagramOptions opt, CommunicationDiagramEdge edge) {
		super(edge.getId(), new PlaceableNode[] { edge.getSourceWayPoint(), edge.getTargetWayPoint() }, false, opt);
		fName = edge.getId();
		fOpt = opt;
		fEdge = edge;
		this.setStrategy(new StrategyInBetween(this, new PlaceableNode[] { edge.getSourceWayPoint(), edge.getTargetWayPoint() }, 0, -10));
	}

	@Override
	public String name() {
		return fName;
	}


	@Override
	public String getStoreType() {
		return "Communication Diagram Message";
	}

	@Override
	public String toString() {
		return "CommunicationDiagramMessage: " + name();
	}

	/**
	 * all visible messages of these Edge
	 * @return visible messages
	 */
	public Set<MMessage> getVisibleMessages(){
		if (visibleMessages!=null && isFiltered) {
			return visibleMessages;
		}else{
			return new HashSet<MMessage>(getEdgeMessages());
		}

	}

	public void resetEnum(boolean enable){
		isEnumReseted = enable;
	}

	public boolean getDeleteSomeMessages(){
		return deleteSomeMessages;
	}

	/**
	 * @return the activatedMessageColor
	 */
	public Color getActivatedMessageColor() {
		return activatedMessageColor;
	}

	/**
	 * @param activatedMessageColor the activatedMessageColor to set
	 */
	public void setActivatedMessageColor(Color activatedMessageColor) {
		this.activatedMessageColor = activatedMessageColor;
	}

	/**
	 * @return the coloredMessage
	 */
	public MMessage getColoredMessage() {
		return coloredMessage;
	}

	/**
	 * @param coloredMessage the coloredMessage to set
	 */
	public void setColoredMessage(MMessage coloredMessage) {
		this.coloredMessage = coloredMessage;
	}

	void removeColoredMessage() {
		this.coloredMessage = null;
	}

	@Override
	protected void onDraw(Graphics2D g) {
		if (!this.fEdge.isVisible())
			return;

		Graphics2D g2 = (Graphics2D) g.create();
		if (invalid) {
			calculateSize(g);
			invalid = false;
		}

		if (this.getColor() != null && !this.getColor().equals(Color.WHITE)) {
			Color old = g2.getColor();
			g2.setColor(Color.green);
			g2.fill(this.getBounds());
			g2.setColor(old);
		}

		g2.setFont(getFont(g2));

		if (isSelected()) {
			drawSelected(g2);
		}

		FontMetrics fm = g2.getFontMetrics();
		int lineHeight = fm.getAscent() + fm.getDescent();

		double xDrawPoint = getX() + MARGIN_HORIZONTAL / 2 + MESSAGE_ARROW_LENGTH;
		double yDrawPoint = getY() + fm.getAscent() + MARGIN_VERTICAL / 2;

		int xDrawPointInteger = (int) xDrawPoint;
		int yDrawPointInteger = (int) yDrawPoint;

		int yOfArrow = yDrawPointInteger - fm.getAscent() / 2;
		double angle = computeMessageArrowAngle(xDrawPoint, getY() + getHeight() / 2);

		Color oldColor = g.getColor();

		ArrayList<MMessage> messages = new ArrayList<>();

		messages = getEdgeMessages();

		for (int i = 0; i < messages.size(); i++) {
			MMessage mess = messages.get(i);
			if (mess.isFailedReturn()) {
				g2.setColor(Color.red);
			}
			if (mess.equals(coloredMessage)) {
				g2.setColor(activatedMessageColor);
			}
			if (mess.getDirection() == MMessage.RETURN) {
				DrawingUtil.drawReturnArrow(g2, xDrawPointInteger, yOfArrow, (int) (xDrawPointInteger + MESSAGE_ARROW_LENGTH * Math.cos(angle)),
						(int) (yOfArrow + MESSAGE_ARROW_LENGTH * Math.sin(angle)));
			} else if (mess.getDirection() == MMessage.BACKWARD) {
				DrawingUtil.drawArrow(g2, (int) (xDrawPointInteger + MESSAGE_ARROW_LENGTH * Math.cos(angle)),
						(int) (yOfArrow + MESSAGE_ARROW_LENGTH * Math.sin(angle)), xDrawPointInteger, yOfArrow);
			} else {
				DrawingUtil.drawArrow(g2, xDrawPointInteger, yOfArrow, (int) (xDrawPointInteger + MESSAGE_ARROW_LENGTH * Math.cos(angle)),
						(int) (yOfArrow + MESSAGE_ARROW_LENGTH * Math.sin(angle)));
			}
			//Draws the the sequence number and the sequence messages
			if(!isEnumReseted){
				g2.drawString(mess.getSequenceNumberMessage(), xDrawPointInteger + MESSAGE_ARROW_LENGTH + MESSAGE_ARROW_OFFSET, yDrawPointInteger);
			}else
			{
				g2.drawString(mess.getResetedNumberMessage() , xDrawPointInteger + MESSAGE_ARROW_LENGTH + MESSAGE_ARROW_OFFSET, yDrawPointInteger);
			}
			yDrawPointInteger += lineHeight;
			yOfArrow += lineHeight;
			if (mess.equals(coloredMessage) || mess.isFailedReturn()) {
				g2.setColor(oldColor);
			}
		}

	}

	/**
	 * Calculate the angle of messages arrow. The nearest edge segment of
	 * communication edge appoints the arrows angle.
	 * 
	 * @param x position X of messages draw point
	 * @param y position Y of messages draw point
	 * @return angle of messages arrow (parallel to nearest edge segment)
	 */
	private double computeMessageArrowAngle(double x, double y) {
		List<WayPoint> wayPoints = fEdge.getWayPoints();
		WayPoint wayPointOne = wayPoints.get(0);
		WayPoint wayPointTwo = wayPointOne.getNextWayPoint();

		// Search for begin point and end point of a segment of communication
		// edge.
		// Distance from drawing point to this segment must be minimum.
		if (wayPoints.size() > 2) {
			double nearestDistance = Double.MAX_VALUE;

			for (int i = 0; i < wayPoints.size() - 1; i++) {
				WayPoint first = wayPoints.get(i); // Point A
				WayPoint second = first.getNextWayPoint(); // Point B
				// Point C (x, y) is the argument of this method
				// Check if angle CAB > 90 degree
				double dotCB = (x - first.getX()) * (second.getX() - first.getX()) + (y - first.getY()) * (second.getY() - first.getY());
				if (dotCB < 0) {
					double distanceCA = Math.sqrt(Math.pow(x - first.getX(), 2) + Math.pow(y - first.getY(), 2));
					if (distanceCA < nearestDistance) {
						nearestDistance = distanceCA;
						wayPointOne = first;
						wayPointTwo = second;
						continue;
					}
				}
				// Check if angle CBA > 90 degree
				double dotCA = (x - second.getX()) * (first.getX() - second.getX()) + (y - second.getY()) * (first.getY() - second.getY());
				if (dotCA < 0) {
					double distanceCB = Math.sqrt(Math.pow(x - second.getX(), 2) + Math.pow(y - second.getY(), 2));
					if (distanceCB < nearestDistance) {
						nearestDistance = distanceCB;
						wayPointOne = first;
						wayPointTwo = second;
						continue;
					}
				}
				// Otherwise calculate distance from C to segment AB
				if (dotCB > 0 && dotCA > 0) {
					double numerator = Math.abs((first.getX() - second.getX()) * (second.getY() - y) - (second.getX() - x) * (first.getY() - second.getY()));
					double denominator = Math.sqrt(Math.pow((first.getX() - second.getX()), 2) + Math.pow((first.getY() - second.getY()), 2));
					double distance = numerator / denominator;
					if (distance < nearestDistance) {
						nearestDistance = distance;
						wayPointOne = first;
						wayPointTwo = second;
					}
				}
			}
		}
		// Compute the angle between target node and source node
		// The arrow of a message is always parallel to message edge
		double dx = wayPointTwo.getX() - wayPointOne.getX(), dy = wayPointTwo.getY() - wayPointOne.getY();
		double angle = Math.atan2(dy, dx);
		return angle;
	}

	/**
	 * Sets the rectangle size of this node.
	 * 
	 * @param g Used Graphics.
	 */
	@Override
	public void doCalculateSize(Graphics2D g) {
		double width = 0;	
		
		for (int i = 0; i < getEdgeMessages().size(); i++) {
			width = Math.max(width, g.getFontMetrics().stringWidth(getEdgeMessages().get(i).getSequenceNumberMessage()));
		}
		setCalculatedWidth(width + MARGIN_HORIZONTAL + MESSAGE_ARROW_OFFSET + 2 * MESSAGE_ARROW_LENGTH);
		setCalculatedHeight(g.getFontMetrics().getHeight() * getEdgeMessages().size() + MARGIN_VERTICAL);
	}

	/**
	 * Returns the all visible messages of the Edge
	 * 
	 * @return messages of the Edge
	 */

	public ArrayList<MMessage> getEdgeMessages(){
		ArrayList<MMessage> messages = new ArrayList<>();

		for (int i = 0; i < ((CommunicationDiagramEdge) fEdge).getMessages().size(); i++) {
			MMessage mess = ((CommunicationDiagramEdge) fEdge).getMessages().get(i);
			if(mess.isVisible() && !mess.isAbsentFromGraph()){
				messages.add(mess);
			}
		}
		return messages;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
	}
}
