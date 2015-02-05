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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.ocl.value.VarBindings.Entry;

/**
 * Context information used during evaluation.
 * 
 * @author Mark Richters
 */
public class EvalNode {
    
	private List<EvalNode> fChildren;

    private Expression fExpr;

    private Value fResult;

    /**
     * for the var bindings window in the
     * ExprEvalBrowser
     */
    private final Vector<Entry> fVarBindings;
    
    /**
     * If <code>true</code>, the occurrence
     * of variables in the text is replaces by the current value
     * of the variable.
     */
    private boolean fSubstituteVariables = false;
    
    private boolean fIsVisible = false;

    private int width = -1;

    /**
     * Constructor for the Variable-Assignment-EvalNodes invoked from the
     * ExprEvalBrowser. The Variable-Assignment-EvalNodes are needed by the
     * early Var-Assignment-Treeviews
     * 
     * @param vars
     *            the var-bindings
     */
    protected EvalNode(Vector<Entry> vars) {
        fVarBindings = vars;
    }

    /**
     * Constructor for the normal EvalNodes invoked from the EvalContext
     * 
     * @param fVarBindings
     */
    public EvalNode(VarBindings fVarBindings) {
        this.fVarBindings = new Vector<Entry>();
        Iterator<Entry> it = fVarBindings.iterator();
        
        while (it.hasNext()) {
            Entry entry = it.next();
            this.fVarBindings.add(entry);
        }
    }

    /**
     * @param nodeWidth
     */
    public void setWidth(int nodeWidth) {
    	this.width = nodeWidth;
    }

    void addChild(EvalNode n) {
        if (fChildren == null)
            fChildren = new ArrayList<EvalNode>();
        fChildren.add(n);
    }

    /**
	 * Sorts the node tree to match the sorted display order of unsorted types.
	 * E.g. for the expression <code>Set{2,3,1}->forAll( i | i < 5 )</code> the
	 * range expression is displayed as <code>Set{1,2,3}</code> and the order of
	 * the evaluation details should follow this order.
	 */
    public void sortSubtree(){
    	for(EvalNode child : children()){
    		child.sortSubtree();
    	}
    	
    	// query expressions with an unordered range type need sorting
    	if(fExpr instanceof ExpQuery &&
        		(((ExpQuery) fExpr).getRangeExpression().type().isTypeOfSet() || ((ExpQuery) fExpr).getRangeExpression().type().isTypeOfBag())){
        	
        	EvalNode rangeNode = null;
        	ExpQuery expr = (ExpQuery) fExpr;
        	
        	// find the node representing the range expression and cut it from the list
	        for (Iterator<EvalNode> iterator = fChildren.iterator(); iterator.hasNext();) {
				EvalNode child = iterator.next();
				if(child.getExpression() == expr.getRangeExpression()){
	        		rangeNode = child;
	        		iterator.remove();
	        		break;
	        	}
			}
        
	        if(rangeNode != null){
	        	final List<String> relevantVars = new ArrayList<String>(expr.getVariableDeclarations().size());
	        	for(int i = 0; i < expr.getVariableDeclarations().size(); i++){
	        		relevantVars.add(expr.getVariableDeclarations().varDecl(i).name());
	        	}

	        	// sort the list by the variables defined in the query expression
	        	Collections.sort(fChildren, new Comparator<EvalNode>() {
					@Override
					public int compare(EvalNode o1, EvalNode o2) {
						Value[] v1 = getVarValues(o1, relevantVars);
						Value[] v2 = getVarValues(o2, relevantVars);
						
						for(int i = 0; i < relevantVars.size(); i++){
							if(v1[i] == null && v2[i] == null){
								return 0;
							} else if(v2[i] == null){
								return -1;
							} else if(v1[i] == null){
								return 1;
							} else if(!v1[i].equals(v2[i])){
								return v1[i].compareTo(v2[i]);
							}
						}
						
						return 0;
					}
					
					private Value[] getVarValues(EvalNode node, List<String> relevantVars) {
						Value[] values = new Value[relevantVars.size()];
						for(int i = 0; i < relevantVars.size(); i++){
							String rVar = relevantVars.get(i);
							for (VarBindings.Entry e : node.getVarBindings()) {
								if (rVar.equals(e.getVarName())) {
									values[i] = e.getValue();
								}
							}
						}
						return values;
					}
				});
	        	
	        	// insert range expression back at the first position
	        	fChildren.add(0, rangeNode);
	        }
        }
    }
    
