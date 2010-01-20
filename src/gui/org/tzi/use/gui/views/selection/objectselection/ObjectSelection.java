package org.tzi.use.gui.views.selection.objectselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.BinaryEdge;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.event.HideAdministration;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;

/** 
 * ClassSelection is responsible for the new functions in the object diagram. 
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class ObjectSelection {

	private DirectedGraph fGraph;

	private Set fHiddenNodes;

	private HideAdministration fHideAdmin;

	private Set fHiddenEdges;

	/**
	 * Constructor for ObjectSelection.
	 */
	public ObjectSelection(DirectedGraph fGraph, Set fHiddenNodes,
			Set fHiddenEdges, HideAdministration fHideAdmin) {
		this.fGraph = fGraph;
		this.fHiddenNodes = fHiddenNodes;
		this.fHideAdmin = fHideAdmin;
		this.fHiddenEdges = fHiddenEdges;
	}

	class ActionSelectedLinkPathView extends AbstractAction {
		private Set selectedObjects;

		private Set anames;

		ActionSelectedLinkPathView(String text, Set sc, Set anames) {
			super(text);
			this.anames = anames;
			selectedObjects = sc;
		}

		public void actionPerformed(ActionEvent e) {
			SelectedLinkPathView v = MainWindow.instance()
					.showSelectedLinkPathView(selectedObjects, anames);
		}
	}

	public ActionSelectedLinkPathView getSelectedLinkPathView(String text,
			Set sc, Set anames) {
		return new ActionSelectedLinkPathView(text, sc, anames);
	}

	class ActionSelectedObjectPathView extends AbstractAction {
		private Set selectedObjects;

		ActionSelectedObjectPathView(String text, Set sc) {
			super(text);
			selectedObjects = sc;
		}

		public void actionPerformed(ActionEvent e) {
			SelectedObjectPathView v = MainWindow.instance()
					.showSelectedObjectPathView(selectedObjects);
		}
	}

	public ActionSelectedObjectPathView getSelectedObjectPathView(String text,
			Set sc) {
		return new ActionSelectedObjectPathView(text, sc);
	}

	class ActionSelectionObjectView extends AbstractAction {
		private MObject fObject;

		ActionSelectionObjectView(String text, MObject object) {
			super(text);
			fObject = object;
		}

		public void actionPerformed(ActionEvent e) {
			SelectionObjectView v = MainWindow.instance()
					.showSelectionObjectView();
			v.selectClass(fObject.name());
		}
	}

	public ActionSelectionObjectView getSelectionObjectView(String text,
			MObject mo) {
		return new ActionSelectionObjectView(text, mo);
	}

	/**
	 * Show Selection OCL View
	 */
	class ActionSelectionOCLView extends AbstractAction {
		ActionSelectionOCLView(String text) {
			super(text);
		}

		public void actionPerformed(ActionEvent e) {
			SelectionOCLView v = MainWindow.instance().showSelectionOCLView();
		}
	}

	public ActionSelectionOCLView getSelectionOCLView(String text) {
		return new ActionSelectionOCLView(text);
	}

	/**
	 * Method getSelectedObjectsofAssociation returns all relevant objects, 
	 * which are connected with the Association selected by the user. 
	 */
	public HashSet getSelectedObjectsofAssociation(AssociationName node,
			HashSet selectedObjectsOfAssociation) {
		HashSet objects = new HashSet();
		Iterator it = fGraph.edgeIterator();
		String name = node.name();

		boolean have = false;
		while (it.hasNext() && !have) {
			Object o = it.next();

			if (o instanceof BinaryEdge) {
				BinaryEdge edge = (BinaryEdge) o;

				if (edge.getAssocName() != null
						&& edge.getAssocName().equals(node)) {

					MObject mo = ((ObjectNode) (edge.source())).object();
					if (!selectedObjectsOfAssociation.contains(mo)) {
						objects.add(mo);
					}
					mo = ((ObjectNode) (edge.target())).object();
					if (!selectedObjectsOfAssociation.contains(mo)
							&& !objects.contains(mo)) {
						objects.add(mo);
					}
					return objects;
				}
			}
		}
		if (!have) {
			it = fGraph.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o instanceof DiamondNode) {
					if (((DiamondNode) o).name().equalsIgnoreCase(name)) {
						DiamondNode dnode = (DiamondNode) o;

						Set set = dnode.link().linkedObjects();
						Iterator it2 = set.iterator();
						while (it2.hasNext()) {
							MObject mo = (MObject) (it2.next());
							if (!selectedObjectsOfAssociation.contains(mo)
									&& !objects.contains(mo)) {
								objects.add(mo);
							}
						}
						return objects;
					}
				}
			}
		}
		return objects;
	}

	/**
	 * Method getSubMenuHideObject supplies a list of the sorted objects in the context menu.
	 * which are not hidden.
	 * @return JMenu
	 */
	public JMenu getSubMenuHideObject() {
		JMenu subMenuHideObject;
		MObject oo = null;

		Iterator it = null;

		it = fGraph.iterator();
		subMenuHideObject = new JMenu("Selection hide object");

		int nodesize = 0;

		SelectionComparator sort = new SelectionComparator();
		TreeSet classes = new TreeSet(sort);
		ArrayList submenus = new ArrayList();

		Iterator itt = fGraph.iterator();
		final HashSet objects = new HashSet();

		// hide all objects
		while (itt.hasNext()) {
			Object node = itt.next();
			if (node instanceof ObjectNode) {
				MObject mobj = ((ObjectNode) node).object();
				objects.add(mobj);
			}
		}

		subMenuHideObject
				.add(fHideAdmin.setValues("Hide all objects", objects));

		subMenuHideObject.addSeparator();

		// all classe find out
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ObjectNode) {
				MClass cls = null;

				oo = ((ObjectNode) node).object();
				cls = ((ObjectNode) node).cls();
				if (!classes.contains(cls)) {
					classes.add(cls);
				}
			}
		}

		// sort classes
		it = classes.iterator();
		while (it.hasNext()) {
			String name = ((MClass) (it.next())).name();
			JMenu subm = new JMenu("Class " + name);
			subm.setName("Class " + name);
			subMenuHideObject.add(subm);
			submenus.add(subm);

		}

		String info;
		TreeSet sortedNodes = new TreeSet(sort);

		it = fGraph.iterator();
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ObjectNode) {
				sortedNodes.add((ObjectNode) node);
			}
		}
		info = "Hide ";
		it = sortedNodes.iterator();

		// add every object menu
		while (it.hasNext()) {
			Object node = it.next();
			String objectname = "";

			if (node instanceof ObjectNode) {
				nodesize++;
				MClass cls = null;
				MObject mobj = null;

				mobj = ((ObjectNode) node).object();
				cls = mobj.cls();
				objectname = mobj.name();
				final HashSet objects2 = new HashSet();
				objects2.add(mobj);

				Iterator cl = submenus.iterator();
				while (cl.hasNext()) {
					JMenu jm = (JMenu) (cl.next());
					if (jm.getName().contains(cls.name())) {
						jm.add(fHideAdmin
								.setValues(info + objectname, objects2));
					}
				}
			}
		}
		// add menu "hide/show all object"
		int index = -1;
		Iterator cl = submenus.iterator();
		while (cl.hasNext()) {
			JMenu subm = (JMenu) (cl.next());
			if (subm.getItemCount() > 1) {
				it = fGraph.iterator();
				info = "Hide all " + subm.getName();
				final HashSet objects3 = new HashSet();

				while (it.hasNext()) {
					Object node = it.next();
					MClass cls = null;
					MObject mobj = null;
					if (node instanceof ObjectNode) {
						mobj = ((ObjectNode) node).object();
						cls = mobj.cls();
						if (subm.getName().contains(cls.name())) {
							objects3.add(mobj);
						}
					}
				}
				subm.insert(fHideAdmin.setValues(info, objects3), 0);
				subm.insertSeparator(1);
			}
		}
		return subMenuHideObject;
	}

	/**
	 * Method getSubMenuShowObject supplies a list of the sorted objects in the context menu.
	 * which are hidden.
	 * @return JMenu
	 */
	public JMenu getSubMenuShowObject() {
		JMenu subMenuShowObject;
		MObject oo = null;

		Iterator it = null;
		it = fHiddenNodes.iterator();
		subMenuShowObject = new JMenu("Selection show object");

		int nodesize = 0;

		SelectionComparator sort = new SelectionComparator();

		TreeSet classes = new TreeSet(sort);
		ArrayList submenus = new ArrayList();
		final JMenuItem showAllObjects = new JMenuItem("Show all objects");
		showAllObjects.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				fHideAdmin.showAllHiddenElements();

			}
		});

		subMenuShowObject.add(showAllObjects);

		subMenuShowObject.addSeparator();
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof MObject) {
				MClass cls = null;
				oo = ((MObject) node);
				cls = ((MObject) node).cls();
				if (!classes.contains(cls)) {
					classes.add(cls);
				}
			}
		}

		it = classes.iterator();
		while (it.hasNext()) {
			String name = ((MClass) (it.next())).name();
			JMenu subm = new JMenu("Class " + name);
			subm.setName("Class " + name);
			subMenuShowObject.add(subm);
			submenus.add(subm);
		}

		String info;

		TreeSet sortedNodes = new TreeSet(sort);

		sortedNodes.addAll(fHiddenNodes);
		info = "Show ";
		it = sortedNodes.iterator();
		while (it.hasNext()) {

			// add every object menu
			Object node = it.next();
			String objectname = "";

			if (node instanceof MObject) {
				nodesize++;
				MClass cls = null;
				MObject mobj = null;
				mobj = (MObject) node;
				cls = mobj.cls();
				objectname = mobj.name();
				final HashSet objects = new HashSet();
				objects.add(mobj);

				final JMenuItem showObjects = new JMenuItem("Show "
						+ objectname);
				showObjects.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						fHideAdmin.showHiddenElements(objects);
					}
				});
				Iterator cl = submenus.iterator();
				while (cl.hasNext()) {
					JMenu jm = (JMenu) (cl.next());
					if (jm.getName().contains(cls.name())) {
						jm.add(showObjects);
					}
				}
			}
		}
		// add menu "hide/show all object"
		Iterator cl = submenus.iterator();
		while (cl.hasNext()) {
			JMenu subm = (JMenu) (cl.next());
			if (subm.getItemCount() > 1) {
				it = fHiddenNodes.iterator();
				info = "Show all " + subm.getName();
				final HashSet objects = new HashSet();

				while (it.hasNext()) {
					Object node = it.next();
					MClass cls = null;
					MObject mobj = null;
					if (node instanceof MObject) {
						mobj = (MObject) node;
						cls = mobj.cls();
						if (subm.getName().contains(cls.name())) {
							objects.add(mobj);
						}
					}
				}

				final JMenuItem showObjects = new JMenuItem(info);
				showObjects.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						fHideAdmin.showHiddenElements(objects);
					}
				});
				subm.insert(showObjects, 0);
				subm.insertSeparator(1);
			}
		}
		return subMenuShowObject;
	}
}
