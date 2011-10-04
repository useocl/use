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

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;

/** 
 * ClassSelection is responsible for the new functions in the object diagram. 
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author   Lars Hamann
 */
public class ObjectSelection {

	private NewObjectDiagram diagram;

	/**
	 * Constructor for ObjectSelection.
	 */
	public ObjectSelection(NewObjectDiagram diagram) {
		this.diagram = diagram;
	}

	@SuppressWarnings("serial")
	class ActionShowSelectedLinkPathView extends AbstractAction {
		private Set<MObject> selectedObjects;
		private Set<MLink> selectedLinks;

		ActionShowSelectedLinkPathView(Set<MObject> sc, Set<MLink> links) {
			super("Selection by path length...");
			this.selectedLinks = links;
			selectedObjects = sc;
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance().showSelectedLinkPathView(
					ObjectSelection.this.diagram, selectedObjects, selectedLinks);
		}
	}

	public ActionShowSelectedLinkPathView getSelectionByPathLengthView(Set<MObject> sc, Set<MLink> links) {
		return new ActionShowSelectedLinkPathView(sc, links);
	}

	@SuppressWarnings("serial")
	class ActionSelectedObjectPathView extends AbstractAction {
		private Set<MObject> selectedObjects;

		ActionSelectedObjectPathView(String text, Set<MObject> sc) {
			super(text);
			selectedObjects = sc;
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance().showSelectedObjectPathView(ObjectSelection.this.diagram, selectedObjects);
		}
	}

	public ActionSelectedObjectPathView getSelectedObjectPathView(String text, Set<MObject> sc) {
		return new ActionSelectedObjectPathView(text, sc);
	}

	@SuppressWarnings("serial")
	class ActionSelectionObjectView extends AbstractAction {
		ActionSelectionObjectView() {
			super("Select objects...");
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance().showSelectionObjectView(
					ObjectSelection.this.diagram);
		}
	}

	public ActionSelectionObjectView getSelectionObjectView() {
		return new ActionSelectionObjectView();
	}

	/**
	 * Show Selection OCL View
	 */
	@SuppressWarnings("serial")
	class ActionSelectionOCLView extends AbstractAction {
		ActionSelectionOCLView() {
			super("Selection with OCL...");
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance().showSelectionOCLView(ObjectSelection.this.diagram);
		}
	}

	public ActionSelectionOCLView getSelectionWithOCLViewAction() {
		return new ActionSelectionOCLView();
	}

