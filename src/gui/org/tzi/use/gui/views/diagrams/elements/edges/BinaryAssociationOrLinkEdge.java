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
import java.awt.geom.Point2D;
import java.util.Iterator;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.elements.AssociationName;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.Multiplicity;
import org.tzi.use.gui.views.diagrams.elements.MultiplicityRolenameWrapper;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyInBetween;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint.ResetStrategy;
import org.tzi.use.gui.views.diagrams.waypoints.QualifierWayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.w3c.dom.Element;

import com.google.common.base.Supplier;
import com.google.common.collect.Multimap;

/**
 * An edge representing a binary link.
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public class BinaryAssociationOrLinkEdge extends AssociationOrLinkPartEdge {
	
	/**
	 * Source and target wrapper to keep multiplicity and role name close.
	 */
	MultiplicityRolenameWrapper fSourceMRWrapper;

	/**
     * Association name / link name of this edge.
     */
    EdgeProperty fAssocName;
    
    /**
     * Role name which is on the source side of this edge.
     */
    Rolename fSourceRolename;
    
    /**
     * Multiplicity which is on the source side of this edge.
     */
    //EdgeProperty fSourceMultiplicity;
    Multiplicity fSourceMultiplicity;
    
    /**
     * The represented association end.
     */
    MAssociationEnd fSourceEnd;

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
    protected BinaryAssociationOrLinkEdge(PlaceableNode source, PlaceableNode target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
			DiagramView diagram, MAssociation assoc, MLink link) {
		super(source, target, assoc.name(), targetEnd, diagram, assoc, link);
		fSourceEnd = sourceEnd;
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
     * @param assoc The represented association  
     */
	protected BinaryAssociationOrLinkEdge(PlaceableNode source, PlaceableNode target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
			DiagramView diagram, MAssociation assoc) {
		this(source, target, sourceEnd, targetEnd, diagram, assoc, null);
	}
	
	/**
     * Constructs a new binary link edge.
     * 
     * @param source The source node of the binary association or link
     * @param target The target node of the binary association or link
     * @param sourceEnd The association end attached to the source node
     * @param targetEnd The association end attached to the target node
     * @param diagram The diagram this edge belongs to 
     * @param link The represented link. 
     */
	protected BinaryAssociationOrLinkEdge(PlaceableNode source, PlaceableNode target,
			MLinkEnd sourceEnd, MLinkEnd targetEnd,
			NewObjectDiagram diagram, MLink link) {
		this(source, target, sourceEnd.associationEnd(), targetEnd.associationEnd(), diagram, link.association(), link);
	}
	
	@Override
	protected void initializeProperties(Multimap<PropertyOwner, EdgeProperty> properties) {
		super.initializeProperties(properties);
		 
		fSourceRolename = new Rolename( getId() + "::source::rolename", fSourceEnd, fSourceWayPoint, fOpt, this );
		properties.put(PropertyOwner.SOURCE, fSourceRolename);
		
		fSourceMultiplicity = new Multiplicity( getId() + "::source::multiplicity", fSourceEnd, fSourceWayPoint, fOpt);
		properties.put(PropertyOwner.SOURCE, fSourceMultiplicity);
		
		fAssocName = new AssociationName(getId() + "::AssociationName", fAssoc.name(), fSourceWayPoint, fTargetWayPoint, fOpt, this, fAssoc, link);
		properties.put(PropertyOwner.EDGE, fAssocName);
		
		fSourceMRWrapper = new MultiplicityRolenameWrapper(fSourceMultiplicity, fSourceRolename, PropertyOwner.SOURCE, fOpt); 
	}
	
	/**
	 * Adds reflexive point as illustrated below:
	 * <image src="DocumentationReflexiveEdges.png"/>
	 */
	@Override
	protected void initializeWayPoints() {		
		// If only one qualifier, the source needs to be the end with the qualifier
		if ( isReflexive() && fTargetEnd.hasQualifiers() && !fSourceEnd.hasQualifiers()) {
			{
				MAssociationEnd temp = this.fSourceEnd;
				this.fSourceEnd = this.fTargetEnd;
				this.fTargetEnd = temp;
			}
		}
		
		super.initializeWayPoints();
		
		if ( isReflexive() && fSourceEnd.hasQualifiers() && fTargetEnd.hasQualifiers() ) {
			// For two qualifiers we need an additional way point
			
			WayPoint p;
        	StrategyRelativeToCorner s;
        	final Direction refPosition2;
        	    		
    		// Switch from WEST to EAST and vice versa
			int value = getReflexivePosition().getValue();
			value = value ^ Direction.WEST.getValue() ^ Direction.EAST.getValue();
			refPosition2 = Direction.getDirection(value);
			
			// Set corner and make relative on x
			final StrategyRelativeToCorner sRef3 = (StrategyRelativeToCorner)getWayPoint(WayPointType.REFLEXIVE_3).getStrategy();
			sRef3.setCorner(refPosition2);
			sRef3.setBasisX(DeltaBasis.RELATIVE);
			sRef3.setDeltaX(0.6666);
			sRef3.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.6666);
        	sRef3.setMaxDeltaY(MAX_REFLEXIVE_SIZE * 0.6666);
			Supplier<Double> sRef3Supplier = new Supplier<Double>() {
				public Double get() {
					return sRef3.getRelativeNode().getBounds().getWidth() + fTargetWayPoint.getWidth() * 2;
				}
			};
			sRef3.setWidthCalculation(sRef3Supplier);
						
			p = new WayPoint(this, WayPointType.REFLEXIVE_4, fAssoc.name(), fOpt);
    		addWayPoint(fWayPoints.size() - 1, p);
    		final StrategyRelativeToCorner sRef4 = new StrategyRelativeToCorner(p, fTarget, refPosition2, 0.6666, DeltaBasis.RELATIVE, -0.3333, DeltaBasis.RELATIVE);
    		sRef4.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.6666);
        	sRef4.setMaxDeltaY(MAX_REFLEXIVE_SIZE * 0.3333);
        	Supplier<Double> sRef4Supplier = new Supplier<Double>() {
				public Double get() {
					return sRef4.getRelativeNode().getBounds().getWidth() + fTargetWayPoint.getWidth() * 2;
				}
			};
			sRef4.setWidthCalculation(sRef4Supplier);
        	p.setStrategy(sRef4);
        				
    		ResetStrategy resetTarget = new ResetStrategy() {
				@Override
				public PositionStrategy reset() {
					StrategyRelativeToCorner ps = new StrategyRelativeToCorner(fTargetWayPoint, fTarget, refPosition2, 0, DeltaBasis.ABSOLUTE, -0.3333, DeltaBasis.RELATIVE);
					ps.setMaxDeltaX(MAX_REFLEXIVE_SIZE * 0.3333);
					return ps;
				}
			};
			
        	// p1 Source stays on x bounds
        	s = (StrategyRelativeToCorner)resetTarget.reset();
        	fTargetWayPoint.setResetStrategy(resetTarget);
			fTargetWayPoint.setStrategy(s);
        }
		
		// A single qualifier on a reflexive edge
		if ( isReflexive() && fSourceEnd.hasQualifiers() ) {
			// The calculation of the position of the first node of the reflexive edge need to be aligned
			final StrategyRelativeToCorner s = (StrategyRelativeToCorner)getWayPoint(WayPointType.REFLEXIVE_1).getStrategy();
			s.setWidthCalculation(new Supplier<Double>() {
				public Double get() {
					return s.getRelativeNode().getBounds().getWidth() + fSourceWayPoint.getWidth() * 2;
				}
			});
			
			final StrategyRelativeToCorner s2 = (StrategyRelativeToCorner)getWayPoint(WayPointType.REFLEXIVE_2).getStrategy();
			s2.setWidthCalculation(new Supplier<Double>() {
				public Double get() {
					return s2.getRelativeNode().getBounds().getWidth() + fSourceWayPoint.getWidth() * 2;
				}
			});
		}
	}

	@Override
	protected void initializeSourceWayPoint() {
		if (fSourceEnd.hasQualifiers()) {
			fSourceWayPoint = new QualifierWayPoint(fSource,
					this, WayPointType.SOURCE, fEdgeName, fOpt, fSourceEnd,
					link);
		} else {
			super.initializeSourceWayPoint();
		}
    }
    
	@Override
    protected void initializeTargetWayPoint() {
    	if (fTargetEnd.hasQualifiers()) {
			fTargetWayPoint = new QualifierWayPoint(fTarget,
					this, WayPointType.TARGET, fEdgeName, fOpt, fTargetEnd,
					link);
    	} else {
    		super.initializeTargetWayPoint();
    	}
    }	

	@Override
	protected void initializeFinal() {
		super.initializeFinal();
		
		if (this.isReflexive()) {
			// set association relative to p3 and p4
	    	PlaceableNode[] relatives = new PlaceableNode[2];
	    	relatives[0] = getWayPoint(WayPointType.REFLEXIVE_2);
	    	relatives[1] = getWayPoint(WayPointType.REFLEXIVE_3);
	    	StrategyInBetween nameStrat = (StrategyInBetween)fAssocName.getStrategy();
	    	nameStrat.setRelatedNodes(relatives);
	    	// Name below association
	    	if (getReflexivePosition().isLocatedSouth())
	    		nameStrat.setOffsetY(nameStrat.getOffsetY() * -1);
		}
	}

	/**
     * @return Returns the fSourceRolename.
     */
    public Rolename getSourceRolename() {
        return fSourceRolename;
    }
        
    /**
     * @return Returns the fSourceMultiplicity.
     */
    public EdgeProperty getSourceMultiplicity() {
        return fSourceMultiplicity;
    }
        
    /**
     * @return Returns the fAssocName.
     */
    public EdgeProperty getAssocName() {
        return fAssocName;
    }
    
    @Override
    public PlaceableNode findNode(double x, double y) {
    	PlaceableNode res = super.findNode(x, y);
    	        
		if (fOpt.isShowRolenames() 
				&& getSourceRolename() != null
				&& getSourceRolename().isVisible()
				&& getSourceRolename().occupies(x, y)) {
			res = getSourceRolename();
		} else if (fOpt.isShowMutliplicities()
				&& getSourceMultiplicity() != null
				&& getSourceMultiplicity().isVisible()
				&& getSourceMultiplicity().occupies(x, y)) {
            res = getSourceMultiplicity();
        } else if ( fOpt.isShowAssocNames() 
        		&& getAssocName() != null
        		&& getAssocName().isVisible()
        		&& getAssocName().occupies( x, y ) ) {
            res = getAssocName();
        }
        
        return res;
    }
    
    @Override
    public double getSourceHeightHint() { return fSourceWayPoint.getHeightHint(); }
    
    @Override
	public double getTargetHeightHint() { return fTargetWayPoint.getHeightHint(); }

	/**
     * Draws a straight line edge between source and target node.
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
    @Override
	protected void drawEdge( Graphics2D g ) {
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
                p1 = n1.getLinePoint();
                p2 = n2.getLinePoint();
               
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
                        DirectedEdgeFactory.drawAssociation( g, (int) Math.round(p1.getX()),
                        										(int) Math.round(p1.getY()),
                                                                (int) Math.round(p2.getX()),
                                                                (int) Math.round(p2.getY()), isDashed );
                    }
                    n1 = n2;
                } catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Draws a straightline edge between source and target node.
     */
    protected void drawBinaryEdge( Graphics2D g ) {
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else if (adjacentObjectNodeGreyed()) {
        	g.setColor( fOpt.getGREYED_LINE_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
        
        drawEdge( g );
                
        g.setColor( fOpt.getEDGE_COLOR() );
    }

    @Override
    protected String getStoreType() { return "BinaryEdge"; }

	@Override
	protected void storeAdditionalInfo(PersistHelper helper, Element element,
			boolean hidden) {
		super.storeAdditionalInfo(helper, element, hidden);
		
		if (this.isLink()) {
			helper.appendChild(element, "linkValue", link.toString());
		}
	}

	@Override
	public void drawProperties(Graphics2D g) {
		super.drawProperties(g);
		
		// draw edge properties on the edge
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );
        }

        if ( fAssocName != null && fOpt.isShowAssocNames() ) {
            fAssocName.draw( g );
        }

        // Target is drawn in base class
        if ( fOpt.isShowRolenames() ) {
            fSourceRolename.draw( g );
        }

        if ( fOpt.isShowMutliplicities() ) {
            fSourceMultiplicity.draw( g );
        }
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
     * @param assoc The represented association  
     */
	public static BinaryAssociationOrLinkEdge create(PlaceableNode source, PlaceableNode target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
			DiagramView diagram, MAssociation assoc) {
		BinaryAssociationOrLinkEdge edge = new BinaryAssociationOrLinkEdge(source, target, sourceEnd, targetEnd, diagram, assoc);
		return edge;
	}
	
	/**
     * Constructs a new binary link edge.
     * 
     * @param source The source node of the binary association or link
     * @param target The target node of the binary association or link
     * @param sourceEnd The association end attached to the source node
     * @param targetEnd The association end attached to the target node
     * @param diagram The diagram this edge belongs to
     * @param link The represented link. 
     */
	public static BinaryAssociationOrLinkEdge create(PlaceableNode source, PlaceableNode target,
			MLinkEnd sourceEnd, MLinkEnd targetEnd,
			NewObjectDiagram diagram, MLink link) {
		BinaryAssociationOrLinkEdge edge = new BinaryAssociationOrLinkEdge(source, target, sourceEnd, targetEnd, diagram, link);
		return edge;
	}
}
