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

import static org.tzi.use.util.collections.CollectionUtil.exactlyOne;

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
import java.awt.event.ItemEvent;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

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

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.ObjectPropertiesView;
import org.tzi.use.gui.views.diagrams.DiagramType;
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
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyFixed;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationClassImpl;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.*;
import org.tzi.use.util.StringUtil;
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
public class NewObjectDiagram extends DiagramViewWithObjectNode implements HighlightChangeListener, SortChangeListener {

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

	protected final ObjectDiagramData visibleData = new ObjectDiagramData();

	private final ObjectDiagramData hiddenData = new ObjectDiagramData();

	private final NewObjectDiagramView fParent;

	/**
	 * The position of the next object node. This is either set to a random
	 * value or to a specific position when an object is created by drag & drop.
	 */
	protected Point2D.Double nextNodePosition = new Point2D.Double();

	/**
	 * Last position of deleted nodes and links. In case of restoration, the
	 * nodes (object nodes, linkobject nodes) are positioned as they were
	 * before. Objects use {@link StrategyFixed}, so we just save the position.
	 */
	private final Map<MObject, Point2D> lastKnownNodePositions = new WeakHashMap<>();
	private final Map<MLink, PositionStrategy> lastKnownLinkPositions = new WeakHashMap<>();

	// show object properties listener is attached inline in constructor (no field required)

	private final ObjectSelection fSelection;

	protected final DiagramInputHandling inputHandling;

	@SuppressWarnings("unused")
	private static boolean javaFxCall = false;

	/**
	 * Creates a new empty diagram.
	 */
	NewObjectDiagram(NewObjectDiagramView parent, PrintWriter log) {
		this(parent, log, new ObjDiagramOptions());
	}

