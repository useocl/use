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
import org.tzi.use.gui.views.diagrams.elements.edges.AssociationEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.collections.CollectionUtil;

/** 
 * ClassSelection is responsible for the new functions "Show", "Crop" and "Hide" in the class diagram. 
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public class ClassSelection {
	
	private final ClassDiagram diagram;
	
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
	        f.setSize(580,230);
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
			SelectedClassPathView opv = new SelectedClassPathView(
					MainWindow.instance(), diagram, selectedClasses);
			ViewFrame f = new ViewFrame("Selection by path length", opv,
					"ObjectProperties.gif");
			JComponent c = (JComponent) f.getContentPane();
			c.setLayout(new BorderLayout());
			c.add(opv, BorderLayout.CENTER);
			MainWindow.instance().addNewViewFrame(f);
			f.setSize(450, 200);
		}
	}
	
	public ActionSelectedClassPathView getSelectedClassPathView(String text, Set<MClass> sc){
		return new ActionSelectedClassPathView(text, sc);
	}
	
	/**
	 * Method getAllKindClasses gives all classes, which contain it as well as all subclasses.
	 */
	public Set<MClass> getAllKindClasses(Set<MClass> selectedClasses){
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
					while(itobject.hasNext()){
						Object node = itobject.next();
						if (node instanceof ObjectNode) {
							MObject mobj = ((ObjectNode) node).object();
							if(mc.equals(mobj.cls())) {
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
			
			if (diag.getHiddenNodes() != null && diag.getHiddenNodes().size() > 0 ) {
				for (MClass mc : classes) {
					Iterator<? extends PlaceableNode> itobject = diag.getHiddenNodes().iterator();
					
					while(itobject.hasNext()){
						Object node = itobject.next();
						if (node instanceof ObjectNode) {
							ObjectNode mobj = (ObjectNode) node;
							if(mc.equals(mobj.cls())){
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
	 * Method getSubMenuHideClass supplies a list of the sorted classes in the context menu.
	 * which are not hidden.
	 * @return JMenu
	 */
	public JMenu getSubMenuHideClass() {
		JMenu subMenuSelectionClassHide = new JMenu("Hide classes");

		SelectionComparator sort = new SelectionComparator();
		TreeSet<ClassNode> sortedNodes = new TreeSet<ClassNode>(sort);
		Iterator<?> it = diagram.getGraph().iterator();
		
		while(it.hasNext()){
			Object node = it.next();
			if(node instanceof ClassNode){
				sortedNodes.add((ClassNode)node);
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
			while(it.hasNext()){
				Object node = it.next();
				if(node instanceof ClassNode){
					MClass cls = ((ClassNode) node).cls();
					hideClass.add(cls);
				}
			}
			subMenuSelectionClassHide.insert(diagram.getActionHideNodes("Hide all classes", hideClass), 0);
		}
		return subMenuSelectionClassHide;
	}

	/**
	 * This creates a JMenu with a sub menu for each currently hidden
	 * class to show it.
	 * @return JMenu
	 */
	public JMenu getSubMenuShowClass() {
		JMenu subMenuSelectionClassShow = new JMenu("Show classes");
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
				ClassNode cNode = (ClassNode)node; 
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
	 * @return
	 */
	public JMenu getSubMenuShowAssociation() {
		JMenu subMenu = new JMenu("Show associations");
		Comparator<MAssociation> sort = new Comparator<MAssociation>() {
			@Override
			public int compare(MAssociation o1, MAssociation o2) {
				return o1.name().compareTo(o2.name());
			}
		};
		
		TreeSet<MAssociation> sortedAssociations = new TreeSet<>(sort);
		for (EdgeBase edge : diagram.getHiddenEdges()) {
			MAssociation association = null;
			
			if (edge instanceof AssociationEdge) {
				association = ((AssociationEdge)edge).getAssociation();
				boolean allVisible = true;
				
				for (MAssociationEnd end : association.associationEnds()) {
					if (!diagram.isVisible(end.cls())) {
						allVisible = false;
						break;
					}
				}
				
				if (allVisible) {
					sortedAssociations.add(association);
				}
			}
		}
		
		for (final MAssociation assoc : sortedAssociations) {
			final JMenuItem showAssoc = new JMenuItem("Show " + assoc.name());
			showAssoc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					diagram.showAssociation(assoc);
					diagram.repaint();
				}
			});
			
			subMenu.add(showAssoc);
		}
				
		return subMenu;
	}
	
}
