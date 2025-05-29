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

import static org.tzi.use.util.collections.CollectionUtil.exactlyOne;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import org.tzi.use.analysis.coverage.CoverageAnalyzer;
import org.tzi.use.analysis.coverage.CoverageData;
import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser.SelectionChangedListener;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.util.ExtFileFilter;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramOptions.ShowCoverage;
import org.tzi.use.gui.views.diagrams.elements.AssociationName;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.elements.edges.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.edges.GeneralizationEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.NAryAssociationClassOrObjectEdge;
import org.tzi.use.gui.views.diagrams.event.ActionHideClassDiagram;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeEvent;
import org.tzi.use.gui.views.diagrams.event.HighlightChangeListener;
import org.tzi.use.gui.views.selection.classselection.ClassSelection;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.StringUtil;
import org.w3c.dom.Element;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * A panel drawing a UML class diagrams.
 * 
 * @author Fabian Gutsche
 * @author Lars Hamann
 * @author Stefan Schoon
 */
@SuppressWarnings("serial")
public class ClassDiagram extends DiagramView
		implements HighlightChangeListener, SelectionChangedListener, SortChangeListener {

	private final ClassDiagramView fParent;

	protected final ClassDiagramData visibleData = new ClassDiagramData();

	private final ClassDiagramData hiddenData = new ClassDiagramData();

	private final ClassSelection fSelection;

	private final DiagramInputHandling inputHandling;

	private static Boolean javaFxCall = false;

	ClassDiagram(ClassDiagramView parent, PrintWriter log) {
		this(parent, log, new ClassDiagramOptions(Paths.get(parent.system().model().filename())));
	}

	protected ClassDiagram(ClassDiagramView parent, PrintWriter log, ClassDiagramOptions opt) {
		super(opt, log);

		fParent = parent;

		inputHandling = new DiagramInputHandling(fNodeSelection, fEdgeSelection, this);

		fSelection = new ClassSelection(this);

		fActionSaveLayout = new ActionSaveLayout("USE class diagram layout", "clt", this);

		fActionLoadLayout = new ActionLoadLayout("USE class diagram layout", "clt", this);

		addMouseListener(inputHandling);
		fParent.addKeyListener(inputHandling);

		fParent.getModelBrowser().addHighlightChangeListener(this);
		fParent.getModelBrowser().addSelectionChangedListener(this);
		ModelBrowserSorting.getInstance().addSortChangeListener(this);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// need a new layouter to adapt to new window size
				fLayouter = null;
			}
		});

		startLayoutThread();
	}

	/**
	 * Returns the options of a specific diagram.
	 */
	@Override
	public ClassDiagramOptions getOptions() {
		return (ClassDiagramOptions) fOpt;
	}

	public MSystem getSystem() {
		return fParent.system();
	}

	public Selection<PlaceableNode> getNodeSelection() {
		return this.fNodeSelection;
	}

	@Override
	public void resetLayout() {
		fParent.initDiagram(false, (ClassDiagramOptions) fOpt);
		fParent.validate();
	}

	/**
	 * Displays the selected class of the model browser in the class diagram.
	 */
	@Override
	public void stateChanged(HighlightChangeEvent e) {
		if (!fParent.isSelectedView()) {
			return;
		}

		MModelElement elem = e.getModelElement();
		List<EdgeBase> edges = new ArrayList<EdgeBase>();
		boolean allEdgesSelected = true;

		// elem is an association
		if (elem != null && elem instanceof MAssociation) {
			int size = ((MAssociation) elem).associationEnds().size();
			EdgeBase eb = null;
			if (size == 2) {
				eb = visibleData.fBinaryAssocToEdgeMap.get(elem);
				if (elem instanceof MAssociationClass) {
					eb = visibleData.fAssocClassToEdgeMap.get(elem);
				}
				edges.add(eb);
			} else {
				List<EdgeBase> halfEdges = visibleData.fNaryAssocToHalfEdgeMap.get(elem);
				if (halfEdges != null) {
					edges.addAll(halfEdges);
				}
				if (elem instanceof MAssociationClass) {
					eb = visibleData.fAssocClassToEdgeMap.get(elem);
					if (!edges.contains(eb)) {
						edges.add(eb);
					}
				}
			}

			// check all edges in the list if they are supposed to be selected
			// or deselected.
			for (EdgeBase edge : edges) {
				if (edge != null) {
					if (fEdgeSelection.isSelected(edge)) {
						fEdgeSelection.remove(edge);
						allEdgesSelected = true;
					} else {
						fEdgeSelection.add(edge);
						allEdgesSelected = false;
					}
				}
			}
		}

		// elem is a class
		if (elem != null && elem instanceof MClass) {
			PlaceableNode node = visibleData.fClassToNodeMap.get(elem);
			if (node != null) {
				if (elem instanceof MAssociationClass) {
					if (fNodeSelection.isSelected(node) && allEdgesSelected) {
						fNodeSelection.remove(node);
					} else {
						fNodeSelection.add(node);
					}
				} else {
					if (fNodeSelection.isSelected(node)) {
						fNodeSelection.remove(node);
					} else {
						fNodeSelection.add(node);
					}
				}
			}
		}

		invalidateContent(true);
	}

	/**
	 * Show all classes and assoziations
	 */
	@Override
	public void showAll() {
		while (hiddenData.fClassToNodeMap.size() > 0) {
			showClass(hiddenData.fClassToNodeMap.keySet().iterator().next());
		}

		while (hiddenData.fEnumToNodeMap.size() > 0) {
			showEnum(hiddenData.fEnumToNodeMap.keySet().iterator().next());
		}

		while (hiddenData.fBinaryAssocToEdgeMap.size() > 0) {
			showAssociation(hiddenData.fBinaryAssocToEdgeMap.keySet().iterator().next());
		}

		while (hiddenData.fAssocClassToEdgeMap.size() > 0) {
			showAssociation(hiddenData.fAssocClassToEdgeMap.keySet().iterator().next());
		}

		while (hiddenData.fNaryAssocToDiamondNodeMap.size() > 0) {
			showAssociation(hiddenData.fNaryAssocToDiamondNodeMap.keySet().iterator().next());
		}

		while (hiddenData.fNaryAssocToHalfEdgeMap.size() > 0) {
			showAssociation(hiddenData.fNaryAssocToHalfEdgeMap.keySet().iterator().next());
		}
		while (hiddenData.fGenToGeneralizationEdge.size() > 0) {
			showGeneralization(hiddenData.fGenToGeneralizationEdge.keySet().iterator().next());
		}
	}

	@Override
	public void hideAll() {
		while (visibleData.fClassToNodeMap.size() > 0) {
			hideClass(visibleData.fClassToNodeMap.keySet().iterator().next());
		}

		while (visibleData.fDataTypeToNodeMap.size() > 0) {
			hideDataType(visibleData.fDataTypeToNodeMap.keySet().iterator().next());
		}

		while (visibleData.fEnumToNodeMap.size() > 0) {
			hideEnum(visibleData.fEnumToNodeMap.keySet().iterator().next());
		}
	}

	/**
	 * Adds a class to the diagram.
	 * 
	 * @param cls
	 *            Class to be added.
	 */
	public void addClass(MClass cls) {
		int[] position = randomPosition();

		ClassNode n = new ClassNode(cls, fOpt);
		n.setPosition(position[0], position[1]);

		n.setMinWidth(minClassNodeWidth);
		n.setMinHeight(minClassNodeHeight);

		fGraph.add(n);
		visibleData.fClassToNodeMap.put(cls, n);

		fLayouter = null;
	}

	public void addSignal(MSignal signal) {
		int[] position = randomPosition();

		SignalNode n = new SignalNode(signal, fOpt);
		n.setPosition(position[0], position[1]);
		n.setMinWidth(minClassNodeWidth);
		n.setMinHeight(minClassNodeHeight);

		fGraph.add(n);
		visibleData.fSignalToNodeMap.put(signal, n);

		fLayouter = null;
	}

	/**
	 * Hides a class from the diagram.
	 */
	public void hideClass(MClass cls) {
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
			associations.add((MAssociationClass) cls);
		}

		for (MAssociation assoc : associations) {
			hideAssociation(assoc);
		}
	}

	/**
	 * Shows an already hidden class again
	 * 
	 * @param cls
	 */
	public void showClass(MClass cls) {
		showOrHideClassNode(cls, true);

		// Add all generalization edges, if nodes are present

		Set<MGeneralization> gens = cls.model().generalizationGraph().allEdges(cls);
		for (MGeneralization gen : gens) {
			if (visibleData.fClassToNodeMap.containsKey(gen.child())
					&& visibleData.fClassToNodeMap.containsKey(gen.parent())) {
				showGeneralization(gen);
			}
		}

		Set<MAssociation> associations = cls.associations();
		if (cls instanceof MAssociationClass) {
			associations.add((MAssociation) cls);
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
	 * Shows or hides a class.
	 * 
	 * @param cls
	 *            The <code>MClass</code> to show or hide
	 * @param show
	 *            If <code>true</code>, the class is shown otherwise it is
	 *            hidden.
	 */
	protected void showOrHideClassNode(MClass cls, boolean show) {
		ClassDiagramData source = (show ? hiddenData : visibleData);
		ClassDiagramData target = (show ? visibleData : hiddenData);

		ClassNode n = source.fClassToNodeMap.get(cls);
		if (n != null) {
			if (!show && this.getNodeSelection().isSelected(n))
				this.getNodeSelection().remove(n);

			if (show) {
				fGraph.add(n);
			} else
				fGraph.remove(n);

			source.fClassToNodeMap.remove(cls);
			target.fClassToNodeMap.put(cls, n);

			fLayouter = null;
		}
	}

	/**
	 * Adds a data type to the diagram.
	 *
	 * @param dtp
	 *            Data type to be added.
	 */
	public void addDataType(MDataType dtp) {
		int[] position = randomPosition();

		DataTypeNode n = new DataTypeNode(dtp, fOpt);
		n.setPosition(position[0], position[1]);
		n.setMinWidth(minClassNodeWidth);
		n.setMinHeight(minClassNodeHeight);

		fGraph.add(n);
		visibleData.fDataTypeToNodeMap.put(dtp, n);

		fLayouter = null;
	}

	/**
	 * Hides a data type from the diagram.
	 */
	public void hideDataType(MDataType dtp) {
		if (!visibleData.fDataTypeToNodeMap.containsKey(dtp))
			return;
		showOrHideDataTypeNode(dtp, false);

		// Remove all generalization edges
		Set<MGeneralization> gens = fParent.system().model().generalizationGraph().allEdges(dtp);

		for (MGeneralization gen : gens) {
			hideGeneralization(gen);
		}
	}

	/**
	 * Shows an already hidden data type again
	 * 
	 * @param dtp
	 */
	public void showDataType(MDataType dtp) {
		showOrHideDataTypeNode(dtp, true);

		// Add all generalization edges, if nodes are present

		Set<MGeneralization> gens = dtp.model().generalizationGraph().allEdges(dtp);
		for (MGeneralization gen : gens) {
			if (visibleData.fDataTypeToNodeMap.containsKey(gen.child())
					&& visibleData.fDataTypeToNodeMap.containsKey(gen.parent())) {
				showGeneralization(gen);
			}
		}
	}

	/**
	 * Shows or hides a data type.
	 * 
	 * @param dtp
	 *            The <code>MDataType</code> to show or hide
	 * @param show
	 *            If <code>true</code>, the data type is shown otherwise it is
	 *            hidden.
	 */
	protected void showOrHideDataTypeNode(MDataType dtp, boolean show) {
		ClassDiagramData source = (show ? hiddenData : visibleData);
		ClassDiagramData target = (show ? visibleData : hiddenData);

		DataTypeNode n = source.fDataTypeToNodeMap.get(dtp);
		if (n != null) {
			if (!show && this.getNodeSelection().isSelected(n))
				this.getNodeSelection().remove(n);

			if (show) {
				fGraph.add(n);
			} else
				fGraph.remove(n);

			source.fDataTypeToNodeMap.remove(dtp);
			target.fDataTypeToNodeMap.put(dtp, n);

			fLayouter = null;
		}
	}

	/**
	 * Adds an enumeration to the diagram.
	 * 
	 * @param enumeration
	 *            Enumeration to be added.
	 */
	public void addEnum(EnumType enumeration) {
		int[] position = randomPosition();

		EnumNode n = new EnumNode(enumeration, fOpt);
		n.setPosition(position[0], position[1]);
		n.setMinWidth(minClassNodeWidth);
		n.setMinHeight(minClassNodeHeight);

		fGraph.add(n);
		visibleData.fEnumToNodeMap.put(enumeration, n);

		fLayouter = null;
	}

	/**
	 * Hides an enumeration from the diagram.
	 */
	public void hideEnum(EnumType enumeration) {
		showOrHideEnum(enumeration, false);
	}

	/**
	 * Shows a hidden enumeration in the diagram again.
	 */
	public void showEnum(EnumType enumeration) {
		showOrHideEnum(enumeration, true);
	}

	public void showOrHideEnum(EnumType enumeration, boolean show) {
		ClassDiagramData source = (show ? hiddenData : visibleData);
		ClassDiagramData target = (show ? visibleData : hiddenData);

		EnumNode n = source.fEnumToNodeMap.get(enumeration);
		if (n != null) {
			if (!show && this.getNodeSelection().isSelected(n))
				this.getNodeSelection().remove(n);

			if (show)
				fGraph.add(n);
			else
				fGraph.remove(n);

			source.fEnumToNodeMap.remove(enumeration);
			target.fEnumToNodeMap.put(enumeration, n);

			fLayouter = null;
		}
	}

	/**
	 * Adds an association to the diagram.
	 */
	public void addAssociation(MAssociation assoc) {
		if (assoc.associationEnds().size() == 2) {
			addBinaryAssociation(assoc);
		} else {
			addNAryAssociation(assoc);
		}
	}

	/**
	 * Show an association. Wenn die uebergebene Assoziation nicht sichtbar ist,
	 * wird sie in die Darstellung aufgenommen. Werden fuer die Darstellung
	 * Klassen benoetigt, werden diese ebenfalls sichtbar gemacht, damit keine
	 * ungueltige Darstellung entsteht.
	 * 
	 * @param assoc
	 */
	public void showAssociation(MAssociation assoc) {
		for (MClass c : assoc.associatedClasses()) {
			if (!visibleData.fClassToNodeMap.containsKey(c)) {
				showClass(c);
			}
		}

		if (assoc instanceof MAssociationClass && !visibleData.fClassToNodeMap.containsKey(assoc)) {
			showClass((MClass) assoc);
		}

		if (assoc.associationEnds().size() == 2) {
			showBinaryAssociation(assoc);
		} else {
			showNAryAssociation(assoc);
		}

	}

	/**
	 * Hides an association in the diagram.
	 */
	public void hideAssociation(MAssociation assoc) {
		if (assoc.associationEnds().size() == 2) {
			hideBinaryAssociation(assoc);
		} else {
			hideNAryAssociation(assoc);
		}

		if (assoc instanceof MAssociationClass) {
			hideClass((MClass) assoc);
		}
	}

	protected void addBinaryAssociation(MAssociation assoc) {
		Iterator<MAssociationEnd> assocEndIter = assoc.associationEnds().iterator();
		MAssociationEnd assocEnd1 = assocEndIter.next();
		MAssociationEnd assocEnd2 = assocEndIter.next();
		MClass cls1 = assocEnd1.cls();
		MClass cls2 = assocEnd2.cls();

		// association class
		if (assoc instanceof MAssociationClass) {
			BinaryAssociationClassOrObject e = BinaryAssociationClassOrObject.create(
					visibleData.fClassToNodeMap.get(cls1), visibleData.fClassToNodeMap.get(cls2), assocEnd1, assocEnd2,
					visibleData.fClassToNodeMap.get(assoc), this, assoc);

			fGraph.addEdge(e);
			visibleData.fAssocClassToEdgeMap.put((MAssociationClass) assoc, e);
			fLayouter = null;
		} else {
			PlaceableNode source;
			PlaceableNode target;

			// for reflexive associations with exactly one qualifier
			// the qualifier end must be the source!
			if (assoc.associatedClasses().size() == 1 && assocEnd2.hasQualifiers() && !assocEnd1.hasQualifiers()) {
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
			BinaryAssociationOrLinkEdge e = createBinaryAssociationOrLinkEdge(source, target, assocEnd1, assocEnd2,
					this, assoc);

			fGraph.addEdge(e);
			visibleData.fBinaryAssocToEdgeMap.put(assoc, e);
			fLayouter = null;
		}
	}

	/**
	 * This part is a separate method for easier inheritance.
	 * 
	 * @author Andreas Kaestner
	 */
	protected BinaryAssociationOrLinkEdge createBinaryAssociationOrLinkEdge(PlaceableNode source, PlaceableNode target,
			MAssociationEnd sourceEnd, MAssociationEnd targetEnd, DiagramView diagram, MAssociation assoc) {
		return BinaryAssociationOrLinkEdge.create(source, target, sourceEnd, targetEnd, this, assoc);
	}

	protected void addNAryAssociation(MAssociation assoc) {

		int[] position = randomPosition();

		// n-ary association: create a diamond node and n edges to classes
		DiamondNode node = new DiamondNode(assoc, fOpt);
		node.setPosition(position[0], position[1]);
		fGraph.add(node);
		// connected to an associationclass
		if (assoc instanceof MAssociationClass) {
			NAryAssociationClassOrObjectEdge e = NAryAssociationClassOrObjectEdge.create(node,
					visibleData.fClassToNodeMap.get(assoc), this, assoc, false);

			fGraph.addEdge(e);
			visibleData.fAssocClassToEdgeMap.put((MAssociationClass) assoc, e);
			fLayouter = null;
		}

		// connected to a "normal" class
		visibleData.fNaryAssocToDiamondNodeMap.put(assoc, node);
		List<EdgeBase> halfEdges = new ArrayList<>();
		List<String> edgeIds = new ArrayList<>();

		for (MAssociationEnd assocEnd : assoc.associationEnds()) {
			MClass cls = assocEnd.cls();
			AssociationOrLinkPartEdge e = AssociationOrLinkPartEdge.create(node, visibleData.fClassToNodeMap.get(cls),
					assocEnd, this, assoc, null);
			fGraph.addEdge(e);
			halfEdges.add(e);
			edgeIds.add(assocEnd.nameAsRolename());
		}
		node.setHalfEdges(halfEdges, edgeIds);

		visibleData.fNaryAssocToHalfEdgeMap.put(assoc, halfEdges);
		fLayouter = null;
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

		if (assoc instanceof MAssociationClass) {
			e = source.fAssocClassToEdgeMap.get(assoc);
		} else {
			e = source.fBinaryAssocToEdgeMap.get(assoc);
		}

		if (e != null) {
			if (show)
				fGraph.addEdge(e);
			else
				fGraph.removeEdge(e);

			if (assoc instanceof MAssociationClass) {
				source.fAssocClassToEdgeMap.remove(assoc);
				target.fAssocClassToEdgeMap.put((MAssociationClass) assoc, e);
			} else {
				source.fBinaryAssocToEdgeMap.remove(assoc);
				target.fBinaryAssocToEdgeMap.put(assoc, (BinaryAssociationOrLinkEdge) e);
			}
			fLayouter = null;
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

		DiamondNode n = source.fNaryAssocToDiamondNodeMap.get(assoc);

		if (n != null) {
			// all dangling HalfLinkEdges are removed by the graph
			if (show)
				fGraph.add(n);
			else
				fGraph.remove(n);

			source.fNaryAssocToDiamondNodeMap.remove(assoc);
			target.fNaryAssocToDiamondNodeMap.put(assoc, n);

			List<EdgeBase> values = source.fNaryAssocToHalfEdgeMap.remove(assoc);
			target.fNaryAssocToHalfEdgeMap.put(assoc, values);

			for (EdgeBase e : values) {
				if (show)
					fGraph.addEdge(e);
				else
					fGraph.removeEdge(e);
			}

			fLayouter = null;

			if (assoc instanceof MAssociationClass) {
				EdgeBase edge = source.fAssocClassToEdgeMap.get(assoc);
				if (edge != null) {
					if (show)
						fGraph.addEdge(edge);
					else
						fGraph.removeEdge(edge);

					source.fAssocClassToEdgeMap.remove(assoc);
					target.fAssocClassToEdgeMap.put((MAssociationClass) assoc, edge);
				}
			}
		}
	}

	/**
	 * Adds a generalization to the diagram.
	 */
	public void addGeneralization(MGeneralization gen) {
		MClassifier parent = gen.parent();
		MClassifier child = gen.child();

		// TODO: Show generalizations of associations
		if (parent instanceof MAssociation && !(parent instanceof MAssociationClass))
			return;

		Map<? extends MClassifier, ? extends ClassifierNode> lookup = visibleData.lookupClassifierToNodeMap(parent);

		GeneralizationEdge e = GeneralizationEdge.create(lookup.get(child), lookup.get(parent), this);

		fGraph.addEdge(e);
		visibleData.fGenToGeneralizationEdge.put(gen, e);
		fLayouter = null;
	}

	/**
	 * Hides a generalization in the diagram.
	 */
	public void hideGeneralization(MGeneralization gen) {
		showOrHideGeneralization(gen, false);
	}

	/**
	 * Shows an already hidden generalization in the diagram again.
	 */
	public void showGeneralization(MGeneralization gen) {
		showOrHideGeneralization(gen, true);
	}

	public void showOrHideGeneralization(MGeneralization gen, boolean show) {
		ClassDiagramData source = (show ? hiddenData : visibleData);
		ClassDiagramData target = (show ? visibleData : hiddenData);

		GeneralizationEdge e = source.fGenToGeneralizationEdge.get(gen);

		if (e != null) {
			if (show)
				fGraph.addEdge(e);
			else
				fGraph.removeEdge(e);

			source.fGenToGeneralizationEdge.remove(gen);
			target.fGenToGeneralizationEdge.put(gen, e);
			fLayouter = null;
		}
	}

	/**
	 * Creates popup menu for class diagram.
	 */
	@Override
	protected PopupMenuInfo unionOfPopUpMenu() {
		// get the base popup menu
		PopupMenuInfo info = super.unionOfPopUpMenu();
		JPopupMenu popupMenu = info.popupMenu;

		// position for the popupMenu items
		int pos = 0;

		// Sets for the different selections

		// This set identifies all associated classes of associations, to allow
		// to crop an association
		final Set<MClass> selectedClassesOfAssociation = new HashSet<>();
		final Set<MClass> selectedClasses = new HashSet<>();
		final Set<MAssociation> selectedAssociations = new HashSet<>();
		final Set<MClassifier> selectedClassifier = new HashSet<>();
		final Set<MGeneralization> selectedGeneralization = new HashSet<>();

		// Split-up the selection of GUI elements to the different model
		// elements
		for (PlaceableNode node : fNodeSelection) {
			if (node instanceof ClassifierNode) {
				ClassifierNode cn = (ClassifierNode) node;
				selectedClassifier.add(cn.getClassifier());
			}

			if (node instanceof ClassNode) {
				ClassNode cn = (ClassNode) node;
				selectedClasses.add(cn.cls());
			} else if (node instanceof AssociationName) {
				MAssociation assoc = ((AssociationName) node).getAssociation();
				selectedClassesOfAssociation.addAll(assoc.associatedClasses());
				selectedAssociations.add(assoc);
			}
		}

		for (EdgeBase edge : fEdgeSelection) {
			if (edge instanceof AssociationOrLinkPartEdge) {
				AssociationOrLinkPartEdge pEdge = (AssociationOrLinkPartEdge) edge;
				selectedAssociations.add(pEdge.getAssociation());
			} else if (edge instanceof NAryAssociationClassOrObjectEdge) {
				NAryAssociationClassOrObjectEdge nEdge = (NAryAssociationClassOrObjectEdge) edge;
				selectedAssociations.add(nEdge.getAssociation());
			} else if (edge instanceof GeneralizationEdge) {
				GeneralizationEdge e = (GeneralizationEdge) edge;
				visibleData.fGenToGeneralizationEdge.entrySet().stream().filter(entry -> entry.getValue().equals(e))
						.forEach(entry -> selectedGeneralization.add(entry.getKey()));
			}

		}

		if (hiddenData.hasNodes() || hiddenData.hasEdges()) {
			final JMenuItem showAllClasses = new JMenuItem("Show hidden elements");
			showAllClasses.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					showAll();
					invalidateContent(true);
				}
			});

			popupMenu.insert(showAllClasses, pos++);
			popupMenu.insert(new JSeparator(), pos++);

		}

		// New menu for selected-Handling
		// Erstelle Labels fuers Menu
		if (!selectedAssociations.isEmpty() || !selectedClasses.isEmpty() || !selectedGeneralization.isEmpty()) {
			String labelHide = "";
			String labelCrop = "";
			int size = selectedAssociations.size() + selectedClasses.size() + selectedGeneralization.size();
			if (size > 1) {
				// Mehr als ein Object ausgewaehlt
				labelHide = "Hide " + size + " elements";
				labelCrop = "Crop " + size + " elements";
			} else if (selectedAssociations.size() == 1) {
				labelHide = "Hide " + exactlyOne(selectedAssociations).name();
				labelCrop = "Crop " + exactlyOne(selectedAssociations).name();
			} else if (selectedClasses.size() == 1) {
				labelHide = "Hide " + exactlyOne(selectedClasses).name();
				labelCrop = "Crop " + exactlyOne(selectedClasses).name();
			} else if (selectedGeneralization.size() == 1) {
				labelHide = "Hide " + exactlyOne(selectedGeneralization).name().substring(4);
				labelCrop = "Crop " + exactlyOne(selectedGeneralization).name().substring(4);
			}

			// Erstelle Actions fuer hide
			popupMenu.insert(new AbstractAction(labelHide) {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Hide Assocs if exisits
					if (selectedAssociations.size() > 0) {
						selectedAssociations.forEach(assoc -> hideAssociation(assoc));
					}
					if (selectedClasses.size() > 0) {
						selectedClasses.forEach(cls -> hideClass(cls));
					}
					if (selectedGeneralization.size() > 0) {
						selectedGeneralization.forEach(gen -> hideGeneralization(gen));
					}

					repaint();
				}
			}, pos++);

			// Erstelle Actions fuer crop
            Set<MClass> classToHide = new HashSet<>(selectedClasses);
			selectedAssociations.forEach(assoc -> {
				classToHide.addAll(assoc.associatedClasses());
				if (assoc instanceof MAssociationClass) {
					classToHide.add((MAssociationClass) assoc);
				}
			});
			selectedGeneralization.forEach(gen -> classToHide.add((MClass) gen.child()));
			selectedGeneralization.forEach(gen -> classToHide.add((MClass) gen.parent()));

			final String label = labelCrop;
			popupMenu.insert(new AbstractAction(labelCrop) {
				@Override
				public void actionPerformed(ActionEvent e) {
					getActionHideNodes(label, getNoneSelectedElementsByElements(classToHide)).actionPerformed(e);
				}
			}, pos++);

			boolean showMenuSubSuperClasses = false;
			JMenu menuSubSuperClasses = new JMenu("Sub/Superclasses");

			// Wenn nur Gens selektiert, dann show super-classes
			// Nach Ruecksprache sollen auch hidden wieder auftauchen.
			if (!selectedClasses.isEmpty()) {

				Set<MClass> filteredSelectedClasses = selectedClasses.stream().filter(cls -> !cls.parents().isEmpty())
						.collect(Collectors.toSet());

				if (filteredSelectedClasses.size() > 0) {

					String labelGen = filteredSelectedClasses.size() == 1
							? filteredSelectedClasses.iterator().next().name()
							: filteredSelectedClasses.size() + " elements";

					Set<MClass> parents = new HashSet<>();
					filteredSelectedClasses.forEach(cls -> {
						parents.addAll(cls.allParents());
					});
					boolean isSuperclassHidden = (parents.stream()
							.filter(cls -> this.hiddenData.containsNodeForClassifer(cls)).findFirst()).isPresent();

					boolean isSuperclassVisible = (parents.stream()
							.filter(cls -> this.visibleData.containsNodeForClassifer(cls)).findFirst()).isPresent();

					if (isSuperclassHidden) {
						menuSubSuperClasses.add(new AbstractAction("Show superclasses of " + labelGen) {
							@Override
							public void actionPerformed(ActionEvent e) {
								parents.forEach(cls -> showClass(cls));
								repaint();
							}
						});
					}

					if (isSuperclassVisible) {
						menuSubSuperClasses.add(new AbstractAction("Hide superclasses of " + labelGen) {
							@Override
							public void actionPerformed(ActionEvent e) {
								getActionHideNodes("Hide superclasses of " + labelGen, parents).actionPerformed(e);
							}
						});
					}

					menuSubSuperClasses.add(new AbstractAction("Crop superclasses of " + labelGen) {

						@Override
						public void actionPerformed(ActionEvent e) {
							parents.forEach(cls -> showClass(cls));
							getActionHideNodes("Crop superclasses of " + labelGen,
									getNoneSelectedElementsByElements(parents)).actionPerformed(e);
						}
					});

					showMenuSubSuperClasses = true;

				}
			}

			// Wenn nur Gens selektiert, dann show sub-classes
			if (!selectedClasses.isEmpty()) {
				Set<MClass> filteredSelectedClasses = selectedClasses.stream().filter(cls -> !cls.children().isEmpty())
						.collect(Collectors.toSet());

				if (filteredSelectedClasses.size() > 0) {

					String labelGen = filteredSelectedClasses.size() == 1
							? filteredSelectedClasses.iterator().next().name()
							: filteredSelectedClasses.size() + " elements";

					Set<MClass> parents = new HashSet<>();
					filteredSelectedClasses.forEach(cls -> {
						parents.addAll(cls.allChildren());
					});
					boolean isSubclassHidden = (parents.stream()
							.filter(cls -> this.hiddenData.containsNodeForClassifer(cls)).findFirst()).isPresent();

					boolean isSubclassVisible = (parents.stream()
							.filter(cls -> this.visibleData.containsNodeForClassifer(cls)).findFirst()).isPresent();

					if (isSubclassHidden) {
						menuSubSuperClasses.add(new AbstractAction("Show subclasses of " + labelGen) {
							@Override
							public void actionPerformed(ActionEvent e) {
								parents.forEach(cls -> showClass(cls));
								repaint();
							}
						});
					}

					if (isSubclassVisible) {

						menuSubSuperClasses.add(new AbstractAction("Hide subclasses of " + labelGen) {
							@Override
							public void actionPerformed(ActionEvent e) {
								getActionHideNodes("Hide subclasses of " + labelGen, parents).actionPerformed(e);
							}
						});
					}
					menuSubSuperClasses.add(new AbstractAction("Crop subclasses of " + labelGen) {
						@Override
						public void actionPerformed(ActionEvent e) {
							parents.forEach(cls -> showClass(cls));
							getActionHideNodes("Crop subclasses of " + labelGen,
									getNoneSelectedElementsByElements(parents)).actionPerformed(e);
						}
					});

					showMenuSubSuperClasses = true;
				}
			}
			if (showMenuSubSuperClasses) {
				popupMenu.insert(menuSubSuperClasses, pos++);
			}
			popupMenu.insert(new JSeparator(), pos++);

		}

		// Submenu for views which allow to configure visibility
		final JMenu menuShowHideCrop = new JMenu("Show/hide/crop classes");
		menuShowHideCrop.add(fSelection.getSelectedClassPathView("By path length...", selectedClasses));
		menuShowHideCrop.add(fSelection.getSelectionClassView("With view..."));

		if (hiddenData.hasNodes()) {
			popupMenu.insert(fSelection.getSubMenuShowClass(), pos++);
		}

		if (fGraph.size() > 0) {
			popupMenu.insert(fSelection.getSubMenuHideClass(), pos++);
		}

		popupMenu.insert(menuShowHideCrop, pos++);
		popupMenu.insert(new JSeparator(), pos++);

		// new menu for hide assoc
		if (fGraph.size() > 0 && (!visibleData.fBinaryAssocToEdgeMap.isEmpty()
				|| !visibleData.fAssocClassToEdgeMap.isEmpty() || !visibleData.fNaryAssocToDiamondNodeMap.isEmpty()
				|| !visibleData.fNaryAssocToHalfEdgeMap.isEmpty())) {
			popupMenu.insert(fSelection.getSubMenuHideAssoc(), pos++);
		}
		// Ende

		if (!hiddenData.fBinaryAssocToEdgeMap.isEmpty() || !hiddenData.fAssocClassToEdgeMap.isEmpty()
				|| !hiddenData.fNaryAssocToDiamondNodeMap.isEmpty() || !hiddenData.fNaryAssocToHalfEdgeMap.isEmpty()) {
			popupMenu.insert(fSelection.getSubMenuShowAssociation(), pos++);
		}
		popupMenu.insert(new JSeparator(), pos++);

		// New menu for handling generalizations
		if (!visibleData.fGenToGeneralizationEdge.isEmpty()) {
			HashSet<MGeneralization> gens = new HashSet<>(visibleData.fGenToGeneralizationEdge.keySet());
			popupMenu.insert(fSelection.getSubMenuHideGeneralizations(gens), pos++);
		}
		if (!hiddenData.fGenToGeneralizationEdge.isEmpty()) {
			HashSet<MGeneralization> gens = new HashSet<>(hiddenData.fGenToGeneralizationEdge.keySet());
			popupMenu.insert(fSelection.getSubMenuShowGeneralizations(gens), pos++);
		}
		if (!visibleData.fGenToGeneralizationEdge.isEmpty() || !hiddenData.fGenToGeneralizationEdge.isEmpty()) {
			popupMenu.insert(new JSeparator(), pos++);
		}
		// Ende: New menu for handling generalizations

		final JMenu showProtocolStateMachine = new JMenu("Show protocol state machine...");
		showProtocolStateMachine.setEnabled(false);

		if (selectedClassifier.size() == 1) {
			MClassifier clf = selectedClassifier.iterator().next();
			if (clf instanceof MClass) {
				MClass selectedClass = (MClass) clf;
				List<MProtocolStateMachine> sortedPSMs = new LinkedList<MProtocolStateMachine>(
						selectedClass.getOwnedProtocolStateMachines());
				Collections.sort(sortedPSMs, new MNamedElementComparator());

				for (MProtocolStateMachine psm : sortedPSMs) {
					showProtocolStateMachine.setEnabled(true);
					final JMenuItem showGivenPSM = new JMenuItem(psm.name());
					showGivenPSM.addActionListener(new ActionListener() {
						protected MStateMachine sm;

						@Override
						public void actionPerformed(ActionEvent ev) {
							MainWindow.instance().showStateMachineView(sm);
						}

						public ActionListener setStateMachine(MStateMachine sm) {
							this.sm = sm;
							return this;
						}
					}.setStateMachine(psm));
					showProtocolStateMachine.add(showGivenPSM);
				}
			}
		}

		popupMenu.insert(showProtocolStateMachine, pos++);
		popupMenu.insert(new JSeparator(), pos++);

		final JCheckBoxMenuItem cbMultiplicities = new JCheckBoxMenuItem("Show multiplicities");
		cbMultiplicities.setState(fOpt.isShowMutliplicities());
		cbMultiplicities.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				fOpt.setShowMutliplicities(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});

		final JCheckBoxMenuItem cbUnion = new JCheckBoxMenuItem("Show union constraints");
		cbUnion.setState(fOpt.isShowUnionConstraints());
		cbUnion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				fOpt.setShowUnionConstraints(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});

		final JCheckBoxMenuItem cbSubsets = new JCheckBoxMenuItem("Show subset constraints");
		cbSubsets.setState(fOpt.isShowSubsetsConstraints());
		cbSubsets.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				fOpt.setShowSubsetsConstraints(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});

		final JCheckBoxMenuItem cbRedefines = new JCheckBoxMenuItem("Show redefines constraints");
		cbRedefines.setState(fOpt.isShowRedefinesConstraints());
		cbRedefines.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				fOpt.setShowRedefinesConstraints(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});

		final JCheckBoxMenuItem cbOperations = new JCheckBoxMenuItem("Show operations");
		cbOperations.setState(fOpt.isShowOperations());
		cbOperations.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				fOpt.setShowOperations(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});

		// setting the right position for the popupMenu items
		// from this point on.
		pos += info.generalShowHideStart;
		int extraShowHideLength = info.generalShowHideLength;

		// put this node before separator
		final JCheckBoxMenuItem cbGroupMR = new JCheckBoxMenuItem("Group multiplicities / role names");
		cbGroupMR.setState(fOpt.isGroupMR());
		cbGroupMR.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				fOpt.setGroupMR(ev.getStateChange() == ItemEvent.SELECTED);
				repaint();
			}
		});
		popupMenu.insert(cbGroupMR, pos - 1);

		// start general show/hide check boxes
		pos++; // Skip 'show attributes'
		extraShowHideLength--;
		popupMenu.insert(cbOperations, ++pos);
		pos++; // Skip 'show association names'
		extraShowHideLength--;
		popupMenu.insert(cbMultiplicities, ++pos);
		pos++; // Skip 'show role names'
		extraShowHideLength--;
		popupMenu.insert(cbUnion, ++pos);
		popupMenu.insert(cbSubsets, ++pos);
		popupMenu.insert(cbRedefines, ++pos);

		pos += extraShowHideLength + 1;

		{
			final JMenu menuCoverage = new JMenu("Show coverage");
			// Add different coverage options
			ButtonGroup bgCoverage = new ButtonGroup();
			for (final ShowCoverage coverageOption : ShowCoverage.values()) {
				JRadioButtonMenuItem optionItem = new JRadioButtonMenuItem();
				menuCoverage.add(optionItem);
				bgCoverage.add(optionItem);
				optionItem.setText(coverageOption.toString());
				optionItem.setSelected(getOptions().getShowCoverage() == coverageOption);
				optionItem.addItemListener(new ItemListener() {

					ShowCoverage selectedOption = coverageOption;

					@Override
					public void itemStateChanged(ItemEvent e) {
						getOptions().setShowCoverage(selectedOption);
						setCoverageColor();
					}
				});
			}

			popupMenu.insert(menuCoverage, pos++);
		}

		final JMenuItem cbExport = new JMenuItem(new AbstractAction("Export visible elements as new model...") {
			private Path lastFile = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				Window owner = null;
				// setting owner for javafxCall, so that new Window is focused in front of the Application and not behind it
				if (MainWindow.getJavaFxCall()){
					// Schritt 1: Owner-Fenster holen aus dem Swing-Content
					owner = SwingUtilities.getWindowAncestor(ClassDiagram.this);
				}

				int option = JOptionPane.YES_OPTION;

				JFileChooser fChooser = new JFileChooser(Options.getLastDirectory().toFile());
				ExtFileFilter filter = new ExtFileFilter("use", "USE model");
				fChooser.setFileFilter(filter);
				fChooser.setDialogTitle("Save visible elements as model...");

				if (lastFile != null && Files.exists(lastFile)
						&& lastFile.getParent().equals(Options.getLastDirectory())) {
					fChooser.setSelectedFile(lastFile.toFile());
				}

				do {
					int returnVal = fChooser.showSaveDialog(owner);

					if (returnVal != JFileChooser.APPROVE_OPTION)
						return;

					Options.setLastDirectory(fChooser.getCurrentDirectory().toPath());
					String filename = fChooser.getSelectedFile().getName();

					// if file does not have the appendix .use
					if (!filename.endsWith(".use")) {
						filename += ".use";
					}

					lastFile = Options.getLastDirectory().resolve(filename);

					if (Files.isWritable(lastFile)) {
						option = JOptionPane.showConfirmDialog(owner, "Overwrite existing file " + lastFile + "?",
								"Please confirm", JOptionPane.YES_NO_CANCEL_OPTION);
						if (option == JOptionPane.CANCEL_OPTION) {
							return;
						}

					}
					// display the saving dialog, as long as the file
					// will be overwritten or cancel is pressed.
				} while (option != JOptionPane.YES_OPTION);

				ModelExporter exporter = new ModelExporter();
				// Binary associations
				Set<MAssociation> sourceAssociations = new HashSet<MAssociation>(
						visibleData.fBinaryAssocToEdgeMap.keySet());
				// n-ary associations
				sourceAssociations.addAll(visibleData.fNaryAssocToDiamondNodeMap.keySet());

				try {
					exporter.export(lastFile, getSystem(), visibleData.fClassToNodeMap.keySet(),
							visibleData.fDataTypeToNodeMap.keySet(), visibleData.fEnumToNodeMap.keySet(), sourceAssociations);
				} catch (IOException e1) {
					if (MainWindow.getJavaFxCall()){
						// Schritt 1: Owner-Fenster holen aus dem Swing-Content
						owner = SwingUtilities.getWindowAncestor(ClassDiagram.this);
						JOptionPane.showMessageDialog(owner, e1.getMessage(), "Error saving the USE model",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(ClassDiagram.this, e1.getMessage(), "Error saving the USE model",
								JOptionPane.ERROR_MESSAGE);
					}
					return;
				}

				JOptionPane.showMessageDialog(owner, "Export succesfull", "Export successfull",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		popupMenu.insert(new JSeparator(), pos++);
		popupMenu.insert(cbExport, pos++);

		return info;
	}

	/**
	 * Finds all elements (class or enum node) which are not selected.
	 * 
	 * @param selectedElements
	 *            Nodes which are selected at this point in the diagram.
	 * @return A Set of the none selected objects in the diagram.
	 */
	private Set<MClassifier> getNoneSelectedElementsByElements(Set<? extends MClassifier> selectedElements) {
		Set<MClassifier> noneSelectedElements = new HashSet<MClassifier>();

		Iterator<PlaceableNode> it = fGraph.iterator();
		MClassifier namedElement;

		while (it.hasNext()) {
			PlaceableNode o = it.next();

			if (o instanceof ClassNode) {
				namedElement = ((ClassNode) o).cls();
			} else if (o instanceof EnumNode) {
				namedElement = ((EnumNode) o).getEnum();
			} else {
				continue;
			}

			if (!selectedElements.contains(namedElement)) {
				noneSelectedElements.add(namedElement);
			}
		}

		selectedElements.forEach(elem -> {
			if (elem instanceof MAssociationClassImpl) {
				noneSelectedElements.removeAll(((MAssociationClassImpl) elem).associatedClasses());
			}
		}

		);

		return noneSelectedElements;
	}

	/**
	 * Hides the given elements in this diagram.
	 * 
	 * @param nodesToHide
	 *            A set of {@link MClassifier} ({@link MClass}
	 *            {@link MDataType} or {@link EnumType}) to hide
	 */
	public void hideElementsInDiagram(Set<MClassifier> nodesToHide, Set<MAssociation> edgesToHide) {
		for (MClassifier elem : nodesToHide) {
			if (elem instanceof MClass) {
				hideClass((MClass) elem);
			} else if (elem instanceof MDataType) {
				hideDataType((MDataType) elem);
			} else if (elem instanceof EnumType) {
				hideEnum((EnumType) elem);
			}
		}

		for (MAssociation assoc : edgesToHide) {
			hideAssociation(assoc);
		}
	}

	/**
	 * Show the given elements in this diagram.
	 * 
	 * @param nodesToShow
	 *            A set of {@link MClassifier} ({@link MClass}
	 *            {@link MDataType} or {@link EnumType}) to hide
	 */
	public void showElementsInDiagram(Set<?> nodesToShow) {
		for (Object elem : nodesToShow) {
			if (elem instanceof MClass) {
				showClass((MClass) elem);
			} else if (elem instanceof MDataType) {
				showDataType((MDataType) elem);
			} else if (elem instanceof EnumType) {
				showEnum((EnumType) elem);
			}
		}
	}

	@Override
	protected void onClosing() {
		if (!javaFxCall) {
			super.onClosing();
		}
		fParent.getModelBrowser().removeHighlightChangeListener(this);
		fParent.getModelBrowser().removeSelectionChangedListener(this);
		ModelBrowserSorting.getInstance().removeSortChangeListener(this);
	} //TODO

	@Override
	public void storePlacementInfos(PersistHelper helper, Element parent) {
		storePlacementInfos(helper, parent, true);
		storePlacementInfos(helper, parent, false);
	}

	protected void storePlacementInfos(PersistHelper helper, Element parent, boolean visible) {
		ClassDiagramData data = (visible ? visibleData : hiddenData);

		// store node positions in property object
		for (ClassNode n : data.fClassToNodeMap.values()) {
			n.storePlacementInfo(helper, parent, !visible);
		}

		for (DataTypeNode n : data.fDataTypeToNodeMap.values()) {
			n.storePlacementInfo(helper, parent, !visible);
		}

		for (EnumNode n : data.fEnumToNodeMap.values()) {
			n.storePlacementInfo(helper, parent, !visible);
		}

		for (DiamondNode n : data.fNaryAssocToDiamondNodeMap.values()) {
			n.storePlacementInfo(helper, parent, !visible);
		}

		for (BinaryAssociationOrLinkEdge e : data.fBinaryAssocToEdgeMap.values()) {
			e.storePlacementInfo(helper, parent, !visible);
		}

		for (EdgeBase e : data.fAssocClassToEdgeMap.values()) {
			e.storePlacementInfo(helper, parent, !visible);
		}

		for (GeneralizationEdge e : data.fGenToGeneralizationEdge.values()) {
			e.storePlacementInfo(helper, parent, !visible);
		}
	}

	private class RestoreHandler {
		protected AutoPilot ap;
		protected PersistHelper helper;
		protected int version;

		public RestoreHandler(AutoPilot ap, PersistHelper helper, int version) {
			this.ap = ap;
			this.helper = helper;
			this.version = version;
		}

		public final void handle(String xpathExpr, RestoreItemHandler handler) {
			helper.getNav().push();
			try {
				// Restore edges
				ap.selectXPath(xpathExpr);

				try {
					while (ap.evalXPath() != -1) {
						handler.handleItem(helper, version);
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
		}
	}

	protected interface RestoreItemHandler {
		void handleItem(PersistHelper helper, int version);
	}

	@Override
	public void restorePlacementInfos(PersistHelper helper, int version) {
		if (version < 12)
			return;

		final Set<MClassifier> hiddenClassifier = new HashSet<MClassifier>();
		final Set<MAssociation> hiddenAssociations = new HashSet<MAssociation>();

		// First restore edges to get possible new nodes, then nodes
		AutoPilot ap = new AutoPilot(helper.getNav());
		RestoreHandler h = new RestoreHandler(ap, helper, version);

		// Simply binary associations
		h.handle("./edge[@type='BinaryEdge']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String name = helper.getElementStringValue("name");
				MAssociation assoc = fParent.system().model().getAssociation(name);
				// Could be deleted
				if (assoc != null) {
					BinaryAssociationOrLinkEdge edge = visibleData.fBinaryAssocToEdgeMap.get(assoc);
					// Could have been changed to a n-ary
					if (edge != null) {
						try {
							edge.restorePlacementInfo(helper, version);
							if (isHidden(helper, version))
								hiddenAssociations.add(assoc);
						} catch (Exception e) {
							fLog.append("Error restoring binary edge ")
									.append(StringUtil.inQuotes(assoc.name()))
									.append(":").append(e.getMessage())
									.append("\n");
						}
					}
				}
			}
		});

		// Edges of association classes
		h.handle("./edge[@type='NodeEdge']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String name = helper.getElementStringValue("name");
				MAssociation assoc = fParent.system().model().getAssociation(name);
				// Could be deleted
				if (assoc != null) {
					EdgeBase edge = visibleData.fAssocClassToEdgeMap.get(assoc);
					edge.restorePlacementInfo(helper, version);
					if (isHidden(helper, version))
						hiddenAssociations.add(assoc);
				}
			}
		});

		// Inheritance
		h.handle("./edge[@type='" + (version == 1 ? "Inheritance" : "Generalization") + "']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String source = helper.getElementStringValue(LayoutTags.SOURCE);
				String target = helper.getElementStringValue(LayoutTags.TARGET);

				MClass child = fParent.system().model().getClass(source);
				MClass parent = fParent.system().model().getClass(target);

				if (child != null && parent != null) {
					Set<MGeneralization> genSet = fParent.system().model().generalizationGraph().edgesBetween(child,
							parent);
					if (!genSet.isEmpty()) {
						MGeneralization gen = genSet.iterator().next();
						GeneralizationEdge edge = visibleData.fGenToGeneralizationEdge.get(gen);
						edge.restorePlacementInfo(helper, version);
					}
				}
			}
		});

		// Simply the class nodes
		h.handle("./node[@type='Class']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String name = helper.getElementStringValue("name");
				MClass cls = fParent.system().model().getClass(name);
				// Could be deleted
				if (cls != null) {
					ClassNode node = visibleData.fClassToNodeMap.get(cls);
					try {
						node.restorePlacementInfo(helper, version);
						if (isHidden(helper, version))
							hiddenClassifier.add(cls);
					} catch (Exception e) {
						fLog.append("Error restoring class node ")
								.append(StringUtil.inQuotes(cls.name())).append(":")
								.append(e.getMessage()).append("\n");
					}
				}
			}
		});

		// Simply the data type nodes
		h.handle("./node[@type='DataType']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String name = helper.getElementStringValue("name");
				MDataType dtp = fParent.system().model().getDataType(name);
				// Could be deleted
				if (dtp != null) {
					DataTypeNode node = visibleData.fDataTypeToNodeMap.get(dtp);
					try {
						node.restorePlacementInfo(helper, version);
						if (isHidden(helper, version))
							hiddenClassifier.add(dtp);
					} catch (Exception e) {
						fLog.append("Error restoring data type node ")
								.append(StringUtil.inQuotes(dtp.name())).append(":")
								.append(e.getMessage()).append("\n");
					}
				}
			}
		});

		// Simply the enumeration nodes
		h.handle("./node[@type='Enumeration']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String name = helper.getElementStringValue("name");
				EnumType enumType = fParent.system().model().enumType(name);
				// Could be deleted
				if (enumType != null) {
					EnumNode node = visibleData.fEnumToNodeMap.get(enumType);
					try {
						node.restorePlacementInfo(helper, version);
						if (isHidden(helper, version))
							hiddenClassifier.add(enumType);
					} catch (Exception e) {
						fLog.append("Error restoring enum node ")
								.append(StringUtil.inQuotes(enumType.name())).append(":")
								.append(e.getMessage()).append("\n");
					}
				}
			}
		});

		// Now diamonds of n-ary associations
		h.handle("./node[@type='DiamondNode']", new RestoreItemHandler() {
			@Override
			public void handleItem(PersistHelper helper, int version) {
				String name = helper.getElementStringValue("name");
				MAssociation assoc = fParent.system().model().getAssociation(name);
				// Could be deleted
				if (assoc != null) {
					DiamondNode node = visibleData.fNaryAssocToDiamondNodeMap.get(assoc);
					if (node != null) {
						try {
							node.restorePlacementInfo(helper, version);
							if (isHidden(helper, version))
								hiddenAssociations.add(assoc);
						} catch (Exception e) {
							fLog.append("Error restoring diamond node ")
									.append(StringUtil.inQuotes(node.name())).append(":")
									.append(e.getMessage()).append("\n");
						}
					}
				}
			}
		});

		// Hide elements
		hideElementsInDiagram(hiddenClassifier, hiddenAssociations);
	}

	protected boolean isHidden(PersistHelper helper, int version) {
		return helper.getElementBooleanValue(LayoutTags.HIDDEN);
	}

	public ActionHideClassDiagram getActionHideNodes(String text, Set<? extends MClassifier> selectedNodes) {
		return new ActionHideClassDiagram(text, selectedNodes, fNodeSelection, fGraph, this);
	}

	private Map<MModelElement, CoverageData> data = null;

	private void setCoverageColor() {

		if (getOptions().getShowCoverage() != ShowCoverage.DONT_SHOW) {
			MModel model = this.fParent.system().model();

			data = CoverageAnalyzer.calculateTotalCoverage(model,
					(getOptions().getShowCoverage() == ShowCoverage.SHOW_EXPAND_OPERATIONS));

			MModelElement selectedElement = this.fParent.getModelBrowser().getSelectedModelElement();

			CoverageData theData;
			if (selectedElement != null && data.containsKey(selectedElement)) {
				theData = data.get(selectedElement);
			} else {
				theData = data.get(model);
			}

			Map<MModelElement, Integer> propCover = theData.getPropertyCoverage();

			int minCover = 0; // data.calcLowestClassCoverage();
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

				for (MOperation op : cls.operations()) {
					if (propCover.containsKey(op)) {
						value = theData.getOperationCoverage().get(op);
					} else {
						value = 0;
					}
					n.setOperationColor(op, scaleColor(value, minCover, maxAttCover));
				}
			}

			for (MDataType dtp : model.dataTypes()) {
				if (theData.getCompleteClassCoverage().containsKey(dtp)) {
					value = theData.getCompleteClassCoverage().get(dtp);
				} else {
					value = 0;
				}

				DataTypeNode n = visibleData.fDataTypeToNodeMap.get(dtp);
				if (n == null)
					n = hiddenData.fDataTypeToNodeMap.get(dtp);

				n.setColor(scaleColor(value, minCover, maxCover));

				for (MAttribute att : dtp.attributes()) {
					if (propCover.containsKey(att)) {
						value = theData.getAttributeCoverage().get(att);
					} else {
						value = 0;
					}
					n.setAttributeColor(att, scaleColor(value, minCover, maxAttCover));
				}

				for (MOperation op : dtp.operations()) {
					if (propCover.containsKey(op)) {
						value = theData.getOperationCoverage().get(op);
					} else {
						value = 0;
					}
					n.setOperationColor(op, scaleColor(value, minCover, maxAttCover));
				}
			}

			for (Rolename rolename : visibleData.getAllRolenames()) {
				if (propCover.containsKey(rolename.getEnd())) {
					value = propCover.get(rolename.getEnd());
				} else {
					value = 0;
				}

				rolename.setColor(scaleColor(value, minCover, maxAttCover));
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
			n.resetOperationColor();
		}
		for (DataTypeNode n : visibleData.fDataTypeToNodeMap.values()) {
			n.setColor(null);
			n.resetAttributeColor();
			n.resetOperationColor();
		}
		for (ClassNode n : hiddenData.fClassToNodeMap.values()) {
			n.setColor(null);
			n.resetAttributeColor();
			n.resetOperationColor();
		}

		for (EdgeProperty rolename : visibleData.getAllRolenames()) {
			rolename.setColor(null);
		}
	}

	private Color scaleColor(int theVal, int low, int high) {
		return produceColor(theVal - low, high - low);
	}

	private Color produceColor(int step, int steps) {
		float value = (step == 0 ? 0.0f : 0.1f + step * (0.9f / steps)); // tend
																			// to
																			// darkness

		int rgb = Color.HSBtoRGB(0.59f, value, 1f); // create a darker color
		// Hue is Blue, not noticeable
		// because Saturation is 0
		Color color = new Color(rgb);
		return color;
	}

	@Override
	public void selectionChanged(MModelElement element) {
		setCoverageColor();
	}

	@Override
	public Set<PlaceableNode> getHiddenNodes() {
		return hiddenData.getNodes();
	}

	/**
	 * @return
	 */
	public Set<EdgeBase> getHiddenEdges() {
		return hiddenData.getAllEdges();
	}

	@Override
	public DiagramData getVisibleData() {
		return visibleData;
	}

	@Override
	public DiagramData getHiddenData() {
		return hiddenData;
	}

	/**
	 * Returns <code>true</code>, if the given classifier <code>cs</code> is
	 * currently visible in the diagram.
	 * 
	 * @param cs
	 * @return
	 */
	public boolean isVisible(MClassifier cs) {
		return visibleData.fClassToNodeMap.containsKey(cs)
				|| visibleData.fDataTypeToNodeMap.containsKey(cs)
				|| visibleData.fEnumToNodeMap.containsKey(cs);
	}

	@Override
	protected String getDefaultLayoutFileSuffix() {
		return "_default.clt";
	}

	/**
	 * @param opt
	 */
	public void setDiagramOptions(ClassDiagramOptions opt) {
		fOpt = opt;
	}

	@Override
	public void stateChanged(SortChangeEvent e) {
		for (ClassNode n : this.visibleData.fClassToNodeMap.values()) {
			n.stateChanged(e);
		}
		for (ClassNode n : this.hiddenData.fClassToNodeMap.values()) {
			n.stateChanged(e);
		}
	}

	/**
	 * Check if one association is hidden
	 * 
	 * @param association
	 * @return true, if association in hiddenData, else return false
	 */
	public boolean isHidden(MAssociation association) {
		if (hiddenData.fAssocClassToEdgeMap.containsKey(association)
				|| hiddenData.fBinaryAssocToEdgeMap.containsKey(association)
				|| hiddenData.fNaryAssocToDiamondNodeMap.containsKey(association)
				|| hiddenData.fNaryAssocToHalfEdgeMap.containsKey(association)) {
			return true;
		} else {
			return false;
		}
	}

	private int[] randomPosition() {
		// Find a random new position. getWidth and getHeight return 0
		// if we are called on a new diagram.
		int fNextNodeX = (int) (Math.random() * Math.max(100, fParent.getWidth() - 50));
		int fNextNodeY = (int) (Math.random() * Math.max(100, fParent.getHeight() - 50));

		return new int[] {fNextNodeX, fNextNodeY};
	}
	public static void setJavaFxCall(Boolean javaFxCall) {
		ClassDiagram.javaFxCall = javaFxCall;
	}

}
