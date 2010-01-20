package org.tzi.use.gui.views.selection.classselection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.event.DiagramMouseHandling;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.util.Log;

/** 
 * SelectionClassTableModel is derived from TableModel. Data of table are 
 * stored in a specific TableModel, i.e. in the SelectionClassTableModel.
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class SelectionClassTableModel extends TableModel {
	
	private HashSet selectedClasses;
	
	private DiagramMouseHandling mouseHandling;
	
	private ClassDiagram classDiagram;
	
    private Map fClassToNodeMap; // (MClass -> ClassNode)
    private Selection fNodeSelection;
    
    
	/**
	 * Constructor for SelectionClassTableModel
	 */
	public SelectionClassTableModel( List fAttributes, List fValues, HashSet selectedClasses, ClassDiagram classDiagram, DiagramMouseHandling mouseHandling,
			Map fClassToNodeMap, Selection fNodeSelection) {
		super(fAttributes, fValues);
		this.fClassToNodeMap = fClassToNodeMap;
		this.fNodeSelection = fNodeSelection;
		this.classDiagram = classDiagram;
		this.selectedClasses = selectedClasses;
		this.mouseHandling = mouseHandling;
		this.setColumnName("class name", "select");
		update();
	}
	
	/**
	 * Method getColumnClass determine the default renderer/ editor for
	 * each cell. 
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
	
	/**
	 * Method setValueAt takes the user input.
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {
		Log.trace(this, "row = " + row + ", col = " + col + ", value = "
				+ value);
		if (value.toString().equals("true")) {
			fValues.set(row, Boolean.valueOf(true));
			changeSelection(row,true);
		} else {
			fValues.set(row, Boolean.valueOf(false));
			changeSelection(row,false);
		}
		
		classDiagram.repaint();
		fireTableCellUpdated(row, col);
	}
	
	public void setValueAtForGUI(Object value, int row, int col) {
		Log.trace(this, "row = " + row + ", col = " + col + ", value = "
				+ value);
		if (value.toString().equals("true")) {
			fValues.set(row, Boolean.valueOf(true));
		} else {
			fValues.set(row, Boolean.valueOf(false));
		}
		
		fireTableCellUpdated(row, col);
	}
	
	public void changeSelection(int row, boolean isAdd){
		TreeSet sortedNodes = getAllNodes();
		Iterator it = sortedNodes.iterator();
		MClass elem = null;
		boolean have = false;
		while(it.hasNext()){
			elem = (MClass)(it.next());
			if(elem.name().equals(fAttributes.get(row))){
				have = true;
				break;
			}
		}
		
		 if ( have && elem instanceof MClass ) {
	            NodeBase node = (NodeBase) fClassToNodeMap.get( (MClass) elem );
	            if ( node != null ) {
	                if ( elem instanceof MAssociationClass ) {
	                    if ( fNodeSelection.isSelected( node ) ){
	                    	if(!isAdd)
	                    		fNodeSelection.remove( node );
	                    } else {
	                    	if(isAdd)
	                    		fNodeSelection.add( node );
	                    }  
	                } else {
	                    if ( fNodeSelection.isSelected( node ) ) {
	                    	if(!isAdd)
	                    		fNodeSelection.remove( node );
	                    } else {
	                    	if(isAdd)
	                    		fNodeSelection.add( node );
	                    }
	                }
	            }
	        }
	}
	
	public TreeSet getAllNodes(){
		SelectionComparator sort = new SelectionComparator();
		TreeSet sortedNodes = new TreeSet(sort);
		Iterator it = ClassDiagram.ffGraph.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof ClassNode) {
				sortedNodes.add(((ClassNode) o).cls());
			}
		}
		it = ClassDiagram.ffHiddenNodes.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof MClass) {
				sortedNodes.add(o);
			}
		}
		
		return sortedNodes;
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		// initialize table model
		TreeSet sortedNodes = getAllNodes();
			
		Iterator it = sortedNodes.iterator();
		HashSet classNames = getClassNames(selectedClasses);
		while (it.hasNext()) {
			MClass cs = (MClass) (it.next());
			fAttributes.add(cs.name());
			if(classNames.contains(cs.name()))
				fValues.add(Boolean.valueOf(true));
			else
				fValues.add(Boolean.valueOf(false));
		}

		fireTableDataChanged();
	}
	
	public void refresh(PlaceableNode pickedObjectNode){
		for(int i = 0; i < fAttributes.size(); i++ ){
			String name = (String)(fAttributes.get(i));
			if(pickedObjectNode.name().equals(name)){
				setValueAtForGUI("true", i, 1);
			}
			else{
				setValueAtForGUI("false", i, 1);
			}
		}
	}
	
	public void refreshAll(){
		for(int i = 0; i < fValues.size(); i++ ){
			boolean value = ((Boolean)(fValues.get(i))).booleanValue();
			if(value){
				changeSelection(i,true);
			}
		}
	}
	
	public void addSelected(PlaceableNode pickedObjectNode){
		for(int i = 0; i < fAttributes.size(); i++ ){
			String name = (String)(fAttributes.get(i));
			if(pickedObjectNode.name().equals(name)){
				setValueAtForGUI("true", i, 1);
				break;
			}
		}
	}
	
	public void clearSelection(){
		for(int i = 0; i < fAttributes.size(); i++ ){
			setValueAtForGUI("false", i, 1);
			}
	}
	
	private HashSet getClassNames(HashSet selectedClasses){
		HashSet classNames = new HashSet();
		
		Iterator it = selectedClasses.iterator();
		while(it.hasNext()){
			Object o = it.next();
			if ( o instanceof MClass ) {
				classNames.add(((MClass)o).name());
			} else if ( o instanceof EnumType ) {
				classNames.add(((EnumType)o).name());
			}
		}
		return classNames;
	}
}
