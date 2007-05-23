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
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * An edge representing a binary link.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class BinaryEdge extends EdgeBase {

    private String fLabel;
    
    public BinaryEdge( String label, Object source, Object target,
                       MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                       DiagramView diagram, MAssociation assoc ) {
        super( source, target, label, diagram, assoc );
        fLabel = label;
        
        fAssocName = new AssociationName( label, (NodeBase) source,
                                          (NodeBase) target, fX1, fY1, fX2, fY2,
                                          fOpt, this, assoc );
         
         fSourceRolename = new Rolename( sourceEnd, (NodeBase) source,
                                         (NodeBase) target, fX1, fY1, fX2, fY2,
                                         fOpt, Rolename.SOURCE_SIDE, this );
         fTargetRolename = new Rolename( targetEnd, (NodeBase) target,
                                         (NodeBase) source, fX2, fY2, fX1, fY1,
                                         fOpt, Rolename.TARGET_SIDE, this );
         
         fSourceMultiplicity = 
             new Multiplicity( sourceEnd, (NodeBase) source, (NodeBase) target, 
                               this, fX1, fY1, fX2, fY2, fOpt,
                               Multiplicity.SOURCE_SIDE );
         fTargetMultiplicity = 
             new Multiplicity( targetEnd, (NodeBase) target, (NodeBase) source,
                               this, fX2, fY2, fX1, fY1, fOpt,
                               Multiplicity.TARGET_SIDE );
        
        checkAndCreateReflexiveEdge( source.equals( target ) );
        
    }

    public BinaryEdge( String label, Object source, Object target,
                       DiagramView diagram, MAssociation assoc ) {
        super( source, target, label, diagram, assoc );
        fLabel = label;
        
        fAssocName = new AssociationName( label, (NodeBase) source,
                                         (NodeBase) target, fX1, fY1, fX2, fY2,
                                         fOpt, this, assoc );
        
        checkAndCreateReflexiveEdge( source.equals( target ) );
    }

    
    
    private void checkAndCreateReflexiveEdge( boolean isReflexive ) {
        if ( isReflexive ) {
            fRefNode1 = new NodeOnEdge( fX1, fY1,
                                        fSource, fTarget, this, 
                                        fNodesOnEdgeCounter++,
                                        EdgeBase.REFLEXIVE_1, 
                                        fAssoc.name(), fOpt );
            fRefNode2 = new NodeOnEdge( fX1, fY1,
                                        fSource, fTarget, this,
                                        fNodesOnEdgeCounter++,
                                        EdgeBase.REFLEXIVE_2, 
                                        fAssoc.name(), fOpt );
            fRefNode3 = new NodeOnEdge( fX1, fY1,
                                        fSource, fTarget, this,
                                        fNodesOnEdgeCounter++,
                                        EdgeBase.REFLEXIVE_3, 
                                        fAssoc.name(), fOpt );
            fNodesOnEdge.add( fNodesOnEdge.size()-1, fRefNode1 );
            fNodesOnEdge.add( fNodesOnEdge.size()-1, fRefNode2 );
            fNodesOnEdge.add( fNodesOnEdge.size()-1, fRefNode3 );
            reIDNodes();
        }
    }
        
    /**
     * Updates the positions of the points participating in the reflexive
     * edge. 
     */
    private void updateReflexiveNodes( FontMetrics fm ) {
        if ( ( fNodesOnEdge.size() <= 5 && !isReflexive() ) 
             || ( isReflexive() && fNodesOnEdge.size() <= 6 ) ) {
            List points = calcReflexivePoints( fm );
            Point2D sp = 
                getIntersectionCoordinate( fSource, 
                                           (int) ((Point2D) points.get( 0 )).getX(),
                                           (int) ((Point2D) points.get( 0 )).getY(),
                                           (int) ((Point2D) points.get( 1 )).getX(),
                                           (int) ((Point2D) points.get( 1 )).getY() );
            Point2D tp = 
                getIntersectionCoordinate( fTarget, 
                                           (int) ((Point2D) points.get( 4 )).getX(),
                                           (int) ((Point2D) points.get( 4 )).getY(),
                                           (int) ((Point2D) points.get( 3 )).getX(),
                                           (int) ((Point2D) points.get( 3 )).getY() );
            
            fSNode.setX( sp.getX() );
            fSNode.setY( sp.getY() );
            fRefNode1.setX( ((Point2D) points.get( 1 )).getX() );
            fRefNode1.setY( ((Point2D) points.get( 1 )).getY() );
            fRefNode2.setX( ((Point2D) points.get( 2 )).getX() );
            fRefNode2.setY( ((Point2D) points.get( 2 )).getY() );
            fRefNode3.setX( ((Point2D) points.get( 3 )).getX() );
            fRefNode3.setY( ((Point2D) points.get( 3 )).getY() );
            fTNode.setX( tp.getX() );
            fTNode.setY( tp.getY() );
        }
    }
    
    /**
     * Calculates the position of the points of a reflexive edge.
     * @param fm The font metrics is used to find the width and height
     * of the reflexive edge.
     * @return The poinst of the reflexive edge are returnd.
     */
    private List calcReflexivePoints( FontMetrics fm ) {
        List points = new ArrayList();
        
        Point2D.Double p1 = new Point2D.Double();
        Point2D.Double p2 = new Point2D.Double();
        Point2D.Double p3 = new Point2D.Double();
        Point2D.Double p4 = new Point2D.Double();
        Point2D.Double p5 = new Point2D.Double();
        
        points.add( p1 );
        points.add( p2 );
        points.add( p3 );
        points.add( p4 );
        points.add( p5 );
        
        int sX = fX1;
        int sY = fY1;
        int tX = fX2;
        int tY = fY2;
        
        int maxWidth = maxWidth( fm );
        int maxHeight = maxHeight( fm );
        
        if ( sX > fSource.x() ) {
            p1.x = sX;
            p2.x = sX;
            p3.x = sX + maxWidth;
            p4.x = sX + maxWidth;
            p5.x = tX;
        } else {
            p1.x = sX;
            p2.x = sX;
            p3.x = sX - maxWidth;
            p4.x = sX - maxWidth;
            p5.x = tX;
        }
        
        if ( sY < fSource.y() ) {
            p1.y = sY;
            p2.y = sY - maxHeight;
            p3.y = sY - maxHeight;
            p4.y = tY;
            p5.y = tY;
        } else {
            p1.y = sY;
            p2.y = sY + maxHeight;
            p3.y = sY + maxHeight;
            p4.y = tY;
            p5.y = tY;
        }

        return points;
    }
    
    /**
     * Calculates the max width of the label on a reflexive edge.
     * @param fm The font metrics which is used to calculate the width.
     * @return The max width of the label.
     */
    private int maxWidth( FontMetrics fm ) {
        final int LARGEST_WIDTH = 100;
        int labelWidth = fm.stringWidth( fLabel );
        double sWidth = fSource.getWidth();
        int maxWidth = Math.max( 30, Math.max( labelWidth, (int) sWidth/2 ));
        
        if ( maxWidth > LARGEST_WIDTH ) {
            maxWidth = LARGEST_WIDTH;
        }
        return maxWidth;
    }

    /**
     * Calculates the max height of the label on a reflexive edge.
     * @param fm The font metrics which is used to calculate the height.
     * @return The max height of the label.
     */
    private int maxHeight( FontMetrics fm ) {
        final int LARGEST_HEIGHT = 55;
        int labelHeight = fm.getHeight();
        double sHeight = fSource.getHeight();
        int maxHeight = Math.max( 30, Math.max( (int) (sHeight/3), labelHeight+4 ) );
        
        // is the height of the node equals or just a little bit bigger
        // as the labelHeight use the labelHeight.
        if ( sHeight <= labelHeight+6 ) {
            maxHeight = labelHeight+6;
        }
        if ( maxHeight > LARGEST_HEIGHT ) {
            maxHeight = LARGEST_HEIGHT;
        }
        return maxHeight;
    }
    
    /**
     * Draws a straightline edge between source and target node.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        // edge is not drawn directly in this method, because this
        // way drawBinaryEdge can be reused for drawing the edge
        // for a link object.
        drawBinaryEdge( g, fm );
    }

    /**
     * Draws a straightline edge between source and target node.
     */
    void drawBinaryEdge( Graphics g, FontMetrics fm ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        // draw the edge
        if ( isReflexive() ) {
            drawReflexiveEdge( g, fm );
        } else {
            drawEdge( g );

            // draw edge properties on the edge
            if ( isSelected() ) {
                g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
            } else {
                g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
            }

            if ( fOpt.isShowAssocNames() ) {
                fAssocName.draw( g, fm );
            }

            if ( fOpt.isShowRolenames() ) {
                fSourceRolename.draw( g, fm );
                fTargetRolename.draw( g, fm );
            }

            if ( fOpt.isShowMutliplicities() ) {
                fSourceMultiplicity.draw( g, fm );
                fTargetMultiplicity.draw( g, fm );
            }
        }
        g.setColor( fOpt.getEDGE_COLOR() );
    }

    /**
     * @param g
     * @param fm
     */
    private void drawReflexiveEdge( Graphics g, FontMetrics fm ) {
        updateReflexiveNodes( fm );
        drawEdge( g );
        
        int maxWidth = maxWidth( fm );
        int maxHeight = maxHeight( fm );
        
        g.setColor( fOpt.getEDGE_LABEL_COLOR() );
        if ( fOpt.isShowAssocNames() ) {
            fAssocName.drawEdgePropertyOnReflexiveEdge( g, fm, maxWidth, 
                                                        maxHeight );
        }

        if ( fOpt.isShowRolenames() ) {
            int furthestX = fX1 + maxWidth;
            if ( fX1 < fSource.x() ) {
                furthestX = fX1 - maxWidth;
            }
            fSourceRolename.drawEdgePropertyOnReflexiveEdge( g, fm, maxHeight,
                                                             furthestX );
            fTargetRolename.drawEdgePropertyOnReflexiveEdge( g, fm, maxHeight,
                                                             furthestX );
        }
        
        if ( fOpt.isShowMutliplicities() ) {
            int furthestX = fX1 + maxWidth;
            if ( fX1 < fSource.x() ) {
                furthestX = fX1 - maxWidth;
            }
            fSourceMultiplicity.drawEdgePropertyOnReflexiveEdge( g, fm, 
                                                                 maxHeight,
                                                                 furthestX );
            fTargetMultiplicity.drawEdgePropertyOnReflexiveEdge( g, fm,
                                                                 maxHeight,
                                                                 furthestX );
        }
    }
}
