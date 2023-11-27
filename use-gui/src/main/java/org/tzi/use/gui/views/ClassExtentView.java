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

package org.tzi.use.gui.views;

import com.google.common.eventbus.Subscribe;
import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.PopupListener;
import org.tzi.use.gui.views.evalbrowser.ExprEvalBrowser;
import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * A view showing all objects of a class, their properties (attributes), and
 * results of invariants.
 * 
 * @author Mark Richters
 */
@SuppressWarnings("serial")
public class ClassExtentView extends JPanel implements View, ActionListener {

    private final MSystem fSystem;

    private MClass fClass;

    private boolean fShowInvResults;

    private final JPopupMenu fPopupMenu; // context menu on right mouse click

    private final JTable fTable;

    private final JScrollPane fTablePane;

    private final TableModel fTableModel;

    public ClassExtentView(MainWindow parent, MSystem system) {
        super(new BorderLayout());

        fSystem = system;
        fSystem.getEventBus().register(this);

        // get a class
        Collection<MClass> classes = fSystem.model().classes();
        if (!classes.isEmpty()) {
            fClass = classes.iterator().next();
        }

        // create table of attribute/value pairs
        fTableModel = new TableModel();
        
        // The following code was posted on StackOverflow
        // by camickr (http://stackoverflow.com/a/15240806/43814)
        // to be able to use auto resize and scroll bars
        fTable = new JTable(fTableModel)
        {
            @Override
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }

            @Override
            public void doLayout()
            {
                TableColumn resizingColumn = null;

                if (tableHeader != null)
                    resizingColumn = tableHeader.getResizingColumn();

                //  Viewport size changed. May need to increase columns widths

                if (resizingColumn == null)
                {
                    setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    super.doLayout();
                }

                //  Specific column resized. Reset preferred widths

                else
                {
                    TableColumnModel tcm = getColumnModel();

                    for (int i = 0; i < tcm.getColumnCount(); i++)
                    {
                        TableColumn tc = tcm.getColumn(i);
                        tc.setPreferredWidth( tc.getWidth() );
                    }

                    // Columns don't fill the viewport, invoke default layout
                    if (tcm.getTotalColumnWidth() < getParent().getWidth())
                        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                    super.doLayout();
                }

                setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }

        };
        fTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fTable.setPreferredScrollableViewportSize(new Dimension(250, 70));
        fTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fTablePane = new JScrollPane(fTable);
        fitWidth();

        // create the popup menu for options
        fPopupMenu = new JPopupMenu();
        final JCheckBoxMenuItem cbShowInvResults = new JCheckBoxMenuItem(
                "Show results of invariants");
        cbShowInvResults.setState(fShowInvResults);
        cbShowInvResults.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                fShowInvResults = ev.getStateChange() == ItemEvent.SELECTED;

