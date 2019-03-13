package org.tzi.use.gui.views.selection;

import java.util.List;

import javax.swing.table.AbstractTableModel;


/**  	
 * This table model handles rows with two columns:
 * <ol>
 * 	<li>a caption as a string value (read only)</li>
 *  <li>an integer value (editable)</li>
 * </ol>
 * For each row the user can specify an item which is represented by this row
 * and a maximum value for the integer column.
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author	 Lars Hamann
 */
@SuppressWarnings("serial")
public abstract class TableModel<T> extends AbstractTableModel {
	
	/**
	 * Nested class for the row data of this table model. 
	 * @author Lars Hamann
	 *
	 * @param <T> Type of the "business" item represented by a row.
	 */
	public static class Row<T> {
		/**
		 * Read only value for the fist column.
		 */
		public String name;
		/**
		 * User editable value for the second column
		 */
		public int value;
		/**
		 * Maximum allowed value for the user editable column
		 */
		public int maxValue;
		/**
		 * The object represented by this row
		 */
		public T item;
		
		public Row(String name, int value, int maxValue, T item) {
			this.name = name;
			this.value = value;
			this.maxValue = maxValue;
			this.item = item;
		}
	}
	
	private String[] columnNames = { "null", "null" };
	
	protected List<Row<T>> rows;

	/**
	 * Constructor for TableModel.
	 */
	public TableModel() {}

	/**
	 * The rows of this table model.
	 * @return
	 */
	public List<Row<T>> getRows() {
		return rows;
	}
	
	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return rows.size();
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public void setColumnName(String captionColumnName, String valueColumnName){
		columnNames[0] = captionColumnName;
		columnNames[1] = valueColumnName;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		if (col == 0)
			return rows.get(row).name;
		else
			return rows.get(row).value;
	}

	/**
	 * Method isCellEditable determines the changeableness of each column. 
	 * In our case the first column is not changeable (return false). 
	 * On the other hand the second column (VALUE) may be entered by the user.
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears on screen.
		if (col < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 1:
			return Integer.class;
		case 0:
		default:
			return String.class;
		}
	}

	/**
	 * Method setValueAt takes the user input. 
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		try {
			int iValue = ((Integer)value).intValue();
			rows.get(row).value = Math.max(0, Math.min(iValue, rows.get(row).maxValue));
		} catch (Exception e) {}
		
		fireTableCellUpdated(row, col);
	}

	
	public abstract void update();
}