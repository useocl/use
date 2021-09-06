package org.tzi.use.gui.views.selection.objectselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.sys.MObject;

/** 
 * ObjectPathTableModel is derived from TableModel 
 * and defined the Modelstructure of ObjectPathTable
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public class ObjectPathTableModel extends TableModel<MObject> {
	Set<MObject> selectedObjects;
	SelectedObjectPathView fView;
	
	public ObjectPathTableModel( Set<MObject> selectedObjects, SelectedObjectPathView fView) {
		this.selectedObjects = selectedObjects;
		this.fView = fView;
		this.setColumnName("Object", "Path length");
		update();
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		rows = new ArrayList<Row<MObject>>();
		
		if (selectedObjects.size() > 0) {
			TreeSet<MObject> sortedObjects = new TreeSet<MObject>(new Comparator<MObject>() {
				@Override
				public int compare(MObject o1, MObject o2) {
					return o1.name().compareTo(o2.name());
				}
				
			});
			
			sortedObjects.addAll(selectedObjects);
			
			for (MObject mo : sortedObjects) {
				int depth = fView.getDepth(mo);
				String name = mo.name() + " (0-" + depth + ")";
				
				rows.add(new Row<MObject>(name, 1, depth, mo));
			}
		}
		
		fireTableDataChanged();
	}
}