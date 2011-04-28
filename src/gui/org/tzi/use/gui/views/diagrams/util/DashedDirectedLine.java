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

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Represents a dashed directed line
 */
public class DashedDirectedLine extends DirectedLine {

    /**
     * The default length of a single dash
     */
    public static final int DEFAULT_FRAGMENT_LENGTH = 10;
    /**
     * The default length of the distance between two dashes
     */
    public static final int DEFAULT_INTERSECTION_LENGTH = 10;

    private int fragmentLength = DEFAULT_FRAGMENT_LENGTH;
    private int intersectionLength = DEFAULT_INTERSECTION_LENGTH;

    /**
     * Creates a dashed directed line between the given coordinates with the given lengths of dashes and intersections
     *
     * @param sourceX            X coordinate of source point
     * @param sourceY            Y coordinate of source point
     * @param targetX            X coordinate of target point
     * @param targetY            Y coordinate of target point
     * @param fragmentLength     length of a single dash
     * @param intersectionLength length of the distance between two dashes
     */
    public DashedDirectedLine(final double sourceX, final double sourceY, final double targetX, final double targetY,
                              final int fragmentLength, final int intersectionLength) {
        init(sourceX, sourceY, targetX, targetY);
        this.fragmentLength = fragmentLength;
        this.intersectionLength = intersectionLength;
    }

    /**
     * Creates a dashed directed line between the given coordinates with the default lengths of dashes and intersections
     *
     * @param sourceX X coordinate of source point
     * @param sourceY Y coordinate of source point
     * @param targetX X coordinate of target point
     * @param targetY Y coordinate of target point
     */
    public DashedDirectedLine(final double sourceX, final double sourceY, final double targetX, final double targetY) {
        init(sourceX, sourceY, targetX, targetY);
    }

    /**
     * Overridden template method
     *
     * @see DirectedLine
     */
    public I_DirectedLine doCreateDirectedLine(final double sourceX, final double sourceY, final double targetX,
                                               final double targetY) {
        return new DashedDirectedLine(sourceX, sourceY, targetX, targetY, fragmentLength, intersectionLength);
    }

    /**
     * Draws the dashed line into the given graphic
     *
     * @param graphic to be drawn into
     * @return line
     */
    public I_DirectedLine draw(final Graphics graphic) {
    	Graphics2D g = (Graphics2D)graphic;
    	
    	Stroke oldStroke = g.getStroke();
		BasicStroke newStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10.0F, new float[] { fragmentLength,
						intersectionLength }, 0.0F);

		g.setStroke(newStroke);		
		g.drawLine((int)sourceX, (int)sourceY, (int)targetX, (int)targetY );
		g.setStroke(oldStroke);
		
        return this;
    }

    private void init(final double sourceX, final double sourceY, final double targetX, final double targetY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
