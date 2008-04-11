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

package org.tzi.use.gui.xmlparser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.EdgeProperty;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.classdiagram.ClsDiagramOptions;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjDiagramOptions;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;

/**
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Fabian Gutsche 
 */
public class Context {

    /**
     * Contains all mappings in a diagram.
     */
    private Map fAllMappings;
    /**
     * The actual map which can contain ClassNodes, ObjectNodes, DiamondNodes,
     * BinaryEdges, HalfEdges, NodeEdges ...
     */
    private Map fActualMap;
    /**
     * The type of elements which is parsed right now. Like class, object, 
     * binaryedge, halfedge, nodeedge, rolename, multiplity ...
     */
    private String fType;
    private Stack fTypes;
    /**
     * The kind of element which is parsed right now. Like association, link,
     * source, target ... 
     */
    private String fKind;
    /**
     * The actual working system.
     */
    private MSystem fSystem;

    /**
     * The diagram options.
     */
    private DiagramOptions fOpt;
    /**
     * Contains all layout information of a diagram. 
     */
    private LayoutInfos fLayoutInfos;
    /**
     * The actual parsed NodeBase.
     */
    private NodeBase fActualNode;
    /**
     * The actual parsed EdgeBase.
     */
    private EdgeBase fActualEdge;
    /**
     * The actual parsed EdgeProperty.
     */
    private EdgeProperty fActualEdgeProperty;
    /**
     * The actual object like (MClass/MObject).
     */
    private Object fActualObj;
    /**
     * The source element of an EdgeBase (MClass/MObject/DiamondNode)
     */
    private Object fSource;
    /**
     * The target element of an EdgeBase (MClass/MObject)
     */
    private Object fTarget;
    /**
     * All nodes which are participating in a t-nary edge.
     */
    private Set fConnectedNodes;
    /**
     * The ID of an nodeOnEdge (EdgeProperty)
     */
    private int fID = -1;
    /**
     * The SpecialID of an nodeOnEdge (EdgeProperty)
     */
    private int fSpecialID = -1;
    
    public Context( LayoutInfos layoutInfos ) {
        fLayoutInfos = layoutInfos;
        fOpt = fLayoutInfos.getOpt();
        
        fAllMappings = new HashMap();
        if ( fOpt instanceof ObjDiagramOptions ) {
            fAllMappings.put( LayoutTags.OBJECT, layoutInfos.getNodeToNodeMap() );
        }
        if ( fOpt instanceof ClsDiagramOptions ) {
            fAllMappings.put( LayoutTags.CLASS, layoutInfos.getNodeToNodeMap() );
        }
        fAllMappings.put( LayoutTags.BINARYEDGE, layoutInfos.getBinaryEdgeToEdgeMap() );
        fAllMappings.put( LayoutTags.DIAMONDNODE, layoutInfos.getNaryEdgeToDiamondNodeMap() );
        fAllMappings.put( LayoutTags.HALFEDGE, layoutInfos.getNaryEdgeToHalfEdgeMap() );
        fAllMappings.put( LayoutTags.EDGENODE, layoutInfos.getEdgeNodeToEdgeMap() );
        fAllMappings.put( LayoutTags.ENUMERATION, layoutInfos.getEnumToNodeMap() );
        fAllMappings.put( LayoutTags.INHERITANCE, layoutInfos.getGenToGeneralizationEdge() );
        
        fSystem = fLayoutInfos.getSystem();
        fConnectedNodes = new HashSet();
        fTypes = new Stack();
    }
    
    /**
     * Resets every field in this Context object. Should be used after
     * every parsed node or edge.
     */
    public void reset() {
        setActualMap( null );
        setType( null );
        setKind( null );
        setActualNode( null );
        setActualEdge( null );
        setActualEdgeProperty( null );
        setActualObj( null );
        setSource( null );
        setTarget( null );
        setID( -1 );
        setSpecialID( -1 );
    }
    
