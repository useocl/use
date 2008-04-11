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
 * @author  Mark Richters
 * @see         DirectedGraph
 */
public class DirectedGraphBase extends AbstractCollection implements DirectedGraph {
    private Map fNodes;     // (Object -> NodeInfo)
    private List fEdges;    // (DirectedEdge)

    /**
     * Constructs an empty graph.
     */
    public DirectedGraphBase() {
        fNodes = new HashMap();
        fEdges = new ArrayList();
    }

    /**
     * Constructs an empty graph with the specified initial capacity
     * for nodes.
     */
    public DirectedGraphBase(int initialNodeCapacity) {
        fNodes = new HashMap(initialNodeCapacity);
        fEdges = new ArrayList();
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
    public Iterator iterator() {
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
    public boolean add(Object o) {
        if (o == null )
            throw new NullPointerException();
        if (fNodes.containsKey(o) )
            return false;
        else {
            fNodes.put(o, new NodeInfo());
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
        // remove incident edges
        fEdges.removeAll(ni.fIncomingEdges);
        fEdges.removeAll(ni.fOutgoingEdges);
        fNodes.remove(o);
        return true;
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
        Iterator it = fEdges.iterator();
        while (it.hasNext() ) {
            DirectedEdge e = (DirectedEdge) it.next();
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
    public Iterator edgeIterator() {
        return fEdges.iterator();
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
    public boolean addEdge(DirectedEdge e) {
        if (e == null )
            throw new NullPointerException();
        if (fEdges.contains(e) )
            return false;

        NodeInfo source = (NodeInfo) fNodes.get(e.source());
        if (source == null )
            throw new NodeDoesNotExistException(e.source().toString());

        NodeInfo target = (NodeInfo) fNodes.get(e.target());
        if (target == null )
            throw new NodeDoesNotExistException(e.target().toString());

        source.addOutgoingEdge(e);
        target.addIncomingEdge(e);

        fEdges.add(e);
        return true;
    }

    /** 
     * Removes the specified edge from this graph.
     *
     * @param e edge to be removed from this graph.
     * @return <tt>true</tt> if the specified edge could be removed.
     * @throws NullPointerException e is null.
     */
    public boolean removeEdge(DirectedEdge e) {
        if (e == null )
            throw new NullPointerException();
        if (! fEdges.contains(e) )
            return false;

        NodeInfo source = (NodeInfo) fNodes.get(e.source());
        NodeInfo target = (NodeInfo) fNodes.get(e.target());

        source.removeOutgoingEdge(e);
        target.removeIncomingEdge(e);

        fEdges.remove(e);
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
    public Set targetNodeSet(Object n) {
        // check for existing node
        NodeInfo ni = getNodeInfo(n);
        Set result = new HashSet();
        Iterator it = ni.outgoingEdgeIterator();
        while (it.hasNext() ) {
            DirectedEdge e = (DirectedEdge) it.next();
            result.add(e.target());
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
    public Set targetNodeClosureSet(Object n) {
        // check for existing node
        getNodeInfo(n);
        Set closure = new HashSet();
        targetNodeClosureSet0(closure, n);
        return closure;
    }

    private void targetNodeClosureSet0(Set closure, Object n) {
        NodeInfo ni = (NodeInfo) fNodes.get(n);
        if ( ni != null ) {
            Iterator it = ni.outgoingEdgeIterator();
            while (it.hasNext() ) {
                Object n2 = ((DirectedEdge) it.next()).target();
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
    public Set sourceNodeSet(Object n) {
        // check for existing node
        NodeInfo ni = getNodeInfo(n);
        Set result = new HashSet();
        Iterator it = ni.incomingEdgeIterator();
        while (it.hasNext() ) {
            DirectedEdge e = (DirectedEdge) it.next();
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
    public Set sourceNodeClosureSet(Object n) {
        // check for existing node
        getNodeInfo(n);
        Set closure = new HashSet();
        sourceNodeClosureSet0(closure, n);
        return closure;
    }

    private void sourceNodeClosureSet0(Set closure, Object n) {
        NodeInfo ni = (NodeInfo) fNodes.get(n);
        Iterator it = ni.incomingEdgeIterator();
        while (it.hasNext() ) {
            Object n2 = ((DirectedEdge) it.next()).source();
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
    public Set edgesBetween(Object n1, Object n2) {
        // check for existing nodes
        NodeInfo ni1 = getNodeInfo(n1);
        getNodeInfo(n2);

        Set result = new HashSet();
        Iterator edgeIter = ni1.outgoingEdgeIterator();
        while (edgeIter.hasNext() ) {
            DirectedEdge e = (DirectedEdge) edgeIter.next();
            if (e.target().equals(n2) )
                result.add(e);
        }
        edgeIter = ni1.incomingEdgeIterator();
        while (edgeIter.hasNext() ) {
            DirectedEdge e = (DirectedEdge) edgeIter.next();
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
    public boolean existsPath(Object n1, Object n2) {
        // check for existing nodes
        getNodeInfo(n1);
        getNodeInfo(n2);
        return targetNodeClosureSet(n1).contains(n2);
    }

    /**
     * Returns true if the graph contains at least one cycle.
     *
     * @return true if the graph contains at least one cycle.
     */
    public boolean hasCycle() {
        // FIXME: this is inefficient
        Iterator it = iterator();
        while (it.hasNext() ) {
            Object n = it.next();
            if (targetNodeClosureSet(n).contains(n) )
                return true;
        }
        return false;
    }

    private NodeInfo getNodeInfo(Object n) {
        if (n == null )
            throw new NullPointerException();

        NodeInfo ni = (NodeInfo) fNodes.get(n);
        if (ni == null )
            throw new NodeDoesNotExistException(n.toString());
        return ni;
    }

    /** 
     * For each node we maintain a list of outgoing edges and incoming edges.
     */
    private class NodeInfo {
        List fOutgoingEdges;    // (DirectedEdge)
        List fIncomingEdges;    // (DirectedEdge)

        NodeInfo() {
            fOutgoingEdges = new ArrayList();
            fIncomingEdges = new ArrayList();
        }

        void addOutgoingEdge(DirectedEdge e) {
            fOutgoingEdges.add(e);
        }

        void removeOutgoingEdge(DirectedEdge e) {
            fOutgoingEdges.remove(e);
        }

        void addIncomingEdge(DirectedEdge e) {
            fIncomingEdges.add(e);
        }

        void removeIncomingEdge(DirectedEdge e) {
            fIncomingEdges.remove(e);
        }

        Iterator outgoingEdgeIterator() {
            return fOutgoingEdges.iterator();
        }

        Iterator incomingEdgeIterator() {
            return fIncomingEdges.iterator();
        }
    }

    public String toString() {
        return "V=" + super.toString() + ", E=" + fEdges;
    }
}
