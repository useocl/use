package org.tzi.use.gui.views.selection.classselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.event.DiagramMouseHandling;
import org.tzi.use.gui.views.diagrams.event.HideAdministration;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;

/** 
 * ClassSelection is responsible for the new functions "Show", "Crop" and "Hide" in the class diagram. 
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public class ClassSelection {
	private DirectedGraph<NodeBase, EdgeBase> fGraph;
	private Set<Object> fHiddenNodes;
	private HideAdministration fHideAdmin;
	
	private Map<MClass, ClassNode> fClassToNodeMap;
	private Selection fNodeSelection;
	protected ClassDiagram diagram;
		
	/**
	 * Constructor for ClassSelection.
	 */
	//TODO: Remove parameter, they are reachable through diagram 
	public ClassSelection(ClassDiagram diagram, DirectedGraph<NodeBase, EdgeBase> fGraph, 
						  Set<Object> fHiddenNodes, HideAdministration fHideAdmin, 
						  Map<MClass, ClassNode> fClassToNodeMap, Selection fNodeSelection) {
		this.fGraph = fGraph;
		this.fHiddenNodes = fHiddenNodes;
		this.fHideAdmin = fHideAdmin;
		this.fClassToNodeMap = fClassToNodeMap;
		this.fNodeSelection = fNodeSelection;
		this.diagram = diagram;
	}
	
	@SuppressWarnings("serial")
	class ActionSelectionClassView extends AbstractAction {
		
		Set<MClass> selectedClasses;
		DiagramMouseHandling mouseHandling;

		ActionSelectionClassView(String text, Set<MClass> selectedClasses, DiagramMouseHandling mouseHandling) {
			super(text);
			this.mouseHandling = mouseHandling;
			this.selectedClasses = selectedClasses;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {  
			MainWindow.instance().showClassSelectionClassView(selectedClasses, diagram, mouseHandling, fClassToNodeMap, fNodeSelection); 
		}
	}
	
	public ActionSelectionClassView getSelectionClassView(String text, DiagramMouseHandling mouseHandling, Set<MClass> selectedClasses){
		return new ActionSelectionClassView(text, selectedClasses, mouseHandling);
	}
	
	@SuppressWarnings("serial")
	class ActionSelectedAssociationPathView extends AbstractAction {
		private Set<MClass> selectedClasses;
		private Set<AssociationName> anames;
		
		ActionSelectedAssociationPathView(String text, Set<MClass> sc, Set<AssociationName> anames) {
			super(text);
			this.anames = anames;
			selectedClasses = sc;
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance().showSelectedAssociationPathView(diagram, selectedClasses, anames);
		}
	}
	
	public ActionSelectedAssociationPathView getSelectedAssociationPathView(String text, Set<MClass> sc, Set<AssociationName> anames){
		return new ActionSelectedAssociationPathView(text, sc, anames);
	}
	
	@SuppressWarnings("serial")
	class ActionSelectedClassPathView extends AbstractAction {
		private Set<MClass> selectedClasses;

		ActionSelectedClassPathView(String text, Set<MClass> sc) {
			super(text);
			selectedClasses = sc;
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance().showSelectedClassPathView(diagram, selectedClasses);
		}
	}
	
	public ActionSelectedClassPathView getSelectedClassPathView(String text, Set<MClass> sc){
		return new ActionSelectedClassPathView(text, sc);
	}
	
	
	/**
	 * Method getSelectedClassesOfAssociation returns all relevant classes, 
	 * which are connected with the Association selected by the user. 
	 */
	public Set<MClass> getSelectedClassesOfAssociation(AssociationName node) {
		Set<MClass> classes = new HashSet<MClass>();
		Iterator<EdgeBase> it = fGraph.edgeIterator();
		String name = node.name();
				
		while (it.hasNext()) {
			EdgeBase edge = it.next();
			
			if (edge.getAssocName() != null && edge.getAssocName().equals(node)){
				MClass mc = ((ClassNode)(edge.source())).cls();
				classes.add(mc);
				
				mc = ((ClassNode)(edge.target())).cls();
				classes.add(mc);
								
				return classes;
			}
		}
		
		Iterator<NodeBase> it2 = fGraph.iterator();
		
		while (it2.hasNext()) {
			Object o = it2.next();
			if(o instanceof DiamondNode) {
				DiamondNode dnode = (DiamondNode)o;
				if (dnode.name().equalsIgnoreCase(name)){
					for (MClass mc : dnode.association().associatedClasses()) {
						classes.add(mc);
					}
					
					return classes;
				}
			}
		}

		return classes;
	}
	
	
	/**
	 * Method getAllKindClasses gives all classes, which contain it as well as all subclasses.
	 */
	public Set<MClass> getAllKindClasses(Set<MClass> selectedClasses){
		Set<MClass> all = new HashSet<MClass>();
		all.addAll(selectedClasses);

		for (MClass mc : selectedClasses) {
			all.addAll(mc.allChildren());
		}
		
		return all;
	}
	
	/**
	 * Returns true, if at least one object of a class given by the set classes
	 * is displayed in any object diagram 
	 */
	public boolean haveShowInObjectDiagram(Set<MClass> classes) {
		//TODO: Rename method!
		List<NewObjectDiagramView> objDiagrams = MainWindow.instance().getObjectDiagrams();
		
		for (NewObjectDiagramView diagView : objDiagrams) {
			NewObjectDiagram diag = diagView.getDiagram();
			
			if (diag.getGraph() != null && diag.getGraph().size() > 0) {
				for (MClass mc : classes) {
					Iterator<NodeBase> itobject = diag.getGraph().iterator();
					
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
	public boolean haveHideInObjectDiagram(Set<MClass> classes) {
		//TODO: rename method!
		List<NewObjectDiagramView> diagrams = MainWindow.instance().getObjectDiagrams();
		
		for (NewObjectDiagramView diagView : diagrams) {
			NewObjectDiagram diag = diagView.getDiagram();
			
			if (diag.getHiddenNodes() != null && diag.getHiddenNodes().size() > 0 ) {
				for (MClass mc : classes) {
					Iterator<Object> itobject = diag.getHiddenNodes().iterator();
					
					while(itobject.hasNext()){
						Object node = itobject.next();
						if (node instanceof MObject) {
							MObject mobj = (MObject) node;
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
		JMenu subMenuSelectionClassHide = new JMenu("Selection hide class");

		SelectionComparator sort = new SelectionComparator();
		TreeSet<ClassNode> sortedNodes = new TreeSet<ClassNode>(sort);
		Iterator<?> it = fGraph.iterator();
		
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
			subMenuSelectionClassHide.add(fHideAdmin.setValues("hide "
						+ classname, hideClass));
		}
		
		if (nodesize > 1) {
			subMenuSelectionClassHide.addSeparator();
			it = fGraph.iterator();
			Set<MClass> hideClass = new HashSet<MClass>();
			while(it.hasNext()){
				Object node = it.next();
				if(node instanceof ClassNode){
					MClass cls = ((ClassNode) node).cls();
					hideClass.add(cls);
				}
			}
			subMenuSelectionClassHide.add(fHideAdmin.setValues("Hide all classes", hideClass));
		}
		return subMenuSelectionClassHide;
	}

	/**
	 * Method getSubMenuShowClass supplies a list of the sorted classes in the context menu, 
	 * which are already hidden.
	 * @return JMenu
	 */
	public JMenu getSubMenuShowClass() {
		JMenu subMenuSelectionClassShow = new JMenu("Selection show class");

		Iterator<?> it = fHiddenNodes.iterator();
		SelectionComparator sort = new SelectionComparator();
		
		TreeSet<MClass> sortedNodes = new TreeSet<MClass>(sort);
		while(it.hasNext()){
			Object node = it.next();
			if (node instanceof MClass) {
				sortedNodes.add((MClass)node);
			}
		}
		
		for (MClass cls : sortedNodes) {
			String classname = cls.name();
			final Set<MClass> sclasses = new HashSet<MClass>();
			sclasses.clear();
			sclasses.add(cls);

			final JMenuItem showClasses = new JMenuItem(
							"Show " + classname);
			showClasses.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
							fHideAdmin.showHiddenElements(sclasses);
						}
					});
				subMenuSelectionClassShow.add(showClasses);
		}

		if (fHiddenNodes.size() > 1) {
			subMenuSelectionClassShow.addSeparator();
			final JMenuItem showAllClasses = new JMenuItem(
			"Show all classes");
			showAllClasses.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					fHideAdmin.showAllHiddenElements();
				}
			});
			subMenuSelectionClassShow.add(showAllClasses);
		}
		return subMenuSelectionClassShow;
	}
	
}
