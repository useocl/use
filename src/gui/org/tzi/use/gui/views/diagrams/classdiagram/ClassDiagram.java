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

package org.tzi.use.gui.views.diagrams.classdiagram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.tzi.use.config.Options;
import org.tzi.use.graph.DirectedGraphBase;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.BinaryEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.GeneralizationEdge;
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
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.selection.classselection.ClassSelection;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.sys.MObject;


/**
 * A panel drawing UML class diagrams.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public class ClassDiagram extends DiagramView 
                             implements HighlightChangeListener {

    private ClassDiagramView fParent;
    private Map<MClass, ClassNode> fClassToNodeMap;
    private Map<EnumType, EnumNode> fEnumToNodeMap;
    private Map<MAssociation, BinaryEdge> fBinaryAssocToEdgeMap;
    private Map<MAssociationClass, NodeEdge> fAssocClassToEdgeMap;
    private Map<MAssociation, DiamondNode> fNaryAssocToDiamondNodeMap;
    private Map<MAssociation, List<EdgeBase>> fNaryAssocToHalfEdgeMap;
    private Map<MGeneralization, GeneralizationEdge> fGenToGeneralizationEdge;
    
    // jj anfangen
	private ClassSelection fSelection;
	private DiagramMouseHandling mouseHandling;
	// jj end
        
    ClassDiagram( ClassDiagramView parent, PrintWriter log ) {
        fOpt = new ClassDiagramOptions();
        fGraph = new DirectedGraphBase<NodeBase, EdgeBase>();
        fClassToNodeMap = new HashMap<MClass, ClassNode>();
        fEnumToNodeMap = new HashMap<EnumType, EnumNode>();
        fBinaryAssocToEdgeMap = new HashMap<MAssociation, BinaryEdge>();
        fAssocClassToEdgeMap = new HashMap<MAssociationClass, NodeEdge>();
        fNaryAssocToDiamondNodeMap = new HashMap<MAssociation, DiamondNode>();
        fNaryAssocToHalfEdgeMap = new HashMap<MAssociation, List<EdgeBase>>();
        fGenToGeneralizationEdge = new HashMap<MGeneralization, GeneralizationEdge>();
        fParent = parent;
        fHiddenNodes = new HashSet<Object>();
        fHiddenEdges = new HashSet<Object>();
        fNodeSelection = new Selection();
        fEdgeSelection = new Selection();
        
        setLayout( null );
        setBackground( Color.white );
        fLog = log;
        setPreferredSize( Options.fDiagramDimension );
        
        fLayoutInfos = new LayoutInfos( fBinaryAssocToEdgeMap, 
                                        fClassToNodeMap, 
                                        fNaryAssocToDiamondNodeMap,
                                        fNaryAssocToHalfEdgeMap,
                                        fAssocClassToEdgeMap,
                                        fEnumToNodeMap,
                                        fGenToGeneralizationEdge,
                                        fHiddenNodes, fHiddenEdges,
                                        fOpt, fParent.system(), this, 
                                        fLog );
        
        fHideAdmin = new HideAdministration( fNodeSelection, fGraph, fLayoutInfos );
        
        mouseHandling = 
            new DiagramMouseHandling( fNodeSelection, fEdgeSelection,
                                      fGraph, this);
        
        // anfangs jj
        fSelection = new ClassSelection(this, fGraph, fHiddenNodes, fHideAdmin, fClassToNodeMap, fNodeSelection);
        // end jj
        
        fActionSaveLayout = new ActionSaveLayout( "USE class diagram layout",
                                                  "clt", fGraph, fLog, fLayoutInfos );
        
        fActionLoadLayout = new ActionLoadLayout( "USE class diagram layout",
                                                  "clt", this, fLog,
                                                  fHideAdmin, fGraph,
                                                  fLayoutInfos );
        
        fActionSelectAll = new ActionSelectAll( fNodeSelection, fClassToNodeMap,
                                                fEnumToNodeMap, this );

        addMouseListener( mouseHandling );
        
        fParent.getModelBrowser().addHighlightChangeListener( this );
        
        addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                // need a new layouter to adapt to new window size
                fLayouter = null;
            }
        } );
        startLayoutThread();
    }

    /**
     * Displays the selected class of the modelbrowser in the class diagram.
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
            EdgeBase eb = null;
            if ( size == 2 ) {
                    eb = fBinaryAssocToEdgeMap.get( (MAssociation) elem );
                if ( elem instanceof MAssociationClass ) {
                    eb = fAssocClassToEdgeMap.get( (MAssociationClass) elem );
                }
                edges.add( eb );
            } else {
                List<EdgeBase> halfEdges =  fNaryAssocToHalfEdgeMap.get( (MAssociation) elem );
                if ( edges != null && halfEdges != null ) {
                    edges.addAll( halfEdges );
                }
                if ( elem instanceof MAssociationClass ) {
                    eb = fAssocClassToEdgeMap.get( (MAssociationClass) elem );
                    if ( !edges.contains( eb ) ) {
                        edges.add( eb );
                    }
                }
            }
            
            // check all edges in the list if they are suppose to be selected 
            // or deselected.
            for (EdgeBase edge : edges) {
                if ( edge != null ) {
                    if ( fEdgeSelection.isSelected( edge ) ) {
                        fEdgeSelection.remove( edge );
                        allEdgesSelected = true;
                    } else {
                        fEdgeSelection.add( edge );
                        allEdgesSelected = false;
                    }  
                }    
            }
        }
        
        // elem is a class
        if ( elem != null && elem instanceof MClass ) {
            NodeBase node = fClassToNodeMap.get( (MClass) elem );
            if ( node != null ) {
                if ( elem instanceof MAssociationClass ) {
                    if ( fNodeSelection.isSelected( node ) && allEdgesSelected ) {
                        fNodeSelection.remove( node );
                    } else {
                        fNodeSelection.add( node );
                    }  
                } else {
                    if ( fNodeSelection.isSelected( node ) ) {
                        fNodeSelection.remove( node );
                    } else {
                        fNodeSelection.add( node );
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
    public void paintComponent( Graphics g ) {
        synchronized ( fLock ) {
            Font f = Font.getFont( "use.gui.view.objectdiagram", getFont() );
            g.setFont( f );
            drawDiagram( g );
        }
    }

    /**
     * Adds a class to the diagram.
     * 
     * @param cls Class to be added.
     */
    public void addClass( MClass cls ) {
        // Find a random new position. getWidth and getheight return 0
        // if we are called on a new diagram.
        double fNextNodeX = Math.random() * Math.max( 100, getWidth() );
        double fNextNodeY = Math.random() * Math.max( 100, getHeight() );
        ClassNode n = new ClassNode( cls, fOpt );
        n.setPosition( fNextNodeX, fNextNodeY );
        synchronized ( fLock ) {
            fGraph.add( n );
            fClassToNodeMap.put( cls, n );
            fLayouter = null;
        }
    }

    /**
     * Deletes a class from the diagram.
     */
    public void deleteClass( MClass cls ) {
        NodeBase n = (NodeBase) fClassToNodeMap.get( cls );
        if (n == null) {
            if ( fHiddenNodes.contains( cls ) ) {
                fHiddenNodes.remove( cls );
                fLog.println("Deleted class `" + cls.name()
                             + "' from the hidden classes.");
            } else {
                throw new RuntimeException("no node for class `" 
                                           + cls.name() + "' in current state.");
            }
        }

        synchronized ( fLock ) {
            fGraph.remove( n );
            fClassToNodeMap.remove( cls );
            fLayouter = null;
        }
    }
    
    /**
     * Adds an enumeration to the diagram.
     * 
     * @param enumeration Enumeration to be added.
     */
    public void addEnum( EnumType enumeration ) {
        // Find a random new position. getWidth and getheight return 0
        // if we are called on a new diagram.
        double fNextNodeX = Math.random() * Math.max( 100, getWidth() );
        double fNextNodeY = Math.random() * Math.max( 100, getHeight() );
        EnumNode n = new EnumNode( enumeration, fOpt );
        n.setPosition( fNextNodeX, fNextNodeY );

        synchronized ( fLock ) {
            fGraph.add( n );
            fEnumToNodeMap.put( enumeration, n );
            fLayouter = null;
        }
    }

    /**
     * Deletes an enumeration from the diagram.
     */
    public void deleteEnum( EnumType enumeration ) {
        NodeBase n = (NodeBase) fEnumToNodeMap.get( enumeration );
        if (n == null) {
            if ( fHiddenNodes.contains( enumeration ) ) {
                fHiddenNodes.remove( enumeration );
                fLog.println("Deleted enumeration `" + enumeration.name()
                             + "' from the hidden enumerations.");
            } else {
                throw new RuntimeException("no node for enumeration `" 
                                           + enumeration.name() 
                                           + "' in current state.");
            }
        }

        synchronized ( fLock ) {
            fGraph.remove( n );
            fEnumToNodeMap.remove( enumeration );
            fLayouter = null;
        }
    }
    
    /**
     * Adds an association to the diagram.
     */
    public void addAssociation( MAssociation assoc ) {
        String label = assoc.name();
        
        Iterator<MAssociationEnd> assocEndIter = assoc.associationEnds().iterator();
        MAssociationEnd assocEnd1 = assocEndIter.next();
        MAssociationEnd assocEnd2 = assocEndIter.next();
        MClass cls1 = assocEnd1.cls();
        MClass cls2 = assocEnd2.cls();

        if ( assoc.associationEnds().size() == 2 ) {
            // associationclass
            if ( assoc instanceof MAssociationClass ) {
                NodeEdge e = 
                    new NodeEdge( label, fClassToNodeMap.get( cls1 ),
                                  fClassToNodeMap.get( cls2),
                                  assocEnd1, assocEnd2, assoc,
                                  (NodeBase) fClassToNodeMap.get( assoc ),
                                  this, assoc );
                synchronized (fLock) {
                    fGraph.addEdge(e);
                    fAssocClassToEdgeMap.put( (MAssociationClass)assoc, e );
                    fLayouter = null;
                }
            } else {
                // binary association
                BinaryEdge e = 
                    new BinaryEdge( label, fClassToNodeMap.get(cls1), 
                                    fClassToNodeMap.get(cls2), 
                                    assocEnd1, assocEnd2, 
                                    this, assoc );
                synchronized ( fLock ) {
                    fGraph.addEdge(e);
                    fBinaryAssocToEdgeMap.put(assoc, e);
                    fLayouter = null;
                }
            }

        } else
            synchronized (fLock) {
                // Find a random new position. getWidth and getheight return 0
                // if we are called on a new diagram.
                double fNextNodeX = Math.random() * Math.max(100, getWidth());
                double fNextNodeY = Math.random() * Math.max(100, getHeight());
                
                // n-ary association: create a diamond node and n edges to classes
                DiamondNode node = new DiamondNode( assoc, fOpt );
                node.setPosition( fNextNodeX, fNextNodeY );
                fGraph.add(node);
                // connected to an associationclass
                if ( assoc instanceof MAssociationClass ) {
                    NodeEdge e = 
                        new NodeEdge( label, fClassToNodeMap.get( cls1 ), 
                                      fClassToNodeMap.get( cls2 ),
                                      assocEnd1, assocEnd2, node, 
                                      (NodeBase) fClassToNodeMap.get( assoc ),
                                      this, assoc );
                    synchronized (fLock) {
                        fGraph.addEdge(e);
                        fAssocClassToEdgeMap.put( (MAssociationClass)assoc, e );
                        fLayouter = null;
                    }
                }
                // connected to a "normal" class
                fNaryAssocToDiamondNodeMap.put( assoc, node );
                List<EdgeBase> halfEdges = new ArrayList<EdgeBase>();
                assocEndIter = assoc.associationEnds().iterator();
                while ( assocEndIter.hasNext() ) {
                    MAssociationEnd assocEnd = (MAssociationEnd) assocEndIter.next();
                    MClass cls = assocEnd.cls();
                    HalfEdge e = 
                        new HalfEdge(node, (NodeBase) fClassToNodeMap.get( cls ),
                                     assocEnd, this );
                    fGraph.addEdge(e);
                    halfEdges.add( e );
                }
                node.setHalfEdges( halfEdges );
                fNaryAssocToHalfEdgeMap.put( assoc, halfEdges );
                fLayouter = null;
            }
    }
    
    /**
     * Removes an association from the diagram.
     */
    public void deleteAssociation( MAssociation assoc, boolean loadingLayout ) {
        if ( assoc.associationEnds().size() == 2 ) {
            EdgeBase e = null;
            if ( assoc instanceof MAssociationClass ) { 
                e = fAssocClassToEdgeMap.get( assoc );
                if (e == null) {
                    return;
                }
            } else {
                e = fBinaryAssocToEdgeMap.get( assoc );
            }

            if ( e != null && !loadingLayout 
                 && !( assoc instanceof MAssociationClass )
                 && fHiddenEdges.contains( assoc ) ) {
                fHiddenEdges.remove( assoc );
                fLog.println("Deleted association `" + assoc.name() 
                             + "' from hidden associations.");
            }
            if (e == null) {
                throw new RuntimeException( "no edge for association `" 
                                            + assoc.name()
                                            + "' in current state." );
            }

            synchronized (fLock) {
                fGraph.removeEdge( e );
                if ( assoc instanceof MAssociationClass ) {
                    fAssocClassToEdgeMap.remove( assoc );
                } else {
                    fBinaryAssocToEdgeMap.remove( assoc );
                }
                fLayouter = null;
            }
        } else {
            DiamondNode n = (DiamondNode) fNaryAssocToDiamondNodeMap.get( assoc );
            if ( n == null && !loadingLayout ) {
                if ( fHiddenEdges.contains( assoc ) ) {
                    fHiddenEdges.remove( assoc );
                    fLog.println( "Deleted association `" + assoc.name()
                                  + "' from hidden associations." );
                } else {
                    throw new RuntimeException(
                            "no diamond node for n-ary association `" 
                            + assoc.name() + "' in current state.");
                }
            }

            synchronized ( fLock ) {
                // all dangling HalfLinkEdges are removed by the graph
                fGraph.remove( n );
                fNaryAssocToDiamondNodeMap.remove( assoc );
                fNaryAssocToHalfEdgeMap.remove( assoc );
                fLayouter = null;
            }

            synchronized ( fLock ) {
                if ( assoc instanceof MAssociationClass ) {
                    NodeEdge edge = 
                        (NodeEdge) fAssocClassToEdgeMap.get( assoc );
                    if ( edge != null ) {
                        fGraph.removeEdge( edge );
                        fAssocClassToEdgeMap.remove( assoc );
                    }
                }
            }
        }
    }
    
    /**
     * Adds a generalization to the diagram.
     */
    public void addGeneralization( MGeneralization gen ) {
        MClass parent = gen.parent();
        MClass child = gen.child();
        GeneralizationEdge e = 
            new GeneralizationEdge( fClassToNodeMap.get( child ),
                                    fClassToNodeMap.get( parent ), this );
        synchronized ( fLock ) {
            fGraph.addEdge( e );
            fGenToGeneralizationEdge.put( gen, e );
            fLayouter = null;
        }
    }

    /**
     * Deletes a generalization from the diagram.
     */
    public void deleteGeneralization( MGeneralization gen ) {
        EdgeBase e = (GeneralizationEdge) fGenToGeneralizationEdge.get( gen );
        if ( e == null ) {
            if ( fHiddenEdges.contains( gen ) ) {
                fHiddenEdges.remove( gen );
                fLog.println("Deleted generalization `" + gen.name()
                             + "' from the hidden associaitons.");
            } else {
                throw new RuntimeException("no edge for generalization `" 
                                           + gen.name() + "' in current state.");
            }
        }

        synchronized ( fLock ) {
            fGraph.removeEdge( e );
            fGenToGeneralizationEdge.remove( gen );
            fLayouter = null;
        }
    }
    
    /**
     * Creates and shows popup menu if mouse event is the trigger for popups.
     */
    public boolean maybeShowPopup( MouseEvent e ) {
        boolean separatorNeeded = false;
        
        if ( !e.isPopupTrigger() )
            return false;

        // create the popup menu
        JPopupMenu popupMenu = unionOfPopUpMenu();
        
        // position for the popupMenu items 
        int pos = 0;
        Set<MClass> selectedClassesOfAssociation = new HashSet<MClass>(); //jj
		Set<AssociationName> anames = new HashSet<AssociationName>(); // jj
        
		final Set<MClass> selectedClasses = new HashSet<MClass>(); // jj add final
		final Set<EnumType> selectedEnumeration = new HashSet<EnumType>();
		final Set<Object> selectedObjects = new HashSet<Object>();
		
        if ( !fNodeSelection.isEmpty() ) {
            Iterator<Selectable> nodeIterator = fNodeSelection.iterator();
            
            while ( nodeIterator.hasNext() ) {
                PlaceableNode node = (PlaceableNode) nodeIterator.next();
                if ( node instanceof ClassNode && node.isDeletable() ) {
                	ClassNode cn = (ClassNode)node;
                	selectedClasses.add( cn.cls() );
                    selectedObjects.add( cn.cls() );
                } else if ( node instanceof EnumNode && node.isDeletable() ) {
                    EnumNode eNode = (EnumNode)node;
                	selectedEnumeration.add(eNode.getEnum());
                	selectedObjects.add(eNode.getEnum());
                } else if (node instanceof AssociationName) { //jj
					selectedClassesOfAssociation.addAll(
							fSelection.getSelectedClassesOfAssociation(((AssociationName)node)));
					
					anames.add((AssociationName)node);//jj
                } //jj
            }
            
            String txt = null;
            if ( selectedObjects.size() == 1 ) {
                if ( selectedObjects.iterator().next() instanceof MClass ) {
                    txt = "'" + ((MClass) selectedClasses.iterator().next()).name() + "'";
                } else if ( selectedObjects.iterator().next() instanceof EnumType ) {
                    txt = "'" + ((EnumType) selectedObjects.iterator().next()).name() + "'";
                }
            } else if ( selectedClasses.size() > 1 ) {
                txt = selectedClasses.size() + " classes";
            }
            
            //anfangen jj
			if(selectedClasses.isEmpty() && !selectedClassesOfAssociation.isEmpty()) {
				String info;
				if (anames.size() == 1) {
					info = anames.iterator().next().name();
				}
				else{
					info = "" + anames.size();
				}
				
				popupMenu.insert(
						fHideAdmin.setValues("Crop association " + info, 
								getNoneSelectedNodes(selectedClassesOfAssociation)), pos++);
				
				popupMenu.insert(
						fHideAdmin.setValues("Hide association " + info, 
								selectedClassesOfAssociation), pos++);  //fixxx
				
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedAssociationPathView(
								"Selection association path length...", 
								selectedClassesOfAssociation, anames), pos++);
				
				popupMenu.insert(new JSeparator(), pos++);
				
			}
			//end jj
            
            if ( txt != null && txt.length() > 0 ) {
            	popupMenu.insert( fHideAdmin.setValues( "Crop " + txt,
                         getNoneSelectedNodes( selectedClasses ) ),
                         pos++ );
                popupMenu.insert( fHideAdmin.setValues( "Hide " + txt,
                                                        selectedClasses ),
                                                        pos++ );
                
				// pathlength view anfangs jj
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedClassPathView("Selection " + txt + " path length...", 
								 selectedClasses), pos++);
				popupMenu.insert(new JSeparator(),pos++);
				
				// TODO: Rename variable
				boolean havehide = fSelection.haveHideInObjectDiagram(fSelection.getAllKindClasses(selectedClasses));
				boolean haveshow = fSelection.haveShowInObjectDiagram(fSelection.getAllKindClasses(selectedClasses));
				
				if ( (haveshow || havehide) && MainWindow.instance().getObjectDiagrams().size() > 0) {
					if(haveshow) {
						
						popupMenu.insert(new AbstractAction("Hide all objects of " + txt) {
							@Override
							public void actionPerformed(ActionEvent e) {
								// Hide objects in all object diagrams
								Set<MClass> allClasses = fSelection.getAllKindClasses(selectedClasses);
																
								for (NewObjectDiagramView oDiagView : MainWindow.instance().getObjectDiagrams()) {
									NewObjectDiagram diagram = oDiagView.getDiagram();
									Set<MObject> allObjectsToHide = diagram.getObjectSelection().getDisplayedObjectsForClasses(allClasses);
									
									// If allObjects contains an object which is not shown, this
									// is handled by the HideAdministration
									Action hide = diagram.getHideAdmin().setValues("Internal", allObjectsToHide);
									hide.actionPerformed(null);
								}
							}}, pos++);
					}
					
					if(havehide){
						final JMenuItem showobj = new JMenuItem("Show all objects of " + txt);
						showobj.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ev) {
								// Show objects in all object diagrams
								Set<MClass> allClasses = fSelection.getAllKindClasses(selectedClasses);

								for (NewObjectDiagramView oDiagView : MainWindow.instance().getObjectDiagrams()) {
									NewObjectDiagram diagram = oDiagView.getDiagram();
									Set<MObject> allObjects = diagram.getObjectSelection().getHiddenObjects(allClasses);
									
									// If allObjects contains an object which is not hidden, this
									// is handled by the HideAdministration
									diagram.getHideAdmin().showHiddenElements(allObjects);
									diagram.repaint();
								}
							}
						});
						popupMenu.insert(showobj,pos++);
					}
					
					//crop all objects of selected classes
					final JMenuItem cropobj = new JMenuItem("Crop all objects of " + txt);
					cropobj.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ev) {
							Set<MClass> allClasses = fSelection.getAllKindClasses(selectedClasses);
							
							for (NewObjectDiagramView oDiagView : MainWindow.instance().getObjectDiagrams()) {
								NewObjectDiagram diagram = oDiagView.getDiagram();
								Set<MObject> objectsToHide = diagram.getObjectSelection().getCropHideObjects(allClasses);
								Set<MObject> objectsToShow = diagram.getObjectSelection().getHiddenObjects(allClasses);
								
								diagram.getHideAdmin().setValues("Hide", objectsToHide).actionPerformed(ev);							
								diagram.getHideAdmin().showHiddenElements(objectsToShow);

								diagram.repaint();
							}
						}
					});
					popupMenu.insert(cropobj,pos++);
				} // end jj
                separatorNeeded = true;
            }
        }
        
        if ( !fHiddenNodes.isEmpty() ) {
            final JMenuItem showAllClasses = new JMenuItem( "Show hidden classes" );
            showAllClasses.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    fHideAdmin.showAllHiddenElements();
                    repaint();
                }
            });

            popupMenu.insert( showAllClasses, pos++ );
            separatorNeeded = true;
        }

        if ( separatorNeeded ) {
            popupMenu.insert( new JSeparator(), pos++ );
            separatorNeeded = false;
        }
        
        // setting the right position for the popupMenu items 
        // from this point on.
        pos = pos + 3;
        
        final JCheckBoxMenuItem cbMultiplicities = 
            new JCheckBoxMenuItem( "Show multiplicities" );
        cbMultiplicities.setState( fOpt.isShowMutliplicities() );
        cbMultiplicities.addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent ev ) {
                fOpt.setShowMutliplicities( ev.getStateChange() 
                                            == ItemEvent.SELECTED );
                repaint();
            }
        } );

        final JCheckBoxMenuItem cbOperations =
            new JCheckBoxMenuItem("Show operations" );
        cbOperations.setState( fOpt.isShowOperations() );
        cbOperations.addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent ev ) {
                fOpt
                    .setShowOperations( ev.getStateChange() == ItemEvent.SELECTED );
                repaint();
            }
        } );

        popupMenu.insert( cbMultiplicities, pos++-1 );
        popupMenu.insert( cbOperations, pos++ );
        
