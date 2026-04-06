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

package org.tzi.use.gui.views.diagrams.objectdiagram;


import java.awt.*;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.ModelBrowserSorting;
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
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.*;
import org.tzi.use.util.StringUtil;
import org.w3c.dom.Element;

/**
 * A panel drawing UML object diagrams.
 * 
 * @author Mark Richters
 * @author Lars Hamann
 */
public class NewObjectDiagram extends DiagramViewWithObjectNode implements HighlightChangeListener, SortChangeListener, ObjectDiagramInteractor {

	/**
	 * This class saves needed information about visible or hidden nodes and
	 * edges. When hiding or showing objects the data is moved between the
	 * fields {@link NewObjectDiagram#visibleData} and
	 * {@link NewObjectDiagram#hiddenData}.
	 *
	 * @author Lars Hamann
	 */
	public static class ObjectDiagramData implements DiagramData {
		// internal maps; keep them private and provide accessors
		private final Map<MObject, ObjectNode> fObjectToNodeMap;
		private final Map<MLink, BinaryAssociationOrLinkEdge> fBinaryLinkToEdgeMap;
		private final Map<MLink, DiamondNode> fNaryLinkToDiamondNodeMap;
		private final Map<MLink, List<EdgeBase>> fHalfLinkToEdgeMap;
		private final Map<MLinkObject, EdgeBase> fLinkObjectToNodeEdge;

		/**
		 * Creates an empty set of diagram data
		 */
		public ObjectDiagramData() {
			fObjectToNodeMap = new HashMap<>();
			fBinaryLinkToEdgeMap = new HashMap<>();
			fNaryLinkToDiamondNodeMap = new HashMap<>();
			fHalfLinkToEdgeMap = new HashMap<>();
			fLinkObjectToNodeEdge = new HashMap<>();
		}

		/**
		 * Checks whether this diagram data contains the given link.
		 *
		 * @param link the link to test
		 * @return true if this data contains an edge or node representing the link
		 */
		public boolean containsLink(MLink link) {
			boolean has = fBinaryLinkToEdgeMap.containsKey(link) || fNaryLinkToDiamondNodeMap.containsKey(link);
			if (!has && link instanceof MLinkObject) {
				has = fLinkObjectToNodeEdge.containsKey((MLinkObject) link);
			}
			return has;
		}

		@Override
		public Set<PlaceableNode> getNodes() {
			Set<PlaceableNode> result = new HashSet<>();

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
			Set<EdgeBase> result = new HashSet<>(fBinaryLinkToEdgeMap.values());
			result.addAll(fLinkObjectToNodeEdge.values());
			for (Map.Entry<MLink, List<EdgeBase>> entry : fHalfLinkToEdgeMap.entrySet()) {
				result.addAll(entry.getValue());
			}
			return result;
		}

		/**
		 * Copies all data to the target object
		 *
		 * @param target the target ObjectDiagramData to copy the data into
		 */
		@SuppressWarnings("unused")
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

		// Accessors to preserve encapsulation
		public Map<MObject, ObjectNode> getObjectToNodeMap() {
			return fObjectToNodeMap;
		}

		public Map<MLink, BinaryAssociationOrLinkEdge> getBinaryLinkToEdgeMap() {
			return fBinaryLinkToEdgeMap;
		}

		public Map<MLink, DiamondNode> getNaryLinkToDiamondNodeMap() {
			return fNaryLinkToDiamondNodeMap;
		}

		public Map<MLink, List<EdgeBase>> getHalfLinkToEdgeMap() {
			return fHalfLinkToEdgeMap;
		}

		public Map<MLinkObject, EdgeBase> getLinkObjectToNodeEdge() {
			return fLinkObjectToNodeEdge;
		}
	}

	private ObjectDiagramData visibleData = new ObjectDiagramData();

    private ObjectDiagramData hiddenData = new ObjectDiagramData();

     private final DiagramContext context;
     private final NewObjectDiagramView view;
     private final ModelBrowser modelBrowser;
     private NewObjectDiagramModel model;
     private MSystem system;
     private final java.util.function.BooleanSupplier isSelectedView;

	/**
	 * The position of the next object node. This is either set to a random
	 * value or to a specific position when an object is created by drag & drop.
	 */
	protected Point2D.Double nextNodePosition = new Point2D.Double();

	// show object properties listener is attached inline in constructor (no field required)

	private final ObjectSelection fSelection;

	protected final DiagramInputHandling inputHandling;

	private static boolean javaFxCall = false;

	/** Presenter reference for incremental MVP refactor. */
	private NewObjectDiagramPresenter presenter;

    private MSystem system() {
        return system;
    }

	public void setPresenter(NewObjectDiagramPresenter presenter) {
        this.presenter = presenter;
        if (presenter instanceof NewObjectDiagramPresenterImpl p) {
            this.model = p.getModel();
            this.system = p.getSystem();
        }
        rebindModelData();
    }

