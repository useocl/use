package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.Comparator;
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
public class ClassPathTableModel extends TableModel<MClass> {
	Set<MClass> selectedClasses;
	
	public ClassPathTableModel( Set<MClass> selectedClasses) {
		this.selectedClasses = selectedClasses;
		this.setColumnName("class", "path length");
		update();
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		rows = new ArrayList<Row<MClass>>();
		
		if (selectedClasses.size() > 0) {
			// add all class
			TreeSet<MClass> sortedClasses = new TreeSet<MClass>(new Comparator<MClass>(){
				public int compare(MClass o1, MClass o2) {
					return o1.name().compareTo(o2.name());
				}});
			
			sortedClasses.addAll(selectedClasses);

			for (MClass mc : sortedClasses) {
				int depth = SelectedClassPathView.getDepth(mc);
				String name = mc.name() + " (0-" + depth + ")";
				
				rows.add(new Row<MClass>(name, depth, depth, mc));
			}
		}
		
		fireTableDataChanged();
	}

}
