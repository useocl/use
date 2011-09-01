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
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A pseude-node representing a diamond in an n-ary association.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class DiamondNode extends NodeBase {
    private DiagramOptions fOpt;
    private MAssociation fAssoc;
    private MLink fLink;
    private AssociationName fAssocName;
    private List<String> fConnectedNodes;
    private String fName;
    
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
        instanciateAssocName(false);
    }

    public DiamondNode( MLink link, DiagramOptions opt ) {
        fAssoc = link.association();
        fLink = link;
        fName = fAssoc.name();
        fOpt = opt;
        fConnectedNodes = new ArrayList<String>();
        List<MObject> objects = link.linkedObjects();
        
        for (MObject obj : objects) {
            fConnectedNodes.add( obj.name() );
        }
        instanciateAssocName(true);
    }
    
    private void instanciateAssocName(boolean isLink) {
        fAssocName = new AssociationName( fName, fConnectedNodes, 
                                          fOpt, this, fAssoc, isLink );
    }
    
    public MAssociation association() {
        return fAssoc;
    }

    public MLink link() {
        return fLink;
    }
    
    public String name() {
        return fAssoc.name();
    }
    
    public void setHalfEdges( List<EdgeBase> edges ) {
        fHalfEdges = edges;
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
        
        Polygon dim = dimension();
        g.fillPolygon( dim );
        
        g.setColor( fOpt.getDIAMONDNODE_FRAME_COLOR() );
        
        g.drawPolygon( dim );
        
        if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );    
        }
        if ( fOpt.isShowAssocNames() ) {
            g.setColor( fOpt.getEDGE_LABEL_COLOR() );
            fAssocName.draw( g );
        }
        g.setColor( fOpt.getDIAMONDNODE_COLOR() );
    }

    public Polygon dimension() {
        int x1 = (int) getX();
        int y1 = (int) getY();
        int[] xpoints = { x1 + 20, x1 + 40, x1 + 20, x1      };
        int[] ypoints = { y1     , y1 + 10, y1 + 20, y1 + 10 };
        return new Polygon( xpoints, ypoints, xpoints.length );
    }

    /**
     * Returns if the point x,y is contained in this polygon.
     */
    @Override
    public boolean occupies( double x, double y ) {
        return dimension().contains( x, y );
    }

    /**
     * Sets this node selected.
     */
    //public void setSelected( boolean on ) {}

    /**
     * Returns weather or not this object is deletable.
     */
    public boolean isDeletable() {
        return false;
    }
        
    @Override
    public void setRectangleSize( Graphics2D g ) {
    	setWidth(dimension().getBounds2D().getWidth());
    	setHeight(dimension().getBounds2D().getHeight());
    }
    
    public EdgeProperty getAssocName() {
        return fAssocName;
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

        fAssocName.storePlacementInfo( helper, nodeElement, hidden );
    
        if ( fHalfEdges != null ) {
            for (EdgeBase e : fHalfEdges) {
            	e.storePlacementInfo( helper, nodeElement, hidden);
            }
        }
    }

    @Override
    public void restoreAdditionalInfo( PersistHelper helper, Element nodeElement, String version ) {
    	// Restore association name
    	NodeList childs = helper.getChildElementsByTagName(nodeElement, LayoutTags.EDGEPROPERTY);
    	
    	Element assocNameElement = (Element)childs.item(0);
    	fAssocName.restorePlacementInfo(helper, assocNameElement, version);

        if ( fHalfEdges != null ) {
            for (EdgeBase e : fHalfEdges) {
            	if (e instanceof AssociationOrLinkPartEdge) {
            		AssociationOrLinkPartEdge partEdge = (AssociationOrLinkPartEdge)e; 
					String xpathExpr = "./edge[@type='" + LayoutTags.HALFEDGE +
									   "' and edgeproperty[@type='" + LayoutTags.ROLENAME + 
									   "' and @kind='" + LayoutTags.TARGET + "']/name='"
									   + partEdge.fTargetEnd.nameAsRolename() + "']";
            	
					Element halfEdgeElement = helper.getElementByExpression(nodeElement, xpathExpr);
            	
					if (halfEdgeElement != null)
						e.restorePlacementInfo(helper, halfEdgeElement, version);
            	}
            }
        }
    }
}