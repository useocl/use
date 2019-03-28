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

package org.tzi.use.gui.views.diagrams.elements;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.RestoreLayoutException;
import org.tzi.use.gui.views.diagrams.Layoutable;
import org.tzi.use.gui.views.diagrams.PositionChangedListener;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyDeserializer;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyFixed;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.util.Util;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.util.FloatUtil;
import org.tzi.use.util.MathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents a placeable node in a diagram. 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public abstract class PlaceableNode implements Layoutable, Selectable {
	
	/**
	 * The strategy used to place this node.
	 * Default is fixed, i. e., not calculated.
	 */
	protected PositionStrategy strategy = StrategyFixed.instance;
	
    /**
     * Position and size of the node
     */
	private final Rectangle2D.Double bounds = new Rectangle2D.Double(0,0,0,0);
    
    /**
     * The unselected back color of this node
     */
    protected Color backColor;
    /**
     * The back color of the node if selected.
     */
    protected Color backColorSelected;
    
    /**
     * The rectangle color of this node
     */
    protected Color frameColor;
    
    /**
     * The text color of this node
     */
    protected Color textColor;
    
    /**
     * The font used to display
     * text in the node.
     */
    protected Font font;
    
    /**
     * Indicates if the node is selected.
     */
    private boolean fIsSelected;
    /**
     * The height this node should at least occupy
     */
    protected double minHeight;
    /**
     * The width this node should at least occupy
     */
    protected double minWidth;
    
    /**
     * <code>true</code>, if hidden by the user.
     */
    private boolean isHidden = false;
    
    /**
	 * Is absent in current view, i. e.,
	 * it cannot be made visible by the user.
	 */
    
    private boolean isAbsentFromGraph = false;
    
    /**
     * <code>true</code>, if this PlacebleNode is an association class/object.
     */
    private boolean isAssocClassOrObject = false;
    
    /**
     * The drawing engine uses this map to register
     * different types of required heights.
     * For each required height a supplier is given, to allow
     * the drawing engine to change a value in a specific context.
     * The operation {@link #getRequiredHeight()} uses the maximum
     * value present in the entry set or 0 if the map is empty. 
     */
    private Map<String,Double> requiredHeight = Collections.emptyMap();
    
    /**
     * The drawing engine uses this map to register
     * different types of required widths.
     * For each required width a supplier is given, to allow
     * the drawing engine to change a value in a specific context.
     * The operation {@link #getRequiredWidth()} uses the maximum
     * value present in the entry set or 0 if the map is empty. 
     */
    private Map<String,Double> requiredWidth = Collections.emptyMap();
    
    /**
     * Most objects need to do extra calculations
     * when drawn the first time.
     */
    protected boolean firstDraw = true;
        
    /**
     * Is the node initialized? 
     */
    private boolean initialized = false;
    
    private ResizeNode[] resizeNodes = new ResizeNode[0];
    
    private boolean resizeAllowed = false;
    
    /**
     * <code>true</code>, if the size of the node is not calculated.
     */
    private boolean isSizeCalculated = true;
    
    /**
     * List of listeners which get notified if this node changes position or bounds, etc. 
     */
    protected EventListenerList fListenerList = new EventListenerList();
    
    /**
     * Returns a unique ID of a node inside a single diagram.
     * The id must be the same for all equal model elements
     * and all diagram instances.
     * <P>This ID is used to restore a node.
     * Further, restoring linked nodes or strategies using
     * this id is easier.</p>
     * @return
     */
    public abstract String getId();
    
    /**
	 * @return the relativeX
	 */
	public PositionStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param relativeX the relativeX to set
	 */
	public void setStrategy(PositionStrategy strategy) {
		this.strategy.dispose();
		this.strategy = strategy;
		this.updatePosition();
	}
	
    /**
	 * @return the 
	 */
	public boolean isInitialized() {
		return this.initialized;
	}

	/**
     * Draws the placeable node to the given Graphics object.
     * When first drawn @link {@link #doCalculateSize(Graphics2D)} and 
     * {@link #onFirstDraw(Graphics2D)} are called
     * and afterwards {@link #onDraw(Graphics2D)}.
     * After the first draw only {@link #onDraw(Graphics2D)} is called. 
     * @param g Graphics object the node is drawn to.
     */
    public final void draw(Graphics2D g) {
    	if (!willBeDrawn()) return;
    	
    	onDraw(g);
    	if (isSelected() && isResizable())
    		drawResizeNodes(g);
    }
    
    /**
     * Called when the node is drawn.
     * @param g
     */
    protected abstract void onDraw(Graphics2D g);
    
    private void drawResizeNodes(Graphics2D g) {
		g.setColor(Color.BLACK);
    	for (ResizeNode rn : this.resizeNodes) {
			rn.draw(g);
		}
	}
    
    /**
	 * @return the backColor
	 */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * @param backColor the backColor to set
	 */
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * Returns <code>true</code>, if this node is drawn.
	 * For this base class it is <code>true</code> if it is not hidden.
	 * Subclasses can override this method to add additional
	 * requirements for the node to be drawn.
	 * For example, EdgeProperties are only drawn if the edge is visible, too.
	 * @return <code>true</code>, if this node is drawn.
	 */
	public boolean isVisible() {
		return !isHidden();
	}

	/**
	 * @return the isHidden
	 */
	public boolean isHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden the isHidden to set
	 */
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	
	/**
     * <code>true</code>, if this PlacebleNode is an association class/object.
     */
	public boolean isAssocClassOrObject() {
		return isAssocClassOrObject;
	}

	/**
	 * Marks this PlacebleNode as an association class/object.
	 */
	public void setAssocClassOrObject() {
		this.isAssocClassOrObject = true;
	}

	/**
	 * @return the foreColor
	 */
	public Color getFrameColor() {
		return frameColor;
	}

	/**
	 * @param foreColor the foreColor to set
	 */
	public void setFrameColor(Color foreColor) {
		this.frameColor = foreColor;
	}

	/**
	 * @return the backColorSelected
	 */
	public Color getBackColorSelected() {
		return backColorSelected;
	}

	/**
	 * @param backColorSelected the backColorSelected to set
	 */
	public void setBackColorSelected(Color backColorSelected) {
		this.backColorSelected = backColorSelected;
	}

	/**
	 * @return the textColor
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
     * Sets the rectangle size of this node.
     * @param g Used Graphics.
     */
    protected abstract void doCalculateSize( Graphics2D g );
    
    /**
     * Sets the rectangle size of this node and
     * notifies listeners.
     * @param g Used Graphics.
     */
    public final void calculateSize(Graphics2D g) {    	    	
    	doCalculateSize(g);
    	this.updatePosition();
    }
    
    public boolean isResizable() {
    	return false;
    }
    
    public boolean isSizeCalculated() {
    	return this.isSizeCalculated;
    }
    
    public void setSizeIsCalculated(boolean newValue) {
    	this.isSizeCalculated = newValue;
    }
    
    /**
     * Returns the height of this node.
     */
    @Override
	public double getHeight() {
        return bounds.height;
    }
    
    public void setExactHeight( double height ) {
    	setExactBounds(getWidth(), height);
    }
    
    public void setCalculatedHeight( double height ) {
    	setCalculatedBounds(getWidth(), height);
    }
    
    public void setCalculatedBounds(double width, double height) {
    	if (isSizeCalculated()) {
    		setBounds(width, height);
    	}
    }
    
    public void setExactBounds(double width, double height) {
    	this.isSizeCalculated = false;
    	setBounds(width, height);
    }
    
    private void setBounds(double width, double height) {
    	Rectangle2D oldBounds = (Rectangle2D)getBounds().clone();
    	bounds.width =  MathUtil.max(getRequiredWidth(), getMinWidth(), width);
    	bounds.height = MathUtil.max(getRequiredHeight(), getMinHeight(), height);
    	    	
    	if (!FloatUtil.equals(oldBounds.getHeight(), bounds.getHeight()) || !FloatUtil.equals(oldBounds.getWidth(), bounds.getWidth())) {
    		this.updatePosition();
    		fireBoundsChanged(oldBounds, getBounds());
    	}
    }
    
    /**
     * Returns the width of this node.
     */
    @Override
	public double getWidth() {
        return getBounds().getWidth();
    }
    
    /**
     * Sets the calculated width of this node.
     * @param width new width of this node.
     */
    public void setCalculatedWidth( double width ) {
    	setCalculatedBounds(width, getHeight());
    }
    
    /**
     * Sets the exact width of this node.
     * @param width new width of this node.
     */
    public void setExactWidth( double width ) {
    	setExactBounds(width, getHeight());
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
		this.setBounds(getWidth(), getHeight());
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
		this.setBounds(getWidth(), getHeight());
	}
	
	/**
	 * Returns the actual required height of this node.
	 * This value is set by the drawing engine.
	 * @return
	 */
	public double getRequiredHeight() {
		return (this.requiredHeight.isEmpty() ? 0 : Collections.max(this.requiredHeight.values()));
	}
	
	/**
	 * Sets the required height of this node.
	 * The difference to {@link #setMinHeight(double)}
	 * is, that the drawing engine might change this
	 * value.   
	 * @param newValue
	 */
	public void setRequiredHeight(String supplier, double newValue) {
		if (this.requiredHeight.isEmpty())
			this.requiredHeight = new HashMap<String, Double>();
		
		this.requiredHeight.put(supplier, Double.valueOf(newValue));
		
		// May update to newValue
		this.setBounds(getWidth(), getHeight());
	}
	
	/**
	 * Returns the actual required width of this node.
	 * This value is set by the drawing engine.
	 * @return
	 */
	public double getRequiredWidth() {
		return (this.requiredWidth.isEmpty() ? 0 : Collections.max(this.requiredWidth.values()));
	}
	
	/**
	 * Sets the required width of this node.
	 * The difference to {@link #setMinWidth(double)}
	 * is, that the drawing engine might change this
	 * value.   
	 * @param newValue
	 */
	public void setRequiredWidth(String supplier, double newValue) {
		if (this.requiredWidth.isEmpty()) {
			this.requiredWidth = new HashMap<>();
		}
		
		this.requiredWidth.put(supplier, Double.valueOf(newValue));
		// May update to newValue
		this.setBounds(getWidth(), getHeight());
	}
	
	/**
     * Sets the position of this node to x and y.
     * This operation is used by all other position related setter operations.
     * @param x The new x-coordinate
     * @param y The new y-coordinate
     **/
    public final void setPosition( double x, double y ) {
    	if (this.strategy.isCalculated()) {
    		this.updatePosition();
    	} else {
    		internalSetPosition(x, y);
    	}
    }
    
    protected final void internalSetPosition(Point2D pos) {
    	internalSetPosition(pos.getX(), pos.getY());
    }
    
    protected void internalSetPosition(double x, double y) {
    	double deltaX = x - bounds.x;
        double deltaY = y - bounds.y;
        
        if (Math.abs(deltaX) < 0.0001d && Math.abs(deltaY) < 0.0001d)
        	return;
        	
    	bounds.x = x;
        bounds.y = y;
        
    	firePositionChanged(deltaX, deltaY);
    }

    
    /**
     * Sets the position of this node to x and y of point <code>p</code>.
     */
    public final void setPosition( Point2D p ) {
        setPosition(p.getX(), p.getY());
    }

    /**
     * Notifies the listeners that the position of this
     * node has changed.
     * @param deltaX
     * @param deltaY
     */
    protected void firePositionChanged(double deltaX, double deltaY) {
    	Object[] listeners = fListenerList.getListenerList();
		Point2D position = getPosition();
		
		for (int i = listeners.length-2; i >= 0; i -= 2) {
	        if (listeners[i] == PositionChangedListener.class) {
	        	try {
	        		((PositionChangedListener)listeners[i+1]).positionChanged(this, position, deltaX, deltaY);
	        	} catch (Exception ex) { 
	        		System.out.println(ex);
	        	}
	        }          
	    }
    }
    
    /**
     * Notifies the bounds of this
     * node have changed.
     */
    protected void fireBoundsChanged(Rectangle2D oldBounds, Rectangle2D newBounds) {
    	if (oldBounds.equals(newBounds)) return;
    	
    	Object[] listeners = fListenerList.getListenerList();
				
		for (int i = listeners.length-2; i >= 0; i -= 2) {
	        if (listeners[i] == PositionChangedListener.class) {
	        	try {
	        		((PositionChangedListener)listeners[i+1]).boundsChanged(this, oldBounds, newBounds);
	        	} catch (Exception ex) { }
	        }          
	    }
    }
    
    /**
     * Gets the position of this node.
     */
    public Point2D getPosition() {
        return new Point2D.Double(getBounds().getX(), getBounds().getY());
    }

    public void addPositionChangedListener(PositionChangedListener listener) {
    	fListenerList.add(PositionChangedListener.class, listener);
	}
	
	public void removePositionChangedListener(PositionChangedListener listener) {	
		fListenerList.remove(PositionChangedListener.class, listener);
	}
	
    /**
     * Sets the position of this node to the new position while dragging.
     */
    public void setDraggedPosition( double deltaX, double deltaY ) {
    	Point2D pos = strategy.calculateDraggedPosition(this, deltaX, deltaY);
    	verifyUpdatePosition(pos);
    	setPosition(pos);
    }
    
    /**
     * Moves the node to the new x position.
     * While {@link #setX(double)} is used by
     * the drawing engine to set the current node position
     * without notifying a possible stateful positioning strategy
     * this operation passes the move request to the strategy.
     * Therefore, an update of the position considers the new
     * offset to the computed strategy value.
     */
    public void moveToX(double x) {
    	this.strategy.moveTo(this, x, getY());
    }
    
    public void moveToY(double y) {
    	this.strategy.moveTo(this, getX(),  y);
    }
    
    public void moveToPosition(Point2D position) {
    	moveToPosition(position.getX(), position.getY());
    }
    
    public void moveToPosition(double x, double y) {
    	this.strategy.moveTo(this, x, y);
    }
    
    /**
	 * Updates the position of way points which are
	 * not manually positioned.
	 */
	public void updatePosition() {
		Point2D res = strategy.calculatePosition(this);
		verifyUpdatePosition(res);
		
		if (Double.isNaN(res.getX()) || Double.isNaN(res.getY())) {
			return;
		}
		
		this.internalSetPosition(res);
	}
	
	/**
	 * Sets the given point <code>updatedCenter</code> to left top coordinates
	 * using the width and height of the node.
	 * @param updatedCenter
	 */
	protected void verifyUpdatePosition(Point2D updatedCenter) {
		updatedCenter.setLocation(updatedCenter.getX() - getWidth() / 2, updatedCenter.getY() - getHeight() / 2);
	}
	
    /**
     * Sets if this node is selected or not.
     */
    @Override
	public void setSelected( boolean on ) {
        fIsSelected = on;
        configureResizeNodes();
    }
    
    @Override
	public void setResizeAllowed(boolean allowed) {
		this.resizeAllowed = allowed;
		configureResizeNodes();
	}

	@Override
	public boolean getResizeAllowed() {
		return this.resizeAllowed;
	}

	/**
     * Returns if this node is selected. 
     */
    @Override
	public boolean isSelected() {
        return fIsSelected;
    }

    private void configureResizeNodes() {
    	if (!isResizable()) return;
        
    	for (ResizeNode rn : this.resizeNodes) {
			rn.dispose();
		}
    	
        if (isSelected() && getResizeAllowed()) {
			this.resizeNodes = new ResizeNode[8];
			int index = 0;
			
			for (Direction d : Direction.getDefinedDirections()) {
				ResizeNode rn = new ResizeNode(this,d);
				this.resizeNodes[index++] = rn;
				rn.setStrategy(new StrategyRelativeToCorner(rn, this, d, 2, DeltaBasis.ABSOLUTE, 2, DeltaBasis.ABSOLUTE));
			}
		} else {
			this.resizeNodes = new ResizeNode[0];
		}
    }
    
    /**
     * Returns the dimension as a polygon of this node.
     */
    public Shape getShape() {
        return getBounds();
    }
    
    /**
     * Returns the bounds rectangle of the node  
     */
    public Rectangle2D getBounds() {
    	return bounds;
    }
    
    public Area getArea() {
    	return new Area(getBounds());
    }
    
    /**
     * Returns the center point of the node.
     * Modifying the return value does not influence this node.
     * @return
     */
    @Override
	public Point2D getCenter() {
    	Rectangle2D currentBounds = getBounds();
    	return new Point2D.Double(currentBounds.getCenterX(), currentBounds.getCenterY() );
    }
    
    /**
     * Sets the position to the given center point
     */
    public final void setCenter(Point2D center) {
    	setCenter(center.getX(), center.getY());
    }
    
    /**
     * Sets the position to the given center point
     */
    @Override
	public final void setCenter(double x, double y) {
    	Point2D.Double newPosition = new Point2D.Double();
    	newPosition.x = x - getBounds().getWidth() / 2;
    	newPosition.y = y - getBounds().getHeight() / 2;
    	setPosition(newPosition);
    }
    
    /**
     * Sets the center x coordinate of this node
     * @param x
     */
    public void setCenterX(double x) {
    	setX(x - getBounds().getWidth() / 2);
    }
    
    /**
     * Sets the center y coordinate of this node
     * @param y
     */
    public void setCenterY(double y) {
    	setY(y - getBounds().getHeight() / 2);
    }
    
    /**
     * Checks whether the node occupies a given point.
     */
    public boolean occupies( double x, double y ) {
        return getShape().contains( x, y );
    }
    
    /**
     * Returns the x coordinate of this node.
     */
    public double getX() {
    	return getBounds().getX();
    }
    
    /**
     * Returns the x coordinate of this node as an integer.
     * This unifies the handling of double and int in drawing. 
     */
    public int getXInteger() {
    	return (int)Math.round(getX());
    }
    
    /**
     * Returns the y coordinate of this node.
     */
    public double getY() {
    	return getBounds().getY();
    }
    
    /**
     * Returns the y coordinate of this node as an integer.
     * This unifies the handling of double and int in drawing. 
     */
    public int getYInteger() {
    	return (int)Math.round(getY());
    }
    
    /**
     * Sets the x coordinate of this node.
     * @param x New x coordinate.
     */
    public final void setX( double x ) {
        setPosition(x, getY());
    }

    /**
     * Sets the y coordinate of this node.
     * @param y New y coordinate.
     */
    public final void setY( double y ) {
    	setPosition(getX(), y);
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
    public final Point2D getIntersectionCoordinate( Point2D target ) {
    	if (FloatUtil.equals(getWidth(), 0.0d) || FloatUtil.equals(getHeight(), 0.0d))
    		return getPosition();
    	
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
        return Util.intersectionPoint(this.getShape(), source, target, true);
    }
    
    /**
     * Calculates the length of a line from the center
     * to the bounds of this node using the angle <code>alpha</code>.
	 * @param alpha The angle alpha in degrees.
	 * @return
	 */
	public Point2D getIntersectionCoordinate(double alpha) {
		// Create a point that is outside the bounds
		double length = Math.max(getWidth(), getHeight()) * 2;
		final Point2D center = getCenter();
		
		if (FloatUtil.equals(length, 0.0d))
			return center;
		
		Point2D.Double calPoint = new Point2D.Double(0, 0);
		calPoint.x += length;
		AffineTransform tf = new AffineTransform();
		tf.rotate(Math.toRadians(alpha));
		tf.transform(calPoint, calPoint);
		
		calPoint.x += center.getX();
		calPoint.y += center.getY();
		
		return getIntersectionCoordinate(calPoint);
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
		
		if (getCenter().equals(targetPoint))
			return Direction.CENTER;
		
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
		
		return null;
	}
	
	/**
	 * Draws the text centered inside the bounds returned by {@link #getBounds()}.
	 * The text is underlined if {@link fEdge#isLink()} returns true.
	 * @param g
	 */
	protected void drawTextCentered(String text, Graphics2D g) {
		TextLayout layout = getTextLayout(text, g);
		drawTextCentered(text, layout, g);
	}
	
	protected void drawTextCentered(String text, Rectangle2D bounds, Graphics2D g) {
		TextLayout layout = getTextLayout(text, g);
		drawTextCentered(text, bounds, layout, g);
	}
	
	protected void drawTextCentered(String text, TextLayout layout, Graphics2D g) {		
		drawTextCentered(text, getBounds(), layout, g);
	}
		
	protected void drawTextCentered(String text, Rectangle2D bounds, TextLayout layout, Graphics2D g) {
		Rectangle2D textBounds = layout.getBounds();
		
		float x = Math.round((bounds.getCenterX() - textBounds.getWidth()  / 2));
		float y = Math.round((float)(bounds.getCenterY() + (layout.getAscent() + layout.getDescent()) / 2 - layout.getDescent()));
		
		g.drawString(text, x, y);
	}
	
	protected void drawTextCentered(Graphics2D g, String text, double x, double y, double width) {
    	double textWidth = g.getFontMetrics().stringWidth(text);
    	x += width / 2 - textWidth / 2; 
        g.drawString( text, Math.round(x), Math.round(y) );
    }
	
	protected TextLayout getTextLayout(String text, Graphics2D g) {
		if (g == null)
			throw new NullPointerException("Textlayout was not initialized.");
			
		FontRenderContext frc = g.getFontRenderContext();
		Font font;
	    font = g.getFont();
	    
	    return new TextLayout(text, font, frc);
	}
	
	/**
	 * If a node contained in the drawing graph has
	 * additional nodes, like e. g., a name node,
	 * this needs to be returned here if it occupies the area
	 * behind the x y coordinates. 
	 * Used to determine if such a node has been selected.
	 * @param x
	 * @param y
	 * @return
	 */
	public PlaceableNode getRelatedNode(double x, double y) {
		for (ResizeNode rn : this.resizeNodes) {
			if (rn.occupies(x, y))
				return rn;
		}
		
		return null;
	}
	
    public abstract String name();
    
    protected abstract String getStoreType();
    
    protected String getStoreKind() { return null; }
    
    protected String getStoreElementName() { return LayoutTags.NODE; }
    
    /**
     * Override, if more then name, position and type data
     * has to be stored
     * @param nodeElement
     */
    protected void storeAdditionalInfo( PersistHelper helper, Element nodeElement, boolean hidden) {}
    
    /**
     * Saves placement information about this placeable node.
     * @param hidden If this node should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public final void storePlacementInfo( PersistHelper helper, Element parent, boolean hidden ) {
        Document doc = parent.getOwnerDocument();
        
        Element nodeElement = doc.createElement(this.getStoreElementName());
        nodeElement.setAttribute("type", this.getStoreType());
        nodeElement.setAttribute("id", this.getId());
        
        String kind = getStoreKind();
        if (kind != null && ! kind.equals("")) {
        	nodeElement.setAttribute("kind", kind);
        }
        
        helper.appendChild(nodeElement, LayoutTags.NAME, name());
        
        nodeElement.setAttribute("sizeCalculated", Boolean.toString(this.isSizeCalculated()));
        
        if (!this.isSizeCalculated()) {
        	nodeElement.setAttribute("width", Double.toString(getWidth()));
        	nodeElement.setAttribute("height", Double.toString(getHeight()));
        }
        
        // The strategy stores position information 
        Element strategyElement = helper.createChild(nodeElement, "strategy");
        strategy.storeAdditionalInfo(this, helper, strategyElement, hidden);
        
        storeAdditionalInfo(helper, nodeElement, hidden);
        
        helper.appendChild(nodeElement, LayoutTags.HIDDEN, String.valueOf(!isVisible()));
        parent.appendChild(nodeElement);
    }
    
    public final void restorePlacementInfo(PersistHelper helper, int version) {    	
    	if (helper.hasAttribute("sizeCalculated") && !Boolean.valueOf(helper.getAttributeValue("sizeCalculated"))) {
    		setExactHeight(Double.valueOf(helper.getAttributeValue("height")));
    		setExactWidth (Double.valueOf(helper.getAttributeValue("width")));
    	}
    	
    	helper.toFirstChild("strategy");
    	
    	PositionStrategy strategy;
		try {
			strategy = StrategyDeserializer.restoreStrategy(this, helper, version);
			strategy.recoverNonSerializedInformation(getStrategy());
			setStrategy(strategy);
		} catch (RestoreLayoutException e) {
			// Fine
		} finally {
			helper.toParent();
		}
    	
		setHidden(helper.getElementBooleanValue(LayoutTags.HIDDEN));
		
    	restoreAdditionalInfo(helper, version);
    }

	protected void restoreAdditionalInfo(PersistHelper helper, int version) { }

	/**
	 * Called if the node is drawn the first time.
	 */
	public final void initialize() {	
		onInitialize();
		this.initialized = true;
	}
	
	protected void onInitialize() { }
	
	/**
	 * Called if a node is destroyed
	 */
	public void dispose() {
		this.strategy.dispose();
	}

	/**
	 * @param allNodes
	 */
	public void collectChildNodes(Map<String, PlaceableNode> allNodes) {
		
	}

	/**
	 * @return
	 */
	public Cursor getCursor() {
		return Cursor.getDefaultCursor();
	}

	/**
	 * Returns the bounds of the node rounded to int values. 
	 * @return
	 */
	protected Rectangle2D.Double getRoundedBounds() {
		Rectangle2D.Double roundedBounds = new Rectangle2D.Double();
	    Rectangle2D bounds = getBounds();
	    roundedBounds.x = Math.round(bounds.getX());
	    roundedBounds.y = Math.round(bounds.getY());
	    roundedBounds.height = Math.round(bounds.getHeight());
	    roundedBounds.width = Math.round(bounds.getWidth());
		return roundedBounds;
	}

	/**
	 * Returns a user friendly text to be shown inside
	 * a menu, e. g., 'object xy' for 'Hide object xy'. 
	 * @return
	 */
	public String getTextForMenu() {
		return name();
	}

	/**
	 * @return the isAbsentFromGraph
	 */
	public boolean isAbsentFromGraph() {
		return isAbsentFromGraph;
	}

	public boolean willBeDrawn() {
		return isVisible() && !isAbsentFromGraph;
	}

	/**
	 * @param isAbsentFromGraph the isAbsentFromGraph to set
	 */
	public void setAbsentFromGraph(boolean isAbsentFromGraph) {
		this.isAbsentFromGraph = isAbsentFromGraph;
	}
}