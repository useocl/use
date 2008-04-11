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

package org.tzi.use.gui.views.diagrams;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;

import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.classdiagram.EnumNode;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.xmlparser.LayoutTags;

/**
 * Represents a placeable node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public abstract class PlaceableNode implements Selectable {
    /**
     * X cooradinate of the placeable node.
     */
    private double fX;
    /**
     * Y cooradinate of the placeable node.
     */
    private double fY;;
    /**
     * Height of the placeable node.
     */
    private int fHeight;
    /**
     * Width of the placeable node.
     */
    private int fWidth;
    /**
     * Indicates if the node is selected.
     */
    private boolean fIsSelected;
    /**
     * Indicates if the node is dragged.
     */
    private boolean fIsDragged;
    
    /**
     * Draws the placeable node to the given Graphics object.
     * @param g Graphics object the node is drawn to.
     * @param fm FontMetrics in the graphics object.
     */
    public abstract void draw(Graphics g, FontMetrics fm);
    
    /**
     * Sets the rectangle size of this node.
     * @param g Used Graphics.
     */
    public abstract void setRectangleSize( Graphics g );
    
    /**
     * Returns if this node is deleteable.
     */
    public abstract boolean isDeletable();    

    /**
     * Returns the height of this node.
     */
    public int getHeight() {
        return fHeight;
    }
    /**
     * Sets the height of this node.
     * @param height new height of this node.
     */
    public void setHeight( int height ) {
        fHeight = height;
    }
    
    /**
     * Returns the width of this node.
     */
    public int getWidth(){
        return fWidth;
    }
    /**
     * Sets the width of this node.
     * @param width new width of this node.
     */
    public void setWidth( int width ) {
        fWidth = width;
    }
    /**
     * Sets the position of this node to x and y.
     */
    public void setPosition( double x, double y ) {
        fX = x;
        fY = y;
    }
    
    /**
     * Sets if this node is selected or not.
     */
    public void setSelected( boolean on ) {
        fIsSelected = on;
    }
    
    /**
     * Returns if this node is selected. 
     */
    public boolean isSelected() {
        return fIsSelected;
    }

    /**
     * Returns if this node is dragged. 
     */
    public boolean isDragged() {
        return fIsDragged;
    }
    
    /**
     * Sets if this node is dragged or not.
     */
    public void setDragged( boolean dragging ) {
        fIsDragged = dragging;
    }
    
    /**
     * Returns the dimension as a polygon of this node.
     */
    public abstract Polygon dimension();
    
    /**
     * Checks whether the node occupies a given point.
     */
    public boolean occupies( int x, int y ) {
        return dimension().contains( x, y );
    }
    
    /**
     * Returns the x coordinate of this node.
     */
    public double x() {
        return fX;
    }
    /**
     * Sets the x coordinate of this node.
     * @param x New x coordinate.
     */
    public void setX( double x ) {
        fX = x;
    }
    
    /**
     * Returns the y coordinate of this node.
     */
    public double y() {
        return fY;
    }
    /**
     * Sets the y coordinate of this node.
     * @param y New y coordinate.
     */
    public void setY( double y ) {
        fY = y;
    }
    
    public abstract String name();
    
    /**
     * Saves placement information about this placeable node.
     * @param hidden If this node should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public String storePlacementInfo( boolean hidden ) {
        String xml = "";
        String nl = "\n";
        
        xml = LayoutTags.NODE_O;
        if ( this instanceof ObjectNode ) {
            xml += " type=\"Object\">" + nl;
        } else if ( this instanceof ClassNode ) {
            xml += " type=\"Class\">" + nl;
        } else if ( this instanceof EnumNode ) {
            xml += " type=\"Enumeration\">" + nl;
        } else {
            xml += " type=Something Went Wrong>" + nl;
        } 
        
        xml += LayoutTags.INDENT + LayoutTags.NAME_O + name() 
               + LayoutTags.NAME_C + nl;
        xml += LayoutTags.INDENT + LayoutTags.X_COORD_O + Double.toString( x() ) 
               + LayoutTags.X_COORD_C + nl;
        xml += LayoutTags.INDENT + LayoutTags.Y_COORD_O + Double.toString( y() ) 
               + LayoutTags.Y_COORD_C + nl;
        xml += LayoutTags.INDENT + LayoutTags.HIDDEN_O + hidden 
               + LayoutTags.HIDDEN_C + nl;
        
        xml += LayoutTags.NODE_C + nl;
        return xml;
    }
}