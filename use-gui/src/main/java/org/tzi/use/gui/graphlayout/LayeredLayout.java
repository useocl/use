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

package org.tzi.use.gui.graphlayout;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.graph.DirectedEdge;
import org.tzi.use.graph.DirectedEdgeBase;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.graph.DirectedGraphBase;


/**
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */


public class LayeredLayout<N, E extends DirectedEdge<N>> {
    private static final boolean DO_TIMING = true;

    private DirectedGraph<N, E> fInGraph;
    private DirectedGraph<LayoutNode, DirectedEdge<LayoutNode>> fOutGraph;
    private HashMap<N, LayoutNode> fObjectToNode; // (Object -> LabeledNode)
    private List<LayoutNode> fSinks;    // (LabeledNode)
    private int fHeight;    // the number of layers

    public LayeredLayout(DirectedGraph<N, E> g) {
        fInGraph = g;
        fOutGraph = new DirectedGraphBase<LayoutNode, DirectedEdge<LayoutNode>>(fInGraph.size());
        fObjectToNode = new HashMap<N, LayoutNode>();
        fSinks = new ArrayList<LayoutNode>();
    }

    /**
     * Calculates a layout.
     */
    public Layout layout() {
        long t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0;
        if (DO_TIMING ) 
            t1 = System.currentTimeMillis();
        createInitialGraph();
        if (DO_TIMING ) 
            t2 = System.currentTimeMillis();
        layer();
        if (DO_TIMING ) 
            t3 = System.currentTimeMillis();
        insertDummyNodes();
        if (DO_TIMING ) 
            t4 = System.currentTimeMillis();
        placeNodesOnLayers();
        if (DO_TIMING ) 
            t5 = System.currentTimeMillis();

        if (DO_TIMING ) {
            System.out.println("Time createInitialGraph: " + (t2 - t1));
            System.out.println("Time layer             : " + (t3 - t2));
            System.out.println("Time insertDummyNodes  : " + (t4 - t3));
            System.out.println("Time placeNodesOnLayers: " + (t5 - t4));
            System.out.println("Time total             : " + (t5 - t1));
        }

        return new Layout(fOutGraph);
    }

    /**
     * Initializes the result graph with a copy of the original
     * graph. Nodes are LabeledNode objects.
     */
    private void createInitialGraph() {
        // copy nodes
        Iterator<N> nodeIter = fInGraph.iterator();
        while (nodeIter.hasNext() ) {
            N node = nodeIter.next();
            LayoutNode layoutNode = new LayoutNode(node);
            fObjectToNode.put(node, layoutNode);
            fOutGraph.add(layoutNode);
            if (fInGraph.numOutgoingEdges(node) == 0 )
                fSinks.add(layoutNode);
        }
    
        // copy edges
        Iterator<E> edgeIter = fInGraph.edgeIterator();
        while (edgeIter.hasNext() ) {
            E edge = edgeIter.next();
            LayoutNode source = fObjectToNode.get(edge.source());
            LayoutNode target = fObjectToNode.get(edge.target());
            fOutGraph.addEdge(new DirectedEdgeBase<LayoutNode>(source, target));
        }
    }

    /**
     */
    private void layer() {
        fHeight = 1;
        Iterator<LayoutNode> sinkIter = fSinks.iterator();
        while (sinkIter.hasNext() ) {
            LayoutNode node = sinkIter.next();
            layerWalk(node, 1);
        }
    }

    private void layerWalk(LayoutNode node, int layer) {
        Set<LayoutNode> predecessors = fOutGraph.sourceNodeSet(node);
        
        Iterator<LayoutNode> predIter = predecessors.iterator();
        while (predIter.hasNext() ) {
            node = predIter.next();
            if (layer > node.fLayer )
                node.fLayer = layer;
            layerWalk(node, layer + 1);
        }
        if (layer > fHeight )
            fHeight = layer;
    }

