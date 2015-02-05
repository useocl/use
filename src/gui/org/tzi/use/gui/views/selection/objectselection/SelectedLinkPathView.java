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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.selection.TableModel.Row;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a view of SelectedLinkPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 * @author   Lars Hamann
 */
public class SelectedLinkPathView extends SelectedObjectPathView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton fBtnReset;
	
	/**
	 * Constructor for SelectedLinkPathView.
	 */
	public SelectedLinkPathView(MainWindow parent, MSystem system, DiagramViewWithObjectNode diagram, 
				Set<MObject> selectedObjects, Set<MLink> selectedLinks) {
		super(parent, system, diagram, selectedObjects);
		initSelectedAssociationPathView(selectedLinks);
	}

	/**
	 * Method initSelectedAssociationPathView initialize the layout of the view 
	 * and add the Button "Reset", which pre-defined values can be reset.
	 */
	void initSelectedAssociationPathView(Set<MLink> selectedLinks) {
		fTableModel = new LinkPathTableModel(selectedLinks, this);
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
		buttonPane.remove(buttonPane.getComponentCount() - 1);
		buttonPane.add(fBtnReset);
		add(fTablePane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	public Set<MObject> getSelectedPathObjects() {
		Set<MObject> objects = new HashSet<MObject>();
		
		for (Row<MLink> row : ((LinkPathTableModel)fTableModel).getRows()) {

			for (MObject obj : row.item.linkedObjects()) {
				Map<MObject, Integer> paths = getAllPathObjects(obj);
			
				for (Map.Entry<MObject, Integer> entry : paths.entrySet()) {
					if (entry.getValue().intValue() <= row.value) {
						objects.add(entry.getKey());
					}
				}
			}
		}
		return objects;
	}

	/**
	 * Method getLinkDepth obtain maximally attainable Depth based on starting point(aname)
	 */
	public int getLinkDepth(MLink link) {
		int maxdepth = 0;
		
		for (MObject mo : link.linkedObjects()) {
			int max = getDepth(mo);
			maxdepth = Math.max(max, maxdepth);
		}
		return maxdepth;
	}

	public void update() {
		((LinkPathTableModel)fTableModel).update();
	}

}
