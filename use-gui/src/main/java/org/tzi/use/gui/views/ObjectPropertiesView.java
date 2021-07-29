/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.util.ExtendedJTable;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

/** 
 * A view for showing and changing object properties (attributes).
 *  
 * @author  Mark Richters
 */
@SuppressWarnings("serial")
public class ObjectPropertiesView extends JPanel implements View {
    private static final String NO_OBJECTS_AVAILABLE = "(No objects available.)";

    private MainWindow fMainWindow;
    private MSystem fSystem;
    private MObject fObject;

    private JComboBox<String> fObjectComboBox;
    private JTable fTable;
    private JScrollPane fTablePane;
    private JButton fBtnApply;
    private JButton fBtnReset;
    private TableModel fTableModel;
    private ObjectComboBoxActionListener fObjectComboBoxActionListener;

    private List<MAttribute> fAttributes;
    private String[] fValues;
    private Map<MAttribute, Value> fAttributeValueMap;

    /**
     * The table model.
     */
    class TableModel extends AbstractTableModel implements SortChangeListener {
        final String[] columnNames = { "Attribute", "Value" };

        TableModel() {
            ModelBrowserSorting.getInstance().addSortChangeListener( this );
            update();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getColumnCount() { 
            return 2; 
        }
        public int getRowCount() { 
            return fAttributes.size();
        }
        public Object getValueAt(int row, int col) { 
            if (col == 0 )
                return fAttributes.get(row);
            else
                return fValues[row];
        }
        public boolean isCellEditable(int row, int col) {
            return col == 1 && !fAttributes.get(row).isDerived();
        }

        public void setValueAt(Object value, int row, int col) {
            fValues[row] = value.toString();
            fireTableCellUpdated(row, col);
        }

        private void update() {
            // initialize table model
            if ( haveObject() ) {
                MObjectState objState = fObject.state(fSystem.state());
                fAttributeValueMap = objState.attributeValueMap();
                                
                Collection<MAttribute> attributes = ModelBrowserSorting.getInstance().sortAttributes( fAttributeValueMap.keySet() );
                
                attributes = Collections2.filter(attributes, new Predicate<MAttribute>() {
					@Override
					public boolean apply(MAttribute input) {
						return !input.isDerived();
					}
				});
                
                fAttributes = Lists.newArrayList(attributes);
                fValues = new String[fAttributes.size()];
                for (int i = 0; i < fValues.length; i++) {
                    fValues[i] = fAttributeValueMap.get(fAttributes.get(i)).toString();
                }
            } else {
                fAttributes = Collections.emptyList();
                fValues = new String[0];
            }
            fireTableDataChanged();
        }
        
        /**
         * After the occurrence of an event the attribute list is updated.
         */
        public void stateChanged( SortChangeEvent e ) {
            fAttributes = ModelBrowserSorting.getInstance()
                                          .sortAttributes( fAttributes );
            update();
        }
    }

    class ObjectComboBoxActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String objName = (String)fObjectComboBox.getSelectedItem();
            
