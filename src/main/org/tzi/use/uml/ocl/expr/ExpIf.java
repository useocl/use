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

import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * If-then-else-endif.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpIf extends Expression {
    private Expression fCondition;
    private Expression fThenExp;
    private Expression fElseExp;
    
    public ExpIf(Expression condition,
                 Expression thenExp,
                 Expression elseExp)
        throws ExpInvalidException
    {
        // result type is type of then/else branch (must be identical)
        super(thenExp.type().getLeastCommonSupertype(elseExp.type()));
        fCondition = condition;
        fCondition.assertBoolean();
        fThenExp = thenExp;
        fElseExp = elseExp;
         
        if (fThenExp.type().getLeastCommonSupertype(fElseExp.type()) == null)
            throw new ExpInvalidException(
                                          "Branches of if expression have different unrelated types, " +
                                          "found `" + fThenExp.type() + 
                                          "' and `" + fElseExp.type() + "'.");
    }

    /**
     * Evaluates expression and returns result value. If the condition
     * evaluates to true return the value of the "then"
     * expression. Otherwise, if the condition evaluates to undefined
     * or false, the result is given by the "else" expression.
     *  
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = UndefinedValue.instance;
        Value condValue = fCondition.eval(ctx);
        // condition may be undefined
        if (condValue.isDefined() ) {
            boolean cond = ((BooleanValue) condValue).value();
            if (cond )
                res = fThenExp.eval(ctx);
            else
                res = fElseExp.eval(ctx);
        }
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("if ");
        fCondition.toString(sb);
        sb.append(" then ");
        fThenExp.toString(sb);
        sb.append(" else ");
        fElseExp.toString(sb);
        return sb.append(" endif");
    }

    public Expression getCondition() {
        return fCondition;
    }

    public Expression getThenExpression() {
        return fThenExp;
    }

    public Expression getElseExpression() {
        return fElseExp;
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitIf(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#childExpressionRequiresPreState()
	 */
	@Override
	protected boolean childExpressionRequiresPreState() {		
		return  fCondition.requiresPreState() || fThenExp.requiresPreState() || fElseExp.requiresPreState();
	}
}
