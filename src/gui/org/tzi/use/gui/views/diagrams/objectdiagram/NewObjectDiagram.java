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
import java.awt.Font;
import java.awt.Graphics;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

import org.tzi.use.config.Options;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.BinaryEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.HalfEdge;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.NodeEdge;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSelectAll;
import org.tzi.use.gui.views.diagrams.event.DiagramMouseHandling;
import org.tzi.use.gui.views.diagrams.event.HideAdministration;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkEnd;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;


/**
 * A panel drawing UML object diagrams.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Mark Richters
 */
@SuppressWarnings("serial")
public class NewObjectDiagram extends DiagramView 
                              implements HighlightChangeListener {

    private class ShowObjectPropertiesViewMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount()==2) { 
                // mouse over node?
                PlaceableNode pickedObjectNode = findNode( fGraph, e.getX(),e.getY());
                if (pickedObjectNode instanceof ObjectNode) {
                    ObjectNode obj = (ObjectNode)pickedObjectNode;
                    ObjectPropertiesView v = 
                        MainWindow.instance().showObjectPropertiesView();
                    v.selectObject(obj.object().name());
                }
            }           
        }

        public void mousePressed(MouseEvent e) {}
    
        public void mouseReleased(MouseEvent e) {}
    
        public void mouseEntered(MouseEvent e) {}
    
        public void mouseExited(MouseEvent e) {}
        
    }

    private Map<MObject, ObjectNode> fObjectToNodeMap;
    private Map<MLink, BinaryEdge> fBinaryLinkToEdgeMap;
    private Map<MLink, DiamondNode> fNaryLinkToDiamondNodeMap;
    private Map<MLink, List<EdgeBase>> fHalfLinkToEdgeMap;
    private Map<MLinkObject, NodeEdge> fLinkObjectToNodeEdge;
    private NewObjectDiagramView fParent;
    
    private double fNextNodeX;
    private double fNextNodeY;
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
        fOpt = new ObjDiagramOptions();
        fGraph = new DirectedGraphBase<NodeBase, EdgeBase>();
        fObjectToNodeMap = new HashMap<MObject, ObjectNode>();
        fBinaryLinkToEdgeMap = new HashMap<MLink, BinaryEdge>();
        fNaryLinkToDiamondNodeMap = new HashMap<MLink, DiamondNode>();
        fHalfLinkToEdgeMap = new HashMap<MLink, List<EdgeBase>>();
        fLinkObjectToNodeEdge = new HashMap<MLinkObject, NodeEdge>();
        fHiddenNodes = new HashSet<Object>();
        fHiddenEdges = new HashSet<Object>();
        fParent = parent;
        fNodeSelection = new Selection();
        fEdgeSelection = new Selection();
        fLog = log;

        fLayoutInfos = new LayoutInfos( fBinaryLinkToEdgeMap, 
                                        fObjectToNodeMap, 
                                        fNaryLinkToDiamondNodeMap,
                                        fHalfLinkToEdgeMap,
                                        fLinkObjectToNodeEdge,
                                        null,
                                        null,
                                        fHiddenNodes, fHiddenEdges,
                                        fOpt, fParent.system(), this,
                                        fLog );
        
        fHideAdmin = new HideAdministration( fNodeSelection, fGraph, fLayoutInfos );
        
        // anfangs jj
        fSelection = new ObjectSelection(this);
        // end jj
        
        fActionSaveLayout = new ActionSaveLayout( "USE object diagram layout",
                                                  "olt", fGraph, fLog, fLayoutInfos );
        
        fActionLoadLayout = new ActionLoadLayout( "USE object diagram layout",
                                                  "olt", this, fLog,
                                                  fHideAdmin, fGraph,
                                                  fLayoutInfos );
        
        fActionSelectAll = new ActionSelectAll( fNodeSelection, fObjectToNodeMap,
                                                null, this );
        
        DiagramMouseHandling mouseHandling = 
            new DiagramMouseHandling( fNodeSelection, fEdgeSelection, fGraph, this);
        
        fParent.getModelBrowser().addHighlightChangeListener( this );
        
        setLayout(null);
        setBackground(Color.white);
        setPreferredSize( Options.fDiagramDimension );
        
        addMouseListener(mouseHandling);
        addMouseListener(showObjectPropertiesViewMouseListener);
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
                    eb = fBinaryLinkToEdgeMap.get( link );
                    if ( elem instanceof MAssociationClass ) {
                        eb = fLinkObjectToNodeEdge.get( (MLinkObject) link );
                    }
                    edges.add( eb );
                }
            } else {
                for (MLink link : links) {
                    for (MLinkEnd lEnd : link.linkEnds()) {
                        eb = (EdgeBase) fHalfLinkToEdgeMap.get( lEnd );
                        edges.add( eb );
                    }
                    if ( elem instanceof MAssociationClass ) {
                        eb = (EdgeBase) fLinkObjectToNodeEdge.get( (MLinkObject) link );
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
                NodeBase node = fObjectToNodeMap.get( obj );
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
        
        repaint();
    }
    
    /**
     * Determinds if the auto layout of the diagram is on or off.
     * @return <code>true</code> if the auto layout is on, otherwise
     * <code>false</code>
     */
    public boolean isDoAutoLayout() {
        return fOpt.isDoAutoLayout();
    }
    


    /**
     * Draws the diagram.
     */
    public void paintComponent(Graphics g) {
        synchronized (fLock) {
            Font f = Font.getFont("use.gui.view.objectdiagram", getFont());
            g.setFont(f);
            drawDiagram(g);
            //drawDiagram(g);
        }
    }

    /**
     * Adds an object to the diagram.
     */
    public void addObject(MObject obj) {
        // Find a random new position. getWidth and getHeight return 0
        // if we are called on a new diagram.
		fNextNodeX = Math.random() * Math.max(100, getWidth());
		fNextNodeY = Math.random() * Math.max(100, getHeight());
  
        ObjectNode n = new ObjectNode( obj, fParent, fOpt);
        n.setPosition( fNextNodeX, fNextNodeY );
        synchronized (fLock) {
            fGraph.add(n);
            fObjectToNodeMap.put(obj, n);
            fLayouter = null;
        }
    }

    /**
     * Deletes an object from the diagram.
     */
    public void deleteObject(MObject obj) {
        ObjectNode n = (ObjectNode) fObjectToNodeMap.get(obj);
        if (n == null) {
            if ( fHiddenNodes.contains(obj) ) {
                fHiddenNodes.remove(obj);
                fLog.println("Deleted object `" + obj
                             + "' from the hidden objects.");
            } else {
                throw new RuntimeException("no node for object `" + obj
                        + "' in current state.");
            }
        }

        synchronized (fLock) {
            fGraph.remove(n);
            fObjectToNodeMap.remove(obj);
            fLayouter = null;
        }
    }

    /**
     * Adds a link to the diagram.
     */
    public void addLink(MLink link) {
		MAssociation assoc = link.association();
	    String label = assoc.name();
		Iterator<MAssociationEnd> iter = assoc.associationEnds().iterator();
	
		MLinkEnd linkEnd1 = null;
		MLinkEnd linkEnd2 = null;
	    if (iter.hasNext()) {
		  linkEnd1 = link.linkEnd((MAssociationEnd) iter.next());
		  linkEnd2 = link.linkEnd((MAssociationEnd) iter.next());	  
		}
	    
		if( (linkEnd1 == null) || (linkEnd2 == null)){
		  throw new RuntimeException( "added link is invalid" );
		}
		
        MObject obj1 = linkEnd1.object();
        MObject obj2 = linkEnd2.object();
        
        if (link.linkEnds().size() == 2) {
            // object link
            if (link instanceof MLinkObject) {
                NodeEdge e = 
                    new NodeEdge(label, fObjectToNodeMap.get(obj1), 
                                 fObjectToNodeMap.get(obj2),
                                 linkEnd1.associationEnd(), 
                                 linkEnd2.associationEnd(), link,
                                 (NodeBase) fObjectToNodeMap.get(link),
                                 this, link.association() );
                synchronized (fLock) {
                    fGraph.addEdge(e);
                    fLinkObjectToNodeEdge.put((MLinkObject)link, e);
                    fLayouter = null;
                }
            } else {
                // binary link
                BinaryEdge e = 
                    new BinaryEdge( label, fObjectToNodeMap.get(obj1), 
                                    fObjectToNodeMap.get(obj2), 
                                    linkEnd1.associationEnd(), 
                                    linkEnd2.associationEnd(), this, 
                                    link.association() );
                synchronized (fLock) {
                    fGraph.addEdge(e);
                    fBinaryLinkToEdgeMap.put(link, e);
                    fLayouter = null;
                }
            }

        } else
            synchronized (fLock) {
                // Find a random new position. getWidth and getheight return 0
                // if we are called on a new diagram.
                double fNextNodeX = Math.random() * Math.max(100, getWidth());
                double fNextNodeY = Math.random() * Math.max(100, getHeight());
                
                // n-ary link: create a diamond node and n edges to objects
                DiamondNode node = new DiamondNode( link, fOpt );
                node.setPosition( fNextNodeX, fNextNodeY );
                fGraph.add(node);
                
                // connected to an "object link"
                if (link instanceof MLinkObject) {
                    NodeEdge e = 
                        new NodeEdge(label, fObjectToNodeMap.get(obj1), 
                                     fObjectToNodeMap.get(obj2), node,
                                     (ObjectNode) fObjectToNodeMap.get(link),
                                     this, link.association() );
                    synchronized (fLock) {
                        fGraph.addEdge(e);
                        fLinkObjectToNodeEdge.put((MLinkObject)link, e);
                        fLayouter = null;
                    }
                }
                // connected to a "normal" link
                fNaryLinkToDiamondNodeMap.put(link, node);
                List<EdgeBase> halfEdges = new ArrayList<EdgeBase>();
                
                for (MLinkEnd linkEnd : link.linkEnds()) {
                    MObject obj = linkEnd.object();
                    HalfEdge e = 
                        new HalfEdge( node, (NodeBase) fObjectToNodeMap.get(obj),
                                      linkEnd.associationEnd(), this );
                    fGraph.addEdge(e);
                    halfEdges.add( e );
                }
                if ( fLinkObjectToNodeEdge.get( link ) != null ) {
                    halfEdges.add( fLinkObjectToNodeEdge.get( link ) );
                }
                node.setHalfEdges( halfEdges );
                fHalfLinkToEdgeMap.put( link, halfEdges );
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
                e = fLinkObjectToNodeEdge.get(link);
                // TODO: this is just a tempory solution
                if (e == null) {
                    return;
                }
            } else {
                e = (BinaryEdge) fBinaryLinkToEdgeMap.get(link);
            }

            if ( e != null && !loadingLayout 
                 && !(link instanceof MLinkObject )
                 && fHiddenEdges.contains(link) ) {
                fHiddenEdges.remove(link);
                fLog.println("Deleted link `" + link + "' from hidden links.");
            }
            if (e == null) {
                throw new RuntimeException( "no edge for link `" + link
                                            + "' in current state." );
            }

            synchronized (fLock) {
                fGraph.removeEdge(e);
                if (link instanceof MObject) {
                    fLinkObjectToNodeEdge.remove(link);
                } else {
                    fBinaryLinkToEdgeMap.remove(link);
                }
                fLayouter = null;
            }
        } else {
            DiamondNode n = (DiamondNode) fNaryLinkToDiamondNodeMap.get( link );
            if ( n == null && !loadingLayout ) {
                if ( fHiddenEdges.contains(link) ) {
                    fHiddenEdges.remove(link);
                    fLog.println("Deleted link `" + link
                                 + "' from hidden links.");
                } else {
                    throw new RuntimeException(
                            "no diamond node for n-ary link `" + link
                            + "' in current state.");
                }
            }

            synchronized (fLock) {
                // all dangling HalfLinkEdges are removed by the graph
                fGraph.remove(n);
                fNaryLinkToDiamondNodeMap.remove(link);
                fHalfLinkToEdgeMap.remove( link );
                fLayouter = null;
            }

            synchronized (fLock) {
                if (link instanceof MObject) {
                    NodeEdge edge = 
                        (NodeEdge) fLinkObjectToNodeEdge.get( link );
                    if (edge != null) {
                        fGraph.removeEdge( edge );
                        fLinkObjectToNodeEdge.remove( link );
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

        	String txt = "insert (";
            for (int i = 0; i < participants.length; ++i) {
                if (i > 0) {
                	txt = txt + ",";
                }
                txt = txt + participants[i].name();
            }
            txt = txt + ") into " + association.name();
            
            putValue(Action.NAME, txt);
        }

        public void actionPerformed(ActionEvent e) {
            fParent.insertLink(fAssociation, fParticipants);
        }
    }

    /**
     * Deletes a Link from the objectdiagram.
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

    
    /**
     * Deletes all hidden elements form this diagram.
     */
    @Override
    public void deleteHiddenElementsFromDiagram( Set<Object> objectsToHide, 
                                          		 Set<Object> linksToHide ) {
        Iterator<Object> it = objectsToHide.iterator();
        while ( it.hasNext() ) {
            MObject obj = (MObject) it.next();
            deleteObject( obj );
        }
        
        Set<MLink> linksToDelete = new HashSet<MLink>();
        it = linksToHide.iterator();
        while ( it.hasNext() ) {
            MLink link = (MLink) it.next();
            linksToDelete.add( link );
        }
        
        for (MLink link : linksToDelete) {
            deleteLink( link, true );
        }
        fHiddenNodes.addAll( objectsToHide );
        fHiddenEdges.addAll( linksToHide );
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
            
            for(Iterator<Selectable> it = fNodeSelection.iterator(); it.hasNext();) {
                PlaceableNode node = (PlaceableNode) it.next();
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
                int n = assoc.associationEnds().size();
                if (m>n) continue;
                
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
                    } 
                    if (fParent.hasLinkBetweenObjects(assoc, l)) {
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
				popupMenu.insert(fHideAdmin.setValues("Hide link " + info + "",
						selectedObjectsOfAssociation), pos++);  //fixxx
				popupMenu.insert(fHideAdmin.setValues("Crop link " + info,
						getNoneSelectedNodes(selectedObjectsOfAssociation)), pos++);
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedLinkPathView("Selection link path length...", selectedObjectsOfAssociation, anames), pos++);
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
                popupMenu.insert( fHideAdmin.setValues("Hide " + txt,
                                                       selectedObjectsSet),
                                                       pos++ );
                popupMenu.insert( fHideAdmin.setValues("Crop " + txt,
                                                       getNoneSelectedNodes( selectedObjectsSet )),
                                                       pos++ );
                separatorNeeded = true;
            	popupMenu.insert(new JSeparator(), pos++); // anfangs jj
				popupMenu.insert(fSelection.getSelectedObjectPathView("Selection "
						+ txt + " path length...", selectedObjectsSet), pos++); // end jj
            }
        }

        if (!fHiddenNodes.isEmpty()) {
            final JMenuItem showAllObjects = new JMenuItem("Show hidden objects");
            showAllObjects.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    fHideAdmin.showAllHiddenElements();
                    repaint();
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
        if (fGraph.size() > 0 || fHiddenNodes.size() > 0) { 
			popupMenu.addSeparator();			

			if (fGraph.size() > 0) {
				popupMenu.add(fSelection.getSubMenuHideObject());
			}
			
			if (fHiddenNodes.size() > 0) {
				popupMenu.add(fSelection.getSubMenuShowObject());
			}
		}
        
		// jj end this

		popupMenu.addSeparator();
		popupMenu.add(fSelection.getSelectionOCLView("OCL Selection...")); // end jj

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
        return true;
    }
    
    /**
     * Finds all nodes which are not selected.
     * @param selectedNodes Nodes which are selected at this point in the diagram.
     * @return A HashSet of the none selected objects in the diagram.
     */
    private Set<Object> getNoneSelectedNodes( Set<?> selectedNodes ) {
        Set<Object> noneSelectedNodes = new HashSet<Object>();
        
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
        //Log.trace(this, "dtde = " + dtde);
        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            Transferable transferable = dtde.getTransferable();

            // we accept only Strings
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s = (String) transferable
                        .getTransferData(DataFlavor.stringFlavor);
                //Log.trace(this, "transfer = " + s);

                if (s.startsWith("CLASS-")) {
                    Point p = dtde.getLocation();
                    if ( isDoAutoLayout() ) {
                        fNextNodeX = Math.random() * Math.max(100, getWidth());
                        fNextNodeY = Math.random() * Math.max(100, getHeight());
                    } else {                    
                        fNextNodeX = p.getX();
                        fNextNodeY = p.getY();
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
}
