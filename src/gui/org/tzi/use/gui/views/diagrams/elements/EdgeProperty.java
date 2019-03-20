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

package org.tzi.use.gui.views.diagrams.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.edges.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.w3c.dom.Element;

/**
 * Represents a movable edge property like rolenames or multiplicities.
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public abstract class EdgeProperty extends PlaceableNode {

	/**
	 * The overall vertical (top and bottom) margin between the text of the EdgeProperty
	 * and the surrounding rectangle.
	 */
	static final int MARGIN_VERTICAL = 0;
	/**
	 * The overall (left and right) horizontal margin between the text of the EdgeProperty
	 * and the surrounding rectangle.
	 */
	static final int MARGIN_HORIZONTAL = 4;

	/**
	 * The displayed string
	 */
	protected String fName;
	
	/**
	 * The edge defines its for its properties.
	 */
	protected String id;
	
	/**
	 * This value is calculated when
	 * the property is drawn the first time
	 * and represents the text bounds of the caption.
	 */
	protected Rectangle2D nameBounds;
	
	protected DiagramOptions fOpt;
	
	protected MAssociation fAssoc;
	
	protected EdgeBase fEdge;

	protected EdgeProperty sourceWayPoint;
	
	protected EdgeProperty targetWayPoint;

	protected boolean isUserDefined = false;
	
	protected boolean isLink;

	protected boolean invalid = true;
	
	private Color color = null;
	
	private boolean isVisible = true;
	
	/**
	 * @return
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	
	/**
	 * Used to draw dashed lines if selected.
	 * Shows the user to which nodes this property is related to.
	 */
	protected PlaceableNode[] relatedNodes;
	
	public EdgeProperty(DiagramOptions opt) {
		this.fOpt = opt;
	}

	public EdgeProperty(String id, PlaceableNode[] relatedNodes, boolean isLink, DiagramOptions opt, EdgeBase edgeBase) {
		this.id = id;
		this.relatedNodes = relatedNodes;
		this.isLink = isLink;
		this.fOpt = opt;
		this.fEdge = edgeBase;
	}

	public MAssociation getAssociation() {
		return fAssoc;
	}
	
	/**
	 * Returns the bounds of the drawn text of {@link #name()}.
	 * The value is set when drawing the first time.
	 * @return
	 */
	protected Rectangle2D getNameBounds() {
		return nameBounds;
	}

	@Override
	public String name() {
		return fName;
	}

	public void setName(String name) {
		fName = name;
		invalid = true;
	}
	
	@Override
	public String getId() {
		return this.id;
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
	
	/**
	 * Returns true if the user placed the edge property by himself.
	 */
	public boolean isUserDefined() {
		return isUserDefined;
	}

	protected void setIsUserDefined(boolean newValue) {
		this.isUserDefined = newValue;
	}

	/**
     * Sets the position of the edge property to its automatically computed one.
     */
	public void setToAutoPosition() {
		setIsUserDefined(false);
		this.strategy.reset();
		updatePosition();
	}
	
	/**
	 * Sets the rectangle size of this node.
	 * 
	 * @param g
	 *            Used Graphics.
	 */
	@Override
	public void doCalculateSize(Graphics2D g) {
		
		double width = 0;
		
    	String[] lines = fName.split("\n");
    	for (String line : lines) {   		
    		width = Math.max(width, g.getFontMetrics().stringWidth(line));
    	}
    	
		setCalculatedWidth(width + MARGIN_HORIZONTAL);
		setCalculatedHeight(g.getFontMetrics().getHeight() * lines.length + MARGIN_VERTICAL);
	}

	protected Font getFont(Graphics2D g) {
		Font font;
		
		if ( this.isLink ) {
        	Map<TextAttribute, Integer> attributes = new HashMap<TextAttribute, Integer>(); 
        	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        	font = g.getFont().deriveFont(attributes);
        } else {
        	font = g.getFont();
        }
		return font;
	}
	
	@Override
	protected void onDraw( Graphics2D g ) {
		if (!isVisible) return;
		
		Graphics2D g2 = (Graphics2D)g.create();
		if (invalid) {
			calculateSize(g);
			invalid = false;
		}
		 
    	if (this.getColor() != null && !this.getColor().equals(Color.WHITE)) {
    		Color old = g2.getColor();
    		g2.setColor(this.getColor());
    		g2.fill(this.getBounds());
    		g2.setColor(old);
    	}
    	
    	setColor( g2 );
        g2.setFont(getFont(g2));
        
    	if ( isSelected() ) {
        	drawSelected(g2);
        }
    	
        FontMetrics fm = g2.getFontMetrics();
    	int lineHeight = fm.getAscent() + fm.getDescent();
    	
    	int y = (int)Math.round(getY() + fm.getAscent() + MARGIN_VERTICAL / 2);
    	int x = (int)Math.round(getX() + MARGIN_HORIZONTAL / 2);
    	
    	String[] lines = fName.split("\n");
    	for (String line : lines) {
    		AttributedString str = new AttributedString(line, g2.getFont().getAttributes());
    		g2.drawString(str.getIterator(), x, y);
    		y += lineHeight;
    	}
    }
	
	/**
	 * Draws a stroked line around the bounds of this edge property
	 * and lines to the attached source or target way point.
	 * If not connected to a specific way point, i.e., <code>{@link #fSide} == 0</code>,
	 * lines to both sides are drawn.
	 * @param g
	 */
	protected void drawSelected(Graphics2D g) {
		Graphics2D g2 = (Graphics2D)g.create();
		
		BasicStroke newStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10.0F, new float[] { 5.0F, 5.0F }, 0.0F);

		g2.setStroke(newStroke);
		g2.setColor(Color.GRAY);
		g2.draw(getBounds());
		
		Point2D ourCenter = getCenter();
		Line2D line = new Line2D.Double();
		for (PlaceableNode n : relatedNodes) {
			Point2D lineTo = n.getIntersectionCoordinate(ourCenter);
			Point2D lineFrom = this.getIntersectionCoordinate(lineTo);
			line.setLine(lineFrom, lineTo);
			g2.draw(line);
		}
		
		g2.dispose();
	}
		
	/**
	 * Moves the edge property dynamically on the user defined position if the
	 * source or target node is moved.
	 */
	@Override
	public void setDraggedPosition(double deltaX, double deltaY) {
		setIsUserDefined(true);
		super.setDraggedPosition(deltaX, deltaY);
	}

	void setColor(Graphics2D g) {
		if (isSelected() || (fEdge != null && fEdge.isSelected())) {
			g.setColor(fOpt.getEDGE_SELECTED_COLOR());
		} else if(fEdge instanceof AssociationOrLinkPartEdge && ((AssociationOrLinkPartEdge) fEdge).adjacentObjectNodeGreyed()) {
			g.setColor(fOpt.getGREYED_LINE_COLOR());
		} else {
			g.setColor(fOpt.getEDGE_LABEL_COLOR());
		}
	}

	@Override
	protected String getStoreElementName() { return LayoutTags.EDGEPROPERTY; }
	
	@Override
	protected void storeAdditionalInfo(PersistHelper helper, Element nodeElement, boolean hidden) {
		nodeElement.setAttribute("userDefined", String.valueOf(isUserDefined()));
		nodeElement.setAttribute("visible", String.valueOf(isVisible()));
	}
	
	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, int version) {
		this.setIsUserDefined(Boolean.valueOf(helper.getAttributeValue("userDefined")));
		String visible = helper.getAttributeValue("visible");
		if (visible != null)
			this.setVisible(Boolean.valueOf(visible));
		
		// Check if saved name without white spaces equals current name.
		// This allows property names to layouted by new lines and spaces.
		String name = helper.getElementStringValue("name");
		if (name.replaceAll("(\\n|\\s)", "").equals(fName.replaceAll("(\\n|\\s)", "")))
			fName = name;
	}
}