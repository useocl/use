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

$Id$

package org.tzi.use.main.shell;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.config.Options;
import org.tzi.use.graph.*;
import org.tzi.use.uml.mm.*;

/** 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
class ModelToGraph {

    // utility class
    private ModelToGraph() {}
    

    static void write(PrintWriter out, MModel model) {
        String nl = Options.LINE_SEPARATOR;
        out.println("graph: { title: \"USE class diagram\"" + nl +
                    "port_sharing: no" + nl + 
                    "orientation: bottom_to_top" + nl + 
                    "near_edges: yes" + nl + 
                    "display_edge_labels: yes" + nl + 
                    "manhattan_edges: yes" + nl + 
                    "straight_phase: yes" + nl + 
                    "priority_phase: yes" + nl +
                    "layoutalgorithm: mindepth" + nl);
        //              "layout_downfactor: 39" + nl + 
        //              "layout_upfactor: 39" + nl + 
        //              "layout_nearfactor: 0" + nl + 
        //              "splines: yes" + nl + 

        // add class vertices to graph
        Iterator clsIter = model.classes().iterator();
        while (clsIter.hasNext() ) {
            MClass cls = (MClass) clsIter.next();
            String s = cls.name();
            Iterator iter = cls.attributes().iterator();
            while (iter.hasNext() ) {
                MAttribute attr = (MAttribute) iter.next();
                s += "\\n" + attr;
            }
            iter = cls.operations().iterator();
            while (iter.hasNext() ) {
                MOperation op = (MOperation) iter.next();
                s += "\\n" + op.signature();
            }
            out.println("  node: { title: \"" + cls.name() + 
                        "\" label: \"" + s + "\"}");
        }

        // add association edges to graph
        Iterator assocIter = model.associations().iterator();
        while (assocIter.hasNext() ) {
            MAssociation assoc = (MAssociation) assocIter.next();
            String aname = assoc.name();
            List aendList = assoc.associationEnds();
            if (aendList.size() == 2 ) {
                MAssociationEnd aend0 = (MAssociationEnd) aendList.get(0);
                MAssociationEnd aend1 = (MAssociationEnd) aendList.get(1);
                out.println("  edge: { sourcename: \"" + aend0.cls().name() + 
                            "\" targetname: \"" + aend1.cls().name() + 
                            "\" label: \"" + aname +
                            "\" arrowstyle : none }");
            } else {
                // create diamond node
                out.println("  node: { title: \"" + aname + 
                            "\" label: \"" + aname + 
                            "\" shape: rhomb }");

                // edges from classes to diamond
                Iterator aendIter = aendList.iterator();
                while (aendIter.hasNext() ) {
                    MAssociationEnd aend = (MAssociationEnd) aendIter.next();
                    out.println("  edge: { sourcename: \"" + aend.cls().name() + 
                                "\" targetname: \"" + aname +
                                "\" arrowstyle : none }");
                }
            }
        }

        // add generalization edges to graph
        DirectedGraph genGraph = model.generalizationGraph();
        Iterator edgeIter = genGraph.edgeIterator();
        while (edgeIter.hasNext() ) {
            MGeneralization gen = (MGeneralization) edgeIter.next();

            out.println("  bentnearedge: { sourcename: \"" + gen.child().name() + 
                        "\" targetname: \"" + gen.parent().name() + 
                        "\" color: red}");
        }

        out.println("}");
        out.flush();
    }
}

