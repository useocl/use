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


package org.tzi.use.gui.views.diagrams.edges;

import java.awt.Graphics;

import org.tzi.use.gui.views.diagrams.util.DashedDirectedLine;
import org.tzi.use.gui.views.diagrams.util.DirectedGraphicFactory;
import org.tzi.use.gui.views.diagrams.util.SolidDirectedLine;

/**
 * This class supplies create and draw methods for common 
 * UML org.tzi.use.gui.views.edges.
 */
public class DirectedEdgeFactory {

    private DirectedEdgeFactory() {
    }
    
    /**
     * Creates an UML inheritance edge between the given coordinates
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML inheritance edge
     * @throws Exception
     */
    public static I_DirectedEdge createInheritance( final int sourceX,
                                                    final int sourceY,
                                                    final int targetX,
                                                    final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.TRIANGLE,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML implementation edge between the given coordinates with
     * default length of dashes
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML implementation edge
     * @throws Exception
     */
    public static I_DirectedEdge createImplementation( final int sourceX,
                                                       final int sourceY,
                                                       final int targetX,
                                                       final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.TRIANGLE,
                                 DashedDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML implementation edge between the given coordinates with
     * customized length of dashes
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @param fragmentLength
     *            length of a single dash
     * @param intersectionLength
     *            intersection between two dashes
     * @return UML implementation edge
     * @throws Exception
     */
    public static I_DirectedEdge createImplementation(
                                                      final int sourceX,
                                                      final int sourceY,
                                                      final int targetX,
                                                      final int targetY,
                                                      final int fragmentLength,
                                                      final int intersectionLength )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.TRIANGLE,
                                 DashedDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY, fragmentLength,
                                 intersectionLength );
    }
    
