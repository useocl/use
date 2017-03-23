/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.uml.mm.MAssociation;

/**
 * Represents the dashed line between an association class or link object
 * and the diamond node of an n-ary association.
 * @author Lars Hamann
 *
 */
public class NAryAssociationClassOrObjectEdge extends EdgeBase implements AssociationEdge {
	
    /**
     * The diamond node the edges and the dashed line is connected to.
     */
    DiamondNode fDiamondNode;
    
    /**
     * The NodeBase of instance ClassNode or ObjectNode displaying 
     * the class part of the associationclass / objectlink.
     */
    private PlaceableNode fAssociationClassOrLinkObjectNode;
    
    private SimpleEdge dashedEdge;
    
    /**
     * True, if the dashed line is connected to a link object.
     */
    private boolean isLink;
    
    private MAssociation association;
    
	/**
     * Use this constructor if it is an t-nary associationclass/objectlink.
     */
	protected NAryAssociationClassOrObjectEdge(DiamondNode diamondNode,
			PlaceableNode associationClassNode, DiagramView diagram,
			MAssociation assoc, boolean isLink) {
        super( diamondNode, associationClassNode, assoc.name(), diagram.getOptions(), true );
        this.isLink = isLink;
        fDiamondNode = diamondNode;
        fAssociationClassOrLinkObjectNode = associationClassNode;
        this.association = assoc;
    }
    
    /**
     * Initializes this NodeEdge.
     */
	@Override
    protected void initializeFinal() {
        this.dashedEdge = SimpleEdge.create(fDiamondNode, fAssociationClassOrLinkObjectNode, getName(), fOpt);
        this.dashedEdge.setDashed(true);
        this.dashedEdge.setColor(fOpt.getEDGE_COLOR());
    }

	/**
	 * @return the association
	 */
	public MAssociation getAssociation() {
		return association;
	}

	@Override
	protected String getIdInternal() {
		return "naryedge::" + fSource.getId() + "::" + fTarget.getId();
	}
	
    /**
     * Draws the dashed line starting at the mid point of the solid line.<br/>
     * Invokes draw on the diamond node
     * @param g The graphics object the dashed line is drawn into.
     */
	@Override
    public void onDraw( Graphics2D g ) {
		if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_COLOR() );
        }
                
        dashedEdge.onDraw( g );
        fDiamondNode.draw( g );
    }
    
	@Override
	public boolean isLink() {
		return isLink;
	}

	@Override
	protected String getStoreType() {
		return "NodeEdge";
	}

	@Override
	protected String getStoreKind() {
		return isLink() ? "link" : "association";
	}
	
	/**
     * Use this factory method if it is an t-nary associationclass/objectlink.
     */
	public static NAryAssociationClassOrObjectEdge create(
			DiamondNode diamondNode, PlaceableNode associationClassNode,
			DiagramView diagram, MAssociation assoc, boolean isLink) {
		
		NAryAssociationClassOrObjectEdge edge = new NAryAssociationClassOrObjectEdge(
				diamondNode, associationClassNode, diagram, assoc, isLink);
		
		return edge;
	}
	
	public PlaceableNode getClassOrLinkObjectNode() {
		return fAssociationClassOrLinkObjectNode;
	}
}
