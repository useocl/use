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

package org.tzi.use.gui.views.diagrams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.util.Direction;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

/**
 * This class controls the placement of
 * edges in a diagram. The main responsibilities are:
 * <h1>Space between multiple edges connecting the same nodes</h2>
 * <p>If two nodes are connected with more than one
 * edge, this class handles the space between these
 * edges to avoid the a merging of the edges while drawing.</p>
 * 
 * <h1>Position of reflexive edges</h1>
 * <p>Reflexive Associations are places "around" a node.</p>   
 * 
 * @author Lars Hamann
 * @since 3.1.0
 */
public class DiagramGraph extends DirectedGraphBase<PlaceableNode, EdgeBase>  {
	
	//TODO: Extract generic interface like "DiagramContent"
	private Set<PlaceableNode> uninitializedNodes = new HashSet<PlaceableNode>();
	
	private Set<EdgeBase> uninitializedEdges = new HashSet<EdgeBase>();
	
	private Set<PlaceableNode> invalidatedNodes = new HashSet<PlaceableNode>();
	
	private Set<EdgeBase> invalidatedEdges = new HashSet<EdgeBase>();
	
	private boolean allInvalid = false;
	
	private static class EdgeComparator implements Comparator<EdgeBase> {
		@Override
		public int compare(EdgeBase o1, EdgeBase o2) {
			if (o1 == o2) return 0;
			
			// We first compare the names of the edges to
			// retrieve the same order between different nodes using
			// the same kind of edges, like links between objects
			// if both are equal the ids are used
			int res = o1.getName().compareTo(o2.getName());
			if (res == 0) {
				res = o1.getId().compareTo(o2.getId());
			}
			return res;
		}
	}

	public DiagramGraph() { 
		// Edge order is important for restoring the layout
		super(null, new EdgeComparator());
	}
	
	/**
	 * @return the uninitializedNodes
	 */
	public Set<PlaceableNode> getUninitializedNodes() {
		return uninitializedNodes;
	}

	/**
	 * @return the uninitializedEdges
	 */
	public Set<EdgeBase> getUninitializedEdges() {
		return uninitializedEdges;
	}

	public void clearUninitialized() {
		this.uninitializedEdges.clear();
		this.uninitializedNodes.clear();
	}
	
	/**
	 * Initializes all uninitialized nodes and edges
	 */
	public void initialize() {
		
		// First set possible offsets in deterministic order
		
		// Save handled edges, because a single iteration handles
		// all edges between the same nodes.
		Set<EdgeBase> handledEdges = new HashSet<EdgeBase>();
		for (EdgeBase e : getUninitializedEdges()) {
			if (handledEdges.contains(e)) continue;
			
			if (e.isReflexive()) {
				handledEdges.addAll(setReflexivePositions(e.source()));
			} else {
				Set<EdgeBase> edges = this.edgesBetween(e.source(), e.target());
				setEdgeOffset(edges);
				handledEdges.addAll(edges);
			}
		}
		
		for (EdgeBase e : getUninitializedEdges()) {
        	e.initialize();
        }
        
        for (PlaceableNode n : getUninitializedNodes()) {
        	n.initialize();
        }
        
        clearUninitialized();
	}
		
	public synchronized boolean addInitializedEdge(EdgeBase e) {
		invalidatedEdges.add(e);
		return super.addEdge(e);
	}
	
	@Override
	public synchronized boolean addEdge(EdgeBase e) {
		uninitializedEdges.add(e);
		invalidatedEdges.add(e);
		return super.addEdge(e);
	}

