package org.tzi.use.gui.plugins;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.PopupListener;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.Log;

/**
 * A view showing all objects with their role in an association. This View is
 * adapted from the class extent view already provided in USE.
 * 
 * @author Roman Asendorf
 */
public class AssociationExtentView extends JPanel implements View,
		ActionListener {

	private final MSystem fSystem;

	private MAssociation fAssociation;

	private final JPopupMenu fPopupMenu; // context menu on right mouse click

	private final JTable fTable;

	private final JScrollPane fTablePane;

	private final TableModel fTableModel;

	public AssociationExtentView(MainWindow parent, MSystem system) {
		super(new BorderLayout());

		this.fSystem = system;
		this.fSystem.addChangeListener(this);

		// get an association
		Collection associations = this.fSystem.model().associations();
		if (!associations.isEmpty()) {
			this.fAssociation = (MAssociation) associations.iterator().next();
		}

		// create table of rolename / object pairs
		this.fTableModel = new TableModel();
		this.fTable = new JTable(this.fTableModel);
		this.fTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
		this.fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.fTablePane = new JScrollPane(this.fTable);
		fitWidth();

		// create the popup menu for options
		this.fPopupMenu = new JPopupMenu();

		JMenu submenu = new JMenu("Select association");
		// add all association to submenu
		Object[] c = associations.toArray();
		Arrays.sort(c);
		JMenu sub = submenu;
		for (int i = 0; i < c.length; i++) {
			if (i > 0 && i % 30 == 0) {
				// nested submenus for large model
				JMenu s = new JMenu("More...");
				sub.add(s);
				sub = s;
			}
			JMenuItem mi = new JMenuItem(((MAssociation) c[i]).name());
			mi.addActionListener(this);
			sub.add(mi);
		}
		this.fPopupMenu.add(submenu);
		this.fTable.addMouseListener(new PopupListener(this.fPopupMenu));
		this.fTablePane.addMouseListener(new PopupListener(this.fPopupMenu));

		// layout panel
		add(this.fTablePane, BorderLayout.CENTER);
	}

	void fitWidth() {

		TableColumnModel colModel = this.fTable.getColumnModel();

		for (int i = 0; i < colModel.getColumnCount(); i++) {
			TableColumn col = colModel.getColumn(i);
			int width = 0;

			TableCellRenderer renderer = col.getHeaderRenderer();
			if (renderer == null) {
				renderer = this.fTable.getTableHeader().getDefaultRenderer();
			}

			Component comp = renderer.getTableCellRendererComponent(
					this.fTable, col.getHeaderValue(), false, false, 0, 0);
			width = comp.getPreferredSize().width;

			for (int r = 0; r < this.fTable.getRowCount(); r++) {
				renderer = this.fTable.getCellRenderer(r, i);
				comp = renderer.getTableCellRendererComponent(this.fTable,
						this.fTable.getValueAt(r, i), false, false, r, i);
				width = Math.max(width, comp.getPreferredSize().width);
			}
			col.setPreferredWidth(width + 10);
		}
	}

	/**
	 * Called when an association was selected in the popup menu.
	 */
	public void actionPerformed(ActionEvent e) {
		// System.err.println("*** ActionEvent = " + e.getActionCommand());
		String name = e.getActionCommand();
		// see if there's really a change
		if (this.fAssociation.name().equals(name))
			return;

		this.fAssociation = this.fSystem.model().getAssociation(name);
		this.fTableModel.initStructure();
		this.fTableModel.initModel();
		this.fTableModel.fireTableStructureChanged();
		this.fTableModel.fireTableDataChanged();
		fitWidth();
	}

	/**
	 * The table model.
	 */
	class TableModel extends AbstractTableModel {
		private final ArrayList fColumnNames;

		private final ArrayList fLinks;

		private MClass[] fClasses;

		private final Map fObjectValueStrMap; // (MObject -> String)

		// need to cache all data because getValueAt is called often.

		TableModel() {
			this.fLinks = new ArrayList();
			this.fColumnNames = new ArrayList();
			this.fObjectValueStrMap = new HashMap();
			initStructure();
			initModel();
		}

		void initStructure() {
			this.fColumnNames.clear();
			if (fAssociation == null)
				return;

			// prepare table: get associated classes
			Set classes = fAssociation.associatedClasses();
			int n = classes.size();

			Iterator classIterator = classes.iterator();

			this.fClasses = new MClass[n];
			System.arraycopy(classes.toArray(), 0, this.fClasses, 0, n);
			Arrays.sort(this.fClasses);

			// set columns
			this.fColumnNames.add(fAssociation.name());
			for (int i = 0; i < this.fClasses.length; i++) {
				Set assocEnds = fAssociation
						.associationEndsAt(this.fClasses[i]);
				Iterator assocEndIter = assocEnds.iterator();
				while (assocEndIter.hasNext()) {
					MAssociationEnd assocEnd = (MAssociationEnd) assocEndIter
							.next();
					Log.debug("AssocEnd name as rolename"
							+ assocEnd.nameAsRolename());
					this.fColumnNames.add(assocEnd.nameAsRolename());
				}
			}
		}

		void initModel() {
			// initialize object list
			this.fLinks.clear();
			this.fObjectValueStrMap.clear();
			if (fAssociation == null)
				return;

			// get names for all objects
			Iterator links = fSystem.state().linksOfAssociation(fAssociation)
					.links().iterator();
			while (links.hasNext()) {
				MLink currentLink = (MLink) links.next();
				// Set linkedobj = currentLink.linkedObjects();
				addLink(currentLink);
			}
			sortRows();
		}

		@Override
		public String getColumnName(int col) {
			return (String) this.fColumnNames.get(col);
		}

		public int getColumnCount() {
			return this.fColumnNames.size();
		}

		public int getRowCount() {
			return this.fLinks.size();
		}

		@Override
		public Class getColumnClass(int col) {
			if (col <= this.fColumnNames.size())
				return String.class;
			else
				return Icon.class;
		}

		public Object getValueAt(int row, int col) {
			// Log.debug("*** getValueAt (" + row + ", " + col + ")");
			MLink link = (MLink) this.fLinks.get(row);
			if (col == 0)
				return "-";// link.association().name();
			else if (col <= this.fColumnNames.size()) {
				String[] values = (String[]) this.fObjectValueStrMap.get(link);
				return values[col - 1];
			} else
				return "/";
		}

		/**
		 * Adds a row for an object to the table.
		 */
		void addLink(MLink link) {
			this.fLinks.add(link);
			updateLink(link);
		}

		/**
		 * Updates the row for the given link.
		 */
		void updateLink(MLink link) {
			MObject[] linkedObj = link.linkedObjectsAsArray();
			String[] values = new String[linkedObj.length];
			Log.debug("Linked Objects cnt [" + linkedObj.length
					+ "] , Classes cnt [" + fClasses.length + "]");
			if (linkedObj.length > fClasses.length) {
				Log.debug("Reflexive Association recognized!");
				for (int k = 0; k < linkedObj.length; k++) {

					values[k] = (linkedObj[k]).name();
					Log.debug("Reflexive Association adding ["
							+ (linkedObj[k]).name() + "] at Position [" + k
							+ "]");
				}
			} else {
				for (int i = 0; i < this.fClasses.length; i++) {
					MClass currentClass = this.fClasses[i];
					for (int k = 0; k < linkedObj.length; k++) {
						if (currentClass.equals(linkedObj[k].cls())) {
							values[i] = (linkedObj[k]).name();
							Log.debug("Normal Association adding ["
									+ (linkedObj[k]).name() + "] at Position ["
									+ i + "]");
						}
					}
				}
			}
			this.fObjectValueStrMap.put(link, values);
		}

		/**
		 * Removes a row for an object from the table.
		 */
		void removeObject(MObject obj) {
			this.fLinks.remove(obj);
			this.fObjectValueStrMap.remove(obj);
		}

		void sortRows() {
			Collections.sort(this.fLinks, new Comparator() {
				public int compare(Object o1, Object o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
		}
	}

	/**
	 * Called due to an external change of state.
	 */
	public void stateChanged(StateChangeEvent e) {
		Iterator it;

		it = e.getDeletedObjects().iterator();
		while (it.hasNext()) {
			MObject obj = (MObject) it.next();
			if (obj.cls().equals(this.fAssociation))
				this.fTableModel.removeObject(obj);
		}

		// change in number of rows?
		if (e.structureHasChanged())
			this.fTableModel.sortRows();
		this.fTableModel.fireTableDataChanged();
	}

	/**
	 * Detaches the view from its model.
	 */
	public void detachModel() {
		this.fSystem.removeChangeListener(this);
	}
}
