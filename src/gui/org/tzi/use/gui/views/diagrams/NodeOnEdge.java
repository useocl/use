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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Iterator;

import org.tzi.use.gui.xmlparser.LayoutTags;

/**
 * Represents a none on an edge. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class NodeOnEdge extends EdgeProperty {
    private EdgeBase fEdge;
    private int fID;
    private int fSpecialID;
    private boolean fWasMoved = true;

    public NodeOnEdge( double x, double y, NodeBase source,
                       NodeBase target, EdgeBase edge, int id,
                       int specialID, String edgeName,
                       DiagramOptions opt ) {
        fEdge = edge;
        fID = id;
        fSpecialID = specialID;
        fOpt = opt;
        fSource = source;
        fTarget = target;
        setX( x );
        setY( y );
        fX_UserDefined = x;
        fY_UserDefined = y;
        setWidth( 4 );
        setHeight( 4 );
    }
    
    public String name() {
        return String.valueOf( getID() );
    }
    
    /**
     * Returns the id of this node.
     */
    public int getID() {
        return fID;
    }
    /**
     * Sets the id of this node.
     * @param id New id.
     */
    public void setID( int id ) {
        fID = id;
    }
    
    /**
     * Returns the id of this node.
     */
    public int getSpecialID() {
        return fSpecialID;
    }
    /**
     * Sets the id of this node.
     * @param id New id.
     */
    public void setSpecialID( int id ) {
        fSpecialID = id;
    }
    
    public boolean wasMoved() {
        return fWasMoved;
    }
    public void setWasMoved( boolean wasMoved ) {
        fWasMoved = wasMoved;
    }
    
    /**
     * Draws a rectangle around the position of this node.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        // nodes of the target and source side should not be
        // visible
        if ( getSpecialID() == EdgeBase.SOURCE                     // source node
             || getSpecialID() == EdgeBase.TARGET                  // target node
             || getSpecialID() == EdgeBase.ASSOC_CLASS             // associactioclass node
             || ( getSpecialID() == EdgeBase.ASSOC_CLASS_CON       // associationclass
                  && fEdge.getNodesOnEdge().size() <= 3 )
             || ( fEdge.isReflexive()                              // reflexive edge
                  && fEdge.getNodesOnEdge().size() <= 5 ) 
             || ( fEdge.isReflexive() && fEdge instanceof NodeEdge // selfreflexive associationclass 
                  && fEdge.getNodesOnEdge().size() <= 6 ) ) {
            return;
        }
        // draw node visible
        if ( fEdge.getClickCount() > -1 ) {
            Color c = g.getColor();
            g.setColor( Color.gray ); 
            g.drawPolygon( dimension() );
            g.setColor( c );
        }
        // draw the node in selected color
        if ( isSelected() ) {
            // helper: need to set clickcount to 1 
            // sometimes the other nodes on the edge are
            // not visible
            fEdge.setClickCount( 1 );
            Color c = g.getColor();
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
            g.drawPolygon( dimension() );
            g.setColor( c );
        }
    }

    /**
     * Removes this node from the list of all nodes for on edge.
     */
    public void reposition() {
        if ( getSpecialID() != EdgeBase.ASSOC_CLASS_CON ) {
            fEdge.removeNodeOnEdge( this );
        } else {
            ((NodeEdge) fEdge).update();
        }
        
        if ( fEdge instanceof NodeEdge ) {
            if ( ( fEdge.isReflexive() && fEdge.getNodesOnEdge().size() <= 6 )
                    || ( !fEdge.isReflexive() && fEdge.getNodesOnEdge().size() <= 3 ) ) {
                Iterator it = fEdge.getNodesOnEdge().iterator();
                while ( it.hasNext() ) {
                    NodeOnEdge node = (NodeOnEdge) it.next();
                    if ( node.getSpecialID() == EdgeBase.ASSOC_CLASS_CON ) {
                        node.setWasMoved( false );
                        ((NodeEdge) fEdge).update();
                    }
                }
            }
        }
        fWasMoved = false;
    }

    /**
     * Returns the dimension as a polygon of this EdgeProperty.
     */
    public Polygon dimension() {
        int x = (int) x();
        int y = (int) y();
        
        int[] xpoints = { x - 4, x - 4, x + getWidth(), x + getWidth()};
        int[] ypoints = { y + getHeight()/2, y - getHeight(), 
                          y - getHeight(), y + getHeight()/2 };
 
        int npoints = xpoints.length;
        return new Polygon( xpoints, ypoints, npoints );
    }
    
    /**
     * Not implemented.
     */
    public void drawEdgePropertyOnReflexiveEdge( Graphics g, 
                                                 FontMetrics fm, 
                                                 int maxHeight, 
                                                 int furthestX ) {
        // empty
    }

    
    public String storePlacementInfo( boolean hidden ) {
        String xml = "";
        String ident = LayoutTags.INDENT + LayoutTags.INDENT;
        
        xml = LayoutTags.INDENT + LayoutTags.EDGEPROPERTY_O;
        
        xml += " type=\"NodeOnEdge\">" + LayoutTags.NL;
        
//        xml += ident + LayoutTags.NAME_O + name() 
//               + LayoutTags.NAME_C + LayoutTags.NL;

        xml += ident + LayoutTags.ID_O + getID() 
               + LayoutTags.ID_C + LayoutTags.NL;

        xml += ident + LayoutTags.SPECIALID_O + getSpecialID() 
               + LayoutTags.SPECIALID_C + LayoutTags.NL;

        // coordinates
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
}