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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Represents a filled graphic that consists of lines which form a closed shape
 *
 * @see DirectedGraphic#isClosed()
 */
public class FilledDirectedGraphic extends DirectedGraphic {

	private Color fillColor = null;
	
    FilledDirectedGraphic(final ArrayList<I_DirectedLine> lines) {
        this.containedLines.addAll(lines);
    }

    /**
     * Creates a FilledDirectedGraphic from the given graphic if that one is closed
     *
     * @param directedGraphic graphic to be filled (should be closed)
     * @return filled graphic
     */
    public static FilledDirectedGraphic fillDirectedGraphic(final I_DirectedGraphic directedGraphic) {
        if (directedGraphic.isClosed()) {
            return new FilledDirectedGraphic(directedGraphic.getLines());
        }
        throw new IllegalArgumentException("fillDirectedGraphic: directed graphic <"
                + directedGraphic + "> is not a closed shape");
    }

    /**
     * Creates a FilledDirectedGraphic from the given graphic if that one is closed
     *
     * @param directedGraphic graphic to be filled (should be closed)
     * @return filled graphic
     */
    public static FilledDirectedGraphic fillDirectedGraphic(final I_DirectedGraphic directedGraphic, Color fillColor) {
        if (directedGraphic.isClosed()) {
        	FilledDirectedGraphic g = new FilledDirectedGraphic(directedGraphic.getLines());
        	g.fillColor = fillColor;
        	return g;
        }
        throw new IllegalArgumentException("fillDirectedGraphic: directed graphic <"
                + directedGraphic + "> is not a closed shape");
    }
    
    /**
     * Draws this graphic as a polygon
     *
     * @param graphic to be drawn into
     * @return this graphic
     */
    public I_DirectedGraphic draw(final Graphics graphic) {
    	final Polygon polygon = createPolygon();
    	final Color org = graphic.getColor();
    	
        if (fillColor != null) {
        	graphic.setColor(fillColor);
        }
        
        graphic.fillPolygon(polygon);
        graphic.setColor(org);
        
        graphic.drawPolygon(polygon);
        return this;
    }

    /**
     * Not permitted (graphic has to be closed)
     *
     * @param line
     */
    public I_DirectedGraphic addLine(final I_DirectedLine line) {
        throw new UnsupportedOperationException("Method addLine not supported by FilledDirectedGraphic");
    }

    /**
     * Not permitted (graphic has to be closed)
     *
     * @param lines
     */
    public I_DirectedGraphic addAllLines(final ArrayList<I_DirectedLine> lines) {
        throw new UnsupportedOperationException("Method addAllLines not supported by FilledDirectedGraphic");
    }

    /**
     * Not permitted (graphic has to be closed)
     *
     * @param graphic
     */
    public I_DirectedGraphic addDirectedGraphic(final I_DirectedGraphic graphic) {
        throw new UnsupportedOperationException("Method addDirectedGraphic not supported by FilledDirectedGraphic");
    }

    Polygon createPolygon() {
        final Polygon polygon = new Polygon();
        for (final I_DirectedLine line : containedLines) {
            polygon.addPoint(line.getRoundedSourceX(), line.getRoundedSourceY());
        }
        return polygon;
    }

    I_DirectedGraphic doCreateDirectedGraphic(final ArrayList<I_DirectedLine> containedLines) {
    	FilledDirectedGraphic res = new FilledDirectedGraphic(containedLines); 
    	res.fillColor = fillColor;
    	return res;
    }

}
