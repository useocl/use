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

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.edges.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyFixed;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyInBetween;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.w3c.dom.Element;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * A pseude-node representing a diamond in an n-ary association.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class DiamondNode extends PlaceableNode {
    private final DiagramOptions fOpt;
    private final MAssociation fAssoc;
    private MLink fLink;
    private AssociationName fAssocName;
    private final List<String> fConnectedNodes;
    private final String fName;
    private List<ObjectNode> linkedObjectNodes;
    
    private List<EdgeBase> fHalfEdges; // participating edges
    
    public DiamondNode( MAssociation assoc, DiagramOptions opt ) {
        fAssoc = assoc;
        fName = assoc.name();
        fOpt = opt;
        fConnectedNodes = new ArrayList<String>();
        Set<MClass> classes = fAssoc.associatedClasses();
        
        for (MClass cls : classes) {
            fConnectedNodes.add( cls.name() );
        }
        
        if (!(assoc instanceof MAssociationClass)) {
        	instanciateAssocName(false);
        }
    }

    public DiamondNode( MLink link, DiagramOptions opt, List<ObjectNode> linkedObjectNodes ) {
        fAssoc = link.association();
        fLink = link;
        fName = fAssoc.name();
        fOpt = opt;
        this.linkedObjectNodes = linkedObjectNodes;
        fConnectedNodes = new ArrayList<String>();
        List<MObject> objects = link.linkedObjects();
        
        for (MObject obj : objects) {
            fConnectedNodes.add( obj.name() );
        }
        
        if (!(fAssoc instanceof MAssociationClass)) {
        	instanciateAssocName(true);
        }
    }
    
    private void instanciateAssocName(boolean isLink) {
        fAssocName = new AssociationName( getId() + "::AssociationName", fName, fOpt, this, fAssoc, fLink );
    }
    
    public MAssociation association() {
        return fAssoc;
    }

    public MLink link() {
        return fLink;
    }
    
    @Override
    public String getId() {
    	return fAssoc.name() + "::DiamondNode"; 
    }
    
    @Override
    public String name() {
        return fAssoc.name();
    }
    
    public void setHalfEdges( List<EdgeBase> edges, List<String> edgeIds) {
        fHalfEdges = edges;
        Set<PlaceableNode> related = new HashSet<PlaceableNode>();
        for (int i = 0; i < edges.size(); ++i) {
        	EdgeBase e = edges.get(i);
        	related.add(e.target());
        	e.setIdSuffix("::" + edgeIds.get(i));
        }
        
        if (related.size() > 1)
        	this.setStrategy(new StrategyInBetween(this, related.toArray(new PlaceableNode[0]), 0, 0));
    }
    
    private boolean isAdjacentNodeGreyed() {
    	// just return false, if this is a class
    	if(linkedObjectNodes == null) {
    		return false;
    	}
    	
    	for(ObjectNode node : linkedObjectNodes) {
    		if(node.isGreyed()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Draws a diamond with an underlined label in the object diagram.
     */
    @Override
    public void onDraw( Graphics2D g ) {
    	
        if ( isSelected() ) {
            g.setColor( fOpt.getNODE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getDIAMONDNODE_COLOR() );    
        }

        Shape ourShape = getShape();
        g.fill( ourShape );
        if (isAdjacentNodeGreyed()) {
        	g.setColor( fOpt.getGREYED_LINE_COLOR() );
        } else {
        	g.setColor( fOpt.getDIAMONDNODE_FRAME_COLOR() );
        }
                
        g.draw( ourShape );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
        }
        
        if ( fAssocName != null && fOpt.isShowAssocNames() ) {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );
            fAssocName.draw( g );
        }
        g.setColor( fOpt.getDIAMONDNODE_COLOR() );
    }

    
    @Override
	public Shape getShape() {
    	Path2D.Double path = new Path2D.Double();
    	Rectangle2D bounds = getBounds();
    	
		path.moveTo(bounds.getX(), bounds.getCenterY());
		path.lineTo(bounds.getCenterX(), bounds.getMinY());
		path.lineTo(bounds.getMaxX(), bounds.getCenterY());
		path.lineTo(bounds.getCenterX(), bounds.getMaxY());
		path.closePath();
    	    	
    	return path;
    }

	@Override
	public void setDraggedPosition(double deltaX, double deltaY) {
		// The first dragging of a diamond node switches its
		// positioning strategy to "fixed".
		if (this.getStrategy() instanceof StrategyInBetween)
			this.setStrategy(StrategyFixed.instance);
		
		super.setDraggedPosition(deltaX, deltaY);
	}

	public void resetPositionStrategy(){
		Set<PlaceableNode> related = new HashSet<PlaceableNode>();
		
		for (EdgeBase edgeBase : fHalfEdges) {
			related.add(edgeBase.target());
		}
		
		if(related.size() > 1){
			this.setStrategy(new StrategyInBetween(this, related.toArray(new PlaceableNode[0]), 0, 0));
		}
	}
	
	@Override
	public PlaceableNode getRelatedNode(double x, double y) {
		if (fAssocName != null && fAssocName.occupies(x, y))
			return fAssocName;
		
		return super.getRelatedNode(x, y);
	}
    
    @Override
    public void doCalculateSize( Graphics2D g ) {
    	setExactWidth(40);
    	setExactHeight(20);
    	if (fAssocName != null) fAssocName.calculateSize(g);
    }
    
    @Override
    public String getStoreType() {
    	return "DiamondNode";
    }
    
    @Override
    public String getStoreKind() {
    	return (fLink != null ? "link" : "association");
    }
    
    @Override
    public void storeAdditionalInfo( PersistHelper helper, Element nodeElement, boolean hidden ) {
                
        for(String nodeName : fConnectedNodes ) {
        	helper.appendChild( nodeElement, LayoutTags.CON_NODE, nodeName);
        }

        if (fAssocName != null) {
        	fAssocName.storePlacementInfo( helper, nodeElement, hidden );
        }
        
        if ( fHalfEdges != null ) {
            for (EdgeBase e : fHalfEdges) {
            	e.storePlacementInfo( helper, nodeElement, hidden);
            }
        }
    }

    @Override
    public void restoreAdditionalInfo( PersistHelper helper, int version ) {
    	// Restore association name
    	if (fAssocName != null) {
    		helper.toFirstChild(LayoutTags.EDGEPROPERTY);
    		fAssocName.restorePlacementInfo(helper, version);
    		helper.toParent();
    	}
    	    	
        if ( fHalfEdges != null ) {
            for (EdgeBase e : fHalfEdges) {
            	if (e instanceof AssociationOrLinkPartEdge) {
            		AssociationOrLinkPartEdge partEdge = (AssociationOrLinkPartEdge)e; 
					String xpathExpr = "./edge[@type='" + LayoutTags.HALFEDGE + "' and name='" + partEdge.getName() + "']";
            	
					helper.getNav().push();
					AutoPilot ap = new AutoPilot(helper.getNav());
					try {
						ap.selectXPath(xpathExpr);
            	
						try {
							if (ap.evalXPath() != -1)
								e.restorePlacementInfo(helper, version);
							
						} catch (XPathEvalException ex) {
							helper.getLog().append(ex.getMessage());
						} catch (NavException ex) {
							helper.getLog().append(ex.getMessage());
						}
					} catch (XPathParseException ex) {
						helper.getLog().append(ex.getMessage());
					} finally {
						helper.getNav().pop();
					}
            	}
            }
        }
    }
}