	protected NewObjectDiagram(NewObjectDiagramView parent, PrintWriter log, ObjDiagramOptions options) {
		super(options, log);
		this.getRandomNextPosition();

		fParent = parent;
		// FIXME: Handle fonts!
		parent.setFont(getFont());

		fSelection = new ObjectSelection(this, parent.system());

		fActionSaveLayout = new ActionSaveLayout("USE object diagram layout", "olt", this);

		fActionLoadLayout = new ActionLoadLayout("USE object diagram layout", "olt", this);

		inputHandling = new DiagramInputHandling(fNodeSelection, fEdgeSelection, this);

		fParent.getModelBrowser().addHighlightChangeListener(this);
		ModelBrowserSorting.getInstance().addSortChangeListener(this);
		fParent.addKeyListener(inputHandling);

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

	/**
	 * Displays objects of the selected class in the modelbrowser.
	 */
	public void stateChanged(HighlightChangeEvent e) {
		if (!fParent.isSelectedView()) {
			return;
		}

		MModelElement elem = e.getModelElement();
		List<EdgeBase> edges = new ArrayList<>();
		boolean allEdgesSelected = true;

		// elem is an association
		if (elem instanceof MAssociation assoc) {
			int size = assoc.associationEnds().size();

			Set<MLink> links = fParent.system().state().linksOfAssociation(assoc).links();
			EdgeBase ed;

			if (size == 2) {
				for (MLink link : links) {
					if (elem instanceof MAssociationClass) {
						if (link instanceof MLinkObject) {
							ed = visibleData.getLinkObjectToNodeEdge().get((MLinkObject) link);
						} else {
							ed = visibleData.getBinaryLinkToEdgeMap().get(link);
						}
					} else {
						ed = visibleData.getBinaryLinkToEdgeMap().get(link);
					}
					edges.add(ed);
				}
			} else {
				for (MLink link : links) {
					edges.addAll(visibleData.getHalfLinkToEdgeMap().getOrDefault(link, Collections.emptyList()));

					if (elem instanceof MAssociationClass) {
						if (link instanceof MLinkObject) {
							ed = visibleData.getLinkObjectToNodeEdge().get((MLinkObject) link);
							edges.add(ed);
						}
					}
				}
			}

			// check all edges in the list if they are supposed to be selected
			for (EdgeBase edge : edges) {
				if (edge != null) {
					if (e.getHighlight()) {
						fEdgeSelection.add(edge);
						allEdgesSelected = true;
					} else {
						fEdgeSelection.remove(edge);
						allEdgesSelected = false;
					}
				}
			}
		}

		// elem is a class
		if (elem instanceof MClass) {
			for (MObject obj : fParent.system().state().objectsOfClass((MClass) elem)) {
				PlaceableNode node = visibleData.getObjectToNodeMap().get(obj);
				if (elem instanceof MAssociationClass) {
					if (e.getHighlight() && allEdgesSelected) {
						fNodeSelection.add(node);
					} else {
						fNodeSelection.remove(node);
					}
				} else {
					if (e.getHighlight()) {
						fNodeSelection.add(node);
					} else {
						fNodeSelection.remove(node);
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
		for (MAssociation assoc : fParent.system().model().associations()) {
			showLink(new ArrayList<>(fParent.system().state().linksOfAssociation(assoc).links()));
		}
	}

	/**
	 * Adds an object to the diagram.
	 */
	public void addObject(MObject obj) {
		ObjectNode n = new ObjectNode(obj, fParent, getOptions());
		n.setMinWidth(minClassNodeWidth);
		n.setMinHeight(minClassNodeHeight);

		if (lastKnownNodePositions.containsKey(obj)) {
			n.moveToPosition(lastKnownNodePositions.get(obj));
			lastKnownNodePositions.remove(obj);
		} else {
			n.setPosition(nextNodePosition);
			getRandomNextPosition();
		}

		fGraph.add(n);
		visibleData.getObjectToNodeMap().put(obj, n);
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
			MLinkSet links = fParent.system().state().linksOfAssociation(assoc);
			// TODO: Not very fast!
			for (MLink link : links.links()) {
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
			fNodeSelection.remove(removedNode);
		}

		showOrHideObjectNode(obj, false);

		// Hide all links the object participates in
		Set<MAssociation> assocs = obj.cls().allAssociations();

		if (obj instanceof MLinkObject) {
			assocs.add((MAssociation) obj.cls());
		}

		for (MAssociation assoc : assocs) {
			MLinkSet links = fParent.system().state().linksOfAssociation(assoc);
			// TODO: Not very fast!
			for (MLink link : links.links()) {
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
		ObjectDiagramData source = (show ? hiddenData : visibleData);
		ObjectDiagramData target = (show ? visibleData : hiddenData);

		ObjectNode n = source.getObjectToNodeMap().get(obj);

		if (n != null) {
			if (show)
				fGraph.add(n);
			else
				fGraph.remove(n);

			source.getObjectToNodeMap().remove(obj);
			target.getObjectToNodeMap().put(obj, n);

			fLayouter = null;
		}
	}

	/**
	 * Deletes an object from the diagram.
	 */
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
			lastKnownNodePositions.put(obj, n.getPosition());
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
		ObjectDiagramData source = (show ? hiddenData : visibleData);
		ObjectDiagramData target = (show ? visibleData : hiddenData);

		// object link
		if (link instanceof MLinkObject) {
			EdgeBase e = source.getLinkObjectToNodeEdge().get((MLinkObject) link);
			if (show && e != null)
				fGraph.addInitializedEdge(e);
			else if (e != null)
				fGraph.removeEdge(e);

			source.getLinkObjectToNodeEdge().remove((MLinkObject) link);
			target.getLinkObjectToNodeEdge().put((MLinkObject) link, e);
			fLayouter = null;
		} else {
			// binary link
			BinaryAssociationOrLinkEdge e = source.getBinaryLinkToEdgeMap().get(link);

			if (show && e != null) {
				fGraph.addEdge(e);
			} else if (e != null) {
				fGraph.removeEdge(e);
			}
			source.getBinaryLinkToEdgeMap().remove(link);
			target.getBinaryLinkToEdgeMap().put(link, e);
		}
	}

	protected void showOrHideNAryLink(MLink link, boolean show) {
		ObjectDiagramData source = (show ? hiddenData : visibleData);
		ObjectDiagramData target = (show ? visibleData : hiddenData);

		DiamondNode node = source.getNaryLinkToDiamondNodeMap().get(link);
		if (show) {
			if (node == null) {
				node = target.getNaryLinkToDiamondNodeMap().get(link);
			}
			fGraph.add(node);
		} else {
			fGraph.remove(node);
		}
		target.getNaryLinkToDiamondNodeMap().put(link, node);
		source.getNaryLinkToDiamondNodeMap().remove(link);


		// connected to an "object link"
		if (link instanceof MLinkObject) {
			EdgeBase e = source.getLinkObjectToNodeEdge().get((MLinkObject) link);

			if (e != null) {
				if (show)
					fGraph.addEdge(e);
				else
					fGraph.removeEdge(e);

				source.getLinkObjectToNodeEdge().remove((MLinkObject) link);
				target.getLinkObjectToNodeEdge().put((MLinkObject) link, e);
				fLayouter = null;
			}
		}

		List<EdgeBase> halfEdges = source.getHalfLinkToEdgeMap().get(link);

		if (halfEdges != null) {
			for (EdgeBase edge : halfEdges) {
				if (show)
					fGraph.addEdge(edge);
				else
					fGraph.removeEdge(edge);
			}

			source.getHalfLinkToEdgeMap().remove(link);
			target.getHalfLinkToEdgeMap().put(link, halfEdges);
		}
		fLayouter = null;
	}

	/**
	 * Deletes a link from the diagram.
	 */
	public void deleteLink(MLink link) {
		if (link.linkEnds().size() == 2) {
			EdgeBase e; // initialize later based on visibility
			boolean isVisible;
			boolean isLinkObject = link instanceof MLinkObject;
			ObjectDiagramData data;

			if (isLinkObject) {
				isVisible = visibleData.getLinkObjectToNodeEdge().containsKey((MLinkObject) link);
				data = isVisible ? visibleData : hiddenData;
				e = data.getLinkObjectToNodeEdge().get((MLinkObject) link);
			} else {
				isVisible = visibleData.getBinaryLinkToEdgeMap().containsKey(link);
				data = isVisible ? visibleData : hiddenData;
				e = data.getBinaryLinkToEdgeMap().get(link);
			}

			if (e == null) {
				return;
			}

			if (isLinkObject) {
				BinaryAssociationClassOrObject edge = (BinaryAssociationClassOrObject) data.getLinkObjectToNodeEdge().get((MLinkObject) link);
				if (edge != null) {
					PlaceableNode objectNode = edge.getClassOrObjectNode();
					lastKnownLinkPositions.put(link, objectNode.getStrategy());
				}

				data.getBinaryLinkToEdgeMap().remove(link);
				data.getLinkObjectToNodeEdge().remove((MLinkObject) link);
			} else {
				data.getBinaryLinkToEdgeMap().remove(link);
			}

			if (isVisible) {
				fGraph.removeEdge(e);
				fLayouter = null;
			}
			e.dispose();
		} else { // n-ary association
			boolean isVisible;
			ObjectDiagramData data;

			isVisible = visibleData.getNaryLinkToDiamondNodeMap().containsKey(link);
			data = isVisible ? visibleData : hiddenData;
			DiamondNode n = data.getNaryLinkToDiamondNodeMap().get(link);

			if (n == null) {
				throw new RuntimeException("no diamond node for n-ary link `" + link + "' in current state.");
			}

			data.getNaryLinkToDiamondNodeMap().remove(link);
			data.getHalfLinkToEdgeMap().remove(link);

			if (isVisible) {
				fGraph.remove(n);
				fLayouter = null;
			}

			n.dispose();

			if (link instanceof MLinkObject) {
				EdgeBase edge = data.getLinkObjectToNodeEdge().get((MLinkObject) link);
				if (edge != null) {
					lastKnownLinkPositions.put(link,
							((NAryAssociationClassOrObjectEdge) edge).getClassOrLinkObjectNode().getStrategy());
					fGraph.removeEdge(edge);
					data.getLinkObjectToNodeEdge().remove((MLinkObject) link);
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
		// Use computeIfPresent with an explicit cast of the key to the Map's key type (MObject)
		visibleData.getObjectToNodeMap().computeIfPresent((MObject) obj, (k, node) -> {
			invalidateNode(node);
			return node; // keep the existing mapping
		});
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
			fParent.insertLink(fAssociation, fParticipants);
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
			fParent.deleteLink(link);
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
			fParent.deleteObjects(fObjects);
			fNodeSelection.clear();
		}
	}

	/**
	 * Show properties of objects
	 */
	@SuppressWarnings("unused")
	static class ActionShowProperties extends AbstractAction {
		private final MObject fObject;

		ActionShowProperties(String text, MObject object) {
			super(text);
			fObject = object;
		}

		public void actionPerformed(ActionEvent e) {
			if (MainWindow.getJavaFxCall()) {
				Platform.runLater(() -> {
					// to create an instance of a SwingNode, which is used to hold the Swing-Components
					SwingNode swingNode = new SwingNode();

					// Create the ClassInvariantView and the enclosing ViewFrame in the javaFX DesktopPane
					ObjectPropertiesView opv = new ObjectPropertiesView(MainWindow.instance(), org.tzi.use.gui.mainFX.MainWindow.getInstance().getSession().system());
					opv.selectObject(fObject.name()); //selecting the focused Object

					ViewFrame f = new ViewFrame("Object properties", opv, "ObjectProperties.gif");

					// Set up the SwingNode content
					JComponent c = (JComponent) f.getContentPane();
					c.setLayout(new BorderLayout());
					c.add(opv, BorderLayout.CENTER);

					// Add the Swing component to the SwingNode
					swingNode.setContent(c);
					swingNode.setCache(false); //This helps ensure the image is re‐drawn more directly, often yielding a crisper result.

					// creating the new Window with the swingNode
					org.tzi.use.gui.mainFX.MainWindow.getInstance().createNewWindow("Object properties", swingNode, DiagramType.OBJECT_PROPERTIES_VIEW);
				});
			} else {
				ObjectPropertiesView v = MainWindow.instance().showObjectPropertiesView();
				v.selectObject(fObject.name());
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
		final Set<MObject> selectedObjectsOfAssociation = new HashSet<>();
		final Set<MLink> selectedLinks = new HashSet<>();
		final List<MObject> selectedObjects = new ArrayList<>();

		// Split selected nodes into model elements
		for (PlaceableNode node : fNodeSelection) {
			if (node instanceof ObjectNode) {
				selectedObjects.add(((ObjectNode) node).object());
			} else if (node instanceof AssociationName) {
				MLink link = ((AssociationName) node).getLink();
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

		// Just to be sure to delete an object only once
		Set<MObject> selectedObjectsSet = new HashSet<>(selectedObjects);

		// This text is reused often
		String selectedObjectsText = null;
		if (selectedObjects.size() == 1) {
			selectedObjectsText = "'" + exactlyOne(selectedObjects).name() + "'";
		} else if (selectedObjects.size() > 1) {
			selectedObjectsText = selectedObjects.size() + " objects";
		}

		if (!selectedObjects.isEmpty()) {
			// A single object can be edited
			if (selectedObjects.size() == 1) {
				popupMenu.insert(
					new ActionShowProperties("Edit properties of " + selectedObjectsText, exactlyOne(selectedObjects)),
					pos++);
			}

			// A single object or multiple objects can be deleted.
			popupMenu.insert(new ActionDelete("Destroy " + selectedObjectsText, selectedObjectsSet), pos++);
			popupMenu.insert(new JSeparator(), pos++);
		}

		// Possible links between objects
		if (!selectedObjects.isEmpty()) {
			int m = selectedObjects.size();
			boolean addedInsertLinkAction = false;

			for (MAssociation assoc : fParent.system().model().associations()) {
				if (assoc.isReadOnly())
					continue;

				int n = assoc.associationEnds().size();
				// More objects then ends selected
				if (m > n)
					continue;

				int pow = 1;
				for (int i = 0; i < n; ++i)
					pow *= m;

				for (int i = 0; i < pow; ++i) {
					MObject[] l = new MObject[n];
					MClass[] c = new MClass[n];
					int[] digits = radixConversion(i, m, n);

					if (!isCompleteObjectCombination(digits, m))
						continue;

					for (int j = 0; j < n; ++j) {
						l[j] = selectedObjects.get(digits[j]);
						c[j] = l[j].cls();
					}

					if (!assoc.isAssignableFrom(c))
						continue;

					Set<MLink> links = fParent.linksBetweenObjects(assoc, l);
					if (links.isEmpty() || assoc.hasQualifiedEnds()) {
						popupMenu.insert(new ActionInsertLink(assoc, l), pos++);
						addedInsertLinkAction = true;
					}

					if (!links.isEmpty()) {
						for (MLink link : links) {
							popupMenu.insert(new ActionDeleteLink(link), pos++);
							addedInsertLinkAction = true;
						}
					}
				}

			}

			if (addedInsertLinkAction)
				popupMenu.insert(new JSeparator(), pos++);
		}

		if (!hiddenData.getObjectToNodeMap().isEmpty() || !hiddenData.getBinaryLinkToEdgeMap().isEmpty()
				|| !hiddenData.getNaryLinkToDiamondNodeMap().isEmpty() || !hiddenData.getHalfLinkToEdgeMap().isEmpty()
				|| !hiddenData.getLinkObjectToNodeEdge().isEmpty()) {
			final JMenuItem showAllObjects = new JMenuItem("Show hidden elements");
			showAllObjects.addActionListener(ev -> {
				showAll();
				showAllLinks();
				invalidateContent(true);
			});

			popupMenu.insert(showAllObjects, pos++);
			popupMenu.insert(new JSeparator(), pos++);
		}

		// Hide / crop / show links
		if (!selectedLinks.isEmpty() || !selectedObjects.isEmpty() || !selectedObjectsOfAssociation.isEmpty()) {
			String labelHide = "";
			String labelCrop = "";

			int size = selectedLinks.size() + selectedObjects.size();

			if (size > 1) {
				labelHide = "Hide " + size + " elements";
				labelCrop = "Crop " + size + " elements";
			} else if (selectedLinks.size() == 1) {
				labelHide = "Hide " + fSelection.formatLinkName(selectedLinks.iterator().next());
				labelCrop = "Crop " + fSelection.formatLinkName(selectedLinks.iterator().next());
			} else if (selectedObjects.size() == 1) {
				labelHide = "Hide " + selectedObjects.listIterator().next().name();
				labelCrop = "Crop " + selectedObjects.listIterator().next().name();
			}

			// New Action for Hide
			popupMenu.insert(createAction(labelHide, arg0 -> {
				if (!selectedLinks.isEmpty()) {
					selectedLinks.forEach(NewObjectDiagram.this::hideLink);
				}
				if (!selectedObjects.isEmpty()) {
					selectedObjects.forEach(NewObjectDiagram.this::hideObject);
				}
				repaint();
			}), pos++);

			// new Action for crop
			Set<MObject> objectsToHide = new HashSet<>(selectedObjects);
			selectedLinks.forEach(link -> {
				if (link instanceof MLinkObject) {
					objectsToHide.add((MLinkObject) link);
				} else {
					objectsToHide.addAll(link.linkedObjects());
				}
			});

			final String labelCropFinal = labelCrop; // ensure effectively final
			popupMenu.insert(createAction(labelCrop, e -> getAction(labelCropFinal, getNoneSelectedNodes(objectsToHide)).actionPerformed(e)), pos++);

			// new Action for gray in/out (single)
			if (selectedObjects.size() == 1) {
				MObject obj = exactlyOne(selectedObjects);
				ObjectNode node = visibleData.getObjectToNodeMap().get(obj);
				if (node != null) {
					String labelGreyed = node.isGreyed() ? "Gray in" : "Gray out";

					popupMenu.insert(createAction(labelGreyed + " " + node.name(), e -> {
						node.setGreyed(!node.isGreyed());
						repaint();
					}), pos++);
				}
			} else if (selectedObjects.size() > 1) {
				Set<ObjectNode> objToGreyIn = new HashSet<>();
				Set<ObjectNode> objToGreyOut = new HashSet<>();
				for (MObject so : selectedObjects) {
					ObjectNode n = visibleData.getObjectToNodeMap().get(so);
					if (n != null) {
						if (n.isGreyed()) objToGreyIn.add(n);
						else objToGreyOut.add(n);
					}
				}

				// gray in
				if (!objToGreyIn.isEmpty()) {
					popupMenu.insert(createAction("Gray in " + objToGreyIn.size() + " elements", arg0 -> {
						objToGreyIn.forEach(n -> n.setGreyed(false));
						repaint();
					}), pos++);
				}

				// gray out
				if (!objToGreyOut.isEmpty()) {
					popupMenu.insert(createAction("Gray out " + objToGreyOut.size() + " elements", arg0 -> {
						objToGreyOut.forEach(n -> n.setGreyed(true));
						repaint();
					}), pos++);
				}
			}
		}
		popupMenu.insert(new JSeparator(), pos++);

		final JMenu showProtocolStateMachine = new JMenu("Show protocol state machine...");
		showProtocolStateMachine.setEnabled(false);
		popupMenu.insert(showProtocolStateMachine, pos++);

		if (selectedObjects.size() == 1) {
			final MObject obj = exactlyOne(selectedObjects);

			List<MProtocolStateMachine> sortedPSMs = new LinkedList<>(
					obj.cls().getAllOwnedProtocolStateMachines());
			sortedPSMs.sort(new MNamedElementComparator());

			for (MProtocolStateMachine psm : sortedPSMs) {
				showProtocolStateMachine.setEnabled(true);
				final JMenuItem showGivenPSM = new JMenuItem(psm.name());
				showGivenPSM.addActionListener(new ActionListener() {
					private MProtocolStateMachine sm;

					public void actionPerformed(ActionEvent ev) {
						MainWindow.instance().showStateMachineView(sm, obj);
					}

					public ActionListener setStateMachine(MProtocolStateMachine sm) {
						this.sm = sm;
						return this;
					}
				}.setStateMachine(psm));
				showProtocolStateMachine.add(showGivenPSM);
			}
		}

		popupMenu.insert(new JSeparator(), pos++);

		final JCheckBoxMenuItem showStates = new JCheckBoxMenuItem("Show states", getOptions().isShowStates());
		showStates.addItemListener(ev -> {
			getOptions().setShowStates(ev.getStateChange() == ItemEvent.SELECTED);
			invalidateContent(true);
		});

		popupMenu.insert(showStates, pos + 3);

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

			// we accept only Strings
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
					fParent.createObject(clsName);
				}
			}
			dtde.dropComplete(true);
		} catch (IOException exception) {
			if (fLog != null) {
				fLog.println("IOException in dropObjectFromModelBrowser: " + exception.getMessage());
			} else {
				System.err.println("IOException in dropObjectFromModelBrowser: " + exception.getMessage());
			}
			dtde.dropComplete(false);
		} catch (UnsupportedFlavorException ufException) {
			if (fLog != null) {
				fLog.println("UnsupportedFlavorException in dropObjectFromModelBrowser: " + ufException.getMessage());
			} else {
				System.err.println("UnsupportedFlavorException in dropObjectFromModelBrowser: " + ufException.getMessage());
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
		for (int i = 0; i < base; ++i) {
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
		fParent.initDiagram(false, (ObjDiagramOptions) fOpt);
		fParent.validate();
	}

	@Override
	public void storePlacementInfos(PersistHelper helper, Element root) {
		storePlacementInfos(helper, root, true);
		storePlacementInfos(helper, root, false);
	}

	protected void storePlacementInfos(PersistHelper helper, Element root, boolean visible) {
		ObjectDiagramData data = (visible ? visibleData : hiddenData);

		for (ObjectNode n : data.getObjectToNodeMap().values()) {
			n.storePlacementInfo(helper, root, !visible);
		}

		for (PlaceableNode n : data.getNaryLinkToDiamondNodeMap().values()) {
			n.storePlacementInfo(helper, root, !visible);
		}

		for (EdgeBase e : data.getBinaryLinkToEdgeMap().values()) {
			e.storePlacementInfo(helper, root, !visible);
		}

		for (EdgeBase e : data.getLinkObjectToNodeEdge().values()) {
			e.storePlacementInfo(helper, root, !visible);
		}
	}

	@Override
	public void restorePlacementInfos(PersistHelper helper, int version) {
		if (version < 12) return;

		Set<MObject> hiddenObjects = new HashSet<>();

		// delegate to smaller, well-scoped helpers
		restoreBinaryEdges(helper, version, hiddenObjects);
		restoreNodeEdges(helper, version, hiddenObjects);
		restoreDiamondNodes(helper, version, hiddenObjects);

		// Hide elements collected during restore
		hideElementsInDiagram(hiddenObjects);
	}

	// Helper: restore binary association edges placement info
	@SuppressWarnings("unused")
	private void restoreBinaryEdges(PersistHelper helper, int version, Set<MObject> hiddenObjects) {
		AutoPilot ap = new AutoPilot(helper.getNav());
		helper.getNav().push();
		try {
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
							link = fParent.system().state().linkBetweenObjects(assoc, Arrays.asList(sourceObject, targetObject), Collections.emptyList());
						}
						if (link != null) {
							BinaryAssociationOrLinkEdge edge = visibleData.getBinaryLinkToEdgeMap().get(link);
							if (edge != null) {
								edge.restorePlacementInfo(helper, version);
							}
						}
					}
				}
			} catch (XPathEvalException | NavException e) {
				if (fLog != null) fLog.println(e.getMessage());
				else System.err.println(e.getMessage());
			}
		} catch (XPathParseException e) {
			if (fLog != null) fLog.println(e.getMessage());
			else System.err.println(e.getMessage());
		} finally {
			ap.resetXPath();
			helper.getNav().pop();
		}
	}

	// Helper: restore node-edge (link objects / node edges)
	@SuppressWarnings("unused")
	private void restoreNodeEdges(PersistHelper helper, int version, Set<MObject> hiddenObjects) {
		AutoPilot ap = new AutoPilot(helper.getNav());
		helper.getNav().push();
		try {
			ap.selectXPath("./edge[@type='NodeEdge']");
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
							link = fParent.system().state().linkBetweenObjects(assoc, Arrays.asList(sourceObject, targetObject), Collections.emptyList());
						}
						if (link != null) {
							if (link instanceof MLinkObject linkObj) {
                                EdgeBase tmp = visibleData.getLinkObjectToNodeEdge().get(linkObj);
                                if (tmp instanceof BinaryAssociationClassOrObject edge) {
                                    edge.restorePlacementInfo(helper, version);
                                }
                            }
						}
					}
				}
			} catch (XPathEvalException | NavException e) {
				if (fLog != null) fLog.println(e.getMessage());
				else System.err.println(e.getMessage());
			}
		} catch (XPathParseException e) {
			if (fLog != null) fLog.println(e.getMessage());
			else System.err.println(e.getMessage());
		} finally {
			ap.resetXPath();
			helper.getNav().pop();
		}
	}

	// Helper: restore diamond nodes (n-ary links)
	private void restoreDiamondNodes(PersistHelper helper, int version, Set<MObject> hiddenObjects) {
		AutoPilot ap = new AutoPilot(helper.getNav());
		helper.getNav().push();
		try {
			ap.selectXPath("./node[@type='Object']");
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					MObject obj = fParent.system().state().objectByName(name);
					if (obj != null) {
						ObjectNode node = visibleData.getObjectToNodeMap().get(obj);
						if (node != null) {
							node.restorePlacementInfo(helper, version);
							if (isHidden(helper, version)) hiddenObjects.add(obj);
						}
					}
				}
			} catch (XPathEvalException | NavException e) {
				if (fLog != null) fLog.println(e.getMessage());
				else System.err.println(e.getMessage());
			}
		} catch (XPathParseException e) {
			if (fLog != null) fLog.println(e.getMessage());
			else System.err.println(e.getMessage());
		} finally {
			ap.resetXPath();
			helper.getNav().pop();
		}

		// now diamond nodes (separate xpath)
		ap = new AutoPilot(helper.getNav());
		helper.getNav().push();
		try {
			ap.selectXPath("./node[@type='DiamondNode']");
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					MAssociation assoc = fParent.system().model().getAssociation(name);
					if (assoc == null) continue;
					List<MObject> connectedObjects = new LinkedList<>();
					if (!helper.toFirstChild("connectedNode")) continue;
					String objectName = helper.getElementStringValue();
					MObject obj = fParent.system().state().objectByName(objectName);
					if (obj != null) connectedObjects.add(obj);
					while (helper.toNextSibling("connectedNode")) {
						objectName = helper.getElementStringValue();
						obj = fParent.system().state().objectByName(objectName);
						if (obj != null) connectedObjects.add(obj);
					}
					if (assoc.associationEnds().size() != connectedObjects.size()) continue;
					MLink link = fParent.system().state().linkBetweenObjects(assoc, connectedObjects, Collections.emptyList());
					if (link != null) {
						DiamondNode node = visibleData.getNaryLinkToDiamondNodeMap().get(link);
						if (node != null) {
							helper.toParent();
							node.restorePlacementInfo(helper, version);
						}
					}
				}
			} catch (XPathEvalException | NavException e) {
				if (fLog != null) fLog.println(e.getMessage());
				else System.err.println(e.getMessage());
			}
		} catch (XPathParseException e) {
			if (fLog != null) fLog.println(e.getMessage());
			else System.err.println(e.getMessage());
		} finally {
			ap.resetXPath();
			helper.getNav().pop();
		}
	}

