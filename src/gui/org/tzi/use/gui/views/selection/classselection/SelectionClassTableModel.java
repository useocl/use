package org.tzi.use.gui.views.selection.classselection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.NodeBase;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
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
@SuppressWarnings("serial")
public class SelectionClassTableModel extends TableModel {
	
	private Set<MClass> selectedClasses;
		
	private ClassDiagram classDiagram;
	
    private Map<MClass, ClassNode> fClassToNodeMap;
    
    private Selection<PlaceableNode> fNodeSelection;
    
    
	/**
	 * Constructor for SelectionClassTableModel
	 */
	public SelectionClassTableModel( List<String> fAttributes, List<Object> fValues, Set<MClass> selectedClasses, ClassDiagram classDiagram,
									 Map<MClass, ClassNode> fClassToNodeMap, Selection<PlaceableNode> fNodeSelection) {
		super(fAttributes, fValues);
		this.fClassToNodeMap = fClassToNodeMap;
		this.fNodeSelection = fNodeSelection;
		this.classDiagram = classDiagram;
		this.selectedClasses = selectedClasses;
		this.setColumnName("class name", "select");
		update();
	}
	
	/**
	 * Method getColumnClass determine the default renderer/ editor for
	 * each cell. 
	 */
	public Class<?> getColumnClass(int c) {
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
		
		classDiagram.invalidateContent();
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
		Set<MClass> sortedNodes = getAllNodes();
		Iterator<MClass> it = sortedNodes.iterator();
		
		MClass elem = null;
		boolean foundClass = false;
		
		while(it.hasNext()){
			elem = it.next();
			if(elem.name().equals(fAttributes.get(row))){
				foundClass = true;
				break;
			}
		}
		
		 if ( foundClass ) {
	            NodeBase node = fClassToNodeMap.get( elem );
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
	
	public Set<MClass> getAllNodes(){
		SelectionComparator sort = new SelectionComparator();
		TreeSet<MClass> sortedNodes = new TreeSet<MClass>(sort);
		Iterator<?> it = classDiagram.getGraph().iterator();
		
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof ClassNode) {
				sortedNodes.add(((ClassNode) o).cls());
			}
		}
		
		it = classDiagram.getHiddenNodes().iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof MClass) {
				sortedNodes.add((MClass)o);
			}
		}
		
		return sortedNodes;
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		// initialize table model
		Set<MClass> sortedNodes = getAllNodes();
			
		Iterator<MClass> it = sortedNodes.iterator();
		Set<String> classNames = getClassNames(selectedClasses);
		
		while (it.hasNext()) {
			MClass cs = it.next();
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
	
	private Set<String> getClassNames(Set<MClass> selectedClasses) {
		Set<String> classNames = new HashSet<String>();
		
		Iterator<?> it = selectedClasses.iterator();
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
