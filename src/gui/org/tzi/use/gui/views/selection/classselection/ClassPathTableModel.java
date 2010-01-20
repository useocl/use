package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.mm.MClass;

/** 
 * ClassPathTableModel is derived from TableModel 
 * and defined the Modelstructure of ClassPathTable
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public class ClassPathTableModel extends TableModel {
	HashSet selectedClasses;
	SelectedClassPathView fView;
	
	public ClassPathTableModel( List fAttributes, List fValues, HashSet selectedClasses, SelectedClassPathView fView) {
		super(fAttributes, fValues);
		this.selectedClasses = selectedClasses;
		this.fView = fView;
		this.setColumnName("class name", "path length");
		update();
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {

		if (selectedClasses.size() > 0) {
			fAttributes.clear();
			fValues.clear();
			// add all class
			SelectionComparator so = new SelectionComparator();
			TreeSet sortedClasses = new TreeSet(so);
			sortedClasses.addAll(selectedClasses);
			Iterator it = sortedClasses.iterator();

			while (it.hasNext()) {
				MClass mc = (MClass)(it.next());
				int depth = fView.getDepth(mc); 
				fAttributes.add(mc.name() + " (0-" + depth + ")");
				fValues.add(new Integer(depth));
			}
		} else {
			fAttributes = new ArrayList();
			fValues = new ArrayList();
		}
		fireTableDataChanged();
	}

}
