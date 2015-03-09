package org.tzi.use.gui.views.selection.objectselection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.collections.CollectionUtil;

import com.google.common.collect.Sets;

/**
 * ClassSelection is responsible for the new functions in the object diagram.
 * 
 * @author Jun Zhang
 * @author Jie Xu
 * @author Lars Hamann
 */
public class ObjectSelection {

	private final DiagramViewWithObjectNode diagram;

	private final MSystem system;

	/**
	 * Constructor for ObjectSelection.
	 */
	public ObjectSelection(DiagramViewWithObjectNode diagram, MSystem system) {
		this.diagram = diagram;
		this.system = system;
	}

	@SuppressWarnings("serial")
	class ActionSelectedObjectPathView extends AbstractAction {
		private Set<MObject> selectedObjects;

		ActionSelectedObjectPathView(String text, Set<MObject> sc) {
			super(text);
			selectedObjects = sc;
		}

		public void actionPerformed(ActionEvent e) {
			SelectedObjectPathView opv = new SelectedObjectPathView(MainWindow.instance(), system, diagram, selectedObjects);
			ViewFrame f = new ViewFrame("Selection by path length", opv, "ObjectProperties.gif");
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);
			MainWindow.instance().addNewViewFrame(f);
			f.setSize(450, 200);
		}
	}

	public ActionSelectedObjectPathView getSelectedObjectPathView(String text, Set<MObject> sc) {
		return new ActionSelectedObjectPathView(text, sc);
	}

	@SuppressWarnings("serial")
	class ActionSelectionObjectView extends AbstractAction {
		ActionSelectionObjectView() {
			super("With view...");
		}

		public void actionPerformed(ActionEvent e) {
			SelectionObjectView opv = new SelectionObjectView(MainWindow.instance(), system, diagram);
			ViewFrame f = new ViewFrame("Select objects", opv, "ObjectProperties.gif");
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);
			MainWindow.instance().addNewViewFrame(f);

			f.setSize(530, 230);
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
			super("With OCL...");
		}

		public void actionPerformed(ActionEvent e) {
			SelectionOCLView opv = new SelectionOCLView(MainWindow.instance(), system, diagram);
			ViewFrame f = new ViewFrame("Selection by OCL expression", opv, "ObjectProperties.gif");
			JComponent c = (JComponent) f.getContentPane();

			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);
			MainWindow.instance().addNewViewFrame(f);
			f.setSize(370, 250);
		}
	}

	public ActionSelectionOCLView getSelectionWithOCLViewAction() {
		return new ActionSelectionOCLView();
	}

    /**
     * Method getSelectedObjectsofAssociation returns all relevant objects,
     * which are connected with the Association selected by the user.
     */
    public Set<MObject> getSelectedObjectsofAssociation(MAssociation node, Set<MObject> selectedObjectsOfAssociation) {
		HashSet<MObject> objects = new HashSet<MObject>();
		Iterator<EdgeBase> it = this.diagram.getGraph().edgeIterator();
		String name = node.name();
	
		while (it.hasNext()) {
		    EdgeBase o = it.next();
	
		    if (o instanceof BinaryAssociationOrLinkEdge) {
				BinaryAssociationOrLinkEdge edge = (BinaryAssociationOrLinkEdge) o;
		
				if (edge.getAssociation() != null && edge.getAssociation().equals(node)) {
				    MObject mo = ((ObjectNodeActivity) (edge.source())).object();
				    if (!selectedObjectsOfAssociation.contains(mo)) {
					objects.add(mo);
				    }
				    mo = ((ObjectNodeActivity) (edge.target())).object();
				    if (!selectedObjectsOfAssociation.contains(mo) && !objects.contains(mo)) {
					objects.add(mo);
				    }
				    return objects;
				}
		    }
		}
	
		Iterator<PlaceableNode> nodeIter = diagram.getGraph().iterator();
		
		while (it.hasNext()) {
		    PlaceableNode o = nodeIter.next();
		    if (o instanceof DiamondNode) {
				if (((DiamondNode) o).name().equalsIgnoreCase(name)) {
				    DiamondNode dnode = (DiamondNode) o;
		
				    for (MObject mo : dnode.link().linkedObjects()) {
						if (!selectedObjectsOfAssociation.contains(mo) && !objects.contains(mo)) {
						    objects.add(mo);
						}
				    }
				    return objects;
				}
		    }
		}
		return objects;
    }

    private class ObjectNodeActivityComparator implements Comparator<ObjectNodeActivity> {
		@Override
		public int compare(ObjectNodeActivity o1, ObjectNodeActivity o2) {
			return o1.object().name().compareTo(o2.object().name());
		}
    }
    
	/**
	 * Method getSubMenuHideObject supplies a list of the sorted objects in the
	 * context menu. which are not hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuHideObject() {
		Set<PlaceableNode> visibleNodes = Sets.newHashSet(diagram.getGraph().getVisibleNodesIterator());
		Set<ObjectNodeActivity> visibleObjectNodes = CollectionUtil.filterByType(visibleNodes, ObjectNodeActivity.class);
		
		JMenu subMenuHideObject = new JMenu("Hide object");
		final JMenuItem hideAllObjects = new JMenuItem("Hide all objects");
		hideAllObjects.setEnabled(diagram.getVisibleData().hasNodes());
		hideAllObjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				diagram.hideAll();
				diagram.repaint();
			}
		});
		subMenuHideObject.add(hideAllObjects);
		subMenuHideObject.addSeparator();

		TreeSet<ObjectNodeActivity> sortedObjectNodes = new TreeSet<ObjectNodeActivity>(new ObjectNodeActivityComparator());
		sortedObjectNodes.addAll(visibleObjectNodes);

		Map<MClass, JMenu> classMenus = new HashMap<MClass, JMenu>();
		for (ObjectNodeActivity objNode : sortedObjectNodes) {
			final MClass cls = objNode.cls();
			final MObject obj = objNode.object();

			if (!classMenus.containsKey(cls)) {
				JMenu subm = new JMenu("Class " + cls.name());
				classMenus.put(cls, subm);

				// Add show all
				JMenuItem showAll = new JMenuItem("Hide all");
				showAll.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						diagram.hideObjects(getVisibleObjects(cls));
						diagram.repaint();
					}
				});
				subm.add(showAll);
				subm.addSeparator();
			}

			JMenu parent = classMenus.get(cls);

			final JMenuItem showObject = new JMenuItem("Hide " + obj.name());
			showObject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					diagram.hideObject(obj);
					diagram.repaint();
				}
			});
			parent.add(showObject);
		}

		List<MClass> classes = new ArrayList<MClass>(classMenus.keySet());
		Collections.sort(classes, new MNamedElementComparator());

		for (MClass cls : classes) {
			subMenuHideObject.add(classMenus.get(cls));
		}

		return subMenuHideObject;
	}

	/**
	 * Method getSubMenuShowObject supplies a list of the sorted objects in the
	 * context menu. which are hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuShowObject() {
		JMenu subMenuShowObject = new JMenu("Show object");
		Set<? extends PlaceableNode> hiddenNodes = diagram.getHiddenNodes();
		Set<ObjectNodeActivity> hiddenObjectNodes = CollectionUtil.filterByType(hiddenNodes, ObjectNodeActivity.class);
		
		final JMenuItem showAllObjects = new JMenuItem("Show all hidden objects");
		showAllObjects.setEnabled(!hiddenObjectNodes.isEmpty());
		showAllObjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				diagram.showAll();
				diagram.repaint();
			}
		});

		subMenuShowObject.add(showAllObjects);
		subMenuShowObject.addSeparator();

		TreeSet<ObjectNodeActivity> sortedNodes = new TreeSet<ObjectNodeActivity>(new ObjectNodeActivityComparator());
		
		sortedNodes.addAll(hiddenObjectNodes);

		Map<MClass, JMenu> classMenus = new HashMap<MClass, JMenu>();
		for (ObjectNodeActivity node : sortedNodes) {
			final ObjectNodeActivity objNode = node;
			final MClass cls = objNode.cls();
			final MObject obj = objNode.object();

			if (!classMenus.containsKey(cls)) {
				JMenu subm = new JMenu("Class " + cls.name());
				classMenus.put(cls, subm);

				// Add show all
				JMenuItem showAll = new JMenuItem("Show all");
				showAll.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						diagram.showObjects(getHiddenObjects(cls));
						diagram.repaint();
					}
				});
				subm.add(showAll);
				subm.addSeparator();
			}

			JMenu parent = classMenus.get(cls);

			final JMenuItem showObject = new JMenuItem("Show " + obj.name());
			showObject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					diagram.showObject(obj);
					diagram.repaint();
				}
			});
			parent.add(showObject);
		}

		List<MClass> classes = new ArrayList<MClass>(classMenus.keySet());
		Collections.sort(classes, new MNamedElementComparator());

		for (MClass cls : classes) {
			subMenuShowObject.add(classMenus.get(cls));
		}

		return subMenuShowObject;
	}

	/**
	 * Returns a set of all displayed objects for the given classes
	 * 
	 * @param allClasses
	 * @return
	 */
	public Set<MObject> getDisplayedObjectsForClasses(Set<MClass> allClasses) {
		Set<MObject> objects = new HashSet<MObject>();
		Iterator<PlaceableNode> itobject = diagram.getVisibleData().getNodes().iterator();

		while (itobject.hasNext()) {
			Object node = itobject.next();
			if (node instanceof ObjectNodeActivity) {
				MObject mobj = ((ObjectNodeActivity) node).object();
				if (allClasses.contains(mobj.cls())) {
					objects.add(mobj);
				}
			}
		}

		return objects;
	}

	/**
	 * Returns a set of all displayed objects for the given class
	 * 
	 * @param cls
	 * @return
	 */
	public Set<MObject> getVisibleObjects(MClass cls) {
		Set<MObject> objects = new HashSet<MObject>();

		for (PlaceableNode node : diagram.getVisibleData().getNodes()) {
			if (node instanceof ObjectNodeActivity) {
				MObject mobj = ((ObjectNodeActivity) node).object();
				if (cls.equals(mobj.cls())) {
					objects.add(mobj);
				}
			}
		}

		return objects;
	}

	public Set<MObject> getCropHideObjects(Set<MClass> classes) {
		Set<MClass> classesToHide = new HashSet<MClass>();
		Iterator<PlaceableNode> itg = diagram.getGraph().iterator();

		while (itg.hasNext()) {
			Object node = itg.next();
			if (node instanceof ObjectNodeActivity) {
				MObject mobj = ((ObjectNodeActivity) node).object();
				if (!classes.contains(mobj.cls())) {
					classesToHide.add(mobj.cls());
				}
			}
		}

		for (Object node : diagram.getHiddenNodes()) {
			if (node instanceof MObject) {
				MObject mobj = (MObject) (node);

				if (!classes.contains(mobj.cls())) {
					classesToHide.add(mobj.cls());
				}
			}
		}

		return getDisplayedObjectsForClasses(classesToHide);
	}

	public Set<MObject> getHiddenObjects(Set<MClass> classes) {
		final Set<MObject> objects = new HashSet<MObject>();

		for (PlaceableNode node : diagram.getHiddenNodes()) {
			if (node instanceof ObjectNodeActivity) {
				ObjectNodeActivity mobj = (ObjectNodeActivity) (node);
				if (classes.contains(mobj.cls())) {
					objects.add(mobj.object());
				}
			}
		}

		return objects;
	}

	public Set<MObject> getHiddenObjects(MClass cls) {
		final Set<MObject> objects = new HashSet<MObject>();

		for (PlaceableNode node : diagram.getHiddenNodes()) {
			if (node instanceof ObjectNodeActivity) {
				ObjectNodeActivity mobj = (ObjectNodeActivity) (node);
				if (mobj.cls().equals(cls)) {
					objects.add(mobj.object());
				}
			}
		}

		return objects;
	}
}
