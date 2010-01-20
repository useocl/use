
package org.tzi.use.gui.views.selection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;

/** 
 * A ObjectSelectionView is derived from JPanel and the superclass of the three other subclasses. 
 * It defines itself as abstract class and serves to define a general interface.
 *
 * @author   Jun Zhang 
 * @author   Jie Xu
 */

public abstract class ObjectSelectionView extends JPanel implements View{
	public JScrollPane fTablePane;

	public JButton fBtnShowAll;

	public JButton fBtnHideAll;

	public JButton fBtnCrop;

	public JButton fBtnShow;

	public JButton fBtnHide;
	
	public JPanel buttonPane;

	public MSystem fSystem;

	public MainWindow fMainWindow;

	public JTable fTable;

	public TableModel fTableModel;

	public List fAttributes = new ArrayList();

	public List fValues = new ArrayList();

	
	public ObjectSelectionView(BorderLayout layout, MainWindow parent, MSystem system){
		super(layout);
		this.fSystem = system;
		fMainWindow = parent;
		fSystem = system;
		fSystem.addChangeListener(this);
		initClassSelectionView();
	}
	
	/**
	 * Method initObjectSelectionView initialize the layout of the View.
	 *
	 */	
	void initClassSelectionView(){
		fBtnShowAll = new JButton("Show all");
		fBtnShowAll.setMnemonic('S');
		fBtnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyShowAllChanges(e);
				repaint();
			}
		});

		fBtnHideAll = new JButton("Hide all");
		fBtnHideAll.setMnemonic('H');
		fBtnHideAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyHideAllChanges(e);
			}
		});

		fBtnCrop = new JButton("Crop");
		fBtnCrop.setMnemonic('C');
		fBtnCrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyCropChanges(e);
			}
		});

		fBtnShow = new JButton("Show");
		fBtnShow.setMnemonic('O');
		fBtnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyShowChanges(e);
			}
		});

		fBtnHide = new JButton("Hide");
		fBtnHide.setMnemonic('I');
		fBtnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyHideChanges(e);
			}
		});
		
		// layout the buttons centered from left to right
		buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonPane.add(Box.createHorizontalGlue());

		buttonPane.add(fBtnShowAll);
		buttonPane.add(fBtnHideAll);

		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(fBtnCrop);
		buttonPane.add(fBtnShow);
		buttonPane.add(fBtnHide);

		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(Box.createHorizontalGlue());
	}

	/**
	 * Method getShowObjects takes a HashSet as parameter, 
	 * which defines itself as a set of the class MObject. 
	 */
	public HashSet getShowObjects(HashSet objects) {
		HashSet showobjects = new HashSet();
		Iterator itshow = NewObjectDiagram.ffHiddenNodes.iterator(); 
																	
		while (itshow.hasNext()) {
			Object node = itshow.next();
			if (node instanceof MObject) {
				MObject mo = (MObject) node;
				if (objects.contains(mo)) {
					showobjects.add(mo);
				}
			}
		}
		return showobjects;
	}
	
	/**
	 * Method getHideObjects takes two parameters: HashSet and a boolean value. 
	 * The boolean value "true" means that the function "crop" is selected.
	 */	
	public HashSet getHideObjects(HashSet objects, boolean isCrop) {
		HashSet hideobjects = new HashSet();
		Iterator ithide = NewObjectDiagram.ffGraph.iterator(); 
																
		while (ithide.hasNext()) {
			Object node = ithide.next();
			if (node instanceof ObjectNode) {
				MObject mo = ((ObjectNode) node).object();
				if(isCrop){
					if (!objects.contains(mo)) {
						hideobjects.add(mo);
					}
				}
				else{
					if (objects.contains(mo)) {
						hideobjects.add(mo);
					}
				}
			}
		}
		return hideobjects;
	}
	
	/**
	 * Method applyShowAllChange is responsible for show all objects and Links.
	 */
	public void applyShowAllChanges(ActionEvent ev) {
		NewObjectDiagram.ffHideAdmin.showAllHiddenElements();
		MainWindow.instance().repaint();
	}

	/**
	 * Method applyHideAllChange is responsible for hiding all objects and Links.
	 */
	public void applyHideAllChanges(ActionEvent ev) {
		Iterator it = NewObjectDiagram.ffGraph.iterator();
		HashSet hideojects = new HashSet();
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ObjectNode) {
				MObject mo = ((ObjectNode) node).object();
				hideojects.add(mo);
			}
		}
		NewObjectDiagram.ffHideAdmin.setValues("Hide all objects", hideojects)
				.actionPerformed(ev);
	}
	
	public abstract void applyCropChanges(ActionEvent ev);
	public abstract void applyShowChanges(ActionEvent ev);
	public abstract void applyHideChanges(ActionEvent ev);
	public abstract void update();
	
	/**
	 * Method stateChanged called due to an external change of state.
	 */
	public void stateChanged(StateChangeEvent e) {
		update();
	}

	/**
	 * Method detachModel detaches the view from its model.
	 */
	public void detachModel() {
		fSystem.removeChangeListener(this);
	}
}
