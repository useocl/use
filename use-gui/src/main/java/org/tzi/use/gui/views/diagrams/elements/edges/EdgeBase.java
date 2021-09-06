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

package org.tzi.use.gui.views.diagrams.elements.edges;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.graph.DirectedEdgeBase;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.PositionChangedListener;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint.ResetStrategy;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.w3c.dom.Element;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * Base class of all edge types in the diagram.
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public abstract class EdgeBase extends DirectedEdgeBase<PlaceableNode> implements Selectable {
    
	/**
	 * The maximum size of a reflexive edge.
	 * Reflexive edges grow with the size of the node until
	 * they reach this value. 
	 */
	protected static final double MAX_REFLEXIVE_SIZE = 200;
	
	/**
	 * Enumeration of possible owners
	 * of a property, e. g., the edge itself owns an association
	 * name, whereas a role name is owned by the source or target end. 
	 * 
	 * @author Lars Hamann
	 *
	 */
	public enum PropertyOwner {
		EDGE,
		SOURCE,
		TARGET
	}
	
    /**
     * Determines if this edge is selected or not.
     */
    private boolean fIsSelected;
        
    /**
     * Name of this edge.
     */
    protected String fEdgeName;
    
    /**
     * Because of the qualifier nodes, calculation has do be done twice
     * the first time.
     * This attribute handles this. 
     */
    private boolean firstCalculation = true;
    
    /**
     * List of all way points (including {@link #fSourceWayPoint} and {@link #fTargetWayPoint})
     * laying on this edge.
     */
    protected List<WayPoint> fWayPoints;
    
    /**
     * Source way point of this edge (the drawn line starts here).
     */
    protected AttachedWayPoint fSourceWayPoint;
    
    /**
     * Target way point of this edge (the drawn line ends here).
     */
    protected AttachedWayPoint fTargetWayPoint;
    
    /**
     * Grouped properties which are needed for de-/serialization.
     */
    protected Multimap<PropertyOwner, EdgeProperty> edgeProperties;
    
    /**
     * Options of the diagram in which this edge is drawn.
     */
    protected DiagramOptions fOpt;
    
    /**
     * When source and target are moved, we also want to move the user defined way points.
     */
    protected final boolean completeEdgeMoveMovesUserWayPoints;
    
    /**
     * Position of a reflexive edge relative to the center of the connected node.
     */
    protected Direction reflexivePosition = null;
    
    /**
     * Specifies the offset to the connected nodes, if multiple edges
     * are connecting the same nodes.
     */
    protected double offset;
    
    private boolean initialized = false;

    private boolean isHidden = false;

	/**
     * Constructs a new edge.
     * @param source The source node of the edge.
     * @param target The target node of the edge.
     * @param edgeName The name of the edge.
     * @param diagram The diagram this edge belongs to.
     * @param completeEdgeMoveMovesUserWayPoints If true, user defined way points are moved by the edge if
     * source and target are selected. 
     */
    public EdgeBase( PlaceableNode source, PlaceableNode target, String edgeName,
                     DiagramOptions opt, boolean completeEdgeMoveMovesUserWayPoints ) {
        super( source, target );
        fOpt = opt;
        fEdgeName = edgeName;
        fSource = source;
        fTarget = target;
        this.completeEdgeMoveMovesUserWayPoints = completeEdgeMoveMovesUserWayPoints;
    }

    public boolean isInitialized() {
    	return this.initialized;
    }
    
	/**
	 * Returns <code>true</code>, if the edge is going to be drawn.
	 * An edge is going to be drawn, if it is not hidden by the user {@link #isHidden}
	 * and both connected nodes are visible.
	 * @return
	 */
	public boolean isVisible() {
		return !isHidden && source().isVisible() && target().isVisible();
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
     * Adds a node to the list of nodes on this edge.
     * @param node Node to be added.
     * @param n1 Behind this node <code>node</code> will be added to 
     *           the list of nodes.
     */
    private void addWayPoint( WayPoint wayPoint, WayPoint after ) {
        addWayPoint( fWayPoints.indexOf(after) + 1, wayPoint );
    }
    
    protected void addWayPoint(WayPoint p) {
    	addWayPoint(fWayPoints.size(), p);
    }
    
    protected void addWayPoint(int index, WayPoint p) {
    	p.setID(index);
    	fWayPoints.add( index, p );
    	    	
    	if (fWayPoints.size() > 1) {
    		WayPoint wp = null;
    		if (index - 1 >= 0) {    	
    			wp = fWayPoints.get(index - 1);
    			wp.setNextWayPoint(p);
    			p.setPreviousWayPoint(wp);
    		}
    		
    		if ( index + 1 < fWayPoints.size() ) {
    			wp = fWayPoints.get(index + 1);
    			wp.setPreviousWayPoint(p);
    			p.setNextWayPoint(wp);
    		}
    	}
    	
    	for (; index < fWayPoints.size(); ++index) {
    		fWayPoints.get(index).setID(index);
    	}
    }
    
    public final void initialize() {
		if (!initialized) {
			initializeWayPoints();
			this.edgeProperties = HashMultimap.create(3, 3);
			initializeProperties(this.edgeProperties);
			initializeFinal();
		}
        this.initialized = true;
    }
    
    protected void initializeWayPoints() {
    	fWayPoints = new ArrayList<WayPoint>();
        
    	initializeSourceWayPoint();
    	initializeTargetWayPoint();
    	
        addWayPoint(fSourceWayPoint);
        addWayPoint(fTargetWayPoint);
                
        if ( isReflexive() ) {
        	WayPoint p;
        	final Direction refPosition = getReflexivePosition();
        	StrategyRelativeToCorner s;

        	ResetStrategy resetSource = new ResetStrategy() {
				@Override
				public PositionStrategy reset() {
					StrategyRelativeToCorner ps = new StrategyRelativeToCorner(fSourceWayPoint, fSource, refPosition, 0, DeltaBasis.ABSOLUTE, -0.3333, DeltaBasis.RELATIVE);
					ps.setMaxDeltaX(0);
					ps.setMaxDeltaY(MAX_REFLEXIVE_SIZE * 0.3333);
					return ps;
				}
			};
        	// p1 Source stays on x bounds
        	s = (StrategyRelativeToCorner)resetSource.reset();
        	fSourceWayPoint.setResetStrategy(resetSource);
        	fSourceWayPoint.setStrategy(s);
        	
        	// p2
        	p = new WayPoint(this, WayPointType.REFLEXIVE_1, getName(), fOpt);
        	addWayPoint(fWayPoints.size() - 1, p);
        	s = new StrategyRelativeToCorner(p, fSource, refPosition, 0.6666, DeltaBasis.RELATIVE, -0.3333, DeltaBasis.RELATIVE);
        	s.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.6666);
        	s.setMaxDeltaY(MAX_REFLEXIVE_SIZE * 0.3333);
        	p.setStrategy(s);

        	// p3
        	p = new WayPoint(this, WayPointType.REFLEXIVE_2, getName(), fOpt);
        	s = new StrategyRelativeToCorner(p, fSource, refPosition, 0.6666, DeltaBasis.RELATIVE, 0.6666, DeltaBasis.RELATIVE);
        	s.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.6666);
        	s.setMaxDeltaY(MAX_REFLEXIVE_SIZE * 0.6666);
        	p.setStrategy(s);
        	addWayPoint(fWayPoints.size() - 1, p);
        	
        	// p4
        	p = new WayPoint(this, WayPointType.REFLEXIVE_3, getName(), fOpt);
        	s = new StrategyRelativeToCorner(p, fSource, refPosition, -0.3333, DeltaBasis.RELATIVE, 0.6666, DeltaBasis.RELATIVE);
        	s.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.3333);
        	s.setMaxDeltaY(MAX_REFLEXIVE_SIZE * 0.6666);
        	p.setStrategy(s);
        	addWayPoint(fWayPoints.size() - 1, p);
        	
    		ResetStrategy resetTarget = new ResetStrategy() {
				@Override
				public PositionStrategy reset() {
					StrategyRelativeToCorner ps = new StrategyRelativeToCorner(fTargetWayPoint, fSource, refPosition, -0.3333, DeltaBasis.RELATIVE, 0, DeltaBasis.ABSOLUTE);
					ps.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.3333);
		        	ps.setMaxDeltaY(0);
					return ps;
				}
			};
			
        	// p1 Source stays on x bounds
        	s = (StrategyRelativeToCorner)resetTarget.reset();
        	fTargetWayPoint.setResetStrategy(resetTarget);
			fTargetWayPoint.setStrategy(s);
        }
        
        if (completeEdgeMoveMovesUserWayPoints) {
	        fSource.addPositionChangedListener(new PositionChangedListener() {
				@Override
				public void positionChanged(Object source, Point2D newPosition, double deltaX, double deltaY) {
					if (fSource.isSelected() && fTarget.isSelected())
						moveUserDefinedWayPoints(deltaX, deltaY);
				}

				@Override
				public void boundsChanged(Object source, Rectangle2D oldBounds,
						Rectangle2D newBounds) {
				
				}
			});
        }
    }
    
    protected void initializeSourceWayPoint() {
		fSourceWayPoint = new AttachedWayPoint(fSource, this,
				WayPointType.SOURCE, fEdgeName, fOpt);
    }
    
    protected void initializeTargetWayPoint() {
		fTargetWayPoint = new AttachedWayPoint(fTarget, this,
				WayPointType.TARGET, fEdgeName, fOpt);
    }
    
    protected void initializeProperties(Multimap<PropertyOwner, EdgeProperty> properties) { }
    
    protected void initializeFinal() { 
    	for (WayPoint p : fWayPoints) {
    		p.initialize();
    	}
    }
    
    /**
     * Called if an edge is destroyed.
     */
    public void dispose() {
    	// The edge has never been shown
    	if (this.edgeProperties == null || fWayPoints == null) return;
    	
    	for (EdgeProperty p : getProperties()) {
    		p.dispose();
    	}
    	
    	for (WayPoint p : fWayPoints) {
    		p.dispose();
    	}
    }
    
	/**
	 * An edge can return a value here to give
	 * the drawing engine a hint for the minimum height
	 * of the source node
	 * @return a hint for the height or 0
	 */
	public double getSourceHeightHint() { return 0; }
	
	/**
	 * An edge can return a value here to give
	 * the drawing engine a hint for the minimum height
	 * of the target node
	 * @return a hint for the height or 0
	 */
	public double getTargetHeightHint() { return 0; }
	
	/**
	 * The way point the drawing starts
	 * @return
	 */
	public AttachedWayPoint getSourceWayPoint() {
		return fSourceWayPoint;
	}
	
	/**
	 * The way point the drawing ends
	 * @return
	 */
	public AttachedWayPoint getTargetWayPoint() {
		return fTargetWayPoint;
	}
	
	/**
     * If this edge is a reflexive edge it can be located
     * at four different positions:
     * NORT_EAST
     * SOUTH_EAST
     * SOUTH_WEST
     * NORTH_WEST
     * @return the reflexive position of this edge
     */
	public Direction getReflexivePosition() {
		return reflexivePosition;
	}

	/**
	 * @param reflexivePosition the reflexivePosition to set
	 */
	public void setReflexivePosition(Direction reflexivePosition) {
		this.reflexivePosition = reflexivePosition;
	}

	/**
	 * Returns the specified relative offset used
	 * by this edge for certain strategies.
	 * This offset is used to keep multiple edges between
	 * two nodes separated.
	 * 
	 * @return the offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * Specifies a relative offset used
	 * by this edge for certain strategies.
	 * This is used to keep multiple edges between
	 * two nodes separated.
	 * @param offset the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/**
     * Most objects need to do extra calculations
     * when drawn the first time.
     */
    protected boolean firstDraw = true;
    
    /**
     * Draws the edge to the given Graphics object if
     * it is set to be visible and both connected nodes are
     * set to be visible.
     *  
     * @param g Graphics object the node is drawn to.
     */
    public final void draw(Graphics2D g) {
    	if (isVisible() && source().isVisible() && target().isVisible()) {
    		onDraw(g);
    	}
    }
    
    /**
     * Called once when the edge is drawn the first time.
     * Can be used for example to calculate the width
     * with respect to text.
     * @param g
     */
    public void calculateSize(Graphics2D g) {
    	for (EdgeProperty p : getProperties()) {
    		p.doCalculateSize(g);
    	}
    	
    	for (WayPoint p : getWayPoints()) {
    		p.doCalculateSize(g);
    	}
    }
    
    /**
     * Called when the edge is drawn.
     * @param g
     */
    protected abstract void onDraw(Graphics2D g);

    /**
     * Returns the list of all way points defined for
     * this edge (engine specific and user defined ones).
     * Changes to the list are reflected in the edge! 
     * @return
     */
    public List<WayPoint> getWayPoints() {
        return fWayPoints;
    }
    
    /**
     * Returns the collection of all properties defined for this
     * edge. Any changes to the collection are reflected in the edge.
     * 
     * @return
     */
    public final Collection<EdgeProperty> getProperties() { 
    	// This returns a view on the multi set. Should
    	// be fast enough for drawing.
    	return this.edgeProperties.values(); 
    }
    
    public final Multimap<PropertyOwner, EdgeProperty> getPropertiesGrouped() {
    	return this.edgeProperties;
    }
    
    /**
     * Resets the edge, i. e., removes user way points and
     * sets first draw to true
     */
    public void reset() {
        List<WayPoint> nodes = new ArrayList<WayPoint>();

        for (WayPoint node : getWayPoints()) {
            if ( node.isSpecial() ) {
                nodes.add( node );
            }
        }
        
        this.fWayPoints = nodes;
        this.firstDraw = true;
        this.firstCalculation = true;
    }
    
    @Override
	public boolean isSelected() {
        return fIsSelected;
    }
    @Override
	public void setSelected( boolean selected ) {
        fIsSelected = selected;
    }
    
    @Override
	public void setResizeAllowed(boolean allowed) {
		// Intentionally nothing...
	}

	@Override
	public boolean getResizeAllowed() {
		// Intentionally false
		return false;
	}
	
    public abstract boolean isLink();

    public String getName() {
    	return fEdgeName;
    }
    
    /**
     * Returns <code>true</code>, if the given point (x,y)
     * is on the edge (a small area (w=4,h=4) is used for the hit test).
     * @param x
     * @param y
     * @return
     */
    public boolean occupies(double x, double y) {
    	if (!isInitialized()) return false;
    	
        Rectangle2D testArea = new Rectangle2D.Double(x - 2, y - 2, 4, 4);
        Line2D line = new Line2D.Double();
        
        EdgeProperty n1 = null;
        EdgeProperty n2 = null;
        
        // checking every line segment of this edge.
        Iterator<WayPoint> it = fWayPoints.iterator();
        if ( it.hasNext() ) {
            n1 = it.next();
        }
        
        while ( it.hasNext() ) {
            n2 = it.next();
            line = new Line2D.Double( n1.getCenter(), n2.getCenter() );
            if( line.intersects( testArea ) )
            	return true;
            // setting n2 to the first node now
            n1 = n2;
        }

        return false;
    }
    /**
     * Is beneath the x,y position an edge, than an additional node
     * will be added on this edge.
     *  
     * @param x x position to check
     * @param y y position to check
     * @return The new <code>WayPoint</code> if there is an edge beneath this location 
     *         and no other way point is beneath the location. 
     *         Otherwise <code>null</code> is returned.
     */
    public WayPoint addWayPoint( double x, double y ) {
        boolean occupies = false;
        Line2D line = new Line2D.Double();
        Rectangle2D testArea = new Rectangle2D.Double(x - 2, y - 2, 4, 4);
        
        WayPoint w1 = null;
        WayPoint w2 = null;
        
        // checking every line segment of this edge.
        Iterator<WayPoint> it = fWayPoints.iterator();
        if ( it.hasNext() ) {
            w1 = it.next();
        }
        
        while ( it.hasNext() ) {
            w2 = it.next();
            line = new Line2D.Double( w1.getCenter(), w2.getCenter() );
            occupies = line.intersects( testArea );
            if ( occupies ) {
                // are the coordinates above another node do not add another node.
                if ( !w2.getShape().contains( x, y ) 
                     || !w1.getShape().contains( x, y ) ) {
                	WayPoint newWayPoint = createWayPoint(WayPointType.USER);
                    newWayPoint.setPosition(x, y);
                    addWayPoint( newWayPoint, w1 );
                    return newWayPoint;
                }
            }
            // setting n2 to the first node now
            w1 = w2;
        }
        return null;
    }
    
    /**
     * Returns the first way point of the given <code>type</code>. 
	 * @param type The type of the way point to retrieve
	 * @return The first way point of the given type or <code>null</code> if no way point of that type exists.
	 */
	protected WayPoint getWayPoint(WayPointType type) {
		// Faster
		if (type == WayPointType.SOURCE)
			return fWayPoints.get(0);
		
		if (type == WayPointType.TARGET)
			return fWayPoints.get(fWayPoints.size() - 1);
		
		for (WayPoint p : fWayPoints) {
			if (p.getSpecialID() == type)
				return p;
		}
		
		return null;
	}

     /**
     * Removes a way point from the edge.
     * 
     * @param waypoint The way point to be removed.
     * @throws IllegalArgumentException if the way point is not allowed to be deleted (see {@link WayPointType#allowsDeletion()}).
     */
    public void removeWayPoint( WayPoint node ) {
        if (!node.getSpecialID().allowsDeletion() ) {
            throw new IllegalArgumentException("The given way point is not deletable!");
        }
        
        int index = fWayPoints.indexOf(node);
        fWayPoints.remove( index );
        WayPoint prev = fWayPoints.get(index - 1); 
        WayPoint next = fWayPoints.get(index);
        
        prev.setNextWayPoint(next);
        next.setPreviousWayPoint(prev);
        
        for (; index < fWayPoints.size(); ++index) {
        	fWayPoints.get(index).setID(index);
        }
        
        prev.updatePosition();
    }
    
    /**
     * Returns the node laying under the x,y coordinate, otherwise null
     * is returned.
     */
    public PlaceableNode findNode( double x, double y ) {
    	if (!isInitialized()) return null;
        return getNonSpecialWayPoint(x, y);
    }
    
    /**
     * Returns the node laying under the x,y coordinate, otherwise null
     * is returned.
     */
    protected PlaceableNode getNonSpecialWayPoint( double x, double y ) {
        for (WayPoint node : fWayPoints) {
            if ( node.occupies( x, y ) ) {
                return node;
            }
            
            PlaceableNode n = node.getRelatedNode(x, y);
            if (n != null)
            	return n;
        }
        
        return null;
    }
    
    /**
     * Sets the start point of this Edge.
     */
    void setSourcePoint( double x, double y ) {
        fSourceWayPoint.setCenter(x, y);
    }
    
    /**
     * Sets the end point of this Edge.
     */
    void setTargetPoint( double x, double y ) {
        fTargetWayPoint.setCenter( x, y );        
    }
    
    /**
     * Before the new position of an edge is calculated by {@link #calculateNewPositions(Set)}
     * this can be used by an edge to calculate positions of additional related elements.
     * Currently used for <code>QualierNode</code>s.
     */
    protected void beforeCalculateNewPosition() { }
    
    /**
     * Returns the successor way point of p
     * @param p
     * @return
     */
    public WayPoint getNextWayPoint(EdgeProperty p) {
    	return fWayPoints.get(fWayPoints.indexOf(p) + 1);
    }
    
    /**
     * Returns the predecessor way point of p
     * @param p
     * @return
     */
    public WayPoint getPreviousWayPoint(EdgeProperty p) {
    	return fWayPoints.get(fWayPoints.indexOf(p) - 1);
    }
    
    /**
     * Calculates and sets the new position of the edges between two
     * nodes.
     */
    protected final void calculateNewPositions( Collection<EdgeBase> edges ) {
    	// Allow the edges to do additional calculations
    	for (EdgeBase e : edges) {
    		e.beforeCalculateNewPosition();
    	}
    	
    	// doCalculateNewPositions(edges);
    	
    	if (firstCalculation) {
    		// Double calculation required...
    		firstCalculation = false;
    		calculateNewPositions(edges);
    	}
    }
    
    /**
     * Determines the <code>WayPoint</code> of this edge which is located
     * most to the direction specified by <code>direction</code>.
     * @return The <code>WayPoint</code> which is located most to the specified direction.
     */
    public EdgeProperty getWayPointMostTo(Direction direction) {
    	EdgeProperty result = fWayPoints.get(0);
		for (EdgeProperty wp : fWayPoints) {
			if (Direction.getDirection(result.getCenter(), wp.getCenter())
					.isLocatedTo(direction))
				result = wp;
		}

		return result;
    }

    /**
     * Moves all user defined way points
	 * @param deltaX
	 * @param deltaY
	 */
	protected void moveUserDefinedWayPoints(double deltaX, double deltaY) {
		for (WayPoint wp : fWayPoints) {
			if (!wp.isSpecial()) {
				wp.setPosition(wp.getX() + deltaX, wp.getY() + deltaY);
			}
		}
	}
	
	/**
	 * Returns a string uniquely identifying
	 * the edge.
	 * @return
	 */
	public final String getId() {
		return getIdInternal() + (idSuffix == null ? "" : idSuffix);
	}
	
	protected abstract String getIdInternal();
	
	/**
	 * Used to be able to add additional information
	 * to an edge.
	 */
	private String idSuffix;
	
	/**
	 * Appends additional information to the id of an edge.
	 * Used for example to handle n-ary edgess
	 * @param suffix A suffix to the id. Might be <code>null</code>.
	 */
	public void setIdSuffix(String suffix) {
		this.idSuffix = suffix;
	}
	
    /**
     * Type written into element tag
     */
    protected abstract String getStoreType();
    
    /**
     * If not null and not an empty string, 
     * the attribute <code>kind</code> with the returned value will be written into the store info
     * @return
     */
    protected String getStoreKind() { return ""; }
    
    /**
     * Saves placement information about this edge.
     * @param hidden If this edge should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public final void storePlacementInfo( PersistHelper helper, Element parent, boolean hidden ) {
        
    	Element edgeElement = helper.createChild(parent, LayoutTags.EDGE);
    	edgeElement.setAttribute("type", getStoreType());
    	
    	String kind = getStoreKind();
    	
        if (kind != null && !kind.equals("")) {
        	edgeElement.setAttribute("kind", kind);
        }
        
        helper.appendChild(edgeElement, LayoutTags.SOURCE, fSource.name());
        helper.appendChild(edgeElement, LayoutTags.TARGET, fTarget.name());
        helper.appendChild(edgeElement, LayoutTags.NAME, fEdgeName);
                
        storeProperties(helper, hidden, edgeElement);

        Element wpElement = helper.createChild(edgeElement, "waypoints");
        int id = 0;
        for (WayPoint n : fWayPoints) {
        	n.setID(id);
            n.storePlacementInfo(helper, wpElement, hidden );
            id++;
        }

        helper.appendChild(edgeElement, LayoutTags.HIDDEN, String.valueOf(hidden));
        
        storeAdditionalInfo(helper, edgeElement, hidden);
    }

	protected void storeProperties(PersistHelper helper, boolean hidden, Element edgeElement) {
		Element properties = helper.createChild(edgeElement, "properties");
						
        for (EdgeProperty prop : this.edgeProperties.values()) {
        		prop.storePlacementInfo(helper, properties, hidden);
        }
	}

    protected void storeAdditionalInfo(PersistHelper helper, Element element, boolean hidden) { }
    
    protected void restoreAdditionalInfo(PersistHelper helper, int version) { }
    
    protected void restoreWayPoint(PersistHelper helper, int version) {
    	// Handle only source, target and user defined way points
		int specialId = helper.getElementIntegerValue(LayoutTags.SPECIALID);
    	
		WayPointType type = WayPointType.getById(specialId);
		if (type.equals(WayPointType.SOURCE)) {
			fSourceWayPoint.restorePlacementInfo(helper, version);
		} else if (type.equals(WayPointType.TARGET)) {
			fTargetWayPoint.restorePlacementInfo(helper, version);
		} else if (!type.equals(WayPointType.ASSOC_CLASS_CON)) {
			int id = helper.getElementIntegerValue("id");
			WayPoint wayPointToRestore = null;
			if (fWayPoints.size() > id) {
				WayPoint wp = fWayPoints.get(id);
				if (wp.getSpecialID().getId() == specialId ) {
					wayPointToRestore = wp;
				}
			}
			
			if (wayPointToRestore == null) {
				wayPointToRestore = createWayPoint(type);
				addWayPoint(id, wayPointToRestore);
			}
			
			wayPointToRestore.restorePlacementInfo(helper, version);
		}
    }
    
    protected WayPoint createWayPoint(WayPointType type) {
    	WayPoint wp = new WayPoint(this, type, fEdgeName, fOpt);
    	wp.initialize();
    	
    	return wp;
    }
    
    public final void restorePlacementInfo( final PersistHelper helper, int version ) {
    	this.reset();
    	
    	AutoPilot ap = new AutoPilot(helper.getNav());
    	
    	// restore way points in order of id
    	int id = 0;
    	while(true) {
    		helper.getNav().push();
    		try {
	    		ap.selectXPath("./waypoints/edgeproperty[id='" + id + "']");
	    		
	    		try {
		    		if (ap.evalXPath() != -1) {
		    			restoreWayPoint(helper, version);
		    		} else {
		    			// We are done
		    			break;
		    		}
	    		} catch (XPathEvalException e) {
					e.printStackTrace();
				} catch (NavException e) {
					e.printStackTrace();
				}
			} catch (XPathParseException e) {
				e.printStackTrace();
			} finally {
				++id;
				ap.resetXPath();
				helper.getNav().pop();
			}
    	}
		
    	// Make new ids available
    	for (WayPoint wp : fWayPoints) {
    		helper.getAllNodes().put(wp.getId(), wp);
    	}
    	
	    helper.getNav().push();
    	try {
    		ap.selectXPath("./properties/edgeproperty");
    	
    		try {
		    	while(ap.evalXPath() != -1) {
		    		String propertyId = helper.getAttributeValue("id");
		    		PlaceableNode node = helper.getAllNodes().get(propertyId);
		    		if (node != null) {
		    			node.restorePlacementInfo(helper, version);
		    			node.updatePosition();
		    		}
		    	}
    		} catch (XPathEvalException e) {
				e.printStackTrace();
			} catch (NavException e) {
				e.printStackTrace();
			}
		} catch (XPathParseException e) {
			e.printStackTrace();
		} finally {
			ap.resetXPath();
			helper.getNav().pop();
		}
    	
    	restoreAdditionalInfo(helper, version);
    }

	/**
	 * Draws additional properties of an edge, e. g., multiplicites.
	 * Using this, the additional properties are drawn on top of other elements
	 * which does not lead to "hidden" multiplicities.
	 * @param g2d
	 */
	public void drawProperties(Graphics2D g2d) {
		for (EdgeProperty p : getProperties()) {
			p.draw(g2d);
		}
	}

	/**
	 * 
	 */
	public void updatePosition() {
		for (WayPoint wp : fWayPoints) {
			wp.updatePosition();
		}
	}

	/**
	 * @param allNodes
	 */
	public void collectChildNodes(Map<String, PlaceableNode> allNodes) {
		for (EdgeProperty p : getProperties()) {
			allNodes.put(p.getId(), p);
		}
		
		for (WayPoint p : fWayPoints) {
			allNodes.put(p.getId(), p);
			p.collectChildNodes(allNodes);
		}
	}	
}