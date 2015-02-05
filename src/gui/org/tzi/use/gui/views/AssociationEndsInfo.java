/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id$

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.sys.MSystem;

/**
 * View for informations about associations, e.g., subsetting, redefined etc.
 * @author Lars Hamann
 *
 */
@SuppressWarnings("serial")
public class AssociationEndsInfo extends JPanel implements View {
	
	private MSystem useSystem;
	
	private JTable table;
	
	private TableModel model;
	
	JComboBox<MAssociation> cboAssociations;
	
	public AssociationEndsInfo(MainWindow parent, MSystem system) {
        super(new BorderLayout());
        this.useSystem = system;
        initGui();
	}

	private void initGui() {
		JPanel topPanel = new JPanel();
		
		JLabel label = new JLabel("Association");
		topPanel.add(label, BorderLayout.WEST);
		
		Vector<MAssociation> associations = new Vector<MAssociation>();
		
		for (MAssociation a : useSystem.model().associations()) {
			if (a.associationEnds().size() == 2 && (
				a.getSubsettedBy().size() > 0 ||
				a.getRedefinedBy().size() > 0)) {
				
				associations.add(a);
			}
		}
			
		Collections.sort(associations, new Comparator<MAssociation>() {
			@Override
			public int compare(MAssociation o1, MAssociation o2) {
				return o1.name().compareTo(o2.name());
			}
		});
		
		cboAssociations = new JComboBox<MAssociation>(associations);
		cboAssociations.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					showAssociationInfo();
				}
			}});
		
		topPanel.add(cboAssociations, BorderLayout.EAST);
		
		model = new TableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(250, 70));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tablePane = new JScrollPane(table);
        
        String legendString = "<html>" +
        		              "Legend: " +
        		              "<font color='green'>green:</font> end of selected association; " +
        		              "<font color='blue'>blue:</font> end is directly redefined/subsetted; " +
        		              "<font color='red'>red:</font> end redefines/subsets implicitly another end</html>";
        JPanel legendPanel = new JPanel();
        legendPanel.add(new JLabel(legendString), BorderLayout.WEST);
        
        // layout panel
        add(topPanel, BorderLayout.NORTH);
        add(tablePane, BorderLayout.CENTER);
        add(legendPanel, BorderLayout.SOUTH);
        
        setPreferedWidth();
	}

	private void setPreferedWidth() {
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
        	table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
        }
	}
	
	protected MAssociation getSelectedAssociation() {
		return (MAssociation)this.cboAssociations.getSelectedItem();
	}
	
	@Override
	public void detachModel() {
		// We do nothing because USE closes all windows
		// after new model is loaded
	}
	
	protected void showAssociationInfo() {
		this.model.initEntries();
	}
	
	private static final int[] columnWidth = new int[] {
		153,
		104,
		31,
		40,
		40,
		167,
		167,
		228,
		167,
		167,
		40,
		40,
		31,
		104,
		153
	};
	
	private static final String[] columnNames = new String[] {
		"Rolename",
		"Type",
		"Mul.",
		"Union",
		"Derived",
		"Subsets",
		"Redefines",
		"Association",
		"Redefines",
		"Subsets",
		"Derived",
		"Union",
		"Mul.",
		"Type",
		"Rolename"
	};
	
	/**
     * The table model.
     */
	class TableModel extends AbstractTableModel {

    	private class RowEntry {
    		public RowEntry(MAssociation a) {
    			association = a;
    		}
    		
    		public MAssociation association;
    		public MAssociationEnd left;
    		public String leftRolename;
    		public String leftSubsets = "";
    		public String leftRedefines = "";
    		
    		public MAssociationEnd right;
    		public String rightRolename;
    		public String rightSubsets = "";
    		public String rightRedefines = "";
    	}
    	
    	private List<RowEntry> entries;
    	
    	public TableModel() {
    		initEntries();
    	}

		public void initEntries() {
			entries = new ArrayList<RowEntry>();
    		
    		// run through the subsetted associations
    		MAssociation assoc = getSelectedAssociation();
    		
    		if (assoc == null) return;
    		
    		// Buildup closures for the left and
    		Set<MAssociationEnd> leftEndSubsetsClosure = assoc.associationEnds().get(0).getSubsettingEndsClosure();
    		leftEndSubsetsClosure.add(assoc.associationEnds().get(0));
    		Set<MAssociationEnd> leftEndRedefinesClosure = assoc.associationEnds().get(0).getRedefiningEndsClosure();
    		leftEndRedefinesClosure.add(assoc.associationEnds().get(0));
    		
    		// Build up closures for the right end
    		Set<MAssociationEnd> rightEndSubsetsClosure = assoc.associationEnds().get(1).getSubsettingEndsClosure();
    		rightEndSubsetsClosure.add(assoc.associationEnds().get(1));
    		Set<MAssociationEnd> rightEndRedefinesClosure = assoc.associationEnds().get(1).getRedefiningEndsClosure();
    		rightEndRedefinesClosure.add(assoc.associationEnds().get(1));
    		
    		// Add info about the selected association
    		RowEntry selected = new RowEntry(assoc);
    		selected.left = assoc.associationEnds().get(0);
    		selected.leftRolename = "<html><font color='green'>" + selected.left.name() + "</font></html>";
    		selected.right = assoc.associationEnds().get(1);
    		selected.rightRolename = "<html><font color='green'>" + selected.right.name() + "</font></html>";
    		entries.add(selected);
    		
    		// We want to keep the order of the elements to get a subsetting / redefining hierarchy
    		LinkedHashSet<MAssociation> todo = new LinkedHashSet<MAssociation>(assoc.getSubsettedBy());
    		todo.addAll(assoc.getRedefinedBy());
    		
    		while (!todo.isEmpty()) {
    			MAssociation childAsso = todo.iterator().next();
    			todo.remove(childAsso);
    			RowEntry e = new RowEntry(childAsso);
    			
    			Set<MAssociationEnd> leftSubsetsSet = new HashSet<MAssociationEnd>();
    			Set<MAssociationEnd> leftRedefinedSet = new HashSet<MAssociationEnd>();
    			Set<MAssociationEnd> rightSubsetsSet = new HashSet<MAssociationEnd>();
    			Set<MAssociationEnd> rightRedefinedSet = new HashSet<MAssociationEnd>();
    			
    			// Its not guaranteed that both subset relations are given (one is guaranteed),
    			// therefore we check for both sides
    			if (leftEndSubsetsClosure.contains(childAsso.associationEnds().get(0)) ||
    				rightEndSubsetsClosure.contains(childAsso.associationEnds().get(1)) ||
    				leftEndRedefinesClosure.contains(childAsso.associationEnds().get(0)) ||
    				rightEndRedefinesClosure.contains(childAsso.associationEnds().get(1))) {
    				e.left = childAsso.associationEnds().get(0);
    				e.right = childAsso.associationEnds().get(1);
    			} else {
    				e.left = childAsso.associationEnds().get(1);
    				e.right = childAsso.associationEnds().get(0);
    			}
    			
    			if (e.left.getSubsettingEnds().size() > 0 || e.left.getRedefiningEnds().size() > 0) {
    				e.leftRolename = "<html><font color='blue'>" + e.left.name() + "</font></html>";
    			} else {
    				e.leftRolename = e.left.name();
    			}
    			
    			if (e.right.getSubsettingEnds().size() > 0 || e.right.getRedefiningEnds().size() > 0) {
    				e.rightRolename = "<html><font color='blue'>" + e.right.name() + "</font></html>";
    			} else {
    				e.rightRolename = e.right.name();
    			}
    			    			
    			for (MAssociationEnd end : e.left.getSubsettedEnds()) {
    				if (leftEndSubsetsClosure.contains(end)) {
    					if (e.leftSubsets.length() > 0) {
    						e.leftSubsets += ", ";
    					}
    					
    					e.leftSubsets += end.name();
    					leftSubsetsSet.add(end);
    				}
    			}
    			
    			for (MAssociationEnd end : e.right.getSubsettedEnds()) {
    				if (rightEndSubsetsClosure.contains(end)) {
    					if (e.rightSubsets.length() > 0) {
    						e.rightSubsets += ", ";
    					}
    					
    					e.rightSubsets += end.name();
    					rightSubsetsSet.add(end);
    				}
    			}
    			
    			for (MAssociationEnd end : e.left.getRedefinedEnds()) {
    				if (leftEndRedefinesClosure.contains(end)) {
    					if (e.leftRedefines.length() > 0) {
    						e.leftRedefines += ", ";
    					}
    					
    					e.leftRedefines += end.name();
    					leftRedefinedSet.add(end);
    				}
    			}
    			
    			for (MAssociationEnd end : e.right.getRedefinedEnds()) {
    				if (rightEndRedefinesClosure.contains(end)) {
    					if (e.rightRedefines.length() > 0) {
    						e.rightRedefines += ", ";
    					}
    					
    					e.rightRedefines += end.name();
    					rightRedefinedSet.add(end);
    				}
    			}
    			
    			if (leftSubsetsSet.size() != rightSubsetsSet.size()) {
    				for (MAssociationEnd end : leftSubsetsSet) {
    					MAssociationEnd otherEnd = end.getAllOtherAssociationEnds().get(0);
    					if (!rightSubsetsSet.contains(otherEnd)) {
    						if (!e.rightSubsets.isEmpty())
    							e.rightSubsets += ", ";
    						
    						e.rightSubsets += "<font color='red'>" + otherEnd.name() + "</font>";
    					}
    				}
    				
    				for (MAssociationEnd end : rightSubsetsSet) {
    					MAssociationEnd otherEnd = end.getAllOtherAssociationEnds().get(0);
    					if (!leftSubsetsSet.contains(otherEnd)) {
    						if (!e.leftSubsets.isEmpty())
    							e.leftSubsets += ", ";
    						
    						e.leftSubsets += "<font color='red'>" + otherEnd.name() + "</font>";
    					}
    				}
    				
    				e.leftSubsets = "<html>" + e.leftSubsets + "</html>";
    				e.rightSubsets = "<html>" + e.rightSubsets + "</html>";
    			}
    			
    			if (leftRedefinedSet.size() != rightRedefinedSet.size()) {
    				for (MAssociationEnd end : leftRedefinedSet) {
    					MAssociationEnd otherEnd = end.getAllOtherAssociationEnds().get(0);
    					if (!rightRedefinedSet.contains(otherEnd)) {
    						if (!e.rightRedefines.isEmpty())
    							e.rightRedefines += ", ";
    						
    						e.rightRedefines += "<font color='red'>" + otherEnd.name() + "</font>";
    					}
    				}
    				
    				for (MAssociationEnd end : rightRedefinedSet) {
    					MAssociationEnd otherEnd = end.getAllOtherAssociationEnds().get(0);
    					if (!leftRedefinedSet.contains(otherEnd)) {
    						if (!e.leftRedefines.isEmpty())
    							e.leftRedefines += ", ";
    						
    						e.leftRedefines += "<font color='red'>" + otherEnd.name() + "</font>";
    					}
    				}
    				
    				e.leftRedefines = "<html>" + e.leftRedefines + "</html>";
    				e.rightRedefines = "<html>" + e.rightRedefines + "</html>";
    			}
    			
    			entries.add(e);
    			todo.addAll(childAsso.getSubsettedBy());
    		}
    		
    		this.fireTableDataChanged();
		}
    	
		@Override
		public int getColumnCount() {
			return 15;
		}

		@Override
		public int getRowCount() {
			return entries.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			RowEntry entry = entries.get(rowIndex);
						
			switch (columnIndex) {
			// rolename at end 1
			case 0:
				return entry.leftRolename;

			// Type at end 1
			case 1:
				return entry.left.cls().name();
			
			// Multiplicity at end 1
			case 2:
				return entry.left.multiplicity().toString();
			
			case 3:
				return entry.left.isUnion();
			
			case 4:
				return entry.left.isDerived();
				
			// info about subsets constraints at end 1
			case 5:
				return entry.leftSubsets;
				
			case 6:
				return entry.leftRedefines;
			
			case 7:
				return entry.association.name();
			
			case 8: 
				return entry.rightRedefines;

			case 9:
				return entry.rightSubsets;
			
			case 10:
				return entry.right.isDerived();
			
			case 11:
				return entry.right.isUnion();
				
			// Multiplicity at end 2
			case 12:
				return entry.right.multiplicity().toString();
				
			// Type at end 2
			case 13:
				return entry.right.cls().name();
				
			// rolename at end 2				
			case 14:
				return entry.rightRolename;
			}
			
			return null;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 10 || columnIndex == 11 || columnIndex == 3 || columnIndex == 4)
				return Boolean.class;
			else
				return String.class;
		}
    }
}
