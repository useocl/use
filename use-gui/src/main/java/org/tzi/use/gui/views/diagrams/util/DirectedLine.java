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

import org.tzi.use.util.FloatUtil;

/**
 * Represents a directed line
 */
public abstract class DirectedLine implements I_DirectedLine {
    protected double sourceX;
    protected double sourceY;
    protected double targetX;
    protected double targetY;

    /**
     * Getter for the x coordinate of the source point
     *
     * @return x coordinate of the source point
     */
    public double getSourceX() {
        return sourceX;
    }

    /**
     * Getter for the y coordinate of the source point
     *
     * @return y coordinate of the source point
     */
    public double getSourceY() {
        return sourceY;
    }

    /**
     * Getter for the x coordinate of the target point
     *
     * @return x coordinate of the target point
     */
    public double getTargetX() {
        return targetX;
    }

    /**
     * Getter for the y coordinate of the target point
     *
     * @return y coordinate of the target point
     */
    public double getTargetY() {
        return targetY;
    }

    /**
     * Getter for the x coordinate of the source point as int
     *
     * @return x coordinate of the source point as int
     */
    public int getRoundedSourceX() {
        return (int) Math.round(sourceX);
    }

    /**
     * Getter for the y coordinate of the source point as int
     *
     * @return y coordinate of the source point as int
     */
    public int getRoundedSourceY() {
        return (int) Math.round(sourceY);
    }

    /**
     * Getter for the x coordinate of the target point as int
     *
     * @return x coordinate of the target point as int
     */
    public int getRoundedTargetX() {
        return (int) Math.round(targetX);
    }

    /**
     * Getter for the y coordinate of the target point as int
     *
     * @return y coordinate of the target point as int
     */
    public int getRoundedTargetY() {
        return (int) Math.round(targetY);
    }

    /**
     * Compares two I_DirectedLines by its contained lines 
     * (raises a class cast exception for objects of different types)
     *
     * @param obj object of type I_DirectedLine
     * @return true if objects equal, false otherwise
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
        	return true;
        } else if (obj instanceof I_DirectedLine) {
        	final I_DirectedLine line = (I_DirectedLine) obj;
            return hasSameClass(line) && hasSamePosition(line);
        } else {
        	return false;
        }
    }

    public int hashCode() {
        return (int)(sourceX + sourceY)*13;
    }
    

    /**
     * Returns the coordinates as a String
     *
     * @return coordinates
     */
    public String toString() {
        return "(" + Math.round(sourceX) + ", "
                + Math.round(sourceY) + ", "
                + Math.round(targetX) + ", "
                + Math.round(targetY) + ")";
    }

    /**
     * Calculates the distance between the given coordinates
     *
     * @return distance
     */
    public double calculateLength() {
        return Util.calculateDistance(sourceX, sourceY, targetX, targetY);
    }

    /**
     * Calculates the gradient angle of this line
     *
     * @return gradient angle
     */
    public double calculateGradientAngle() {
        return Util.calculateAngleBetween(sourceX, sourceY, targetX, targetY);
    }

    /**
     * Tests if two lines are equal (rounded coordinates)
     *
     * @param line line to be compared with
     * @return true if the lines are equal, false otherwise
     */
    public boolean compareEquals(final I_DirectedLine line) {
        return Math.round(sourceX) == getRoundedSourceX()
                && Math.round(sourceY) == getRoundedSourceY()
                && Math.round(targetX) == getRoundedTargetX()
                && Math.round(targetY) == getRoundedTargetY();
    }

    /**
     * Returns a copy of the line that is rotated counter-clockwise by the given rotation angle around source point
     *
     * @param rotationAngle in degrees
     * @return new rotated line
     */
    public I_DirectedLine rotateAroundSourcePoint(final double rotationAngle) {
        final double absoluteAngle = rotationAngle + calculateGradientAngle();
        final double trimmedAngle = absoluteAngle % 360.0;
        final double angleInQuadrant = trimmedAngle % 90.0;
        final I_DirectedLine flattenedLine = doCreateDirectedLine(sourceX, sourceY, 
                                                                  sourceX + calculateLength(), 
                                                                  sourceY);
        final I_DirectedLine auxiliaryLine = flattenedLine.rotateInQuadrant(angleInQuadrant);

        final double remainingAngle = trimmedAngle - angleInQuadrant;
        return rotateInRightAngleMultiples(auxiliaryLine, remainingAngle);
    }

    /**
     * Rotates the line counter-clockwise by the given rotation 
     * angle which must be between 0 and 90 degrees
     *
     * @param rotationAngle in degrees
     */
    public I_DirectedLine rotateInQuadrant(final double rotationAngle) {
        final double length = calculateLength();
        final double deltaX = Math.cos(Math.toRadians(rotationAngle)) * length;
        final double deltaY = Math.sin(Math.toRadians(rotationAngle)) * length;
        return doCreateDirectedLine(sourceX, sourceY, sourceX + deltaX, 
                                    sourceY + deltaY);
    }

