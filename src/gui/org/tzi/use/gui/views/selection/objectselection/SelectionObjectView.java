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

/* $ProjectHeader: use 2-3-1-release.3 Wed, 02 Aug 2006 17:53:29 +0200 green $ */

package org.tzi.use.gui.views.selection.objectselection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.selection.ObjectSelectionView;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;

/** 
 * a view of SelectionObject
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public class SelectionObjectView extends ObjectSelectionView {
	private static final String NO_OBJECTS_AVAILABLE = "(No objects available.)";

	private JComboBox<String> fClassComboBox;

	private JButton fBtnSelectAll;

	private JButton fBtnClear;

	private ClassComboBoxActionListener fClassComboBoxActionListener;

	/**
	 * Constructor for SelectionObjectView.
	 */
	public SelectionObjectView(MainWindow parent, MSystem system, DiagramViewWithObjectNode diagram) {
		super(parent, system, diagram);
		this.fSystem = system;
		initSelectionObjectView();
		update();
	}

	void initSelectionObjectView() {
		fTableModel = new SelectionObjectTableModel(fSystem);
		fTable = new JTable(fTableModel);
		fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
		fTablePane = new JScrollPane(fTable);

		fBtnSelectAll = new JButton("Select all");
		fBtnSelectAll.setMnemonic('e');
		fBtnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applySelectAllChanges(e);
			}
		});

		fBtnClear = new JButton("Unselect all");
		fBtnClear.setMnemonic('U');
		fBtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyClearChanges(e);
			}
		});

		buttonPane.add(fBtnSelectAll);
		buttonPane.add(fBtnClear);
		buttonPane.add(Box.createHorizontalGlue());

		// create combo box with available objects
		fClassComboBox = new JComboBox<String>();
		fClassComboBoxActionListener = new ClassComboBoxActionListener();
		add(fClassComboBox, BorderLayout.NORTH);
		add(buttonPane, BorderLayout.SOUTH);
		add(fTablePane, BorderLayout.CENTER);
	}

	class ClassComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String clsName = (String) fClassComboBox.getSelectedItem();
			
			if (!NO_OBJECTS_AVAILABLE.equals(clsName))
				selectClass(clsName);
		}
	}

	/**
	 * Method getSelectedObjects return selected objects.
	 */
	private Set<MObject> getSelectedObjects() {
		return ((SelectionObjectTableModel)fTableModel).getSelectedObjects();
	}

	private void applySelectAllChanges(ActionEvent ev) {
		((SelectionObjectTableModel)fTableModel).selectAll();
	}

	private void applyClearChanges(ActionEvent ev) {
		((SelectionObjectTableModel)fTableModel).deselectAll();
	}

	/**
	 * Method selectClass update the table of class, when an object has been selected from the list.
	 */
	public void selectClass(String clsName) {
		MClass cls = fSystem.model().getClass(clsName);
		((SelectionObjectTableModel) fTableModel).setSelected(cls);
		((SelectionObjectTableModel) fTableModel).update();
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked objects.
	 */
	public void applyCropChanges(ActionEvent ev) {
		Set<MObject> selectedObjects = getSelectedObjects();
		
		this.diagram.hideAll();
		this.diagram.showObjects(selectedObjects);
		this.diagram.invalidateContent(true);
	}

	/**
	 * Method applyShowChanges shows the appropriate marked objects.
	 */
	public void applyShowChanges(ActionEvent ev) {
		this.diagram.showObjects(getSelectedObjects());
		this.diagram.invalidateContent(true);
	}

	/**
	 * Method applyHideChanges hides the appropriate marked objects.
	 */
	public void applyHideChanges(ActionEvent ev) {
		this.diagram.hideObjects(getSelectedObjects());
		this.diagram.invalidateContent(true);
	}

	public void update() {
		fClassComboBox.removeActionListener(fClassComboBoxActionListener);
		
		MSystemState state = fSystem.state();
		List<String> namesOfClassesWithObjects = new ArrayList<String>();
		String previouslySelectedItem = (String)fClassComboBox.getSelectedItem();
		
		for (MClass cls : fSystem.model().classes()) {
			if (!state.objectsOfClassAndSubClasses(cls).isEmpty())
				namesOfClassesWithObjects.add(cls.name());
		}

		if (namesOfClassesWithObjects.isEmpty()) {
			namesOfClassesWithObjects.add(NO_OBJECTS_AVAILABLE);
			fClassComboBox.setEnabled(false);
		} else {
			fClassComboBox.setEnabled(true);
		}
		
		String[] clsNames = namesOfClassesWithObjects.toArray(new String[0]);
		Arrays.sort(clsNames);

		// create combo box with available objects
		fClassComboBox.setModel(new DefaultComboBoxModel<String>(clsNames));
		
		fClassComboBox.addActionListener(fClassComboBoxActionListener);
		
		// try to keep selection
		if (previouslySelectedItem != null) {
			fClassComboBox.setSelectedItem(previouslySelectedItem);
			((SelectionObjectTableModel) fTableModel).setSelected(fSystem.model().getClass(previouslySelectedItem));
		} else {
			fClassComboBox.setSelectedIndex(0);
		}
	}

}
