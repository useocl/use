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

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.w3c.dom.Element;

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

	protected boolean isUserDefined = false;
	
	protected boolean isLink;

	public EdgeProperty() { }

	public EdgeProperty(NodeBase source, WayPoint sourceWayPoint, NodeBase target,
			WayPoint targetWayPoint, boolean isLink) {
		this.fSource = source;
		this.sourceWayPoint = sourceWayPoint;
			
		this.fTarget = target;
		this.targetWayPoint = targetWayPoint;
		
		this.isLink = isLink;
	}

	public MAssociation getAssociation() {
		return fAssoc;
	}

	public void setX_UserDefined(double x) {
		isUserDefined = true;
		setX(x);
	}

	public void setY_UserDefined(double y) {
		isUserDefined = true;
		setY(y);
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
	 * Resets the edge property to the automatically computed position.
	 */
	public void reposition() {
		isUserDefined = false;
		setSelected(false);
		calculatePosition(0, 0);
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
	protected void calculatePosition(double deltaX, double deltaY) {
		if (isUserDefined()) {
			setPosition(getX() + deltaX, getY() + deltaY);
		} else {
			setPosition(getDefaultPosition());
		}
	}
	
	/**
	 * Calculates the default position, which is used
	 * when not placed by the user.
	 */
	protected Point2D getDefaultPosition() { return getCenter(); }
	
	protected TextLayout textLayout = null;
	
	protected int fmTextWidth = 0;
	
	/**
	 * Gets the {@link TextLayout} used to draw the edge property.
	 * @param g The GraphicContext to draw with
	 * @return
	 */
	protected TextLayout getTextLayout(Graphics2D g) {
		if (textLayout != null) return textLayout;
		
		if (g == null)
			throw new NullPointerException("Textlayout was not initialized.");
		
		FontRenderContext frc = g.getFontRenderContext();
		Font font;
		
		if ( this.isLink ) {
        	Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>(); 
        	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        	font = g.getFont().deriveFont(attributes);
        } else {
        	font = g.getFont();
        }
		
		fmTextWidth = g.getFontMetrics().stringWidth(fName);
		
		textLayout = new TextLayout(fName, font, frc);
		return textLayout;
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
		
		// Association name of n-Ary associations are related to the
		// diamond node.
		if (this.fEdge == null) {
			attachedPosition = fSource.getCenter();
		} else if (this.fSide == SOURCE_SIDE) {
			attachedPosition = fEdge.getSourceWayPoint().getCenter(); 
		} else {
			attachedPosition = fEdge.getTargetWayPoint().getCenter(); 
		}
		
		Point2D sourcePoint = this.getIntersectionCoordinate(attachedPosition);
		g.drawLine((int)attachedPosition.getX(), (int)attachedPosition.getY(), (int)sourcePoint.getX(), (int)sourcePoint.getY());
		
		// For label in between draw another line
		if (this.fSide == 0 && this.fEdge != null) {
			attachedPosition = fEdge.getSourceWayPoint().getCenter();
			sourcePoint = this.getIntersectionCoordinate(attachedPosition);
			g.drawLine((int)attachedPosition.getX(), (int)attachedPosition.getY(), (int)sourcePoint.getX(), (int)sourcePoint.getY());
		}
		
		g.setStroke(oldStroke);
		g.setColor(fOpt.getNODE_SELECTED_COLOR());
	}
		
	/**
	 * Draws the text centered inside the bounds returned by {@link #getBounds()}.
	 * The text is underlined if {@link fEdge#isLink()} returns true.
	 * @param g
	 */
	protected void drawTextCentered(Graphics2D g) {
		TextLayout layout = getTextLayout(g);
		
		Rectangle2D textBounds = layout.getBounds();
		Rectangle2D bounds = getBounds();
		
		float x = Math.round((bounds.getCenterX() - textBounds.getWidth()  / 2));
		float y = Math.round((float)(bounds.getCenterY() + (layout.getAscent() + layout.getDescent()) / 2 - layout.getDescent()));
		
		layout.draw(g, x, y);
	}
		
	/**
	 * Moves the edge property dynamically on the user defined position if the
	 * source or target node is moved.
	 */
	@Override
	public void setDraggedPosition(double deltaX, double deltaY) {
		isUserDefined = true;
		super.setDraggedPosition(deltaX, deltaY);
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
	
	@Override
	protected String getStoreElementName() { return LayoutTags.EDGEPROPERTY; }
	
	@Override
	protected void storeAdditionalInfo(PersistHelper helper, Element nodeElement, boolean hidden) {
		nodeElement.setAttribute("userDefined", String.valueOf(isUserDefined()));
	}
	
	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, Element nodeElement, String version) {
		double x = helper.getElementDoubleValue(nodeElement, "x_coord");
		double y = helper.getElementDoubleValue(nodeElement, "y_coord");
		
		if (version.equals("1")) {
			this.isUserDefined = x != -1 && y != -1;
			
			if (isUserDefined) {
				TextLayout layout = getTextLayout(null);
				int widthDiff = (int)getWidth() - fmTextWidth; 
				x = Math.round(x - widthDiff);
				y = Math.floor(y - (layout.getAscent() + layout.getDescent()) / 2 - layout.getDescent() - MARGIN_VERTICAL / 2);
			}
		} else {
			this.isUserDefined = Boolean.valueOf(nodeElement.getAttribute("userDefined"));
		}
		
		this.setPosition(x, y);
		calculatePosition(0, 0);
	}
}