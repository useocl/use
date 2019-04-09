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
import java.util.List;
import java.util.Map;

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
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;

import com.google.common.eventbus.Subscribe;

/**
 * A view showing all objects with their role in an association. This View is
 * adapted from the class extent view already provided in USE.
 *
 * @author Roman Asendorf
 * @author Frank Hilken
 */
@SuppressWarnings("serial")
public class AssociationExtentView extends JPanel implements View {

	private final MSystem fSystem;
	private MAssociation fAssociation;
	private final JPopupMenu fPopupMenu;
	private final JTable fTable;
	private final JScrollPane fTablePane;
	private final AssociationExtentTableModel fTableModel;

	public AssociationExtentView(MainWindow parent, MSystem system) {
		super(new BorderLayout());

		fSystem = system;

		// try to get an association
		Collection<MAssociation> associations = fSystem.model().associations();
		if (!associations.isEmpty()) {
			fAssociation = associations.iterator().next();
		}

		// create table of rolename / object pairs
		fTableModel = new AssociationExtentTableModel();
		fTable = new JTable(fTableModel);
		fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
		fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fTablePane = new JScrollPane(fTable);
		fitWidth();

		// create the popup menu for options
		fPopupMenu = new JPopupMenu();

		JMenu submenu = new JMenu("Select association");
		// reuse action listener
		final ActionListener popupAL = new ActionListener() {
			/**
			 * Called when an association was selected in the popup menu.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = e.getActionCommand();
				// see if there's really a change
				if (fAssociation.name().equals(name)){
					return;
				}

				fAssociation = fSystem.model().getAssociation(name);
				fTableModel.initStructure();
				fTableModel.initModel();
				fTableModel.fireTableStructureChanged();
				fTableModel.fireTableDataChanged();
				fitWidth();
			}
		};
		// add all associations to submenu
		MAssociation[] c = new MAssociation[associations.size()];
		associations.toArray(c);
		Arrays.sort(c);
		JMenu sub = submenu;
		for (int i = 0; i < c.length; i++) {
			if (i > 0 && i % 30 == 0) {
				// nested submenus for large model
				JMenu s = new JMenu("More...");
				sub.add(s);
				sub = s;
			}
			JMenuItem mi = new JMenuItem(c[i].name());
			mi.addActionListener(popupAL);
			sub.add(mi);
		}
		fPopupMenu.add(submenu);
		fTable.addMouseListener(new PopupListener(fPopupMenu));
		fTablePane.addMouseListener(new PopupListener(fPopupMenu));

		// layout panel
		add(fTablePane, BorderLayout.CENTER);

		fSystem.getEventBus().register(this);
	}

	void fitWidth() {
		TableColumnModel colModel = fTable.getColumnModel();

		for (int i = 0; i < colModel.getColumnCount(); i++) {
			TableColumn col = colModel.getColumn(i);
			int width = 0;

			TableCellRenderer renderer = col.getHeaderRenderer();
			if (renderer == null) {
				renderer = fTable.getTableHeader().getDefaultRenderer();
			}

			Component comp = renderer.getTableCellRendererComponent(fTable, col.getHeaderValue(), false, false, 0, 0);
			width = comp.getPreferredSize().width;

			for (int r = 0; r < fTable.getRowCount(); r++) {
				renderer = fTable.getCellRenderer(r, i);
				comp = renderer.getTableCellRendererComponent(fTable,
						fTable.getValueAt(r, i), false, false, r, i);
				width = Math.max(width, comp.getPreferredSize().width);
			}
			col.setPreferredWidth(width + 10);
		}
	}

	/**
	 * The table model.
	 */
	class AssociationExtentTableModel extends AbstractTableModel {
		private MAssociationEnd[] assocEnds;
		private final ArrayList<MLink> fLinks;
		private final Map<MLink, String[]> fObjectValueStrMap;

		// cache all data because getValueAt is called often.
		AssociationExtentTableModel() {
			fLinks = new ArrayList<MLink>();
			fObjectValueStrMap = new HashMap<MLink, String[]>();
			initStructure();
			initModel();
		}

		void initStructure() {
			if (fAssociation == null){
				return;
			}

			// keep the order, which should be use file order
			List<MAssociationEnd> aEnds = fAssociation.associationEnds();
			assocEnds = aEnds.toArray(new MAssociationEnd[aEnds.size()]);
		}

		void initModel() {
			// initialize object list
			fLinks.clear();
			fObjectValueStrMap.clear();
			if (fAssociation == null){
				return;
			}

			// get names for all objects
			for(MLink link : fSystem.state().linksOfAssociation(fAssociation).links()){
				addLink(link);
			}
			sortRows();
		}

		@Override
		public String getColumnName(int col) {
			if(col == 0){
				return fAssociation.name();
			}
			else {
				return assocEnds[col-1].name();
			}
		}

		@Override
		public int getColumnCount() {
			return fAssociation == null ? 0 : assocEnds.length + 1;
		}

		@Override
		public int getRowCount() {
			return fLinks.size();
		}

		@Override
		public Class<?> getColumnClass(int col) {
			return String.class;
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (col == 0){
				return "-"; // link.association().name();
			} else if (col <= assocEnds.length) {
				MLink link = fLinks.get(row);
				String[] values = fObjectValueStrMap.get(link);
				return values[col - 1];
			} else {
				return "/";
			}
		}

		/**
		 * Adds a row for an object to the table.
		 */
		void addLink(MLink link) {
			fLinks.add(link);
			updateLink(link);
		}

		/**
		 * Removes a link from the list.
		 */
		void removeLink(MLink link){
			fLinks.remove(link);
			fObjectValueStrMap.remove(link);
		}

		/**
		 * Updates the row for the given link.
		 */
		void updateLink(MLink link) {
			String[] values = new String[assocEnds.length];
			for(int i = 0; i < assocEnds.length; i++){
				MObject o = link.linkEnd(assocEnds[i]).object();
				values[i] = o.name();
			}
			fObjectValueStrMap.put(link, values);
		}

		void sortRows() {
			Collections.sort(fLinks, new Comparator<MLink>() {
				@Override
				public int compare(MLink o1, MLink o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
		}
	}

	@Subscribe
	public void onLinkInserted(LinkInsertedEvent e) {
		fTableModel.addLink(e.getLink());
		fTableModel.sortRows();
		fTableModel.fireTableDataChanged();
	}

	@Subscribe
	public void onLinkDeleted(LinkDeletedEvent e){
		fTableModel.removeLink(e.getLink());
		fTableModel.fireTableDataChanged();
	}

	/**
	 * Detaches the view from its model.
	 */
	@Override
	public void detachModel() {
		fSystem.getEventBus().unregister(this);
	}
}
