package org.tzi.use.gui.views.selection.objectselection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.util.AlphanumComparator;
import org.tzi.use.gui.views.diagrams.DiagramType;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.edges.LinkEdge;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.util.MenuScroller;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObjectImpl;
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

	// Controls for ScrollMenu
	private static final int NUM_OF_ELEMS = 20;
	private static final int INTERVAL = 125;
	private static final int TOP_FIXED_COUNT = 0;
	private static final int BOTTOM_FIXED_COUNT = 0;

	// UI label constants to avoid duplicated string literals
	private static final String LABEL_HIDE_OBJECTS = "Hide objects";
	private static final String LABEL_HIDE_ALL_OBJECTS = "Hide all objects";
	private static final String LABEL_HIDE = "Hide ";
	private static final String LABEL_CLASS_PREFIX = "Class ";
	private static final String LABEL_SHOW = "Show ";
	private static final String LABEL_SHOW_ALL_HIDDEN_OBJECTS = "Show all hidden objects";
	private static final String LABEL_HIDE_ALL_LINKS = "Hide all links";
	private static final String LABEL_SHOW_ALL_LINKS = "Show all links";
	private static final String LABEL_HIDE_ALL = "Hide all";
	private static final String LABEL_SHOW_ALL = "Show all";
	private static final String OBJECT_PROPERTIES_ICON = "ObjectProperties.gif";

	private final Comparator<MLink> linkComparator;
	private final Comparator<ObjectNodeActivity> objectComparator;

	/**
	 * Constructor for ObjectSelection.
	 */
	public ObjectSelection(DiagramViewWithObjectNode diagram, MSystem system) {
		this.diagram = diagram;
		this.system = system;
		AlphanumComparator keyComparator = new AlphanumComparator();
		this.linkComparator = Comparator
				.comparing((MLink link) -> link.linkedObjects().get(0).toString(), keyComparator)
				.thenComparing(link -> link.linkedObjects().get(1).toString(), keyComparator);
		this.objectComparator = Comparator.comparing((ObjectNodeActivity n) -> n.object().name(), keyComparator);

	}

	class ActionSelectedObjectPathView extends AbstractAction {
		private final transient Set<MObject> selectedObjects;

		ActionSelectedObjectPathView(String text, Set<MObject> sc) {
			super(text);
			this.selectedObjects = sc;
		}

		public void actionPerformed(ActionEvent e) {
			SelectedObjectPathView opv = new SelectedObjectPathView(MainWindow.instance(), system, diagram,
					selectedObjects);
			ViewFrame f = new ViewFrame("Selection by path length", opv, OBJECT_PROPERTIES_ICON);
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);

			if (Boolean.TRUE.equals(MainWindow.getJavaFxCall())){
				Platform.runLater(() -> {
					SwingNode swingNode = new SwingNode();
					swingNode.setContent(opv);
					swingNode.setCache(false);

					org.tzi.use.gui.mainFX.MainWindow.getInstance().createNewWindow("Selection by path length",swingNode, DiagramType.SELECTED_OBJECT_PATH_VIEW);
				});
			} else {
				MainWindow.instance().addNewViewFrame(f);
				f.setSize(450, 200);
			}
		}
	}

	public ActionSelectedObjectPathView getSelectedObjectPathView(String text, Set<MObject> sc) {
		return new ActionSelectedObjectPathView(text, sc);
	}

	class ActionSelectionObjectView extends AbstractAction {
		ActionSelectionObjectView() {
			super("With view...");
		}

		public void actionPerformed(ActionEvent e) {
			SelectionObjectView opv = new SelectionObjectView(MainWindow.instance(), system, diagram);
			ViewFrame f = new ViewFrame("Select objects", opv, OBJECT_PROPERTIES_ICON);
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);

			if (Boolean.TRUE.equals(MainWindow.getJavaFxCall())){
				Platform.runLater(() -> {
					SwingNode swingNode = new SwingNode();
					swingNode.setContent(opv);
					swingNode.setCache(false);

					org.tzi.use.gui.mainFX.MainWindow.getInstance().createNewWindow("Select objects",swingNode, DiagramType.SELECTED_OBJECT_VIEW);
				});
			} else {
				MainWindow.instance().addNewViewFrame(f);
				f.setSize(530, 230);
			}
		}
	}

	public ActionSelectionObjectView getSelectionObjectView() {
		return new ActionSelectionObjectView();
	}

	/**
	 * Show Selection OCL View
	 */
	class ActionSelectionOCLView extends AbstractAction {
		ActionSelectionOCLView() {
			super("With OCL...");
		}

		public void actionPerformed(ActionEvent e) {
			SelectionOCLView opv = new SelectionOCLView(MainWindow.instance(), system, diagram);
			ViewFrame f = new ViewFrame("Selection by OCL expression", opv, OBJECT_PROPERTIES_ICON);
			JComponent c = (JComponent) f.getContentPane();

			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);

			if (Boolean.TRUE.equals(MainWindow.getJavaFxCall())){
				Platform.runLater(() -> {
					SwingNode swingNode = new SwingNode();
					swingNode.setContent(opv);
					swingNode.setCache(false);

					org.tzi.use.gui.mainFX.MainWindow.getInstance().createNewWindow("Selection by OCL expression",swingNode, DiagramType.SELECTED_OCL_VIEW);
				});
			} else {
				MainWindow.instance().addNewViewFrame(f);
				f.setSize(370, 250);
			}
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
		if (node == null) {
			return Set.of();
		}

		Set<MObject> safeSelected = selectedObjectsOfAssociation == null ? Set.of() : selectedObjectsOfAssociation;

		Set<MObject> fromEdges = findObjectsFromEdges(node, safeSelected);
		if (!fromEdges.isEmpty()) {
			return fromEdges;
		}

		return findObjectsFromDiamonds(node.name(), safeSelected);
	}

	private Set<MObject> findObjectsFromEdges(MAssociation node, Set<MObject> selectedObjectsOfAssociation) {
		Set<MObject> objects = new HashSet<>();
		return findMatchingEdge(node)
				.map(edge -> {
					MObject mo1 = ((ObjectNodeActivity) edge.source()).object();
					MObject mo2 = ((ObjectNodeActivity) edge.target()).object();
					if (!selectedObjectsOfAssociation.contains(mo1)) {
						objects.add(mo1);
					}
					if (!selectedObjectsOfAssociation.contains(mo2)) {
						objects.add(mo2);
					}
					return objects;
				})
				.orElse(objects);
	}

	private java.util.Optional<BinaryAssociationOrLinkEdge> findMatchingEdge(MAssociation node) {
		Iterator<EdgeBase> it = this.diagram.getGraph().edgeIterator();
		while (it.hasNext()) {
			EdgeBase o = it.next();
			if (o instanceof BinaryAssociationOrLinkEdge edge && edge.getAssociation() != null
					&& edge.getAssociation().equals(node)) {
				return java.util.Optional.of(edge);
			}
		}
		return java.util.Optional.empty();
	}

	private Set<MObject> findObjectsFromDiamonds(String name, Set<MObject> selectedObjectsOfAssociation) {
		return findMatchingDiamond(name).map(dnode -> {
			Set<MObject> objects = new HashSet<>();
			for (MObject mo : dnode.link().linkedObjects()) {
				if (!selectedObjectsOfAssociation.contains(mo)) {
					objects.add(mo);
				}
			}
			return objects;
		}).orElse(Set.of());
	}

	private java.util.Optional<DiamondNode> findMatchingDiamond(String name) {
		Iterator<PlaceableNode> nodeIter = diagram.getGraph().iterator();
		while (nodeIter.hasNext()) {
			PlaceableNode o = nodeIter.next();
			if (o instanceof DiamondNode dnode && dnode.name().equalsIgnoreCase(name)) {
				return java.util.Optional.of(dnode);
			}
		}
		return java.util.Optional.empty();
	}

	/**
	 * Collect unique links and prepare association menus for the given edges.
	 */
	private void collectAssociationNamesAndLinks(Set<EdgeBase> edges, Map<String, JMenu> associationNames,
			List<MLink> links) {
		for (EdgeBase edge : edges) {
			if (edge instanceof LinkEdge aEdge) {
				MLink link = aEdge.getLink();
				if (!links.contains(link)) {
					JMenu menu = new JMenu(link.association().toString());
					MenuScroller.setScrollerFor(menu, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);
					associationNames.put(link.association().toString(), menu);
					links.add(link);
				}
			}
		}
	}

	private JMenuItem createMenuItem(String label, Runnable action) {
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(e -> action.run());
		return item;
	}

	/**
	 * Add menu entries (show or hide) for an association menu entry.
	 */
	private void addAssociationMenuItems(Entry<String, JMenu> menuEntry, List<MLink> links, boolean showMode) {
		JMenu assocMenu = menuEntry.getValue();
		String key = menuEntry.getKey();

		String allLabel = showMode ? LABEL_SHOW_ALL : LABEL_HIDE_ALL;
		assocMenu.add(createMenuItem(allLabel, () -> {
			List<MLink> matching = links.stream().filter(l -> key.equals(l.association().toString())).toList();
			if (showMode) {
				((NewObjectDiagram) diagram).showLink(matching);
			} else {
				((NewObjectDiagram) diagram).hideLink(matching);
			}
			diagram.repaint();
		}));

		assocMenu.addSeparator();

		for (MLink link : links) {
			if (key.equals(link.association().toString())) {
				String label = (showMode ? LABEL_SHOW : LABEL_HIDE) + formatLinkName(link);
				assocMenu.add(createMenuItem(label, () -> {
					if (showMode) {
						((NewObjectDiagram) diagram).showLink(link);
					} else {
						((NewObjectDiagram) diagram).hideLink(link);
					}
					diagram.repaint();
				}));
			}
		}
	}

	// ObjectNodeActivityComparator removed — not used (S3985)

	/**
	 * Method getSubMenuHideObject supplies a list of the sorted objects in the
	 * context menu. which are not hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuHideObject() {
		Set<PlaceableNode> visibleNodes = Sets.newHashSet(diagram.getGraph().getVisibleNodesIterator());
		Set<ObjectNodeActivity> visibleObjectNodes = CollectionUtil.filterByType(visibleNodes,
				ObjectNodeActivity.class);

		JMenu subMenuHideObject = new JMenu(LABEL_HIDE_OBJECTS);
		MenuScroller.setScrollerFor(subMenuHideObject, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

		final JMenuItem hideAllObjects = new JMenuItem(LABEL_HIDE_ALL_OBJECTS);
		hideAllObjects.setEnabled(diagram.getVisibleData().hasNodes());
		hideAllObjects.addActionListener(ev -> {
			diagram.hideAll();
			diagram.repaint();
		});
		subMenuHideObject.add(hideAllObjects);
		subMenuHideObject.addSeparator();

		TreeSet<ObjectNodeActivity> sortedObjectNodes = new TreeSet<>(objectComparator);
		sortedObjectNodes.addAll(visibleObjectNodes);
		Map<MClass, JMenu> classMenus = new HashMap<>();

		for (ObjectNodeActivity objNode : sortedObjectNodes) {
			final MClass cls = objNode.cls();
			final MObject obj = objNode.object();

			JMenu parent = classMenus.computeIfAbsent(cls, k -> {
				JMenu subm = new JMenu(LABEL_CLASS_PREFIX + k.name());
				MenuScroller.setScrollerFor(subm, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);
				JMenuItem showAll = new JMenuItem(LABEL_HIDE_ALL);
				showAll.addActionListener(e -> {
					diagram.hideObjects(getVisibleObjects(k));
					diagram.repaint();
				});
				subm.add(showAll);
				subm.addSeparator();
				return subm;
			});

			final JMenuItem hideObject = new JMenuItem(LABEL_HIDE + obj.name());
			hideObject.addActionListener(ev -> {
				diagram.hideObject(obj);
				diagram.repaint();
			});
			parent.add(hideObject);
		}

		List<MClass> classes = new ArrayList<>(classMenus.keySet());
		Collections.sort(classes, new MNamedElementComparator());
		for (JMenu m : classMenus.values()) {
			MenuScroller.setScrollerFor(m, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);
		}

		for (MClass cls : classes) {
			subMenuHideObject.add(classMenus.get(cls));
		}

		return subMenuHideObject;
	}

	/**
	 * Method getSubMenuHideAssoc supplies a list of the sorted objects in the
	 * context menu. which are not hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuHideLinks() {
		JMenu subMenuHideAssoc = new JMenu(LABEL_HIDE + "links");
		MenuScroller.setScrollerFor(subMenuHideAssoc, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

		final JMenuItem hideAllAssoc = new JMenuItem(LABEL_HIDE_ALL_LINKS);
		hideAllAssoc.setEnabled(!diagram.getVisibleData().getEdges().isEmpty());
		hideAllAssoc.addActionListener(e -> {
			((NewObjectDiagram) diagram).hideAllLinks();
			diagram.repaint();
		});
		subMenuHideAssoc.add(hideAllAssoc);
		subMenuHideAssoc.addSeparator();

		// ADD CLASS SUPPORT
		Set<EdgeBase> edges = diagram.getVisibleData().getEdges();

		final Map<String, JMenu> associationNames = new TreeMap<>();
		final List<MLink> links = new ArrayList<>();

		collectAssociationNamesAndLinks(edges, associationNames, links);

		links.sort(linkComparator);

		for (final Entry<String, JMenu> menu : associationNames.entrySet()) {
			subMenuHideAssoc.add(menu.getValue());
			addAssociationMenuItems(menu, links, false);
		}
		return subMenuHideAssoc;
	}

	/**
	 * Formatiere Link-Bezeichung fuer die GUI-Anzeige. Vorgabe Martin: Wenn
	 * nicht reflexiv, dann Format von [AssocName : (role1:Object1,
	 * role2:Object2)] nach AssocName (Object1, Object2) Wenn reflexiv, dann
	 * Format von [AssocName : (role1:Object1, role2:Object2)] nach AssocName
	 * (role1:Object1, role2:Object2)
	 * 
	 * @param link
	 * @return
	 */
	public String formatLinkName(MLink link) {
		StringBuilder label = new StringBuilder();

		// start building label
		label.append("(");

		// if linkobject
		if (link instanceof MLinkObjectImpl lom) {
			label.append(lom.name());
			label.append(" : ");

		}

		// If reflexiv, add role
		if (link.association().associatedClasses().size() == 1) {
			label.append(link.getLinkEnd(0).associationEnd().nameAsRolename());
			label.append(":");
		}

		// Add object
		label.append(link.linkedObjects().get(0).toString());

		// If has Qualifiers, add qualifiers
		if (link.getLinkEnd(0).hasQualifiers()) {
			label.append(",");
			label.append(link.getLinkEnd(0).getQualifierValues());
		}

		label.append(", ");

		// If reflexiv, add role
		if (link.association().associatedClasses().size() == 1) {
			label.append(link.getLinkEnd(1).associationEnd().nameAsRolename());
			label.append(":");
		}

		// Add object
		label.append(link.linkedObjects().get(1).toString());

		// If has Qualifiers, add qualifiers
		if (link.getLinkEnd(1).hasQualifiers()) {
			label.append(",");
			label.append(link.getLinkEnd(1).getQualifierValues());
		}

		// if n-ary
		if (link.linkedObjects().size() > 2) {
			for (int i = 2; i < link.linkedObjects().size(); i++) {
				label.append(", ");
				label.append(link.linkedObjects().get(i).toString());
			}
		}

		label.append(")");

		return label.toString();
	}

	/**
	 * Method getSubMenuShowLinks supplies a list of the sorted links in the
	 * context menu. which are hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuShowLinks() {

		JMenu subMenuShowLinks = new JMenu(LABEL_SHOW + "links");
		MenuScroller.setScrollerFor(subMenuShowLinks, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

		final JMenuItem showAllLinks = new JMenuItem(LABEL_SHOW_ALL_LINKS);
		showAllLinks.setEnabled(!diagram.getHiddenData().getEdges().isEmpty());
		showAllLinks.addActionListener(e -> {
			((NewObjectDiagram) diagram).showAllLinks();
			diagram.repaint();
		});
		subMenuShowLinks.add(showAllLinks);
		subMenuShowLinks.addSeparator();

		Set<EdgeBase> edges = diagram.getHiddenData().getEdges();

		final Map<String, JMenu> associationNames = new TreeMap<>();
		final List<MLink> links = new ArrayList<>();

		collectAssociationNamesAndLinks(edges, associationNames, links);

		links.sort(linkComparator);

		for (final Entry<String, JMenu> menu : associationNames.entrySet()) {
			subMenuShowLinks.add(menu.getValue());
			addAssociationMenuItems(menu, links, true);
		}

		return subMenuShowLinks;
	}

	/**
	 * Method getSubMenuLinksByKind supplies a list of the sorted links by kinds
	 * in the context menu.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuLinksByKind() {
		JMenu subMenuAssociation = new JMenu("Show/hide links-by-kind");
		MenuScroller.setScrollerFor(subMenuAssociation, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

		if (!diagram.getVisibleData().getEdges().isEmpty()) {
			final JMenuItem hideAllAssoc = new JMenuItem(LABEL_HIDE_ALL_LINKS);
			hideAllAssoc.addActionListener(e -> {
				((NewObjectDiagram) diagram).hideAllLinks();
				diagram.repaint();
			});
			subMenuAssociation.add(hideAllAssoc);
		}

		if (!diagram.getHiddenData().getEdges().isEmpty()) {
			final JMenuItem showAllAssoc = new JMenuItem(LABEL_SHOW_ALL_LINKS);
			showAllAssoc.addActionListener(e -> {
				((NewObjectDiagram) diagram).showAllLinks();
				diagram.repaint();
			});
			subMenuAssociation.add(showAllAssoc);
		}

		subMenuAssociation.addSeparator();

		Map<String, List<MLink>> assocs = ((NewObjectDiagram) diagram).mapLinksToKindOfAssociation();
		for (Map.Entry<String, List<MLink>> a : assocs.entrySet()) {
			addLinksByKindEntry(subMenuAssociation, a.getKey(), a.getValue());
		}
		return subMenuAssociation;
	}

	private void addLinksByKindEntry(JMenu parentMenu, String key, List<MLink> assocLinks) {
		if (assocLinks == null || assocLinks.isEmpty()) {
			return;
		}
		JMenu assocMenu = new JMenu(key);
		MenuScroller.setScrollerFor(assocMenu, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

		List<MLink> links = new ArrayList<>(assocLinks);
		int assocLinkState = ((NewObjectDiagram) diagram).isHidden(links);
		if (assocLinkState == 1 || assocLinkState == 2) {
			assocMenu.add(createMenuItem(LABEL_SHOW_ALL, () -> ((NewObjectDiagram) diagram).showLink(links)));
		}
		if (assocLinkState == 0 || assocLinkState == 2) {
			assocMenu.add(createMenuItem(LABEL_HIDE_ALL, () -> ((NewObjectDiagram) diagram).hideLink(links)));
		}
		assocMenu.addSeparator();

		links.sort(linkComparator);

		for (MLink link : links) {
			if (((NewObjectDiagram) diagram).isHidden(link)) {
				assocMenu.add(createMenuItem(LABEL_SHOW + formatLinkName(link), () -> ((NewObjectDiagram) diagram).showLink(link)));
			} else {
				assocMenu.add(createMenuItem(LABEL_HIDE + formatLinkName(link), () -> ((NewObjectDiagram) diagram).hideLink(link)));
			}
		}
		parentMenu.add(assocMenu);
	}

	/**
	 * Method getSubMenuShowObject supplies a list of the sorted objects in the
	 * context menu, which are hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuShowObject() {
		JMenu subMenuShowObject = new JMenu(LABEL_SHOW + "objects");
		MenuScroller.setScrollerFor(subMenuShowObject, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

		Set<? extends PlaceableNode> hiddenNodes = diagram.getHiddenNodes();
		Set<ObjectNodeActivity> hiddenObjectNodes = CollectionUtil.filterByType(hiddenNodes, ObjectNodeActivity.class);

		final JMenuItem showAllObjects = new JMenuItem(LABEL_SHOW_ALL_HIDDEN_OBJECTS);
		showAllObjects.setEnabled(!hiddenObjectNodes.isEmpty());
		showAllObjects.addActionListener(ev -> {
			diagram.showAll();
			diagram.repaint();
		});

		subMenuShowObject.add(showAllObjects);
		subMenuShowObject.addSeparator();

		TreeSet<ObjectNodeActivity> sortedNodes = new TreeSet<>(objectComparator);

		sortedNodes.addAll(hiddenObjectNodes);

		Map<MClass, JMenu> classMenus = new HashMap<>();
		for (ObjectNodeActivity node : sortedNodes) {
			final ObjectNodeActivity objNode = node;
			final MClass cls = objNode.cls();
			final MObject obj = objNode.object();

			JMenu parent = classMenus.computeIfAbsent(cls, k -> {
				JMenu subm = new JMenu(LABEL_CLASS_PREFIX + k.name());
				MenuScroller.setScrollerFor(subm, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);

				JMenuItem showAll = new JMenuItem(LABEL_SHOW_ALL);
				showAll.addActionListener(e -> {
					diagram.showObjects(getHiddenObjects(k));
					diagram.repaint();
				});
				subm.add(showAll);
				subm.addSeparator();
				return subm;
			});

			final JMenuItem showObject = new JMenuItem(LABEL_SHOW + obj.name());
			showObject.addActionListener(ev -> {
				diagram.showObject(obj);
				diagram.repaint();
			});
			parent.add(showObject);
		}

		List<MClass> classes = new ArrayList<>(classMenus.keySet());
		Collections.sort(classes, new MNamedElementComparator());

		for (JMenu m : classMenus.values()) {
			MenuScroller.setScrollerFor(m, NUM_OF_ELEMS, INTERVAL, TOP_FIXED_COUNT, BOTTOM_FIXED_COUNT);
		}

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
		Set<MObject> objects = new HashSet<>();
		Iterator<PlaceableNode> itobject = diagram.getVisibleData().getNodes().iterator();

		while (itobject.hasNext()) {
			Object node = itobject.next();
			if (node instanceof ObjectNodeActivity ona && allClasses.contains(ona.object().cls())) {
				objects.add(ona.object());
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
		Set<MObject> objects = new HashSet<>();

		for (PlaceableNode node : diagram.getVisibleData().getNodes()) {
			if (node instanceof ObjectNodeActivity ona && cls.equals(ona.object().cls())) {
				objects.add(ona.object());
			}
		}

		return objects;
	}

	public Set<MObject> getCropHideObjects(Set<MClass> classes) {
		Set<MClass> classesToHide = new HashSet<>();
		Iterator<PlaceableNode> itg = diagram.getGraph().iterator();

		while (itg.hasNext()) {
			Object node = itg.next();
			if (node instanceof ObjectNodeActivity ona && !classes.contains(ona.object().cls())) {
				classesToHide.add(ona.object().cls());
			}
		}

		for (Object node : diagram.getHiddenNodes()) {
			if (node instanceof MObject mobj && !classes.contains(mobj.cls())) {
				classesToHide.add(mobj.cls());
			}
		}

		return getDisplayedObjectsForClasses(classesToHide);
	}

	public Set<MObject> getHiddenObjects(Set<MClass> classes) {
		final Set<MObject> objects = new HashSet<>();

		for (PlaceableNode node : diagram.getHiddenNodes()) {
			if (node instanceof ObjectNodeActivity ona && classes.contains(ona.cls())) {
				objects.add(ona.object());
			}
		}

		return objects;
	}

	public Set<MObject> getHiddenObjects(MClass cls) {
		final Set<MObject> objects = new HashSet<>();

		for (PlaceableNode node : diagram.getHiddenNodes()) {
			if (node instanceof ObjectNodeActivity ona && ona.cls().equals(cls)) {
				objects.add(ona.object());
			}
		}

		return objects;
	}
}
