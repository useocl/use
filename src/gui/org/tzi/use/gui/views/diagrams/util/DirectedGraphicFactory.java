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
import java.util.ArrayList;

public class DirectedGraphicFactory {
    
    private DirectedGraphicFactory() {}
    
    public static final I_DirectedGraphic EMPTY_HEAD = new SimpleDirectedGraphic();
    
    public static final I_DirectedGraphic TRIANGLE_EMPTY =
            new SimpleDirectedGraphic().addLine(DirectedLineFactory.createSolidDirectedLine(12, 8, 0, 0))
            .addLine(DirectedLineFactory.createSolidDirectedLine(0, 0, 0, 16))
            .addLine(DirectedLineFactory.createSolidDirectedLine(0, 16, 12, 8));
    
    public static final I_DirectedGraphic TRIANGLE =
    		FilledDirectedGraphic.fillDirectedGraphic(TRIANGLE_EMPTY, Color.WHITE);
    
    public static final I_DirectedGraphic ARROW_HEAD =
            new SimpleDirectedGraphic().addLine(DirectedLineFactory.createSolidDirectedLine(15, 6, 0, 0))
            .addLine(DirectedLineFactory.createSolidDirectedLine(15, 6, 0, 12));
    
    public static final I_DirectedGraphic HOLLOW_DIAMOND =
            new SimpleDirectedGraphic().addLine(DirectedLineFactory.createSolidDirectedLine(18, 6, 9, 0))
            .addLine(DirectedLineFactory.createSolidDirectedLine(9, 0, 0, 6))
            .addLine(DirectedLineFactory.createSolidDirectedLine(0, 6, 9, 12))
            .addLine(DirectedLineFactory.createSolidDirectedLine(9, 12, 18, 6));
    
    public static final I_DirectedGraphic FILLED_DIAMOND =
            FilledDirectedGraphic.fillDirectedGraphic(HOLLOW_DIAMOND);

    public static I_DirectedGraphic createSimpleDirectedGraphic() {
        return new SimpleDirectedGraphic();
    }

    public static I_DirectedGraphic createFilledDirectedGraphic(final ArrayList<I_DirectedLine> lines) {
        return new FilledDirectedGraphic(lines);
    }
}