	/**
	 * Hide link using name of link
	 *
	 * @param linkName the association (link) name to hide
	 * @param links the list of links to examine
	 */
	public void hideLink(String linkName, List<MLink> links) {
		for (MLink link : links) {
			if (linkName.equals(link.association().toString())) {
				hideLink(link);
			}
		}

	}

	/**
	 * Show link using name of link
	 *
	 * @param linkName the association (link) name to show
	 * @param links the list of links to examine
	 */
	public void showLink(String linkName, List<MLink> links) {
		for (MLink link : links) {
			if (linkName.equals(link.association().toString())) {
				showLink(link);
			}
		}
	}

	/**
	 * Map links to kind of association. Used for show/hide-links-by-kind
	 *
	 * @return a sorted map from kind label to links
	 */
	public TreeMap<String, List<MLink>> mapLinksToKindOfAssociation() {
		Map<String, List<MLink>> assocs = new HashMap<>();
		final String derivedLinks = "Derived links";
		final String associationClass = "Link objects";
		final String nAryLinks = "N-Ary links";
		final String reflexiveLinks = "Reflexive links";
		final String binaryLinks = "Binary links";
		final String aggregation = "Aggregations";
		final String composition = "Compositions";

		for (MAssociation assoc : fParent.system().model().associations()) {

			/*
			 * aggregationKind = 0, other
			 * aggregationKind = 1, aggreation
			 * aggregationKind = 2, composition
			 */
			int kind = assoc.aggregationKind();
			switch (kind) {
				case 0:
					Set<MLink> links = fParent.system().state().linksOfAssociation(assoc).links();
					for (MLink link : links) {
						MAssociation linkAssoc = link.association();
						if (linkAssoc == null) continue;
						if (linkAssoc.isDerived() || linkAssoc.isUnion()) {
							assocs.computeIfAbsent(derivedLinks, k -> new ArrayList<>()).add(link);
						} else if (linkAssoc instanceof MAssociationClassImpl) {
							assocs.computeIfAbsent(associationClass, k -> new ArrayList<>()).add(link);
						} else if (linkAssoc.associationEnds().size() > 2) {
							assocs.computeIfAbsent(nAryLinks, k -> new ArrayList<>()).add(link);
						} else if (linkAssoc.associatedClasses().size() == 1) {
							assocs.computeIfAbsent(reflexiveLinks, k -> new ArrayList<>()).add(link);
						} else if (link.linkedObjects().size() == 2 && !link.linkedObjects().get(0).equals(link.linkedObjects().get(1))) {
							assocs.computeIfAbsent(binaryLinks, k -> new ArrayList<>()).add(link);
						} else {
							if (fLog != null) {
								fLog.println("ERROR: NO MATCH IN ASSOC-KIND");
							} else {
								System.err.println("ERROR: NO MATCH IN ASSOC-KIND");
							}
						}

					}
					break;
				case 1: // Aggregation

					// Get aggregations
					Set<MLink> aggregations = fParent.system().state().linksOfAssociation(assoc).links();

					for (MLink agg : aggregations) {
						assocs.computeIfAbsent(aggregation, k -> new ArrayList<>()).add(agg);
					}
					break;

				case 2: // Composition

					// Get Compositions
					Set<MLink> compositions = fParent.system().state().linksOfAssociation(assoc).links();

					for (MLink comp : compositions) {
						assocs.computeIfAbsent(composition, k -> new ArrayList<>()).add(comp);
					}
					break;
			}
		}
		return new TreeMap<>(assocs);

	}

