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

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

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

    /** 
     * Calculates the intersection point of to lines.
     * 
     * @param line1 the first line.
     * @param line2 the second line.
     * @return the intersection point of both lines.
     */    
    public static Point intersectionPoint( Line2D line1, Line2D line2) {
        // getting the intersection coordinate by vector arithmetic
        // example for the top line:
        //   sx over sy + r * (tx - sx) over (ty - sy)
        // = line2.getX1() over line2.getY1() + v * (line2.getX2() - line2.getX1()) over (line2.getY2() - line2.getY1())
        
        double numerator = 1.0;
        double denominator = 1.0;
        
        numerator = ( line1.getX2() - line1.getX1()) * (line1.getY1() - line2.getY1()) + (line1.getY2() - line1.getY1()) * (line2.getX1() - line1.getX1());
        denominator = (line2.getY2() - line2.getY1()) * (line1.getX2() - line1.getX1()) 
        - (line2.getX2() - line2.getX1()) * (line1.getY2() - line1.getY1()); 
        
        double v = numerator / denominator;
        
        double intersection_X = line2.getX1() + v * (line2.getX2() - line2.getX1());
        double intersection_Y = line2.getY1() + v * (line2.getY2() - line2.getY1());
        
        return new Point( (int)intersection_X, (int)intersection_Y );
    }
    
    /**
     * Calculates the mid point of two given points which form a straight line.
     * @param x1 x-coordinate of the first point.
     * @param y1 y-coordinate of the first point.
     * @param x2 x-coordinate of the second point.
     * @param y2 y-coordinate of the second point.
     */
    public static Point2D calculateMidPoint( double x1, double y1, double x2, double y2 ) {
    	Point2D.Double result = new Point2D.Double();
    	
    	double deltaX = Math.abs(x1 - x2) / 2;
        double deltaY = Math.abs(y1 - y2) / 2;

        // Either add or subtract (multiply by one or minus one)
        // the delta value. This depends on the location of the points
        result.x = x1 + deltaX * (x1 < x2 ? 1 : -1);
        result.y = y1 + deltaY * (y1 < y2 ? 1 : -1);
        
        return result;
    }
    
    /**
     * Calculates the mid point of two given points which form a straight line.
     * @param p1 first point
     * @param p2 second point
     */
    public static Point2D calculateMidPoint( Point2D p1, Point2D p2 ) {
    	return calculateMidPoint(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}
