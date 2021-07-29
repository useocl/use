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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.List;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.ToolTipProvider;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.util.FloatUtil;
import org.w3c.dom.Element;

/**
 * @author Lars Hamann
 *
 */
public class StateNode extends VertexNode implements ToolTipProvider {
	
	private static final int RECTARG = 20;
	
	private static final int TEXT_INDENT = 15;
	
	private static final int MIN_HEIGHT = 50;
	
	private static final int MIN_WIDTH = 100;
	
	private static final int NAME_INV_SPACING = 5;
	
	private final String stateName;
	
	private boolean showInvariant = true; 
	
	private final String stateInvariant;
	
	private static final String suppressedInvText = "[...]";
	
	private boolean showSuppressedInvText = true;
		
	/**
	 * @return the showSuppressedInvText
	 */
	public boolean isShowSuppressedInvText() {
	    return showSuppressedInvText;
	}

	/**
	 * @param showSuppressedInvText the showSuppressedInvText to set
	 */
	public void setShowSuppressedInvText(boolean showSuppressedInvText) {
	    this.showSuppressedInvText = showSuppressedInvText;
	}

	private Font invFont;
		
	/**
	 * @param v
	 */
	public StateNode(MState state) {
		super(state);
		
		if (state.getStateInvariant() != null) {
			StringBuilder text = new StringBuilder("[");
			state.getStateInvariant().toString(text);
			text.append("]");
			stateInvariant = text.toString();
		} else {
			stateInvariant = "";
		}
		
		stateName = state.name();
		
		setRequiredWidth("STATENODE", MIN_WIDTH);
		setRequiredHeight("STATENODE", MIN_HEIGHT);
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
    public String getId() {
    	return stateName; 
    }
	
	/**
	 * @return
	 */
	public boolean isShowInvariant() {
		return showInvariant;
	}

	/**
	 * @param b
	 */
	public void setShowInvariant(boolean b) {
		showInvariant = b;
	}
	
	@Override
	public Area getArea() {		
		RoundRectangle2D rec = new RoundRectangle2D.Double(getX(), getY(),
				getWidth(), getHeight(), RECTARG, RECTARG);
		return new Area(rec);
	}

	/**
	 * Returns the current font used to display
	 * the invariant text.
	 * @return the invFont
	 */
	public Font getInvFont() {
		return invFont;
	}

	/**
	 * Sets the font, used to display the invariant text.
	 * @param invFont the invFont to set
	 */
	public void setInvFont(Font invFont) {
		this.invFont = invFont;
	}

	@Override
	protected void onDraw(Graphics2D g) {
		Graphics2D g2 = (Graphics2D)g.create();
		
		Area area = getArea();
		
		if (this.isSelected()) {
			g2.setColor( getBackColorSelected() );
		} else if (this.isActive)
			g2.setColor( Color.GREEN );
		else
			g2.setColor( getBackColor() );
		
		g2.fill(area);
		
		g2.setColor( getFrameColor() );
		g2.draw(area);
        
        g2.setColor( getTextColor() );
        
        Rectangle2D stateNameBounds;
        String invText = getInvariantText();
        
        if (invText.length() == 0) {
        	stateNameBounds = getBounds();
        } else {
        	Rectangle2D bounds = getBounds();
			stateNameBounds = new Rectangle2D.Double(bounds.getX(),
					bounds.getY() + TEXT_INDENT, bounds.getWidth(), g
							.getFontMetrics().getStringBounds(stateName, g2)
							.getHeight());
        }
        
        Point2D.Double pen = new Point2D.Double(stateNameBounds.getX() + TEXT_INDENT, 0);
        List<TextLayout> stateInvariantLayouts = layoutText(g, pen, getWidth() - 2 * TEXT_INDENT, getHeight() - 2 * TEXT_INDENT - Util.getLineHeight(g.getFontMetrics()), invText);
        
        pen.y = stateNameBounds.getMaxY();
        drawTextCentered(stateName, stateNameBounds, g2);
        
		for (TextLayout l : stateInvariantLayouts) {
			pen.y += (l.getAscent());
			
			if (!isShowInvariant()) {
				// Draw centered
				pen.x +=  area.getBounds().getWidth() / 2 - l.getBounds().getWidth() / 2 - TEXT_INDENT;
			}
			
			l.draw(g, (float)pen.x, (float)pen.y);
	        pen.y += l.getDescent() + l.getLeading();
		}
	}

	@Override
	public void doCalculateSize(Graphics2D g) {
		String invText = getInvariantText();
		
		double requiredHeight = 2 * TEXT_INDENT +  
				                Util.getLineHeight(g.getFontMetrics(getFont())) +
				                (invText.isEmpty() ? 0 : 
				                	Util.getLineHeight(g.getFontMetrics(getInvFont())) + NAME_INV_SPACING);
				                
		this.setRequiredHeight("STATE_NODE", requiredHeight);
		// First the width of the state name
		Rectangle2D stateNameBounds = g.getFontMetrics().getStringBounds(stateName, g);
		double stateNameWidth = stateNameBounds.getWidth();
		
		// With is between state name width + indent and 3 * state name width + indent
		double maxAllowedWidth = stateNameWidth * 3;
		
		Rectangle2D stateInvBounds = g.getFontMetrics(getFont()).getStringBounds(invText, g); 
		double stateInvWidth = stateInvBounds.getWidth();
		// Force to max allowed size
		stateInvWidth = Math.min(stateInvWidth, maxAllowedWidth);
		double width = Math.max(stateNameWidth, stateInvWidth);
		width += TEXT_INDENT * 2;
		this.setCalculatedWidth(width);
		
		Point2D.Double pen = new Point2D.Double(TEXT_INDENT, NAME_INV_SPACING);
		layoutText(g, pen, getWidth() - 2 * TEXT_INDENT, getWidth() * 0.6, invText);
		double height = stateNameBounds.getHeight() + pen.y + TEXT_INDENT;
		this.setCalculatedHeight(height);
	}

	/**
	 * Returns the text to be displayed for
	 * this state node. If no state invariant
	 * is present, the string is empty.
	 * If an invariant is present, but should not be shown,
	 * <code>[...]</code> is returned.
	 * Otherwise, the state invariant text is returned. 
	 * @return
	 */
	protected String getInvariantText() {
		String invText = "";
		if (stateInvariant.length() > 0) {
			if (isShowInvariant()) {
				invText = stateInvariant;
			} else {
			    if (isShowSuppressedInvText()) {
				invText = suppressedInvText;
			    } 
			}
		}
		return invText;
	}

	private List<TextLayout> layoutText(Graphics2D g, Point2D.Double pen, double width, double availableHeight, String toLayout) {
		List<TextLayout> stateInvariantLayouts = new LinkedList<TextLayout>();

		if (toLayout == null || toLayout.isEmpty())
			return stateInvariantLayouts;
		
		FontRenderContext frc = g.getFontRenderContext();

		AttributedString styledText = new AttributedString(toLayout);
		styledText.addAttribute(TextAttribute.FONT, getInvFont());
		styledText.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
	    
		AttributedCharacterIterator characterIterator = styledText.getIterator();

		LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, frc);
		
		float wrappingWidth = (float)width;
		int nextOffset = 0;
		double lineHeight = Util.getLineHeight(g.getFontMetrics(getInvFont()));
		TextLayout layout;
		
	    while (measurer.getPosition() < toLayout.length()) {
	    	nextOffset = measurer.nextOffset(wrappingWidth);
	    	
	    	
    		// Enough space for more text?
    		if (nextOffset < toLayout.length() && pen.y + lineHeight > availableHeight) {
    			int position = measurer.getPosition();
    			
    			AttributedString styledSuffix = new AttributedString(toLayout.substring(0, nextOffset - 5) + " ...]");
    			styledSuffix.addAttribute(TextAttribute.FONT, getInvFont());
    			styledSuffix.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
    			
    			// Remove last six chars and add ...]
    			for (int i = 6; i > 0; --i) {
    				measurer.insertChar(styledSuffix.getIterator(), nextOffset - i);
    			}
    			
    			    			
    			measurer.setPosition(position);
    			layout = measurer.nextLayout(wrappingWidth);
    			stateInvariantLayouts.add(layout);
    			break;
    		} else {
    			pen.y += lineHeight;
    			layout = measurer.nextLayout(wrappingWidth);
    			stateInvariantLayouts.add(layout);
    		}
	    }
	    
	    return stateInvariantLayouts;
	}
	
