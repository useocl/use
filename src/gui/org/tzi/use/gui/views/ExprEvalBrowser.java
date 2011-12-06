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

// $Id$

package org.tzi.use.gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.TreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.tzi.use.gui.util.CaptureTheWindow;
import org.tzi.use.uml.ocl.expr.EvalNode;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;
import org.tzi.use.uml.sys.MSystem;

/**
 * A tree view showing evaluation results of an expression. Each node is adorned
 * with the result of evaluating the sub-expression.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
@SuppressWarnings("serial")
public class ExprEvalBrowser extends JPanel {
    private MSystem fSystem;

    // several attributes needed by the inner classes: e.g. the action- and
    // item-listener
    JFrame fParent;

    JLabel fTopLabel;

    JSplitPane fSplit1, fSplit2;

    JTree fTree;

    DefaultTreeModel fTreeModel;

    JList fVarAssList;

    JTextArea fSubstituteWin;

    JScrollPane fScrollSubstituteWin, fScrollVarAssList;

    JComboBox fComboTreeDisplays;

    JScrollPane fScrollTree;

    JCheckBoxMenuItem fExtendedExists = new JCheckBoxMenuItem("exists");

    JCheckBoxMenuItem fExtendedForAll = new JCheckBoxMenuItem("forAll");

    JCheckBoxMenuItem fExtendedAnd = new JCheckBoxMenuItem("and");

    JCheckBoxMenuItem fExtendedOr = new JCheckBoxMenuItem("or");

    JCheckBoxMenuItem fExtendedImplies = new JCheckBoxMenuItem("implies");

    JCheckBoxMenuItem fExtendedAll = new JCheckBoxMenuItem("all");

    JCheckBoxMenuItem fVarAssListChk = new JCheckBoxMenuItem(
            "Variable assignment window");

    JCheckBoxMenuItem fVarSubstituteWinChk = new JCheckBoxMenuItem(
            "Subexpression evaluation window");

    JCheckBoxMenuItem fNoColorHighlightingChk = new JCheckBoxMenuItem("Black and white");

    JRadioButtonMenuItem[] fTreeViews = {
            new JRadioButtonMenuItem("Late variable assignment", true),
            new JRadioButtonMenuItem("Early variable assignment"),
            new JRadioButtonMenuItem("Variable assignment & substitution"),
            new JRadioButtonMenuItem("Variable substitution"),
            new JRadioButtonMenuItem("No variable assignment") };

    JRadioButtonMenuItem[] fTreeHighlightings = {
            new JRadioButtonMenuItem("No highlighting", true),
            new JRadioButtonMenuItem("Term highlighting"),
            new JRadioButtonMenuItem("Subtree highlighting"),
            new JRadioButtonMenuItem("Complete subtree highlighting") };

    DefaultMutableTreeNode fTopNode;

    String fTitle;

    String fHtmlTitle;

    JPanel fSouthPanel;

    int fDefaultDividerSize;

    double fTreeIndent = 0;

    boolean fEarlyVarEval = false;

    boolean fHideVarAss = false;

    boolean fVarSubstitution = false;

    boolean fFirstInvoke1 = true;

    boolean fFirstInvoke2 = true;

    Vector<Entry> fNeedlessVarBindings;

    // DefaultFont for TreeCellRenderer
    Font fDefaultFont = getFont();

    JPopupMenu fPopup;

    // several Listeners
    EvalActionListener fActionListener = new EvalActionListener();

    EvalItemListener fItemListener = new EvalItemListener();

    EvalMouseAdapter fMouseListener = new EvalMouseAdapter();

    /**
     * builds the evaluation tree recursively from the EvalNodes
     */
    private void createNodes(DefaultMutableTreeNode treeParent, EvalNode node) {
        Expression parentExpr = node.getExpr();
        Vector<Entry> parentVars = node.getVarBindings();

        breakLabel: for(EvalNode child : node.children()) {
            Expression childExpr = child.getExpr();
            DefaultMutableTreeNode treeChild = new DefaultMutableTreeNode(child);
            // TreeHighlightings for the node
            char[] highlightings = new char[2];
            TreeNode[] nodes = treeParent.getPath();
            // subtree highlighting
            DefaultMutableTreeNode dnode = treeParent;
            EvalNode enode;
            if (child.getResult() == "true")
                highlightings[0] = 't';
            else if (child.getResult() == "false")
                highlightings[0] = 'f';
            else if (child.getResult() == "Undefined"
                    && child.getExpr().type().toString() == "Boolean")
                highlightings[0] = 'u';
            else {
                for (int i = nodes.length - 1; i >= 0; i--) {
                    dnode = (DefaultMutableTreeNode) nodes[i];
                    enode = (EvalNode) dnode.getUserObject();
                    if (enode.getResult().equals("true")) {
                        highlightings[0] = 't';
                        break;
                    } else if (enode.getResult().equals("false")) {
                        highlightings[0] = 'f';
                        break;
                    } else if (enode.getResult().equals("Undefined")
                            && enode.getExpr().type().toString() == "Boolean") {
                        highlightings[0] = 'u';
                        break;
                    }
                }
                if ((highlightings[0] != 't') && (highlightings[0] != 'f')
                        && (highlightings[0] != 'u')) {
                    EvalNode root = (EvalNode) fTopNode.getUserObject();
                    if (root.getResult() == "true")
                        highlightings[0] = 't';
                    else if (root.getResult() == "false")
                        highlightings[0] = 'f';
                }
            }
            // complete subtree highlighting
            dnode = (DefaultMutableTreeNode) nodes[0];
            enode = (EvalNode) dnode.getUserObject();
            if (nodes.length == 1)
                if (child.getResult() == "true")
                    highlightings[1] = 't';
                else if (child.getResult() == "false")
                    highlightings[1] = 'f';
                else if (child.getResult() == "Undefined"
                        && child.getExpr().type().toString() == "Boolean")
                    highlightings[1] = 'u';
            for (int i = 1; i < nodes.length; i++) {
                dnode = (DefaultMutableTreeNode) nodes[i];
                enode = (EvalNode) dnode.getUserObject();
                if (enode.isEarlyVarNode())
                    continue;
                if (enode.getResult().equals("true")) {
                    highlightings[1] = 't';
                    break;
                } else if (enode.getResult().equals("false")) {
                    highlightings[1] = 'f';
                    break;
                } else if (enode.getResult().equals("Undefined")
                        && enode.getExpr().type().toString() == "Boolean") {
                    highlightings[1] = 'u';
                    break;
                }
            }
            if ((highlightings[1] != 't') && (highlightings[1] != 'f')
                    && (highlightings[1] != 'u')) {
                EvalNode root = (EvalNode) fTopNode.getUserObject();
                if (root.getResult() == "true")
                    highlightings[1] = 't';
                else if (root.getResult() == "false")
                    highlightings[1] = 'f';
            }
            child.setHighlighting(highlightings);
            // remove all Varbindings that are in the system state but not in
            // the term
            child.getVarBindings().removeAll(fNeedlessVarBindings);
            // VarBindings for the Early-Variable-Assignment-View
            Vector<Entry> childVars = child.getVarBindings();
            Vector<Entry> newVars = new Vector<Entry>( childVars );

            newVars.removeAll(parentVars);
            DefaultMutableTreeNode paren = treeParent;
            // if new Variables appear in the term, they are added in the tree
            // before the child
            if (fEarlyVarEval && newVars.size() > 0) {
                Entry e = newVars.get(0);
                enode = new EvalNode(new Vector<Entry>(childVars));
                ExpVariable expVar = new ExpVariable(e.getVarName(), e
                        .getValue().type());
                enode.setExpression(expVar);
                enode.setResult(e.getValue());
                // if the child-node is expanded the new variable assignment
                // node is also expanded
                enode.setVisibleAttr(child.isVisible());
                enode.setVarAssignment(e.getVarName() + " = " + e.getValue());
                // Highlighting for the new Var-Assignment-Node
                char[] highlightings2 = (char[]) highlightings.clone();
                if (newVars.size() == 1 && enode.getResult() == "true")
                    highlightings2[0] = 't';
                else if (newVars.size() == 1 && enode.getResult() == "false")
                    highlightings2[0] = 'f';
                enode.setHighlighting(highlightings2);

                for (int i = 1; i < newVars.size(); i++) {
                    Entry e2 = (Entry) newVars.get(i);
                    enode.setVarAssignment(e2.getVarName() + " = "
                            + e2.getValue());
                }

                DefaultMutableTreeNode chil = new DefaultMutableTreeNode(enode);
                paren.add(chil);
                paren = chil;
            } // end of Early-Variable-Assignment-View

            // set the var Substitution View if requested
            child.setVarSubstituteView(fVarSubstitution);
            // don't add var assignments if not desired
            if (!fHideVarAss || childExpr.name() != "var") {
                // don't add insignificant nodes like "1=1" to the eval tree
                if (!childExpr.toString().equals(child.getResult())) {
                    paren.add(treeChild);
                    createNodes(treeChild, child);
                }
                // break if the parent node is a forAll-Node and child evaluates
                // to "false" or "Undefined"
                // for minimal evaluation
                if (!fExtendedForAll.isSelected()
                        && parentExpr.name() == "forAll"
                        && (child.getResult() == "false" || child.getResult() == "Undefined"))
                    break breakLabel;
                // break if the parent node is an exists node and child
                // evaluates to "true"
                // for minimal evaluation
                else if (!fExtendedExists.isSelected()
                        && parentExpr.name() == "exists"
                        && child.getResult() == "true")
                    break breakLabel;
                else if (!fExtendedOr.isSelected() && parentExpr.name() == "or"
                        && child.getResult() == "true")
                    break breakLabel;
                else if (!fExtendedAnd.isSelected()
                        && parentExpr.name() == "and"
                        && child.getResult() == "false")
                    break breakLabel;
                else if (!fExtendedImplies.isSelected()
                        && parentExpr.name() == "implies"
                        && child.getResult() == "false")
                    break breakLabel;
            }
        } // while
    } // createNodes()

    public void updateEvalBrowser(EvalNode root) {
        // create the nodes for the JTree
        fTopNode = new DefaultMutableTreeNode(root);
        
        fNeedlessVarBindings = new Vector<Entry>( root.getVarBindings() );
        root.getVarBindings().removeAll(fNeedlessVarBindings);
        
        // the tree is created later when the default configuration is set
        createNodes(fTopNode, root);
        // highlightings informations for the root node
        char[] highlightings = new char[2];
        if (root.getResult() == "true") {
            highlightings[0] = 't';
            highlightings[1] = 't';
        } else if (root.getResult() == "false") {
            highlightings[0] = 'f';
            highlightings[1] = 'f';
        }
        root.setHighlighting(highlightings);
        // remove old tree
        fScrollTree.remove(fTree);
        // create the tree
        fTreeModel = new DefaultTreeModel(fTopNode);
        fTree = new JTree(fTreeModel);
        fTree.putClientProperty("JTree.lineStyle", "Angled");
        // Listener sets the content to the VarBinding- and
        // VarSubstitution-Window
        fTree.addTreeSelectionListener(new TermSelectionListener());

        fScrollTree = new JScrollPane(fTree);
        int divider = fSplit1.getDividerLocation();
        fSplit1.setLeftComponent(fScrollTree);
        fSplit1.setDividerLocation(divider);

        fTree.addMouseListener(fMouseListener);
        // set the selectedRenderer to the new created tree
        if (fTreeHighlightings[0].isSelected())
            fTree.setCellRenderer(new DefaultTreeCellRenderer());
        else if (fTreeHighlightings[1].isSelected())
            fTree.setCellRenderer(new TermRenderer());
        else if (fTreeHighlightings[2].isSelected())
            fTree.setCellRenderer(new SubtreeRenderer());
        else if (fTreeHighlightings[3].isSelected())
            fTree.setCellRenderer(new CompleteSubtreeRenderer());
        // reset the extra windows
        fVarAssList.setListData(new Vector<Entry>());
        fSubstituteWin.setText(null);
        // set new text for the top-label
        fTitle = root.getExpr().toString();
        String htmlTitle = "<html>" + root.getHtmlExpr() + "</html>";
        adjustTopWidth(fTitle, htmlTitle);
    }

    /**
     * call for the new evaluation browser
     */
    public static ExprEvalBrowser create(EvalNode root, MSystem sys) {
        ExprEvalBrowser eb;
        eb = createPlus(root, sys, root.getExpr().toString(), "<html>"
                + root.getHtmlExpr() + "</html>");
        return eb;
    }

    /**
     * creates a new evaluation browser window with the tree created from the
     * hand overed root node
     */
    public static ExprEvalBrowser createPlus(EvalNode root, MSystem sys,
            String invName, String invHtml) {
        final JFrame f = new JFrame("Evaluation browser ");
        ExprEvalBrowser eb = new ExprEvalBrowser(root, sys, invName, invHtml, f);

        // Layout the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(eb, BorderLayout.CENTER);

        f.setContentPane(contentPane);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        // set the right size for the top
        eb.adjustTopWidth(invName, invHtml);
        // set the default configuration for the eval browser
        eb.fActionListener.actionPerformed(new ActionEvent(new JMenuItem(
                "Default configuration"), 0, "Default configuration"));
        eb.fItemListener.itemStateChanged(new ItemEvent(eb.fComboTreeDisplays,
                0, "Collapse", ItemEvent.SELECTED));
        return eb;
    }

    /**
     * the constructor for the new evaluation browser window
     */
    public ExprEvalBrowser(EvalNode root, MSystem sys, String invAsStr,
            String invInHtml, JFrame mother) {
        fSystem = sys;
        setLayout(new BorderLayout());
        // connection to the mother JFrame
        this.fParent = mother;
        // set the title in the NORTH
        fTitle = invAsStr;
        fHtmlTitle = invInHtml;
        fTopLabel = new JLabel();
        fTopLabel.setToolTipText("Double click to min or max title, "
                + "right click to open config menu");
        add(fTopLabel, BorderLayout.NORTH);

        // set the combo-menu in the SOUTH with popupmenu-listener
        EvalPopupMenuListener plistener = new EvalPopupMenuListener();
        String[] treeControll_items = { "Display options", "Expand all",
                "Expand all true", "Expand all false", "Collapse" };
        fComboTreeDisplays = new JComboBox(treeControll_items);
        fComboTreeDisplays.addItemListener(fItemListener);
        fComboTreeDisplays.addPopupMenuListener(plistener);
        fComboTreeDisplays.setMaximumRowCount(4);
        fComboTreeDisplays.setMaximumSize(new Dimension(150, 100));
        fComboTreeDisplays.setPreferredSize(new Dimension(150, 25));

        FlowLayout panelLayout = new FlowLayout();
        panelLayout.setVgap(0);
        fSouthPanel = new JPanel(panelLayout);
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(fActionListener);
        fSouthPanel.add(fComboTreeDisplays);
        fSouthPanel.add(closeBtn);
        fSouthPanel.addMouseListener(fMouseListener);

        Box comboBox = Box.createHorizontalBox();
        comboBox.add(Box.createGlue());
        comboBox.add(fSouthPanel);
        comboBox.add(Box.createGlue());
        // add the menubar to the frame
        add(comboBox, BorderLayout.SOUTH);

        // create the nodes for the JTree
        fTopNode = new DefaultMutableTreeNode(root);
        
        fNeedlessVarBindings = new Vector<Entry>(root.getVarBindings());
        root.getVarBindings().removeAll(fNeedlessVarBindings);
        // the tree is created later when the default configuration is set
        createNodes(fTopNode, root);
        // highlightings informations for the root node
        char[] highlightings = new char[2];
        if (root.getResult() == "true") {
            highlightings[0] = 't';
            highlightings[1] = 't';
        } else if (root.getResult() == "false") {
            highlightings[0] = 'f';
            highlightings[1] = 'f';
        }
        root.setHighlighting(highlightings);
        // create the tree
        fTreeModel = new DefaultTreeModel(fTopNode);
        fTree = new JTree(fTreeModel);
        fTree.putClientProperty("JTree.lineStyle", "Angled");
        // Listener sets the content to the VarBinding- and
        // VarSubstitution-Window
        fTree.addTreeSelectionListener(new TermSelectionListener());

        fScrollTree = new JScrollPane(fTree);
        fSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        fSplit1.setLeftComponent(fScrollTree);
        fSplit1.setResizeWeight(1);
        fDefaultDividerSize = fSplit1.getDividerSize();
        fSplit1.setDividerSize(0);

        // The Extra Evaluation Window for variable evaluation
        fSplit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        fSplit2.setDividerSize(0);
        fVarAssList = new JList();
        // MouseListener to show Object Properties if requested
        MouseAdapter ma = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Entry var = (Entry) fVarAssList.getSelectedValue();
                    new ObjectBrowser(fSystem, var);
                }
            }
        };
        fVarAssList.addMouseListener(ma);
        fVarAssList.setToolTipText("Double click to open object browser");

        fScrollVarAssList = new JScrollPane(fVarAssList);
        // Termview Window
        fSubstituteWin = new JTextArea();
        fSubstituteWin.setEditable(false);
        fSubstituteWin.setLineWrap(true);
        fSubstituteWin.setWrapStyleWord(true);
        fScrollSubstituteWin = new JScrollPane(fSubstituteWin);

        // add the mouse listener for the config and the tree popup menu
        // to the content pane
        fTree.addMouseListener(fMouseListener);
        fVarAssList.addMouseListener(fMouseListener);
        fSubstituteWin.addMouseListener(fMouseListener);
        fTopLabel.addMouseListener(fMouseListener);

        addMouseListener(fMouseListener);
        // shortcuts
        JButton shortcuts = new JButton("Fit width");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('f');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Default configuration");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('d');
        shortcuts = new JButton("Set as default");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('s');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Capture to file");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('c');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Extended Search");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('x');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Variable assignment window");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('v');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Subexpression evaluation window");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('e');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Late variable assignment");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('1');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Early variable assignment");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('2');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Variable assignment & substitution");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('3');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Variable substitution");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('4');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("No variable assignment");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('5');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("No highlighting");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('0');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Term highlighting");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('9');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Subtree highlighting");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('8');
        shortcuts.addActionListener(fActionListener);
        shortcuts = new JButton("Complete Subtree highlighting");
        add(shortcuts, BorderLayout.CENTER);
        shortcuts.setMnemonic('7');
        shortcuts.addActionListener(fActionListener);

        // add the tree to the contentpane
        add(fSplit1, BorderLayout.CENTER);

    } // ExprEvalBrowser

    /**
     * collapses all nodes recursively under the node of the hand overed path
     */
    void collapseAll(TreePath path) {
        Object node = path.getLastPathComponent();
        TreeModel model = fTree.getModel();
        if (model.isLeaf(node))
            return;
        int num = model.getChildCount(node);
        for (int i = 0; i < num; i++)
            collapseAll(path.pathByAddingChild(model.getChild(node, i)));
        fTree.collapsePath(path);
    }

    /**
     * expands all nodes recursively under the node of the hand overed path
     */
    void expandAll(TreePath path) {
        Object node = path.getLastPathComponent();
        TreeModel model = fTree.getModel();
        if (model.isLeaf(node))
            return;
        fTree.expandPath(path);
        int num = model.getChildCount(node);
        for (int i = 0; i < num; i++)
            expandAll(path.pathByAddingChild(model.getChild(node, i)));

    }

    /**
     * expands all nodes recursively under the node of the hand overed path if a
     * node has the same result value as
     * 
     * @param:val that node itself and it's subtree is not expanded
     */
    void expandAllTrue(TreePath path, String stop) {
        Object node = path.getLastPathComponent();
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;
        EvalNode enode = (EvalNode) dnode.getUserObject();
        TreeModel model = fTree.getModel();
        if (model.isLeaf(node) || enode.getResult() == stop)
            return;
        else if (fEarlyVarEval) {
            DefaultMutableTreeNode dchild = dnode;
            EvalNode echild = (EvalNode) dchild.getUserObject();
            while (dchild.getChildCount() > 0
                    && echild.getExpr().name().equals("var")
                    && !echild.getResult().equals("true")
                    && !echild.getResult().equals("false")) {
                dchild = (DefaultMutableTreeNode) dchild.getChildAt(0);
                echild = (EvalNode) dchild.getUserObject();
            }
            if (echild.getResult().equals(stop))
                return;
        }
        fTree.expandPath(path);
        int num = model.getChildCount(node);
        for (int i = 0; i < num; i++)
            expandAllTrue(path.pathByAddingChild(model.getChild(node, i)), stop);
    }

    /**
     * finds collapsed visible nodes
     */
    void expandCollapsedVisibleNodes(TreePath path, boolean val) {
        Object node = path.getLastPathComponent();
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;
        EvalNode enode = (EvalNode) dnode.getUserObject();
        TreeModel model = fTree.getModel();
        if (fTree.isVisible(path) && fTree.isCollapsed(path)) {
            if (val && enode.getResult().equals("true"))
                expandAllTrue(path, "false");
            else if (!val && enode.getResult().equals("false"))
                expandAllTrue(path, "true");
            else if (fEarlyVarEval) {
                DefaultMutableTreeNode dchild = dnode;
                EvalNode echild = (EvalNode) dchild.getUserObject();
                while (dchild.getChildCount() > 0
                        && echild.getExpr().name().equals("var")) {
                    dchild = (DefaultMutableTreeNode) dchild.getChildAt(0);
                    echild = (EvalNode) dchild.getUserObject();
                }
                if (val && echild.getResult().equals("true"))
                    expandAllTrue(new TreePath(dnode.getPath()), "false");
                if (!val && echild.getResult().equals("false"))
                    expandAllTrue(new TreePath(dnode.getPath()), "true");
            }
            return;
        }
        int num = model.getChildCount(node);
        for (int i = 0; i < num; i++)
            expandCollapsedVisibleNodes(path.pathByAddingChild(model.getChild(
                    node, i)), val);
    }

    /**
     * MouseAdapter for Expand- Collapse- and Config-Popupmenu in the tree
     */
    class EvalMouseAdapter extends MouseAdapter {

        // the tree actions
        JPopupMenu treepop;

        Action expandAction, expandAllAction, copyAction;

        TreePath clickedPath;

        // Configuration Menu
        JPopupMenu popup = new JPopupMenu();

        public EvalMouseAdapter() {
            fPopup = popup;
            // Expand- Collapse- Popupmenu Action for the tree
            treepop = new JPopupMenu();
            // context sensitive Menu needs actions added to the menu
            // collapsed node needs expand action and expanded node collapse
            // action
            expandAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (clickedPath == null)
                        return;
                    if (fTree.isExpanded(clickedPath))
                        fTree.collapsePath(clickedPath);
                    else
                        fTree.expandPath(clickedPath);
                }
            };
            treepop.add(expandAction);

            // ExpandAll, CollapseAll PopupMenu Action
            expandAllAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (clickedPath == null)
                        return;
                    if (fTree.isExpanded(clickedPath)) {
                        collapseAll(clickedPath);
                    } else {
                        expandAll(clickedPath);
                    }
                }
            };
            treepop.add(expandAllAction);
            // Copy Action for the PopupMenu
            copyAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    // there are 3 clipboards on X11 - get the primary clipboard
                    // there
                    Clipboard clip = getToolkit().getSystemSelection();
                    // get the system clipboard on windows
                    if (clip == null)
                        clip = getToolkit().getSystemClipboard();
                    DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) clickedPath
                            .getLastPathComponent();
                    EvalNode enode = (EvalNode) dnode.getUserObject();
                    StringSelection cont = new StringSelection(enode
                            .toNormString());
                    clip.setContents(cont, null);
                }
            };
            treepop.add(copyAction);

            // Initilize the Configuration Menu
            // extended search
            JMenu extendedSearch = new JMenu("Extended evaluation");
            extendedSearch.add(fExtendedExists);
            extendedSearch.add(fExtendedForAll);
            extendedSearch.add(fExtendedAnd);
            extendedSearch.add(fExtendedOr);
            extendedSearch.add(fExtendedImplies);
            extendedSearch.addSeparator();
            extendedSearch.add(fExtendedAll);
            extendedSearch.addItemListener(fItemListener);
            fExtendedExists.addItemListener(fItemListener);
            fExtendedForAll.addItemListener(fItemListener);
            fExtendedAnd.addItemListener(fItemListener);
            fExtendedOr.addItemListener(fItemListener);
            fExtendedImplies.addItemListener(fItemListener);
            fExtendedAll.addItemListener(fItemListener);
            popup.add(extendedSearch);
            popup.addSeparator();
            // Variable-Eval-Window
            fVarAssListChk.addItemListener(fItemListener);
            popup.add(fVarAssListChk);
            // Term-View-Window
            fVarSubstituteWinChk.addItemListener(fItemListener);
            popup.add(fVarSubstituteWinChk);
            // the 4 Tree Views
            JMenu menu = new JMenu("Tree views");
            ButtonGroup bg_treeview = new ButtonGroup();
            for (int i = 0; i < fTreeViews.length; i++) {
                fTreeViews[i].addActionListener(fActionListener);
                menu.add(fTreeViews[i]);
                bg_treeview.add(fTreeViews[i]);
            }
            popup.add(menu);
            // Syntax Highlighting
            menu = new JMenu("True-false highlighting");
            ButtonGroup bg_highlighting = new ButtonGroup();
            for (int i = 0; i < fTreeHighlightings.length; i++) {
                fTreeHighlightings[i].addActionListener(fActionListener);
                menu.add(fTreeHighlightings[i]);
                bg_highlighting.add(fTreeHighlightings[i]);
            }
            menu.addSeparator();
            fNoColorHighlightingChk.addItemListener(fItemListener);
            menu.add(fNoColorHighlightingChk);
            popup.add(menu);
            // Button for Adjustments to window-size
            JMenuItem treewidth = new JMenuItem("Fit width");
            popup.add(treewidth);
            treewidth.addActionListener(fActionListener);
            popup.addSeparator();
            // Button for Default configuration state
            JMenuItem defaultButton = new JMenuItem("Default configuration");
            popup.add(defaultButton);
            defaultButton.addActionListener(fActionListener);
            JMenuItem setDefaultButton = new JMenuItem("Set as default");
            popup.add(setDefaultButton);
            setDefaultButton.addActionListener(fActionListener);
            // Separator hinzufuegen
            popup.addSeparator();
            // Capture-Button
            JMenuItem capture = new JMenuItem("Capture to file");
            popup.add(capture);
            capture.addActionListener(fActionListener);
            // set the Mnemonics / shortcuts
            extendedSearch.setMnemonic('x');
            fVarAssListChk.setMnemonic('v');
            fVarSubstituteWinChk.setMnemonic('e');
            defaultButton.setMnemonic('d');
            setDefaultButton.setMnemonic('s');
            treewidth.setMnemonic('f');
            capture.setMnemonic('c');
        } // EvalMouseAdapter

        public void mouseReleased(MouseEvent e) {
            if (e.getComponent() == fTree) {
                if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                    int x = e.getX();
                    int y = e.getY();
                    TreePath path = fTree.getPathForLocation(x, y);
                    if (path == null) {
                        popup.show(fTree, x, y);
                        return;
                    }
                    if (fTree.isExpanded(path)) {
                        expandAction.putValue(Action.NAME, "Collapse");
                        expandAllAction.putValue(Action.NAME, "Collapse all");
                        copyAction.putValue(Action.NAME, "Copy");
                    } else {
                        expandAction.putValue(Action.NAME, "Expand");
                        expandAllAction.putValue(Action.NAME, "Expand all");
                        copyAction.putValue(Action.NAME, "Copy");
                    }
                    fTree.setSelectionPath(path);
                    fTree.scrollPathToVisible(path);
                    treepop.show(fTree, x, y);
                    clickedPath = path;
                }

            } else if (e.isPopupTrigger()
                    || e.getButton() == MouseEvent.BUTTON3)
                popup.show(e.getComponent(), e.getX(), e.getY());
            else if (e.getClickCount() == 2
                    && (e.getComponent() == fTopLabel || e.getComponent() == fSouthPanel)) {
                if (fTopLabel.getText() != "") {
                    fTopLabel.setPreferredSize(new Dimension(0, 5));
                    fTopLabel.setText("");
                    fTopLabel.setVisible(false);
                    fTopLabel.setVisible(true);
                } else {
                    fTopLabel.setText(fHtmlTitle);
                    adjustTopWidth(fTitle, fHtmlTitle);
                }
                fParent.repaint();
            } else if (e.getButton() == MouseEvent.BUTTON1
                    && e.getComponent() != fVarAssList
                    && e.getComponent() != fSubstituteWin) {
                fTree.setSelectionRow(-1);
                fVarAssList.setListData(new Vector<Entry>());
                fSubstituteWin.setText(null);
            }

        }
    }

    /**
     * ItemListener for the combobox and the checkboxes
     */
    class EvalItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            Object object = e.getSource();
            // Implemented Actions for the comboTree
            if (object == fComboTreeDisplays
                    && e.getStateChange() == ItemEvent.SELECTED) {
                JComboBox selectedChoice = (JComboBox) e.getSource();
                if (selectedChoice.getSelectedItem().equals("Expand all"))
                    expandAll(new TreePath(fTopNode.getPath()));
                else // if(selectedChoice.getSelectedItem().equals("Collapse")){
                if (e.getItem().equals("Collapse")) {
                    TreePath rootpath = new TreePath(fTopNode.getPath());
                    collapseAll(rootpath);
                    fTree.expandPath(rootpath);
                } else if (selectedChoice.getSelectedItem().equals(
                        "Expand all true")) {
                    TreePath rootpath = new TreePath(fTopNode.getPath());
                    expandCollapsedVisibleNodes(rootpath, true);
                } else if (selectedChoice.getSelectedItem().equals(
                        "Expand all false")) {
                    TreePath rootpath = new TreePath(fTopNode.getPath());
                    expandCollapsedVisibleNodes(rootpath, false);
                } else if (selectedChoice.getSelectedItem().equals(
                        "Close window")) {
                    fParent.setVisible(false);
                    fParent.dispose();
                }
            }
            // Action for Checkbox extended search
            else if (object == fExtendedExists || object == fExtendedForAll
                    || object == fExtendedAnd || object == fExtendedOr
                    || object == fExtendedImplies) {
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);

            } else if (object == fExtendedAll) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fExtendedExists.setSelected(true);
                    fExtendedForAll.setSelected(true);
                    fExtendedAnd.setSelected(true);
                    fExtendedOr.setSelected(true);
                    fExtendedImplies.setSelected(true);
                } else {
                    fExtendedExists.setSelected(false);
                    fExtendedForAll.setSelected(false);
                    fExtendedAnd.setSelected(false);
                    fExtendedOr.setSelected(false);
                    fExtendedImplies.setSelected(false);
                }
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);
            }
            // Action for Checkbox ExtraEval
            else if (object == fVarAssListChk) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (fSplit2.getBottomComponent() != null)
                        fSplit2.setDividerSize(fDefaultDividerSize);
                    fSplit2.setTopComponent(fScrollVarAssList);
                    if (fSplit1.getRightComponent() == null)
                        setSplitDivider();
                } else {
                    fSplit2.remove(fScrollVarAssList);
                    fSplit2.setDividerSize(0);
                    if (fSplit2.getTopComponent() == null
                            && fSplit2.getBottomComponent() == null) {
                        fSplit1.remove(fSplit2);
                        fSplit1.setDividerSize(0);
                    }
                }
            }
            // Action for Checkbox TermView
            else if (object == fVarSubstituteWinChk) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fVarSubstituteWinChk.setSelected(true);
                    if (fSplit2.getTopComponent() != null)
                        fSplit2.setDividerSize(fDefaultDividerSize);
                    fSplit2.setBottomComponent(fScrollSubstituteWin);
                    if (fSplit1.getRightComponent() == null)
                        setSplitDivider();
                } else {
                    fVarSubstituteWinChk.setSelected(false);
                    fSplit2.remove(fScrollSubstituteWin);
                    fSplit2.setDividerSize(0);
                    if (fSplit2.getTopComponent() == null
                            && fSplit2.getBottomComponent() == null) {
                        fSplit1.remove(fSplit2);
                        fSplit1.setDividerSize(0);
                    }
                }
            }
            // Action for Checkbox fNoColorHighlightingChk
            else if (object == fNoColorHighlightingChk) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fNoColorHighlightingChk.setSelected(true);
                } else {
                    fNoColorHighlightingChk.setSelected(false);
                }
                markVisibleNodes(fTopNode);
                fTreeModel.reload();
                fTree.revalidate();
                fTree.repaint();
                expandMarkedNodes(fTopNode);
            }

        }

        public void setSplitDivider() {
            fSplit1.setDividerSize(fDefaultDividerSize);
            fSplit1.setRightComponent(fSplit2);
            int width = getWidth();
            int subwidth = width / 3;
            if (subwidth > 200)
                subwidth = 200;
            if (subwidth < 50)
                subwidth = 50;
            if (fFirstInvoke1 || fFirstInvoke2) {
                fParent.setSize(width + subwidth, getHeight());
                if (fParent.getWidth() > getToolkit().getScreenSize().width) {
                    fParent.setSize(getToolkit().getScreenSize().width, fSplit1
                            .getHeight());
                    fSplit1.setDividerLocation(fParent.getWidth() - 200);
                }
                fSplit1.setDividerLocation(width);
            } else
                fSplit1.setDividerLocation(width - subwidth
                        + (fDefaultDividerSize * 2));
        }

    }

    /**
     * ActionListener for all the buttons in the configuration menu
     */
    class EvalActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            String command = event.getActionCommand();
            if (command.equals("Close")) {
                close();
            } else if (command.equals("Fit width")) {
                deleteHTMLTags(fTopNode);
                formatNodes(fTopNode);
                // adjustTopWidth(fTopLabel,fTitle,fTopLabel.getText());
            } else if (command.equals("Extended Search")) {
                boolean sel = fExtendedAll.isSelected();
                fExtendedAll.setSelected(!sel);
            } else if (command.equals("Variable assignment window")) {
                boolean sel = fVarAssListChk.isSelected();
                fVarAssListChk.setSelected(!sel);
            } else if (command.equals("Subexpression evaluation window")) {
                boolean sel = fVarSubstituteWinChk.isSelected();
                fVarSubstituteWinChk.setSelected(!sel);
            } else if (command.equals("No highlighting")) {
                fTreeHighlightings[0].setSelected(true);
                fTree.setCellRenderer(new DefaultTreeCellRenderer());
                fTree.repaint();
            } else if (command.equals("Term highlighting")) {
                fTreeHighlightings[1].setSelected(true);
                fTree.setCellRenderer(new TermRenderer());
                fTree.repaint();
            } else if (command.equals("Subtree highlighting")) {
                fTreeHighlightings[2].setSelected(true);
                fTree.setCellRenderer(new SubtreeRenderer());
                fTree.repaint();
            } else if (command.equalsIgnoreCase("Complete subtree hightliting")) {
                fTreeHighlightings[3].setSelected(true);
                fTree.setCellRenderer(new CompleteSubtreeRenderer());
                fTree.repaint();
            } else if (command.equals("Default configuration")) {
                // extended evaluation checkboxes
                fExtendedAll.setSelected(false);
                String prop = System.getProperty(
                        "use.gui.view.evalbrowser.exists", "false");
                if (prop.equals("true"))
                    fExtendedExists.setSelected(true);
                else
                    fExtendedExists.setSelected(false);
                prop = System.getProperty("use.gui.view.evalbrowser.forall",
                        "false");
                if (prop.equals("true"))
                    fExtendedForAll.setSelected(true);
                else
                    fExtendedForAll.setSelected(false);
                prop = System.getProperty("use.gui.view.evalbrowser.and",
                        "false");
                if (prop.equals("true"))
                    fExtendedAnd.setSelected(true);
                else
                    fExtendedAnd.setSelected(false);
                prop = System.getProperty("use.gui.view.evalbrowser.or",
                        "false");
                if (prop.equals("true"))
                    fExtendedOr.setSelected(true);
                else
                    fExtendedOr.setSelected(false);
                prop = System.getProperty("use.gui.view.evalbrowser.implies",
                        "false");
                if (prop.equals("true"))
                    fExtendedImplies.setSelected(true);
                else
                    fExtendedImplies.setSelected(false);
                prop = System
                        .getProperty(
                                "use.gui.view.evalbrowser.VarAssignmentWindow",
                                "false");
                if (prop.equals("true"))
                    fVarAssListChk.setSelected(true);
                else
                    fVarAssListChk.setSelected(false);
                fFirstInvoke1 = false;
                prop = System.getProperty(
                        "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                        "false");
                if (prop.equals("true"))
                    fVarSubstituteWinChk.setSelected(true);
                else
                    fVarSubstituteWinChk.setSelected(false);
                fFirstInvoke2 = false;
                prop = System.getProperty("use.gui.view.evalbrowser.treeview",
                        "false");
                if (prop.equals("earlyVarAssignment"))
                    actionPerformed(new ActionEvent(fTreeViews[1], 0,
                            "Early variable assignment"));
                else if (prop.equals("VarAssignment&Substitution"))
                    actionPerformed(new ActionEvent(fTreeViews[2], 0,
                            "Variable assignment & substitution"));
                else if (prop.equals("VarSubstitution"))
                    actionPerformed(new ActionEvent(fTreeViews[3], 0,
                            "Variable substitution"));
                else if (prop.equals("noVarAssignment"))
                    actionPerformed(new ActionEvent(fTreeViews[4], 0,
                            "No variable assignment"));
                else
                    actionPerformed(new ActionEvent(fTreeViews[0], 0,
                            "Late variable assignment"));
                prop = System.getProperty(
                        "use.gui.view.evalbrowser.highlighting", "false");
                if (prop.equals("term")) {
                    fTreeHighlightings[1].setSelected(true);
                    actionPerformed(new ActionEvent(fTreeHighlightings[1], 0,
                            "Term highlighting"));
                } else if (prop.equals("subtree")) {
                    fTreeHighlightings[2].setSelected(true);
                    actionPerformed(new ActionEvent(fTreeHighlightings[2], 0,
                            "Subtree highlighting"));
                } else if (prop.equals("complete")) {
                    fTreeHighlightings[3].setSelected(true);
                    actionPerformed(new ActionEvent(fTreeHighlightings[3], 0,
                            "Complete subtree hightliting"));
                } else {
                    fTreeHighlightings[0].setSelected(true);
                    actionPerformed(new ActionEvent(fTreeHighlightings[3], 0,
                            "No highlighting"));
                }
                prop = System.getProperty(
                        "use.gui.view.evalbrowser.blackHighlighting", "false");
                if (prop.equals("true"))
                    fNoColorHighlightingChk.setSelected(true);
                else
                    fNoColorHighlightingChk.setSelected(false);
                // delete the HTML-Formatting
                deleteHTMLTags(fTopNode);
            } else if (command.equals("Set as default")) {
                new SetDefaultDialog(fParent);

            } else if (command.equals("Capture to file")) {
                // CaptureTheWindow ctw = new CaptureTheWindow();
                Thread t = new Thread(new CaptureTheWindow((Component) fParent));
                t.start();

            } else if (command.equals("Late variable assignment")) {
                fTreeViews[0].setSelected(true);
                fHideVarAss = false;
                fEarlyVarEval = false;
                fVarSubstitution = false;
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);
            } else if (command.equals("Early variable assignment")) {
                fTreeViews[1].setSelected(true);
                fHideVarAss = true;
                fEarlyVarEval = true;
                fVarSubstitution = false;
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);
            } else if (command.equals("Variable assignment & substitution")) {
                fTreeViews[2].setSelected(true);
                fHideVarAss = true;
                fEarlyVarEval = true;
                fVarSubstitution = true;
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);
            } else if (command.equals("Variable substitution")) {
                fTreeViews[3].setSelected(true);
                fHideVarAss = true;
                fEarlyVarEval = false;
                fVarSubstitution = true;
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);
            } else if (command.equals("No variable assignment")) {
                fTreeViews[4].setSelected(true);
                fHideVarAss = true;
                fEarlyVarEval = false;
                fVarSubstitution = false;
                DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel
                        .getRoot();
                markVisibleNodes(dnode);
                // rebuild the tree
                fTopNode.removeAllChildren();
                EvalNode enode = (EvalNode) fTopNode.getUserObject();
                createNodes(fTopNode, enode);
                fTreeModel.reload();
                // expand the nodes expanded before
                expandMarkedNodes(dnode);
            }

        }

        /**
         * traverse the tree and formats the width of the treenodes
         */
        public void formatNodes(TreeNode node) {
            // calculate the total width of the tree and the indent size
            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;
            EvalNode enode = (EvalNode) dnode.getUserObject();
            // calculate the indent-size
            if (node == fTopNode && fTopNode.getChildCount() > 0) {
                TreePath rootpath = new TreePath(fTopNode.getPath());
                if (fTree.isCollapsed(rootpath)) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) (fTopNode
                            .getChildAt(0));
                    TreePath childpath = new TreePath(child.getPath());
                    fTree.expandPath(rootpath);
                    TreeUI ui = fTree.getUI();
                    Rectangle rec = ui.getPathBounds(fTree, childpath);
                    fTreeIndent = rec.getX();
                    fTree.collapsePath(rootpath);
                } else {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) (fTopNode
                            .getChildAt(0));
                    TreePath childpath = new TreePath(child.getPath());
                    TreeUI ui = fTree.getUI();
                    Rectangle rec = ui.getPathBounds(fTree, childpath);
                    fTreeIndent = rec.getX();
                }
            }
            // the new implementation
            double nodeIndent = fTreeIndent * dnode.getLevel();
            nodeIndent += fTreeIndent * 2 + 4;
            double nodeWidth = fScrollTree.getWidth() - nodeIndent;
            // detect the fonr metrics of the current node
            char highlighting[] = enode.getHighlighting();
            FontMetrics fm = fTree.getFontMetrics(fTree.getFont());
            if (!fNoColorHighlightingChk.isSelected()
                    || fTreeHighlightings[0].isSelected()
                    || highlighting.length < 2)
                fm = fTree.getFontMetrics(fTree.getFont());
            else if (fTreeHighlightings[1].isSelected()
                    && (enode.getResult() == "true" || enode.getResult() == "false"))
                fm = fTree
                        .getFontMetrics(fTree.getFont().deriveFont(Font.BOLD));
            else if (fTreeHighlightings[2].isSelected()
                    && (highlighting[0] == 't' || highlighting[0] == 'f'))
                fm = fTree
                        .getFontMetrics(fTree.getFont().deriveFont(Font.BOLD));
            else if (fTreeHighlightings[3].isSelected()
                    && (highlighting[1] == 't' || highlighting[0] == 'f'))
                fm = fTree
                        .getFontMetrics(fTree.getFont().deriveFont(Font.BOLD));
            // set the width for the node if the node text goes beyond the
            // viewpane
            if (nodeIndent + fm.stringWidth(enode.toNormString()) > fScrollTree
                    .getWidth())
                enode.setTabWidth(nodeWidth);
            fTreeModel.nodeChanged(node);
            // traverse the remaining child nodes
            // if node is leaf loop will not enter
            for (int i = 0; i < node.getChildCount(); i++)
                formatNodes(node.getChildAt(i));
        }

        /**
         * formats the nodes into the initial state without width specification
         */
        public void deleteHTMLTags(TreeNode node) {
            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;
            EvalNode enode = (EvalNode) dnode.getUserObject();
            enode.resetTabWidth();
            fTreeModel.nodeChanged(node);
            // traverse the remaining nodes
            for (int i = 0; i < node.getChildCount(); i++)
                deleteHTMLTags(node.getChildAt(i));
        }

    }

    /**
     * Set-Default-Configuration Dialog
     */
    class SetDefaultDialog extends JDialog {

        public SetDefaultDialog(JFrame mother) {
            super(mother, "Evaluation browser configuration", true);
            Container c = getContentPane();
            c.setLayout(new GridLayout(2, 1));
            JLabel titleLabel = new JLabel(
                    "Set current evaluation browser configurations as default");
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setVerticalAlignment(JLabel.CENTER);
            c.add(titleLabel);
            JPanel p = new JPanel(new FlowLayout());
            JButton b1 = new JButton("For this session");
            // action for setting the defaults for only the current session
            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (fExtendedExists.isSelected())
                        System.setProperty("use.gui.view.evalbrowser.exists",
                                "true");
                    else
                        System.setProperty("use.gui.view.evalbrowser.exists",
                                "false");
                    if (fExtendedForAll.isSelected())
                        System.setProperty("use.gui.view.evalbrowser.forall",
                                "true");
                    else
                        System.setProperty("use.gui.view.evalbrowser.forall",
                                "false");
                    if (fExtendedOr.isSelected())
                        System.setProperty("use.gui.view.evalbrowser.or",
                                "true");
                    else
                        System.setProperty("use.gui.view.evalbrowser.or",
                                "false");
                    if (fExtendedAnd.isSelected())
                        System.setProperty("use.gui.view.evalbrowser.and",
                                "true");
                    else
                        System.setProperty("use.gui.view.evalbrowser.and",
                                "false");
                    if (fExtendedImplies.isSelected())
                        System.setProperty("use.gui.view.evalbrowser.implies",
                                "true");
                    else
                        System.setProperty("use.gui.view.evalbrowser.implies",
                                "false");
                    if (fVarAssListChk.isSelected())
                        System.setProperty(
                                "use.gui.view.evalbrowser.VarAssignmentWindow",
                                "true");
                    else
                        System.setProperty(
                                "use.gui.view.evalbrowser.VarAssignmentWindow",
                                "false");
                    if (fVarSubstituteWinChk.isSelected())
                        System
                                .setProperty(
                                        "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                                        "true");
                    else
                        System
                                .setProperty(
                                        "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                                        "false");
                    if (fTreeViews[0].isSelected())
                        System.setProperty("use.gui.view.evalbrowser.treeview",
                                "lateVarAssignment");
                    else if (fTreeViews[1].isSelected())
                        System.setProperty("use.gui.view.evalbrowser.treeview",
                                "earlyVarAssignment");
                    else if (fTreeViews[2].isSelected())
                        System.setProperty("use.gui.view.evalbrowser.treeview",
                                "VarAssignment&Substitution");
                    else if (fTreeViews[3].isSelected())
                        System.setProperty("use.gui.view.evalbrowser.treeview",
                                "VarSubstitution");
                    else if (fTreeViews[4].isSelected())
                        System.setProperty("use.gui.view.evalbrowser.treeview",
                                "noVarAssignment");
                    if (fTreeHighlightings[0].isSelected())
                        System.setProperty(
                                "use.gui.view.evalbrowser.highlighting", "no");
                    else if (fTreeHighlightings[1].isSelected())
                        System.setProperty(
                                "use.gui.view.evalbrowser.highlighting", "term");
                    else if (fTreeHighlightings[2].isSelected())
                        System.setProperty(
                                "use.gui.view.evalbrowser.highlighting",
                                "subtree");
                    else if (fTreeHighlightings[3].isSelected())
                        System.setProperty(
                                "use.gui.view.evalbrowser.highlighting",
                                "complete");
                    if (fNoColorHighlightingChk.isSelected())
                        System.setProperty(
                                "use.gui.view.evalbrowser.blackHighlighting",
                                "true");
                    else
                        System.setProperty(
                                "use.gui.view.evalbrowser.blackHighlighting",
                                "false");
                    setVisible(false);
                    dispose();
                }
            });
            JButton b2 = new JButton("For all sessions");
            // action for setting the defaults and save them persistently in the
            // config file
            b2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String s = "";
                    File f = new File(System.getProperty("user.dir", null),
                            ".userc");
                    if (!f.exists())
                        f = new File(System.getProperty("user.home", null),
                                ".userc");
                    try {
                        // create new config-file if there was no
                        if (!f.exists())
                            f.createNewFile();
                        // f = new File(System.getProperty("user.home", null),
                        // ".userc");
                        // read the config file int string s
                        FileReader reader = new FileReader(f);
                        for (int c; (c = reader.read()) != -1;) {
                            s += (char) c;
                        }
                        reader.close();
                        if (s.length() != 0 && s.charAt(s.length() - 1) != '\n')
                            s += "\n";
                        // reconfigure s
                        s = setConfigPoint(s,
                                "use.gui.view.evalbrowser.exists",
                                fExtendedExists.isSelected());
                        s = setConfigPoint(s,
                                "use.gui.view.evalbrowser.forall",
                                fExtendedForAll.isSelected());
                        s = setConfigPoint(s, "use.gui.view.evalbrowser.and",
                                fExtendedAnd.isSelected());
                        s = setConfigPoint(s, "use.gui.view.evalbrowser.or",
                                fExtendedOr.isSelected());
                        s = setConfigPoint(s,
                                "use.gui.view.evalbrowser.implies",
                                fExtendedImplies.isSelected());
                        s = setConfigPoint(s,
                                "use.gui.view.evalbrowser.VarAssignmentWindow",
                                fVarAssListChk.isSelected());
                        s = setConfigPoint(
                                s,
                                "use.gui.view.evalbrowser.SubExprSubstitutionWindow",
                                fVarSubstituteWinChk.isSelected());
                        if (fTreeViews[0].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.treeview",
                                    "lateVarAssignment");
                        else if (fTreeViews[1].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.treeview",
                                    "earlyVarAssignment");
                        else if (fTreeViews[2].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.treeview",
                                    "VarAssignment&Substitution");
                        else if (fTreeViews[3].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.treeview",
                                    "VarSubstitution");
                        else if (fTreeViews[4].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.treeview",
                                    "noVarAssignment");
                        if (fTreeHighlightings[0].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.highlighting", "no");
                        else if (fTreeHighlightings[1].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.highlighting",
                                    "term");
                        else if (fTreeHighlightings[2].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.highlighting",
                                    "subtree");
                        else if (fTreeHighlightings[3].isSelected())
                            s = setConfigPoint(s,
                                    "use.gui.view.evalbrowser.highlighting",
                                    "complete");
                        s = setConfigPoint(s,
                                "use.gui.view.evalbrowser.blackHighlighting",
                                fNoColorHighlightingChk.isSelected());

                        // rewrite the config file
                        FileWriter writer = new FileWriter(f);
                        for (int i = 0; i < s.length(); i++)
                            writer.write(s.charAt(i));
                        writer.close();
                    } catch (IOException e) {
                        new ErrorFrame(
                                "IO Error with Configuration File occured");
                    }
                    setVisible(false);
                    dispose();

                }
            });
            JButton b3 = new JButton("Cancel");
            // cancel-Action
            b3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    setVisible(false);
                    dispose();
                }
            });
            p.add(b1);
            p.add(b2);
            p.add(b3);
            c.add(p);
            pack();
            setVisible(true);
        }

        /**
         * sets the desired value to the attribute in the configure file. this
         * method is for the non-boolean attributes.
         * 
         * @return the changed content of the configure file
         */
        public String setConfigPoint(String confFileContent, String attr,
                String val) {
            confFileContent = "\n" + confFileContent;
            String escaptedAttr = "";
            for (int i = 0; i < attr.length(); i++) {
                if (attr.charAt(i) == '.')
                    escaptedAttr += "\\.";
                else
                    escaptedAttr += attr.charAt(i);
            }
            Pattern p = Pattern.compile("\n" + escaptedAttr);
            String parts[] = p.split(confFileContent);
            confFileContent = "";
            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    confFileContent += truncatePart(parts[i]);
                } else {
                    System.setProperty(attr, val);
                    confFileContent += truncatePart(parts[i]) + "\n" + attr
                            + "=" + val;
                }
            }
            if (parts.length == 1) {
                if (confFileContent.length() != 0
                        && confFileContent.charAt(confFileContent.length() - 1) != '\n')
                    confFileContent += "\n";
                System.setProperty(attr, val);
                confFileContent += attr + "=" + val + "\n";
            }
            return confFileContent.substring(1);

        }

        /**
         * sets the desired value to the attribute in the configure file this
         * method is for the boolean attributes.
         * 
         * @return the changed content of the configure file
         */
        public String setConfigPoint(String confFileContent, String attr,
                boolean boolVal) {
            String val;
            if (boolVal)
                val = "true";
            else
                val = "false";
            confFileContent = "\n" + confFileContent;
            String escaptedAttr = "";
            for (int i = 0; i < attr.length(); i++) {
                if (attr.charAt(i) == '.')
                    escaptedAttr += "\\.";
                else
                    escaptedAttr += attr.charAt(i);
            }
            Pattern p = Pattern.compile("\n" + escaptedAttr);
            String parts[] = p.split(confFileContent);
            confFileContent = "";
            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    confFileContent += truncatePart(parts[i]);
                } else {
                    System.setProperty(attr, val);
                    confFileContent += truncatePart(parts[i]) + "\n" + attr
                            + "=" + val;
                }
            }
            if (parts.length == 1) {
                if (confFileContent.length() != 0
                        && confFileContent.charAt(confFileContent.length() - 1) != '\n')
                    confFileContent += "\n";
                System.setProperty(attr, val);
                confFileContent += attr + "=" + val + "\n";
            }
            return confFileContent.substring(1);
        }

        /**
         * detaches the value from the rest of the part of the config file
         * 
         * @param part
         *            the part of the config file
         * @return the rest of the part of the config file
         */
        private String truncatePart(String part) {
            for (int i = 0; i < part.length(); i++)
                if (part.charAt(i) == '\n') {
                    return part.substring(i);
                }
            return "";
        }
    } // SetDefaultDialog

    /**
     * shows an error Frame for the user
     */
    public class ErrorFrame extends JFrame {
        public ErrorFrame(String labelTxt) {
            super("Error message");
            JLabel label = new JLabel(labelTxt);
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            setSize(300, 100);
            setVisible(true);
            adjustTopWidth(label, labelTxt, labelTxt);
        }
    }

    /**
     * listener for the tree-modification-comobox in the SOUTH of the window
     */
    class EvalPopupMenuListener implements PopupMenuListener {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            fComboTreeDisplays.setSelectedIndex(-1);
            fComboTreeDisplays.removeItem("Display options");
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            if (fComboTreeDisplays.getSelectedIndex() == -1) {
                fComboTreeDisplays.addItem("Display options");
                fComboTreeDisplays.setSelectedItem("Display options");
            }
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            fComboTreeDisplays.addItem("Display options");
            fComboTreeDisplays.setSelectedItem("Display options");
        }
    }

    /**
     * Tree Listener for setting the substituted term in the Variable-
     * Substitution-Window
     */
    class TermSelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            EvalNode enode = (EvalNode) dnode.getUserObject();
            // get the Varbindings
            Vector<Entry> bindings = enode.getVarBindings();
            // set content to the variable window
            fVarAssList.setListData(bindings);
            // substitutes subexpressions with the values
            String subs = "";
            subs = enode.getExprAndValue();
            DefaultMutableTreeNode dnode2;
            EvalNode enode2;
            for (int i = 0; i < dnode.getChildCount(); i++) {
                if (i > 0 && dnode.getChildCount() > 2)
                    break;
                dnode2 = (DefaultMutableTreeNode) dnode.getChildAt(i);
                enode2 = (EvalNode) dnode2.getUserObject();
                subs = substituteSubExpr(subs, enode2.getExpr().toString(),
                        enode2.getResult());
            }
            fSubstituteWin.setText(enode.substituteVariables(subs));
        }

        /**
         * substitutes the subexpressions with the associated values
         * 
         * @return the expression with substituted subexpressions with their
         *         values
         */
        public String substituteSubExpr(String expr, String subExpr,
                String subValue) {
            String ret = expr;
            HashSet<Character> stoken = new HashSet<Character>();
            stoken.add(new Character(' '));
            stoken.add(new Character('<'));
            stoken.add(new Character('>'));
            stoken.add(new Character('('));
            stoken.add(new Character(')'));
            stoken.add(new Character('.'));
            stoken.add(new Character(':'));
            stoken.add(new Character('-'));
            Pattern p = Pattern.compile(escapeString(subExpr));
            String[] parts = p.split(ret);
            String help = "";
            for (int j = 0; j < parts.length; j++) {

                if (j == parts.length - 1)
                    help += parts[j];
                else {
                    help += parts[j];
                    char first = ' ';
                    if (parts[j].length() > 0)
                        first = parts[j].charAt(parts[j].length() - 1);
                    char second = ' ';
                    if (parts[j + 1] != null)
                        second = parts[j + 1].charAt(0);
                    if (stoken.contains(new Character(first))
                            && stoken.contains(new Character(second)))
                        help += subValue;
                    else
                        help += subExpr;
                }
            }
            ret = help;
            return ret;
        }

        /**
         * escapes the metacharacters .([{\^$|)?*+ with a backslash '\'
         * 
         * @param origin
         *            the original string
         * @return the espaped string
         */
        public String escapeString(String origin) {
            String ret = "";
            for (int i = 0; i < origin.length(); i++) {
                char c = origin.charAt(i);
                if (c == '.' || c == '(' || c == '[' || c == '{' || c == '\\'
                        || c == '^' || c == '$' || c == '|' || c == '}'
                        || c == ']' || c == ')' || c == '?' || c == '*')
                    ret += "\\";
                ret += origin.charAt(i);
            }
            return ret;
        }
    }

    /**
     * the TreeCellRenderer for term-Highlighting
     */
    class TermRenderer extends DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) value;
            EvalNode enode = (EvalNode) (dnode).getUserObject();
            if (!enode.htmlUsed())
                super.getTreeCellRendererComponent(tree, value, sel, expanded,
                        leaf, row, hasFocus);
            if ((enode.getResult()).equals("true")) {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(new Color(0, 0x80, 0));
                    setBackgroundNonSelectionColor(Color.white);
                }
            } else if ((enode.getResult()).equals("false")) {
                if (fNoColorHighlightingChk.isSelected()) {
                    // setFont(new Font("Courier",Font.PLAIN+Font.ITALIC,14));
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.white);
                    setBackgroundNonSelectionColor(Color.black);
                } else {
                    setFont(fDefaultFont);
                    setForeground(new Color(0xc0, 0, 0));
                    setBackgroundNonSelectionColor(Color.white);
                }
            } else {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont);
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                }
            }

            if (enode.htmlUsed())
                super.getTreeCellRendererComponent(tree, value, sel, expanded,
                        leaf, row, hasFocus);

            return this;

        }
    }

    /**
     * the TreeCellRenderer for subtree-Highlighting
     */
    class SubtreeRenderer extends DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) value;
            EvalNode enode = (EvalNode) dnode.getUserObject();
            if (!enode.htmlUsed())
                super.getTreeCellRendererComponent(tree, value, sel, expanded,
                        leaf, row, hasFocus);
            char[] highlightings = enode.getHighlighting();
            if (highlightings != null && highlightings[0] == 't') {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(new Color(0, 0x80, 0));
                    setBackgroundNonSelectionColor(Color.white);
                }
            } else if (highlightings != null && highlightings[0] == 'f') {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.white);
                    setBackgroundNonSelectionColor(Color.black);
                } else {
                    setFont(fDefaultFont);
                    setForeground(new Color(0xc0, 0, 0));
                    setBackgroundNonSelectionColor(Color.white);
                }
            } else {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont);
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                }
            }

            if (enode.htmlUsed()) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded,
                        leaf, row, hasFocus);
            }

            return this;
        }
    }

    /**
     * highlights Complete Subtree of the evaluated complete terms
     */
    class CompleteSubtreeRenderer extends DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) value;
            EvalNode enode = (EvalNode) dnode.getUserObject();
            if (!enode.htmlUsed())
                super.getTreeCellRendererComponent(tree, value, sel, expanded,
                        leaf, row, hasFocus);
            char[] highlightings = enode.getHighlighting();
            if (highlightings[1] == 't') {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(new Color(0, 0x80, 0));
                    setBackgroundNonSelectionColor(Color.white);
                }
            } else if (highlightings[1] == 'f') {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.white);
                    setBackgroundNonSelectionColor(Color.black);
                } else {
                    setFont(fDefaultFont);
                    setForeground(new Color(0xc0, 0, 0));
                    setBackgroundNonSelectionColor(Color.white);
                }
            } else {
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont);
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                }
            }
            if (enode.htmlUsed())
                super.getTreeCellRendererComponent(tree, value, sel, expanded,
                        leaf, row, hasFocus);
            return this;
        }
    }

    public void markVisibleNodes(TreeNode tnode) {
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) tnode;
        DefaultMutableTreeNode child;
        EvalNode enode = (EvalNode) dnode.getUserObject();
        TreePath path = new TreePath(dnode.getPath());
        // if(fTree.isExpanded(path) || (dnode.isLeaf() &&
        // fTree.isVisible(path)))
        if (fTree.isVisible(path))
            enode.setVisibleAttr(true);
        else
            enode.setVisibleAttr(false);
        for (int i = 0; i < dnode.getChildCount(); i++) {
            child = (DefaultMutableTreeNode) dnode.getChildAt(i);
            markVisibleNodes(child);
        }

    }

    public void expandMarkedNodes(TreeNode tnode) {
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) tnode;
        DefaultMutableTreeNode child;
        DefaultMutableTreeNode parent;
        if (dnode.isRoot())
            parent = dnode;
        else
            parent = (DefaultMutableTreeNode) dnode.getParent();
        EvalNode enode = (EvalNode) dnode.getUserObject();
        TreePath path = new TreePath(parent.getPath());
        if (enode.isVisible())
            fTree.expandPath(path);
        for (int i = 0; i < dnode.getChildCount(); i++) {
            child = (DefaultMutableTreeNode) dnode.getChildAt(i);
            expandMarkedNodes(child);
        }
    }

    public void adjustTopWidth(String text, String htmlText) {
        adjustTopWidth(fTopLabel, text, htmlText);
    }

    public void adjustTopWidth(JLabel label, String text, String htmlText) {
        // get the Display Size
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension displaySize = tk.getScreenSize();
        int maxWidth = (int) displaySize.getWidth();

        // Dimension windowSize = getSize();
        int windowSize = getWidth();

        if (windowSize < maxWidth)
            maxWidth = windowSize;
        // calculate the Dimension for the title
        FontMetrics fm = label.getFontMetrics(label.getFont());
        int topWidth = 0;
        int topHeight = fm.getHeight();
        // int topHeight = 0;
        Pattern p = Pattern.compile("\n");
        String s[] = p.split(text);
        for (int i = 0; i < s.length; i++) {
            // if the current string is wider
            if (topWidth < fm.stringWidth(s[i]))
                if (fm.stringWidth(s[i]) < maxWidth)
                    // string is between width so far and maxWidth
                    topWidth = fm.stringWidth(s[i]);
                else
                    topWidth = maxWidth;
            // increment the height with 1 line or more if needed
            topHeight += (fm.getHeight() * (1 + (fm.stringWidth(s[i]) / maxWidth)));
        }
        // set the Size of the South Frame
        label.setPreferredSize(new Dimension(topWidth, topHeight));
        // make changes visible
        label.setText(htmlText);
        label.setVisible(false);
        label.setVisible(true);
    }

    /**
     * closes and exists the eval browser window
     */
    public void close() {
        setVisible(false);
        fParent.dispose();
    }

    /**
     * returns the parent jframe, where the eval browser is added
     */
    public JFrame getFrame() {
        return fParent;
    }

    /**
     * sets the selection to the element/node results from the hand overed
     * element number
     * 
     * @param elemNr
     *            number of the child node from root which should be selected
     */
    public void setSelectionElement(int elemNr) {
        if (fTopNode.getChildCount() < elemNr + 2)
            fExtendedForAll.setSelected(true);
        fTree.setSelectionRow(elemNr + 2);
    }
}
