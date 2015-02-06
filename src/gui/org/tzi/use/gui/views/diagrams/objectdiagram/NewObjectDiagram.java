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

import static org.tzi.use.util.collections.CollectionUtil.exactlyOne;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.diagrams.elements.AssociationName;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.edges.LinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.NAryAssociationClassOrObjectEdge;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
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
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MLinkSet;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.StringUtil.IElementFormatter;
import org.w3c.dom.Element;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;


/**
 * A panel drawing UML object diagrams.
 * 
 * @author Mark Richters
 * @author Lars Hamann
 */
@SuppressWarnings("serial")
public class NewObjectDiagram extends DiagramViewWithObjectNode 
                              implements HighlightChangeListener, SortChangeListener {

    /**
     * This class saves needed information about
     * visible or hidden nodes and edges.
     * When hiding or showing objects the data is
     * moved between the fields {@link NewObjectDiagram#visibleData} and
     * {@link NewObjectDiagram#hiddenData}. 
     * 
     * @author Lars Hamann
     */
	public static class ObjectDiagramData implements DiagramData {
		/**
		 * Map from object instances to object nodes.
		 * It also includes link objects.
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

		@Override
		public Set<PlaceableNode> getNodes() {
			Set<PlaceableNode> result = new HashSet<PlaceableNode>();
			
			result.addAll(this.fNaryLinkToDiamondNodeMap.values());
			result.addAll(this.fObjectToNodeMap.values());
			
			return result;
		}

		@Override
		public boolean hasNodes() {
			return !(fNaryLinkToDiamondNodeMap.isEmpty() && fObjectToNodeMap.isEmpty());
		}

		@Override
		public Set<EdgeBase> getEdges() {
			Set<EdgeBase> result = new HashSet<EdgeBase>(fBinaryLinkToEdgeMap.values());
			result.addAll(fLinkObjectToNodeEdge.values());
			for (Map.Entry<MLink, List<EdgeBase>> entry : fHalfLinkToEdgeMap.entrySet()) {
				result.addAll(entry.getValue());
			}
			return result;
		}
				
		/**
		 * Copies all data to the target object
		 * @param hiddenData
		 */
		public void copyTo(ObjectDiagramData target) {
			 target.fBinaryLinkToEdgeMap.putAll(this.fBinaryLinkToEdgeMap);
			 target.fHalfLinkToEdgeMap.putAll(this.fHalfLinkToEdgeMap);
			 target.fLinkObjectToNodeEdge.putAll(this.fLinkObjectToNodeEdge);
			 target.fNaryLinkToDiamondNodeMap.putAll(this.fNaryLinkToDiamondNodeMap);
			 target.fObjectToNodeMap.putAll(this.fObjectToNodeMap);
		}
		
		/**
		 * Removes all data
		 */
		public void clear() {
			this.fBinaryLinkToEdgeMap.clear();
			this.fHalfLinkToEdgeMap.clear();
			this.fLinkObjectToNodeEdge.clear();
			this.fNaryLinkToDiamondNodeMap.clear();
			this.fObjectToNodeMap.clear();
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

	private ObjectSelection fSelection;
	
	private DiagramInputHandling inputHandling;

    /**
     * Creates a new empty diagram.
     */
    NewObjectDiagram(NewObjectDiagramView parent, PrintWriter log) {
    	this(parent, log, new ObjDiagramOptions());
    }

    NewObjectDiagram(NewObjectDiagramView parent, PrintWriter log, ObjDiagramOptions options) {
    	super(options, log);
    	this.getRandomNextPosition();
    	
        fParent = parent;
        //FIXME: Handle fonts!
        parent.setFont(getFont());

        fSelection = new ObjectSelection(this, parent.system());
        
        fActionSaveLayout = new ActionSaveLayout( "USE object diagram layout",
                                                  "olt", this );
        
        fActionLoadLayout = new ActionLoadLayout( "USE object diagram layout",
                                                  "olt", this);

        inputHandling = new DiagramInputHandling( fNodeSelection, fEdgeSelection, this);
        
        fParent.getModelBrowser().addHighlightChangeListener( this );
        ModelBrowserSorting.getInstance().addSortChangeListener( this );
        fParent.addKeyListener( inputHandling );
        
        
        addMouseListener(inputHandling);
        addMouseListener(showObjectPropertiesViewMouseListener);
                
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // need a new layouter to adapt to new window size
                fLayouter = null;
            }
        });

        startLayoutThread();
    }
    
    @Override
    public ObjDiagramOptions getOptions() {
    	return (ObjDiagramOptions)super.getOptions();
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
        	MAssociation assoc = (MAssociation) elem; 
            int size = assoc.associationEnds().size();
            
            Set<MLink> links = fParent.system().state().linksOfAssociation(assoc).links();
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
                    edges.addAll(visibleData.fHalfLinkToEdgeMap.get(link));
                    
                    if ( elem instanceof MAssociationClass ) {
                        eb = visibleData.fLinkObjectToNodeEdge.get(link);
                        edges.add( eb );        
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
            	PlaceableNode node = visibleData.fObjectToNodeMap.get( obj );
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
        
        invalidateContent(true);
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
     * Hides all currently visible elements.
     *The diagram is not repainted!
     */
    public void hideAll() {
    	// Copy all elements to hidden and remove all elements from graph
    	this.visibleData.copyTo(this.hiddenData);
    	this.visibleData.clear();
    	this.fGraph.clear();
    	this.invalidateContent(false);
    }
    
    /**
     * Adds an object to the diagram.
     */
    public void addObject(MObject obj) {
    	ObjectNode n = new ObjectNode( obj, fParent, getOptions());
		n.setPosition( nextNodePosition );
        n.setMinWidth(minClassNodeWidth);
        n.setMinHeight(minClassNodeHeight);
        
        getRandomNextPosition();

        fGraph.add(n);
        visibleData.fObjectToNodeMap.put(obj, n);
        fLayouter = null;
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
    	
    	this.invalidateContent(false);
    }
    
    /**
     * Shows an already hidden class.
     */
    protected void showOrHideObjectNode( MObject obj, boolean show ) {
    	ObjectDiagramData source = (show ? hiddenData : visibleData);
    	ObjectDiagramData target = (show ? visibleData : hiddenData);
    	
    	ObjectNode n = source.fObjectToNodeMap.get( obj );
        
        if (n != null) {
            if (show) 
            	fGraph.add( n );
            else
            	fGraph.remove(n);
            
            source.fObjectToNodeMap.remove( obj );
            target.fObjectToNodeMap.put(obj, n);
            
            fLayouter = null;
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
                fGraph.remove(n);
                visibleData.fObjectToNodeMap.remove(obj);
                fLayouter = null;
        	} else {
        		hiddenData.fObjectToNodeMap.remove(obj);
        	}
        	n.dispose();
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
        //TODO: Create link edge factory.
        
        // object link
        if (link instanceof MLinkObject) {
            BinaryAssociationClassOrObject e = 
                BinaryAssociationClassOrObject.create(
                			 visibleData.fObjectToNodeMap.get(obj1), 
                             visibleData.fObjectToNodeMap.get(obj2),
                             linkEnd1.associationEnd(), 
                             linkEnd2.associationEnd(),
                             visibleData.fObjectToNodeMap.get(link),
                             this, link );
            
        
            fGraph.addEdge(e);
            visibleData.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
            fLayouter = null;
        } else {
            // binary link
        	boolean isHidden = false;
        	ObjectNode node1;
        	ObjectNode node2;
        	
        	if (visibleData.fObjectToNodeMap.containsKey(obj1)) {
        		node1 = visibleData.fObjectToNodeMap.get(obj1);
        	} else {
        		node1 = hiddenData.fObjectToNodeMap.get(obj1);
        		isHidden = true;
        	}
        	
        	if (visibleData.fObjectToNodeMap.containsKey(obj2)) {
        		node2 = visibleData.fObjectToNodeMap.get(obj2);
        	} else {
        		node2 = hiddenData.fObjectToNodeMap.get(obj2);
        		isHidden = true;
        	}
        	
			BinaryAssociationOrLinkEdge e = BinaryAssociationOrLinkEdge.create(
					node1,
					node2,
					linkEnd1.associationEnd(), linkEnd2.associationEnd(), this,
					link);
			
			if (link.isVirtual()) {
				e.setDashed(true);
			}
			
			if (isHidden) {
				hiddenData.fBinaryLinkToEdgeMap.put(link, e);
			} else {
				fGraph.addEdge(e);
				visibleData.fBinaryLinkToEdgeMap.put(link, e);
				fLayouter = null;
			}
        }
    }
    
    protected void addNAryLink(MLink link) {        
        getRandomNextPosition();
        
        // n-ary link: create a diamond node and n edges to objects
        DiamondNode node = new DiamondNode( link, fOpt );
        node.setPosition( nextNodePosition );
        fGraph.add(node);
        
        // connected to an "object link"
        if (link instanceof MLinkObject) {
			NAryAssociationClassOrObjectEdge e = NAryAssociationClassOrObjectEdge
					.create(node, visibleData.fObjectToNodeMap.get(link),
							this, link.association(), true);

            fGraph.addEdge(e);
            visibleData.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
            fLayouter = null;
        }
        // connected to a "normal" link
        visibleData.fNaryLinkToDiamondNodeMap.put(link, node);
        List<EdgeBase> halfEdges = new ArrayList<>();
        List<String> edgeIds = new ArrayList<>();
        
        for (MLinkEnd linkEnd : link.linkEnds()) {
            MObject obj = linkEnd.object();
            AssociationOrLinkPartEdge e = 
                AssociationOrLinkPartEdge.create( node, visibleData.fObjectToNodeMap.get(obj), linkEnd.associationEnd(), this, link.association(), link );
            
            if (link.isVirtual()) {
				e.setDashed(true);
			}
            
            fGraph.addEdge(e);
            halfEdges.add( e );
            edgeIds.add(linkEnd.associationEnd().nameAsRolename());
        }
        
        if ( visibleData.fLinkObjectToNodeEdge.get( link ) != null ) {
            halfEdges.add( visibleData.fLinkObjectToNodeEdge.get( link ) );
            edgeIds.add(((MLinkObject)link).name());
        }
        
        node.setHalfEdges( halfEdges, edgeIds );
        
        visibleData.fHalfLinkToEdgeMap.put( link, halfEdges );
        fLayouter = null;
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
            if (show)
            	fGraph.addInitializedEdge(e);
            else
            	fGraph.removeEdge(e);
            
            source.fLinkObjectToNodeEdge.remove((MLinkObject)link);
            target.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
            fLayouter = null;
        } else {
            // binary link
			BinaryAssociationOrLinkEdge e = source.fBinaryLinkToEdgeMap.get(link);

            if (show)
            	fGraph.addEdge(e);
            else
            	fGraph.removeEdge(e);
            
            source.fBinaryLinkToEdgeMap.remove(link);
            target.fBinaryLinkToEdgeMap.put(link, e);
        }
    }

    protected void showOrHideNAryLink(MLink link, boolean show) {
    	ObjectDiagramData source = (show ? hiddenData : visibleData);
    	ObjectDiagramData target = (show ? visibleData : hiddenData);

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
                if (show)
                	fGraph.addEdge(e);
                else
                	fGraph.removeEdge(e);
                
                source.fLinkObjectToNodeEdge.remove(link);
                target.fLinkObjectToNodeEdge.put((MLinkObject)link, e);
                fLayouter = null;
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
    
    /**
     * Removes a link from the diagram.
     */
    public void deleteLink(MLink link) {
        if (link.linkEnds().size() == 2) {
            EdgeBase e = null;
            boolean isVisible;
            boolean isLinkObject = link instanceof MLinkObject; 
            ObjectDiagramData data;
            
            if (isLinkObject) {
            	isVisible = visibleData.fLinkObjectToNodeEdge.containsKey(link);
            	data = isVisible ? visibleData : hiddenData;
                e = data.fLinkObjectToNodeEdge.get(link);
            } else {
            	isVisible = visibleData.fBinaryLinkToEdgeMap.containsKey(link);
            	data = isVisible ? visibleData : hiddenData;
            	e = data.fBinaryLinkToEdgeMap.get(link);
            }

            if (e == null) {
                return;
            }
            
            if ( isLinkObject ) {
            	data.fBinaryLinkToEdgeMap.remove(link);
            	data.fLinkObjectToNodeEdge.remove(link);
            } else {
            	data.fBinaryLinkToEdgeMap.remove(link);
            }

            if (isVisible) {
        		fGraph.removeEdge(e);
        		fLayouter = null;
        	}
            e.dispose();
        } else { // n-ary association
        	boolean isVisible;
            ObjectDiagramData data;
            
            isVisible = visibleData.fNaryLinkToDiamondNodeMap.containsKey( link );
            data = isVisible ? visibleData : hiddenData;
        	DiamondNode n = data.fNaryLinkToDiamondNodeMap.get( link );
        	
            if ( n == null ) {
				throw new RuntimeException("no diamond node for n-ary link `"
						+ link + "' in current state.");
            }

            data.fNaryLinkToDiamondNodeMap.remove(link);
            data.fHalfLinkToEdgeMap.remove( link );

            if (isVisible) {
        		fGraph.remove(n);
        		fLayouter = null;
            }
            
            n.dispose();
            
            if (link instanceof MLinkObject) {
                EdgeBase edge = data.fLinkObjectToNodeEdge.get( link );
                if (edge != null) {
                	fGraph.removeEdge( edge );
                    data.fLinkObjectToNodeEdge.remove( link );
                    edge.dispose();
                }
            }
        }
    }

    /**
     * Forces the object node to update its content.
	 * @param obj
	 */
	public void updateObject(MObject obj) {
		ObjectNode node = visibleData.fObjectToNodeMap.get(obj);
		if (node != null) invalidateNode(node);
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
        	StringUtil.fmtSeq(txt, participants, ",", new IElementFormatter<MObject>() {
				@Override
				public String format(MObject element) {
					return element.name();
				} });
        	
            txt.append(") into ")
               .append(association.name());
            
            putValue(Action.NAME, txt.toString());
        }

        public void actionPerformed(ActionEvent e) {
            fParent.insertLink(fAssociation, fParticipants);
        }
    }

    /**
     * Deletes a Link from the object diagram.
     */
    class ActionDeleteLink extends AbstractAction {
    	private MLink link;

        ActionDeleteLink(MLink link) {
        	this.link = link;
            MObject[] participants = link.linkedObjectsAsArray();
            StringBuilder txt = new StringBuilder("delete (");
            
            for (int i = 0; i < participants.length; ++i) {
                if (i > 0) {
                	txt.append(",");
                }
                txt.append(participants[i].name());
                if (!link.getQualifier().isEmpty() && !link.getQualifier().get(i).isEmpty()) {
                	txt.append("{");
                	StringUtil.fmtSeq(txt, link.getQualifier().get(i), ",");
                	txt.append("}");
                }
            }
            txt.append(") from ").append(link.association().name());
            
            putValue(Action.NAME, txt.toString());
        }

        public void actionPerformed(ActionEvent e) {
            fParent.deleteLink(link);
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
     * Creates and shows popup menu if mouse event is the trigger for popups.
     */
    protected PopupMenuInfo unionOfPopUpMenu() {
        // create the popup menu
        PopupMenuInfo popupInfo = super.unionOfPopUpMenu(); 
        JPopupMenu popupMenu = popupInfo.popupMenu;

        // position for the popupMenu items 
        int pos = 0;
        final Set<MObject>  selectedObjectsOfAssociation = new HashSet<MObject>();
		final Set<MLink>    selectedLinks = new HashSet<MLink>();
		final List<MObject> selectedObjects = new ArrayList<MObject>();
		
		// Split selected nodes into model elements
		for (PlaceableNode node : fNodeSelection) {
            if (node instanceof ObjectNode ) {
                selectedObjects.add(((ObjectNode) node).object());
            } else if (node instanceof AssociationName) { 
				MLink link = ((AssociationName)node).getLink();
				selectedObjectsOfAssociation.addAll(link.linkedObjects());
				selectedLinks.add(link);
            } 
        }
		
		for (EdgeBase selectedEdge : fEdgeSelection) {
			if (selectedEdge instanceof LinkEdge) {
				LinkEdge aEdge = (LinkEdge)selectedEdge;
				MLink link = aEdge.getLink();
				selectedLinks.add(link);
				selectedObjectsOfAssociation.addAll(link.linkedObjects());
			}
		}
		
		// Just to be sure to delete an object only once 
		Set<MObject> selectedObjectsSet = new HashSet<MObject>(selectedObjects);
		
		// This text is reused often
		String selectedObjectsText = null;
        if (selectedObjects.size() == 1) {
        	selectedObjectsText = "'" + selectedObjects.get(0).name() + "'";
        } else if (selectedObjects.size() > 1) {
        	selectedObjectsText = selectedObjects.size() + " objects";
        }
                
        if (!selectedObjects.isEmpty()) {
        	// A single object can be edited
        	if (selectedObjects.size() == 1) {
        		popupMenu.insert( 
        				new ActionShowProperties("Edit properties of " + selectedObjectsText, selectedObjects.get(0)), pos++);
        	}
        	
        	// A single object or multiple objects can be deleted.
        	popupMenu.insert( new ActionDelete("Destroy " + selectedObjectsText, 
                selectedObjectsSet),
                pos++ );
        	popupMenu.insert(new JSeparator(), pos++);
        }
		
		
		// Possible links between objects 
        if (!selectedObjects.isEmpty()) {
            int m = selectedObjects.size();
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
                        l[j] = selectedObjects.get(digits[j]);
                        c[j] = l[j].cls();
                    }
                    
                    if (!assoc.isAssignableFrom(c))
                    	continue;
                    
                    Set<MLink> links = fParent.linksBetweenObjects(assoc, l); 
                    if (links.isEmpty() || assoc.hasQualifiedEnds()) {
                        popupMenu.insert(new ActionInsertLink(assoc, l), pos++ );
                        addedInsertLinkAction = true;
                    }
                    
                    if (!links.isEmpty()) {
                        for (MLink link : links) {
                        	popupMenu.insert(new ActionDeleteLink(link), pos++ );
                        	addedInsertLinkAction = true;
                        }
                    }
                }
               
            }

            if ( addedInsertLinkAction )
                popupMenu.insert( new JSeparator(), pos++ );
        }
        
        // Hide / crop / show links
        //TODO: Hide / crop / show links doesen't work!
        
        if (selectedObjects.isEmpty() && selectedObjectsOfAssociation.size() > 0 ) { 
			String info;
			
			if(selectedLinks.size() == 1) {
				info = selectedLinks.iterator().next().association().name();
			} else {
				info = "" + selectedLinks.size();
			}
			
			popupMenu.insert(new AbstractAction("Hide " + info) {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (MLink linkToHide : selectedLinks) {
						hideLink(linkToHide);
						repaint();
					}
				}
			}, pos++);

			popupMenu.insert(new JSeparator(),pos++);
		} 
        
        if (!selectedObjects.isEmpty()) {
            popupMenu.insert( getAction("Hide " + selectedObjectsText,
                                                  selectedObjectsSet),
                                                  pos++ );
            popupMenu.insert( getAction("Crop " + selectedObjectsText,
                                                  getNoneSelectedNodes( selectedObjectsSet )),
                                                  pos++ );
            popupMenu.insert(new JSeparator(), pos++);
        }
            
        final JMenu showHideCrop = new JMenu("Show/hide/crop objects");
        
        if (!selectedObjects.isEmpty()) {
        	showHideCrop.add(fSelection.getSelectedObjectPathView("By path length...", selectedObjectsSet));
        }
        
        showHideCrop.add(fSelection.getSelectionWithOCLViewAction());
        showHideCrop.add(fSelection.getSelectionObjectView());
		
        popupMenu.insert(showHideCrop, pos++);
                
        if (!hiddenData.fObjectToNodeMap.isEmpty()) {
            final JMenuItem showAllObjects = new JMenuItem("Show hidden objects");
            showAllObjects.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    showAll();
                    invalidateContent(true);
                }
            });

            popupMenu.insert( showAllObjects, pos++ );
        }
        
        if (fGraph.size() > 0 || !hiddenData.fObjectToNodeMap.isEmpty()) { 
			if (fGraph.size() > 0) {
				popupMenu.insert(fSelection.getSubMenuHideObject(), pos++);
			}
			
			if (!hiddenData.fObjectToNodeMap.isEmpty()) {
				popupMenu.insert(fSelection.getSubMenuShowObject(), pos++);
			}
		}

        popupMenu.insert(new JSeparator(), pos++);
    	
        if (!selectedObjects.isEmpty()) {
    		final JMenu showProtocolStateMachine = new JMenu( "Show protocol state machine..." );
            showProtocolStateMachine.setEnabled(false);
            popupMenu.insert(showProtocolStateMachine, pos++);
            
            if (selectedObjects.size() == 1) {
            	final MObject obj = exactlyOne(selectedObjects);
            	
            	List<MProtocolStateMachine> sortedPSMs = new LinkedList<MProtocolStateMachine>(
						obj.cls().getAllOwnedProtocolStateMachines());
				Collections.sort(sortedPSMs, new MNamedElementComparator());
        		
        		for (MProtocolStateMachine psm : sortedPSMs) {
        			showProtocolStateMachine.setEnabled(true);
        			final JMenuItem showGivenPSM = new JMenuItem( psm.name() );
        			showGivenPSM.addActionListener(new ActionListener() {
						protected MProtocolStateMachine sm;
						
        				public void actionPerformed(ActionEvent ev) {
							MainWindow.instance().showStateMachineView(sm, obj);
						}
        				
        				public ActionListener setStateMachine(MProtocolStateMachine sm, MObject instance) {
        					this.sm = sm;
        					return this;
        				}
					}.setStateMachine(psm, obj));
        			showProtocolStateMachine.add(showGivenPSM);
        		}
            }
            
            popupMenu.insert(new JSeparator(), pos++);
        }

        final JCheckBoxMenuItem showStates = new JCheckBoxMenuItem("Show states", getOptions().isShowStates());
        showStates.addItemListener(new ItemListener() {
            @Override
			public void itemStateChanged(ItemEvent ev) {
                getOptions().setShowStates(ev.getStateChange() == ItemEvent.SELECTED );
                invalidateContent(true);
            }
        });
        
        popupMenu.insert(showStates, pos + 3);
        
        return popupInfo;
    }
    
    /**
     * Finds all nodes which are not selected.
     * @param selectedNodes Nodes which are selected at this point in the diagram.
     * @return A HashSet of the none selected objects in the diagram.
     */
    private Set<MObject> getNoneSelectedNodes( Set<MObject> selectedNodes ) {
        Set<MObject> noneSelectedNodes = new HashSet<MObject>();
        
        Iterator<PlaceableNode> it = fGraph.iterator();
        while ( it.hasNext() ) {
        	PlaceableNode o = it.next();
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
	public void resetLayout() {
		fParent.initDiagram(false, (ObjDiagramOptions)fOpt);
		fParent.validate();
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
		
		for (PlaceableNode n : data.fNaryLinkToDiamondNodeMap.values()) {
			n.storePlacementInfo(helper, root, !visible);
		}
		
		for (EdgeBase e : data.fBinaryLinkToEdgeMap.values()) {
			e.storePlacementInfo(helper, root, !visible);
		}
		
		for (EdgeBase e : data.fLinkObjectToNodeEdge.values()) {
			e.storePlacementInfo(helper, root, !visible);
		}
	}

	@Override
	public void restorePlacementInfos(PersistHelper helper, int version) {
		if (version < 12) return;
		
		Set<MObject> hiddenObjects = new HashSet<MObject>();
		AutoPilot ap = new AutoPilot(helper.getNav());
		
		// First restore edges to get possible new nodes, then nodes
		helper.getNav().push();
		
		try {
			// Restore edges
			ap.selectXPath("./edge[@type='BinaryEdge']");
			
			try {
				while (ap.evalXPath() != -1) {			
					String name = helper.getElementStringValue("name");
					MAssociation assoc = fParent.system().model().getAssociation(name);
					String sourceObjectName = helper.getElementStringValue("source");
					String targetObjectName = helper.getElementStringValue("target");
					
					MObject sourceObject = fParent.system().state().objectByName(sourceObjectName);
					MObject targetObject = fParent.system().state().objectByName(targetObjectName);
					
					// Could be deleted
					if (assoc != null && sourceObject != null && targetObject != null) {
						MLink link;
						
						if (assoc.hasQualifiedEnds()) {
							String linkValue = helper.getElementStringValue("linkValue");
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
							edge.restorePlacementInfo(helper, version);
						}
					}
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
		ap.resetXPath();
		helper.getNav().pop();
		
		helper.getNav().push();
		try {
			// Restore edges
			ap.selectXPath("./edge[@type='NodeEdge']");
			
			try {
				while(ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					MAssociation assoc = fParent.system().model().getAssociation(name);
					String sourceObjectName = helper.getElementStringValue("source");
					String targetObjectName = helper.getElementStringValue("target");
					
					MObject sourceObject = fParent.system().state().objectByName(sourceObjectName);
					MObject targetObject = fParent.system().state().objectByName(targetObjectName);
					
					// Could be deleted
					if (assoc != null && sourceObject != null && targetObject != null) {
						MLink link;
						
						if (assoc.hasQualifiedEnds()) {
							String linkValue = helper.getElementStringValue("linkValue");
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
							edge.restorePlacementInfo(helper, version);
						}
					}
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
		helper.getNav().pop();
		ap.resetXPath();
				
		helper.getNav().push();
		try {
			ap.selectXPath("./node[@type='Object']");
		
			try {
				while(ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					MObject obj = fParent.system().state().objectByName(name);
					// Could be deleted
					if (obj != null) {
						ObjectNode node = visibleData.fObjectToNodeMap.get(obj);
						node.restorePlacementInfo(helper, version);
						if (isHidden(helper, version)) hiddenObjects.add(obj);
					}
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
		
		helper.getNav().pop();
		ap.resetXPath();
		
		helper.getNav().push();
		try {
			// Restore diamond nodes
			ap.selectXPath("./node[@type='DiamondNode']");
			
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					MAssociation assoc = fParent.system().model().getAssociation(name);

					// Renamed or deleted
					if (assoc == null) continue;
					
					// Get connected objects
					List<MObject> connectedObjects = new LinkedList<MObject>();
					if (!helper.toFirstChild("connectedNode"))
						break;
					
					String objectName = helper.getElementStringValue();
					MObject obj = fParent.system().state().objectByName(objectName);
					
					if (obj != null)
						connectedObjects.add(obj);
					
					while (helper.toNextSibling("connectedNode")) {
						objectName = helper.getElementStringValue();
						obj = fParent.system().state().objectByName(objectName);
						
						if (obj != null) {
							connectedObjects.add(obj);
						}
					}
					
					// Modified
					if (assoc.associationEnds().size() != connectedObjects.size())
						continue;
					
					// n-ary links cannot be qualified therefore an empty list for the qualifer values is provided
					MLink link = fParent.system().state().linkBetweenObjects(assoc, connectedObjects, Collections.<List<Value>>emptyList());
					
					// Could be deleted
					if (link != null) {
						DiamondNode node = visibleData.fNaryLinkToDiamondNodeMap.get(link);
						helper.toParent();
						node.restorePlacementInfo(helper, version);
					}   
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
		helper.getNav().pop();
		ap.resetXPath();
		
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
	protected boolean isHidden(PersistHelper helper, int version) {
		return helper.getElementBooleanValue(LayoutTags.HIDDEN);
	}

	@Override
	public Set<? extends PlaceableNode> getHiddenNodes() {
		return new HashSet<ObjectNode>(hiddenData.fObjectToNodeMap.values());
	}

	@Override
	public DiagramData getVisibleData() {
		return visibleData;
	}
	
	@Override
	public DiagramData getHiddenData() {
		return hiddenData;
	}

	@Override
	protected String getDefaultLayoutFileSuffix() {
		// No default layout
		return null;
	}
	
	@Override
	protected void onClosing() {
		super.onClosing();
		fParent.getModelBrowser().removeHighlightChangeListener( this );
		fParent.removeKeyListener( inputHandling );
		ModelBrowserSorting.getInstance().removeSortChangeListener( this );
	}

	@Override
	public void stateChanged(SortChangeEvent e) {
		for (ObjectNode n : this.visibleData.fObjectToNodeMap.values()) {
			n.stateChanged(e);
		}
	}
}
