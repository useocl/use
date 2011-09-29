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

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.Color;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.xml.xpath.XPathConstants;

import org.tzi.use.config.Options;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.NAryAssociationClassOrObjectEdge;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.event.ActionHideObjectDiagram;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSelectAll;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MLinkSet;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.util.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * A panel drawing UML object diagrams.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Mark Richters
 */
@SuppressWarnings("serial")
public class NewObjectDiagram extends DiagramView 
                              implements HighlightChangeListener {

    /**
     * This class saves needed information about
     * visible or hidden nodes and edges.
     * When hiding or showing objects the data is
     * moved between the fields {@link NewObjectDiagram#visibleData} and
     * {@link NewObjectDiagram#hiddenData}. 
     * 
     * @author lhamann
     */
	public static class ObjectDiagramData {
		/**
		 * 
		 */
		public Map<MObject, ObjectNode> fObjectToNodeMap;
		/**
		 * 
		 */
		public Map<MLink, BinaryAssociationOrLinkEdge> fBinaryLinkToEdgeMap;
		/**
		 * 
		 */
		public Map<MLink, DiamondNode> fNaryLinkToDiamondNodeMap;
		/**
		 * 
		 */
		public Map<MLink, List<EdgeBase>> fHalfLinkToEdgeMap;
		/**
		 * 
		 */
		public Map<MLinkObject, EdgeBase> fLinkObjectToNodeEdge;

		/**
		 * 
		 */
		public ObjectDiagramData() {
			fObjectToNodeMap = new HashMap<MObject, ObjectNode>();
	        fBinaryLinkToEdgeMap = new HashMap<MLink, BinaryAssociationOrLinkEdge>();
	        fNaryLinkToDiamondNodeMap = new HashMap<MLink, DiamondNode>();
	        fHalfLinkToEdgeMap = new HashMap<MLink, List<EdgeBase>>();
	        fLinkObjectToNodeEdge = new HashMap<MLinkObject, EdgeBase>();
		}

		/**
		 * @param link
		 * @return
		 */
		public boolean containsLink(MLink link) {
			return fBinaryLinkToEdgeMap.containsKey(link)
					|| fNaryLinkToDiamondNodeMap.containsKey(link)
					|| fLinkObjectToNodeEdge.containsKey(link);
		}
	}

    private ObjectDiagramData visibleData = new ObjectDiagramData();
    
    private ObjectDiagramData hiddenData = new ObjectDiagramData();
    
	private NewObjectDiagramView fParent;
    
	/**
	 * The position of the next object node.
	 * This is either set to a random value or
	 * to a specific position when an object
	 * is created by drag & drop.
	 */
	private Point2D.Double nextNodePosition = new Point2D.Double();
        
    private ShowObjectPropertiesViewMouseListener 
        showObjectPropertiesViewMouseListener 
            = new ShowObjectPropertiesViewMouseListener();

    // jj anfangen
	private ObjectSelection fSelection;
	// jj end

    /**
     * Creates a new empty diagram.
     */
    NewObjectDiagram(NewObjectDiagramView parent, PrintWriter log) {
    	this.getRandomNextPosition();
    		
        fOpt = new ObjDiagramOptions();
        fGraph = new DirectedGraphBase<NodeBase, EdgeBase>();
        
        fParent = parent;
        fNodeSelection = new Selection<PlaceableNode>();
        fEdgeSelection = new Selection<EdgeBase>();
        
        fLog = log;
        
        // anfangs jj
        fSelection = new ObjectSelection(this);
        // end jj
        
        fActionSaveLayout = new ActionSaveLayout( "USE object diagram layout",
                                                  "olt", this );
        
        fActionLoadLayout = new ActionLoadLayout( "USE object diagram layout",
                                                  "olt", this, fLog, fGraph);
        
        fActionSelectAll = new ActionSelectAll( fNodeSelection, visibleData.fObjectToNodeMap,
                                                null, this );
        
        DiagramInputHandling inputHandling = 
            new DiagramInputHandling( fNodeSelection, fEdgeSelection, this);
        
        fParent.getModelBrowser().addHighlightChangeListener( this );
        
        setLayout(null);
        setBackground(Color.white);
        setPreferredSize( Options.fDiagramDimension );
        
        addMouseListener(inputHandling);
        addMouseListener(showObjectPropertiesViewMouseListener);
        fParent.addKeyListener( inputHandling );
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // need a new layouter to adapt to new window size
                fLayouter = null;
            }
        });

        startLayoutThread();
    }

    public ObjectSelection getObjectSelection() {
    	return this.fSelection;
    }
    
    /**
     * Displays objects of the selected class in the modelbrowser.
     */
    public void stateChanged( HighlightChangeEvent e ) {
        if ( !fParent.isSelectedView() ) {
            return;
        }
        
        MModelElement elem = e.getModelElement();
        List<EdgeBase> edges = new ArrayList<EdgeBase>();
        boolean allEdgesSelected = true;
   
        // elem is an association
        if ( elem != null && elem instanceof MAssociation ) {
            int size = ((MAssociation) elem).associationEnds().size();
            Set<MLink> links = 
                fParent.system().state().linksOfAssociation( (MAssociation) elem ).links();
            EdgeBase eb = null;
            if ( size == 2 ) {
                for (MLink link : links) {
                    eb = visibleData.fBinaryLinkToEdgeMap.get( link );
                    if ( elem instanceof MAssociationClass ) {
                        eb = visibleData.fLinkObjectToNodeEdge.get( (MLinkObject) link );
                    }
                    edges.add( eb );
                }
            } else {
                for (MLink link : links) {
                    for (MLinkEnd lEnd : link.linkEnds()) {
                        eb = (EdgeBase) visibleData.fHalfLinkToEdgeMap.get( lEnd );
                        edges.add( eb );
                    }
                    if ( elem instanceof MAssociationClass ) {
                        eb = (EdgeBase) visibleData.fLinkObjectToNodeEdge.get( (MLinkObject) link );
                        if ( !edges.contains( eb ) ) {
                            edges.add( eb );        
                        }
                    }
                }
            }
             
            // check all edges in the list if they are suppose to be selected 
            // or deselected.
            for (EdgeBase edge : edges) {
                if ( edge != null ) {
                    if ( e.getHighlight() ) {
                        fEdgeSelection.add( edge );
                        allEdgesSelected = true;
                    } else {
                        fEdgeSelection.remove( edge );
                        allEdgesSelected = false;
                    }  
                }    
            }
        }        

        // elem is a class
        if ( elem != null && elem instanceof MClass ) {
            for (MObject obj : fParent.system().state().objectsOfClass( (MClass) elem )) {
                NodeBase node = visibleData.fObjectToNodeMap.get( obj );
                if ( elem instanceof MAssociationClass ) {
                    if ( e.getHighlight() && allEdgesSelected ) {
                        fNodeSelection.add( node );
                    } else {
                        fNodeSelection.remove( node );
                    }  
                } else {
                    if ( e.getHighlight() ) { 
                        fNodeSelection.add( node );
                    } else {
                        fNodeSelection.remove( node );
                    }
                } 
            }
        }
        
        invalidateContent();
    }

    /**
     * Shows all hidden elements again
     */
    public void showAll() {
    	while (!hiddenData.fObjectToNodeMap.isEmpty()) {
    		showObject(hiddenData.fObjectToNodeMap.keySet().iterator().next());
    	}
    }
    
    /**
     * Hides all currently visible elements
     */
    public void hideAll() {
    	while (!visibleData.fObjectToNodeMap.isEmpty()) {
    		hideObject(visibleData.fObjectToNodeMap.keySet().iterator().next());
    	}
    }
    
    /**
     * Adds an object to the diagram.
     */
    public void addObject(MObject obj) {
    	ObjectNode n = new ObjectNode( obj, fParent, fOpt);
		n.setPosition( nextNodePosition );
        n.setMinWidth(minClassNodeWidth);
        n.setMinHeight(minClassNodeHeight);
        
        getRandomNextPosition();
        		
        synchronized (fLock) {
            fGraph.add(n);
            visibleData.fObjectToNodeMap.put(obj, n);
            fLayouter = null;
        }
    }

    /**
     * Show all objects contained in <code>objects</code>
	 * @param objects
	 */
	public void showObjects(Set<MObject> objects) {
		for (MObject o : objects) {
			showObject(o);
		}
	}
	
    /**
     * Shows an already hidden object again
     * @param obj The object to show
     */
    public void showObject( MObject obj ) {
    	if (visibleData.fObjectToNodeMap.containsKey(obj)) return;
    	
    	showOrHideObjectNode(obj, true);
    	
    	boolean isLinkObject = false;
    	
    	Set<MAssociation> assocs = obj.cls().allAssociations();
    	if (obj instanceof MLinkObject) {
    		assocs.add((MAssociation)obj.cls());
    		isLinkObject = true;
    	}
    	
    	// Show all links the object participates in,
    	// when all other objects are visible, too.
    	for (MAssociation assoc : assocs) {
    		MLinkSet links = fParent.system().state().linksOfAssociation(assoc);
    		// TODO: Not very fast!
    		for (MLink link : links.links()) {
    			if (link.linkedObjects().contains(obj) || (isLinkObject && link.equals(obj))) {
    				boolean allVisible = true;
    				for (MObject linkedO : link.linkedObjects()) {
    					if (!visibleData.fObjectToNodeMap.containsKey(linkedO)) {
    						allVisible = false;
    						break;
    					}
    				}
    				
    				if (allVisible && (!isLinkObject || visibleData.fObjectToNodeMap.containsKey(obj)))
    					showLink(link);
    			}
    		}
    	}
    }
    
    /**
	 * Hides all objects included in <code>objects</code>
	 * @param objects
	 */
	public void hideObjects(Set<MObject> objects) {
		for (MObject o : objects)
			hideObject(o);
	}
	
    /**
     * Hides an object in the diagram
     * @param obj The <code>MObject</code> to hide
     */
    public void hideObject( MObject obj ) {
    	showOrHideObjectNode(obj, false);
    	
    	// Hide all links the object participates in
    	Set<MAssociation> assocs = obj.cls().allAssociations();
    	
    	if (obj instanceof MLinkObject) {
    		assocs.add((MAssociation)obj.cls());
    	}
    	
    	for (MAssociation assoc : assocs) {
    		MLinkSet links = fParent.system().state().linksOfAssociation(assoc);
    		// TODO: Not very fast!
    		for (MLink link : links.links()) {
    			if (link.linkedObjects().contains(obj) || (link instanceof MLinkObject && ((MLinkObject)link).equals(obj))) {
    				hideLink(link);
    			}
    		}
    	}
    }
    
    /**
     * Shows an already hidden class.
     */
    protected void showOrHideObjectNode( MObject obj, boolean show ) {
    	ObjectDiagramData source = (show ? hiddenData : visibleData);
    	ObjectDiagramData target = (show ? visibleData : hiddenData);
    	
    	ObjectNode n = source.fObjectToNodeMap.get( obj );
        
        if (n != null) {
        	synchronized ( fLock ) {
                if (show) 
                	fGraph.add( n );
                else
                	fGraph.remove(n);
                
                source.fObjectToNodeMap.remove( obj );
                target.fObjectToNodeMap.put(obj, n);
                
                fLayouter = null;
            }
        }
    }
    
	/**
     * Deletes an object from the diagram.
     */
    public void deleteObject(MObject obj) {
    	ObjectNode n;
    	boolean isVisible;
    	
    	if (visibleData.fObjectToNodeMap.containsKey(obj)) {
    		n = visibleData.fObjectToNodeMap.get(obj);
    		isVisible = true;
    	} else {
    		n = hiddenData.fObjectToNodeMap.get(obj);
    		isVisible = false;
    	}
    	
        if (n != null) {
        	if (isVisible) {
	        	synchronized (fLock) {
	                fGraph.remove(n);
	                visibleData.fObjectToNodeMap.remove(obj);
	                fLayouter = null;
	            }
        	} else {
        		hiddenData.fObjectToNodeMap.remove(obj);
        	}
        }
    }

    /**
     * Adds a link to the diagram.
     */
    public void addLink(MLink link) {
    	if (link.linkEnds().size() == 2) {
    		addBinaryLink(link);
    	} else {
    		addNAryLink(link);
    	}
    }
    
    protected void addBinaryLink(MLink link) {
    	MAssociation assoc = link.association();
	
		MLinkEnd linkEnd1 = link.linkEnd(assoc.associationEnds().get(0));
		MLinkEnd linkEnd2 = link.linkEnd(assoc.associationEnds().get(1));

        MObject obj1 = linkEnd1.object();
        MObject obj2 = linkEnd2.object();
        
        // object link
        if (link instanceof MLinkObject) {
            BinaryAssociationClassOrObject e = 
                new BinaryAssociationClassOrObject(
                			 visibleData.fObjectToNodeMap.get(obj1), 
                             visibleData.fObjectToNodeMap.get(obj2),
                             linkEnd1.associationEnd(), 
                             linkEnd2.associationEnd(),
                             visibleData.fObjectToNodeMap.get(link),
                             this, link );
            
            synchronized (fLock) {
                fGraph.addEdge(e);
                visibleData.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
                fLayouter = null;
            }
        } else {
            // binary link
			BinaryAssociationOrLinkEdge e = new BinaryAssociationOrLinkEdge(
					visibleData.fObjectToNodeMap.get(obj1), visibleData.fObjectToNodeMap.get(obj2),
					linkEnd1.associationEnd(), linkEnd2.associationEnd(),
					this, link);
			
            synchronized (fLock) {
                fGraph.addEdge(e);
                visibleData.fBinaryLinkToEdgeMap.put(link, e);
                fLayouter = null;
            }
        }
    }
    
    protected void addNAryLink(MLink link) {
        synchronized (fLock) {
            getRandomNextPosition();
            
            // n-ary link: create a diamond node and n edges to objects
            DiamondNode node = new DiamondNode( link, fOpt );
            node.setPosition( nextNodePosition );
            fGraph.add(node);
            
            // connected to an "object link"
            if (link instanceof MLinkObject) {
                NAryAssociationClassOrObjectEdge e = 
                    new NAryAssociationClassOrObjectEdge(
                    			 node,
                                 visibleData.fObjectToNodeMap.get(link),
                                 this, link.association(), true );
                synchronized (fLock) {
                    fGraph.addEdge(e);
                    visibleData.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
                    fLayouter = null;
                }
            }
            // connected to a "normal" link
            visibleData.fNaryLinkToDiamondNodeMap.put(link, node);
            List<EdgeBase> halfEdges = new ArrayList<EdgeBase>();
            
            for (MLinkEnd linkEnd : link.linkEnds()) {
                MObject obj = linkEnd.object();
                AssociationOrLinkPartEdge e = 
                    new AssociationOrLinkPartEdge( node, visibleData.fObjectToNodeMap.get(obj), linkEnd.associationEnd(), this, link.association(), true );
                
                fGraph.addEdge(e);
                halfEdges.add( e );
            }
            if ( visibleData.fLinkObjectToNodeEdge.get( link ) != null ) {
                halfEdges.add( visibleData.fLinkObjectToNodeEdge.get( link ) );
            }
            node.setHalfEdges( halfEdges );
            visibleData.fHalfLinkToEdgeMap.put( link, halfEdges );
            fLayouter = null;
        }
    }

    public void showLink (MLink link) {
    	if (visibleData.containsLink(link)) return;
    	
    	if (link instanceof MLinkObject) {
    		showObject((MObject)link);
    	}

    	if (visibleData.containsLink(link)) return;
    	
    	if (link.linkEnds().size() == 2) {
    		showBinaryLink(link);
    	} else {
    		showNAryLink(link);
    	}
    }
    
    public void hideLink (MLink link) {
    	if (hiddenData.containsLink(link)) return;
    	
    	if (link.linkEnds().size() == 2) {
    		hideBinaryLink(link);
    	} else {
    		hideNAryLink(link);
    	}
    	
    	if (link instanceof MLinkObject && visibleData.fObjectToNodeMap.containsKey(link)) {
    		hideObject((MObject)link);
    	}
    }

    protected void showBinaryLink(MLink link) {
    	showOrHideBinaryLink(link, true);
    }
    
    protected void showNAryLink(MLink link) {
    	showOrHideNAryLink(link, true);
    }
    
    protected void hideBinaryLink(MLink link) {
    	showOrHideBinaryLink(link, false);
    }
    
    protected void hideNAryLink(MLink link) {
    	showOrHideNAryLink(link, false);
    }
    
    protected void showOrHideBinaryLink (MLink link, boolean show) {
    	ObjectDiagramData source = (show ? hiddenData : visibleData);
    	ObjectDiagramData target = (show ? visibleData : hiddenData);
        
        // object link
        if (link instanceof MLinkObject) {
        	EdgeBase e = source.fLinkObjectToNodeEdge.get(link);
            synchronized (fLock) {
                if (show)
                	fGraph.addEdge(e);
                else
                	fGraph.removeEdge(e);
                
                source.fLinkObjectToNodeEdge.remove((MLinkObject)link);
                target.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
                fLayouter = null;
            }
        } else {
            // binary link
			BinaryAssociationOrLinkEdge e = source.fBinaryLinkToEdgeMap.get(link);
			
            synchronized (fLock) {
                if (show)
                	fGraph.addEdge(e);
                else
                	fGraph.removeEdge(e);
                
                source.fBinaryLinkToEdgeMap.remove(link);
                target.fBinaryLinkToEdgeMap.put(link, e);
                fLayouter = null;
            }
        }
    }

    protected void showOrHideNAryLink(MLink link, boolean show) {
    	ObjectDiagramData source = (show ? hiddenData : visibleData);
    	ObjectDiagramData target = (show ? visibleData : hiddenData);
    	
    	
    	synchronized (fLock) {
    		DiamondNode node = source.fNaryLinkToDiamondNodeMap.get(link);
    		source.fNaryLinkToDiamondNodeMap.remove(link);
    		target.fNaryLinkToDiamondNodeMap.put(link, node);
    		
    		if (show)
    			fGraph.add(node);
    		else
    			fGraph.remove(node);
            
            // connected to an "object link"
            if (link instanceof MLinkObject) {                
            	EdgeBase e = source.fLinkObjectToNodeEdge.get(link);
                
                if (e != null) {
	                synchronized (fLock) {
	                    if (show)
	                    	fGraph.addEdge(e);
	                    else
	                    	fGraph.removeEdge(e);
	                    
	                    source.fLinkObjectToNodeEdge.remove(link);
	                    target.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
	                    fLayouter = null;
	                }
                }
            }
            
            List<EdgeBase> halfEdges = source.fHalfLinkToEdgeMap.get(link);
            
            if (halfEdges != null) {
	            for (EdgeBase edge : halfEdges) {
	                if (show)
	                	fGraph.addEdge(edge);
	                else
	                	fGraph.removeEdge(edge);
	            }
            
	            source.fHalfLinkToEdgeMap.remove( link );
	            target.fHalfLinkToEdgeMap.put( link, halfEdges );
            }
	        fLayouter = null;
        }
    }
    
    /**
     * Removes a link from the diagram.
     */
    public void deleteLink(MLink link, boolean loadingLayout ) {
        if (link.linkEnds().size() == 2) {
            EdgeBase e = null;
            if (link instanceof MObject) { // MLinkObject ???
                e = visibleData.fLinkObjectToNodeEdge.get(link);
                // TODO: this is just a tempory solution
                if (e == null) {
                    return;
                }
            } else {
                e = (BinaryAssociationOrLinkEdge) visibleData.fBinaryLinkToEdgeMap.get(link);
            }

            if ( e != null && !loadingLayout 
                 && !(link instanceof MLinkObject )
                 && hiddenData.fBinaryLinkToEdgeMap.containsKey(link) ) {
            	hiddenData.fBinaryLinkToEdgeMap.remove(link);
            }
            
            if (e == null) {
                throw new RuntimeException( "no edge for link `" + link
                                            + "' in current state." );
            }

            synchronized (fLock) {
                fGraph.removeEdge(e);
                if (link instanceof MObject) {
                    visibleData.fLinkObjectToNodeEdge.remove(link);
                } else {
                    visibleData.fBinaryLinkToEdgeMap.remove(link);
                }
                fLayouter = null;
            }
        } else {
            DiamondNode n = (DiamondNode) visibleData.fNaryLinkToDiamondNodeMap.get( link );
            if ( n == null && !loadingLayout ) {
                if ( hiddenData.fNaryLinkToDiamondNodeMap.containsKey(link) ) {
                	hiddenData.fNaryLinkToDiamondNodeMap.remove(link);
                } else {
                    throw new RuntimeException(
                            "no diamond node for n-ary link `" + link
                            + "' in current state.");
                }
            }

            synchronized (fLock) {
                // all dangling HalfLinkEdges are removed by the graph
                fGraph.remove(n);
                visibleData.fNaryLinkToDiamondNodeMap.remove(link);
                visibleData.fHalfLinkToEdgeMap.remove( link );
                fLayouter = null;
            }

            synchronized (fLock) {
                if (link instanceof MObject) {
                    BinaryAssociationClassOrObject edge = 
                        (BinaryAssociationClassOrObject) visibleData.fLinkObjectToNodeEdge.get( link );
                    if (edge != null) {
                        fGraph.removeEdge( edge );
                        visibleData.fLinkObjectToNodeEdge.remove( link );
                    }
                }
            }
        }
    }

    /**
     * Adds a new Link to the objectdiagram.
     */
    class ActionInsertLink extends AbstractAction {
        private MAssociation fAssociation;
    	private MObject[] fParticipants;
        
        ActionInsertLink(MAssociation association, MObject[] participants) {
        	fAssociation = association;
            fParticipants = participants;

        	StringBuilder txt = new StringBuilder("insert (");
        	StringUtil.fmtSeq(txt, participants, ",");
            txt.append(") into ")
               .append(association.name());
            
            putValue(Action.NAME, txt);
        }

        public void actionPerformed(ActionEvent e) {
            fParent.insertLink(fAssociation, fParticipants);
        }
    }

    /**
     * Deletes a Link from the object diagram.
     */
    class ActionDeleteLink extends AbstractAction {
    	private MAssociation fAssociation;
    	private MObject[] fParticipants;

        ActionDeleteLink(MAssociation association, MObject[] participants) {
            fAssociation = association;
            fParticipants = participants;
        	
            String txt = "delete (";
            for (int i = 0; i < participants.length; ++i) {
                if (i > 0) {
                	txt = txt + ",";
                }
                txt = txt + participants[i].name();
            }
            txt = txt + ") from " + association.name();
            
            putValue(Action.NAME, txt);
        }

        public void actionPerformed(ActionEvent e) {
            fParent.deleteLink(fAssociation, fParticipants);
            fEdgeSelection.clear();
        }
    }
    
    /**
     * Deletes the selected objects.
     */
    class ActionDelete extends AbstractAction {
        private Set<MObject> fObjects;

        ActionDelete(String text, Set<MObject> objects) {
            super(text);
            fObjects = objects;
        }

        public void actionPerformed(ActionEvent e) {
            fParent.deleteObjects(fObjects);
            fNodeSelection.clear();
        }
    }

    /**
     * Show properties of objects 
     */
    class ActionShowProperties extends AbstractAction {
        private MObject fObject;

        ActionShowProperties(String text, MObject object) {
            super(text);
            fObject = object;
        }

        public void actionPerformed(ActionEvent e) {
            ObjectPropertiesView v = 
                MainWindow.instance().showObjectPropertiesView();
            v.selectObject(fObject.name());
        }
    }

    private class ShowObjectPropertiesViewMouseListener extends MouseAdapter {

		@Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount()==2) { 
                // mouse over node?
                PlaceableNode pickedObjectNode = findNode( e.getX(),e.getY());
                if (pickedObjectNode instanceof ObjectNode) {
                    ObjectNode obj = (ObjectNode)pickedObjectNode;
                    ObjectPropertiesView v = 
                        MainWindow.instance().showObjectPropertiesView();
                    v.selectObject(obj.object().name());
                }
            }           
        }        
    }
    
    /**
     * Hides all elements included in <code>objectsToHide</code> in this diagram.
     * @param objectsToHide A set of {@link MObject}s to hide.
     */
    public void hideElementsInDiagram( Set<MObject> objectsToHide ) {
        Iterator<?> it = objectsToHide.iterator();
        while ( it.hasNext() ) {
            MObject obj = (MObject) it.next();
            hideObject( obj );
        }
    }
    
   /**
     * Creates and shows popup menu if mouse event is the trigger for popups.
     */
    public boolean maybeShowPopup(MouseEvent e) {
        boolean separatorNeeded = false;

        if (!e.isPopupTrigger())
            return false;

        // create the popup menu
        JPopupMenu popupMenu = unionOfPopUpMenu();

        // position for the popupMenu items 
        int pos = 0;
        HashSet<MObject> selectedObjectsOfAssociation = new HashSet<MObject>(); //jj
		HashSet<AssociationName> anames = new HashSet<AssociationName>(); // jj
        
        // get all associations that exist between the classes of the
        // selected objects
        if (!fNodeSelection.isEmpty()) {
            List<MObject> selectedObjects = new ArrayList<MObject>();
            
            for (PlaceableNode node : fNodeSelection) {
                if (node instanceof ObjectNode && node.isDeletable()) 
                    selectedObjects.add(((ObjectNode) node).object());
                else if (node instanceof AssociationName) { // anfangs jj 
                	selectedObjectsOfAssociation.addAll(fSelection.getSelectedObjectsofAssociation(((AssociationName)node)
							,selectedObjectsOfAssociation)); // end jj
                	anames.add((AssociationName)node);
                } 
            }
            
            final MObject[] selectedObjs = (MObject[])selectedObjects.toArray(new MObject[0]);
            if (selectedObjs.length==1) {
                popupMenu.insert( new ActionShowProperties("Edit properties of " + 
                		selectedObjects.get(0).name(), selectedObjects.get(0)), pos++);
            }
            int m = selectedObjs.length;
            boolean addedInsertLinkAction = false;
            
            for (MAssociation assoc : fParent.system().model().associations()) {
                if (assoc.isReadOnly()) continue;
                
            	int n = assoc.associationEnds().size();
                // More objects then ends selected
            	if (m > n) continue;
                
                int pow = 1;
                for(int i=0; i < n; ++i) 
                	pow *= m;
                
                for(int i=0;i<pow;++i) {
                    MObject[] l = new MObject[n];
                    MClass[] c = new MClass[n];
                    int[] digits = radixConversion(i,m,n);
                    
                    if (!isCompleteObjectCombination(digits, m)) 
                    	continue;
                    
                    for(int j=0;j<n;++j) {
                        l[j] = selectedObjs[digits[j]];
                        c[j] = l[j].cls();
                    }
                    
                    if (!assoc.isAssignableFrom(c))
                    	continue;
                    
                    if (!fParent.hasLinkBetweenObjects(assoc, l)) {
                        popupMenu.insert(new ActionInsertLink(assoc, l), pos++ );
                        addedInsertLinkAction = true;
                    } else {
                        popupMenu.insert(new ActionDeleteLink(assoc, l),pos++);
                        addedInsertLinkAction = true;
                    }
                }
               
            }

            if ( addedInsertLinkAction )
                popupMenu.insert( new JSeparator(), pos++ );
            
//          anfangs jj
            if(selectedObjects.size()<=0 && selectedObjectsOfAssociation.size() > 0){ 
				String info;
				if(anames.size() == 1){
					info =((AssociationName) anames.iterator().next()).name();
				}
				else{
					info = "" + anames.size();
				}
				popupMenu.insert(getAction("Hide link " + info + "",
						selectedObjectsOfAssociation), pos++);  //fixxx
				popupMenu.insert(getAction("Crop link " + info, 
						         getNoneSelectedNodes(selectedObjectsOfAssociation)), pos++);
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedLinkPathView("Selection by path length...", selectedObjectsOfAssociation, anames), pos++);
				popupMenu.insert(new JSeparator(),pos++);
				
			} 
            // end jj


            String txt = null;
            if (selectedObjects.size() == 1) {
                txt = "'" + selectedObjects.get(0).name() + "'";
            } else if (selectedObjects.size() > 1) {
                txt = selectedObjects.size() + " objects";
            }
            Set<MObject> selectedObjectsSet = new HashSet<MObject>(selectedObjects);
            if (txt != null && txt.length() > 0) {
                popupMenu.insert( new ActionDelete("Delete " + txt, 
                                                   selectedObjectsSet),
                                                   pos++ );
                popupMenu.insert( getAction("Hide " + txt,
                                                       selectedObjectsSet),
                                                       pos++ );
                popupMenu.insert( getAction("Crop " + txt,
                                                       getNoneSelectedNodes( selectedObjectsSet )),
                                                       pos++ );
                separatorNeeded = true;
            	popupMenu.insert(new JSeparator(), pos++); // anfangs jj
				popupMenu.insert(fSelection.getSelectedObjectPathView("Selection "
						+ txt + " path length...", selectedObjectsSet), pos++); // end jj
            }
        }

        if (!hiddenData.fObjectToNodeMap.isEmpty()) {
            final JMenuItem showAllObjects = new JMenuItem("Show hidden objects");
            showAllObjects.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    showAll();
                    invalidateContent();
                }
            });

            popupMenu.insert( showAllObjects, pos++ );
            separatorNeeded = true;
        }

        if (separatorNeeded) {
            popupMenu.insert( new JSeparator(), pos++ );
            separatorNeeded = false;
        }
        
        // anfangs jj
        if (fGraph.size() > 0 || !hiddenData.fObjectToNodeMap.isEmpty()) { 
			popupMenu.addSeparator();			

			if (fGraph.size() > 0) {
				popupMenu.add(fSelection.getSubMenuHideObject());
			}
			
			if (!hiddenData.fObjectToNodeMap.isEmpty()) {
				popupMenu.add(fSelection.getSubMenuShowObject());
			}
		}
		// jj end this

		popupMenu.addSeparator();
		popupMenu.add(fSelection.getSelectionOCLView("Selection with OCL...")); // end jj

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
        return true;
    }
    
    /**
     * Finds all nodes which are not selected.
     * @param selectedNodes Nodes which are selected at this point in the diagram.
     * @return A HashSet of the none selected objects in the diagram.
     */
    private Set<MObject> getNoneSelectedNodes( Set<MObject> selectedNodes ) {
        Set<MObject> noneSelectedNodes = new HashSet<MObject>();
        
        Iterator<NodeBase> it = fGraph.iterator();
        while ( it.hasNext() ) {
        	NodeBase o = it.next();
            if ( o instanceof ObjectNode ) {
                MObject obj = ((ObjectNode) o).object();
                if ( !selectedNodes.contains( obj ) ) {
                    noneSelectedNodes.add( obj );
                }    
            }
        }
        return noneSelectedNodes;
    }
    

    private JWindow fObjectInfoWin = null;

    private void displayObjectInfo(MObject obj, MouseEvent e) {
        if (fObjectInfoWin != null) {
            fObjectInfoWin.setVisible(false);
            fObjectInfoWin.dispose();
        }

        Box attrBox = Box.createVerticalBox();
        Box valueBox = Box.createVerticalBox();

        MObjectState objState = obj.state(fParent.system().state());
        Map<MAttribute, Value> attributeValueMap = objState.attributeValueMap();

        for (Map.Entry<MAttribute, Value> entry : attributeValueMap.entrySet()) {
            MAttribute attr = entry.getKey();
            Value v = entry.getValue();
            attrBox.add(new JLabel(attr.name() + " = "));
            valueBox.add(new JLabel(v.toString()));
        }

        fObjectInfoWin = new JWindow();
        JPanel contentPane = new JPanel();

        Border border = BorderFactory.createCompoundBorder(BorderFactory
                .createRaisedBevelBorder(), BorderFactory
                .createLoweredBevelBorder());
        contentPane.setBorder(border);
        Box b = Box.createHorizontalBox();
        b.add(attrBox);
        b.add(valueBox);
        contentPane.add(b);
        fObjectInfoWin.setContentPane(contentPane);
        Point p = e.getPoint();
        SwingUtilities.convertPointToScreen(p, (JComponent) e.getSource());

        fObjectInfoWin.setLocation(p);//e.getPoint());
        fObjectInfoWin.pack();
        fObjectInfoWin.setVisible(true);
    }
    
    
    /**
     * 
     * Accepts a drag of a class from the ModelBrowser. A new object of this
     * class will be created.
     * @param dtde
     */
    public void dropObjectFromModelBrowser( DropTargetDropEvent dtde ) {

        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            Transferable transferable = dtde.getTransferable();

            // we accept only Strings
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s = (String) transferable
                        .getTransferData(DataFlavor.stringFlavor);

                if (s.startsWith("CLASS-")) {
                    Point p = dtde.getLocation();
                    if ( isDoAutoLayout() ) {
                        getRandomNextPosition();
                    } else {                    
                        nextNodePosition.x = p.getX();
                        nextNodePosition.y = p.getY();
                    }
                    String clsName = s.substring(6);
                    fParent.createObject(clsName);
                }
            }
            dtde.dropComplete(true);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.err.println("Exception" + exception.getMessage());
            dtde.dropComplete(false);
        } catch (UnsupportedFlavorException ufException) {
            ufException.printStackTrace();
            System.err.println("Exception" + ufException.getMessage());
            dtde.dropComplete(false);
        }
    }
    
    
    /**
     * Checks if the object info window should be displayed.
     * @param e MouseEvent
     */
    public void mayBeShowObjectInfo( MouseEvent e ) {
        if (fNodeSelection.size() == 1) {
            PlaceableNode node = (PlaceableNode) fNodeSelection.iterator().next();
            if (node instanceof ObjectNode) {
                displayObjectInfo(((ObjectNode) node).object(), e);
            }
        }
    }
    
    /**
     * Checks if the object info window should be disposed.
     */
    public void mayBeDisposeObjectInfo() {
        if (fObjectInfoWin != null) {
            fObjectInfoWin.setVisible(false);
            fObjectInfoWin.dispose();
            fObjectInfoWin = null;
        }
    }
    
    private int[] radixConversion(int number, int base, int maxDigits) {
        int[] res = new int[maxDigits];
        for (int i=0;i<maxDigits;++i) {
            res[i]=number % base;
            number=number / base;
        }
        return res;
    }

    private boolean isCompleteObjectCombination(int[] c, int base) {
        for(int i=0;i<base;++i) {
            boolean found=false;
            for(int j=0;j<c.length;++j) { 
                if (c[j]==i) found=true;
            }
            if (!found) return false;
        }
        return true;
    }
    
    /**
	 * Finds a random new position for the next
	 * object node.
	 */
	private void getRandomNextPosition() {
		// getWidth and getHeight return 0
        // if we are called on a new diagram.
		nextNodePosition.x = Math.random() * Math.max(100, getWidth() - 100);
		nextNodePosition.y = Math.random() * Math.max(100, getHeight() - 100);
	}
	
	@Override
	public void storePlacementInfos(PersistHelper helper, Element root) {
		storePlacementInfos(helper, root, true);
		storePlacementInfos(helper, root, false);
	}
	
	protected void storePlacementInfos(PersistHelper helper, Element root, boolean visible) {
		ObjectDiagramData data = (visible ? visibleData : hiddenData);
		
		for (ObjectNode n : data.fObjectToNodeMap.values()) {
			n.storePlacementInfo(helper, root, !visible);
		}
		
		for (NodeBase n : data.fNaryLinkToDiamondNodeMap.values()) {
			n.storePlacementInfo(helper, root, !visible);
		}
		
		for (EdgeBase e : data.fBinaryLinkToEdgeMap.values()) {
			e.storePlacementInfo(helper, root, !visible);
		}
		
		for (EdgeBase e : data.fLinkObjectToNodeEdge.values()) {
			e.storePlacementInfo(helper, root, !visible);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#restorePositionData(org.w3c.dom.Element)
	 */
	@Override
	public void restorePlacementInfos(PersistHelper helper, Element rootElement, int version) {
		Set<MObject> hiddenObjects = new HashSet<MObject>();
		
		// Restore object nodes
		NodeList elements = (NodeList) helper.evaluateXPathSave(rootElement,
				"./" + LayoutTags.NODE + "[@type='Object']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {
			Element nodeElement = (Element)elements.item(i);			
			String name = helper.getElementStringValue(nodeElement, "name");
			MObject obj = fParent.system().state().objectByName(name);
			// Could be deleted
			if (obj != null) {
				ObjectNode node = visibleData.fObjectToNodeMap.get(obj);
				node.restorePlacementInfo(helper, nodeElement, version);
				if (isHidden(helper, nodeElement, version)) hiddenObjects.add(obj);
			}
		}
				
		// Restore diamond nodes
		elements = (NodeList) helper.evaluateXPathSave(rootElement, "./"
				+ LayoutTags.NODE + "[@type='DiamondNode']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {
			Element nodeElement = (Element)elements.item(i);
			String name = helper.getElementStringValue(nodeElement, "name");
			MAssociation assoc = fParent.system().model().getAssociation(name);
			
			// Get connected objects
			NodeList objectElements = helper.getChildElementsByTagName(nodeElement, "connectedNode");
			List<MObject> connectedObjects = new LinkedList<MObject>();
			
			for (int iNode = 0; iNode < objectElements.getLength(); ++iNode) {
				Element objectElement = (Element)objectElements.item(iNode);
				String objectName = objectElement.getFirstChild().getNodeValue();
				MObject obj = fParent.system().state().objectByName(objectName);
				if (obj != null) {
					connectedObjects.add(obj);
				}
			}
			
			// Deleted
			if (assoc.associationEnds().size() != connectedObjects.size())
				continue;
			// n-ary links cannot be qualified therefore an empty list for the qualifer values is provided
			MLink link = fParent.system().state().linkBetweenObjects(assoc, connectedObjects, Collections.<List<Value>>emptyList());
			
			// Could be deleted
			if (link != null) {
				DiamondNode node = visibleData.fNaryLinkToDiamondNodeMap.get(link);
				node.restorePlacementInfo(helper, nodeElement, version);
			}   
		}
				
		// Restore edges
		elements = (NodeList) helper.evaluateXPathSave(rootElement, "./"
				+ LayoutTags.EDGE + "[@type='BinaryEdge']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {
			Element edgeElement = (Element)elements.item(i);
			
			String name = helper.getElementStringValue(edgeElement, "name");
			MAssociation assoc = fParent.system().model().getAssociation(name);
			String sourceObjectName = helper.getElementStringValue(edgeElement, "source");
			String targetObjectName = helper.getElementStringValue(edgeElement, "target");
			
			MObject sourceObject = fParent.system().state().objectByName(sourceObjectName);
			MObject targetObject = fParent.system().state().objectByName(targetObjectName);
			
			// Could be deleted
			if (assoc != null && sourceObject != null && targetObject != null) {
				MLink link;
				
				if (assoc.hasQualifiedEnds()) {
					String linkValue = helper.getElementStringValue(edgeElement, "linkValue");
					link = getLinkByValue(assoc, Arrays.asList(sourceObject, targetObject), linkValue);
				} else {
					// No qualifier values are present. 
					link = fParent
							.system()
							.state()
							.linkBetweenObjects(assoc,
									Arrays.asList(sourceObject, targetObject),
									Collections.<List<Value>>emptyList());
				}
				
				if (link != null) {
					BinaryAssociationOrLinkEdge edge = visibleData.fBinaryLinkToEdgeMap.get(link);
					edge.restorePlacementInfo(helper, edgeElement, version);
				}
			}
		}
		
		// Restore edges
		elements = (NodeList) helper.evaluateXPathSave(rootElement, "./"
				+ LayoutTags.EDGE + "[@type='NodeEdge']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {
			Element edgeElement = (Element)elements.item(i);
			String name = helper.getElementStringValue(edgeElement, "name");
			MAssociation assoc = fParent.system().model().getAssociation(name);
			String sourceObjectName = helper.getElementStringValue(edgeElement, "source");
			String targetObjectName = helper.getElementStringValue(edgeElement, "target");
			
			MObject sourceObject = fParent.system().state().objectByName(sourceObjectName);
			MObject targetObject = fParent.system().state().objectByName(targetObjectName);
			
			// Could be deleted
			if (assoc != null && sourceObject != null && targetObject != null) {
				MLink link;
				
				if (assoc.hasQualifiedEnds()) {
					String linkValue = helper.getElementStringValue(edgeElement, "linkValue");
					link = getLinkByValue(assoc, Arrays.asList(sourceObject, targetObject), linkValue);
				} else {
					// No qualifier values are present. 
					link = fParent
							.system()
							.state()
							.linkBetweenObjects(assoc,
									Arrays.asList(sourceObject, targetObject),
									Collections.<List<Value>> emptyList());
				}
				
				if (link != null) {
					BinaryAssociationClassOrObject edge = (BinaryAssociationClassOrObject)visibleData.fLinkObjectToNodeEdge.get(link);
					edge.restorePlacementInfo(helper, edgeElement, version);
				}
			}
		}
		
		// Hide elements
		hideElementsInDiagram(hiddenObjects);
	}

	protected MLink getLinkByValue(MAssociation assoc, List<MObject> objects, String linkValue) {
		Set<MLink> links = fParent.system().state().linkBetweenObjects(assoc, objects);
		if (links.size() == 1) {
			return links.iterator().next(); 
		} else {
			for (MLink aLink : links) {
				if (aLink.toString().equals(linkValue)) {
					return aLink;
				}
			}
		}
		return null;
	}
	protected boolean isHidden(PersistHelper helper, Element element, int version) {
		return helper.getElementBooleanValue(element, LayoutTags.HIDDEN);
	}
	
	public ActionHideObjectDiagram getAction( String text, Set<MObject> selectedNodes ) {
        return new ActionHideObjectDiagram( text, selectedNodes, fNodeSelection, fGraph, this );
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#getHiddenNodes()
	 */
	@Override
	public Set<? extends NodeBase> getHiddenNodes() {
		return new HashSet<ObjectNode>(hiddenData.fObjectToNodeMap.values());
	}
}
