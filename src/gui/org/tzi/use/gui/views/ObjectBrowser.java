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

// $Id$

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkSet;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Window for the Object Properties 
 */
class ObjectBrowser extends JFrame {
    private JTable fTable;
    private TableModel fTableModel;
    private JScrollPane fTablePane;
    private JLabel fTopLabel;
    private Map fCellEditors;
    
    private MSystem fSystem;
    private MObject fObject;
    private MAttribute[] fAttributes;
    private String[] fValues;
    private String[] fAssociations;
    private String[] fObjects;
    private Map fAttributeValueMap;
    private Vector fAssoc;
    private Map fConnectedObject;
    
    /**
     * The table model.
     */
    class TableModel extends AbstractTableModel {
        final String[] columnNames = { "Attribute", "Value", "Association", "Objects" };
        
        TableModel() {
            update();
        }
        
        public String getColumnName(int col) {
            return columnNames[col];
        }
        
        public int getColumnCount() { 
            return 4; 
        }
        public int getRowCount() {
            if(fAttributes.length > fAssoc.size()) 
                return fAttributes.length;
            else 
                return fAssoc.size();
        }
        public boolean isCellEditable(int row, int col) {
            return col==3; 
        }
        public Object getValueAt(int row, int col) { 
            
            if (col == 0 )
                if(row < fAttributes.length)
                    return fAttributes[row];
                else 
                    return "";
            else if(col == 1)
                if(row < fAttributes.length)
                    return fValues[row];
                else
                    return "";
            else if( col == 2 )
                if(row < fAssoc.size())
                    return fAssociations[row];
                else
                    return "";
            else
                if(row < fAssoc.size()) {
                    return fObjects[row];
                }
            else
                    return "";
        }
        /*
         public void setValueAt(Object value, int row, int col) {
         Log.trace(this, "row = " + row + ", col = " + col + ", value = " + value);
         fValues[row] = value.toString();
         fireTableCellUpdated(row, col);
         }
         */
        private void update() {
            // initialize table model
            if (haveObject() ) {
                MObjectState objState = fObject.state(fSystem.state());
                fAttributeValueMap = objState.attributeValueMap();
                final int N = fAttributeValueMap.size();
                fAttributes = new MAttribute[N];
                fValues = new String[N];
                
                System.arraycopy(fAttributeValueMap.keySet().toArray(), 0, 
                        fAttributes, 0, N);
                Arrays.sort(fAttributes);
                for (int i = 0; i < N; i++)
                    fValues[i] = 
                        ((Value) fAttributeValueMap.get(fAttributes[i])).toString();
                
                final int M = fAssoc.size();
                fAssociations = new String[M];
                fObjects = new String[M];
                System.arraycopy(fAssoc.toArray(),0,fAssociations,0,M);
                //Arrays.sort(fAssociations);
                for(int i=0; i<M; i++)
                    fObjects[i] = fConnectedObject.get(fAssociations[i]).toString();
            } else {
                fAttributes = new MAttribute[0];
                fValues = new String[0];
            }
            
            fireTableDataChanged();
        }
    }
    private boolean haveObject() {
        return fObject != null && fObject.exists(fSystem.state());
    }
    /**
     * An object has been selected from the list. Update the table of
     * properties.
     */
    private void selectObject(String objName) {
        MSystemState state = fSystem.state();
        if(objName.charAt(0) == '@'){
            objName = objName.substring(1);
            fObject = state.objectByName(objName);
            //fTableModel.update();
        }
    }
    
    public void calcLinks(){
        // Associations and connected Objects
        fAssoc = new Vector();
        fConnectedObject = new TreeMap();
        MClass objClass = fObject.cls();
        // get associations this object might be participating in
        Set assocSet = objClass.allAssociations();   
        Iterator assocIter = assocSet.iterator();
        while ( assocIter.hasNext() ) {
            MAssociation assoc = ( MAssociation ) assocIter.next();
            MLinkSet linkSet = fSystem.state().linksOfAssociation(assoc) ;
            // check all association ends the objects' class is
            // connected to
            Iterator linkIter = linkSet.links().iterator();
            Set connectedObjects = new HashSet();
            while( linkIter.hasNext() ) {
                MLink link = (MLink)linkIter.next();
                if(link.linkedObjects().contains(fObject)) {
                    //System.out.println("debugg "+link.linkedObjects().toString());
                    //add association if it's still not in the list
                    if(!fAssoc.contains(assoc.toString()))
                        fAssoc.add(assoc.toString());
                    Set convertToSet = new HashSet(link.linkedObjects());
                    // remove fObject from dummy if it is not connected reflexive
                    MObject[] objSet = {fObject};
                    if(!linkSet.hasLinkBetweenObjects(objSet))
                        convertToSet.remove(fObject);
                    connectedObjects.addAll(convertToSet);
                }
            }
            fConnectedObject.put(assoc.toString(),connectedObjects);
            
        }
        
    }
    
