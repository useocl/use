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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** 
 * Basic interface of graphs with directed edges. A graph is a collection
 * of node objects which may be connected by directed edges.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * 
 * @param N Type of the nodes
 * @param E Type of the Edges 
 */


public interface DirectedGraph<N, E extends DirectedEdge<N>> extends Collection<N> {
    /**
     * Returns the number of nodes in this graph.  If this graph
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * 
     * @return the number of nodes in this graph
     */
    int size();

    /**
     * Returns <tt>true</tt> if this graph contains no nodes.
     *
     * @return <tt>true</tt> if this graph contains no nodes
     */
    boolean isEmpty();

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
    boolean contains(Object o);

    /**
     * Returns an iterator over the nodes in this collection.  There are no
     * guarantees concerning the order in which the nodes are returned.
     * 
     * @return an <tt>Iterator</tt> over the nodes in this graph
     */
    Iterator<N> iterator();

    /**
     * Returns an array containing all of the nodes in this graph.
     * Obeys the general contract of the <tt>Collection.toArray</tt> method.
     *
     * @return an array containing all of the nodes in this set.
     */
    Object[] toArray();

    /**
     * Returns a List containing all the nodes of the graph.<br/>
     * The returned list should be independent to the source graph, 
     * to allow changes to the list without modifying the source graph.
     * @return A <code>List</code> of the nodes.
     */
    List<N> getNodes();
    
    // Modification Operations

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
    boolean add(N o);

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
    boolean remove(Object o);


    // Graph specific Operations

    /**
     * Returns the number of all edges in this graph.
     *
     * @return the number of edges in this graph
     */
    int numEdges();

    /**
     * Returns the number of cyclic edges in this graph.
     *
     * @return the number of cyclic edges in this graph
     */
    int numCycles();

    /**
     * Returns the number of all incoming edges for the specified node.
     *
     * @return the number of incoming edges for node n
     * @throws NullPointerException n is null.
     * @throws NodeDoesNotExistException n is not part of this graph.
     */
    int numIncomingEdges(N n);

    /**
     * Returns the number of all outgoing edges of the specified node.
     *
     * @return the number of outgoing edges for node n
     * @throws NullPointerException n is null.
     * @throws NodeDoesNotExistException n is not part of this graph.
     */
    int numOutgoingEdges(N n);

    /**
     * Returns all edges a node is connected with.
     * @param n
     * @return
     */
    Set<E> allEdges(N n);
    
    /**
     * Returns an iterator over the edges in this collection. There
     * are no guarantees concerning the order in which the edges are
     * returned. The iterator delivers instances of type
     * <code>DirectedEdge</code>.
     * 
     * @return an <tt>Iterator</tt> over the edges in this graph 
     * @see DirectedEdge
     */
    Iterator<E> edgeIterator();

    /** 
     * Adds the specified edge to this graph. Multiple edges between
     * the same nodes may exist but they must be distinct, i.e. their 
     * equals method is pair-wise false.
     *
     * @param e edge to be added to this graph.
     * @return <tt>true</tt> if this graph did not already contain the
     *          specified edge.
     * @throws NodeDoesNotExistException node referenced by e is
     *            not part of this graph.
     * @throws NullPointerException e is null.
     */
    boolean addEdge(E e);

    /** 
     * Removes the specified edge from this graph.
     *
     * @param e edge to be removed from this graph.
     * @return <tt>true</tt> if the specified edge could be removed.
     * @throws NullPointerException e is null.
     */
    boolean removeEdge(E e);

    // Views

    /**
     * Returns a set view of nodes which are directly reachable by outgoing
     * edges from <code>n</code>.
     *
     * @return a set view of reachable target nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    Set<N> targetNodeSet(N n);

    /**
     * An iterable over the nodes reachable by outgoing edges.
     * @param node The starting node
     * @param includeNode If <code>true</code>, the starting node is the first element of the iteration.
     * @return
     */
    NodeSetIterator<N> targetNodeClosureSetIterator(N node, boolean includeNode);
    
    /**
     * Returns a set view of nodes which are directly reachable by outgoing
     * edges from <code>n</code>.
     * This operation is used to get a set of subclasses of type <code>T</code> the nodes.
     * <code>s</code> is used as the "erasure object" to check the cast.
     * 
     * @return a set view of reachable target nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    <T extends N> Set<T> targetNodeSet(Class<T> s, T n);
    
    /**
     * Returns a set view of nodes which are reachable by one ore more 
     * outgoing edges from <code>n</code>.
     *
     * @return a set view of all reachable target nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    Set<N> targetNodeClosureSet(N n);

    /**
     * Returns a set view of nodes which are reachable by one ore more 
     * outgoing edges from <code>n</code>.
     *
     * @return a set view of all reachable target nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    <T extends N> Set<T> targetNodeClosureSet(Class<T> s, T n);
    
    /**
     * Returns a set view of nodes which have directed edges targeting
     * <code>n</code>.
     *
     * @return a set view of connected source nodes.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.  */
    Set<N> sourceNodeSet(N n);
    
    <T extends N> Set<T> sourceNodeSet(Class<T> s, T n);
    
    /**
     * Returns a set view of all nodes which have a path to
     * <code>n</code>.
     *
     * @return a set view of all nodes leading to n.
     * @throws NodeDoesNotExistException node n is not part of this graph.
     * @throws NullPointerException n is null.
     */
    Set<N> sourceNodeClosureSet(N n);

    <T extends N> Set<T> sourceNodeClosureSet(Class<T> s, T n);
    
    /**
     * An iterable over the nodes reachable by outgoing edges.
     * @param node The starting node
     * @param includeNode If <code>true</code>, the starting node is the first element of the iteration.
     * @return
     */
    NodeSetIterator<N> sourceNodeClosureSetIterator(N node, boolean includeNode);
    
    /**
     * Returns the set of edges between two nodes.
     *
     * @return Set(Object)
     * @throws NodeDoesNotExistException node n1 or n2 is not in this graph.
     * @throws NullPointerException n1 or n2 is null.
     */
    Set<E> edgesBetween(N n1, N n2);

    /**
     * Returns the set of edges between two nodes.
     * If <code>oneDirection</code> is <code>true</code>, only the edges
     * from <code>source</code> to <code>target</code> are returned.
     * Otherwise, edges in both directions are returned. 
     * @return Set(E)
     * @throws NodeDoesNotExistException node <code>source</code> or <code>target</code> is not in this graph.
     * @throws NullPointerException <code>source</code> or <code>target</code> is null.
     */
    Set<E> edgesBetween(N source, N target, boolean oneDirection);
    
    /**
     * Returns true if there is a set of directed edges from n1 to n2.
     *
     * @return true if there is a set of directed edges from n1 to n2.
     * @throws NodeDoesNotExistException node n1 or n2 is not in this graph.
     * @throws NullPointerException n1 or n2 is null.
     */
    boolean existsPath(N n1, N n2);

    /**
     * Returns true if the graph contains at least one cycle.
     *
     * @return true if the graph contains at least one cycle.
     */
    boolean hasCycle();

	/**
	 * Removes all nodes and edges from the graph
	 */
	void clear();
	
	public interface NodeSetIterator<N> extends Iterator<N> {
		int getDepth();
	}
}

