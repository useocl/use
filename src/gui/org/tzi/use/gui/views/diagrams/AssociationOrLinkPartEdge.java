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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.w3c.dom.Element;

/**
 * An edge being part of a n-ary edge. This edge connects an node with a
 * DiamondNode in case of an n-ary edge, or an Object/ClassNode with a 
 * PseudoNode in case of an association class.
 * It only provides semantic informations about the target association end.
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class AssociationOrLinkPartEdge extends EdgeBase {
	protected final static int DIRECTED_EDGE = 100;
	
    /**
     * Role name which is on the target side of this edge.
     */
    Rolename fTargetRolename;
    
    /**
     * Multiplicity which is on the target side of this edge.
     */
    EdgeProperty fTargetMultiplicity;
    
    /**
     * Association name / link name of this edge.
     */
    EdgeProperty fAssocName;
    /**
     * Association this edge belongs to.
     */
    MAssociation fAssoc;
    
    /**
     * The association end displayed at target 
     */
    MAssociationEnd fTargetEnd;
    
    /**
     * True if an link is represented false if association
     */
    boolean isLink;
    
    /**
     * Constructs a new edge. source is a pseude-node, target is a node.
     */
    public AssociationOrLinkPartEdge( NodeBase source, NodeBase target, MAssociationEnd targetEnd, DiagramView diagram, MAssociation assoc, boolean isLink ) {
        super( source, target, targetEnd.association().name(), diagram );
        
        this.isLink = isLink;
        
        fAssoc = assoc;
        
		fAssocName = new AssociationName(fAssoc.name(), source, target,
				fSourceWayPoint, fTargetWayPoint, fOpt, this, assoc, isLink);
        
        fTargetEnd = targetEnd;
        
        fTargetRolename = new Rolename( targetEnd, target, source, 
        								fTargetWayPoint, fSourceWayPoint,
                                        fOpt, Rolename.TARGET_SIDE, this );
        
        fTargetMultiplicity = new Multiplicity( targetEnd, target,
                                                source, this,
                                                fTargetWayPoint, fSourceWayPoint, fOpt,
                                                Multiplicity.TARGET_SIDE );
    }

    @Override
	public boolean isLink() { return isLink; }
    
    /**
     * @return Returns the fTargetRolename.
     */
    public Rolename getTargetRolename() {
        return fTargetRolename;
    }
    
    /**
     * @return Returns the fTargetMultiplicity.
     */
    public EdgeProperty getTargetMultiplicity() {
        return fTargetMultiplicity;
    }
    
    /**
     * @return Returns the fAssocName.
     */
    public EdgeProperty getAssocName() {
        return fAssocName;
    }
    
    @Override
    public List<EdgeProperty> getProperties() { 
    	ArrayList<EdgeProperty> result = new ArrayList<EdgeProperty>();
    	
    	if ( fAssocName != null ) {
            result.add(fAssocName);
        }
        if ( fTargetRolename != null ) {
        	result.add(fTargetRolename);
        }
        if ( fTargetMultiplicity != null ) {
        	result.add(fTargetMultiplicity);
        }
    	    
    	return result;
    }
    
    @Override
    public PlaceableNode getWayPoint(double x, double y) {
    	PlaceableNode res = null;
    	        
        if ( fOpt.isShowRolenames() && 
        	 getTargetRolename() != null && getTargetRolename().occupies( x, y ) ) {
            // Do not break here. We search in the same order
            // which is used for drawing. There may be another
            // node drawn on top of this node. That node should be
            // picked.
            res = getTargetRolename();
        } else if ( fOpt.isShowMutliplicities() && 
        	        getTargetMultiplicity() != null && getTargetMultiplicity().occupies( x, y ) ) {
            res = getTargetMultiplicity();
        } else if ( fOpt.isShowAssocNames() && 
        		    getAssocName() != null && getAssocName().occupies( x, y ) ) {
            res = getAssocName();
        } else if ( super.occupiesNonSpecialNodeOnEdge( x, y ) ) {
            res = super.getNonSpecialWayPoint(x, y);
        }
        
        return res;
    }
    
    @Override
    protected void onFirstDraw( Graphics2D g ) {
    	super.onFirstDraw(g);
    	
    	// Position depends on width of the rectangle
    	if (fAssocName != null)
    		fAssocName.setRectangleSize(g);
    	
    	if (fTargetMultiplicity != null)
    		fTargetMultiplicity.setRectangleSize(g);
    	
    	if (fTargetRolename != null)
    		fTargetRolename.setRectangleSize(g);
    }
    
    @Override
    protected void onDraw( Graphics2D g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        drawEdge( g );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
        }
        
        if ( fOpt.isShowRolenames() ) {
            fTargetRolename.draw( g );
        }
        
        if ( fOpt.isShowMutliplicities() ) {
            fTargetMultiplicity.draw( g );
        }
        
        g.setColor( fOpt.getEDGE_COLOR() );
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

        boolean targetNavigable = false;
                
        // draw all line segments
        
        if ( !fWayPoints.isEmpty() ) {
        	Iterator<WayPoint> it = fWayPoints.iterator();
        	
            if ( it.hasNext() ) {
                n1 = it.next();
                n1.draw( g );
            }
            
            while( it.hasNext() ) {
                n2 = it.next();
            	p1 = n1.getCenter();
                p2 = n2.getCenter();
                                
                // draw nodeOnEdge
                n2.draw( g );
                
                try {
                    if ( targetNavigable && n2.getSpecialID() == WayPointType.TARGET ) {
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
    }
    
    /**
     * Draws the last line segment, as an association, 
     * composition, aggregation, directed edge etc.
     */
    protected void drawAssociationKind( Graphics g, Point2D p2d1, Point2D p2d2, int kind ) {
        
    	Point p1 = new Point((int)p2d1.getX(), (int)p2d1.getY());
    	Point p2 = new Point((int)p2d2.getX(), (int)p2d2.getY());
    	
        try {
            // draw association
            switch ( kind ) {
            case MAggregationKind.NONE:
                DirectedEdgeFactory.drawAssociation( g, p1.x, p1.y, p2.x, p2.y );
                break;
            case MAggregationKind.AGGREGATION:
                DirectedEdgeFactory.drawAggregation( g, p1.x, p1.y, p2.x, p2.y );
                break;
            case MAggregationKind.AGGREGATION + DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedAggregation( g, p1.x, p1.y, p2.x, p2.y );
                break;
            case MAggregationKind.COMPOSITION:
                DirectedEdgeFactory.drawComposition( g, p1.x, p1.y, p2.x, p2.y );
                break;
            case MAggregationKind.COMPOSITION + DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedComposition( g, p1.x, p1.y, p2.x, p2.y );
                break;
            case DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedEdge( g, p1.x, p1.y, p2.x, p2.y );
                break;
            default:
                DirectedEdgeFactory.drawAssociation( g, p1.x, p1.y, p2.x, p2.y );
                break;
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    @Override
    protected String getStoreType() { return "HalfEdge"; }
    
    @Override
    protected void restoreEdgeProperty ( PersistHelper helper, Element propertyElement, String type, int version) {
    	
    	if (type.equals(LayoutTags.ASSOCNAME)) {
    		fAssocName.restorePlacementInfo(helper, propertyElement, version);
    	} else if (type.equals(LayoutTags.ROLENAME)) {
    		String kind = propertyElement.getAttribute("kind");
    		if (kind.equals(LayoutTags.TARGET)) {
    			fTargetRolename.restorePlacementInfo(helper, propertyElement, version);
    		}
    	} else if (type.equals(LayoutTags.MULTIPLICITY)) {
    		String kind = propertyElement.getAttribute("kind");
    		if (kind.equals(LayoutTags.TARGET)) {
    			fTargetMultiplicity.restorePlacementInfo(helper, propertyElement, version);
    		}
    	}
    	
    }
}

