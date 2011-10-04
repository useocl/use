
package org.tzi.use.gui.views.selection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.NodeBase;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public AbstractTableModel fTableModel;

	protected NewObjectDiagram diagram;
	
	public ObjectSelectionView(MainWindow parent, MSystem system, NewObjectDiagram diagram) {
		super(new BorderLayout());
		this.fSystem = system;
		this.fMainWindow = parent;
		this.diagram = diagram;
		
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
	 * Shows all objects and links.
	 */
	public void applyShowAllChanges(ActionEvent ev) {
		this.diagram.showAll();
		this.diagram.invalidateContent();
	}

	/**
	 * Hides all objects and links.
	 */
	public void applyHideAllChanges(ActionEvent ev) {
		Iterator<NodeBase> it = this.diagram.getGraph().iterator();
		Set<MObject> hideojects = new HashSet<MObject>();
		
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ObjectNode) {
				MObject mo = ((ObjectNode) node).object();
				hideojects.add(mo);
			}
		}
		
		this.diagram.getAction("Hide all objects", hideojects).actionPerformed(ev);
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
