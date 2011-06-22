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
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.graph.DirectedEdgeBase;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.SourceWayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.TargetWayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointComparator;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Base class of all edge types in the diagram.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Fabian Gutsche
 */
public abstract class EdgeBase extends DirectedEdgeBase<NodeBase> implements Selectable {
    
    /**
     * Initial counter for the IDs for the nodes which are laying on 
     * this edge.
     */
    final int INITIAL_COUNTER = 0;
    /**
     * Counter for the IDs for the nodes which are laying on this edge.
     */
    int fNodesOnEdgeCounter = INITIAL_COUNTER;
    
    /**
     * Mouse click count of the given diagram. 
     */
    private int fClickCount = -1; // ==1 than show nodes on edge
    
    /**
     * Determines if this edge is selected or not.
     */
    private boolean fIsSelected;
    
    /**
     * Determines if this edge is dragged or not.
     */
    private boolean fIsDragged;
    
    /**
     * Name of this edge.
     */
    String fEdgeName;
    
    /**
     * List of all way points laying on this edge.
     */
    List<WayPoint> fWayPoints;
    /**
     * Source way point of this edge (the drawn line starts here).
     */
    SourceWayPoint fSourceWayPoint;
    /**
     * Target way point of this edge (the drawn line ends here).
     */
    TargetWayPoint fTargetWayPoint;
    
    /**
     * Options of the diagram in which this edge is drawn.
     */
    DiagramOptions fOpt;
    
