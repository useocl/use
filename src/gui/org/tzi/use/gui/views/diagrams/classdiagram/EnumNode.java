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

$Id$

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * A node representing an enumeration.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class EnumNode extends NodeBase implements SortChangeListener {
    
    private DiagramOptions fOpt;
    private EnumType fEnum;
    private String fLabel;
    private List fLiterals;
    private String[] fLiteralValues;
    
    private final String ENUMERATION = "<<enumeration>>";
    
    EnumNode( EnumType enumeration, DiagramOptions opt ) {
        fEnum = enumeration;
        fOpt = opt;
        fLabel = enumeration.name();
        
        List allLiterals = new ArrayList();
        Iterator it = enumeration.literals();
        while ( it.hasNext() ) {
            allLiterals.add( (String) it.next() );
        }
        final int N = allLiterals.size();
        fLiteralValues = new String[N];
        fLiterals = allLiterals; //(ArrayList) ModelBrowserSorting.getInstance()
        //                  .sortLiterals( allLiterals );
        
    }
    
    public EnumType getEnum() {
        return fEnum;
    }
    
    public String name() {
        return fEnum.name();
    }

    public MClass cls() {
        return null;
    }
    
    /**
     * After the occurence of an event the attribute list is updated.
     */
    public void stateChanged( SortChangeEvent e ) {
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
    public void setRectangleSize( Graphics g ) { //FontMetrics fm ) {
        FontMetrics fm = g.getFontMetrics();
        
        setWidth( Math.max( fm.stringWidth( fLabel ), 
                            fm.stringWidth( ENUMERATION ) ) );
        setHeight( fm.getHeight()*2 );
        
        int attrHeight = 0;
        
        if ( fOpt.isShowAttributes() ) {
            for ( int i = 0; i < fLiterals.size(); i++ ) {
                String literal = (String) fLiterals.get( i );
                fLiteralValues[i] = literal;
                setWidth( Math.max( getWidth(), fm.stringWidth( fLiteralValues[i] ) ) );
            }
            attrHeight = fm.getHeight() * fLiterals.size() + 3;
        }
        
        setWidth( getWidth() + 10 );
        setHeight( attrHeight + fm.getHeight()*2 + 4 );
    }
    
    /**
     * Returns if this class node may be deleted.
     */
    public boolean isDeletable() {
        return true;
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
    public void draw( Graphics g, FontMetrics fm ) {
        int x = (int) x();
        int y = (int) y();
        
        int labelWidth = fm.stringWidth( fLabel );
        //if ( labelWidth < fm.stringWidth( ENUMERATION ) ) {
        int enumerationWidth = fm.stringWidth( ENUMERATION );
        //}
        
        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getNODE_COLOR() );
        }
        g.fillRect( x - getWidth() / 2, y - getHeight() / 2, getWidth(), getHeight() );
        g.setColor( fOpt.getNODE_FRAME_COLOR() );
        g.drawRect( x - getWidth() / 2, y - getHeight() / 2, getWidth() - 1, getHeight() - 1 );
        
        y = y - ( getHeight() / 2 ) + fm.getAscent() + 2;
        g.setColor( fOpt.getNODE_LABEL_COLOR() );
        
        x -= enumerationWidth / 2;
        g.drawString( ENUMERATION, x, y );
        
        y += fm.getHeight();
        x = (int) x();
        x -= labelWidth / 2;
        g.drawString( fLabel, x, y );
        
        if ( fOpt.isShowAttributes() ) {
            // compartment divider
            x = (int) x();
            g.drawLine( x - getWidth() / 2, y + 3, x + getWidth() / 2 - 1, y + 3 );
            x -= ( getWidth() - 10 ) / 2;
            y += 3;
            for ( int i = 0; i < fLiterals.size(); i++ ) {
                y += fm.getHeight();
                g.drawString( fLiteralValues[i], x, y );
            }
        }
    }
    
}
