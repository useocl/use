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

package org.tzi.use.uml.mm;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.al.ALAction;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;

/**
 * An operation is a parameterized expression. Evaluation of the
 * expression has no side-effects wrt the system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MOperation extends MModelElementImpl {
    private VarDeclList fVarDeclList; // A list of parameters
    private Type fResultType;   // The declared result type (optional)
    private Expression fExpr;   // The operation's body (optional)
    private MClass fClass;  // owner
    private List<MPrePostCondition> fPreConditions;
    private List<MPrePostCondition> fPostConditions;
    private int fPositionInModel;

    // for UML AL
    private ALAction fAction;

    MOperation(String name, VarDeclList varDeclList, Type resultType) {
        super(name);
        fVarDeclList = varDeclList;
        fResultType = resultType;
        fExpr = null;
        fPreConditions = new ArrayList<MPrePostCondition>();
        fPostConditions = new ArrayList<MPrePostCondition>();
    }

    /** 
     * Returns the owner class of this operation.
     */
    public MClass cls() {
        return fClass;
    }

    /** 
     * Returns the parameter list of the operation.
     */
    public VarDeclList paramList() {
        return fVarDeclList;
    }

    /** 
     * Returns true if the operation declares a return type.
     */
    public boolean hasResultType() {
        return fResultType != null;
    }

    /** 
     * Returns the declared result type of the operation.
     *
     * @return resultType or null, if no return type specified
     */
    public Type resultType() {
        return fResultType;
    }

    /** 
     * Returns true if the operation has an expression body.
     */
    public boolean hasExpression() {
        return fExpr != null;
    }

    /** 
     * Returns the expression of the operation.
     *
     * @return expression or null, if no expression was specified
     */
    public Expression expression() {
        return fExpr;
    }

    /** 
     * Sets the expression of the operation.
     *
     * @exception MInvalidModelException if the expression's type does
     *            not conform to the declared result type 
     */
    public void setExpression(Expression expr) throws MInvalidModelException {
        // If no result type is set, use type of the expression
    	if (fResultType == null)
    		fResultType = expr.type();
    	else if (! expr.type().isSubtypeOf(fResultType) )
            throw new MInvalidModelException("Expression type `" + 
                                             expr.type() + 
                                             "' does not match declared result type `" + fResultType + "'.");
        fExpr = expr;
    }

    void setClass(MClass cls) {
        fClass = cls;
    }

    /**
     * Returns a string describing the signature of the operation.
     */
    public String signature() {
        String res = name() + "(" + fVarDeclList + ")";
        if (fResultType != null )
            res += " : " + fResultType;
        return res;
    }

    void addPreCondition(MPrePostCondition ppc) {
        fPreConditions.add(ppc);
    }

    void addPostCondition(MPrePostCondition ppc) {
        fPostConditions.add(ppc);
    }

    /**
     * Returns a list of preconditions attached to this operation. Do
     * not modify this list.
     */
    public List<MPrePostCondition> preConditions() {
        return fPreConditions;
    }

    /**
     * Returns a list of postconditions attached to this operation. Do
     * not modify this list.
     */
    public List<MPrePostCondition> postConditions() {
        return fPostConditions;
    }

    /**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel() {
        return fPositionInModel;
    }

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position) {
        fPositionInModel = position;
    }

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitOperation(this);
    }

    // for UML AL
    public void setAction(ALAction action) {
        fAction = action;
    }
    
    // for UML AL
    public ALAction getAction() {
        return fAction;
    }
}
