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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.AbstractAction;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.BinaryEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.GeneralizationEdge;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.NodeEdge;
import org.tzi.use.gui.views.diagrams.classdiagram.EnumNode;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public abstract class ActionHide extends AbstractAction {
    /**
     * All nodes which are suppose to be hidden in a diagram.
     */
    Set<Object> fNodesToHide;
    /**
     * All actuall hidden nodes in a diagram.
     */
    Set<Object> fHiddenNodes;
    /**
     * All actuall hidden edges in a diagram.
     */
    Set<Object> fHiddenEdges;
    /**
     * Mapping from an edge (Associaiton/Link) to a BinaryEdge.
     * (MAssociation -> BinaryEdge) or (MLink -> BinaryEdge)
     */
    Map<?, BinaryEdge> fEdgeToBinaryEdgeMap;
    /**
     * Mapping from a node (Class/Object) to a NodeBase.
     * (MClass -> ClassNode) or (MObject -> ObjectNode)
     */
    Map<?, ? extends NodeBase> fNodeToNodeBaseMap;
    /**
     * Mapping from an n-ary edge (Associaiton/Link) to a DiamondNode.
     * (MAssociation -> DiamondNode) or (MLink -> DiamondNode)
     */
    Map<?, DiamondNode> fNaryEdgeToDiamondNodeMap;
    /**
     * Mapping from an edge (Associaiton/Link) to a Set of HalfEdges.
     * (MAssociation -> Set(HalfEdge)) or (MLink -> Set(HalfEdge))
     */
    Map<?, List<EdgeBase>> fEdgeToHalfEdgeMap;
    /**
     * Mapping from an edge node (AssociaitonClass/LinkObject) to an NodeEdge.
     * (MAssociationClass -> NodeEdge) or (MLinkObject -> NodeEdge)
     */
    Map<?, NodeEdge> fEdgeToNodeEdgeMap;
    /**
     * Mapping from a generalization to an GeneralizationEdge.
     * (MGeneralization -> GeneralizationEdge)
     */
    Map<MGeneralization, GeneralizationEdge> fGenToGeneralizationEdge;
    /**
     * Mapping from an enumeration to an EnumNode.
     * (EnumType -> EnumNode)
     */
    Map<EnumType, EnumNode> fEnumToNodeMap;
    /**
     * All hidden nodes or edges are saved into these properties.
     */
    Properties fPropForHiddenElements;
    /**
     * Actual selected nodes in the diagram.
     */
    Selection fNodeSelection;
    /**
     * This graph contains all nodes and edges of a diagram.  
     */
    DirectedGraph<NodeBase, EdgeBase> fGraph;
    /**
     * The diagram the graph, nodes and edges belong to.
     */
    DiagramView fDiagram;
    /**
     * XML representation of the hidden element.
     */
    String fLayoutXMLForHiddenElements = "";
    /**
     * Layout information about the given diagram. 
     */
    LayoutInfos fLayoutInfos;
 
    ActionHide( String text ) {
        super( text );
    }


    void setNodes( Set<?> set ) {
        if ( set != null ) {
            fNodesToHide = new HashSet<Object>(set);
        }
    }
    
    /**
     * Displays all hidden elements again. The hidden elements need to be 
     * added to the diagram again, because they were deleted from the 
     * view before.
     */
    public abstract void showAllHiddenElements();
    
    /**
     * Saves edges which are connected to the hidden nodes.
     */
    public abstract Set<Object> saveEdges( Set<Object> nodesToHide );
    
    /**
     * Hides all nodes with there connecting edges.
     */
    public abstract void hideNodesAndEdges();
    
}