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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.Multiplicity;
import org.tzi.use.gui.views.diagrams.elements.MultiplicityRolenameWrapper;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram.ObjectDiagramData;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;

import com.google.common.collect.Multimap;

/**
 * An edge being part of a n-ary edge. This edge connects a node with a
 * DiamondNode in case of an n-ary edge, or an Object/ClassNode with a 
 * PseudoNode in case of an association class.
 * It only provides semantic informations about the target association end.
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public class AssociationOrLinkPartEdge extends EdgeBase implements AssociationEdge, LinkEdge {
	protected final static int DIRECTED_EDGE = 100;
	
	/**
	 * Target wrapper to keep multiplicity and role name close.
	 */
	MultiplicityRolenameWrapper fTargetMRWrapper;
	
    /**
     * Role name which is on the target side of this edge.
     */
    Rolename fTargetRolename;
    
    /**
     * Multiplicity which is on the target side of this edge.
     */
    //EdgeProperty fTargetMultiplicity;
    Multiplicity fTargetMultiplicity;
    
    /**
     * Association this edge belongs to.
     */
    MAssociation fAssoc;
    
    /**
     * The association end displayed at target 
     */
    MAssociationEnd fTargetEnd;
    
    /**
     * The link, if any.
     */
    protected final MLink link;
    
    /**
     * The object diagram, if any.
     */
    protected final NewObjectDiagram objectDiagram;
    
    /**
     * if <code>true</code> a dashed line is drawn instead of a solid one.
     */
    boolean isDashed = false;
    
    /**
     * Constructs a new edge. source is a pseude-node, target is a node.
     * Name is set to targetEnd.nameAsRolename(). 
     * @param source
     * @param target
     * @param targetEnd
     * @param diagram
     * @param assoc
     * @param link
     */
	protected AssociationOrLinkPartEdge(PlaceableNode source, PlaceableNode target, MAssociationEnd targetEnd, 
			                         DiagramView diagram, MAssociation assoc, MLink link) {
		this(source, target, targetEnd.nameAsRolename(), targetEnd, diagram, assoc, link);
	}
	
    /**
     * Constructs a new edge. source is a pseude-node, target is a node.
     */
	protected AssociationOrLinkPartEdge(PlaceableNode source, PlaceableNode target,
			String name, MAssociationEnd targetEnd, DiagramView diagram, MAssociation assoc, MLink link) {
        super( source, target, name, diagram.getOptions(), true );
        
        this.link = link;
        if(diagram instanceof NewObjectDiagram) {
        	this.objectDiagram = (NewObjectDiagram) diagram;
        } else {
        	this.objectDiagram = null;
        }
        fAssoc = assoc;
        fTargetEnd = targetEnd;
    }

	@Override
	protected void initializeProperties(Multimap<PropertyOwner, EdgeProperty> properties) {
		super.initializeProperties(properties);
		
        fTargetRolename = new Rolename( getId() + "::target::rolename", fTargetEnd, fTargetWayPoint, fOpt, this);
        properties.put(PropertyOwner.TARGET, fTargetRolename);
        
        fTargetMultiplicity = new Multiplicity( getId() + "::target::multiplicity", fTargetEnd, fTargetWayPoint, fOpt);
        properties.put(PropertyOwner.TARGET, fTargetMultiplicity);
        
        fTargetMRWrapper = new MultiplicityRolenameWrapper(fTargetMultiplicity, fTargetRolename, PropertyOwner.TARGET, fOpt);
	}

	/**
	 * @return the represented asssociation
	 */
	public MAssociation getAssociation() {
		return fAssoc;
	}

	@Override
	public boolean isLink() { return link != null; }
    
	@Override
	protected String getIdInternal() {
		return (isLink() ? link.toString() : fAssoc.name());
	}
	
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
    
    public boolean isDashed() {
    	return isDashed;
    }
    
    /**
     * Sets the dashed style to <code>true</code> or <code>false</code>.
	 * @param newValue
	 */
	public void setDashed(boolean newValue) {
		isDashed = newValue;
	}
	
    @Override
    public PlaceableNode findNode(double x, double y) {
    	if (!isInitialized()) return null;
    	
    	PlaceableNode res = null;
    	        
		if (fOpt.isShowRolenames() && getTargetRolename() != null
				&& getTargetRolename().isVisible()
				&& getTargetRolename().occupies(x, y)) {
            // Do not break here. We search in the same order
            // which is used for drawing. There may be another
            // node drawn on top of this node. That node should be
            // picked.
            res = getTargetRolename();
        } else if ( fOpt.isShowMutliplicities() 
        		&& getTargetMultiplicity() != null 
        		&& getTargetMultiplicity().isVisible()
        		&& getTargetMultiplicity().occupies( x, y ) ) {
            res = getTargetMultiplicity();
        } else {
            res = getNonSpecialWayPoint(x, y);
        }

        return res;
    }
    
    public boolean adjacentObjectNodeGreyed() {
    	
    	if(!isLink()) {
    		return false;
    	}
    	
		ObjectDiagramData visibleData = objectDiagram.getVisibleData();
		if (visibleData.containsLink(link)) {
			//special treatment for associationObj: objGreyed => linkGreyed
			if(this instanceof BinaryAssociationClassOrObject) {
				BinaryAssociationClassOrObject binaryLink = (BinaryAssociationClassOrObject) this;
				ObjectNode node = (ObjectNode) binaryLink.getClassOrObjectNode();
				if(node.isGreyed()) {
					return true;
				}
			}
			
			List<MObject> adjacentObjects = getLink().linkedObjects();
			for (MObject adjacentObject : adjacentObjects) {
				ObjectNode node = visibleData.fObjectToNodeMap.get(adjacentObject);
				if(node.isGreyed()) {
					return true;
				}
			}
		}
    	
    	return false;
    }
    
    @Override
    protected void onDraw( Graphics2D g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else if (adjacentObjectNodeGreyed()) {
        	g.setColor( fOpt.getGREYED_LINE_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        drawEdge( g );
    }
    
    /**
     * Draws the edge segments of this edge.
     * @param g The edge is drawn in this graphics object.
     */
    void drawEdge( Graphics2D g ) {
        EdgeProperty n1 = null;
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
                                
                // draw way points
                n2.draw( g );
                
                try {
                    if ( targetNavigable && n2.getSpecialID() == WayPointType.TARGET ) {
                        drawAssociationKind( g, p1, p2, DIRECTED_EDGE );
                    } else {
                        DirectedEdgeFactory.drawAssociation( g, (int) p1.getX(),
                                                                (int) p1.getY(),
                                                                (int) p2.getX(),
                                                                (int) p2.getY(),
                                                                isDashed);
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
        
    	Point p1 = new Point((int)Math.round(p2d1.getX()), (int)Math.round(p2d1.getY()));
    	Point p2 = new Point((int)Math.round(p2d2.getX()), (int)Math.round(p2d2.getY()));
    	
        try {
            // draw association
            switch ( kind ) {
            case MAggregationKind.NONE:
                DirectedEdgeFactory.drawAssociation( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            case MAggregationKind.AGGREGATION:
                DirectedEdgeFactory.drawAggregation( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            case MAggregationKind.AGGREGATION + DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedAggregation( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            case MAggregationKind.COMPOSITION:
                DirectedEdgeFactory.drawComposition( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            case MAggregationKind.COMPOSITION + DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedComposition( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            case DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedEdge( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            default:
                DirectedEdgeFactory.drawAssociation( g, p1.x, p1.y, p2.x, p2.y, isDashed );
                break;
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    @Override
    protected String getStoreType() { return "HalfEdge"; }

	@Override
	public void drawProperties(Graphics2D g) {
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
	
	public static AssociationOrLinkPartEdge create(PlaceableNode source,
			PlaceableNode target, MAssociationEnd targetEnd, DiagramView diagram,
			MAssociation assoc, MLink link) {
		AssociationOrLinkPartEdge edge = new AssociationOrLinkPartEdge(source, target, targetEnd, diagram, assoc, link);
		return edge;
	}

	/**
	* Constructs a new edge. source is a pseude-node, target is a node.
	*/
	public static AssociationOrLinkPartEdge create(PlaceableNode source,
			PlaceableNode target, String name, MAssociationEnd targetEnd,
			DiagramView diagram, MAssociation assoc, MLink link) {
		AssociationOrLinkPartEdge edge = new AssociationOrLinkPartEdge(source, target, name, targetEnd, diagram, assoc, link);
		return edge;
	}

	@Override
	public MLink getLink() {
		return link;
	}
}