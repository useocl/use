package org.tzi.use.gui.views.selection.classselection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.diagrams.util.MenuScroller;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.collections.CollectionUtil;

/**
 * ClassSelection is responsible for the new functions "Show", "Crop" and "Hide"
 * in the class diagram.
 * 
 * @author Jun Zhang
 * @author Jie Xu
 */

public class ClassSelection {

	private final ClassDiagram diagram;

	//Conrols for ScrollMenu
	private final int numOfElems = 20;
	private final int interval = 125;
	private final int topFixedCount = 0;
	private final int bottomFixedCount = 0;

	
	/**
	 * Constructor for ClassSelection.
	 */
	public ClassSelection(ClassDiagram diagram) {
		this.diagram = diagram;
	}

	@SuppressWarnings("serial")
	class ActionSelectionClassView extends AbstractAction {

		Set<MClass> selectedClasses;
		DiagramInputHandling mouseHandling;

		ActionSelectionClassView(String text) {
			super(text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SelectionClassView opv = new SelectionClassView(MainWindow.instance(), diagram);

			ViewFrame f = new ViewFrame("Selection classes", opv, "ObjectProperties.gif");

			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);
			MainWindow.instance().addNewViewFrame(f);
			f.setSize(580, 230);
		}
	}

	public ActionSelectionClassView getSelectionClassView(String text) {
		return new ActionSelectionClassView(text);
	}

	@SuppressWarnings("serial")
	class ActionSelectedClassPathView extends AbstractAction {
		private final Set<MClass> selectedClasses;

