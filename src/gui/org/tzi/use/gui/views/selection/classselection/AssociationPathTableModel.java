package org.tzi.use.gui.views.selection.classselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.mm.MAssociation;

/**  	
 * Table model for associations and their path length (reachable classes).
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author	 Lars Hamann
 */
@SuppressWarnings("serial")
public class AssociationPathTableModel extends TableModel<MAssociation> {

	protected Set<MAssociation> selectedAssociations;

	/**
	 * Constructor for AssociationPathTableModel
	 * 
	 * @param selectedAssociations The selected associations.
	 */
	public AssociationPathTableModel(Set<MAssociation> selectedAssociations) {
		this.selectedAssociations = selectedAssociations;
		this.setColumnName("association", "path length");
		update();
	}
    
	/**
	 * Initializes the rows to an initial state
	 */
	@Override
	public void update() {
		rows = new ArrayList<Row<MAssociation>>();
		
		if (selectedAssociations.size() > 0) {
			// add all class
			TreeSet<MAssociation> sortedAssociations = new TreeSet<MAssociation>(new Comparator<MAssociation>() {
				@Override
				public int compare(MAssociation o1, MAssociation o2) {
					return o1.name().compareTo(o2.name());
				}
			});
			
			sortedAssociations.addAll(selectedAssociations);
			
			for (MAssociation assoc : sortedAssociations) {
				int depth = SelectedAssociationPathView.getAssociationDepth(assoc);
				String name = assoc.name() + " (0-" + depth + ")";
				Row<MAssociation> row = new Row<MAssociation>(name, depth, depth, assoc);
				rows.add(row);
			}
		}
		
		fireTableDataChanged();
	}

}