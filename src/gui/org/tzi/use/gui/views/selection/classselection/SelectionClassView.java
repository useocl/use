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

package org.tzi.use.gui.views.selection.classselection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.selection.ClassSelectionView;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a view of SelectionClass
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public class SelectionClassView extends ClassSelectionView {

	private JButton fBtnSelectAll;

	private JButton fBtnClear;

	private Set<MClass> selectedClasses;

	private Map<MClass, ClassNode> fClassToNodeMap; // (MClass -> ClassNode)

	private Selection fNodeSelection;

	/**
	 * Constructor for SelectionClassView.
	 */
	public SelectionClassView(MainWindow parent, MSystem system,
			Set<MClass> selectedClasses, ClassDiagram classDiagram,
			DiagramInputHandling mouseHandling, Map<MClass, ClassNode> fClassToNodeMap,
			Selection fNodeSelection) {
		super(new BorderLayout(), parent, system, classDiagram);
		this.fClassToNodeMap = fClassToNodeMap;
		this.fNodeSelection = fNodeSelection;
		this.selectedClasses = selectedClasses;
		initSelectionClassView();
	}

	void initSelectionClassView() {
		fTableModel = new SelectionClassTableModel(fAttributes, fValues,
				selectedClasses, diagram, fClassToNodeMap,
				fNodeSelection);
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

		add(fTablePane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	/**
	 * Method getSelectedClasses return selected classes.
	 */
	private Set<MClass> getSelectedClasses() {
		Set<MClass> selected = new HashSet<MClass>();
		for (int i = 0; i < fAttributes.size(); i++) {
			if (fValues.get(i) != null
					&& ((Boolean) fValues.get(i)).booleanValue()) {
				String name = fAttributes.get(i).toString();
				Iterator<?> it = diagram.getGraph().iterator();
				boolean find = false;
				while (it.hasNext()) {
					Object node = it.next();
					if (node instanceof ClassNode) {
						MClass mc = ((ClassNode) node).cls();
						if (mc.name().equals(name)) {
							selected.add(mc);
							find = true;
							break;
						}
					}
				}
				if (!find) {
					it = diagram.getHiddenNodes().iterator();
					while (it.hasNext()) {
						Object node = it.next();
						if (node instanceof MClass) {
							MClass mc = (MClass) node;
							if (mc.name().equals(name)) {
								selected.add(mc);
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
			fTableModel.setValueAt("true", i, 1);
		}
		this.repaint();
	}

	private void applyClearChanges(ActionEvent ev) {
		for (int i = 0; i < fValues.size(); i++) {
			fTableModel.setValueAt("false", i, 1);
		}
		this.repaint();
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked classes.
	 */
	public void applyCropChanges(ActionEvent ev) {
		if (getHideClasses(getSelectedClasses(), true).size() > 0) {
			diagram.getAction("Hide",
					getHideClasses(getSelectedClasses(), true))
					.actionPerformed(ev);
		}
		if (getClassesToShow(getSelectedClasses()).size() > 0) {
			diagram.showElementsInDiagram(getClassesToShow(getSelectedClasses()));
		}
		((SelectionClassTableModel) fTableModel).refreshAll();
	}

	/**
	 * Method applyShowChanges shows the appropriate marked classes.
	 */
	public void applyShowChanges(ActionEvent ev) {
		if (getClassesToShow(getSelectedClasses()).size() > 0) {
			diagram.showElementsInDiagram(getClassesToShow(getSelectedClasses()));
			((SelectionClassTableModel) fTableModel).refreshAll();
		}
	}

	/**
	 * Method applyHideChanges hides the appropriate marked classes.
	 */
	public void applyHideChanges(ActionEvent ev) {
		if (getHideClasses(getSelectedClasses(), false).size() > 0) {
			diagram.getAction("Hide",
					getHideClasses(getSelectedClasses(), false))
					.actionPerformed(ev);
		}
	}

	/**
	 * Method applyShowAllChanges show all classes.
	 */
	public void applyShowAllChanges(ActionEvent ev) {
		diagram.showAll();
		diagram.invalidateContent();
		((SelectionClassTableModel) fTableModel).refreshAll();
	}

	public void update() {
		fTableModel.update();
	}
}
