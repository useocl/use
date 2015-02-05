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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.ExtendedJTable;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.selection.ClassSelectionView;
import org.tzi.use.gui.views.selection.TableModel.Row;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;

/** 
 * a view of SelectedClassPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

@SuppressWarnings("serial")
public class SelectedClassPathView extends ClassSelectionView {

	private JButton fBtnReset;

	/**
	 * Constructor for SelectedClassPathView.
	 */
	public SelectedClassPathView(MainWindow parent, ClassDiagram diagram, Set<MClass> selectedClasses) {
		this(parent, diagram, new ClassPathTableModel(selectedClasses));
	}
    
	protected SelectedClassPathView(MainWindow parent, ClassDiagram diagram, AbstractTableModel model) {
		super(parent, diagram);
		initView(model);
	}
	
	/**
	 * initSelectionClassPathView initialize the layout of view and add a Button "Reset", 
	 * which pre-defined values can be reset.
	 */
	void initView(AbstractTableModel model) {
		fTableModel = model;
		fTable = new ExtendedJTable(fTableModel);
		fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
		fTablePane = new JScrollPane(fTable);

		fBtnReset = new JButton("Reset");
		fBtnReset.setMnemonic('R');
		fBtnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		buttonPane.add(fBtnReset);

		add(fTablePane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	public Set<MClass> getSelectedPathClasses() {
		Set<MClass> classes = new HashSet<MClass>();
		ClassPathTableModel model = (ClassPathTableModel)this.fTableModel;
		
		for (Row<MClass> row : model.getRows()) {
			Map<MClass, Integer> allPathes = getAllPathClasses(row.item);
			int maxDepth = row.value;

			for (Entry<MClass, Integer> entry : allPathes.entrySet()) {
				if (entry.getValue() <= maxDepth) {
					classes.add(entry.getKey());
				}
			}
		}
		
		return classes;
	}

	/**
	 * Method getAllPathClasses return all attainable classes from the given class mc
	 */
	public static Map<MClass, Integer> getAllPathClasses(MClass mc) {
		
		Map<MClass, Integer> result = new HashMap<MClass, Integer>();
		
		// For FIFO handling of remaining classes to process
		LinkedList<MClass> buffer = new LinkedList<MClass>();
		
		buffer.addLast(mc);
		result.put(mc, Integer.valueOf(0));
		
		MClass currentClass;
		Set<? extends MClassifier> relatedClasses;
		int depth;
		
		while (buffer.size() > 0) {
			currentClass = buffer.poll();
			depth = result.get(currentClass).intValue() + 1;
			
			// Check generalizations
			relatedClasses = currentClass.parents();
			addNewClassesWithDepth(buffer, result, relatedClasses, depth);
			
			// Check the subclasses
			relatedClasses = currentClass.children();
			addNewClassesWithDepth(buffer, result, relatedClasses, depth);

			// Check classes related from associations
			for (MAssociation association : currentClass.associations()) {
				relatedClasses = association.associatedClasses();
				addNewClassesWithDepth(buffer, result, relatedClasses, depth);
			}
		}
		
		return result;
	}

	/**
	 * Helper function for getAllPathClasses
	 * @param result buffer with already handled classes and depth
	 * @param relatedClasses classes to evantually at to buffer
	 * @param depth depth to set
	 */
	private static void addNewClassesWithDepth(LinkedList<MClass> buffer, Map<MClass, Integer> result,
										Set<? extends MClassifier> relatedClasses, int depth) {
		if (relatedClasses != null) {
			for (MClassifier cls : relatedClasses) {
				if (!(cls instanceof MClass)) continue;
				
				if (!result.containsKey(cls)) {
					result.put((MClass)cls, Integer.valueOf(depth));
					buffer.add((MClass)cls);
				}
			}
		}
	}

	/**
	 * Method getDepth obtains maximally attainable Depth based on starting point(mc)
	 */
	public static int getDepth(MClass mc) {
		Map<MClass, Integer> allClasses = getAllPathClasses(mc);

		int maxDepth = 0;
		for (Integer val : allClasses.values()) {
			maxDepth = Math.max(maxDepth, val.intValue());
		}
		
		return maxDepth;
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked classes.
	 */
	public void applyCropChanges(ActionEvent ev) {
		Set<MClassifier> classifierToHide = getClassifierToHide(getSelectedPathClasses(), true);
		
		if (classifierToHide.size() > 0) {
			diagram.hideElementsInDiagram(classifierToHide, Collections.<MAssociation>emptySet());
		}
		
		Set<MClassifier> classifierToShow = getClassifierToShow(getSelectedPathClasses());
		if (classifierToShow.size() > 0) {
			diagram.showElementsInDiagram(classifierToShow);
		}
		
		diagram.invalidateContent(true);
	}

	/**
	 * Method applyShowChanges shows the appropriate marked classes.
	 */
	public void applyShowChanges(ActionEvent ev) {
		if (this.fTable.isEditing())
			this.fTable.getCellEditor().stopCellEditing();
		
		Set<MClassifier> classifierToShow = getClassifierToShow(getSelectedPathClasses());
		
		if (!classifierToShow.isEmpty()) {
			diagram.showElementsInDiagram(classifierToShow);
		}
		
		diagram.invalidateContent(true);
	}

	/**
	 * Method applyHideChanges hides the appropriate marked classes.
	 */
	public void applyHideChanges(ActionEvent ev) {
		if (this.fTable.isEditing())
			this.fTable.getCellEditor().stopCellEditing();
		
		Set<MClassifier> classifierToHide = getClassifierToHide(getSelectedPathClasses(), false);
		
		if (classifierToHide.size() > 0) {
			diagram.hideElementsInDiagram(classifierToHide, Collections.<MAssociation>emptySet());
		}
		diagram.invalidateContent(true);
	}

	public void update() {
		((ClassPathTableModel)fTableModel).update();
	}

}
