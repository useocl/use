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
import org.tzi.use.uml.mm.MNamedElement;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * A node representing an enumeration.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class EnumNode extends ClassifierNode implements SortChangeListener {
    
    private DiagramOptions fOpt;
    private EnumType fEnum;
    private List<String> fLiterals;
        
    private static final String ENUMERATION = "<<enumeration>>";
    
    EnumNode( EnumType enumeration, DiagramOptions opt ) {
        fEnum = enumeration;
        fOpt = opt;
        
        fLiterals = enumeration.getLiterals();
    }
    
    public EnumType getEnum() {
        return fEnum;
    }
    
    public MNamedElement getClassifier() {
    	return fEnum;
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
    
    /**
     * Sets the correct size of the width and height of this class node.
     * This method needs to be called before actually drawing the node.
     * (Width and height are needed from other methods before the nodes are
     * drawn.)
     */
    public void setRectangleSize( Graphics2D g ) { //FontMetrics fm ) {
        FontMetrics fm = g.getFontMetrics();
        
        setWidth( Math.max( fm.stringWidth( fEnum.name() ), 
                            fm.stringWidth( ENUMERATION ) ) );
        setHeight( fm.getHeight()*2 );
        
        int attrHeight = 0;
        
        if ( fOpt.isShowAttributes() ) {
            for (String literal : fLiterals) {
                setWidth( Math.max( getWidth(), fm.stringWidth( literal ) ) );
            }
            attrHeight = fm.getHeight() * fLiterals.size() + 3;
        }
        
        setWidth( getWidth() + 10 );
        setHeight( attrHeight + fm.getHeight()*2 + 4 );
    }
    
    public String ident() {
        return "Enumeration." + fEnum.name();
    }
    public String identNodeEdge() {
        return "";
    }    
    /**
     * Draws a box with a label.
     */
    protected void onDraw( Graphics2D g ) {
        int x = (int) getCenter().getX();
        int y;
        
        Rectangle2D bounds = getBounds();
        FontMetrics fm = g.getFontMetrics();
        
        int labelWidth = fm.stringWidth( fEnum.name() );
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
        g.drawString( fEnum.name(), x, y );
        
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