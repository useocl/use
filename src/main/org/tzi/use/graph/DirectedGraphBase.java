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

package org.tzi.use.graph;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 * Basic implementation of directed graphs.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  	Mark Richters
 * @see         DirectedGraph
 * @param N Type of the nodes
 * @param E Type of the edges
 */
public class DirectedGraphBase<N, E extends DirectedEdge<N>> extends AbstractCollection<N> implements DirectedGraph<N, E> {
    private Map<N, NodeInfo> fNodes;
    private Set<E> fEdges;

    // Array is of type N
    private Map<N, Object[]> closureCache;
    
    /**
     * Constructs an empty graph.
     */
    public DirectedGraphBase() {
        fNodes = new HashMap<N, NodeInfo>();
        fEdges = new HashSet<E>();
        closureCache = new HashMap<N, Object[]>();
    }

    /**
     * Constructs an empty graph with the specified initial capacity
     * for nodes.
     */
    public DirectedGraphBase(int initialNodeCapacity) {
        fNodes = new HashMap<N, NodeInfo>(initialNodeCapacity);
        fEdges = new HashSet<E>();
    }


    private synchronized void clearCache() {
    	closureCache.clear();
    }
    
    // Query Operations

    /**
     * Returns the number of nodes in this graph.  If this graph
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * 
     * @return the number of nodes in this graph
     */
    public int size() {
        return fNodes.size();
    }

    /**
     * Returns <tt>true</tt> if this graph contains the specified
     * node.  More formally, returns <tt>true</tt> if and only if this
     * graph contains at least one node <tt>e</tt> such that
     * <tt>(o==null ? e==null : o.equals(e))</tt>.
     *
     * @param o element whose presence in this graph is to be tested.
     * @return <tt>true</tt> if this graph contains the specified
     *         node
     */
    public boolean contains(Object o) {
        if (o == null )
            throw new NullPointerException();
        return fNodes.containsKey(o);
    }

    /**
     * Returns an iterator over the nodes in this collection.  There are no
     * guarantees concerning the order in which the nodes are returned.
     * 
     * @return an <tt>Iterator</tt> over the nodes in this graph
     */
    public Iterator<N> iterator() {
        return fNodes.keySet().iterator();
    }

    /**
     * Adds the specified node to this graph if it is not already
     * present.  If this graph already contains the specified node,
     * the call leaves this Graph unchanged and returns
     * <tt>false</tt>.
     *
     * @param o node to be added to this graph.
     * @return <tt>true</tt> if this graph did not already contain the
     *          specified node.
     * @throws NullPointerException n is null.  
     */
    public boolean add(N o) {
        if (o == null )
            throw new NullPointerException();

        if (fNodes.containsKey(o) )
            return false;
        else {
            fNodes.put(o, new NodeInfo());
            clearCache();
            return true;
        }
    }

    /**
     * Removes the specified node and all incident edges from this
     * graph if it is present.  If this graph does not contain the
     * specified node, the call leaves this Graph unchanged and
     * returns <tt>false</tt>.
     *
     * @param o node to be removed from this graph.
     * @return <tt>true</tt> if this graph did contain the
     *          specified node and removed it.  
     */
    public boolean remove(Object o) {
        if (o == null )
            return false;
        if (! fNodes.containsKey(o) )
            return false;
        
        NodeInfo ni = getNodeInfo(o);

        // remove incoming edges from graph and node info 
        for (E edge : ni.fIncomingEdges) {
        	// we ignore reflexive edges because 
        	// the node info is removed from the graph        	
        	if (!edge.isReflexive()) {
        		getNodeInfo(edge.source()).removeOutgoingEdge(edge);
        	}
        	fEdges.remove(edge);
        }
        
        // remove outgoing edges from graph and node info
        for (E edge : ni.fOutgoingEdges) {
        	// we ignore reflexive edges because 
        	// the node info is removed from the graph        	
        	if (!edge.isReflexive()) {
        		getNodeInfo(edge.target()).removeIncomingEdge(edge);
        	}
        	fEdges.remove(edge);
        }
                
        fNodes.remove(o);
        clearCache();
        
        return true;
    }

