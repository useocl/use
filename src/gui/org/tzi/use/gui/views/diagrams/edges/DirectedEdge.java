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


package org.tzi.use.gui.views.diagrams.edges;

import java.awt.Graphics;
import java.lang.reflect.Constructor;

import org.tzi.use.gui.views.diagrams.util.DirectedGraphicFactory;
import org.tzi.use.gui.views.diagrams.util.I_DirectedGraphic;
import org.tzi.use.gui.views.diagrams.util.I_DirectedLine;
import org.tzi.use.gui.views.diagrams.util.Util;

/**
 * General type of org.tzi.use.gui.views.edges consisting of a head and a tail.
 * Can be used to represent UML standard org.tzi.use.gui.views.edges.
 */
public class DirectedEdge implements I_DirectedEdge {
    private I_DirectedGraphic fHead;
    private I_DirectedGraphic fTail;
    private I_DirectedLine fTailLine;
    
    /**
     * Standard constructor for DirectedEdge
     * 
     * @param head
     *            head of the edge
     * @param tailType
     *            type of the edge tail as class
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @throws Exception
     *             thrown if an edge of the given type cannot be instantiated
     */
    public DirectedEdge( final I_DirectedGraphic head, final Class<?> tailType,
                         final int sourceX, final int sourceY, final int targetX,
                         final int targetY ) throws Exception {
        fHead = head;
        final double gradientAngle = Util.calculateAngleBetween( sourceX,
                                                                 sourceY,
                                                                 targetX,
                                                                 targetY );
        createTailLine( head.calculateWidth(), tailType, sourceX, sourceY, targetX,
                    targetY, gradientAngle );
        createHead( head, targetX, targetY, gradientAngle );
    }
    
    
    /**
     * Constructor for DirectedEdge with an specific head and tail of the edge.
     * 
     * @param head
     *            head of the edge
     * @param tail
     *            tail of the edge
     * @param tailType
     *            type of the edge tail as class
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @throws Exception
     *             thrown if an edge of the given type cannot be instantiated
     */
    public DirectedEdge( final I_DirectedGraphic head,
                         final I_DirectedGraphic tail, final Class<?> tailType,
                         final int sourceX, final int sourceY, final int targetX,
                         final int targetY ) throws Exception {
        fHead = head;
        fTail = tail;
        final double gradientAngleHead = Util.calculateAngleBetween( sourceX,
                                                                     sourceY,
                                                                     targetX,
                                                                     targetY );
        final double gradientAngleTail = Util.calculateAngleBetween( targetX,
                                                                     targetY,
                                                                     sourceX,
                                                                     sourceY );
        createTailLine( head.calculateWidth(), tailType, sourceX, sourceY, targetX,
                    targetY, gradientAngleHead );
        createHead( head, targetX, targetY, gradientAngleHead );
        createTail( tail, sourceX, sourceY, gradientAngleTail );
    }
    
    
    /**
     * Constructor for DirectedEdge with customized length of dashes. Only to be
     * used with edge types that support dashes.
     * 
     * @param head
     *            head of the edge
     * @param tailType
     *            type of the edge tail as class
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
     * @throws Exception
     *             thrown if an edge of the given type cannot be instantiated
     */
    public DirectedEdge( final I_DirectedGraphic head, final Class<?> tailType,
                         final int sourceX, final int sourceY, final int targetX,
                         final int targetY, final int fragmentLength,
                         final int intersectionLength ) throws Exception {
        fHead = head;
        final double gradientAngle = Util.calculateAngleBetween( sourceX,
                                                                 sourceY,
                                                                 targetX,
                                                                 targetY );
        createTailLine( head.calculateWidth(), tailType, sourceX, sourceY, targetX,
                    targetY, gradientAngle, fragmentLength, intersectionLength );
        createHead( head, targetX, targetY, gradientAngle );
        
    }
    
    /**
     * Getter for the head of the edge
     * 
     * @return head of the edge
     */
    public I_DirectedGraphic getHead() {
        return fHead;
    }
    
    /**
     * Getter for the tail of the edge
     * 
     * @return tail of the edge
     */
    public I_DirectedLine getTailLine() {
        return fTailLine;
    }
    
