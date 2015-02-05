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

// $Id: EnumNode.java 1048 2009-07-05 12:10:11Z lars $

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * A node representing an enumeration.
 * 
 * @author Lars Hamann
 * @author Fabian Gutsche
 */
public class EnumNode extends ClassifierNode implements SortChangeListener {

    private final String[] fLiterals;
        
    private static final String ENUMERATION = "\u00ABenumeration\u00BB";
    
    EnumNode( EnumType enumeration, DiagramOptions opt ) {
    	super(enumeration, opt);
        fLiterals = enumeration.getLiterals().toArray(new String[0]);
    }
    
    public EnumType getEnum() {
        return (EnumType)getClassifier();
    }
    
    /**
     * After the occurence of an event the attribute list is updated.
     */
    @Override
	public void stateChanged( SortChangeEvent e ) {
    	//TODO: Implement sorting
        // the sort algorithem in the model browser is not implemented yet.
        //        fLiterals = (ArrayList) ModelBrowserSorting.getInstance()
        //                                                   .sortLiterals( fLiterals );
    }
    
    @Override
    protected void calculateNameRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	FontMetrics fm = g.getFontMetrics();
        rect.width = Math.max( fm.stringWidth( getClassifier().name() ), fm.stringWidth( ENUMERATION ) ) + HORIZONTAL_INDENT * 2;
        rect.height = Util.getLineHeight(fm) * 2 + Util.getLeading(fm) + 2 * VERTICAL_INDENT;
    }
    
    @Override
    protected void calculateAttributeRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	calculateCompartmentRectSize(g, rect, fLiterals);
    }
    
    @Override
    protected void calculateOperationsRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	rect.height = 0;
    	rect.width = 0;
    }
    
    public String ident() {
        return "Enumeration." + getClassifier().name();
    }
    public String identNodeEdge() {
        return "";
    }
    
    @Override
    protected String getStoreType() {
    	return "Enumeration";
    }
    
    /**
     * Draws a box with a label.
     */
    @Override
	protected void onDraw( Graphics2D g ) {
        int y;
        Color textColor = fOpt.getNODE_LABEL_COLOR();
        Color frameColor = fOpt.getNODE_FRAME_COLOR();
                
        Graphics2D shapeG = (Graphics2D)g.create();
        
        Rectangle2D.Double roundedBounds = getRoundedBounds();
        
        FontMetrics fm = g.getFontMetrics();
        int singleHeight = (int)Math.round(Util.getLineHeight(fm));
        
        if ( isSelected() ) {
        	shapeG.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
        	shapeG.setColor( fOpt.getNODE_COLOR() );
        }
        
        shapeG.fill( roundedBounds );
        shapeG.setColor( frameColor );
        shapeG.draw( roundedBounds );

        y = (int)roundedBounds.getY() + fm.getAscent() + VERTICAL_INDENT;
        
        shapeG.setColor(textColor);
        drawTextCentered(shapeG, ENUMERATION, roundedBounds.x, y, roundedBounds.width);
        y += singleHeight;
        
        drawTextCentered(shapeG, getClassifier().name(), roundedBounds.x, y, roundedBounds.width);
        
        y += VERTICAL_INDENT + fm.getDescent();
        
        if ( fOpt.isShowAttributes() ) {
            // compartment divider
            y += VERTICAL_INDENT;
            shapeG.setColor(frameColor);
            shapeG.drawLine( (int)roundedBounds.getX(), y, (int)roundedBounds.getMaxX(), y );
            shapeG.setColor(textColor);
            drawCompartment(shapeG, y, fLiterals, new Color[fLiterals.length], roundedBounds);
        }
        
        shapeG.dispose();
    }
    
    @Override
    public boolean hasAttributes() {
    	return fLiterals.length > 0;
    }
    
    @Override
    public boolean hasOperations() {
    	return false;
    }
}