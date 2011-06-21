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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.ObjectSelectionView;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.Log;

/** 
 * a view of SelectionObject
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
//FIXME: This view is never used!
public class SelectionObjectView extends ObjectSelectionView {
	private static final String NO_CLASSES_AVAILABLE = "(No classes available.)";

	private JComboBox fClassComboBox;

	private JButton fBtnSelectAll;

	private JButton fBtnClear;

	private ClassComboBoxActionListener fClassComboBoxActionListener;

	private MClass fClass;

	private MSystem fSystem;

	/**
	 * Constructor for SelectionObjectView.
	 */
	public SelectionObjectView(MainWindow parent, MSystem system, NewObjectDiagram diagram) {
		super(new BorderLayout(), parent, system, diagram);
		this.fSystem = system;
		initSelectionObjectView();
		updateGUIState();
	}

	void initSelectionObjectView() {
		fTableModel = new SelectionObjectTableModel(fAttributes, fValues,
				fClass, fSystem);
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
		fClassComboBox = new JComboBox();
		fClassComboBoxActionListener = new ClassComboBoxActionListener();
		add(fClassComboBox, BorderLayout.NORTH);
		add(buttonPane, BorderLayout.SOUTH);
		add(fTablePane, BorderLayout.CENTER);
	}

	class ClassComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String clsName = (String) cb.getSelectedItem();
			Log.trace(this, "fClassComboBox.actionPerformed(): " + clsName);
			if (clsName != NO_CLASSES_AVAILABLE)
				selectClass(clsName);
		}
	}

	/**
	 * Method getSelectedObjects return selected objects.
	 */
	private Set<MObject> getSelectedObjects() {
		Set<MObject> selected = new HashSet<MObject>();
		
		for (int i = 0; i < fAttributes.size(); i++) {
			if (fValues.get(i) != null
					&& ((Boolean) fValues.get(i)).booleanValue()) {
				String name = fAttributes.get(i).toString();
				Iterator<?> it = this.diagram.getGraph().iterator();
				boolean find = false;
				while (it.hasNext()) {
					Object node = it.next();
					if (node instanceof ObjectNode) {
						MObject mobj = ((ObjectNode) node).object();
						if (mobj.name().equals(name)) {
							selected.add(mobj);
							find = true;
							break;
						}
					}
				}
				if (!find) {
					it = this.diagram.getHiddenNodes().iterator();
					while (it.hasNext()) {
						Object node = it.next();
						if (node instanceof MObject) {
							MObject mobj = (MObject) node;
							if (mobj.name().equals(name)) {
								selected.add(mobj);
								break;
							}
						}
					}
				}
			}
		}
		return selected;
	}

	private void applySelectAllChanges(ActionEvent ev) {
		for (int i = 0; i < fValues.size(); i++) {
			fValues.set(i, (Boolean.valueOf(true)));
		}
		this.repaint();
	}

	private void applyClearChanges(ActionEvent ev) {
		for (int i = 0; i < fValues.size(); i++) {
			fValues.set(i, (Boolean.valueOf(false)));
		}
		this.repaint();
	}

	/**
	 * Method updateGUIState initializes and updates the list of available objects.
	 */
	private void updateGUIState() { 
		Log.trace(this, "updateGUIState1");

		fClassComboBox.removeActionListener(fClassComboBoxActionListener);

		MSystemState state = fSystem.state();
		Set<MObject> allObjects = state.allObjects();
		
		ArrayList<String> livingClasses = new ArrayList<String>();
		
		for (MObject obj : allObjects) {
			MClass cls = obj.cls();
			if (obj.exists(state) && !livingClasses.contains(cls.name()))
				livingClasses.add(cls.name());
		}

		if (livingClasses.isEmpty()) {
			livingClasses.add(NO_CLASSES_AVAILABLE);
			fClassComboBox.setEnabled(false);
			fClass = null;
		} else
			fClassComboBox.setEnabled(true);

		Object[] clsNames = livingClasses.toArray();
		Arrays.sort(clsNames);

		// create combo box with available objects
		fClassComboBox.setModel(new DefaultComboBoxModel(clsNames));
		// try to keep selection
		if (fClass != null)
			fClassComboBox.setSelectedItem(fClass.name());
		fClassComboBox.addActionListener(fClassComboBoxActionListener);
		Log.trace(this, "updateGUIState2");
	}

	/**
	 * Method selectClass update the table of class, when an object has been selected from the list.
	 */
	public void selectClass(String clsName) {
		MSystemState state = fSystem.state();
		String objectName = "";
				
		for (MObject obj : state.allObjects()) {
			if (obj.name().contains(clsName)) {
				objectName = obj.name();
				break;
			}
		}
		
		fClass = (state.objectByName(objectName)).cls();

		if (!fClassComboBox.getSelectedItem().equals(objectName)) {
			fClassComboBox.setSelectedItem(objectName);
		}
		((SelectionObjectTableModel) fTableModel).setSelected(fClass);
		fTableModel.update();
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked objects.
	 */
	public void applyCropChanges(ActionEvent ev) {
		Set<MObject> selectedObjects = getSelectedObjects();
		
		this.diagram.hideAll();
		this.diagram.showObjects(selectedObjects);
		this.diagram.invalidateContent();
	}

	/**
	 * Method applyShowChanges shows the appropriate marked objects.
	 */
	public void applyShowChanges(ActionEvent ev) {
		this.diagram.showObjects(getSelectedObjects());
		this.diagram.invalidateContent();
	}

	/**
	 * Method applyHideChanges hides the appropriate marked objects.
	 */
	public void applyHideChanges(ActionEvent ev) {
		this.diagram.hideObjects(getSelectedObjects());
		this.diagram.invalidateContent();
	}

	public void update() {
		updateGUIState();
		fTableModel.update();
	}

}
