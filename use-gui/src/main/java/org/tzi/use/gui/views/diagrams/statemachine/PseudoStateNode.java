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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.uml.mm.statemachines.MPseudoState;
import org.tzi.use.uml.mm.statemachines.MPseudoStateKind;
import org.w3c.dom.Element;

/**
 * @author Lars Hamann
 *
 */
public class PseudoStateNode extends VertexNode {

	protected DrawingImpl drawingImpl;
	
	protected StateName nameNode;
	
	/**
	 * @param v
	 */
	public PseudoStateNode(MPseudoState v) {
		super(v);
		drawingImpl = new DrawingFactory().createDrawing(v.getKind());
		nameNode = new StateName(this);
		//TODO: Make settable
		nameNode.setSelectedColor(Color.orange);
		nameNode.setColor(Color.black);
	}

	public MPseudoState getPseudoState() {
		return (MPseudoState)this.getVertex();
	}

	@Override
    public String getId() {
    	return getVertex().name(); 
    }
	
	@Override
	protected void onDraw(Graphics2D g) {
		nameNode.draw(g);
		drawingImpl.onDraw(g);
	}

	@Override
	public void doCalculateSize(Graphics2D g) {
		drawingImpl.setRectangleSize(g);
		nameNode.doCalculateSize(g);
	}

	@Override
	public PlaceableNode getRelatedNode(double x, double y) {
		if (nameNode.occupies(x, y)) {
			return nameNode;
		}
		
		return super.getRelatedNode(x, y);
	}

	@Override
	public Point2D getIntersectionCoordinate(Point2D source, Point2D target) {
		return drawingImpl.getIntersectionCoordinate(source, target);
	}
	
	@Override
	protected void storeAdditionalInfo(PersistHelper helper,
			Element nodeElement, boolean hidden) {
		super.storeAdditionalInfo(helper, nodeElement, hidden);
		nameNode.storePlacementInfo(helper, nodeElement, hidden);
	}

	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, int version) {
		super.restoreAdditionalInfo(helper, version);
		
		if (helper.toFirstChild("node"))
			nameNode.restorePlacementInfo(helper, version);
		
		helper.toParent();
	}

	@Override
	protected String getStoreType() {
		return "PseudoStateNode";
	}

	protected class DrawingFactory {
		protected Map<MPseudoStateKind, DrawingImpl> kindDrawings = new HashMap<MPseudoStateKind, DrawingImpl>();
		
		public DrawingFactory() {
			kindDrawings.put(MPseudoStateKind.initial, new DrawingImpl() {
				protected static final int DOT_SIZE = 14;
				
				@Override
				public void onDraw(Graphics2D g) {
					Rectangle2D bounds = getBounds();
					if (isSelected())
						g.setColor(getBackColorSelected());
					else
						g.setColor(Color.BLACK);
					
					g.fillArc(
							(int)Math.round(bounds.getX()), 
							(int)Math.round(bounds.getY()),
							DOT_SIZE,
							DOT_SIZE, 
							0, 360);
				}

				@Override
				public void setRectangleSize(Graphics2D g) {
					setExactWidth(DOT_SIZE);
					setExactHeight(DOT_SIZE);
				}
				
				@Override
				public Point2D getIntersectionCoordinate(Point2D source, Point2D target) {
					// we get the center: build ellipse around it
					return Util.intersectionPoint(
							new Ellipse2D.Double(source.getX() - DOT_SIZE / 2, source.getY() - DOT_SIZE / 2, DOT_SIZE, DOT_SIZE), target);
				}
			});
		}
		
		public DrawingImpl createDrawing(MPseudoStateKind kind) {
			DrawingImpl i = kindDrawings.get(kind);
			return i;
		}
	}
	
	protected interface DrawingImpl {
		public void onDraw(Graphics2D g);
		public void setRectangleSize(Graphics2D g);
		public Point2D getIntersectionCoordinate(Point2D source, Point2D target);
	}
}