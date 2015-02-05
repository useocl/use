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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.selection.TableModel.Row;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;

/** 
 * a View of SelectedAssociationPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

@SuppressWarnings("serial")
public class SelectedAssociationPathView extends SelectedClassPathView {

	private JButton fBtnReset;
	
	/**
	 * Constructor for SelectedAssociationPathView.
	 */
	public SelectedAssociationPathView(MainWindow parent, ClassDiagram diagram, Set<MClass> selectedClasses, Set<MAssociation> selectedAssociations) {
		super(parent, diagram, new AssociationPathTableModel(selectedAssociations));
	}
   
	/**
	 * Method initSelectedAssociationPathView initialize the layout of the view 
	 * and add the Button "Reset", which pre-defined values can be reset.
	 */
	void initView(AbstractTableModel model) {
		fTableModel = model; 
		fTable = new JTable(fTableModel); 
		fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
		fTablePane = new JScrollPane(fTable);
		
		fBtnReset = new JButton("Reset");
		fBtnReset.setMnemonic('R');
		fBtnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		buttonPane.remove(buttonPane.getComponentCount()-1);
		buttonPane.add(fBtnReset);
		add(fTablePane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	public Set<MClass> getSelectedPathClasses() {		
		Set<MClass> classes = new HashSet<MClass>();
		AssociationPathTableModel model = (AssociationPathTableModel)this.fTableModel;

		for (Row<MAssociation> row : model.getRows()) {
			Set<MClass> assclasses = row.item.associatedClasses();
			
			for (MClass mc : assclasses) {
				Map<MClass, Integer> allPathes = getAllPathClasses(mc);
				
				for (Entry<MClass, Integer> entry : allPathes.entrySet()) {
					if (entry.getValue().intValue() <= row.value) {
						classes.add(entry.getKey());
					}
				}
			}
		}
		
		return classes;
	}
	
	/**
	 * Method getAssociationDepth obtains maximally attainable Depth based on starting point(aname)
	 */
	public static int getAssociationDepth(MAssociation assoc) {
		int maxdepth = -1;
				
		for (MClass mc : assoc.associatedClasses()) {
			int max = getDepth(mc);
			maxdepth = Math.max(max, maxdepth);
		}
		
		return maxdepth;
	}
	
	public void update() {
		((AssociationPathTableModel)fTableModel).update();
	}
}
