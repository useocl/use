package org.tzi.use.gui.views.selection.classselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
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
	private Set fHiddenNodes;
	private HideAdministration fHideAdmin;
	
	private Map<MClass, ClassNode> fClassToNodeMap;
	private Selection fNodeSelection;
	
	/**
	 * Constructor for ClassSelection.
	 */
	public ClassSelection(DirectedGraph<NodeBase, EdgeBase> fGraph, 
						  Set fHiddenNodes, HideAdministration fHideAdmin, 
						  Map<MClass, ClassNode> fClassToNodeMap, Selection fNodeSelection) {
		this.fGraph = fGraph;
		this.fHiddenNodes = fHiddenNodes;
		this.fHideAdmin = fHideAdmin;
		this.fClassToNodeMap = fClassToNodeMap;
		this.fNodeSelection = fNodeSelection;
	}
	
	class ActionSelectionClassView extends AbstractAction {
		
		HashSet selectedClasses;
		ClassDiagram classDiagram;
		DiagramMouseHandling mouseHandling;

		ActionSelectionClassView(String text, HashSet selectedClasses,DiagramMouseHandling mouseHandling, ClassDiagram classDiagram) {
			super(text);
			this.mouseHandling = mouseHandling;
			this.selectedClasses = selectedClasses;
			this.classDiagram = classDiagram;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {  
			MainWindow.instance().showClassSelectionClassView(selectedClasses, classDiagram, mouseHandling, fClassToNodeMap, fNodeSelection); 
		}
	}
	
	public ActionSelectionClassView getSelectionClassView(String text, DiagramMouseHandling mouseHandling, HashSet selectedClasses, ClassDiagram classDiagram){
		return new ActionSelectionClassView(text,selectedClasses, mouseHandling, classDiagram);
	}
	
	class ActionSelectedAssociationPathView extends AbstractAction {
		private HashSet selectedClasses;
		private HashSet anames;
		ActionSelectedAssociationPathView(String text, HashSet sc, HashSet anames) {
			super(text);
			this.anames = anames;
			selectedClasses = sc;
		}

		public void actionPerformed(ActionEvent e) {
			MainWindow.instance()
					.showSelectedAssociationPathView(selectedClasses, anames);
		}
	}
	
	public ActionSelectedAssociationPathView getSelectedAssociationPathView(String text, HashSet sc, HashSet anames){
		return new ActionSelectedAssociationPathView(text, sc, anames);
	}
	
	class ActionSelectedClassPathView extends AbstractAction {
		private HashSet selectedClasses;

		ActionSelectedClassPathView(String text, HashSet sc) {
			super(text);
			selectedClasses = sc;
		}

		public void actionPerformed(ActionEvent e) {
			SelectedClassPathView v = MainWindow.instance()
					.showSelectedClassPathView(selectedClasses);
		}
	}
	
	public ActionSelectedClassPathView getSelectedClassPathView(String text, HashSet sc){
		return new ActionSelectedClassPathView(text, sc);
	}
	
	
	/**
	 * Method getSelectedClassesOfAssociation returns all relevant classes, 
	 * which are connected with the Association selected by the user. 
	 */
	public HashSet getSelectedClassesOfAssociation(AssociationName node, HashSet selectedClassesOfAssociation){
		HashSet classes = new HashSet();
		Iterator it = fGraph.edgeIterator();
		String name = node.name();
		boolean have = false;
		while(it.hasNext() && !have){
			Object o = it.next();
			if(o instanceof EdgeBase){
				EdgeBase edge = (EdgeBase)o;
				if(edge.getAssocName()!= null && edge.getAssocName().equals(node)){
					MClass mc = ((ClassNode)(edge.source())).cls();
					if(!selectedClassesOfAssociation.contains(mc)){
						classes.add(mc);
					}
					mc = ((ClassNode)(edge.target())).cls();
					if(!selectedClassesOfAssociation.contains(mc) && !classes.contains(mc)){
						classes.add(mc);
					}
					return classes;
				}
			}
		}
		if(!have){
			it = fGraph.iterator();
			while(it.hasNext()){
				Object o = it.next();
				if(o instanceof DiamondNode){
					if(((DiamondNode)o).name().equalsIgnoreCase(name)){
						DiamondNode dnode = (DiamondNode)o;
						Set set = dnode.association().associatedClasses();
						Iterator it2 = set.iterator();
						while(it2.hasNext()){
							MClass mc = (MClass)(it2.next());
							if(!selectedClassesOfAssociation.contains(mc) && !classes.contains(mc)){
								classes.add(mc);
							}
						}
						return classes;
					}
				}
			}
		}
		return classes;
	}
	
	
	/**
	 * Method getAllKindClasses gives all classes, which contain it as well as all subclasses.
	 */
	public Set<Object> getAllKindClasses(Set<?> selectedClasses){
		Set<Object> all = new HashSet<Object>();
		all.addAll(selectedClasses);

		for (Object obj : selectedClasses) {
			if (obj instanceof MClass) {
				MClass mc = (MClass)obj;
				all.addAll(mc.allChildren());
			}
		}
		
		return all;
	}
	
	/**
	 * Method haveShowInObjectDiagram examines whether the marked class in the class diagram 
	 * at least an appropriate object in the object diagram gives(those is still show). 
	 */
	public boolean haveShowInObjectDiagram(Set classes){
		boolean haveshow = false;
		
		if((NewObjectDiagram.ffGraph!=null && NewObjectDiagram.ffGraph.size()>0)){
			Iterator it= classes.iterator();
			while(it.hasNext() && !haveshow){
				MClass mc = (MClass)(it.next());
				Iterator itobject = NewObjectDiagram.ffGraph.iterator();
				
				//scan in fgraph
				while(itobject.hasNext()){
					Object node = itobject.next();
					if (node instanceof ObjectNode) {
						MObject mobj = ((ObjectNode) node).object();
						if(mc.name().equals(mobj.cls().name())){
							haveshow = true;
							break;
						}
					}
				}
			}
		}
		return haveshow;
	}
				
	/**
	 * Method aveHidelnObjectDiagram examines whether the marked class in the class diagram 
	 * at least an appropriate object in the object diagram gives(those is already hidden). 
	 */
	public boolean haveHideInObjectDiagram(Set classes){
		boolean havehide = false;
		if((NewObjectDiagram.ffHiddenNodes!=null && NewObjectDiagram.ffHiddenNodes.size()>0)){
			Iterator it= classes.iterator();
			while(it.hasNext() && !havehide){
				MClass mc = (MClass)(it.next());
				Iterator itobject = NewObjectDiagram.ffHiddenNodes.iterator();
				
				while(itobject.hasNext()){
					Object node = itobject.next();
					if (node instanceof MObject) {
						MObject mobj = (MObject) node;
						if(mc.name().equals(mobj.cls().name())){
							havehide = true;
							break;
						}
					}
				}
			}
		}
		return havehide;
	}
	
	public HashSet getHideObjects(Set classes){ 
		final HashSet objects = new HashSet();
		Iterator it= classes.iterator();
		while(it.hasNext()){
			MClass mc = (MClass)(it.next());
			
			Iterator itobject = NewObjectDiagram.ffGraph.iterator();
			while(itobject.hasNext()){
				Object node = itobject.next();
				if(node instanceof ObjectNode){
					MObject mobj = ((ObjectNode) node).object();
					if(mobj.cls().name().equals(mc.name())){
						objects.add(mobj);
					}
				}
			}
		}
		return objects;
		
	}
	
	public HashSet getShowObjects(Set classes){ 
		final HashSet objects = new HashSet();
		
		Iterator it= classes.iterator();
		while(it.hasNext()){
			MClass mc = (MClass)(it.next());
			
			Iterator ithide = NewObjectDiagram.ffHiddenNodes.iterator(); 
			while(ithide.hasNext()){
				Object node = ithide.next();
				if(node instanceof MObject){
					MObject mobj = (MObject)(node);
					if(mobj.cls().name().equals(mc.name())){
						objects.add(mobj);
					}
				}
			}
		}
		return objects;
	}
	
	public HashSet getCropHideObjects(Set classes){ 
		HashSet hideclasses = new HashSet(); 
		Iterator itg = NewObjectDiagram.ffGraph.iterator();
		while(itg.hasNext()){
			Object node = itg.next();
			if(node instanceof ObjectNode){
				MObject mobj = ((ObjectNode) node).object();
				Iterator its = classes.iterator();
				boolean have = false;
				while(its.hasNext()){
					MClass mc = (MClass)(its.next());
					if(mc.name().equals(mobj.cls().name())){
						have = true;
						break;
					}
				}
				if(!have){
					hideclasses.add(mobj.cls());
				}
			}
		}
		Iterator ith = NewObjectDiagram.ffHiddenNodes.iterator();
		while(ith.hasNext()){
			Object node = ith.next();
			if(node instanceof MObject){
				MObject mobj = (MObject)(node);
				Iterator its = classes.iterator();
				boolean have = false;
				while(its.hasNext()){
					MClass mc = (MClass)(its.next());
					if(mc.name().equals(mobj.cls().name())){
						have = true;
						break;
					}
				}
				if(!have && !hideclasses.contains(mobj.cls())){
					hideclasses.add(mobj.cls());
				}
			}
		}
		return getHideObjects(hideclasses);
		
	}
	
	/**
	 * Method getSubMenuHideClass supplies a list of the sorted classes in the context menu.
	 * which are not hidden.
	 * @return JMenu
	 */
	public JMenu getSubMenuHideClass() {
		JMenu subMenuSelectionClassHide = new JMenu("Selection hide class");

		SelectionComparator sort = new SelectionComparator();
		TreeSet sortedNodes = new TreeSet(sort);
		Iterator it = fGraph.iterator();
		while(it.hasNext()){
			Object node = it.next();
			if(node instanceof ClassNode){
				sortedNodes.add((ClassNode)node);
			}
		}
		it = sortedNodes.iterator();
		int nodesize = 0;
		while (it.hasNext()) {
			ClassNode node = (ClassNode)(it.next());
			String classname = "";
			nodesize++;
			MClass cls = node.cls();
			classname = cls.name();
			HashSet hideClass = new HashSet();
			hideClass.add(cls);
			subMenuSelectionClassHide.add(fHideAdmin.setValues("hide "
						+ classname, hideClass));
		}
		if (nodesize > 1) {
			subMenuSelectionClassHide.addSeparator();
			it = fGraph.iterator();
			HashSet hideClass = new HashSet();
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

		Iterator it = fHiddenNodes.iterator();
		SelectionComparator sort = new SelectionComparator();
		TreeSet sortedNodes = new TreeSet(sort);
		while(it.hasNext()){
			Object node = it.next();
			if (node instanceof MClass) {
				sortedNodes.add(node);
			}
		}
		it = sortedNodes.iterator();
		while (it.hasNext()) {
			MClass cls = (MClass)(it.next());
			String classname = "";

			classname = cls.name();
			final Set sclasses = new HashSet();
			sclasses.clear();
			sclasses.add(cls);

			final JMenuItem showClasses = new JMenuItem(
							"Show "+classname);
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