    /**
     * Draws the edge into the given graphic
     * 
     * @param graphic
     *            graphic to draw into
     * @return the edge
     */
    public I_DirectedEdge draw( final Graphics graphic ) {
        fHead.draw( graphic );
        fTailLine.draw( graphic );
        if ( fTail != null ) {
            fTail.draw( graphic );
        }
        return this;
    }
    
    /**
     * Calculates the distance between the given coordinates
     * 
     * @param sourceX
     *            X coordinate of source point
     * @param sourceY
     *            Y coordinate of source point
     * @param targetX
     *            X coordinate of target point
     * @param targetY
     *            Y coordinate of target point
     * @return distance
     */
    public double calculateEdgeLength( final int sourceX, final int sourceY,
                                       final int targetX, final int targetY ) {
        return Util.calculateDistance( sourceX, sourceY, targetX, targetY );
    }
    
    int calculateTailWidth( final int sourceX, final int sourceY,
                            final int targetX, final int targetY,
                            final double headWidth ) {
        if ( getHead().equals( DirectedGraphicFactory.ARROW_HEAD ) ) {
            return (int) Math.round( calculateEdgeLength( sourceX, sourceY,
                                                          targetX, targetY ) );
        } 
        return (int) Math.round( calculateEdgeLength( sourceX, sourceY,
                                                      targetX, targetY )
                                                      - headWidth );
        
    }
    
    private void createTailLine( final double headWidth, final Class<?> tailType,
                                 final int sourceX, final int sourceY,
                                 final int targetX, final int targetY,
                                 final double gradientAngle,
                                 final int fragmentLength,
                                 final int intersectionLength ) throws Exception {
        final Class<?>[] parameterTypes = { double.class, double.class,
                                         double.class, double.class, int.class,
                                         int.class };
        final int tailWidth = calculateTailWidth( sourceX, sourceY, targetX,
                                                  targetY, headWidth );
        final Object[] parameters = { sourceX,
                                      sourceY,
                                      sourceX + tailWidth,
                                      sourceY,
                                      fragmentLength,
                                      intersectionLength };
        createTailLineAuxiliary( tailType, parameterTypes, parameters,
                                 gradientAngle );
    }
    
    private void createTailLine( final double headWidth, final Class<?> tailType,
                                 final int sourceX, final int sourceY,
                                 final int targetX, final int targetY,
                                 final double gradientAngle ) throws Exception {
        final Class<?>[] parameterTypes = { double.class, double.class,
                                         double.class, double.class };
        final int tailWidth = calculateTailWidth( sourceX, sourceY, targetX,
                                                  targetY, headWidth );
        final Object[] parameters = { Double.valueOf( sourceX ),
                                      Double.valueOf( sourceY ),
                                      Double.valueOf( sourceX + tailWidth ),
                                      Double.valueOf( sourceY ) };
        createTailLineAuxiliary( tailType, parameterTypes, parameters,
                                 gradientAngle );
    }
    
    private void createTailLineAuxiliary( final Class<?> tailType,
                                          final Class<?>[] parameterTypes,
                                          final Object[] parameters,
                                          final double gradientAngle )
    throws Exception {
        final Constructor<?> constructor = tailType
        .getConstructor( parameterTypes );
        final I_DirectedLine auxiliaryLine = (I_DirectedLine) constructor
        .newInstance( parameters );
        fTailLine = auxiliaryLine.rotateAroundSourcePoint( gradientAngle );
    }
    
    private void createHead( final I_DirectedGraphic head, final int targetX,
                             final int targetY, final double gradientAngle ) {
        final I_DirectedGraphic translatedHead = head
        .translatePeakPointTo( targetX, targetY );
        fHead = translatedHead.rotateAroundPeakPoint( gradientAngle );
    }
    
    private void createTail( final I_DirectedGraphic tail, final int x,
                             final int y, final double gradientAngle ) {
        final I_DirectedGraphic translatedTail = 
            tail.translatePeakPointTo( x, y );
        fTail = translatedTail.rotateAroundPeakPoint( gradientAngle );
    }
}