package org.tzi.use.gui.views.selection.objectselection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.selection.SelectionComparator;
import org.tzi.use.gui.views.selection.TableModel;

/**  	
 * LinkPathTableModel is derived from TableModel 
 * and defined the Modelstructure of LinkPathTable
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class LinkPathTableModel extends TableModel {
	SelectedLinkPathView fView;
	Set anames; 
	
	/**
	 * Constructor for LinkPathTableModel
	 * 
	 * @param anames stores the names of the associations, which are selected by the user. 
	 */
	public LinkPathTableModel( List fAttributes, List fValues, Set anames, SelectedLinkPathView fView) {
		super(fAttributes, fValues);
		this.fView = fView;
		this.anames = anames;
		this.setColumnName("link name", "path length");
		update();
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {

		if (anames.size() > 0) {
			fAttributes.clear();
			fValues.clear();
			// add all classe
			SelectionComparator sort = new SelectionComparator();
			TreeSet sortedClasses = new TreeSet(sort);
			sortedClasses.addAll(anames);
			Iterator it = sortedClasses.iterator();
			int index = 0;
			while (it.hasNext()) {
				AssociationName aname = (AssociationName)(it.next());
				int depth = fView.getLinkDepth(aname);
				if(depth < 0)
					depth = 0;
				fAttributes.add(aname.name()+ " (0-" + depth + ")");
				fValues.add(Integer.valueOf(depth));
			}
		} else {
			fAttributes = new ArrayList();
			fValues = new ArrayList();
		}
		fireTableDataChanged();
	}

}