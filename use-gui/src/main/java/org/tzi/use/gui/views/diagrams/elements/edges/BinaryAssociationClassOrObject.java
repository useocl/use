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
import java.util.Map;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyIdentity;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyInBetween;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyRelativeToCorner.DeltaBasis;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.w3c.dom.Element;

/**
 * Extends the {@link BinaryAssociationOrLinkEdge} to additionally  
 * draw the line between the rectangle of the association class/ objectlink 
 * and the corresponding classes/objects.
 +
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public class BinaryAssociationClassOrObject extends BinaryAssociationOrLinkEdge {
	
	private WayPoint connectionWayPoint;
	
    /**
     * The PlaceableNode of instance ClassNode or ObjectNode displaying 
     * the class part of the association class / object link.
     */
    private PlaceableNode fAssociationClassOrObjectNode; 
    
    private SimpleEdge associationClassEdge;
    
    /**
     * Use this constructor if it is a binary association class.
     */
    protected BinaryAssociationClassOrObject( PlaceableNode source, PlaceableNode target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     PlaceableNode associationClassNode,
                     DiagramView diagram, MAssociation assoc ) {
        super( source, target, sourceEnd, targetEnd, diagram, assoc );
        fAssociationClassOrObjectNode = associationClassNode;
        fAssociationClassOrObjectNode.setAssocClassOrObject();
    }
    
    /**
     * Use this constructor if it is a binary object link.
     */
    protected BinaryAssociationClassOrObject( PlaceableNode source, PlaceableNode target,
                     MLinkEnd sourceEnd, MLinkEnd targetEnd,
                     PlaceableNode linkObjectNode,
                     NewObjectDiagram diagram, MLink link ) {
        super( source, target, sourceEnd, targetEnd, diagram, link );
        fAssociationClassOrObjectNode = linkObjectNode;
        fAssociationClassOrObjectNode.setAssocClassOrObject();
    }

    /**
     * Initializes this NodeEdge.
     */
    @Override
    protected void initializeFinal() {
    	super.initializeFinal();
    	
        connectionWayPoint = new WayPoint(this, WayPointType.ASSOC_CLASS_CON, getName(), fOpt);
        configureConnectionWayPoint();
        connectionWayPoint.initialize();
        
        PositionStrategy strategy = new StrategyRelativeToCorner(fAssociationClassOrObjectNode, connectionWayPoint, Direction.NORTH, 0, DeltaBasis.ABSOLUTE, -54, DeltaBasis.ABSOLUTE);
        fAssociationClassOrObjectNode.setStrategy(strategy);
        
        associationClassEdge = SimpleEdge.create(connectionWayPoint, fAssociationClassOrObjectNode, getName(), fOpt);
        associationClassEdge.setDashed(true);
        associationClassEdge.setColor(fOpt.getEDGE_COLOR());
    }

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		associationClassEdge.setSelected(selected);
	}

	/**
     * Draws this edge and the dashed edge to the association class.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
    	super.onDraw(g);
    	// begin by restoring the default color for the small line
    	associationClassEdge.setColor(fOpt.getEDGE_COLOR());
    	
    	if(fAssociationClassOrObjectNode instanceof ObjectNode) {
    		if(((ObjectNode) fAssociationClassOrObjectNode).isGreyed()) {
    			associationClassEdge.setColor(fOpt.getGREYED_LINE_COLOR());
    		}
    	}
    	
        associationClassEdge.onDraw(g);
    }
    
    
	@Override
	public void collectChildNodes(Map<String, PlaceableNode> allNodes) {
		super.collectChildNodes(allNodes);
		allNodes.put(connectionWayPoint.getId(), connectionWayPoint);
	}

	@Override
	public WayPoint addWayPoint(double x, double y) {
		WayPoint wp = super.addWayPoint(x, y);
		if (wp != null) {
			configureConnectionWayPoint();
		}
		return wp;
	}

	@Override
	public void removeWayPoint(WayPoint node) {
		super.removeWayPoint(node);
		configureConnectionWayPoint();
	}

	protected void configureConnectionWayPoint() {
		if (fWayPoints.size() % 2 == 0) {
			int middle = fWayPoints.size() / 2;
			PlaceableNode[] related = new PlaceableNode[] {fWayPoints.get(middle - 1), fWayPoints.get(middle)}; 
			connectionWayPoint.setStrategy(new StrategyInBetween(connectionWayPoint, related, 0, 0));
		} else {
			int middle = fWayPoints.size() / 2;
			connectionWayPoint.setStrategy(new StrategyIdentity(connectionWayPoint, fWayPoints.get(middle)));
		}
	}
	
	@Override
    protected String getStoreType() { return "NodeEdge"; }

	@Override
	protected void storeAdditionalInfo(PersistHelper helper, Element element,
			boolean hidden) {
		Element cNode = helper.createChild(element, "connectionNode");
		connectionWayPoint.storePlacementInfo(helper, cNode, hidden);
		
		cNode = helper.createChild(element, "associationClass");
		fAssociationClassOrObjectNode.storePlacementInfo(helper, cNode, hidden);
		
		super.storeAdditionalInfo(helper, element, hidden);
	}
	
	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, int version) {
		super.restoreAdditionalInfo(helper, version);
		
		helper.toFirstChild("connectionNode");
		helper.toFirstChild("edgeproperty");
		connectionWayPoint.restorePlacementInfo(helper, version);
		helper.getAllNodes().put(connectionWayPoint.getId(), connectionWayPoint);
		helper.toParent();
		helper.toParent();
		
		helper.toFirstChild("associationClass");
		helper.toFirstChild("node");
		fAssociationClassOrObjectNode.restorePlacementInfo(helper, version);
		helper.toParent();
		helper.toParent();
	}
	
	/**
     * Use this constructor if it is a binary association class.
     */
	public static BinaryAssociationClassOrObject create( PlaceableNode source, PlaceableNode target,
                     MAssociationEnd sourceEnd, MAssociationEnd targetEnd,
                     PlaceableNode associationClassNode,
                     DiagramView diagram, MAssociation assoc ) {
		BinaryAssociationClassOrObject edge = new BinaryAssociationClassOrObject(source, target, sourceEnd, targetEnd, associationClassNode, diagram, assoc);
		return edge;
    }
    
    /**
     * Use this constructor if it is a binary object link.
     */
	public static BinaryAssociationClassOrObject create ( PlaceableNode source, PlaceableNode target,
                     MLinkEnd sourceEnd, MLinkEnd targetEnd,
                     PlaceableNode linkObjectNode,
                     NewObjectDiagram diagram, MLink link ) {
		BinaryAssociationClassOrObject edge = new BinaryAssociationClassOrObject(source, target, sourceEnd, targetEnd, linkObjectNode, diagram, link);
		return edge;
    }
	
	public PlaceableNode getClassOrObjectNode() {
		return fAssociationClassOrObjectNode;
	}
	
}