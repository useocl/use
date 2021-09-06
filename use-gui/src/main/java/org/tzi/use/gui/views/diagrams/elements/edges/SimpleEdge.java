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

package org.tzi.use.gui.views.diagrams.elements.edges;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.Iterator;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;

/**
 * TODO
 * @author Lars Hamann
 *
 */
public class SimpleEdge extends EdgeBase {

	private boolean isDashed = false;

	private Color color;
	
	/**
	 * @param source
	 * @param target
	 * @param edgeName
	 * @param opt
	 * @param completeEdgeMoveMovesUserWayPoints
	 */
	protected SimpleEdge(PlaceableNode source, PlaceableNode target,
			String edgeName, DiagramOptions opt) {
		super(source, target, edgeName, opt, false);
		this.color = Color.BLACK;
	}

	/**
	 * @return the isDashed
	 */
	public boolean isDashed() {
		return isDashed;
	}

	/**
	 * @param isDashed the isDashed to set
	 */
	public void setDashed(boolean isDashed) {
		this.isDashed = isDashed;
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

	@Override
	protected void onDraw(Graphics2D g) {
		Graphics2D ourG = (Graphics2D)g.create();
		
		if (this.isSelected()) {
			ourG.setColor(fOpt.getEDGE_SELECTED_COLOR());
		} else {
			ourG.setColor(color);
		}
		
		WayPoint n1 = null;
		Point2D p1 = null;

		WayPoint n2 = null;
		Point2D p2 = null;

		Stroke edgeStroke;
		Stroke wpStroke = g.getStroke();
		
		if (isDashed) {
			edgeStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0F, new float[] { 5.0F, 5.0F }, 0.0F);
		} else {
			edgeStroke = g.getStroke();
		}
		
		// draw all line segments
		if (!fWayPoints.isEmpty()) {
			Iterator<WayPoint> it = fWayPoints.iterator();
			n1 = it.next();
			
			n1.draw(ourG);
			
			while (it.hasNext()) {
				n2 = it.next();
				p1 = n1.getLinePoint();
				p2 = n2.getLinePoint();

				// draw nodeOnEdge
				ourG.setStroke(wpStroke);
				n2.draw(ourG);
				ourG.setStroke(edgeStroke);
				ourG.drawLine((int) Math.round(p1.getX()),
						   (int) Math.round(p1.getY()),
						   (int) Math.round(p2.getX()),
						   (int) Math.round(p2.getY()));
				n1 = n2;
			}
		}
		
		ourG.dispose();
	}

	@Override
	public boolean isLink() {
		return false;
	}

	@Override
	protected String getStoreType() {
		return "simpleedge";
	}

	@Override
	protected String getIdInternal() {
		return "simpleedge::" + fSource.getId() + "::" + fTarget.getId();
	}
	
	public static SimpleEdge create(PlaceableNode source, PlaceableNode target, String edgeName, DiagramOptions opt) {
		SimpleEdge e = new SimpleEdge(source, target, edgeName, opt);
		e.initialize();
		return e;
	}
}
