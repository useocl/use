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
import java.awt.geom.Point2D;
import java.util.Iterator;

import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * Draws the line between the rectangle of the associationclass/objectlink 
 * and the corresponding classes/objects.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class NodeEdge extends BinaryEdge {
    private Object fNode;  
    private double fX_old;
    private double fY_old;
    private double fX_con_old;
    private double fY_con_old;
    private boolean fFirstCall = true;
    /**
     * The NodeBase of instance ClassNode or ObjectNode displaying 
     * the class part of the associationclass/objectlink.
     */
    private NodeBase fNodeEdgeNode; 
    
    /**
     * Use this constructor if it is a binary associationclass/objectlink.
     */
    public NodeEdge( String label, Object source, Object target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     Object node, NodeBase nodeEdgeNode,
                     DiagramView diagram, MAssociation assoc ) {
        super( label, source, target, sourceEnd, targetEnd, diagram, assoc );
        initNodeEdge( node, nodeEdgeNode, label );
    }

    /**
     * Use this constructor if it is an t-nary associationclass/objectlink.
     */
    public NodeEdge( String label, Object source, Object target,
                     Object node, NodeBase nodeEdgeNode,
                     DiagramView diagram, MAssociation assoc ) {
        super( label, source, target, diagram, assoc );
        initNodeEdge( node, nodeEdgeNode, label );
    }
    
    /**
     * Initializes this NodeEdge.
     */
    private void initNodeEdge( Object node, NodeBase nodeEdgeNode,
                               String label ) {
        fNode = node;
        fNodeEdgeNode = nodeEdgeNode;
        Point2D conPoint = calcConnectionPoint( fX1, fY1, fX2, fY2 );
        Point2D nep = getIntersectionCoordinate( fNodeEdgeNode, 
                                                 (int) fNodeEdgeNode.x(),
                                                 (int) fNodeEdgeNode.y(), 
                                                 (int) conPoint.getX(),
                                                 (int) conPoint.getY() );
        
        fNENode = new NodeOnEdge( nep.getX(), nep.getY(),
                                  fSource, fTarget, this,
                                  fNodesOnEdgeCounter++,
                                  EdgeBase.ASSOC_CLASS, 
                                  label, fOpt );
        fConNode = new NodeOnEdge( conPoint.getX(), conPoint.getY(),
                                   fSource, fTarget, this,
                                   fNodesOnEdgeCounter++,
                                   EdgeBase.ASSOC_CLASS_CON,
                                   label, fOpt );
        
        fConNode.setWasMoved( false );
        if ( isReflexive() ) {
            fNodesOnEdge.add( fNodesOnEdge.indexOf( fRefNode3 ), fConNode );
        } else {
            fNodesOnEdge.add( 1, fConNode );
        }
        reIDNodes();
    }
    
    
    /**
     * Draws this nodeedge.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        drawNodeEdge( g );
        if ( !( fNode instanceof DiamondNode ) ) {
            drawBinaryEdge( g, fm );
        } else {
            ( (DiamondNode) fNode ).draw( g, fm );
        }
        
    }

    /**
     * Draws the dashed line starting at the mid point of the solid line.
     * @param g The graphics object the dashed line is drawn into.
     */
    private void drawNodeEdge( Graphics g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        update();
        
        try {
            if ( fNode instanceof DiamondNode ) {
                DiamondNode diamond = (DiamondNode) fNode;
                DirectedEdgeFactory.drawDashedEdge( g, (int) diamond.x(), 
                                                    (int) diamond.y(), 
                                                    (int) fNodeEdgeNode.x(), 
                                                    (int) fNodeEdgeNode.y() );
                
            } else {
                DirectedEdgeFactory.drawDashedEdge( g, 
                                                    (int) fConNode.x(), 
                                                    (int) fConNode.y(), 
                                                    (int) fNodeEdgeNode.x(), 
                                                    (int) fNodeEdgeNode.y() );
            }
        } catch ( Exception ex ) {
            //ignore
        }
    }

    /**
     * Updates the connection point.
     */
    public void update() {
        if ( fNodeEdgeNode.isDragged() ) { // && fDiagram.isDoAutoLayout() ) {
            fFirstCall = false;
            fX_old = fNodeEdgeNode.x();
            fY_old = fNodeEdgeNode.y();
            fX_con_old = fConNode.x();
            fY_con_old = fConNode.y();
        } else if ( !fNodeEdgeNode.isDragged() ) { //&& fDiagram.isDoAutoLayout() ) {
            double x = fX_old - fX_con_old;
            double y = fY_old - fY_con_old;
            double newX = x + fConNode.x();
            double newY = y + fConNode.y();
            fNENode.setPosition( newX, newY );
            fNodeEdgeNode.setPosition( newX, newY );
        }
        
        if ( fFirstCall && !fNodeEdgeNode.isSelected() ) {
            final int maxSpace = 30;
            double newX = 0.0;
            double newY = 0.0;
            
            if ( fNodeEdgeNode.x() >= fConNode.x() ) {
                newX = fConNode.x() + maxSpace + fNodeEdgeNode.getWidth()/2;
                newY = fConNode.y();
            } else {
                newX = fConNode.x() - maxSpace - fNodeEdgeNode.getWidth()/2;
                newY = fConNode.y();
            }
            fNENode.setPosition( newX, newY );
            fNodeEdgeNode.setPosition( newX, newY );
        } 
        
        
        if ( isReflexive() ) {
            updateConnectionPoint( (int) fRefNode2.x(), (int) fRefNode2.y(), 
                                   (int) fRefNode3.x(), (int) fRefNode3.y() );
        } else {
            updateConnectionPoint( fX1, fY1, fX2, fY2 );
        }
    }
    
    /**
     * Calculates the new position of the connection point where the 
     * dashed line connectes to the solid line.
     * @param x1 x-coordinate of the first point.
     * @param y1 y-coordinate of the first point.
     * @param x2 x-coordinate of the second point.
     * @param y2 y-coordinate of the second point.
     */
    private void updateConnectionPoint( int x1, int y1, int x2, int y2 ) {
        if ( ( fNodesOnEdge.size() <= 3 && !fConNode.isSelected() 
               && !fConNode.wasMoved() && !isReflexive() )
              || ( fNodesOnEdge.size() <= 6 && !fConNode.isSelected() 
                   && !fConNode.wasMoved() && isReflexive() ) ) {
            Point2D conPoint = calcConnectionPoint( x1, y1, x2, y2 );
            Point2D nep = getIntersectionCoordinate( fNodeEdgeNode, 
                                                     (int) fNodeEdgeNode.x(),
                                                     (int) fNodeEdgeNode.y(), 
                                                     (int) conPoint.getX(),
                                                     (int) conPoint.getY() );
            fNENode.setX( nep.getX() );
            fNENode.setY( nep.getY() );
            fConNode.setX( conPoint.getX() );
            fConNode.setY( conPoint.getY() );
        }
    }

    /**
     * Calculates the mid point of two given points which form a straight line.
     * This calculated mid point marks the connection point, where the 
     * dashed line connectes to the solid line.
     * @param x1 x-coordinate of the first point.
     * @param y1 y-coordinate of the first point.
     * @param x2 x-coordinate of the second point.
     * @param y2 y-coordinate of the second point.
     */
    private Point2D calcConnectionPoint( int x1, int y1, int x2, int y2 ) {
        // calculating midpoint for x on the edge between obj1
        // and obj2
        if ( x1 > x2 ) {
            x1 = ( ( x1 - x2 ) / 2 ) + x2;
        } else if ( x2 > x1 ) {
            x1 = ( ( x2 - x1 ) / 2 ) + x1;
        }

        // calculating midpoint for y on the edge between obj1
        // and obj2
        if ( y1 > y2 ) {
            y1 = ( ( y1 - y2 ) / 2 ) + y2;
        } else if ( y2 > y1 ) {
            y1 = ( ( y2 - y1 ) / 2 ) + y1;
        }
        return new Point2D.Double( x1, y1 );
    }

    
    
    /**
     * Saves placement information about this edge iff this NodeEdge
     * participates in a tnary edge.
     * @param hidden If this edge should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public String storeInfo( boolean hidden ) {
        String xml = "";
        
        xml = LayoutTags.EDGE_O;
        if ( this instanceof NodeEdge ) {
            if ( isLink() ) {
                xml += " type=\"NodeEdge\" kind=\"link\">" + LayoutTags.NL;
            } else {
                xml += " type=\"NodeEdge\" kind=\"association\">" + LayoutTags.NL;
            }
        } 
        
        xml += LayoutTags.INDENT + LayoutTags.NAME_O + fEdgeName 
               + LayoutTags.NAME_C + LayoutTags.NL;

        if ( fSourceMultiplicity != null ) {
            xml += fSourceMultiplicity.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( fTargetMultiplicity != null ) {
            xml += fTargetMultiplicity.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( !fNodesOnEdge.isEmpty() ) {
            Iterator it = fNodesOnEdge.iterator();
            while ( it.hasNext() ) {
                NodeOnEdge n = (NodeOnEdge) it.next();
                xml += n.storePlacementInfo( hidden ) + LayoutTags.NL;
            }
        }
        xml += LayoutTags.INDENT + LayoutTags.HIDDEN_O + hidden 
               + LayoutTags.HIDDEN_C + LayoutTags.NL;
        
        xml += LayoutTags.EDGE_C + LayoutTags.NL;
        return xml;
    }
}