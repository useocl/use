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
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkSet;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a view of SelectedLinkPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class SelectedLinkPathView extends SelectedObjectPathView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<AssociationName> anames;

	private JButton fBtnReset;
	
	/**
	 * Constructor for SelectedLinkPathView.
	 */
	public SelectedLinkPathView(MainWindow parent, MSystem system, NewObjectDiagram diagram, 
				Set<MObject> selectedObjects, Set<AssociationName> anames) {
		super(parent, system, diagram, selectedObjects);
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

	public Set<MObject> getSelectedPathObjects() {
		Set<MObject> objects = new HashSet<MObject>();
		for (int i = 0; i < fAttributes.size(); i++) {
			String cname = fAttributes.get(i).toString().substring(0,
					fAttributes.get(i).toString().indexOf("(")).trim();

			Iterator<MObject> it = getSelectedObjectsOfLink(cname).iterator();
			MObject mo = null;
						
			while (it.hasNext()) {
				mo = it.next();
				if (mo.name().equals(cname)) {
					break;
				}
			}
			
			Map<MObject, Integer> paths = getAllPathObjects(mo);
			for (Map.Entry<MObject, Integer> entry : paths.entrySet()) {
				if (entry.getValue().intValue() <= Integer.parseInt(fValues.get(i).toString())) {
					objects.add(entry.getKey());
				}
			}
		}
		return objects;
	}

	/**
	 * Method getLinkDepth obtain maximally attainable Depth based on starting point(aname)
	 */
	public int getLinkDepth(AssociationName aname) {
		Set<MObject> objects = getSelectedObjectsOfLink(aname.name());
		int maxdepth = 0;
		
		for (MObject mo : objects) {
			int max = getDepth(mo);
			maxdepth = Math.max(max, maxdepth);
		}
		return maxdepth;
	}

	/**
	 * Returns all objects which are linked by the association named <code>associationName</code>.
	 * @param associationName
	 * @return
	 */
	private Set<MObject> getSelectedObjectsOfLink(String associationName) {
		Set<MObject> objects = new HashSet<MObject>();
		MLinkSet linkSet = fSystem.state().linksOfAssociation(fSystem.model().getAssociation(associationName));
		
		for (MLink link : linkSet.links()) { 
			objects.addAll(link.linkedObjects());
		}
		
		return objects;
	}

	public void update() {
		fTableModel.update();
	}

}
