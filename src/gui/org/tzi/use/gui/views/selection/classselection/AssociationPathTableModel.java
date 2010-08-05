package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.selection.TableModel;

/**  	
 * AssociationPathTableModel is derived from TableModel 
 * and defined the Modelstructure of AssociationPathTable
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

@SuppressWarnings("serial")
public class AssociationPathTableModel extends TableModel {
	protected SelectedAssociationPathView fView;
	protected Set<AssociationName> anames;

	/**
	 * Constructor for AssociationPathTableModel
	 * 
	 * @param anames stores the names of the associations, which are selected by the user.
	 */
	public AssociationPathTableModel(List<String> fAttributes, List<Object> fValues,
			Set<AssociationName> anames, SelectedAssociationPathView fView) {
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
			TreeSet<AssociationName> sortedAssociationnames = new TreeSet<AssociationName>(new Comparator<AssociationName>() {
				public int compare(AssociationName o1, AssociationName o2) {
					return o1.name().compareTo(o2.name());
				}
			});
			
			sortedAssociationnames.addAll(anames);
			
			for (AssociationName aname : sortedAssociationnames) {
				int depth = fView.getAssociationDepth(aname);
				String one = aname.name() + " (0-" + depth + ")";

				fAttributes.add(one);
				fValues.add(Integer.valueOf(depth));

			}
		} else {
			fAttributes = new ArrayList<String>();
			fValues = new ArrayList<Object>();
		}
		fireTableDataChanged();
	}

}