	/**
	 * Hide all links of list links
	 *
	 * @param links list of links to hide
	 */
	public void hideLink(List<MLink> links) {
		for (MLink link : links) {
			hideLink(link);
		}
	}

	/**
	 * Show all links of list links
	 *
	 * @param links list of links to show
	 */
	public void showLink(List<MLink> links) {
		for (MLink link : links) {
			showLink(link);
		}
	}

	/**
	 * Check if a link is hidden
	 *
	 * @param link the link instance to check
	 * @return true if the provided link is currently hidden; otherwise false
	 */
	public boolean isHidden(MLink link) {
		return hiddenData.getBinaryLinkToEdgeMap().containsKey(link)
				|| hiddenData.getHalfLinkToEdgeMap().containsKey(link)
				|| (link instanceof MLinkObject && hiddenData.getLinkObjectToNodeEdge().containsKey((MLinkObject) link))
				|| hiddenData.getNaryLinkToDiamondNodeMap().containsKey(link);
	}

	/**
	 * Determine whether the provided list of links is hidden, visible or mixed.
	 *
	 * @param links collection of links to evaluate
	 * @return 0 if all links are hidden, 1 if all links are shown, 2 if mixed, -1 if none present
	 */
	public int isHidden(List<MLink> links) {
		boolean existHiddenLink = false;
		boolean existShownLink = false;
		for (MLink link : links) {
			if (isHidden(link)) {
				existHiddenLink = true;
			} else {
				existShownLink = true;
			}
		}
		if (existHiddenLink && existShownLink) {
			return 2;
		} else if (existShownLink) {
			return 1;
		} else if (existHiddenLink) {
			return 0;
		}
		return -1;
	}

