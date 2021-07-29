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

package org.tzi.use.gui.views.evalbrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
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

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.CaptureTheWindow;
import org.tzi.use.gui.util.MMHTMLPrintVisitor;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.ocl.expr.EvalNode;
import org.tzi.use.uml.ocl.expr.EvalNode.TreeValue;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.operations.StandardOperationsBoolean;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;
import org.tzi.use.uml.sys.MSystem;

/**
 * A tree view showing evaluation results of an expression. Each node is adorned
 * with the result of evaluating the sub-expression.
 *
 * @author Jens Bruening
 */
@SuppressWarnings("serial")
public class ExprEvalBrowser extends JPanel {
    
	private enum ShowVariableAssignment {
		LATE,
		EARLY,
		NEVER
	}
	
	private final MSystem fSystem;

	private ShowVariableAssignment varAssignmentMode = ShowVariableAssignment.LATE;
	
    // several attributes needed by the inner classes: e.g. the action- and
    // item-listener
    JFrame fParent;

    JLabel fTopLabel;

    JSplitPane fSplit1, fSplit2;

    JTree fTree;

    DefaultTreeModel fTreeModel;

    JList<Entry> fVarAssList;

    JEditorPane fSubstituteWin;

    JScrollPane fScrollSubstituteWin, fScrollVarAssList;

    JComboBox<String> fComboTreeDisplays;

    JScrollPane fScrollTree;

    JCheckBoxMenuItem fExtendedExists = new JCheckBoxMenuItem("exists");

    JCheckBoxMenuItem fExtendedForAll = new JCheckBoxMenuItem("forAll");

    JCheckBoxMenuItem fExtendedAnd = new JCheckBoxMenuItem("and");

    JCheckBoxMenuItem fExtendedOr = new JCheckBoxMenuItem("or");

    JCheckBoxMenuItem fExtendedImplies = new JCheckBoxMenuItem("implies");

    JMenuItem fExtendedAll = new JMenuItem("Set all");
    
    JMenuItem fExtendedNone = new JMenuItem("Set none");

    JCheckBoxMenuItem fVarAssListChk = new JCheckBoxMenuItem(
            "Variable assignment window");

    JCheckBoxMenuItem fVarSubstituteWinChk = new JCheckBoxMenuItem(
            "Subexpression evaluation window");

    JCheckBoxMenuItem fNoColorHighlightingChk = new JCheckBoxMenuItem("Black and white");

    JRadioButtonMenuItem[] rbVariableAssignment = {
            new JRadioButtonMenuItem("Late", true),
            new JRadioButtonMenuItem("Early"),
            new JRadioButtonMenuItem("Never")
    };
      
    JCheckBoxMenuItem cbSubstituteVariables = new JCheckBoxMenuItem("Variable Substitution", false);

    JRadioButtonMenuItem[] fTreeHighlightings = {
            new JRadioButtonMenuItem("No highlighting", true),
            new JRadioButtonMenuItem("Term highlighting"),
            new JRadioButtonMenuItem("Subtree highlighting"),
            new JRadioButtonMenuItem("Complete subtree highlighting") };

    DefaultMutableTreeNode fTopNode;

    JPanel fSouthPanel;

    int fDefaultDividerSize;

    double fTreeIndent = 0;

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

    private boolean isSubstituteVars() {
    	return cbSubstituteVariables.isSelected();
    }
    
    private TreeValue getNodeTreeValue(EvalNode node) {
    	Value value = node.getResult();
    	Type type = node.getExpression().type();
    	
    	if (value.equals(BooleanValue.TRUE)) {
            return TreeValue.TRUE;
        } else if (value.equals(BooleanValue.FALSE)) {
            return TreeValue.FALSE;
        } else if (value.equals(UndefinedValue.instance)
                && type.isTypeOfBoolean()) {
            return TreeValue.UNDEFINED;
        } else {
        	return TreeValue.INVALID;
        }
    }
    
