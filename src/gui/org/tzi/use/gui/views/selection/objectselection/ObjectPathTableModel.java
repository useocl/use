package org.tzi.use.gui.views.selection.objectselection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.sys.MObject;

/** 
 * ObjectPathTableModel is derived from TableModel 
 * and defined the Modelstructure of ObjectPathTable
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class ObjectPathTableModel extends TableModel{
	Set selectedObjects;
	SelectedObjectPathView fView;
	
	public ObjectPathTableModel( List fAttributes, List fValues, Set selectedObjects, SelectedObjectPathView fView) {
		super(fAttributes, fValues);
		this.selectedObjects = selectedObjects;
		this.fView = fView;
		this.setColumnName("object name", "path length");
		update();
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {

		if (selectedObjects.size() > 0) {
			fAttributes.clear();
			fValues.clear();
			// add all classe
			SelectionComparator sort = new SelectionComparator();
			TreeSet sortedObjects = new TreeSet(sort);
			sortedObjects.addAll(selectedObjects);
			Iterator it = sortedObjects.iterator();
			int index = 0;
			while (it.hasNext()) {
				MObject mo = (MObject)(it.next());
				int depth = fView.getDepth(mo);
				fAttributes.add(mo.name() + " (0-" + depth + ")");
				fValues.add(new Integer(depth));
			}
		} else {
			fAttributes = new ArrayList();
			fValues = new ArrayList();
		}
		fireTableDataChanged();
	}
}