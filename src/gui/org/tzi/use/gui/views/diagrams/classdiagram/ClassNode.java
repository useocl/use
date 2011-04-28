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

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;

/**
 * A node representing a class.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class ClassNode extends NodeBase implements SortChangeListener {
    
    private DiagramOptions fOpt;
    private MClass fClass;
    private String fLabel;
    private List<MAttribute> fAttributes;
    private List<MOperation> fOperations;
    private String[] fAttrValues;
    private String[] fOprSignatures;
    
    ClassNode( MClass cls, DiagramOptions opt ) {
        fClass = cls;
        fOpt = opt;
        fLabel = cls.name();
        
        fAttributes = ModelBrowserSorting.getInstance()
            .sortAttributes(cls.attributes()); 
        fAttrValues = new String[fAttributes.size()];
        fOperations = cls.operations();
        fOprSignatures = new String[fOperations.size()];
        fOperations = ModelBrowserSorting.getInstance()
            .sortOperations( fOperations );
        ModelBrowserSorting.getInstance().addSortChangeListener( this );
    }
    
    public MClass cls() {
        return fClass;
    }
    
    public String name() {
        return fClass.name();
    }
    
    /**
     * After the occurrence of an event the attribute list is updated.
     */
    public void stateChanged( SortChangeEvent e ) {
        fAttributes = ModelBrowserSorting.getInstance()
             .sortAttributes( fAttributes );
        fOperations = ModelBrowserSorting.getInstance()
            .sortOperations( fOperations );
    }
    
    /**
     * Sets the correct size of the width and height of this class node.
     * This method needs to be called before actually drawing the node.
     * (Width and height are needed from other methods before the nodes are
     * drawn.)
     */
    public void setRectangleSize( Graphics2D g ) { //FontMetrics fm ) {
        FontMetrics fm = g.getFontMetrics();
        Font normalFont = fm.getFont();
        if ( fClass.isAbstract() ) {
            Font italicFont = normalFont.deriveFont( Font.ITALIC );
            FontMetrics italicFm = g.getFontMetrics( italicFont );
            setWidth( italicFm.stringWidth( fLabel ) );
            setHeight( italicFm.getHeight() );
        } else {
            setWidth( fm.stringWidth( fLabel ) );
            setHeight( fm.getHeight() );
        }
        
        int attrHeight = 0;
        int oprHeight = 0;
        
        if ( fOpt.isShowAttributes() ) {
            for ( int i = 0; i < fAttributes.size(); i++ ) {
                MAttribute attr = (MAttribute) fAttributes.get( i );
                fAttrValues[i] = attr.toString();
                setWidth( Math.max( getWidth(), fm.stringWidth( fAttrValues[i] ) ) );
            }
            attrHeight = fm.getHeight() * fAttributes.size() + 3;
        }
        
        if ( fOpt.isShowOperations() ) {
            for ( int i = 0; i < fOperations.size(); i++ ) {
                MOperation opr = (MOperation) fOperations.get( i );
                fOprSignatures[i] = opr.signature();
                setWidth( Math.max( getWidth(), fm.stringWidth( fOprSignatures[i] ) ) );
            }
            oprHeight = fm.getHeight() * fOperations.size() + 3;
        }
        
        setWidth( getWidth() + 10 );
        setHeight( attrHeight + oprHeight + fm.getHeight() + 4 );
        
        setHeight(Math.max(getHeight(), (int)getMinHeight()));
        setWidth(Math.max(getWidth(), (int)getMinWidth()));
    }
    
    /**
     * Returns if this class node may be deleted.
     */
    public boolean isDeletable() {
        return true;
    }
    
    public String ident() {
        return "Class." + fClass.name();
    }
    public String identNodeEdge() {
        return "AssociationClass." + fClass.name();
    }
    
    /**
     * Draws a box with a label.
     */
    protected void onDraw( Graphics2D g ) {
        int x = (int) getCenter().getX();
        int y;
        
        Rectangle2D bounds = this.getBounds();
        Polygon dimension = dimension();
        FontMetrics fm = g.getFontMetrics();
        
        Font oldFont = g.getFont();
        if ( fClass.isAbstract() ) {
            g.setFont( oldFont.deriveFont( Font.ITALIC ) );
            fm = g.getFontMetrics();
        }
        
        int labelWidth = fm.stringWidth( fLabel );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getNODE_COLOR() );
        }
        g.fillPolygon( dimension );
        g.setColor( fOpt.getNODE_FRAME_COLOR() );
        g.drawPolygon( dimension );
        
        x -= labelWidth / 2;
        y = (int)bounds.getY() + fm.getAscent() + 2;
        g.setColor( fOpt.getNODE_LABEL_COLOR() );
        
        g.drawString( fLabel, x, y );
        // resetting font and fontMetrics if the class was abstract
        g.setFont( oldFont );
        fm = g.getFontMetrics();
        
        if ( fOpt.isShowAttributes() ) {
            // compartment divider
            g.drawLine( (int)bounds.getX(), y + 3, (int)bounds.getMaxX() - 1, y + 3 );
            // add insets
            x = (int)bounds.getX() + 5;
            y += 3;
            for ( int i = 0; i < fAttributes.size(); i++ ) {
                y += fm.getHeight();
                g.drawString( fAttrValues[i], x, y );
            }
        }
        
        if ( fOpt.isShowOperations() ) {
            // compartment divider
            g.drawLine( (int)bounds.getX(), y + 3, (int)bounds.getMaxX() - 1, y + 3 );
            // add insets
            x = (int)bounds.getX() + 5;
            y += 3;
            for ( int i = 0; i < fOperations.size(); i++ ) {
                y += fm.getHeight();
                g.drawString( fOprSignatures[i], x, y );
            }
        }
    }
    
    @Override
    public String toString() {
    	return fClass.name() + "(ClassNode)";
    }
}
