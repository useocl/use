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

package org.tzi.use.gui.views.diagrams;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.w3c.dom.Element;

/**
 * A QualifierNode represents the rectangle placed at the
 * end of an association with qualifiers.
 * It is placed around the classifier node.
 * @author Lars Hamann
 *
 */
public class QualifierNode extends NodeBase {

	/**
	 * The classifier this qualifier node is attached to
	 */
	private NodeBase attachedClassifier;
	
	/**
	 * The association end containing informations about the
	 * qualifiers.
	 */
	private MAssociationEnd qualifiedEnd;
	
	/**
	 * Saves the displayed informations. Because qualifier values cannot be
	 * changed yet they can be stored when creating a qualifier node
	 */
	private List<String> displayedEntries;
	
	/**
	 * When multiple qualifier nodes are present at
	 * one classifier they are displayed one below the other 
	 */
	private double yOffset = 0;
	
	/**
	 * Relative position when used on a reflexive association
	 */
	private Direction relativePosition = Direction.UNKNOWN;
	
	/**
	 * 
	 * @param attachedClassifier
	 * @param target
	 * @param qualifiedEnd
	 */
	public QualifierNode(NodeBase attachedClassifier, MAssociationEnd qualifiedEnd) {
		this.attachedClassifier = attachedClassifier;
		this.qualifiedEnd = qualifiedEnd;
		this.displayedEntries = new ArrayList<String>(qualifiedEnd.getQualifiers().size());
		
		for (VarDecl qualifier : qualifiedEnd.getQualifiers()) {
        	displayedEntries.add(qualifier.toString());
        }
	}
	
	/**
	 * 
	 * @param attachedClassifier
	 * @param target
	 * @param qualifiedEnd
	 */
	public QualifierNode(NodeBase attachedClassifier, MAssociationEnd qualifiedEnd, MLink link) {
		this.attachedClassifier = attachedClassifier;
		this.qualifiedEnd = qualifiedEnd;
		this.displayedEntries = new ArrayList<String>(qualifiedEnd.getQualifiers().size());
		
		MLinkEnd linkEnd = link.linkEnd(qualifiedEnd);
		int index = 0;
		
		for (VarDecl qualifier : qualifiedEnd.getQualifiers()) {
			Value v = linkEnd.getQualifierValues().get(index);
        	displayedEntries.add(qualifier.name() + " = " + v.toString());
        	++index;
        }
	}
	
	/**
	 * Gets the offset of this qualifier node from the top position of the
	 * attached classifier.
	 * @return The offset from the top of the attached classifier.
	 */
	public double getYOffset() {
		return yOffset;
	}

	/**
	 * Sets the offset of this qualifier node from the top position of the
	 * attached classifier.
	 * @param yOffset The offset from the top of the attached classifier.
	 */
	public void setYOffset(double yOffset) {
		this.yOffset = yOffset;
	}

	/**
	 * Calculates the position of the qualifier rectangle
	 * wrt. {@link #attachedClassifier} source and <code>nextWayPoint</code>. 
	 */
	public void calculatePosition(Point2D nextWayPoint) {
		
		Rectangle2D classifierBounds = this.attachedClassifier.getBounds();
		
		if (this.relativePosition != Direction.UNKNOWN) {
			if (this.relativePosition.isLocatedNorth()) {
				// TODO: Common spacing
				this.setY(classifierBounds.getMinY() + 5);
			} else {
				this.setY(classifierBounds.getMaxY() - this.getHeight() - 5);
			}
		}
		else if (this.yOffset == 0) {
			this.setCenterY(classifierBounds.getCenterY());
		} else {
			this.setY(classifierBounds.getY() + getYOffset());
		}
		
		if (relativePosition.isLocatedEast()
				|| (relativePosition == Direction.UNKNOWN && Direction
						.getDirection(this.attachedClassifier.getCenter(),
								nextWayPoint).isLocatedEast())) {
			this.setX(classifierBounds.getMaxX());
		} else {
			this.setX(classifierBounds.getMinX() - getWidth());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#onDraw(java.awt.Graphics)
	 */
	@Override
	public void onDraw(Graphics2D g) {
		Rectangle2D dimension = this.getBounds();
		
		int x = (int)dimension.getX();
		int y = (int)dimension.getY();
		
		Color oldColor = g.getColor();
		g.setColor(Color.white);
		g.fill(dimension);
		g.setColor(oldColor);
		g.draw(dimension);
		x += 3;
				
		for (String displayedEntry : this.displayedEntries) {
			y += g.getFontMetrics().getStringBounds(displayedEntry, g).getHeight();
			g.drawString(displayedEntry.toString(), x, y);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#setRectangleSize(java.awt.Graphics)
	 */
	@Override
	public void setRectangleSize(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
                
        double width = 0;
        double height = 4;
        
        for (String displayedEntry : this.displayedEntries) {
        	Rectangle2D rec = fm.getStringBounds(displayedEntry, g);
        	width = Math.max(width, rec.getWidth());
        	height += rec.getHeight();
        }
        
        width += 4;
        setWidth((int)width);
        setHeight((int)height);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#name()
	 */
	@Override
	public String name() {
		return qualifiedEnd.name();
	}

	/**
	 * Reflexive associations have a relative position to
	 * the classifier.
	 * This values is used when calculating the position of the qualifier. 
	 * @param reflexivePosition
	 */
	public void setRelativePosition(Direction relativePosition) {
		this.relativePosition = relativePosition;
	}
	
	@Override
    protected String getStoreType() {
    	return "QualifierNode";
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#storeAdditionalInfo(org.tzi.use.gui.util.PersistHelper, org.w3c.dom.Element, boolean)
	 */
	@Override
	protected void storeAdditionalInfo(PersistHelper helper,
			Element nodeElement, boolean hidden) {
		super.storeAdditionalInfo(helper, nodeElement, hidden);
		helper.appendChild(nodeElement, "relativePosition", this.relativePosition.name());
		helper.appendChild(nodeElement, "yOffset", String.valueOf(this.getYOffset()));
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#restoreAdditionalInfo(org.tzi.use.gui.util.PersistHelper, org.w3c.dom.Element, java.lang.String)
	 */
	@Override
	protected void restoreAdditionalInfo(PersistHelper helper,
			Element nodeElement, int version) {
		super.restoreAdditionalInfo(helper, nodeElement, version);
		this.relativePosition = Direction.valueOf(helper.getElementStringValue(nodeElement, "relativePosition"));
		this.yOffset = helper.getElementDoubleValue(nodeElement, "yOffset");
	}
	
}
