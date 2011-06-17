package org.tzi.use.gui.views.selection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;

/** 
 * A ClassSelectionView is derived from JPanel and the superclass of the three other subclasses. 
 * It defines itself as abstract and serves to define a general interface.
 *
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public abstract class ClassSelectionView extends JPanel implements View {
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

	public List<String> fAttributes = new ArrayList<String>();

	public List<Object> fValues = new ArrayList<Object>();

	protected ClassDiagram diagram;
	
	/**
	 * Constructor for ClassSelectionView.
	 */
	public ClassSelectionView(BorderLayout layout, MainWindow parent, MSystem system, ClassDiagram diagram) {
		super(layout);
		
		this.fSystem = system;
		this.fMainWindow = parent;
		this.diagram = diagram;
		
		fSystem.addChangeListener(this);
		initClassSelectionView();
	}
	
    /**
     * Method initClassSelectionView initialize the layout of the View.
     *
     */
	void initClassSelectionView() {
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
	 * Returns all MClass objects from classes which are hidden.
	 */
	public Set<MClass> getClassesToShow(Set<MClass> classes) {
		Set<MClass> showclasses = new HashSet<MClass>();
		Iterator<?> itshow = diagram.getHiddenNodes().iterator();
		
		// add Instance of MClass
		while (itshow.hasNext()) {
			Object node = itshow.next();
			if (node instanceof MClass) {
				MClass cls = (MClass) node;
				if (classes.contains(cls)) {
					showclasses.add(cls);
				}
			}
		}
		
		return showclasses;
	}

	/**
	 * Method getHideClasses takes two parameters: HashSet and a boolean value. 
	 * The boolean value "true" means that the function "crop" is selected.
	 */
	public Set<MClass> getHideClasses(Set<MClass> classes, boolean isCrop) {
		Set<MClass> hideclasses = new HashSet<MClass>();
		Iterator<?> ithide = diagram.getGraph().iterator();
		
		// add Instance of MClass
		while (ithide.hasNext()) {
			Object node = ithide.next();
			if (node instanceof ClassNode) {
				MClass cls = ((ClassNode) node).cls();
				if (isCrop) {
					if (!classes.contains(cls)) {
						hideclasses.add(cls);
					}
				} else {
					if (classes.contains(cls)) {
						hideclasses.add(cls);
					}
				}
			}
		}
		return hideclasses;
	}

	/**
	 * Method applyHideAllChanges is responsible for hiding all classes and associations.
	 */
	public void applyHideAllChanges(ActionEvent ev) {
		Iterator<?> it = diagram.getGraph().iterator();
		
		Set<MClass> hideClass = new HashSet<MClass>();
		while (it.hasNext()) {
			Object node = it.next();
			if (node instanceof ClassNode) {
				MClass cls = ((ClassNode) node).cls();
				hideClass.add(cls);
			}
		}

		diagram.getHideAdmin().getAction("Hide all classes", hideClass).actionPerformed(ev);
	}

	/**
	 * Method applyShowAllChanges is responsible for show all classes and associations.
	 */
	public void applyShowAllChanges(ActionEvent ev) {
		diagram.getHideAdmin().showAllHiddenElements();
		MainWindow.instance().repaint();
	}

	/**
	 * Method applyCropChanges show only the appropriate marked classes or associations, 
	 * others is hidden.
	 */
	public abstract void applyCropChanges(ActionEvent ev);

	/**
	 * Method applyShowChanges shows the appropriate marked Classes or associations. 
	 */
	public abstract void applyShowChanges(ActionEvent ev);

	/**
	 * Method applyHideChanges hides the appropriate marked Classes or Associationen. 
	 */
	public abstract void applyHideChanges(ActionEvent ev);
	
    /**
     * Method updates the data of the table, if the condition in the respective opinion is changed.
     */
	public abstract void update();

	/**
	 * Method stateChanged called due to an external change of state.
	 * 
	 * @see org.tzi.use.uml.sys.StateChangeListener#stateChanged(StateChangeEvent)
	 */
	public void stateChanged(StateChangeEvent e) {
		update();
	}

	/**
	 * Method detachModel detaches the view from its model.
	 * 
	 * @see org.tzi.use.gui.views.View#detachModel()
	 */
	public void detachModel() {
		fSystem.removeChangeListener(this);
	}
}