	@Override
	public void setExactHeight(double height) {
		super.setExactHeight(height);
	}

	@Override
	public void setExactWidth(double width) {
		super.setExactWidth(width);
	}

	@Override
	public Point2D getIntersectionCoordinate(Point2D source, Point2D target) {
        // The original code was published here:
		// http://gravisto.fim.uni-passau.de/doc/guide/plugins/kinds/shape.html
		Rectangle2D rect = getBounds();
		
		double dist = Math.max(rect.getWidth(), rect.getHeight()) * 2;
		if (FloatUtil.equals(dist, 0.0f))
			return source;
		
        Line2D line = new Line2D.Double(source, target);
        Util.enlargeLine(line, dist);
        
        double x = rect.getX();
        double y = rect.getY();
        double w = rect.getWidth();
        double h = rect.getHeight();
        double radius = RECTARG / 2d;

        Point2D lowerLeft = new Point2D.Double(x, (y + h) - radius);
        Point2D upperLeft = new Point2D.Double(x, y + radius);
        Point2D leftUpper = new Point2D.Double(x + radius, y);
        Point2D rightUpper = new Point2D.Double((x + w) - radius, y);
        Point2D upperRight = new Point2D.Double(x + w, y + radius);
        Point2D lowerRight = new Point2D.Double(x + w, (y + h) - radius);
        Point2D rightLower = new Point2D.Double((x + w) - radius, y + h);
        Point2D leftLower = new Point2D.Double(x + radius, y + h);

        // turn the round rectangle into 4 lines
        Line2D left = new Line2D.Double(lowerLeft, upperLeft);
        Line2D upper = new Line2D.Double(leftUpper, rightUpper);
        Line2D right = new Line2D.Double(upperRight, lowerRight);
        Line2D lower = new Line2D.Double(rightLower, leftLower);

        // testing with which line intersects with line
        // and then computing the intersection point
        if (left.intersectsLine(line))
        {
            return Util.intersectionPoint(left, line);
        }
        else if (upper.intersectsLine(line))
        {
        	return Util.intersectionPoint(upper, line);
        }
        else if (right.intersectsLine(line))
        {
        	return Util.intersectionPoint(right, line);
        }
        else if (lower.intersectsLine(line))
        {
        	return Util.intersectionPoint(lower, line);
        }

        // intersection with upper left circle
        Ellipse2D upperLeftCircle2D = new Ellipse2D.Double(x, y, RECTARG, RECTARG);
        Point2D intUpperLeftCircle2D = Util.intersectWithCircle(upperLeftCircle2D, line, Direction.NORTH_WEST);

        if (intUpperLeftCircle2D != null)
        {
            return intUpperLeftCircle2D;
        }

        // intersection with upper right circle
        Ellipse2D upperRightCircle2D = new Ellipse2D.Double((x + w) -
        		RECTARG, y, RECTARG, RECTARG);
        Point2D intUpperRightCircle2D = Util.intersectWithCircle(upperRightCircle2D,
                line, Direction.NORTH_EAST);

        if (intUpperRightCircle2D != null)
        {
            return intUpperRightCircle2D;
        }

        // intersection with lower left circle
        Ellipse2D lowerLeftCircle2D = new Ellipse2D.Double(x,
                (y + h) - RECTARG, RECTARG, RECTARG);
        Point2D intLowerLeftCircle2D = Util.intersectWithCircle(lowerLeftCircle2D,
                line, Direction.SOUTH_WEST);

        if (intLowerLeftCircle2D != null)
        {
            return intLowerLeftCircle2D;
        }

        // intersection with lower right circle
        Ellipse2D lowerRightCircle2D = new Ellipse2D.Double((x + w) -
        		RECTARG, (y + h) - RECTARG, RECTARG,
        		RECTARG);
        Point2D intLowerRightCircle2D = Util.intersectWithCircle(lowerRightCircle2D,
                line, Direction.SOUTH_EAST);

        if (intLowerRightCircle2D != null)
        {
            return intLowerRightCircle2D;
        }

        return getCenter();
    }

	@Override
	public String getToolTip(MouseEvent event) {
		if (stateInvariant.equals(""))
			return null;
		else
			return "State invariant: " + stateInvariant;
	}
	
	@Override
	protected String getStoreType() {
		return "StateNode";
	}

	@Override
	protected void storeAdditionalInfo(PersistHelper helper, Element nodeElement, boolean hidden) {
		super.storeAdditionalInfo(helper, nodeElement, hidden);
		helper.appendChild(nodeElement, "showInvariant", String.valueOf(showInvariant));
	}

	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, int version) {
		super.restoreAdditionalInfo(helper, version);
		if (version > 12)
			showInvariant = helper.getElementBooleanValue("showInvariant");
	}
	
	
}