                updateStructure();
            }
        });
        fPopupMenu.add(cbShowInvResults);
        JMenu submenu = new JMenu("Select class");
        // add all classes to submenu
        Object[] c = classes.toArray();
        Arrays.sort(c);
        JMenu sub = submenu;
        for (int i = 0; i < c.length; i++) {
            if (i > 0 && i % 30 == 0) {
                // nested submenus for large model
                JMenu s = new JMenu("More...");
                sub.add(s);
                sub = s;
            }
            JMenuItem mi = new JMenuItem(((MClass) c[i]).name());
            mi.addActionListener(this);
            sub.add(mi);
        }
        fPopupMenu.add(submenu);
        fTable.addMouseListener(new PopupListener(fPopupMenu));
        fTablePane.addMouseListener(new PopupListener(fPopupMenu));

        // layout panel
        add(fTablePane, BorderLayout.CENTER);
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

            Component comp = renderer.getTableCellRendererComponent(fTable, col
                    .getHeaderValue(), false, false, 0, 0);
            width = comp.getPreferredSize().width;

            for (int r = 0; r < fTable.getRowCount(); r++) {
                renderer = fTable.getCellRenderer(r, i);
                comp = renderer.getTableCellRendererComponent(fTable, fTable
                        .getValueAt(r, i), false, false, r, i);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            col.setPreferredWidth(width + 10);

        }
    }

    /**
     * Called when a class was selected in the popup menu.
     */
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        // see if there's really a change
        if (fClass.name().equals(name))
            return;

        fClass = fSystem.model().getClass(name);
        fTableModel.initStructure();
        fTableModel.initModel();
        if (fShowInvResults)
            fTableModel.updateInvariants();
        fTableModel.fireTableStructureChanged();
        fTableModel.fireTableDataChanged();
        fitWidth();
    }

    // icons used for invariant results
    private static final Icon fTrueIcon = new ImageIcon(Options.getIconPath("InvTrue.gif"));
    private static final Icon fFalseIcon = new ImageIcon(Options.getIconPath("InvFalse.gif"));
    private static final Icon fNotAvailIcon = new ImageIcon(Options.getIconPath("InvNotAvail.gif"));

    /**
     * The table model.
     */
    class TableModel extends AbstractTableModel {
        private final List<String> fColumnNames;

		private final List<MObject> fObjects;

        private MAttribute[] fAttributes;

        private MClassInvariant[] fClassInvariants;

        private final Map<MObject, String[]> fObjectValueStrMap;

        private final Map<MClassInvariant, Set<MObject>> fInvBadObjects;

        // need to cache all data because getValueAt is called often.

        TableModel() {
            fObjects = new ArrayList<MObject>();
            fColumnNames = new ArrayList<String>();
            fObjectValueStrMap = new HashMap<MObject, String[]>();
            fInvBadObjects = new HashMap<MClassInvariant, Set<MObject>>();
            initStructure();
            initModel();
        }

        void initStructure() {
            fColumnNames.clear();
            fInvBadObjects.clear();
            if (fClass == null)
                return;

            // prepare table: get attributes of class
            List<MAttribute> attributes = fClass.allAttributes();
            int n = attributes.size();
            fAttributes = new MAttribute[n];
            System.arraycopy(attributes.toArray(), 0, fAttributes, 0, n);
            Arrays.sort(fAttributes);

            // set columns
            fColumnNames.add(fClass.name());
            for (MAttribute fAttribute : fAttributes)
                fColumnNames.add(fAttribute.name());

            // get all invariants for selected class
            if (fShowInvResults) {
                Set<MClassInvariant> invSet = fSystem.model().allClassInvariants(fClass);
                n = invSet.size();
                fClassInvariants = new MClassInvariant[n];
                System.arraycopy(invSet.toArray(), 0, fClassInvariants, 0, n);
                Arrays.sort(fClassInvariants);
                for (MClassInvariant fClassInvariant : fClassInvariants)
                    fColumnNames.add(fClassInvariant.name());
            }
        }

        void initModel() {
            // initialize object list
            fObjects.clear();
            fObjectValueStrMap.clear();
            if (fClass == null)
                return;

            // get attribute values for all objects
            for (MObject obj : fSystem.state().objectsOfClass(fClass)) {
                addObject(obj);
            }
            
            sortRows();
        }

        void updateInvariants() {
            if (!fSystem.state().checkStructure(VoidUserOutput.getInstance())) {
                // cannot evaluate on ill-formed state
                for (MClassInvariant fClassInvariant : fClassInvariants) {
                    fInvBadObjects.put(fClassInvariant, null);
                }
            }

            for (MClassInvariant fClassInvariant : fClassInvariants) {
                if (!fClassInvariant.isActive()) {
                    continue;
                }
                try {
                    Set<MObject> badObjects = new HashSet<MObject>();
                    Evaluator evaluator = new Evaluator();
                    Expression expr = fClassInvariant
                            .getExpressionForViolatingInstances();
                    Value v = evaluator.eval(expr, fSystem.state(),
                            new VarBindings());

                    for (Value value : ((SetValue) v).collection()) {
                        ObjectValue oVal = (ObjectValue) value;
                        badObjects.add(oVal.value());
                    }
                    fInvBadObjects.put(fClassInvariant, badObjects);
                } catch (Exception e) {
                    // No need to handle e.
                }
            }

            // MouseListener for calling the Evaluation Browser Browser
            MouseAdapter ma = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2
                            && fTable.getSelectedColumn() > fAttributes.length) {
                        Expression expr;
                        String invName = fTable.getColumnName(fTable
                                .getSelectedColumn());
                        for (MClassInvariant fClassInvariant : fClassInvariants) {
                            if (fClassInvariant.isActive() && fClassInvariant.name().equals(invName)) {
                                expr = fClassInvariant.expandedExpression();
                                Evaluator evaluator = new Evaluator(true);
                                try {
                                    evaluator.eval(expr, fSystem.state());
                                } catch (MultiplicityViolationException ex) {
                                    return;
                                }

                                ExprEvalBrowser eb = ExprEvalBrowser
                                        .createPlus(
                                                evaluator.getEvalNodeRoot(),
                                                fSystem, fClassInvariant);
                                eb.setSelectionElement(fTable.getSelectedRow());
                                break;
                            }
                        }

                    }
                }
            };
            // add the mouselistener for the Eval Browser if it was not added
            // till now
            MouseListener[] ml = fTable.getMouseListeners();
            boolean mouseAdapterExists = false;

            for (MouseListener mouseListener : ml) {
                if (mouseListener.getClass().equals(ma.getClass())) {
                    mouseAdapterExists = true;
                    break;
                }
            }
            if (!mouseAdapterExists)
                fTable.addMouseListener(ma);
        }

        public String getColumnName(int col) {
            return fColumnNames.get(col);
        }

        public int getColumnCount() {
            return fColumnNames.size();
        }

        public int getRowCount() {
            return fObjects.size();
        }

        public Class<?> getColumnClass(int col) {
            if (col <= fAttributes.length)
                return String.class;
            else
                return Icon.class;
        }

        public Object getValueAt(int row, int col) {

            MObject obj = fObjects.get(row);
            if (col == 0)
                return obj.name();
            else if (col <= fAttributes.length) {
                String[] values = fObjectValueStrMap.get(obj);
                return values[col - 1];
            } else {
                MClassInvariant inv = fClassInvariants[col - fAttributes.length - 1];
                Set<MObject> badObjects = fInvBadObjects.get(inv);
                if (badObjects == null || !inv.isActive()){
                	//TODO create separate icon for inactive invariants
                	return fNotAvailIcon;
                } else if (badObjects.contains(obj)){
                	return fFalseIcon;
                } else {
                	return fTrueIcon;
                }
            }
        }

        /**
         * Adds a row for an object to the table.
         */
        void addObject(MObject obj) {
            fObjects.add(obj);
            updateObject(obj);
        }

        /**
         * Updates the row for the given object.
         */
        void updateObject(MObject obj) {
            MObjectState objState = obj.state(fSystem.state());
            String[] values = new String[fAttributes.length];
            for (int i = 0; i < fAttributes.length; i++)
                values[i] = objState.attributeValue(fAttributes[i]).toString();

            fObjectValueStrMap.put(obj, values);
        }

        /**
         * Removes a row for an object from the table.
         */
        void removeObject(MObject obj) {
            fObjects.remove(obj);
            fObjectValueStrMap.remove(obj);
        }

        void sortRows() {
            fObjects.sort(new Comparator<MObject>() {
                public int compare(MObject o1, MObject o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
        }

    }

    private void updateStructure(){
    	fTableModel.initStructure();
        fTableModel.initModel();
        if (fShowInvResults) {
            fTableModel.updateInvariants();
            fTable.setToolTipText("Double click on the symbols of the invariants to open evaluation browser");
        } else {
        	fTable.setToolTipText(null);
        }
        fTableModel.fireTableStructureChanged();
        fTableModel.fireTableDataChanged();
        fitWidth();
    }
    
    private void update() {
        if (fShowInvResults)
            fTableModel.updateInvariants();
        
        fTableModel.fireTableDataChanged();
    }
    
    @Subscribe
    public void onObjectCreated(ObjectCreatedEvent e) {
    	if (e.getCreatedObject().cls().equals(fClass)) {
            fTableModel.addObject(e.getCreatedObject());
    	}
    	
    	fTableModel.sortRows();
    	update();
    }
    
    @Subscribe
    public void onObjectDestroyed(ObjectDestroyedEvent e) {
    	if (e.getDestroyedObject().cls().equals(fClass)) {
            fTableModel.removeObject(e.getDestroyedObject());
    	}
    	update();
    }
    
    @Subscribe
    public void onAttributeAssignment(AttributeAssignedEvent e) {
    	if (e.getObject().cls().equals(fClass))
            fTableModel.updateObject(e.getObject());
    }
    
    @Subscribe
    public void onClassInvariantsLoaded(ClassInvariantsLoadedEvent e){
    	updateStructure();
    }
    
    @Subscribe
    public void onClassInvariantsUnloaded(ClassInvariantsUnloadedEvent e){
    	updateStructure();
    }
    
    @Subscribe
    public void onClassInvariantStateChange(ClassInvariantChangedEvent e){
    	update();
    }
    
    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
    }
}