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
import java.util.ArrayList;

/**
 * Interface representing a graphic that consists of lines
 *
 * @see I_DirectedLine
 */
public interface I_DirectedGraphic {

    /**
     * Calculates the extension of the graphic in horizontal direction
     *
     * @return horizontal extension
     */
    double calculateWidth();

    /**
     * Getter for the contained lines
     *
     * @return contained lines
     */
    ArrayList<I_DirectedLine> getLines();

    /**
     * Getter for the x coordinate of the peak point
     *
     * @return x coordinate of the peak point
     */
    int getPeakPointX();

    /**
     * Getter for the y coordinate of the peak point
     *
     * @return y coordinate of the peak point
     */
    int getPeakPointY();

    /**
     * Rotates the graphic its peak point (i.e. the source point of the first contained line)
     *
     * @param rotationAngle
     * @return graphic
     */
    I_DirectedGraphic rotateAroundPeakPoint(final double rotationAngle);

    /**
     * Translates the graphic by its peak point (i.e. the source point of the first contained line)
     * to the specified point
     *
     * @param newX x coordinate of the new peak point
     * @param newY y coordinate of the new peak point
     * @return graphic
     */
    I_DirectedGraphic translatePeakPointTo(final int newX, final int newY);

    /**
     * Draws the graphic
     *
     * @param graphic
     * @return this graphic
     */
    I_DirectedGraphic draw(final Graphics graphic);

    /**
     * Adds a line to the graphic
     *
     * @param line to be added
     * @return graphic
     */
    I_DirectedGraphic addLine(final I_DirectedLine line);

    /**
     * Adds a list of lines to the graphic
     *
     * @param lines to be added
     * @return graphic
     */
    I_DirectedGraphic addAllLines(final ArrayList<I_DirectedLine> lines);

    /**
     * Adds lines from a given graphic to the graphic
     *
     * @param graphic lines to be added
     * @return graphic
     */
    I_DirectedGraphic addDirectedGraphic(final I_DirectedGraphic graphic);

    /**
     * Tests if the contained lines of the graphic form a closed shape:
     * Target point of each line is the source point of the next one,
     * target point of last line is source point of first line and there are at least three lines.
     *
     * @return true if graphic is closed, false otherwise
     */
    boolean isClosed();
}
