package org.tzi.use.gui.views.selection.objectselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.gui.views.selection.TableModel;
import org.tzi.use.uml.sys.MLink;

/**  	
 * LinkPathTableModel is derived from TableModel 
 * and defined the Modelstructure of LinkPathTable
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public class LinkPathTableModel extends TableModel<MLink> {
	SelectedLinkPathView fView;
	
	List<MLink> selectedLinks; 
	
	/**
	 * Constructor for LinkPathTableModel
	 * 
	 * @param anames stores the names of the associations, which are selected by the user. 
	 */
	public LinkPathTableModel( Set<MLink> selectedLinks, SelectedLinkPathView fView) {
		this.fView = fView;
		this.selectedLinks = new ArrayList<MLink>(selectedLinks);
		this.setColumnName("link", "path length");
		update();
	}

	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		rows = new ArrayList<TableModel.Row<MLink>>();
		
		if (selectedLinks.size() > 0) {
			TreeSet<MLink> sortedLinks = new TreeSet<MLink>(new Comparator<MLink>() {
				@Override
				public int compare(MLink o1, MLink o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
			
			sortedLinks.addAll(selectedLinks);
			
			for (MLink link : sortedLinks) {
				int depth = fView.getLinkDepth(link);
				if(depth < 0)
					depth = 0;
				String name = link.toString() + " (0-" + depth + ")";
				rows.add(new Row<MLink>(name, depth, depth, link));
			}
		}
		
		fireTableDataChanged();
	}

}