	public List<EvalNode> children() {
        if (fChildren == null)
            return new ArrayList<EvalNode>();
        else
            return fChildren;
    }

    public void setExpression(Expression expr) {
        fExpr = expr;
    }

    public Expression getExpression() {
        return fExpr;
    }

    public void setResult(Value result) {
        fResult = result;
    }

    public Value getResult() {
        return fResult;
    }

    /**
     * returns the string for the node in the eval-tree of the ExprEvalBrowser
     * if this is a Variable-Assignment-node (used in the Early-Eval-Treeviews)
     * fVarAss is returned if substitute views are activated the the variables
     * are substituted before the width spec is added if it is needed
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<html>");
    	if (this.width > 0) {
    		sb.append("<table width='");
    		sb.append(this.width);
    		sb.append("'>");
    	}
    	sb.append(getExprAndValue(fSubstituteVariables));
    	
    	if (this.width > 0) {
    		sb.append("</table>");
    	}
    	
    	sb.append("</html>");
    	
    	return sb.toString();
    }

    /**
     * returns the original expression without substitutions and the associated
     * value
     */
    public String getExprAndValue(boolean substituteVariables) {
        String exp = getExpressionString(substituteVariables);
        exp += " = " + (fResult.isObject()?"@":"") + fResult.toString();
        
    	return exp;
    }

    /**
	 * @return
	 */
	public String getExpressionString(boolean substituteVariables) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
    	ExpressionVisitor visitor;
    	if (substituteVariables) {
    		visitor = new SubstituteVariablesExpressionVisitor(pw, true);
    	} else {
    		visitor = new RelevantOperationHighlightVisitor(pw);
    	}
        fExpr.processWithVisitor(visitor);
    	