    private void update() {
        if(fTablePane!=null)
            getContentPane().remove(fTablePane);
        calcLinks();
        //updateGUIState();
        fCellEditors = new TreeMap();
        for(int i=0; i<fAssoc.size(); i++) {
            JComboBox combo = new JComboBox(); 
            HashSet objectsSet = (HashSet)fConnectedObject.get(fAssoc.get(i));
            Iterator it = objectsSet.iterator();
            while (it.hasNext()) {
                MObject obj = (MObject)it.next();
                combo.addItem(obj);
            }
            combo.setSelectedIndex(-1);
            combo.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    JComboBox selectedChoice = (JComboBox)e.getSource();
                    // Implemented Actions for the comboTree
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        
                        Object obj = selectedChoice.getSelectedItem();
                        if(obj instanceof MObject) {
                            MObject sel = (MObject)obj;
                            //System.out.println("debugg: "+ sel.name());
                            selectObject("@"+sel.name());
                            setTopLabelText("@"+fObject.name()+" : "+fObject.type());
                            update();
                            fTableModel.update();
                        }
                    }
                }
            });
            
            DefaultCellEditor editor = new DefaultCellEditor(combo);
            fCellEditors.put(fAssoc.get(i).toString(),editor);
        }
        
        fTableModel = new TableModel();
        fTable = new JTable(fTableModel) {
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel( column );
                //return (TableCellEditor)fCellEditors.get(fAssoc.get(row).toString());
                
                if(modelColumn == 3 && row < fAssoc.size()) {
                    return (TableCellEditor)fCellEditors.get(fAssoc.get(row).toString()); 
                }   
                return super.getCellEditor(row,column);
                
            }
        };
        
        fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
        fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fTablePane = new JScrollPane(fTable);
        fTableModel.update();
        getContentPane().add(fTablePane,BorderLayout.CENTER);
    }
    
    public void setTopLabelText(String txt){
        // get the Display Size
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension displaySize = tk.getScreenSize();
        int maxWidth = (int)displaySize.getWidth();
        
        //Dimension windowSize = getSize();
        int windowSize = getWidth();
        
        if(windowSize < maxWidth)
            maxWidth = windowSize;
        // calculate the Dimension for the title
        FontMetrics fm = fTopLabel.getFontMetrics(fTopLabel.getFont());
        int topWidth = 0;
        int topHeight = fm.getHeight();
        //int topHeight = 0;
        Pattern p = Pattern.compile("\n");
        String s[] = p.split(txt);
        for (int i=0; i<s.length; i++){
            // if the current string is wider 
            if(topWidth < fm.stringWidth(s[i]))
                if(fm.stringWidth(s[i]) < maxWidth)
                    // string is between width so far and maxWidth 
                    topWidth = fm.stringWidth(s[i]);
                else topWidth = maxWidth;
            // increment the height with 1 line or more if needed
            topHeight += (fm.getHeight() *(1+(fm.stringWidth(s[i])/maxWidth)));
        }
        // set the Size of the South Frame
        fTopLabel.setPreferredSize(new Dimension(topWidth,topHeight));
        // make changes visible
        fTopLabel.setText(txt);
        fTopLabel.setVisible(false);
        fTopLabel.setVisible(true);
    }
    
    public ObjectBrowser(MSystem sys, Entry var){
        super("Object browser");
        fSystem = sys;
        getContentPane().setLayout(new BorderLayout());
        fTopLabel = new JLabel(var.getValue().toStringWithType());
        //fTopLabel.setSize(300,100);
        fTopLabel.setHorizontalAlignment(JLabel.CENTER);
        fTopLabel.setVerticalAlignment(JLabel.CENTER);
        JButton button = new JButton("Close");
        button.setSize(new Dimension(30,(int)button.getHeight()));
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                setVisible(false);
                dispose();
            }
        });         
        Box comboBox = Box.createHorizontalBox();
        comboBox.add(Box.createGlue());
        comboBox.add(button);
        comboBox.add(Box.createGlue());         
        
        // first select Object then create new table of attribute/value pairs
        selectObject(var.getValue().toString());
        if(fObject!=null) 
            update();
        
        getContentPane().add(fTopLabel,BorderLayout.NORTH);
        getContentPane().add(comboBox,BorderLayout.SOUTH);
        setSize(600,300);
        setVisible(true);
        setTopLabelText(var.getValue().toStringWithType());
    }
}