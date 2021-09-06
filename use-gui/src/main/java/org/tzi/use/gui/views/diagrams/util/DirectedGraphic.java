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

import java.util.ArrayList;

/**
 * Abstract class representing a graphic that consists of lines
 *
 * @see I_DirectedLine
 */
public abstract class DirectedGraphic implements I_DirectedGraphic {
    protected ArrayList<I_DirectedLine> containedLines = new ArrayList<I_DirectedLine>();

    /**
     * Getter for the contained lines
     *
     * @return contained lines
     */
    public ArrayList<I_DirectedLine> getLines() {
        return containedLines;
    }

    /**
     * Getter for the x coordinate of the peak point
     *
     * @return x coordinate of the peak point
     */
    public int getPeakPointX() {
        final I_DirectedLine firstLine = containedLines.get(0);
        return firstLine.getRoundedSourceX();
    }

    /**
     * Getter for the y coordinate of the peak point
     *
     * @return y coordinate of the peak point
     */
    public int getPeakPointY() {
        final I_DirectedLine firstLine = containedLines.get(0);
        return firstLine.getRoundedSourceY();
    }

    /**
     * Rotates the graphic its peak point (i.e. the source point of the first contained line)
     *
     * @param rotationAngle
     * @return graphic
     */
    public I_DirectedGraphic rotateAroundPeakPoint(final double rotationAngle) {
        if (containedLines.isEmpty()) {
            return this;
        }
        final ArrayList<I_DirectedLine> rotatedLines = new ArrayList<I_DirectedLine>();
        for (final I_DirectedLine line : containedLines) {
            final I_DirectedLine rotatedLine
                    = line.rotateAroundAnyPoint(getPeakPointX(), getPeakPointY(), rotationAngle);
            rotatedLines.add(rotatedLine);
        }
        return doCreateDirectedGraphic(rotatedLines);
    }

    /**
     * Translates the graphic by its peak point (i.e. the source point of the first contained line)
     * to the specified point
     *
     * @param newX x coordinate of the new peak point
     * @param newY y coordinate of the new peak point
     * @return graphic
     */
    public I_DirectedGraphic translatePeakPointTo(final int newX, final int newY) {
        if (containedLines.isEmpty()) {
            return this;
        }
        final int deltaX = newX - getPeakPointX();
        final int deltaY = newY - getPeakPointY();
        final ArrayList<I_DirectedLine> translatedLines = new ArrayList<I_DirectedLine>();
        for (final I_DirectedLine line : containedLines) {
            final I_DirectedLine translatedLine = line.translateBy(deltaX, deltaY);
            translatedLines.add(translatedLine);
        }
        return doCreateDirectedGraphic(translatedLines);
    }

    /**
     * Compares two DirectedGraphics by its contained lines 
     * (raises a class cast exception for objects of different types)
     *
     * @param obj object of type DirectedGraphic
     * @return true if objects equal, false otherwise
     */
    public boolean equals(final Object obj) {
    	if (obj == this) {
    		return true;
    	} else if (obj instanceof DirectedGraphic) {
    		final DirectedGraphic other = ((DirectedGraphic) obj);
    		return containedLines.equals(other.containedLines);
    	} else {
    		return false;
    	}
    }

    public int hashCode() {
        return containedLines.hashCode();
    }
    
    /**
     * Calculates the extension of the graphic in horizontal direction
     *
     * @return horizontal extension
     */
    public double calculateWidth() {
        if (containedLines.isEmpty()) {
            return 0.0;
        }
        return calculateWidthAuxiliary();
    }

    /**
     * Tests if the contained lines of the graphic form a closed shape:
     * Target point of each line is the source point of the next one,
     * target point of last line is source point of first line and there are at least three lines.
     *
     * @return true if graphic is closed, false otherwise
     */
    public boolean isClosed() {
        final ArrayList<I_DirectedLine> lines = new ArrayList<I_DirectedLine>();
        lines.addAll(getLines());
        return hasEnoughLines(lines) && isChain(lines);
    }

    private double calculateWidthAuxiliary() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (final I_DirectedLine line : containedLines) {
            minX = calculateMinX(line, minX);
            maxX = calculateMaxX(line, maxX);
        }
        return maxX - minX;
    }

    private double calculateMaxX(final I_DirectedLine line, double maxX) {
        if (line.getSourceX() > maxX) {
            maxX = line.getSourceX();
        }
        if (line.getTargetX() > maxX) {
            maxX = line.getTargetX();
        }
        return maxX;
    }

    private double calculateMinX(final I_DirectedLine line, double minX) {
        if (line.getSourceX() < minX) {
            minX = line.getSourceX();
        }
        if (line.getTargetX() < minX) {
            minX = line.getTargetX();
        }
        return minX;
    }


    abstract I_DirectedGraphic doCreateDirectedGraphic(final ArrayList<I_DirectedLine> containedLines);


    private static boolean hasEnoughLines(final ArrayList<I_DirectedLine> lines) {
        return (!lines.isEmpty() && lines.size() > 2);
    }

    private static boolean isChain(final ArrayList<I_DirectedLine> lines) {
        lines.add(lines.get(0));
        for (int index = 0; index < lines.size() - 1; index++) {
            final I_DirectedLine line = lines.get(index);
            final I_DirectedLine nextLine = lines.get(index + 1);
            if (!areSuccessionalLines(line, nextLine)) {
                return false;
            }
        }
        return true;
    }

    private static boolean areSuccessionalLines(final I_DirectedLine line, final I_DirectedLine nextLine) {
        return line.getRoundedTargetX() == nextLine.getRoundedSourceX()
                && line.getRoundedTargetY() == nextLine.getRoundedSourceY();
    }

}
