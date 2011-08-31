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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * A node representing an enumeration.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class EnumNode extends ClassifierNode implements SortChangeListener {

    private List<String> fLiterals;
        
    private static final String ENUMERATION = "<<enumeration>>";
    
    EnumNode( EnumType enumeration, DiagramOptions opt ) {
    	super(enumeration, opt);
        fLiterals = enumeration.getLiterals();
    }
    
    public EnumType getEnum() {
        return (EnumType)getClassifier();
    }
    
    /**
     * After the occurence of an event the attribute list is updated.
     */
    public void stateChanged( SortChangeEvent e ) {
    	//TODO: Implement sorting
        // the sort algorithem in the model browser is not implemented yet.
        //        fLiterals = (ArrayList) ModelBrowserSorting.getInstance()
        //                                                   .sortLiterals( fLiterals );
    }
    
    @Override
    protected void calculateNameRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	FontMetrics fm = g.getFontMetrics();
        rect.width = Math.max( fm.stringWidth( getClassifier().name() ), fm.stringWidth( ENUMERATION ) ) + 10;
        rect.height = fm.getHeight() * 2;
    }
    
    @Override
    protected void calculateAttributeRectSize(Graphics2D g, Rectangle2D.Double rect) {
    	FontMetrics fm = g.getFontMetrics();
    	rect.height = 0;

        for (String literal : fLiterals) {
            rect.width = Math.max( rect.width, fm.stringWidth( literal ) );
        }
        rect.height = fm.getHeight() * fLiterals.size() + 3;
        rect.width += 10;
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
    protected void onDraw( Graphics2D g ) {
        int x = (int) getCenter().getX();
        int y;
        
        Rectangle2D bounds = getBounds();
        FontMetrics fm = g.getFontMetrics();
        
        int labelWidth = fm.stringWidth( getClassifier().name() );
        int enumerationWidth = fm.stringWidth( ENUMERATION );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getNODE_COLOR() );
        }
        
        g.fillPolygon( dimension() );
       
        g.setColor( fOpt.getNODE_FRAME_COLOR() );
        g.drawPolygon( dimension() );
        
        y = (int)bounds.getY() + fm.getAscent() + 2;
        g.setColor( fOpt.getNODE_LABEL_COLOR() );
        
        x -= enumerationWidth / 2;
        g.drawString( ENUMERATION, x, y );
        
        y += fm.getHeight();
        x = (int) getCenter().getX();
        x -= labelWidth / 2;
        g.drawString( getClassifier().name(), x, y );
        
        if ( fOpt.isShowAttributes() ) {
            // compartment divider
            x  = (int)getCenter().getX();
            g.drawLine( (int)bounds.getX(), y + 3, (int)bounds.getMaxX() - 1, y + 3 );
            x -= ( getWidth() - 10 ) / 2;
            y += 3;
            for ( String literal : fLiterals ) {
                y += fm.getHeight();
                g.drawString( literal, x, y );
            }
        }
    }
}