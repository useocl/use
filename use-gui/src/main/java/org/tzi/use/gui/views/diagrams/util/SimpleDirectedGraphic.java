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
 * Represents a simple graphic that consists of lines
 */
public class SimpleDirectedGraphic extends DirectedGraphic {

    /**
     * Adds the given line to the graphic
     *
     * @param line to be added
     * @return graphic
     */
    public I_DirectedGraphic addLine(final I_DirectedLine line) {
        final SimpleDirectedGraphic directedGraphic = new SimpleDirectedGraphic();
        directedGraphic.containedLines.addAll(containedLines);
        directedGraphic.containedLines.add(line);
        
        
        return directedGraphic;
    }

    /**
     * Adds a list of lines to the graphic
     *
     * @param lines to be added
     * @return graphic
     */
    public I_DirectedGraphic addAllLines(final ArrayList<I_DirectedLine> lines) {
        final SimpleDirectedGraphic directedGraphic = new SimpleDirectedGraphic();
        directedGraphic.containedLines.addAll(containedLines);
        directedGraphic.containedLines.addAll(lines);
        return directedGraphic;
    }

    /**
     * Adds the lines of the given graphic to this graphic
     *
     * @param graphic to be added
     * @return graphic
     */
    public I_DirectedGraphic addDirectedGraphic(final I_DirectedGraphic graphic) {
        final SimpleDirectedGraphic directedGraphic = new SimpleDirectedGraphic();
        directedGraphic.containedLines.addAll(containedLines);
        final ArrayList<I_DirectedLine> containedLinesGraphic = graphic.getLines();
        directedGraphic.containedLines.addAll(containedLinesGraphic);
        return directedGraphic;
    }

    /**
     * Draws the lines of the this graphic
     *
     * @param graphic to be drawn into
     * @return this graphic
     */
    public I_DirectedGraphic draw(final Graphics graphic) {
        for (final I_DirectedLine line : containedLines) {
        	line.draw(graphic);
        }
        return this;
    }

    I_DirectedGraphic doCreateDirectedGraphic(final ArrayList<I_DirectedLine> containedLines) {
        return new SimpleDirectedGraphic().addAllLines(containedLines);
    }
}
