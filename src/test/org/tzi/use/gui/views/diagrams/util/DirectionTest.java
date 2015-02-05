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

import junit.framework.TestCase;

/**
 * Tests the enumeration Direction
 * @author Lars Hamann
 *
 */
public class DirectionTest extends TestCase {
	public void testHelper() {
		assertTrue(Direction.NORTH.isLocatedNorth());
		assertFalse(Direction.NORTH.isLocatedEast());
		assertFalse(Direction.NORTH.isLocatedSouth());
		assertFalse(Direction.NORTH.isLocatedWest());
		
		assertFalse(Direction.EAST.isLocatedNorth());
		assertTrue(Direction.EAST.isLocatedEast());
		assertFalse(Direction.EAST.isLocatedSouth());
		assertFalse(Direction.EAST.isLocatedWest());
		
		assertFalse(Direction.SOUTH.isLocatedNorth());
		assertFalse(Direction.SOUTH.isLocatedEast());
		assertTrue(Direction.SOUTH.isLocatedSouth());
		assertFalse(Direction.SOUTH.isLocatedWest());
		
		assertFalse(Direction.WEST.isLocatedNorth());
		assertFalse(Direction.WEST.isLocatedEast());
		assertFalse(Direction.WEST.isLocatedSouth());
		assertTrue(Direction.WEST.isLocatedWest());
		
		assertTrue(Direction.NORTH_EAST.isLocatedNorth());
		assertTrue(Direction.NORTH_EAST.isLocatedEast());
		assertFalse(Direction.NORTH_EAST.isLocatedSouth());
		assertFalse(Direction.NORTH_EAST.isLocatedWest());
		
		assertFalse(Direction.SOUTH_EAST.isLocatedNorth());
		assertTrue(Direction.SOUTH_EAST.isLocatedEast());
		assertTrue(Direction.SOUTH_EAST.isLocatedSouth());
		assertFalse(Direction.SOUTH_EAST.isLocatedWest());
		
		assertFalse(Direction.SOUTH_WEST.isLocatedNorth());
		assertFalse(Direction.SOUTH_WEST.isLocatedEast());
		assertTrue(Direction.SOUTH_WEST.isLocatedSouth());
		assertTrue(Direction.SOUTH_WEST.isLocatedWest());
		
		assertTrue(Direction.NORTH_WEST.isLocatedNorth());
		assertFalse(Direction.NORTH_WEST.isLocatedEast());
		assertFalse(Direction.NORTH_WEST.isLocatedSouth());
		assertTrue(Direction.NORTH_WEST.isLocatedWest());
		
		Point2D.Double from = new Point2D.Double(), to = new Point2D.Double();
		
		from.setLocation(0, 0);
		to.setLocation(0, 0);
		
		assertEquals(Direction.getDirection(from, to), Direction.CENTER);
		
		to.x = 1;
		assertEquals(Direction.getDirection(from, to), Direction.EAST);
		assertEquals(Direction.getDirection(to, from), Direction.WEST);
		
		to.x = 0;
		to.y = 1;
		assertEquals(Direction.getDirection(from, to), Direction.SOUTH);
		assertEquals(Direction.getDirection(to, from), Direction.NORTH);
		
		to.x = 1;
		assertEquals(Direction.getDirection(from, to), Direction.SOUTH_EAST);
		assertEquals(Direction.getDirection(to, from), Direction.NORTH_WEST);
		
		to.x = -1;
		assertEquals(Direction.getDirection(from, to), Direction.SOUTH_WEST);
		assertEquals(Direction.getDirection(to, from), Direction.NORTH_EAST);
	}
}
