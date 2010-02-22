package org.tzi.use.gui.views.selection.objectselection;

import java.util.ArrayList;
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
@SuppressWarnings("serial")
public class LinkPathTableModel extends TableModel {
	SelectedLinkPathView fView;
	Set<AssociationName> anames; 
	
	/**
	 * Constructor for LinkPathTableModel
	 * 
	 * @param anames stores the names of the associations, which are selected by the user. 
	 */
	public LinkPathTableModel( List<String> fAttributes, List<Object> fValues, Set<AssociationName> anames, SelectedLinkPathView fView) {
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
			TreeSet<AssociationName> sortedAssociations = new TreeSet<AssociationName>(sort);
			sortedAssociations.addAll(anames);
			
			for (AssociationName aname : sortedAssociations) {
				int depth = fView.getLinkDepth(aname);
				if(depth < 0)
					depth = 0;
				fAttributes.add(aname.name()+ " (0-" + depth + ")");
				fValues.add(Integer.valueOf(depth));
			}
		} else {
			fAttributes = new ArrayList<String>();
			fValues = new ArrayList<Object>();
		}
		fireTableDataChanged();
	}

}