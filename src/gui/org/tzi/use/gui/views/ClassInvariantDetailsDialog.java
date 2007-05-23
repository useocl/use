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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.config.Options;
import org.tzi.use.gui.util.CloseOnEscapeKeyListener;
import org.tzi.use.gui.util.GridBagHelper;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;

/** 
 * A dialog for showing objects of a class violating or satisfying an
 * invariant.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
class ClassInvariantDetailsDialog extends JDialog implements ItemListener {
    private MSystem fSystem;
//    private MClassInvariant fInv;
    private Set fGoodObjects;
    private Set fBadObjects;
    private Object[] fObjects;  // contents of fListObjects
    private JList fListObjects;
    private JLabel fLabelProperties;
    private JCheckBox fCheckBoxGood;
    private JCheckBox fCheckBoxBad;
    
    // icons used in list
    private static Icon fTrueIcon = new ImageIcon(Options.iconDir + "InvTrue.gif");
    private static Icon fFalseIcon = new ImageIcon(Options.iconDir + "InvFalse.gif");

    // table for attribute values
    private JTable fTable;
    private JScrollPane fTablePane;
    private TableModel fTableModel;
    
    private MObject fSelectedObject; // selected object or null
    private MAttribute[] fAttributes;
    private String[] fValues;

    /**
     * The table model.
     */
    class TableModel extends AbstractTableModel {
        final String[] columnNames = { "Attribute", "Value" };

        TableModel() {
            update();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getColumnCount() { 
            return 2; 
        }
        public int getRowCount() { 
            return fAttributes.length;
        }
        public Object getValueAt(int row, int col) { 
            if (col == 0 )
                return fAttributes[row].name();
            else
                return fValues[row];
        }
        private void update() {
            // initialize table model
            if (haveObject() ) {
                MObjectState objState = fSelectedObject.state(fSystem.state());
                for (int i = 0; i < fValues.length; i++)
                    fValues[i] = 
                        ((Value) objState.attributeValue(fAttributes[i])).toString();
            } else {
                for (int i = 0; i < fValues.length; i++)
                    fValues[i] = "n/a";
            }
            fireTableDataChanged();
        }
    }

    private boolean haveObject() {
        return fSelectedObject != null && fSelectedObject.exists(fSystem.state());
    }

    /**
     * Updates table and its label to reflect currently selected
     * object.  
     */
    private void selectObject(int i) {
        if (i < 0 ) {
            fSelectedObject = null;
            fLabelProperties.setText("Properties:");
        } else {
            fSelectedObject = ((ObjectValue) fObjects[i]).value();
            fLabelProperties.setText("Properties of object " +
                                     fSelectedObject.name() + ":");
        }
        fTableModel.update();
    }

    /**
     * Listens on checkboxes.
     */
    public void itemStateChanged(ItemEvent e) {
        changeListContents(fCheckBoxGood.isSelected(), fCheckBoxBad.isSelected());
    }

    /**
     * Show objects satisfying and/or violating invariant.
     */
    private void changeListContents(boolean showGoodOnes, boolean showBadOnes) {
        Set s = new HashSet();
        if (showGoodOnes )
            s.addAll(fGoodObjects);
        if (showBadOnes )
            s.addAll(fBadObjects);
        fObjects = s.toArray();
        Arrays.sort(fObjects);
        fListObjects.setListData(fObjects);
        if (fObjects.length > 0 )
            fListObjects.setSelectedIndex(0);
        else
            selectObject(-1);
    }

    ClassInvariantDetailsDialog(MSystem system, MClassInvariant inv) {
        super((Frame) null, "Details of invariant " + inv.name(), true);
        fSystem = system;
//        fInv = inv;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // prepare table: get attributes of class
        List attributes = inv.cls().allAttributes();
        final int N = attributes.size();
        fAttributes = new MAttribute[N];
        System.arraycopy(attributes.toArray(), 0, fAttributes, 0, N);
        Arrays.sort(fAttributes);
        fValues = new String[N];

        // initialize good/bad objects
        fGoodObjects = new HashSet();
        fBadObjects = new HashSet();
        Evaluator evaluator = new Evaluator();
        Expression expr = inv.getExpressionForViolatingInstances();
        Value v = evaluator.eval(expr, fSystem.state(), new VarBindings());
        fBadObjects.addAll(((SetValue) v).collection());
        expr = inv.getExpressionForSatisfyingInstances();
        v = evaluator.eval(expr, fSystem.state(), new VarBindings());
        fGoodObjects.addAll(((SetValue) v).collection());

        // create object list and label
        fListObjects = new JList();
        fListObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fListObjects.setCellRenderer(new MyCellRenderer());
        // update table if selection changes
        fListObjects.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    int index = fListObjects.getSelectedIndex();
                    selectObject(index);
                }
            });


        JLabel labelObjects = new JLabel("Objects of class " + inv.cls().name() + ":");
        labelObjects.setDisplayedMnemonic('O');
        labelObjects.setLabelFor(fListObjects);
        JScrollPane scrollListClasses = new JScrollPane(fListObjects);

        // create table of attribute/value pairs
        fTableModel = new TableModel();
        fTable = new JTable(fTableModel);
        fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
        fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fTablePane = new JScrollPane(fTable);

        // create object name field and label
        fLabelProperties = new JLabel("Properties:");
        fLabelProperties.setDisplayedMnemonic('P');
        fLabelProperties.setLabelFor(fTable);


        // create check boxes
        fCheckBoxGood = new JCheckBox("Show objects satisfying " + inv.name());
        fCheckBoxGood.setMnemonic(KeyEvent.VK_S); 
        fCheckBoxGood.setSelected(true);
        fCheckBoxGood.addItemListener(this);

        fCheckBoxBad = new JCheckBox("Show objects violating " + inv.name());
        fCheckBoxBad.setMnemonic(KeyEvent.VK_V); 
        fCheckBoxBad.setSelected(true);
        fCheckBoxBad.addItemListener(this);

        // create buttons
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeDialog();
                }
            });

        // layout content pane
        JComponent contentPane = (JComponent) getContentPane();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagHelper gh = new GridBagHelper(contentPane);
        gh.add(labelObjects, 
               0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(scrollListClasses, 
               0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH);

        gh.add(fCheckBoxGood, 
               0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(fCheckBoxBad, 
               0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(fLabelProperties, 
               1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(fTablePane, 
               1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH);

        gh.add(btnClose, 
               1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NONE);

        getRootPane().setDefaultButton(btnClose);
        pack();
        setSize(new Dimension(500, 300));
        //setLocationRelativeTo(parent);
        fListObjects.requestFocus();
    
        // allow dialog close on escape key
        CloseOnEscapeKeyListener ekl = new CloseOnEscapeKeyListener(this);
        addKeyListener(ekl);

        // initialize list 
        changeListContents(true, true);
        if (fListObjects.getModel().getSize() > 0 )
            selectObject(0);
    }

    /**
     * Renders list items with icons.
     */
    class MyCellRenderer extends JLabel implements ListCellRenderer  {
        Color highlightColor = new Color(0, 0, 128);
    
        MyCellRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus)
        {
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            }
            else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }

            this.setText(" " + fObjects[index]);
            if (fGoodObjects.contains(fObjects[index]) )
                this.setIcon(fTrueIcon);
            else
                this.setIcon(fFalseIcon);
            return this;
        }
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }

}

