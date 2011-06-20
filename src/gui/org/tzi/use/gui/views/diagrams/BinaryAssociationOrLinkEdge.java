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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.util.MathUtil;

/**
 * An edge representing a binary link.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class BinaryAssociationOrLinkEdge extends AssociationOrLinkPartEdge {
	
	/**
     * Possible node for qualifier.<br/>
     * Introduced here because only allowed on binary associations
     */
    QualifierNode fTargetQualifier = null;
    
	/**
     * Possible node for qualifier.<br/>
     * Introduced here because only allowed on binary associations
     */
    QualifierNode fSourceQualifier = null;
    
    /**
     * Role name which is on the source side of this edge.
     */
    EdgeProperty fSourceRolename;
    
    /**
     * Multiplicity which is on the source side of this edge.
     */
    EdgeProperty fSourceMultiplicity;
    
    /**
     * The represented association end.
     */
    MAssociationEnd fSourceEnd;
    
	/**
     * First reflexive node on an reflexive edge.
     */
    WayPoint fRefNode1;
    
    /**
     * First reflexive node on an reflexive edge.
     */
    WayPoint fRefNode2;
    
    /**
     * First reflexive node on an reflexive edge.
     */
    WayPoint fRefNode3;

    /**
     * If this edge is a reflexive edge it can be located
     * at four different positions:
     * NORT_EAST
     * SOUTH_EAST
     * SOUTH_WEST
     * NORTH_WEST
     */
    Direction fReflexivePosition = Direction.UNKNOWN;
    
    /**
     * Internal constructor
     * @param source
     * @param target
     * @param sourceEnd
     * @param targetEnd
     * @param diagram
     * @param assoc
     * @param isLink
     */
    protected BinaryAssociationOrLinkEdge(NodeBase source, NodeBase target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
			DiagramView diagram, MAssociation assoc, boolean isLink) {
		super(source, target, targetEnd, diagram, assoc, isLink);
    	
		fSourceEnd = sourceEnd;
        
        fSourceRolename = new Rolename( sourceEnd, source, target, 
        								fSourceWayPoint, fTargetWayPoint,
                                        fOpt, Rolename.SOURCE_SIDE, this );
        
        fSourceMultiplicity = new Multiplicity( sourceEnd, source, target, this,
                                                fSourceWayPoint, fTargetWayPoint, fOpt,
                                                Multiplicity.SOURCE_SIDE );
    }
    
    /**
     * Constructs a new binary association edge.
     * <p><strong>Note:</strong>
     * For associations or links with exactly one qualified end the qualified end must
     * be the source end.</p> 
     * @param source The source node of the binary association or link
     * @param target The target node of the binary association or link
     * @param sourceEnd The association end attached to the source node
     * @param targetEnd The association end attached to the target node
     * @param diagram The diagram this edge belongs to
     * @param assoc The represented association or the association of the represented link  
     */
	public BinaryAssociationOrLinkEdge(NodeBase source, NodeBase target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
			DiagramView diagram, MAssociation assoc) {
		this(source, target, sourceEnd, targetEnd, diagram, assoc, false);

		if (sourceEnd.hasQualifiers()) {
			this.setSourceQualifier(new QualifierNode(source, sourceEnd));
		}
		
		if (targetEnd.hasQualifiers()) {
			this.setTargetQualifier(new QualifierNode(target, targetEnd));
		}
		
		checkAndCreateReflexiveEdge();
	}
	
	/**
     * Constructs a new binary link edge.
     * 
     * @param source The source node of the binary association or link
     * @param target The target node of the binary association or link
     * @param sourceEnd The association end attached to the source node
     * @param targetEnd The association end attached to the target node
     * @param diagram The diagram this edge belongs to
     * @param assoc The represented association or the association of the represented link 
     * @param isLink <code>true</code> if a link is generated. <code>false</code> if a association is represented. 
     */
	public BinaryAssociationOrLinkEdge(NodeBase source, NodeBase target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
			DiagramView diagram, MLink link) {
		this(source, target, sourceEnd, targetEnd, diagram, link.association(), true);

		if (sourceEnd.hasQualifiers()) {
			this.setSourceQualifier(new QualifierNode(source, sourceEnd, link));
		}
		
		if (targetEnd.hasQualifiers()) {
			this.setTargetQualifier(new QualifierNode(target, targetEnd));
		}
		
		checkAndCreateReflexiveEdge();
	}
	
	public QualifierNode getSourceQualifier() {
    	return this.fSourceQualifier;
    }
    
    public boolean hasSourceQualifier() {
    	return this.fSourceQualifier != null;
    }
    
    public void setSourceQualifier(QualifierNode node) {
    	fSourceQualifier = node;
    }
    
    /**
     * @return Returns the fSourceRolename.
     */
    public EdgeProperty getSourceRolename() {
        return fSourceRolename;
    }
        
    /**
     * @return Returns the fSourceMultiplicity.
     */
    public EdgeProperty getSourceMultiplicity() {
        return fSourceMultiplicity;
    }
    
    public QualifierNode getTargetQualifier() {
    	return fTargetQualifier;
    }
    
    public boolean hasTargetQualifier() {
    	return fTargetQualifier != null;
    }
    
    public void setTargetQualifier(QualifierNode node) {
    	fTargetQualifier = node;
    }
    
    /**
     * True if this edge has at least one qualifier
     * @return
     */
    public boolean hasQualifier() {
    	return hasSourceQualifier() || hasTargetQualifier();
    }
    
    /**
     * Returns the number of qualifiers attached to this edge (up to two)
     * @return
     */
    public int getNumberOfQualifers() {
    	int count = 0;
    	if (hasSourceQualifier()) ++count;
    	if (hasTargetQualifier()) ++count;
    	return count;
    }
    
    public boolean isReflexive() {
        return fSource.equals( fTarget );
    }
    
    private void checkAndCreateReflexiveEdge() {
        if ( isReflexive() ) {
            // Add before the last element in the list
			fWayPoints.add(fWayPoints.size() - 1, new WayPoint(fSource,
					fTarget, this, fNodesOnEdgeCounter++,
					WayPointType.REFLEXIVE_1, fAssoc.name(), fOpt));

			fWayPoints.add(fWayPoints.size() - 1, new WayPoint(fSource,
					fTarget, this, fNodesOnEdgeCounter++,
					WayPointType.REFLEXIVE_2, fAssoc.name(), fOpt));

			fWayPoints.add(fWayPoints.size() - 1, new WayPoint(fSource,
					fTarget, this, fNodesOnEdgeCounter++,
					WayPointType.REFLEXIVE_3, fAssoc.name(), fOpt));
            
			if (getNumberOfQualifers() == 2) {
				fWayPoints.add(fWayPoints.size() - 1, new WayPoint(fSource,
						fTarget, this, fNodesOnEdgeCounter++,
						WayPointType.REFLEXIVE_3, fAssoc.name(), fOpt));
			}
			
            reIDNodes();
        }
    }
    
    /**
     * Handles reflexive associations / links
     */
    @Override
    protected void doCalculateNewPositions(Set<EdgeBase> edges) {
    	
    	if (isReflexive()) {
    		// there are up to four reflexive edges 
    		int counter = 0;
    		
    		for (EdgeBase e : edges) {
    			// Should all be binary, but who knows...
    			if (!(e instanceof BinaryAssociationOrLinkEdge))
    				continue;
    			
    			BinaryAssociationOrLinkEdge be = (BinaryAssociationOrLinkEdge)e;
    			
                switch ( counter ) {
                case 0:
                	be.fReflexivePosition = Direction.NORTH_WEST;
                    break;
                case 1:
                	be.fReflexivePosition = Direction.NORTH_EAST;
                    break;
                case 2:
                	be.fReflexivePosition = Direction.SOUTH_EAST;
                    break;
                case 3:
                	be.fReflexivePosition = Direction.SOUTH_WEST;
                    break;
                default /* (not visible) */:
                	be.fReflexivePosition = Direction.NORTH_EAST;
                    break;
                }
                
                if (be.hasQualifier()) {
	                if (be.getNumberOfQualifers() > 1) {
	                	// If another reflexive edge "blocks" north
	                	Direction relPosition = (counter > 0 ? Direction.SOUTH : Direction.NORTH);
	                	be.getSourceQualifier().setRelativePosition(Direction.getDirection(relPosition.getValue() + Direction.WEST.getValue()));
	    				be.getTargetQualifier().setRelativePosition(Direction.getDirection(relPosition.getValue() + Direction.EAST.getValue()));
	                } else {
	                	be.getSourceQualifier().setRelativePosition(be.fReflexivePosition);
	                }
                }
                
                ++counter;
            }
		} else {
			super.doCalculateNewPositions(edges);
		}
    }
    
    /**
     * Updates the positions of the points participating in the reflexive
     * edge. 
     */
    private void updateReflexiveNodes( FontMetrics fm ) {
        List<Point2D> points;
        
        if (this.hasQualifier())
        	points = calcReflexivePointsWithQualifier( fm );
        else
        	points = calcReflexivePoints( fm );
        
        int index = 0;
        
        for (WayPoint wp : this.fWayPoints) {
        	if (wp.isSpecial()) {
        		wp.setCenter(points.get(index));
        		++index;
        	}
        }
    }
    
    @Override
    protected void beforeCalculateNewPosition() {
    	if (hasSourceQualifier()) {
        	getSourceQualifier().calculatePosition(getNextWayPoint(fSourceWayPoint).getCenter());
        }
    	
    	if (hasTargetQualifier()) {
        	getTargetQualifier().calculatePosition(getPreviousWayPoint(fTargetWayPoint).getCenter());
        }
    	
    	super.beforeCalculateNewPosition();
    }
    
    @Override
    public List<EdgeProperty> getProperties() { 
    	List<EdgeProperty> result = super.getProperties();

        if ( fSourceRolename != null ) {
        	result.add(fSourceRolename);
        }
        if ( fSourceMultiplicity != null ) {
        	result.add(fSourceMultiplicity);        
        }
            
    	return result;
    }
    
    @Override
    public PlaceableNode getWayPoint(double x, double y) {
    	PlaceableNode res = super.getWayPoint(x, y);
    	        
        if ( getSourceRolename() != null && getSourceRolename().occupies( x, y ) ) {
            res = getSourceRolename();
        } else if ( getSourceMultiplicity() != null && getSourceMultiplicity().occupies( x, y ) ) {
            res = getSourceMultiplicity();
        }
        
        return res;
    }
    
    @Override
    public NodeBase getDrawingSource() {
    	return (this.hasSourceQualifier() ? this.getSourceQualifier() : fSource);
    }
    
    @Override
    public boolean hasSpecialSource() {
    	return this.hasSourceQualifier();
    }
    
    @Override
    public NodeBase getDrawingTarget() {
    	return (this.hasTargetQualifier() ? this.getTargetQualifier() : fTarget);
    }
    
    @Override
    public boolean hasSpecialTarget() {
    	return this.hasTargetQualifier();
    }
    
    @Override
	public void setSpecialTargetYOffSet(double newOffset) { 
    	getTargetQualifier().setYOffset(newOffset); 
    }
	
    @Override
	public void setSpecialSourceYOffSet(double newOffset) { 
    	getSourceQualifier().setYOffset(newOffset); 
    }
    
    @Override
    public double getSourceHeightHint() { return (hasSourceQualifier() ? getSourceQualifier().getHeight() : 0); }
    
    @Override
	public double getTargetHeightHint() { return (hasTargetQualifier() ? getTargetQualifier().getHeight() : 0); }
    
    /**
     * Returns the reflexive position this edge is located. 
     * That is NW,NW,SE or SW if it is reflexive.
     * UNKNOWN otherwise.
     * @return
     */
    public Direction getReflexivePosition() {
    	return this.fReflexivePosition;
    }
    
    /**
     * Calculates the position of the points of a reflexive edge.
     * <p>The order of the calculated points are shown in the image below.</p>   
     * <image src="DocumentationReflexiveEdges.png"/>
     * @param fm The font metrics is used to find the width and height
     * of the reflexive edge.
     * @return The points of the reflexive edge are returned.
     */
    private List<Point2D> calcReflexivePoints( FontMetrics fm ) {
        List<Point2D> points = new ArrayList<Point2D>(5);
        
        Point2D.Double p1 = new Point2D.Double(); // Vertical "exit/entry" from Node
        Point2D.Double p2 = new Point2D.Double(); // horizontal outer most point
        Point2D.Double p3 = new Point2D.Double(); // horizontal and vertical outer most point
        Point2D.Double p4 = new Point2D.Double(); // vertical outer most point
        Point2D.Double p5 = new Point2D.Double(); // Horizontal "exit/entry" from Node
        
        points.add( p1 );
        points.add( p2 );
        points.add( p3 );
        points.add( p4 );
        points.add( p5 );
        
        int maxWidth = maxWidth( fm );
        int maxHeight = maxHeight( fm );
        
        Rectangle2D sourceBounds = getDrawingSource().getBounds();
              
        // Distances from the bounds of the class node to the entrance
        // of the lines
        double horizontalSpace = sourceBounds.getWidth() / 4;
        double verticalSpace = sourceBounds.getHeight() / 4;
        
        if (this.fReflexivePosition.isLocatedWest()) {
        	p1.x = sourceBounds.getMinX();
        	p5.x = sourceBounds.getMinX() + horizontalSpace;
        	p2.x = p5.x - maxWidth;
        	p3.x = p5.x - maxWidth;
        	p4.x = p5.x;
        } else {
        	p1.x = sourceBounds.getMaxX();
        	p5.x = sourceBounds.getMaxX() - horizontalSpace;
        	p2.x = p5.x + maxWidth;
        	p3.x = p5.x + maxWidth;
        	p4.x = p5.x;
        }
        
        if (this.fReflexivePosition.isLocatedNorth()) {
        	p1.y = sourceBounds.getMinY() + verticalSpace;
        	p5.y = sourceBounds.getMinY();
        	p2.y = p1.y;
        	p3.y = p1.y - maxHeight;
        	p4.y = p3.y; 
        } else {
        	p1.y = sourceBounds.getMaxY() - verticalSpace;
        	p5.y = sourceBounds.getMaxY();
        	p2.y = p1.y;
        	p3.y = p1.y + maxHeight;
        	p4.y = p3.y;
        }
        
        return points;
    }
    
    /**
     * Calculates the position of the points of a reflexive edge with qualfied ends.
     * @param fm The font metrics is used to find the width and height
     * of the reflexive edge.
     * @return The points of the reflexive edge are returned.
     */
    private List<Point2D> calcReflexivePointsWithQualifier( FontMetrics fm ) {
        List<Point2D> points = new ArrayList<Point2D>(6);
        
        Point2D.Double p1 = new Point2D.Double();
        Point2D.Double p2 = new Point2D.Double();
        Point2D.Double p3 = new Point2D.Double();
        Point2D.Double p4 = new Point2D.Double();
        Point2D.Double p5 = new Point2D.Double();
        Point2D.Double p6 = new Point2D.Double();
        
        points.add( p1 );
        points.add( p2 );
        points.add( p3 );
        points.add( p4 );
        points.add( p5 );
        
        int maxCaptionWidth = maxWidth( fm );
        int maxHeight = maxHeight( fm );
        int maxWidth;
        
        Rectangle2D sourceBounds = getDrawingSource().getBounds();
        Rectangle2D targetBounds = getDrawingTarget().getBounds();
        
        double horizontalSpace = targetBounds.getWidth() / 4;
        
        if (this.getNumberOfQualifers() == 2) {
        	points.add(p6);
           	maxWidth = (int)MathUtil.max( 
           			maxCaptionWidth, 
           			this.getTargetQualifier().getWidth() + fSource.getWidth() + this.getSourceQualifier().getWidth());
           	
           	maxWidth += 2 * horizontalSpace;
           	
           	p1.x = sourceBounds.getMinX();
           	p2.x = p1.x - horizontalSpace;
           	p3.x = p2.x;
           	p4.x = p3.x + maxWidth;
           	p5.x = p4.x;
           	p6.x = targetBounds.getMaxX();
           	
           	p1.y = sourceBounds.getCenterY();
           	p2.y = p1.y;
           	
           	if (this.fReflexivePosition.isLocatedNorth()) {
           		p3.y = p2.y - maxHeight;
           	} else {
           		p3.y = p2.y + maxHeight;
           	}
           	
           	p4.y = p3.y;
           	p5.y = p1.y;
           	p6.y = p1.y;
        }
        else {
        	maxWidth = (int)Math.max(
        			maxCaptionWidth, 
        			this.getSourceQualifier().getWidth() + ((double)this.fSource.getWidth()) / 1.5);
        
            if (this.fReflexivePosition.isLocatedWest()) {
            	p1.x = sourceBounds.getMinX();
            	p5.x = targetBounds.getMinX() + horizontalSpace;
            	p2.x = p5.x - maxWidth;
            	p3.x = p5.x - maxWidth;
            	p4.x = p5.x;
            } else {
            	p1.x = sourceBounds.getMaxX();
            	p5.x = targetBounds.getMaxX() - horizontalSpace;
            	p2.x = p5.x + maxWidth;
            	p3.x = p5.x + maxWidth;
            	p4.x = p5.x;
            }
            
            if (this.fReflexivePosition.isLocatedNorth()) {
            	p1.y = sourceBounds.getCenterY();
            	p5.y = targetBounds.getMinY();
            	p2.y = p1.y;
            	p3.y = p5.y - maxHeight;
            	p4.y = p3.y; 
            } else {
            	p1.y = sourceBounds.getCenterY();
            	p5.y = targetBounds.getMaxY();
            	p2.y = p1.y;
            	p3.y = p5.y + maxHeight;
            	p4.y = p3.y;
            }
        }
        
        return points;
    }
    
    /**
     * Calculates the max width of the label on a reflexive edge.
     * @param fm The font metrics which is used to calculate the width.
     * @return The max width of the label.
     */
    private int maxWidth( FontMetrics fm ) {
        final int LARGEST_WIDTH = 100;
        int labelWidth = fm.stringWidth( getName() );
        double sWidth = fSource.getWidth();
        int maxWidth = Math.max( 30, Math.max( labelWidth, (int) sWidth / 2 ));
        
        if ( maxWidth > LARGEST_WIDTH ) {
            maxWidth = LARGEST_WIDTH;
        }
        return maxWidth;
    }

    /**
     * Calculates the max height of the label on a reflexive edge.
     * @param fm The font metrics which is used to calculate the height.
     * @return The max height of the label.
     */
    private int maxHeight( FontMetrics fm ) {
        final int LARGEST_HEIGHT = 55;
        int labelHeight = fm.getHeight();
        double sHeight = fSource.getHeight();
        int maxHeight = Math.max( 30, Math.max( (int) (sHeight/3), labelHeight+4 ) );
        
        // is the height of the node equals or just a little bit bigger
        // as the labelHeight use the labelHeight.
        if ( sHeight <= labelHeight+6 ) {
            maxHeight = labelHeight+6;
        }
        if ( maxHeight > LARGEST_HEIGHT ) {
            maxHeight = LARGEST_HEIGHT;
        }
        return maxHeight;
    }
    
    @Override
    public void onFirstDraw(Graphics2D g) {
    	if (hasSourceQualifier()) {
			getSourceQualifier().setRectangleSize(g);
		}
    	
		if (hasTargetQualifier()) {
			getTargetQualifier().setRectangleSize(g);
		}
		
		// Prepares the qualifier
    	super.onFirstDraw(g);
    }
    
    /**
     * Draws a straightline edge between source and target node.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        // edge is not drawn directly in this method, because this
        // way drawBinaryEdge can be reused for drawing the edge
        // for a link object.
        drawBinaryEdge( g );
    }

    /**
     * Draws the edge segments of this edge.
     * @param g The edge is drawn in this graphics object.
     */
    void drawEdge( Graphics2D g ) {
        WayPoint n1 = null;
        Point2D p1 = null;
        
        WayPoint n2 = null;
        Point2D p2 = null;
        
        int source = fSourceEnd.aggregationKind();
        int target = fTargetEnd.aggregationKind();
        boolean sourceNavigable = fSourceEnd.isExplicitNavigable();
        boolean targetNavigable = fTargetEnd.isExplicitNavigable();
                
        // draw all line segments
        if ( !fWayPoints.isEmpty() ) {
        	Iterator<WayPoint> it = fWayPoints.iterator();
            n1 = it.next();
            
            n1.draw(g);
            
            while( it.hasNext() ) {
                n2 = it.next();
                p1 = n1.getCenter();
                p2 = n2.getCenter();
               
                // draw nodeOnEdge
                n2.draw(g);
                
                try {
                    if ( source != MAggregationKind.NONE
                         && ( n1.getSpecialID() == WayPointType.SOURCE || n1.getSpecialID() == WayPointType.TARGET )) {
                        if ( targetNavigable 
                             && n1.getSpecialID() == WayPointType.SOURCE 
                             && n2.getSpecialID() == WayPointType.TARGET ) {
                            drawAssociationKind( g, p2, p1, source + DIRECTED_EDGE );
                        } else {
                            drawAssociationKind( g, p2, p1, source );
                        }
                    } else if ( target != MAggregationKind.NONE
                                && ( n2.getSpecialID() == WayPointType.TARGET || n2.getSpecialID() == WayPointType.SOURCE )) {
                        if ( sourceNavigable 
                             && n1.getSpecialID() == WayPointType.SOURCE 
                             && n2.getSpecialID() == WayPointType.TARGET ) {
                            drawAssociationKind( g, p1, p2, target + DIRECTED_EDGE );
                        } else {
                            drawAssociationKind( g, p1, p2, target );
                        }
                    } else if ( sourceNavigable && n1.getSpecialID() == WayPointType.SOURCE ) {
                        drawAssociationKind( g, p2, p1, DIRECTED_EDGE );
                    } else if ( targetNavigable && n2.getSpecialID() == WayPointType.TARGET ) {
                        drawAssociationKind( g, p1, p2, DIRECTED_EDGE );
                    } else {
                        DirectedEdgeFactory.drawAssociation( g, (int) p1.getX(),
                                                                (int) p1.getY(),
                                                                (int) p2.getX(),
                                                                (int) p2.getY() );
                    }
                    n1 = n2;
                } catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        }
        
        if (fSourceQualifier != null) {
        	fSourceQualifier.draw(g);
        }
        
        if (fTargetQualifier != null) {
        	fTargetQualifier.draw(g);
        }
    }
    
    /**
     * Draws a straightline edge between source and target node.
     */
    void drawBinaryEdge( Graphics2D g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        // draw the edge
        if ( isReflexive() ) {
            drawReflexiveEdge( g );
        } else {
            drawEdge( g );

            // draw edge properties on the edge
            if ( isSelected() ) {
                g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
            } else {
                g.setColor( fOpt.getEDGE_LABEL_COLOR() );
            }

            if ( fOpt.isShowAssocNames() ) {
                fAssocName.draw( g );
            }

            if ( fOpt.isShowRolenames() ) {
                fSourceRolename.draw( g );
                fTargetRolename.draw( g );
            }

            if ( fOpt.isShowMutliplicities() ) {
                fSourceMultiplicity.draw( g );
                fTargetMultiplicity.draw( g );
            }
        }
        g.setColor( fOpt.getEDGE_COLOR() );
    }

    /**
     * @param g
     * @param fm
     */
    private void drawReflexiveEdge( Graphics2D g ) {
        updateReflexiveNodes( g.getFontMetrics() );
        drawEdge( g );
        
        g.setColor( fOpt.getEDGE_LABEL_COLOR() );
        if ( fOpt.isShowAssocNames() ) {
            fAssocName.draw( g );
        }

        if ( fOpt.isShowRolenames() ) {
            fSourceRolename.draw( g );
            fTargetRolename.draw( g );
        }
        
        if ( fOpt.isShowMutliplicities() ) {
            fSourceMultiplicity.draw( g );
            fTargetMultiplicity.draw( g );
        }
    }
    
    @Override
    protected String storeGetType() { return "BinaryEdge"; }
}
