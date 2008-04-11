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

package org.tzi.use.gui.views.diagrams;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.sys.MSystem;

/**
 * This class provieds all Maps and Set which contain information about 
 * the layout in a classdiagram and objectdiagram.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Fabian Gutsche 
 */
public class LayoutInfos {

    private Map fBinaryEdgeToEdgeMap; 
    private Map fNodeToNodeMap; 
    private Map fNaryEdgeToDiamondNodeMap;
    private Map fNaryEdgeToHalfEdgeMap;
    private Map fEdgeNodeToEdgeMap;
    private Map fEnumToNodeMap;
    private Map fGenToGeneralizationEdge;
    private Set fHiddenNodes;
    private Set fHiddenEdges;
    
    private DiagramOptions fOpt;
    private DiagramView fDiagram;
    private MSystem fSystem;
    private PrintWriter fLog;
    
    private String fHiddenElementsXML;
    
    
    public LayoutInfos( Map binaryEdgeToEdgeMap, 
                        Map nodeToNodeMap, 
                        Map naryEdgeToDiamondNodeMap,
                        Map naryEdgeToHalfEdgeMap,
                        Map edgeNodeToEdgeMap,
                        Map enumToNodeMap,
                        Map genToGeneralizationEdge,
                        Set hiddenNodes, Set hiddenEdges,
                        DiagramOptions opt, MSystem system,
                        DiagramView diagram, PrintWriter log ) {     
        fBinaryEdgeToEdgeMap = binaryEdgeToEdgeMap;
        fNodeToNodeMap = nodeToNodeMap;
        fNaryEdgeToDiamondNodeMap = naryEdgeToDiamondNodeMap;
        fNaryEdgeToHalfEdgeMap = naryEdgeToHalfEdgeMap;
        fEdgeNodeToEdgeMap = edgeNodeToEdgeMap;
        fEnumToNodeMap = enumToNodeMap;
        fGenToGeneralizationEdge = genToGeneralizationEdge;
        fHiddenNodes = hiddenNodes;
        fHiddenEdges = hiddenEdges;
        fOpt = opt;
        fDiagram = diagram;
        fSystem = system;
        fHiddenElementsXML = "";
        fLog = log;
    }

    public Map getBinaryEdgeToEdgeMap() {
        return fBinaryEdgeToEdgeMap;
    }
    public Map getEdgeNodeToEdgeMap() {
        return fEdgeNodeToEdgeMap;
    }
    public Map getEnumToNodeMap() {
        return fEnumToNodeMap;
    }
    public Map getGenToGeneralizationEdge() {
        return fGenToGeneralizationEdge;
    }
    public Set getHiddenEdges() {
        return fHiddenEdges;
    }
    public void setHiddenEdges( Set hiddenEdges ) {
        fHiddenEdges = hiddenEdges;
    }
    public Set getHiddenNodes() {
        return fHiddenNodes;
    }
    public void setHiddenNodes( Set hiddenNodes ) {
        fHiddenNodes = hiddenNodes;
    }
    public Map getNaryEdgeToDiamondNodeMap() {
        return fNaryEdgeToDiamondNodeMap;
    }
    public Map getNaryEdgeToHalfEdgeMap() {
        return fNaryEdgeToHalfEdgeMap;
    }
    public Map getNodeToNodeMap() {
        return fNodeToNodeMap;
    }
    public DiagramOptions getOpt() {
        return fOpt;
    }
    public MSystem getSystem() {
        return fSystem;
    }

    public String getHiddenElementsXML() {
        return fHiddenElementsXML;
    }
    public void setHiddenElementsXML( String xml ) {
        fHiddenElementsXML = xml;
    }
    
    public DiagramView getDiagram() {
        return fDiagram;
    }
    
    public PrintWriter getLog() {
        return fLog;
    }
    
    
    public void resetNodesOnEdges() {
        Iterator it = null;
        if ( fBinaryEdgeToEdgeMap != null ) {
            it = fBinaryEdgeToEdgeMap.values().iterator(); 
            while ( it.hasNext() ) {
                ((EdgeBase) it.next()).resetNodesOnEdges();
            }
        }
        if ( fNaryEdgeToHalfEdgeMap != null ) {
            it = fNaryEdgeToHalfEdgeMap.values().iterator(); 
            while ( it.hasNext() ) {
                ((EdgeBase) it.next()).resetNodesOnEdges();
            }
        }
        if ( fEdgeNodeToEdgeMap != null ) {
            it = fEdgeNodeToEdgeMap.values().iterator(); 
            while ( it.hasNext() ) {
                ((EdgeBase) it.next()).resetNodesOnEdges();
            }
        }
        if ( fGenToGeneralizationEdge != null ) {
            it = fGenToGeneralizationEdge.values().iterator(); 
            while ( it.hasNext() ) {
                ((EdgeBase) it.next()).resetNodesOnEdges();
            }
        }
    }
}
