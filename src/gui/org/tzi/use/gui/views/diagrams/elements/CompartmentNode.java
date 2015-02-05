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

package org.tzi.use.gui.views.diagrams.elements;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.util.Util;

/**
 * Base class for nodes with compartments.
 * TODO: This is an initial version. Needs more refactoring!
 * @author Lars Hamann
 *
 */
public abstract class CompartmentNode extends PlaceableNode {

	protected static final int HORIZONTAL_INDENT = 5;
	protected static final int VERTICAL_INDENT = 2;
	
	protected void calculateCompartmentRectSize(Graphics2D g, Rectangle2D.Double rect, String[] values) {
    	FontMetrics fm = g.getFontMetrics();
    	rect.height = 0;
    	rect.width = 0;
    	int leading = Util.getLeading(fm);
    	
    	if (values.length == 0) {
    		rect.height = 2 * leading;
    	} else {
	    	double singleHeight = Util.getLineHeight(fm);
	    	
	    	for ( int i = 0; i < values.length; i++ ) {
	            rect.width = Math.max( rect.width, g.getFont().getStringBounds(values[i], new FontRenderContext(null, true, true)).getWidth() );
	        }
	    	
	        rect.height = (singleHeight + leading) * values.length + leading;
    	}
    }
	
	protected int drawCompartment(Graphics2D g, int y, String[] values, Color[] colors, Rectangle2D roundedBounds) {
    	FontMetrics fm = g.getFontMetrics();
    	int leading = Util.getLeading(fm);
    	String shortenSuffix = "...";
    	int shortensuffixLength = g.getFontMetrics().stringWidth(shortenSuffix);
    	    	
    	if (values.length == 0) {
       	 	y += 2 * leading;
       	 	return y;
    	}
       	
    	Color orgColor;
    	int singleHeight = (int)Math.round(Util.getLineHeight(fm));
		Rectangle2D.Double elementRect = new Rectangle2D.Double(
				roundedBounds.getX(), roundedBounds.getY(),
				roundedBounds.getWidth(), roundedBounds.getHeight());
    	
		for ( int i = 0; i < values.length; ++i ) {
			y += leading / 2;
           	if (!isSelected() && colors[i] != null) {
           		orgColor = g.getColor();
           		g.setColor(colors[i]);
           		elementRect.y = y;
           		elementRect.height = singleHeight + leading;
           		g.fill(elementRect);
           		g.setColor(orgColor);
           	}
       	
           	y += singleHeight;
			
           	String toDraw = values[i];
           	double space = roundedBounds.getWidth() - (2 * HORIZONTAL_INDENT);
           	double roundedRequiredSpace = Math.round(g.getFont().getStringBounds(toDraw, new FontRenderContext(null, true, true)).getWidth());
           	
           	//FIXME: There must be a better way in Java to do this!
           	if (roundedRequiredSpace > space) {
           		space -= shortensuffixLength; 
           		double usedSpace = 0;
           		StringBuilder newToDraw = new StringBuilder();
           		           		
           		for (int index = 0; index < toDraw.length(); ++index) {
           			double charWidth = g.getFontMetrics().charWidth(toDraw.charAt(index)); 
           			if (usedSpace + charWidth < space) {
           				newToDraw.append(toDraw.charAt(index));
           				usedSpace += charWidth; 
           			} else {
           				break;
           			}
           		}
           		newToDraw.append(shortenSuffix);
           		toDraw = newToDraw.toString();
           	}
           	
           	// Do we have another row and do we have space for it?
           	if (values.length > i + 1 && roundedBounds.getMaxY() < y + singleHeight + VERTICAL_INDENT ) {
           		this.drawTextCentered(g, "...", roundedBounds.getX(), y - fm.getDescent(), roundedBounds.getWidth());
           		break;           		
           	} else {
	           	g.drawString(toDraw,
						Math.round(roundedBounds.getX() + HORIZONTAL_INDENT), y - fm.getDescent());
           	}
           	
           	y += leading / 2;
       }
       y += leading / 2;
       
       return y;
    }
}
