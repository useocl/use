package org.tzi.use.gui.views.selection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
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
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagram;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramData;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassifierNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;

import com.google.common.eventbus.Subscribe;

/** 
 * A ClassSelectionView is derived from JPanel and the superclass of the three other subclasses. 
 * It defines itself as abstract and serves to define a general interface.
 *
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
@SuppressWarnings("serial")
public abstract class ClassSelectionView extends JPanel implements View {
	protected JScrollPane fTablePane;

	protected JButton fBtnShowAll;

	protected JButton fBtnHideAll;

	protected JButton fBtnCrop;

	protected JButton fBtnShow;

	protected JButton fBtnHide;

	protected JPanel buttonPane;

	protected MSystem fSystem;

	protected MainWindow fMainWindow;

	protected JTable fTable;

	protected AbstractTableModel fTableModel;

	protected ClassDiagram diagram;
	
	/**
	 * Constructor for ClassSelectionView.
	 */
	public ClassSelectionView(MainWindow parent, ClassDiagram diagram) {
		super(new BorderLayout());
		
		this.fSystem = diagram.getSystem();
		this.fMainWindow = parent;
		this.diagram = diagram;
		
		fSystem.getEventBus().register(this);
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
				if (fTable.isEditing())
					fTable.getCellEditor().stopCellEditing();
				
				applyCropChanges(e);
			}
		});

		fBtnShow = new JButton("Show");
		fBtnShow.setMnemonic('O');
		fBtnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fTable.isEditing())
					fTable.getCellEditor().stopCellEditing();
				
				applyShowChanges(e);
			}
		});

		fBtnHide = new JButton("Hide");
		fBtnHide.setMnemonic('I');
		fBtnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fTable.isEditing())
					fTable.getCellEditor().stopCellEditing();
				
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
	 * Returns all MClass objects contained in <code>classifier</code> which are hidden.
	 */
	public Set<MClassifier> getClassifierToShow(Set<? extends MClassifier> classifier) {
		Set<MClassifier> showclassifier = new HashSet<MClassifier>();
		ClassDiagramData hiddenData = (ClassDiagramData)diagram.getHiddenData();
		
		for (MClassifier cf : classifier) {
			if (hiddenData.containsNodeForClassifer(cf))
				showclassifier.add(cf);
		}
		
		return showclassifier;
	}

	/**
	 * This method calculates the classifiers that need to be hidden in the diagram.
	 * When isCrop is false, all classifiers which are currently visible and contained in <code>classifiers</code>
	 * are returned. 
	 */
	public Set<MClassifier> getClassifierToHide(Set<? extends MClassifier> classifiers, boolean isCrop) {
		Set<MClassifier> hideclasses = new HashSet<MClassifier>();

		for (PlaceableNode node : diagram.getVisibleData().getNodes()) {
			if (node instanceof ClassifierNode) {
				MClassifier cf = ((ClassifierNode)node).getClassifier();
				if (isCrop) {
					if (!classifiers.contains(cf)) {
						hideclasses.add(cf);
					}
				} else {
					if (classifiers.contains(cf)) {
						hideclasses.add(cf);
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
		diagram.hideAll();
		diagram.invalidateContent(true);
	}

	/**
	 * Method applyShowAllChanges is responsible for show all classes and associations.
	 */
	public void applyShowAllChanges(ActionEvent ev) {
		diagram.showAll();
		diagram.invalidateContent(true);
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

	@Subscribe
	public void onStatementExecuted(StatementExecutedEvent e) {
		update();
	}
	
	/**
	 * Method detachModel detaches the view from its model.
	 * 
	 * @see org.tzi.use.gui.views.View#detachModel()
	 */
	public void detachModel() {
		fSystem.getEventBus().unregister(this);
	}
}