//      jj anfangen this
		// etwas seleted
		if (fGraph.size() > 0 || fHiddenNodes.size() > 0) {
			popupMenu.addSeparator();
			if (fHiddenNodes.size() > 0) {
				popupMenu.add(fSelection.getSubMenuShowClass());
			}
			if (fGraph.size() > 0) {
				popupMenu.add(fSelection.getSubMenuHideClass());
			}
			
			popupMenu.add(fSelection.getSelectionClassView("Selection classes...", mouseHandling, selectedClasses));
			
		} // end jj
        
        popupMenu.show( e.getComponent(), e.getX(), e.getY() );
        return true;
    }

    /**
     * Finds all nodes which are not selected.
     * @param selectedNodes Nodes which are selected at this point in the diagram.
     * @return A Set of the none selected objects in the diagram.
     */
    private Set<Object> getNoneSelectedNodes( Set<?> selectedNodes ) {
        Set<Object> noneSelectedNodes = new HashSet<Object>();
        
        Iterator<NodeBase> it = fGraph.iterator();
        while ( it.hasNext() ) {
            NodeBase o = it.next();
            Object obj;
            
            if ( o instanceof ClassNode ) {
                obj = ((ClassNode) o).cls();
            } else if (o instanceof EnumNode) {
            	obj = ((EnumNode)o).getEnum();
            } else {
            	continue;
        	}
            
            if ( !selectedNodes.contains( obj ) ) {
                noneSelectedNodes.add( obj );
            }
        }
        
        return noneSelectedNodes;
    }

    /**
     * Deletes all hidden elements form this diagram.
     */
    @Override
    public void deleteHiddenElementsFromDiagram( Set<Object> nodesToHide, 
                                                 Set<Object> edgesToHide ) {
        Iterator<?> it = nodesToHide.iterator();
        while ( it.hasNext() ) {
            Object elem = it.next();
            if ( elem instanceof MClass ) {
                deleteClass( (MClass) elem );
            } else if ( elem instanceof EnumType ) {
                deleteEnum( (EnumType) elem );
            }
        }
        
        Set<MAssociation> assocsToDelete = new HashSet<MAssociation>();
        Set<MGeneralization> gensToDelete = new HashSet<MGeneralization>();
        it = edgesToHide.iterator();
        while ( it.hasNext() ) {
            MModelElement edge = (MModelElement) it.next();
            if ( edge instanceof MAssociation ) {
                assocsToDelete.add( (MAssociation) edge );
            } else if ( edge instanceof MGeneralization ) {
                gensToDelete.add( (MGeneralization) edge );
            }
        }
        
        for (MAssociation ass : assocsToDelete) {
            deleteAssociation( ass, true );
        }
        
        for (MGeneralization gen : gensToDelete) {
            deleteGeneralization( gen );
        }
        
        fHiddenNodes.addAll( nodesToHide );
        fHiddenEdges.addAll( edgesToHide );
    }
}
