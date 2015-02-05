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

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.gui.views.diagrams.DiagramView.DiagramData;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.edges.GeneralizationEdge;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * This class is used to keep track of
 * visible and hidden data in a class diagram.
 * @author Lars Hamann
 *
 */
public class ClassDiagramData implements DiagramData {
	/**
	 * All mappings from (association-)classes to nodes.
	 */
	public Map<MClass, ClassNode> fClassToNodeMap;
	
	
	public Map<MSignal, SignalNode> fSignalToNodeMap;
	
	/**
	 * 
	 */
	public Map<EnumType, EnumNode> fEnumToNodeMap;
	/**
	 * 
	 */
	public Map<MAssociation, BinaryAssociationOrLinkEdge> fBinaryAssocToEdgeMap;
	/**
	 * 
	 */
	public Map<MAssociationClass, EdgeBase> fAssocClassToEdgeMap;
	/**
	 * 
	 */
	public Map<MAssociation, DiamondNode> fNaryAssocToDiamondNodeMap;
	/**
	 * 
	 */
	public Map<MAssociation, List<EdgeBase>> fNaryAssocToHalfEdgeMap;
	/**
	 * 
	 */
	public Map<MGeneralization, GeneralizationEdge> fGenToGeneralizationEdge;

	/**
	 * 
	 */
	public ClassDiagramData() {
		fClassToNodeMap = new HashMap<MClass, ClassNode>();
        fEnumToNodeMap = new HashMap<EnumType, EnumNode>();
        fBinaryAssocToEdgeMap = new HashMap<MAssociation, BinaryAssociationOrLinkEdge>();
        fAssocClassToEdgeMap = new HashMap<MAssociationClass, EdgeBase>();
        fNaryAssocToDiamondNodeMap = new HashMap<MAssociation, DiamondNode>();
        fNaryAssocToHalfEdgeMap = new HashMap<MAssociation, List<EdgeBase>>();
        fGenToGeneralizationEdge = new HashMap<MGeneralization, GeneralizationEdge>();
        fSignalToNodeMap = new HashMap<>();
	}
	
	public boolean hasNodes() {
		return !(fClassToNodeMap.isEmpty() && fEnumToNodeMap.isEmpty());
	}

	public boolean hasEdges() {
		return !(fBinaryAssocToEdgeMap.isEmpty()
				&& fAssocClassToEdgeMap.isEmpty()
				&& fNaryAssocToDiamondNodeMap.isEmpty()
				&& fNaryAssocToHalfEdgeMap.isEmpty() 
				&& fGenToGeneralizationEdge.isEmpty());
	}
	
	@Override
	public Set<PlaceableNode> getNodes() {
		Set<PlaceableNode> result = new HashSet<PlaceableNode>(fClassToNodeMap.values());
		result.addAll(fEnumToNodeMap.values());
		result.addAll(fNaryAssocToDiamondNodeMap.values());
		return result;
	}

	@Override
	public Set<EdgeBase> getEdges() {
		Set<EdgeBase> result = new HashSet<EdgeBase>(fBinaryAssocToEdgeMap.values());
		result.addAll(fAssocClassToEdgeMap.values());
		for (Map.Entry<MAssociation, List<EdgeBase>> entry : fNaryAssocToHalfEdgeMap.entrySet()) {
			result.addAll(entry.getValue());
		}
		result.addAll(fGenToGeneralizationEdge.values());
		return result;
	}
	
	/**
	 * Searches for the given classifier and returns the node
	 * which represents it.
	 * @param cf
	 * @return
	 */
	public PlaceableNode getNode(MClassifier cf) {
		PlaceableNode n = this.fClassToNodeMap.get(cf);
		if (n != null) return n;
		return this.fEnumToNodeMap.get(cf);
	}
	
	public boolean containsNodeForClassifer(MClassifier cf) {
		return this.fClassToNodeMap.containsKey(cf) || this.fEnumToNodeMap.containsKey(cf);
	}

	/**
	 * Returns all placeable nodes for the given classifiers.
	 * @param selected
	 * @return
	 */
	public Set<PlaceableNode> getNodes(Set<MClassifier> classifier) {
		Set<PlaceableNode> nodes = new HashSet<PlaceableNode>();
		for (MClassifier cf : classifier) {
			PlaceableNode node = getNode(cf);
			if (node != null)
				nodes.add(node);
		}
		return nodes;
	}
	
	public Set<EdgeBase> getAllEdges() {
		Set<EdgeBase> res = new HashSet<EdgeBase>();
		res.addAll(this.fAssocClassToEdgeMap.values());
		res.addAll(this.fBinaryAssocToEdgeMap.values());
		res.addAll(this.fGenToGeneralizationEdge.values());
		for (List<EdgeBase> edges : this.fNaryAssocToHalfEdgeMap.values()) {
			res.addAll(edges);
		}
		
		return res;
	}
	
	/**
	 * Returns all {@link Rolename}s.
	 * @return
	 */
	public Set<Rolename> getAllRolenames() {
		Set<EdgeBase> allEdges = getAllEdges();
		Set<Rolename> res  = new HashSet<Rolename>();
			
		for (EdgeBase edge : allEdges) {
			for (EdgeProperty p : edge.getProperties()) {
				if (p instanceof Rolename)
					res.add((Rolename)p);
			}
		}
		
		return res;
	}
}