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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
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

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.MMHTMLPrintVisitor;
import org.tzi.use.gui.util.PopupListener;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MMVisitor;
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
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.NullWriter;

/**
 * A view showing all objects of a class, their properties (attributes), and
 * results of invariants.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public class ClassExtentView extends JPanel implements View, ActionListener {
    // private MainWindow fMainWindow;
    private MSystem fSystem;

    private MClass fClass;

    private boolean fShowInvResults;

    private JPopupMenu fPopupMenu; // context menu on right mouse click

    private JTable fTable;

    private JScrollPane fTablePane;

    private TableModel fTableModel;

    public ClassExtentView(MainWindow parent, MSystem system) {
        super(new BorderLayout());

        // fMainWindow = parent;
        fSystem = system;
        fSystem.addChangeListener(this);

        // get a class
        Collection classes = fSystem.model().classes();
        if (!classes.isEmpty()) {
            fClass = (MClass) classes.iterator().next();
        }

        // create table of attribute/value pairs
        fTableModel = new TableModel();
        fTable = new JTable(fTableModel);
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

                fTableModel.initStructure();
                fTableModel.initModel();
                if (fShowInvResults) {
                    fTableModel.updateInvariants();
                    fTable
                            .setToolTipText("Double click on the symbols of the invariants to open evaluation browser");
                } else
                    fTable.setToolTipText(null);
                fTableModel.fireTableStructureChanged();
                fTableModel.fireTableDataChanged();
                fitWidth();
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
        // System.err.println("*** ActionEvent = " + e.getActionCommand());
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
        // TableColumn column = null;
        // for (int i = fTableModel.getColumnCount() - 1; i >= 0; i--) {
        // column = fTable.getColumnModel().getColumn(i);
        // column.sizeWidthToFit();
        // }
    }

    // icons used for invariant results
    private static final Icon fTrueIcon = new ImageIcon(Options.iconDir
            + "InvTrue.gif");

    private static final Icon fFalseIcon = new ImageIcon(Options.iconDir
            + "InvFalse.gif");

    private static final Icon fNotAvailIcon = new ImageIcon(Options.iconDir
            + "InvNotAvail.gif");

    /**
     * The table model.
     */
    class TableModel extends AbstractTableModel {
        private ArrayList fColumnNames;

        private ArrayList fObjects;

        private MAttribute[] fAttributes;

        private MClassInvariant[] fClassInvariants;

        private Map fObjectValueStrMap; // (MObject -> String)

        private Map fInvBadObjects; // (MClassInvariant -> Set(MObject))

        // need to cache all data because getValueAt is called often.

        TableModel() {
            fObjects = new ArrayList();
            fColumnNames = new ArrayList();
            fObjectValueStrMap = new HashMap();
            fInvBadObjects = new HashMap();
            initStructure();
            initModel();
        }

        void initStructure() {
            fColumnNames.clear();
            fInvBadObjects.clear();
            if (fClass == null)
                return;

            // prepare table: get attributes of class
            List attributes = fClass.allAttributes();
            int n = attributes.size();
            fAttributes = new MAttribute[n];
            System.arraycopy(attributes.toArray(), 0, fAttributes, 0, n);
            Arrays.sort(fAttributes);

            // set columns
            fColumnNames.add(fClass.name());
            for (int i = 0; i < fAttributes.length; i++)
                fColumnNames.add(fAttributes[i].name());

            // get all invariants for selected class
            if (fShowInvResults) {
                Set invSet = fSystem.model().allClassInvariants(fClass);
                n = invSet.size();
                fClassInvariants = new MClassInvariant[n];
                System.arraycopy(invSet.toArray(), 0, fClassInvariants, 0, n);
                Arrays.sort(fClassInvariants);
                for (int i = 0; i < fClassInvariants.length; i++)
                    fColumnNames.add(fClassInvariants[i].name());
            }
        }

        void initModel() {
            // initialize object list
            fObjects.clear();
            fObjectValueStrMap.clear();
            if (fClass == null)
                return;

            // get attribute values for all objects
            Iterator objIter = fSystem.state().objectsOfClass(fClass)
                    .iterator();
            while (objIter.hasNext()) {
                MObject obj = (MObject) objIter.next();
                addObject(obj);
            }
            sortRows();
        }

        void updateInvariants() {
            if (!fSystem.state().checkStructure(
                    new PrintWriter(new NullWriter()))) {
                // cannot evaluate on ill-formed state
                for (int i = 0; i < fClassInvariants.length; i++) {
                    fInvBadObjects.put(fClassInvariants[i], null);
                }
            }
            for (int i = 0; i < fClassInvariants.length; i++) {
                try {
                    Set badObjects = new HashSet();
                    Evaluator evaluator = new Evaluator();
                    Expression expr = fClassInvariants[i]
                            .getExpressionForViolatingInstances();
                    Value v = evaluator.eval(expr, fSystem.state(),
                            new VarBindings());

                    Iterator valIter = ((SetValue) v).collection().iterator();
                    while (valIter.hasNext()) {
                        ObjectValue oVal = (ObjectValue) valIter.next();
                        badObjects.add(oVal.value());
                    }
                    fInvBadObjects.put(fClassInvariants[i], badObjects);
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
                        for (int i = 0; i < fClassInvariants.length; i++) {
                            if (fClassInvariants[i].name() == invName) {
                                expr = fClassInvariants[i].expandedExpression();
                                Evaluator evaluator = new Evaluator();
                                evaluator.enableEvalTree();
                                try {
                                    Value v = evaluator.eval(expr, fSystem
                                            .state());
                                } catch (MultiplicityViolationException ex) {
                                    return;
                                }
                                // get the invariant as html string
                                StringWriter sw = new StringWriter();
                                sw.write("<html>");
                                MMVisitor v = new MMHTMLPrintVisitor(
                                        new PrintWriter(sw));
                                fClassInvariants[i].processWithVisitor(v);
                                sw.write("</html>");
                                String htmlSpec = sw.toString();
                                // get the invariant as normal string
                                sw = new StringWriter();
                                v = new MMPrintVisitor(new PrintWriter(sw));
                                fClassInvariants[i].processWithVisitor(v);
                                String spec = sw.toString();
                                ExprEvalBrowser eb = ExprEvalBrowser
                                        .createPlus(
                                                evaluator.getEvalNodeRoot(),
                                                fSystem, spec, htmlSpec);
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
            for (int i = 0; i < ml.length; i++) {
                if (ml[i].getClass().equals(ma.getClass())) {
                    mouseAdapterExists = true;
                    break;
                }
            }
            if (!mouseAdapterExists)
                fTable.addMouseListener(ma);
        }

        public String getColumnName(int col) {
            return (String) fColumnNames.get(col);
        }

        public int getColumnCount() {
            return fColumnNames.size();
        }

        public int getRowCount() {
            return fObjects.size();
        }

        public Class getColumnClass(int col) {
            if (col <= fAttributes.length)
                return String.class;
            else
                return Icon.class;
        }

        public Object getValueAt(int row, int col) {
            // System.err.println("*** getValueAt (" + row + ", " + col + ")");
            MObject obj = (MObject) fObjects.get(row);
            if (col == 0)
                return obj.name();
            else if (col <= fAttributes.length) {
                String[] values = (String[]) fObjectValueStrMap.get(obj);
                return values[col - 1];
            } else {
                MClassInvariant inv = fClassInvariants[col - fAttributes.length
                        - 1];
                Set badObjects = (Set) fInvBadObjects.get(inv);
                if (badObjects == null)
                    return fNotAvailIcon;
                else if (badObjects.contains(obj))
                    return fFalseIcon;
                else
                    return fTrueIcon;
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
                values[i] = ((Value) objState.attributeValue(fAttributes[i]))
                        .toString();

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
            Collections.sort(fObjects, new Comparator() {
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

        // incremental update of table model
        it = e.getNewObjects().iterator();
        while (it.hasNext()) {
            MObject obj = (MObject) it.next();
            if (obj.cls().equals(fClass))
                fTableModel.addObject(obj);
        }

        it = e.getDeletedObjects().iterator();
        while (it.hasNext()) {
            MObject obj = (MObject) it.next();
            if (obj.cls().equals(fClass))
                fTableModel.removeObject(obj);
        }

        it = e.getModifiedObjects().iterator();
        while (it.hasNext()) {
            MObject obj = (MObject) it.next();
            if (obj.cls().equals(fClass))
                fTableModel.updateObject(obj);
        }

        // change in number of rows?
        if (e.structureHasChanged())
            fTableModel.sortRows();
        if (fShowInvResults)
            fTableModel.updateInvariants();
        fTableModel.fireTableDataChanged();
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
    }
}