    @Override
    public List<N> getNodes()
    {
    	return new ArrayList<N>(fNodes.keySet());
    }
    
    // Graph specific Operations

    /**
     * Returns the number of all edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int numEdges() {
        return fEdges.size();
    }

    /**
     * Returns the number of cyclic edges in this graph.
     *
     * @return the number of cyclic edges in this graph
     */
    public int numCycles() {
        int result = 0;
        Iterator<E> it = fEdges.iterator();
        while (it.hasNext() ) {
            E e = it.next();
            if (e.isReflexive() )
                result++;
        }
        return result;
    }

    /**
     * Returns the number of all incoming edges for the specified node.
     *
     * @return the number of incoming edges for node n
     * @throws NullPointerException n is null.
     * @throws NodeDoesNotExistException n is not part of this graph.
     */
    public int numIncomingEdges(Object n) {
        // check for existing node
        NodeInfo ni = getNodeInfo(n);
        return ni.fIncomingEdges.size();
    }   

    /**
     * Returns the number of all outgoing edges of the specified node.
     *
     * @return the number of outgoing edges for node n
     * @throws NullPointerException n is null.
     * @throws NodeDoesNotExistException n is not part of this graph.
     */
    public int numOutgoingEdges(Object n) {
        // check for existing node
        NodeInfo ni = getNodeInfo(n);
        return ni.fOutgoingEdges.size();
    }

    /**
     * Returns an iterator over the edges in this collection. There
     * are no guarantees concerning the order in which the edges are
     * returned. The iterator delivers instances of type
     * <code>DirectedEdge</code>.
     * 
     * @return an <tt>Iterator</tt> over the edges in this graph 
     * @see DirectedEdge
     */
    public Iterator<E> edgeIterator() {
        return fEdges.iterator();
    }

    @Override
    public Set<E> allEdges(N n) {
    	NodeInfo ni = getNodeInfo(n);
    	Set<E> result = new HashSet<E>(ni.fIncomingEdges);
    	result.addAll(ni.fOutgoingEdges);
    	
    	return result;
    }
    
    /** 
     * Adds the specified edge to this graph. Multiple edges between
     * the same nodes may exist but they must be distinct, i.e. their 
     * equals method is pair-wise false.
     *
     * @param e edge to be added to this graph.
     * @return <tt>true</tt> if this graph did not already contain the
     *          specified edge.
     * @exception NodeDoesNotExistException node referenced by e is
     *            not part of this graph.
     * @exception NullPointerException e is null.
     */
    public boolean addEdge(E e) {
        if (e == null )
            throw new NullPointerException();
        
        if (fEdges.contains(e) )
            return false;

        NodeInfo source = fNodes.get(e.source());
        if (source == null )
            throw new NodeDoesNotExistException(e.source().toString());

        NodeInfo target = fNodes.get(e.target());
        if (target == null )
            throw new NodeDoesNotExistException(e.target().toString());

        source.addOutgoingEdge(e);
        target.addIncomingEdge(e);

        fEdges.add(e);
        
        clearCache();
        return true;
    }

    /** 
     * Removes the specified edge from this graph.
     *
     * @param e edge to be removed from this graph.
     * @return <tt>true</tt> if the specified edge could be removed.
     * @throws NullPointerException e is null.
     */
    public boolean removeEdge(E e) {
        if (e == null )
            throw new NullPointerException();
        
        if (! fEdges.contains(e) )
            return false;

        NodeInfo source = fNodes.get(e.source());
        NodeInfo target = fNodes.get(e.target());

        source.removeOutgoingEdge(e);
        target.removeIncomingEdge(e);

        fEdges.remove(e);
        clearCache();
        return true;
    }


    // Views

