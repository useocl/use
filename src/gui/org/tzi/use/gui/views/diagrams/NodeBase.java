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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.diagrams;

import java.awt.Polygon;

import org.tzi.use.uml.mm.MClass;

/** 
 * Base class of all node types in the diagram.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public abstract class NodeBase extends PlaceableNode {
    double fX = 200;
    double fY = 200;

    public abstract String name();
    
    public abstract MClass cls();
    
    public Polygon dimension() { 
        int x = (int) x();
        int y = (int) y();
        int halfWidth = (int) (getWidth() / 2);
        int halfHeight = (int) (getHeight() / 2);
        
        int[] xpoints = { x - halfWidth, x + halfWidth, 
                          x + halfWidth, x - halfWidth };
        int[] ypoints = { y - halfHeight, y - halfHeight,
                          y + halfHeight, y + halfHeight };
        
        int npoints = xpoints.length;
        return new Polygon( xpoints, ypoints, npoints );
    }

}