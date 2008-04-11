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

package org.tzi.use.gui.views.diagrams;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;

/**
 * A pseude-node representing a diamond in an n-ary association.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class DiamondNode extends NodeBase {
    private DiagramOptions fOpt;
    private MAssociation fAssoc;
    private MLink fLink;
    private AssociationName fAssocName;
    private List fConnectedNodes;
    private String fName;
    private double fX_old;
    private double fY_old;
    
    private List fHalfEdges; // participating edges
    
    public DiamondNode( MAssociation assoc, DiagramOptions opt ) {
        fAssoc = assoc;
        fName = assoc.name();
        fOpt = opt;
        fConnectedNodes = new ArrayList();
        Set classes = fAssoc.associatedClasses();
        Iterator it = classes.iterator();
        while ( it.hasNext() ) {
            fConnectedNodes.add( ((MClass) it.next()).name() );
        }
        instanciateAssocName();
    }

    public DiamondNode( MLink link, DiagramOptions opt ) {
        fAssoc = link.association();
        fLink = link;
        fName = fAssoc.name();
        fOpt = opt;
        fConnectedNodes = new ArrayList();
        Set objects = link.linkedObjects();
        Iterator it = objects.iterator();
        while ( it.hasNext() ) {
            fConnectedNodes.add( ((MObject) it.next()).name() );
        }
        instanciateAssocName();
    }
    
    private void instanciateAssocName() {
        fAssocName = new AssociationName( fName, fConnectedNodes, 
                                          fOpt, (NodeBase) this, fAssoc );
    }
    
    public MAssociation association() {
        return fAssoc;
    }

    public MLink link() {
        return fLink;
    }
    
    public MClass cls() {
        return null;
    }
    
    public String name() {
        return fAssoc.name();
    }
    
    public void setHalfEdges( List edges ) {
        fHalfEdges = edges;
    }
    
    /**
     * Draws a diamond with an underlined label in the object diagram.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        int x = (int) x();
        int y = (int) y();
        int[] xpoints = { x, x + 20, x, x - 20 };
        int[] ypoints = { y - 10, y, y + 10, y };
        
        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getDIAMONDNODE_COLOR() );    
        }
        g.fillPolygon( xpoints, ypoints, xpoints.length );
        
        g.setColor( fOpt.getDIAMONDNODE_FRAME_COLOR() );
        g.drawPolygon( xpoints, ypoints, xpoints.length );

        
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
        }
        if ( fOpt.isShowAssocNames() ) {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );
            fAssocName.drawOnDiamondNode( g, fm );
        }
        g.setColor( fOpt.getDIAMONDNODE_COLOR() );
        fX_old = x();
        fY_old = y();
    }

    public Polygon dimension() {
        int x1 = (int) x();
        int y1 = (int) y();
        int[] xpoints = { x1, x1 + 20, x1, x1 - 20 };
        int[] ypoints = { y1 - 10, y1, y1 + 10, y1 };
        return new Polygon( xpoints, ypoints, xpoints.length );
    }

    /**
     * Returns if the point x,y is containt in this polygon.
     */
    public boolean occupies( int x, int y ) {
        return dimension().contains( x, y );
    }

    /**
     * Sets this node selected.
     */
    //public void setSelected( boolean on ) {}

    /**
     * Returns weather or not this object is deletable.
     */
    public boolean isDeletable() {
        return false;
    }
    
    public void setPosition( double x, double y ) {
        fX_old = x();
        fY_old = y();
        setX( x );
        setY( y );
    }

    public double oldX() {
        return fX_old;
    }
    public double oldY() {
        return fY_old;
    }
    
    public void setRectangleSize( Graphics g ) {}
    
    public int getWidth() {
        return (int) dimension().getBounds().getWidth();
    }
    public int getHeight() {
        return (int) dimension().getBounds().getHeight();
    }
    
    public EdgeProperty getAssocName() {
        return fAssocName;
    }
    
    public String storePlacementInfo( boolean hidden ) {
        String xml = "";
        
        xml = LayoutTags.NODE_O;
        if ( fLink != null ) {
            xml += " type=\"DiamondNode\" kind=\"link\">" + LayoutTags.NL;
        } else {
            xml += " type=\"DiamondNode\" kind=\"association\">" + LayoutTags.NL;
        }
        
        Iterator it = fConnectedNodes.iterator();
        while ( it.hasNext() ) {
            String nodeName = (String) it.next();
            xml += LayoutTags.INDENT + LayoutTags.CON_NODE_O + nodeName 
                   + LayoutTags.CON_NODE_C + LayoutTags.NL;
        }
        
        xml += LayoutTags.INDENT + LayoutTags.NAME_O + name() 
               + LayoutTags.NAME_C + LayoutTags.NL;
        
        xml += LayoutTags.INDENT + LayoutTags.X_COORD_O + Double.toString( x() ) 
               + LayoutTags.X_COORD_C + LayoutTags.NL;
        xml += LayoutTags.INDENT + LayoutTags.Y_COORD_O + Double.toString( y() ) 
               + LayoutTags.Y_COORD_C + LayoutTags.NL;
        
        xml += fAssocName.storePlacementInfo( hidden ) + LayoutTags.NL;
        
        xml += LayoutTags.INDENT + LayoutTags.HIDDEN_O + hidden 
               + LayoutTags.HIDDEN_C + LayoutTags.NL;
    
        if ( fHalfEdges != null ) {
            Iterator itt = fHalfEdges.iterator();
            while ( itt.hasNext() ) {
                EdgeBase e = (EdgeBase) itt.next();
                if ( e instanceof NodeEdge ) {
                    xml += ((NodeEdge) e).storeInfo( hidden ) + LayoutTags.NL;
                } else {
                    xml += e.storePlacementInfo( hidden ) + LayoutTags.NL;
                }
            }
        }
        
        xml += LayoutTags.NODE_C + LayoutTags.NL;
        return xml;
    }

}