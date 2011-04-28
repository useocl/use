/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;

/**
 * Represents a movable edge property like rolenames or multiplicities.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public abstract class EdgeProperty extends PlaceableNode {
	static final int SOURCE_SIDE = 1;
	static final int TARGET_SIDE = 2;

	static final int BINARY_EDGE = 1;
	static final int REFLEXIVE_EDGE = 2;

	/**
	 * The overall vertical (top and bottom) margin between the text of the EdgeProperty
	 * and the surrounding rectangle.
	 */
	static final int MARGIN_VERTICAL = 8;
	/**
	 * The overall (left and right) horizontal margin between the text of the EdgeProperty
	 * and the surrounding rectangle.
	 */
	static final int MARGIN_HORIZONTAL = 8;
	
	/**
	 * Signals on which side the edge property is placed. (SOURCE_SIDE or
	 * TARGET_SIDE)
	 */
	int fSide;

	/**
	 * The displayed string
	 */
	protected String fName;
	
	/**
	 * This value is calculated when
	 * the property is drawn the first time
	 * and represents the text bounds of the caption.
	 */
	protected Rectangle2D nameBounds;
	
	protected NodeBase fSource;
	
	protected NodeBase fTarget;
	
	protected DiagramOptions fOpt;
	
	protected MAssociation fAssoc;
	
	protected EdgeBase fEdge;

	protected WayPoint sourceWayPoint;
	
	protected WayPoint targetWayPoint;

	boolean isUserDefined = false;
	
	/**
	 * x-delta from the calculated position
	 */
	double fX_UserDefined = 0.0;
	
	/**
	 * y-delta from the calculated position
	 */
	double fY_UserDefined = 0.0;

	public EdgeProperty() { }

	public EdgeProperty(NodeBase source, WayPoint sourceWayPoint, NodeBase target,
			WayPoint targetWayPoint) {
		this.fSource = source;
		this.sourceWayPoint = sourceWayPoint;
			
		this.fTarget = target;
		this.targetWayPoint = targetWayPoint;
	}

	public MAssociation getAssociation() {
		return fAssoc;
	}

	public void setX_UserDefined(double x) {
		fX_UserDefined = x;
		isUserDefined = true;
		calculatePosition();
	}

	public void setY_UserDefined(double y) {
		fY_UserDefined = y;
		isUserDefined = true;
		calculatePosition();
	}

	/**
	 * Returns the bounds of the drawn text of {@link #name()}.
	 * The value is set when drawing the first time.
	 * @return
	 */
	protected Rectangle2D getNameBounds() {
		return nameBounds;
	}

	public String name() {
		return fName;
	}

	/**
	 * Returns true if the user placed the edge property by himself.
	 */
	public boolean isUserDefined() {
		return isUserDefined;
	}

	/**
	 * Updates the start coordinate of the source node of the corresponding
	 * edge.
	 * 
	 * @param p The new coordinates
	 */
	public void updateSourceEdgePoint(Point2D p) {
		calculatePosition();
	}

	/**
	 * Updates the start coordinate of the target node of the corresponding
	 * edge.
	 * 
	 * @param p The new coordinates
	 */
	public void updateTargetEdgePoint(Point2D p) {
		calculatePosition();
	}

	/**
	 * Resets the edge property to the automatically computed position.
	 */
	public void reposition() {
		fX_UserDefined = 0.0;
		fY_UserDefined = 0.0;
		
		isUserDefined = false;
		setSelected(false);
	}

	/**
	 * Sets the rectangle size of this node.
	 * 
	 * @param g
	 *            Used Graphics.
	 */
	public void setRectangleSize(Graphics2D g) {
		nameBounds = getTextLayout(g).getBounds();
		setWidth(nameBounds.getWidth() + MARGIN_HORIZONTAL);
		setHeight(nameBounds.getHeight() + MARGIN_VERTICAL);
	}

	/**
	 * Called when related elements change positions.
	 */
	protected void calculatePosition() {
		Point2D defaultPosition = getDefaultPosition();
		setX(defaultPosition.getX() + (isUserDefined() ? fX_UserDefined : 0));
		setY(defaultPosition.getY() + (isUserDefined() ? fY_UserDefined : 0));
	}
	
	/**
	 * Calculates the default position, which is used
	 * when not placed by the user.
	 */
	protected Point2D getDefaultPosition() { return getCenter(); }
	
	/**
	 * Gets the {@link TextLayout} used to draw the association name.
	 * @param g The GraphicContext to draw with
	 * @return
	 */
	protected TextLayout getTextLayout(Graphics2D g) {
		FontRenderContext frc = g.getFontRenderContext();
		Font font;
		
		if ( fEdge.isUnderlinedLabel() ) {
        	Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>(); 
        	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        	font = g.getFont().deriveFont(attributes);
        } else {
        	font = g.getFont();
        }
		
		TextLayout layout = new TextLayout(fName, font, frc);
		return layout;
	}
	
	/**
	 * Returns if this node is deleteable.
	 */
	public boolean isDeletable() {
		return false;
	}

	/**
	 * Draws a stroked line around the bounds of this edge property
	 * and lines to the attached source or target way point.
	 * If not connected to a specific way point, i.e., <code>{@link #fSide} == 0</code>,
	 * lines to both sides are drawn.
	 * @param g
	 */
	protected void drawSelected(Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		BasicStroke newStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10.0F, new float[] { 5.0F, 5.0F }, 0.0F);

		g.setStroke(newStroke);
		g.setColor(Color.GRAY);
		g.draw(getBounds());
		
		Point2D attachedPosition;
		
		if (this.fSide == SOURCE_SIDE) {
			attachedPosition = fEdge.getSourceWayPoint().getCenter(); 
		} else {
			attachedPosition = fEdge.getTargetWayPoint().getCenter(); 
		}
		
		Point2D sourcePoint = this.getIntersectionCoordinate(attachedPosition);
		g.drawLine((int)attachedPosition.getX(), (int)attachedPosition.getY(), (int)sourcePoint.getX(), (int)sourcePoint.getY());
		
		// For label in between draw another line
		if (this.fSide == 0) {
			attachedPosition = fEdge.getSourceWayPoint().getCenter();
			sourcePoint = this.getIntersectionCoordinate(attachedPosition);
			g.drawLine((int)attachedPosition.getX(), (int)attachedPosition.getY(), (int)sourcePoint.getX(), (int)sourcePoint.getY());
		}
		
		g.setStroke(oldStroke);
		g.setColor(fOpt.getNODE_SELECTED_COLOR());
	}
	
	/**
	 * Moves the edge property dynamically on the user defined position if the
	 * source or target node is moved.
	 */
	@Override
	public void setDraggedPosition(double deltaX, double deltaY) {
		fX_UserDefined += deltaX;
		fY_UserDefined += deltaY;
		isUserDefined = true;
		calculatePosition();
	}

	public String storePlacementInfo(boolean hidden) {
		StringBuilder xml = new StringBuilder();

		String ident = LayoutTags.INDENT + LayoutTags.INDENT;

		xml.append(LayoutTags.INDENT).append(LayoutTags.EDGEPROPERTY_O);
		if (this instanceof Rolename) {
			if (fSide == SOURCE_SIDE) {
				xml.append(" type=\"rolename\" kind=\"source\">").append(
						LayoutTags.NL);
			} else {
				xml.append(" type=\"rolename\" kind=\"target\">").append(
						LayoutTags.NL);
			}
		} else if (this instanceof Multiplicity) {
			if (fSide == SOURCE_SIDE) {
				xml.append(" type=\"multiplicity\" kind=\"source\">").append(
						LayoutTags.NL);
			} else {
				xml.append(" type=\"multiplicity\" kind=\"target\">").append(
						LayoutTags.NL);
			}
		} else if (this instanceof AssociationName) {
			xml.append(" type=\"associationName\">").append(LayoutTags.NL);
		} else if (this instanceof WayPoint) {
			xml.append(" type=\"NodeOnEdge\">").append(LayoutTags.NL);
		} else {
			xml.append(" type=Something Went Wrong>").append(LayoutTags.NL);
		}

		xml.append(ident).append(LayoutTags.NAME_O).append(name())
				.append(LayoutTags.NAME_C).append(LayoutTags.NL);
		if (isUserDefined()) {
			xml.append(ident).append(LayoutTags.X_COORD_O)
					.append(Double.toString(getX()))
					.append(LayoutTags.X_COORD_C).append(LayoutTags.NL);
			xml.append(ident).append(LayoutTags.Y_COORD_O)
					.append(Double.toString(getY()))
					.append(LayoutTags.Y_COORD_C).append(LayoutTags.NL);
		} else {
			xml.append(ident).append(LayoutTags.X_COORD_O).append("-1")
					.append(LayoutTags.X_COORD_C).append(LayoutTags.NL);
			xml.append(ident).append(LayoutTags.Y_COORD_O).append("-1")
					.append(LayoutTags.Y_COORD_C).append(LayoutTags.NL);
		}
		xml.append(ident).append(LayoutTags.HIDDEN_O).append(hidden)
				.append(LayoutTags.HIDDEN_C).append(LayoutTags.NL);

		xml.append(LayoutTags.INDENT).append(LayoutTags.EDGEPROPERTY_C);
		return xml.toString();
	}

	void setColor(Graphics2D g) {
		if (isSelected() || (fEdge != null && fEdge.isSelected())) {
			g.setColor(fOpt.getEDGE_SELECTED_COLOR());
		} else {
			g.setColor(fOpt.getEDGE_LABEL_COLOR());
		}
	}

	void resetColor(Graphics2D g) {
		g.setColor(fOpt.getEDGE_COLOR());
	}
}