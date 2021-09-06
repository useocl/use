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

import java.awt.FontMetrics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.util.FloatUtil;

/**
 * Class providing mathematical calculations
 */
public class Util {

    private Util() {}

    private final static int DEFAULT_LEADING = 2;
    
    public static int getLeading(FontMetrics fm) {
    	int leading = fm.getLeading();
    	return (leading == 0 ? DEFAULT_LEADING : leading);
    }
    
    public static double getLineHeight(FontMetrics fm) {
    	return fm.getMaxDescent() + fm.getMaxAscent();
    }
    
    /**
     * Returns a rectangle enlarged by <code>size</code> (half of size in each direction).
     * @param toEnlarge The <code>rectangle</code> to enlarge.
     * @param size The <code>size</code> the rectangle is enlarged by. 
     * @return A new <code>rectangle</code> instance enlarged by <code>size</code>. 
     */
    public static Rectangle2D enlargeRectangle(Rectangle2D toEnlarge, double size) {
		final Rectangle2D.Double res = new Rectangle2D.Double(
				toEnlarge.getX() - size / 2, 
				toEnlarge.getY() - size / 2, 
				toEnlarge.getWidth() + size,
				toEnlarge.getHeight() + size);
		
		return res;
    }
    
    /**
     * Calculates the angle between the given coordinates
     *
     * @param p1 x the first point
     * @param p2 y the second point
     * 
     * @return angle in degrees
     */
    public static double calculateAngleBetween(final Point2D p1, final Point2D p2) {
    	return calculateAngleBetween(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
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
        return FloatUtil.equals(x1, x2);
    }

    private static boolean isLineToTheLeft(final double x1, final double x2) {
        return x1 > x2;
    }

    private static double calculateAngleInQuadrant(final double x1, final double y1, final double x2, final double y2) {
        if (FloatUtil.equals(x1, x2)) {
            return 0.0d;
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
    public static Point2D intersectionPoint( Line2D line1, Line2D line2) {
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
        
        return new Point2D.Double( intersection_X, intersection_Y );
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
    
    public static Point2D intersectWithCircle(Ellipse2D circle, Line2D intLine, Direction intersectionArea)
    {
    	// The original code was published here:
    	// http://gravisto.fim.uni-passau.de/doc/guide/plugins/kinds/shape.html
    	
        if (!FloatUtil.equals(circle.getWidth(), circle.getHeight()))
        {
            throw new IllegalArgumentException(
                "First parameter must be a circle, i.e. height and width " +
                "must be equal. Were: width=" + circle.getWidth() +
                "  height=" + circle.getHeight());
        }

        double cx = circle.getCenterX();
        double cy = circle.getCenterY();
        double radius = circle.getWidth() / 2d;
        double sx = intLine.getX1();
        double sy = intLine.getY1();
        double tx = intLine.getX2();
        double ty = intLine.getY2();

        double a = ((tx - sx) * (tx - sx)) + ((ty - sy) * (ty - sy));
        double b = 2d * (((tx - sx) * (sx - cx)) + ((ty - sy) * (sy - cy)));
        double c = ((cx * cx) + (cy * cy) + (sx * sx) + (sy * sy)) -
            (2d * ((cx * sx) + (cy * sy))) - (radius * radius);
        double discr = (b * b) - (4d * a * c);

        if (discr < 0d)
        {
            // line does not intersect
            return null;
        }
        else if (discr <= Double.MIN_VALUE) // epsilon test
        {
            // line is tangent
            double u = (-b) / (2 * a);

            Point2D res = calculatePointOnLine(u, intLine.getP1(),
                    intLine.getP2());

            if (((intersectionArea == Direction.NORTH_WEST) && (res.getX() <= cx) &&
                 (res.getY() <= cy)) ||
                ((intersectionArea == Direction.NORTH_EAST) && (res.getX() >= cx) &&
                 (res.getY() <= cy)) ||
                ((intersectionArea == Direction.SOUTH_EAST) && (res.getX() >= cx) &&
                 (res.getY() >= cy)) ||
                ((intersectionArea == Direction.SOUTH_WEST) && (res.getX() <= cx) &&
                 (res.getY() >= cy)))
            {
                return res;
            }
        }
        else
        {
            double discrsqr = Math.sqrt(discr);
            double u1 = (-b + discrsqr) / (2d * a); // first result
            double u2 = (-b - discrsqr) / (2d * a); // second result

            // there should be only one intersection point ...
            if ((0d <= u1) && (u1 <= 1.0001d))
            {
                Point2D res = calculatePointOnLine(u1, intLine.getP1(),
                        intLine.getP2());

                if (((intersectionArea == Direction.NORTH_WEST) && (res.getX() <= cx) &&
                     (res.getY() <= cy)) ||
                    ((intersectionArea == Direction.NORTH_EAST) && (res.getX() >= cx) &&
                     (res.getY() <= cy)) ||
                    ((intersectionArea == Direction.SOUTH_EAST) && (res.getX() >= cx) &&
                     (res.getY() >= cy)) ||
                    ((intersectionArea == Direction.SOUTH_WEST) && (res.getX() <= cx) &&
                     (res.getY() >= cy)))
                {
                    return res;
                }
            }

            if ((0d <= u2) && (u2 <= 1d))
            {
                Point2D res = calculatePointOnLine(u2, intLine.getP1(),
                        intLine.getP2());

                if (((intersectionArea == Direction.NORTH_WEST) && (res.getX() <= cx) &&
                     (res.getY() <= cy)) ||
                    ((intersectionArea == Direction.NORTH_EAST) && (res.getX() >= cx) &&
                     (res.getY() <= cy)) ||
                    ((intersectionArea == Direction.SOUTH_EAST) && (res.getX() >= cx) &&
                     (res.getY() >= cy)) ||
                    ((intersectionArea == Direction.SOUTH_WEST) && (res.getX() <= cx) &&
                     (res.getY() >= cy)))
                {
                    return res;
                }
            }
        }

        return null;
    }

    private static Point2D calculatePointOnLine(double u, Point2D s, Point2D t)
    {
        double diffX = t.getX() - s.getX();
        double diffY = t.getY() - s.getY();

        return new Point2D.Double(s.getX() + (u * diffX), s.getY() +
            (u * diffY));
    }

	/**
	 * @param circle
	 * @param double1
	 * @return
	 */
	public static Point2D intersectionPoint(Ellipse2D circle, Point2D point) {
		if (!FloatUtil.equals(circle.getWidth(), circle.getHeight()))
        {
            throw new IllegalArgumentException(
                "First parameter must be a circle, i.e. height and width " +
                "must be equal. Were: width=" + circle.getWidth() +
                "  height=" + circle.getHeight());
        }
		
		double dX = point.getX() - circle.getCenterX();
		double dY = point.getY() - circle.getCenterY();
		double tTan =  dY / dX;
		double arcT = Math.atan(tTan);
		double tSin =  (Double.isInfinite(tTan) ? 1 : Math.sin(arcT));
		double tCos =  (Double.isInfinite(tTan) ? 0 : Math.cos(arcT));
				
		tCos = Math.copySign(tCos, dX);
		tSin  =Math.copySign(tSin, dY);
		
		double R = circle.getHeight() / 2;
		Point2D.Double result = new Point2D.Double();
		result.x = circle.getCenterX() + R * tCos;
		result.y = circle.getCenterY() + R * tSin;
		
		return result;
	}

	/**
     * This method calculates the intersection point of the given line and 
     * one of the four lines of the nodes polygon.
     * <h1>source corner points:</h1>
     * <code>
     * corner 1  ---------------  corner 2 <br/>           
     *           |             |           <br/>
     *           |             |           <br/>
     * corner 4  ---------------  corner 3 <br/>
     *                                     <br/>
     *                  /\ corner 1        <br/>
     *       corner 4  /  \                <br/>
     *                 \  /  corner 2      <br/>
     *       corner 3   \/                 <br/>
     * </code>
     * @param node the source node.
     * @param source the source <code>Point2D</code> of the line.
     * @param target the target <code>Point2D</code> of the line.
     */
    public static Point2D intersectionPoint( Shape s, Point2D source, Point2D target, boolean enlargeLine ) {
    	Line2D line = new Line2D.Double(source, target);
    	
    	if (enlargeLine) {
    		Rectangle2D b = s.getBounds2D();
    		double dist = Math.max(b.getWidth(), b.getHeight()) * 2;
    		if (FloatUtil.equals(dist, 0.0d))
    			return source;
    		line = enlargeLine(line, dist);
    	}
		
    	PathIterator i = s.getPathIterator(null);
    	Point2D.Double p1 = new Point2D.Double();
    	Point2D.Double p2 = new Point2D.Double();
    	
    	double[] coordinates = new double[6];
    	Line2D line2 = new Line2D.Double();
    	double startX = 0;
    	double startY = 0;
    	
    	while (!i.isDone()) {
    		int type = i.currentSegment(coordinates);
    		switch (type) {
    		case PathIterator.SEG_MOVETO:
    			p2.x = startX = coordinates[0];
    			p2.y = startY = coordinates[1];
    			break;
    		case PathIterator.SEG_LINETO:
    		case PathIterator.SEG_QUADTO:
    		case PathIterator.SEG_CUBICTO:
    			p1.x = p2.x;
    			p1.y = p2.y;
    			p2.x = coordinates[0];
    			p2.y = coordinates[1];
    			
    			line2.setLine(p1, p2);
    			if ( line.intersectsLine( line2 ) ) {
    				return Util.intersectionPoint( line, line2 ); 
    			}
    			break;
    		case PathIterator.SEG_CLOSE:
    			p1.x = p2.x;
    			p1.y = p2.y;
    			p2.x = startX;
    			p2.y = startY;
    			
    			line2.setLine(p1, p2);
    			if ( line.intersectsLine( line2 ) ) {
    				return Util.intersectionPoint( line, line2 ); 
    			}
    		}
    		    
    		i.next();
    	}
    	
    	return source; 
    }
    /**
     * Enlarges the line by <code>length</code>.
     * The point <code>p1</code> is unchanged. 
     * @return
     */
    public static Line2D enlargeLine(Line2D l, double length) {
    	double x = l.getX2() - l.getX1();
    	double y = l.getY2() - l.getY1();
    	double theta = Math.atan2(y, x);
    	double r = l.getP2().distance(l.getP1()) + length;
    	
    	Point2D.Double newP2 = new Point2D.Double(l.getP1().getX() + r * Math.cos(theta), l.getP1().getY() + r * Math.sin(theta));
		l.setLine(l.getP1(), newP2);
		return l;
    }
}