    /**
     * Returns a set view of nodes which are directly reachable by outgoing
     * edges from <code>n</code>.
     *
     * @return a set view of reachable target nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    public Set<N> targetNodeSet(N n) {
        // check for existing node
        
    	NodeInfo ni = getNodeInfo(n);
        Set<N> result = new HashSet<N>();
        Iterator<E> it = ni.outgoingEdgeIterator();
        
        while (it.hasNext() ) {
            E e = it.next();
            result.add( e.target());
        }
        
        return result;
    }

    /**
     * Returns a set view of nodes which are reachable by one ore more 
     * outgoing edges from <code>n</code>.
     *
     * @return a set view of all reachable target nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    public synchronized Set<N> targetNodeClosureSet(N n) {
        // check for existing node
        getNodeInfo(n);
        
        if (!closureCache.containsKey(n)) {
        	Set<N> closure = new HashSet<N>();
        	targetNodeClosureSet0(closure, n);
        	Object[] closureArray = new Object[closure.size()];
        	closureArray = closure.toArray(closureArray);
        	closureCache.put(n, closureArray);
        	return closure;
        }
        
        @SuppressWarnings("unchecked")
		N[] closureArray = (N[])closureCache.get(n);
        return new HashSet<N>(Arrays.asList(closureArray));
    }

    private void targetNodeClosureSet0(Set<N> closure, N n) {
        NodeInfo ni = (NodeInfo) fNodes.get(n);
        if ( ni != null ) {
            Iterator<E> it = ni.outgoingEdgeIterator();
            while (it.hasNext() ) {
                N n2 = it.next().target();
                if (! closure.contains(n2) ) {
                    closure.add(n2);
                    targetNodeClosureSet0(closure, n2);
                }
            }
        }
    }

    /**
     * Returns a set view of nodes which have directed edges targeting
     * <code>n</code>.
     *
     * @return a set view of connected source nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.  */
    public Set<N> sourceNodeSet(N n) {
        // check for existing node
        NodeInfo ni = getNodeInfo(n);
        Set<N> result = new HashSet<N>();
        Iterator<E> it = ni.incomingEdgeIterator();
        while (it.hasNext() ) {
            E e = it.next();
            result.add(e.source());
        }
        return result;
    }

    /**
     * Returns a set view of all nodes which have a path to
     * <code>n</code>.
     *
     * @return a set view of all nodes leading to n.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    public Set<N> sourceNodeClosureSet(N n) {
        // check for existing node
        getNodeInfo(n);
        Set<N> closure = new HashSet<N>();
        sourceNodeClosureSet0(closure, n);
        return closure;
    }

    private void sourceNodeClosureSet0(Set<N> closure, N n) {
        NodeInfo ni = (NodeInfo) fNodes.get(n);
        Iterator<E> it = ni.incomingEdgeIterator();
        
        while (it.hasNext() ) {
            N n2 = it.next().source();
            
            if (! closure.contains(n2) ) {
                closure.add(n2);
                sourceNodeClosureSet0(closure, n2);
            }
        }
    }

    /**
     * Returns the set of edges between two nodes.
     *
     * @return Set(Object)
     * @throws NodeDoesNotExistException node n1 or n2 is not in this graph.
     * @throws NullPointerException n1 or n2 is null.
     */
    public Set<E> edgesBetween(N n1, N n2) {
        // check for existing nodes
        NodeInfo ni1 = getNodeInfo(n1);
        getNodeInfo(n2);

        Set<E> result = new HashSet<E>();
        Iterator<E> edgeIter = ni1.outgoingEdgeIterator();
        
        while (edgeIter.hasNext() ) {
            E e = edgeIter.next();
            if (e.target().equals(n2) )
                result.add(e);
        }
        edgeIter = ni1.incomingEdgeIterator();
        while (edgeIter.hasNext() ) {
            E e = (E) edgeIter.next();
            if (e.source().equals(n2) )
                result.add(e);
        }
        return result;
    }

