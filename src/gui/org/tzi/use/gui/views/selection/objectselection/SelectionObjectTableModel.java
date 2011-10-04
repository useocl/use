package org.tzi.use.gui.views.selection.objectselection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;

/** 
 * This table model stores represents all objects of a given
 * MClass and allows to select or delect it.
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author   Lars Hamann
 */
@SuppressWarnings("serial")
public class SelectionObjectTableModel extends AbstractTableModel {
	public MClass fClass = null;
	
	MSystem fSystem;

	private List<Row> rows = new ArrayList<Row>();
	
	private static class Row {
		public String name;
		public boolean value;
		public MObject item;
		
		public Row(String name, boolean value, MObject item) {
			this.name = name;
			this.value = value;
			this.item = item;
		}	
	}
	
	/**
	 * Constructor for SelectionObjectTableModel.
	 */
	public SelectionObjectTableModel(MSystem system) {
		this.fSystem = system;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "object";
		default:
			return "select";
		}
	}

	/**
	 * Method getColumnClass determine the default renderer/ editor for
	 * each cell. 
	 */
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		default:
			return Boolean.class;
		}
	}

	/**
	 * Method setValueAt takes the user input.
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {
		Boolean bValue = (Boolean)value;
		Row r = rows.get(row);
		r.value = bValue;
		
		fireTableCellUpdated(row, col);
	}

	public void setSelected(MClass mc){
		fClass = mc;
		update();
	}
	
	/**
	 * Method update updates the data of Table. 
	 */
	public void update() {
		rows.clear();
		
		if (fClass != null) {		
			MSystemState state = fSystem.state();
			Set<MObject> objects = state.objectsOfClass(fClass);
			TreeSet<MObject> sortedObjects = new TreeSet<MObject>(new Comparator<MObject>() {
				@Override
				public int compare(MObject o1, MObject o2) {
					return o1.name().compareTo(o2.name());
				}
			});
			sortedObjects.addAll(objects);
			
			for (MObject o : sortedObjects) {
				rows.add(new Row(o.name(), false, o));
			}
		}
		
		fireTableDataChanged();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return rows.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Row r = rows.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return r.name;
		default:
			return r.value;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	/**
	 * Sets all selection values to true
	 */
	public void selectAll() { 
		for (Row r : rows) {
			r.value = true;
		}
		this.fireTableDataChanged();
	}

	/**
	 * Sets all selection values to false
	 */
	public void deselectAll() {
		for (Row r : rows) {
			r.value = false;
		}
		this.fireTableDataChanged();
	}

	/**
	 * @return
	 */
	public Set<MObject> getSelectedObjects() {
		Set<MObject> selected = new HashSet<MObject>();
		for (Row row : rows) {
			if (row.value) {
				selected.add(row.item);
			}
		}
		
		return selected;
	}

}