	public static void setJavaFxCall(boolean javaFxCall) {
		NewObjectDiagram.javaFxCall = javaFxCall;
	}

	@Override
	public ObjectDiagramData getVisibleData() {
		return visibleData;
	}

	@Override
	public ObjectDiagramData getHiddenData() {
		return hiddenData;
	}

	/**
	 * Restore helper to determine hidden state for items when restoring layout.
	 * Mirrors the implementation used in ClassDiagram to remain consistent across diagrams.
	 */
	@SuppressWarnings("unused")
	protected boolean isHidden(PersistHelper helper, int version) {
		// The 'version' parameter is reserved for future format-specific logic.
		return helper.getElementBooleanValue(LayoutTags.HIDDEN);
	}

	/**
	 * Finds a link for the given association that connects the provided objects and
	 * matches the serialized linkValue. This method reverses what BinaryAssociationOrLinkEdge.storeAdditionalInfo
	 * wrote using link.toString(). It prefers exact object-list match and the string representation.
	 */
	private MLink getLinkByValue(MAssociation assoc, List<MObject> objects, String linkValue) {
		if (assoc == null || objects == null || linkValue == null) {
			return null;
		}

		Set<MLink> candidates = fParent.system().state().linksOfAssociation(assoc).links();
		for (MLink l : candidates) {
			// first ensure the linked objects match the expected ordering/collection
			List<MObject> linked = l.linkedObjects();
			if (linked.size() == objects.size()) {
				boolean sameObjects = true;
				for (int i = 0; i < linked.size(); i++) {
					if (!linked.get(i).equals(objects.get(i))) {
						sameObjects = false;
						break;
					}
				}
				if (!sameObjects) continue;

				// compare the string representation stored by the edge
				if (linkValue.equals(l.toString())) {
					return l;
				}
			}
		}
		return null;
	}

	@Override
	public Set<? extends PlaceableNode> getHiddenNodes() {
		return hiddenData.getNodes();
	}

	@Override
	protected String getDefaultLayoutFileSuffix() {
		// Use a dedicated suffix for object diagram default layouts (consistent with other diagrams)
		return "_objdia.clt";
	}


	@Override
	public void stateChanged(SortChangeEvent e) {
		for (ObjectNode n : this.visibleData.fObjectToNodeMap.values()) {
			n.stateChanged(e);
		}
	}

	/**
	 * A kleine Helfermethode zum Erstellen von Actions mit weniger Boilerplate-Code.
	 *
	 * @param text Der Text der Action.
	 * @param listener Der ActionListener, der die Action ausführt.
	 * @return Die erstellte Action.
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

			if (lastKnownLinkPositions.containsKey(link)) {
				e.initialize();
				ObjectNode linkObjNode = visibleData.getObjectToNodeMap().get(link);
				if (linkObjNode != null) {
					linkObjNode.setStrategy(lastKnownLinkPositions.get(link));
				}
				lastKnownLinkPositions.remove(link);
				fGraph.addInitializedEdge(e);
			} else {
				fGraph.addEdge(e);
			}
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
}
