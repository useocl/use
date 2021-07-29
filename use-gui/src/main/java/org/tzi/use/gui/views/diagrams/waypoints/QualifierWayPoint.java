/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2013 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams.waypoints;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;

/**
 * This way point can be used instead
 * of an attached way point to add qualifier to an edge. 
 * @author Lars Hamann
 *
 */
public class QualifierWayPoint extends AttachedWayPoint {
	
	MAssociationEnd qualifiedEnd;
	
	/**
	 * Saves the displayed informations. Because qualifier values cannot be
	 * changed yet they can be stored when creating a qualifier node
	 */
	private List<String> displayedEntries;

	private AttachedWayPoint ourAttached;
	
	public QualifierWayPoint(PlaceableNode attachedNode, EdgeBase edge,
			WayPointType type, String edgeName, DiagramOptions opt, MAssociationEnd theQualifiedEnd, MLink link) {
		super(attachedNode, edge, type, edgeName, opt);
		qualifiedEnd = theQualifiedEnd;
		ourAttached = new AttachedWayPoint(this, fEdge, fType, fName, fOpt);
		ourAttached.setID(-2);
		
		displayedEntries = new ArrayList<String>(qualifiedEnd.getQualifiers().size());
		
		if (link == null) {
			for (VarDecl qualifier : qualifiedEnd.getQualifiers()) {
	        	displayedEntries.add(qualifier.toString());
	        }
		} else {
			MLinkEnd linkEnd = link.linkEnd(qualifiedEnd);
			int index = 0;
			
			for (VarDecl qualifier : qualifiedEnd.getQualifiers()) {
				Value v = linkEnd.getQualifierValues().get(index);
	        	displayedEntries.add(qualifier.name() + " = " + v.toString());
	        	++index;
	        }
		}
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		ourAttached.setPreviousWayPoint(previousWayPoint);
		ourAttached.setNextWayPoint(nextWayPoint);
		ourAttached.initialize();
	}

	@Override
	public AttachedWayPoint getStrategyWayPoint() {		
		return this.ourAttached;
	}

	@Override
	public double getHeightHint() {
		return getHeight();
	}
	
	@Override
	public void setPreviousWayPoint(WayPoint previousWayPoint) {
		super.setPreviousWayPoint(previousWayPoint);
		if (this.ourAttached != null) this.ourAttached.setPreviousWayPoint(previousWayPoint);
	}

	@Override
	public void setNextWayPoint(WayPoint nextWayPoint) {
		super.setNextWayPoint(nextWayPoint);
		if (this.ourAttached != null) this.ourAttached.setNextWayPoint(nextWayPoint);
	}

	@Override
	protected void onDraw(Graphics2D g) {
		Graphics2D myG = (Graphics2D)g.create();
		ourAttached.onDraw(myG);
		Rectangle2D bounds = getRoundedBounds();
		
		int x = (int)Math.round(bounds.getX());
		int y = (int)Math.round(bounds.getY());
		
		Color oldColor = g.getColor();
		myG.setColor(Color.white);
		myG.fill(bounds);
		myG.setColor(oldColor);
		myG.draw(bounds);
		x += 3;
				
		for (String displayedEntry : this.displayedEntries) {
			y += myG.getFontMetrics().getStringBounds(displayedEntry, myG).getHeight();
			myG.drawString(displayedEntry.toString(), x, y);
		}
		
		myG.dispose();
	}
	
	@Override
	public void doCalculateSize(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
                
        double width = 0;
        double height = 4;
        
        for (String displayedEntry : this.displayedEntries) {
        	Rectangle2D rec = fm.getStringBounds(displayedEntry, g);
        	width = Math.max(width, rec.getWidth());
        	height += rec.getHeight();
        }
        
        width += 4;
        setCalculatedBounds(width, height);
	}

	/**
	 * A qualifier is attached to the WEST or EAST side of
	 * the node. The y value of the qualifier node is the
	 * original position calculated by the attach startegy.  
	 */
	@Override
	public void verifyUpdatePosition(Point2D autoPosition) {
		Rectangle2D attached = getAttachedNode().getBounds();
				
		Direction nextWayPointDirection = Direction.getDirection(getAttachedNode().getCenter(), autoPosition);
				
		double x;
		if (nextWayPointDirection.isLocatedEast()) {
			x = attached.getMaxX();
		} else {
			x = attached.getMinX() - getWidth();
		}
		
		double y = Math.min(Math.max(autoPosition.getY() - getHeight() / 2, attached.getMinY() + 4), attached.getMaxY() - getHeight() - 4);
		
		autoPosition.setLocation(x, y);
	}

	/**
	 * First, the qualifier way point updates the qualifier position.
	 * Afterwards, the position of the way point attached to
	 * the qualifier is updated.
	 */
	@Override
	public void updatePosition() {
		super.updatePosition();
		if (ourAttached != null) // Null during construction
			ourAttached.updatePosition();
	}

	/**
	 * Returns the point to draw the edge to.
	 * For a qualifier this is the position of the way point
	 * attached to the qualifier.
	 * @return
	 */
	public Point2D getLinePoint() {
		return ourAttached.getLinePoint();
	}

	@Override
	public PlaceableNode getRelatedNode(double x, double y) {
		if (ourAttached.occupies(x, y))
			return ourAttached;
		
		return super.getRelatedNode(x, y);				
	}

	@Override
	public void collectChildNodes(Map<String, PlaceableNode> allNodes) {
		super.collectChildNodes(allNodes);
		allNodes.put(this.ourAttached.getId(), this.ourAttached);
	}
}
