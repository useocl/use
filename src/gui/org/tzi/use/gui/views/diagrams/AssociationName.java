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
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.views.diagrams.objectdiagram.ObjDiagramOptions;
import org.tzi.use.uml.mm.MAssociation;

/**
 * Represents a association name node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class AssociationName extends EdgeProperty {
    private int fLabelWidth;
    private List fConnectedNodes;
    
    AssociationName( String name, NodeBase source, NodeBase target,
                     int x1, int y1, int x2, int y2, 
                     DiagramOptions opt, EdgeBase edge, MAssociation assoc ) {
        fName = name;
        fSource = source;
        fTarget = target;
        fAssoc = assoc;
        fOpt = opt;
        fEdge = edge;
        fX_SourceEdgePoint = x1;
        fY_SourceEdgePoint = y1;
        fX_TargetEdgePoint = x2;
        fY_TargetEdgePoint = y2;
    }
    
    AssociationName( String name, List connectedNodes, DiagramOptions opt,
                     NodeBase source, MAssociation assoc ) {
        fName = name;
        fSource = source;
        fAssoc = assoc;
        fConnectedNodes = connectedNodes;
        fOpt = opt;
    }
    
    public String name() {
        return fName;
    }
    
    /**
     * Draws a association name on a reflexive edge.
     */
    public void drawEdgePropertyOnReflexiveEdge( Graphics g, FontMetrics fm,
                                                 int maxWidth, int maxHeight ) {
        setRectangleSize( g );
        
        setColor( g );
        
        fLabelWidth = fm.stringWidth( fName );
        if ( isSelected() ) {
            movingEdgeProperty( g );
        } else {
            // has the user moved the association name use the user
            // defined position.
            if ( !isUserDefined() ) {
                int labelHeight = fm.getHeight();
                
                // simple approximation of association name placement
                setX( fX_SourceEdgePoint + ( maxWidth / 2 - fLabelWidth / 2 ) );
                setY( fY_SourceEdgePoint - maxHeight - 4 );
                
                if ( fX_SourceEdgePoint < fSource.x() ) {
                    setX( fX_SourceEdgePoint - fLabelWidth 
                          - ( maxWidth / 2 - fLabelWidth / 2 ) );
                }
                if ( fY_SourceEdgePoint > fSource.y() ) {
                    setY( fY_SourceEdgePoint + maxHeight + labelHeight - 4 );
                }
                            
            } else {
                moveEdgePropertyDynamicaly();
            }
        }
        g.drawString( fName, (int) x(), (int) y() );

        // underline association name
        if ( fEdge.isUnderlinedLabel() ) {
            g.drawLine( (int) x(), (int) y()+1, (int) x()+fLabelWidth,
                        (int) y()+1 );       
        }
        fLoadingLayout = false;
        resetColor( g );
    }
    
    /**
     * Draws a association name on a binary edge.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        setRectangleSize( g );
        
        setColor( g );
        
        fLabelWidth = fm.stringWidth( fName );
        if ( isSelected() ) {
            movingEdgeProperty( g );
        } else {
            // has the user moved the association name use the user
            // defined position.
            if ( !isUserDefined() ) {
                // simple approximation of association name placement
                setX( fX_SourceEdgePoint
                      + ( fX_TargetEdgePoint - fX_SourceEdgePoint ) / 2
                      - fLabelWidth / 2 );
                
                
//                setY( fY_SourceEdgePoint
//                      + ( ( fY_TargetEdgePoint - fY_SourceEdgePoint ) / 2 )
//                      + ( labelHeight / 2 ) - 3 );
    
//              if ( isSideBySide() ) {
                    setY( fY_SourceEdgePoint
                          + ( fY_TargetEdgePoint - fY_SourceEdgePoint ) / 2 - 4 );
//                }
                    
            } else {
                moveEdgePropertyDynamicaly();
            }
        }
        
        g.drawString( fName, (int) x(), (int) y() );
        // underline association name
        if ( fEdge.isUnderlinedLabel() ) {
            g.drawLine( (int) x(), (int) y()+1, 
                        (int) x()+fLabelWidth, (int) y()+1 );
        }
        fLoadingLayout = false;
        resetColor( g );
    }

    /**
     * Draws a association name above a diamond node.
     */
    public void drawOnDiamondNode( Graphics g, FontMetrics fm ) {
        setRectangleSize( g );
        
        setColor( g );
        
        fLabelWidth = fm.stringWidth( fName );
        
        if ( isSelected() ) {
            movingEdgeProperty( g );
        } else {
            // has the user moved the association name use the user
            // defined position.
            if ( !isUserDefined() ) {
                double x = fSource.x() - fLabelWidth / 2;
                setX( x );
                setY( fSource.y() - 14 );
            }else {
                moveEdgePropertyDynamicalyOnDiamondNode();
            }
        }
        
        g.drawString( fName, (int) x(), (int) y() );
        
        if ( fOpt instanceof ObjDiagramOptions ) {
            g.drawLine( (int) x(), (int) y()+2, (int) x()+fLabelWidth,
                        (int) y()+2 );    
        }    
        fLoadingLayout = false;
        resetColor( g );
    }
    
    /**
     * Moves the edge property dynamicaly on the user defined position
     * if the diamond node is moved (allways the source node).
     */
    private void moveEdgePropertyDynamicalyOnDiamondNode() {
        if ( !fLoadingLayout ) {   
            Point2D.Double p = vectorBetweenPositions( fSource.x(), fSource.y(),
                                                       ((DiamondNode) fSource).oldX(),
                                                       ((DiamondNode) fSource).oldY() );
            setX( fX_UserDefined + p.x );
            setY( fY_UserDefined + p.y );
            fLoadingLayout = false;
        }
        fX_UserDefined = x();
        fY_UserDefined = y();
    }
    
    /**
     * Moves the edge property dynamicaly on the user defined position
     * if the source or target node is moved.
     */
    void moveEdgePropertyDynamicaly() {
        if ( !fLoadingLayout ) {
            double x_old = fX_SourceEdgePoint_old 
            + ( fX_TargetEdgePoint_old - fX_SourceEdgePoint_old ) / 2 
            - fLabelWidth/2 ;
            double y_old = fY_SourceEdgePoint_old 
            + ( fY_TargetEdgePoint_old - fY_SourceEdgePoint_old ) / 2 
            - 4 ;
            
            Point2D.Double v_old = vectorBetweenPositions( fX_UserDefined, 
                                                           fY_UserDefined,
                                                           x_old, y_old );
            
            double x = fX_SourceEdgePoint
            + ( fX_TargetEdgePoint - fX_SourceEdgePoint ) / 2 
            - fLabelWidth/2 ;
            double y = fY_SourceEdgePoint 
            + ( fY_TargetEdgePoint - fY_SourceEdgePoint ) / 2 
            - 4 ;
            setX( x + v_old.x );
            setY( y + v_old.y );
            fLoadingLayout = false;
        }
        fX_UserDefined = x();
        fY_UserDefined = y();
    }
 
    public String ident() {
        String connectedNodes = "";
        if ( fConnectedNodes != null && !fConnectedNodes.isEmpty() ) {
            Iterator it = fConnectedNodes.iterator();
            while ( it.hasNext() ) {
                connectedNodes += (String) it.next() + "_";
            }
        } else {
            connectedNodes = fSource.name() + "_" + fTarget.name();
        }
        return "AssociationName." + fAssoc.name() + "_"
               + connectedNodes + "." + fName;
    }

}
