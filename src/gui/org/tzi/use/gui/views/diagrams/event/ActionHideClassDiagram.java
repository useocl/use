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
import java.util.List;
import java.util.Set;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.NodeEdge;
import org.tzi.use.gui.views.diagrams.classdiagram.NewClassDiagram;
import org.tzi.use.gui.xmlparser.XMLParserAccess;
import org.tzi.use.gui.xmlparser.XMLParserAccessImpl;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * Hides selected nodes and edges from a given diagram.
 *
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
  */
public final class ActionHideClassDiagram extends ActionHide {
    
    /**
     * The diagram the graph, nodes and edges belong to.
     */
    private NewClassDiagram fDiagram;
    
    public ActionHideClassDiagram( String text, Set nodesToHide,
                                   Selection nodeSelection, DirectedGraph graph,
                                   LayoutInfos layoutInfos ) {
        super( text );
        setNodes( nodesToHide );
        fLayoutInfos = layoutInfos;
        fHiddenNodes = layoutInfos.getHiddenNodes();
        fHiddenEdges = layoutInfos.getHiddenEdges();
        fEdgeToBinaryEdgeMap = layoutInfos.getBinaryEdgeToEdgeMap();
        fNodeToNodeBaseMap = layoutInfos.getNodeToNodeMap();
        fNaryEdgeToDiamondNodeMap = layoutInfos.getNaryEdgeToDiamondNodeMap();
        fEdgeToHalfEdgeMap = layoutInfos.getNaryEdgeToHalfEdgeMap();
        fEdgeToNodeEdgeMap = layoutInfos.getEdgeNodeToEdgeMap();
        fEnumToNodeMap = layoutInfos.getEnumToNodeMap();
        fGenToGeneralizationEdge = layoutInfos.getGenToGeneralizationEdge();
        fDiagram = (NewClassDiagram) layoutInfos.getDiagram();
        
        fNodeSelection = nodeSelection;
        fGraph = graph;
    }

    public void showAllHiddenElements() {
        // add all hidden nodes
        Iterator it = fHiddenNodes.iterator();
        MClass cls = null;
        while ( it.hasNext() ) {
            Object elem = it.next();
            if ( elem instanceof MClass ) {
                cls = (MClass) elem;
                fDiagram.addClass( cls );
            } else if ( elem instanceof EnumType ) {
                EnumType enumeration = (EnumType) elem;
                fDiagram.addEnum( enumeration );
            }
        }
        fHiddenNodes.clear();

        // add all hidden links
        it = fHiddenEdges.iterator();
        while ( it.hasNext() ) {
            MModelElement edge = (MModelElement) it.next();
            if ( edge instanceof MAssociation ) {
                MAssociation assoc = (MAssociation) edge;
                fDiagram.addAssociation( assoc );
            } else if ( edge instanceof MGeneralization ) {
                MGeneralization gen = (MGeneralization) edge;
                fDiagram.addGeneralization( gen );
            }

        }
        fHiddenEdges.clear();
        
        XMLParserAccess xmlParser = new XMLParserAccessImpl( fLayoutInfos );
        xmlParser.loadXMLString( fLayoutInfos.getHiddenElementsXML(), false );
        fLayoutInfos.setHiddenElementsXML( "" );
        fLayoutXMLForHiddenElements = "";
    }
    