    	return sw.toString();
	}
	
	public String getExpressionStringRaw(boolean substituteVariables) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		ExpressionPrintVisitor v = new ExpressionPrintVisitor(pw);
		
		fExpr.processWithVisitor(v);
		
		return sw.toString();
	}
	
    /**
     * sets if this EvalNode is visible in the tree in the ExprEvalBrowser
     */
    public void setVisible(boolean b) {
        fIsVisible = b;
    }

    public boolean isVisible() {
        return fIsVisible;
    }

    public boolean isEarlyVarNode() {
        return false;
    }

    public Vector<Entry> getVarBindings() {
        return fVarBindings;
    }

    public enum TreeValue {
    	TRUE,
    	FALSE,
    	UNDEFINED,
    	INVALID
    }
    
    private TreeValue subTreeValue;
    
    /**
	 * @return the highlightSubTree
	 */
	public TreeValue getSubTreeValue() {
		return subTreeValue;
	}

	/**
	 * @param highlightSubTree the highlightSubTree to set
	 */
	public void setSubTreeValue(TreeValue highlightSubTree) {
		this.subTreeValue = highlightSubTree;
	}

	/**
	 * @return the highlightCompleteTree
	 */
	public TreeValue getCompleteTreeValue() {
		return completeTreeValue;
	}

	/**
	 * @param highlightCompleteTree the highlightCompleteTree to set
	 */
	public void setCompleteTreeValue(TreeValue highlightCompleteTree) {
		this.completeTreeValue = highlightCompleteTree;
	}

	private TreeValue completeTreeValue;
    
    /**
     * sets or resets the variable-substitution attribute
     */
    public void setSubstituteVariables(boolean b) {
        this.fSubstituteVariables = b;
    }

	/**
	 *  
	 * @return
	 */
	public String substituteChildExpressions() {
		Map<Expression, Value> subexpressionsToReplace = new HashMap<>();
		for (EvalNode child : children()) {
			if(getExpression() instanceof ExpQuery){
				// only replace range expression for query expressions
				subexpressionsToReplace.put(child.getExpression(), child.getResult());
				break;
			}
		}
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		ExpressionVisitor v = new SubstituteVariablesExpressionVisitor(pw, false, subexpressionsToReplace);
		getExpression().processWithVisitor(v);
		
		return sw.toString();
	}
    
    private class SubstituteVariablesExpressionVisitor extends RelevantOperationHighlightVisitor {

    	
		/**
		 * Since variables can be hidden by sub expressions,
		 * we need to keep track of variable declarations hiding
		 * variables with a broader scope.
		 */
		private final Set<String> hiddenVars = new HashSet<>();
		
		private final boolean doHighlighting;
		
		private final Map<Expression, Value> expressionsToReplace;
		
		public SubstituteVariablesExpressionVisitor(PrintWriter pw, boolean doHighlighting) {
			this(pw, doHighlighting, Collections.<Expression,Value>emptyMap());
		}

		public SubstituteVariablesExpressionVisitor(PrintWriter pw, boolean doHighlighting, Map<Expression, Value> expressionsToReplace) {
			super(pw);
			this.doHighlighting = doHighlighting;
			this.expressionsToReplace = expressionsToReplace;
		}
		
		protected boolean replace(Expression exp) {
			if (this.expressionsToReplace.containsKey(exp)) {
				String value = "";
				Value val = this.expressionsToReplace.get(exp);
				
				if (val.isObject()) {
					value += "@";
				}
				value += val.toString();
				
				writer.write(value);
				return true;
			}
			
			return false;
		}
		
		@Override
		protected boolean doHighlight() { 
			return doHighlighting; 
		}
		
		@Override
		public void visitVariable(ExpVariable exp) {
			if (replace(exp)) return;
			
			if (!hiddenVars.contains(exp.getVarname())) {
				Entry varEntry = null;
				for (Entry e : fVarBindings) {
					if (e.getVarName().equals(exp.getVarname())) {
						varEntry = e;
						break;
					}
				}
				
				if (varEntry != null) {
					String value = "";
					if (varEntry.getValue().isObject()) {
						value += "@";
					}
					value += varEntry.getValue().toString();
					
					writer.write(variable(value, exp));
					return;
				}
			}
			
			super.visitVariable(exp);
		}
		
		@Override
		public void visitVarDecl(VarDecl varDecl) {
			hiddenVars.add(varDecl.name());
			super.visitVarDecl(varDecl);
		}

		@Override
		public void visitNavigation(ExpNavigation exp) {
			if (replace(exp)) return;
			super.visitNavigation(exp);
		}

		@Override
		public void visitAttrOp(ExpAttrOp exp) {
			if (replace(exp)) return;
			super.visitAttrOp(exp);
		}

		@Override
		public void visitAllInstances(ExpAllInstances exp) {
			if (replace(exp)) return;
			super.visitAllInstances(exp);
		}

		@Override
		public void visitAny(ExpAny exp) {
			if (replace(exp)) return;
			super.visitAny(exp);
		}

		@Override
		public void visitAsType(ExpAsType exp) {
			if (replace(exp)) return;
			super.visitAsType(exp);
		}

		@Override
		public void visitCollect(ExpCollect exp) {
			if (replace(exp)) return;
			super.visitCollect(exp);
		}

		@Override
		public void visitCollectNested(ExpCollectNested exp) {
			if (replace(exp)) return;
			super.visitCollectNested(exp);
		}

		@Override
		public void visitExists(ExpExists exp) {
			if (replace(exp)) return;
			super.visitExists(exp);
		}

		@Override
		public void visitForAll(ExpForAll exp) {
			if (replace(exp)) return;
			super.visitForAll(exp);
		}

		@Override
		public void visitIf(ExpIf exp) {
			if (replace(exp)) return;
			super.visitIf(exp);
		}

		@Override
		public void visitIsKindOf(ExpIsKindOf exp) {
			if (replace(exp)) return;
			super.visitIsKindOf(exp);
		}

		@Override
		public void visitIsTypeOf(ExpIsTypeOf exp) {
			if (replace(exp)) return;
			super.visitIsTypeOf(exp);
		}

		@Override
		public void visitIsUnique(ExpIsUnique exp) {
			if (replace(exp)) return;
			super.visitIsUnique(exp);
		}

		@Override
		public void visitIterate(ExpIterate exp) {
			if (replace(exp)) return;
			super.visitIterate(exp);
		}

		@Override
		public void visitLet(ExpLet exp) {
			if (replace(exp)) return;
			super.visitLet(exp);
		}

		@Override
		public void visitObjAsSet(ExpObjAsSet exp) {
			if (replace(exp)) return;
			super.visitObjAsSet(exp);
		}

		@Override
		public void visitObjOp(ExpObjOp exp) {
			if (replace(exp)) return;
			super.visitObjOp(exp);
		}

		@Override
		public void visitObjRef(ExpObjRef exp) {
			if (replace(exp)) return;
			super.visitObjRef(exp);
		}

		@Override
		public void visitOne(ExpOne exp) {
			if (replace(exp)) return;
			super.visitOne(exp);
		}

		@Override
		public void visitReject(ExpReject exp) {
			if (replace(exp)) return;
			super.visitReject(exp);
		}

		@Override
		public void visitWithValue(ExpressionWithValue exp) {
			if (replace(exp)) return;
			super.visitWithValue(exp);
		}

		@Override
		public void visitSelect(ExpSelect exp) {
			if (replace(exp)) return;
			super.visitSelect(exp);
		}

		@Override
		public void visitSortedBy(ExpSortedBy exp) {
			if (replace(exp)) return;
			super.visitSortedBy(exp);
		}

		@Override
		public void visitStdOp(ExpStdOp exp) {
			if (replace(exp)) return;
			super.visitStdOp(exp);
		}

		@Override
		public void visitTupleSelectOp(ExpTupleSelectOp exp) {
			if (replace(exp)) return;
			super.visitTupleSelectOp(exp);
		}

		@Override
		public void visitUndefined(ExpUndefined exp) {
			if (replace(exp)) return;
			super.visitUndefined(exp);
		}

		@Override
		public void visitClosure(ExpClosure exp) {
			if (replace(exp)) return;
			super.visitClosure(exp);
		}

		@Override
		public void visitOclInState(ExpOclInState exp) {
			if (replace(exp)) return;
			super.visitOclInState(exp);
		}

		@Override
		public void visitObjectByUseId(ExpObjectByUseId exp) {
			if (replace(exp)) return;
			super.visitObjectByUseId(exp);
		}

		@Override
		public void visitSelectByKind(ExpSelectByKind exp) {
			if (replace(exp)) return;
			super.visitSelectByKind(exp);
		}

		@Override
		public void visitExpSelectByType(ExpSelectByType exp) {
			if (replace(exp)) return;
			super.visitExpSelectByType(exp);
		}
    }
    
    private class RelevantOperationHighlightVisitor extends GenerateHTMLExpressionVisitor {
    	
		public RelevantOperationHighlightVisitor(PrintWriter pw) {
			super(pw);
		}
    	
		protected boolean doHighlight() { return true; }
		
		private String highlight(String s, Expression expr){
			return getExpression() == expr && doHighlight() ? "<font color=\"blue\">" + s + "</font>" : s ;
		}
		
		@Override
		protected String formatKeyword(String s, Expression expr) {
			return super.formatKeyword(highlight(s, expr), expr);
		}
		
		@Override
		protected String formatLiteral(String s, Expression expr) {
			return super.formatLiteral(highlight(s, expr), expr);
		}
		
		@Override
		protected String formatOperation(String s, Expression expr) {
			return super.formatOperation(highlight(s, expr), expr);
		}
		
		@Override
		protected String formatVariable(String s, Expression expr) {
			return super.formatVariable(highlight(s, expr), expr);
		}

		@Override
		public void visitNavigation(ExpNavigation exp) {
			if(exp == getExpression()){
				exp.getObjectExpression().processWithVisitor(this);
				writer.write('.');
				writer.write(highlight(exp.getDestination().nameAsRolename(), exp));
				atPre(exp);
			}
			else {
				super.visitNavigation(exp);
			}
		}
		
		@Override
		public void visitAttrOp(ExpAttrOp exp) {
			if(exp == getExpression()){
				exp.objExp().processWithVisitor(this);
				writer.write('.');
				writer.write(highlight(exp.attr().name(), exp));
				atPre(exp);
			}
			else {
				super.visitAttrOp(exp);
			}
		}
		
    }
}