            if (!NO_OBJECTS_AVAILABLE.equals(objName))
                selectObject(objName);
        }
    }

    public ObjectPropertiesView(MainWindow parent, MSystem system) {
        super(new BorderLayout());
        fMainWindow = parent;
        fSystem = system;
        fSystem.registerRequiresAllDerivedValues();
        fSystem.getEventBus().register(this);

        // create combo box with available objects
        fObjectComboBox = new JComboBox<String>();
        fObjectComboBoxActionListener = new ObjectComboBoxActionListener();

        // create table of attribute/value pairs
        fTableModel = new TableModel();
        fTable = new ExtendedJTable(fTableModel);
        fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
        fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fTablePane = new JScrollPane(fTable);

        // create buttons
        fBtnApply = new JButton("Apply");
        fBtnApply.setMnemonic('A');
        fBtnApply.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    applyChanges();
                }
            });
        fBtnReset = new JButton("Reset");
        fBtnReset.setMnemonic('R');
        fBtnReset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    update();
                }
            });

        // layout the buttons centered from left to right
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(fBtnApply);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(fBtnReset);
        buttonPane.add(Box.createHorizontalGlue());
    
        // layout panel
        add(fObjectComboBox, BorderLayout.NORTH);
        add(fTablePane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
        setSize(new Dimension(300, 300));

        updateGUIState();
    }

    /**
     * Applies changes by setting new attribute values. Entries may be
     * arbitrary OCL expressions. 
     */
    private void applyChanges() {
        
    	if (fTable.getCellEditor() != null) { 
        	fTable.getCellEditor().stopCellEditing();
        }
        
        if (!haveObject()) { 
        	return;
        }
        
        // Don't refresh after first change...
        fSystem.getEventBus().unregister(this);
        boolean error = false;
        
        for (int i = 0; i < fValues.length; ++i) {
        	MAttribute attribute = fAttributes.get(i);
        	String newValue = fValues[i];
        	String oldValue = fAttributeValueMap.get(attribute).toString();
        	
        	if (!newValue.equals(oldValue)) {
        		
        		StringWriter errorOutput = new StringWriter();
        		Expression valueAsExpression = 
        			OCLCompiler.compileExpression(
        					fSystem.model(),
        					fSystem.state(),
        					newValue, 
        					"<input>", 
        					new PrintWriter(errorOutput, true), 
        					fSystem.varBindings());
        		
        		if (valueAsExpression == null) {
        			JOptionPane.showMessageDialog(
        					fMainWindow, 
        					errorOutput, 
        					"Error", 
        					JOptionPane.ERROR_MESSAGE);
        			error = true;
        			continue;
        		}
        		
        		try {
        			fSystem.execute(
        					new MAttributeAssignmentStatement(
        							fObject, 
        							attribute, 
        							valueAsExpression));
        			
        		} catch (MSystemException e) {
        			JOptionPane.showMessageDialog(
        					fMainWindow, 
        					e.getMessage(), 
        					"Error", 
        					JOptionPane.ERROR_MESSAGE);
        			error = true;
        		}
        	}
        }
        
        if (!error) update();
        
        fSystem.getEventBus().register(this);
    }

    private boolean haveObject() {
        return fObject != null && fObject.exists(fSystem.state());
    }

    /**
     * Initializes and updates the list of available objects.
     */
    private void updateGUIState() {
        // temporarily turn off action listener, since setting the
        // model triggers a select action which cannot be
        // distinguished from a user initiated selection
        fObjectComboBox.removeActionListener(fObjectComboBoxActionListener);

        // build list of names of currently existing objects
        MSystemState state = fSystem.state();
        Set<MObject> allObjects = state.allObjects();
        ArrayList<String> livingObjects = new ArrayList<String>();

        for (MObject obj : allObjects) {
            if (obj.exists(state) )
                livingObjects.add(obj.name());
        }
        
        if (livingObjects.isEmpty() ) {
            livingObjects.add(NO_OBJECTS_AVAILABLE);
            fObjectComboBox.setEnabled(false);
            fObject = null;
        } else
            fObjectComboBox.setEnabled(true);
        
        String[] objNames = livingObjects.toArray(new String[]{});
        Arrays.sort(objNames);

        // create combo box with available objects
        fObjectComboBox.setModel(new DefaultComboBoxModel<String>(objNames));
        
        // try to keep selection
        if (haveObject() )
            fObjectComboBox.setSelectedItem(fObject.name());
        fObjectComboBox.addActionListener(fObjectComboBoxActionListener);
    }

    /**
     * An object has been selected from the list. Update the table of
     * properties.
     */
    public void selectObject(String objName) {
        MSystemState state = fSystem.state();
        fObject = state.objectByName(objName);
        fTableModel.update();
        if (!fObjectComboBox.getSelectedItem().equals(objName)) {
            fObjectComboBox.setSelectedItem(objName);
        }
    }


    private void update() {
        updateGUIState();
        fTableModel.update();
    }
    
    @Subscribe
    public void onStateChanged(SystemStateChangedEvent e) {
    	update();
    }
    
    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
        fSystem.unregisterRequiresAllDerivedValues();
    }
}