    /**
     * Creates an UML aggregation edge between the given coordinates
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML aggregation edge
     * @throws Exception
     */
    public static I_DirectedEdge createAggregation( final int sourceX,
                                                    final int sourceY,
                                                    final int targetX,
                                                    final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.HOLLOW_DIAMOND,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML composition edge between the given coordinates
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML composition edge
     * @throws Exception
     */
    public static I_DirectedEdge createComposition( final int sourceX,
                                                    final int sourceY,
                                                    final int targetX,
                                                    final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.FILLED_DIAMOND,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML dependency edge between the given coordinates with default
     * length of dashes
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML dependency edge
     * @throws Exception
     */
    public static I_DirectedEdge createDependency( final int sourceX,
                                                   final int sourceY,
                                                   final int targetX,
                                                   final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.ARROW_HEAD,
                                 DashedDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML dependency edge between the given coordinates with
     * customized length of dashes
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @param fragmentLength
     *            length of a single dash
     * @param intersectionLength
     *            intersection between two dashes
     * @return UML dependency edge
     * @throws Exception
     */
    public static I_DirectedEdge createDependency( final int sourceX,
                                                   final int sourceY,
                                                   final int targetX,
                                                   final int targetY,
                                                   final int fragmentLength,
                                                   final int intersectionLength )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.ARROW_HEAD,
                                 DashedDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY, fragmentLength,
                                 intersectionLength );
    }
    
    /**
     * Creates an UML association edge between the given coordinates
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML association edge
     * @throws Exception
     */
    public static I_DirectedEdge createAssociation( final int sourceX,
                                                    final int sourceY,
                                                    final int targetX,
                                                    final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.EMPTY_HEAD,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML dashed edge between the given coordinates
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML dashed edge
     * @throws Exception
     */
    public static I_DirectedEdge createDashedEdge( final int sourceX,
                                                   final int sourceY,
                                                   final int targetX,
                                                   final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.EMPTY_HEAD,
                                 DashedDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    /**
     * Creates an UML dashed edge between the given coordinates with customized
     * length of dashes
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @param fragmentLength
     *            length of a single dash
     * @param intersectionLength
     *            intersection between two dashes
     * @return UML dashed edge
     * @throws Exception
     */
    public static I_DirectedEdge createDashedEdge( final int sourceX,
                                                   final int sourceY,
                                                   final int targetX,
                                                   final int targetY,
                                                   final int fragmentLength,
                                                   final int intersectionLength )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.EMPTY_HEAD,
                                 DashedDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY, fragmentLength,
                                 intersectionLength );
    }
    
    /**
     * Creates an UML directed edge between the given coordinates.
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML directed edge
     * @throws Exception
     */
    public static I_DirectedEdge createDirectedEdge( final int sourceX,
                                                     final int sourceY,
                                                     final int targetX,
                                                     final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.ARROW_HEAD,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }
    
    
    /**
     * Creates an UML directed aggregation edge between the given coordinates.
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML directed edge
     * @throws Exception
     */
    public static I_DirectedEdge createDirectedAggregation( final int sourceX,
                                                            final int sourceY,
                                                            final int targetX,
                                                            final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.HOLLOW_DIAMOND,
                                 DirectedGraphicFactory.ARROW_HEAD,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }  
    
    /**
     * Creates an UML directed composition edge between the given coordinates.
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML directed edge
     * @throws Exception
     */
    public static I_DirectedEdge createDirectedComposition( final int sourceX,
                                                            final int sourceY,
                                                            final int targetX,
                                                            final int targetY )
    throws Exception {
        return new DirectedEdge( DirectedGraphicFactory.FILLED_DIAMOND,
                                 DirectedGraphicFactory.ARROW_HEAD,
                                 SolidDirectedLine.class, sourceX, sourceY,
                                 targetX, targetY );
    }  
    
    
    
    /**
     * Creates and draws an UML inheritance edge between the given coordinates
     * into the given graphic
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML inheritance edge
     * @throws Exception
     */
    public static I_DirectedEdge drawInheritance( final Graphics graphic,
                                                  final int sourceX,
                                                  final int sourceY,
                                                  final int targetX,
                                                  final int targetY )
    throws Exception {
        return createInheritance( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML implementation edge between the given
     * coordinates into the given graphic with default length of dashes
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML implementation edge
     * @throws Exception
     */
    public static I_DirectedEdge drawImplementation( final Graphics graphic,
                                                     final int sourceX,
                                                     final int sourceY,
                                                     final int targetX,
                                                     final int targetY )
    throws Exception {
        return createImplementation( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML implementation edge between the given
     * coordinates into the given graphic with customized length of dashes
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @param fragmentLength
     *            length of a single dash
     * @param intersectionLength
     *            intersection between two dashes
     * @return UML implementation edge
     * @throws Exception
     */
    public static I_DirectedEdge drawImplementation( final Graphics graphic,
                                                     final int sourceX,
                                                     final int sourceY,
                                                     final int targetX,
                                                     final int targetY,
                                                     final int fragmentLength,
                                                     final int intersectionLength )
    throws Exception {
        return createImplementation( sourceX, sourceY, targetX, targetY,
                                     fragmentLength, intersectionLength )
                                     .draw(
                                           graphic );
    }
    
    /**
     * Creates and draws an UML aggregation edge between the given coordinates
     * into the given graphic
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML aggregation edge
     * @throws Exception
     */
    public static I_DirectedEdge drawAggregation( final Graphics graphic,
                                                  final int sourceX,
                                                  final int sourceY,
                                                  final int targetX,
                                                  final int targetY )
    throws Exception {
        return createAggregation( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML composition edge between the given coordinates
     * into the given graphic
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML composition edge
     * @throws Exception
     */
    public static I_DirectedEdge drawComposition( final Graphics graphic,
                                                  final int sourceX,
                                                  final int sourceY,
                                                  final int targetX,
                                                  final int targetY )
    throws Exception {
        return createComposition( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML dependency edge between the given coordinates
     * into the given graphic with default length of dashes
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML dependency edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDependency( final Graphics graphic,
                                                 final int sourceX,
                                                 final int sourceY,
                                                 final int targetX,
                                                 final int targetY )
    throws Exception {
        return createDependency( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML dependency edge between the given coordinates
     * into the given graphic with customized length of dashes
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @param fragmentLength
     *            length of a single dash
     * @param intersectionLength
     *            intersection between two dashes
     * @return UML dependency edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDependency( final Graphics graphic,
                                                 final int sourceX,
                                                 final int sourceY,
                                                 final int targetX,
                                                 final int targetY,
                                                 final int fragmentLength,
                                                 final int intersectionLength )
    throws Exception {
        return createDependency( sourceX, sourceY, targetX, targetY,
                                 fragmentLength, intersectionLength )
                                 .draw(
                                       graphic );
    }
    
    /**
     * Creates and draws an UML associtation edge between the given coordinates into the given graphic
     *
     * @param graphic graphic to draw into
     * @param sourceX X coordinate of source point
     * @param sourceY Y coordinate of source point
     * @param targetX X coordinate of target point
     * @param targetY Y coordinate of target point
     * @return UML associtation edge
     * @throws Exception
     */
    public static I_DirectedEdge drawAssociation( final Graphics graphic,
                                                  final int sourceX,
                                                  final int sourceY,
                                                  final int targetX,
                                                  final int targetY )
    throws Exception {
        return createAssociation( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML dashed edge between the given coordinates into the given graphic
     *
     * @param graphic graphic to draw into
     * @param sourceX X coordinate of source point
     * @param sourceY Y coordinate of source point
     * @param targetX X coordinate of target point
     * @param targetY Y coordinate of target point
     * @return UML dashed edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDashedEdge( final Graphics graphic,
                                                 final int sourceX,
                                                 final int sourceY,
                                                 final int targetX,
                                                 final int targetY )
    throws Exception {
        return createDashedEdge( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }
    
    /**
     * Creates and draws an UML dashed edge between the given coordinates into the given graphic with
     * customized length of dashes
     *
     * @param graphic graphic to draw into
     * @param sourceX X coordinate of source point
     * @param sourceY Y coordinate of source point
     * @param targetX X coordinate of target point
     * @param targetY Y coordinate of target point
     * @param fragmentLength length of a single dash
     * @param intersectionLength intersection between two dashes
     * @return UML dashed edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDashedEdge( final Graphics graphic,
                                                 final int sourceX,
                                                 final int sourceY,
                                                 final int targetX,
                                                 final int targetY,
                                                 final int fragmentLength,
                                                 final int intersectionLength )
    throws Exception {
        return createDashedEdge( sourceX, sourceY, targetX, targetY,
                                 fragmentLength, intersectionLength )
                                 .draw(
                                       graphic );
        
    }
    
    /**
     * Creates and draws an UML directed edge between the given coordinates into
     * the given graphic.
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML directed edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDirectedEdge( final Graphics graphic,
                                                   final int sourceX,
                                                   final int sourceY,
                                                   final int targetX,
                                                   final int targetY )
    throws Exception {
        return createDirectedEdge( sourceX, sourceY, targetX, targetY )
        .draw(
              graphic );
    }

    /**
     * Creates and draws an UML directed aggregation edge between the given 
     * coordinates into the given graphic.
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML directed edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDirectedAggregation( final Graphics graphic,
                                                          final int sourceX,
                                                          final int sourceY,
                                                          final int targetX,
                                                          final int targetY )
    throws Exception {
        return createDirectedAggregation( sourceX, sourceY, targetX, targetY )
               .draw(graphic );
    }

    /**
     * Creates and draws an UML directed composition edge between the given 
     * coordinates into the given graphic.
     * 
     * @param graphic
     *            graphic to draw into
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return UML directed edge
     * @throws Exception
     */
    public static I_DirectedEdge drawDirectedComposition( final Graphics graphic,
                                                          final int sourceX,
                                                          final int sourceY,
                                                          final int targetX,
                                                          final int targetY )
    throws Exception {
        return createDirectedComposition( sourceX, sourceY, targetX, targetY )
               .draw( graphic );
    }
}