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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.BinaryEdge;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a view of SelectedLinkPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class SelectedLinkPathView extends SelectedObjectPathView {

	private Set anames;

	private JButton fBtnReset;

	/**
	 * Constructor for SelectedLinkPathView.
	 */
	public SelectedLinkPathView(MainWindow parent, MSystem system,
			Set selectedClasses, Set anames) {
		super(parent, system, selectedClasses);
		this.anames = anames;
		initSelectedAssociationPathView();
	}

	/**
	 * Method initSelectedAssociationPathView initialize the layout of the view 
	 * and add the Button "Reset", which pre-defined values can be reset.
	 */
	void initSelectedAssociationPathView() {
		fTableModel = new LinkPathTableModel(fAttributes, fValues, anames, this);
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

	public HashSet getSelectedPathObjects() {
		HashSet objects = new HashSet();
		for (int i = 0; i < fAttributes.size(); i++) {
			String cname = fAttributes.get(i).toString().substring(0,
					fAttributes.get(i).toString().indexOf("(")).trim();

			Iterator it = getSelectedObjectsOfLink(cname).iterator();
			MObject mo = null;
			int maxdepth = Integer.parseInt(fValues.get(i).toString());
			while (it.hasNext()) {
				mo = (MObject) (it.next());
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
	 * Method getLinkDepth obtaine maximally attainable Depth based on starting point(aname)
	 */
	public int getLinkDepth(AssociationName aname) {
		HashSet objects = getSelectedObjectsOfLink(aname.name());
		int maxdepth = -1;
		Iterator it = objects.iterator();
		while (it.hasNext()) {
			MObject mo = (MObject) (it.next());
			int max = getDepth(mo);
			if (maxdepth < 0 || maxdepth > max) {
				maxdepth = max;
			}
		}
		return maxdepth;
	}

	/**
     * Method getSelectedObjectsOfLink calls twice Method getSelectedObjectsOfLinkHS(), 
     * in order to find relevant objects both in "Hidden" and in "Show". 
	 */
	private HashSet getSelectedObjectsOfLink(String name) {
		HashSet objects = new HashSet();
		objects = getSelectedObjectsOfLinkHS(name, objects, true);
		objects.addAll(getSelectedObjectsOfLinkHS(name, objects, false));
		return objects;
	}

	/**
	 * Method getSelectedObjectsOfLinkHS returns selected objects of Association in "Show" oder "Hidden"
	 */
	private HashSet getSelectedObjectsOfLinkHS(String node,
			HashSet selectedObjectsOfLink, boolean isshow) {
		HashSet objects = new HashSet();
		Iterator it;
		if (isshow) {
			it = NewObjectDiagram.ffGraph.edgeIterator();
		} else {
			it = NewObjectDiagram.ffHiddenEdges.iterator();
		}
		String name = node;
		boolean have = false;
		while (it.hasNext() && !have) {
			Object o = it.next();
			if (o instanceof BinaryEdge) {
				BinaryEdge edge = (BinaryEdge) o;
				if (edge.getAssocName() != null
						&& edge.getAssocName().name().equals(name)) {
					MObject mo = ((ObjectNode) (edge.source())).object();
					if (!selectedObjectsOfLink.contains(mo)) {
						objects.add(mo);
					}
					mo = ((ObjectNode) (edge.target())).object();
					if (!selectedObjectsOfLink.contains(mo)
							&& !objects.contains(mo)) {
						objects.add(mo);
					}
					return objects;
				}
			}
		}
		if (!have) {
			if (isshow) {
				it = NewObjectDiagram.ffGraph.iterator();
			} else {
				it = NewObjectDiagram.ffHiddenNodes.iterator();
			}
			while (it.hasNext()) {
				Object o = it.next();
				if (o instanceof DiamondNode) {
					if (((DiamondNode) o).name().equalsIgnoreCase(name)) {
						DiamondNode dnode = (DiamondNode) o;
						Set set = dnode.link().linkedObjects();
						Iterator it2 = set.iterator();
						while (it2.hasNext()) {
							MObject mo = (MObject) (it2.next());
							if (!selectedObjectsOfLink.contains(mo)
									&& !objects.contains(mo)) {
								objects.add(mo);
							}
						}
						return objects;
					}
				}
			}
		}
		return objects;
	}

	public void update() {
		fTableModel.update();
	}

}
