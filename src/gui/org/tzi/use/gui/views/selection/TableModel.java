package org.tzi.use.gui.views.selection;

import java.util.List;

import javax.swing.table.AbstractTableModel;


/**  	
 * TableModel is the superclass of the other six classes in the class and object diagram 
 * and defined the Modelstructure of Table
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

@SuppressWarnings("serial")
public abstract class TableModel extends AbstractTableModel {
	private String[] columnNames = { "null", "null" };
	public List<String> fAttributes;
	public List<Object> fValues;

	/**
	 * Constructor for TableModel.
	 * 
	 * @param The type of the fAttributes is a list and contains all values of the first column, 
	 * for example all values of class name.
	 * @param fValues contains all values of the second column.
	 */
	public TableModel( List<String> fAttributes, List<Object> fValues) {
		this.fAttributes = fAttributes;
		this.fValues = fValues;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return fAttributes.size();
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public void setColumnName(String name1, String name2){
		columnNames[0] = name1;
		columnNames[1] = name2;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		if (col == 0)
			return fAttributes.get(row);
		else
			return fValues.get(row);
	}

	/**
	 * Method isCellEditable determines the changeableness of each column. 
	 * In our case the first column is not changeable (return false). 
	 * On the other hand the second column (VALUE) may be entered by the user.
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears on screen.
		if (col < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Method setValueAt takes the user input. 
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {

		try {
			int depth = Integer.parseInt(value.toString());
			String maxstring = fAttributes.get(row).toString();

			int maxdepth = Integer.parseInt(maxstring.substring(maxstring
					.indexOf("-") + 1, maxstring.indexOf(")")));
			if (depth <= maxdepth && depth >= 0)
				fValues.set(row, new Integer(depth));
		} catch (Exception e) {
		}
		fireTableCellUpdated(row, col);
	}

	
	public abstract void update();
}