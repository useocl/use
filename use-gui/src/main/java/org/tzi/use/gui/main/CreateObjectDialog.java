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

package org.tzi.use.gui.main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.tzi.use.gui.util.CloseOnEscapeKeyListener;
import org.tzi.use.gui.util.GridBagHelper;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;

/** 
 * A dialog for creating objects.
 *  
 * @author  Mark Richters
 * @author  Lars Hamann
 */
@SuppressWarnings("serial")
class CreateObjectDialog extends JDialog {
    private MSystem fSystem;
    private MainWindow fParent;
    private List<MClass> fClasses;
    private JList<MClass> fListClasses;
    private JTextField fTextObjectName;

    CreateObjectDialog(Session session, MainWindow parent) {
        super(parent, "Create object");
        
        session.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				closeDialog();
			}
		});
        
        fSystem = session.system();
        fParent = parent;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        fClasses = new ArrayList<MClass>(fSystem.model().classes().size());
        // create class list and label
        for (MClass cls : fSystem.model().classes()) {
        	if (!(cls instanceof MAssociationClass))
        		fClasses.add(cls);
        }
        
        fListClasses = new JList<MClass>(fClasses.toArray(new MClass[]{}));
        fListClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JLabel labelClasses = new JLabel("Select class:");
        labelClasses.setDisplayedMnemonic('S');
        labelClasses.setLabelFor(fListClasses);
        JScrollPane scrollListClasses = new JScrollPane(fListClasses);

        // create object name field and label
        fTextObjectName = new JTextField(10);
        JLabel labelObjectName = new JLabel("Object name:");
        labelObjectName.setDisplayedMnemonic('O');
        labelObjectName.setLabelFor(fTextObjectName);

        // create buttons
        JButton btnCreate = new JButton("Create");
        btnCreate.setMnemonic('C');
        btnCreate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    createObject();
                }
            });
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
        gh.add(labelClasses, 0, 0, 
               1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(scrollListClasses, 0, 1, 
               1, GridBagConstraints.REMAINDER, 1.0, 0.0, 
               GridBagConstraints.BOTH);

        gh.add(labelObjectName, 1, 0, 
               1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(fTextObjectName, 1, 1, 
               1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(new JPanel(), 1, 2, 
               1, 1, 0.0, 1.0, GridBagConstraints.BOTH);

        gh.add(btnCreate, 1, 3, 
               1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        gh.add(btnClose, 1, 4, 
               1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);


        getRootPane().setDefaultButton(btnCreate);
        pack();
        setSize(new Dimension(300, 300));
        setLocationRelativeTo(parent);
        fListClasses.requestFocus();
    
        // allow dialog close on escape key
        CloseOnEscapeKeyListener ekl = new CloseOnEscapeKeyListener(this);
        addKeyListener(ekl);
        fTextObjectName.addKeyListener(ekl);
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }

    private void createObject() {
        // get object name
        String name = fTextObjectName.getText();
        if (name.length() == 0 ) {
            JOptionPane.showMessageDialog(this,
                                          "You need to specify a name for the new object.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        // get class
        MClass cls = fListClasses.getSelectedValue();
        if ( cls == null ) {
            JOptionPane.showMessageDialog(this,
                                          "You need to specify a class for the new object.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        fParent.createObject(cls, name);
    }
}