    /**
     * Saves edges which are connected to the hidden nodes.
     */
    public Set saveEdges( Set nodesToHide ) {
        Set edgesToHide = new HashSet();
        Set additionalNodesToHide = new HashSet();
        
        Iterator it = nodesToHide.iterator();
        while ( it.hasNext() ) {
            Object elem = it.next();
            if ( elem instanceof EnumType ) {
                continue;
            }
            MClass cls = (MClass) elem;
            
            if ( cls instanceof MAssociationClass ) {
                edgesToHide.add( cls );
                additionalNodesToHide.add( cls );
                NodeEdge ne = 
                    (NodeEdge) fEdgeToNodeEdgeMap.get( cls );
                NodeBase n = 
                    (NodeBase) fNodeToNodeBaseMap.get( cls );
                fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                fLayoutXMLForHiddenElements += n.storePlacementInfo( true );
                
                // associationclass is participating in an nary link than save 
                // location of diamond as well.
                List naryEdgeList = ((MAssociation) cls).associationEnds();
                if ( naryEdgeList.size() > 2 ) {
                    NodeBase dn = 
                        (NodeBase) fNaryEdgeToDiamondNodeMap.get( cls );
                    fLayoutXMLForHiddenElements += dn.storePlacementInfo( true );
                    
//                    // save HalfEdges
//                    Set halfEdges = (Set) fEdgeToHalfEdgeMap.get( cls );
//                    Iterator naryHalfEdgesIt = halfEdges.iterator();
//                    while ( naryHalfEdgesIt.hasNext() ) {
//                        HalfEdge he = (HalfEdge) naryHalfEdgesIt.next();
//                        fLayoutXMLForHiddenElements += he.storePlacementInfo( true );
//                    }
                }
            } else {
                // check if node is in one of the binary edges
                Iterator edgeIt = fEdgeToBinaryEdgeMap.keySet().iterator();
                while ( edgeIt.hasNext() ) {
                    MAssociation assoc = (MAssociation) edgeIt.next();
                    if ( assoc.associatedClasses().contains( cls ) ) {
                        edgesToHide.add( assoc );
                        // save layout information
                        if ( assoc instanceof MAssociationClass ) {
                            NodeEdge ne = 
                                (NodeEdge) fEdgeToNodeEdgeMap.get( assoc );
                            fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                        } else {
                            EdgeBase e = 
                                (EdgeBase) fEdgeToBinaryEdgeMap.get( assoc );
                            fLayoutXMLForHiddenElements += e.storePlacementInfo( true );
                        }
                    }
                }
                
                // check if node is in one of the nary edges
                Iterator naryEdgeIt = fNaryEdgeToDiamondNodeMap.keySet().iterator();
                while ( naryEdgeIt.hasNext() ) {
                    MAssociation naryEdge = (MAssociation) naryEdgeIt.next();
                    
                    if ( naryEdge.associatedClasses().contains( cls ) ) {
                        edgesToHide.add( naryEdge );
                        
                        // save layout information
                        if ( naryEdge instanceof MAssociationClass ) {
                            NodeEdge ne = 
                                (NodeEdge) fEdgeToNodeEdgeMap.get( naryEdge );
                            fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                        } 
                        
                        // save diamond node
                        NodeBase n = 
                            (NodeBase) fNaryEdgeToDiamondNodeMap.get( naryEdge );
                        fLayoutXMLForHiddenElements += n.storePlacementInfo( true );
                        
//                        // save HalfEdges
//                        List halfEdges = (ArrayList) fEdgeToHalfEdgeMap.get( naryEdge );
//                        Iterator naryHalfEdgeIt = halfEdges.iterator();
//                        while ( naryHalfEdgeIt.hasNext() ) {
//                            HalfEdge he = (HalfEdge) naryHalfEdgeIt.next();
//                            fLayoutXMLForHiddenElements += he.storePlacementInfo( true );
//                        }
                    }
                }
                
                // check if node is participating in an associationclass
                Iterator edgeNodeIt = fEdgeToNodeEdgeMap.keySet().iterator();
                while ( edgeNodeIt.hasNext() ) {
                    MAssociation assoc = (MAssociation) edgeNodeIt.next();
                    if ( assoc.associatedClasses().contains( cls ) ) {
                        edgesToHide.add( assoc );
                        additionalNodesToHide.add( assoc );
                        
                        // save layout information
                        if ( assoc instanceof MAssociationClass ) {
                            NodeEdge ne = 
                                (NodeEdge) fEdgeToNodeEdgeMap.get( assoc );
                            NodeBase n = (NodeBase) fNodeToNodeBaseMap.get( assoc );
                            fLayoutXMLForHiddenElements += ne.storePlacementInfo( true );
                            fLayoutXMLForHiddenElements += n.storePlacementInfo( true );
                        }
                    }
                }
            }
        }
        
        nodesToHide.addAll( additionalNodesToHide );
        edgesToHide.addAll( saveGeneralizations( nodesToHide ) );
        return edgesToHide;
    }
    
    private Set saveGeneralizations( Set nodesToHide ) {
        Set genEdgesToHide = new HashSet();
        DirectedGraph genGraph = null;
        
        // just getting the generalization graph from the model.
        Iterator it = nodesToHide.iterator();
        while ( it.hasNext() ) {
            Object elem = it.next();
            if ( elem instanceof MClass ) {
                genGraph = ((MClass) elem).model().generalizationGraph();
                break;
            }
        }
        
        // saving the generalization edges.
        if ( genGraph != null ) {
            it = genGraph.edgeIterator();
            while ( it.hasNext() ) {
                MGeneralization gen = (MGeneralization) it.next();
                if ( nodesToHide.contains( gen.parent() ) 
                        || nodesToHide.contains( gen.child() ) ) {
                    genEdgesToHide.add( gen );
                    EdgeBase e = (EdgeBase) fGenToGeneralizationEdge.get( gen );
                    fLayoutXMLForHiddenElements += e.storePlacementInfo( true );
                }
            }
        }
        return genEdgesToHide;
    }

    /**
     * Hides all nodes with there connecting edges.
     */
    public void hideNodesAndEdges() {
        Set nodesToHide = new HashSet();
        
        // hide objects
        Iterator it = fNodesToHide.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            NodeBase nodeToHide = null;
            
            if ( elem instanceof MClass ) {
                MClass cls = (MClass) elem;
                nodeToHide = (NodeBase) fNodeToNodeBaseMap.get( cls );
            } else if ( elem instanceof EnumType ) {
                EnumType enumeration = (EnumType) elem;
                nodeToHide = (NodeBase) fEnumToNodeMap.get( enumeration );
            }    
            
            // save position information of the node
            Iterator nodeIt = fGraph.iterator();
            while ( nodeIt.hasNext() ) {
                NodeBase node = (NodeBase) nodeIt.next();
                if ( node.equals( nodeToHide ) ) {
                    fLayoutXMLForHiddenElements += nodeToHide.storePlacementInfo( true );
                }
            }
            nodesToHide.add( elem );
        }
        
        // save edges which are connected to the nodes
        Set edgesToHide = saveEdges( nodesToHide );
        
        fDiagram.deleteHiddenElementsFromDiagram( nodesToHide, edgesToHide );
        
        fNodeSelection.clear();
        fDiagram.repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        hideNodesAndEdges();
        String xml = fLayoutInfos.getHiddenElementsXML()
                     + fLayoutXMLForHiddenElements;
        fLayoutInfos.setHiddenElementsXML( xml );
    }


}