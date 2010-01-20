package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.gui.views.selection.TableModel;

/**  	
 * AssociationPathTableModel is derived from TableModel 
 * and defined the Modelstructure of AssociationPathTable
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public class AssociationPathTableModel extends TableModel {
	SelectedAssociationPathView fView;

	HashSet anames;

	/**
	 * Constructor for AssociationPathTableModel
	 * 
	 * @param anames stores the names of the associations, which are selected by the user.
	 */
	public AssociationPathTableModel(List fAttributes, List fValues,
			HashSet anames, SelectedAssociationPathView fView) {
		super(fAttributes, fValues);
		this.fView = fView;
		this.anames = anames;
		this.setColumnName("association name", "path length");
		update();
	}
    
	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		if (anames.size() > 0) {
			fAttributes.clear();
			fValues.clear();
			// add all class
			SelectionComparator so = new SelectionComparator();
			TreeSet sortedClasses = new TreeSet(so);
			sortedClasses.addAll(anames);
			Iterator it = sortedClasses.iterator();
			while (it.hasNext()) {
				AssociationName aname = (AssociationName) (it.next());

				int depth = fView.getAssociationDepth(aname);
				String one = aname.name() + " (0-" + depth + ")";

				fAttributes.add(one);
				fValues.add(Integer.valueOf(depth));

			}
		} else {
			fAttributes = new ArrayList();
			fValues = new ArrayList();
		}
		fireTableDataChanged();
	}

}