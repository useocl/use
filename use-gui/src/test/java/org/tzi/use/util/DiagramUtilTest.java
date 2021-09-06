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

package org.tzi.use.util;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import junit.framework.TestCase;

import org.junit.Test;
import org.tzi.use.gui.views.diagrams.util.Util;

/**
 * Test for diagram utilities (mainly geometry)
 * @author Lars Hamann
 *
 */
public class DiagramUtilTest extends TestCase {
	@Test
	public void testCircleIntersection() {
		Ellipse2D circle = new Ellipse2D.Double(-4, -4, 8, 8);
		Point2D res;
		Point2D.Double expected = new Point2D.Double();
		
		expected.x = 4;
		expected.y = 0;
		res = Util.intersectionPoint(circle, new Point2D.Double(4, 0));
		assertEquals(expected, res);
		
		expected.x = 0;
		expected.y = 4;
		res = Util.intersectionPoint(circle, new Point2D.Double(0, 4));
		assertEquals(expected, res);
		
		expected.x = -4;
		expected.y = 0;
		res = Util.intersectionPoint(circle, new Point2D.Double(-4, 0));
		assertEquals(expected, res);
		
		expected.x = 0;
		expected.y = -4;
		res = Util.intersectionPoint(circle, new Point2D.Double(0, -4));
		assertEquals(expected, res);
		
		res = Util.intersectionPoint(circle, new Point2D.Double(1.5, -2.5));
		Point2D res2 = Util.intersectionPoint(circle, res);
		assertEquals(res, res2);
	}
	
	public void testRectangleInterception() {
		Rectangle2D.Double r = new Rectangle2D.Double();
		r.x = 0;
		r.y = 0;
		r.width = 1;
		r.height = 1;
		
		Line2D.Double l = new Line2D.Double();
		l.x1 = 0.5;
		l.y1 = 0.5;
		
		l.x2 = 1.5;
		l.y2 = 0.5;
		
		Point2D res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(1.0, res.getX());
		assertEquals(0.5, res.getY());
		
		l.x2 = 0.5;
		l.y2 = 1.5;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(0.5, res.getX());
		assertEquals(1.0, res.getY());
		
		l.x2 = -0.5;
		l.y2 = 0.5;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(0.0, res.getX());
		assertEquals(50, Math.round(res.getY() * 100));
		
		l.x2 = 0.5;
		l.y2 = -1.5;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(0.5, res.getX());
		assertEquals(0.0, res.getY() * 100);
		
		// Test enlarge
		l.x2 = 0.6;
		l.y2 = 0.5;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(1.0, res.getX());
		assertEquals(0.5, res.getY());
		
		l.x2 = 0.5;
		l.y2 = 0.6;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(0.5, res.getX());
		assertEquals(1.0, res.getY());
		
		l.x2 = 0.4;
		l.y2 = 0.5;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(0.0, res.getX());
		assertEquals(50, Math.round(res.getY() * 100));
		
		l.x2 = 0.5;
		l.y2 = 0.4;
		res = Util.intersectionPoint(r, l.getP1(), l.getP2(), true);
		assertEquals(0.5, res.getX());
		assertEquals(0.0, res.getY() * 100);
	}
}