	/**
	 * Method getSelectedObjectsofAssociation returns all relevant objects, 
	 * which are connected with the Association selected by the user. 
	 */
	public Set<MObject> getSelectedObjectsofAssociation(MAssociation node,
														Set<MObject> selectedObjectsOfAssociation) {
		HashSet<MObject> objects = new HashSet<MObject>();
		Iterator<?> it = this.diagram.getGraph().edgeIterator();
		String name = node.name();

		while (it.hasNext()) {
			Object o = it.next();

			if (o instanceof BinaryAssociationOrLinkEdge) {
				BinaryAssociationOrLinkEdge edge = (BinaryAssociationOrLinkEdge) o;

				if (edge.getAssocName() != null && edge.getAssocName().equals(node)) {

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
		
		it = diagram.getGraph().iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof DiamondNode) {
				if (((DiamondNode) o).name().equalsIgnoreCase(name)) {
					DiamondNode dnode = (DiamondNode) o;

					for (MObject mo : dnode.link().linkedObjects()) {
						if (!selectedObjectsOfAssociation.contains(mo)
								&& !objects.contains(mo)) {
							objects.add(mo);
						}
					}
					return objects;
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
		Iterator<?> it = null;

		it = diagram.getGraph().iterator();
		subMenuHideObject = new JMenu("Hide object");

		SelectionComparator sort = new SelectionComparator();
		TreeSet<MClass> classes = new TreeSet<MClass>(sort);
		ArrayList<JMenu> submenus = new ArrayList<JMenu>();

		Iterator<?> itt = diagram.getGraph().iterator();
		final HashSet<MObject> objects = new HashSet<MObject>();

		// hide all objects
		while (itt.hasNext()) {
			Object node = itt.next();
			if (node instanceof ObjectNode) {
				MObject mobj = ((ObjectNode) node).object();
				objects.add(mobj);
			}
		}

		subMenuHideObject.add(diagram.getAction("Hide all objects", objects));

		subMenuHideObject.addSeparator();

		// all classe find out
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ObjectNode) {
				MClass cls = null;

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
		TreeSet<ObjectNode> sortedNodes = new TreeSet<ObjectNode>(sort);

		it = diagram.getGraph().iterator();
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
				MClass cls = null;
				MObject mobj = null;

				mobj = ((ObjectNode) node).object();
				cls = mobj.cls();
				objectname = mobj.name();
				final HashSet<MObject> objects2 = new HashSet<MObject>();
				objects2.add(mobj);

				Iterator<JMenu> cl = submenus.iterator();
				while (cl.hasNext()) {
					JMenu jm = (JMenu) (cl.next());
					if (jm.getName().contains(cls.name())) {
						jm.add(diagram.getAction(info + objectname, objects2));
					}
				}
			}
		}
		// add menu "hide/show all object"
		Iterator<JMenu> cl = submenus.iterator();
		
		while (cl.hasNext()) {
			JMenu subm = (JMenu) (cl.next());
			if (subm.getItemCount() > 1) {
				it = diagram.getGraph().iterator();
				info = "Hide all";
				final HashSet<MObject> objects3 = new HashSet<MObject>();

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
				subm.insert(diagram.getAction(info, objects3), 0);
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

		Iterator<?> it = null;
		it = diagram.getHiddenNodes().iterator();
		subMenuShowObject = new JMenu("Show object");

		SelectionComparator sort = new SelectionComparator();

		TreeSet<MClass> classes = new TreeSet<MClass>(sort);
		ArrayList<JMenu> submenus = new ArrayList<JMenu>();
		final JMenuItem showAllObjects = new JMenuItem("Show all objects");
		showAllObjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				diagram.showAll();
				diagram.invalidateContent();

			}
		});

		subMenuShowObject.add(showAllObjects);

		subMenuShowObject.addSeparator();
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof MObject) {
				MClass cls = null;
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

		TreeSet<Object> sortedNodes = new TreeSet<Object>(sort);

		sortedNodes.addAll(diagram.getHiddenNodes());
		info = "Show ";
		it = sortedNodes.iterator();
		while (it.hasNext()) {

			// add every object menu
			Object node = it.next();
			String objectname = "";

			if (node instanceof MObject) {
				MClass cls = null;
				MObject mobj = null;
				mobj = (MObject) node;
				cls = mobj.cls();
				objectname = mobj.name();
				final HashSet<MObject> objects = new HashSet<MObject>();
				objects.add(mobj);

				final JMenuItem showObjects = new JMenuItem("Show "
						+ objectname);
				showObjects.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						diagram.showObjects(objects);
					}
				});
				
				for (JMenu jm : submenus) {
					if (jm.getName().contains(cls.name())) {
						jm.add(showObjects);
					}
				}
			}
		}
		
		// add menu "hide/show all object"
		for (JMenu subm : submenus) {
			if (subm.getItemCount() > 1) {
				it = diagram.getHiddenNodes().iterator();
				info = "Show all";
				final HashSet<MObject> objects = new HashSet<MObject>();

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
						diagram.showObjects(objects);
					}
				});
				subm.insert(showObjects, 0);
				subm.insertSeparator(1);
			}
		}
		return subMenuShowObject;
	}

	/**
	 * Returns a set of all displayed objects for the given classes
	 * @param allClasses
	 * @return
	 */
	public Set<MObject> getDisplayedObjectsForClasses(Set<MClass> allClasses) {
		Set<MObject> objects = new HashSet<MObject>();
		
		for (MClass mc : allClasses) {
			Iterator<NodeBase> itobject = diagram.getGraph().iterator();
			
			while(itobject.hasNext()){
				Object node = itobject.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if(mobj.cls().equals(mc)){
						objects.add(mobj);
					}
				}
			}
		}
		
		return objects;
	}
	
	
	public Set<MObject> getCropHideObjects(Set<MClass> classes) { 
		Set<MClass> classesToHide = new HashSet<MClass>();
		Iterator<NodeBase> itg = diagram.getGraph().iterator();
		
		while (itg.hasNext()) {
			Object node = itg.next();
			if(node instanceof ObjectNode) {
				MObject mobj = ((ObjectNode) node).object();
				if( !classes.contains(mobj.cls()) ){
					classesToHide.add(mobj.cls());
				}
			}
		}
		
		for (Object node : diagram.getHiddenNodes()) {
			if(node instanceof MObject){
				MObject mobj = (MObject)(node);
								
				if(!classes.contains(mobj.cls())) {
					classesToHide.add(mobj.cls());
				}
			}
		}
		
		return getDisplayedObjectsForClasses(classesToHide);
	}
	
	public Set<MObject> getHiddenObjects(Set<MClass> classes) {
		final Set<MObject> objects = new HashSet<MObject>();
		
		for (MClass mc : classes) {
			for (NodeBase node : diagram.getHiddenNodes()) {
				if(node instanceof ObjectNode){
					ObjectNode mobj = (ObjectNode)(node);
					if(mobj.cls().equals(mc)){
						objects.add(mobj.object());
					}
				}
			}
		}
		
		return objects;
	}
}
