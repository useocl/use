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

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.util.ExtendedJTable;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.util.StringUtil;

/**
 * This dialog asks the user for qualifier values
 * for a new link.
 * @author Lars Hamann
 *
 */
public class QualifierInputView extends JDialog {

	private static final long serialVersionUID = -6361281949862418261L;
	
	/**
	 * The system to execute the insert link statement on.
	 */
	private MSystem system;
	
	/**
	 * The association to insert the link into
	 */
	private MAssociation association;
	
	/**
	 * The objects participating in the link
	 */
	private MObject[] participants;
	
	/**
	 * A table model for each end with qualifier	
	 */
	private TableModel[] tableModels;
	
	private JTable[] tables;
	
	public QualifierInputView(Frame owner, MSystem system, MAssociation association, MObject[] participants) {
		super(owner, "Enter qualifier values");
		this.system = system;
		this.association = association;
		this.participants = participants;
		initGui();
	}

	private void initGui() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.tableModels = new TableModel[association.associationEnds().size()];
		this.tables = new JTable[association.associationEnds().size()];
		int index = 0; 
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
		
		for (MAssociationEnd end : this.association.associationEnds()) {
			if (end.hasQualifiers()) {
				String[] qualifierNames = new String[end.getQualifiers().size()];
				int index2 = 0;
				for (VarDecl qualifier : end.getQualifiers()) {
					qualifierNames[index2++] = qualifier.name();
				}
				
				this.tableModels[index] = new TableModel(qualifierNames);
				
				entryPanel.add(new JLabel("Qualifier values for association end " + StringUtil.inQuotes(end.name())));
				JTable table = new ExtendedJTable(this.tableModels[index]);
		        table.setPreferredScrollableViewportSize(new Dimension(250, 70));
		        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		        entryPanel.add(new JScrollPane(table));
		        this.tables[index] = table;
			} else {
				this.tableModels[index] = null;
				this.tables[index] = null;
			}
			++index;
		}

        // create buttons
        JButton btnApply = new JButton("Insert");
        btnApply.setMnemonic('I');
        btnApply.setVerifyInputWhenFocusTarget(true);
        btnApply.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    apply();
                }
            });
        
        JButton btnReset = new JButton("Reset");
        btnReset.setMnemonic('R');
        btnReset.setVerifyInputWhenFocusTarget(false);
        btnReset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reset();
                }
            });

        // layout the buttons centered from left to right
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(btnApply);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(btnReset);
        buttonPane.add(Box.createHorizontalGlue());
    
        // layout panel
        add(entryPanel, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
        setSize(new Dimension(300, 200));
	}
	
	private void apply() {
		
		for (JTable table : this.tables) {
			if (table != null && table.isEditing())
				table.getCellEditor().stopCellEditing();
		}
		
		List<List<Value>> qualifier = new ArrayList<>();
		for (int i = 0; i < this.tableModels.length; ++i) {
			TableModel model = this.tableModels[i];
			if (model == null) {
				qualifier.add(Collections.<Value>emptyList());
			} else {
				List<Value> endValues = getEndValues(model);
				
				if (endValues == null) return;
				
				qualifier.add(endValues);
			}
		}
		
		try {
			system.execute(
					new MLinkInsertionStatement(association, participants, qualifier));
			
		} catch (MSystemException e) {
			JOptionPane.showMessageDialog(
					getParent(), 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}

		this.dispose();
	}

	/**
	 * @param model
	 */
	protected List<Value> getEndValues(TableModel model) {
		List<Value> endValues = new ArrayList<Value>();
		for (int i2 = 0; i2 < model.getValues().length; ++i2) {
			String value = model.getValues()[i2];
			
			if (value == null) {
				JOptionPane.showMessageDialog(
						this, 
						"Please provide a value for the qualifier " + model.getQualifierNames()[i2], 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
			StringWriter errorOutput = new StringWriter();
			Expression valueAsExpression = 
				OCLCompiler.compileExpression(
						system.model(),
						system.state(),
						value, 
						"<input>", 
						new PrintWriter(errorOutput, true), 
						system.varBindings());
			
			if (valueAsExpression == null) {
				JOptionPane.showMessageDialog(
						this, 
						errorOutput, 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
			try {
		        // evaluate it with current system state
		        Evaluator evaluator = new Evaluator(true);
		        Value val = evaluator.eval(valueAsExpression, system.state(), system.varBindings());
		        endValues.add(val);
		    } catch (MultiplicityViolationException e) {
		    	JOptionPane.showMessageDialog(
						this, 
						"Could not evaluate. " + e.getMessage(), 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
		    	return null;
		    }
		}
		
		return endValues;
	}
	
	private void reset() {
		for (int i = 0; i < this.tableModels.length; ++i) {
			if (this.tableModels[i] != null) {
				this.tableModels[i].reset();
			}
		}
	}
	
	/**
     * The table model.
     */
    class TableModel extends AbstractTableModel {    
        /**
		 * 
		 */
		private static final long serialVersionUID = 5021692836889351559L;

		final String[] columnNames = { "Qualifier", "Value" };

        private String[] qualifierNames;
        
        private String[] qualifierValues;
        
        TableModel(String[] qualifierNames) {
        	this.qualifierNames = qualifierNames;
        	this.qualifierValues = new String[qualifierNames.length];
        }

        /**
		 * @return
		 */
		public String[] getQualifierNames() {
			return this.qualifierNames;
		}

		/**
		 * 
		 */
		public void reset() {
			for (int i = 0; i < this.qualifierValues.length; ++i) {
				qualifierValues[i] = null;
			}
			this.fireTableDataChanged();
		}

		public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getColumnCount() { 
            return 2; 
        }
        
        public int getRowCount() { 
            return qualifierNames.length;
        }
        
        public Object getValueAt(int row, int col) { 
            if (col == 0 )
                return qualifierNames[row];
            else
                return qualifierValues[row];
        }
        public boolean isCellEditable(int row, int col) {
            return col == 1;
        }

        public void setValueAt(Object value, int row, int col) {
            qualifierValues[row] = value.toString();
        }
        
        public String[] getValues() {
        	return this.qualifierValues;
        }
    }
}