    /**
     * Constructs a new edge.
     * @param source The source node of the edge.
     * @param target The target node of the edge.
     * @param edgeName The name of the edge.
     * @param diagram The diagram this edge belongs to.
     */
    public EdgeBase( NodeBase source, NodeBase target, String edgeName,
                     DiagramView diagram ) {
        super( source, target );
        fOpt = diagram.getOptions();
        fEdgeName = edgeName;
        fSource = source;
        fTarget = target;
        
        fWayPoints = new ArrayList<WayPoint>();
        
        fSourceWayPoint = new SourceWayPoint(fSource, fTarget, this, 
                                 	   fNodesOnEdgeCounter++, fEdgeName, fOpt );
        
        fTargetWayPoint = new TargetWayPoint(fSource, fTarget, this, 
                                 	   fNodesOnEdgeCounter++, fEdgeName, fOpt ); 
        
        fWayPoints.add( fSourceWayPoint );
        fWayPoints.add( fTargetWayPoint );
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
	public WayPoint getSourceWayPoint() {
		return fSourceWayPoint;
	}
	
	/**
	 * The way point the drawing ends
	 * @return
	 */
	public WayPoint getTargetWayPoint() {
		return fTargetWayPoint;
	}
	
	/**
	 * Gets the source node the first edge part is
	 * drawn from. Can be another source the the semantic source node, e.g., a qualifier node.  
	 * @return
	 */
	public NodeBase getDrawingSource() {
		return fSource;
	}
	
	public NodeBase getDrawingTarget() {
		return fTarget;
	}

	/**
	 * True if a special drawing source is attached to this edge.
	 * When true {@link #getDrawingSource()} differs from {@link #fSource}
	 * @return
	 */
	public boolean hasSpecialSource() { return false; }
	
	/**
	 * True if a special drawing target is attached to this edge.
	 * When true {@link #getDrawingTarget()} differs from {@link #fTarget}
	 * @return
	 */
	public boolean hasSpecialTarget() { return false; }
	
	/**
	 * Sets an offset value for the vertical placement of a special source.
	 * It is left to concrete implementations how to handle the value 0.
	 * For example it could mean to center the special source. 
	 */
	public void setSpecialSourceYOffSet(double newOffset) { return; }
	
	/**
	 * Gets the offset value for the vertical placement of a special source.
	 * It is left to concrete implementations how to handle the value 0.
	 * For example it could mean to center the special source.
	 */
	public double getSpecialSourceYOffSet() { return 0; }
	
	/**
	 * Sets an offset value for the vertical placement of a special target.
	 * It is left to concrete implementations how to handle the value 0.
	 * For example it could mean to center the special source.
	 */
	public void setSpecialTargetYOffSet(double newOffset) { return; }
	
	/**
	 * Gets the offset value for the vertical placement of a special source.
	 * It is left to concrete implementations how to handle the value 0.
	 * For example it could mean to center the special source. 	
	 */
	public double getSpecialTargetYOffSet() { return 0; }
	
	/**
     * Most objects need to do extra calculations
     * when drawn the first time.
     */
    protected boolean firstDraw = true;
    
    /**
     * Draws the edge to the given Graphics object.
     * When first drawn {@link #onFirstDraw(Graphics2D)} is called
     * and afterwards {@link #onDraw(Graphics2D)}.
     * After the first draw only {@link #onDraw(Graphics2D)} is called. 
     * @param g Graphics object the node is drawn to.
     */
    public final void draw(Graphics2D g) {
    	if (firstDraw) {
    		onFirstDraw(g);
    		firstDraw = false;
    	}
    	onDraw(g);
    }
    
    /**
     * Called once when the edge is drawn the first time.
     * Can be used for example to calculate the width
     * with respect to text.  
     * @param g
     */
    protected void onFirstDraw(Graphics2D g) {}
    
    /**
     * Called when the edge is drawn.
     * @param g
     */
    protected abstract void onDraw(Graphics2D g);
    

    public int getClickCount() {
        return fClickCount;
    }
    
    public void setClickCount( int clickCount ) {
        fClickCount = clickCount;
    }
    
    public List<WayPoint> getNodesOnEdge() {
        return fWayPoints;
    }
    
    public List<EdgeProperty> getProperties() { return Collections.emptyList(); }
    
    public void resetNodesOnEdges() {
        List<WayPoint> nodes = new ArrayList<WayPoint>();

        for (WayPoint node : getNodesOnEdge()) {
            if ( node.isSpecial() ) {
                nodes.add( node );
            }
        }
        
        fWayPoints = nodes;
    }
    
    public boolean isSelected() {
        return fIsSelected;
    }
    public void setSelected( boolean selected ) {
        fIsSelected = selected;
    }
    
    public boolean isDragged() {
        return fIsDragged;
    }
    public void setDragged( boolean dragging ) {
        fIsDragged = dragging;
    }
    
    public abstract boolean isLink();

    public String getName() {
    	return fEdgeName;
    }
    
    /**
     * Is beneath the x,y position an edge, than an additional node
     * will be added on this edge.
     *  
     * @param x x position to check
     * @param y y position to check
     * @return The new NodeOnEdge if there was an edge beneath this location
     * otherwise null is returned.
     */
    public WayPoint occupiesThanAdd( int x, int y, int clickCount ) {
        boolean occupies = false;
        Line2D line = new Line2D.Double();
        WayPoint n1 = null;
        WayPoint n2 = null;
        
        // checking every line segment of this edge.
        Iterator<WayPoint> it = fWayPoints.iterator();
        if ( it.hasNext() ) {
            n1 = it.next();
        }
        
        while ( it.hasNext() ) {
            n2 = it.next();
            line = new Line2D.Double( n1.getCenter().getX(), n1.getCenter().getY(), n2.getCenter().getX(), n2.getCenter().getY() );
            occupies = line.intersects( x - 2, y - 2, 4, 4 );
            if ( occupies && clickCount == 2 ) {
                // are the coordinates above another node do not add another node.
                if ( !n2.dimension().contains( x, y ) 
                     || !n1.dimension().contains( x, y ) ) {
                    
                    WayPoint newNode = new WayPoint( fSource, fTarget, 
                                                     this, fNodesOnEdgeCounter++,
                                                     WayPointType.USER, fEdgeName, fOpt );
                    newNode.setX_UserDefined(x);
                    newNode.setY_UserDefined(y);
                    
                    addNode( newNode, n1 );
                    fClickCount = clickCount;
                    return newNode;    
                }
            }
            if ( occupies ) {
                fClickCount = clickCount;
                return null;
            }
            // setting n2 to the first node now
            n1 = n2;
        }
        
        fClickCount = -1;
        return null;
    }
    
    /**
     * Adds a node to the list of nodes on this edge.
     * @param node Node to be added.
     * @param n1 Behind this node <code>node</code> will be added to 
     *           the list of nodes.
     */
    private void addNode( WayPoint node, WayPoint n1 ) {
        fWayPoints.add( fWayPoints.indexOf(n1) + 1, node );
        reIDNodes();
    }
    
    /**
     * Resets the ids of all nodes on this edge. The nodes have 
     * allways an increasing ID starting at the source node. 
     */
     void reIDNodes() {
        int counter = INITIAL_COUNTER;

        for (WayPoint n : fWayPoints) {
            n.setID( counter );
            counter++;
        }
    }

     private boolean shouldNodeBeMoveableRightNow( WayPoint node ) {
         if ( // node.getSpecialID() == WayPointType.SOURCE                     // source node
              //   || node.getSpecialID() == WayPointType.TARGET                  // target node
                  node.getSpecialID() == WayPointType.ASSOC_CLASS             // associactioclass node
                 || ( node.getSpecialID() == WayPointType.ASSOC_CLASS_CON       // associationclass
                         && getNodesOnEdge().size() <= 3 )
                         || ( isReflexive() && getNodesOnEdge().size() <= 5 ) 
                                 || ( isReflexive() && this instanceof BinaryAssociationClassOrObject // selfreflexive associationclass 
                                         && getNodesOnEdge().size() <= 6 ) ) {
             return false;
         } 
         return true;
     }

     /**
     * Removes a node from the edge.
     * @param node Node to be removed.
     */
    public void removeNodeOnEdge( WayPoint node ) {
        if ( node.isSpecial() ) {
            return;
        }
        fWayPoints.remove( node );
    }
    
    /**
     * Checks if there is a node laying under the x,y coordinate.
     * 
     * @return true if the position occupies a node otherwise false.
     */
    public boolean occupiesNodeOnEdge( double x, double y ) {
        return getWayPoint(x, y) != null;
    }
    
    /**
     * Checks if there is a node laying under the x,y coordinate.
     * 
     * @return true if the position occupies a node otherwise false.
     */
    protected boolean occupiesNonSpecialNodeOnEdge( double x, double y ) {
        return getNonSpecialWayPoint(x, y) != null;
    }
    
    /**
     * Returns the node laying under the x,y coordinate, otherwise null
     * is returned.
     */
    public PlaceableNode getWayPoint( double x, double y ) {
        return getNonSpecialWayPoint(x, y);
    }
    
    /**
     * Returns the node laying under the x,y coordinate, otherwise null
     * is returned.
     */
    protected PlaceableNode getNonSpecialWayPoint( double x, double y ) {
        for (WayPoint node : fWayPoints) {
            if ( node.occupies( x, y ) && ( !node.isSpecial() || shouldNodeBeMoveableRightNow( node ) ) ) {
                return node;
            }
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
     * Checks if there is more than one edge between two nodes.
     */
    public Set<EdgeBase> checkForNewPositionAndDraw( DirectedGraph<NodeBase, EdgeBase> graph, Graphics2D g ) {
        Set<EdgeBase> edges = null;

        if ( graph.existsPath( fSource, fTarget ) ) {
            edges = graph.edgesBetween( fSource, fTarget );
            calculateNewPositions( edges );
        }
        
        if ( edges != null ) {
            for (EdgeBase e : edges) {
                e.draw( g );
            }
        }
        
        return edges;
    }
    
    /**
     * Calculates the space between the lines if there are more than one
     * edge between two nodes.
     */
    private double calculateSpaces( double length, double numEdges ) {
        return length / ( numEdges + 1 );
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
    public WayPoint getNextWayPoint(WayPoint p) {
    	return fWayPoints.get(fWayPoints.indexOf(p) + 1);
    }
    
    /**
     * Returns the predecessor way point of p
     * @param p
     * @return
     */
    public WayPoint getPreviousWayPoint(WayPoint p) {
    	return fWayPoints.get(fWayPoints.indexOf(p) - 1);
    }

    /**
     * Calculates the position of a single edge (from center to center) or 
     * multiple edges (with space between edges) between two nodes. 
     * Primitive operation (see TemplateMethod in GOF) for template method
     * {@link #calculateNewPositions(Set)}. 
     * @param edges All edges between to given nodes.
     */
    protected void doCalculateNewPositions(Set<EdgeBase> edges) {
    	// if there is just one link between two objects set the
        // intersection points from center to center with the node bounds 
    	// of the objects as start and end point of the link. 
    	// Otherwise calculate the new positions.
        if ( edges.size() == 1 ) {
        	fSourceWayPoint.updatePosition(getNextWayPoint(fSourceWayPoint));
        	fTargetWayPoint.updatePosition(getPreviousWayPoint(fTargetWayPoint));
        } else {
            // there are more then just one link between source and target node.
        	NodeBase source = fSource;
        	Rectangle2D sourceRec = source.getBounds();
                        
            NodeBase target = fTarget;
            Rectangle2D targetRec = target.getBounds();
            double space = 0;
            double projection = 0;

            // Where is the position of the opposite node?
            Direction intersection = source.getIntersectionDirection(target.getCenter());
            			
            // to get a approiate separation of the links ...
            // ... use the width
            if ( intersection.isLocatedNorth() || intersection.isLocatedSouth() ) {
            	// calculates the different x-coordinates of start and end
                // point
              
            	// calculates the length of the space between the edges
                space = calculateSpaces( Math.min( sourceRec.getWidth(), targetRec.getWidth() ), edges.size() );
                // calculates the length of the space to the first edge
                // for the bigger rectangle
                projection = ( Math.max( sourceRec.getWidth(), targetRec.getWidth() ) - space
                        * ( edges.size() - 1 ) ) / 2.0;
	                
	            double counter = 1.0;
	            // The delta is later calculated from the center, therefore we start
	            // with a negative delta
	            double sDeltaX = source.getBounds().getMinX() - source.getBounds().getCenterX();
                double tDeltaX = target.getBounds().getMinX() - target.getBounds().getCenterX();
                
                boolean sourceIsSmaller = sourceRec.getWidth() <= targetRec.getWidth();
                
	            for (EdgeBase e : edges) {	            	
	            	AlignedObjects aligned = alignEdge(e);
	            
                	boolean drawingSourceEqualsSource = aligned.drawingSource.equals(e.fSource);
                	boolean drawingTargetEqualsTarget = aligned.drawingTarget.equals(e.fTarget);
                	
                    // addition of space or projection to get the
                    // wanted space between the edges
                    if ( counter == 1 ) {
                        if ( sourceIsSmaller ) {
                            sDeltaX += (drawingSourceEqualsSource ? space : 0 );
                            tDeltaX += (drawingTargetEqualsTarget ? projection : 0);
                        } else {
                            sDeltaX += (drawingSourceEqualsSource ? projection : 0);
                            tDeltaX += (drawingTargetEqualsTarget ? space : 0);
                        }
                    } else {
                        sDeltaX += (drawingSourceEqualsSource ? space : 0 );
                        tDeltaX += (drawingTargetEqualsTarget ? space : 0 );
                    }
                    
                    aligned.sourceWayPoint.updatePosition((drawingSourceEqualsSource ? sDeltaX : 0), 0, aligned.sourceWayPointNext);
                    aligned.targetWayPoint.updatePosition((drawingTargetEqualsTarget ? tDeltaX : 0), 0, aligned.targetWayPointNext);

                    counter++;
                }
            // ... use the height
            } else {
                // calculates the length of the space between the edges
                space = calculateSpaces( Math.min( sourceRec.getHeight(), targetRec.getHeight() ), edges.size() );
                // calculates the length of the space to the first edge
                // for the bigger rectangle
                projection = ( Math.max( sourceRec.getHeight(), targetRec.getHeight() ) - space
                        * ( edges.size() - 1 ) ) / 2.0;
                
                double counter = 1.0;
                
	            // The delta is later calculated from the center, therefore we start
	            // with a negative delta
                double sDeltaY = source.getBounds().getMinY() - source.getBounds().getCenterY();
                double tDeltaY = target.getBounds().getMinY() - target.getBounds().getCenterY();
                
                boolean sourceIsSmaller = sourceRec.getHeight() <= targetRec.getHeight();
                
                // calculates the different y-coordinates of start and end
                // point
                for (EdgeBase e : edges) {
                	AlignedObjects aligned = alignEdge(e);

                	boolean drawingSourceEqualsSource = aligned.drawingSource.equals(e.fSource);
                	boolean drawingTargetEqualsTarget = aligned.drawingTarget.equals(e.fTarget);
                	
                    // addition of space or projection to get the
                    // wanted space between the edges
                    if ( counter == 1 ) {
                        if ( sourceIsSmaller ) {
                            sDeltaY += space;
                            tDeltaY += projection;
                        } else {
                            sDeltaY += projection;
                            tDeltaY += space;
                        }
                    } else {
                        sDeltaY += space;
                        tDeltaY += space;
                    }
                    
                    aligned.sourceWayPoint.updatePosition(0, (drawingSourceEqualsSource ? sDeltaY : 0), aligned.sourceWayPointNext);
                    aligned.targetWayPoint.updatePosition(0, (drawingTargetEqualsTarget ? tDeltaY : 0), aligned.targetWayPointNext);
     
                    counter++;
                }
            }
        }
    }
    
    /**
     * Saves information about the aligned objects of another edge, i.e.,
     * the correct source objects when two edges between two nodes save source and
     * target the other way round.
     * 
     * @author lhamann
     *
     */
    private class AlignedObjects {
    	NodeBase drawingSource;
		NodeBase drawingTarget;
		AttachedWayPoint sourceWayPoint;
		WayPoint sourceWayPointNext;
		AttachedWayPoint targetWayPoint;
		WayPoint targetWayPointNext;
    }
    
    /**
     * Returns the informations of the other edge aligned to this edge, e.g.,
     * the returned source is equal to the source of this edge.
     * @param e The <code>EdgeBase</code> to be aligned
     * @return
     */
    protected AlignedObjects alignEdge(EdgeBase e) {
    	AlignedObjects result = new AlignedObjects();
    	
    	if (e.fSource.equals(fSource)) {
    		result.drawingSource = e.getDrawingSource();
    		result.drawingTarget = e.getDrawingTarget();
    		result.sourceWayPoint = e.fSourceWayPoint;
    		result.sourceWayPointNext = e.getNextWayPoint(e.fSourceWayPoint);
    		result.targetWayPoint = e.fTargetWayPoint;
    		result.targetWayPointNext = e.getPreviousWayPoint(e.fTargetWayPoint);
    	} else {
    		result.drawingSource = e.getDrawingTarget();
    		result.drawingTarget = e.getDrawingSource();
    		result.sourceWayPoint = e.fTargetWayPoint;
    		result.sourceWayPointNext = e.getPreviousWayPoint(e.fTargetWayPoint);
    		result.targetWayPoint = e.fSourceWayPoint;
    		result.targetWayPointNext = e.getNextWayPoint(e.fSourceWayPoint);
    	}
    	
    	return result;
    }
    
    /**
     * Calculates and sets the new position of the edges between two
     * nodes.
     */
    protected final void calculateNewPositions( Set<EdgeBase> edges ) {
    	// Allow the edges to do additional calculations
    	for (EdgeBase e : edges) {
    		e.beforeCalculateNewPosition();
    	}
    	
    	doCalculateNewPositions(edges);
    }
    
    /**
     * Determines the <code>WayPoint</code> of this edge which is located
     * most to the direction specified by <code>direction</code>.
     * @return The <code>WayPoint</code> which is located most to the specified direction.
     */
    public WayPoint getWayPointMostTo(Direction direction) {
    	WayPoint result = fWayPoints.get(0);
		for (WayPoint wp : fWayPoints) {
			if (Direction.getDirection(result.getCenter(), wp.getCenter())
					.isLocatedTo(direction))
				result = wp;
		}

		return result;
    }

    /**
     * Sorts the nodes on this edge according to their id.
     */
    public void sortNodesOnEdge() {
        Collections.sort( fWayPoints, new WayPointComparator() );
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
    public final void storePlacementInfo( Element parent, boolean hidden ) {
        
    	Element edgeElement = parent.getOwnerDocument().createElement(LayoutTags.EDGE);
    	parent.appendChild(edgeElement);
    	edgeElement.setAttribute("type", getStoreType());
    	String kind = getStoreKind();
    	
        if (kind != null && !kind.equals("")) {
        	edgeElement.setAttribute("kind", kind);
        }
        
        PersistHelper.appendChild(edgeElement, LayoutTags.SOURCE, fSource.name());
        PersistHelper.appendChild(edgeElement, LayoutTags.TARGET, fTarget.name());
        PersistHelper.appendChild(edgeElement, LayoutTags.NAME, fEdgeName);
                
        for (EdgeProperty prop : getProperties()) {
        	prop.storePlacementInfo(edgeElement, hidden);
        }

        for (WayPoint n : fWayPoints) {
            n.storePlacementInfo( edgeElement, hidden );
        }

        PersistHelper.appendChild(edgeElement, LayoutTags.HIDDEN, String.valueOf(hidden));
    }
    
    protected void restoreEdgeProperty(Element propertyElement, String type, String version) {}
    
    protected void restoreWayPoint(Element wayPointElement, String version) {
    	// Handle only source, target and user defined way points
		int specialId = Integer.valueOf(PersistHelper.getElementStringValue(
				wayPointElement, LayoutTags.SPECIALID));
    	
		WayPointType type = WayPointType.getById(specialId);
		if (type.equals(WayPointType.SOURCE)) {
			fSourceWayPoint.restorePlacementInfo(wayPointElement, version);
		} else if (type.equals(WayPointType.TARGET)) {
			fTargetWayPoint.restorePlacementInfo(wayPointElement, version);
		} else if (type.equals(WayPointType.USER)) {
			WayPoint userWp = new WayPoint(fSource, fTarget, this, fWayPoints.size() + 1, type, fEdgeName, fOpt);
			fWayPoints.add(fWayPoints.size() - 1, userWp);
			userWp.restorePlacementInfo(wayPointElement, version);
		}
    }
    
    public final void restorePlacementInfo( Element parent, String version ) {
    	NodeList elements = parent.getElementsByTagName(LayoutTags.EDGEPROPERTY);
    	
    	for (int i = 0; i < elements.getLength(); ++i) {
    		Element propertyElement = (Element)elements.item(i);
    		String type = propertyElement.getAttribute("type");
    		
    		if (version.equals("1") && type.equals("NodeOnEdge") ||
    			version.equals("2") && type.equals("WayPoint")) {
    			restoreWayPoint(propertyElement, version);
    		} else {
    			restoreEdgeProperty(propertyElement, type, version);
    		}
    	}
    	
    	/*
        for (WayPoint n : fWayPoints) {
            n.storePlacementInfo( edgeElement, hidden );
        }
        */
    }
}