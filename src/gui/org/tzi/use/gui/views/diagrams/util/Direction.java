/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum to define directions.
 * @author Lars Hamann
 *
 */
public enum Direction {
	UNKNOWN(0),
	NORTH(1),
	EAST(2),
	SOUTH(4),
	WEST(8),
	NORTH_EAST(NORTH.getValue() + EAST.getValue()),
	SOUTH_EAST(SOUTH.getValue() + EAST.getValue()),
	SOUTH_WEST(SOUTH.getValue() + WEST.getValue()),
	NORTH_WEST(NORTH.getValue() + WEST.getValue());
	
	static Map<Integer, Direction> intMap = new HashMap<Integer, Direction>();
	static {
		for (Direction d : Direction.values()) {
			intMap.put(d.getValue(), d);
		}
	}
	
	int value;
	
	Direction(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isLocatedTo(Direction d) {
		return (value & d.value) == d.value;
	}
	
	public boolean isLocatedNorth() {
		return isLocatedTo(NORTH);
	}
	
	public boolean isLocatedSouth() {
		return isLocatedTo(SOUTH);
	}
	
	public boolean isLocatedWest() {
		return isLocatedTo(WEST);
	}
	
	public boolean isLocatedEast() {
		return isLocatedTo(EAST);
	}
	
	/**
	 * Returns the direction the point <code>to</code> is
	 * located from <code>from</code>,e.g., 
	 * from (0,0) to (1,1) = <code>SOUTHEAST</code>.
	 * @param from
	 * @param to
	 * @return
	 */
	public static Direction getDirection(Point2D from, Point2D to) {
		int horizontal = 0;
		int vertical = 0;
		
		if (to.getX() < from.getX()) {
			horizontal = WEST.getValue();
		} else if (to.getX() > from.getX()) {
			horizontal = EAST.getValue();
		}
		
		if (to.getY() < from.getY()) {
			vertical = NORTH.getValue();
		} else if (to.getY() > from.getY()) {
			vertical = SOUTH.getValue();
		}
		
		return getDirection(vertical + horizontal);
	}
	
	public static Direction getDirection(int value) {
		return intMap.get(value);
	}
}
