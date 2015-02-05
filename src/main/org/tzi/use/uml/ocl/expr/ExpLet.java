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

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;

/**
 * OCL Let-expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpLet extends Expression {
    private String fVarname;
    private Type fVarType;
    private Expression fVarExpr;
    private Expression fInExpr;
    
    public ExpLet(String varname, 
                  Type varType,
                  Expression varExpr,
                  Expression inExpr)
        throws ExpInvalidException
    {
        super(inExpr.type());
        fVarname = varname;
        fVarType = varType;
        fVarExpr = varExpr;
        fInExpr = inExpr;
        if (! fVarExpr.type().conformsTo(fVarType) )
            throw new ExpInvalidException(
                                          "Type of variable expression `" + fVarExpr.type() +
                                          "' does not match declared type `" + fVarType + "'.");
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;
        Value varValue = fVarExpr.eval(ctx);
        ctx.pushVarBinding(fVarname, varValue);
        res = fInExpr.eval(ctx);
        ctx.popVarBinding();
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("let ")
          .append(fVarname)
          .append(" : ");
        fVarType.toString(sb);
        sb.append(" = ");
        fVarExpr.toString(sb);
        sb.append(" in ");
        return fInExpr.toString(sb);
    }

    public String getVarname() {
        return fVarname;
    }

    public Type getVarType()  {
        return fVarType;
    }

    public Expression getInExpression() {
        return fInExpr;
    }
    
    public Expression getVarExpression() {
        return fVarExpr;
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitLet(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#childExpressionRequiresPreState()
	 */
	@Override
	protected boolean childExpressionRequiresPreState() {
		return fVarExpr.requiresPreState() ||  fInExpr.requiresPreState();
	}
}