    /**
     * Returns true if there is a set of directed edges from n1 to n2.
     *
     * @return true if there is a set of directed edges from n1 to n2.
     * @throws NodeDoesNotExistException node n1 or n2 is not in this graph.
     * @throws NullPointerException n1 or n2 is null.
     */
    public boolean existsPath(N n1, N n2) {
        // check for existing nodes
        getNodeInfo(n1);
        getNodeInfo(n2);
        
        // Depth-first search from "Data Structures and Algorithms" (Aho, Hopcroft and Ullman, 1987)
    	Set<N> visitedNodes = new HashSet<N>();
    	    	
    	for (N node : targetNodeSet(n1)) {
    		if (node.equals(n2)) return true;
    		
    		if (!visitedNodes.contains(node)) {
    			if (dfs_path(node, n2, visitedNodes)) return true;
    		}
    	}
    	
        return false;
    }

    /**
     * Depth-First search in the graph
     * @param node The current node
     * @param targetNode The node to reach
     * @param visitedNodes All visited nodes of the graph
     * @param visitedNodesTree All visited nodes in this search path
     * @return
     */
    private boolean dfs_path(N node, N targetNode, Set<N> visitedNodes) {
    	visitedNodes.add(node);
    	
    	for (N n : targetNodeSet(node)) {
    		if (n.equals(targetNode)) return true;
    		
    		if (!visitedNodes.contains(n)) {
    			if (dfs_path(n, targetNode, visitedNodes)) return true;
    		}
    	}
    	
        return false;
    }
    
    /**
     * Returns true if the graph contains at least one cycle.
     *
     * @return true if the graph contains at least one cycle.
     */
    public boolean hasCycle() {    	
    	// Depth-first search from "Data Structures and Algorithms" (Aho, Hopcroft and Ullman, 1987)
    	Set<N> visitedNodes = new HashSet<N>();
    	    	
    	for (N node : fNodes.keySet()) {
    		if (!visitedNodes.contains(node)) {
    			if (dfs_cycle(node, visitedNodes, new HashSet<N>())) return true;
    		}
    	}
    	
        return false;
    }

    /**
     * Depth-First search in the graph
     * @param node The current node
     * @param visitedNodes All visited nodes of the graph
     * @param visitedNodesTree All visited nodes in this search path
     * @return
     */
    private boolean dfs_cycle(N node, Set<N> visitedNodes, Set<N> visitedNodesTree) {
    	visitedNodes.add(node);
    	visitedNodesTree.add(node);
    	
    	for (N n : targetNodeSet(node)) {
    		if (!visitedNodes.contains(n)) {
    			if (dfs_cycle(n, visitedNodes, new HashSet<N>(visitedNodesTree))) return true;
    		} else if (visitedNodesTree.contains(n)) {
                return true;
        }
    	}
    	
        return false;
    }
    
    private NodeInfo getNodeInfo(Object n) {
        if (n == null )
            throw new NullPointerException();

        NodeInfo ni = fNodes.get(n);
        
        if (ni == null )
            throw new NodeDoesNotExistException(n.toString());
        
        return ni;
    }

    /** 
     * For each node we maintain a list of outgoing edges and incoming edges.
     */
    private class NodeInfo {
        List<E> fOutgoingEdges;
        List<E> fIncomingEdges;

        NodeInfo() {
            fOutgoingEdges = new ArrayList<E>();
            fIncomingEdges = new ArrayList<E>();
        }

        void addOutgoingEdge(E e) {
            fOutgoingEdges.add(e);
        }

        void removeOutgoingEdge(E e) {
            fOutgoingEdges.remove(e);
        }

        void addIncomingEdge(E e) {
            fIncomingEdges.add(e);
        }

        void removeIncomingEdge(E e) {
            fIncomingEdges.remove(e);
        }

        Iterator<E> outgoingEdgeIterator() {
            return fOutgoingEdges.iterator();
        }

        Iterator<E> incomingEdgeIterator() {
            return fIncomingEdges.iterator();
        }
    }

    public String toString() {
        return "V=" + super.toString() + ", E=" + fEdges;
    }
}
