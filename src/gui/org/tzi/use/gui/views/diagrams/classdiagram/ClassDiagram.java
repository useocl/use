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

// $Id: ClassDiagram.java 2048 2011-02-11 15:32:33Z lhamann $

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
import org.tzi.use.gui.views.diagrams.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.GeneralizationEdge;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.tzi.use.gui.views.diagrams.NAryAssociationClassOrObjectEdge;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSelectAll;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
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
import org.tzi.use.util.NamedElement;


/**
 * A panel drawing UML class diagrams.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public class ClassDiagram extends DiagramView 
                             implements HighlightChangeListener {

    public static class ClassDiagramData {
		/**
		 * 
		 */
		public Map<MClass, ClassNode> fClassToNodeMap;
		/**
		 * 
		 */
		public Map<EnumType, EnumNode> fEnumToNodeMap;
		/**
		 * 
		 */
		public Map<MAssociation, BinaryAssociationOrLinkEdge> fBinaryAssocToEdgeMap;
		/**
		 * 
		 */
		public Map<MAssociationClass, EdgeBase> fAssocClassToEdgeMap;
		/**
		 * 
		 */
		public Map<MAssociation, DiamondNode> fNaryAssocToDiamondNodeMap;
		/**
		 * 
		 */
		public Map<MAssociation, List<EdgeBase>> fNaryAssocToHalfEdgeMap;
		/**
		 * 
		 */
		public Map<MGeneralization, GeneralizationEdge> fGenToGeneralizationEdge;

		/**
		 * 
		 */
		public ClassDiagramData() {
			fClassToNodeMap = new HashMap<MClass, ClassNode>();
	        fEnumToNodeMap = new HashMap<EnumType, EnumNode>();
	        fBinaryAssocToEdgeMap = new HashMap<MAssociation, BinaryAssociationOrLinkEdge>();
	        fAssocClassToEdgeMap = new HashMap<MAssociationClass, EdgeBase>();
	        fNaryAssocToDiamondNodeMap = new HashMap<MAssociation, DiamondNode>();
	        fNaryAssocToHalfEdgeMap = new HashMap<MAssociation, List<EdgeBase>>();
	        fGenToGeneralizationEdge = new HashMap<MGeneralization, GeneralizationEdge>();
		}
		
		public boolean hasNodes() {
			return !(fClassToNodeMap.isEmpty() && fEnumToNodeMap.isEmpty());
		}
	}

	private ClassDiagramView fParent;
    
    private ClassDiagramData visibleData = new ClassDiagramData();

    private ClassDiagramData hiddenData = new ClassDiagramData();
    
	// jj anfangen
	private ClassSelection fSelection;
	private DiagramInputHandling inputHandling;
	// jj end
    
    ClassDiagram( ClassDiagramView parent, PrintWriter log ) {
        fOpt = new ClassDiagramOptions();
        fGraph = new DirectedGraphBase<NodeBase, EdgeBase>();
        
        fParent = parent;
        fHiddenNodes = new HashSet<Object>();
        fHiddenEdges = new HashSet<Object>();
        fNodeSelection = new Selection<PlaceableNode>();
        fEdgeSelection = new Selection<EdgeBase>();
        
        setLayout( null );
        setBackground( Color.white );
        fLog = log;
        setPreferredSize( Options.fDiagramDimension );

        fLayoutInfos = new LayoutInfos( visibleData.fBinaryAssocToEdgeMap, 
                                        visibleData.fClassToNodeMap, 
                                        visibleData.fNaryAssocToDiamondNodeMap,
                                        visibleData.fNaryAssocToHalfEdgeMap,
                                        visibleData.fAssocClassToEdgeMap,
                                        visibleData.fEnumToNodeMap,
                                        visibleData.fGenToGeneralizationEdge,
                                        fHiddenNodes, fHiddenEdges,
                                        fOpt, fParent.system(), this, 
                                        fLog );
        
        fHideAdmin = new HideAdministration( fNodeSelection, fGraph, fLayoutInfos );
        
        inputHandling = 
            new DiagramInputHandling( fNodeSelection, fEdgeSelection, this);
        
        fSelection = new ClassSelection(this);
        
        fActionSaveLayout = new ActionSaveLayout( "USE class diagram layout",
                                                  "clt", fGraph, fLog, fLayoutInfos );
        
        fActionLoadLayout = new ActionLoadLayout( "USE class diagram layout",
                                                  "clt", this, fLog,
                                                  fHideAdmin, fGraph,
                                                  fLayoutInfos );
        
        fActionSelectAll = new ActionSelectAll( fNodeSelection, visibleData.fClassToNodeMap,
                                                visibleData.fEnumToNodeMap, this );

        addMouseListener( inputHandling );
        fParent.addKeyListener( inputHandling );
                
        fParent.getModelBrowser().addHighlightChangeListener( this );
        
        addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                // need a new layouter to adapt to new window size
                fLayouter = null;
            }
        } );
        startLayoutThread();
    }

    public Map<MClass, ClassNode> getClassToNodeMap() {
    	return this.visibleData.fClassToNodeMap;
    }
    
    public Selection<PlaceableNode> getNodeSelection() {
		return this.fNodeSelection;
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
                    eb = visibleData.fBinaryAssocToEdgeMap.get( (MAssociation) elem );
                if ( elem instanceof MAssociationClass ) {
                    eb = visibleData.fAssocClassToEdgeMap.get( (MAssociationClass) elem );
                }
                edges.add( eb );
            } else {
                List<EdgeBase> halfEdges =  visibleData.fNaryAssocToHalfEdgeMap.get( (MAssociation) elem );
                if ( edges != null && halfEdges != null ) {
                    edges.addAll( halfEdges );
                }
                if ( elem instanceof MAssociationClass ) {
                    eb = visibleData.fAssocClassToEdgeMap.get( (MAssociationClass) elem );
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
            NodeBase node = visibleData.fClassToNodeMap.get( (MClass) elem );
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
                
        invalidateContent();
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
     * Shows all hidden elements again
     */
    public void showAll() {
    	while (hiddenData.fClassToNodeMap.size() > 0) {
    		showClass(hiddenData.fClassToNodeMap.keySet().iterator().next());
    	}
    	
    	while (hiddenData.fEnumToNodeMap.size() > 0) {
    		showEnum(hiddenData.fEnumToNodeMap.keySet().iterator().next());
    	}
    	
    	while (hiddenData.fGenToGeneralizationEdge.size() > 0) {
    		showGeneralization(hiddenData.fGenToGeneralizationEdge.keySet().iterator().next());
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
        int fNextNodeX = (int)(Math.random() * Math.max( 100, getWidth() ));
        int fNextNodeY = (int)(Math.random() * Math.max( 100, getHeight() ));
        ClassNode n = new ClassNode( cls, fOpt );
        n.setPosition( fNextNodeX, fNextNodeY );
        n.setMinWidth(minClassNodeWidth);
        n.setMinHeight(minClassNodeHeight);
        
        synchronized ( fLock ) {
            fGraph.add( n );
            visibleData.fClassToNodeMap.put( cls, n );
            fLayouter = null;
        }
    }

    /**
     * Hides a class from the diagram.
     */
    public void hideClass( MClass cls ) {
    	showOrHideClassNode(cls, false);
    	
    	// Remove all generalization edges
    	Set<MGeneralization> gens = fParent.system().model().generalizationGraph().allEdges(cls);
    	
    	for (MGeneralization gen : gens) {
    		hideGeneralization(gen);
    	}
    	
    	// Remove all associations
    	for (MAssociation assoc : cls.associations()) {
    		hideAssociation(assoc);
    	}
    }
    
    /**
     * Shows an already hidden class again
     * @param cls
     */
    public void showClass( MClass cls ) {
    	showOrHideClassNode(cls, true);
    	
    	// Add all generalization edges, if nodes are present
    	Set<MGeneralization> gens = cls.model().generalizationGraph().allEdges(cls);
    	for (MGeneralization gen : gens) {
    		if (visibleData.fClassToNodeMap.containsKey(gen.child()) &&
    			visibleData.fClassToNodeMap.containsKey(gen.parent())) {
    			showGeneralization(gen);
    		}
    	}
    	
    	// Remove all associations
    	for (MAssociation assoc : cls.associations()) {
    		boolean allsEndsVisible = true;
    		for (MAssociationEnd end : assoc.associationEnds()) {
    			if (!visibleData.fClassToNodeMap.containsKey(end.cls())) {
    				allsEndsVisible = false;
    				break;
    			}
    		}	
    		
    		if (allsEndsVisible)
    			showAssociation(assoc);
    	}
    }
    
    /**
     * Shows an already hidden class.
     */
    protected void showOrHideClassNode( MClass cls, boolean show ) {
    	ClassDiagramData source = (show ? hiddenData : visibleData);
    	ClassDiagramData target = (show ? visibleData : hiddenData);
    	
    	ClassNode n = source.fClassToNodeMap.get( cls );
        
        if (n != null) {
        	synchronized ( fLock ) {
                if (show) 
                	fGraph.add( n );
                else
                	fGraph.remove(n);
                
                source.fClassToNodeMap.remove( cls );
                target.fClassToNodeMap.put(cls, n);
                
                fLayouter = null;
            }
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
        int fNextNodeX = (int)(Math.random() * Math.max( 100, getWidth() ));
        int fNextNodeY = (int)(Math.random() * Math.max( 100, getHeight() ));
        EnumNode n = new EnumNode( enumeration, fOpt );
        n.setPosition( fNextNodeX, fNextNodeY );

        synchronized ( fLock ) {
            fGraph.add( n );
            visibleData.fEnumToNodeMap.put( enumeration, n );
            fLayouter = null;
        }
    }

    /**
     * Hides an enumeration from the diagram.
     */
    public void hideEnum( EnumType enumeration ) {
        showOrHideEnum(enumeration, false);
    }
    
    /**
     * Shows an hidden enumeration in the diagram again.
     */
    public void showEnum( EnumType enumeration ) {
        showOrHideEnum(enumeration, true);
    }
    
    public void showOrHideEnum( EnumType enumeration, boolean show ) {
    	ClassDiagramData source = (show ? hiddenData : visibleData);
    	ClassDiagramData target = (show ? visibleData : hiddenData);
    	
    	EnumNode n = source.fEnumToNodeMap.get( enumeration );
        
        if (n != null) {
        	synchronized ( fLock ) {
                if (show)
                	fGraph.add( n );
                else
                	fGraph.remove( n );
                
                source.fEnumToNodeMap.remove( enumeration );
                target.fEnumToNodeMap.put(enumeration, n);
                fLayouter = null;
            }
        }
    }
    
    /**
     * Adds an association to the diagram.
     */
    public void addAssociation( MAssociation assoc ) {
    	if (assoc.associationEnds().size() == 2) {
    		addBinaryAssociation(assoc);
    	} else {
    		addNAryAssociation(assoc);
    	}
    }
    
    public void showAssociation( MAssociation assoc) {
		if (assoc.associationEnds().size() == 2) {
    		showBinaryAssociation(assoc);
    	} else {
    		showNAryAssociation(assoc);
    	}
		
		if (assoc instanceof MAssociationClass) {
    		showClass((MClass)assoc);
    	}
	}
    
    /**
     * Hides an association in the diagram.
     */
    public void hideAssociation( MAssociation assoc ) {
    	if (assoc.associationEnds().size() == 2) {
    		hideBinaryAssociation(assoc);
    	} else {
    		hideNAryAssociation(assoc);
    	}
    	
    	if (assoc instanceof MAssociationClass) {
    		hideClass((MClass)assoc);
    	}
    }
    
    protected void addBinaryAssociation(MAssociation assoc) {
    	Iterator<MAssociationEnd> assocEndIter = assoc.associationEnds().iterator();
        MAssociationEnd assocEnd1 = assocEndIter.next();
        MAssociationEnd assocEnd2 = assocEndIter.next();
        MClass cls1 = assocEnd1.cls();
        MClass cls2 = assocEnd2.cls();

        // association class
        if ( assoc instanceof MAssociationClass ) {
            BinaryAssociationClassOrObject e = 
                new BinaryAssociationClassOrObject( 
                			  visibleData.fClassToNodeMap.get( cls1 ),
                			  visibleData.fClassToNodeMap.get( cls2 ),
                              assocEnd1, assocEnd2,
                              visibleData.fClassToNodeMap.get( assoc ),
                              this, assoc, false );
            synchronized (fLock) {
                fGraph.addEdge(e);
                visibleData.fAssocClassToEdgeMap.put( (MAssociationClass)assoc, e );
                fLayouter = null;
            }
        } else {
        	NodeBase source;
        	NodeBase target;
        	
        	// for reflexive associations with exactly one qualifier
        	// the qualifier end must be the source!
        	if (assoc.associatedClasses().size() == 1 && 
        		assocEnd2.hasQualifiers() && !assocEnd1.hasQualifiers()) {
        		MAssociationEnd temp = assocEnd1;
        		assocEnd1 = assocEnd2;
        		assocEnd2 = temp;
        		source = visibleData.fClassToNodeMap.get(cls2);
        		target = visibleData.fClassToNodeMap.get(cls1);
        	} else {
        		source = visibleData.fClassToNodeMap.get(cls1);
        		target = visibleData.fClassToNodeMap.get(cls2);
        	}
        	
            // binary association
        	BinaryAssociationOrLinkEdge e = 
                new BinaryAssociationOrLinkEdge( source, 
                                		   	     target, 
                                		   	     assocEnd1, assocEnd2, 
                                		   	     this, assoc );
            synchronized ( fLock ) {
                fGraph.addEdge(e);
                visibleData.fBinaryAssocToEdgeMap.put(assoc, e);
                fLayouter = null;
            }
        }
    }

    protected void addNAryAssociation(MAssociation assoc) {
    	Iterator<MAssociationEnd> assocEndIter = assoc.associationEnds().iterator();
        MAssociationEnd assocEnd1 = assocEndIter.next();
        MAssociationEnd assocEnd2 = assocEndIter.next();
        
        MClass cls1 = assocEnd1.cls();
        MClass cls2 = assocEnd2.cls();
        
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
                NAryAssociationClassOrObjectEdge e = 
                    new NAryAssociationClassOrObjectEdge( visibleData.fClassToNodeMap.get( cls1 ), 
                                  visibleData.fClassToNodeMap.get( cls2 ),
                                  node, 
                                  visibleData.fClassToNodeMap.get( assoc ),
                                  this, assoc, false );
                
                fGraph.addEdge(e);
                visibleData.fAssocClassToEdgeMap.put( (MAssociationClass)assoc, e );
                fLayouter = null;
            }
            
            // connected to a "normal" class
            visibleData.fNaryAssocToDiamondNodeMap.put( assoc, node );
            List<EdgeBase> halfEdges = new ArrayList<EdgeBase>();
            assocEndIter = assoc.associationEnds().iterator();
            while ( assocEndIter.hasNext() ) {
                MAssociationEnd assocEnd = (MAssociationEnd) assocEndIter.next();
                MClass cls = assocEnd.cls();
                AssociationOrLinkPartEdge e = 
                    new AssociationOrLinkPartEdge(node, visibleData.fClassToNodeMap.get( cls ), assocEnd, this, assoc, false );
                fGraph.addEdge(e);
                halfEdges.add( e );
            }
            node.setHalfEdges( halfEdges );
            visibleData.fNaryAssocToHalfEdgeMap.put( assoc, halfEdges );
            fLayouter = null;
        }
    }

	protected void hideBinaryAssociation(MAssociation assoc) {
		showOrHideBinaryAssociation(assoc, false);
	}
	
	protected void showBinaryAssociation(MAssociation assoc) {
		showOrHideBinaryAssociation(assoc, true);
	}
	
	protected void showOrHideBinaryAssociation(MAssociation assoc, boolean show) {
		ClassDiagramData source = (show ? hiddenData : visibleData);
    	ClassDiagramData target = (show ? visibleData : hiddenData);
    	
		EdgeBase e = null;
		
        if ( assoc instanceof MAssociationClass ) { 
            e = source.fAssocClassToEdgeMap.get( assoc );
        } else {
            e = source.fBinaryAssocToEdgeMap.get( assoc );
        }

        if (e != null) {
	        synchronized (fLock) {
	            if (show) 
	            	fGraph.addEdge( e );
	            else
	            	fGraph.removeEdge( e );
	            
	            if ( assoc instanceof MAssociationClass ) {
	            	source.fAssocClassToEdgeMap.remove( assoc );
	            	target.fAssocClassToEdgeMap.put((MAssociationClass)assoc, e);
	            } else {
	            	source.fBinaryAssocToEdgeMap.remove( assoc );
	            	target.fBinaryAssocToEdgeMap.put(assoc, (BinaryAssociationOrLinkEdge)e);
	            }
	            fLayouter = null;
	        }
        }
	}

	protected void hideNAryAssociation(MAssociation assoc) {
        showOrHideNAryAssociation(assoc, false);
    }
	
	protected void showNAryAssociation(MAssociation assoc) {
        showOrHideNAryAssociation(assoc, true);
    }
	
	protected void showOrHideNAryAssociation(MAssociation assoc, boolean show) {
		ClassDiagramData source = (show ? hiddenData : visibleData);
    	ClassDiagramData target = (show ? visibleData : hiddenData);
    	
		DiamondNode n = source.fNaryAssocToDiamondNodeMap.get( assoc );

        if (n != null) {
            synchronized ( fLock ) {
                // all dangling HalfLinkEdges are removed by the graph
            	if (show)
            		fGraph.add( n );
            	else
            		fGraph.remove( n );
            	
                source.fNaryAssocToDiamondNodeMap.remove( assoc );
                target.fNaryAssocToDiamondNodeMap.put(assoc, n);
                
                List<EdgeBase> values = source.fNaryAssocToHalfEdgeMap.remove( assoc );
                target.fNaryAssocToHalfEdgeMap.put(assoc, values);
                
                fLayouter = null;

                if ( assoc instanceof MAssociationClass ) {
                    EdgeBase edge = source.fAssocClassToEdgeMap.get( assoc );
                    if ( edge != null ) {
                        if (show)
                        	fGraph.addEdge(edge);
                        else
                        	fGraph.removeEdge( edge );
                        
                        source.fAssocClassToEdgeMap.remove( assoc );
                        target.fAssocClassToEdgeMap.put((MAssociationClass)assoc, edge);
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
            new GeneralizationEdge( visibleData.fClassToNodeMap.get( child ),
                                    visibleData.fClassToNodeMap.get( parent ), this );
        synchronized ( fLock ) {
            fGraph.addEdge( e );
            visibleData.fGenToGeneralizationEdge.put( gen, e );
            fLayouter = null;
        }
    }

    /**
     * Hides a generalization in the diagram.
     */
    public void hideGeneralization( MGeneralization gen ) {
    	showOrHideGeneralization(gen, false);
    }
    
    /**
     * Shows an already hidden generalization in the diagram again.
     */
    public void showGeneralization( MGeneralization gen ) {
    	showOrHideGeneralization(gen, true);
    }
    
    public void showOrHideGeneralization(MGeneralization gen, boolean show) {
    	ClassDiagramData source = (show ? hiddenData : visibleData);
    	ClassDiagramData target = (show ? visibleData : hiddenData);
    	
    	GeneralizationEdge e = source.fGenToGeneralizationEdge.get( gen );
        
        if ( e != null ) {
        	synchronized ( fLock ) {
                if (show)
                	fGraph.addEdge( e );
                else
                	fGraph.removeEdge( e );
                
                source.fGenToGeneralizationEdge.remove( gen );
                target.fGenToGeneralizationEdge.put(gen, e);
                fLayouter = null;
            }
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
		
		final Set<NamedElement> selectedObjects = new HashSet<NamedElement>();
				
        if ( !fNodeSelection.isEmpty() ) {
            for (PlaceableNode node : fNodeSelection) {
                if ( node instanceof ClassNode && node.isDeletable() ) {
                	ClassNode cn = (ClassNode)node;
                	selectedClasses.add( cn.cls() );
                    selectedObjects.add( cn.cls() );
                } else if ( node instanceof EnumNode && node.isDeletable() ) {
                    EnumNode eNode = (EnumNode)node;
                	selectedObjects.add(eNode.getEnum());
                } else if (node instanceof AssociationName) { //jj
					selectedClassesOfAssociation.addAll(
							fSelection.getSelectedClassesOfAssociation(((AssociationName)node).getAssociation()));
					
					anames.add((AssociationName)node);//jj
                } //jj
            }
            
            String txt = null;
            if ( selectedObjects.size() == 1 ) {
                NamedElement m = selectedObjects.iterator().next();
                txt = "'" + m.name() + "'";
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
						fHideAdmin.getAction("Crop association " + info, 
								getNoneSelectedNodes(selectedClassesOfAssociation)), pos++);
				
				popupMenu.insert(
						fHideAdmin.getAction("Hide association " + info, 
								selectedClassesOfAssociation), pos++);  //fixxx
				
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedAssociationPathView(
								"Selection association path length...", 
								selectedClassesOfAssociation, anames), pos++);
				
				popupMenu.insert(new JSeparator(), pos++);
				
			}
			//end jj
            
            if ( txt != null && txt.length() > 0 ) {
            	popupMenu.insert( fHideAdmin.getAction( "Crop " + txt, getNoneSelectedNodes( selectedObjects ) ), pos++ );
                popupMenu.insert( fHideAdmin.getAction( "Hide " + txt, selectedObjects ), pos++ );
                
				// pathlength view anfangs jj
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedClassPathView("Selection " + txt + " path length...", 
								 selectedClasses), pos++);
				popupMenu.insert(new JSeparator(),pos++);
				
				boolean hasHiddenObjects = fSelection.classHasHiddenObjects(fSelection.getAllKindClasses(selectedClasses));
				boolean hasShownObjects = fSelection.classHasDisplayedObjects(fSelection.getAllKindClasses(selectedClasses));
				
				if ( (hasShownObjects || hasHiddenObjects) && MainWindow.instance().getObjectDiagrams().size() > 0) {
					if(hasShownObjects) {
						
						popupMenu.insert(new AbstractAction("Hide all objects of " + txt) {
							public void actionPerformed(ActionEvent e) {
								// Hide objects in all object diagrams
								Set<MClass> allClasses = fSelection.getAllKindClasses(selectedClasses);
																
								for (NewObjectDiagramView oDiagView : MainWindow.instance().getObjectDiagrams()) {
									NewObjectDiagram diagram = oDiagView.getDiagram();
									Set<MObject> allObjectsToHide = diagram.getObjectSelection().getDisplayedObjectsForClasses(allClasses);
									
									// If allObjects contains an object which is not shown, this
									// is handled by the HideAdministration
									Action hide = diagram.getHideAdmin().getAction("Internal", allObjectsToHide);
									hide.actionPerformed(null);
								}
							}}, pos++);
					}
					
					if(hasHiddenObjects){
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
								
								diagram.getHideAdmin().getAction("Hide", objectsToHide).actionPerformed(ev);							
								diagram.getHideAdmin().showHiddenElements(objectsToShow);
							}
						}
					});
					popupMenu.insert(cropobj,pos++);
				} // end jj
                separatorNeeded = true;
            }
        }
        
        if ( hiddenData.hasNodes() ) {
            final JMenuItem showAllClasses = new JMenuItem( "Show hidden classes" );
            showAllClasses.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    fHideAdmin.showAllHiddenElements();
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
			
			popupMenu.add(fSelection.getSelectionClassView("Selection classes...", inputHandling, selectedClasses));
			
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
        List<NodeBase> nodes = fGraph.getNodes();
        nodes.removeAll(selectedNodes);
        return new HashSet<Object>(nodes);
    }

    /**
     * Hides the given elements in this diagram.
     */
    @Override
    public void hideElementsInDiagram( Set<Object> nodesToHide ) {
        for (Object elem : nodesToHide) {
            if ( elem instanceof MClass ) {
                hideClass( (MClass) elem );
            } else if ( elem instanceof EnumType ) {
                hideEnum( (EnumType) elem );
            }
        }
    }
}