    /**
     * Inserts dummy nodes and edges to achieve a properly layered
     * graph.  
     */
    private void insertDummyNodes() {
        List<LayoutNode> dummyNodes = new ArrayList<LayoutNode>();
        List<DirectedEdge<LayoutNode>> dummyEdges = new ArrayList<DirectedEdge<LayoutNode>>();
        int dummyNode = 0;

        Iterator<DirectedEdge<LayoutNode>> edgeIter = fOutGraph.edgeIterator();
        while (edgeIter.hasNext() ) {
            DirectedEdge<LayoutNode> edge = edgeIter.next();
            LayoutNode source = edge.source();
            LayoutNode target = edge.target();
            int span = source.fLayer - target.fLayer;

            if (span > 1 ) {
                //System.out.println("Span = " + span + ", edge = " + edge);
                LayoutNode n1 = source;
                int layer = source.fLayer - 1;
                while (layer > target.fLayer ) {
                    LayoutNode n2 = new LayoutNode(null, dummyNode++);
                    n2.fLayer = layer;
                    dummyNodes.add(n2);
                    dummyEdges.add(new DirectedEdgeBase<LayoutNode>(n1, n2));
                    n1 = n2;
                    layer--;
                }
                dummyEdges.add(new DirectedEdgeBase<LayoutNode>(n1, target));

                // remove direct edge
                edgeIter.remove();
            }
        }

        Iterator<LayoutNode> nodeIter = dummyNodes.iterator();
        while (nodeIter.hasNext() )
            fOutGraph.add(nodeIter.next());

        edgeIter = dummyEdges.iterator();
        while (edgeIter.hasNext() )
            fOutGraph.addEdge(edgeIter.next());
    }

    /**
     * Determines the x-coordinates of nodes for each layer.
     */
    private void placeNodesOnLayers() {
        System.out.println("placeNodesOnLayers: " + fOutGraph);
        //System.out.println("fHeight = " + fHeight);

        // determine size of each layer and set a relative (arbitrary)
        // order for each node on its layer
        int[] layerSize = new int[fHeight];
        Iterator<LayoutNode> nodeIter = fOutGraph.iterator();
        while (nodeIter.hasNext() ) {
            LayoutNode node = nodeIter.next();
            node.fX = node.fLayerX = layerSize[node.fLayer]++;
        }

        // create layer array
        int width = 0;
        LayoutNode[][] layers = new LayoutNode[fHeight][];
        for (int i = 0; i < fHeight; i++) {
            int layerWidth = layerSize[i];
            if (layerWidth > width) 
                width = layerWidth;
            layers[i] = new LayoutNode[layerWidth];
        }
    
        // place nodes into layer array
        nodeIter = fOutGraph.iterator();
        while (nodeIter.hasNext() ) {
            LayoutNode node = nodeIter.next();
            layers[node.fLayer][node.fLayerX] = node;
        }

        // place sinks on bottom layer
        int numSinks = layers[0].length;
        if (numSinks == 1 ) {
            layers[0][0].fX = width / 2;
        } else {
            numSinks--;
            for (int j = 0; j <= numSinks; j++)
                layers[0][j].fX = j * (width - 1) / numSinks;
        }
    
        // layout layers bottom-up
        for (int i = 1; i < fHeight; i++) {
            BitSet occupiedPositions = new BitSet(width);
            for (int j = 0; j < layers[i].length; j++) {
                LayoutNode u = layers[i][j];
                // calculate barycenter of neighbors
                int sum = 0;
                Set<LayoutNode> neighbors = fOutGraph.targetNodeSet(u);
                nodeIter = neighbors.iterator();
                while (nodeIter.hasNext() ) {
                    LayoutNode v = nodeIter.next();
                    sum += v.fX;
                }
                int x = sum / neighbors.size();
                int xd = 0;
                int xn;
                while (true ) {
                    xn = x + xd;
                    if (xn < width && ! occupiedPositions.get(xn) )
                        break;
                    xn = x - xd;
                    if (xn >= 0 && ! occupiedPositions.get(xn) )
                        break;
                    xd++;
                }
                occupiedPositions.set(xn);
                u.fX = xn;
            }
        }
    }
}