    public NewObjectDiagramPresenter getPresenter() {
        return presenter;
    }

	/**
	 * Creates a new empty diagram.
	 */
	NewObjectDiagram(DiagramContext parent, PrintWriter log) {
		this(parent, log, new ObjDiagramOptions());
	}

	protected NewObjectDiagram(DiagramContext parent, PrintWriter log, ObjDiagramOptions options) {
		super(options, log);
		this.getRandomNextPosition();

		context = parent;
		view = (parent instanceof NewObjectDiagramView v) ? v : null;
		this.system = parent.system();
		this.modelBrowser = parent.getModelBrowser();
		this.isSelectedView = parent::isSelectedView;
		this.model = new NewObjectDiagramModel();
		rebindModelData();

		// Font is component-local; no context propagation needed

		fSelection = new ObjectSelection(this, system());

		fActionSaveLayout = new ActionSaveLayout("USE object diagram layout", "olt", this);

		fActionLoadLayout = new ActionLoadLayout("USE object diagram layout", "olt", this);

		inputHandling = new DiagramInputHandling(fNodeSelection, fEdgeSelection, this);

		modelBrowser.addHighlightChangeListener(this);
		ModelBrowserSorting.getInstance().addSortChangeListener(this);
		addKeyListener(inputHandling);

		addMouseListener(inputHandling);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					PlaceableNode pickedObjectNode = findNode(e.getX(), e.getY());
					if (pickedObjectNode instanceof ObjectNode on) {
						ObjectPropertiesView v = MainWindow.instance().showObjectPropertiesView();
						v.selectObject(on.object().name());
					}
				}
			}
		});

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
		return (ObjDiagramOptions) super.getOptions();
	}

	@Override
	public void setShowStates(boolean show) {
		getOptions().setShowStates(show);
		invalidateContent(true);
	}

	@Override
	public boolean isShowStates() {
		return getOptions().isShowStates();
	}

	@Override
	public void setGreyed(MObject obj, boolean greyed) {
        ObjectNode node = visibleData.getObjectToNodeMap().get(obj);
        if (node != null) {
            node.setGreyed(greyed);
            repaint();
        }
	}

	@Override
	public boolean isGreyed(MObject obj) {
		ObjectNode node = visibleData.getObjectToNodeMap().get(obj);
		return node != null && node.isGreyed();
	}

	/**
	 * Displays objects of the selected class in the modelbrowser.
	 */
	public void stateChanged(HighlightChangeEvent e) {
		if (presenter != null) {
			presenter.onHighlightChange(e);
			return;
		}
		handleHighlightChange(e);
	}

	/** Shared highlight handler used by presenter or direct fallback. */
	public void handleHighlightChange(HighlightChangeEvent e) {
        if (e == null || !isSelectedView.getAsBoolean()) {
            return;
        }

        MModelElement elem = e.getModelElement();
        Set<MObject> selectedObjects = new HashSet<>();
        Set<MLink> selectedLinks = new HashSet<>();

        if (elem instanceof MAssociation assoc) {
            Set<MLink> links = presenter != null ? presenter.fetchLinksOfAssociation(assoc)
                    : system().state().linksOfAssociation(assoc).links();
            selectedLinks.addAll(links);
        }

        if (elem instanceof MClass) {
            Set<MObject> objs = presenter != null ? presenter.fetchObjectsOfClass((MClass) elem)
                    : system().state().objectsOfClass((MClass) elem);
            selectedObjects.addAll(objs);
        }

        if (!e.getHighlight()) {
            selectedObjects.clear();
            selectedLinks.clear();
        }

        model.replaceSelection(selectedObjects, selectedLinks);
        applySelectionFromModel();
        invalidateContent(true);
	}

	/**
	 * Shows all hidden elements again
	 */
	public void showAll() {
		while (!hiddenData.getObjectToNodeMap().isEmpty()) {
			showObject(hiddenData.getObjectToNodeMap().keySet().iterator().next());
		}
	}

	public void hideAll() {
		Set<MObject> objects = new HashSet<>(this.visibleData.getObjectToNodeMap().keySet());
		objects.forEach(this::hideObject);
	}

	/**
	 * Hides all currently visible links. The diagram is not repainted!
	 */
	public void hideAllLinks() {
		Set<MLink> links = new HashSet<>(this.visibleData.getBinaryLinkToEdgeMap().keySet());

		for (MLink e : links) {
			hideBinaryLink(e);
		}

		links.clear();

		// Hide HalfLinks
		// HalfLinks used by TernaryAssoc
		links.addAll(this.visibleData.getHalfLinkToEdgeMap().keySet());

		// Hide LinkObject
		links.addAll(this.visibleData.getLinkObjectToNodeEdge().keySet());
		for (MLink e : links) {
			hideLink(e);
		}

		links.clear();

		// Hide NaryLinks
		links.addAll(this.visibleData.getNaryLinkToDiamondNodeMap().keySet());
		for (MLink e : links) {
			hideNAryLink(e);
		}

		links.clear();
	}

	/**
	 * Shows all currently invisible links. The diagram is not repainted!
	 */
	public void showAllLinks() {
		Collection<MAssociation> assocs = presenter != null ? presenter.fetchAllAssociations()
				: system().model().associations();
        for (MAssociation assoc : assocs) {
            Set<MLink> links = presenter != null ? presenter.fetchLinksOfAssociation(assoc)
                    : system().state().linksOfAssociation(assoc).links();
            for (MLink link : links) {
                showLink(link);
            }
        }
	}

	/**
	 * Adds an object to the diagram.
	 */
	@Override
	public void addObject(MObject obj) {
        ObjectNode n = new ObjectNode(obj, view, getOptions());
		n.setMinWidth(minClassNodeWidth);
		n.setMinHeight(minClassNodeHeight);

		Map<MObject, Point2D> nodeCache = nodePositionCache();
		if (nodeCache.containsKey(obj)) {
			n.moveToPosition(nodeCache.get(obj));
			nodeCache.remove(obj);
		} else {
			n.setPosition(nextNodePosition);
			getRandomNextPosition();
		}

		fGraph.add(n);
		visibleData.getObjectToNodeMap().put(obj, n);
		// state stored in model via visibleData reference
		fLayouter = null;
	}

	/**
	 * Shows an already hidden object again
	 *
	 * @param obj The object to show
	 */
	public void showObject(MObject obj) {
		if (visibleData.getObjectToNodeMap().containsKey(obj))
			return;

		showOrHideObjectNode(obj, true);

		boolean isLinkObject = false;

		Set<MAssociation> assocs = obj.cls().allAssociations();
		if (obj instanceof MLinkObject) {
			assocs.add((MAssociation) obj.cls());
			isLinkObject = true;
		}

		// Show all links the object participates in,
		// when all other objects are visible, too.
		for (MAssociation assoc : assocs) {
            Set<MLink> links = presenter != null ? presenter.fetchLinksOfAssociation(assoc)
                    : system().state().linksOfAssociation(assoc).links();
			// TODO: Not very fast!
			for (MLink link : links) {
				if (link.linkedObjects().contains(obj) || (isLinkObject && link.equals(obj))) {
					boolean allVisible = true;
					for (MObject linkedO : link.linkedObjects()) {
						if (!visibleData.getObjectToNodeMap().containsKey(linkedO)) {
							allVisible = false;
							break;
						}
					}

					if (allVisible && (!isLinkObject || visibleData.getObjectToNodeMap().containsKey(obj)))
						showLink(link);
				}
			}
		}
	}

	@Override
	public void moveObjectNode(MObject obj, int x, int y) {
		PlaceableNode node = null;
		if (visibleData.getObjectToNodeMap().containsKey(obj)) {
			node = visibleData.getObjectToNodeMap().get(obj);
		} else if (hiddenData.getObjectToNodeMap().containsKey(obj)) {
			node = hiddenData.getObjectToNodeMap().get(obj);
		}
		if (node != null) {
			node.moveToPosition(x, y);
		}
	}

	/**
	 * Hides an object in the diagram
	 *
	 * @param obj The <code>MObject</code> to hide
	 */
	public void hideObject(MObject obj) {
		// a hidden object should no longer be selected
		ObjectNode removedNode = visibleData.getObjectToNodeMap().get(obj);
		if (removedNode != null) {
			Set<MObject> remainingObjects = new HashSet<>(model.getSelectedObjects());
			remainingObjects.remove(obj);
			model.replaceSelection(remainingObjects, model.getSelectedLinks());
			applySelectionFromModel();
		}

		showOrHideObjectNode(obj, false);
		// Hide all links the object participates in
		Set<MAssociation> assocs = obj.cls().allAssociations();
		if (obj instanceof MLinkObject) {
			assocs.add((MAssociation) obj.cls());
		}
		for (MAssociation assoc : assocs) {
            Set<MLink> links = presenter != null ? presenter.fetchLinksOfAssociation(assoc)
                    : system().state().linksOfAssociation(assoc).links();
			for (MLink link : links) {
				if (link.linkedObjects().contains(obj) || (link instanceof MLinkObject && link.equals(obj))) {
					hideLink(link);
				}
			}
		}

		this.invalidateContent(false);
	}

	/**
	 * Shows an already hidden class.
	 */
	protected void showOrHideObjectNode(MObject obj, boolean show) {
		ObjectNode n = (show ? hiddenData : visibleData).getObjectToNodeMap().get(obj);
		if (n != null) {
			if (show) {
				fGraph.add(n);
			} else {
				fGraph.remove(n);
			}
			model.moveObject(obj, show);
		}
	}

	/**
	 * Deletes an object from the diagram.
	 */
	@Override
	public void deleteObject(MObject obj) {

		ObjectNode n;
		boolean isVisible;

		if (visibleData.getObjectToNodeMap().containsKey(obj)) {
			n = visibleData.getObjectToNodeMap().get(obj);
			isVisible = true;
		} else {
			n = hiddenData.getObjectToNodeMap().get(obj);
			isVisible = false;
		}

		if (n != null) {
			nodePositionCache().put(obj, n.getPosition());
			if (isVisible) {
				fGraph.remove(n);
				visibleData.getObjectToNodeMap().remove(obj);
				fLayouter = null;
			} else {
				hiddenData.getObjectToNodeMap().remove(obj);
			}
			n.dispose();
		}
	}

	/**
	 * Adds a link to the diagram.
	 */
	@Override
	public void addLink(MLink link) {
		if (link.linkEnds().size() == 2) {
			addBinaryLink(link);
		} else {
			addNAryLink(link);
		}
	}

	protected void addNAryLink(MLink link) {
		getRandomNextPosition();

		List<ObjectNode> linkedObjectNodes = new ArrayList<>();
		for (MObject linkedObject : link.linkedObjects()) {
			linkedObjectNodes.add(visibleData.getObjectToNodeMap().get(linkedObject));
		}

		// n-ary link: create a diamond node and n edges to objects
		DiamondNode node = new DiamondNode(link, fOpt, linkedObjectNodes);
		node.setPosition(nextNodePosition);
		fGraph.add(node);

		// connected to an "object link"
		if (link instanceof MLinkObject) {
			NAryAssociationClassOrObjectEdge e = NAryAssociationClassOrObjectEdge.create(node,
					visibleData.getObjectToNodeMap().get(link), this, link.association(), true);

			fGraph.addEdge(e);
			visibleData.getLinkObjectToNodeEdge().put((MLinkObject) link, e);
			fLayouter = null;
		}
		// connected to a "normal" link
		visibleData.getNaryLinkToDiamondNodeMap().put(link, node);
		List<EdgeBase> halfEdges = new ArrayList<>();
		List<String> edgeIds = new ArrayList<>();

		for (MLinkEnd linkEnd : link.linkEnds()) {
			MObject obj = linkEnd.object();
			AssociationOrLinkPartEdge e = AssociationOrLinkPartEdge.create(node, visibleData.getObjectToNodeMap().get(obj),
					linkEnd.associationEnd(), this, link.association(), link);

			if (link.isVirtual()) {
				e.setDashed(true);
			}

			fGraph.addEdge(e);
			halfEdges.add(e);
			edgeIds.add(linkEnd.associationEnd().nameAsRolename());
		}

		// If there is an associated link-object edge, add it once (avoid duplicate map access)
		{
			EdgeBase linkObjEdge = null;
			if (link instanceof MLinkObject) {
				linkObjEdge = visibleData.getLinkObjectToNodeEdge().get((MLinkObject) link);
			}
			if (linkObjEdge != null) {
				halfEdges.add(linkObjEdge);
				edgeIds.add(((MLinkObject) link).name());
			}
		}

		node.setHalfEdges(halfEdges, edgeIds);

		visibleData.getHalfLinkToEdgeMap().put(link, halfEdges);
		fLayouter = null;
	}

	/**
	 * Show one link. The diagram is not repainted!
	 *
	 * @param link the link to show
	 */
	public void showLink(MLink link) {
		if (visibleData.containsLink(link))
			return;

		if (link instanceof MLinkObject) {
			showObject((MObject) link);
		}

		if (visibleData.containsLink(link))
			return;

		for (MObject obj : link.linkedObjects()) {
			this.showObject(obj);
		}

		if (link.linkEnds().size() == 2) {
			showBinaryLink(link);
		} else {
			showNAryLink(link);
		}
	}

	/**
	 * Hide one link. The diagram is not repainted!
	 *
	 * @param link the link to hide
	 */
	public void hideLink(MLink link) {
		if (hiddenData.containsLink(link))
			return;

		if (link.linkEnds().size() == 2) {
			hideBinaryLink(link);
		} else {
			hideNAryLink(link);
		}

		// Hide Linkobject, if link has a linkobject
		if (link instanceof MLinkObject && visibleData.getObjectToNodeMap().containsKey(link)) {
			hideObject((MObject) link);
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

	protected void showOrHideBinaryLink(MLink link, boolean show) {
		// object link
		if (link instanceof MLinkObject ml) {
			EdgeBase e = model.moveLinkObjectEdge(ml, show);
			if (e != null) {
				if (show) {
					fGraph.addInitializedEdge(e);
				} else {
					fGraph.removeEdge(e);
				}
				fLayouter = null;
			}
		} else {
			BinaryAssociationOrLinkEdge e = model.moveBinaryLink(link, show);
			if (e != null) {
				if (show) {
					fGraph.addEdge(e);
				} else {
					fGraph.removeEdge(e);
				}
				fLayouter = null;
			}
		}
	}

	protected void showOrHideNAryLink(MLink link, boolean show) {
		DiamondNode node = model.moveNaryLinkNode(link, show);
		if (node != null) {
			if (show) {
				fGraph.add(node);
			} else {
				fGraph.remove(node);
			}
		}

		if (link instanceof MLinkObject ml) {
			EdgeBase e = model.moveLinkObjectEdge(ml, show);
			if (e != null) {
				if (show) {
					fGraph.addEdge(e);
				} else {
					fGraph.removeEdge(e);
				}
				fLayouter = null;
			}
		}

		List<EdgeBase> halfEdges = model.moveHalfEdges(link, show);
		if (halfEdges != null) {
			for (EdgeBase edge : halfEdges) {
				if (show) {
					fGraph.addEdge(edge);
				} else {
					fGraph.removeEdge(edge);
				}
			}
		}
		fLayouter = null;
	}

	/**
	 * Deletes a link from the diagram.
	 */
	@Override
	public void deleteLink(MLink link) {
		if (link.linkEnds().size() == 2) {
			boolean wasVisible = visibleData.containsLink(link);
			boolean isLinkObject = link instanceof MLinkObject;

			EdgeBase e = isLinkObject ? model.popLinkObjectEdge((MLinkObject) link) : model.popBinaryLink(link);
			if (e == null) {
				return;
			}

			if (isLinkObject && e instanceof BinaryAssociationClassOrObject edge) {
				PlaceableNode objectNode = edge.getClassOrObjectNode();
				linkPositionCache().put(link, objectNode.getStrategy());
			}

			if (wasVisible) {
				fGraph.removeEdge(e);
				fLayouter = null;
			}
			e.dispose();
		} else { // n-ary association
			boolean wasVisible = visibleData.getNaryLinkToDiamondNodeMap().containsKey(link);
			DiamondNode n = model.popNaryLinkNode(link);

			if (n == null) {
				throw new RuntimeException("no diamond node for n-ary link `" + link + "' in current state.");
			}

			model.popHalfEdges(link).forEach(edge -> {
				if (wasVisible) {
					fGraph.removeEdge(edge);
				}
				edge.dispose();
			});

			if (wasVisible) {
				fGraph.remove(n);
				fLayouter = null;
			}

			n.dispose();

			if (link instanceof MLinkObject) {
				EdgeBase edge = model.popLinkObjectEdge((MLinkObject) link);
				if (edge != null) {
					linkPositionCache().put(link,
							((NAryAssociationClassOrObjectEdge) edge).getClassOrLinkObjectNode().getStrategy());
					if (wasVisible) {
						fGraph.removeEdge(edge);
					}
					edge.dispose();
				}
			}
		}
	}

	/**
	 * Forces the object node to update its content.
	 *
	 * @param obj the MInstance whose node should be refreshed
	 */
	public void updateObject(MInstance obj) {
		// Only update if the instance is actually an MObject to avoid ClassCastException
		if (!(obj instanceof MObject mObj)) {
			return;
		}

        visibleData.getObjectToNodeMap().computeIfPresent(mObj, (k, node) -> {
			invalidateNode(node);
			return node; // keep the existing mapping
		});
	}

	/** Clears the current node and edge selection. */
	public void clearSelection() {
		if (model != null) {
			model.replaceSelection(Set.of(), Set.of());
			applySelectionFromModel();
		}
		fNodeSelection.clear();
		fEdgeSelection.clear();
		invalidateContent(true);
	}

	public ObjectSelection getObjectSelection() {
		return fSelection;
	}


	/**
	 * Adds a new Link to the objectdiagram.
	 */
	class ActionInsertLink extends AbstractAction {
 		private final MAssociation fAssociation;
 		private final MObject[] fParticipants;

 		ActionInsertLink(MAssociation association, MObject[] participants) {
 			fAssociation = association;
 			fParticipants = participants;

 		 StringBuilder txt = new StringBuilder("insert (");
 		 StringUtil.fmtSeq(txt, participants, ",", MObject::name);

 		 txt.append(") into ").append(association.name());

 		 putValue(Action.NAME, txt.toString());
      }

      public void actionPerformed(ActionEvent e) {
            if (presenter != null) {
                presenter.onInsertLink(fAssociation, Arrays.asList(fParticipants));
            }
      }
   }

	/**
	 * Deletes a Link from the object diagram.
	 */
	class ActionDeleteLink extends AbstractAction {
 		private final MLink link;

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
            if (presenter != null) {
                presenter.onDeleteLink(link);
            }
            fEdgeSelection.clear();
 		}
 	}

	/**
	 * Deletes the selected objects.
	 */
	class ActionDelete extends AbstractAction {
 		private final Set<MObject> fObjects;

 		ActionDelete(String text, Set<MObject> objects) {
 			super(text);
 			fObjects = objects;
 		}

 		public void actionPerformed(ActionEvent e) {
			if (presenter != null) {
				presenter.onDeleteObjects(fObjects);
			}
			fNodeSelection.clear();
		}
 	}

	/**
	 * Show properties of objects
	 */
	class ActionShowProperties extends AbstractAction {
        private final MObject fObject;

        ActionShowProperties(String text, MObject object) {
            super(text);
            fObject = object;
        }

        public void actionPerformed(ActionEvent e) {
            if (presenter != null) {
                presenter.onShowObjectProperties(fObject);
            }
        }
    }

	/**
	 * Creates and shows popup menu if mouse event is the trigger for popups.
	 */
	protected PopupMenuInfo unionOfPopUpMenu() {
		PopupMenuInfo popupInfo = super.unionOfPopUpMenu();
		if (presenter == null) {
			return popupInfo;
		}

		JPopupMenu popupMenu = popupInfo.popupMenu;
		Set<MObject> selectedObjectsOfAssociation = new HashSet<>();
		Set<MLink> selectedLinks = new HashSet<>();
		Set<MObject> selectedObjects = new HashSet<>();

		// collect selections
		for (PlaceableNode node : fNodeSelection) {
			if (node instanceof ObjectNode on) {
				selectedObjects.add(on.object());
			} else if (node instanceof AssociationName name) {
				MLink link = name.getLink();
				selectedObjectsOfAssociation.addAll(link.linkedObjects());
				selectedLinks.add(link);
			}
		}
		for (EdgeBase selectedEdge : fEdgeSelection) {
			if (selectedEdge instanceof LinkEdge aEdge) {
				MLink link = aEdge.getLink();
				selectedLinks.add(link);
				selectedObjectsOfAssociation.addAll(link.linkedObjects());
			}
		}

		presenter.onPopupMenuPrepared(popupMenu, popupInfo.position, selectedObjects, selectedLinks, selectedObjectsOfAssociation);
		presenter.onStatusMessage("Object diagram context menu opened");
		return popupInfo;
	}

	/**
	 * Finds all nodes which are not selected.
	 *
	 * @param selectedNodes Nodes which are selected at this point in the diagram.
	 * @return A HashSet of the none selected objects in the diagram.
	 */
	private Set<MObject> getNoneSelectedNodes(Set<MObject> selectedNodes) {
		Set<MObject> noneSelectedNodes = new HashSet<>();
		for (PlaceableNode o : fGraph) {
			if (o instanceof ObjectNode) {
				MObject obj = ((ObjectNode) o).object();
				if (!selectedNodes.contains(obj)) {
					noneSelectedNodes.add(obj);
				}
			}
		}

		selectedNodes.forEach(elem -> {
			if (elem instanceof MLinkObjectImpl) {
				((MLinkObjectImpl) elem).linkedObjects().forEach(noneSelectedNodes::remove);
			}
		});
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

		MObjectState objState = obj.state(system().state());
		Map<MAttribute, Value> attributeValueMap = objState.attributeValueMap();

		for (Map.Entry<MAttribute, Value> entry : attributeValueMap.entrySet()) {
			MAttribute attr = entry.getKey();
			Value v = entry.getValue();
			attrBox.add(new JLabel(attr.name() + " = "));
			valueBox.add(new JLabel(v.toString()));
		}

		fObjectInfoWin = new JWindow();
		JPanel contentPane = new JPanel();

		Border border = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder());
		contentPane.setBorder(border);
		Box b = Box.createHorizontalBox();
		b.add(attrBox);
		b.add(valueBox);
		contentPane.add(b);
		fObjectInfoWin.setContentPane(contentPane);
		Point p = e.getPoint();
		SwingUtilities.convertPointToScreen(p, (JComponent) e.getSource());

		fObjectInfoWin.setLocation(p);// e.getPoint());
		fObjectInfoWin.pack();
		fObjectInfoWin.setVisible(true);
	}

	/**
	 *
	 * Accepts a drag of a class from the ModelBrowser. A new object of this
	 * class will be created.
	 *
	 * @param dtde the drop event provided by the DnD subsystem
	 */
	public void dropObjectFromModelBrowser(DropTargetDropEvent dtde) {
        try {
            dtde.acceptDrop(DnDConstants.ACTION_MOVE);
            Transferable transferable = dtde.getTransferable();
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                if (s.startsWith("CLASS-")) {
                    Point p = dtde.getLocation();
                    if (isDoAutoLayout()) {
                        getRandomNextPosition();
                    } else {
                        nextNodePosition.x = p.getX();
                        nextNodePosition.y = p.getY();
                    }
                    String clsName = s.substring(6);
                    if (presenter != null) {
                        presenter.onCreateObject(clsName);
                    }
                }
            }
            dtde.dropComplete(true);
        } catch (IOException | UnsupportedFlavorException ex) {
            if (fLog != null) {
                fLog.println(ex.getClass().getSimpleName() + " in dropObjectFromModelBrowser: " + ex.getMessage());
            }
            dtde.dropComplete(false);
        }
	}

	/**
	 * Checks if the object info window should be displayed.
	 *
	 * @param e MouseEvent
	 */
	public void mayBeShowObjectInfo(MouseEvent e) {
		if (fNodeSelection.size() == 1) {
			PlaceableNode node = fNodeSelection.iterator().next();
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
		for (int i = 0; i < maxDigits; ++i) {
			res[i] = number % base;
			number = number / base;
		}
		return res;
	}

	private boolean isCompleteObjectCombination(int[] c, int base) {
		for (int i = 0; i < base; i++) {
			boolean found = false;
			for (int v : c) {
				if (v == i) {
					found = true;
					break;
				}
			}
			if (!found)
				return false;
		}
		return true;
	}

	/**
	 * Finds a random new position for the next object node.
	 */
	protected void getRandomNextPosition() {
		// getWidth and getHeight return 0
		// if we are called on a new diagram.
		nextNodePosition.x = Math.random() * Math.max(100, getWidth() - 100);
		nextNodePosition.y = Math.random() * Math.max(100, getHeight() - 100);
	}

	@Override
	public void resetLayout() {
		if (presenter != null) {
			presenter.onResetDiagram((ObjDiagramOptions) fOpt);
		} else {
			context.resetDiagram((ObjDiagramOptions) fOpt);
		}
	}

	@Override
	public void storePlacementInfos(PersistHelper helper, Element root) {
        if (presenter != null) {
            presenter.onStoreLayout(helper, root, model);
         }
     }

     @Override
     public void restorePlacementInfos(PersistHelper helper, int version) {
        if (presenter != null) {
            Set<MObject> hidden = presenter.onRestoreLayout(helper, version, model, system());
             if (hidden != null && !hidden.isEmpty()) {
                 hideElementsInDiagram(hidden);
             }
         }
     }

    @Override
    protected String getDefaultLayoutFileSuffix() {
        // Object diagrams do not auto-load/save a default layout file.
        return null;
    }

	/**
	 * A small helper method for creating {@link Action} instances with less boilerplate code.
	 *
	 * @param text The text of the action.
	 * @param listener The {@link.ActionListener} that executes the action.
	 * @return The created action.
	 */
	private Action createAction(String text, ActionListener listener) {
		return new AbstractAction(text) {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.actionPerformed(e);
			}
		};
	}

	protected void addBinaryLink(MLink link) {
		MAssociation assoc = link.association();

		MLinkEnd linkEnd1 = link.linkEnd(assoc.associationEnds().get(0));
		MLinkEnd linkEnd2 = link.linkEnd(assoc.associationEnds().get(1));

		MObject obj1 = linkEnd1.object();
		MObject obj2 = linkEnd2.object();

		// object link
		if (link instanceof MLinkObject) {
			BinaryAssociationClassOrObject e = BinaryAssociationClassOrObject.create(
					visibleData.getObjectToNodeMap().get(obj1), visibleData.getObjectToNodeMap().get(obj2),
					linkEnd1, linkEnd2, visibleData.getObjectToNodeMap().get(link), this,
					link);

			BinaryAssociationClassOrObject edge = (BinaryAssociationClassOrObject) visibleData.getLinkObjectToNodeEdge().get((MLinkObject) link);
			if (edge != null) {
				PlaceableNode objectNode = edge.getClassOrObjectNode();
				linkPositionCache().put(link, objectNode.getStrategy());
			}

			fGraph.addEdge(e);
			visibleData.getLinkObjectToNodeEdge().put((MLinkObject) link, e);
			fLayouter = null;
		} else {
			// binary link
			boolean isHidden = false;
			ObjectNode node1;
			ObjectNode node2;

			if (visibleData.getObjectToNodeMap().containsKey(obj1)) {
				node1 = visibleData.getObjectToNodeMap().get(obj1);
			} else {
				node1 = hiddenData.getObjectToNodeMap().get(obj1);
				isHidden = true;
			}

			if (visibleData.getObjectToNodeMap().containsKey(obj2)) {
				node2 = visibleData.getObjectToNodeMap().get(obj2);
			} else {
				node2 = hiddenData.getObjectToNodeMap().get(obj2);
				isHidden = true;
			}

			BinaryAssociationOrLinkEdge e = createBinaryAssociationOrLinkEdge(node1, node2, linkEnd1,
					linkEnd2, this, link);

			if (link.isVirtual()) {
				e.setDashed(true);
			}

			if (isHidden) {
				hiddenData.getBinaryLinkToEdgeMap().put(link, e);
			} else {
				fGraph.addEdge(e);
				visibleData.getBinaryLinkToEdgeMap().put(link, e);
				fLayouter = null;
			}
		}
	}

	/**
	 * Factory hook for creating binary link edges. Kept as a protected method to allow easy overriding in subclasses.
	 */
	protected BinaryAssociationOrLinkEdge createBinaryAssociationOrLinkEdge(PlaceableNode source, PlaceableNode target,
							MLinkEnd sourceEnd, MLinkEnd targetEnd, NewObjectDiagram diagram, MLink link) {
		return BinaryAssociationOrLinkEdge.create(source, target, sourceEnd, targetEnd, diagram, link);
	}

    @Override
    public ObjectDiagramData getHiddenData() {
        return hiddenData;
    }

    @Override
    public ObjectDiagramData getVisibleData() {
        return visibleData;
    }

    @Override
    public Set<? extends PlaceableNode> getHiddenNodes() {
        return hiddenData.getNodes();
    }

    /** Used by JavaFX caller to toggle FX-mode behavior. */
    public static void setJavaFxCall(boolean value) {
        javaFxCall = value;
    }

    /** Sort change from ModelBrowserSorting. */
    @Override
    public void stateChanged(ModelBrowserSorting.SortChangeEvent e) {
        for (ObjectNode n : this.visibleData.getObjectToNodeMap().values()) {
            n.stateChanged(e);
        }
    }

    /** Check if a link is currently hidden. */
    public boolean isHidden(MLink link) {
        return hiddenData.containsLink(link);
    }

    /**
     * Returns 0 if all links visible, 1 if all hidden, 2 if mixed.
     */
    public int isHidden(List<MLink> links) {
        boolean anyVisible = false;
        boolean anyHidden = false;
        for (MLink l : links) {
            if (isHidden(l)) {
                anyHidden = true;
            } else {
                anyVisible = true;
            }
            if (anyHidden && anyVisible) {
                return 2;
            }
        }
        if (anyHidden) {
            return 1;
        }
        return 0;
    }

    /** Group links by association name for ObjectSelection needs. */
    public Map<String, List<MLink>> mapLinksToKindOfAssociation(List<MLink> links) {
        Map<String, List<MLink>> result = new HashMap<>();
        for (MLink link : links) {
            String key = link.association().name();
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(link);
        }
        return result;
    }

    /** Legacy overload: collects all links grouped by association. */
    public TreeMap<String, List<MLink>> mapLinksToKindOfAssociation() {
        TreeMap<String, List<MLink>> result = new TreeMap<>();
        Collection<MAssociation> assocs = presenter != null ? presenter.fetchAllAssociations()
                : system().model().associations();
        for (MAssociation assoc : assocs) {
            Set<MLink> links = presenter != null ? presenter.fetchLinksOfAssociation(assoc)
                    : system().state().linksOfAssociation(assoc).links();
            for (MLink link : links) {
                result.computeIfAbsent(assoc.name(), k -> new ArrayList<>()).add(link);
            }
        }
        return result;
    }

     /** Hide all given links. */
     public void hideLink(List<MLink> links) {
         for (MLink link : links) {
             hideLink(link);
         }
     }

     /** Show all given links. */
     public void showLink(List<MLink> links) {
         for (MLink link : links) {
             showLink(link);
         }
     }

    private Map<MObject, Point2D> nodePositionCache() {
        return model.getLastKnownNodePositions();
    }

    private Map<MLink, PositionStrategy> linkPositionCache() {
        return model.getLastKnownLinkPositions();
    }

    /** Ensure diagram uses the current model as single source of truth. */
    private void rebindModelData() {
         if (this.model == null) {
             this.model = new NewObjectDiagramModel();
         }
         this.visibleData = model.getVisibleData();
         this.hiddenData = model.getHiddenData();
         applySelectionFromModel();
     }

    /** Apply selection from model to local selection sets for rendering. */
    private void applySelectionFromModel() {
        fNodeSelection.clear();
        fEdgeSelection.clear();
        for (MObject obj : model.getSelectedObjects()) {
            ObjectNode node = visibleData.getObjectToNodeMap().get(obj);
            if (node != null) {
                fNodeSelection.add(node);
            }
        }
        for (MLink link : model.getSelectedLinks()) {
            EdgeBase edge = visibleData.getBinaryLinkToEdgeMap().get(link);
            if (edge != null) {
                fEdgeSelection.add(edge);
            }
            if (link instanceof MLinkObject ml) {
                EdgeBase linkObjEdge = visibleData.getLinkObjectToNodeEdge().get(ml);
                if (linkObjEdge != null) {
                    fEdgeSelection.add(linkObjEdge);
                }
            }
            List<EdgeBase> halfEdges = visibleData.getHalfLinkToEdgeMap().get(link);
            if (halfEdges != null) {
                fEdgeSelection.addAll(halfEdges);
            }
            DiamondNode node = visibleData.getNaryLinkToDiamondNodeMap().get(link);
            if (node != null) {
                fNodeSelection.add(node);
            }
        }
    }

    public void onPointerMoved(Point p) {
        if (presenter != null) {
            presenter.onStatusMessage("[x=" + p.getX() + ", y=" + p.getY() + "]");
        } else if (getStatusBar() != null) {
            getStatusBar().showMessage("[x=" + p.getX() + ", y=" + p.getY() + "] ", BorderLayout.EAST);
        }
    }

    public void onPointerExited() {
        if (presenter != null) {
            presenter.onStatusMessage("");
        } else if (getStatusBar() != null) {
            getStatusBar().clearMessage();
        }
    }
 }