    /**
     * builds the evaluation tree recursively from the EvalNodes
     */
    private void createNodes(DefaultMutableTreeNode treeParent, EvalNode node) {
        Expression parentExpr = node.getExpression();
        List<Entry> parentVars = node.getVarBindings();

        breakLabel: for(EvalNode child : node.children()) {
            Expression childExpr = child.getExpression();
            DefaultMutableTreeNode treeChild = new DefaultMutableTreeNode(child);
            
            TreeNode[] treePath = treeParent.getPath();
            // subtree highlighting
            DefaultMutableTreeNode dnode = treeParent;
            EvalNode enode;
            
            // Goes up the tree path and looks for the first 
            // parent node that has a boolean value (true, false, null) 
            TreeValue subtreeValue = getNodeTreeValue(child);
            if (subtreeValue == TreeValue.INVALID) {
                for (int i = treePath.length - 1; i >= 0; i--) {
                    dnode = (DefaultMutableTreeNode) treePath[i];
                    enode = (EvalNode) dnode.getUserObject();
                    subtreeValue = getNodeTreeValue(enode);
                    if(subtreeValue != TreeValue.INVALID){
                    	break;
                    }
                }
                
                if (subtreeValue == TreeValue.INVALID) {
                    EvalNode root = (EvalNode) fTopNode.getUserObject();
                    subtreeValue = getNodeTreeValue(root);
                }
            }
            child.setSubTreeValue(subtreeValue);
            
            
            // complete subtree highlighting
            TreeValue completeTreeValue = TreeValue.INVALID;
            dnode = (DefaultMutableTreeNode) treePath[0];
            enode = (EvalNode) dnode.getUserObject();
            if (treePath.length == 1) {
            	completeTreeValue = getNodeTreeValue(child);
            }
            
            // Searches from the root + 1 node down to
            // the first node on the tree path that has a 
            // valid boolean value (true, false, null)
            for (int i = 1; i < treePath.length; i++) {
                dnode = (DefaultMutableTreeNode) treePath[i];
                enode = (EvalNode) dnode.getUserObject();
                
                if (enode.isEarlyVarNode())
                    continue;
                
                completeTreeValue = getNodeTreeValue(enode);
                if(completeTreeValue != TreeValue.INVALID){
                	break;
                }
            }
            
            if (completeTreeValue == TreeValue.INVALID) {
                EvalNode root = (EvalNode) fTopNode.getUserObject();
                completeTreeValue = getNodeTreeValue(root);
            }
            
            child.setCompleteTreeValue(completeTreeValue);
            
            // remove all variable bindings that are in the system state but not in
            // the term
            
            // VarBindings for the Early-Variable-Assignment-View
            List<Entry> childVars = child.getVarBindings();
            childVars.removeAll(fNeedlessVarBindings);
            
            List<Entry> newVars = new ArrayList<Entry>( childVars );

            newVars.removeAll(parentVars);
            DefaultMutableTreeNode paren = treeParent;
            // if new variables appear in the term, they are added in the tree
            // before the child
            if (varAssignmentMode == ShowVariableAssignment.EARLY && newVars.size() > 0) {
                Entry e = newVars.get(0);
                
                enode = new EvalNodeVarAssignment(new Vector<Entry>(childVars));
                ExpVariable expVar = new ExpVariable(e.getVarName(), e.getValue().type());
                enode.setExpression(expVar);
                enode.setResult(e.getValue());
                // if the child-node is expanded the new variable assignment
                // node is also expanded
                enode.setVisible(child.isVisible());
                ((EvalNodeVarAssignment)enode).addVarAssignment(e);
                // The highlighting for the new Var-Assignment-Node
                // is reused from the previous eval node.
                // If only one new var is added and it is true oder false,
                // the highlighting for the sub tree is set to the corresponding value
                enode.setSubTreeValue(subtreeValue);
                enode.setCompleteTreeValue(completeTreeValue);
                
                if (newVars.size() == 1 && enode.getResult().equals(BooleanValue.TRUE))
                    enode.setSubTreeValue(TreeValue.TRUE);
                else if (newVars.size() == 1 && enode.getResult().equals(BooleanValue.FALSE))
                    enode.setSubTreeValue(TreeValue.FALSE);

                for (int i = 1; i < newVars.size(); i++) {
                    Entry e2 = newVars.get(i);
                    ((EvalNodeVarAssignment)enode).addVarAssignment(e2);
                }

                DefaultMutableTreeNode chil = new DefaultMutableTreeNode(enode);
                paren.add(chil);
                paren = chil;
            } // end of Early-Variable-Assignment-View

            // set the var Substitution View if requested
            child.setSubstituteVariables(isSubstituteVars());
            // don't add var assignments if not desired
            if (varAssignmentMode == ShowVariableAssignment.LATE || !(childExpr instanceof ExpVariable)) {
                // don't add insignificant nodes like "1=1" to the eval tree
                if (!childExpr.toString().equals(child.getResult().toString())) {
                    paren.add(treeChild);
                    createNodes(treeChild, child);
                }
                // break if the parent node is a forAll-Node and child evaluates
                // to "false" or "Undefined"
                // for minimal evaluation
                if (!fExtendedForAll.isSelected()
                        && parentExpr instanceof ExpForAll
                        && (child.getResult().equals(BooleanValue.FALSE) || child.getResult().equals(UndefinedValue.instance)))
                    break breakLabel;
                // break if the parent node is an exists node and child
                // evaluates to "true"
                // for minimal evaluation
                else if (!fExtendedExists.isSelected()
                        && parentExpr instanceof ExpExists
                        && child.getResult().equals(BooleanValue.TRUE))
                    break breakLabel;
                else if (!fExtendedOr.isSelected() && parentExpr instanceof ExpStdOp && ((ExpStdOp)parentExpr).getOperation() instanceof StandardOperationsBoolean.Op_boolean_or
                        && child.getResult().equals(BooleanValue.TRUE))
                    break breakLabel;
                else if (!fExtendedAnd.isSelected()
                        && parentExpr instanceof ExpStdOp && ((ExpStdOp)parentExpr).getOperation() instanceof StandardOperationsBoolean.Op_boolean_and
                        && child.getResult().equals(BooleanValue.FALSE))
                    break breakLabel;
                else if (!fExtendedImplies.isSelected()
                        && parentExpr instanceof ExpStdOp && ((ExpStdOp)parentExpr).getOperation() instanceof StandardOperationsBoolean.Op_boolean_implies
                        && child.getResult().equals(BooleanValue.FALSE))
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
        // highlighting information for the root node
        if (root.getResult().equals(BooleanValue.TRUE)) {
            root.setSubTreeValue(TreeValue.TRUE);
            root.setCompleteTreeValue(TreeValue.TRUE);
        } else if (root.getResult().equals(BooleanValue.FALSE)) {
            root.setSubTreeValue(TreeValue.FALSE);
            root.setCompleteTreeValue(TreeValue.FALSE);
        }
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
        String htmlTitle = "<html>" + root.getExpressionString(false) + "</html>";
        fTopLabel.setText(htmlTitle);
    }

    /**
     * call for the new evaluation browser
     */
    public static ExprEvalBrowser create(EvalNode root, MSystem sys) {
        ExprEvalBrowser eb;
        eb = createPlus(root, sys, null);
        return eb;
    }

    /**
     * creates a new evaluation browser window with the tree created from the
     * hand overed root node
     */
    public static ExprEvalBrowser createPlus(EvalNode root, MSystem sys, MClassInvariant inv) {
        final JFrame f = new JFrame("Evaluation browser ");
        String expressionString;
        
        if (inv == null) {
        	expressionString = "<html>" + root.getExpressionString(false) + "</html>"; 
        } else {
        	StringWriter sw = new StringWriter();
            sw.write("<html>");
            
            MMVisitor v = new MMHTMLPrintVisitor(new PrintWriter(sw));
            inv.processWithVisitor(v);
            sw.write("</html>");
            expressionString = sw.toString();
        }
        
        ExprEvalBrowser eb = new ExprEvalBrowser(root, sys, expressionString, f);

        // Layout the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(eb, BorderLayout.CENTER);

        f.setContentPane(contentPane);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setPreferredSize(new Dimension(800, 300));
        f.pack();
        f.setLocationRelativeTo(MainWindow.instance());
        f.setVisible(true);

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
    public ExprEvalBrowser(EvalNode root, MSystem sys, String expressionString, JFrame mother) {
        fSystem = sys;
        setLayout(new BorderLayout());
        // connection to the mother JFrame
        this.fParent = mother;
        
        // set the title in the NORTH
        
        fTopLabel = new JLabel();
        fTopLabel.setToolTipText("Double click to min or max title, "
                + "right click to open config menu");
        fTopLabel.setText(expressionString);
        
        add(fTopLabel, BorderLayout.NORTH);

        // set the combo-menu in the SOUTH with popupmenu-listener
        EvalPopupMenuListener plistener = new EvalPopupMenuListener();
        String[] treeControll_items = { "Display options", "Expand all",
                "Expand all true", "Expand all false", "Collapse" };
        fComboTreeDisplays = new JComboBox<>(treeControll_items);
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
        // Highlighting information for the root node
        //FIXME: Code-Clone
        if (root.getResult().equals(BooleanValue.TRUE)) {
            root.setSubTreeValue(TreeValue.TRUE);
            root.setCompleteTreeValue(TreeValue.TRUE);
        } else if (root.getResult().equals(BooleanValue.FALSE)) {
            root.setSubTreeValue(TreeValue.FALSE);
            root.setCompleteTreeValue(TreeValue.FALSE);
        }
        // create the tree
        fTreeModel = new DefaultTreeModel(fTopNode);
        fTree = new JTree(fTreeModel);
        fTree.setRowHeight(0);
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
        fVarAssList = new JList<>();
        // MouseListener to show Object Properties if requested
        MouseAdapter ma = new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Entry var = fVarAssList.getSelectedValue();
                    if(var != null && var.getValue().type().isTypeOfClass()){
                    	new ObjectBrowser(fSystem, ((ObjectValue) var.getValue()).value());
                    }
                }
            }
        };
        fVarAssList.addMouseListener(ma);
        fVarAssList.setToolTipText("Double click to open object browser");

        fScrollVarAssList = new JScrollPane(fVarAssList);
        // Termview Window
        fSubstituteWin = new JEditorPane();
        fSubstituteWin.setEditable(false);
        fSubstituteWin.setContentType("text/html");
        
        fScrollSubstituteWin = new JScrollPane(fSubstituteWin);

        // add the mouse listener for the config and the tree popup menu
        // to the content pane
        fTree.addMouseListener(fMouseListener);
        fVarAssList.addMouseListener(fMouseListener);
        fSubstituteWin.addMouseListener(fMouseListener);
        fTopLabel.addMouseListener(fMouseListener);
        addMouseListener(fMouseListener);

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
     * @param stop that node itself and it's subtree is not expanded
     */
    void expandAllTrue(TreePath path, String stop) {
        Object node = path.getLastPathComponent();
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;
        EvalNode enode = (EvalNode) dnode.getUserObject();
        TreeModel model = fTree.getModel();
        if (model.isLeaf(node) || enode.getResult().toString().equals(stop))
            return;
        else if (varAssignmentMode == ShowVariableAssignment.EARLY) {
            DefaultMutableTreeNode dchild = dnode;
            EvalNode echild = (EvalNode) dchild.getUserObject();
            while (dchild.getChildCount() > 0
                    && echild.getExpression() instanceof ExpVariable
                    && !echild.getResult().equals(BooleanValue.TRUE)
                    && !echild.getResult().equals(BooleanValue.FALSE)) {
                dchild = (DefaultMutableTreeNode) dchild.getChildAt(0);
                echild = (EvalNode) dchild.getUserObject();
            }
            if (echild.getResult().toString().equals(stop))
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
            if (val && enode.getResult().equals(BooleanValue.TRUE))
                expandAllTrue(path, "false");
            else if (!val && enode.getResult().equals(BooleanValue.FALSE))
                expandAllTrue(path, "true");
            else if (varAssignmentMode == ShowVariableAssignment.EARLY) {
                DefaultMutableTreeNode dchild = dnode;
                EvalNode echild = (EvalNode) dchild.getUserObject();
                while (dchild.getChildCount() > 0
                        && echild.getExpression() instanceof ExpVariable) {
                    dchild = (DefaultMutableTreeNode) dchild.getChildAt(0);
                    echild = (EvalNode) dchild.getUserObject();
                }
                if (val && echild.getResult().equals(BooleanValue.TRUE))
                    expandAllTrue(new TreePath(dnode.getPath()), "false");
                if (!val && echild.getResult().equals(BooleanValue.FALSE))
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
                @Override
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
                @Override
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
                @Override
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
                    StringSelection cont = new StringSelection(enode.getExpressionStringRaw(isSubstituteVars()));
                    
                    clip.setContents(cont, null);
                }
            };
            treepop.add(copyAction);

            // Initialize the Configuration Menu
            // extended search
            JMenu extendedSearch = new JMenu("Extended evaluation");
            extendedSearch.add(fExtendedExists);
            extendedSearch.add(fExtendedForAll);
            extendedSearch.add(fExtendedAnd);
            extendedSearch.add(fExtendedOr);
            extendedSearch.add(fExtendedImplies);
            extendedSearch.addSeparator();
            extendedSearch.add(fExtendedAll);
            extendedSearch.add(fExtendedNone);
            extendedSearch.addItemListener(fItemListener);
            
            fExtendedExists.addItemListener(fItemListener);
            fExtendedForAll.addItemListener(fItemListener);
            fExtendedAnd.addItemListener(fItemListener);
            fExtendedOr.addItemListener(fItemListener);
            fExtendedImplies.addItemListener(fItemListener);
            
            fExtendedAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fExtendedExists.setSelected(true);
		            fExtendedForAll.setSelected(true);
		            fExtendedAnd.setSelected(true);
		            fExtendedOr.setSelected(true);
		            fExtendedImplies.setSelected(true);
				}
			});
            
            fExtendedNone.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fExtendedExists.setSelected(false);
		            fExtendedForAll.setSelected(false);
		            fExtendedAnd.setSelected(false);
		            fExtendedOr.setSelected(false);
		            fExtendedImplies.setSelected(false);
				}
			});
            
            popup.add(extendedSearch);
            popup.addSeparator();
            // Variable-Eval-Window
            fVarAssListChk.addItemListener(fItemListener);
            popup.add(fVarAssListChk);
            // Term-View-Window
            fVarSubstituteWinChk.addItemListener(fItemListener);
            popup.add(fVarSubstituteWinChk);
            // the 4 Tree Views
            JMenu menu = new JMenu("Show variable assignments");
            ButtonGroup bg_treeview = new ButtonGroup();
            
			for (int i = 0; i < rbVariableAssignment.length; ++i) {
				rbVariableAssignment[i]
						.addActionListener(new ChangeVarAssignmentViewActionListener(
								ShowVariableAssignment.values()[i]));
				menu.add(rbVariableAssignment[i]);
				bg_treeview.add(rbVariableAssignment[i]);
			}
                        
            menu.addSeparator();
            
            cbSubstituteVariables.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel.getRoot();
		            
		            markVisibleNodes(dnode);
		            
		            // rebuild the tree
		            fTopNode.removeAllChildren();
		            EvalNode enode = (EvalNode) fTopNode.getUserObject();
		            createNodes(fTopNode, enode);
		            fTreeModel.reload();
		            // expand the nodes expanded before
		            expandMarkedNodes(dnode);					
				}
			});
            
            menu.add(cbSubstituteVariables);
            
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

        @Override
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
                    || e.getButton() == MouseEvent.BUTTON3) {
                popup.show(e.getComponent(), e.getX(), e.getY());
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

        @Override
		public void itemStateChanged(ItemEvent e) {
            Object object = e.getSource();
            // Implemented Actions for the comboTree
            if (object == fComboTreeDisplays && e.getStateChange() == ItemEvent.SELECTED) {
                String selectedChoise = e.getItem().toString();
                switch (selectedChoise) {
                case "Expand all":
                    expandAll(new TreePath(fTopNode.getPath()));
                    break;
                case "Collapse": 
                {
                    TreePath rootpath = new TreePath(fTopNode.getPath());
                    collapseAll(rootpath);
                    fTree.expandPath(rootpath);
                    break;
                } 
                case "Expand all true":
                {
                    TreePath rootpath = new TreePath(fTopNode.getPath());
                    expandCollapsedVisibleNodes(rootpath, true);
                    break;
                }
                case "Expand all false":
                {
                    TreePath rootpath = new TreePath(fTopNode.getPath());
                    expandCollapsedVisibleNodes(rootpath, false);
                    break;
                }
                case "Close window":
                    fParent.setVisible(false);
                    fParent.dispose();
                    break;
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

    private class ChangeVarAssignmentViewActionListener implements ActionListener {

    	private final ShowVariableAssignment switchTo;
    	
    	public ChangeVarAssignmentViewActionListener(ShowVariableAssignment to) {
    		this.switchTo = to;
    	}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			varAssignmentMode = switchTo;
            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) fTreeModel.getRoot();
            
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
     * ActionListener for all the buttons in the configuration menu
     */
    class EvalActionListener implements ActionListener {
    	
        @Override
		public void actionPerformed(ActionEvent event) {
        	
            String command = event.getActionCommand();
            
            if (command.equals("Close")) {
                close();
            } else if (command.equals("Fit width")) {
                formatNodes(fTopNode);
                // adjustTopWidth(fTopLabel,fTitle,fTopLabel.getText());
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
            } else if (command.equalsIgnoreCase("Complete subtree highlighting")) {
                fTreeHighlightings[3].setSelected(true);
                fTree.setCellRenderer(new CompleteSubtreeRenderer());
                fTree.repaint();
            } else if (command.equals("Default configuration")) {
            	
                // extended evaluation checkboxes
				fExtendedExists.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.exists"));
                fExtendedForAll.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.forall"));
                fExtendedAnd.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.and"));
                fExtendedOr.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.or"));
                fExtendedImplies.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.implies"));
                
                fVarAssListChk.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.VarAssignmentWindow"));

                fFirstInvoke1 = false;
                fVarSubstituteWinChk.setSelected(getBooleanPropertyValue("use.gui.view.evalbrowser.SubExprSubstitutionWindow"));                
                fFirstInvoke2 = false;
                
                String prop = System.getProperty("use.gui.view.evalbrowser.treeview", "false");
                
                if (prop.equals("earlyVarAssignment"))
                    actionPerformed(new ActionEvent(rbVariableAssignment[1], 0,
                            "Early variable assignment"));
                else if (prop.equals("VarAssignment&Substitution"))
                    actionPerformed(new ActionEvent(rbVariableAssignment[2], 0,
                            "Variable assignment & substitution"));
                else if (prop.equals("VarSubstitution"))
                    actionPerformed(new ActionEvent(rbVariableAssignment[3], 0,
                            "Variable substitution"));
                else if (prop.equals("noVarAssignment"))
                    actionPerformed(new ActionEvent(rbVariableAssignment[4], 0,
                            "No variable assignment"));
                else
                    actionPerformed(new ActionEvent(rbVariableAssignment[0], 0,
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

            } else if (command.equals("Set as default")) {
                new SetDefaultDialog(ExprEvalBrowser.this, fParent);

            } else if (command.equals("Capture to file")) {
                // CaptureTheWindow ctw = new CaptureTheWindow();
                Thread t = new Thread(new CaptureTheWindow(fParent));
                t.start();
            }
        }

		/**
		 * @return
		 */
		protected boolean getBooleanPropertyValue(String entry) {
			return System.getProperty(
					entry, "false").equals(
					"true");
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
            // detect the font metrics of the current node
            
            FontMetrics fm = fTree.getFontMetrics(fTree.getFont());
            if (!fNoColorHighlightingChk.isSelected()
                    || fTreeHighlightings[0].isSelected())
                fm = fTree.getFontMetrics(fTree.getFont());
            else if (fTreeHighlightings[1].isSelected()
                    && (enode.getResult().equals(BooleanValue.TRUE) || enode.getResult().equals(BooleanValue.FALSE)))
                fm = fTree.getFontMetrics(fTree.getFont().deriveFont(Font.BOLD));
            else if (fTreeHighlightings[2].isSelected()
                    && (enode.getSubTreeValue() == TreeValue.TRUE || enode.getSubTreeValue() == TreeValue.FALSE))
                fm = fTree.getFontMetrics(fTree.getFont().deriveFont(Font.BOLD));
            else if (fTreeHighlightings[3].isSelected()
                    && (enode.getCompleteTreeValue() == TreeValue.TRUE || enode.getSubTreeValue() == TreeValue.FALSE))
                fm = fTree.getFontMetrics(fTree.getFont().deriveFont(Font.BOLD));
            // set the width for the node if the node text goes beyond the
            // viewpane
            if (nodeIndent + fm.stringWidth(enode.getExpressionStringRaw(isSubstituteVars())) > fScrollTree.getWidth())
                enode.setWidth((int)nodeWidth);
            
            fTreeModel.nodeChanged(node);
            // traverse the remaining child nodes
            // if node is leaf loop will not enter
            for (int i = 0; i < node.getChildCount(); i++)
                formatNodes(node.getChildAt(i));
        }
    }

    /**
     * listener for the tree-modification-comobox in the SOUTH of the window
     */
    class EvalPopupMenuListener implements PopupMenuListener {
        @Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            fComboTreeDisplays.setSelectedIndex(-1);
            fComboTreeDisplays.removeItem("Display options");
        }

        @Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            if (fComboTreeDisplays.getSelectedIndex() == -1) {
                fComboTreeDisplays.addItem("Display options");
                fComboTreeDisplays.setSelectedItem("Display options");
            }
        }

        @Override
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
        @Override
		public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            EvalNode enode = (EvalNode) dnode.getUserObject();

            List<Entry> bindings = enode.getVarBindings();
            
            // set content to the variable window
            fVarAssList.setListData(bindings.toArray(new Entry[bindings.size()]));
                        
            // substitutes subexpressions with the values
			String content = "<html><head><style> <!-- body { font-family: sansserif; } --> </style></head><body><font size=\"-1\">"
					+ enode.substituteChildExpressions()
					+ "</font></body></html>";
            fSubstituteWin.setText(content);
        }
    }

    private static final Color COLOR_TRUE = new Color(0, 0x80, 0);
    private static final Color COLOR_FALSE = new Color(0xc0, 0, 0);
    
    abstract class TreeHighlightingRenderer extends DefaultTreeCellRenderer {
    	
    	protected abstract TreeValue getTreeValue(EvalNode node);
    	
    	@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) value;
            EvalNode enode = (EvalNode)dnode.getUserObject();
            TreeValue treeValue = getTreeValue(enode);
            
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            switch (treeValue) {
            case TRUE:
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.black);
                    setBackgroundNonSelectionColor(Color.white);
                } else {
                    setFont(fDefaultFont);
                    setForeground(COLOR_TRUE);
                    setBackgroundNonSelectionColor(Color.white);
                }
                break;
            case FALSE:
                if (fNoColorHighlightingChk.isSelected()) {
                    setFont(fDefaultFont.deriveFont(Font.BOLD));
                    setForeground(Color.white);
                    setBackgroundNonSelectionColor(Color.black);
                } else {
                    setFont(fDefaultFont);
                    setForeground(COLOR_FALSE);
                    setBackgroundNonSelectionColor(Color.white);
                }
                break;
            default:
                setFont(fDefaultFont);
                setForeground(Color.black);
                setBackgroundNonSelectionColor(Color.white);
            }
            
            return this;

        }
    }
    
    /**
     * the TreeCellRenderer for term-Highlighting
     */
    class TermRenderer extends TreeHighlightingRenderer {
		@Override
		protected TreeValue getTreeValue(EvalNode node) {
			if (node.getResult().equals(BooleanValue.TRUE)) {
				return TreeValue.TRUE;
			} else if (node.getResult().equals(BooleanValue.FALSE)) {
				return TreeValue.FALSE;
			} else {
				return TreeValue.INVALID;
			}
		}
    }

    /**
     * the TreeCellRenderer for subtree-Highlighting
     */
    class SubtreeRenderer extends TreeHighlightingRenderer {
		@Override
		protected TreeValue getTreeValue(EvalNode node) {
			return node.getSubTreeValue();
		}
    }

    /**
     * highlights Complete Subtree of the evaluated complete terms
     */
    class CompleteSubtreeRenderer extends TreeHighlightingRenderer {
		@Override
		protected TreeValue getTreeValue(EvalNode node) {
			return node.getCompleteTreeValue();
		}
    }

    public void markVisibleNodes(TreeNode tnode) {
        DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) tnode;
        DefaultMutableTreeNode child;
        EvalNode enode = (EvalNode) dnode.getUserObject();
        TreePath path = new TreePath(dnode.getPath());
                
        if (fTree.isVisible(path))
            enode.setVisible(true);
        else
            enode.setVisible(false);
        
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
