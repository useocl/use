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

import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Constant real expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpConstReal extends Expression {
    private double fValue;

    public ExpConstReal(double d) {
        super(TypeFactory.mkReal());
        fValue = d;
    }

    public ExpConstReal(Double d) {
        super(TypeFactory.mkReal());
        fValue = d.doubleValue();
    }

    public double value() {
        return fValue;
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = new RealValue(fValue);
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append(fValue);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitConstReal(this);		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#childExpressionRequiresPreState()
	 */
	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
}

