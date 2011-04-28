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

package org.tzi.use.gui.views.diagrams;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.classdiagram.EnumNode;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.xmlparser.LayoutTags;

/**
 * Represents a placeable node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public abstract class PlaceableNode implements Layoutable, Selectable {
    /**
     * Position and size of the node
     */
    protected Rectangle2D.Double bounds = new Rectangle2D.Double();
    
    /**
     * Indicates if the node is selected.
     */
    private boolean fIsSelected;
    /**
     * Indicates if the node is dragged.
     */
    private boolean fIsDragged;
    /**
     * The height this node should at least occupy
     */
    protected double minHeight;
    /**
     * The width this node should at least occupy
     */
    protected double minWidth;
    
    /**
     * Most objects need to do extra calculations
     * when drawn the first time.
     */
    protected boolean firstDraw = true;
    
    /**
     * Draws the placeable node to the given Graphics object.
     * When first drawn @link {@link #setRectangleSize(Graphics2D)} and {@link #onFirstDraw(Graphics2D)} are called
     * and afterwards {@link #onDraw(Graphics2D)}.
     * After the first draw only {@link #onDraw(Graphics2D)} is called. 
     * @param g Graphics object the node is drawn to.
     */
    public final void draw(Graphics2D g) {
    	if (firstDraw) {
    		setRectangleSize(g);
    		onFirstDraw(g);
    		firstDraw = false;
    	}
    	onDraw(g);
    }
    
    /**
     * Called once when the node is drawn the first time.
     * Can be used for example to calculate the width
     * with respect to text.  
     * @param g
     */
    protected void onFirstDraw(Graphics2D g) {}
    
    /**
     * Called when the node is drawn.
     * @param g
     */
    protected abstract void onDraw(Graphics2D g);
    
    /**
     * Sets the rectangle size of this node.
     * @param g Used Graphics.
     */
    public abstract void setRectangleSize( Graphics2D g );
    
    /**
     * Returns if this node is deleteable.
     */
    public abstract boolean isDeletable();    

    /**
     * Returns the height of this node.
     */
    public double getHeight() {
        return bounds.height;
    }
    /**
     * Sets the height of this node.
     * @param height new height of this node.
     */
    public void setHeight( double height ) {
        bounds.height = height;
    }
    
    /**
     * Returns the width of this node.
     */
    public double getWidth(){
        return bounds.width;
    }
    /**
     * Sets the width of this node.
     * @param width new width of this node.
     */
    public void setWidth( double width ) {
        bounds.width = width;
    }
    
    /**
     * Returns the height this node should at least occupy.
     * @return The set value.
     */
    public double getMinHeight() {
		return minHeight;
	}

    /**
     * Sets the height this node should at least occupy.
     * @param minHeight the new minimum height
     */
	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	/**
     * Returns the width this node should at least occupy.
     * @return The set value.
     */
	public double getMinWidth() {
		return minWidth;
	}

	/**
     * Sets the width this node should at least occupy.
     * @param minWidth the new minimum width
     */
	public void setMinWidth(double minWidth) {
		this.minWidth = minWidth;
	}

	/**
     * Sets the position of this node to x and y.
     */
    public void setPosition( double x, double y ) {
        double deltaX = x - bounds.x;
        double deltaY = y - bounds.x;
        
    	bounds.x = x;
        bounds.y = y;
        
        onPositionChanged(deltaX, deltaY);
    }

    /**
     * Sets the position of this node to x and y of point <code>p</code>.
     */
    public final void setPosition( Point2D p ) {
        setPosition(p.getX(), p.getY());
    }

    protected void onPositionChanged(double deltaX, double deltaY) {}
    
    /**
     * Gets the position of this node.
     */
    public Point2D getPosition() {
        return new Point2D.Double(bounds.x, bounds.y);
    }

    /**
     * Sets the position of this node to the new position while dragging.
     */
    public void setDraggedPosition( double deltaX, double deltaY ) {
        setPosition(bounds.x + deltaX, bounds.y + deltaY);
    }
    
    /**
     * Sets if this node is selected or not.
     */
    public void setSelected( boolean on ) {
        fIsSelected = on;
    }
    
    /**
     * Returns if this node is selected. 
     */
    public boolean isSelected() {
        return fIsSelected;
    }

    /**
     * Returns if this node is dragged. 
     */
    public boolean isDragged() {
        return fIsDragged;
    }
    
    /**
     * Sets if this node is dragged or not.
     */
    public void setDragged( boolean dragging ) {
        fIsDragged = dragging;
    }
    
    /**
     * Returns the dimension as a polygon of this node.
     */
    public Polygon dimension() {
        int[] xpoints = { (int)bounds.getMinX(), (int)bounds.getMaxX(), 
        				  (int)bounds.getMaxX(), (int)bounds.getMinX() };
        
        int[] ypoints = { (int)bounds.getMinY(), (int)bounds.getMinY(),
        				  (int)bounds.getMaxY(), (int)bounds.getMaxY() };
        
        int npoints = xpoints.length;
        return new Polygon( xpoints, ypoints, npoints );
    }
    
    /**
     * Returns the bounds rectangle of the node  
     */
    public Rectangle2D getBounds() {
    	return bounds;
    }
    
    /**
     * Returns the center point of the node.
     * @return
     */
    public Point2D getCenter() {
    	return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY() );
    }
    
    /**
     * Sets the position to the given center point
     */
    public void setCenter(Point2D center) {
    	setCenterX(center.getX());
    	setCenterY(center.getY());
    }
    
    /**
     * Sets the position to the given center point
     */
    public void setCenter(double x, double y) {
    	setCenterX(x);
    	setCenterY(y);
    }
    
    /**
     * Sets the center x coordinate of this node
     * @param x
     */
    public void setCenterX(double x) {
    	this.bounds.x = x - bounds.width / 2;
    }
    
    /**
     * Sets the center y coordinate of this node
     * @param y
     */
    public void setCenterY(double y) {
    	this.bounds.y = y - bounds.height / 2;
    }
    
    /**
     * Checks whether the node occupies a given point.
     */
    public boolean occupies( double x, double y ) {
        return bounds.contains( x, y );
    }
    
    /**
     * Returns the x coordinate of this node.
     */
    public double getX() {
    	return bounds.x;
    }
    
    /**
     * Returns the y coordinate of this node.
     */
    public double getY() {
    	return bounds.y;
    }
    
    /**
     * Sets the x coordinate of this node.
     * @param x New x coordinate.
     */
    public void setX( double x ) {
        bounds.x = x;
    }

    /**
     * Sets the y coordinate of this node.
     * @param y New y coordinate.
     */
    public void setY( double y ) {
        bounds.y = y;
    }
    
    /**
     * This method calculates the intersection point of the line from the center
     * position of this node to the <code>target</code> point and
     * one of the four lines of the nodes polygon.
     * 
     * @param node the source node.
     * @param source the source <code>Point2D</code> of the line.
     * @param target the target <code>Point2D</code> of the line.
     */
    Point2D getIntersectionCoordinate( Point2D target ) {
    	return getIntersectionCoordinate(this.getCenter(), target);
    }
    
    /**
     * This method calculates the intersection point of the given line and 
     * one of the four lines of the nodes polygon.
     * 
     * @param node the source node.
     * @param source the source <code>Point2D</code> of the line.
     * @param target the target <code>Point2D</code> of the line.
     */
    public Point2D getIntersectionCoordinate( Point2D source, Point2D target ) {
        Polygon polygon = this.dimension();
        
        int[] xpoints = polygon.xpoints;
        int[] ypoints = polygon.ypoints;
        
        // source corner points
        // corner 1  ---------------  corner 2              
        //           |             |
        //           |             |
        // corner 4  ---------------  corner 3
        //
        //                  /\ corner 1
        //       corner 4  /  \ 
        //                 \  /  corner 2
        //       corner 3   \/
        Point2D corner1 = new Point2D.Double(xpoints[0], ypoints[0]);
        Point2D corner2 = new Point2D.Double(xpoints[1], ypoints[1]);
        Point2D corner3 = new Point2D.Double(xpoints[2], ypoints[2]);
        Point2D corner4 = new Point2D.Double(xpoints[3], ypoints[3]);
        Line2D line1_2 = new Line2D.Double(corner1, corner2);
        Line2D line2_3 = new Line2D.Double(corner2, corner3);
        Line2D line3_4 = new Line2D.Double(corner3, corner4);
        Line2D line4_1 = new Line2D.Double(corner4, corner1);
        
        // line from source node to target node
        Line2D.Double line = new Line2D.Double( source, target );
        
        // getting the intersection coordinate 
        if ( line.intersectsLine( line1_2 ) ) {
            // cuts the line between corner 1 and 2 of the node
            return Util.intersectionPoint( line, line1_2 );
        } else if ( line.intersectsLine( line3_4 ) ) {
            // cuts the line between corner 3 and 4 of the node
            return Util.intersectionPoint( line, line3_4 );
        } else if ( line.intersectsLine( line4_1 ) ) {
            // cuts the line between corner 1 and 4 of the node
            return Util.intersectionPoint( line, line4_1 );
        } else if ( line.intersectsLine( line2_3 ) ) {
            // cuts the line between corner 2 and 3 of the node
            return Util.intersectionPoint( line, line2_3 );
        }
        
        // if no line is cut return the start point 
        // (both nodes lay on top of each other.
        return new Point( (int)source.getX(), (int)source.getY() );
    }
    
    /**
	 * Returns the direction where the line from the center (see {@link #getCenter()}) 
	 * of this node to the <code>targetPoint</code> intersects this node / shape.
	 * The default implementation uses the bounds of the node return by {@link #getBounds()}.   
	 * @param targetPoint The end point for the line from the center of this node
	 * @return {@link Direction#NORTH}, {@link Direction#EAST}, {@link Direction#SOUTH}, {@link Direction#WEST} or <code>null</code> if
	 * there is no intersection.
	 */
	public Direction getIntersectionDirection(Point2D targetPoint) {
		Rectangle2D b = getBounds();
		Line2D.Double line = new Line2D.Double(getCenter(), targetPoint);
		Line2D.Double boundsLine = new Line2D.Double();
		
		// Top line
		boundsLine.setLine(
				b.getMinX(), b.getMinY(), // Top-Left
				b.getMaxX(), b.getMinY());// Top-Right

		if (line.intersectsLine( boundsLine )) {
			return Direction.NORTH;
		}
		
		// Bottom line
		boundsLine.setLine(
				b.getMinX(), b.getMaxY(), // Bottom-Left
				b.getMaxX(), b.getMaxY());// Bottom-Right

		if (line.intersectsLine( boundsLine )) {
			return Direction.SOUTH;
		}
		
		// Left line
		boundsLine.setLine(
				b.getMinX(), b.getMinY(), // Top-Left
				b.getMinX(), b.getMaxY());// Bottom-Left

		if (line.intersectsLine( boundsLine )) {
			return Direction.WEST;
		}
		
		// Right line
		boundsLine.setLine(
				b.getMaxX(), b.getMinY(), // Top-Right
				b.getMaxX(), b.getMaxY());// Bottom-Right

		if (line.intersectsLine( boundsLine )) {
			return Direction.EAST;
		}
		
		return Direction.UNKNOWN;
	}
	
    public abstract String name();
    
    /**
     * Saves placement information about this placeable node.
     * @param hidden If this node should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public String storePlacementInfo( boolean hidden ) {
        StringBuilder xml = new StringBuilder();
        String nl = "\n";
        
        xml.append(LayoutTags.NODE_O);
        
        if ( this instanceof ObjectNode ) {
            xml.append(" type=\"Object\">").append(nl);
        } else if ( this instanceof ClassNode ) {
            xml.append(" type=\"Class\">").append(nl);
        } else if ( this instanceof EnumNode ) {
            xml.append(" type=\"Enumeration\">").append(nl);
        } else {
            xml.append(" type=Something Went Wrong>").append(nl);
        } 
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.NAME_O).append(name()) 
               .append(LayoutTags.NAME_C).append(nl);
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.X_COORD_O)
        	   .append( Double.toString( getCenter().getX() )).append(LayoutTags.X_COORD_C).append(nl);
        	   
        xml.append(LayoutTags.INDENT).append(LayoutTags.Y_COORD_O)
        	   .append( Double.toString( getCenter().getY() )).append(LayoutTags.Y_COORD_C).append(nl);
        
        xml.append(LayoutTags.INDENT).append(LayoutTags.HIDDEN_O).append(hidden) 
               .append(LayoutTags.HIDDEN_C).append(nl);
        
        xml.append(LayoutTags.NODE_C).append(nl);
        
        return xml.toString();
    }
}