    /**
     * Returns the actual EdgeBase. If no edge is parsed it returns null.
     */
    public EdgeBase getActualEdge() {
        return fActualEdge;
    }
    public void setActualEdge( EdgeBase actualEdge ) {
        fActualEdge = actualEdge;
    }
    /**
     * Returns the actual EdgeProperty. If no edgeProperty is parsed it returns null.
     */
    public EdgeProperty getActualEdgeProperty() {
        return fActualEdgeProperty;
    }
    public void setActualEdgeProperty( EdgeProperty actualEdgeProperty ) {
        fActualEdgeProperty = actualEdgeProperty;
    }
    /**
     * Returns the actual Map with nodes or edges.
     */
    public Map getActualMap() {
        return fActualMap;
    }
    public void setActualMap( Map actualMap ) {
        fActualMap = actualMap;
    }
    /**
     * Returns the actual Nodease. If no node is parsed it returns null.
     */
    public NodeBase getActualNode() {
        return fActualNode;
    }
    public void setActualNode( NodeBase actualNode ) {
        fActualNode = actualNode;
    }
    /**
     * Returns the actual object (MClass/MObject). If no node is parsed it 
     * returns null.
     */
    public Object getActualObj() {
        return fActualObj;
    }
    public void setActualObj( Object actualObj ) {
        fActualObj = actualObj;
    }
    /**
     * Returns the actual source object. If no edge is parsed it returns null.
     */
    public Object getSource() {
        return fSource;
    }
    public void setSource( Object source ) {
        fSource = source;
    }
    /**
     * Returns the actual target object. If no edge is parsed it returns null.
     */
    public Object getTarget() {
        return fTarget;
    }
    public void setTarget( Object target ) {
        fTarget = target;
    }
    
    /**
     * Returns the actual source NodeBase correspoinding to the actual 
     * source object. If no edge is parsed it returns null.
     */
    public NodeBase getSourceNode() {
        NodeBase n = null;
        if ( fSource instanceof MObject ) {
            Map objectsMap = (Map) fAllMappings.get( LayoutTags.OBJECT );
            n = (NodeBase) objectsMap.get( fSource );
        }
        if ( fSource instanceof MClass ) {
            Map clsMap = (Map) fAllMappings.get( LayoutTags.CLASS );
            n = (NodeBase) clsMap.get( fSource );
        }
        return n;
    }
    /**
     * Returns the actual target NodeBase correspoinding to the actual 
     * target object. If no edge is parsed it returns null.
     */
    public NodeBase getTargetNode() {
        NodeBase n = null;
        if ( fTarget instanceof MObject ) {
            Map objectsMap = (Map) fAllMappings.get( LayoutTags.OBJECT );
            n = (NodeBase) objectsMap.get( fTarget );
        }
        if ( fTarget instanceof MClass ) {
            Map clsMap = (Map) fAllMappings.get( LayoutTags.CLASS );
            n = (NodeBase) clsMap.get( fTarget );
        }
        return n;
    }
    
    public Map getAllMappings() {
        return fAllMappings;
    }
    public void setAllMappings( Map allMappings ) {
        fAllMappings = allMappings;
    }
    public LayoutInfos getLayoutInfos() {
        return fLayoutInfos;
    }
    public void setLayoutInfos( LayoutInfos layoutInfos ) {
        fLayoutInfos = layoutInfos;
    }
    public DiagramOptions getOpt() {
        return fOpt;
    }
    public void setOpt( DiagramOptions opt ) {
        fOpt = opt;
    }
    public MSystem getSystem() {
        return fSystem;
    }
    public void setSystem( MSystem system ) {
        fSystem = system;
    }
    public String getType() {
        return fType;
    }
    public void setType( String type ) {
        fType = type;
        setActualMap( (Map) fAllMappings.get( fType ) );
    }
    public String getKind() {
        return fKind;
    }
    public void setKind( String kind ) {
        fKind = kind;
    }
    
    public Set getHiddenNodes() {
        return fLayoutInfos.getHiddenNodes();
    }
    public Set getHiddenEdges() {
        return fLayoutInfos.getHiddenEdges();
    }
    public MModel getModel() {
        return fSystem.model();
    }
    public MSystemState getSystemState() {
        return fSystem.state();
    }
    
    public Set getConnectedNodes() {
        return fConnectedNodes;
    }
    
    public void setID( int id ) {
        fID = id;
    }
    public int getID() {
        return fID;
    }
    public void setSpecialID( int specialId ) {
        fSpecialID = specialId;
    }
    public int getSpecialID() {
        return fSpecialID;
    }
    
    public String peekType() {
        if ( fTypes.isEmpty() ) {
            return null;
        }
        return (String) fTypes.peek();
    }
    public String pushType( String type ) {
        setActualMap( (Map) fAllMappings.get( type ) );
        return (String) fTypes.push( type );
    }
    public String popType() {
        String pop = (String) fTypes.pop();
        setActualMap( (Map) fAllMappings.get( peekType() ) );
        return pop;
    }
    
    public void printToLog( String message ) {
        fLayoutInfos.getLog().println( message );
    }
}
