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

import java.awt.*;
import java.util.*;
import javax.swing.*;

import org.tzi.use.graph.*;


/**
 * Test class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class LayeredLayout_test {

    public static <N, E extends DirectedEdge<N>> Object[] removeCycles (final DirectedGraph<N, E> g) {
        LinkedList<N> sl = new LinkedList<N>();
        LinkedList<N> sr = new LinkedList<N>();
        ArrayList<N> rest = new ArrayList<N>();
    
        Iterator<N> nodes = g.iterator();
        while (nodes.hasNext() ) {
            N node = nodes.next();
            int ind = g.numIncomingEdges(node);
            int outd = g.numOutgoingEdges(node);
            if (outd == 0 ) {
                // prepend sinks to sr
                sr.addFirst(node);
            } else if (ind == 0 ) {
                // append source to sl
                sl.addLast(node);
            } else {
                rest.add(node);
            }
        }
        System.out.println("sl = " + sl);
        System.out.println("sr = " + sr);
    
        System.out.println("rest before sort = " + rest);
        Collections.sort(rest, new Comparator<N>() {
                public int compare(N node1, N node2) {
                    int d1 = g.numOutgoingEdges(node1) - g.numIncomingEdges(node1);
                    int d2 = g.numOutgoingEdges(node2) - g.numIncomingEdges(node2);
                    return d2 - d1;
                }
            });
        System.out.println("rest after sort = " + rest);

        nodes = rest.iterator();
        while (nodes.hasNext() ) {
            N node = nodes.next();
            sl.addLast(node);
        }

        System.out.println("sl = " + sl);
        System.out.println("sr = " + sr);
        sl.addAll(sr);
        return sl.toArray();
    }

    // p. 248
    public static DirectedGraph<Integer, DirectedEdgeBase<Integer>> graph1() {
        final int N = 13;
        int[][] edges = { 
            {1,2}, {1,4}, {1,5}, 
            {2,3}, {2,4}, {2,12}, 
            {3,4}, {3,9}, {3,11},
            {4,8},
            {5,6}, {5,7}, {5,9},
            {6,7}, {6,10}, {6,13},
            {7,8}, {7,9},
            {8,11}, {8,12},
            {9,12},
            {10,11}, {10,13},
            {11,13},
            {12,13},
        };
        return newGraph(N, edges);
    }

    // p. 295
    public static DirectedGraph<Integer, DirectedEdgeBase<Integer>> graph2() {
        final int N = 9;
        int[][] edges = { 
            {1,2}, {2,3}, {2,5}, {3,6}, {4,1}, {4,5}, {5,8},
            {6,5}, {6,9}, {7,4}, {8,7}, {9,8}
        };
        return newGraph(N, edges);
    }

    public static DirectedGraph<Integer, DirectedEdgeBase<Integer>> graph3() {
        final int N = 5;
        int[][] edges = { 
            {1,2}, {1,3}, {1,4}, {1,5}, {2,3}, {2,4}, {3,4}
        };
        return newGraph(N, edges);
    }

    public static DirectedGraph<Integer, DirectedEdgeBase<Integer>> randomGraph(int N) {
        int[][] edges = new int[1 * N][2];

        Random r = new Random(1);
        for (int i = 0; i < edges.length; i++) {
            int source = r.nextInt(N - 1);
            int target = source + 1 + r.nextInt(N - source - 1);
            edges[i][0] = source + 1;
            edges[i][1] = target + 1;
        }   
        return newGraph(N, edges);
    }

    public static DirectedGraph<Integer, DirectedEdgeBase<Integer>> newGraph(int N, int[][] edges) {
    	DirectedGraph<Integer, DirectedEdgeBase<Integer>> g = new DirectedGraphBase<Integer, DirectedEdgeBase<Integer>>(N);
        Integer[] nodes = new Integer[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = i + 1;
            g.add(nodes[i]);
        }
        
        for (int i = 0; i < edges.length; i++) {
            // System.out.println(edges[i].length);
            g.addEdge(new DirectedEdgeBase<Integer>(nodes[edges[i][0] - 1], 
                                           nodes[edges[i][1] - 1]));
        }
        return g;
    }


    public static void main(String[] args) {
    	DirectedGraph<Integer, DirectedEdgeBase<Integer>> g;
        //      System.out.println(g);
        //      System.out.println();
        //      removeCycles(g);

        //      System.out.println();
        //g = randomGraph(20);
        g = graph1();
        System.out.println(g);
        System.out.println();
        LayeredLayout<Integer, DirectedEdgeBase<Integer>> l = new LayeredLayout<Integer, DirectedEdgeBase<Integer>>(g);
        Layout layout = l.layout();
        //Object[] orderedNodes = removeCycles(g);
        //layer(g, orderedNodes);

        GraphPanel gp = new GraphPanel(layout);
        JFrame f = new JFrame("GraphPanel");

        // Layout the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(gp), BorderLayout.CENTER);
        f.setContentPane(contentPane);
        f.pack();
        f.setVisible(true);
    }
}
