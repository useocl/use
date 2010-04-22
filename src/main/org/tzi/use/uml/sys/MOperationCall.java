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

package org.tzi.use.uml.sys;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;

/**
 * Call of an operation
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MOperationCall {
    private Expression fTargetExpr; // target object
    private MOperation fOp; // operation to be called
    private Expression[] fArgExprs; // argument expressions

    // the following are set by the enter() method
    private MObject fTargetObj; // result of fTargetExpr
    private Value[] fArgValues; // results of fArgExprs
    private MSystemState fPreState; // saved be enter() method
    private VarBindings fVarBindings; // local scope inside operation

    // the following are set by the exit() method
    private Value fOptionalResult; // may be null
    private boolean fPostconditionsOkOnExit;

    /**
     * Constructs a new operation call object.
     */
    public MOperationCall(Expression target, MOperation op, Expression[] args) {
        fTargetExpr = target;
        fOp = op;
        fArgExprs = args;
    }
    
    Expression target() {
        return fTargetExpr;
    }

    Expression[] argExprs() {
        return fArgExprs;
    }

    
    public MOperation operation() {
        return fOp;
    }

    public MSystemState getPreState() {
    	return fPreState;
    }
    
    /**
     * Evaluates source and argument expressions, inserts "self" and
     * parameters into local scope.
     */
    public void enter(MSystemState state) {
        //System.out.println("*** fTargetExpr: " + fTargetExpr);
        Evaluator evaluator = new Evaluator();
        Value v = evaluator.eval(fTargetExpr, state, 
                                 state.system().topLevelBindings());
        ObjectValue objVal = (ObjectValue) v;
        fTargetObj = objVal.value();

        fArgValues = new Value[fArgExprs.length];
        for (int i = 0; i < fArgExprs.length; i++) {
            //System.out.println("*** fArgExprs[" + i + "]: " + fArgExprs[i]);
            fArgValues[i] = evaluator.eval(fArgExprs[i], state, 
                                           state.system().topLevelBindings());
        }

        // bind the argument values to the operation's parameters and
        // push them into scope
        fVarBindings = new VarBindings();
        VarDeclList params = fOp.paramList();
        for (int i = 0; i < fArgValues.length; i++) {
            VarDecl decl = params.varDecl(i);
            fVarBindings.push(decl.name(), fArgValues[i]);
        }

        // bind the "self" variable to the receiver object.
        fVarBindings.push("self", objVal);
        fPreState = state;
    }

    /**
     * Sets result information on exit.
     */
    void exit(Value optionalResult, boolean postconditionsOkOnExit) {
        fOptionalResult = optionalResult;
        fPostconditionsOkOnExit = postconditionsOkOnExit;
    }


    public MObject targetObject() {
        return fTargetObj;
    }

    /**
     * Returns the argument values passed on entry. The values are
     * available only after a call to enter().
     */
    public Value[] argValues() {
        return fArgValues;
    }

    /**
     * Returns the result after exit. The value returned is null in
     * case the operation did not specify a return value. 
     */
    public Value resultValue() {
        return fOptionalResult;
    }

    /**
     * Returns true if all postconditions were satisfied on exit.
     */
    public boolean postconditionsOkOnExit() {
        return fPostconditionsOkOnExit;
    }

    /**
     * Returns the variable bindings local to this operation. The
     * bindings contain the "self" variable and operation parameters
     * and are available for pre- and postconditions.  
     */
    public VarBindings varBindings() {
        return fVarBindings;
    }

    /**
     * Returns the state in which the operation has been entered.
     */
    public MSystemState preState() {
        return fPreState;
    }
}
