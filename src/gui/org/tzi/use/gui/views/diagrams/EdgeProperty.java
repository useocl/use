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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.diagrams;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;

import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;

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
     * Signals on which side the edge property is placed.
     * (SOURCE_SIDE or TARGET_SIDE)
     */
    int fSide;
    
    String fName;
    NodeBase fSource;
    NodeBase fTarget;
    DiagramOptions fOpt;
    MAssociation fAssoc;
    EdgeBase fEdge;
    
    boolean fLoadingLayout = false; // true when loading a layout
    
    double fX_SourceEdgePoint;
    double fY_SourceEdgePoint;
    double fX_TargetEdgePoint;
    double fY_TargetEdgePoint;
    
    double fX_SourceEdgePoint_old;
    double fY_SourceEdgePoint_old;
    double fX_TargetEdgePoint_old;
    double fY_TargetEdgePoint_old;
    
    double fX_UserDefined = -1.0;
    double fY_UserDefined = -1.0;
    
    /**
     * Draws an edge property on a reflexive edge.
     */
    public abstract void drawEdgePropertyOnReflexiveEdge( Graphics g, 
                                                          FontMetrics fm ,
                                                          int maxHeight,
                                                          int furthestX );
    
    public void setX_UserDefined( double x ) {
        fX_UserDefined = x;
    }
    public void setY_UserDefined( double y ) {
        fY_UserDefined = y;
    }
    public void setLoadingLayout( boolean loading ) {
        fLoadingLayout = true;
    }
    
    public String name() {
        return fName;
    }
    
    /**
     * Returns true if the user placed the edge property by himself.
     */
    public boolean isUserDefined() {
        return fX_UserDefined != -1.0 && fY_UserDefined != -1.0;
    }
    
    /**
     * Updates the start coordinate of the source node of the 
     * corresponding edge.
     * @param x New x coordinate.
     * @param y New y coordiante.
     */   
    public void updateSourceEdgePoint( double x, double y ) {
        fX_SourceEdgePoint_old = fX_SourceEdgePoint;
        fY_SourceEdgePoint_old = fY_SourceEdgePoint;
        fX_SourceEdgePoint = x;
        fY_SourceEdgePoint = y;
    }
    
    /**
     * Updates the start coordinate of the target node of the 
     * corresponding edge.
     * @param x New x coordinate.
     * @param y New y coordiante.
     */   
    public void updateTargetEdgePoint( double x, double y ) {
        fX_TargetEdgePoint_old = fX_TargetEdgePoint;
        fY_TargetEdgePoint_old = fY_TargetEdgePoint;
        fX_TargetEdgePoint = x;
        fY_TargetEdgePoint = y;
    }
    
    /**
     * Resets the edge property to the automaticaly computet
     * position.
     */
    public void reposition() {
        fX_UserDefined = -1.0;
        fY_UserDefined = -1.0;
        setSelected( false );
    }
    /**
     * Returns the dimension as a polygon of this EdgeProperty.
     */
    public Polygon dimension() {
        int x = (int) x();
        int y = (int) y();
        
        int[] xpoints = { x - 3, x - 3, x + getWidth() + 1, 
                          x + getWidth() + 1};
        int[] ypoints = { y + 2, y - getHeight() + 5, 
                          y - getHeight() + 5, y + 2 };
        
        int npoints = xpoints.length;
        return new Polygon( xpoints, ypoints, npoints );
    }
    
    /**
     * Sets the rectangle size of this node.
     * @param g Used Graphics.
     */
    public void setRectangleSize( Graphics g ) {
        FontMetrics fm = g.getFontMetrics();
        setWidth( fm.stringWidth( fName ) );
        setHeight( fm.getHeight() );
    }
    
    /**
     * Returns if this node is deleteable.
     */
    public boolean isDeletable() {
        return false;
    }
    
    
    /**
     * Moves the edge property dynamicaly on the user defined position
     * if the source or target node is moved.
     */
    void moveEdgePropertyDynamicaly() {
        if ( !fLoadingLayout ) {
            Point2D.Double p = vectorBetweenPositions();
            setX( fX_UserDefined + p.x );
            setY( fY_UserDefined + p.y );
            fLoadingLayout = false;
        }
        fX_UserDefined = x();
        fY_UserDefined = y();
    }
    
    /**
     * Updates the user defined positions.
     */
    void movingEdgeProperty( Graphics g ) {
        g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        fX_UserDefined = x();
        fY_UserDefined = y();        
    }
    
    /**
     * Calculates the vector between the old x and y coordinates 
     * and the new x and y coordinates of the source start point.
     * @return Vector between the old and new x,y points.
     */
    Point2D.Double vectorBetweenPositions() {
        double x = fX_SourceEdgePoint - fX_SourceEdgePoint_old;
        double y = fY_SourceEdgePoint - fY_SourceEdgePoint_old;
        return new Point2D.Double( x, y );
    }
    
    Point2D.Double vectorBetweenPositions( double newX, double newY, 
                                           double oldX, double oldY ) {
        double x = newX - oldX;
        double y = newY - oldY;
        return new Point2D.Double( x, y );
    }
    
    public String storePlacementInfo( boolean hidden ) {
        String xml = "";
        String ident = LayoutTags.INDENT + LayoutTags.INDENT;
        
        xml = LayoutTags.INDENT + LayoutTags.EDGEPROPERTY_O;
        if ( this instanceof Rolename ) {
            if ( fSide == SOURCE_SIDE ) {
                xml += " type=\"rolename\" kind=\"source\">" + LayoutTags.NL;
            } else {
                xml += " type=\"rolename\" kind=\"target\">" + LayoutTags.NL;
            }
        } else if ( this instanceof Multiplicity ) {
            if ( fSide == SOURCE_SIDE ) {
                xml += " type=\"multiplicity\" kind=\"source\">" + LayoutTags.NL;
            } else {
                xml += " type=\"multiplicity\" kind=\"target\">" + LayoutTags.NL;
            }
        } else if ( this instanceof AssociationName ) {
            xml += " type=\"associationName\">" + LayoutTags.NL;
        } else if ( this instanceof NodeOnEdge ) {
            xml += " type=\"NodeOnEdge\">" + LayoutTags.NL;
        } else {
            xml += " type=Something Went Wrong>" + LayoutTags.NL;
        } 
        
        xml += ident + LayoutTags.NAME_O + name() 
               + LayoutTags.NAME_C + LayoutTags.NL;
        if ( isUserDefined() ) {
            xml += ident + LayoutTags.X_COORD_O + Double.toString( x() ) 
                   + LayoutTags.X_COORD_C + LayoutTags.NL;
            xml += ident + LayoutTags.Y_COORD_O + Double.toString( y() ) 
                   + LayoutTags.Y_COORD_C + LayoutTags.NL;
        } else {
            xml += ident + LayoutTags.X_COORD_O + "-1" 
                   + LayoutTags.X_COORD_C + LayoutTags.NL;
            xml += ident + LayoutTags.Y_COORD_O + "-1" 
                   + LayoutTags.Y_COORD_C + LayoutTags.NL;
        }
        xml += ident + LayoutTags.HIDDEN_O + hidden 
               + LayoutTags.HIDDEN_C + LayoutTags.NL;
        
        xml += LayoutTags.INDENT + LayoutTags.EDGEPROPERTY_C;
        return xml;
    }
    
    void setColor( Graphics g ) {
        if ( isSelected() || ( fEdge != null && fEdge.isSelected() ) ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
        }
    }
    
    void resetColor( Graphics g ) {
        g.setColor( fOpt.getEDGE_COLOR() );
    }
}