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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.diagrams;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.graph.DirectedEdgeBase;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * Base class of all edge types in the diagram.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public abstract class EdgeBase extends DirectedEdgeBase
                               implements Selectable {
    private final int DIRECTED_EDGE = 100; 
    private final int INHERITANCE_EDGE = 200;
    /**
     * Special identification for the source node on this edge.
     */
    static final int SOURCE = 1;
    /**
     * Special identification for the target node on this edge.
     */
    static final int TARGET = 2;
    /**
     * Special identification for the first reflexive node on this edge.
     */
    static final int REFLEXIVE_1 = 3;
    /**
     * Special identification for the second reflexive node on this edge.
     */
    static final int REFLEXIVE_2 = 4;
    /**
     * Special identification for the third reflexive node on this edge.
     */
    static final int REFLEXIVE_3 = 5;
    /**
     * Special identification for the associationclass/objectlink node 
     * on this edge.
     */
    static final int ASSOC_CLASS = 6;
    /**
     * Special identification for the connection point from the dashed to 
     * the solid line of an associationclass/objectlink.
     */
    static final int ASSOC_CLASS_CON = 7;
    
    /**
     * Initial counter for the IDs for the nodes which are laying on 
     * this edge.
     */
    final int INITIAL_COUNTER = 0;
    /**
     * Counter for the IDs for the nodes which are laying on this edge.
     */
    int fNodesOnEdgeCounter = INITIAL_COUNTER;
    
    /**
     * Mouse click count of the given diagram. 
     */
    private int fClickCount = -1; // ==1 than show nodes on edge
    
    /**
     * Determinds if this edge is selected or not.
     */
    private boolean fIsSelected;
    
    /**
     * Determinds if this edge is dragged or not.
     */
    private boolean fIsDragged;
    
    /**
     * Name of this edge.
     */
    String fEdgeName;
    
    /**
     * List of all nodes laying on the dashed edge of an 
     * associationclass/objectlink.
     */
    List fNodesOnAssocClsEdge;
    /**
     * List of all nodes laying on this edge.
     */
    List fNodesOnEdge;
    /**
     * Source node of this edge (the drawn line starts here).
     */
    NodeOnEdge fSNode;
    /**
     * Target node of this edge (the drawn line ends here).
     */
    NodeOnEdge fTNode;
    /**
     * First reflexiv node on an reflexive edge.
     */
    NodeOnEdge fRefNode1;
    /**
     * Second reflexiv node on an reflexive edge.
     */
    NodeOnEdge fRefNode2;
    /**
     * Third reflexiv node on an reflexive edge.
     */
    NodeOnEdge fRefNode3;
    /**
     * Associationclass/Objectlink node.
     */
    NodeOnEdge fNENode; 
    /**
     * Point which connects the dashed line of an associationclass/objectlink
     * to the solid line.
     */
    NodeOnEdge fConNode; 
    
    /**
     * Options of the diagram in which this edge is drawn.
     */
    DiagramOptions fOpt;
    
    /**
     * Source node of this edge.
     */
    NodeBase fSource;
    /**
     * Target node of this edge.
     */
    NodeBase fTarget;
    /**
     * Rolename which is on the source side of this edge.
     */
    EdgeProperty fSourceRolename;
    /**
     * Rolename which is on the target side of this edge.
     */
    EdgeProperty fTargetRolename;
    /**
     * Multiplicity which is on the source side of this edge.
     */
    EdgeProperty fSourceMultiplicity;
    /**
     * Multiplicity which is on the target side of this edge.
     */
    EdgeProperty fTargetMultiplicity;
    /**
     * Associationname/Linkname of this edge.
     */
    EdgeProperty fAssocName;
    /**
     * X-coordinate of the source node.
     */
    int fX1;
    /**
     * Y-coordinate of the source node.
     */
    int fY1;
    /**
     * X-coordinate of the target node.
     */
    int fX2;
    /**
     * Y-coordinate of the target node.
     */
    int fY2;
    
    /**
     * The diagram the edge belongs to.
     */
    DiagramView fDiagram;
    
    /**
     * Association this edge belongs to.
     */
    MAssociation fAssoc;

    /**
     * Constructs a new edge.
     * @param source The source node of the edge.
     * @param target The target node of the edge.
     * @param edgeName The name of the edge.
     * @param diagram The diagram this edge belongs to.
     */
    public EdgeBase( Object source, Object target, String edgeName,
                     DiagramView diagram, MAssociation assoc ) {
        super( source, target );
        fDiagram = diagram;
        fOpt = fDiagram.getOptions();
        fEdgeName = edgeName;
        fAssoc = assoc;
        fSource = (NodeBase) source;
        fTarget = (NodeBase) target;
        fNodesOnEdge = new ArrayList();
        fNodesOnAssocClsEdge = new ArrayList();
        
        fX1 = (int) fSource.x();
        fY1 = (int) fSource.y();
        fX2 = (int) fTarget.x();
        fY2 = (int) fTarget.y();
        
        Point2D sp = getIntersectionCoordinate( fSource, fX1, fY1, 
                                                fX2, fY2 );
        Point2D tp = getIntersectionCoordinate( fTarget, fX2, fY2, 
                                                fX1, fY1 );
        
        fSNode = new NodeOnEdge( sp.getX(), sp.getY(),
                                 fSource, fTarget, this, 
                                 fNodesOnEdgeCounter++,
                                 EdgeBase.SOURCE, edgeName, fOpt );
        fTNode = new NodeOnEdge( tp.getX(), tp.getY(), 
                                 fSource, fTarget, this, 
                                 fNodesOnEdgeCounter++,
                                 EdgeBase.TARGET, edgeName, fOpt ); 
        fNodesOnEdge.add( fSNode );
        fNodesOnEdge.add( fTNode );
    }
    
    /**
     * Draws the edge in a given graphic object.
     */
    public abstract void draw( Graphics g, FontMetrics fm );
    
    /**
     * Draws the edge segments of this edge.
     * @param g The edge is drawn in this graphics object.
     */
    void drawEdge( Graphics g ) {
        NodeOnEdge n1 = null;
        NodeOnEdge n2 = null;
        int source = MAggregationKind.NONE;
        int target = MAggregationKind.NONE;
        int sourceNav = -1; // end navigable??
        int targetNav = -1; // end navigable??
        int inheritance = -1; // inheritance edge
        
        if ( fAssoc != null ) {
            Iterator assocEndIt = fAssoc.associationEnds().iterator();
            while ( assocEndIt.hasNext() ) {
                MAssociationEnd assocEnd = (MAssociationEnd) assocEndIt.next();
                
                if ( isReflexive() ) {
                    source = assocEnd.aggregationKind();
                    if ( assocEnd.isExplicitNavigable() ) {
                        sourceNav = DIRECTED_EDGE;
                    }
                    MAssociationEnd assocEnd2 = (MAssociationEnd) assocEndIt.next();
                    target = assocEnd2.aggregationKind();
                    if ( assocEnd2.isExplicitNavigable() ) {
                        targetNav = DIRECTED_EDGE;
                    }
                    continue;
                }
                
                if ( fSource.cls() != null 
                     && fSource.cls().equals( assocEnd.cls() ) ) {
                    source = assocEnd.aggregationKind();
                    if ( assocEnd.isExplicitNavigable() ) {
                        sourceNav = DIRECTED_EDGE;
                    }
                } else if ( fTarget.cls() != null 
                            && fTarget.cls().equals( assocEnd.cls() ) ) {
                    target = assocEnd.aggregationKind();
                    if ( assocEnd.isExplicitNavigable() ) {
                        targetNav = DIRECTED_EDGE;
                    }
                }
            }
        } else if ( fAssoc == null ) {
            inheritance = INHERITANCE_EDGE;
        }
        // draw all line segments
        if ( !fNodesOnEdge.isEmpty() ) {
            Iterator it = fNodesOnEdge.iterator();
            int counter = 0;
            if ( it.hasNext() ) {
                n1 = (NodeOnEdge) it.next();
                counter++;
            }
            while( it.hasNext() ) {
                n2 = (NodeOnEdge) it.next();
                counter++;
                // draw nodeOnEdge
                n2.draw( g, g.getFontMetrics() );
                
                try {
                    if ( source != MAggregationKind.NONE
                         && ( n1.getSpecialID() == SOURCE || n1.getSpecialID() == TARGET )) {
                        if ( targetNav == DIRECTED_EDGE 
                             && n1.getSpecialID() == SOURCE 
                             && n2.getSpecialID() == TARGET ) {
                            drawAssociationKind( g, n2, n1, source+DIRECTED_EDGE );
                        } else {
                            drawAssociationKind( g, n2, n1, source );
                        }
                    } else if ( target != MAggregationKind.NONE
                                && ( n2.getSpecialID() == TARGET || n2.getSpecialID() == SOURCE )) {
                        if ( sourceNav == DIRECTED_EDGE 
                             && n1.getSpecialID() == SOURCE 
                             && n2.getSpecialID() == TARGET ) {
                            drawAssociationKind( g, n1, n2, target+DIRECTED_EDGE );
                        } else {
                            drawAssociationKind( g, n1, n2, target );
                        }
                    } else if ( sourceNav == DIRECTED_EDGE 
                                && n1.getSpecialID() == SOURCE ) {
                        drawAssociationKind( g, n2, n1, DIRECTED_EDGE );
                    } else if ( targetNav == DIRECTED_EDGE 
                                && n2.getSpecialID() == TARGET ) {
                        drawAssociationKind( g, n1, n2, DIRECTED_EDGE );
                    } else if ( inheritance == INHERITANCE_EDGE ) {
                        drawAssociationKind( g, n1, n2, INHERITANCE_EDGE );
                    } else {
                        DirectedEdgeFactory.drawAssociation( g, (int) n1.x(),
                                                             (int) n1.y(),
                                                             (int) n2.x(),
                                                             (int) n2.y() );
                    }
                    n1 = n2;
                } catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Draws the last line segment, as an association, 
     * composition, aggregation, directed edge etc.
     */
    private void drawAssociationKind( Graphics g, NodeOnEdge n1, NodeOnEdge n2,
                                      int kind ) {
        
        try {
            // draw association
            switch ( kind ) {
            case MAggregationKind.NONE:
                DirectedEdgeFactory.drawAssociation( g, (int) n1.x(),
                                                     (int) n1.y(),
                                                     (int) n2.x(),
                                                     (int) n2.y() );
                break;
            case MAggregationKind.AGGREGATION:
                DirectedEdgeFactory.drawAggregation( g, (int) n1.x(),
                                                     (int) n1.y(),
                                                     (int) n2.x(),
                                                     (int) n2.y() );
                break;
            case MAggregationKind.AGGREGATION + DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedAggregation( g, (int) n1.x(),
                                                             (int) n1.y(),
                                                             (int) n2.x(),
                                                             (int) n2.y() );
                break;
            case MAggregationKind.COMPOSITION:
                DirectedEdgeFactory.drawComposition( g, (int) n1.x(),
                                                     (int) n1.y(),
                                                     (int) n2.x(),
                                                     (int) n2.y() );
                break;
            case MAggregationKind.COMPOSITION + DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedComposition( g, (int) n1.x(),
                                                             (int) n1.y(),
                                                             (int) n2.x(),
                                                             (int) n2.y() );
                break;
            case DIRECTED_EDGE:
                DirectedEdgeFactory.drawDirectedEdge( g, (int) n1.x(),
                                                      (int) n1.y(),
                                                      (int) n2.x(),
                                                      (int) n2.y() );
                break;
            case INHERITANCE_EDGE:
                DirectedEdgeFactory.drawInheritance( g, (int) n1.x(),
                                                     (int) n1.y(),
                                                     (int) n2.x(),
                                                     (int) n2.y() );
                break;
            default:
                DirectedEdgeFactory.drawAssociation( g, (int) n1.x(),
                                                     (int) n1.y(),
                                                     (int) n2.x(),
                                                     (int) n2.y() );
                break;
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    public int getClickCount() {
        return fClickCount;
    }
    public void setClickCount( int clickCount ) {
        fClickCount = clickCount;
    }
    public boolean isReflexive() {
        return fSource.equals( fTarget );
    }
    public List getNodesOnEdge() {
        return fNodesOnEdge;
    }
    public void resetNodesOnEdges() {
        List nodes = new ArrayList();
        Iterator it = getNodesOnEdge().iterator();
        while ( it.hasNext() ) {
            NodeOnEdge node = (NodeOnEdge) it.next();
            if ( isNodeSpecial( node ) ) {
                nodes.add( node );
            }
        }
        fNodesOnEdge = nodes;
    }
    
    public boolean isSelected() {
        return fIsSelected;
    }
    public void setSelected( boolean selected ) {
        fIsSelected = selected;
    }
    
    public boolean isDragged() {
        return fIsDragged;
    }
    public void setDragged( boolean dragging ) {
        fIsDragged = dragging;
    }
    
    public boolean isLink() {
        return fDiagram instanceof NewObjectDiagram;
    }
    
    /**
     * Is beneith the x,y position an edge, than an additional node
     * will be added on this edge.
     *  
     * @param x x position to check
     * @param y y position to check
     * @return The new NodeOnEdge if there was an edge beneith this location
     * otherwise null is returnd.
     */
    public NodeOnEdge occupiesThanAdd( int x, int y, int clickCount ) {
        boolean occupies = false;
        Line2D line = new Line2D.Double();
        NodeOnEdge n1 = null;
        NodeOnEdge n2 = null;
        
        // checking every line segmend of this edge.
        Iterator it = fNodesOnEdge.iterator();
        if ( it.hasNext() ) {
            n1 = (NodeOnEdge) it.next();
        }
        while ( it.hasNext() ) {
            n2 = (NodeOnEdge) it.next();
            line = new Line2D.Double( n1.x(), n1.y(), n2.x(), n2.y() );
            occupies = line.intersects( x - 2, y - 2, 4, 4 );
            if ( occupies && clickCount == 2 ) {
                // are the coordinates abouve another node do not add another 
                // node.
                if ( !n2.dimension().contains( x, y ) 
                     || !n1.dimension().contains( x, y ) ) {
                    
                    NodeOnEdge newNode = new NodeOnEdge( x, y, fSource, fTarget, 
                                                         this, fNodesOnEdgeCounter++,
                                                         0, fEdgeName, fOpt );
                    addNode( newNode, n1 );
                    fClickCount = clickCount;
                    return newNode;    
                }
            }
            if ( occupies ) {
                fClickCount = clickCount;
                return null;
            }
            // setting n2 to the first node now
            n1 = n2;
        }
        
        fClickCount = -1;
        return null;
    }
    
    /**
     * Adds a node to the list of nodes on this edge.
     * @param node Node to be added.
     * @param n1 Behind this node <code>node</code> will be added to 
     *           the list of nodes.
     */
    private void addNode( NodeOnEdge node, NodeOnEdge n1 ) {
        fNodesOnEdge.add( fNodesOnEdge.indexOf( n1 )+1, node );
        reIDNodes();
    }
    
    /**
     * Resets the ids of all nodes on this edge. The nodes have 
     * allways an increasing ID starting at the source node. 
     */
     void reIDNodes() {
        int counter = INITIAL_COUNTER;
        Iterator it = fNodesOnEdge.iterator();
        while ( it.hasNext() ) {
            NodeOnEdge n = (NodeOnEdge) it.next();
            n.setID( counter );
            counter++;
        }
    }
     
     public boolean isNodeSpecial( NodeOnEdge node )  {
         if ( node.getSpecialID() == EdgeBase.SOURCE 
                 || node.getSpecialID() == EdgeBase.TARGET
                 || node.getSpecialID() == EdgeBase.ASSOC_CLASS
                 || node.getSpecialID() == EdgeBase.ASSOC_CLASS_CON
                 || node.getSpecialID() == EdgeBase.REFLEXIVE_1 
                 || node.getSpecialID() == EdgeBase.REFLEXIVE_2
                 || node.getSpecialID() == EdgeBase.REFLEXIVE_3 ) {
             return true;
         }
         return false;
     }
     

     private boolean shouldNodeBeMoveableRightNow( NodeOnEdge node ) {
         if ( node.getSpecialID() == EdgeBase.SOURCE                     // source node
                 || node.getSpecialID() == EdgeBase.TARGET                  // target node
                 || node.getSpecialID() == EdgeBase.ASSOC_CLASS             // associactioclass node
                 || ( node.getSpecialID() == EdgeBase.ASSOC_CLASS_CON       // associationclass
                         && getNodesOnEdge().size() <= 3 )
                         || ( isReflexive()                              // reflexive edge
                                 && getNodesOnEdge().size() <= 5 ) 
                                 || ( isReflexive() && this instanceof NodeEdge // selfreflexive associationclass 
                                         && getNodesOnEdge().size() <= 6 ) ) {
             return false;
         } 
         return true;         
     }

     /**
     * Removes a node from the edge.
     * @param node Node to be removed.
     */
    public void removeNodeOnEdge( NodeOnEdge node ) {
        if ( isNodeSpecial( node ) ) {
            return;
        }
        fNodesOnEdge.remove( node );
    }
    
    /**
     * Checkes if there is a node laying under the x,y coordinate.
     * 
     * @return true if the position occupies a node otherwise false.
     */
    public boolean occupiesNodeOnEdge( int x, int y ) {
        Iterator it = fNodesOnEdge.iterator();
        while ( it.hasNext() ) {
            NodeOnEdge node = (NodeOnEdge) it.next();
            if ( node.occupies( x, y ) 
                 && ( !isNodeSpecial( node ) || shouldNodeBeMoveableRightNow( node ) ) ) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the node laying under the x,y coordinate, otherwise null
     * is returnd. 
     */
    public EdgeProperty getNodeOnEdge( int x, int y ) {
        Iterator it = fNodesOnEdge.iterator();
        while ( it.hasNext() ) {
            NodeOnEdge node = (NodeOnEdge) it.next();
            if ( node.occupies( x, y ) ) {
                return node;
            }
        }
        return null;
    }
    
    /**
     * Sets the correct intersection points on the target and source
     * nodes.
     */
    private void updateNodeOnEdges() {
        NodeOnEdge n1 = (NodeOnEdge) fNodesOnEdge.get( 1 );
        Point2D sp = getIntersectionCoordinate( fSource, fX1, fY1, 
                                                (int) n1.x(), (int) n1.y() );
        fSNode.setPosition( sp.getX(), sp.getY() );
        
        NodeOnEdge n2 = (NodeOnEdge) fNodesOnEdge.get( fNodesOnEdge.size()-2 );
        Point2D tp = getIntersectionCoordinate( fTarget, fX2, fY2,
                                                (int) n2.x(), (int) n2.y() );
        fTNode.setPosition( tp.getX(), tp.getY() );
    }
    
    /**
     * Sets the start and end point of this Edge.
     */
    void setPoint( int side, double x, double y ) {
        switch ( side ) {
        case SOURCE: 
            fX1 = (int) x;
            fY1 = (int) y;
            fSNode.setPosition( fX1, fY1 );
            
            if ( fAssocName != null ) {
                fAssocName.updateSourceEdgePoint( x, y );
            }
            if ( fSourceRolename != null ) {
                fSourceRolename.updateSourceEdgePoint( x, y );    
            }
            if ( fTargetRolename != null ) {
                fTargetRolename.updateTargetEdgePoint( x, y );
            }
            if ( fSourceMultiplicity != null ) {
                fSourceMultiplicity.updateSourceEdgePoint( x, y );        
            }
            if ( fTargetMultiplicity != null ) {
                fTargetMultiplicity.updateTargetEdgePoint( x, y );
            }
            break;
        case TARGET:
            fX2 = (int) x;
            fY2 = (int) y;
            fTNode.setPosition( fX2, fY2 );
            
            if ( fAssocName != null ) {
                fAssocName.updateTargetEdgePoint( x, y );
            }
            if ( fSourceRolename != null ) {
                fSourceRolename.updateTargetEdgePoint( x, y );
            }
            if ( fTargetRolename != null ) {            
                fTargetRolename.updateSourceEdgePoint( x, y );
            }
            if ( fSourceMultiplicity != null ) {
                fSourceMultiplicity.updateTargetEdgePoint( x, y );        
            }
            if ( fTargetMultiplicity != null ) {
                fTargetMultiplicity.updateSourceEdgePoint( x, y );
            }
            break;
        default:
            break;
        }
        
    }
    
    /**
     * @return Returns the fSourceRolename.
     */
    public EdgeProperty getSourceRolename() {
        return fSourceRolename;
    }
    /**
     * @return Returns the fTargetRolename.
     */
    public EdgeProperty getTargetRolename() {
        return fTargetRolename;
    }
    
    /**
     * @return Returns the fSourceMultiplicity.
     */
    public EdgeProperty getSourceMultiplicity() {
        return fSourceMultiplicity;
    }
    /**
     * @return Returns the fTargetMultiplicity.
     */
    public EdgeProperty getTargetMultiplicity() {
        return fTargetMultiplicity;
    }
    
    /**
     * @return Returns the fAssocName.
     */
    public EdgeProperty getAssocName() {
        return fAssocName;
    }
    
    /**
     * Returns true if the source or target of this edge is an
     * ObjectNode otherwise the method returns false.
     */
    protected boolean isUnderlinedLabel() {
        return fSource instanceof ObjectNode || fTarget instanceof ObjectNode;
    }
    
    /**
     * Checks if there is more than one edge between two nodes.
     */
    public Set checkForNewPositionAndDraw( DirectedGraph graph, Graphics g, 
                                           FontMetrics fm ) {
        Set edges = null;
        
        if ( graph.existsPath( fSource, fTarget ) ) {
            edges = graph.edgesBetween( fSource, fTarget );
            calculateNewPosition( edges );
        }
        if ( edges != null ) {
            Iterator it = edges.iterator();
            while ( it.hasNext() ) {
                EdgeBase e = (EdgeBase) it.next();
                e.draw( g, fm );
            }
        }
        return edges;
    }
    
    /**
     * Calculates the space between the lines if there are more than one
     * edge between two nodes.
     */
    private double calculateSpaces( double length, Set edges ) {
        return length / ( (double) edges.size() + 1 );
    }
    
    /**
     * Calculates and sets the new position of the edges between two
     * nodes.
     */
    private void calculateNewPosition( Set edges ) {
        Polygon sourceRec = fSource.dimension();
        Polygon targetRec = fTarget.dimension();
        
        double sWidth = sourceRec.getBounds().getWidth();
        double sHeight = sourceRec.getBounds().getHeight();
        double tWidth = targetRec.getBounds().getWidth();
        double tHeight = targetRec.getBounds().getHeight();
        
        // midpoints
        double sX = fSource.x();
        double sY = fSource.y();
        double tX = fTarget.x();
        double tY = fTarget.y();
        
        // source corner points
        double uLeftX = sX - sWidth / 2.0;
        double uRightX = sX + sWidth / 2.0;
        double lLeftX = sX - sWidth / 2.0;
        double lRightX = sX + sWidth / 2.0;
        
        double uLeftY = sY - sHeight / 2.0;
        double uRightY = sY - sHeight / 2.0;
        double lLeftY = sY + sHeight / 2.0;
        double lRightY = sY + sHeight / 2.0;
        
        double space = 0;
        double projection = 0;
        
        // line from midpoint to midpoint
        Line2D.Double line = new Line2D.Double( fSource.x(), fSource.y(),
                                                fTarget.x(), fTarget.y() );
        
        // if there is just one link between two objects set the
        // midpoint of the objects as start and end point of the
        // link. Otherwise calculate the new positions.
        if ( edges.size() == 1 ) {
            Iterator it = edges.iterator();
            while ( it.hasNext() ) {
                EdgeBase e = (EdgeBase) it.next();
                // edge is reflexive
                if ( isReflexive() ) {
                    setCorrectPoints( sX + sWidth/3, sY - sHeight/2,
                                      tX + tWidth/2, tY - 4, e );   
                    updateNodeOnEdges();
                } else {
                    setCorrectPoints( fSource.x(), fSource.y(),
                                      fTarget.x(), fTarget.y(), e );
                    updateNodeOnEdges();
                }
            }
        } else {
            // there are more then just one link between source and target
            // node.
            
            // there are up to four reflexive edges 
            if ( isReflexive() ) {
                Iterator it = edges.iterator();
                int counter = 0;
                while ( it.hasNext() ) {
                    EdgeBase e = (EdgeBase) it.next();
                    counter++;
                    switch ( counter ) {
                    case 1:
                        setCorrectPoints( sX + sWidth/3, sY - sHeight/2,
                                          tX + tWidth/2, tY - 4, e );
                        break;
                    case 2:
                        setCorrectPoints( sX + sWidth/3, sY + sHeight/2,
                                          tX + tWidth/2, tY + 4, e );
                        break;
                    case 3:
                        setCorrectPoints( sX - sWidth/3, sY - sHeight/2,
                                          tX - tWidth/2, tY - 4, e );
                        break;
                    case 4:
                        setCorrectPoints( sX - sWidth/3, sY + sHeight/2,
                                          tX - tWidth/2, tY + 4, e );
                        break;
                    default:
                        setCorrectPoints( sX + sWidth/3, sY - sHeight/2,
                                          tX + tWidth/2, tY - 4, e );
                        break;
                    }
                }
            }
            
            
            // to get a approiate separation of the links ...
            // ... use the width
            if ( line.intersectsLine( uLeftX, uLeftY, uRightX, uRightY )
                    || line.intersectsLine( lLeftX, lLeftY, lRightX, lRightY ) ) {
                // calculates the length of the space between the edges
                space = calculateSpaces( Math.min( sWidth, tWidth ), edges );
                // calculates the length of the space to the first edge
                // for the bigger rectangle
                projection = ( Math.max( sWidth, tWidth ) - space
                        * ( edges.size() - 1 ) ) / 2.0;
                
                double counter = 1.0;
                
                // y-coordinates of start and end point for all lines
                double sStartY = sY;
                double tStartY = tY;
                
                double sStartX = sX - sWidth / 2.0;
                double tStartX = tX - tWidth / 2.0;
                
                // calculates the different x-coordinates of start and end
                // point
                Iterator it = edges.iterator();
                while ( it.hasNext() ) {
                    EdgeBase e = (EdgeBase) it.next();
                    // addition of space or projection to get the
                    // wanted space between the edges
                    if ( counter == 1 ) {
                        if ( sWidth <= tWidth ) {
                            sStartX += space;
                            tStartX += projection;
                        } else {
                            sStartX += projection;
                            tStartX += space;
                        }
                    } else {
                        sStartX += space;
                        tStartX += space;
                    }
                    setCorrectPoints( sStartX, sStartY, tStartX, tStartY, e );   
                    updateNodeOnEdges();
                    counter++;
                }
            // ... use the height
            } else if ( line.intersectsLine( uLeftX, uLeftY, lLeftX, lLeftY )
                    || line.intersectsLine( uRightX, uRightY, lRightX,
                                            lRightY ) ) {
                // calculates the length of the space between the edges
                space = calculateSpaces( Math.min( sHeight, tHeight ), edges );
                // calculates the length of the space to the first edge
                // for the bigger rectangle
                projection = ( Math.max( sHeight, tHeight ) - space
                        * ( edges.size() - 1 ) ) / 2.0;
                
                double counter = 1.0;
                
                // x-coordinates of start and end point for all lines
                double sStartX = sX;
                double tStartX = tX;
                
                double sStartY = sY - sHeight / 2.0;
                double tStartY = tY - tHeight / 2.0;
                
                // calculates the different y-coordinates of start and end
                // point
                Iterator it = edges.iterator();
                while ( it.hasNext() ) {
                    EdgeBase e = (EdgeBase) it.next();
                    // addition of space or projection to get the
                    // wanted space between the edges
                    if ( counter == 1 ) {
                        if ( sHeight <= tHeight ) {
                            sStartY += space;
                            tStartY += projection;
                        } else {
                            sStartY += projection;
                            tStartY += space;
                        }
                    } else {
                        sStartY += space;
                        tStartY += space;
                    }
                    setCorrectPoints( sStartX, sStartY, tStartX, tStartY, e );   
                    updateNodeOnEdges();
                    counter++;
                }
            }
        }
    }
    
    /**
     * Determinds the correct source side and sets the specific points.
     * 
     * @param sX X-coordinate for the source point.
     * @param sY Y-coordinate for the source point.
     * @param tX X-coordinate for the target point.
     * @param tY Y-coordinate for the target point.
     * @param e The points of this EdgeBase will be set.
     */
    private void setCorrectPoints( double sX, double sY, double tX, double tY,
                                   EdgeBase e ) {
        if ( e.fSource.equals( fSource ) ) {
            e.setPoint( SOURCE, sX, sY );
            e.setPoint( TARGET, tX, tY );
        } else {
            e.setPoint( TARGET, sX, sY );
            e.setPoint( SOURCE, tX, tY );
        }
        e.updateNodeOnEdges();
    }
    
    /**
     * This method calculates the intersection point of the given line and 
     * one of the four lines of the nodes polygon.
     * 
     * @param node the source node.
     * @param sX the source x-coordinate of the line.
     * @param sY the source y-coordinate of the line.
     * @param tX the target x-coordinate of the line.
     * @param tY the target y-coordinate of the line.
     */
    Point2D.Double getIntersectionCoordinate( NodeBase node, int sX, int sY,
                                              int tX, int tY ) {
        Polygon polygon = node.dimension();
        
        int[] xpoints = polygon.xpoints;
        int[] ypoints = polygon.ypoints;
        
        // source corner points
        // corner 1  ---------------  corner 2              
        //           |             |
        //           |             |
        // corner 4  ---------------  corner 3
        //
        //                  /\ corner 1
        //       corner 4  /  \ 
        //                 \  /  corner 2
        //       corner 3   \/
        double x_corner1 = xpoints[0];
        double x_corner2 = xpoints[1];
        double x_corner3 = xpoints[2];
        double x_corner4 = xpoints[3];
        double y_corner1 = ypoints[0];
        double y_corner2 = ypoints[1];
        double y_corner3 = ypoints[2];
        double y_corner4 = ypoints[3];
        
        // line from source node to target node
        Line2D.Double line = new Line2D.Double( sX, sY, tX, tY );
        
        // getting the intersection coordinate 
        if ( line.intersectsLine( x_corner1, y_corner1, x_corner2, y_corner2 ) ) {
            // cuts the line between corner 1 and 2 of the node
            return intersectionPoint( sX, sY, tX, tY, 
                                      x_corner1, y_corner1, 
                                      x_corner2, y_corner2 );
        } else if ( line.intersectsLine( x_corner3, y_corner3, x_corner4, y_corner4 ) ) {
            // cuts the line between corner 3 and 4 of the node
            return intersectionPoint( sX, sY, tX, tY, 
                                      x_corner3, y_corner3, 
                                      x_corner4, y_corner4 );
        } else if ( line.intersectsLine( x_corner1, y_corner1, x_corner4, y_corner4 ) ) {
            // cuts the line between corner 1 and 4 of the node
            return intersectionPoint( sX, sY, tX, tY, 
                                      x_corner1, y_corner1, 
                                      x_corner4, y_corner4 );
        } else if ( line.intersectsLine( x_corner2, y_corner2, x_corner3, y_corner3 ) ) {
            // cuts the line between corner 2 and 3 of the node
            return intersectionPoint( sX, sY, tX, tY, 
                                      x_corner2, y_corner2, 
                                      x_corner3, y_corner3 );
        }
        
        // if no line is cut return the start point 
        // (both nodes lay on top of each other.
        return new Point2D.Double( sX, sY );
    }
    
    /** 
     * Calculates the intersection point of to lines.
     * 
     * @param sX source x-coordinate of the first line.
     * @param sY source y-coordinate of the first line.
     * @param tX target x-coordinate of the first line.
     * @param tY target y-coordinate of the first line.
     * @param x_corner1 source x-coordinate of the second line.
     * @param y_corner1 source y-coordinate of the second line.
     * @param x_corner2 target x-coordinate of the second line.
     * @param y_corner2 target y-coordinate of the second line.
     * @return the intersection point of both lines.
     */    
    private Point2D.Double intersectionPoint( int sX, int sY, int tX, int tY, 
                                              double x_corner1, double y_corner1, 
                                              double x_corner2, double y_corner2 ) {
        // getting the intersection coordinate by vector arithmetic
        // example for the top line:
        //   sx over sy + r * (tx - sx) over (ty - sy)
        // = x_corner1 over y_corner1 + v * (x_corner2 - x_corner1) over (y_corner2 - y_corner1)
        
        double numerator = 1.0;
        double denominator = 1.0;
        
        numerator = (tX - sX) * (sY - y_corner1) + (tY - sY) * (x_corner1 - sX);
        denominator = (y_corner2 - y_corner1) * (tX - sX) 
        - (x_corner2 - x_corner1) * (tY - sY); 
        
        double v = numerator / denominator;
        
        double intersection_X = x_corner1 + v * (x_corner2 - x_corner1);
        double intersection_Y = y_corner1 + v * (y_corner2 - y_corner1);
        
        return new Point2D.Double( intersection_X, intersection_Y );
    }
    
    /**
     * Saves placement information about this edge.
     * @param hidden If this edge should be hidden or not.
     * @return A XML representation of the layout information.
     */
    public String storePlacementInfo( boolean hidden ) {
        String xml = "";

        if ( this instanceof NodeEdge && fAssoc.associationEnds().size() > 2 ) {
            // xml tag will be written in NodeEdge itself. 
            // Will be called from DiamondNode.
            return xml;
        }
        
        xml = LayoutTags.EDGE_O;
        if ( this instanceof NodeEdge ) {
            if ( isLink() ) {
                xml += " type=\"NodeEdge\" kind=\"link\">" + LayoutTags.NL;
            } else {
                xml += " type=\"NodeEdge\" kind=\"association\">" + LayoutTags.NL;
            }
        } else if ( this instanceof BinaryEdge ) {
            if ( isLink() ) {
                xml += " type=\"BinaryEdge\" kind=\"link\">" + LayoutTags.NL;
            } else {
                xml += " type=\"BinaryEdge\" kind=\"association\">" + LayoutTags.NL;
            } 
        } else if ( this instanceof HalfEdge ) {
            if ( isLink() ) {
                xml += " type=\"HalfEdge\" kind=\"link\">" + LayoutTags.NL;
            } else { 
                xml += " type=\"HalfEdge\" kind=\"association\">" + LayoutTags.NL;
            }
        } else if ( this instanceof GeneralizationEdge ) { 
            xml += " type=\"Inheritance\">" + LayoutTags.NL;
        } else {
            xml += " type=\"EdgeBase\">" + LayoutTags.NL;
        } 
        
        xml += LayoutTags.INDENT + LayoutTags.SOURCE_O + fSource.name() 
               + LayoutTags.SOURCE_C + LayoutTags.NL;
        xml += LayoutTags.INDENT + LayoutTags.TARGET_O + fTarget.name() 
               + LayoutTags.TARGET_C + LayoutTags.NL;
        xml += LayoutTags.INDENT + LayoutTags.NAME_O + fEdgeName 
               + LayoutTags.NAME_C + LayoutTags.NL;

        
        if ( fSourceRolename != null ) {
            xml += fSourceRolename.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( fTargetRolename != null ) {
            xml += fTargetRolename.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( fSourceMultiplicity != null ) {
            xml += fSourceMultiplicity.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( fTargetMultiplicity != null ) {
            xml += fTargetMultiplicity.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( fAssocName != null ) {
            xml += fAssocName.storePlacementInfo( hidden ) + LayoutTags.NL;
        }
        if ( !fNodesOnEdge.isEmpty() ) {
            Iterator it = fNodesOnEdge.iterator();
            while ( it.hasNext() ) {
                NodeOnEdge n = (NodeOnEdge) it.next();
                xml += n.storePlacementInfo( hidden ) + LayoutTags.NL;
            }
        }
        xml += LayoutTags.INDENT + LayoutTags.HIDDEN_O + hidden 
               + LayoutTags.HIDDEN_C + LayoutTags.NL;
        
        xml += LayoutTags.EDGE_C + LayoutTags.NL;
        return xml;
    }
    
    /**
     * Sorts the nodes on this edge according to their id.
     */
    public void sortNodesOnEdge() {
        Collections.sort( fNodesOnEdge, new NodeOnEdgeComparator() );
    }
}