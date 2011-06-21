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

package org.tzi.use.gui.views.diagrams.event;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/**
 * Hides the selected objects.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
  */
@SuppressWarnings("serial")
public final class ActionHideObjectDiagram extends ActionHide {
 
    public ActionHideObjectDiagram( String text, Set<?> nodesToHide,
                                    Selection<Selectable> nodeSelection,
                                    DirectedGraph<NodeBase, EdgeBase> graph, LayoutInfos layoutInfos ) {
        super(text);
        setNodes( nodesToHide );
        
        fLayoutInfos = layoutInfos;
        fNodeSelection = nodeSelection;
        fGraph = graph;
    }

    /**
     * Easier access to the object diagram
     * @return
     */
    protected NewObjectDiagram getDiagram() {
    	return (NewObjectDiagram)fLayoutInfos.getDiagram();
    }
    
    /**
     * Displays all hidden objects again. The objects have to be added
     * again, because they were deleted from the view before.
     */
    public void showAllHiddenElements() {
    	getDiagram().showAll();
        getDiagram().invalidateContent();
    }    
    
    /**
     * Saves links which are connected to the hidden objects.
     */
    public Set<Object> saveEdges( Set<Object> objectsToHide ) {
        Set<Object> linksToHide = new HashSet<Object>();
        Set<Object> additionalObjToHide = new HashSet<Object>();
        
        StringBuilder layoutXml = new StringBuilder(this.fLayoutXMLForHiddenElements);
        
        Iterator<Object> it = objectsToHide.iterator();
        while ( it.hasNext() ) {
            MObject obj = (MObject) it.next();
            if ( obj instanceof MLinkObject ) {
                linksToHide.add( obj );
                additionalObjToHide.add( obj );
                BinaryAssociationClassOrObject ne = 
                    (BinaryAssociationClassOrObject) fLayoutInfos.getEdgeNodeToEdgeMap().get( (MLinkObject) obj );
                NodeBase n = fLayoutInfos.getNodeToNodeMap().get( (MObject) obj );
                
                layoutXml.append(ne.storePlacementInfo( true ));
                layoutXml.append(n.storePlacementInfo( true ));
                
                // link object is participating in an nary link than save 
                // location of diamond as well.
                Set<MLinkEnd> naryLinkSet = ((MLink) obj).linkEnds();
                if ( naryLinkSet.size() > 2 ) {
                    NodeBase dn = fLayoutInfos.getNaryEdgeToDiamondNodeMap().get( (MLink) obj );
                    
                    layoutXml.append(dn.storePlacementInfo( true ));
                    
//                    // save HalfEdges
//                    Iterator naryLinkEndIt = naryLinkSet.iterator();
//                    while ( naryLinkEndIt.hasNext() ) {
//                        MLinkEnd naryLinkEnd = (MLinkEnd) naryLinkEndIt.next();
//                        HalfEdge he = (HalfEdge) fEdgeToHalfEdgeMap.get( naryLinkEnd );
//                        fLayoutXMLForHiddenElements += he.storePlacementInfo( true );
//                    }
                }
            } else {
                // check if object is in one of the binary links
                Iterator<?> linkIt = fLayoutInfos.getBinaryEdgeToEdgeMap().keySet().iterator();
                while ( linkIt.hasNext() ) {
                    MLink link = (MLink) linkIt.next();
                    if ( link.linkedObjects().contains( obj ) ) {
                        linksToHide.add( link );
                        // save layout information
                        if ( link instanceof MLinkObject ) {
                            BinaryAssociationClassOrObject ne = 
                                (BinaryAssociationClassOrObject) fLayoutInfos.getEdgeNodeToEdgeMap().get( (MLinkObject) link );
                            layoutXml.append(ne.storePlacementInfo( true ));
                        } else {
                            EdgeBase e = fLayoutInfos.getBinaryEdgeToEdgeMap().get( link );
                            layoutXml.append(e.storePlacementInfo( true ));
                        }
                    }
                }
                
                // check if object is in one of the nary links
                Iterator<?> naryLinkIt = fLayoutInfos.getNaryEdgeToDiamondNodeMap().keySet().iterator();
                while ( naryLinkIt.hasNext() ) {
                    MLink naryLink = (MLink) naryLinkIt.next();
                    
                    if ( naryLink.linkedObjects().contains( obj ) ) {
                        linksToHide.add( naryLink );
                        
                        // save layout information
                        if ( naryLink instanceof MLinkObject ) {
                            BinaryAssociationClassOrObject ne = 
                                (BinaryAssociationClassOrObject) fLayoutInfos.getEdgeNodeToEdgeMap().get( (MLinkObject) naryLink );
                            layoutXml.append(ne.storePlacementInfo( true ));
                        } 
                        
                        // save diamond node
                        NodeBase n = 
                        	fLayoutInfos.getNaryEdgeToDiamondNodeMap().get( naryLink );
                        layoutXml.append(n.storePlacementInfo( true ));
                    }
                }
                
                // check if object is participating in an link object
                Iterator<?> linkObjIt = fLayoutInfos.getEdgeNodeToEdgeMap().keySet().iterator();
                while ( linkObjIt.hasNext() ) {
                    MLink linkObj = (MLink) linkObjIt.next();
                    if ( linkObj.linkedObjects().contains( obj ) ) {
                        linksToHide.add( linkObj );
                        additionalObjToHide.add( linkObj );
                        
                        // save layout information
                        if ( linkObj instanceof MLinkObject ) {
                            BinaryAssociationClassOrObject ne = 
                                (BinaryAssociationClassOrObject) fLayoutInfos.getEdgeNodeToEdgeMap().get( (MLinkObject) linkObj );
                            NodeBase n = fLayoutInfos.getNodeToNodeMap().get( (MObject) linkObj );
                            layoutXml.append(ne.storePlacementInfo( true ));
                            layoutXml.append(n.storePlacementInfo( true ));
                        }
                    }
                }
            }
        }
        
        this.fLayoutXMLForHiddenElements = layoutXml.toString();
        objectsToHide.addAll( additionalObjToHide );
        return linksToHide;
    }
    

    /**
     * Hides all objects with there connecting links.
     */
    public void hideNodesAndEdges() {        
        fLayoutInfos.getDiagram().hideElementsInDiagram( fNodesToHide );
        fNodeSelection.clear();
        getDiagram().invalidateContent();
    }
    
    public void actionPerformed(ActionEvent e) {
        hideNodesAndEdges();
        String xml = fLayoutInfos.getHiddenElementsXML()
                     + fLayoutXMLForHiddenElements;
        fLayoutInfos.setHiddenElementsXML( xml );
    }
    
    /*
     * Displays all hidden objects again. The objects have to be added
     * again, because they were deleted from the view before.
     */
    public void showHiddenElements(Set<?> hiddenElements) {
        
    	// Add all already hidden nodes
    	Set<Object> nodesToHide = new HashSet<Object>(fLayoutInfos.getHiddenNodes());

    	// Remove all supplied nodes (don't hide them anymore)
    	nodesToHide.removeAll(hiddenElements);

    	// Shows all Nodes and Edges
    	// (Copies hiddenObjects to objects...)
    	this.showAllHiddenElements();
    	
    	fNodesToHide.clear();
    	fNodesToHide.addAll(nodesToHide);

    	this.hideNodesAndEdges();
    	
    	String xml = fLayoutInfos.getHiddenElementsXML()
        + fLayoutXMLForHiddenElements;
    	fLayoutInfos.setHiddenElementsXML( xml );
    }
}