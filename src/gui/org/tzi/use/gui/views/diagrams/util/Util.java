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

/**
 * Class providing mathematical calculations
 */
public class Util {

    private Util() {}
    
    /**
     * Calculates the angle between the given coordinates
     *
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return angle in degrees
     */
    public static double calculateAngleBetween(final double x1, final double y1, final double x2, final double y2) {
        if (isLineToTheLeft(x1, x2)) {
            return 180.0 + calculateAngleInQuadrant(x1, y1, x2, y2);
        }
        if (isVerticalLine(x1, x2) && isLineToTheTop(y1, y2)) {
            return 90.0;
        }
        if (isVerticalLine(x1, x2) && isLineToTheBottom(y1, y2)) {
            return 270.0;
        }
        if (isLineToTheBottom(y1, y2)) {
            return 360.0 + calculateAngleInQuadrant(x1, y1, x2, y2);
        }
        return calculateAngleInQuadrant(x1, y1, x2, y2);
    }

    /**
     * Calculates the distance between the given coordinates
     *
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     * @return distance
     */
    public static double calculateDistance(final double x1, final double y1,
                                           final double x2, final double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static boolean isVerticalLine(final double x1, final double x2) {
        return x1 == x2;
    }

    private static boolean isLineToTheLeft(final double x1, final double x2) {
        return x1 > x2;
    }

    private static double calculateAngleInQuadrant(final double x1, final double y1, final double x2, final double y2) {
        if (x2 == x1) {
            return 0.0;
        }
        final double height = y2 - y1;
        final double width = x2 - x1;
        return Math.toDegrees(Math.atan(height / width));
    }

    private static boolean isLineToTheBottom(final double y1, final double y2) {
        return y1 > y2;
    }

    private static boolean isLineToTheTop(final double y1, final double y2) {
        return y1 < y2;
    }


}
