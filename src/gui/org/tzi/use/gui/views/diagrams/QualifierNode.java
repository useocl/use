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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.VarDecl;

/**
 * A QualifierNode represents the rectangle placed at the
 * end of an association with qualifiers.
 * It is placed around the classifier node.
 * @author lhamann
 *
 */
public class QualifierNode extends NodeBase {

	/**
	 * The classifier this qualifier node is attached to
	 */
	private NodeBase attachedClassifier;
	
	/**
	 * The node the line points to. May be a classifier or diamond
	 * node of a n-ary association.
	 */
	private NodeBase oppositeNode;
	
	/**
	 * The association end containing informations about the
	 * qualifiers.
	 */
	private MAssociationEnd qualifiedEnd;
	
	/**
	 * 
	 * @param attachedClassifier
	 * @param target
	 * @param qualifiedEnd
	 */
	public QualifierNode(NodeBase attachedClassifier, NodeBase oppositeNode, MAssociationEnd qualifiedEnd) {
		this.attachedClassifier = attachedClassifier;
		this.qualifiedEnd = qualifiedEnd;
		this.oppositeNode = oppositeNode;
		calculatePosition();
	}
	
	/**
	 * Calculates the position of the qualifier rectangle
	 * wrt. the source and opposite node
	 */
	public void calculatePosition() {
		Rectangle classifierBounds = this.attachedClassifier.dimension().getBounds();
		
		this.fY = classifierBounds.getCenterY();
		
		double halfWidth = ((double)this.getWidth()) / 2;
		if (this.getQualifierLocation() == Direction.EAST) {
			this.fX = classifierBounds.getMaxX() + halfWidth - 1;
		} else {
			this.fX = classifierBounds.getMinX() - halfWidth;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#draw(java.awt.Graphics, java.awt.FontMetrics)
	 */
	@Override
	public void draw(Graphics g, FontMetrics fm) {
		Rectangle dimension = this.dimension().getBounds();
		
		int x = dimension.x;
		int y = dimension.y;
		
		Color oldColor = g.getColor();
		g.setColor(Color.white);
		g.fillRect(dimension.x, dimension.y, dimension.width, dimension.height);
		g.setColor(oldColor);
		g.drawRect(dimension.x, dimension.y, dimension.width, dimension.height);
		x += 3;
				
		for (VarDecl qualifier : qualifiedEnd.getQualifiers()) {
			y += fm.getStringBounds(qualifier.toString(), g).getHeight();
			g.drawString(qualifier.toString(), x, y);
		}
	}

	/**
	 * Determine location of qualifier node relative
	 * to attached classifier and opposite node.
	 * @return
	 */
	public Direction getQualifierLocation() {
		Direction qualifierLocation;
		
		if (attachedClassifier.x() < oppositeNode.x()) {
			qualifierLocation = Direction.EAST;
		} else {
			qualifierLocation = Direction.WEST;
		}
		return qualifierLocation;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.PlaceableNode#setRectangleSize(java.awt.Graphics)
	 */
	@Override
	public void setRectangleSize(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
                
        double width = 0;
        double height = 4;
        
        for (VarDecl qualifier : qualifiedEnd.getQualifiers()) {
        	Rectangle2D rec = fm.getStringBounds(qualifier.toString(), g);
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
		return qualifiedEnd.name() + " qualifier node";
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.NodeBase#cls()
	 */
	@Override
	public MClass cls() {
		return null;
	}

}
