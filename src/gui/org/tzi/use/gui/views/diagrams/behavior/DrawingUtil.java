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

package org.tzi.use.gui.views.diagrams.behavior;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

/**
 * A Util class for drawing components such as actors, messages arrow
 * 
 * @author Quang Dung Nguyen
 * 
 */
public final class DrawingUtil {

	public static final int ARMS_LENGTH = 20;
	public static final int BODY_LENGTH = 20;
	public static final int LEGS_LENGTH = 10;
	public static final int HEAD_DIAMETER = 10;
	public static final int TOTAL_HEIGHT = HEAD_DIAMETER + BODY_LENGTH
			+ LEGS_LENGTH;

	public static final int ARMS_LENGTH_BIG = 30;
	public static final int BODY_LENGTH_BIG = 30;
	public static final int LEGS_LENGTH_BIG = 15;
	public static final int HEAD_DIAMETER_BIG = 15;
	public static final int TOTAL_HEIGHT_BIG = HEAD_DIAMETER_BIG
			+ BODY_LENGTH_BIG + LEGS_LENGTH_BIG;

	private DrawingUtil() {

	}

	/**
	 * Draw actor in sequence diagram
	 * 
	 * @param x
	 *            position x of actor
	 * @param y
	 *            position y of actor
	 * @param g
	 *            Graphics2D
	 */
	public static void drawActor(int x, int y, Graphics2D g) {
		// head
		g.drawOval(x - HEAD_DIAMETER / 2, y, HEAD_DIAMETER, HEAD_DIAMETER);

		// body
		g.drawLine(x, y + HEAD_DIAMETER, x, y + HEAD_DIAMETER + BODY_LENGTH);

		// arms
		g.drawLine(x - ARMS_LENGTH / 2, y + HEAD_DIAMETER + BODY_LENGTH / 2, x
				+ ARMS_LENGTH / 2, y + HEAD_DIAMETER + BODY_LENGTH / 2);

		// legs
		g.drawLine(x, y + HEAD_DIAMETER + BODY_LENGTH, x - ARMS_LENGTH / 2, y
				+ TOTAL_HEIGHT);
		g.drawLine(x, y + HEAD_DIAMETER + BODY_LENGTH, x + ARMS_LENGTH / 2, y
				+ TOTAL_HEIGHT);
	}

	/**
	 * Draw big actor in communication diagram
	 * 
	 * @param x
	 *            position x of actor
	 * @param y
	 *            position y of actor
	 * @param g
	 *            Graphics2D
	 */
	public static void drawBigActor(int x, int y, Graphics2D g) {
		// head
		g.drawOval(x - HEAD_DIAMETER_BIG / 2, y, HEAD_DIAMETER_BIG,
				HEAD_DIAMETER_BIG);

		// body
		g.drawLine(x, y + HEAD_DIAMETER_BIG, x, y + HEAD_DIAMETER_BIG
				+ BODY_LENGTH_BIG);

		// arms
		g.drawLine(x - ARMS_LENGTH_BIG / 2, y + HEAD_DIAMETER_BIG
				+ BODY_LENGTH_BIG / 2, x + ARMS_LENGTH_BIG / 2, y
				+ HEAD_DIAMETER_BIG + BODY_LENGTH_BIG / 2);

		// legs
		g.drawLine(x, y + HEAD_DIAMETER_BIG + BODY_LENGTH_BIG, x
				- ARMS_LENGTH_BIG / 2, y + TOTAL_HEIGHT_BIG);
		g.drawLine(x, y + HEAD_DIAMETER_BIG + BODY_LENGTH_BIG, x
				+ ARMS_LENGTH_BIG / 2, y + TOTAL_HEIGHT_BIG);
	}

	/**
	 * Draw arrow of a message in communication diagram
	 * 
	 * @param g
	 *            Graphics2D
	 * @param x1
	 *            position x where arrow starts
	 * @param y1
	 *            position y where arrow starts
	 * @param x2
	 *            position x where arrow ends
	 * @param y2
	 *            position y where arrow ends
	 */
	public static void drawArrow(final Graphics2D g, final int x1,
			final int y1, final int x2, final int y2) {
		final int ARR_SIZE = 5;

		// Get the current transform
		AffineTransform saveAT = g.getTransform();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		// Perform transformation
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
				new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);

		// Restore original transform
		g.setTransform(saveAT);
	}

	/**
	 * Draw arrow of a answered message in communication diagram
	 * 
	 * @param g
	 *            Graphics2D
	 * @param x1
	 *            position x where arrow starts
	 * @param y1
	 *            position y where arrow starts
	 * @param x2
	 *            position x where arrow ends
	 * @param y2
	 *            position y where arrow ends
	 */
	public static void drawReturnArrow(final Graphics2D g, final int x1,
			final int y1, final int x2, final int y2) {
		final int ARR_SIZE = 4;

		Stroke oldStroke = g.getStroke();
		// Get the current transform
		AffineTransform saveAT = g.getTransform();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		// Perform transformation
		g.transform(at);

		g.drawLine(len - ARR_SIZE, ARR_SIZE, 0, 0);
		g.drawLine(len - ARR_SIZE, -ARR_SIZE, 0, 0);

		// Dash pattern of arrow line
		float[] dash = { 2, 3 };

		BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.CAP_BUTT, 1.0f, dash, 1.0f);
		g.setStroke(bs);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len + 1, 0);

		// Restore original stroke
		g.setStroke(oldStroke);
		// Restore original transform
		g.setTransform(saveAT);
	}

}
