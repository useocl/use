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
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.NodeEdge;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.xmlparser.XMLParserAccess;
import org.tzi.use.gui.xmlparser.XMLParserAccessImpl;
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
 
    /**
     * The diagram the graph, nodes and edges belong to.
     */
    private NewObjectDiagram fDiagram;

    public ActionHideObjectDiagram( String text, Set<?> nodesToHide,
                                    Selection nodeSelection,
                                    DirectedGraph<NodeBase, EdgeBase> graph, LayoutInfos layoutInfos ) {
        super(text);
        setNodes( nodesToHide );
        
        fLayoutInfos = layoutInfos;
        fHiddenNodes = layoutInfos.getHiddenNodes();
        fHiddenEdges = layoutInfos.getHiddenEdges();
        fEdgeToBinaryEdgeMap = layoutInfos.getBinaryEdgeToEdgeMap();
        fNodeToNodeBaseMap = layoutInfos.getNodeToNodeMap();
        fNaryEdgeToDiamondNodeMap = layoutInfos.getNaryEdgeToDiamondNodeMap();
        fEdgeToHalfEdgeMap = layoutInfos.getNaryEdgeToHalfEdgeMap();
        fEdgeToNodeEdgeMap = layoutInfos.getEdgeNodeToEdgeMap();
        fDiagram = (NewObjectDiagram) layoutInfos.getDiagram();
        
        fNodeSelection = nodeSelection;
        fGraph = graph;
    }

    /**
     * Displays all hidden objects again. The objects have to be added
     * again, because they were deleted from the view before.
     */
    public void showAllHiddenElements() {
        // add all hidden objects
        Iterator<Object> it = fHiddenNodes.iterator();
        MObject obj = null;
        while (it.hasNext()) {
            obj = (MObject) it.next();
            fDiagram.addObject(obj);
        }
        fHiddenNodes.clear();

        // add all hidden links
        it = fHiddenEdges.iterator();
        while (it.hasNext()) {
            MLink link = (MLink) it.next();
            fDiagram.addLink(link);
        }
        fHiddenEdges.clear();
        
        XMLParserAccess xmlParser = new XMLParserAccessImpl( fLayoutInfos );
        xmlParser.loadXMLString( fLayoutInfos.getHiddenElementsXML(), false );
        fLayoutInfos.setHiddenElementsXML( "" );
        fLayoutXMLForHiddenElements = "";
    }    
    
    /**
     * Saves links which are connected to the hidden objects.
     */
    public Set<Object> saveEdges( Set<Object> objectsToHide ) {
        Set<Object> linksToHide = new HashSet<Object>();
        Set<Object> additionalObjToHide = new HashSet<Object>();
        
        Iterator<Object> it = objectsToHide.iterator();
        while ( it.hasNext() ) {
            MObject obj = (MObject) it.next();
            if ( obj instanceof MLinkObject ) {
                linksToHide.add( obj );
                additionalObjToHide.add( obj );
                NodeEdge ne = 
                    (NodeEdge) fEdgeToNodeEdgeMap.get( (MLinkObject) obj );
                NodeBase n = 
                    (NodeBase) fNodeToNodeBaseMap.get( (MObject) obj );
                fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                fLayoutXMLForHiddenElements += n.storePlacementInfo( true );
                
                // link object is participating in an nary link than save 
                // location of diamond as well.
                Set<MLinkEnd> naryLinkSet = ((MLink) obj).linkEnds();
                if ( naryLinkSet.size() > 2 ) {
                    NodeBase dn = 
                        (NodeBase) fNaryEdgeToDiamondNodeMap.get( (MLink) obj );
                    fLayoutXMLForHiddenElements += dn.storePlacementInfo( true );
                    
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
                Iterator<?> linkIt = fEdgeToBinaryEdgeMap.keySet().iterator();
                while ( linkIt.hasNext() ) {
                    MLink link = (MLink) linkIt.next();
                    if ( link.linkedObjects().contains( obj ) ) {
                        linksToHide.add( link );
                        // save layout information
                        if ( link instanceof MLinkObject ) {
                            NodeEdge ne = 
                                (NodeEdge) fEdgeToNodeEdgeMap.get( (MLinkObject) link );
                            fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                        } else {
                            EdgeBase e = 
                                (EdgeBase) fEdgeToBinaryEdgeMap.get( link );
                            fLayoutXMLForHiddenElements += e.storePlacementInfo( true );
                        }
                    }
                }
                
                // check if object is in one of the nary links
                Iterator<?> naryLinkIt = fNaryEdgeToDiamondNodeMap.keySet().iterator();
                while ( naryLinkIt.hasNext() ) {
                    MLink naryLink = (MLink) naryLinkIt.next();
                    
                    if ( naryLink.linkedObjects().contains( obj ) ) {
                        linksToHide.add( naryLink );
                        
                        // save layout information
                        if ( naryLink instanceof MLinkObject ) {
                            NodeEdge ne = 
                                (NodeEdge) fEdgeToNodeEdgeMap.get( (MLinkObject) naryLink );
                            fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                        } 
                        
                        // save diamond node
                        NodeBase n = 
                            (NodeBase) fNaryEdgeToDiamondNodeMap.get( naryLink );
                        fLayoutXMLForHiddenElements += n.storePlacementInfo( true );
                        
//                        // save HalfEdges
//                        List halfEdges = (ArrayList) fEdgeToHalfEdgeMap.get( naryLink );
//                        Iterator naryHalfEdgeIt = halfEdges.iterator();
//                        while ( naryHalfEdgeIt.hasNext() ) {
//                            HalfEdge he = (HalfEdge) naryHalfEdgeIt.next();
//                            fLayoutXMLForHiddenElements += he.storePlacementInfo( true );
//                        }

                    }
                }
                
                // check if object is participating in an link object
                Iterator<?> linkObjIt = fEdgeToNodeEdgeMap.keySet().iterator();
                while ( linkObjIt.hasNext() ) {
                    MLink linkObj = (MLink) linkObjIt.next();
                    if ( linkObj.linkedObjects().contains( obj ) ) {
                        linksToHide.add( linkObj );
                        additionalObjToHide.add( linkObj );
                        
                        // save layout information
                        if ( linkObj instanceof MLinkObject ) {
                            NodeEdge ne = 
                                (NodeEdge) fEdgeToNodeEdgeMap.get( (MLinkObject) linkObj );
                            NodeBase n = (NodeBase) fNodeToNodeBaseMap.get( (MObject) linkObj );
                            fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                            fLayoutXMLForHiddenElements += n.storePlacementInfo( true );
                        }
                    }
                }
            }
        }
        
        objectsToHide.addAll( additionalObjToHide );
        return linksToHide;
    }
    

    /**
     * Hides all objects with there connecting links.
     */
    public void hideNodesAndEdges() {
        Set<Object> objectsToHide = new HashSet<Object>();
        
        // hide objects
        Iterator<Object> it = fNodesToHide.iterator();
        while (it.hasNext()) {
            MObject obj = (MObject) it.next();
            NodeBase objToHideNode = (NodeBase) fNodeToNodeBaseMap.get(obj);
            
            // save position information about object
            Iterator<NodeBase> nodeIt = fGraph.iterator();
            while (nodeIt.hasNext()) {
                NodeBase node = nodeIt.next();
                if (node.equals(objToHideNode)) {
                    fLayoutXMLForHiddenElements += objToHideNode.storePlacementInfo( true );
                }
            }
            
            objectsToHide.add( obj );
        }
        
        // save links which are connected to the objects
        Set<Object> linksToHide = saveEdges( objectsToHide );
        
        fDiagram.deleteHiddenElementsFromDiagram( objectsToHide, linksToHide );
        
        fNodeSelection.clear();
        fDiagram.repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        hideNodesAndEdges();
        String xml = fLayoutInfos.getHiddenElementsXML()
                     + fLayoutXMLForHiddenElements;
        fLayoutInfos.setHiddenElementsXML( xml );
    }
    
    /** anfangs jj
     * Displays all hidden objects again. The objects have to be added
     * again, because they were deleted from the view before.
     */
    public void showHiddenElements(Set hiddenElements) {
        // add hidden objects
    	
    	HashSet classes = new HashSet();
    	Iterator t1 = fHiddenNodes.iterator();
    	while(t1.hasNext()){ // add schon gehiddenene Nodes
//    		classes.add(t1.next());
    		MObject node = (MObject)t1.next();
    		classes.add(node);
    	}
    	
    	Iterator t2 = hiddenElements.iterator();
    	while(t2.hasNext()){
    		MObject node = (MObject)t2.next();
    		classes.remove(node); // remove vorher schon gehidden nodes-> show
    	}
    	this.showAllHiddenElements();
    	
    	Iterator t3 = classes.iterator();
    	fNodesToHide.clear();
    	while(t3.hasNext()){
    		MObject node = (MObject)t3.next(); //aktuelle zustand nach fNodesToHide copieren
    		fNodesToHide.add(node);
    	}
//    	fNodesToHide =classes;
//    	fNodesToHidehiddenElements;
    	this.hideNodesAndEdges();
    	String xml = fLayoutInfos.getHiddenElementsXML()
        + fLayoutXMLForHiddenElements;
    	fLayoutInfos.setHiddenElementsXML( xml );
    }    
    //end jj

}