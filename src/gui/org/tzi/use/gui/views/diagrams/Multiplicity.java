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

import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * Represents a Multiplicity node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class Multiplicity extends EdgeProperty {
    MAssociationEnd fAssocEnd;
    
    Multiplicity( MAssociationEnd assocEnd, NodeBase source, NodeBase target,
                  EdgeBase edge, int x1, int y1, int x2, int y2, 
                  DiagramOptions opt, int side ) {
        fAssocEnd = assocEnd;
        fName = fAssocEnd.multiplicity().toString();
        fSource = source;
        fTarget = target;
        fAssoc = fAssocEnd.association();
        fEdge = edge;
        fOpt = opt;
        fSide = side;
        fX_SourceEdgePoint = x1;
        fY_SourceEdgePoint = y1;
        fX_TargetEdgePoint = x2;
        fY_TargetEdgePoint = y2;
    }
    
    /**
     * Draws a multiplicity on a reflexive edge.
     */
    public void drawEdgePropertyOnReflexiveEdge( Graphics g, FontMetrics fm,
                                                 int maxHeight, int furthestX ) {
        setRectangleSize( g );
        
        setColor( g );
        
        if ( isSelected() ) {
            movingEdgeProperty( g );
        } else {
            // has the user moved the multiplicity use the user
            // defined position.
            if ( !isUserDefined() ) {
                // simple approximation of multiplicity placement
                int labelWidth = fm.stringWidth( fName );
                int labelHeight = fm.getHeight();
                final int spaceToEdge = 5;
                
                switch ( fSide ) {
                case SOURCE_SIDE:
                    if ( fX_SourceEdgePoint > fSource.x() ) {
                        setX( fX_SourceEdgePoint + spaceToEdge );    
                    } else {
                        setX( fX_SourceEdgePoint - labelWidth - spaceToEdge );
                    }
                    if ( fY_SourceEdgePoint < fSource.y() ) {
                        setY( fY_SourceEdgePoint - (maxHeight/2) + (labelHeight/3) );    
                    } else {
                        setY( fY_SourceEdgePoint + (maxHeight/2) + (labelHeight/3) );
                    }
                    break;
                case TARGET_SIDE:
                    int sY = (int) fY_SourceEdgePoint - maxHeight;
                    int tY = (int) fY_SourceEdgePoint + maxHeight;
                    
                    if ( fX_TargetEdgePoint > fTarget.x() ) {
                        setX( furthestX - labelWidth - spaceToEdge);    
                    } else {
                        setX( furthestX + spaceToEdge );
                    }
                    if ( fY_TargetEdgePoint < fTarget.y() ) {
                        setY( (fY_TargetEdgePoint 
                                - (( fY_TargetEdgePoint-sY )/2))
                                + (labelHeight/3) );    
                    } else {
                        setY( (fY_TargetEdgePoint 
                                - (( fY_TargetEdgePoint-tY )/2))
                                + (labelHeight/3) );
                    }
                    break;
                default:
                    break;
                }
            } else {
                moveEdgePropertyDynamicaly();
            }
        }
        g.drawString( fName, (int) x(), (int) y() );
        fLoadingLayout = false;
        resetColor( g );
    }
    
    /**
     * Draws a multiplicity on a binary edge.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        setRectangleSize( g );
        
        setColor( g );
        
        if ( isSelected() ) {
            movingEdgeProperty( g );
        } else {
            // has the user moved the multiplicity use the user
            // defined position.
            if ( !isUserDefined() ) {
                
                // simple approximation of multiplicity placement
                int fn1H = fSource.getHeight() / 2;
                if ( fY_TargetEdgePoint > fY_SourceEdgePoint ) {
                    setY( fY_SourceEdgePoint + fn1H + 15 );
                } else {
                    setY( fY_SourceEdgePoint - fn1H - 10 );
                }
                
                if ( fX_TargetEdgePoint > fX_SourceEdgePoint ) {
                    setX( fX_SourceEdgePoint - fm.stringWidth( fName ) - 2 );
                } else {
                    setX( fX_SourceEdgePoint + 2 );
                }
                
            } else {
                moveEdgePropertyDynamicaly();
            }
        }
        g.drawString( fName, (int) x(), (int) y() );
        fLoadingLayout = false;
        resetColor( g );
    }

}
