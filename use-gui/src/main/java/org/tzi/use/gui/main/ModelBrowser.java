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

package org.tzi.use.gui.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.main.runtime.IPluginMMVisitor;
import org.tzi.use.gui.main.runtime.IPluginMModelExtensionPoint;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.diagrams.event.ModelBrowserMouseHandling;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;

/** 
 * A ModelBrowser provides a tree view of classes, associations, and constraints
 * in a model. The definition of a selected element is shown in an HTML pane. A
 * class can be dragged onto an object diagram to create a new object of this
 * class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class ModelBrowser extends JPanel 
    implements DragSourceListener, DragGestureListener, SortChangeListener {
    private MModel fModel;
    private JTree fTree;
    private JEditorPane fHtmlPane;
    private ModelBrowserSorting fMbs;

    private DragSource fDragSource = null;

    private DefaultTreeModel fTreeModel = null;
    private DefaultMutableTreeNode fTop;
    
    private EventListenerList fListenerList;
    private ModelBrowserMouseHandling fMouseHandler;
    private Map<String, Collection<?>> modelCollections = new HashMap<String, Collection<?>>();
    private MainWindow mainWindow;
    
    // implementation of interface DragSourceListener
    public void dragEnter(DragSourceDragEvent dsde) {
        //Log.trace(this, "dragEnter");
    }

    public void dragOver(DragSourceDragEvent dsde) {
        //Log.trace(this, "dragOver");
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
        //Log.trace(this, "dropActionChanged");
    }

    public void dragExit(DragSourceEvent dse) {
        //Log.trace(this, "dragExit");
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
        //Log.trace(this, "dragDropEnd");
    }

    private static IRuntime fPluginRuntime;

    public JEditorPane getHTMLPane() {
    	return this.fHtmlPane;
    }

    public Map<String, Collection<?>> getModelCollections() {
    	return this.modelCollections;
    }

    public MModelElement getSelectedModelElement() {
    	if (fTree.getSelectionCount() > 0) {
    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)fTree.getLastSelectedPathComponent();
    		if (node.getUserObject() instanceof MModelElement)
    			return (MModelElement)node.getUserObject();
    	}
    	return null;
    }
    
    // implementation of interface DragSourceListener
    public void dragGestureRecognized(DragGestureEvent dge) {
        //Log.trace(this, "dragGestureRecognized");
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) fTree
			.getLastSelectedPathComponent();

        if (fModel == null || node == null ) 
            return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf() && (nodeInfo instanceof MClass) ) {
            MClass cls = (MClass) nodeInfo;
            StringSelection text = new StringSelection("CLASS-" + cls.name());
            fDragSource.startDrag (dge, DragSource.DefaultMoveDrop, text, this);
        }
    }

    /** 
     * Creates a browser with no model.
     */
    public ModelBrowser(MainWindow mainWindow, IRuntime pluginRuntime) {
    	this(null, mainWindow, pluginRuntime);
        fListenerList = new EventListenerList();
    }

    /** 
     * Creates a model browser. The model may be null.
     */
    public ModelBrowser(MModel model, MainWindow mainWindow,
	    IRuntime pluginRuntime) {
        fListenerList = new EventListenerList();
        // Create tree and nodes.
        setMainWindow(mainWindow);
        setPluginRuntime(pluginRuntime);
        setModel(model);

        fDragSource = new DragSource();
        fDragSource.createDefaultDragGestureRecognizer(fTree, 
		DnDConstants.ACTION_MOVE, this);

        // Allow one selection at a time.
        fTree.getSelectionModel().setSelectionMode(
                                                   TreeSelectionModel.SINGLE_TREE_SELECTION);
        fTree.putClientProperty("JTree.lineStyle", "Angled");
        fTree.setCellRenderer(new CellRenderer());

        // Enable tool tips
        ToolTipManager.sharedInstance().registerComponent(fTree);

		// Listen for when the selection changes.
		fTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) fTree
						.getLastSelectedPathComponent();

				if (fModel == null || node == null)
					return;

				Object nodeInfo = node.getUserObject();
				if (node.isLeaf() && (nodeInfo instanceof MModelElement)) {
					MModelElement me = (MModelElement) nodeInfo;
					displayInfo(me);
					int selectedRow = -1;
					// which node is selected
					for (int i = 0; i < fTree.getRowCount(); i++) {
						if (fTree.isRowSelected(i)) {
							selectedRow = i;
						}
					}
					fMouseHandler.setSelectedNodeRectangle(fTree
							.getRowBounds(selectedRow));
					fMouseHandler.setSelectedModelElement(me);
					
					fireSelectionChanged(me);
				} else {
					fireSelectionChanged(null);
				}
			}
		});

        // Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(fTree);

        // Create the HTML viewing pane.
        fHtmlPane = new JEditorPane();
        fHtmlPane.setEditable(false);
        fHtmlPane.setContentType("text/html");
        JScrollPane htmlView = new JScrollPane(fHtmlPane);

        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              treeView, htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(500, 300));
        splitPane.setDividerLocation((int) (0.42 * Options.DEFAULT_HEIGHT));//FIXME 240);
        splitPane.setResizeWeight(.6d);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

    private void setMainWindow(MainWindow mainWindow) {
	this.mainWindow = mainWindow;
    }

    private void setPluginRuntime(IRuntime pluginRuntime) {
	ModelBrowser.fPluginRuntime = pluginRuntime;

    }

    public MainWindow mainWindow() {
	return this.mainWindow;
    }

    /**
     * This renderer always displays root and categories as non-leaf nodes even
     * if they are empty.
     */
    class CellRenderer extends DefaultTreeCellRenderer {
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean sel, boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {
	    super.getTreeCellRendererComponent(tree, value, sel, expanded,
		    leaf, row, hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            int level = node.getLevel();
            this.setToolTipText(null);
            // always display root and categories as non-leaf nodes
            if (level == 0 ) {
                if (node.isLeaf() )
                    setIcon(getClosedIcon()); // we don't have a model
                else 
                    setIcon(getOpenIcon());
            } else if (level == 1 ) {
                if (tree.isExpanded(new TreePath(node.getPath())) )
                    setIcon(getOpenIcon());
                else
                    setIcon(getClosedIcon());
            } else {
                Object nodeInfo = node.getUserObject();
                if (nodeInfo instanceof MClass ) {
                    MClass cls = (MClass) nodeInfo;
		    this
			    .setToolTipText("Drag onto object diagram to create a new "
				    + cls.name() + " object.");
                }

            }
            return this;
        }
    }

    /** 
     * Initializes the browser to a new model. The model may be null.  
     */
    public void setModel(MModel model) {
        fModel = model;

        // Create the nodes.
        if (fModel != null ) {
            fTop = new DefaultMutableTreeNode(model.name());
            createNodes(fTop);
        } else {
            fTop = new DefaultMutableTreeNode("No model available");
        }

        // Create new tree or reinitialize existing tree
        if (fTree == null ) {
            fTreeModel = new DefaultTreeModel(fTop);
            fTree = new JTree(fTreeModel);
            fMbs = ModelBrowserSorting.getInstance();
            fMbs.addSortChangeListener( this );
            
            fMouseHandler = new ModelBrowserMouseHandling( this );
            fTree.addMouseListener( fMouseHandler );
        } else {
            fTreeModel.setRoot(fTop);
            fTreeModel = (DefaultTreeModel) fTree.getModel();
        }

        // reset HTML pane
        if (fHtmlPane != null )
            fHtmlPane.setText("");
    }

    /** 
     * Displays info about the selected model element in the HTML pane.
     */
    private void displayInfo(MModelElement element) {
		IPluginMModelExtensionPoint modelExtensionPoint = (IPluginMModelExtensionPoint) fPluginRuntime
			.getExtensionPoint("model");
	        StringWriter sw = new StringWriter();
	        sw.write("<html><head>");
	        sw.write("<style> <!-- body { font-family: sansserif; } --> </style>");
	        sw.write("</head><body><font size=\"-1\">");
	
		IPluginMMVisitor v = modelExtensionPoint.createMMHTMLPrintVisitor(
			new PrintWriter(sw), this);

        element.processWithVisitor(v);

        sw.write("</font></body></html>");
        String spec = sw.toString();
        fHtmlPane.setText(spec);
    }

    public void createNodes( final DefaultMutableTreeNode top ) {
        final Collection<MClass> sortedClasses = 
            fMbs.sortClasses( new ArrayList<MClass>(fModel.classes()) );
        addChildNodes( top, "Classes", sortedClasses );

        final ArrayList<MAssociation> sortedAssociations = 
            fMbs.sortAssociations(new ArrayList<MAssociation>(fModel.associations()));
	    
        addChildNodes( top, "Associations", sortedAssociations );

        final Collection<MClassInvariant> sortedInvariants = 
            fMbs.sortInvariants( fModel.classInvariants() );
        
        addChildNodes( top, "Invariants", sortedInvariants );

        final Collection<MPrePostCondition> sortedConditions = 
            fMbs.sortPrePostConditions(fModel.prePostConditions());
        addChildNodes( top, "Pre-/Postconditions", sortedConditions );
        
		Set<Map.Entry<String, Collection<?>>> modelCollectionEntrySet = this.modelCollections.entrySet();
				
		for (Map.Entry<String, Collection<?>> modelCollectionMapEntry : modelCollectionEntrySet) {
		    String modelCollectionName = modelCollectionMapEntry.getKey()
			    .toString();
		    Collection<?> modelCollection = fMbs
			    .sortPluginCollection(modelCollectionMapEntry.getValue());
		    addChildNodes(top, modelCollectionName, modelCollection);
		}
		
		final Collection<MOperation> queryOperations = new ArrayList<MOperation>();
		for (MClass mClass : fModel.classes()) {
			for (MOperation mOperation : mClass.operations()) {
				if(mOperation.hasExpression()){
					queryOperations.add(mOperation);
				}
			}
		}
		
		addChildNodes(top, "Query Operations", queryOperations);
    }

    /**
     * Adds the childs nodes to the top node.
     */
    private void addChildNodes(DefaultMutableTreeNode top, String name,
                               Collection<?> items) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode(name);
        top.add(category);
        Iterator<?> it = items.iterator();
	    
        while (it.hasNext() ) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(it.next());
            category.add(child);
        }
    }

    public void addPluginCollection(String modelCollectionName, Collection<?> modelCollection) {
    	this.modelCollections.put(modelCollectionName, modelCollection);
    	fTop.removeAllChildren();
    	createNodes(fTop);
    	fTreeModel.reload();
    	fHtmlPane.setText("");
    }

    /**
     * If an event occures the tree will be reloaded.
     */
    public void stateChanged( SortChangeEvent e ) {
        ArrayList<Integer> pathWereExpanded = new ArrayList<Integer>();
        int selectedRow = -1;
        
        // which nodes are expanded
        for ( int i=0; i<fTree.getRowCount(); i++ ){
            if ( fTree.isExpanded( i ) ){
                pathWereExpanded.add( i );
            }
            // which node is selected
            if ( fTree.isRowSelected( i ) ) {
                selectedRow = i;
            }
        }

        fTop.removeAllChildren();
        createNodes( fTop );
        fTreeModel.reload();
        fHtmlPane.setText( "" );

        // expand all nodes that were expanded.
        for ( int i=0; i<pathWereExpanded.size(); i++ ){
            fTree.expandRow( pathWereExpanded.get(i).intValue() );
        }
        // set selected node again.
        if ( selectedRow >= 0 ) {
            fTree.setSelectionRow( selectedRow );    
        }
    }
    
    /**
     * Adds Listeners who are interested on a change event of highlighting.
     * 
     * @param l
     *            The listener who is interested
     */
    public void addHighlightChangeListener( HighlightChangeListener l ) {
        fListenerList.add( HighlightChangeListener.class, l );
    }
    
    public void removeHighlightChangeListener( HighlightChangeListener l ) {
        fListenerList.remove( HighlightChangeListener.class, l );
    }
    
    /*
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     */
    public void fireStateChanged( MModelElement elem, boolean highlight ) {
        // Guaranteed to return a non-null array
        Object[] listeners = fListenerList.getListenerList();
        HighlightChangeEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == HighlightChangeListener.class ) {
                // Lazily create the event:
                if (e == null) {
                    e = new HighlightChangeEvent(this);
                    e.setModelElement( elem );
                    e.setHighlight( highlight );
                }
                ((HighlightChangeListener) listeners[i+1]).stateChanged(e);
            }          
        }
    }
    
    /*
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     */
    public void fireSelectionChanged( MModelElement elem ) {
        // Guaranteed to return a non-null array
        Object[] listeners = fListenerList.getListenerList();
        
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionChangedListener.class ) {
                // Lazily create the event:
                ((SelectionChangedListener) listeners[i+1]).selectionChanged(elem);
            }          
        }
    }
    
    /**
     * Adds Listeners who are interested on a change of the selected element in the browser.
     * 
     * @param l
     *            The listener who is interested
     */
    public void addSelectionChangedListener( SelectionChangedListener l ) {
        fListenerList.add( SelectionChangedListener.class, l );
    }
    
    public void removeSelectionChangedListener(SelectionChangedListener l) {
        listenerList.remove(SelectionChangedListener.class, l);
    }

    public interface SelectionChangedListener extends EventListener {
    	public void selectionChanged(MModelElement element);
    }
}
