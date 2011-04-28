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

package org.tzi.use.gui.views.diagrams.waypoints;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.EdgeProperty;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.util.Log;

/**
 * Represents a way point on an edge. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class WayPoint extends EdgeProperty {
    
    private int fID;
    
    private WayPointType fType;
    
    private boolean fWasMoved = true;

    public WayPoint( NodeBase source,
                     NodeBase target, EdgeBase edge, int id,
                     WayPointType type, String edgeName,
                     DiagramOptions opt ) {
        fEdge = edge;
        fID = id;
        fType = type;
        fOpt = opt;
        fSource = source;
        fTarget = target;
        setWidth( 8 );
        setHeight( 8 );
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
    public WayPointType getSpecialID() {
        return fType;
    }
    
    public boolean wasMoved() {
        return fWasMoved;
    }
    public void setWasMoved( boolean wasMoved ) {
        fWasMoved = wasMoved;
    }
    
    public boolean isSpecial()  {
        return fType.isSpecial();
    }
    
    /**
     * Returns the point that the drawing engine uses during
     * calculation of the path of an edge.
     * For user defined way points (<code>type == {@link WayPointType#USER}</code>) this is the center of
     * the way point. For special ones this differs.     
     * @return The point the drawing engine uses to calculate pathes
     */
    public Point2D getCalculationPoint() {
    	return this.getCenter();
    }
    
    @Override
    protected Point2D getDefaultPosition() {
    	return new Point2D.Double();
    }
    
    protected boolean isVisible() {
    	if (getSpecialID() == WayPointType.ASSOC_CLASS             // associactioclass node
            || ( getSpecialID() == WayPointType.ASSOC_CLASS_CON       // associationclass
                 && fEdge.getNodesOnEdge().size() <= 3 )
            || ( fEdge.isReflexive()                              // reflexive edge
                 && fEdge.getNodesOnEdge().size() <= 5 ) 
            || ( fEdge.isReflexive() && fEdge instanceof BinaryAssociationClassOrObject // selfreflexive associationclass 
                 && fEdge.getNodesOnEdge().size() <= 6 ) ) {
           return false;
       } else {
    	   return true;
       }
    }
    
    @Override
    public void setRectangleSize(Graphics2D g) {
    	// Size is set by constructor
    	
    	// has no text
    	nameBounds = new Rectangle2D.Double();
    }
    
    /**
     * Draws a rectangle around the position of this node.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        // some way points are invisible 
        //if (!isVisible()) return;
        
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
        
        if (Log.isDebug()) {
        	String text = this.getSpecialID().toString();
        	if (text == null) text = String.valueOf(this.getID());
        	
        	g.drawString(text, (int)this.getX(), (int)this.getY());
        }
    }

    /**
     * Removes this node from the list of all nodes for on edge.
     */
    public void reposition() {
        if ( getSpecialID() != WayPointType.ASSOC_CLASS_CON ) {
            fEdge.removeNodeOnEdge( this );
        } else {
            ((BinaryAssociationClassOrObject) fEdge).update();
        }
        
        if ( fEdge instanceof BinaryAssociationClassOrObject ) {
            if ( ( fEdge.isReflexive() && fEdge.getNodesOnEdge().size() <= 6 )
                    || ( !fEdge.isReflexive() && fEdge.getNodesOnEdge().size() <= 3 ) ) {
                for (WayPoint node : fEdge.getNodesOnEdge()) {
                    if ( node.getSpecialID() == WayPointType.ASSOC_CLASS_CON ) {
                        node.setWasMoved( false );
                        ((BinaryAssociationClassOrObject) fEdge).update();
                    }
                }
            }
        }
        fWasMoved = false;
    }
      
    /**
     * Not implemented.
     */
    public void drawEdgePropertyOnReflexiveEdge( Graphics2D g, 
                                                 FontMetrics fm, 
                                                 double maxHeight, 
                                                 double furthestX ) {
        // empty
    }

    
    public String storePlacementInfo( boolean hidden ) {
        StringBuilder xml = new StringBuilder();
        String ident = LayoutTags.INDENT + LayoutTags.INDENT;
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.EDGEPROPERTY_O);
        
        xml.append(" type=\"NodeOnEdge\">").append(LayoutTags.NL);
        
        xml.append(ident).append(LayoutTags.ID_O).append(getID()) 
               .append(LayoutTags.ID_C).append(LayoutTags.NL);

        xml.append(ident).append(LayoutTags.SPECIALID_O).append(getSpecialID().getId()) 
               .append(LayoutTags.SPECIALID_C).append(LayoutTags.NL);

        // coordinates
        if ( isUserDefined() ) {
            xml.append(ident).append(LayoutTags.X_COORD_O).append(Double.toString( getX() )) 
                   .append(LayoutTags.X_COORD_C).append( LayoutTags.NL );
            xml.append(ident).append(LayoutTags.Y_COORD_O).append(Double.toString( getY() )) 
                   .append(LayoutTags.Y_COORD_C).append(LayoutTags.NL);
        } else {
            xml.append(ident).append(LayoutTags.X_COORD_O).append("-1") 
                   .append(LayoutTags.X_COORD_C).append(LayoutTags.NL);
            xml.append(ident).append(LayoutTags.Y_COORD_O).append("-1") 
                   .append(LayoutTags.Y_COORD_C).append(LayoutTags.NL);
        }
        xml.append(ident).append(LayoutTags.HIDDEN_O).append(hidden) 
               .append(LayoutTags.HIDDEN_C).append(LayoutTags.NL);
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.EDGEPROPERTY_C);
        return xml.toString();
    }
}