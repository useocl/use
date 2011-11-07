/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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
import java.util.Stack;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Extends EvalContext to construct an evaluation tree
 * for detailed output.
 * @author Lars Hamann
 *
 */
public class DetailedEvalContext extends EvalContext {
	private Stack<EvalNode> fNodeStack = new Stack<EvalNode>();
	private EvalNode fRootNode;
	
	public DetailedEvalContext(MSystemState preState,
			   MSystemState postState,
			   VarBindings globalBindings,
			   PrintWriter evalLog,
			   String evalLogIndent) {
		super(preState, postState, globalBindings, evalLog, evalLogIndent);
	}
	/**
     * Returns the root node of the evaluation tree after evaluation
     * is complete. Result is null, if building the tree has not been
     * enabled before.
     */
    EvalNode getEvalNodeRoot() {
        return fRootNode;
    }    

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.EvalContext#isEnableEvalTree()
	 */
	@Override
	public boolean isEnableEvalTree() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.EvalContext#enter(org.tzi.use.uml.ocl.expr.Expression)
	 */
	@Override
	void enter(Expression expr) {
		super.enter(expr);
		fNodeStack.push(new EvalNode(varBindings()));
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.EvalContext#exit(org.tzi.use.uml.ocl.expr.Expression, org.tzi.use.uml.ocl.value.Value)
	 */
	@Override
	void exit(Expression expr, Value result) {
		super.exit(expr, result);
	    EvalNode n = fNodeStack.pop();
	    n.setExpression(expr);
	    n.setResult(result);
	    
	    if ( !fNodeStack.empty() )
	        fNodeStack.peek().addChild(n);
	    else
	        fRootNode = n;
	}
}
