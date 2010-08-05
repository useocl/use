package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.mm.MClass;

/** 
 * ClassPathTableModel is derived from TableModel 
 * and defined the Modelstructure of ClassPathTable
 * 
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

@SuppressWarnings("serial")
public class ClassPathTableModel extends TableModel {
	Set<MClass> selectedClasses;
	SelectedClassPathView fView;
	
	public ClassPathTableModel( List<String> fAttributes, List<Object> fValues, Set<MClass> selectedClasses, SelectedClassPathView fView) {
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
			TreeSet<MClass> sortedClasses = new TreeSet<MClass>(new Comparator<MClass>(){
				public int compare(MClass o1, MClass o2) {
					return o1.name().compareTo(o2.name());
				}});
			
			sortedClasses.addAll(selectedClasses);

			for (MClass mc : sortedClasses) {
				int depth = fView.getDepth(mc); 
				fAttributes.add(mc.name() + " (0-" + depth + ")");
				fValues.add(new Integer(depth));
			}
		} else {
			fAttributes = new ArrayList<String>();
			fValues = new ArrayList<Object>();
		}
		
		fireTableDataChanged();
	}

}
