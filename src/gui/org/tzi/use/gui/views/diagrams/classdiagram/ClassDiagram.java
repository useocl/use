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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.xml.xpath.XPathConstants;

import org.tzi.use.analysis.coverage.CoverageAnalyzer;
import org.tzi.use.analysis.coverage.CoverageData;
import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser.SelectionChangedListener;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.GeneralizationEdge;
import org.tzi.use.gui.views.diagrams.NAryAssociationClassOrObjectEdge;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.event.ActionHideClassDiagram;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.selection.classselection.ClassSelection;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.Annotatable;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MNamedElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.NullPrintWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * A panel drawing UML class diagrams.
 * 
 * @version $ProjectVersion: 2-3-1-release.3 $
 * @author Fabian Gutsche
 */
@SuppressWarnings("serial")
public class ClassDiagram extends DiagramView 
                             implements HighlightChangeListener, SelectionChangedListener {

    public static class ClassDiagramData implements DiagramData {
		/**
		 * All mappings from (association-)classes to nodes.
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

		/* (non-Javadoc)
		 * @see org.tzi.use.gui.views.diagrams.DiagramView.DiagramData#getNodes()
		 */
		@Override
		public Set<PlaceableNode> getNodes() {
			Set<PlaceableNode> result = new HashSet<PlaceableNode>(fClassToNodeMap.values());
			result.addAll(fEnumToNodeMap.values());
			return result;
		}

		/**
		 * Searches for the given classifier and returns the node
		 * which represents it.
		 * @param cf
		 * @return
		 */
		public PlaceableNode getNode(MClassifier cf) {
			PlaceableNode n = this.fClassToNodeMap.get(cf);
			if (n != null) return n;
			return this.fEnumToNodeMap.get(cf);
		}
		
		public boolean containsNodeForClassifer(MClassifier cf) {
			return this.fClassToNodeMap.containsKey(cf) || this.fEnumToNodeMap.containsKey(cf);
		}

		/**
		 * Returns all placeable nodes for the given classifiers.
		 * @param selected
		 * @return
		 */
		public Set<PlaceableNode> getNodes(Set<MClassifier> classifier) {
			Set<PlaceableNode> nodes = new HashSet<PlaceableNode>();
			for (MClassifier cf : classifier) {
				PlaceableNode node = getNode(cf);
				if (node != null)
					nodes.add(node);
			}
			return nodes;
		}
	}

	private ClassDiagramView fParent;
    
    private ClassDiagramData visibleData = new ClassDiagramData();

    private ClassDiagramData hiddenData = new ClassDiagramData();
    
    /**
     * True if a user loads or saves a layout
     */
    private boolean hasUserDefinedLayout = false;
    
	// jj anfangen
	private ClassSelection fSelection;
	private DiagramInputHandling inputHandling;
	// jj end
    
    ClassDiagram( ClassDiagramView parent, PrintWriter log ) {
    	super(new ClassDiagramOptions(), log);
        
        fParent = parent;
        
        inputHandling = 
            new DiagramInputHandling( fNodeSelection, fEdgeSelection, this);
        
        fSelection = new ClassSelection(this);
        
        fActionSaveLayout = new ActionSaveLayout( "USE class diagram layout",
                                                  "clt", this );
        
        fActionLoadLayout = new ActionLoadLayout( "USE class diagram layout",
                                                  "clt", this);

        addMouseListener( inputHandling );
        fParent.addKeyListener( inputHandling );
                
        fParent.getModelBrowser().addHighlightChangeListener( this );
        fParent.getModelBrowser().addSelectionChangedListener( this );
        
        addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                // need a new layouter to adapt to new window size
                fLayouter = null;
            }
        } );
        startLayoutThread();
    }

    public MSystem getSystem() {
    	return fParent.system();
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
				List<EdgeBase> halfEdges = visibleData.fNaryAssocToHalfEdgeMap.get((MAssociation) elem);
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

    @Override
    public void showAll() {
    	while (hiddenData.fClassToNodeMap.size() > 0) {
    		showClass(hiddenData.fClassToNodeMap.keySet().iterator().next());
    	}
    	
    	while (hiddenData.fEnumToNodeMap.size() > 0) {
    		showEnum(hiddenData.fEnumToNodeMap.keySet().iterator().next());
    	}
    }
    
    @Override
    public void hideAll() {
    	while (visibleData.fClassToNodeMap.size() > 0) {
    		hideClass(visibleData.fClassToNodeMap.keySet().iterator().next());
    	}
    	
    	while (visibleData.fEnumToNodeMap.size() > 0) {
    		hideEnum(visibleData.fEnumToNodeMap.keySet().iterator().next());
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
    	if (!visibleData.fClassToNodeMap.containsKey(cls))
    		return;
    	
    	showOrHideClassNode(cls, false);
    	
    	// Remove all generalization edges
    	Set<MGeneralization> gens = fParent.system().model().generalizationGraph().allEdges(cls);
    	
    	for (MGeneralization gen : gens) {
    		hideGeneralization(gen);
    	}
    	
    	// Remove all associations
    	Set<MAssociation> associations = cls.associations();
    	if (cls instanceof MAssociationClass) {
    		associations.add((MAssociationClass)cls);
    	}
    	
    	for (MAssociation assoc : associations) {
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
    	Set<MAssociation> associations = cls.associations();
    	if (cls instanceof MAssociationClass) {
    		associations.add((MAssociation)cls);
    	}
    	
    	for (MAssociation assoc : associations) {
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
        		if (!show && this.getNodeSelection().isSelected(n))
        			this.getNodeSelection().remove(n);
        		
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
        		if (!show && this.getNodeSelection().isSelected(n))
        			this.getNodeSelection().remove(n);
        		
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
    
    public void showAssociation( MAssociation assoc ) {
		if (assoc.associationEnds().size() == 2) {
    		showBinaryAssociation(assoc);
    	} else {
    		showNAryAssociation(assoc);
    	}
		
		if (assoc instanceof MAssociationClass
				&& !visibleData.fAssocClassToEdgeMap.containsKey(assoc)) {
			showClass((MClass) assoc);
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
                              this, assoc );
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
                    new NAryAssociationClassOrObjectEdge(
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
            
            for (MAssociationEnd assocEnd : assoc.associationEnds()) {
                MClass cls = assocEnd.cls();
                AssociationOrLinkPartEdge e = 
                    new AssociationOrLinkPartEdge(node, visibleData.fClassToNodeMap.get( cls ), assocEnd, this, assoc, null );
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
                
                for (EdgeBase e : values) {
                	if (show)
                		fGraph.addEdge(e);
                	else
                		fGraph.removeEdge(e);
                }
                
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
		Set<MAssociation> selectedAssociations = new HashSet<MAssociation>(); // jj
        
		final Set<MClass> selectedClasses = new HashSet<MClass>(); // jj add final
		
		final Set<MClassifier> selectedClassifier = new HashSet<MClassifier>();
				
        if ( !fNodeSelection.isEmpty() ) {
            for (PlaceableNode node : fNodeSelection) {
                if ( node instanceof ClassNode && node.isDeletable() ) {
                	ClassNode cn = (ClassNode)node;
                	selectedClasses.add( cn.cls() );
                    selectedClassifier.add( cn.cls() );
                } else if ( node instanceof EnumNode && node.isDeletable() ) {
                    EnumNode eNode = (EnumNode)node;
                	selectedClassifier.add(eNode.getEnum());
                } else if (node instanceof AssociationName) { //jj
                	MAssociation assoc = ((AssociationName) node).getAssociation();
					selectedClassesOfAssociation.addAll(assoc.associatedClasses());
					selectedAssociations.add(assoc);//jj
                } //jj
            }
            
            //begin jj
			if(selectedClasses.isEmpty() && !selectedClassesOfAssociation.isEmpty()) {
				String info;
				if (selectedAssociations.size() == 1) {
					info = selectedAssociations.iterator().next().name();
				}
				else{
					info = "" + selectedAssociations.size();
				}
				
				popupMenu.insert(
						getAction("Crop association " + info, 
								getNoneSelectedElementsByElements(selectedClassesOfAssociation)), pos++);
				
				popupMenu.insert(
						getAction("Hide association " + info, 
								selectedClassesOfAssociation), pos++);  //fixxx
				
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedAssociationPathView(
								"Selection association path length...", 
								selectedClassesOfAssociation, selectedAssociations), pos++);
				
				popupMenu.insert(new JSeparator(), pos++);
				
			}
			//end jj
            
			String txt = null;
            if ( selectedClassifier.size() == 1 ) {
                MNamedElement m = selectedClassifier.iterator().next();
                txt = "'" + m.name() + "'";
            } else if ( selectedClasses.size() > 1 ) {
                txt = selectedClasses.size() + " classes";
            }
            
            if ( txt != null && txt.length() > 0 ) {
            	popupMenu.insert( getAction( "Crop " + txt, getNoneSelectedElementsByElements( selectedClassifier ) ), pos++ );
                popupMenu.insert( getAction( "Hide " + txt, selectedClassifier ), pos++ );
                
				// pathlength view anfangs jj
				popupMenu.insert(new JSeparator(),pos++);
				popupMenu.insert(fSelection.getSelectedClassPathView("Selection " + txt + " path length...", 
								 selectedClasses), pos++);
				
				boolean hasHiddenObjects = fSelection.classHasHiddenObjects(fSelection.getAllKindClasses(selectedClasses));
				boolean hasShownObjects = fSelection.classHasDisplayedObjects(fSelection.getAllKindClasses(selectedClasses));
				
				if ( (hasShownObjects || hasHiddenObjects) && MainWindow.instance().getObjectDiagrams().size() > 0) {
					popupMenu.insert(new JSeparator(),pos++);
					if(hasShownObjects) {
						popupMenu.insert(new AbstractAction("Hide all objects of " + txt) {
							public void actionPerformed(ActionEvent e) {
								// Hide objects in all object diagrams
								Set<MClass> allClasses = fSelection.getAllKindClasses(selectedClasses);
																
								for (NewObjectDiagramView oDiagView : MainWindow.instance().getObjectDiagrams()) {
									NewObjectDiagram diagram = oDiagView.getDiagram();
									Set<MObject> allObjectsToHide = diagram.getObjectSelection().getDisplayedObjectsForClasses(allClasses);
									diagram.hideElementsInDiagram(allObjectsToHide);
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
									diagram.showObjects(allObjects);
									diagram.invalidateContent();
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
								
								diagram.getAction("Hide", objectsToHide).actionPerformed(ev);							
								diagram.showObjects(objectsToShow);
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
                    showAll();
                    invalidateContent();
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
        

        final JCheckBoxMenuItem cbCoverage =
                new JCheckBoxMenuItem("Show coverage" );
        cbCoverage.setState( fOpt.isShowCoverage() );
        cbCoverage.addItemListener( new ItemListener() {
                public void itemStateChanged( ItemEvent ev ) {
                    fOpt.setShowCoverage( ev.getStateChange() == ItemEvent.SELECTED );
                    setCoverageColor();
                }
            } );

        popupMenu.insert( cbCoverage, pos++ );
        
        // jj anfangen this
		if (fGraph.size() > 0 || hiddenData.hasNodes()) {
			popupMenu.addSeparator();
			if (hiddenData.hasNodes()) {
				popupMenu.add(fSelection.getSubMenuShowClass());
			}
			if (fGraph.size() > 0) {
				popupMenu.add(fSelection.getSubMenuHideClass());
			}
			
			popupMenu.add(fSelection.getSelectionClassView("Selection classes..."));
			
		} // end jj
        
		final JMenuItem cbExport =
                new JMenuItem("Export visible elements as new model..." );
        cbExport.addActionListener(new ActionListener() {
        	File lastFile = null;
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.YES_OPTION;

				JFileChooser fChooser = new JFileChooser(Options.getLastDirectory());
				ExtFileFilter filter = new ExtFileFilter("use", "USE model");
				fChooser.addChoosableFileFilter(filter);
				fChooser.setDialogTitle("Save visible elements as model...");
		        
		        if (lastFile != null && lastFile.exists()
						&& lastFile.getParent().equals(Options.getLastDirectory())) {
					fChooser.setSelectedFile(lastFile);
				}
		        
		        do {
		            int returnVal = fChooser.showSaveDialog( ClassDiagram.this );
		            if (returnVal != JFileChooser.APPROVE_OPTION)
		                return;

		            Options.setLastDirectory(fChooser.getCurrentDirectory().toString());
		            String filename = fChooser.getSelectedFile().getName();

		            // if file does not have the appendix .use
		            int dot = filename.lastIndexOf(".");
		            if (dot == -1 || !filename.substring(dot, 
		                                       filename.length()).trim()
		                                       .equals( ".use" )) {
		                filename += ".use";
		            }

		            lastFile = new File(Options.getLastDirectory(), filename);
		            
		            if (lastFile.exists()) {
		                option = JOptionPane.showConfirmDialog(ClassDiagram.this,
		                        "Overwrite existing file " + lastFile + "?",
		                        "Please confirm", JOptionPane.YES_NO_CANCEL_OPTION);
		                if (option == JOptionPane.CANCEL_OPTION) {
		                    return;
		                }

		            }
		            // display the saving dialog, as long as the file
		            // will be overwritten or cancel is pressed.
		        } while (option != JOptionPane.YES_OPTION);
				exportVisibleClassesAsModel(lastFile);
			}
		});

        popupMenu.insert( cbExport, pos++ );
        popupMenu.show( e.getComponent(), e.getX(), e.getY() );
        return true;
    }

    /**
     * Finds all elements (class or enum node) which are not selected.
     * @param selectedElements Nodes which are selected at this point in the diagram.
     * @return A Set of the none selected objects in the diagram.
     */
    private Set<MClassifier> getNoneSelectedElementsByElements( Set<? extends MClassifier> selectedElements ) {
    	Set<MClassifier> noneSelectedElements = new HashSet<MClassifier>();
        
        Iterator<NodeBase> it = fGraph.iterator();
        MClassifier namedElement;
        
        while ( it.hasNext() ) {
            NodeBase o = it.next();
            
            if ( o instanceof ClassNode ) {
            	namedElement = ((ClassNode) o).cls();
            } else if (o instanceof EnumNode) {
            	namedElement = ((EnumNode)o).getEnum();
            } else {
            	continue;
            }
            
            if ( !selectedElements.contains( namedElement ) ) {
            	noneSelectedElements.add( namedElement );
            }
        }
        
        return noneSelectedElements;
    }
    
    /**
     * Hides the given elements in this diagram.
     * @param nodesToHide A set of {@link MClassifier} ({@link MClass} or {@link EnumType}) to hide
     */
    public void hideElementsInDiagram( Set<MClassifier> nodesToHide ) {
        for (Object elem : nodesToHide) {
            if ( elem instanceof MClass ) {
                hideClass( (MClass) elem );
            } else if ( elem instanceof EnumType ) {
                hideEnum( (EnumType) elem );
            }
        }
    }
    
    /**
     * Hides the given elements in this diagram.
     * @param nodesToHide A set of {@link MClassifier} ({@link MClass} or {@link EnumType}) to hide
     */
    public void showElementsInDiagram( Set<?> nodesToShow ) {
        for (Object elem : nodesToShow) {
            if ( elem instanceof MClass ) {
                showClass( (MClass) elem );
            } else if ( elem instanceof EnumType ) {
                showEnum( (EnumType) elem );
            }
        }
    }
    
    protected File getDefaultLayoutFile() {
		if (this.getSystem().model().getModelDirectory() == null)
    		return null;
    	
		File modelFile = new File(this.getSystem().model().filename()); 
		String fileNameOnly = modelFile.getName();
		if (fileNameOnly.contains(".")) {
			fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf('.'));
		}
		
    	File defaultLayoutFile = new File(this.getSystem().model().getModelDirectory(),  fileNameOnly + "_default.clt");
		return defaultLayoutFile;
	}
    
    /**
     * Tries to load the default layout from the file "default.clt" in the same
     * directory as the model file.
     * If no such file is present, nothing is done. 
     */
    protected void loadDefaultLayout() {
    	File defaultLayoutFile = getDefaultLayoutFile();
    	
    	if (defaultLayoutFile == null || !defaultLayoutFile.exists()) 
    		return;
    	
    	loadLayout(defaultLayoutFile);
    	hasUserDefinedLayout = false;
    }

    /* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#afterLoadLayout(java.io.File)
	 */
	@Override
	protected void afterLoadLayout(File layoutFile) {
		super.afterLoadLayout(layoutFile);
		hasUserDefinedLayout = true;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#afterSaveLayout(java.io.File)
	 */
	@Override
	protected void afterSaveLayout(File layoutFile) {
		super.afterSaveLayout(layoutFile);
		hasUserDefinedLayout = true;
	}

	/**
     * Saves the current layout to the file "default.clt" if no other
     * layout was loaded or saved.
     */
    private void saveDefaultLayout() {
    	if (this.hasUserDefinedLayout) return;
    	    	
    	File defaultLayoutFile = getDefaultLayoutFile();
    	if (defaultLayoutFile == null) 
    		return;
    	
    	saveLayout(defaultLayoutFile);
    	
    	hasUserDefinedLayout = false;
    }

    
    /* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#onClosing()
	 */
	@Override
	protected void onClosing() {
		super.onClosing();
		fParent.getModelBrowser().removeHighlightChangeListener( this );
        fParent.getModelBrowser().removeSelectionChangedListener( this );
        saveDefaultLayout();
	}

	@Override
    public void storePlacementInfos(PersistHelper helper, Element parent) {
    	storePlacementInfos(helper, parent, true);
    	storePlacementInfos(helper, parent, false);
    }
    
    protected void storePlacementInfos(PersistHelper helper, Element parent, boolean visible) {
    	ClassDiagramData data = (visible ? visibleData : hiddenData);
    	
    	// store node positions in property object
        for (ClassNode n : data.fClassToNodeMap.values()) {
            n.storePlacementInfo( helper, parent, !visible );
        }

        for (EnumNode n : data.fEnumToNodeMap.values()) {
            n.storePlacementInfo( helper, parent, !visible );
        }
        
        for (DiamondNode n : data.fNaryAssocToDiamondNodeMap.values()) {
        	n.storePlacementInfo( helper, parent, !visible );
        }

        for (BinaryAssociationOrLinkEdge e : data.fBinaryAssocToEdgeMap.values()) {
        	e.storePlacementInfo( helper, parent, !visible );
        }
        
        for (EdgeBase e : data.fAssocClassToEdgeMap.values()) {
        	e.storePlacementInfo( helper, parent, !visible );
        }
        
        for (GeneralizationEdge e : data.fGenToGeneralizationEdge.values()) {
        	e.storePlacementInfo( helper, parent, !visible );
        }
    }

	@Override
	public void restorePlacementInfos(PersistHelper helper, Element rootElement, int version) {
		Set<MClassifier> hiddenClassifier = new HashSet<MClassifier>();
				
		// Restore class nodes
		NodeList elements = (NodeList) helper.evaluateXPathSave(rootElement,
				"./" + LayoutTags.NODE + "[@type='Class']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {
			Element nodeElement = (Element)elements.item(i);			
			String name = helper.getElementStringValue(nodeElement, "name");
			MClass cls = fParent.system().model().getClass(name);
			// Could be deleted
			if (cls != null) {
				ClassNode node = visibleData.fClassToNodeMap.get(cls);
				node.restorePlacementInfo(helper, nodeElement, version);
				if (isHidden(helper, nodeElement, version)) hiddenClassifier.add(cls);
			}
		}
		
		// Restore enum nodes
		elements = (NodeList) helper.evaluateXPathSave(rootElement, "./"
				+ LayoutTags.NODE + "[@type='Enumeration']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {		
			Element nodeElement = (Element)elements.item(i);
			String name = helper.getElementStringValue(nodeElement, "name");
			EnumType enumType = fParent.system().model().enumType(name);
			// Could be deleted
			if (enumType != null) {
				EnumNode node = visibleData.fEnumToNodeMap.get(enumType);
				node.restorePlacementInfo(helper, nodeElement, version);
				if (isHidden(helper, nodeElement, version)) hiddenClassifier.add(enumType);
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
			// Could be deleted
			if (assoc != null) {
				DiamondNode node = visibleData.fNaryAssocToDiamondNodeMap.get(assoc);
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
			// Could be deleted
			if (assoc != null) {
				BinaryAssociationOrLinkEdge edge = visibleData.fBinaryAssocToEdgeMap.get(assoc);
				edge.restorePlacementInfo(helper, edgeElement, version);
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
			// Could be deleted
			if (assoc != null) {
				EdgeBase edge = visibleData.fAssocClassToEdgeMap.get(assoc);
				edge.restorePlacementInfo(helper, edgeElement, version);
			}
		}
		
		elements = (NodeList) helper.evaluateXPathSave(rootElement, "./"
				+ LayoutTags.EDGE + "[@type='" + (version == 1 ? "Inheritance" : "Generalization")  + "']",
				XPathConstants.NODESET);
		
		for (int i = 0; i < elements.getLength(); ++i) {
			Element edgeElement = (Element)elements.item(i);
			String source = helper.getElementStringValue(edgeElement, LayoutTags.SOURCE);
			String target = helper.getElementStringValue(edgeElement, LayoutTags.TARGET);
				
			MClass child = fParent.system().model().getClass(source);
			MClass parent = fParent.system().model().getClass(target);
			
			if (child != null && parent != null) {
				Set<MGeneralization> genSet = fParent.system().model().generalizationGraph().edgesBetween(child, parent);
				if (!genSet.isEmpty()) {
					MGeneralization gen = genSet.iterator().next();
					GeneralizationEdge edge = visibleData.fGenToGeneralizationEdge.get(gen);
					edge.restorePlacementInfo(helper, edgeElement, version);
				}
			}
		}

		// Hide elements
		hideElementsInDiagram(hiddenClassifier);
	}
	
	protected boolean isHidden(PersistHelper helper, Element element, int version) {
		return helper.getElementBooleanValue(element, LayoutTags.HIDDEN);
	}
	
	public ActionHideClassDiagram getAction( String text, Set<? extends MClassifier> selectedNodes ) {
        return new ActionHideClassDiagram( text, selectedNodes,
                                           fNodeSelection, fGraph,
                                           this );
    }
	
	private Map<MModelElement, CoverageData> data = null;
	
	private void setCoverageColor() {

		if (fOpt.isShowCoverage()) {
			MModel model = this.fParent.system().model();
			
			if (data == null)
				data = CoverageAnalyzer.calculateModelCoverage(model);
			
			MModelElement selectedElement = this.fParent.getModelBrowser().getSelectedModelElement();
			
			CoverageData theData;
			if (selectedElement != null && data.containsKey(selectedElement)) {
				theData = data.get(selectedElement);
			} else {
				theData = data.get(model);
			}
			
			Map<MModelElement, Integer> propCover = theData.getPropertyCoverage();
			
			int minCover = 0; //data.calcLowestClassCoverage();
			int maxCover = theData.calcHighestCompleteClassCoverage();
			int maxAttCover = theData.highestInt(propCover);
			int value;
			
			for (MClass cls : model.classes()) {
				if (theData.getCompleteClassCoverage().containsKey(cls)) {
					value = theData.getCompleteClassCoverage().get(cls);
				} else {
					value = 0;
				}
				
				ClassNode n = visibleData.fClassToNodeMap.get(cls);
				if (n == null)
					n = hiddenData.fClassToNodeMap.get(cls);
				
				n.setColor(scaleColor(value, minCover, maxCover));
				
				for (MAttribute att : cls.attributes()) {
					if (propCover.containsKey(att)) {
						value = theData.getAttributeCoverage().get(att);
					} else {
						value = 0;
					}
					n.setAttributeColor(att, scaleColor(value, minCover, maxAttCover));
				}
			}
			
			for (BinaryAssociationOrLinkEdge edge : visibleData.fBinaryAssocToEdgeMap.values()) {
				if (propCover.containsKey(edge.getSourceRolename().getEnd())) {
					value = propCover.get(edge.getSourceRolename().getEnd());
				} else {
					value = 0;
				}
				
				edge.getSourceRolename().setColor(scaleColor(value, minCover, maxAttCover));
				
				if (propCover.containsKey(edge.getTargetRolename().getEnd())) {
					value = propCover.get(edge.getTargetRolename().getEnd());
				} else {
					value = 0;
				}
				
				edge.getTargetRolename().setColor(scaleColor(value, minCover, maxAttCover));
			}
		} else {
			resetColor();
		}
		
		repaint();
	}

	protected void resetColor() {
		for (ClassNode n : visibleData.fClassToNodeMap.values()) {
			n.setColor(null);
			n.resetAttributeColor();
		}
		for (ClassNode n : hiddenData.fClassToNodeMap.values()) {
			n.setColor(null);
			n.resetAttributeColor();
		}
		
		for (BinaryAssociationOrLinkEdge edge : visibleData.fBinaryAssocToEdgeMap.values()) {
			edge.getSourceRolename().setColor(null);
			edge.getTargetRolename().setColor(null);
		}
	}
	
	private Color scaleColor(int theVal, int low, int high) {
		return produceColor(theVal - low, high - low);
	}
	
	private Color produceColor(int step, int steps)
	{
	    float value = (step == 0 ? 0.0f : 0.1f + step * (0.9f / steps)); //tend to darkness
	    
	    int rgb = Color.HSBtoRGB(0.59f, value, 1f); //create a darker color
        //Hue is Blue, not noticeable
        //because Saturation is 0
	    Color color = new Color(rgb);
	    return color;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.main.ModelBrowser.SelectionChangedListener#selectionChanged(org.tzi.use.uml.mm.MModelElement)
	 */
	@Override
	public void selectionChanged(MModelElement element) {
		setCoverageColor();
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#getHiddenNodes()
	 */
	@Override
	public Set<PlaceableNode> getHiddenNodes() {
		return hiddenData.getNodes();
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#getVisibleData()
	 */
	@Override
	public DiagramData getVisibleData() {
		return visibleData;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.DiagramView#getHiddenData()
	 */
	@Override
	public DiagramData getHiddenData() {
		return hiddenData;
	}
	
	/**
	 * @param cs
	 * @return
	 */
	public boolean isVisible(MClassifier cs) {
		return visibleData.fClassToNodeMap.containsKey(cs) || 
			   visibleData.fEnumToNodeMap.containsKey(cs);
	}
	
	private void exportVisibleClassesAsModel(File exportFile) {
		ModelFactory f = new ModelFactory();
		MModel sourceModel = getSystem().model();
		MModel targetModel = f.createModel(sourceModel.name());
		
		copyAnnotations(sourceModel, targetModel);
		
		// Create "skeletons" for class
		for (MClass sourceClass : this.visibleData.fClassToNodeMap.keySet()) {
			try {
				MClass targetClass;
				
				if (sourceClass instanceof MAssociationClass)
					targetClass = f.createAssociationClass(sourceClass.name(), sourceClass.isAbstract());
				else
					targetClass = f.createClass(sourceClass.name(), sourceClass.isAbstract());
				
				targetModel.addClass(targetClass);
				copyAnnotations(sourceClass, targetClass);
			} catch (MInvalidModelException e) { /* Cannot happen */ }
		}
		
		// Create enumerations
		for (EnumType sourceEnum : visibleData.fEnumToNodeMap.keySet()) {
			try {
				EnumType targetEnum = TypeFactory.mkEnum(sourceEnum.name(), sourceEnum.getLiterals());
				targetModel.addEnumType(targetEnum);
				copyAnnotations(sourceEnum, targetEnum);
			} catch (MInvalidModelException e) { /* Cannot happen */ }
		}
		
		// Create inheritance and attributes
		for (MClass targetClass : targetModel.classes()) {
			MClass sourceClass = sourceModel.getClass(targetClass.name());
			
			// Inheritance
			for (MClass sourceParentClass : sourceClass.parents()) {
				MClass targetParentClass = findMostSpecificExportedType(sourceParentClass, targetModel);
				
				// Could be hidden!
				if (targetParentClass != null) {
					try {
						targetModel.addGeneralization(f.createGeneralization(targetClass, targetParentClass));
					} catch (MInvalidModelException e) { /* Cannot happen */ }
				}
			}
			
			// Attributes
			for (MAttribute sourceAttribute : sourceClass.attributes()) {
				Type attType = OCLCompiler.compileType(
						targetModel, sourceAttribute.type().toString(),
						"Export", NullPrintWriter.getInstance());
				
				// if type is not exported, don't export the attribute
				if (attType != null) {
					MAttribute targetAttribute = f.createAttribute(sourceAttribute.name(), attType);
					try {
						targetClass.addAttribute(targetAttribute);
					} catch (MInvalidModelException e) { /* Cannot happen */ }
					copyAnnotations(sourceAttribute, targetAttribute);
				}
			}
			
			// Operations
			for (MOperation sourceOperation : sourceClass.operations()) {
				boolean hasErrors = false;
				Type resultType = null;
				
				if (sourceOperation.hasResultType()) {
					resultType = OCLCompiler.compileType(
							targetModel, sourceOperation.resultType().toString(),
							"Export", NullPrintWriter.getInstance());
					
					// Result Type is not exported
					if (resultType == null)
						continue;
				}
				
				VarDeclList targetArgs = new VarDeclList(false);
									
				// Build arguments
				for (VarDecl arg : sourceOperation.paramList()) {
					VarDecl v = cloneVarDecl(targetModel, arg);
					if (v == null) {
						hasErrors = true;
						break;
					}

					targetArgs.add(v);
				}
				
				// If arg type is not present, continue to next operation
				if (hasErrors)
					continue;
				
				MOperation targetOperation = f.createOperation(sourceOperation.name(), targetArgs, resultType);
									
				try {
					targetClass.addOperation(targetOperation);
				} catch (MInvalidModelException e) { /* Cannot happen */ }
				
				copyAnnotations(sourceOperation, targetOperation);
			}
		}
		
		// Binary associations
		Set<MAssociation> sourceAssociations = new HashSet<MAssociation>(visibleData.fBinaryAssocToEdgeMap.keySet());
		// n-ary associations
		sourceAssociations.addAll(visibleData.fNaryAssocToDiamondNodeMap.keySet());
		
		for (MAssociation sourceAssoc : sourceAssociations) {
			MAssociation targetAssoc;
			if (sourceAssoc instanceof MAssociationClass) {
				targetAssoc = (MAssociation)targetModel.getClass(sourceAssoc.name());
			} else {
				targetAssoc = f.createAssociation(sourceAssoc.name());
				copyAnnotations(sourceAssoc, targetAssoc);
			}
			
			for (MAssociationEnd sourceEnd : sourceAssoc.associationEnds()) {
				List<VarDecl> targetQualifiers = new ArrayList<VarDecl>();
				boolean hasErrors = false;
				
				for (VarDecl sourceQualifier : sourceEnd.getQualifiers()) {
					VarDecl targetQualifier = cloneVarDecl(targetModel, sourceQualifier);
					if (targetQualifier == null) {
						hasErrors = true;
						break;
					}
					
					targetQualifiers.add(targetQualifier);
				}
				
				if (hasErrors)
					continue;
				
				MAssociationEnd targetEnd = 
						f.createAssociationEnd(
								targetModel.getClass(sourceEnd.cls().name()),
								sourceEnd.name(),
								sourceEnd.multiplicity(),
								sourceEnd.aggregationKind(),
								sourceEnd.isOrdered(),
								targetQualifiers);

				copyAnnotations(sourceEnd, targetEnd);
				try {
					targetAssoc.addAssociationEnd(targetEnd);
				} catch (MInvalidModelException e) {
					
				}
			}
			
			try {
				targetModel.addAssociation(targetAssoc);
			} catch (MInvalidModelException e) {

			}
		}

		// Write result
		FileOutputStream out;
		try {
			out = new FileOutputStream(exportFile, false);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(ClassDiagram.this, e.getMessage(),
					"Error saving the USE model", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		MMVisitor v = new MMPrintVisitor(new PrintWriter(out, true));
        targetModel.processWithVisitor(v);
        
        try {
			out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ClassDiagram.this, e.getMessage(),
					"Error saving the USE model", JOptionPane.ERROR_MESSAGE);
			return;
		}
        
		JOptionPane.showMessageDialog(ClassDiagram.this, "Export succesfull",
				"Export successfull", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @param sourceParentClass
	 * @param targetModel
	 * @return
	 */
	private MClass findMostSpecificExportedType(MClass sourceParentClass, MModel targetModel) {
		MClass parent = targetModel.getClass(sourceParentClass.name());
		
		if (parent != null)
			return parent;
		
		for (MClass otherParent : sourceParentClass.parents()) {
			parent = findMostSpecificExportedType(otherParent, targetModel); 
			if (parent != null)
				return parent;
		}
		
		return null;
	}

	private VarDecl cloneVarDecl(MModel targetModel, VarDecl v) {
		Type argType = OCLCompiler.compileType(
				targetModel, v.type().toString(),
				"Export", NullPrintWriter.getInstance());
		
		if (argType == null) {
			return null;
		}
		
		return new VarDecl(v.name(), argType);
	}
	
	/**
	 * @param sourceModel
	 * @param targetModel
	 */
	private void copyAnnotations(Annotatable source, Annotatable target) {
		for (MElementAnnotation an : source.getAllAnnotations().values()) {
			target.addAnnotation(an);
		}
	}
}
