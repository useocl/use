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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramData;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.selection.ClassSelectionView;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClassifier;

/** 
 * This view shows all classes in a table and allows
 * the user to show or hide them by changing
 * the value of the second column.
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author   Lars Hamann 
 */
@SuppressWarnings("serial")
public class SelectionClassView extends ClassSelectionView {

	private JButton fBtnSelectAll;

	private JButton fBtnClear;

	/**
	 * Constructor for SelectionClassView.
	 */
	public SelectionClassView(MainWindow parent, ClassDiagram classDiagram) {
		super(parent, classDiagram);
		initSelectionClassView();
	}

	void initSelectionClassView() {
		fTableModel = new SelectionClassTableModel(diagram);
		fTable = new JTable(fTableModel);
		fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
		fTable.getColumnModel().getColumn(0).setCellRenderer(new VivibilityCellrenderer());
				
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
	private Set<MClassifier> getSelectedClassifier() {
		return ((SelectionClassTableModel)fTableModel).getSelectedClassifier();
	}

	private void applySelectAllChanges(ActionEvent ev) {
		((SelectionClassTableModel)fTableModel).selectAll();
	}

	private void applyClearChanges(ActionEvent ev) {
		((SelectionClassTableModel)fTableModel).deselectAll();
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked classes.
	 */
	public void applyCropChanges(ActionEvent ev) {
		Set<MClassifier> selectedClassifier = getSelectedClassifier();
		Set<MClassifier> classifierToHide = getClassifierToHide(selectedClassifier, true); 
		
		if (!classifierToHide.isEmpty()) {
			diagram.hideElementsInDiagram(classifierToHide, Collections.<MAssociation>emptySet());
		}

		Set<MClassifier> classifierToShow = getClassifierToShow(selectedClassifier);
		
		if (classifierToShow.size() > 0) {
			diagram.showElementsInDiagram(classifierToShow);
			selectedClassifier.retainAll(classifierToShow);
			Set<PlaceableNode> selectedNodes = ((ClassDiagramData)diagram.getVisibleData()).getNodes(selectedClassifier);
			diagram.getNodeSelection().addAll(selectedNodes);
		}
		
		diagram.invalidateContent(true);
		((SelectionClassTableModel) fTableModel).update();
	}

	/**
	 * Method applyShowChanges shows the appropriate marked classes.
	 */
	public void applyShowChanges(ActionEvent ev) {
		Set<MClassifier> selected = ((SelectionClassTableModel) fTableModel).getSelectedClassifier();
		Set<MClassifier> classifierToShow = getClassifierToShow(selected);
		
		if (classifierToShow.size() > 0) {
			diagram.showElementsInDiagram(classifierToShow);
			
			selected.retainAll(classifierToShow);
			Set<PlaceableNode> selectedNodes = ((ClassDiagramData)diagram.getVisibleData()).getNodes(selected);
			diagram.getNodeSelection().addAll(selectedNodes);
			diagram.invalidateContent(true);
		}
	}

	/**
	 * Method applyHideChanges hides the appropriate marked classes.
	 */
	public void applyHideChanges(ActionEvent ev) {
		Set<MClassifier> classifierToHide = getClassifierToHide(getSelectedClassifier(), false);
		
		if (classifierToHide.size() > 0) {
			diagram.hideElementsInDiagram(classifierToHide, Collections.<MAssociation>emptySet());
			diagram.invalidateContent(true);
			((SelectionClassTableModel) fTableModel).update();
		}
	}

	/**
	 * Show all classifiers in diagram and refresh the table
	 */
	public void applyShowAllChanges(ActionEvent ev) {
		super.applyShowAllChanges(ev);
		((SelectionClassTableModel) fTableModel).update();
	}

	/**
	 * Hide all classifiers in diagram and refresh the table
	 */
	public void applyHideAllChanges(ActionEvent ev) {
		super.applyHideAllChanges(ev);
		((SelectionClassTableModel) fTableModel).update();
	}
	
	public void update() {
		((SelectionClassTableModel)fTableModel).update();
	}

	@Override
	public void detachModel() {
		super.detachModel();
		((SelectionClassTableModel)fTableModel).dispose();
	}
	
	private class VivibilityCellrenderer extends DefaultTableCellRenderer {
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                   boolean hasFocus, int row, int column){
	        	
				Component defaultRenderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
				
				if (!((SelectionClassTableModel)fTableModel).getRows().get(row).visible) {
					this.setForeground(Color.gray);
				} else {
					this.setForeground(Color.black);
				}
								
				return defaultRenderer;
	    }
	}
}