    /**
     * Template method to create subclass instances
     *
     * @param sourceX
     * @param sourceY
     * @param targetX
     * @param targetY
     */
    public abstract I_DirectedLine doCreateDirectedLine(final double sourceX, final double sourceY,
                                                        final double targetX, final double targetY);

    /**
     * Returns a copy of the line that is rotated counter-clockwise by the given rotation angle around target point
     *
     * @param rotationAngle in degrees
     * @return new rotated line
     */
    public I_DirectedLine rotateAroundTargetPoint(final double rotationAngle) {
        final I_DirectedLine auxiliaryLine = doCreateDirectedLine(targetX, targetY, sourceX, sourceY);
        final I_DirectedLine rotatedAuxiliaryLine = auxiliaryLine.rotateAroundSourcePoint(rotationAngle);
        return doCreateDirectedLine(rotatedAuxiliaryLine.getTargetX(),
                rotatedAuxiliaryLine.getTargetY(),
                rotatedAuxiliaryLine.getSourceX(),
                rotatedAuxiliaryLine.getSourceY());
    }

    /**
     * Returns a copy of the line that is rotated counter-clockwise 
     * by the given rotation angle around the given point
     *
     * @param rotationPointX x coordinate of the rotation point
     * @param rotationPointY y coordinate of the rotation point
     * @param rotationAngle  in degrees
     * @return new rotated line
     */
    public I_DirectedLine rotateAroundAnyPoint(final double rotationPointX, final double rotationPointY,
                                               final double rotationAngle) {
        final I_DirectedLine auxiliarySourceLine = 
            doCreateDirectedLine(rotationPointX, rotationPointY, sourceX, sourceY);
        final I_DirectedLine auxiliaryTargetLine = 
            doCreateDirectedLine(rotationPointX, rotationPointY, targetX, targetY);

        final I_DirectedLine rotatedAuxiliarySourceLine = 
            auxiliarySourceLine.rotateAroundSourcePoint(rotationAngle);
        final I_DirectedLine rotatedAuxiliaryTargetLine = 
            auxiliaryTargetLine.rotateAroundSourcePoint(rotationAngle);

        return doCreateDirectedLine(rotatedAuxiliarySourceLine.getTargetX(),
                rotatedAuxiliarySourceLine.getTargetY(),
                rotatedAuxiliaryTargetLine.getTargetX(),
                rotatedAuxiliaryTargetLine.getTargetY());
    }

    /**
     * Translates the line by its source point to the specified point
     *
     * @param newX x coordinate of the new source point
     * @param newY y coordinate of the new source point
     * @return line
     */
    public I_DirectedLine translateSourcePointTo(final int newX, final int newY) {
        final double width = targetX - sourceX;
        final double height = targetY - sourceY;
        return doCreateDirectedLine(newX, newY, newX + width, newY + height);
    }

    /**
     * Translates the line by the specified deltas
     *
     * @param deltaX translation delta in x direction
     * @param deltaY translation delta in y direction
     * @return line
     */
    public I_DirectedLine translateBy(final int deltaX, final int deltaY) {
        return doCreateDirectedLine(sourceX + deltaX, sourceY + deltaY, 
                                    targetX + deltaX, targetY + deltaY);
    }

    private boolean hasSamePosition(final I_DirectedLine line) {
		return (FloatUtil.equals(sourceX, line.getSourceX())
				&& FloatUtil.equals(sourceY, line.getSourceY())
				&& FloatUtil.equals(targetX, line.getTargetX())
				&& FloatUtil.equals(targetY, line.getTargetY()));
    }

    private boolean hasSameClass(final I_DirectedLine line) {
        return this.getClass().equals(line.getClass());
    }

    private I_DirectedLine rotateInRightAngleMultiples(final I_DirectedLine auxiliaryLine, 
                                                       final double remainingAngle) {
        final double height = auxiliaryLine.getTargetY() - auxiliaryLine.getSourceY();
        final double width = auxiliaryLine.getTargetX() - auxiliaryLine.getSourceX();
        if (FloatUtil.equals(remainingAngle, 270.0d)) {
            return doCreateDirectedLine(sourceX, sourceY, sourceX + height, sourceY - width);
        }
        if (FloatUtil.equals(remainingAngle, 180.0d)) {
            return doCreateDirectedLine(sourceX, sourceY, sourceX - width, sourceY - height);
        }
        if (FloatUtil.equals(remainingAngle, 90.0d)) {
            return doCreateDirectedLine(sourceX, sourceY, sourceX - height, sourceY + width);
        }
        return auxiliaryLine;
    }
}