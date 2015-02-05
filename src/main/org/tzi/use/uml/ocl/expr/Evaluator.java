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
import java.util.ArrayList;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.Log;
import org.tzi.use.util.collections.Queue;

/**
 * Evaluation of expressions.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class Evaluator {

    private EvalContext fEvalContext;
    private final boolean fEnableEvalTree;

    /**
     * Creates a default Evaluator without building an evaluation tree.
     * 
     * Turns on building an evaluation tree if <code>enableEvalTree</code> is true. 
     * The tree is used, e.g., in the evaluation browser.  
     *
     */
    public Evaluator() {
    	fEnableEvalTree = false;
    }
    
    /**
     * Creates a default Evaluator.
     * 
     * Turns on building an evaluation tree if <code>enableEvalTree</code> is true. 
     * The tree is used, e.g., in the evaluation browser.  
     *
     */
    public Evaluator(boolean enableEvalTree) {
    	fEnableEvalTree = enableEvalTree;
    }

    /**
     * Evaluates an expression in the specified system state context
     * with a set of initial variable bindings. 
     * Detailed information is printed to <code>evalLog</code>, which can be <code>null</code>.
     */
    public Value eval(Expression expr, 
                      MSystemState preState,
                      MSystemState postState,
                      VarBindings bindings, 
                      PrintWriter evalLog,
                      String evalLogIndent) {
    	if (fEnableEvalTree)
    		fEvalContext = new DetailedEvalContext(preState, postState, bindings, evalLog, evalLogIndent);
    	else if (evalLog != null || Log.isTracing())
    		fEvalContext = new EvalContext(preState, postState, bindings, evalLog, evalLogIndent);
    	else
    		fEvalContext = new SimpleEvalContext(preState, postState, bindings);
    	
        Value res = evaluate(expr);
        if (evalLog != null )
            evalLog.flush();
        return res;
    }
    
    /**
     * Evaluates an expression in the specified system state context
     * with a set of initial variable bindings. Detailed information
     * is printed to evalLog.
     */
    public Value eval(Expression expr, 
            MSystemState preState,
            MSystemState postState,
            VarBindings bindings, 
            PrintWriter evalLog) {
		return this.eval(expr, preState, postState, bindings, evalLog, "  ");
	}

    /**
     * Evaluates an expression in the specified system state context
     * with a set of initial variable bindings.
     * This evaluation method uses the provided system state for the prestate and the current state.  
     * Detailed information is printed to evalLog.
     * @param expr The expression to evaluate.
     * @param postState The system state used as the current and pre state
     * @param bindings The var bindings.
     * @param evalLog A PrintWriter for the details.
     * @return The result value.
     */
    public Value eval(Expression expr, 
                      MSystemState postState,
                      VarBindings bindings, 
                      PrintWriter evalLog)
    {
        return eval(expr, postState, postState, bindings, evalLog);
    }

    /**
     * Evaluates an expression in the specified system state context
     * with a set of initial variable bindings.
     */
    public Value eval(Expression expr, 
                      MSystemState postState,
                      VarBindings bindings)
    {
        return eval(expr, postState, bindings, null);
    }
    
    /**
     * Evaluates an expression in the specified system state context
     * with a set of initial variable bindings.
     */
    public Value eval(
    		Expression expr,
    		MSystemState preState,
    		MSystemState postState,
    		VarBindings bindings) {
    	
    	return eval(expr, preState, postState, bindings, null);
    }
    
    /**
     * Evaluates an expression in the specified system state context.
     */
    public Value eval(Expression expr, MSystemState postState) {
        return eval(expr, postState, new VarBindings(), null);
    }


    private Value evaluate(Expression expr) {
        if (Log.isTracing() )
            Log.trace("Evaluator.eval expr: " + expr);

        Value res = null;
        try {
            res = expr.eval(fEvalContext);
        } catch (StackOverflowError ex) {
            throw new RuntimeException(
                                       "Stack overflow. The expression is probably nested" +
                                       " too deep or contains an infinite recursion.");
        }
        return res;
    }

    public EvalNode getEvalNodeRoot() {
    	if (fEnableEvalTree)
    		return ((DetailedEvalContext)fEvalContext).getEvalNodeRoot();
    	
    	return null;
    }

    /**
     * Evaluates a list of expressions with <code>numThreads</code>
     * threads running in parallel. This method spawns a thread doing
     * the evaluation and returns immediately with a synchronized
     * queue. The caller should use the Queue.get() method on this
     * queue to wait for results. The queue will block until results
     * become available. The order in which results are delivered is
     * the same order passed in as <code>exprList</code>, i.e.,
     * expressions are processed in FIFO order.
     *
     * If <code>numThreads == 1</code>, the expression list will be
     * processed sequentially with no thread overhead.
     *
     * @return Queue(Value) a queue of result values for each expression.  
     */
    public Queue evalList(int numThreads, 
                          ArrayList<Expression> exprList, 
                          MSystemState systemState) {
        if (numThreads < 1 )
            throw new IllegalArgumentException("numThreads == " + numThreads);

        Queue result = new Queue();
        ThreadedEvaluator.Controller controller = 
            new ThreadedEvaluator.Controller(numThreads, result, 
                                             exprList, systemState);
        controller.start();
        return result;
    }
}