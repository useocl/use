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
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.AssociationName;
import org.tzi.use.gui.views.diagrams.DiamondNode;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;

/** 
 * a View of SelectedAssociationPath
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

@SuppressWarnings("serial")
public class SelectedAssociationPathView extends SelectedClassPathView {

	private HashSet anames;
	private JButton fBtnReset;

	/**
	 * Constructor for SelectedAssociationPathView.
	 */
	public SelectedAssociationPathView(MainWindow parent, MSystem system,
			HashSet selectedClasses, HashSet anames) {
		super(parent, system, selectedClasses);
		this.anames = anames;
		initSelectedAssociationPathView();
	}
   
	/**
	 * Method initSelectedAssociationPathView initialize the layout of the view 
	 * and add the Button "Reset", which pre-defined values can be reset.
	 */
	void initSelectedAssociationPathView(){
		fTableModel = new AssociationPathTableModel(fAttributes, fValues, anames, this); 
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
		
		for (int i = 0; i < fAttributes.size(); i++) {
			// TODO: Use Associations instead of parsing 
			String cname = fAttributes.get(i).toString().substring(0,
					fAttributes.get(i).toString().indexOf("(")).trim();
			
			Set<MClass> assclasses = getSelectedClassesOfAssociation(cname);
			int enteredValue = Integer.parseInt(fValues.get(i).toString());
			
			for (MClass mc : assclasses) {
				Map<MClass, Integer> allPathes = getAllPathClasses(mc);
				
				for (Entry<MClass, Integer> entry : allPathes.entrySet()) {
					if (entry.getValue().intValue() <= enteredValue) {
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
	public int getAssociationDepth(AssociationName aname) {

		Set<MClass> classes = getSelectedClassesOfAssociation(aname.name());
		int maxdepth = -1;
				
		for (MClass mc : classes) {
			int max = getDepth(mc);
			maxdepth = Math.max(max, maxdepth);
		}
		
		return maxdepth;
	}
	
	/**
     * Method getSelectedClassesOfAssociation calls twice Method getSelectedClassesofAssociationHS(), 
     * in order to find relevant classes both in "Hidden" and in "Show". 
	 */
	private Set<MClass> getSelectedClassesOfAssociation(String name) {
		Set<MClass> classes = getSelectedClassesofAssociationHS(name, true);
		classes.addAll(getSelectedClassesofAssociationHS(name, false));
		
		return classes;
	}
	
	/**
	 * Method getSelectedClassesOfAssociationHS returns selected classes of Association in "Show" oder "Hidden"
	 */
	private Set<MClass> getSelectedClassesofAssociationHS(String name, boolean isshow){
		Set<MClass> classes = new HashSet<MClass>();
		Iterator it;
		
		if(isshow){
			it = ClassDiagram.ffGraph.edgeIterator();
		}
		else{
			it = ClassDiagram.ffHiddenEdges.iterator();
		}
		
		boolean have = false;
		while(it.hasNext() && !have){
			Object o = it.next();
			if(isshow){
			if(o instanceof EdgeBase){
				EdgeBase edge = (EdgeBase)o;
				if(edge.getAssocName()!= null && edge.getAssocName().name().equalsIgnoreCase(name)){
					MClass mc = ((ClassNode)(edge.source())).cls();
						classes.add(mc);
						mc = ((ClassNode)(edge.target())).cls();
						if(!classes.contains(mc)){
							classes.add(mc);
						}
						return classes;
					}
				}
			}
			else{
				if(o instanceof MAssociation){
					MAssociation ma = ((MAssociation) o);
					if(ma.name().equalsIgnoreCase(name)){
						Set set = ((MAssociation) o).associatedClasses();
						Iterator itset = set.iterator();
						while(itset.hasNext()){
							MClass mc = (MClass)(itset.next());
							if(!classes.contains(mc)){
								classes.add(mc);
							}
						}
						return classes;
					}
				}
			}
		}
		if(!have){
			it = ClassDiagram.ffGraph.iterator();
			while(it.hasNext()){
				Object o = it.next();
				if(o instanceof DiamondNode){
					if(((DiamondNode)o).name().equalsIgnoreCase(name)){
						DiamondNode dnode = (DiamondNode)o;
						Set set = dnode.association().associatedClasses();
						Iterator it2 = set.iterator();
						while(it2.hasNext()){
							MClass mc = (MClass)(it2.next());
							if(!classes.contains(mc)){
								classes.add(mc);
							}
						}
						return classes;
					}
				}
			}
		}
		return classes;
	}
	
	public void update() {
		fTableModel.update();
	}
}
