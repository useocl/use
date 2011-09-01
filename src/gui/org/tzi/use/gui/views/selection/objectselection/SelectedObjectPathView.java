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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.selection.ObjectSelectionView;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a view of SelectedObjectPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public class SelectedObjectPathView extends ObjectSelectionView {

	Set<MObject> selectedObjects;

	private JButton fBtnReset;

	/**
	 * Constructor for SelectedObjectPathView.
	 */
	public SelectedObjectPathView(MainWindow parent, MSystem system, NewObjectDiagram diagram, Set<MObject> selectedObjects) {
		super(new BorderLayout(), parent, system, diagram);

		this.selectedObjects = selectedObjects;
		initSelectedClassPathView();
	}

	/**
	 * Method initSelectedClassPathView initialize the layout of the view 
	 * and add the Button "Reset", which pre-defined values can be reset.
	 */
	void initSelectedClassPathView() {
		fTableModel = new ObjectPathTableModel(fAttributes, fValues,
				selectedObjects, this);
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
		buttonPane.add(fBtnReset);

		add(fTablePane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	public Set<MObject> getSelectedPathObjects() {
		Set<MObject> objects = new HashSet<MObject>();
		
		for (int i = 0; i < fAttributes.size(); i++) {
			String cname = fAttributes.get(i).toString().substring(0,
					fAttributes.get(i).toString().indexOf("(")).trim();

			//find out, which MObject is selected 
			MObject selected = null;
			for (MObject mo	: selectedObjects) {
				if (mo.name().equals(cname)) {
					selected = mo;
					break;
				}
			}
			
			Map<MObject, Integer> paths = getAllPathObjects(selected);
			for (Map.Entry<MObject, Integer> entry : paths.entrySet()) {
				if (entry.getValue().intValue() <= Integer.parseInt(fValues.get(i).toString())) {
					objects.add(entry.getKey());
				}
			}
		}
	
		return objects;
	}

	/**
	 * Method getAllPathObjects return all attainable objects based on mo
	 */
	public Map<MObject, Integer> getAllPathObjects(MObject mo) {
		Map<MObject, Integer> result = new HashMap<MObject, Integer>();
		
		// For FIFO handling of remaining classes to process
		LinkedList<MObject> buffer = new LinkedList<MObject>();
		
		buffer.addLast(mo);
		result.put(mo, new Integer(0));
		
		MObject currentObject;
		int depth;

		Set<MLink> allLinks = fSystem.state().allLinks();
		
		while (buffer.size() > 0) {
			currentObject = buffer.poll();
			depth = result.get(currentObject).intValue() + 1;

			for (MLink link : allLinks) {
				List<MObject> linkedobjects = link.linkedObjects();

				if (linkedobjects.contains(currentObject)) {
					for (MObject object : linkedobjects) {
						if (!result.containsKey(object)) {
							result.put(object, new Integer(depth));
							// Check objects reachable by this object
							buffer.add(object);
						}
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Method getDepth obtaine maximally attainable Depth based on starting point(mo)
	 */
	public int getDepth(MObject mo) {
		Map<MObject, Integer> paths = getAllPathObjects(mo);
		
		int maxdepth = 0;
		for (Integer depth : paths.values()) {
			maxdepth = Math.max(maxdepth, depth.intValue());
		}
		
		return maxdepth;
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked classes.
	 */
	public void applyCropChanges(ActionEvent ev) {
		Set<MObject> selectedPathObjects = getSelectedPathObjects();
		
		this.diagram.hideAll();
		this.diagram.showObjects(selectedPathObjects);
		this.diagram.invalidateContent();
	}

	/**
	 * Method applyShowChanges shows the appropriate marked classes.
	 */
	public void applyShowChanges(ActionEvent ev) {
		this.diagram.showObjects(getSelectedPathObjects());
		this.diagram.invalidateContent();
	}

	/**
	 * Method applyHideChanges hides the appropriate marked classes.
	 */
	public void applyHideChanges(ActionEvent ev) {
		this.diagram.hideObjects(getSelectedPathObjects());
		this.diagram.invalidateContent();
	}

	public void update() {
		fTableModel.update();
	}

}
