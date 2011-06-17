package org.tzi.use.gui.views.selection.classselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;

/** 
 * ClassSelection is responsible for the new functions "Show", "Crop" and "Hide" in the class diagram. 
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public class ClassSelection {
	protected ClassDiagram diagram;
		
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

		ActionSelectionClassView(String text, Set<MClass> selectedClasses, DiagramInputHandling mouseHandling) {
			super(text);
			this.mouseHandling = mouseHandling;
			this.selectedClasses = selectedClasses;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {  
			MainWindow.instance().showClassSelectionClassView(selectedClasses, diagram, mouseHandling, diagram.getClassToNodeMap(), diagram.getNodeSelection()); 
		}
	}
	
	public ActionSelectionClassView getSelectionClassView(String text, DiagramInputHandling mouseHandling, Set<MClass> selectedClasses){
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
	public Set<MClass> getSelectedClassesOfAssociation(MAssociation ass) {
		return ass.associatedClasses();
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
	public boolean classHasDisplayedObjects(Set<MClass> classes) {
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
	public boolean classHasHiddenObjects(Set<MClass> classes) {
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
			subMenuSelectionClassHide.add(diagram.getHideAdmin().getAction("hide "
						+ classname, hideClass));
		}
		
		if (nodesize > 1) {
			subMenuSelectionClassHide.addSeparator();
			it = diagram.getGraph().iterator();
			Set<MClass> hideClass = new HashSet<MClass>();
			while(it.hasNext()){
				Object node = it.next();
				if(node instanceof ClassNode){
					MClass cls = ((ClassNode) node).cls();
					hideClass.add(cls);
				}
			}
			subMenuSelectionClassHide.add(diagram.getHideAdmin().getAction("Hide all classes", hideClass));
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

		Iterator<?> it = diagram.getHiddenNodes().iterator();
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
							diagram.getHideAdmin().showHiddenElements(sclasses);
						}
					});
				subMenuSelectionClassShow.add(showClasses);
		}

		if (diagram.getHiddenNodes().size() > 1) {
			subMenuSelectionClassShow.addSeparator();
			final JMenuItem showAllClasses = new JMenuItem(
			"Show all classes");
			showAllClasses.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					diagram.getHideAdmin().showAllHiddenElements();
				}
			});
			subMenuSelectionClassShow.add(showAllClasses);
		}
		return subMenuSelectionClassShow;
	}
	
}
