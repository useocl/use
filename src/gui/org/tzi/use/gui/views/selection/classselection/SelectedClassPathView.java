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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.selection.ClassSelectionView;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a view of SelectedClassPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public class SelectedClassPathView extends ClassSelectionView {

	HashSet selectedClasses;

	private JButton fBtnReset;

	/**
	 * Constructor for SelectedClassPathView.
	 */
	public SelectedClassPathView(MainWindow parent, MSystem system,
			HashSet selectedClasses) {
		super(new BorderLayout(), parent, system);

		this.selectedClasses = selectedClasses;
		initSelectedClassPathView();
	}
    
	/**
	 * initSelectionClassPathView initialize the layout of view and add a Button "Reset", 
	 * which pre-defined values can be reset.
	 */
	void initSelectedClassPathView() {
		fTableModel = new ClassPathTableModel(fAttributes, fValues,
				selectedClasses, this);
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

	public HashSet getSelectedPathClasses() {
		HashSet classes = new HashSet();
		for (int i = 0; i < fAttributes.size(); i++) {
			String cname = fAttributes.get(i).toString().substring(0,
					fAttributes.get(i).toString().indexOf("(")).trim();

			Iterator it = selectedClasses.iterator();
			MClass mc = null;
			
			// find out, which mclass selected 
			while (it.hasNext()) { 
				mc = (MClass) (it.next());
				if (mc.name().equals(cname)) {
					break;
				}
			}
			List note[] = getAllPathClasses(mc);
			for (int j = 0; j < note[0].size(); j++) {
				if (Integer.parseInt(note[1].get(j).toString()) <= Integer
						.parseInt(fValues.get(i).toString())) {
					if (!classes.contains((MClass) (note[0].get(j)))) {
						classes.add((MClass) (note[0].get(j)));
					}
				}
			}
		}
		return classes;
	}

	/**
	 * Method getAllPathClasses return all attainable classe based on mc
	 */
	public List[] getAllPathClasses(MClass mc) {
		List[] note = new List[2];
		List classes = new ArrayList();
		List index = new ArrayList();
		note[0] = classes;
		note[1] = index;
		classes.add(mc);
		index.add(new Integer(0));

		int actual = 0;
		while (classes.size() > actual) {
			if (((MClass) classes.get(actual)).parents() != null) {
				Iterator itp = ((MClass) classes.get(actual)).parents()
						.iterator();
				while (itp.hasNext()) {
					MClass mcp = (MClass) (itp.next());
					if (!classes.contains(mcp)) {
						classes.add(mcp);
						index.add(new Integer(((Integer) (index.get(actual)))
								.intValue() + 1));
					}
				}
			}
			if (((MClass) classes.get(actual)).children() != null) {
				Iterator itc = ((MClass) classes.get(actual)).children()
						.iterator();
				while (itc.hasNext()) {
					MClass mcc = (MClass) (itc.next());
					if (!classes.contains(mcc)) {
						classes.add(mcc);
						index.add(new Integer(((Integer) (index.get(actual)))
								.intValue() + 1));
					}
				}
			}

			Iterator it = ((MClass) classes.get(actual)).associations()
					.iterator();
			while (it.hasNext()) {
				MAssociation ma = (MAssociation) (it.next());

				Iterator itt = ma.associatedClasses().iterator();
				while (itt.hasNext()) {
					MClass mcc = (MClass) (itt.next());
					if (!classes.contains(mcc)) {
						classes.add(mcc);
						index.add(new Integer(((Integer) (index.get(actual)))
								.intValue() + 1));
					}
				}
			}
			actual++;
		}
		return note;
	}

	/**
	 * Method getDepth obtaine maximally attainable Depth based on starting point(mc)
	 */
	public int getDepth(MClass mc) {
		List note[] = getAllPathClasses(mc);

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
		if (getHideClasses(getSelectedPathClasses(), true).size() > 0) {
			ClassDiagram.ffHideAdmin.setValues("Hide",
					getHideClasses(getSelectedPathClasses(), true))
					.actionPerformed(ev);
		}
		if (getShowClasses(getSelectedPathClasses()).size() > 0) {
			ClassDiagram.ffHideAdmin
					.showHiddenElements(getShowClasses(getSelectedPathClasses()));
		}
	}

	/**
	 * Method applyShowChanges shows the appropriate marked classes.
	 */
	public void applyShowChanges(ActionEvent ev) {
		if (getShowClasses(getSelectedPathClasses()).size() > 0) {
			ClassDiagram.ffHideAdmin
					.showHiddenElements(getShowClasses(getSelectedPathClasses()));
		}
	}

	/**
	 * Method applyHideChanges hides the appropriate marked classes.
	 */
	public void applyHideChanges(ActionEvent ev) {
		if (getHideClasses(getSelectedPathClasses(), false).size() > 0) {
			ClassDiagram.ffHideAdmin.setValues("Hide",
					getHideClasses(getSelectedPathClasses(), false))
					.actionPerformed(ev);
		}
	}

	public void update() {
		fTableModel.update();
	}

}