	@Override
	public synchronized boolean removeEdge(EdgeBase e) {
		if (super.removeEdge(e)) {
			if (!e.isReflexive()) {
				setEdgeOffset(edgesBetween(e.source(), e.target()));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public synchronized boolean add(PlaceableNode o) {
		uninitializedNodes.add(o);
		invalidatedNodes.add(o);
		return super.add(o);
	}

	@Override
	public synchronized boolean remove(Object o) {
		uninitializedNodes.remove(o);
		invalidatedNodes.remove(o);
		return super.remove(o);
	}

	@Override
	protected void onEdgeRemoved(EdgeBase edge) {
		// Since DirectedGraphBase removes the edges related to the node
		// we need to remove them from the additional edge sets
		this.invalidatedEdges.remove(edge);
		this.uninitializedEdges.remove(edge);
	}

	/**
	 * Calculates the reflexive position for a reflexive edge.
	 * @param e
	 */
	protected Set<EdgeBase> setReflexivePositions(PlaceableNode node) {
		// Check the next free reflexive position
		// This allows to remove edges and place another edge at this position.
		Set<EdgeBase> connectedEdges = this.edgesBetween(node, node);
		Direction[] reflexivePositions = Direction.getOrdinalDirections();
		int counter = 0;
		
		for (EdgeBase edge : connectedEdges) {
			//FIXME: Hide and reshow!
			edge.setReflexivePosition(reflexivePositions[counter % reflexivePositions.length]);
			++counter;
		}
		
		return connectedEdges;
	}
	
	/**
	 * Sets the offset for a set of edges.
	 * @param e
	 */
	protected void setEdgeOffset(Set<EdgeBase> edges) {
		if (edges.isEmpty()) return;
		
		// Keep the order of the edges
		List<EdgeBase> orderedEdges = new ArrayList<EdgeBase>(edges);
		
		double singleOffset = 1.0 / (orderedEdges.size() + 1);
		Collections.sort(orderedEdges, new Comparator<EdgeBase>() {
			@Override
			public int compare(EdgeBase o1, EdgeBase o2) {
				return Double.compare(o1.getOffset(), o2.getOffset());
			}
		});
		
		PlaceableNode firstSource = orderedEdges.get(0).source();
		for (int i = 0; i < orderedEdges.size(); ++i) {
			EdgeBase edge = orderedEdges.get(i);
			double offSet = (i + 1) * singleOffset - 0.5;
			// Align for switched source and targets
			offSet *= edge.source().equals(firstSource) ? 1 : -1;
			edge.setOffset( offSet );
		}
	}


	/**
	 * @return
	 */
	public Collection<EdgeBase> getInvalidatedEdges() {
		if (allInvalid)
			return this.getEdges();
		else
			return invalidatedEdges;
	}

	/**
	 * Add a node to the set of nodes to validate
	 * at the next paint event.
	 * @param n
	 */
	public void invalidateNode(PlaceableNode n) {
		if (contains(n))
			invalidatedNodes.add(n);
	}
	
	/**
	 * Add an edge to the set of edges to validate
	 * at the next paint event.
	 * @param e
	 */
	public void invalidateEdge(EdgeBase e) {
		if (this.getEdges().contains(e))
			invalidatedEdges.add(e);
	}
	
	public Collection<PlaceableNode> getInvalidatedNodes() {
		if (allInvalid)
			return getNodes();
		else {
			return invalidatedNodes;
		}
	}
	
	public void clearInvalidated() {
		invalidatedNodes.clear();
		invalidatedEdges.clear();
		allInvalid = false;
	}

	/**
	 * Invalidates all elements of the diagram, i. e., forces
	 * them to update its position.
	 */
	public void invalidate() {
		allInvalid = true;
	}
	
	@Override
	public void clear() {
		super.clear();
		clearInvalidated();
		clearUninitialized();
	}
	
	/**
	 * Returns an iterator over all visible edges.
	 * @return
	 */
	public Iterator<EdgeBase> getVisibleEdgesIterator() {
		return Iterators.filter(this.edgeIterator(), new Predicate<EdgeBase>()  {
			@Override
			public boolean apply(EdgeBase edge) {
				return edge.isVisible();
			}});
	}
	
	/**
	 * Returns an iterator over all visible nodes.
	 * @return
	 */
	public Iterator<PlaceableNode> getVisibleNodesIterator() {
		return Iterators.filter(this.iterator(), new Predicate<PlaceableNode>()  {
			@Override
			public boolean apply(PlaceableNode n) {
				return n.isVisible();
			}});
	}
	
	/**
	 * Returns an iterator over all hidden nodes.
	 * @return
	 */
	public Iterator<PlaceableNode> getHiddenNodesIterator() {
		return Iterators.filter(this.iterator(), new Predicate<PlaceableNode>()  {
			@Override
			public boolean apply(PlaceableNode n) {
				return n.isHidden();
			}});
	}
}
