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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;

import com.google.common.collect.Multimap;

/**
 * This class represents Edge between Nodes (ActorNode, ObjectBoxNode, LinkBoxNode) in
 * communication diagrams.
 * 
 * @author Quang Dung Nguyen
 * 
 */
public class CommunicationDiagramEdge extends EdgeBase {
	/**
	 * if <code>true</code> a dashed line is drawn instead of a solid one.
	 */
	private boolean isDashed = false;

	/**
	 * Status of edge while navigating of messages in communication diagram
	 */
	private boolean isActivated;

	/**
	 * All of messages belong to a edge
	 */
	private ArrayList<MMessage> messages;

	/**
	 * Group of all messages belong to a edge
	 */
	private MessagesGroup messagesGroup;

	/**
	 * Is absent in current view, i. e.,
	 * it cannot be made visible by the user.
	 */
	private boolean isAbsentInCurrentView = false;

	private DiagramView parentView;

	public CommunicationDiagramEdge(PlaceableNode source, PlaceableNode target, DiagramView diagram, boolean completeEdgeMoveMovesUserWayPoints) {
		super(source, target, source.name() + " - " + target.name(), diagram.getOptions(), false);
		messages = new ArrayList<MMessage>();
		isActivated = false;
		parentView = diagram;
	}

	@Override
	protected void initializeProperties(Multimap<PropertyOwner, EdgeProperty> properties) {
		super.initializeProperties(properties);

		messagesGroup = new MessagesGroup(fOpt, this);
		properties.put(PropertyOwner.EDGE, messagesGroup);
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && !isAbsentInCurrentView();
	}

	@Override
	public void setHidden(boolean isHidden){
		super.setHidden(isHidden);
	}

	public void setActivate(boolean on) {
		this.isActivated = on;
	}

	public boolean isActivated() {
		return isActivated;
	}

	void addNewMessage(MMessage newMess) {
		messages.add(newMess);
		newMess.setOwner(this);
	}

	public void hideMessage(MMessage hideMessage){

	}

	public boolean isDashed(){
		return isDashed;
	}

	public void setDashed(boolean isDashed){
		this.isDashed = isDashed;
	}

	public List<MMessage> getMessages() {
		return messages;
	}

	public MessagesGroup getMessagesGroup() {
		return messagesGroup;
	}

	/**
	 * @return the isAbsentInCurrentView
	 */
	public boolean isAbsentInCurrentView() {
		return isAbsentInCurrentView;
	}

	/**
	 * @param isAbsentInCurrentView the isAbsentInCurrentView to set
	 */
	public void setAbsentInCurrentView(boolean isAbsentInCurrentView) {
		this.isAbsentInCurrentView = isAbsentInCurrentView;
	}

	public MMessage getLongestMessage() {
		if (messages.size() == 0)
			return null;

		MMessage result = messages.get(0);

		for (int i = 1; i < messages.size(); ++i) {		
			MMessage item = messages.get(i);

			if (item.getSequenceNumberMessage().length() > result.getSequenceNumberMessage().length()) {
				result = item;
			}
		}

		return result;
	}

	public int getPreferedLength() {
		MMessage longestMessage = getLongestMessage();
		String text = (longestMessage == null ? "xxxxxxxx" : longestMessage.getSequenceNumberMessage());
		Graphics g = parentView.getGraphics();
		if (g == null) {
			return text.length();
		} else {
			return g.getFontMetrics().stringWidth(text) * 2 + 100;
		}
	}

	@Override
	protected void onDraw(Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		if (isSelected()) {
			g.setColor(fOpt.getEDGE_SELECTED_COLOR());
		} else if (isActivated) {
			g.setColor(new Color(80, 136, 252));
			g.setStroke(new BasicStroke((float) 1.3));
		} else {
			g.setColor(fOpt.getEDGE_COLOR());
		}

		drawEdge(g);

		g.setStroke(oldStroke);
		g.setColor(fOpt.getEDGE_COLOR());
	}

	/**
	 * Draws the edge segments of this edge.
	 * 
	 * @param g
	 *            The edge is drawn in this graphics object.
	 */
	private void drawEdge(Graphics2D g) {
		EdgeProperty n1 = null;
		Point2D p1 = null;
		WayPoint n2 = null;
		Point2D p2 = null;

		// draw all line segments

		if (!fWayPoints.isEmpty()) {
			Iterator<WayPoint> it = fWayPoints.iterator();

			if (it.hasNext()) {
				n1 = it.next();
				n1.draw(g);
			}

			while (it.hasNext()) {
				n2 = it.next();
				p1 = n1.getCenter();
				p2 = n2.getCenter();

				// draw way points
				n2.draw(g);

				try {
					DirectedEdgeFactory.drawAssociation(g, (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY(), isDashed);
					n1 = n2;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public PlaceableNode findNode(double x, double y) {
		PlaceableNode res = super.findNode(x, y);

		if (((CommunicationDiagramOptions) fOpt).isShowCommunicationMessages() && messages.size() > 0 && messagesGroup.isVisible()
				&& messagesGroup.occupies(x, y)) {
			res = messagesGroup;
		}
		return res;
	}

	@Override
	public void drawProperties(Graphics2D g2d) {
		// draw edge properties on the edge
		if (isSelected()) {
			g2d.setColor(fOpt.getEDGE_SELECTED_COLOR());
		} else {
			g2d.setColor(fOpt.getEDGE_LABEL_COLOR());
		}

		if (((CommunicationDiagramOptions) fOpt).isShowCommunicationMessages()) {
			if (messagesGroup != null)
				messagesGroup.draw(g2d);
		}
	}

	@Override
	public boolean isLink() {
		return false;
	}

	@Override
	protected String getIdInternal() {
		return "communication edge::" + fSource.getId() + "::" + fTarget.getId() + "::";
	}

	@Override
	protected String getStoreType() {
		return "Communication Edge";
	}

	static CommunicationDiagramEdge create(PlaceableNode source, PlaceableNode target, DiagramView diagram, boolean completeEdgeMoveMovesUserWayPoints) {
		CommunicationDiagramEdge edge = new CommunicationDiagramEdge(source, target, diagram, completeEdgeMoveMovesUserWayPoints);
		edge.initialize();
		return edge;
	}

}
