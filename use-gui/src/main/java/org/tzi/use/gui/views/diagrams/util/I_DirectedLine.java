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


package org.tzi.use.gui.views.diagrams.util;

import java.awt.Graphics;

/**
 * Represents a directed line
 */
public interface I_DirectedLine {

    /**
     * Translates the line by the specified deltas
     *
     * @param deltaX translation delta in x direction
     * @param deltaY translation delta in y direction
     * @return line
     */
    I_DirectedLine translateBy(final int deltaX, final int deltaY);

    /**
     * Getter for the x coordinate of the source point as int
     *
     * @return x coordinate of the source point as int
     */
    int getRoundedSourceX();

    /**
     * Getter for the y coordinate of the source point as int
     *
     * @return y coordinate of the source point as int
     */
    int getRoundedSourceY();

    /**
     * Getter for the x coordinate of the target point as int
     *
     * @return x coordinate of the target point as int
     */
    int getRoundedTargetX();

    /**
     * Getter for the y coordinate of the target point as int
     *
     * @return y coordinate of the target point as int
     */
    int getRoundedTargetY();

    /**
     * Getter for the x coordinate of the source point
     *
     * @return x coordinate of the source point
     */
    double getSourceX();

    /**
     * Getter for the y coordinate of the source point
     *
     * @return y coordinate of the source point
     */
    double getSourceY();

    /**
     * Getter for the x coordinate of the target point
     *
     * @return x coordinate of the target point
     */
    double getTargetX();

    /**
     * Getter for the y coordinate of the target point
     *
     * @return y coordinate of the target point
     */
    double getTargetY();

    /**
     * Calculates the distance between the given coordinates
     *
     * @return distance
     */
    double calculateLength();

    /**
     * Calculates the gradient angle of this line
     *
     * @return gradient angle
     */
    double calculateGradientAngle();

    /**
     * Tests if two lines are equal (rounded coordinates)
     *
     * @param line line to be compared with
     * @return true if the lines are equal, false otherwise
     */
    boolean compareEquals(final I_DirectedLine line);

    /**
     * Returns a copy of the line that is rotated counter-clockwise by the given rotation angle around source point
     *
     * @param rotationAngle in degrees
     * @return new rotated line
     */
    I_DirectedLine rotateAroundSourcePoint(final double rotationAngle);

    /**
     * Returns a copy of the line that is rotated counter-clockwise by the given rotation angle around target point
     *
     * @param rotationAngle in degrees
     * @return new rotated line
     */
    I_DirectedLine rotateAroundTargetPoint(final double rotationAngle);

    /**
     * Returns a copy of the line that is rotated counter-clockwise by the given rotation angle around the given point
     *
     * @param rotationPointX x coordinate of the rotation point
     * @param rotationPointY y coordinate of the rotation point
     * @param rotationAngle  in degrees
     * @return new rotated line
     */
    I_DirectedLine rotateAroundAnyPoint(final double rotationPointX, final double rotationPointY,
                                        final double rotationAngle);

    /**
     * Translates the line by its source point to the specified point
     *
     * @param newX x coordinate of the new source point
     * @param newY y coordinate of the new source point
     * @return line
     */
    I_DirectedLine translateSourcePointTo(final int newX, final int newY);

    /**
     * Draws the line into the given graphic
     *
     * @param graphic to be drawn into
     * @return line
     */
    I_DirectedLine draw(final Graphics graphic);

    /**
     * Rotates the line counter-clockwise by the given rotation angle which must be between 0 and 90 degrees
     *
     * @param angleInQuadrant in degrees
     */
    I_DirectedLine rotateInQuadrant(final double angleInQuadrant);

    /**
     * Template method to create subclass instances
     *
     * @param sourceX
     * @param sourceY
     * @param targetX
     * @param targetY
     */
    I_DirectedLine doCreateDirectedLine(final double sourceX, final double sourceY,
                                        final double targetX, final double targetY);

}
