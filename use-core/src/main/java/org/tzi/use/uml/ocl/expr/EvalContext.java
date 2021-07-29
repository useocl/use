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

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.Log;

/**
 * Context information used during evaluation.
 *
 * @author  Mark Richters
 */

public class EvalContext {
    private final MSystemState fPreState; // required for postconditions
    private final MSystemState fPostState; // default state
    private final VarBindings fVarBindings;
    private int fNesting;   // for indentation during trace
    
    private final PrintWriter fEvalLog; // may be null    
    private final String fEvalLogIndent;
    
    private final boolean isTracing;
    
    /**
     * Creates new evaluation context. The parameter preState may be
     * <code>null</code>.
     * @param preState the pre <code>MSystemState</code> for the evaluation. May be <code>null</code>
     * @param postState the current <code>MSystemState</code> for the evaluation
     * @param globalBindings the global <code>VarBindings</code>
     * @param evalLog <code>PrintWriter</code> to print evaluation informations. May be <code>null</code>
     * @param evalLogIndent used indent for the output, e.g., "   "
     */
    public EvalContext(MSystemState preState,
    				   MSystemState postState,
    				   VarBindings globalBindings,
    				   PrintWriter evalLog,
    				   String evalLogIndent) {
        fPreState = preState;
        fPostState = postState;
        fVarBindings = new VarBindings(globalBindings);
        fNesting = 0;
        fEvalLog = evalLog;
        fEvalLogIndent = evalLogIndent;
        isTracing = Log.isTracing();
    }

    /**
     * Copy constructor to create a new evaluation context with different
     * states and bindings, but with the same setup, e.g., indent.
     * @param preState The used pre state
     * @param postState The used post state
     * @param globalBindings the global <code>VarBindings</code>
     * @param src the <code>EvalContext</code> to copy the settings from
     */
	public EvalContext(MSystemState preState, MSystemState postState, VarBindings globalBindings, EvalContext src) {
		fPreState = preState;
		fPostState = postState;
		fVarBindings = new VarBindings(globalBindings);
		fNesting = src.fNesting;
		fEvalLog = src.fEvalLog;
		fEvalLogIndent = src.fEvalLogIndent;
		isTracing = src.isTracing;
	}
    
    public boolean isEnableEvalTree() { return false; }
    
    /**
     * Pushes a new variable binding onto the binding stack.
     */
    public void pushVarBinding(String varname, Value value) {
        fVarBindings.push(varname, value);
    }

    /**
     * Pops the last added variable binding from the binding stack.
     */
    void popVarBinding() {
        fVarBindings.pop();
    }
    
    /**
     * Pops the last numToPop added variable bindings from the binding stack 
     */
    void popVarBindings(int numToPop) {
    	for (int i = 0; i < numToPop; ++i) {
    		popVarBinding();
    	}
    }

    /**
     * Returns current state of variable bindings (for debugging).
     */
    public VarBindings varBindings() {
        return fVarBindings;
    }

    /**
     * Returns the current system state.
     */
    public MSystemState postState() {
        return fPostState;
    }

    /**
     * Returns the prestate.
     */
    public MSystemState preState() {
        return fPreState;
    }
    
    /** 
     * Search current bindings for variable name. Visibility is
     * determined by the order of elements. Variable bindings may thus
     * be hidden by bindings at earlier positions.
     *
     * @return value for name binding or null if not bound
     */
    Value getVarValue(String name) {
        return fVarBindings.getValue(name);
    }

    void enter(Expression expr) {
        if (isTracing) {
        	++fNesting;
            String ec = expr.getClass().getName();
            ec = ec.substring(ec.lastIndexOf(".") + 1);
            Log.trace(this, indent() + "enter " + ec + " \"" + expr + "\"");
        }
    }

    void exit(Expression expr, Value result) {
        if (isTracing) {
        	--fNesting;
            Log.trace(this, indent() + "exit  \"" + expr + "\" = " + result);
        }

        // print the results sequentially from the innermost
        // subexpression to the outermost expression
        if ( fEvalLog != null ) {
            fEvalLog.println(fEvalLogIndent + expr + " : " + result.type() + " = " + result);
        }
    }

    private String indent() {
        char[] indent = new char[fNesting];
        for (int i = 0; i < fNesting; i++)
            indent[i] = ' ';
        return new String(indent);
    }
}

