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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

			Iterator<MObject> it = selectedObjects.iterator();
			MObject mo = null;
			
			//find out, which MObject is selected 
			while (it.hasNext()) {
				mo = it.next();
				if (mo.name().equals(cname)) {
					break;
				}
			}
			
			List note[] = getAllPathObjects(mo);
			for (int j = 0; j < note[0].size(); j++) {
				if (Integer.parseInt(note[1].get(j).toString()) <= Integer
						.parseInt(fValues.get(i).toString())) {
					if (!objects.contains((MObject) (note[0].get(j)))) {
						objects.add((MObject) (note[0].get(j)));
					}
				}
			}
		}
		return objects;
	}

	/**
	 * Method getAllPathObjects return all attainable objects based on mo
	 */
	public List[] getAllPathObjects(MObject mo) {
		List[] note = new List[2];
		List<MObject> objects = new ArrayList<MObject>();
		List<Integer> index = new ArrayList<Integer>();
		note[0] = objects;
		note[1] = index;
		objects.add(mo);
		index.add(new Integer(0));

		int actual = 0;
		Set<MLink> allLinks = fSystem.state().allLinks();
		
		while (objects.size() > actual) {
			for (MLink link : allLinks) {
				Set<MObject> linkedobjects = link.linkedObjects();

				if (linkedobjects.contains(objects.get(actual))) {
					for (MObject object : linkedobjects) {
						if (!objects.contains(object)) {
							objects.add(object);
							index
									.add(new Integer(((Integer) (index
											.get(actual))).intValue() + 1));
						}
					}
				}
			}
			actual++;
		}
		return note;
	}

	/**
	 * Method getDepth obtaine maximally attainable Depth based on starting point(mo)
	 */
	public int getDepth(MObject mo) {
		List note[] = getAllPathObjects(mo);
		int maxdepth = 0;
		for (int i = 0; i < note[1].size(); i++) {
			if (maxdepth < ((Integer) (note[1].get(i))).intValue()) {
				maxdepth = ((Integer) (note[1].get(i))).intValue();
			}
		}
		return maxdepth;
	}

	/**
	 * Method applyCropChanges shows only the appropriate marked classes.
	 */
	public void applyCropChanges(ActionEvent ev) {
		if (getHideObjects(getSelectedPathObjects(), true).size() > 0) {
			this.diagram.getHideAdmin().setValues("Hide",
					getHideObjects(getSelectedPathObjects(), true))
					.actionPerformed(ev);
		}
		if (getShowObjects(getSelectedPathObjects()).size() > 0) {
			this.diagram.getHideAdmin()
					.showHiddenElements(getShowObjects(getSelectedPathObjects()));
		}
	}

	/**
	 * Method applyShowChanges shows the appropriate marked classes.
	 */
	public void applyShowChanges(ActionEvent ev) {
		if (getShowObjects(getSelectedPathObjects()).size() > 0) {
			this.diagram.getHideAdmin()
					.showHiddenElements(getShowObjects(getSelectedPathObjects()));
		}
	}

	/**
	 * Method applyHideChanges hides the appropriate marked classes.
	 */
	public void applyHideChanges(ActionEvent ev) {
		if (getHideObjects(getSelectedPathObjects(), false).size() > 0) {
			this.diagram.getHideAdmin().setValues("Hide",
					getHideObjects(getSelectedPathObjects(), false))
					.actionPerformed(ev);
		}
	}

	public void update() {
		fTableModel.update();
	}

}
