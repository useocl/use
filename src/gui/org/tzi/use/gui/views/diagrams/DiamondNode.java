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
        StringBuilder xml = new StringBuilder();
        
        xml.append(LayoutTags.NODE_O);
        if ( fLink != null ) {
            xml.append(" type=\"DiamondNode\" kind=\"link\">").append(LayoutTags.NL);
        } else {
            xml.append(" type=\"DiamondNode\" kind=\"association\">").append(LayoutTags.NL);
        }
        
        Iterator it = fConnectedNodes.iterator();
        while ( it.hasNext() ) {
            String nodeName = (String) it.next();
            xml.append(LayoutTags.INDENT).append(LayoutTags.CON_NODE_O).append(nodeName) 
                   .append(LayoutTags.CON_NODE_C).append(LayoutTags.NL);
        }
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.NAME_O).append(name()) 
               .append(LayoutTags.NAME_C).append(LayoutTags.NL);
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.X_COORD_O).append(Double.toString( x() )) 
               .append(LayoutTags.X_COORD_C).append(LayoutTags.NL);
        xml.append(LayoutTags.INDENT).append(LayoutTags.Y_COORD_O).append(Double.toString( y() )) 
               .append(LayoutTags.Y_COORD_C).append(LayoutTags.NL);
        
        xml.append(fAssocName.storePlacementInfo( hidden )).append(LayoutTags.NL);
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.HIDDEN_O).append(hidden) 
               .append(LayoutTags.HIDDEN_C).append(LayoutTags.NL);
    
        if ( fHalfEdges != null ) {
            Iterator itt = fHalfEdges.iterator();
            while ( itt.hasNext() ) {
                EdgeBase e = (EdgeBase) itt.next();
                if ( e instanceof NodeEdge ) {
                    xml.append(((NodeEdge) e).storeInfo( hidden )).append(LayoutTags.NL);
                } else {
                    xml.append(e.storePlacementInfo( hidden )).append(LayoutTags.NL);
                }
            }
        }
        
        xml.append(LayoutTags.NODE_C).append(LayoutTags.NL);
        return xml.toString();
    }

}