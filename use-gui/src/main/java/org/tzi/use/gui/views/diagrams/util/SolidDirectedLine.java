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
 * Represents a solid directed line
 */
public class SolidDirectedLine extends DirectedLine {

    /**
     * Creates a solid directed line between the given coordinates
     *
     * @param sourceX X coordinate of source point
     * @param sourceY Y coordinate of source point
     * @param targetX X coordinate of target point
     * @param targetY Y coordinate of target point
     */
    public SolidDirectedLine(final double sourceX, final double sourceY, final double targetX, final double targetY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * Overridden template method
     *
     * @see DirectedLine
     */
    public I_DirectedLine doCreateDirectedLine(final double sourceX, final double sourceY, final double targetX,
                                               final double targetY) {
        return new SolidDirectedLine(sourceX, sourceY, targetX, targetY);
    }

    /**
     * Draws the solid line into the given graphic
     *
     * @param graphic to be drawn into
     * @return line
     */
    public I_DirectedLine draw(final Graphics graphic) {
        graphic.drawLine((int) Math.round(sourceX),
                (int) Math.round(sourceY),
                (int) Math.round(targetX),
                (int) Math.round(targetY));
        return this;
    }

}
