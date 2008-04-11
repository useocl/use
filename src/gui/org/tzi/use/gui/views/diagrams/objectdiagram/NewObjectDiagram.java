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
import org.tzi.use.gui.views.diagrams.BinaryEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.HalfEdge;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.NodeEdge;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSelectAll;
import org.tzi.use.gui.views.diagrams.event.DiagramMouseHandling;
import org.tzi.use.gui.views.diagrams.event.HideAdministration;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAssociationClass;
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

    private Map fObjectToNodeMap; // (MObject -> ObjectNode)
    private Map fBinaryLinkToEdgeMap; // (MLink -> BinaryLinkEdge)
    private Map fNaryLinkToDiamondNodeMap; // (MLink -> DiamondNode)
    private Map fHalfLinkToEdgeMap; // (MLink -> List(HalfEdge))
    private Map fLinkObjectToNodeEdge; // (MLinkObject -> NodeEdge)
    private NewObjectDiagramView fParent;
    
    private double fNextNodeX;
    private double fNextNodeY;
    private ShowObjectPropertiesViewMouseListener 
        showObjectPropertiesViewMouseListener 
            = new ShowObjectPropertiesViewMouseListener();

    

    /**
     * Creates a new empty diagram.
     */
    NewObjectDiagram(NewObjectDiagramView parent, PrintWriter log) {
        fOpt = new ObjDiagramOptions();
        fGraph = new DirectedGraphBase();
        fObjectToNodeMap = new HashMap();
        fBinaryLinkToEdgeMap = new HashMap();
        fNaryLinkToDiamondNodeMap = new HashMap();
        fHalfLinkToEdgeMap = new HashMap();
        fLinkObjectToNodeEdge = new HashMap();
        fHiddenNodes = new HashSet();
        fHiddenEdges = new HashSet();
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
        
        fActionSaveLayout = new ActionSaveLayout( "USE object diagram layout",
                                                  "olt", fGraph, fLog, fLayoutInfos );
        
        fActionLoadLayout = new ActionLoadLayout( "USE object diagram layout",
                                                  "olt", this, fLog,
                                                  fHideAdmin, fGraph,
                                                  fLayoutInfos );
        
        fActionSelectAll = new ActionSelectAll( fNodeSelection, fObjectToNodeMap,
                                                null, this );
        
        DiagramMouseHandling mouseHandling = 
            new DiagramMouseHandling( fNodeSelection, fEdgeSelection, fGraph,
                                      fHideAdmin, fHiddenNodes, fOpt, this);
        
        fParent.getModelBrowser().addHighlightChangeListener( this );
        
        setLayout(null);
        setBackground(Color.white);
//        setPreferredSize(new Dimension(400, 400));
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

    /**
     * Displays objects of the selected class in the modelbrowser.
     */
    public void stateChanged( HighlightChangeEvent e ) {
        if ( !fParent.isSelectedView() ) {
            return;
        }
        
        MModelElement elem = e.getModelElement();
        List edges = new ArrayList();
        boolean allEdgesSelected = true;
   
        // elem is an association
        if ( elem != null && elem instanceof MAssociation ) {
            int size = ((MAssociation) elem).associationEnds().size();
            Set links = 
                fParent.system().state().linksOfAssociation( (MAssociation) elem ).links();
            EdgeBase eb = null;
            if ( size == 2 ) {
                Iterator it = links.iterator();
                while ( it.hasNext() ) {
                    MLink link = (MLink) it.next();
                    eb = (EdgeBase) fBinaryLinkToEdgeMap.get( link );
                    if ( elem instanceof MAssociationClass ) {
                        eb = (EdgeBase) fLinkObjectToNodeEdge.get( (MLinkObject) link );
                    }
                    edges.add( eb );
                }
            } else {
                Iterator it = links.iterator();
                while ( it.hasNext() ) {
                    MLink link = (MLink) it.next();
                    Iterator itLinkEnd = link.linkEnds().iterator();
                    while ( itLinkEnd.hasNext() ) {
                        MLinkEnd lEnd = (MLinkEnd) itLinkEnd.next();
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
            Iterator it = edges.iterator();
            while ( it.hasNext() ) {
                EdgeBase edge = (EdgeBase) it.next();
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
            Iterator it = 
                fParent.system().state().objectsOfClass( (MClass) elem ).iterator();
            while ( it.hasNext() ) {
                MObject obj = (MObject) it.next();
                NodeBase node = (NodeBase) fObjectToNodeMap.get( obj );
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
        // Find a random new position. getWidth and getheight return 0
        // if we are called on a new diagram.
//        if ( isDoAutoLayout() ) {
//            fNextNodeX = Math.random() * Math.max(100, getWidth());
//            fNextNodeY = Math.random() * Math.max(100, getHeight());
//        }
//        
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
	Iterator iter = assoc.associationEnds().iterator();
	MLinkEnd linkEnd1 = null;
	MLinkEnd linkEnd2 = null;
        while (iter.hasNext()) {
	  linkEnd1 = link.linkEnd((MAssociationEnd) iter.next());
	  linkEnd2 = link.linkEnd((MAssociationEnd) iter.next());
	  break;	  
	}
	if( (linkEnd1 == null) || (linkEnd2 == null)){
	  throw new RuntimeException( "added link is invalidate" );
	}
		
        MObject obj1 = linkEnd1.object();
        MObject obj2 = linkEnd2.object();
        
        if (link.linkEnds().size() == 2) {
            // object link
            if (link instanceof MObject) {
                NodeEdge e = 
                    new NodeEdge(label, fObjectToNodeMap.get(obj1), 
                                 fObjectToNodeMap.get(obj2),
                                 linkEnd1.associationEnd(), 
                                 linkEnd2.associationEnd(), link,
                                 (NodeBase) fObjectToNodeMap.get(link),
                                 this, link.association() );
                synchronized (fLock) {
                    fGraph.addEdge(e);
                    fLinkObjectToNodeEdge.put(link, e);
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
                if (link instanceof MObject) {
                    NodeEdge e = 
                        new NodeEdge(label, fObjectToNodeMap.get(obj1), 
                                     fObjectToNodeMap.get(obj2), node,
                                     (ObjectNode) fObjectToNodeMap.get(link),
                                     this, link.association() );
                    synchronized (fLock) {
                        fGraph.addEdge(e);
                        fLinkObjectToNodeEdge.put(link, e);
                        fLayouter = null;
                    }
                }
                // connected to a "normal" link
                fNaryLinkToDiamondNodeMap.put(link, node);
                List halfEdges = new ArrayList();
                Iterator linkEndIter = link.linkEnds().iterator();
                while (linkEndIter.hasNext()) {
                    MLinkEnd linkEnd = (MLinkEnd) linkEndIter.next();
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
                e = (NodeEdge) fLinkObjectToNodeEdge.get(link);
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
        private MObject[] fObjects;
        private String fAssocName;

        ActionInsertLink(MObject[] objects, String assocName) {
            fObjects = objects;
            fAssocName = assocName;
            String txt = "insert (";
            for (int i=0;i<objects.length;++i) {
                if (i>0) txt = txt + ",";
                MObject o = objects[i];
                txt = txt + o.name();
            }
            txt = txt + ") into " + assocName;
            putValue(Action.NAME, txt);
        }

        public void actionPerformed(ActionEvent e) {
            fParent.insertLink(fAssocName, fObjects);
        }
    }

    /**
     * Deletes a Link from the objectdiagram.
     */
    class ActionDeleteLink extends AbstractAction {
        private MObject[] fObjects;
        private String fAssocName;

        ActionDeleteLink(MObject[] objects, String assocName) {
            fObjects = objects;
            fAssocName = assocName;
            String txt = "delete (";
            for (int i=0;i<objects.length;++i) {
                if (i>0) txt = txt + ",";
                MObject o = objects[i];
                txt = txt + o.name();
            }
            txt = txt + ") from " + assocName;
            putValue(Action.NAME, txt);
            fObjects = objects;
            fAssocName = assocName;
        }

        public void actionPerformed(ActionEvent e) {
            fParent.deleteLink( fAssocName, fObjects );
            fEdgeSelection.clear();
        }
    }
    
    /**
     * Deletes the selected objects.
     */
    class ActionDelete extends AbstractAction {
        private Set fObjects;

        ActionDelete(String text, Set objects) {
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
    public void deleteHiddenElementsFromDiagram( Set objectsToHide, 
                                          Set linksToHide ) {
        Iterator it = objectsToHide.iterator();
        while ( it.hasNext() ) {
            MObject obj = (MObject) it.next();
            deleteObject( obj );
        }
        
        Set linksToDelete = new HashSet();
        it = linksToHide.iterator();
        while ( it.hasNext() ) {
            MLink link = (MLink) it.next();
            linksToDelete.add( link );
//            deleteLink( link );
        }
        
        it = linksToDelete.iterator();
        while ( it.hasNext() ) {
            deleteLink( (MLink) it.next(), true );
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
        
        // get all associations that exist between the classes of the
        // selected objects
        if (!fNodeSelection.isEmpty()) {
            HashSet selectedObjects = new HashSet();
            for(Iterator it = fNodeSelection.iterator(); it.hasNext();) {
                PlaceableNode node = (PlaceableNode) it.next();
                if (node instanceof ObjectNode && node.isDeletable()) 
                    selectedObjects.add(((ObjectNode) node).object());
            }
            final MObject[] selectedObjs = (MObject[])selectedObjects.toArray(new MObject[0]);
            if (selectedObjs.length==1) {
                popupMenu.insert( new ActionShowProperties("Edit properties of " + 
                    selectedObjs[0].name(), selectedObjs[0]), pos++);
            }
            int m = selectedObjs.length;
            boolean addedInsertLinkAction = false;
            for (Iterator it=fParent.system().model().associations().iterator(); it.hasNext();) {
                MAssociation assoc = (MAssociation)it.next();
                int n = assoc.associationEnds().size();
                if (m>n) continue;
                int pow=1;for(int i=0;i<n;++i) pow*=m;
                for(int i=0;i<pow;++i) {
                    MObject[] l = new MObject[n];
                    MClass[] c = new MClass[n];
                    int[] digits = radixConversion(i,m,n);
                    if (!isCompleteObjectCombination(digits, m)) continue;
                    for(int j=0;j<n;++j) {
                        l[j] = selectedObjs[digits[j]];
                        c[j] = l[j].cls();
                    }
                    if (!assoc.isAssignableFrom(c)) continue;
                    if (!fParent.hasLinkBetweenObjects(assoc, l)) {
                        popupMenu.insert(new ActionInsertLink(l,assoc.name()), pos++ );
                        addedInsertLinkAction = true;
                    } 
                    if (fParent.hasLinkBetweenObjects(assoc, l)) {
                        popupMenu.insert(new ActionDeleteLink(l,assoc.name()),pos++);
                        addedInsertLinkAction = true;
                    }
                }
               
            }

            if ( addedInsertLinkAction )
                popupMenu.insert( new JSeparator(), pos++ );

            String txt = null;
            if (selectedObjects.size() == 1) {
                txt = "'"+((MObject) selectedObjects.iterator().next()).name()+"'";
            } else if (selectedObjects.size() > 1) {
                txt = selectedObjects.size() + " objects";
            }
            if (txt != null && txt.length() > 0) {
                popupMenu.insert( new ActionDelete("Delete " + txt, 
                                                   selectedObjects),
                                                   pos++ );
                popupMenu.insert( fHideAdmin.setValues("Hide " + txt,
                                                       selectedObjects),
                                                       pos++ );
                popupMenu.insert( fHideAdmin.setValues("Crop " + txt,
                                                       getNoneSelectedNodes( selectedObjects )),
                                                       pos++ );
                separatorNeeded = true;
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

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
        return true;
    }
    
    /**
     * Finds all nodes which are not selected.
     * @param selectedNodes Nodes which are selected at this point in the diagram.
     * @return A HashSet of the none selected objects in the diagram.
     */
    private Set getNoneSelectedNodes( Set selectedNodes ) {
        Set noneSelectedNodes = new HashSet();
        
        Iterator it = fGraph.iterator();
        while ( it.hasNext() ) {
            Object o = it.next();
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
        Map attributeValueMap = objState.attributeValueMap();
        Iterator it = attributeValueMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            MAttribute attr = (MAttribute) entry.getKey();
            Value v = (Value) entry.getValue();
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
