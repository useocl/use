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

package org.tzi.use.gui.util;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 * A JTable that allows for Excel-like editing of fields. Writing while a cell
 * is selected will override the contents instead of adding to the end of the
 * previous text. Also double clicking a cell to start editing will select the
 * whole text for easy overriding if desired.
 * 
 * @author Frank Hilken
 * @author (Modified for Java 9 by Andreas Kaestner)
 */
public class ExtendedJTable extends JTable {

	private static final long serialVersionUID = 1L;

	/**
	 * @see JTable#JTable()
	 */
	public ExtendedJTable() {
		super();
	}

	/**
	 * @see JTable#JTable(TableModel)
	 */
	public ExtendedJTable(TableModel dm) {
		super(dm);
	}

	/**
	 * @see JTable#JTable(TableModel, TableColumnModel)
	 */
	public ExtendedJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	/**
	 * @see JTable#JTable(int, int)
	 */
	public ExtendedJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
	}

	/**
	 * @see JTable#JTable(Vector, Vector)
	 */
	public ExtendedJTable(Vector<? extends Vector<?>> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * @see JTable#JTable(Object[][], Object[])
	 */
	public ExtendedJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * @see JTable#JTable(TableModel, TableColumnModel, ListSelectionModel)
	 */
	public ExtendedJTable(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
	}
	
	@Override
	public boolean editCellAt(int row, int column, EventObject e){
        boolean result = super.editCellAt(row, column, e);
        if(!result){
        	return false;
        }
        final Component editor = getEditorComponent();
        if (editor == null || !(editor instanceof JTextComponent)) {
            return result;
        }
        if (e instanceof KeyEvent) {
            ((JTextComponent) editor).selectAll();
        }
        else if(e instanceof MouseEvent){
        	// must be run later; otherwise the click to edit the field immediately deselects the text
        	SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					((JTextComponent) editor).selectAll();
				}
			});
        }
        return result;
    }
	
}
