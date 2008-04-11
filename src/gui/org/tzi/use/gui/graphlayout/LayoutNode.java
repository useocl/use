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

package org.tzi.use.gui.graphlayout;


/**
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */


public class LayoutNode {
    Object fObject;     // if null this node is a dummy
    int fNumber;
    int fLayer;
    int fLayerX;        // ordering on layer
    int fX;
    int fY;
    
    public LayoutNode(Object o) {
        fObject = o;
    }

    public LayoutNode(Object o, int num) {
        this(o);
        fNumber = num;
    }

    public boolean isDummy() {
        return fObject == null;
    }

    public int getX() {
        return fX;
    }

    public int getY() {
        return fY;
    }

    public String toString() {
        if (fObject == null )
            return "D" + fNumber;
        else
            return fObject.toString();
    }

}
