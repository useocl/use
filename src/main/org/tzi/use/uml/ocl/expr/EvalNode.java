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

package org.tzi.use.uml.ocl.expr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;

/**
 * Context information used during evaluation.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public class EvalNode {
    private List fChildren; // (EvalNode)

    private Expression fExpr;

    private Value fResult;

    private Vector fVarBindings; // for the var bindings window in the

    // ExprEvalBrowser

    private String fTabWidth = "<table>"; // widthspec for the treenode

    private String fTabEnd = "</table>";

    private boolean fIsVisible = false;

    private char[] fHighliting; // highliting informations for the node

    private String fVarSubstituteView; // the ocl-term with substituted

    // variables

    private String fVarAss; // Variable-Assignments - Attribute for the

    // Var-Assignment-EvalNodes

    /**
     * Constructor for the Variable-Assignment-EvalNodes invoked from the
     * ExprEvalBrowser. The Variable-Assignment-EvalNodes are needed by the
     * early Var-Assignment-Treeviews
     * 
     * @param vars
     *            the var-bindings
     */
    public EvalNode(Vector vars) {
        fVarBindings = vars;
    }

    /**
     * Constructor for the normal EvalNodes invoked from the EvalContext
     * 
     * @param fVarBindings
     */
    public EvalNode(VarBindings fVarBindings) {
        this.fVarBindings = new Vector();
        Iterator it = fVarBindings.iterator();
        
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            this.fVarBindings.add(entry);
        }

    }

    void addChild(EvalNode n) {
        if (fChildren == null)
            fChildren = new ArrayList();
        fChildren.add(n);
    }

    public List children() {
        if (fChildren == null)
            return new ArrayList();
        else
            return fChildren;
    }

    public void setExpression(Expression expr) {
        fExpr = expr;
    }

    public Expression getExpr() {
        return fExpr;
    }

    public void setResult(Value result) {
        fResult = result;
    }

    public String getResult() {
        return fResult.toString();
    }

    /**
     * if HTML-Code is needed for the node true is returned
     */
    public boolean htmlUsed() {
        if (fTabWidth == "<table>")
            return false;
        return true;
    }

    /**
     * returns the string for the node in the eval-tree of the ExprEvalBrowser
     * if this is a Variable-Assignment-node (used in the Early-Eval-Treeviews)
     * fVarAss is returned if substitute views are activated the the variables
     * are substituted before the width spec is added if it is needed
     */
    public String toString() {

        String htmlConform;
        if (fVarAss != null) {
            if (fTabWidth == "<table>")
                return fVarAss;
            htmlConform = "<html>" + fTabWidth + other(fVarAss) + fTabEnd
                    + "</html>";
            return htmlConform;
        }

        if (fVarSubstituteView != null)
            if (fTabWidth == "<table>")
                return fVarSubstituteView;
            else
                return "<html>" + fTabWidth + other(fVarSubstituteView)
                        + fTabEnd + "</html>";
        else if (fTabWidth == "<table>")
            return fExpr + " = " + fResult;
        else
            return "<html>" + fTabWidth + other(fExpr + " = " + fResult)
                    + fTabEnd + "</html>";
        // return htmlConform ;
    }

    /**
     * returns the String of the node that is displayed in the tree without the
     * HTML-informations
     */
    public String toNormString() {
        if (fVarAss != null)
            return fVarAss;
        else if (fVarSubstituteView != null)
            return fVarSubstituteView;
        else
            return fExpr + " = " + fResult;
    }

    /**
     * returns the original expression without substitutions and the associated
     * value
     */
    public String getExprAndValue() {
        if (fVarAss == null)
            return fExpr + " = " + fResult;
        else
            return fVarAss;
    }

    public String getHtmlExpr() {
        return other2(getExpr().toString());
    }

    /**
     * sets the width spec for the node
     */
    public void setTabWidth(double d) {
        fTabWidth = "<table width=\"" + d + "\">";
        fTabEnd = "</table>";
    }

    /**
     * resets the spec width
     */
    public void resetTabWidth() {
        fTabWidth = "<table>";
        fTabEnd = "</table>";
    }

    /**
     * sets if this EvalNode is visible in the tree in the ExprEvalBrowser
     */
    public void setVisibleAttr(boolean b) {
        fIsVisible = b;
    }

    public boolean isVisible() {
        return fIsVisible;
    }

    public boolean isEarlyVarNode() {
        return fVarAss != null;
    }

    public Vector getVarBindings() {
        return fVarBindings;
    }

    /**
     * EvalNode true/false-highliting informations 1. field: subtree-highliting
     * 2. field: complete-highliting
     */
    public void setHighliting(char[] c) {
        fHighliting = c;
    }

    public char[] getHighliting() {
        return fHighliting;
    }

    /**
     * adds the next Variable-assignment for Variable-Assignment-EvalNodes
     */
    public void setVarAssignment(String nextVar) {
        if (fVarAss == null)
            fVarAss = nextVar;
        else
            fVarAss += ", " + nextVar;
    }

    /**
     * sets or resets the variable-substitution attribute
     */
    public void setVarSubstituteView(boolean b) {
        if (b)
            fVarSubstituteView = substituteVariables();
        else
            fVarSubstituteView = null;
    }

    /**
     * substitutes the variables with the assigned values this method is invoked
     * by the toString-method if one of the substitute-tree-views are selected
     * in the ExprEvalBrowser
     * 
     * @return string with the substituted values
     */
    public String substituteVariables() {
        // if the ocl-expression represents a variable
        if (fExpr.name() == "var")
            // if the this is a normal var-node
            if (fVarAss == null)
                return fExpr + " = " + fResult;
            // if this variable-expr-node is created in
            // ExprEvalBrowser::createNodes
            // for the early-var-assignment-view
            else
                return fVarAss;

        String ret = "";
        HashSet stoken = new HashSet();
        stoken.add(new Character(' '));
        stoken.add(new Character('<'));
        stoken.add(new Character('>'));
        stoken.add(new Character('('));
        stoken.add(new Character(')'));
        stoken.add(new Character('.'));
        stoken.add(new Character(':'));
        stoken.add(new Character('-'));
        ret = "(" + getExprAndValue() + ")";
        
        for (int i = fVarBindings.size() - 1; i >= 0; --i) {
            Entry entry = (Entry) fVarBindings.get(i);
            String varname = entry.getVarName();
            String varval = entry.valToString();
            Pattern p = Pattern.compile(varname);
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
                        help += varval;
                    else
                        help += varname;
                }
            }
            ret = help;
        }
        return ret.substring(1, ret.length() - 1);
    }

    /**
     * substitutes the variables with it's values needed by the
     * VarSubstituetWindow in the EvalBrowser
     * 
     * @return the term with substituted variables
     */
    public String substituteVariables(String term) {
        String ret = "";
        HashSet stoken = new HashSet();
        stoken.add(new Character(' '));
        stoken.add(new Character('<'));
        stoken.add(new Character('>'));
        stoken.add(new Character('('));
        stoken.add(new Character(')'));
        stoken.add(new Character('.'));
        stoken.add(new Character(':'));
        stoken.add(new Character('-'));
        ret = "(" + term + ")";
        
        for (int i = fVarBindings.size() - 1; i >= 0; --i) {
            Entry entry = (Entry) fVarBindings.get(i);
            String varname = entry.getVarName();
            String varval = entry.valToString();
            Pattern p = Pattern.compile(varname);
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
                        help += varval;
                    else
                        help += varname;
                }
            }
            ret = help;
        }
        return ret.substring(1, ret.length() - 1);
    }

    /**
     * substitutes the "<" and ">" and "," char to html-conform chars returns
     * the html-conform string
     */
    protected String other(String s) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '<':
                b.append("&lt;");
                break;
            case '>':
                b.append("&gt;");
                break;
            case ',':
                b.append("&#130;");
                break;
            default:
                b.append(c);
            }
        }
        return b.toString();
    }

    /**
     * substitutes the "<" and ">" char to html-conform chars returns the
     * html-conform string
     */
    protected String other2(String s) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '<':
                b.append("&lt;");
                break;
            case '>':
                b.append("&gt;");
                break;
            default:
                b.append(c);
            }
        }
        return b.toString();
    }

}