		ActionSelectedClassPathView(String text, Set<MClass> sc) {
			super(text);
			selectedClasses = sc;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SelectedClassPathView opv = new SelectedClassPathView(MainWindow.instance(), diagram, selectedClasses);
			ViewFrame f = new ViewFrame("Selection by path length", opv, "ObjectProperties.gif");
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);
			MainWindow.instance().addNewViewFrame(f);
			f.setSize(450, 200);
		}
	}

	public ActionSelectedClassPathView getSelectedClassPathView(String text, Set<MClass> sc) {
		return new ActionSelectedClassPathView(text, sc);
	}

	/**
	 * Method getAllKindClasses gives all classes, which contain it as well as
	 * all subclasses.
	 */
	public Set<MClass> getAllKindClasses(Set<MClass> selectedClasses) {
		Set<MClass> all = new HashSet<MClass>();
		all.addAll(selectedClasses);

		for (MClass mc : selectedClasses) {
			Set<MClass> allChildrenClass = CollectionUtil.downCastUnsafe(mc.allChildren());
			all.addAll(allChildrenClass);
		}

		return all;
	}

	/**
	 * Returns true, if at least one object of a class given by the set classes
	 * is displayed in any object diagram
	 */
	public boolean classHasDisplayedObjects(Set<MClass> classes) {
		List<NewObjectDiagramView> objDiagrams = MainWindow.instance().getObjectDiagrams();

		for (NewObjectDiagramView diagView : objDiagrams) {
			NewObjectDiagram diag = diagView.getDiagram();

			if (diag.getGraph() != null && diag.getGraph().size() > 0) {
				for (MClass mc : classes) {
					Iterator<PlaceableNode> itobject = diag.getGraph().iterator();

					// scan in fgraph
					while (itobject.hasNext()) {
						Object node = itobject.next();
						if (node instanceof ObjectNode) {
							MObject mobj = ((ObjectNode) node).object();
							if (mc.equals(mobj.cls())) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * Returns true, if at least one object of a class given by the set classes
	 * is hidden in any object diagram
	 */
	public boolean classHasHiddenObjects(Set<MClass> classes) {
		List<NewObjectDiagramView> diagrams = MainWindow.instance().getObjectDiagrams();

		for (NewObjectDiagramView diagView : diagrams) {
			NewObjectDiagram diag = diagView.getDiagram();

			if (diag.getHiddenNodes() != null && diag.getHiddenNodes().size() > 0) {
				for (MClass mc : classes) {
					Iterator<? extends PlaceableNode> itobject = diag.getHiddenNodes().iterator();

					while (itobject.hasNext()) {
						Object node = itobject.next();
						if (node instanceof ObjectNode) {
							ObjectNode mobj = (ObjectNode) node;
							if (mc.equals(mobj.cls())) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Method getSubMenuHideAssoc supplies a list of the sorted associations in the
	 * context menu. which are not hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuHideAssoc() {
		JMenu subMenuSelectionAssoc = new JMenu("Hide associations");

		Comparator<MAssociation> sort = new Comparator<MAssociation>() {
			@Override
			public int compare(MAssociation o1, MAssociation o2) {
				return o1.name().compareTo(o2.name());
			}
		};

		TreeSet<MAssociation> sortedAssociations = new TreeSet<>(sort);
		sortedAssociations.addAll(diagram.getSystem().model().associations());

		if (!diagram.getVisibleData().getEdges().isEmpty()) {
			final JMenuItem hideAllAssoc = new JMenuItem("Hide all associations");
			hideAllAssoc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sortedAssociations.forEach(assoc -> diagram.hideAssociation(assoc));
					diagram.repaint();
				}
			});
			subMenuSelectionAssoc.add(hideAllAssoc);
			subMenuSelectionAssoc.addSeparator();
		}

		sortedAssociations.stream().filter(association -> !diagram.isHidden(association)).forEach(association -> {
			final JMenuItem hideAssoc = new JMenuItem("Hide " + association.name());
			hideAssoc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					diagram.hideAssociation(association);
					diagram.repaint();
				}
			});
			subMenuSelectionAssoc.add(hideAssoc);

		});
		MenuScroller.setScrollerFor(subMenuSelectionAssoc, numOfElems, interval, topFixedCount, bottomFixedCount);
		return subMenuSelectionAssoc;
	}

	/**
	 * Method getSubMenuHideClass supplies a list of the sorted classes in the
	 * context menu. which are not hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuHideClass() {
		JMenu subMenuSelectionClassHide = new JMenu("Hide classes");
		MenuScroller.setScrollerFor(subMenuSelectionClassHide, numOfElems, interval, topFixedCount, bottomFixedCount);
		SelectionComparator sort = new SelectionComparator();
		TreeSet<ClassNode> sortedNodes = new TreeSet<ClassNode>(sort);
		Iterator<?> it = diagram.getGraph().iterator();

		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ClassNode) {
				sortedNodes.add((ClassNode) node);
			}
		}

		int nodesize = 0;

		for (ClassNode node : sortedNodes) {
			nodesize++;
			MClass cls = node.cls();
			String classname = cls.name();

			Set<MClass> hideClass = new HashSet<MClass>();
			hideClass.add(cls);
			subMenuSelectionClassHide.add(diagram.getActionHideNodes("Hide " + classname, hideClass));
		}

		if (nodesize > 1) {
			subMenuSelectionClassHide.insertSeparator(0);
			it = diagram.getGraph().iterator();
			Set<MClass> hideClass = new HashSet<MClass>();
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof ClassNode) {
					MClass cls = ((ClassNode) node).cls();
					hideClass.add(cls);
				}
			}
			subMenuSelectionClassHide.insert(diagram.getActionHideNodes("Hide all classes", hideClass), 0);
		}
		return subMenuSelectionClassHide;
	}

	/**
	 * This creates a JMenu with a sub menu for each currently hidden class to
	 * show it.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuShowClass() {
		JMenu subMenuSelectionClassShow = new JMenu("Show classes");
		MenuScroller.setScrollerFor(subMenuSelectionClassShow, numOfElems, interval, topFixedCount, bottomFixedCount);
		SelectionComparator sort = new SelectionComparator();

		if (diagram.getHiddenNodes().size() > 1) {
			final JMenuItem showAllClasses = new JMenuItem("Show all classes");
			showAllClasses.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					diagram.showAll();
					diagram.invalidateContent(true);
				}
			});
			subMenuSelectionClassShow.add(showAllClasses);
			subMenuSelectionClassShow.addSeparator();
		}

		TreeSet<MClass> sortedNodes = new TreeSet<MClass>(sort);
		for (PlaceableNode node : diagram.getHiddenNodes()) {
			if (node instanceof ClassNode) {
				ClassNode cNode = (ClassNode) node;
				sortedNodes.add(cNode.cls());
			}
		}

		for (final MClass cls : sortedNodes) {
			String classname = cls.name();
			final JMenuItem showClasses = new JMenuItem("Show " + classname);
			showClasses.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					diagram.showClass(cls);
					diagram.repaint();
				}
			});
			subMenuSelectionClassShow.add(showClasses);
		}

		return subMenuSelectionClassShow;
	}

	/**
	 * This creates a JMenu with a sub menu for each currently hidden association to
	 * show it.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuShowAssociation() {
		JMenu subMenuSelectionAssoc = new JMenu("Show associations");
		MenuScroller.setScrollerFor(subMenuSelectionAssoc, numOfElems, interval, topFixedCount, bottomFixedCount);
		Comparator<MAssociation> sort = new Comparator<MAssociation>() {
			@Override
			public int compare(MAssociation o1, MAssociation o2) {
				return o1.name().compareTo(o2.name());
			}
		};

		TreeSet<MAssociation> sortedAssociations = new TreeSet<>(sort);
		sortedAssociations.addAll(diagram.getSystem().model().associations());

		if (!diagram.getHiddenData().getEdges().isEmpty()) {
			final JMenuItem showAllAssoc = new JMenuItem("Show all associations");
			showAllAssoc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sortedAssociations.forEach(assoc -> diagram.showAssociation(assoc));
					diagram.repaint();
				}
			});
			subMenuSelectionAssoc.add(showAllAssoc);
			subMenuSelectionAssoc.addSeparator();
		}

		sortedAssociations.stream().filter(association -> diagram.isHidden(association)).forEach(association -> {
			final JMenuItem showAssoc = new JMenuItem("Show " + association.name());
			showAssoc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					diagram.showAssociation(association);
					diagram.repaint();
				}
			});
			subMenuSelectionAssoc.add(showAssoc);

		});

		return subMenuSelectionAssoc;
	}


	/**
	 * Method getSubMenuHideGeneralizations supplies a list of the sorted generalizations in the
	 * context menu. which are not hidden.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuHideGeneralizations(Set<MGeneralization> gens) {
		JMenu subMenuHideGeneralization = new JMenu("Hide generalizations");
		MenuScroller.setScrollerFor(subMenuHideGeneralization, numOfElems, interval, topFixedCount, bottomFixedCount);
		JMenuItem hideAll = new JMenuItem("Hide all generalizations");
		hideAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (MGeneralization gen : gens) {
					diagram.hideGeneralization(gen);
					diagram.repaint();
				}

			}
		});
		subMenuHideGeneralization.add(hideAll);

		subMenuHideGeneralization.addSeparator();
		
		for (MGeneralization gen : gens) {
			String name = gen.name().startsWith("GEN_") ? gen.name().substring(4, gen.name().length()) : gen.name();
			JMenuItem hideOneGen = new JMenuItem("Hide "+ name);
			hideOneGen.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					diagram.hideGeneralization(gen);
					diagram.repaint();
				}
			});
			subMenuHideGeneralization.add(hideOneGen);
		}
		
		return subMenuHideGeneralization;
	}


	/**
	 * This creates a JMenu with a sub menu for each currently hidden generalizations to
	 * show it.
	 * 
	 * @return JMenu
	 */
	public JMenu getSubMenuShowGeneralizations(HashSet<MGeneralization> gens) {
		JMenu subMenuShowGeneralization = new JMenu("Show generalizations");
		MenuScroller.setScrollerFor(subMenuShowGeneralization, numOfElems, interval, topFixedCount, bottomFixedCount);
				
		JMenuItem showAll = new JMenuItem("Show all generalizations");
		showAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (MGeneralization gen : gens) {
					diagram.showClass((MClass) gen.source());
					diagram.showClass((MClass) gen.target());
					diagram.showGeneralization(gen);
					diagram.repaint();
				}

			}
		});
		subMenuShowGeneralization.add(showAll);

		subMenuShowGeneralization.addSeparator();
		
		for (MGeneralization gen : gens) {
			String name = gen.name().startsWith("GEN_") ? gen.name().substring(4, gen.name().length()) : gen.name();
			JMenuItem showOneGen = new JMenuItem("Show "+ name);
			showOneGen.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!diagram.isVisible(gen.source())) 
						diagram.showClass((MClass) gen.source());
					if (!diagram.isVisible(gen.target())) 
						diagram.showClass((MClass) gen.target());
					
					diagram.showGeneralization(gen);
					diagram.repaint();
				}
			});
			subMenuShowGeneralization.add(showOneGen);
		}
		
		return subMenuShowGeneralization;
	}
}